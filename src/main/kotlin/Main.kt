package org.example

import org.example.di.appModule
import org.example.di.useCaseModule
import org.example.logic.FilterQuickHealthyMealsUseCase
import org.koin.core.context.startKoin
import org.koin.core.context.GlobalContext.get as getKoin

fun main() {
    startKoin {
        modules(appModule, useCaseModule)
    }

    val quickHealthyMealsUseCase: FilterQuickHealthyMealsUseCase = getKoin().get()
    val quickHealthyMeals = quickHealthyMealsUseCase.getQuickHealthyMeals(5)
    quickHealthyMeals.forEach { meal ->
        println(
            "Meal Name : ${meal.name} | Time to cook :${meal.timeToCock} | Total Fat :${meal.nutrition?.totalFat} " +
                    "| Saturated Fat :  ${meal.nutrition?.saturatedFat} | Carbohydrates : ${meal.nutrition?.carbohydrates}"
        )

    }


}