package com.example.desafio.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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

        //Função para escutar sempre que o texto for alterado
        cepCadastro.afterTextChanged {
            if(cepCadastro.length().equals(8)){
                val call = RetrofitInitializer().enderecoService().preencheEndereco(cepCadastro.text.toString())
                call.enqueue(callback({ r ->
                    val endereco = r.body()
                    if(endereco != null){
                        numeroCadastro.requestFocus()
                        logradouroCadastro.setText(endereco.logradouro)
                        logradouroCadastro.isEnabled = false
                        bairroCadastro.setText(endereco.bairro)
                        bairroCadastro.isEnabled = false
                        ufCadastro.setText(endereco.uf)
                        ufCadastro.isEnabled = false
                    }

                }, { t ->
                    Log.e("Erro de retorno", "${t.message}")
                    Toast.makeText(this, "Cep inválido", Toast.LENGTH_SHORT).show()
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
