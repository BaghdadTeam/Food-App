package helpers.suggest

import helpers.createMealHelper

object EggFreeSweetsTestData {

    fun eggFreeCoconutLadoo() = createMealHelper(
        name = "Coconut Ladoo",
        tags = listOf("  sweet   "),
        ingredients = listOf("coconut", "milk  ", "  sugar")
    )

    fun allNonEggFreeSweets() = listOf(
        nonSweetMeal(),
        sweetWithEggs()
    )

    fun allEggFreeSweets() = listOf(
        eggFreeBananaOatCookies(),
        eggFreeCoconutLadoo(),
        eggFreeChocolateAvocadoMousse()
    )

    fun allTestMeals() = listOf(
        eggFreeChocolateAvocadoMousse(),
        eggFreeCoconutLadoo(),
        eggFreeBananaOatCookies(),
        sweetWithEggs(),
        nonSweetMeal()
    )

    fun nullableMeals() = listOf(
        nullIngredientMeal(),
        nullTagMeal()
    )

    private fun nullIngredientMeal() = createMealHelper(
        name = "Mystery Meal",
        tags = listOf("sweet"),
        ingredients = null
    )

    private fun nullTagMeal() = createMealHelper(
        name = "Another Mystery",
        tags = null,
        ingredients = listOf("sugar")
    )

    private fun eggFreeChocolateAvocadoMousse() = createMealHelper(
        name = "Chocolate Avocado Mousse",
        tags = listOf("sweet", "dessert", "vegan"),
        ingredients = listOf("avocado", "cocoa powder", "maple syrup", "vanilla extract")
    )

    private fun eggFreeBananaOatCookies() = createMealHelper(
        name = "Banana Oat Cookies",
        tags = listOf("sweet", "snack"),
        ingredients = listOf("banana", "oats", "cinnamon", "raisins")
    )

    private fun sweetWithEggs() = createMealHelper(
        name = "Egg Soufflé",
        tags = listOf("sweet"),
        ingredients = listOf("egg", "milk", "sugar")
    )

    private fun nonSweetMeal() = createMealHelper(
        name = "Spicy Lentils",
        tags = listOf("spicy", "savory"),
        ingredients = listOf("lentils", "chili", "garlic")
    )
}