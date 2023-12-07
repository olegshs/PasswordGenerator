package ga.sharich.passwordgenerator

import android.content.ClipData
import android.content.ClipboardManager
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.*
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.ViewModelProvider
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var model: RandomPasswordModel

    private val passwordMinLength = 4
    private val passwordMaxLength = 40

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        loadConfig()
    }

    override fun onStop() {
        super.onStop()

        saveConfig()
    }

    private fun init() {
        model = ViewModelProvider(this).get(RandomPasswordModel::class.java)

        initNumbersControls()
        initLowerCaseControls()
        initUpperCaseControls()
        initSymbolsControls()

        initLengthControls()
        initPropertiesControls()

        initGenerateButton()

        initPasswordTextControls()
        initEntropy()
    }

    private fun initNumbersControls() {
        val numbersSwitch = findViewById<SwitchCompat>(R.id.numbersSwitch)
        model.getNumbers().observe(this, { value ->
            numbersSwitch.isChecked = value
        })
        numbersSwitch.setOnCheckedChangeListener { _, isChecked ->
            model.setNumbers(isChecked)
        }

        val numbersExcludeCheckBox = findViewById<CheckBox>(R.id.numbersExcludeCheckBox)
        model.getNumbers().observe(this, { value ->
            numbersExcludeCheckBox.isChecked = value
        })
        numbersExcludeCheckBox.setOnCheckedChangeListener { _, isChecked ->
            model.setNumbersExclude(isChecked)
        }
    }

    private fun initLowerCaseControls() {
        val lowerCaseSwitch = findViewById<SwitchCompat>(R.id.lowerCaseSwitch)
        model.getLowerCase().observe(this, { value ->
            lowerCaseSwitch.isChecked = value
        })
        lowerCaseSwitch.setOnCheckedChangeListener { _, isChecked ->
            model.setLowerCase(isChecked)
        }

        val lowerCaseExcludeCheckBox = findViewById<CheckBox>(R.id.lowerCaseExcludeCheckBox)
        model.getLowerCaseExclude().observe(this, { value ->
            lowerCaseExcludeCheckBox.isChecked = value
        })
        lowerCaseExcludeCheckBox.setOnCheckedChangeListener { _, isChecked ->
            model.setLowerCaseExclude(isChecked)
        }
    }

    private fun initUpperCaseControls() {
        val upperCaseSwitch = findViewById<SwitchCompat>(R.id.upperCaseSwitch)
        model.getUpperCase().observe(this, { value ->
            upperCaseSwitch.isChecked = value
        })
        upperCaseSwitch.setOnCheckedChangeListener { _, isChecked ->
            model.setUpperCase(isChecked)
        }

        val upperCaseExcludeCheckBox = findViewById<CheckBox>(R.id.upperCaseExcludeCheckBox)
        model.getUpperCaseExclude().observe(this, { value ->
            upperCaseExcludeCheckBox.isChecked = value
        })
        upperCaseExcludeCheckBox.setOnCheckedChangeListener { _, isChecked ->
            model.setUpperCaseExclude(isChecked)
        }
    }

    private fun initSymbolsControls() {
        val symbolsSwitch = findViewById<SwitchCompat>(R.id.symbolsSwitch)
        model.getSymbols().observe(this, { value ->
            symbolsSwitch.isChecked = value
        })
        symbolsSwitch.setOnCheckedChangeListener { _, isChecked ->
            model.setSymbols(isChecked)
        }
    }

    private fun initLengthControls() {
        val lengthValue = findViewById<TextView>(R.id.lengthValue)

        val lengthSeekBar = findViewById<SeekBar>(R.id.lengthSeekBar)
        lengthSeekBar.max = passwordMaxLength - passwordMinLength

        model.getLength().observe(this, { value ->
            lengthValue.text = value.toString()
            lengthSeekBar.progress = value - passwordMinLength
        })

        lengthSeekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                model.setLength(progress + passwordMinLength)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    private fun initPropertiesControls() {
        val mustContainAllTypesCheckBox = findViewById<CheckBox>(R.id.mustContainAllTypesCheckBox)
        model.getMustContainAllTypes().observe(this, { value ->
            mustContainAllTypesCheckBox.isChecked = value
        })
        mustContainAllTypesCheckBox.setOnCheckedChangeListener { _, isChecked ->
            model.setMustContainAllTypes(isChecked)
        }

        val lessUpperCaseCheckBox = findViewById<CheckBox>(R.id.lessUpperCaseCheckBox)
        model.getLessUpperCase().observe(this, { value ->
            lessUpperCaseCheckBox.isChecked = value
        })
        lessUpperCaseCheckBox.setOnCheckedChangeListener { _, isChecked ->
            model.setLessUpperCase(isChecked)
        }

        val lessSymbolsCheckBox = findViewById<CheckBox>(R.id.lessSymbolsCheckBox)
        model.getLessSymbols().observe(this, { value ->
            lessSymbolsCheckBox.isChecked = value
        })
        lessSymbolsCheckBox.setOnCheckedChangeListener { _, isChecked ->
            model.setLessSymbols(isChecked)
        }
    }

    private fun initGenerateButton() {
        val generateButton = findViewById<Button>(R.id.generateButton)
        generateButton.setOnClickListener {
            model.generate()
        }
    }

    private fun initPasswordTextControls() {
        val passwordText = findViewById<TextView>(R.id.passwordText)
        model.getPassword().observe(this, { value ->
            passwordText.text = value
        })

        val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager

        val passwordCopyButton = findViewById<ImageButton>(R.id.passwordCopyButton)
        passwordCopyButton.setOnClickListener {
            val password = passwordText.text.toString()

            val clip = ClipData.newPlainText("Generated password", password)
            clipboard.setPrimaryClip(clip)

            Toast.makeText(applicationContext, "Copied to clipboard", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initEntropy() {
        val entropyValue = findViewById<TextView>(R.id.entropyValue)
        val entropyCombinations = findViewById<TextView>(R.id.entropyCombinations)

        model.getEntropy().observe(this, { entropy ->
            val bits = entropy.getEntropy().toInt()
            entropyValue.text = bits.toString()

            val combinations: Double = entropy.getCombinations()
            entropyCombinations.text = formatDouble(combinations)
        })
    }

    private fun formatDouble(v: Double): String {
        return if (v < 1000000000.0) {
            String.format(Locale.US, "%,.0f", v).replace(",", " ")
        } else {
            String.format(Locale.US, "%g", v)
        }
    }

    private fun loadConfig() {
        val config = getConfig() ?: return

        with(config) {
            with(model) {
                setLength(getInt("length", getLength().value!!))
                setNumbers(getBoolean("numbers", getNumbers().value!!))
                setNumbersExclude(getBoolean("numbersExclude", getNumbersExclude().value!!))
                setLowerCase(getBoolean("lowerCase", getLowerCase().value!!))
                setLowerCaseExclude(getBoolean("lowerCaseExclude", getLowerCaseExclude().value!!))
                setUpperCase(getBoolean("upperCase", getUpperCase().value!!))
                setUpperCaseExclude(getBoolean("upperCaseExclude", getUpperCaseExclude().value!!))
                setSymbols(getBoolean("symbols", getSymbols().value!!))
                setMustContainAllTypes(getBoolean("mustContainAllTypes", getMustContainAllTypes().value!!))
                setLessUpperCase(getBoolean("lessUpperCase", getLessUpperCase().value!!))
                setLessSymbols(getBoolean("lessSymbols", getLessSymbols().value!!))
            }
        }
    }

    private fun saveConfig() {
        val config = getConfig() ?: return

        with(config.edit()) {
            with(model) {
                putInt("length", getLength().value!!)
                putBoolean("numbers", getNumbers().value!!)
                putBoolean("numbersExclude", getNumbersExclude().value!!)
                putBoolean("lowerCase", getLowerCase().value!!)
                putBoolean("lowerCaseExclude", getLowerCaseExclude().value!!)
                putBoolean("upperCase", getUpperCase().value!!)
                putBoolean("upperCaseExclude", getUpperCaseExclude().value!!)
                putBoolean("symbols", getSymbols().value!!)
                putBoolean("mustContainAllTypes", getMustContainAllTypes().value!!)
                putBoolean("lessUpperCase", getLessUpperCase().value!!)
                putBoolean("lessSymbols", getLessSymbols().value!!)
            }
            apply()
        }
    }

    private fun getConfig(): SharedPreferences? {
        return getSharedPreferences("RandomPassword", MODE_PRIVATE)
    }
}
