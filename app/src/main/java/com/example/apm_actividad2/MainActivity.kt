package com.example.apm_actividad2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity()  {


    private lateinit var actividad1Button: Button
    private lateinit var actividad2Button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        actividad1Button = findViewById(R.id.actividad1)
        actividad2Button = findViewById(R.id.actividad2)

        actividad1Button.setOnClickListener {
            //TODO: Implementar la lógica para registrar nuevos usuarios
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }
        actividad2Button.setOnClickListener {
            //TODO: Implementar la lógica para registrar nuevos usuarios
            val intent = Intent(this, CiudadActivity::class.java)
            startActivity(intent)
        }
    }

}