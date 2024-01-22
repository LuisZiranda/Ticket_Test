package com.example.ticket

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.ticket.Model.Ticket
import com.example.ticket.databinding.FragmentUpdateTicketBinding
import com.google.firebase.firestore.FirebaseFirestore

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UpdateTicket.newInstance] factory method to
 * create an instance of this fragment.
 */
class UpdateTicket(idValue: String) : Fragment() {
    private val db= FirebaseFirestore.getInstance()
    private val coleccion = db.collection("Tickets")
    private val idSearch = idValue
    private var ticketActual:Ticket? = null
    private lateinit var binding: FragmentUpdateTicketBinding
    val data2 = HashMap<String, Any>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpdateTicketBinding.inflate(inflater, container, false)
        val equipoArray = arrayOf("Soporte","Desarrollo","Atencion a clientes")
        val incidenciaArray = arrayOf("Bug","Feature")
        val gravedadArray = arrayOf("High","Medium","Low")
        val estatusArray = arrayOf("Nuevo","Atendido","Proceso","Archivado")
        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(activity?.applicationContext!!, android.R.layout.simple_spinner_item, estatusArray)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val spinner = binding.spinner
        spinner.adapter = adapter
        spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?,
                position: Int, id: Long
            ) {
                data2["Estatus"] = estatusArray[position]
            }

            override fun onNothingSelected(arg0: AdapterView<*>?) {}
        })

        val adapterEquipo: ArrayAdapter<String> =
            ArrayAdapter<String>(activity?.applicationContext!!, android.R.layout.simple_spinner_item, equipoArray)
        adapterEquipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val spinnerEquipo = binding.spinnerEquipo
        spinnerEquipo.adapter = adapterEquipo
        spinnerEquipo.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?,
                position: Int, id: Long
            ) {
                data2["Equipo Responsable"] = equipoArray[position]
            }

            override fun onNothingSelected(arg0: AdapterView<*>?) {}
        })

        val adapterIncidencia: ArrayAdapter<String> =
            ArrayAdapter<String>(activity?.applicationContext!!, android.R.layout.simple_spinner_item, incidenciaArray)
        adapterIncidencia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)


        val spinnerIncidencia = binding.spinnerTipoIncidencia
        spinnerIncidencia.adapter = adapterIncidencia
        spinnerIncidencia.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?,
                position: Int, id: Long
            ) {
                data2["Tipo Incidencia"] = incidenciaArray[position]
            }

            override fun onNothingSelected(arg0: AdapterView<*>?) {}
        })

        val adapterGravedad: ArrayAdapter<String> =
            ArrayAdapter<String>(activity?.applicationContext!!, android.R.layout.simple_spinner_item, gravedadArray)
        adapterGravedad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)


        val spinnerGravedad = binding.spinnerGravedad
        spinnerGravedad.adapter = adapterGravedad
        spinnerGravedad.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?,
                position: Int, id: Long
            ) {
                data2["Gravedad Incidencia"] = gravedadArray[position]
            }

            override fun onNothingSelected(arg0: AdapterView<*>?) {}
        })

        binding.button.setOnClickListener {
            updateFunction()
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        coleccion.document(idSearch).get().addOnSuccessListener { ticket ->
            ticketActual = Ticket(
                idSearch,ticket.getString("Titulo"),ticket.getString("Nombre Responsable"),
                ticket.getString("Equipo Responsable"),ticket.getString("Tipo Incidencia"),
                ticket.getString("Gravedad Incidencia"), ticket.getString("Version Software"),
                ticket.getString("Descripcion"),ticket.getString("Archivos"),ticket.getString("Estatus"),
                ticket.getString("Fecha"))
            if (ticket != null) {
                binding.etTitulo.setText(ticket.getString("Titulo"))
                binding.etNombre.setText(ticket.getString("Nombre Responsable"))
                when(ticket.getString("Equipo Responsable")){
                    "Soporte" ->  binding.spinnerEquipo.setSelection(0)
                    "Desarrollo" -> binding.spinnerEquipo.setSelection(1)
                     "Atencion a clientes" -> binding.spinnerEquipo.setSelection(2)
                }
                when(ticket.getString("Tipo Incidencia")){
                    "Bug" ->  binding.spinnerTipoIncidencia.setSelection(0)
                    "Feature" -> binding.spinnerTipoIncidencia.setSelection(1)
                }
                when(ticket.getString("Gravedad Incidencia")){
                    "High" ->  binding.spinnerGravedad.setSelection(0)
                    "Medium" -> binding.spinnerGravedad.setSelection(1)
                    "Low" -> binding.spinnerGravedad.setSelection(2)
                }
                binding.etSoftware.setText(ticket.getString("Version Software"))
                binding.etDescripcion.setText(ticket.getString("Descripcion"))
                binding.etArchivos.setText(ticket.getString("Archivos"))
                when(ticket.getString("Estatus")){
                    "Nuevo" ->  binding.spinner.setSelection(0)
                    "Atendido" -> binding.spinner.setSelection(1)
                    "Proceso" -> binding.spinner.setSelection(2)
                    "Archivado" -> binding.spinner.setSelection(3)
                }
            }else{
                println("Ticket no found")
            }
        }
            .addOnFailureListener{exception ->
                println(exception)
            }
    }

    private fun updateFunction(){
        val db = FirebaseFirestore.getInstance()
        data2["Titulo"]= binding.etTitulo.text.toString()
        data2["Fecha"]=ticketActual?.fecha.toString()
        data2["Nombre Responsable"]=binding.etNombre.text.toString()
        data2["Version Software"]=binding.etSoftware.text.toString()
        data2["Descripcion"]=binding.etDescripcion.text.toString()
        data2["Archivos"]=binding.etArchivos.text.toString()
        db.collection("Tickets").document(idSearch).update(data2).addOnSuccessListener {
            println("Update exitoso")
        }
            .addOnFailureListener{
                    e ->
            }
    }

}