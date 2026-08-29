package com.example.dcsg1_practical9.data

import kotlinx.serialization.Serializable
import java.io.Serial

@Serializable
data class UserInput(
    val name: String,
    val email: String
)
