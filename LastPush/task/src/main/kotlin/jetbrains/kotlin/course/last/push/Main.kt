package jetbrains.kotlin.course.last.push

fun getPatternHeight(pattern: String): Int = pattern.lines().size

fun fillPatternRow(patternRow: String, patternWidth: Int): String {
    check(patternRow.length <= patternWidth) { "error" }
    return patternRow.padEnd(length = patternWidth, separator)
}

fun repeatHorizontally(pattern: String, n: Int, patternWidth: Int): String {
    val patternLines: List<String> = pattern.lines()
    val patternBuild: StringBuilder = StringBuilder()

    for (line in patternLines){
        val fixedLine: String = fillPatternRow(line, patternWidth)
        for (i in 0 until n){
            patternBuild.append(fixedLine)
        }
        patternBuild.append(newLineSymbol)
    }
    return patternBuild.toString()
}

fun dropTopLine(image: String, width: Int, patternHeight: Int, patternWidth: Int): String {
    if (patternHeight <= 1){
        return image
    }
    val strDrops = (patternWidth * width) + newLineSymbol.length
    return image.drop(strDrops)
}

fun canvasGenerator(pattern: String, width: Int, height: Int): String {
    val patternWidth: Int = getPatternWidth(pattern)
    val initialPattern: String = repeatHorizontally(pattern, width, patternWidth)

    if (height <= 1){
        return initialPattern
    }
    val canvasBuilder = StringBuilder()
    val patternHeight: Int = getPatternHeight(pattern)
    canvasBuilder.append(initialPattern)
    
    val cropPattern = dropTopLine(initialPattern, width, patternHeight, patternWidth)
    for (level in 0 until height - 1){
        canvasBuilder.append(cropPattern)
    }
    return canvasBuilder.toString()
}

fun canvasWithGapsGenerator(pattern: String, width: Int, height: Int): String {
    if (width <= 0 || height <= 0 || pattern.isEmpty()) return ""

    val rawLines = pattern.lines()
    val maxWidth = rawLines.maxOfOrNull { it.length } ?: 0

    val patternLines = rawLines.map { line -> line.padEnd(maxWidth, ' ') }
    val gapLines = patternLines.map { " ".repeat(maxWidth) }

    val canvasLines = mutableListOf<String>()

    for (level in 0 until height) {
        for (lineIndex in patternLines.indices) {
            val currentLine = StringBuilder()

            for (col in 0 until width) {
                val isGap = (level + col) % 2 != 0 && width > 1

                if (isGap) {
                    currentLine.append(gapLines[lineIndex])
                } else {
                    currentLine.append(patternLines[lineIndex])
                }
            }
            canvasLines.add(currentLine.toString())
        }
    }

    return canvasLines.joinToString(separator = newLineSymbol)
}

fun applyGenerator(pattern: String, generatorName: String, width: Int, height: Int): String {
    val trimmedPattern = pattern.trim('\n', '\r')

    val result = when (generatorName) {
        "canvas" -> canvasGenerator(trimmedPattern, width, height)
        "canvasGaps" -> canvasWithGapsGenerator(trimmedPattern, width, height)
        else -> throw IllegalArgumentException("Unexpected generator name: $generatorName")
    }

    return if (result.isNotEmpty() && !result.endsWith(newLineSymbol)) {
        result + newLineSymbol
    } else {
        result
    }
}

// You will use this function later
fun getPattern(): String {
    println(
        "Do you want to use a pre-defined pattern or a custom one? " +
                "Please input 'yes' for a pre-defined pattern or 'no' for a custom one"
    )
    do {
        when (safeReadLine()) {
            "yes" -> {
                return choosePattern()
            }
            "no" -> {
                println("Please, input a custom picture")
                return safeReadLine()
            }
            else -> println("Please input 'yes' or 'no'")
        }
    } while (true)
}

// You will use this function later
fun choosePattern(): String {
    do {
        println("Please choose a pattern. The possible options: ${allPatterns().joinToString(", ")}")
        val name = safeReadLine()
        val pattern = getPatternByName(name)
        pattern?.let {
            return@choosePattern pattern
        }
    } while (true)
}

// You will use this function later
fun chooseGenerator(): String {
    var toContinue = true
    var generator = ""
    println("Please choose the generator: 'canvas' or 'canvasGaps'.")
    do {
        when (val input = safeReadLine()) {
            "canvas", "canvasGaps" -> {
                toContinue = false
                generator = input
            }
            else -> println("Please, input 'canvas' or 'canvasGaps'")
        }
    } while (toContinue)
    return generator
}

// You will use this function later
fun safeReadLine(): String = readlnOrNull() ?: error("Your input is incorrect, sorry")

fun main() {
    // Uncomment this code on the last step of the game

     val pattern = getPattern()
     val generatorName = chooseGenerator()
     println("Please input the width of the resulting picture:")
     val width = safeReadLine().toInt()
     println("Please input the height of the resulting picture:")
     val height = safeReadLine().toInt()

     println("The pattern:$newLineSymbol${pattern.trimIndent()}")

     println("The generated image:")
     println(applyGenerator(pattern, generatorName, width, height))


    //val test = canvasGenerator(rhombus, 5, 3)
}
