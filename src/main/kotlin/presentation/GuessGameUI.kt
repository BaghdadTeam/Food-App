package org.example.presentation

import logic.usecase.GuessMealGameUseCase
import org.example.utils.EmptyMeals
import org.example.utils.NoElementMatch

class GuessGameUI(private val useCase: GuessMealGameUseCase) : Feature {
    override val id: Int = FEATURE_ID
    override val name: String = FEATURE_NAME

    override fun execute() {
        try {
            var attempts = ATTEMPTS
            val meal = useCase.guessMealPreparationTime()
            val preparationTime = meal.preparationTime
            for (i in 1..attempts) {
                println("Guess the preparation time for ${meal.name}:")
                val guess = readlnOrNull()?.toIntOrNull()
                if (guess != null) {
                    when {
                        guess == preparationTime -> {
                            println("Correct! The preparation time is $preparationTime minutes.")
                            return
                        }

                        guess < preparationTime -> println("Too low!")
                        else -> println("Too high!")
                    }
                }
                attempts--
            }
            println("The correct time was $preparationTime minutes.")


        }catch (e: EmptyMeals) {
            println("There is no meals in database")
        } catch (e: NoElementMatch) {
            println(
                """There is no meals to be used in this game
                |please try again later
            """.trimMargin()
            )
        } catch (e: Exception) {
            println("There is something wrong getting the data")
        }
    }

    companion object {
        const val FEATURE_ID = 5
        const val FEATURE_NAME = "Guess Game"
        const val ATTEMPTS = 3
    }
}