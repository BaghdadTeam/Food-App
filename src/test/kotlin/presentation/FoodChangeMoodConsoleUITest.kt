package presentation

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.example.presentation.Feature
import org.example.presentation.FoodChangeMoodConsoleUI
import org.example.presentation.Reader
import org.example.presentation.Viewer
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test


class FoodChangeMoodConsoleUITest {

    private lateinit var feature1: Feature
    private lateinit var feature2: Feature
    private lateinit var foodChangeMoodConsoleUseCase: FoodChangeMoodConsoleUI

    private lateinit var viewer: Viewer
    private lateinit var reader: Reader

    @BeforeEach
    fun setUp() {
        feature1 = mockk<Feature>(relaxed = true)
        feature2 = mockk<Feature>(relaxed = true)

        every { feature1.id } returns 1
        every { feature1.name } returns "Feature One"
        every { feature2.id } returns 2
        every { feature2.name } returns "Feature Two"

        viewer = mockk(relaxed = true)
        reader = mockk()

        foodChangeMoodConsoleUseCase =
            FoodChangeMoodConsoleUI(
                mapOf(1 to feature1, 2 to feature2), viewer, reader
            )

    }

    @Test
    fun `should print the welcome message when started and exit after finish`() {
        every { reader.readInput() } returns "0"
        foodChangeMoodConsoleUseCase.start()
        verify { viewer.log("Welcome to Food Change Mood!") }
        verify { viewer.log("Exiting... Goodbye!") }
    }

    @Test
    fun `should execute the selected feature`() {
        every { reader.readInput() } returnsMany listOf("1", "0") // Simulate Multiple Inputs
        foodChangeMoodConsoleUseCase.start()
        verify { feature1.execute() }
    }

}