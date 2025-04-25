package logic.usecase

import model.Meal
import logic.MealsProvider
import org.example.utils.EmptyMealsException
import org.example.utils.NoMealFoundException

class SuggestHighCalorieMealUseCase(private val mealsProvider: MealsProvider) {
    private val suggestedMeals = mutableSetOf<Meal>()

    fun execute(): Meal {
        if (mealsProvider.getMeals().isEmpty()) throw EmptyMealsException("No meals found")
        return mealsProvider.getMeals()
            .filter(::isHighCalorieMeal)
            .takeIf { it.isNotEmpty() }
            ?.random()
            ?.also { suggestedMeals.add(it) }
            ?: throw NoMealFoundException("There is no High Calories meals")
    }

    private fun isHighCalorieMeal(meal: Meal): Boolean =
        meal.nutrition?.calories!! > 700 && !suggestedMeals.contains(meal)
}