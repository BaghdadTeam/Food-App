package logic.helpers

import model.Nutrition

object KetoTestMeals {

    val validKetoMeals = listOf(
        createMealHelper(
            nutrition = Nutrition(
                totalFat = 20.0,
                calories = null,
                sugar = 4.0,
                sodium = null,
                protein = 20.0,
                saturatedFat = null,
                carbohydrates = 5.0,
            )

        ), createMealHelper(
            nutrition = Nutrition(
                totalFat = 20.0,
                calories = null,
                sugar = 4.0,
                sodium = null,
                protein = 40.0,
                saturatedFat = null,
                carbohydrates = 5.0,
            )
        ), createMealHelper(
            nutrition = Nutrition(
                totalFat = 20.0,
                calories = null,
                sugar = 4.0,
                sodium = null,
                protein = 10.0,
                saturatedFat = null,
                carbohydrates = 5.0,
            )

        )
    )
    val inValidKetoMeals = listOf(
        createMealHelper(
            nutrition = Nutrition(
                totalFat = 20.0,
                calories = null,
                sugar = 4.0,
                sodium = null,
                protein = 5.0,
                saturatedFat = null,
                carbohydrates = 5.0,
            )
        ),

        createMealHelper(
            nutrition = Nutrition(
                totalFat = 10.0,
                calories = null,
                sugar = 3.0,
                sodium = null,
                protein = 25.0,
                saturatedFat = null,
                carbohydrates = 6.0,
            )
        ),

        createMealHelper(
            nutrition = Nutrition(
                totalFat = 20.0,
                calories = null,
                sugar = 10.0,
                sodium = null,
                protein = 20.0,
                saturatedFat = null,
                carbohydrates = 8.0,
            )
        ),

        createMealHelper(
            nutrition = Nutrition(
                totalFat = 20.0,
                calories = null,
                sugar = 3.0,
                sodium = null,
                protein = 50.0,
                saturatedFat = null,
                carbohydrates = 5.0,
            )
        ),

        createMealHelper(
            nutrition = Nutrition(
                totalFat = 10.0,
                calories = null,
                sugar = 12.0,
                sodium = null,
                protein = 10.0,
                saturatedFat = null,
                carbohydrates = 60.0,
            )
        )
    )
    val ketoMealWithNullCarbohydrates = listOf(
        createMealHelper(
            nutrition = Nutrition(
                totalFat = 20.0,
                calories = null,
                sugar = 4.0,
                sodium = null,
                protein = 20.0,
                saturatedFat = null,
                carbohydrates = null,
            )
        )
    )
    val ketoMealWithNullNutrition = listOf(
        createMealHelper(
            nutrition = Nutrition(null, null, null, null, null, null, null)
        )
    )
    val ketoMealWithNullSugar = listOf(
        createMealHelper(
            nutrition = Nutrition(
                totalFat = 20.0,
                calories = null,
                sugar = null,
                sodium = null,
                protein = 20.0,
                saturatedFat = null,
                carbohydrates = 5.0,
            )
        )
    )
    val ketoMealWithNullTotalFat = listOf(
        createMealHelper(
            nutrition = Nutrition(
                totalFat = null,
                calories = null,
                sugar = 4.0,
                sodium = null,
                protein = 20.0,
                saturatedFat = null,
                carbohydrates = 5.0,
            )
        )
    )
    val ketoMealWithNullProtein = listOf(
        createMealHelper(
            nutrition = Nutrition(
                totalFat = 20.0,
                calories = null,
                sugar = 4.0,
                sodium = null,
                protein = null,
                saturatedFat = null,
                carbohydrates = 5.0,
            )
        )
    )
}
