package com.example.masacorporal

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        // Obtener los datos pasados desde MainActivity
        val imc = intent.getDoubleExtra("IMC_RESULT", 0.0)
        val resultadoText = intent.getStringExtra("IMC_TEXT") ?: ""
        val colorResultado = intent.getIntExtra("IMC_COLOR", R.color.peso_normal)

        // Configurar las vistas
        val tvImc = findViewById<TextView>(R.id.tvIMC)
        val tvResultado = findViewById<TextView>(R.id.tvResultado)
        val tvDescripcion = findViewById<TextView>(R.id.tvDescripcion)
        val btnRecalcular = findViewById<Button>(R.id.btnRecalcular)

        // Mostrar los resultados
        tvImc.text = String.format("%.2f", imc)
        tvResultado.text = resultadoText
        tvResultado.setTextColor(ContextCompat.getColor(this, colorResultado))

        // Configurar la descripción según el resultado
        val descripcion = when {
            imc < 18.5 -> "Estas por debajo de lo óptimo para tu peso y altura."
            imc in 18.5..24.9 -> "¡Felicitaciones! Estás en tu peso ideal."
            imc in 25.0..29.9 -> "Estás ligeramente por encima de tu peso ideal."
            else -> "Estás muy por encima de tu peso ideal."
        }
        tvDescripcion.text = descripcion

        // Configurar el botón de recalcular
        btnRecalcular.setOnClickListener {
            finish()
        }
    }
}