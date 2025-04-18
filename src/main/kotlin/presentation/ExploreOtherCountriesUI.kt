package org.example.presentation

import logic.usecase.ExploreOtherCountriesFoodCultureUseCase

class ExploreOtherCountriesUI(
    private val useCase: ExploreOtherCountriesFoodCultureUseCase
    ) : Feature {
    override val id: Int = FEATURE_ID
    override val name: String = FEATURE_NAME

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

    companion object {
        const val FEATURE_ID = 10
        const val FEATURE_NAME = "Explore other countries' food culture"
    }
}
