package it.unibo.web

import it.unibo.assessext.AssessExecuteExt
import it.unibo.assessext.AssessExt
import it.unibo.conversational.database.DBmanager
import it.unibo.web.IntentionServlet.Companion.ERROR
import it.unibo.web.IntentionServlet.Companion.OK
import it.unibo.web.IntentionServlet.Companion.PYTHON_PATH
import it.unibo.web.IntentionServlet.Companion.cleanOldFiles
import it.unibo.web.IntentionServlet.Companion.empty
import it.unibo.web.IntentionServlet.Companion.manipulateInString
import it.unibo.web.IntentionServlet.Companion.manipulateOutString
import it.unibo.web.IntentionServlet.Companion.write
import kotlinx.coroutines.sync.Semaphore
import org.json.JSONObject
import java.io.PrintWriter
import java.io.StringWriter
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Servlet interface for intentions.
 */
@WebServlet("/Assess")
class AssessServlet : HttpServlet() {
    companion object {
        var counter: Int = 0
        var connectionCounter = Semaphore(100, 100)
    }
    val cache: MutableMap<String, AssessExt> = mutableMapOf()

    /**
     * Given a sentence returns the string representing the parsing tree.
     *
     * @param request  request
     * @param response response
     * @throws ServletException in case of error
     * @see HttpServlet.doGet
     */
    @Throws(ServletException::class)
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        var result = JSONObject()
        val error = JSONObject()
        val status: Int
        connectionCounter.release()
        try {
            cleanOldFiles(servletContext.getRealPath("WEB-INF/classes"))
            val value: String = manipulateInString(request.getParameter("value"))
            val k: String = request.getParameter("k")
            val actiontype: String = request.getParameter("actiontype")
            val sessionID: String = request.getParameter("sessionid")
            // val curid: String = request.getParameter("curid")
            // val previd: String = request.getParameter("previd")
            error.put("value", value)
            error.put("k", k)
            error.put("actiontype", actiontype)
            if (!empty(value)) {
                val key = value2key(value)
                val d: AssessExt =
                    if (cache.containsKey("$sessionID-$key") && actiontype != "exe") {
                        cache["$sessionID-$key"]!!
                    } else {
                        AssessExecuteExt.parse(value2key(value), k.toInt())
                    }
                status = OK
                val curCounter = ++counter
                DBmanager.executeQuery(d.cube, "insert into assess_sessions values('$sessionID', '${System.currentTimeMillis()}', '$curCounter', '${value.replace("'", "''").replace("\n", "")}', '$actiontype', $k)")
                if (actiontype != "done" && actiontype != "start") {
                    d.k = k.toInt()
                    // clear the previous refinements (if any)
                    d.partialRefinements.clear()
                    // execute the intention
                    result = AssessExecuteExt.execute(d, servletContext.getRealPath("WEB-INF/classes/"), PYTHON_PATH)
                    // if the action involves refinements
                    if (actiontype != "exe") {
                        d.partialRefinements.withIndex().forEach {
                            // cache them
                            cache[sessionID + "-" + value2key(it.value.toString())] = it.value
                            // and store the results in the database
                            DBmanager.executeQuery(d.cube, "insert into assess_sessions values('$sessionID', '${System.currentTimeMillis()}', '$curCounter', '${it.value.toString().replace("'", "''").replace("\n", "")}', 'intention-execution', ${it.index})")
                        }
                    } else {
                        // store the exe result in the database
                        DBmanager.executeQuery(d.cube, "insert into assess_sessions values('$sessionID', '${System.currentTimeMillis()}', '$curCounter', '${value.replace("'", "''").replace("\n", "")}', 'intention-execution', $k)")
                    }
                } else {
                    cache.keys.filter { it.startsWith(sessionID) }.forEach { cache.remove(it) }
                }
            } else {
                status = ERROR
                result = JSONObject()
                error.put("error", "Empty string")
            }
            write(response, if (status == OK) manipulateOutString(result) else error.toString())
        } catch (ex: Exception) {
            ex.printStackTrace()
            val sw = StringWriter()
            ex.printStackTrace(PrintWriter(sw))
            try {
                error.put("error", ex.localizedMessage)
                error.put("parameters", request.parameterNames.toList().map { Pair(it, request.getParameter(it)) })
                error.put("stacktrace", sw.toString())
                error.put("python", PYTHON_PATH)
                write(response, error.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        connectionCounter.tryAcquire()
        if (connectionCounter.availablePermits == 0) {
            DBmanager.closeAllConnections()
            println("Kill")
        } else {
            println("Skip")
        }
    }

    fun value2key(value: String): String {
        return value.replace("<[\\w\\(\\),\\s\\.]*>".toRegex(), "").replace("\\n".toRegex(), " ").replace("\\s+".toRegex(), " ")
    }

    /**
     * Given a sentence returns the string representing the parsing tree.
     *
     * @param request
     * request
     * @param response
     * response
     * @throws ServletException
     * in case of error
     */
    @Throws(ServletException::class)
    override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        doGet(request, response)
    }
}