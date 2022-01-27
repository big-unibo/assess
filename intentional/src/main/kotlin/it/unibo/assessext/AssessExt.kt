package it.unibo.assessext

import com.google.common.collect.Sets
import it.unibo.assess.Assess
import it.unibo.conversational.Utils
import it.unibo.conversational.Utils.quote
import it.unibo.conversational.database.Config
import it.unibo.conversational.database.DBmanager
import it.unibo.conversational.database.QueryGenerator
import it.unibo.conversational.datatypes.DependencyGraph
import org.apache.commons.lang3.tuple.Triple
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.util.*



/**
 * Extended assess intention
 */
class AssessExt(var k: Int = 3) : Assess(null) {

    val id: String = UUID.randomUUID().toString()
    var defaultLabelingFunction: String? = null
    var defaultFunctions: String? = null
    val partialRefinements: MutableList<AssessExt> = mutableListOf()

    fun isNullBenchmark(): Boolean = benchmark == null || benchmark == 0
    private fun <T> concat(l: Collection<T>): String = if (l.isEmpty()) { "" } else { l.map { it.toString() }.reduce { a, b -> "$a,$b" } }

    /**
     * Generate the query bringing together ALL the multi-siblings
     * (SIBLING of Italy) join (SIBLING of Jan-31)
     * Siblings are restricted to the closest parent if present
     */
    fun siblingQuery(): Pair<String, List<String>> {
        val allSiblings = mutableListOf<String>()
        val originalClauses = Sets.newLinkedHashSet(clauses)
        var id = 0
        var pivotId = 0
        var query = ""
        val measure = measures.stream().findFirst().get()
        val allProperties = attributes.flatMap { DependencyGraph.getProperties(cube, it) }

        // for each selection clause (which is a sibling if contained in the by clause)
        originalClauses
            .filter { isNullBenchmark() && attributes.contains(it.left) || benchmarkType == BenchmarkType.SIBLING && it.left == (benchmark as Triple<String, String, String>).left } // filter the potential siblings
            .map {
                val attr = it.left // get the attribute
                val parent = DependencyGraph.getParent(cube, attr) // get its parent
                val value = it.right[0] // get the value
                val table = QueryGenerator.getTable(cube, attr) // get the corresponding table
                val members: MutableSet<String> = mutableSetOf() // accumulator os siblings
                val properties = DependencyGraph.getProperties(cube, attr)
                var whereClause = ""
                var propClause = ""
                var propAttr = ""
                var propValue = ""
                // if the benchmark is not defined, then we need to look for all the siblings from the same parent
                if (isNullBenchmark()) {
                    /* THIS IS AN SQL TRICK
                     * Since oracle does not support dynamic pivoting (i.e., the pivot operator can only contain manually defined columns)
                     * I have to populate the SQL "programmatically" by getting al the siblings from the same parent before running the actual query.
                     * In plain SQL this works: select * from (select "CATEGORY", count(*) as c from ssb_flight.part2 group by "CATEGORY") pivot(sum(c) for "CATEGORY" in ('MFGR#31', 'MFGR#32')) ;
                     * But this doesn't: select * from (select "CATEGORY", count(*) as c from ssb_flight.part2 group by "CATEGORY") pivot (sum(c) for "CATEGORY" in (select distinct "CATEGORY" from ssb_flight.part2))
                     */
                    if (parent.isPresent) {
                        // if the parent exists, selects the siblings among its children
                        whereClause = "where ${parent.get()} in (select ${parent.get()} from $table where $attr = $value)"
                    }
                    // also look for the properies, since we want to retrieve normalized values as well

                    if (properties.size > 1) {
                        // more than one property is not implemented. This would brake how the query is built
                        throw IllegalArgumentException("This is not implemented")
                    } else if (properties.isNotEmpty()) {
                        propClause = "," + concat(properties)
                        propAttr = properties.first()
                    }
                } else if (benchmarkType == BenchmarkType.SIBLING) {
                    // if the benchmark is defined, then we need to look for a single sibling
                    val triple = (benchmark as Triple<String, String, List<String>>)
                    whereClause = "where ${triple.left} in (${it.right.first()}, ${triple.right.first()})"
                    if (scaled != null && scaled.isNotEmpty()) {
                        propClause = ",$scaled"
                        propAttr = scaled
                    }
                }

                DBmanager.executeDataQuery(cube, "select distinct $attr $propClause from $table $whereClause")
                    { res ->
                        while (res.next()) {
                            val sib = res.getString(1)
                            if ("'$sib'" != value) {
                                allSiblings += sib + (if (res.metaData.columnCount == 1) "" else { "_" + res.getString(2) })
                            } else if (res.metaData.columnCount > 1) {
                                propValue = res.getString(2)
                            }
                            members += sib + (if (res.metaData.columnCount == 1) "" else { "_" + res.getString(2) })
                        }
                    }
                if (members.isEmpty()) {
                    throw IllegalArgumentException("Cannot find siblings for $it, did you choose a proper selection predicate?")
                }
                propClause = if (properties.isEmpty() || !isNullBenchmark() && scaled.isEmpty()) "" else ", $propValue as $propAttr"
                clauses.addAll(originalClauses)
                clauses.remove(it)
                clauses.add(Triple.of(it.left, "in", members.map { "'" + it.split("_")[0] + "'" }))
                "select ${it.right[0]} as $attr $propClause, t.* " +
                        "from (${Utils.createQuery(cube, json)}) " +
                        "pivot (sum($measure) for $attr in (" +
                        members
                            .filter { a -> (pivotId++ == 0 || pivotId > 0 && a != value) && (a.contains(value.replace("'", "")) || a.length < 30) }
                            .map { "'${it.split("_")[0]}' as \"${if (it.contains(value.replace("'", ""))) { measure.toUpperCase() } else { it }}\"" }
                            .reduce { a, b -> "$a, $b"} +
                        ")) t"
            }.forEach { a ->
                // join the queries on all the (original) selection predicates
                id += 1
                if (id == 1) {
                    query += "($a) t$id"
                } else {
                    query += " join ($a) t${id} on (${attributes.map { "t${id - 1}.$it = t$id.$it" }.reduce{ a, b -> "$a and $b"}})"
                }
            }
        clauses.clear()
        clauses.addAll(originalClauses)
        return Pair(
            if (query.isNotEmpty()) {
                // due to some oracle constraint, column identifiers must be shorter than 30 characters
                val potentialSiblings = allSiblings.filter { it.length < 30 } // get only the potentially valid siblings
                if (potentialSiblings.isEmpty()) {
                    throw IllegalArgumentException("No candidate siblings satisfied the maximum length")
                }
                // build the query
                "select ${concat(attributes.map { "t1.$it" })}" + // get the attributes
                        ",t1.$measure," + // and the measure
                        concat(
                            potentialSiblings // transfrom the siblings
                                .map {
                                    val sibling = it.split("_")
                                    if (sibling.size == 2) { // if the sibling contains a propery value
                                        val attr = sibling[0] // get the attribute
                                        val propVal = sibling[1] // get the property value
                                        val property = allProperties.first()
                                        if (scaled.isEmpty()) {
                                            // if scaled has not been specified and the sibling contains a property value, then we are dealing with autocompletion
                                            "\"$it\" as \"$attr\"" +
                                                    // as above, only add the scaling if the concatenation satisfies the oracle constraint
                                                    if ("${attr}_$property".length < 30) {
                                                        ",\"$it\"*$property/$propVal as \"${attr}_$property\""
                                                    } else {
                                                        ""
                                                    }
                                        } else {
                                            // if the scaling factor has been specified,
                                            "\"$it\"*$property/$propVal as ${
                                                if (!isNullBenchmark()) {
                                                    "bc_$measure" // if we are not dealing with auto completion
                                                } else {
                                                    "\"$attr\"" // if we are dealing with auto completion
                                                }
                                            }"
                                        }
                                    } else {
                                        "\"${sibling[0]}\" ${
                                            if (!isNullBenchmark()) {
                                                "as bc_$measure"
                                            } else {
                                                ""
                                            }
                                        }"
                                    }
                                }) +
                        " from ($query)"
                // }
            } else {
                ""
            }
        , allSiblings)
        // return Pair(if (query.isNotEmpty()) { "select ${((attributes + listOf(measure)).map { "t1.$it" } + allSiblings.map { "\"$it\"" }).reduce{ a, b -> "$a,$b"}} from $query" } else { "" }, allSiblings)
    }

    fun parentQuery(): Pair<String, List<String>> {
        val parents = mutableListOf<String>()
        val measure = measures.stream().findFirst().get()
        val originalClauses = Sets.newLinkedHashSet(clauses)
        val originalAttributes = Sets.newLinkedHashSet(attributes)
        val allProperties = mutableListOf<String>()
        var id = 0
        var query = ""
        val mapid: MutableMap<String, Int> = mutableMapOf()

        attributes // iterate over the attributes
            .toList() // avoid concurrentModification by cloning the list
            .filter { isNullBenchmark() || benchmark is String && it == DependencyGraph.lca(cube, it, benchmark.toString()).or("") } // consider all the attributes if the benchmark is not specified
            .forEach { child -> // for each attribute
                val table = QueryGenerator.getTable(cube, child)
                val curParents = DependencyGraph.getParents(cube, child, true).toMutableList() // get its parents
                parents += curParents // add the list of the parents
                curParents
                    .filter { isNullBenchmark() || benchmark is String && it.equals(benchmark.toString(), ignoreCase = true) }
                    .forEach { parent -> // iterate over the current parents
                        mapid[parent] = ++id // store the parent along with its global id
                        attributes.clear() // clean the attributes
                        attributes.addAll(originalAttributes) // add the original attributes; e.g., {month, type}
                        attributes.remove(child) // remove the current child; e.g., {month}
                        if (parent.contains("all")) {
                            attributes.add(child)
                        } else {
                            attributes.add(parent)
                        }
                        clauses.clear()
                        clauses.addAll(originalClauses.filter { c -> child != c.left }) // remove any existing clause on type
                        val curJSON = JSONObject(json.toString());
                        val normalizedMeasures: MutableList<JSONObject> = mutableListOf()
                        if (isNullBenchmark()) {
                            var norm = JSONObject()
                            norm.put(quote(Utils.Type.MEA), quote("sum($measure)/count(distinct $table.$child)"))
                            norm.put(quote("AS"), quote(measure))
                            normalizedMeasures += norm
                            val properties = DependencyGraph.getProperties(cube, child)
                            properties.map { property ->
                                norm = JSONObject()
                                norm.put(quote(Utils.Type.MEA), quote("sum($measure)/sum(distinct $table.$property)"))
                                norm.put(quote("AS"), quote(parent + "_$property"))
                                allProperties += parent + "_$property"
                                normalizedMeasures += norm
                            }
                        } else {
                            val norm = JSONObject()
                            norm.put(quote(Utils.Type.MEA), quote("sum($measure)/${if (scaled.isEmpty()) { "count(distinct $table.$child)" } else { "sum(distinct $table.$scaled)" }}"))
                            norm.put(quote("AS"), quote(measure))
                            normalizedMeasures += norm
                        }
                        curJSON.put(quote(Utils.Type.MC), JSONArray(normalizedMeasures))
                        var curquery = Utils.createQuery(cube, curJSON)
                        if (parent.contains("all")) {
                            curquery = curquery
                                .replace(", $table.$child", "", ignoreCase = true)
                                .replace(",$table.$child", "", ignoreCase = true)
                                .replace("$table.$child,", "", ignoreCase = true)
                                .replace("$table.$child ,", "", ignoreCase = true)
                            if (attributes.size == 1) {
                                curquery = curquery
                                    .replace("group by $table.$child", "", ignoreCase = true)
                                    .replace("order by $table.$child", "", ignoreCase = true)
                            }
                        }
                        query += " join ($curquery) t$id on (${attributes.map { a -> if (a.equals(child)) { "1=1" } else {"t0.$a = t$id.$a"} }.reduce{ a, b -> "$a and $b" }})"
                }
            }
        if (query.isEmpty()) {
            return  Pair("", listOf())
        }
        attributes.addAll(originalAttributes)
        attributes.addAll(parents.filter { !it.contains("all") })
        properties.addAll(attributes.flatMap { DependencyGraph.getProperties(cube, it) }.toMutableList())
        clauses.clear()
        clauses.addAll(originalClauses)
        query = "(" + Utils.createQuery(cube, json) + ") t0" + query
        properties.clear()
        attributes.clear()
        attributes.addAll(originalAttributes)
        query = "select " +
                "${concat(attributes.map { "t0.$it" })}," +
                if (allProperties.isNotEmpty()) { concat(allProperties.map { "$it*${it.split("_")[1]} as $it"}) + ","} else { "" } +
                "t0.$measure," +
                "${concat(mapid.map { (k, v) -> "t$v.$measure${if (scaled.isEmpty()) { "" } else { "* $scaled"}} as ${if(!isNullBenchmark()) { "bc_$measure" } else { k } }" })} " +
                "from $query"
        parents += allProperties
        return Pair(query, parents)
    }

    fun getBenchmarks(): String {
        val siblings = siblingQuery()
        val parents = parentQuery()
        if (siblings.first.isEmpty()) {
            return parents.first
        }
        if (parents.first.isEmpty()) {
            return siblings.first
        }
        val query: String =
                "select s.*" + if (parents.second.isEmpty()) { "" } else { "," + concat(parents.second.map { "p.$it" }) } +
                " from (${siblings.first}) s join (${parents.first}) p on (${ attributes.map { "s.$it = p.$it" }.reduce { a, b -> "$a and $b" } })"
        return query
    }

    override fun toPythonCommand(commandPath: String?, path: String?): String {
        val credentials = JSONObject()
        credentials.put(quote("ip"), quote(cube.ip))
        credentials.put(quote("port"), quote(cube.port))
        credentials.put(quote("user"), quote(cube.user))
        credentials.put(quote("pwd"), quote(cube.pwd))
        credentials.put(quote("oracleclient"), quote(Config.getOracleclient()))
        credentials.put(quote("metadata"), quote(cube.metaData))
        val sql = getBenchmarks()
        val fullCommand =
            (commandPath!!.replace("/", File.separator)
                    + " --curid " + curId //
                    + " --credentials " + credentials.toString() //
                    + " --cube " + cubeName //
                    + " --measure " + measures.toList()[0] //
                    + " --byclause " + attributes.stream().reduce { a, b -> "$a,$b" }.get().replace(" ", "")
                    + " --forclause " + clauses.map{ it.left.toUpperCase() }.toList().toString().replace(" ", "") //
                    + " --benchmark " + benchmark.toString().replace(" ", "!") //
                    + " --using " + function //
                    + " --labels " + labelingFunction //
                    + " --sql \"" + sql.replace("\"", "?").replace(" ", "!") + "\""
                    + " --k " + k
                    + " --path " + (if (path!!.contains(" ")) "\"" else "") + path.replace("\\", "/") + filename + (if (path.contains(" ")) "\"" else ""))
        L.warn(fullCommand)
        L.warn(sql)
        return fullCommand
    }

    fun clauseToString(o: Any): String {
        return if (o is Triple<*, *, *>) {
            val c = o as Triple<String, String, List<String>>
            return c.left + c.middle + c.right[0]
        } else {
            o.toString()
        }
    }

    override fun toString(): String {
        val mea = measures.toList()[0]
        return "with ${cubeSyn} " +
                "by ${attributes.reduce { a, b -> "$a, $b" }} " +
                if (clauses.isEmpty()) { "" } else { "for ${clauses.toList().map { clauseToString(it) }.reduce { a, b -> "$a and $b"} } " } +
                "assess $mea " +
                "against ${clauseToString(benchmark)} " +
                (if (scaled.isEmpty()) {""} else {"scaled $scaled "}).replace("\"", "") +
                (if (function == null) "<using $defaultFunctions> " else "using ${getFunctionAsString(function)} ").replace("\"", "") +
                (if (labelingFunction == null) "<labels $defaultLabelingFunction>" else "labels $labelingFunction ").replace("\"", "")
    }

    companion object {
        var curId: String = "foo"

        fun from(a: Assess, k: Int): AssessExt {
            val newa = AssessExt()
            newa.cube = a.cube
            newa.cubeSyn = a.cubeName
            newa.attributes = a.attributes
            newa.properties = a.properties
            newa.clauses = a.clauses
            newa.measures = a.measures
            newa.benchmark = a.benchmark
            newa.scaled = a.scaled
            newa.labelingSchema = a.labelingSchema
            newa.labelingFunction = a.labelingFunction
            newa.benchmarkType = a.benchmarkType
            newa.function = a.functionJSON
            newa.filename = a.filename
            newa.k = k
            return newa
        }
    }
}