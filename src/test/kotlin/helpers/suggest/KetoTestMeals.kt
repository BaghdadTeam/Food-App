package helpers.suggest

import helpers.createMealHelper
import model.Nutrition

object KetoTestMeals {

    fun allMeals() = listOf(
        validKetoMeals(),
        invalidKetoMeals(),
        edgeCases()
    ).flatten()

    fun validKetoMeals() = listOf(
        createMealHelper(
            name = "Avocado Chicken Salad",
            nutrition = Nutrition(
                totalFat = 20.0, protein = 20.0, carbohydrates = 5.0, sugar = 4.0,
                calories = null, sodium = null, saturatedFat = null
            )
        ),
        createMealHelper(
            name = "Bacon and Eggs",
            nutrition = Nutrition(
                totalFat = 25.0, protein = 18.0, carbohydrates = 2.0, sugar = 1.0,
                calories = null, sodium = null, saturatedFat = null
            )
        ),
        createMealHelper(
            name = "Salmon with Butter Sauce",
            nutrition = Nutrition(
                totalFat = 30.0, protein = 25.0, carbohydrates = 3.0, sugar = 1.0,
                calories = null, sodium = null, saturatedFat = null
            )
        ),
        createMealHelper(
            name = "Cheese Omelette",
            nutrition = Nutrition(
                totalFat = 22.0, protein = 15.0, carbohydrates = 2.0, sugar = 1.0,
                calories = null, sodium = null, saturatedFat = null
            )
        ),
        createMealHelper(
            name = "Beef Steak with Vegetables",
            nutrition = Nutrition(
                totalFat = 20.0, protein = 40.0, carbohydrates = 5.0, sugar = 4.0,
                calories = null, sodium = null, saturatedFat = null
            )
        ),
        createMealHelper(
            name = "Keto Fat Bombs",
            nutrition = Nutrition(
                totalFat = 20.0, protein = 10.0, carbohydrates = 5.0, sugar = 4.0,
                calories = null, sodium = null, saturatedFat = null
            )
        )
    )

    fun invalidKetoMeals() = listOf(
        createMealHelper(
            name = "Pasta Carbonara",
            nutrition = Nutrition(
                totalFat = 20.0, protein = 5.0, carbohydrates = 5.0, sugar = 4.0,
                calories = null, sodium = null, saturatedFat = null
            )
        ),
        createMealHelper(
            name = "Fruit Salad",
            nutrition = Nutrition(
                totalFat = 10.0, protein = 25.0, carbohydrates = 6.0, sugar = 3.0,
                calories = null, sodium = null, saturatedFat = null
            )
        ),
        createMealHelper(
            name = "Pancakes with Syrup",
            nutrition = Nutrition(
                totalFat = 20.0, protein = 20.0, carbohydrates = 8.0, sugar = 10.0,
                calories = null, sodium = null, saturatedFat = null
            )
        ),
        createMealHelper(
            name = "Rice and Chicken",
            nutrition = Nutrition(
                totalFat = 20.0, protein = 50.0, carbohydrates = 5.0, sugar = 3.0,
                calories = null, sodium = null, saturatedFat = null
            )
        ),
        createMealHelper(
            name = "Vegetable Stir Fry with Rice",
            nutrition = Nutrition(
                totalFat = 10.0, protein = 10.0, carbohydrates = 60.0, sugar = 12.0,
                calories = null, sodium = null, saturatedFat = null
            )
        ),
        createMealHelper(
            name = "Banana Smoothie",
            nutrition = Nutrition(
                totalFat = 5.0, protein = 8.0, carbohydrates = 45.0, sugar = 30.0,
                calories = null, sodium = null, saturatedFat = null
            )
        ),
        createMealHelper(
            name = "Bread with Jam",
            nutrition = Nutrition(
                totalFat = 3.0, protein = 5.0, carbohydrates = 50.0, sugar = 25.0,
                calories = null, sodium = null, saturatedFat = null
            )
        )
    )

    fun edgeCases() = listOf(
        createMealHelper(
            name = "Meal with Null Carbs",
            nutrition = Nutrition(
                totalFat = 20.0, protein = 20.0, carbohydrates = null, sugar = 4.0,
                calories = null, sodium = null, saturatedFat = null
            )
        ),
        createMealHelper(
            name = "Null Nutrition Meal",
            nutrition = null
        ),
        createMealHelper(
            name = "Meal with Null Sugar",
            nutrition = Nutrition(
                totalFat = 20.0, protein = 20.0, carbohydrates = 5.0, sugar = null,
                calories = null, sodium = null, saturatedFat = null
            )
        ),
        createMealHelper(
            name = "Meal with Null Fat",
            nutrition = Nutrition(
                totalFat = null, protein = 20.0, carbohydrates = 5.0, sugar = 4.0,
                calories = null, sodium = null, saturatedFat = null
            )
        ),
        createMealHelper(
            name = "Meal with Null Protein",
            nutrition = Nutrition(
                totalFat = 20.0, protein = null, carbohydrates = 5.0, sugar = 4.0,
                calories = null, sodium = null, saturatedFat = null
            )
        ),
        createMealHelper(
            name = "High Carb Meal with Null Protein",
            nutrition = Nutrition(
                totalFat = 20.0, protein = null, carbohydrates = 60.0, sugar = 4.0,
                calories = null, sodium = null, saturatedFat = null
            )
        ),
        createMealHelper(
            name = "Empty Nutrition Meal",
            nutrition = Nutrition(null, null, null, null, null, null, null)
        )
    )

    fun baseKetoMeal() = createMealHelper(
        name = "Base Keto Meal",
        nutrition = Nutrition(
            totalFat = 20.0, protein = 20.0, carbohydrates = 5.0, sugar = 4.0,
            calories = null, sodium = null, saturatedFat = null
        )
    )

    fun ketoMealsWithNullFat() = listOf(
        createMealHelper(
            name = "Keto Meal with Null Fat 1",
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
            name = "Keto Meal with Null Fat 2",
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

    fun nullNutritionMeal() = createMealHelper(
        name = "Null Nutrition Meal",
        nutrition = null
    )

    fun mealsWithPartialNutrition() = listOf(
        createMealHelper(
            name = "Only Fat and Protein",
            nutrition = Nutrition(
                totalFat = 25.0,
                protein = 20.0,
                carbohydrates = null,
                sugar = null,
                calories = null,
                sodium = null,
                saturatedFat = null
            )
        ),
        createMealHelper(
            name = "Only Carbs",
            nutrition = Nutrition(
                totalFat = null,
                protein = null,
                carbohydrates = 10.0,
                sugar = null,
                calories = null,
                sodium = null,
                saturatedFat = null
            )
        )
    )
}