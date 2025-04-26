package org.example.presentation

import logic.usecase.ExploreOtherCountriesFoodCultureUseCase
import org.example.utils.EmptyMealsException
import org.example.utils.NoMealFoundException

class ExploreOtherCountriesUI(
    private val useCase: ExploreOtherCountriesFoodCultureUseCase,
    private val viewer: Viewer,
    private val reader: Reader
    ) : Feature {
    override val id: Int = FEATURE_ID
    override val name: String = FEATURE_NAME

    override fun execute() {
        viewer.log("Enter country name to discover it's culture ")
        val countryName = reader.readInput()?.trim().orEmpty()
        try {
            val meals = useCase.execute(countryName)
            viewer.log(
                 "Matching Meals:\n${
                    meals.joinToString("\n") { "- ${it.name}" }
                }"
            )
        } catch (_: EmptyMealsException) {
            viewer.log("No meals in the database.")
        } catch (e: NoMealFoundException) {
            viewer.log(
                "No meals found matching '$countryName'."
            )
        } catch (e: Exception) {
            viewer.log("There is something happened when retrieving data")
        }
    }

    companion object {
        const val FEATURE_ID = 10
        const val FEATURE_NAME = "Explore other countries' food culture"
    }
}
