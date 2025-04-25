package logic.usecase

import logic.MealsProvider
import model.Meal
import org.example.presentation.Viewer
import org.example.presentation.ViewerImpl
import org.example.utils.EmptyMealsException

class PotatoLovingMealsUseCase(
    private val mealsProvider: MealsProvider,
) {

    private val viewer: Viewer = ViewerImpl()
    fun execute(): List<Meal> {
        return try {
            mealsProvider.getMeals().filter { meal ->
                meal.ingredients?.any { ingredient ->
                    ingredient.contains("potato", ignoreCase = true)
                } == true
            }
        } catch (e: EmptyMealsException) {
            viewer.log("Error : ${e.message}")
            emptyList()
        }
    }

}
