package logic.helpers.game

import kotlinx.datetime.LocalDate
import logic.helpers.createMealHelper
import model.Nutrition
import org.example.model.IngredientQuestion

object IngredientGameTestData {
    val meals = listOf(
        createMealHelper(
            name = "Breakfast Pizza",
            id = 1,
            ingredients = listOf("Cheese", "Tomato", "Lettuce", "null", "Onion", "Beef"),
        ),

        createMealHelper(
            name = "Chicken Alfredo", id = 2,
            ingredients = listOf("Chicken", "Cream", "Parmesan", "Garlic", "Pasta", "Pepper")
        ),

        createMealHelper(
            "Beef Tacos", 3, 102, LocalDate.parse("2022-01-03"), listOf("mexican"),
            Nutrition(300.0, 20.0, 5.0, 20.0, 25.0, 4.0, 1.2), 3,
            listOf("Cook beef", "Prepare shells", "Assemble tacos"), "Spicy beef tacos",
            listOf("Beef", "Taco shells", "Lettuce", "Cheddar", "Tomato", "Onion"), 3, 25
        ),

        createMealHelper(
            "Veggie Stir Fry", 4, 103, LocalDate.parse("2022-01-04"), listOf("vegetarian"),
            Nutrition(250.0, 10.0, 0.0, 15.0, 30.0, 10.0, 1.3), 3,
            listOf("Chop veggies", "Stir fry", "Serve hot"), "Colorful and quick veggie stir fry",
            listOf("Broccoli", "Carrot", "Bell Pepper", "Soy Sauce", "Garlic", "Ginger"), 3, 20
        ),

        createMealHelper(
            "Salmon Bowl", 5, 104, LocalDate.parse("2022-01-05"), listOf("seafood"),
            Nutrition(350.0, 15.0, 1.0, 25.0, 28.0, 7.0, 1.6), 3,
            listOf("Grill salmon", "Cook rice", "Assemble bowl"), "Nutritious salmon bowl",
            listOf("Salmon", "Rice", "Avocado", "Cucumber", "Soy Sauce", "Sesame"), 3, 35
        ),

        createMealHelper(
            "Pancakes", 6, 105, LocalDate.parse("2022-01-06"), listOf("breakfast"),
            Nutrition(200.0, 5.0, 0.0, 6.0, 30.0, 8.0, 1.1), 2,
            listOf("Mix batter", "Cook on skillet"), "Fluffy pancakes",
            listOf("Flour", "Egg", "Milk", "Sugar", "Baking Powder", "Butter"), 2, 15
        ),

        createMealHelper(
            "Lentil Soup", 7, 106, LocalDate.parse("2022-01-07"), listOf("vegan", "soup"),
            Nutrition(180.0, 3.0, 0.0, 10.0, 25.0, 12.0, 1.2), 2,
            listOf("Boil ingredients", "Simmer"), "Comforting lentil soup",
            listOf("Lentils", "Carrot", "Celery", "Onion", "Garlic", "Tomato"), 2, 40
        ),

        createMealHelper(
            "Shrimp Scampi", 8, 107, LocalDate.parse("2022-01-08"), listOf("seafood", "italian"),
            Nutrition(370.0, 18.0, 1.0, 24.0, 30.0, 6.0, 1.4), 3,
            listOf("Cook pasta", "Sauté shrimp", "Combine with sauce"), "Buttery shrimp scampi",
            listOf("Shrimp", "Garlic", "Butter", "Parsley", "Lemon", "Spaghetti"), 3, 30
        ),

        createMealHelper(
            "Caesar Salad", 9, 108, LocalDate.parse("2022-01-09"), listOf("salad"),
            Nutrition(220.0, 15.0, 0.5, 7.0, 12.0, 3.0, 1.0), 2,
            listOf("Chop ingredients", "Toss with dressing"), "Classic Caesar salad",
            listOf("Lettuce", "Croutons", "Parmesan", "Caesar dressing", "Chicken"), 2, 10
        ),

        createMealHelper(
            "Steak and Potatoes", 10, 109, LocalDate.parse("2022-01-10"), listOf("dinner"),
            Nutrition(450.0, 25.0, 1.5, 35.0, 40.0, 5.0, 1.7), 3,
            listOf("Grill steak", "Boil potatoes", "Plate together"), "Hearty meal",
            listOf("Steak", "Potatoes", "Salt", "Pepper", "Butter"), 3, 50
        ),

        createMealHelper(
            "Fruit Smoothie", 11, 110, LocalDate.parse("2022-01-11"), listOf("drink", "breakfast"),
            Nutrition(150.0, 2.0, 0.0, 3.0, 30.0, 15.0, 0.9), 1,
            listOf("Blend all ingredients"), "Refreshing smoothie",
            listOf("Banana", "Strawberry", "Yogurt", "Honey", "Milk"), 1, 5
        ),

        createMealHelper(
            "Baked Ziti", 12, 111, LocalDate.parse("2022-01-12"), listOf("italian"),
            Nutrition(380.0, 20.0, 2.0, 22.0, 45.0, 8.0, 1.3), 4,
            listOf("Boil pasta", "Prepare sauce", "Layer with cheese", "Bake"), "Cheesy baked ziti",
            listOf("Ziti", "Tomato Sauce", "Mozzarella", "Ricotta", "Basil", "Onion"), 4, 40
        ),

        createMealHelper(
            "Quinoa Salad", 13, 112, LocalDate.parse("2022-01-13"), listOf("vegan", "salad"),
            Nutrition(240.0, 9.0, 0.0, 8.0, 35.0, 6.0, 1.1), 2,
            listOf("Cook quinoa", "Mix ingredients"), "Light quinoa salad",
            listOf("Quinoa", "Tomato", "Cucumber", "Mint", "Olive oil", "Lemon juice"), 2, 25
        ),

        createMealHelper(
            "Eggplant Parmesan",
            14,
            113,
            LocalDate.parse("2022-01-14"),
            listOf("vegetarian", "italian"),
            Nutrition(310.0, 15.0, 1.0, 12.0, 28.0, 5.0, 1.2),
            3,
            listOf("Fry eggplant", "Layer with cheese", "Bake"),
            "Crispy eggplant parmesan",
            listOf("Eggplant", "Parmesan", "Marinara", "Mozzarella", "Basil"),
            3,
            35
        ),

        createMealHelper(
            "Sushi Rolls", 15, 114, LocalDate.parse("2022-01-15"), listOf("japanese", "seafood"),
            Nutrition(280.0, 8.0, 0.0, 15.0, 36.0, 6.0, 1.0), 3,
            listOf("Cook rice", "Roll ingredients", "Slice and serve"), "Homemade sushi",
            listOf("Rice", "Seaweed", "Salmon", "Avocado", "Cucumber", "Soy Sauce"), 3, 50
        )
    )

    val inValidMeals = listOf(
        createMealHelper(
            "Breakfast Pizza", 1, 100, LocalDate.parse("2022-01-01"), listOf("breakfast"),
            Nutrition(173.4, 18.0, 0.0, 17.0, 22.0, 35.0, 1.0), 3,
            listOf("Preheat", "Add toppings", "Bake"), "Quick pizza",
            null, 3, 30
        ),

        createMealHelper(
            null, 2, 101, LocalDate.parse("2022-01-02"), listOf("dinner"),
            Nutrition(400.0, 25.0, 2.0, 30.0, 35.0, 5.0, 1.5), 4,
            listOf("Cook pasta", "Grill chicken", "null", "Mix together"), "Creamy pasta",
            listOf("Chicken", "Cream", "Parmesan", "Garlic", "Pasta", "Pepper"), 4, 45
        ),

        createMealHelper(
            "Beef Tacos", null, 102, LocalDate.parse("2022-01-03"), listOf("mexican"),
            Nutrition(300.0, 20.0, 5.0, 20.0, 25.0, 4.0, 1.2), 3,
            listOf("Cook beef", "Prepare shells", "Assemble tacos"), "Spicy beef tacos",
            listOf("Beef", "Taco shells", "Lettuce", "Cheddar", "Tomato", "Onion"), 3, 25
        ),

        createMealHelper(
            "Veggie Stir Fry",
            4,
            103,
            LocalDate.parse("2022-01-04"),
            listOf("vegetarian"),
            Nutrition(250.0, 10.0, 0.0, 15.0, 30.0, 10.0, 1.3),
            3,
            listOf("Chop veggies", "Stir fry", "Serve hot"),
            "Colorful and quick veggie stir fry",
            listOf("Broccoli", "Carrot", "Bell Pepper", "Soy Sauce", "Garlic", "Ginger"),
            3,
            20
        )
    )

    val fixedOptions = IngredientQuestion(
        mealName = "Pizza",
        correctIngredient = "Cheese",
        options = listOf("Cheese", "lattice", "sugar")
    )

}