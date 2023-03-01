package svcs.file

import java.io.File

class CommitStorage(private val vcsDirName: String = ".svcs") {
    private val commitsDirName = "commits"

    fun add(commitHash: String, files: List<String>) {
        val vcsDir = File(System.getProperty("user.dir") + File.separator + vcsDirName)
        val commitDir = vcsDir.resolve(commitsDirName + File.separator + commitHash)

        files.forEach {
            File(it).copyTo(commitDir.resolve(it))
        }
    }

    fun getFilePath(commitHash: String, filePath: String): String {
        return System.getProperty("user.dir") + File.separator +
            vcsDirName + File.separator +
            commitsDirName + File.separator +
            commitHash + File.separator +
            filePath
    }

    fun checkout(commitHash: String) {
        val wd = File(System.getProperty("user.dir"))
        val vcsDir = wd.resolve(vcsDirName)
        val commitDir = vcsDir.resolve(commitsDirName + File.separator + commitHash + File.separator)

        if (commitDir.canRead()) {
            commitDir.walkTopDown().forEach {
                if (!it.isDirectory) {
                    val relativePath = it.relativeToOrSelf(commitDir)
                    val newPath = wd.resolve(relativePath)

                    it.copyTo(newPath, true)
                }
            }
        }
    }
}