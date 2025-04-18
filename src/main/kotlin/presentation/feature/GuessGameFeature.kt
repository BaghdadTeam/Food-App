package presentation.feature

import logic.use_case.GuessMealGameUseCase

class GuessGameFeature(private val useCase: GuessMealGameUseCase) : Feature {
    override val number: Int = 5
    override val name: String = "Guess game"

    override fun execute() {
        var attempts = 3
        val meal = useCase.guessMealPreparationTime()
        val preparationTime = meal.preparationTime
        for (i in 1..attempts) {
            println("Guess the preparation time for ${meal.name}:")
            val guess = readlnOrNull()?.toIntOrNull()
            if (guess != null) {
                when {
                    guess == preparationTime -> { println("Correct! The preparation time is ${preparationTime} minutes.")
                        return
                    }
                    guess < preparationTime -> println("Too low!")
                    else -> println("Too high!")
                }
            }
            attempts--
        }
        println("The correct time was ${preparationTime} minutes.")
    }
}