package com.example.ticket.Model

data class Ticket (
    var id: String?,
    val title: String,
    val name: String,
    val team: String?,
    val type: String,
    val gravety: String,
    val software_version: String?,
    val descrition: String,
    val files: String?
)