package model

data class Meal(
    val name: String = "",
    val id: Int = 0,
    val timeToCock: Int = 0,
    val contributorId: Int = 0,
    val date: String = "",
    val tags: List<String> = emptyList(),
    val nutrition: Nutrition = Nutrition(),
    val nSteps: Int = 0,
    val steps: List<String> = emptyList(),
    val description: String = "",
    val ingredients: List<String> = emptyList(),
    val nIngredients: Int = 0
)

