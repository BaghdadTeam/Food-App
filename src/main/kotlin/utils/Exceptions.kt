package org.example.utils

class EmptyMealNameException(message: String = "") : Exception(message)
class EmptyMealsException(message: String = "") : Exception(message)
class NoMealFoundException(message: String = "") : NoSuchElementException(message)
class InvalidDateFormatException(message: String = "") : Exception(message)
class CsvParsingException(message: String = "", cause: Throwable) : Exception(message, cause)
class CsvLoadingException(message: String = "", cause: Throwable) : Exception(message, cause)
class EmptyRecordException(message: String = "") : Exception(message)