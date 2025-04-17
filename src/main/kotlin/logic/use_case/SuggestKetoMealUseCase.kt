package logic.use_case

import model.Meal
import org.example.data.MealsProvider

class SuggestKetoMealUseCase (private val mealsProvider :MealsProvider) {
    private val alreadySuggestKetoMeals = mutableSetOf<Meal>()

    /**
     * Function to suggest a different Keto meal for the user
     * @return A unique Keto meal for every call
     */
    fun getKetoMealSuggest():Meal {
        val availableMeals =
            mealsProvider.meals.filter(::isKetoMeal).filterNot { ketoMeal -> ketoMeal in alreadySuggestKetoMeals }
           //Throw NoSuchElementException if all keto meals already suggested
           if (availableMeals.isEmpty()) throw NoSuchElementException("There is no more unique keto Meals")
           return availableMeals.random().also {ketoMeal-> alreadySuggestKetoMeals.add(ketoMeal) }
    }
    /**
     * A simple functions to check a given [meal] if was Keto meal or not
     * A keto meal should have equal or more than 15 gram total fat
     * ,less than 10 carbs, less than 5 grams sugar and between 10 and 30 gram protein
     * @return true if [meal] was a Keto meal and false on the opposite
     */
    private fun isKetoMeal(meal: Meal): Boolean {
        val nutrition = meal.nutrition
        return nutrition != null && nutrition.totalFat!! >= 15 &&
                nutrition.carbohydrates!! < 10 &&
                nutrition.sugar!! < 5 && nutrition.protein!! in 10.0..30.0
    }
    }
