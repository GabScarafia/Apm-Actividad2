package com.example.apm_actividad2

import android.os.Bundle
import android.view.MenuItem
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
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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
            val country = countryEditText.text.toString().trim()
            val city = cityEditText.text.toString().trim()
            val population = populationEditText.text?.toString()?.toIntOrNull() ?: 0

            if(country.isNullOrBlank() or city.isNullOrBlank() or (population == 0)){
                Toast.makeText(
                    this,
                    "Hay un campo En nulo",
                    Toast.LENGTH_SHORT
                ).show()
            }else {

                val newCity = City(null, country, city, population)
                val res = db.addCity(newCity)
                if (res) {
                    Toast.makeText(
                        this,
                        "Ciudad Agregada Correctamente",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this,
                        "Esta ciudad ya existe",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        getCityButton.setOnClickListener {
            val cityName = cityEditText.text.toString().trim()
            if(cityName.isNullOrBlank()){
                Toast.makeText(
                    this,
                    "El campo ciudad no puede estar nulo",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else
            {
                val city = db.getCityByName(cityName)
                city?.let {
                    Toast.makeText(
                        this,
                        "${it.city}, ${it.country}: ${it.population} Personas",
                        Toast.LENGTH_SHORT
                    ).show()
                } ?: run {
                    Toast.makeText(
                        this,
                        "Ciudad No Encontrada",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        deleteCityButton.setOnClickListener {
            val cityName = cityEditText.text.toString().trim()
            if(cityName.isNullOrBlank()){
                Toast.makeText(
                    this,
                    "el campo ciudad no puede estar nulo",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else
            {
                val res = db.deleteCityByName(cityName)
                if(res){
                    Toast.makeText(
                        this,
                        "Ciudad Borrada Correctamente",
                        Toast.LENGTH_SHORT
                    ).show()}
                else
                {
                    Toast.makeText(
                        this,
                        "Error, esta ciudad no existe",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }


        deleteCitiesByCountryButton.setOnClickListener {
            val country = countryEditText.text.toString().trim()
            if(country.isNullOrBlank()){
                Toast.makeText(
                    this,
                    "El campo pais no puede estar nulo",
                    Toast.LENGTH_SHORT
                ).show()
            }else
            {
                val res = db.deleteCitiesByCountry(country)
                if(res){
                    Toast.makeText(
                        this,
                        "Ciudades Borradas Correctamente",
                        Toast.LENGTH_SHORT
                    ).show()}
                else
                {
                    Toast.makeText(
                        this,
                        "Error, no hay ciudades en este pais",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }


        }

        updatePopulationButton.setOnClickListener {
            val cityName = cityEditText.text.toString().trim()
            val newPopulation = populationEditText.text?.toString()?.toIntOrNull() ?: 0
            if(cityName.isNullOrBlank() or (newPopulation == 0)){
                Toast.makeText(
                    this,
                    "El campo ciudad y el campo poblacion no puede ser nulo",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else{
                val city = db.getCityByName(cityName)

                city?.let {
                    it.population = newPopulation
                    db.updateCityPopulation(it)

                    Toast.makeText(
                        this,
                        "Población actualizada",
                        Toast.LENGTH_SHORT
                    ).show()
                } ?: run {
                    Toast.makeText(
                        this,
                        "Ciudad No Encontrada",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}