package com.example.securityzone

import android.content.ContentValues.TAG
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.ScrollView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class AlertasActivity : AppCompatActivity() {
    private lateinit var textViewAlerta1: TextView
    private lateinit var textViewAlerta2: TextView
    private lateinit var textViewAlerta3: TextView
    private lateinit var timeViewAlerta1: TextView
    private lateinit var timeViewAlerta2: TextView
    private lateinit var timeViewAlerta3: TextView

    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_alertas)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        fetchAlerts()
        setupButtonClickListeners()
    }

    private fun fetchAlerts() {


        textViewAlerta1 = findViewById(R.id.textViewAlerta1)
        textViewAlerta2 = findViewById(R.id.textViewAlerta2)
        textViewAlerta3 = findViewById(R.id.textViewAlerta3)

        timeViewAlerta1 = findViewById(R.id.timeView1)
        timeViewAlerta2 = findViewById(R.id.timeView2)
        timeViewAlerta3 = findViewById(R.id.timeView3)

        /*
                val calendar = Calendar.getInstance()
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                val startOfDay = calendar.timeInMillis
                val endOfDay = startOfDay + 24 * 60 * 60 * 1000

                val container = findViewById<ConstraintLayout>(R.id.container) ?: return
                */

        db.collection("alertas")
            .get()
            .addOnSuccessListener { result ->
                //val stringBuilder = StringBuilder()
                val textosArray = mutableListOf<String>()
                val fechasArray = mutableListOf<Date>()
                for (document in result) {
                    val campoTexto = document.getString("texto")
                    val campoFecha = document.getDate("hora")
                    campoTexto?.let {
                        textosArray.add(it)
                    }
                    campoFecha?.let {
                        fechasArray.add(it)
                    }
                }
                //textViewAlerta1.text = stringBuilder.toString()

                textViewAlerta1.text = textosArray[0]
                textViewAlerta2.text = textosArray[1]
                textViewAlerta3.text = textosArray[2]

                if(fechasArray.isNotEmpty()) {
                    val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault()).apply {
                        timeZone = TimeZone.getTimeZone("GMT-6")
                    }

                    timeViewAlerta1.text = sdf.format(fechasArray[0]).toString()
                    timeViewAlerta2.text = sdf.format(fechasArray[1]).toString()
                    timeViewAlerta3.text = sdf.format(fechasArray[2]).toString()
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
                textViewAlerta1.text = "Error obteniendo documentos: ${exception.message}"
            }
    }

    private fun setupButtonClickListeners() {
        findViewById<ImageButton>(R.id.backButton).setOnClickListener {
            finish()
        }
    }
}