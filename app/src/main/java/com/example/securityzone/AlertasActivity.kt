package com.example.securityzone

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.Timestamp
import com.google.firebase.firestore.Query
import java.text.SimpleDateFormat
import java.util.*

class AlertasActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AlertaAdapter
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    companion object {
        private const val TAG = "AlertasActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alertas)

        recyclerView = findViewById(R.id.alertasRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this).apply {
            reverseLayout = false
            stackFromEnd = false
        }
        adapter = AlertaAdapter()
        recyclerView.adapter = adapter

        fetchAlerts()
        setupButtonClickListeners()
    }

    private fun fetchAlerts() {
        db.collection("alertas")
            .orderBy("hora", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
                val alertas = result.mapNotNull { document ->
                    val texto = document.getString("text") ?: return@mapNotNull null
                    val timestamp = document.getTimestamp("hora") ?: return@mapNotNull null
                    Alerta(texto, timestamp)
                }
                adapter.setAlertas(alertas)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    private fun setupButtonClickListeners() {
        findViewById<ImageButton>(R.id.backButton).setOnClickListener {
            finish()
        }
    }
}

data class Alerta(val texto: String, val timestamp: Timestamp)

class AlertaAdapter : RecyclerView.Adapter<AlertaAdapter.AlertaViewHolder>() {
    private var alertas: List<Alerta> = listOf()

    fun setAlertas(newAlertas: List<Alerta>) {
        alertas = newAlertas
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_alerta, parent, false)
        return AlertaViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlertaViewHolder, position: Int) {
        val alerta = alertas[position]
        holder.bind(alerta)
    }

    override fun getItemCount() = alertas.size

    class AlertaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textoView: TextView = itemView.findViewById(R.id.textoAlerta)
        private val fechaHoraView: TextView = itemView.findViewById(R.id.fechaHoraAlerta)

        fun bind(alerta: Alerta) {
            textoView.text = alerta.texto
            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            fechaHoraView.text = sdf.format(alerta.timestamp.toDate())
        }
    }
}