package org.example.di

import org.example.presentation.FoodChangeMoodConsoleUI
import org.koin.dsl.bind
import org.koin.dsl.module
import presentation.feature.Feature
import presentation.feature.GuessGameFeature
import presentation.feature.HighCalorieMealsFeature
import presentation.feature.IngredientGameFeature
import presentation.feature.IraqiMealsFeature
import presentation.feature.KetoFriendlyMealFeature
import presentation.feature.MealSearchFeature
import presentation.feature.PotatoLovingMealsFeature
import presentation.feature.QuickHealthyMealsFeature
import presentation.feature.SeafoodMealsFeature
import presentation.feature.SweetsWithNoEggsFeature

val FeatureModule = module {

    factory { QuickHealthyMealsFeature(get()) } bind Feature::class
    factory { IraqiMealsFeature(get()) } bind Feature::class
    factory { GuessGameFeature(get()) } bind Feature::class
    factory { SweetsWithNoEggsFeature(useCase = get()) } bind Feature::class
    factory { KetoFriendlyMealFeature(useCase = get()) } bind Feature::class
    factory { MealSearchFeature(useCase = get()) } bind Feature::class
//    factory { SearchFoodByDateFeature(useCase = get()) }bind Feature::class
//    factory { GymHelperFeature(useCase = get()) }bind Feature::class
//    factory { ExploreOtherCountriesFeature(useCase = get()) }bind Feature::class
    factory { IngredientGameFeature(useCase = get()) } bind Feature::class
//    factory { PotatoLovingMealsFeature(useCase = get()) }bind Feature::class
    factory { HighCalorieMealsFeature(useCase = get()) } bind Feature::class
    factory { SeafoodMealsFeature(useCase = get()) } bind Feature::class
//    factory { ItalianForLargeGroupsFeature(useCase = get()) }bind Feature::class

    factory { PotatoLovingMealsFeature(useCase = get()) } bind Feature::class

    factory<Map<Int, Feature>> {
        getAll<Feature>().associateBy { it.number }
    }

    single { FoodChangeMoodConsoleUI(get()) }

}