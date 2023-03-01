package svcs.file

import java.io.File

class Log(private val vcsDirName: String = ".svcs") {
    private val logFileName = "log.txt"

    fun getLogs(): List<List<String>> {
        val vcsDir = File(System.getProperty("user.dir") + File.separator + vcsDirName)
        val result = mutableListOf<MutableList<String>>()

        if (vcsDir.exists() && vcsDir.isDirectory) {
            val logFile = vcsDir.resolve(logFileName)

            if (logFile.canRead()) {
                var commitLine = mutableListOf<String>()

                logFile.forEachLine {
                    commitLine.add(it)
                    if (it.isEmpty()) {
                        result.add(commitLine)
                        commitLine = mutableListOf()
                    }
                }
                if (commitLine.size > 0) {
                    result.add(commitLine)
                }

                return result.asReversed()
            }
        }

        return emptyList()
    }

    fun getLastCommitHash(): String {
        val logs = getLogs()

        if (logs.isEmpty()) {
            return ""
        }

        return getHashFromCommitLog(logs.last())
    }

    private fun getHashFromCommitLog(commitLog: List<String>) = commitLog.first().split(" ").last()

    fun hasCommitHash(commitHash: String): Boolean {
        val commitLog = getLogs().find { getHashFromCommitLog(it) == commitHash }

        return commitLog != null
    }

    fun addLog(lines: List<String>) {
        val vcsDir = File(System.getProperty("user.dir") + File.separator + vcsDirName)
        val logFile = vcsDir.resolve(logFileName)

        if (!vcsDir.exists()) {
            vcsDir.mkdir()
        }

        lines.forEach {
            logFile.appendText("$it\n")
        }
        logFile.appendText("\n")
    }
}