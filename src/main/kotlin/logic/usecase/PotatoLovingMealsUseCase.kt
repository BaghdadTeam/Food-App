package logic.usecase

import logic.MealsProvider
import model.Meal
import org.example.presentation.Viewer
import org.example.presentation.ViewerImpl

class PotatoLovingMealsUseCase(
    private val mealsProvider: MealsProvider,
) {

    private val viewer:Viewer = ViewerImpl()
    fun execute(): List<Meal> {
        return try {
            mealsProvider.getMeals().filter { meal ->
                meal.ingredients?.any { ingredient ->
                    ingredient.contains("potato", ignoreCase = true)
                } == true
            }
        } catch (e: Exception) {
            viewer.log("There is no matching or potato's in here")
            emptyList()
        }
    }

}
