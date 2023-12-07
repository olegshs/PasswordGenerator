package ga.sharich.randompassword

import java.security.SecureRandom

class RandomPassword {
    var length = 16

    var numbers = true
    var numbersExclude = true
    private val numberChars = "123456789"
    private val numberExtraChars = "0"

    var lowerCase = true
    var lowerCaseExclude = true
    private val lowerCaseChars = "abcdefghijkmnopqrstuvwxyz"
    private val lowerCaseExtraChars = "l"

    var upperCase = true
    var upperCaseExclude = true
    private val upperCaseChars = "ABCDEFGHJKLMNPQRSTUVWXYZ"
    private val upperCaseExtraChars = "IO"

    var symbols = false
    private val symbolChars = "!\"#$&'()*+,-./:;?@_"

    var mustContainAllTypes = true
    var lessUpperCase = true
    var lessSymbols = true

    fun getEntropy(): Entropy {
        val uniqueCharsCount = getAlphabet().toCharArray().distinct().size
        return Entropy(uniqueCharsCount, length)
    }

    fun generate(): String {
        if (getAlphabet().isEmpty()) {
            return ""
        }

        for (i in 0 until 1000) {
            val password = getRandomPassword()
            if (isValid(password)) {
                return password
            }
        }
        return ""
    }

    private fun getRandomPassword(): String {
        val alphabet = getAlphabet()
        val alphabetLength = alphabet.length
        if (alphabetLength == 0) {
            return ""
        }

        val rand = SecureRandom()
        val password = StringBuilder()

        for (i in 0 until length) {
            val r = rand.nextInt(alphabetLength)
            password.append(alphabet[r])
        }

        return password.toString()
    }

    private fun isValid(password: String): Boolean {
        if (mustContainAllTypes && !stringContainsAllTypes(password)) {
            return false
        }
        return true
    }

    private fun stringContainsAllTypes(string: String): Boolean {
        if (numbers && !stringContainsChars(string, numberChars, numberExtraChars)) {
            return false
        }
        if (lowerCase && !stringContainsChars(string, lowerCaseChars, lowerCaseExtraChars)) {
            return false
        }
        if (upperCase) {
            var p = string
            if (p.length >= 8) {
                p = p.substring(1)
            }
            if (!stringContainsChars(p, upperCaseChars, upperCaseExtraChars)) {
                return false
            }
        }
        if (symbols && !stringContainsChars(string, symbolChars)) {
            return false
        }
        return true
    }

    private fun stringContainsChars(string: String, vararg charsets: String): Boolean {
        for (chars in charsets) {
            for (ch in chars) {
                if (string.contains(ch)) {
                    return true
                }
            }
        }
        return false
    }

    private fun getAlphabet(): String {
        val alphabet = StringBuilder()

        val n = if (lessUpperCase && lessSymbols) {
            5
        } else if (lessUpperCase || lessSymbols) {
            4
        } else {
            1
        }

        for (i in 0 until n) {
            if (numbers) {
                alphabet.append(numberChars)
                if (!numbersExclude) {
                    alphabet.append(numberExtraChars)
                }
            }

            if (lowerCase) {
                alphabet.append(lowerCaseChars)
                if (!lowerCaseExclude) {
                    alphabet.append(lowerCaseExtraChars)
                }
            }

            if (upperCase && (!lessUpperCase || i == 0)) {
                alphabet.append(upperCaseChars)
                if (!upperCaseExclude) {
                    alphabet.append(upperCaseExtraChars)
                }
            }

            if (symbols && (!lessSymbols || i == 0)) {
                alphabet.append(symbolChars)
            }
        }

        return alphabet.toString()
    }
}
