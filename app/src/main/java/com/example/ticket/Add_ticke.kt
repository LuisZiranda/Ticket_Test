package com.example.ticket

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.ticket.databinding.ActivityAddTickeBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class Add_ticke : AppCompatActivity() {
    private lateinit var binding: ActivityAddTickeBinding
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTickeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.setOnClickListener {
            insertar()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun insertar(){
        val db = FirebaseFirestore.getInstance()
        val status = "Nuevo"
        val data = hashMapOf(
            "Titulo" to binding.etTitulo.text.toString(),
            "Fecha" to DateTimeFormatter.ofPattern("dd/MMM/yy HH:mm:ss")
                .format(LocalDateTime.now()),
            "Nombre Responsable" to binding.etNombre.text.toString(),
            "Equipo Responsable" to binding.etEquipo.text.toString(),
            "Tipo Incidencia" to binding.etIncidencia.text.toString(),
            "Gravedad Incidencia" to binding.etGravedad.text.toString(),
            "Version Software" to binding.etSoftware.text.toString(),
            "Descripcion" to binding.etDescripcion.text.toString(),
            "Archivos" to binding.etArchivos.text.toString(),
            "Estatus" to status
        )
        db.collection("Tickets").add(data).addOnSuccessListener { documentReference ->
            println("Registro exitoso")
        }
            .addOnFailureListener{
                    e ->
            }
    }
}