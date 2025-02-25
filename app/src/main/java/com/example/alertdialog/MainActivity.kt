package com.example.alertdialog

import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var textWeather: TextView
    private lateinit var sharedPreferences: android.content.SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textWeather = findViewById(R.id.textWeather)
        val btnSettings: Button = findViewById(R.id.btn_settings)
        val btnLanguage: Button = findViewById(R.id.btn_language)

        sharedPreferences = getSharedPreferences("AppSettings", Context.MODE_PRIVATE)

        val savedLanguage = sharedPreferences.getString("language", "English") ?: "English"

        btnSettings.setOnClickListener {
            val dialog = MyDialog(this) { selectedCity ->
                updateWeatherInfo(selectedCity)
            }
            dialog.show(supportFragmentManager, "settings_dialog")
        }

        btnLanguage.setOnClickListener {
            val dialog = LanguageDialog(this) { selectedLanguage ->
                saveLanguage(selectedLanguage)
                updateWeatherInfo(textWeather.text.toString().split(":")[0]) // Перевести текущий город
            }
            dialog.show(supportFragmentManager, "language_dialog")
        }

        updateWeatherInfo("New York")
    }

    private fun updateWeatherInfo(city: String) {
        val language = sharedPreferences.getString("language", "English") ?: "English"

        val weatherData = when (city) {
            "New York", "Нью-Йорк", "Nueva York" -> mapOf(
                "English" to "New York: 10°C, Wind 5m/s, Clear",
                "Русский" to "Нью-Йорк: 10°C, Ветер 5м/с, Ясно",
                "Español" to "Nueva York: 10°C, Viento 5m/s, Despejado"
            )
            "Moscow", "Москва", "Moscú" -> mapOf(
                "English" to "Moscow: -5°C, Snow, Wind 3m/s",
                "Русский" to "Москва: -5°C, Снег, Ветер 3м/с",
                "Español" to "Moscú: -5°C, Nieve, Viento 3m/s"
            )
            "Tokyo", "Токио", "Tokio" -> mapOf(
                "English" to "Tokyo: 15°C, Rain, Wind 2m/s",
                "Русский" to "Токио: 15°C, Дождь, Ветер 2м/с",
                "Español" to "Tokio: 15°C, Lluvia, Viento 2m/s"
            )
            else -> mapOf(
                "English" to "Weather data unavailable",
                "Русский" to "Данные о погоде недоступны",
                "Español" to "Datos meteorológicos no disponibles"
            )
        }

        textWeather.text = weatherData[language] ?: weatherData["English"]
    }

    private fun saveLanguage(language: String) {
        sharedPreferences.edit().putString("language", language).apply()
    }
}
