package org.example.presentation

import logic.usecase.GuessMealGameUseCase
import org.example.utils.EmptyMealsException
import org.example.utils.NoMealFoundException

class GuessGameUI(private val useCase: GuessMealGameUseCase, private val viewer: Viewer,private val reader: Reader) : Feature {
    override val id: Int = FEATURE_ID
    override val name: String = FEATURE_NAME

    override fun execute() {
        try {
            var attempts = ATTEMPTS
            val meal = useCase.guessMealPreparationTime()
            val preparationTime = meal.preparationTime
            for (i in 1..attempts) {
                viewer.log("Guess the preparation time for ${meal.name}:")
                val guess = reader.readInput()?.toIntOrNull()
                if (guess != null) {
                    when {
                        guess == preparationTime -> {
                            viewer.log("Correct! The preparation time is $preparationTime minutes.")
                            return
                        }

                        guess < preparationTime -> viewer.log("Too low!")
                        else -> viewer.log("Too high!")
                    }
                }
                attempts--
            }
            viewer.log("The correct time was $preparationTime minutes.")


        } catch (e: EmptyMealsException) {
            viewer.log("There is no meals in database")
        } catch (e: NoMealFoundException) {
            viewer.log(
                """There is no meals to be used in this game
                |please try again later
            """.trimMargin()
            )
        } catch (e: Exception) {
            viewer.log("There is something wrong getting the data")
        }
    }

    companion object {
        const val FEATURE_ID = 5
        const val FEATURE_NAME = "Guess Game"
        const val ATTEMPTS = 3
    }
}