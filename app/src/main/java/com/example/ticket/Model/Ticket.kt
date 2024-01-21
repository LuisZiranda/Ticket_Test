package com.example.ticket.Model

data class Ticket (
    var id: String?,
    val titulo: String?,
    val nombre: String?,
    val equipo: String?,
    val incidencia: String?,
    val gravedad: String?,
    val software_version: String?,
    val descripcion: String?,
    val files: String?,
    val estatus: String?,
    val fecha: String?
)