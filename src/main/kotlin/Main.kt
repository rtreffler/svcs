package svcs

import svcs.command.*

fun main(args: Array<String>) {
    val commandFactory = CommandFactory(".svcs")
    val commands = CommandsCollection()

    commands.add(commandFactory.createConfigCommand())
    commands.add(commandFactory.createAddCommand())
    commands.add(commandFactory.createLogCommand())
    commands.add(commandFactory.createCommitCommand())
    commands.add(commandFactory.createCheckoutCommand())

    commands.handle(args.firstOrNull() ?: "", args.slice(1..args.lastIndex))
}