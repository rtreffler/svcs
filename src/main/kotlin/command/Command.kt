package svcs.command

interface Command {
    fun getName(): String
    fun getHelp(): String
    fun handle(args: List<String>)
}