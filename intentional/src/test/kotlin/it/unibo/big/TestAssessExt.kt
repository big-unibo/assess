package it.unibo.big

import it.unibo.assessext.AssessExecuteExt
import it.unibo.assessext.AssessExt
import it.unibo.conversational.datatypes.DependencyGraph
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import java.util.*
import io.github.cdimascio.dotenv.Dotenv
import it.w4bo.database.waitForIt

class TestAssessExt {

    companion object {
        private const val path = "resources/assess/output/"
        val dotenv = Dotenv.load()
        val WAIT: Int = 1000 * 60 * 1

        @BeforeAll
        @JvmStatic
        internal fun beforeAll() {
            try {
                waitForIt(dotenv.get("ORACLE_DBMS"), dotenv.get("ORACLE_IP"), dotenv.get("ORACLE_PORT").toInt(), dotenv.get("ORACLE_DB"), "foodmart", dotenv.get("ORACLE_PWD"), WAIT)
                waitForIt(dotenv.get("ORACLE_DBMS"), dotenv.get("ORACLE_IP"), dotenv.get("ORACLE_PORT").toInt(), dotenv.get("ORACLE_DB"), "covid_weekly", dotenv.get("ORACLE_PWD"), WAIT)
                waitForIt(dotenv.get("ORACLE_DBMS"), dotenv.get("ORACLE_IP"), dotenv.get("ORACLE_PORT").toInt(), dotenv.get("ORACLE_DB"), "ssb_flight", dotenv.get("ORACLE_PWD"), WAIT)
            } catch (e: Exception) {
                fail { e.message!! }
            }
        }
    }

    @Test
    fun testRefinement1() {
        try {
            val c: AssessExt = AssessExecuteExt.parse("with consommation_electrique by secteurnaf2 assess consototale")
            AssessExecuteExt.execute(c, path)
            val d = c.partialRefinements[0]
            AssessExecuteExt.execute(d, path)
            val a: AssessExt = AssessExecuteExt.parse("with ssbora by nation, category assess quantity")
            AssessExecuteExt.execute(a, path)
            val b = a.partialRefinements[0]
            AssessExecuteExt.execute(b, path)
        } catch (e: Exception) {
            e.printStackTrace()
            fail(e.message)
        }
    }

    @Test
    fun testParents() {
        try {
            val a: AssessExt = AssessExecuteExt.parse("with ssbora by nation assess quantity")
            AssessExecuteExt.execute(a, path)
            // val b: AssessExt = AssessExecuteExt.parse("with ssbora by nation, brand assess quantity")
            // AssessExecuteExt.execute(b, path)
            val c: AssessExt = AssessExecuteExt.parse("with ssbora by nation, category assess quantity")
            AssessExecuteExt.execute(c, path)
            val g: AssessExt = AssessExecuteExt.parse("with ssbora by nation assess quantity against region scaled population using difference(quantity, benchmark.quantity) labels quartile")
            AssessExecuteExt.execute(g, path)
            val j: AssessExt = AssessExecuteExt.parse("with ssbora by nation assess quantity against region using difference(quantity, benchmark.quantity) labels quartile")
            AssessExecuteExt.execute(j, path)
            val d: AssessExt = AssessExecuteExt.parse("with ssbora by category assess quantity")
            AssessExecuteExt.execute(d, path)
            val e: AssessExt = AssessExecuteExt.parse("with ssbora by category assess quantity against allproduct")
            AssessExecuteExt.execute(e, path)
        } catch (e: Exception) {
            e.printStackTrace()
            fail(e.message)
        }
    }

    @Test
    fun testFailure1() {
        try {
            val f: AssessExt = AssessExecuteExt.parse("with ssbora by nation for nation = 'GERMANY' assess quantity against region = 'FRANCE' using difference(quantity, benchmark.quantity) labels quartile")
            AssessExecuteExt.execute(f, path)
            fail()
        } catch (e: Exception) {
            assertTrue(e.message!!.isNotEmpty())
        }
    }

    @Test
    fun testFailure2() {
        try {
            val f: AssessExt = AssessExecuteExt.parse("with ssbora by nation assess quantity against category using difference(quantity, benchmark.quantity) labels quartile")
            AssessExecuteExt.execute(f, path)
            fail()
        } catch (e: Exception) {
            assertTrue(e.message!!.isNotEmpty())
        }
    }

//    @Test
//    fun testScalability() {
//        fun refine(a: AssessExt, first: Boolean = true) {
//            if (first) {
//                AssessExt.curId = UUID.randomUUID().toString()
//            }
//            AssessExecuteExt.execute(a, path)
//            if (a.partialRefinements.isNotEmpty() && a.partialRefinements[0].labelingFunction == null) {
//                refine(a.partialRefinements[0], false)
//            }
//        }
//        try {
//                listOf(1, 3).forEach { k ->
//                    (1..3).forEach { r ->
//                        listOf("ssbora", "ssbora5", "ssbora10", "ssbora15").forEach { c -> // , "ssbora15", "ssbora10", "ssbora5", "ssbora"
//                            // refine(AssessExecuteExt.parse("with $c by nation, year for nation = 'GERMANY' and year = '1992' assess quantity", k))
//                            // refine(AssessExecuteExt.parse("with $c by s_nation, nation for nation = 'GERMANY' assess quantity", k))
//                            // refine(AssessExecuteExt.parse("with $c by s_nation, nation, category for nation = 'GERMANY' and category = 'MFGR#31' assess quantity", k))
//                            // refine(AssessExecuteExt.parse("with $c by s_nation, nation, category, year for nation = 'GERMANY' and category = 'MFGR#31' and year = '1992' assess quantity", k))
//                            // refine(AssessExecuteExt.parse("with $c by nation, category for nation = 'GERMANY' and category = 'MFGR#31' assess quantity", k))
//                            // refine(AssessExecuteExt.parse("with $c by nation, year for nation = 'GERMANY' and year = '1992' assess quantity", k))
//
//                            // refine(AssessExecuteExt.parse("with $c by nation for nation = 'GERMANY' assess quantity", k))
//                            // refine(AssessExecuteExt.parse("with $c by category for category = 'MFGR#31' assess quantity", k))
//                            // refine(AssessExecuteExt.parse("with $c by nation assess quantity", k))
//                            // refine(AssessExecuteExt.parse("with $c by nation, category assess quantity", k))
//                            // refine(AssessExecuteExt.parse("with $c by nation, category, year assess quantity", k))
//
//                            refine(AssessExecuteExt.parse("with $c by nation, category, year for nation = 'GERMANY' and category = 'MFGR#12' and year = '1992' assess quantity", k))
//                            refine(AssessExecuteExt.parse("with $c by year, category for year = '1992' and category = 'MFGR#12' assess quantity", k))
//                            refine(AssessExecuteExt.parse("with $c by year for year = '1992' assess quantity", k))
//
//                            refine(AssessExecuteExt.parse("with $c by year assess quantity", k))
//                            refine(AssessExecuteExt.parse("with $c by year, category assess quantity", k))
//                            refine(AssessExecuteExt.parse("with $c by nation, category, year assess quantity", k))
//                        }
//                    }
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//            fail(e.message)
//        }
//    }

    @Test
    fun testAssess1() {
        try {
            val i: AssessExt = AssessExecuteExt.parse("with ssbora by nation assess quantity against region scaled population using difference(quantity, benchmark.quantity) labels quartile")
            AssessExecuteExt.execute(i, path)
            val h: AssessExt = AssessExecuteExt.parse("with ssbora by nation for nation = 'GERMANY' assess quantity against nation = 'FRANCE' scaled population using difference(quantity, benchmark.quantity) labels quartile")
            AssessExecuteExt.execute(h, path)
            val d: AssessExt = AssessExecuteExt.parse("with ssbora by nation, brand for nation = 'GERMANY' and category = 'MFGR#31' assess quantity")
            AssessExecuteExt.execute(d, path)
            val c: AssessExt = AssessExecuteExt.parse("with ssbora by region, category for category = 'MFGR#31' assess quantity")
            AssessExecuteExt.execute(c, path)
            val b: AssessExt = AssessExecuteExt.parse("with ssbora by nation, category for nation = 'GERMANY' and category = 'MFGR#31' assess quantity")
            AssessExecuteExt.execute(b, path)
            val a: AssessExt = AssessExecuteExt.parse("with ssbora by nation, category for nation = 'GERMANY' assess quantity")
            AssessExecuteExt.execute(a, path)
        } catch (e: Exception) {
            e.printStackTrace()
            fail(e.message)
        }
    }

    @Test
    fun testIncremental1() {
        try {
            val a: AssessExt = AssessExecuteExt.parse("with ssbora by nation assess quantity")
            AssessExecuteExt.execute(a, path)
            val b: AssessExt = AssessExecuteExt.parse("with ssbora by nation assess quantity against region scaled population")
            AssessExecuteExt.execute(b, path)
            val c: AssessExt = AssessExecuteExt.parse("with ssbora by nation assess quantity against REGION scaled POPULATION using difference(QUANTITY, benchmark.QUANTITY)")
            AssessExecuteExt.execute(c, path)
            val d: AssessExt = AssessExecuteExt.parse("with ssbora by nation assess quantity against region scaled population using difference(quantity, benchmark.quantity) labels quartile")
            AssessExecuteExt.execute(d, path)
        } catch (e: Exception) {
            e.printStackTrace()
            fail(e.message)
        }
    }

    @Test
    fun testIncremental2() {
        try {
            // val a: AssessExt = AssessExecuteExt.parse("with ssbora by nation for nation = 'FRANCE' assess quantity")
            // AssessExecuteExt.execute(a, path)
            val b: AssessExt = AssessExecuteExt.parse("with ssbora by nation for nation = 'FRANCE' assess quantity against nation = 'GERMANY' scaled population")
            AssessExecuteExt.execute(b, path)
            val c: AssessExt = AssessExecuteExt.parse("with ssbora by nation for nation = 'FRANCE' assess quantity against nation = 'GERMANY' scaled population using difference(quantity, benchmark.quantity)")
            AssessExecuteExt.execute(c, path)
            val d: AssessExt = AssessExecuteExt.parse("with ssbora by nation for nation = 'FRANCE' assess quantity against nation = 'GERMANY' scaled population using difference(quantity, benchmark.quantity) labels quartile")
            AssessExecuteExt.execute(d, path)
        } catch (e: Exception) {
            e.printStackTrace()
            fail(e.message)
        }
    }

    @Test
    fun testIncremental3() {
        try {
            var b: AssessExt = AssessExecuteExt.parse("with covid19 by country assess deaths against continent")
            AssessExecuteExt.execute(b, path)
            b = AssessExecuteExt.parse("with COVID19 by country for country='Bosnia And Herzegovina' assess deaths against country='France'")
            AssessExecuteExt.execute(b, path)
            b = AssessExecuteExt.parse("with COVID19 by country for country='Bosnia And Herzegovina' assess deaths against country='France' using ratio(deaths, benchmark.deaths)")
            AssessExecuteExt.execute(b, path)
            b = AssessExecuteExt.parse("with COVID19 by country for country='Bosnia And Herzegovina' assess deaths against country='France' using ratio(deaths, benchmark.deaths) labels quartile")
            AssessExecuteExt.execute(b, path)
            b = AssessExecuteExt.parse("with COVID19 by country for country='Bosnia And Herzegovina' assess deaths")
            AssessExecuteExt.execute(b, path)
        } catch (e: Exception) {
            e.printStackTrace()
            fail(e.message)
        }
    }

    @Test
    fun testIncremental4() {
        try {
            var b: AssessExt = AssessExecuteExt.parse("with covid19 by country assess deaths against continent")
            AssessExecuteExt.execute(b, path)
            b = AssessExecuteExt.parse("with COVID19 by country for country='Bosnia And Herzegovina' assess deaths against country='France' scaled population")
            AssessExecuteExt.execute(b, path)
            b = AssessExecuteExt.parse("with COVID19 by country for country='Bosnia And Herzegovina' assess deaths against country='France' scaled population using ratio(deaths, benchmark.deaths)")
            AssessExecuteExt.execute(b, path)
            b = AssessExecuteExt.parse("with COVID19 by country for country='Bosnia And Herzegovina' assess deaths against country='France' scaled population using ratio(deaths, benchmark.deaths) labels quartile")
            AssessExecuteExt.execute(b, path)
        } catch (e: Exception) {
            e.printStackTrace()
            fail(e.message)
        }
    }

    @Test
    fun testIncremental5() {
        try {
            var b: AssessExt = AssessExecuteExt.parse("with covid19 by country assess deaths against continent")
            AssessExecuteExt.execute(b, path)
            b = AssessExecuteExt.parse("with covid19 by country assess deaths against continent using ratio(deaths, benchmark.deaths)")
            AssessExecuteExt.execute(b, path)
            b = AssessExecuteExt.parse("with covid19 by country assess deaths against continent using ratio(deaths, benchmark.deaths) labels quartile")
            AssessExecuteExt.execute(b, path)
        } catch (e: Exception) {
            e.printStackTrace()
            fail(e.message)
        }
    }

    @Test
    fun testIncremental6() {
        try {
            try {
                var b: AssessExt
                // TODO This is BROKEN, cannot compare against grand parent but only against parent
                // b = AssessExecuteExt.parse("with COVID19 by country assess cases against allcountry")
                // AssessExecuteExt.execute(b, path)
                b = AssessExecuteExt.parse("with COVID19 by country, year for country='France' assess deaths against country='United Kingdom' using difference(DEATHS, benchmark.DEATHS)")
                AssessExecuteExt.execute(b, path)
                b = AssessExecuteExt.parse("with COVID19 by country, month for country='Italy' assess cases")
                AssessExecuteExt.execute(b, path)
                b = AssessExecuteExt.parse("with covid19 by country assess deaths against continent scaled population")
                AssessExecuteExt.execute(b, path)
                b = AssessExecuteExt.parse("with covid19 by country assess deaths against continent scaled population using ratio(deaths, benchmark.deaths)")
                AssessExecuteExt.execute(b, path)
                b = AssessExecuteExt.parse("with covid19 by country assess deaths against continent scaled population using ratio(deaths, benchmark.deaths) labels quartile")
                AssessExecuteExt.execute(b, path)
            } catch (e: Exception) {
                e.printStackTrace()
                fail(e.message)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            fail(e.message)
        }
    }

    @Test
    fun testIncremental7() {
        try {
            try {
                var b: AssessExt
                listOf("deaths", "cases").forEach {
                    m ->
                    b = AssessExecuteExt.parse("with covid19 by week for continent = 'Europe' assess $m")
                    AssessExecuteExt.execute(b, path)
                    b = AssessExecuteExt.parse("with COVID19 by country for country='France' assess $m")
                    AssessExecuteExt.execute(b, path)
                    b = AssessExecuteExt.parse("with covid19 by week for continent = 'Europe' assess $m")
                    AssessExecuteExt.execute(b, path)
                    b = AssessExecuteExt.parse("with COVID19 by country, year for country='France' assess $m")
                    AssessExecuteExt.execute(b, path)
                    b = AssessExecuteExt.parse("with COVID19 by country for country='France' assess $m")
                    AssessExecuteExt.execute(b, path)
                }
                // b = AssessExecuteExt.parse("with covid19 by week assess deaths")
                // AssessExecuteExt.execute(b, path)
            } catch (e: Exception) {
                e.printStackTrace()
                fail(e.message)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            fail(e.message)
        }
    }

    @Test
    fun testIncremental8() {
        try {
            var b: AssessExt
            listOf("deaths", "cases").forEach { m ->
                listOf("", "week", "month", "year").forEach { a1 ->
                    listOf("", "country", "continent").forEach { a2 ->
                        if (a1.isNotEmpty() || a2.isNotEmpty()) {
                            val attrs = if (a1.isNotEmpty() && a2.isNotEmpty()) {
                                "$a1, $a2"
                            } else {
                                if (a1.isNotEmpty()) {
                                    a1
                                } else {
                                    a2
                                }
                            }
                            val i = "with covid19 by $attrs for continent = 'Europe' and year='2020' assess $m"
                            println(i)
                            b = AssessExecuteExt.parse(i)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            fail(e.message)
        }
    }

    @Test
    fun testIncremental9() {
        try {
            listOf(1, 3).forEach { k ->
                listOf("deaths", "cases").forEach { m ->
                    var b: AssessExt
                    b = AssessExecuteExt.parse("with COVID19 by country, month for country='Italy' and year='2020' assess $m against country='Andorra' scaled population using reldifference($m, benchmark.$m)", k)
                    AssessExecuteExt.execute(b, path)
                    b = AssessExecuteExt.parse("with COVID19 by country, month for country='Italy' assess $m against country='Andorra' scaled population using reldifference($m, benchmark.$m)", k)
                    AssessExecuteExt.execute(b, path)
                    b = AssessExecuteExt.parse("with COVID19 by country, month for country='Italy' assess $m against country='Malta' scaled population using reldifference($m, benchmark.$m)", k)
                    AssessExecuteExt.execute(b, path)
                    b = AssessExecuteExt.parse("with COVID19 by country, month for country='Italy' and year='2020' assess $m against country = 'Malta' scaled population", k)
                    AssessExecuteExt.execute(b, path)
                    b = AssessExecuteExt.parse("with COVID19 by country, month for country='Italy' and year='2020' assess $m against continent scaled population", k)
                    AssessExecuteExt.execute(b, path)
                    b = AssessExecuteExt.parse("with COVID19 by country, month for country='Italy' and year='2020' assess $m", k)
                    AssessExecuteExt.execute(b, path)
                    b = AssessExecuteExt.parse("with covid19 by country for country='France' assess $m", k)
                    AssessExecuteExt.execute(b, path)
                    b = AssessExecuteExt.parse("with covid19 by month, country for country='Italy' assess $m", k)
                    AssessExecuteExt.execute(b, path)
                    b = AssessExecuteExt.parse("with covid19 by week, country for week='2020-JAN-01' assess $m", k)
                    AssessExecuteExt.execute(b, path)
                    b = AssessExecuteExt.parse("with covid19 by month, country for month='2020-JAN' assess $m", k)
                    AssessExecuteExt.execute(b, path)
                    b = AssessExecuteExt.parse("with covid19 by year, country for continent = 'Europe' and year='2020' assess $m", k)
                    AssessExecuteExt.execute(b, path)
                    b = AssessExecuteExt.parse("with covid19 by year, continent for continent = 'Europe' and year='2020' assess $m", k)
                    AssessExecuteExt.execute(b, path)
                    b = AssessExecuteExt.parse("with covid19 by year for continent = 'Europe' and year='2020' assess $m", k)
                    AssessExecuteExt.execute(b, path)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            fail(e.message)
        }
    }

    @Test
    fun testIncremental10() {
        try {
            var b: AssessExt
            b = AssessExecuteExt.parse("with covid19 by year, country for continent = 'Europ' and year='2020' assess deaths", 1)
            AssessExecuteExt.execute(b, path)
            fail()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Test
    fun testIncremental11() {
        try {
            var b: AssessExt
            b = AssessExecuteExt.parse("with covid19 by year, country for continent = 'Europe' and month='2020-JAN' assess deaths", 1)
            AssessExecuteExt.execute(b, path)
            fail()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Test
    fun testIncremental12() {
        try {
            var b: AssessExt
            b = AssessExecuteExt.parse("with covid19 by week, country for week='2020-JAN-10' assess deaths", 1)
            AssessExecuteExt.execute(b, path)
            fail()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Test
    fun testIncremental14() {
        try {
            var b: AssessExt
            b = AssessExecuteExt.parse("with CONSOMMATION_ELECTRIQUE_EXT by epci assess consototale against epci='248200099 CA Grand Montauban'", 1)
            AssessExecuteExt.execute(b, path)
            fail()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Test
    fun testIncremental13() {
        try {
            var b: AssessExt
            b = AssessExecuteExt.parse("with consommation_electrique_ext by annee, categorieconsommation for categorieconsommation='Entreprise' assess consototale against ALLANNEE", 1)
            AssessExecuteExt.execute(b, path)
            b = AssessExecuteExt.parse("with consommation_electrique by typeEPCI assess consototale", 1)
            AssessExecuteExt.execute(b, path)
            b = AssessExecuteExt.parse("with consommation_electrique by typeIRIS assess consototale", 1)
            AssessExecuteExt.execute(b, path)
            b = AssessExecuteExt.parse("with consommation_electrique by typeIRIS, annee assess consototale", 1)
            AssessExecuteExt.execute(b, path)
            b = AssessExecuteExt.parse("with CONSOMMATION_ELECTRIQUE by commune for commune = '35238 Rennes' assess consototale", 1)
            AssessExecuteExt.execute(b, path)
            b = AssessExecuteExt.parse("with CONSOMMATION_ELECTRIQUE by commune for commune = '35238 Rennes' assess consototale against epci scaled popcomm", 1)
            AssessExecuteExt.execute(b, path)
            b = AssessExecuteExt.parse("with CONSOMMATION_ELECTRIQUE by commune, categorieconsommation, annee for commune='35238 Rennes' and categorieconsommation = 'Entreprise' assess consototale", 1)
            AssessExecuteExt.execute(b, path)
            b = AssessExecuteExt.parse("with CONSOMMATION_ELECTRIQUE by iris for commune = '35238 Rennes' assess consototale", 1)
            AssessExecuteExt.execute(b, path)
            b = AssessExecuteExt.parse("with CONSOMMATION_ELECTRIQUE_EXT by epci for epci='244400404 Nantes Metropole' assess consototale against epci='248200099 CA Grand Montauban'", 1)
            AssessExecuteExt.execute(b, path)
            b = AssessExecuteExt.parse("with CONSOMMATION_ELECTRIQUE by secteurnaf2 assess consototale", 1)
            AssessExecuteExt.execute(b, path)
            b = AssessExecuteExt.parse("with CONSOMMATION_ELECTRIQUE by annee assess consototale", 1)
            AssessExecuteExt.execute(b, path)
            // The last three tests will fail if looking for duplicates in the cube. EPCIs are not unique in the database!!
            b = AssessExecuteExt.parse("with consommation_electrique by EPCI, annee for categorieconsommation = 'Entreprise' assess consototale", 1)
            AssessExecuteExt.execute(b, path)
            b = AssessExecuteExt.parse("with CONSOMMATION_ELECTRIQUE by epci assess consototale", 1)
            AssessExecuteExt.execute(b, path)
            b = AssessExecuteExt.parse("with CONSOMMATION_ELECTRIQUE by epci assess consototale against typeepci", 1)
            AssessExecuteExt.execute(b, path)
        } catch (e: Exception) {
            e.printStackTrace()
            fail(e.message)
        }
    }

    @Test
    fun testIncremental15() {
        try {
            var b: AssessExt
            b = AssessExecuteExt.parse("with consommation_electrique by commune assess consototale", 1)
            var vertexes = DependencyGraph.getDependencies(b.cube).vertexSet().filter { !it.startsWith("pop") && !it.startsWith("all") }.toList()
            vertexes += ""
            vertexes.withIndex().forEach { i1 ->
                vertexes.withIndex().forEach { i2 ->
                    val a1 = i1.value
                    val a2 = i2.value
                    // TODO: i2.index < 4 is added to keep the tests short, comment it to check all the combinations of 2 attributes
                    if ((i2.index < 4 && i1.index < i2.index) && (a1.isEmpty() || a2.isEmpty() || !DependencyGraph.lca(b.cube, a1, a2).isPresent)) {
                        val attrList = listOf(a1, a2).filter { it.isNotEmpty() }
                        val attrs = attrList.stream().reduce { a, b -> "$a,$b" }.get()
                        val i = "WITH CONSOMMATION_ELECTRIQUE by $attrs ASSESS consototale"
                        println(i)
                        b = AssessExecuteExt.parse(i)
                        AssessExecuteExt.execute(b, path)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            fail(e.message)
        }
    }

    @Test
    fun testIncremental16() {
        try {
            val b = AssessExecuteExt.parse("WITH CONSOMMATION_ELECTRIQUE_EXT by epci FOR epci='200065597 CU Caen la Mer' assess consototale", 1)
            AssessExecuteExt.execute(b, path)
            fail()
        } catch (e: Exception) {
        }
    }

    @Test
    fun testIncremental17() {
        try {
            val b = AssessExecuteExt.parse("WITH CONSOMMATION_ELECTRIQUE by commune, epci assess consototale", 1)
            AssessExecuteExt.execute(b, path)
            fail()
        } catch (e: Exception) {
        }
    }

    @Test
    fun testIncremental18() {
        try {
            val b: AssessExt = AssessExecuteExt.parse("WITH CONSOMMATION_ELECTRIQUE by region, typeepci assess consototale", 1)
            AssessExecuteExt.execute(b, path)
            fail()
        } catch (e: Exception) {
        }
    }
}
