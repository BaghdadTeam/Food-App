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
    factory<Feature> { IraqiMealsFeature(get()) }
    factory<Feature> { GuessGameFeature(get()) }
    factory<Feature> { SweetsWithNoEggsFeature(get()) }
    factory<Feature> { KetoFriendlyMealFeature(get()) }
    factory<Feature> { MealSearchFeature(get()) }
//    factory<Feature> { SearchFoodByDateFeature(get()) }
//    factory<Feature> { GymHelperFeature(get()) }
//    factory<Feature> { ExploreOtherCountriesFeature(get()) }
    factory<Feature> { IngredientGameFeature(get()) }
//    factory<Feature> { PotatoLovingMealsFeature(get()) }
    factory<Feature> { HighCalorieMealsFeature(get()) }
    factory<Feature> { SeafoodMealsFeature(get()) }
//    factory<Feature> { ItalianForLargeGroupsFeature(get()) }


    // Create a map of features using getAll()
    factory<Map<Int, Feature>> {
        getAll<Feature>().associateBy { it.number }
    }

    // UI Singleton
    single { FoodChangeMoodConsoleUI(get()) }
}

