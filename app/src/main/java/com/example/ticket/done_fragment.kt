package com.example.ticket

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ticket.Model.Ticket
import com.google.firebase.firestore.FirebaseFirestore

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [done_fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class done_fragment : Fragment() {
    private val db= FirebaseFirestore.getInstance()
    private val coleccion = db.collection("Tickets")
    private var emptyList:ArrayList<Ticket> = ArrayList<Ticket>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_done_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        coleccion.get().addOnSuccessListener { querySnapshot ->
            for (document in querySnapshot){
                val titulo = document.getString("Titulo")
                val nombre = document.getString("Nombre Responsable")
                val fecha = document.getString("Fecha")
                val equipo = document.getString("Equipo Responsable")
                val tipo_incidencia = document.getString("Tipo Incidencia")
                val gravedad_incidencia = document.getString("Gravedad Incidencia")
                val version_software = document.getString("Version Software")
                val descripcion = document.getString("Descripcion")
                val archivos = document.getString("Archivos")
                val estatus = document.getString("Estatus")
                val ID = document.id
                if (estatus == "Atendido"){
                    val modelo = Ticket(ID,titulo,nombre,equipo,tipo_incidencia,gravedad_incidencia,
                        version_software,descripcion,archivos,estatus,fecha)
                    emptyList.add(modelo)
                }
            }
            val itemAdapter = Adaptador_Ticket(emptyList){ ticket ->
                val fragmentManager = requireActivity().supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(
                    ((view as ViewGroup).parent as View).id,
                    UpdateTicket(ticket.id.toString()),
                )
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
            val recyclerView: RecyclerView =view.findViewById(R.id.recyclerView_done)
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = itemAdapter
        }
        emptyList.clear()
    }
}