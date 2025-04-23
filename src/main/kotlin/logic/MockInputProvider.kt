package org.example.logic
class MockInputProvider(private val inputs: List<String>) {
    private var index = 0
    fun provideInput(): String {
        return if (index < inputs.size) {
            inputs[index++]
        } else {
            throw IllegalStateException("No more inputs available")
        }
    }
}