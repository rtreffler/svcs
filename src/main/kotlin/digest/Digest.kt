package svcs.digest

import java.io.File
import java.security.MessageDigest
import java.util.HexFormat

class Digest(algorithm: String = "SHA-1") {
    private val md = MessageDigest.getInstance(algorithm)

    fun digest(bytes: ByteArray): ByteArray = md.digest(bytes)

    fun digest(file: File): ByteArray? {
        if (file.canRead()) {
            return digest(file.readBytes())
        }

        return null
    }

    fun digest(filePath: String): ByteArray? = digest(File(filePath))

    fun digestAsHex(bytes: ByteArray): String = HexFormat.of().formatHex(md.digest(bytes))
}
