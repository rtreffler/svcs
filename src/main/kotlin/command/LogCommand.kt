package svcs.command

import svcs.file.Log

class LogCommand(private val log: Log): Command {
    private val name = "log"

    override fun getName(): String {
        return name
    }

    override fun getHelp(): String {
        return "Show commit logs."
    }

    override fun handle(args: List<String>) {
        val logs = log.getLogs()

        if (logs.isEmpty()) {
            println("No commits yet.")
        } else {
            logs.forEach {
                println(it.joinToString("\n"))
            }
        }
    }
}