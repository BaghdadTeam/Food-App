package logic.usecase

import model.Meal
import logic.MealsProvider

class SuggestHighCalorieMealUseCase(private val mealsProvider: MealsProvider) {
    private val suggestedMeals = mutableSetOf<Meal>()

    fun execute(): Meal {

        return mealsProvider.getMeals()
            .filter(::isHighCalorieMeal)
            .takeIf { it.isNotEmpty() }
            ?.random()
            ?.also { suggestedMeals.add(it) }
            ?: throw NoSuchElementException("There is no High Calories meals")
    }

    private fun isHighCalorieMeal(meal: Meal): Boolean =
        meal.nutrition?.calories!! > 700 && !suggestedMeals.contains(meal)
}