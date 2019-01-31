package com.example.desafio.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import com.example.desafio.R
import com.example.desafio.dao.RetrofitInitializer
import com.example.desafio.extension.afterTextChanged
import com.example.desafio.extension.callback
import kotlinx.android.synthetic.main.activity_cadastro.*

class CadastroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        //Função para escutar sempre que
        testes.afterTextChanged {
            if(testes.length().equals(8)){
                val call = RetrofitInitializer().enderecoService().preencheEndereco(testes.text.toString())
                call.enqueue(callback({ r ->
                    val endereco = r.body()
                    teste.text = endereco!!.logradouro
                }, { t ->
                    Log.e("Erro de retorno", "${t.message}")
                }))
            }
        }
    }

    //Função que sobrescreve o retorno pra activity anterior
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
