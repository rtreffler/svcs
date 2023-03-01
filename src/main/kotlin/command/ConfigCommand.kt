package svcs.command

import svcs.file.Config

class ConfigCommand(private val config: Config): Command {
    private val name = "config"

    override fun getName(): String {
        return name
    }

    override fun getHelp(): String {
        return "Get and set a username."
    }

    override fun handle(args: List<String>) {
        if (args.isEmpty()) {
            handleNoArgs()
        } else {
            setUpName(args.joinToString(" "))
        }
    }

    private fun handleNoArgs() {
        val name = config.getName()

        if (name != "") {
            println("The username is $name.")
        } else {
            println("Please, tell me who you are.")
        }
    }

    private fun setUpName(name: String) {
        config.setName(name)
        println("The username is $name.")
    }
}