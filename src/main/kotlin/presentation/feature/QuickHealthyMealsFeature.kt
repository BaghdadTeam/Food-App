package presentation.feature
import logic.use_case.FilterQuickHealthyMealsUseCase

class QuickHealthyMealsFeature(private val useCase: FilterQuickHealthyMealsUseCase) : Feature {
    override val number: Int = 1
    override val name: String = "Get healthy fast food meals"

    override fun execute() {

        println("Please enter the number of meals you want to see:")
        val countInput = readln().toIntOrNull()

        if (countInput == null) {
            println("Please enter a valid number.")
        }
        else {
            try {
                val meals = useCase.getQuickHealthyMeals(countInput)
                if (meals.isEmpty()) {
                    println("No quick healthy meals found ")
                } else {
                    println("Quick & Healthy Meals:")
                    meals.forEach { println("- ${it.name}") }
                }
            } catch (e: NoSuchElementException) {
                println("There are no quick healthy meals available ")
            }
        }}
}
