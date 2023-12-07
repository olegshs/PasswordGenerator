package ga.sharich.randompassword

import kotlin.math.log2
import kotlin.math.pow

class Entropy(val base: Int, val length: Int) {
    fun getCombinations(): Double {
        return if (base == 0 || length == 0) {
            0.0
        } else {
            base.toDouble().pow(length.toDouble())
        }
    }

    fun getEntropy(): Double {
        return if (base == 0 || length == 0) {
            0.0
        } else {
            log2(getCombinations())
        }
    }
}
