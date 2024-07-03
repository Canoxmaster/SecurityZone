package com.example.securityzone

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.ToggleButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var preferencesManager: PreferencesManager
    private lateinit var statusTextView: TextView
    private var spanish = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        preferencesManager = PreferencesManager(this)
        statusTextView = findViewById(R.id.textView2)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Set up click listeners for each button
        setupButtonClickListeners()

        // Set up the toggle for the country
        setupCountryToggle()

        // Update the status TextView
        updateStatusTextView()
    }

    private fun setupButtonClickListeners() {
        findViewById<ImageButton>(R.id.mensajeBtn).setOnClickListener {
            startActivity(Intent(this, MsjMotivoActivity::class.java))
        }

        findViewById<ImageButton>(R.id.bloquearBtn).setOnClickListener {
            startActivity(Intent(this, BloquearActivity::class.java))
        }

        findViewById<ImageButton>(R.id.alertaBtn).setOnClickListener {
            startActivity(Intent(this, AlertasActivity::class.java))
        }

        findViewById<ImageButton>(R.id.informeBtn).setOnClickListener {
            startActivity(Intent(this, InformeActivity::class.java))
        }
    }

    private fun updateStatusTextView() {
        if (preferencesManager.isBlocked) {
            statusTextView.text = "Status: Bloqueado"
            statusTextView.setBackgroundColor(resources.getColor(R.color.red, theme))
        } else {
            statusTextView.text = "Status: Abierto"
            statusTextView.setBackgroundColor(resources.getColor(R.color.green, theme))
        }
    }

    private fun setupCountryToggle() {
        val toggleButton = findViewById<ToggleButton>(R.id.toggleIdioma)

        // Set the initial state
        updateToggleButtonBackground(toggleButton, spanish)

        toggleButton.setOnClickListener {
            spanish = !spanish
            updateToggleButtonBackground(toggleButton, spanish)
        }
    }

    private fun updateToggleButtonBackground(button: ToggleButton, isSpanish: Boolean) {
        if (isSpanish) {
            button.setBackgroundResource(R.drawable.espana)
        } else {
            button.setBackgroundResource(R.drawable.usa)
        }
    }

    override fun onResume() {
        super.onResume()
        updateStatusTextView()
    }
}