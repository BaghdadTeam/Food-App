package org.example.presentation

import org.example.logic.*

class FoodChangeMoodConsoleUI(
    private val quickHealthyMealsUseCase: FilterQuickHealthyMealsUseCase,
    private val mealSearchUseCase: SearchMealUseCase,
    private val iraqiMealsUseCase: IraqiMealsIdentifierUseCase,
//    private val easyFoodSuggestionUseCase: EasyFoodSuggestionUseCase,
    private val guessGameUseCase: GuessMealGameUseCase,
//    private val sweetsWithNoEggsUseCase: SweetsWithNoEggsUseCase,
    private val ketoFriendlyMealUseCase: SuggestKetoMealUseCase,
//    private val searchFoodsByAddDateUseCase: SearchFoodsByAddDateUseCase,
//    private val gymHelperUseCase: GymHelperUseCase,
//    private val exploreOtherCountriesUseCase: ExploreOtherCountriesUseCase,
    private val ingredientGameUseCase: IngredientGameUseCase,
//    private val potatoLovingMealsUseCase: PotatoLovingMealsUseCase,
//    private val highCalorieMealsUseCase: HighCalorieMealsUseCase,
//    private val seafoodMealsUseCase: SeafoodMealsUseCase,
//    private val italianForLargeGroupsUseCase: ItalianForLargeGroupsUseCase
) {
    fun start() {
        showWelcome()
        presentFeatures()
    }

    private fun showWelcome() {
        println("Welcome to Food Change Mood!")
    }

    fun presentFeatures() {
        while (true) {
            showOptions()
            when (getUserInput()) {
                1 -> launchQuickHealthyMeals()
                2 -> launchMealSearch()
                3 -> launchIraqiMeals()
                4 -> launchEasyFoodSuggestion()
                5 -> launchGuessGame()
                6 -> launchSweetsWithNoEggs()
                7 -> launchKetoFriendlyMeal()
                8 -> launchSearchFoodsByAddDate()
                9 -> launchGymHelper()
                10 -> launchExploreOtherCountries()
                11 -> launchIngredientGame()
                12 -> launchPotatoLovingMeals()
                13 -> launchHighCalorieMeals()
                14 -> launchSeafoodMealsByProtein()
                15 -> launchItalianForLargeGroups()
                0 -> {
                    println("Exiting... Goodbye!")
                    break
                }

                else -> println("Invalid input, please try again.")
            }
        }
    }

    private fun showOptions() {
        println("\n=== Please enter one of the following numbers ===")
        println("1. Get healthy fast food meals")
        println("2. Search meal by name")
        println("3. Identify Iraqi meals")
        println("4. Easy food suggestion game")
        println("5. Guess game")
        println("6. Sweets with no eggs")
        println("7. Keto diet meal helper")
        println("8. Search foods by add date")
        println("9. Gym helper")
        println("10. Explore other countries' food culture")
        println("11. Ingredient game")
        println("12. I love potato list")
        println("13. So thin problem")
        println("14. Show seafood meals sorted by protein")
        println("15. Italian food suggestions for large groups")
        println("0. Exit\n")
        print("Enter your choice: ")
    }

    private fun getUserInput(): Int? = readlnOrNull()?.toIntOrNull()

    private fun launchQuickHealthyMeals() {
        val meals = quickHealthyMealsUseCase.getQuickHealthyMeals(10)
        println(
            if (meals.isEmpty()) "No quick healthy meals found." else "Quick & Healthy Meals:\n${
                meals.joinToString(
                    "\n"
                ) { "- ${it.name}" }
            }"
        )
    }

    private fun launchMealSearch() {
        print("Enter meal name (partial or full): ")
        val keyword = readlnOrNull()?.trim().orEmpty()
        val meals = mealSearchUseCase.search(keyword)
        println(
            if (meals.isEmpty()) "No meals found matching '$keyword'." else "Matching Meals:\n${
                meals.joinToString(
                    "\n"
                ) { "- ${it.name}" }
            }"
        )
    }

    private fun launchIraqiMeals() {
        val meals = iraqiMealsUseCase.getIraqiMeals()
        println(
            if (meals.isEmpty()) "No Iraqi meals found." else "Iraqi Meals:\n${
                meals.joinToString(
                    "\n"
                ) { "- ${it.name}" }
            }"
        )
    }

    private fun launchEasyFoodSuggestion() {
        //ToDo
    }

    private fun launchGuessGame() = guessGameUseCase.guessMealPreparationTime()

    private fun launchSweetsWithNoEggs() {
        //ToDo
    }

    private fun launchKetoFriendlyMeal() {
        println("Suggested Keto Meal: ${ketoFriendlyMealUseCase.getKetoMealSuggest()}")
    }

    private fun launchSearchFoodsByAddDate() {
        //ToDo
    }

    private fun launchGymHelper() {
        //ToDo

    }

    private fun launchExploreOtherCountries() {
        //ToDo

    }

    private fun launchIngredientGame() = ingredientGameUseCase.playGame()

    private fun launchPotatoLovingMeals() {
        //ToDo

    }

    private fun launchHighCalorieMeals() {
        //ToDo

    }

    private fun launchSeafoodMealsByProtein() {
        //ToDo

    }

    private fun launchItalianForLargeGroups() {
        //ToDo

    }
}
