package com.example.apm_actividad2

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class GameActivity : AppCompatActivity() {
    private lateinit var guessEditText: EditText
    private lateinit var scoreTextView: TextView
    private lateinit var highScoreTextView: TextView

    private lateinit var game: Game
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        guessEditText = findViewById(R.id.guessEditText)
        scoreTextView = findViewById(R.id.scoreTextView)
        highScoreTextView = findViewById(R.id.highScoreTextView)

        sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)

        val highScore = sharedPreferences.getInt("highScore", 0)
        highScoreTextView.text = "Puntuación máxima: $highScore"

        game = Game()

        val submitButton: Button = findViewById(R.id.submitButton)
        submitButton.setOnClickListener {
            val guess = guessEditText.text.toString().toIntOrNull()
            if (guess != null) {
                if(guess <= 5) {
                    val result = game.guess(guess)
                    if (result == GuessResult.CORRECT) {
                        Toast.makeText(
                            this,
                            "Correcto! El numero era ${game.randomNumber}",
                            Toast.LENGTH_SHORT
                        ).show()
                        game.incrementScore()
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
                        } else {
                            Toast.makeText(this,"Incorrecto! El numero era ${game.randomNumber}",Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    Toast.makeText(this,"Debe ingresar un numero del 1 al 5",Toast.LENGTH_SHORT).show()
                }
                guessEditText.text.clear()
            }
        }
    }
}
private class Game {
    var randomNumber = 0
    private var attempts = 0
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