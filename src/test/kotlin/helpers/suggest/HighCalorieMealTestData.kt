package helpers.suggest

import helpers.createMealHelper
import model.Meal
import model.Nutrition

object HighCalorieMealTestData {
    fun listOfTwoTheSameHighCalorieMeals(): List<Meal> {
        return listOf(
            HighCalorieMeal(),
            HighCalorieMeal()
        )
    }

    fun listOfOneHighCalorieMeal(): List<Meal> {
        return listOf(HighCalorieMeal())
    }

    fun listOfOneNotHighCalorieMeal(): List<Meal> {
        return listOf(notHighCalorieMeal())

    }

    fun listOfOneNullMeal(): List<Meal> {
        return listOf(nullMeal())
    }

    fun listOfTwoDifferentHighCalorieMeals(): List<Meal> {
        return listOf(
            createMealHelper(
                id = 1,
                nutrition = Nutrition(
                    calories = 701.0,
                    totalFat = null,
                    sugar = null,
                    sodium = null,
                    protein = null,
                    saturatedFat = null,
                    carbohydrates = null
                )
            ),
            createMealHelper(
                id = 2,
                nutrition = Nutrition(
                    calories = 705.0,
                    totalFat = null,
                    sugar = null,
                    sodium = null,
                    protein = null,
                    saturatedFat = null,
                    carbohydrates = null
                )
            )
        )
    }

    private fun HighCalorieMeal(): Meal {
        return createMealHelper(
            nutrition = Nutrition(
                calories = 701.0,
                totalFat = null,
                sugar = null,
                sodium = null,
                protein = null,
                saturatedFat = null,
                carbohydrates = null,
            )
        )
    }

    private fun notHighCalorieMeal(): Meal {
        return createMealHelper(
            nutrition = Nutrition(
                calories = 0.0,
                totalFat = null,
                sugar = null,
                sodium = null,
                protein = null,
                saturatedFat = null,
                carbohydrates = null,
            )
        )

    }

    private fun nullMeal(): Meal {
        return createMealHelper()

    }


}