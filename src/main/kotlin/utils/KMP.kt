fun kmpSearch(text: String, pattern: String): Boolean {
    val textLength = text.length
    val patternLength = pattern.length
    val lps = constructLPS(pattern)

    var textIndex = 0
    var patternIndex = 0

    while (textIndex < textLength) {
        if (text[textIndex] == pattern[patternIndex]) {
            textIndex++
            patternIndex++
        }
        if (patternIndex == patternLength) {
            return true
        } else if (textIndex < textLength && text[textIndex] != pattern[patternIndex]) {
            if (patternIndex != 0) {
                patternIndex = lps[patternIndex - 1]
            } else {
                textIndex++
            }
        }
    }
    return false
}
fun constructLPS(pattern: String): IntArray {
    val patternLength = pattern.length
    val lps = IntArray(patternLength)

    var len = 0
    var index = 1

    while (index < patternLength) {
        if (pattern[index] == pattern[len]) {
            len++
            lps[index] = len
            index++
        } else {
            if (len != 0) {
                len = lps[len - 1]
            } else {
                lps[index] = 0
                index++
            }
        }
    }
    return lps
}