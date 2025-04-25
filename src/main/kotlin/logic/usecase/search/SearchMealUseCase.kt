package logic.usecase

import logic.MealsProvider
import model.Meal
import org.example.logic.SearchAlgorithm
import org.example.utils.EmptyMealNameException
import org.example.utils.EmptyMealsException
import org.example.utils.EmptyTextException
import org.example.utils.NoMealFoundException

class SearchMealUseCase(
    private val mealsProvider: MealsProvider,
    private val searchAlgorithm: SearchAlgorithm
) {

    fun execute(name: String): List<Meal> {
        if (mealsProvider.getMeals().isEmpty()) throw EmptyMealsException("No meals found")
        if (name.isBlank() or name.isEmpty()) throw EmptyMealNameException("Meal name should not be empty")

        return mealsProvider.getMeals().filter { meal ->
            if (meal.name.isNullOrEmpty()) throw EmptyTextException("empty meal name in database")
            if (meal.name.isBlank()) throw EmptyTextException("empty meal name in database")

            searchAlgorithm.search(meal.name.lowercase(), name.lowercase())
        }
            .takeIf { it.isNotEmpty() }
            ?: throw NoMealFoundException("No meals found matching '$name'.")
    }
}

