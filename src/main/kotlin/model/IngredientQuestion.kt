package org.example.model

data class IngredientQuestion(
    val mealName: String,
    val correctIngredient: String,
    val options: List<String>
)