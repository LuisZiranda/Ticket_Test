package com.example.ticket

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.ticket.Model.Ticket

class Adaptador_Ticket(private val emplist: ArrayList<Ticket>) : RecyclerView.Adapter<Adaptador_Ticket.ViewHolder>() {

    interface OnClickListener{
        fun onTicketClick()
    }

    //lleva el control de los elementos de la vista
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvNombre: TextView = itemView.findViewById(R.id.tvName)
        val tvIncidencia: TextView = itemView.findViewById(R.id.tvIncidencia)
        val tvGravedad: TextView = itemView.findViewById(R.id.tvGravedad)
        val cardItem: CardView = itemView.findViewById(R.id.cardItem)

    }

    //vista
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ticket_layout, parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = emplist[position]
        holder.tvNombre.text = item.titulo
        holder.tvIncidencia.text = item.incidencia
        holder.tvGravedad.text = item.gravedad
    }

    //posicion
    override fun getItemCount(): Int {
        return emplist.size
    }
}