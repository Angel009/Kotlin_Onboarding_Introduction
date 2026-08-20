package jetbrains.kotlin.course.almost.done

fun safeReadLine(): String = readlnOrNull()?: error("null value received")

fun trimPicture(picture: String):  String = picture.trimIndent()

fun applyBordersFilter(picture: String): String {
    val pictureLines: List<String> = picture.lines()
    val pictureWidth: Int = getPictureWidth(picture) + 4
    val borderedPicture: StringBuilder = StringBuilder()
    val openClosedBorder: String = "#".repeat(pictureWidth)

    borderedPicture.append(openClosedBorder)

    for (line in pictureLines) {
        borderedPicture.append(newLineSymbol)
        borderedPicture.append(borderSymbol)
        borderedPicture.append(separator)
        borderedPicture.append(line.padEnd(pictureWidth - 4))
        borderedPicture.append(separator)
        borderedPicture.append(borderSymbol)
    }
    borderedPicture.append(newLineSymbol)
    borderedPicture.append(openClosedBorder)

    return borderedPicture.toString()
}

fun applySquaredFilter(picture: String): String {
    val borderedPicture: String = applyBordersFilter(picture)
    val pictureLines: List<String> = borderedPicture.lines()
    val pictureCols: StringBuilder = StringBuilder()

    for (line in pictureLines) {
        pictureCols.append(line)
        pictureCols.append(line)
        pictureCols.append(newLineSymbol)
    }
    val completeCols = pictureCols.toString()
    val rowsLines: List<String> = completeCols.lines()

    for (i in 1 until rowsLines.size) {
        pictureCols.append(rowsLines[i])
        pictureCols.append(newLineSymbol)
    }
    return pictureCols.toString()
}

fun choosePicture(): String {
    var choice: String?
    val pictures:  String = allPictures().joinToString()

    do{
        println("Please choose a picture. The possible options are: $pictures")
        choice = safeReadLine()
        choice = getPictureByName(choice)
    }while (choice == null)
    return choice
}

fun getPicture(): String {
    println("Do you want to use a predefined picture or a custom one? Please input 'yes' for a predefined image or 'no' for a custom one")
    var choice: String
    do {
        choice = when(val userInput: String = safeReadLine()){
            "yes", "no" -> {
                userInput
            }else -> "x"
        }
        if (choice == "x") println("Please input 'yes' or 'no'")
    }while (choice == "x")

    val pictureType: String = when(choice){
        "yes" -> choosePicture()
        else -> {
            println("Please input a custom picture")
            safeReadLine()
        }
    }
    return pictureType
}

fun chooseFilter(): String {
    var choice: String
    do{
        println("Please choose the correct filter: 'borders' or 'squared'")
        choice = when (val userInput: String = safeReadLine()){
            "borders", "squared" -> {
                userInput
            }else -> "x"
        }

    }while( choice == "x" )
    return choice
}

fun applyFilter(picture: String, filter: String): String {
    var trimmedPicture = trimPicture(picture)
    trimmedPicture = when (filter) {
        "borders" -> applyBordersFilter(trimmedPicture)
        "squared" -> applySquaredFilter(trimmedPicture)
        else -> error("Unexpected filter: $filter")
    }
    return trimmedPicture
}

fun photoshop(): Unit {
    val userPicture: String = getPicture()
    val userFilter: String = chooseFilter()
    val appliedFilter: String = applyFilter(userPicture, userFilter)

    println("The old image:")
    println(userPicture)
    println("The transformed picture:")
    println(appliedFilter)
}

fun main() {
    // Uncomment this code on the last step of the game

    photoshop()
}
