package com.example.masacorporal

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.android.material.slider.Slider
import androidx.core.content.ContextCompat
import android.content.Intent

class MainActivity : AppCompatActivity() {

    private var selectedGender: String = "Masculino" // Por defecto
    private var alturaValue: Int = 120 // Valor inicial de altura
    private var pesoValue: Int = 60   // Valor inicial de peso
    private var edadValue: Int = 30   // Valor inicial de edad

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencias a las vistas del XML
        val viewMasculino = findViewById<CardView>(R.id.viewMasculino)
        val viewFemenino = findViewById<CardView>(R.id.viewFemenino)
        val alturaSlider = findViewById<Slider>(R.id.rsAltura)
        val alturaTextView = findViewById<TextView>(R.id.altura)
        val pesoTextView = findViewById<TextView>(R.id.pesoValue)
        val edadTextView = findViewById<TextView>(R.id.edadValue)
        val btnDecreasePeso = findViewById<Button>(R.id.btnDecreasePeso)
        val btnIncreasePeso = findViewById<Button>(R.id.btnIncreasePeso)
        val btnDecreaseEdad = findViewById<Button>(R.id.btnDecreaseEdad)
        val btnIncreaseEdad = findViewById<Button>(R.id.btnIncreaseEdad)
        val btnCalcular = findViewById<Button>(R.id.btnCalcular)

        // Selección de género
        viewMasculino.setOnClickListener {
            selectedGender = "Masculino"
            viewMasculino.setCardBackgroundColor(
                ContextCompat.getColor(this, R.color.background_component_seleted)
            )
            viewFemenino.setCardBackgroundColor(
                ContextCompat.getColor(this, R.color.background_component)
            )
        }

        viewFemenino.setOnClickListener {
            selectedGender = "Femenino"
            viewFemenino.setCardBackgroundColor(
                ContextCompat.getColor(this, R.color.background_component_seleted)
            )
            viewMasculino.setCardBackgroundColor(
                ContextCompat.getColor(this, R.color.background_component)
            )
        }

        // Ajuste de altura con el Slider
        alturaSlider.addOnChangeListener { _, value, _ ->
            alturaValue = value.toInt()
            alturaTextView.text = "$alturaValue cm"
        }

        // Incremento y decremento de peso
        btnIncreasePeso.setOnClickListener {
            pesoValue++
            pesoTextView.text = pesoValue.toString()
        }

        btnDecreasePeso.setOnClickListener {
            if (pesoValue > 0) {
                pesoValue--
                pesoTextView.text = pesoValue.toString()
            }
        }

        // Incremento y decremento de edad
        btnIncreaseEdad.setOnClickListener {
            edadValue++
            edadTextView.text = edadValue.toString()
        }

        btnDecreaseEdad.setOnClickListener {
            if (edadValue > 0) {
                edadValue--
                edadTextView.text = edadValue.toString()
            }
        }

        // Lógica para calcular el IMC
        btnCalcular.setOnClickListener {
            if (pesoValue > 0 && alturaValue > 0) {
                val alturaMetros = alturaValue / 100.0
                val imc = pesoValue / (alturaMetros * alturaMetros)
                mostrarResultado(imc)
            } else {
                Toast.makeText(this, "Ingrese valores válidos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun mostrarResultado(imc: Double) {
        val resultadoText: String
        val colorResultado: Int

        when {
            imc < 18.5 -> {
                resultadoText = "Bajo Peso"
                colorResultado = R.color.peso_bajo
            }
            imc in 18.5..24.9 -> {
                resultadoText = "Peso Normal"
                colorResultado = R.color.peso_normal
            }
            imc in 25.0..29.9 -> {
                resultadoText = "Sobrepeso"
                colorResultado = R.color.peso_sobrepeso
            }
            else -> {
                resultadoText = "Obesidad"
                colorResultado = R.color.obesidad
            }
        }

        val intent = Intent(this, ResultActivity::class.java).apply {
            putExtra("IMC_RESULT", imc)
            putExtra("IMC_TEXT", resultadoText)
            putExtra("IMC_COLOR", colorResultado)
        }
        startActivity(intent)
    }
}
