package com.example.apm_actividad2

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class GameActivity : AppCompatActivity() {
    private lateinit var guessEditText: EditText
    private lateinit var scoreTextView: TextView
    private lateinit var highScoreTextView: TextView
    private lateinit var attemptTextView: TextView

    private lateinit var volverButton: Button

    private lateinit var game: Game
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        guessEditText = findViewById(R.id.guessEditText)
        scoreTextView = findViewById(R.id.scoreTextView)
        highScoreTextView = findViewById(R.id.highScoreTextView)
        sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        attemptTextView = findViewById(R.id.attemptTextView)

        val highScore = sharedPreferences.getInt("highScore", 0)
        highScoreTextView.text = "Puntuación máxima: $highScore"
        val submitButton: Button = findViewById(R.id.submitButton)
        attemptTextView.text = "Vidas:❤❤❤❤❤"

        game = Game()

        submitButton.setOnClickListener {
            val guess = guessEditText.text.toString().toIntOrNull()
            if (guess != null) {

                if(guess <= 5) {
                    val result = game.guess(guess)
                    val attempts = game.attempts
                    if (result == GuessResult.CORRECT) {
                        Toast.makeText(
                            this,
                            "Correcto! El numero era ${game.randomNumber}",
                            Toast.LENGTH_SHORT
                        ).show()
                        game.incrementScore()
                        attemptTextView.text = "Vidas:❤❤❤❤❤"
                        scoreTextView.text = "Puntuación: ${game.score}"
                    } else if (result == GuessResult.INCORRECT || result == GuessResult.INCORRECT_GAME_OVER) {
                        if (game.isGameOver()) {
                            Toast.makeText(this,"Perdiste! El numero era ${game.randomNumber}",Toast.LENGTH_SHORT).show()
                            val highScore = sharedPreferences.getInt("highScore", 0)
                            if (game.score > highScore) {
                                highScoreTextView.text = "Puntuación máxima: ${game.score}"
                                with(sharedPreferences.edit()) {
                                    putInt("highScore", game.score)
                                    apply()
                                }
                            }
                            game.reset()
                            scoreTextView.text = "Puntuación: ${game.score}"
                            attemptTextView.text = "Vidas:❤❤❤❤❤"
                        } else {
                            Toast.makeText(this,"Incorrecto! El numero era ${game.randomNumber}",Toast.LENGTH_SHORT).show()
                            attemptTextView.text = "Vidas:❤❤❤❤❤"
                            for (i in 1..attempts){
                                attemptTextView.text = attemptTextView.text.substring(0, attemptTextView.text.length - 1)
                            }

                        }
                    }
                }else{
                    Toast.makeText(this,"Debe ingresar un numero del 1 al 5",Toast.LENGTH_SHORT).show()
                }
                guessEditText.text.clear()
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
private class Game {
    var randomNumber = 0
    var attempts = 0
    var score = 0
        private set

    fun guess(guess: Int): GuessResult {
        attempts++
        randomNumber = (1..5).random()
        return if(guess == randomNumber) {
            attempts = 0
            GuessResult.CORRECT
        } else if(attempts >= 5){
            GuessResult.INCORRECT_GAME_OVER
        } else {
            GuessResult.INCORRECT
        }
    }

    fun incrementScore() {
        score += 10
    }

    fun isGameOver(): Boolean {
        return attempts >= 5
    }

    fun reset() {
        attempts = 0
        score = 0
    }
}

enum class GuessResult {
    CORRECT, INCORRECT, INCORRECT_GAME_OVER
}