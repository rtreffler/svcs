package svcs.file

import java.io.File

class Index(private val vcsDirName: String = ".svcs") {
    private val indexFileName = "index.txt"

    fun getFiles(): List<String> {
        val vcsDir = File(System.getProperty("user.dir") + File.separator + vcsDirName)

        if (vcsDir.exists() && vcsDir.isDirectory) {
            val indexFile = vcsDir.resolve(indexFileName)

            if (indexFile.canRead()) {
                return indexFile.readLines()
            }
        }

        return emptyList()
    }

    fun addFile(filePath: String) {
        val vcsDir = File(System.getProperty("user.dir") + File.separator + vcsDirName)
        val configFile = vcsDir.resolve(indexFileName)

        if (!vcsDir.exists()) {
            vcsDir.mkdir()
        }

        configFile.appendText(filePath)
    }
}