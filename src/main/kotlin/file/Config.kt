package svcs.file

import java.io.File

class Config(private val vcsDirName: String = ".svcs") {
    private val configFileName = "config.txt"

    fun getName(): String {
        val vcsDir = File(System.getProperty("user.dir") + File.separator + vcsDirName)

        if (vcsDir.exists() && vcsDir.isDirectory) {
            val configFile = vcsDir.resolve(configFileName)

            if (configFile.canRead()) {
                return configFile.readText()
            }
        }

        return ""
    }

    fun setName(name: String) {
        val vcsDir = File(System.getProperty("user.dir") + File.separator + vcsDirName)
        val configFile = vcsDir.resolve(configFileName)

        if (!vcsDir.exists()) {
            vcsDir.mkdir()
        }

        configFile.writeText(name)
    }
}