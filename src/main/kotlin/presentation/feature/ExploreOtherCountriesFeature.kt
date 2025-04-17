package presentation.feature

import logic.use_case.ExploreOtherCountriesFoodCultureUseCase

class ExploreOtherCountriesFeature(
    private val useCase: ExploreOtherCountriesFoodCultureUseCase
    ) : Feature {
    override val number: Int = 20
    override val name: String = "Explore other countries' food culture"

    override fun execute() {
        print("Enter country name to discover it's culture ")
        val countryName = readlnOrNull()?.trim().orEmpty()
        val meals = useCase.execute(countryName)
        println(
            if (meals.isEmpty()) "No meals found matching '$countryName'."
            else "Matching Meals:\n${
                meals.joinToString("\n") { "- ${it.name}" }
            }"
        )
    }
}
