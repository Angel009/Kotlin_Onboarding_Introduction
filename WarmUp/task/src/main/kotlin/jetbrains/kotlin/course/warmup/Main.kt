package jetbrains.kotlin.course.warmup

// You will use this function later
fun getGameRules(wordLength: Int, maxAttemptsCount: Int, secretExample: String) =
    "Welcome to the game! $newLineSymbol" +
            newLineSymbol +
            "Two people play this game: one chooses a word (a sequence of letters), " +
            "the other guesses it. In this version, the computer chooses the word: " +
            "a sequence of $wordLength letters (for example, $secretExample). " +
            "The user has several attempts to guess it (the max number is $maxAttemptsCount). " +
            "For each attempt, the number of complete matches (letter and position) " +
            "and partial matches (letter only) is reported. $newLineSymbol" +
            newLineSymbol +
            "For example, with $secretExample as the hidden word, the BCDF guess will " +
            "give 1 full match (C) and 1 partial match (B)."

fun generateSecret(): String = "ABCD"

fun countPartialMatches(secret: String, guess: String): Int = countAllMatches(secret, guess) - countExactMatches(secret, guess)

fun countExactMatches(secret: String, guess: String): Int{
    val filterMatches = secret.filterIndexed { index, symbol -> guess[index] == symbol }

    return filterMatches.length
}

fun countAllMatches(secret: String, guess: String): Int{
    val matchesGuess: String = guess.filter{it in secret}
    val matchesSecret: String = secret.filter{it in guess}

    return minOf(matchesGuess.length, matchesSecret.length)
}

fun printRoundResults(secret: String, guess: String) {
    val fullMatches = countExactMatches(secret, guess)
    val partialMatches = countPartialMatches(secret, guess)

    println("Your guess has $fullMatches full matches and $partialMatches partial matches.")
}

fun isWon(complete: Boolean, attempts: Int, maxAttempts: Int): Boolean = complete && attempts <= maxAttempts

fun isLost(complete: Boolean, attempts: Int, maxAttemptsCount: Int): Boolean = !complete && attempts > maxAttemptsCount

fun isComplete(secret: String, guess: String): Boolean = if (secret == guess) true else false

fun playGame(secret: String, wordLength: Int, maxAttemptsCount: Int){
    var userAttempts = 0
    do{
        println("Please input your guess. It should be of length $wordLength.")
        val guess = safeReadLine()
        val complete = isComplete(secret, guess)

        userAttempts++

        printRoundResults(secret, guess)

        if (isWon(complete, userAttempts, maxAttemptsCount)) {
            println("Congratulations! You guessed it!")
        }else if(isLost(complete, userAttempts, maxAttemptsCount)){
            println("Sorry, you lost! :( My word is $secret")
        }

    }while (!complete && userAttempts <= maxAttemptsCount)
}


fun main() {
    val wordLength: Int = 4
    val maxAttemptsCount: Int = 3
    val secretExample: String = "ACEB"

    println(getGameRules(wordLength, maxAttemptsCount, secretExample))

    playGame(generateSecret(), wordLength, maxAttemptsCount)
}
