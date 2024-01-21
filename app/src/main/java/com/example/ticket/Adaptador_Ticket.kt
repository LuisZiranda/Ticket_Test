package com.example.ticket

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.ticket.Model.Ticket
import com.example.ticket.databinding.ItemTicketLayoutBinding

class Adaptador_Ticket(private val emplist: ArrayList<Ticket>,
                       val itemClickListener:(Ticket)->Unit
                        ) : RecyclerView.Adapter<Adaptador_Ticket.ViewHolder>() {

    //lleva el control de los elementos de la vista
    inner class ViewHolder(val binding: ItemTicketLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(get: Ticket) = with(binding){
            tvName.text = get.titulo
            tvIncidencia.text = get.incidencia
            tvGravedad.text = get.gravedad
            root.setOnClickListener {
                itemClickListener(get)
            }
        }
    }

    //vista
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTicketLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(emplist.get(position))
    }

    //posicion
    override fun getItemCount(): Int {
        return emplist.size
    }
}