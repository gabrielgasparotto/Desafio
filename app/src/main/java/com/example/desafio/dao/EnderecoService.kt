package com.example.desafio.dao

import com.example.desafio.model.Endereco
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface EnderecoService {

    @GET("{cep}/json/")
    fun preencheEndereco(@Path("cep")cep: String ) : Call<Endereco>

}