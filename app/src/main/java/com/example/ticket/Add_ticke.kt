package com.example.ticket

import android.R
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.ticket.databinding.ActivityAddTickeBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class Add_ticke : AppCompatActivity() {
    private lateinit var binding: ActivityAddTickeBinding
    val dataInsertar = HashMap<String, Any>()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTickeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.setOnClickListener {
            insertar()
        }
        val equipoArray = arrayOf("Selecciona el equipo a enviar","Soporte","Desarrollo","Atencion a clientes")
        val incidenciaArray = arrayOf("Selecciona cual es el problema","Bug","Feature")
        val gravedadArray = arrayOf("Selecciona que prioridad tiene","High","Medium","Low")
        val adapterEquipo: ArrayAdapter<String> =
            ArrayAdapter<String>(this, R.layout.simple_spinner_item, equipoArray)
        adapterEquipo.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)

        val spinerEquipo = binding.spinnerEquipo
        spinerEquipo.adapter = adapterEquipo
        spinerEquipo.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?,
                position: Int, id: Long
            ) {
                dataInsertar["Equipo Responsable"] = equipoArray[position]
            }

            override fun onNothingSelected(arg0: AdapterView<*>?) {}
        })

        val adapterIncidencia: ArrayAdapter<String> =
            ArrayAdapter<String>(this, R.layout.simple_spinner_item, incidenciaArray)
        adapterIncidencia.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)

        val spinerIncidencia = binding.spinnerIncidencia
        spinerIncidencia.adapter = adapterIncidencia
        spinerIncidencia.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?,
                position: Int, id: Long
            ) {
                dataInsertar["Tipo Incidencia"] = incidenciaArray[position]
            }

            override fun onNothingSelected(arg0: AdapterView<*>?) {}
        })

        val adapterGravedad: ArrayAdapter<String> =
            ArrayAdapter<String>(this, R.layout.simple_spinner_item, gravedadArray)
        adapterGravedad.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        val spinerGravedad = binding.spinnerGravedad
        spinerGravedad.adapter = adapterGravedad
        spinerGravedad.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?,
                position: Int, id: Long
            ) {
                dataInsertar["Gravedad Incidencia"] = gravedadArray[position]
            }

            override fun onNothingSelected(arg0: AdapterView<*>?) {}
        })

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun insertar(){
        val db = FirebaseFirestore.getInstance()
        dataInsertar["Titulo"] = binding.etTitulo.text.toString()
        dataInsertar["Fecha"] = DateTimeFormatter.ofPattern("dd/MMM/yy HH:mm:ss")
            .format(LocalDateTime.now())
        dataInsertar["Nombre Responsable"] = binding.etNombre.text.toString()
        dataInsertar["Version Software"] = binding.etSoftware.text.toString()
        dataInsertar["Descripcion"] = binding.etDescripcion.text.toString()
        dataInsertar["Archivos"] = binding.etArchivos.text.toString()
        dataInsertar["Estatus"]= "Nuevo"
        db.collection("Tickets").add(dataInsertar).addOnSuccessListener { documentReference ->
            println("Registro exitoso")
        }
            .addOnFailureListener{
                    e ->
            }
    }
}