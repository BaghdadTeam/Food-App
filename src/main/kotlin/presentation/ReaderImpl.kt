package org.example.presentation

class ReaderImpl: Reader {
    override fun readInput(): String? {
        return readlnOrNull()
    }
}