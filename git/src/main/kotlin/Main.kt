import java.io.File
import java.math.BigInteger
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.security.MessageDigest


fun main(command: Array<String>) {

    var path: String = ".mgit"
    var dir: File = File("\\")

        if(command.contains("init")) {
            dir = File(path)
            if(dir.mkdir()) {
                println("Directory created successfully")
            } else {
                println("Directory already exists")
            }
        } else if(command.contains("hash-object")) {
            var fname = command[1]
            val file = File(fname)
            var content = file.readText()
            val msgDigType = MessageDigest.getInstance("SHA-1")
            val messageDigest = msgDigType.digest(content.toByteArray())
            val num = BigInteger(1, messageDigest)
            //println(num)
            val hashText = num.toString(16)
            println(hashText)

            val objectsDir = File(".mgit\\objects")
            if(objectsDir.mkdir()) {
                println("Objects directory created successfully")
            } else {
                println("Objects directory already exists")
            }
            val to = File(".mgit\\objects\\" + hashText)
            val created = to.createNewFile()
            if(created) {
                Files.copy(file.toPath(), to.toPath(), StandardCopyOption.REPLACE_EXISTING)
                println("Created file under objects directory")
            } else {
                println("File already exists")
            }
        } else if(command.contains("cat-file")) {
            val file = File(".mgit\\objects\\" + command[1])
            var content = file.readText()
            println(content)
        }
}