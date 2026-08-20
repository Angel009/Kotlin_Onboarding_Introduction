package jetbrains.kotlin.course.hangman

// You will use this function later
fun getGameRules(wordLength: Int, maxAttemptsCount: Int) = "Welcome to the game!$newLineSymbol$newLineSymbol" +
        "In this game, you need to guess the word made by the computer.$newLineSymbol" +
        "The hidden word will appear as a sequence of underscores, one underscore means one letter.$newLineSymbol" +
        "You have $maxAttemptsCount attempts to guess the word.$newLineSymbol" +
        "All words are English words, consisting of $wordLength letters.$newLineSymbol" +
        "Each attempt you should enter any one letter,$newLineSymbol" +
        "if it is in the hidden word, all matches will be guessed.$newLineSymbol$newLineSymbol" +
        "" +
        "For example, if the word \"CAT\" was guessed, \"_ _ _\" will be displayed first, " +
        "since the word has 3 letters.$newLineSymbol" +
        "If you enter the letter A, you will see \"_ A _\" and so on.$newLineSymbol$newLineSymbol" +
        "" +
        "Good luck in the game!"

// You will use this function later
fun isWon(complete: Boolean, attempts: Int, maxAttemptsCount: Int): Boolean = complete && attempts <= maxAttemptsCount

// You will use this function later
fun isLost(complete: Boolean, attempts: Int, maxAttemptsCount: Int): Boolean = !complete && attempts > maxAttemptsCount

fun isComplete(secret: String, currentGuess:String): Boolean{
    val currentGuess: String = currentGuess.replace(separator, "")
    return currentGuess == secret
}

fun safeUserInput(): Char {
    var guess: String
    do{
        println("Please input your guess.")
        guess = safeReadLine()
    }while(!isCorrectInput(guess))

    return guess.uppercase()[0]
}

fun isCorrectInput(userInput: String): Boolean {
    if (userInput.length != 1){
        println("The length of your guess should be 1! Try again!")
        return false
    }
    if (!userInput[0].isLetter()){
        println("You should input only English letters! Try again!")
        return false
    }
    return true
}

fun generateNewUserWord(secret: String, guess: Char, currentUserWord: String):String {
    val newWord = StringBuilder(currentUserWord)
    for (i in secret.indices){
        if (guess == secret[i]) {
            val position: Int = i * 2
            newWord.setCharAt(position, guess)
        }
    }
    return newWord.toString()
}

fun generateSecret(): String {
    val numWords = words.size
    val randomElement = (0 until numWords).random()
    return words[randomElement]
}

fun getHiddenSecret(wordLength: Int): String {
    val secretLength = (wordLength * 2)
    val hiddenSecret = StringBuilder()

    for (i in 0 until secretLength){
        if (i % 2 == 0){
            hiddenSecret.append("_")
        }else{
            hiddenSecret.append(separator)
        }
    }
    return hiddenSecret.toString().removeSuffix(separator)
}

fun getRoundResults(secret: String, guess: Char, currentUserWord: String): String {
    var currWord: String = currentUserWord

    if (guess !in secret){
        println("Sorry, the secret does not contain the symbol: $guess. The current word is $currentUserWord")
    }else{
        currWord = generateNewUserWord(secret, guess, currentUserWord)
        println("Great! This letter is in the word! The current word is $currWord")
    }
    return currWord
}

fun playGame(secret: String, maxAttemptsCount: Int){
    var checkResult: String = getHiddenSecret(secret.length)
    println("I guessed a word: $checkResult")

    var currAttempts: Int = 0
    var completeWord: Boolean = false

    while(!isWon(completeWord, currAttempts, maxAttemptsCount) && !isLost(completeWord, currAttempts, maxAttemptsCount)) {
        val userInput = safeUserInput()

        if (userInput !in secret){
            currAttempts++
        }

        checkResult = getRoundResults(secret, userInput, checkResult)
        completeWord = isComplete(secret, checkResult)
    }

    if (isWon(completeWord, currAttempts, maxAttemptsCount)){
        println("Congratulations! You guessed it!")
    } else {
        println("Sorry, you lost! My word is $secret")
    }
}

fun main() {
    // Uncomment this code on the last step of the game

    println(getGameRules(wordLength, maxAttemptsCount))
    playGame(generateSecret(), maxAttemptsCount)
}
