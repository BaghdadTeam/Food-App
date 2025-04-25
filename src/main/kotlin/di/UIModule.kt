package org.example.di

import org.example.presentation.*
import org.example.presentation.filter.SeafoodMealsUI
import org.example.presentation.game.IngredientGameUI
import org.example.presentation.suggest.IraqiMealsUI
import org.example.presentation.suggest.EggFreeSweetsUI
import org.example.presentation.suggest.HighCalorieMealUI
import org.example.presentation.suggest.KetoFriendlyMealUI
import org.koin.dsl.bind
import org.koin.dsl.module
import presentation.feature.GymHelperUI

val uiModule = module {

    factory { QuickHealthyMealsUI(get(), get(), get()) } bind Feature::class
    factory { IraqiMealsUI(get(), get()) } bind Feature::class
    factory { GuessGameUI(get(), get(), get()) } bind Feature::class

    factory { EggFreeSweetsUI(useCase = get(), get(), get()) } bind Feature::class
    factory { KetoFriendlyMealUI(useCase = get(), get(), get()) } bind Feature::class
    factory { MealSearchUI(useCase = get(), get(), get()) } bind Feature::class

    factory { SearchFoodByDateUI(useCase = get(), get(), get()) } bind Feature::class
    factory { ExploreOtherCountriesUI(useCase = get(), get(), get()) } bind Feature::class
    factory { IngredientGameUI(useCase = get(), get(), get()) } bind Feature::class

    factory { HighCalorieMealUI(useCase = get(), get(), get()) } bind Feature::class
    factory { SeafoodMealsUI(useCase = get(), get()) } bind Feature::class
    factory { GymHelperUI(useCase = get(), get(), get()) } bind Feature::class

    factory { SuggestEasyMealUI(useCase = get()) } bind Feature::class
    factory { ExploreOtherCountriesUI(useCase = get(), get(), get()) } bind Feature::class
    factory { IngredientGameUI(useCase = get(), get(), get()) } bind Feature::class

    factory { ItalianForLargeGroupsUI(useCase = get(), get()) } bind Feature::class
    factory { PotatoLovingMealsUI(useCase = get(), get()) } bind Feature::class
    factory { SearchFoodByDateUI(get(), get(), get()) } bind Feature::class

    factory<Map<Int, Feature>> {
        getAll<Feature>().associateBy { it.id }
    }

    single { FoodChangeMoodConsoleUI(get(), get(), get()) }
}