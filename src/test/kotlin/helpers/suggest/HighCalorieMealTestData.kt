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
        return listOf(NotHighCalorieMeal())

    }

    fun listOfTwoDifferentHighCalorieMeals(): List<Meal>{
        return listOf(createMealHelper(nutrition = Nutrition(
            calories = 701.0,
            totalFat = null,
            sugar = null,
            sodium = null,
            protein = null,
            saturatedFat = null,
            carbohydrates = null)),

            createMealHelper(nutrition = Nutrition(
                calories = 705.0,
                totalFat = null,
                sugar = null,
                sodium = null,
                protein = null,
                saturatedFat = null,
                carbohydrates = null)
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

    private fun NotHighCalorieMeal(): Meal {
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


}