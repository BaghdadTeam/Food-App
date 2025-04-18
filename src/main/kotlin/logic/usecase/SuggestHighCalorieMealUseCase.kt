package logic.usecase

import model.Meal
import logic.MealsProvider
import org.example.utils.EmptyMeals
import org.example.utils.NoElementMatch

class SuggestHighCalorieMealUseCase(private val mealsProvider: MealsProvider) {
    private val suggestedMeals = mutableSetOf<Meal>()

    fun execute(): Meal {
        if (mealsProvider.getMeals().isEmpty()) throw EmptyMeals("No meals found")
        return mealsProvider.getMeals()
            .filter(::isHighCalorieMeal)
            .takeIf { it.isNotEmpty() }
            ?.random()
            ?.also { suggestedMeals.add(it) }
            ?: throw NoElementMatch("There is no High Calories meals")
    }

    private fun isHighCalorieMeal(meal: Meal): Boolean =
        meal.nutrition?.calories!! > 700 && !suggestedMeals.contains(meal)
}