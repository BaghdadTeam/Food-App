import data.CsvReader
import data.RecordParser
import logic.feature.SearchIfPotatos
import model.Meal
import java.io.File

fun main() {
    val csvRecords = CsvReader(File("food.csv")).readCsv()
    val parser = RecordParser()
    val meals = mutableListOf<Meal>()
    csvRecords.forEach { record ->
        meals.add(parser.parseRecord(record))
    }

    // println("${meals[5].name?.trim()}: ${meals[5].description}")
//    val potatoMeal = findPotatoMeals(meals)
//    displayRandomPotatoMeals(potatoMeal)

    // #8
//    val searcher = MealDateSearcher()
//    searcher.searchMealsByDate()

    // #12

}
