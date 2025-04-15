fun kmpSearch(text: String, pattern: String): Boolean {
    val textLength = text.length
    val patternLength = pattern.length
    val lps = constructLPS(pattern)
    var i = 0
    var j = 0

    while (i < textLength) {
        if (text[i] == pattern[j]) {
            i++
            j++
        }
        if (j == patternLength) {
            return true
        } else if (i < textLength && text[i] != pattern[j]) {
            if (j != 0) {
                j = lps[j - 1]
            } else {
                i++
            }
        }
    }
    return false
}
fun constructLPS(pattern: String): IntArray {
    val patternLength = pattern.length
    val lps = IntArray(patternLength)

    var len = 0
    var i = 1

    while (i < patternLength) {
        if (pattern[i] == pattern[len]) {
            len++
            lps[i] = len
            i++
        } else {
            if (len != 0) {
                len = lps[len - 1]
            } else {
                lps[i] = 0
                i++
            }
        }
    }
    return lps
}