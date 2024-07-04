package com.example.securityzone

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible

class BloquearActivity : AppCompatActivity() {
    private lateinit var preferencesManager: PreferencesManager
    private lateinit var statusTextView: TextView
    private lateinit var bloquearButton: Button
    private lateinit var desbloquearButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_bloquear)

        preferencesManager = PreferencesManager(this)
        statusTextView = findViewById(R.id.statusTextView)
        bloquearButton = findViewById(R.id.button2)
        desbloquearButton = findViewById(R.id.button)

        setupButtonClickListeners()
        updateUI()
    }

    private fun setupButtonClickListeners() {
        findViewById<ImageButton>(R.id.backButton).setOnClickListener {
            finish()
        }

        bloquearButton.setOnClickListener {
            showBloquearDialog()
        }

        desbloquearButton.setOnClickListener {
            preferencesManager.isBlocked = false
            updateUI()
        }
    }

    private fun showBloquearDialog() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.dialog_bloquear, null)
        val editText = dialogLayout.findViewById<EditText>(R.id.editText)

        builder.setView(dialogLayout)
            .setTitle("Bloquear")
            .setPositiveButton("Confirmar") { _, _ ->
                val inputText = editText.text.toString()
                if (inputText.isNotEmpty()) {
                    preferencesManager.isBlocked = true
                    // Aquí puedes guardar el inputText si es necesario
                    updateUI()
                }
            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.cancel()
            }

        builder.create().show()
    }

    private fun updateUI() {
        if (preferencesManager.isBlocked) {
            statusTextView.text = "Status: Bloqueado"
            statusTextView.setBackgroundColor(resources.getColor(R.color.red, theme))
            bloquearButton.isVisible = false
            desbloquearButton.isVisible = true
        } else {
            statusTextView.text = "Status: Abierto"
            statusTextView.setBackgroundColor(resources.getColor(R.color.green, theme))
            bloquearButton.isVisible = true
            desbloquearButton.isVisible = false
        }
    }
}