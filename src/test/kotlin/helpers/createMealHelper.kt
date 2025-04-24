package helpers

import kotlinx.datetime.LocalDate
import model.Meal
import model.Nutrition

fun createMealHelper(
    name: String? = null, id: Int? = null,
    contributorId: Int? = null, date: LocalDate? = null,
    tags: List<String>? = null, nutrition: Nutrition? = null,
    nSteps: Int? = null, steps: List<String> = listOf(),
    description: String? = null, ingredients: List<String>? = null,
    nIngredients: Int? = null, preparationTime: Int = 0
): Meal = Meal(
    nutrition = nutrition,
    nSteps = nSteps,
    steps = steps,
    description = description,
    ingredients = ingredients,
    nIngredients = nIngredients,
    preparationTime = preparationTime,
    name = name,
    id = id,
    contributorId = contributorId,
    date = date,
    tags = tags,
)