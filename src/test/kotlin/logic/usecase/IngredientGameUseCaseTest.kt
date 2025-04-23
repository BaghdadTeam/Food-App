package logic.usecase

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import kotlinx.datetime.LocalDate
import logic.MealsProvider
import model.Meal
import model.Nutrition
import org.example.model.IngredientQuestion
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


class IngredientGameUseCaseTest {
    private lateinit var mealsProvider: MealsProvider
    private lateinit var ingredientGameUseCase: IngredientGameUseCase

    private var meal: Meal = Meal(
        name = "Breakfast Pizza",
        id = 31490,
        contributorId = 26278,
        date = LocalDate.parse("2002-06-17"),
        tags = listOf("30-minutes-or-less", "time-to-make", "course"),
        nutrition = Nutrition(173.4, 18.0, 0.0, 17.0, 22.0, 35.0, 1.0),
        nSteps = 9,
        steps = listOf("Preheat oven", "Press dough", "Bake 5 minutes"),
        description = "A quick breakfast pizza recipe",
        ingredients = listOf("Cheese", "Tomato", "Lettuce", "Olive", "Onion", "Beef"),
        nIngredients = 3,
        preparationTime = 30
    )
    val meals = listOf(
        Meal(
            "Breakfast Pizza",
            1,
            100,
            LocalDate.parse("2022-01-01"),
            listOf("breakfast"),
            Nutrition(173.4, 18.0, 0.0, 17.0, 22.0, 35.0, 1.0),
            3,
            listOf("Preheat", "Add toppings", "Bake"),
            "Quick pizza",
            listOf("Cheese", "Tomato", "Lettuce", "Olive", "Onion", "Beef"),
            3,
            30
        ),
        Meal(
            "Chicken Alfredo",
            2,
            101,
            LocalDate.parse("2022-01-02"),
            listOf("dinner"),
            Nutrition(400.0, 25.0, 2.0, 30.0, 35.0, 5.0, 1.5),
            4,
            listOf("Cook pasta", "Grill chicken", "Make sauce", "Mix together"),
            "Creamy pasta",
            listOf("Chicken", "Cream", "Parmesan", "Garlic", "Pasta", "Pepper"),
            4,
            45
        ),
        Meal(
            "Beef Tacos",
            3,
            102,
            LocalDate.parse("2022-01-03"),
            listOf("mexican"),
            Nutrition(300.0, 20.0, 5.0, 20.0, 25.0, 4.0, 1.2),
            3,
            listOf("Cook beef", "Prepare shells", "Assemble tacos"),
            "Spicy beef tacos",
            listOf("Beef", "Taco shells", "Lettuce", "Cheddar", "Tomato", "Onion"),
            3,
            25
        ),
        Meal(
            "Caesar Salad",
            4,
            103,
            LocalDate.parse("2022-01-04"),
            listOf("salad"),
            Nutrition(150.0, 10.0, 2.0, 5.0, 10.0, 3.0, 0.5),
            2,
            listOf("Chop veggies", "Toss with dressing"),
            "Classic salad",
            listOf("Romaine", "Croutons", "Parmesan", "Caesar dressing", "Chicken", "Anchovy"),
            4,
            15
        ),
        Meal(
            "Veggie Stir Fry",
            5,
            104,
            LocalDate.parse("2022-01-05"),
            listOf("vegetarian"),
            Nutrition(180.0, 8.0, 6.0, 7.0, 20.0, 6.0, 1.0),
            3,
            listOf("Chop veggies", "Stir fry", "Add sauce"),
            "Healthy stir fry",
            listOf("Broccoli", "Carrot", "Bell pepper", "Soy sauce", "Tofu", "Garlic"),
            4,
            20
        ),
        Meal(
            "Grilled Cheese",
            6,
            105,
            LocalDate.parse("2022-01-06"),
            listOf("snack"),
            Nutrition(250.0, 15.0, 2.0, 10.0, 20.0, 1.0, 1.0),
            2,
            listOf("Butter bread", "Grill sandwich"),
            "Simple and tasty",
            listOf("Bread", "Cheese", "Butter", "Tomato"),
            2,
            10
        ),
        Meal(
            "Shrimp Pasta",
            7,
            106,
            LocalDate.parse("2022-01-07"),
            listOf("seafood"),
            Nutrition(350.0, 12.0, 3.0, 25.0, 40.0, 2.0, 1.3),
            3,
            listOf("Boil pasta", "Cook shrimp", "Mix together"),
            "Elegant seafood pasta",
            listOf("Shrimp", "Pasta", "Garlic", "Olive oil", "Chili flakes", "Parsley"),
            4,
            35
        ),
        Meal(
            "BBQ Ribs",
            8,
            107,
            LocalDate.parse("2022-01-08"),
            listOf("grill"),
            Nutrition(500.0, 30.0, 10.0, 35.0, 25.0, 0.0, 2.0),
            3,
            listOf("Season ribs", "Grill slowly", "Add BBQ sauce"),
            "Sticky and sweet",
            listOf("Pork ribs", "BBQ sauce", "Salt", "Pepper", "Honey", "Garlic"),
            4,
            60
        ),
        Meal(
            "Spaghetti Bolognese",
            9,
            108,
            LocalDate.parse("2022-01-09"),
            listOf("italian"),
            Nutrition(400.0, 18.0, 5.0, 20.0, 45.0, 3.0, 1.7),
            4,
            listOf("Cook spaghetti", "Prepare sauce", "Simmer", "Serve"),
            "Classic Italian pasta",
            listOf("Spaghetti", "Minced beef", "Tomato sauce", "Onion", "Carrot", "Celery"),
            4,
            40
        ),
        Meal(
            "Tofu Curry",
            10,
            109,
            LocalDate.parse("2022-01-10"),
            listOf("vegan"),
            Nutrition(320.0, 15.0, 4.0, 12.0, 30.0, 5.0, 1.4),
            3,
            listOf("Cook tofu", "Prepare curry", "Simmer together"),
            "Spicy vegan curry",
            listOf("Tofu", "Coconut milk", "Curry paste", "Bell pepper", "Onion", "Spinach"),
            4,
            30
        ),
        Meal(
            "Beef Stew",
            11,
            110,
            LocalDate.parse("2022-01-11"),
            listOf("comfort-food"),
            Nutrition(480.0, 22.0, 4.0, 40.0, 35.0, 6.0, 2.1),
            4,
            listOf("Brown beef", "Add veggies", "Simmer", "Serve hot"),
            "Hearty stew",
            listOf("Beef", "Potato", "Carrot", "Celery", "Onion", "Stock"),
            4,
            90
        ),
        Meal(
            "Egg Fried Rice",
            12,
            111,
            LocalDate.parse("2022-01-12"),
            listOf("chinese"),
            Nutrition(280.0, 10.0, 3.0, 8.0, 40.0, 2.0, 1.0),
            3,
            listOf("Cook rice", "Scramble egg", "Stir fry"),
            "Quick Asian dish",
            listOf("Rice", "Egg", "Peas", "Carrot", "Soy sauce", "Green onion"),
            3,
            20
        ),
        Meal(
            "Fish Tacos",
            13,
            112,
            LocalDate.parse("2022-01-13"),
            listOf("seafood"),
            Nutrition(260.0, 12.0, 2.0, 18.0, 25.0, 3.0, 1.2),
            3,
            listOf("Fry fish", "Prepare salsa", "Assemble tacos"),
            "Fresh and crispy",
            listOf("Fish", "Tortilla", "Cabbage", "Sour cream", "Lime", "Cilantro"),
            3,
            25
        ),
        Meal(
            "Falafel Wrap",
            14,
            113,
            LocalDate.parse("2022-01-14"),
            listOf("middle-eastern"),
            Nutrition(300.0, 14.0, 6.0, 10.0, 35.0, 7.0, 1.5),
            2,
            listOf("Fry falafel", "Wrap with veggies"),
            "Crispy veggie wrap",
            listOf("Falafel", "Pita", "Lettuce", "Tomato", "Tahini", "Cucumber"),
            3,
            15
        ),
        Meal(
            "Mac and Cheese",
            15,
            114,
            LocalDate.parse("2022-01-15"),
            listOf("kids"),
            Nutrition(400.0, 22.0, 3.0, 15.0, 45.0, 2.0, 1.3),
            2,
            listOf("Boil pasta", "Add cheese sauce"),
            "Cheesy goodness",
            listOf("Macaroni", "Cheddar", "Milk", "Butter", "Salt", "Pepper"),
            3,
            20
        )
    )


//    fun fakeQuestion(correct: String = "Cheese") = IngredientQuestion(
//        "Pizza", correct, listOf("Cheese", "Tomato", "Dough")
//    )

    @BeforeEach
    fun setUp() {
        mealsProvider = mockk(relaxed = true)
        ingredientGameUseCase = IngredientGameUseCase(mealsProvider)

    }


    @Test
    fun `should return true when answer is correct `() {

        val options = IngredientQuestion(
            mealName = "Pizza",
            correctIngredient = "Cheese",
            options = listOf("Cheese", "lattice", "sugar")
        )

        every { mealsProvider.getMeals() } returns listOf(meal)

        val result = ingredientGameUseCase.execute(options, 1)

        assertThat(result).isEqualTo(true)
//        assertThat(ingredientGameUseCase.getScore()).isEqualTo(1000)
    }

    @Test
    fun `should increase score when answer is correct`() {

        val options = IngredientQuestion(
            mealName = "Pizza",
            correctIngredient = "Cheese",
            options = listOf("Cheese", "lattice", "sugar")
        )

        every { mealsProvider.getMeals() } returns listOf(meal)

        ingredientGameUseCase.execute(options, 1)

//        assertThat(result).isEqualTo(true)
        assertThat(ingredientGameUseCase.getScore()).isEqualTo(1000)
    }

    @Test
    fun `should return false when invalid choice`() {
        val options = IngredientQuestion(
            mealName = "Pizza",
            correctIngredient = "Cheese",
            options = listOf("Cheese", "lattice", "sugar")
        )

        every { mealsProvider.getMeals() } returns listOf(meal)

        val result = ingredientGameUseCase.execute(options, 3)

        assertThat(result).isEqualTo(false)
//        assertThat(ingredientGameUseCase.getScore()).isEqualTo(0)
    }

    @Test
    fun `should not increase score for invalid choice`() {
        val options = IngredientQuestion(
            mealName = "Pizza",
            correctIngredient = "Cheese",
            options = listOf("Cheese", "lattice", "sugar")
        )

        every { mealsProvider.getMeals() } returns listOf(meal)

        ingredientGameUseCase.execute(options, 3)

//        assertThat(result).isEqualTo(false)
        assertThat(ingredientGameUseCase.getScore()).isEqualTo(0)
    }

    //    @Test
//    fun `should handle game over scenario`() {
//        val options = IngredientQuestion(
//            mealName = "Pizza",
//            correctIngredient = "Cheese",
//            options = listOf("Cheese", "lattice", "sugar")
//        )
//
//        // Mock no meals available, meaning the game is over
//        every { mealsProvider.getMeals() } returns emptyList()
//
//        val result = ingredientGameUseCase.execute(options, 1)
//
//        assertFalse(result)
//    }

    @Test
    fun `isGameOver should return true when reached 15 try`() {
        //todo make fake meals
//        val fakeMeals = List(20) { i ->
//            meal.copy(
//                id = i,
//                name = "Meal $i",
//                ingredients = listOf("Cheese", "Sugar", "Lettuce")
//            )
//        }
        every { mealsProvider.getMeals() } returns meals

        repeat(15) {
            val options = ingredientGameUseCase.getOptions()
            ingredientGameUseCase.execute(
                options!!,
                options.options.indexOf(options.correctIngredient) + 1
            )
        }

        val isOver = ingredientGameUseCase.isGameOver()
//        println("Game Over! Final Score: ${ingredientGameUseCase.getScore()} points")
        assertTrue(isOver)
    }

    @Test
    fun `isGameOver should return score of 15000 after 15 correct answers`() {

        every { mealsProvider.getMeals() } returns meals

        repeat(15) {
            val options = ingredientGameUseCase.getOptions()
            ingredientGameUseCase.execute(
                options!!,
                options.options.indexOf(options.correctIngredient) + 1
            )
        }

        assertThat(ingredientGameUseCase.getScore()).isEqualTo(15000)
    }


    @Test
    fun `getOptions should return null when no unused meals are available`() {
        every { mealsProvider.getMeals() } returns listOf(meal)

        repeat(1) { ingredientGameUseCase.getOptions() }

        val result = ingredientGameUseCase.getOptions()

        assertEquals(null, result)
    }

    //todo
    @Test
    fun `getOptions should return a meal to question for when game executed`() {
        every { mealsProvider.getMeals() } returns meals

    }

    //error todo
    @Test
    fun `getOptions should return 3 unique options`() {
        every { mealsProvider.getMeals() } returns meals

        val options = ingredientGameUseCase.getOptions()

        assertEquals(3, options?.options?.distinct()?.size)
//        assertEquals(options?.options?.distinct()?.size, options?.options?.size) // all unique
    }

    // todo
//    @Test
//    fun `getWrongIngredients should return 2 unique options that are not the correct answer`() {
//        every { mealsProvider.getMeals() } returns meals
//
//        val options = ingredientGameUseCase.getWrongIngredients() // private
//
//        assertEquals(3, options?.options?.size)
//    }

    @Test
    fun `getQuestionNumber should increase with correct answers`() {
        val options = IngredientQuestion(
            "Pizza", "Cheese", options = listOf("Cheese", "lattice", "sugar")
        )
        every { mealsProvider.getMeals() } returns listOf(meal)

        assertEquals(1, ingredientGameUseCase.getQuestionNumber())
        ingredientGameUseCase.execute(options, 1)
        assertEquals(2, ingredientGameUseCase.getQuestionNumber())
    }

    //todo edit
    @Test
    fun `getNextMeal should return the next unused meal`() {
//        val options = IngredientQuestion(
//            "Pizza", "Cheese", options = listOf("Cheese", "lattice", "sugar")
//        )
//
//        val result = ingredientGameUseCase.getMealName(options)
//
////        assertEquals("Pizza", result)
//        assertThat(result).isEqualTo("Pizza")
    }
    @Test
    fun `getMealName should return the correct meal name`() {
        val options = IngredientQuestion(
            "Pizza", "Cheese", options = listOf("Cheese", "lattice", "sugar")
        )

        val result = ingredientGameUseCase.getMealName(options)

        assertThat(result).isEqualTo("Pizza")
    }




}