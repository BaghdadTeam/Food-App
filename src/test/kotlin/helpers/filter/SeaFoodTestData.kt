package helpers.filter

import helpers.createMealHelper
import helpers.createNutritionHelper

object SeaFoodTestData {

    fun allMeals() = listOf(
        seaFoodMeals(),
        nonSeaFoodMeals(),
        mealWithNullTag(),
        seaFoodMealWithNullProtein(),
        seaFoodMealWithNullNutrition()
    ).flatten()

    fun nonSeaFoodMeals() = listOf(
        nonSeaFoodMeal(),
        mealWithNullTag(),
        seaFoodMealWithNullProtein(),
        seaFoodMealWithNullNutrition()
    ).flatten()

    fun rankedSeaFoodMeals() = listOf(
        Pair(1, createMealHelper("Shrimp Pasta", nutrition = createNutritionHelper(protein = 30.0))),
        Pair(2, createMealHelper("Grilled Salmon", nutrition = createNutritionHelper(protein = 20.0)))
    )

    fun seaFoodMeals() = listOf(
        createMealHelper(
            name = "Shrimp Pasta",
            tags = listOf("seafood", "dinner"),
            nutrition = createNutritionHelper(protein = 30.0)
        ),
        createMealHelper(
            name = "Grilled Salmon",
            tags = listOf("seafood"),
            nutrition = createNutritionHelper(protein = 20.0)
        )
    )

    fun nonSeaFoodMeal() = listOf(
        createMealHelper(
            name = "Beef Burger",
            tags = listOf("Dinner", "Beef"),
            nutrition = createNutritionHelper(protein = 80.0)
        ),
    )

    fun mealWithNullTag() = listOf(
        createMealHelper(
            name = "just null tag meal",
            tags = null,
            nutrition = createNutritionHelper(protein = 500.0)
        )
    )

    fun seaFoodMealWithNullProtein() = listOf(
        createMealHelper(
            name = "Null protein meal",
            tags = listOf("seafood"),
            nutrition = createNutritionHelper(protein = null)
        )
    )

    fun seaFoodMealWithNullNutrition() = listOf(
        createMealHelper(
            name = "null nutrition meal",
            tags = listOf("seafood"),
            nutrition = null
        )
    )
}