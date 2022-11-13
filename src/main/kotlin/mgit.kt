import java.io.BufferedReader
import java.io.File
import java.math.BigInteger
import java.nio.file.Files
import java.nio.file.Paths
import java.security.MessageDigest
import java.io.PrintWriter

fun main(args: Array<String>) {

    //Initialize empty mgit directory
    if(args[0]=="init") {
        val path = ".mgit"
        if (Files.exists(Paths.get(path))) {
            println("Mgit repository is already initialized!!")
        }
        else{
            println("Initialized empty mgit repository in ${Paths.get(path)}")
            Files.createDirectory(Paths.get(path))
        }

    }
    else if(args[0]=="hash-object"){

        //Read file
        val bufferedReader: BufferedReader = File(args[1]).bufferedReader()
        val inputString = bufferedReader.use { it.readText() }

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
            val writer = PrintWriter(".mgit/objects/$hashtext")
            writer.append(inputString)
            writer.close()
        } else{
            println("$hashtext already exists.")
        }
    }
    else if(args[0]=="cat-file"){
        var file = ".mgit\\objects\\" + args[1]
        val output = File(file).readText(Charsets.UTF_8)
        println(output)

    }
}