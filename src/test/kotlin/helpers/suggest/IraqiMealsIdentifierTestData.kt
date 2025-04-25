package helpers.suggest

import helpers.createMealHelper

object IraqiMealsIdentifierTestData {

    fun allMeals() = listOf(
        allIraqiMeals(),
        allNonIraqiMeals()
    ).flatten()

    fun allIraqiMeals() = listOf(
        mealWithIraqiTag(),
        mealWithIraqInDescription(),
        mealWithIraqiTagAndIraqInDescription(),
        iraqiMealWithNullDescription(),
        iraqiMealWithNullTags(),
        iraqiMealWithEmptyTags(),
        iraqiMealWithEmptyDescription(),
        iraqiMealWithInconsistentSpaceAndCase(),
        iraqiMealWithIraqiDescription()
    )

    fun allNonIraqiMeals() = listOf(
        nonIraqiMeal(),
        nullMeal(),
        nonIraqiMealWithNoTag(),
        emptyMeal()
    )

    fun iraqiMealWithIraqiDescription() = createMealHelper(
        name = "Confusing Description",
        tags = listOf("famous"),
        description = "This is an iraqi-style dish"
    )

    fun nonIraqiMealWithNoTag() = createMealHelper(
        name = "Empty Tags No Iraq",
        tags = emptyList(),
        description = "Just a simple meal"
    )

    fun emptyMeal() = createMealHelper(
        name = "empty meal",
        tags = emptyList(),
        description = ""
    )


    fun iraqiMealWithInconsistentSpaceAndCase() = createMealHelper(
        name = "inconsistent Meal",
        tags = listOf("iraqi"),
        description = "A traditional IraQ dish made with grape leaves stuffed with rice and meat."
    )

    fun mealWithIraqiTag() = createMealHelper(
        name = "Dolma",
        tags = listOf("iraqi"),
        description = "A traditional Iraq dish made with grape leaves stuffed with rice and meat."
    )

    fun mealWithIraqInDescription() = createMealHelper(
        name = "Baklava",
        tags = listOf("time-to-make", "sweet", "easy"),
        description = "Popular dessert in many countries, including Iraq, made of layers of filo pastry and nuts."
    )

    fun mealWithIraqiTagAndIraqInDescription() = createMealHelper(
        name = "Kebab",
        tags = listOf("iraqi", "time-to-make"),
        description = "Grilled meat skewers that are a staple in Iraqi cuisine."
    )

    fun iraqiMealWithNullTags() = createMealHelper(
        name = "Quzi",
        tags = null,
        description = "Slow-cooked lamb with rice, a festive Iraq dish."
    )

    fun iraqiMealWithNullDescription() = createMealHelper(
        name = "Masgouf",
        tags = listOf("iraqi", "fish"),
        description = null
    )

    fun iraqiMealWithEmptyTags() = createMealHelper(
        name = "Tashreeb",
        tags = emptyList(),
        description = "A bread and broth dish common in Iraq homes."
    )

    fun iraqiMealWithEmptyDescription() = createMealHelper(
        name = "Samoon",
        tags = listOf("iraqi", "bread"),
        description = ""
    )

    fun nonIraqiMeal() = createMealHelper(
        name = "Spaghetti Carbonara",
        tags = listOf("italian", "pasta"),
        description = "Classic Italian pasta dish made with eggs, cheese, pancetta, and pepper."
    )

    fun nullMeal() = createMealHelper(
        name = "null meal",
        tags = null,
        description = null
    )
}