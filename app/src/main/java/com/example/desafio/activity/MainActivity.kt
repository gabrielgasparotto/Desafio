package com.example.desafio.activity

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import com.example.desafio.R
import com.example.desafio.model.Cliente
import com.example.desafio.model.Endereco
import com.example.desafio.util.ClienteListAdapter

import kotlinx.android.synthetic.main.activity_main.*
import java.math.BigDecimal
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        var clientes: ArrayList<Cliente> = addClientes();

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        recycleCliente.layoutManager = LinearLayoutManager(this)
        recycleCliente.layoutManager = GridLayoutManager(this, 1)
        recycleCliente.adapter = ClienteListAdapter(clientes, this)
    }

    fun addClientes(): ArrayList<Cliente>{

        var endereco = Endereco(cep = "a",
            longradouro = "a",
            bairro = "a",
            estado = "a",
            numero = BigDecimal(49))

        val cliente = Cliente(
            nomeCompleto = "Gabriel Gasparotto Souza Cabral",
            cpf = "462.426.308-18",
            endereco = endereco,
            dataNascimento = Calendar.getInstance()
        )

        var clientes: ArrayList<Cliente> = ArrayList()
        clientes.add(cliente)
        clientes.add(cliente)
        clientes.add(cliente)
        clientes.add(cliente)
        clientes.add(cliente)
        clientes.add(cliente)
        clientes.add(cliente)
        clientes.add(cliente)
        clientes.add(cliente)
        clientes.add(cliente)
        return clientes
    }


}
