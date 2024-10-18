package com.example.ulgan1

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ageInput = findViewById<EditText>(R.id.ageInput)
        val predictButton = findViewById<Button>(R.id.predictButton)
        val resultTextView = findViewById<TextView>(R.id.resultTextView)

        val predictor = MaxHRPredictor(this)

        predictButton.setOnClickListener {
            val age = ageInput.text.toString().toFloatOrNull()
            if (age != null) {
                val predictedMaxHR = predictor.predictMaxHR(age)
                resultTextView.text = "Predicted MaxHR: $predictedMaxHR"
            } else {
                resultTextView.text = "Please enter a valid age."
            }
        }
    }
}

