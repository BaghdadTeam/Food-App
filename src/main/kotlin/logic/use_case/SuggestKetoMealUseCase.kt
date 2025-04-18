package logic.use_case

import model.Meal
import logic.MealsProvider

class SuggestKetoMealUseCase(private val mealsProvider: MealsProvider) {

    private val alreadySuggestKetoMeals = mutableSetOf<Meal>()

    fun execute(): Meal {
        return mealsProvider.getMeals()
            .filter(::isKetoMealAndNotSuggested)
            .takeIf { it.isNotEmpty() }
            ?.random()
            ?.also { alreadySuggestKetoMeals.add(it) }
            ?: throw NoSuchElementException("There is no more unique keto Meals")
    }

    private fun isKetoMealAndNotSuggested(meal: Meal): Boolean {
        val nutrition = meal.nutrition
        return nutrition?.totalFat != null && nutrition.totalFat >= MIN_FAT &&
                nutrition.carbohydrates != null && nutrition.carbohydrates < MAX_CARBS &&
                nutrition.sugar != null && nutrition.sugar < MAX_SUGAR &&
                nutrition.protein != null && nutrition.protein in PROTEIN_RANGE&&
                meal !in alreadySuggestKetoMeals

    }

    companion object {
        const val MIN_FAT = 15
        const val MAX_CARBS = 10
        const val MAX_SUGAR = 5
        val PROTEIN_RANGE = 10.0..30.0}
}