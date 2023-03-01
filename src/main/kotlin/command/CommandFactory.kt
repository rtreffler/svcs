package svcs.command

import svcs.digest.Digest
import svcs.file.*

class CommandFactory(vcsDirName: String = ".svcs") {
    private val config = Config(vcsDirName)
    private val index = Index(vcsDirName)
    private val log = Log(vcsDirName)
    private val digest = Digest()
    private val commitStorage = CommitStorage(vcsDirName)

    fun createConfigCommand(): ConfigCommand = ConfigCommand(config)
    fun createAddCommand(): AddCommand = AddCommand(index)
    fun createLogCommand(): LogCommand = LogCommand(log)
    fun createCommitCommand(): CommitCommand = CommitCommand(config, index, log, digest, commitStorage)
    fun createCheckoutCommand(): CheckoutCommand = CheckoutCommand(log, commitStorage)
}