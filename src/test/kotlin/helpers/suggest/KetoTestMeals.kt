package helpers.suggest

import helpers.createMealHelper
import model.Nutrition

object KetoTestMeals {

    val validKetoMeals = listOf(
        createMealHelper(
            nutrition = Nutrition(
                totalFat = 20.0, protein = 20.0, carbohydrates = 5.0, sugar = 4.0,
                calories = null, sodium = null, saturatedFat = null
            )
        ),
        createMealHelper(
            nutrition = Nutrition(
                totalFat = 20.0, protein = 40.0, carbohydrates = 5.0, sugar = 4.0,
                calories = null, sodium = null, saturatedFat = null
            )
        ),
        createMealHelper(
            nutrition = Nutrition(
                totalFat = 20.0, protein = 10.0, carbohydrates = 5.0, sugar = 4.0,
                calories = null, sodium = null, saturatedFat = null
            )
        )
    )

    val invalidKetoMeals = listOf(
        createMealHelper(
            nutrition = Nutrition(
                totalFat = 20.0, protein = 5.0, carbohydrates = 5.0, sugar = 4.0,
                calories = null, sodium = null, saturatedFat = null
            )
        ),
        createMealHelper(
            nutrition = Nutrition(
                totalFat = 10.0, protein = 25.0, carbohydrates = 6.0, sugar = 3.0,
                calories = null, sodium = null, saturatedFat = null
            )
        ),
        createMealHelper(
            nutrition = Nutrition(
                totalFat = 20.0, protein = 20.0, carbohydrates = 8.0, sugar = 10.0,
                calories = null, sodium = null, saturatedFat = null
            )
        ),
        createMealHelper(
            nutrition = Nutrition(
                totalFat = 20.0, protein = 50.0, carbohydrates = 5.0, sugar = 3.0,
                calories = null, sodium = null, saturatedFat = null
            )
        ),
        createMealHelper(
            nutrition = Nutrition(
                totalFat = 10.0, protein = 10.0, carbohydrates = 60.0, sugar = 12.0,
                calories = null, sodium = null, saturatedFat = null
            )
        )
    )


    val edgeCases = listOf(
        createMealHelper(
            nutrition = Nutrition(
                totalFat = 20.0, protein = 20.0, carbohydrates = null, sugar = 4.0,
                calories = null, sodium = null, saturatedFat = null
            )
        ),
        createMealHelper(
            nutrition = Nutrition(null, null, null, null, null, null, null)
        ),
        createMealHelper(
            nutrition = Nutrition(
                totalFat = 20.0, protein = 20.0, carbohydrates = 5.0, sugar = null,
                calories = null, sodium = null, saturatedFat = null
            )
        ),
        createMealHelper(
            nutrition = Nutrition(
                totalFat = null, protein = 20.0, carbohydrates = 5.0, sugar = 4.0,
                calories = null, sodium = null, saturatedFat = null
            )
        ),
        createMealHelper(
            nutrition = Nutrition(
                totalFat = 20.0, protein = null, carbohydrates = 5.0, sugar = 4.0,
                calories = null, sodium = null, saturatedFat = null
            )
        ),
        createMealHelper(
            nutrition = Nutrition(
                totalFat = 20.0, protein = null, carbohydrates = 60.0, sugar = 4.0,
                calories = null, sodium = null, saturatedFat = null
            )
        )
    )

    val baseKetoMeal = createMealHelper(
        nutrition = Nutrition(
            totalFat = 20.0, protein = 20.0, carbohydrates = 5.0, sugar = 4.0,
            calories = null, sodium = null, saturatedFat = null
        )
    )
    val ketoMealsWithNullFat = listOf(
        createMealHelper(
            nutrition = Nutrition(
                totalFat = null,
                protein = 20.0,
                sugar = 3.0,
                carbohydrates = 5.0,
                calories = null,
                sodium = null,
                saturatedFat = null
            )
        ),
        createMealHelper(
            nutrition = Nutrition(
                totalFat = null,
                protein = 15.0,
                sugar = 2.0,
                carbohydrates = 4.0,
                calories = null,
                sodium = null,
                saturatedFat = null
            )
        )
    )
    val nullNutritionMeal = createMealHelper(nutrition = null)

}