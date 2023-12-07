package ga.sharich.passwordgenerator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ga.sharich.randompassword.Entropy
import ga.sharich.randompassword.RandomPassword

class RandomPasswordModel : ViewModel() {
    private val randomPassword = RandomPassword()

    private val password = MutableLiveData(randomPassword.generate())
    private val entropy = MutableLiveData(randomPassword.getEntropy())

    private val length = MutableLiveData(randomPassword.length)

    private val numbers = MutableLiveData(randomPassword.numbers)
    private val numbersExclude = MutableLiveData(randomPassword.numbersExclude)

    private val lowerCase = MutableLiveData(randomPassword.lowerCase)
    private val lowerCaseExclude = MutableLiveData(randomPassword.lowerCaseExclude)

    private val upperCase = MutableLiveData(randomPassword.upperCase)
    private val upperCaseExclude = MutableLiveData(randomPassword.upperCaseExclude)

    private val symbols = MutableLiveData(randomPassword.symbols)

    private val mustContainAllTypes = MutableLiveData(randomPassword.mustContainAllTypes)
    private val lessUpperCase = MutableLiveData(randomPassword.lessUpperCase)
    private val lessSymbols = MutableLiveData(randomPassword.lessSymbols)

    fun generate() {
        password.value = randomPassword.generate()
        entropy.value = randomPassword.getEntropy()
    }

    fun getPassword(): LiveData<String> {
        return password
    }

    fun getEntropy(): LiveData<Entropy> {
        return entropy
    }

    fun getLength(): LiveData<Int> {
        return length
    }

    fun setLength(value: Int) {
        randomPassword.length = value
        length.value = value
        generate()
    }

    fun getNumbers(): LiveData<Boolean> {
        return numbers
    }

    fun setNumbers(value: Boolean) {
        randomPassword.numbers = value
        numbers.value = value
        generate()
    }

    fun getNumbersExclude(): LiveData<Boolean> {
        return numbersExclude
    }

    fun setNumbersExclude(value: Boolean) {
        randomPassword.numbersExclude = value
        numbersExclude.value = value
        generate()
    }

    fun getLowerCase(): LiveData<Boolean> {
        return lowerCase
    }

    fun setLowerCase(value: Boolean) {
        randomPassword.lowerCase = value
        lowerCase.value = value
        generate()
    }

    fun getLowerCaseExclude(): LiveData<Boolean> {
        return lowerCaseExclude
    }

    fun setLowerCaseExclude(value: Boolean) {
        randomPassword.lowerCaseExclude = value
        lowerCaseExclude.value = value
        generate()
    }

    fun getUpperCase(): LiveData<Boolean> {
        return upperCase
    }

    fun setUpperCase(value: Boolean) {
        randomPassword.upperCase = value
        upperCase.value = value
        generate()
    }

    fun getUpperCaseExclude(): LiveData<Boolean> {
        return upperCaseExclude
    }

    fun setUpperCaseExclude(value: Boolean) {
        randomPassword.upperCaseExclude = value
        upperCaseExclude.value = value
        generate()
    }

    fun getSymbols(): LiveData<Boolean> {
        return symbols
    }

    fun setSymbols(value: Boolean) {
        randomPassword.symbols = value
        symbols.value = value
        generate()
    }

    fun getMustContainAllTypes(): LiveData<Boolean> {
        return mustContainAllTypes
    }

    fun setMustContainAllTypes(value: Boolean) {
        randomPassword.mustContainAllTypes = value
        mustContainAllTypes.value = value
        generate()
    }

    fun getLessUpperCase(): LiveData<Boolean> {
        return lessUpperCase
    }

    fun setLessUpperCase(value: Boolean) {
        randomPassword.lessUpperCase = value
        lessUpperCase.value = value
        generate()
    }

    fun getLessSymbols(): LiveData<Boolean> {
        return lessSymbols
    }

    fun setLessSymbols(value: Boolean) {
        randomPassword.lessSymbols = value
        lessSymbols.value = value
        generate()
    }
}
