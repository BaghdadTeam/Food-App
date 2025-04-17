package org.example.di

import logic.feature.Feature
import logic.feature.GuessGameFeature
import logic.feature.HighCalorieMealsFeature
import logic.feature.IngredientGameFeature
import logic.feature.IraqiMealsFeature
import logic.feature.KetoFriendlyMealFeature
import logic.feature.MealSearchFeature
import logic.feature.QuickHealthyMealsFeature
import logic.feature.SeafoodMealsFeature
import logic.feature.SweetsWithNoEggsFeature
import org.example.presentation.FoodChangeMoodConsoleUI
import org.koin.dsl.module

val FeatureModule = module {
    // Injecting use cases to features
    factory<Feature> { QuickHealthyMealsFeature(useCase = get()) }
    factory<Feature> { IraqiMealsFeature(useCase = get()) }
    factory<Feature> { GuessGameFeature(useCase = get()) }
    factory<Feature> { SweetsWithNoEggsFeature(useCase = get()) }
    factory<Feature> { KetoFriendlyMealFeature(useCase = get()) }
    factory<Feature> { MealSearchFeature(useCase = get()) }
//    factory<Feature> { SearchFoodByDateFeature(useCase = get()) }
//    factory<Feature> { GymHelperFeature(useCase = get()) }
//    factory<Feature> { ExploreOtherCountriesFeature(useCase = get()) }
    factory<Feature> { IngredientGameFeature(useCase = get()) }
//    factory<Feature> { PotatoLovingMealsFeature(useCase = get()) }
    factory<Feature> { HighCalorieMealsFeature(useCase = get()) }
    factory<Feature> { SeafoodMealsFeature(useCase = get()) }
//    factory<Feature> { ItalianForLargeGroupsFeature(useCase = get()) }


    // Create a map of features using getAll()
    factory<Map<Int, Feature>> {
        getAll<Feature>().associateBy { it.number }
    }

    // UI Singleton
    single { FoodChangeMoodConsoleUI(get()) }
}