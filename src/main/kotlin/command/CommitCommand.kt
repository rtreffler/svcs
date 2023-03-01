package svcs.command

import svcs.digest.Digest
import svcs.file.CommitStorage
import svcs.file.Config
import svcs.file.Index
import svcs.file.Log
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class CommitCommand(
    private val config: Config,
    private val index: Index,
    private val log: Log,
    private val digest: Digest,
    private val commitStorage: CommitStorage
): Command {
    private val name = "commit"

    override fun getName(): String {
        return name
    }

    override fun getHelp(): String {
        return "Save changes."
    }

    override fun handle(args: List<String>) {
        if (args.isEmpty()) {
            handleNoArgs()
        } else {
            commitChanges(args.joinToString("\n"))
        }
    }

    private fun handleNoArgs() {
        println("Message was not passed.")
    }

    private fun commitChanges(message: String) {
        val author = config.getName()
        val lastCommitHash = log.getLastCommitHash()
        val commitHash = calculateCommitHash(lastCommitHash, author, message)
        val indexedFiles = index.getFiles()

        if (!haveIndexedFilesChanged(indexedFiles, lastCommitHash)) {
            println("Nothing to commit.")
        } else {
            commitStorage.add(commitHash, indexedFiles)

            log.addLog(
                listOf(
                    "commit $commitHash",
                    "Author: $author",
                    message
                )
            )

            println("Changes are committed.")
        }
    }

    private fun haveIndexedFilesChanged(indexedFiles: List<String>, lastCommitHash: String): Boolean {
        for (filePath in indexedFiles) {
            val currentFileHash = digest.digest(filePath)
            val previousFileHash = digest.digest(commitStorage.getFilePath(lastCommitHash, filePath))

            if (!currentFileHash.contentEquals(previousFileHash)) {
                return true
            }
        }

        return false
    }


    private fun calculateCommitHash(lastHash: String, author: String, message: String): String {
        val hashData = listOf(
            System.getProperty("user.dir"),
            lastHash,
            author,
            OffsetDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ISO_DATE_TIME),
            message
        )

        return digest.digestAsHex(hashData.joinToString(".").toByteArray())
    }
}