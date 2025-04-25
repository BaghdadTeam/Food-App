package logic.usecase.filter


import com.google.common.truth.Truth.assertThat
import helpers.suggest.PotatoLovingMealsTestData.getEmptyMealsWithNoPotato
import helpers.suggest.PotatoLovingMealsTestData.getIngredient
import helpers.suggest.PotatoLovingMealsTestData.getOnlyMealContainsPotato
import io.mockk.every
import io.mockk.mockk
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import logic.MealsProvider
import logic.usecase.PotatoLovingMealsUseCase
import org.example.utils.EmptyMealsException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.assertThrows // Make sure to import this

class PotatoLovingMealsUseCaseTest {

    private lateinit var mealsProvider: MealsProvider
    private lateinit var useCase: PotatoLovingMealsUseCase
    private val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

    @BeforeEach
    fun setup() {
        mealsProvider = mockk()
        useCase = PotatoLovingMealsUseCase(mealsProvider)
    }

    @Test
    fun `returns only meals containing potato`() {
        val meals = getOnlyMealContainsPotato()

        every { mealsProvider.getMeals() } returns meals

        val result = useCase.execute()

        assertThat(result).hasSize(2)
        assertThat(result.map { it.name }).containsExactly("Mashed Potatoes", "Fries")
    }

    @Test
    fun `throws exception when no meal contains potato`() {
        val meals = getEmptyMealsWithNoPotato()

        every { mealsProvider.getMeals() } returns meals

        val exception = assertThrows<EmptyMealsException> {
            useCase.execute()
        }

        assertThat(exception.message).isEqualTo("There is no meal containing potato.")
    }

    @Test
    fun `throws exception when meals list is empty`() {
        every { mealsProvider.getMeals() } returns emptyList()

        val exception = assertThrows<EmptyMealsException> {
            useCase.execute()
        }

        assertThat(exception.message).isEqualTo("There are no meals in the dataset.")
    }

    @Test
    fun `handles null ingredients gracefully and throws exception`() {
        val meals = getIngredient()

        every { mealsProvider.getMeals() } returns meals

        val exception = assertThrows<EmptyMealsException> {
            useCase.execute()
        }

        assertThat(exception.message).isEqualTo("There is no meal containing potato.")
    }
}

