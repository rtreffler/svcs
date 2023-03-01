package svcs.command

import kotlin.math.max

class CommandsCollection {
    private val commands: MutableList<Command> = emptyList<Command>().toMutableList()
    private var longestCommandName = 0

    fun add(command: Command) {
        commands.add(command)
        longestCommandName = max(longestCommandName, command.getName().length + 2)
    }

    fun handle(commandStr: String, args: List<String>) {
        if (commandStr.isEmpty() || commandStr == "--help") {
            showHelp()

            return
        }

        commands
            .firstOrNull { it.getName() == commandStr }
            .let {
                if (it == null) {
                    showUnknownCommand(commandStr)

                    return
                }

                it.handle(args)
            }
    }

    private fun showUnknownCommand(commandStr: String) {
        println("'$commandStr' is not a SVCS command.")
    }

    private fun showHelp() {
        println("These are SVCS commands:")
        commands.forEach { println("%-${longestCommandName}s %s".format(it.getName(), it.getHelp())) }
    }
}