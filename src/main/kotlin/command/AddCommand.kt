package svcs.command

import svcs.file.Index
import java.io.File

class AddCommand(private val index: Index): Command {
    private val name = "add"

    override fun getName(): String {
        return name
    }

    override fun getHelp(): String {
        return "Add a file to the index."
    }

    override fun handle(args: List<String>) {
        if (args.isEmpty()) {
            handleNoArgs()
        } else {
            addFiles(args)
        }
    }

    private fun handleNoArgs() {
        val files = index.getFiles()

        if (files.isEmpty()) {
            println("Add a file to the index.")
        } else {
            println("Tracked files:")
            files.forEach { println(it) }
        }
    }

    private fun addFiles(files: List<String>) {
        files.forEach {
            val file = File(it)

            if (file.exists()) {
                index.addFile("$it\n")
                println("The file '$it' is tracked.")
            } else {
                println("Can't find '$it'.")
            }
        }
    }
}