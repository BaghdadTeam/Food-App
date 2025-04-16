package model

data class Meal(
    val name: String?,
    val id: Int?,
   // val timeToCock: Int?,
    val contributorId: Int?,
    val date: String?,
    val tags: List<String>?,
    val nutrition: Nutrition?,
    val nSteps: Int?,
    val steps: List<String?>,
    val description: String?,
    val ingredients: List<String>?,
    val nIngredients: Int?,
    val preparationTime: Int
) {

}