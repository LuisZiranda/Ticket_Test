package com.example.ticket

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ticket.Model.Ticket
import com.example.ticket.databinding.ActivityAddTickeBinding
import com.example.ticket.databinding.FragmentUpdateTicketBinding
import com.google.firebase.database.database
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentUpdateTicketBinding.inflate(inflater,container,false)
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
                binding.etEquipo.setText(ticket.getString("Equipo Responsable"))
                binding.etIncidencia.setText(ticket.getString("Tipo Incidencia"))
                binding.etGravedad.setText(ticket.getString("Gravedad Incidencia"))
                binding.etSoftware.setText(ticket.getString("Version Software"))
                binding.etDescripcion.setText(ticket.getString("Descripcion"))
                binding.etArchivos.setText(ticket.getString("Archivos"))
                binding.etEstatus.setText(ticket.getString("Estatus"))
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
        val data2 = HashMap<String, Any>()
        data2["Titulo"]= binding.etTitulo.text.toString()
        data2["Fecha"]=ticketActual?.fecha.toString()
        data2["Nombre Responsable"]=binding.etNombre.text.toString()
        data2["Equipo Responsable"]=binding.etEquipo.text.toString()
        data2["Tipo Incidencia"]=binding.etIncidencia.text.toString()
        data2["Gravedad Incidencia"]=binding.etGravedad.text.toString()
        data2["Version Software"]=binding.etSoftware.text.toString()
        data2["Descripcion"]=binding.etDescripcion.text.toString()
        data2["Archivos"]=binding.etArchivos.text.toString()
        data2["Estatus"]=binding.etEstatus.text.toString()
        db.collection("Tickets").document(idSearch).update(data2).addOnSuccessListener {
            println("Update exitoso")
        }
            .addOnFailureListener{
                    e ->
            }
    }

}