package com.example.securityzone

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Set up click listeners for each button
        setupButtonClickListeners()
    }

    private fun setupButtonClickListeners() {
        findViewById<ImageButton>(R.id.mensajeBtn).setOnClickListener {
            startActivity(Intent(this, MensajeActivity::class.java))
        }

        findViewById<ImageButton>(R.id.bloquearBtn).setOnClickListener {
            startActivity(Intent(this, BloquearActivity::class.java))
        }

        findViewById<ImageButton>(R.id.activityBtn).setOnClickListener {
            startActivity(Intent(this, MotivoActivity::class.java))
        }

        findViewById<ImageButton>(R.id.informeBtn).setOnClickListener {
            startActivity(Intent(this, InformeActivity::class.java))
        }
    }
}