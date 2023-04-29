package com.example.apm_actividad2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CiudadActivity : AppCompatActivity() {

    private lateinit var db: CiudadService
    private lateinit var countryEditText: EditText
    private lateinit var cityEditText: EditText
    private lateinit var populationEditText: EditText
    private lateinit var resultTextView: TextView

    private lateinit var addCityButton: Button
    private lateinit var getCityButton: Button
    private lateinit var deleteCityButton: Button
    private lateinit var deleteCitiesByCountryButton: Button
    private lateinit var updatePopulationButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cities)

        db = CiudadService(this)

        countryEditText = findViewById(R.id.countryEditText)
        cityEditText = findViewById(R.id.cityEditText)
        populationEditText = findViewById(R.id.populationEditText)
        resultTextView = findViewById(R.id.resultTextView)

        addCityButton = findViewById(R.id.addCityButton)
        getCityButton = findViewById(R.id.getCityButton)
        deleteCityButton = findViewById(R.id.deleteCityButton)
        deleteCitiesByCountryButton = findViewById(R.id.deleteCitiesByCountryButton)
        updatePopulationButton = findViewById(R.id.updatePopulationButton)


        addCityButton.setOnClickListener {
            val country = countryEditText.text.toString()
            val city = cityEditText.text.toString()
            val population = populationEditText.text.toString().toInt()

            val newCity = City(null, country, city, population)
            db.addCity(newCity)
        }

        getCityButton.setOnClickListener {
            val cityName = cityEditText.text.toString()

            val city = db.getCityByName(cityName)

            city?.let {
                Toast.makeText(
                    this,
                    "${it.city}, ${it.country}: ${it.population} people",
                    Toast.LENGTH_SHORT
                ).show()
            } ?: run {
                Toast.makeText(
                    this,
                    "City not found",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        deleteCityButton.setOnClickListener {
            val cityName = cityEditText.text.toString()

            db.deleteCityByName(cityName)
        }

        deleteCitiesByCountryButton.setOnClickListener {
            val country = countryEditText.text.toString()

            db.deleteCitiesByCountry(country)
        }

        updatePopulationButton.setOnClickListener {
            val cityName = cityEditText.text.toString()
            val newPopulation = populationEditText.text.toString().toInt()

            val city = db.getCityByName(cityName)

            city?.let {
                it.population = newPopulation
                db.updateCityPopulation(it)

                Toast.makeText(
                    this,
                    "Population updated",
                    Toast.LENGTH_SHORT
                ).show()
            } ?: run {
                Toast.makeText(
                    this,
                    "City not found",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}