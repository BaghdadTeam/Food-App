package logic.use_case
import org.example.data.MealsProvider

class GuessMealGameUseCase (
    private val mealsProvider: MealsProvider
){
    fun guessMealPreparationTime() {
        var attempts = 3
        val meal = mealsProvider.meals.random()
        val correctTime = meal.preparationTime

        for (i in 1..attempts) {
            println("Guess the preparation time for ${meal.name}:")
            val guess = readLine()?.toIntOrNull()
            if (guess != null) {
                when {
                    guess == correctTime -> { println("Correct! The preparation time is $correctTime minutes.")
                        return
                    }
                    guess < correctTime -> println("Too low!")
                    guess > correctTime -> println("Too high!")
                }
            }
            attempts--
        }
        println("The correct time was $correctTime minutes.")
    }
}