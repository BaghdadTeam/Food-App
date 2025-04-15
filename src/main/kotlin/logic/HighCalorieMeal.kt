import model.Meal
import org.example.data.MealsProvider

class SuggestHighCalorieMeal(private val mealsProvider: MealsProvider) {
    private val allMeals = mealsProvider.meals
    private val suggestedMeals = mutableSetOf<Meal>()

    fun suggestMeal():Meal?{
        try {

        return allMeals.filter { it.nutrition?.calories!! > 700 && !suggestedMeals.contains(it) }
            .random().also{suggestedMeals.add(it)}

        }catch (e:NoSuchElementException){
            print("No more meals to suggest")
            return null
        }
    }
}
