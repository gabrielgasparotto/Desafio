package com.example.desafio.model

import com.example.desafio.extension.formataData
import java.util.Calendar

class Cliente (val id: Int = 0,
               val nomeCompleto: String = "Indefinido",
               val cpf: String = "Indefinido",
               val cep: String = "Indefinido",
               val logradouro: String = "Indefinido",
               val bairro: String = "Indefinido",
               val uf: String = "XX",
               val numero: String = "00",
               val dataNascimento: String = Calendar.getInstance().formataData())