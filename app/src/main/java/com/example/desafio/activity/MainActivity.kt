package com.example.desafio.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import com.example.desafio.R
import com.example.desafio.extension.formataData
import com.example.desafio.model.Cliente
import com.example.desafio.util.ClienteListAdapter
import com.example.desafio.util.DatabaseHandler

import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val dbHandler = DatabaseHandler(this)
        val clientes: ArrayList<Cliente> = dbHandler.listCliente();

        fab.setOnClickListener {_->
            startActivity(Intent(this, CadastroActivity::class.java))
        }

        recycleCliente.layoutManager = LinearLayoutManager(this)
        recycleCliente.layoutManager = GridLayoutManager(this, 1)
        recycleCliente.adapter = ClienteListAdapter(clientes, this)
    }
}
