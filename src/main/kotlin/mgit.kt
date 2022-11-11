import java.io.BufferedReader
import java.io.File
import java.math.BigInteger
import java.nio.file.Files
import java.nio.file.Paths
import java.security.MessageDigest
import java.io.PrintWriter

fun main(args: Array<String>) {
    println("Mgit it is!")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    if(args[0]=="init") {
        println("init!")
        val path = ".mgit"
        println(Paths.get(path))
        if (Files.exists(Paths.get(path))) {
            println("Mgit is already initialized!!")
        }
        else
            Files.createDirectory(Paths.get(path))
    }
    else if(args[0]=="hash-object"){

        //Read file
        val bufferedReader: BufferedReader = File(args[1]).bufferedReader()
        val inputString = bufferedReader.use { it.readText() }
        println(inputString)

        // sha-1 calculated
        val md = MessageDigest.getInstance("SHA-1")
        val messageDigest = md.digest(inputString.toByteArray())
        val no = BigInteger(1, messageDigest)
        var hashtext = no.toString(16)
        while (hashtext.length < 32) {
            hashtext = "0" + hashtext
        }
        println(hashtext)

        //save file
        val directory = File(".mgit/objects")
        if (directory.mkdirs()) {
            println("object folder created successfully")
        }

        var file = File(".mgit/objects/$hashtext")
        val isNewFileCreated :Boolean = file.createNewFile()
        if(isNewFileCreated){
            println("$hashtext is created successfully.")
            val writer = PrintWriter(".mgit/objects/$hashtext")
            writer.append(inputString)
            writer.close()
        } else{
            println("$hashtext already exists.")
        }

    }
    else if(args[0]=="cat-file"){

        var file = ".mgit\\objects\\" + args[1]
        println(file)
        val output = File(file).readText(Charsets.UTF_8)

        println(output)

    }

    println("Program arguments: ${args.joinToString()}")
}