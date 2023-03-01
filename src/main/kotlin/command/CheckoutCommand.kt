package svcs.command

import svcs.file.CommitStorage
import svcs.file.Log

class CheckoutCommand(private val log: Log, private val commitStorage: CommitStorage): Command {
    private val name = "checkout"

    override fun getName(): String {
        return name
    }

    override fun getHelp(): String {
        return "Restore a file."
    }

    override fun handle(args: List<String>) {
        if (args.isEmpty()) {
            handleNoArgs()
        } else {
            checkoutCommit(args.first())
        }
    }

    private fun checkoutCommit(commitHash: String) {
        if (!log.hasCommitHash(commitHash)) {
            println("Commit does not exist.")
        } else {
            commitStorage.checkout(commitHash)
            println("Switched to commit $commitHash.")
        }
    }

    private fun handleNoArgs() {
        println("Commit id was not passed.")
    }
}