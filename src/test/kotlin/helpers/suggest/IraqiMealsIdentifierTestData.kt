package helpers.suggest

import helpers.createMealHelper

object IraqiMealsIdentifierTestData {

    fun allIraqiMeals() = listOf(
        mealWithIraqiTag(),
        mealWithIraqInDescription(),
        mealWithIraqiTagAndIraqInDescription(),
        iraqiMealWithNullDescription(),
        iraqiMealWithNullTags(),
        iraqiMealWithEmptyTags(),
        iraqiMealWithEmptyDescription(),
    )

    fun allMeals() = listOf(
        mealWithIraqiTag(),
        mealWithIraqInDescription(),
        mealWithIraqiTagAndIraqInDescription(),
        iraqiMealWithNullTags(),
        iraqiMealWithNullDescription(),
        iraqiMealWithEmptyTags(),
        iraqiMealWithEmptyDescription(),
        nonIraqiMeal(),
        nullMeal()
    )

    fun mealWithIraqiTag() = createMealHelper(
        name = "Dolma",
        tags = listOf("iraqi"),
        description = "A traditional Iraqi dish made with grape leaves stuffed with rice and meat."
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

    fun iraqiMealWithNullDescription() =   createMealHelper(
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