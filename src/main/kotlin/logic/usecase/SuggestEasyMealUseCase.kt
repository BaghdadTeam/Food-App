package logic.usecase

import logic.MealsProvider
import model.Meal
import org.example.utils.NoMealFoundException
import java.util.Collections

class SuggestEasyMealUseCase(
    private val mealsProvider: MealsProvider,
    private val maxPreparationTime: Int = 30,
    private val maxIngredients: Int = 5,
    private val maxSteps: Int = 6,
    private val maxMealsToSuggest: Int = 10,
) {
    private fun isEasyMeal(meal: Meal): Boolean {
        return meal.preparationTime <= maxPreparationTime &&
                (meal.ingredients?.size ?: 0) <= maxIngredients &&
                meal.steps.size <= maxSteps
    }

    fun execute(): List<Meal> {
        val easyMeals = mealsProvider.getMeals()
            .filter(::isEasyMeal)
            .let { getRandomElements(it, maxMealsToSuggest) }

        if (easyMeals.isEmpty()) {
            throw NoMealFoundException("There are no easy meals available")
        }

        return easyMeals
    }

    private fun <T> getRandomElements(originalList: List<T>, count: Int): List<T> {
        validateInput(count)

        return when {
            originalList.isEmpty() -> emptyList()
            originalList.size <= count -> originalList
            else -> selectRandomElements(originalList, count)
        }
    }

    private fun validateInput(count: Int) {
        if (count < 0) throw IllegalArgumentException("Count must be non-negative")
    }

    private fun <T> selectRandomElements(originalList: List<T>, count: Int): List<T> {
        val result = ArrayList<T>(count)
        val mutableList = originalList.toMutableList()

        repeat(count) {
            swapRandomElementToCurrentPosition(mutableList, it)
            result.add(mutableList[it])
        }

        return result
    }

    private fun <T> swapRandomElementToCurrentPosition(list: MutableList<T>, currentPosition: Int) {
        val randomIndex = (currentPosition until list.size).random()
        Collections.swap(list, currentPosition, randomIndex)
    }
}