package helpers.suggest

import helpers.createMealHelper
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import model.Nutrition

object PotatoLovingMealsTestData {
    private val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

    fun getOnlyMealContainsPotato() = listOf(
        createMealHelper(
            name = "Mashed Potatoes",
            id = 1,
            contributorId = 101,
            date = now,
            tags = listOf("comfort food"),
            nutrition = Nutrition(
                calories = 200.0,
                totalFat = 10.0,
                sugar = 2.0,
                sodium = 300.0,
                protein = 4.0,
                saturatedFat = 3.0,
                carbohydrates = 25.0
            ),
            nSteps = 3,
            steps = listOf("Boil", "Mash", "Serve"),
            description = "Creamy mashed potatoes",
            ingredients = listOf("potato", "butter", "salt"),
            nIngredients = 3,
            preparationTime = 20
        ),
        createMealHelper(
            name = "Fries",
            id = 2,
            contributorId = 102,
            date = now,
            tags = listOf("fast food"),
            nutrition = Nutrition(
                calories = 300.0,
                totalFat = 15.0,
                sugar = 0.0,
                sodium = 400.0,
                protein = 3.0,
                saturatedFat = 2.0,
                carbohydrates = 30.0
            ),
            nSteps = 2,
            steps = listOf("Cut", "Fry"),
            description = "Crispy french fries",
            ingredients = listOf("potato", "oil", "salt"),
            nIngredients = 3,
            preparationTime = 15
        ),
        createMealHelper(
            name = "Salad",
            id = 3,
            contributorId = 103,
            date = now,
            tags = listOf("healthy"),
            nutrition = Nutrition(
                calories = 100.0,
                totalFat = 2.0,
                sugar = 3.0,
                sodium = 100.0,
                protein = 1.0,
                saturatedFat = 0.5,
                carbohydrates = 12.0
            ),
            nSteps = 2,
            steps = listOf("Chop", "Toss"),
            description = "Green salad",
            ingredients = listOf("lettuce", "tomato", "cucumber"),
            nIngredients = 3,
            preparationTime = 10
        )
    )

    fun getEmptyMealsWithNoPotato() = listOf(
        createMealHelper(
            name = "Avocado Toast",
            id = 4,
            contributorId = 104,
            date = now,
            tags = listOf("breakfast"),
            nutrition = Nutrition(
                calories = 250.0,
                totalFat = 12.0,
                sugar = 1.0,
                sodium = 200.0,
                protein = 5.0,
                saturatedFat = 1.5,
                carbohydrates = 20.0
            ),
            nSteps = 2,
            steps = listOf("Toast", "Spread"),
            description = "Healthy avocado toast",
            ingredients = listOf("bread", "avocado", "lemon"),
            nIngredients = 3,
            preparationTime = 5
        )
    )

    fun getIngredient() = listOf(
        createMealHelper(
            name = "Fries",
            id = 2,
            contributorId = 102,
            date = now,
            tags = listOf("fast food"),
            nutrition = Nutrition(
                calories = 300.0,
                totalFat = 15.0,
                sugar = 0.0,
                sodium = 400.0,
                protein = 3.0,
                saturatedFat = 2.0,
                carbohydrates = 30.0
            ),
            nSteps = 2,
            steps = listOf("Cut", "Fry"),
            description = "Crispy french fries",
            ingredients = null, // test case with null ingredients
            nIngredients = 3,
            preparationTime = 15
        )
    )

    fun getSampleMeals(count: Int = 12) = List(count) {
        createMealHelper(
            id = it,
            name = "Potato Dish $it",
            description = "A tasty potato meal number $it",
            nutrition = Nutrition(
                calories = 100.0 + it,
                totalFat = 5.0,
                sugar = 2.0,
                sodium = 200.0,
                protein = 3.0,
                saturatedFat = 1.0,
                carbohydrates = 15.0
            ),
            ingredients = listOf("potato", "salt", "oil"),
            contributorId = it,
            date = now,
            tags = listOf("vegetarian", "quick"),
            nSteps = 3,
            steps = listOf("Peel", "Fry", "Serve"),
            nIngredients = 3,
            preparationTime = 15
        )
    }

    fun getSmallPotatoMeals() = List(3) {
        createMealHelper(
            id = it,
            name = "Small Potato Dish $it",
            description = "Delicious description $it",
            nutrition = Nutrition(
                calories = 90.0 + it,
                totalFat = 4.0,
                sugar = 1.5,
                sodium = 180.0,
                protein = 2.0,
                saturatedFat = 0.5,
                carbohydrates = 13.0
            ),
            ingredients = listOf("potato", "oil"),
            contributorId = it,
            date = now,
            tags = listOf("easy"),
            nSteps = 2,
            steps = listOf("Chop", "Boil"),
            nIngredients = 2,
            preparationTime = 10
        )
    }

    fun getNullDataMeal() = createMealHelper(
        id = 1,
        name = "Mystery Potato",
        description = null,
        nutrition = null,
        ingredients = null,
        contributorId = 0,
        date = now,
        tags = emptyList(),
        nSteps = 0,
        steps = emptyList(),
        nIngredients = 0,
        preparationTime = 0
    )

}