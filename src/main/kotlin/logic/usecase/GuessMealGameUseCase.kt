package logic.usecase

import logic.MealsProvider

class GuessMealGameUseCase (
    private val mealsProvider: MealsProvider
){
    fun execute() {
        var attempts = 3
        val meal = mealsProvider.getMeals().random()
        val correctTime = meal.preparationTime

        for (i in 1..attempts) {
            println("Guess the preparation time for ${meal.name}:")
            val guess = readlnOrNull()?.toIntOrNull()
            if (guess != null) {
                when {
                    guess == correctTime -> { println("Correct! The preparation time is $correctTime minutes.")
                        return
                    }
                    guess < correctTime -> println("Too low!")
                    else -> println("Too high!")
                }
            }
            attempts--
        }
        println("The correct time was $correctTime minutes.")
    }
}