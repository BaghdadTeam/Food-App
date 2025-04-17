package logic.use_case

import model.Meal
import org.example.data.MealsProvider

class SuggestHighCalorieMealUseCase(private val mealsProvider: MealsProvider) {
    private val suggestedMeals = mutableSetOf<Meal>()

    fun suggestMeal(): Meal {
        try {
            return mealsProvider.meals.filter(::isHighCalorieMeal)
                .random()
                .also { suggestedMeals.add(it) }
        } catch (exception: NoSuchElementException) {
            throw exception
        }
    }

    private fun isHighCalorieMeal(meal: Meal): Boolean =
        meal.nutrition?.calories!! > 700 && !suggestedMeals.contains(meal)
}
