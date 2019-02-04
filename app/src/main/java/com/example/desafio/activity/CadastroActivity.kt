package com.example.desafio.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.desafio.R
import com.example.desafio.dao.RetrofitInitializer
import com.example.desafio.extension.afterTextChanged
import com.example.desafio.extension.callback
import com.example.desafio.extension.formataData
import com.example.desafio.model.Cliente
import com.example.desafio.util.DatabaseHandler
import kotlinx.android.synthetic.main.activity_cadastro.*
import java.util.*

class CadastroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        //Preenche os campos com os dados do cliente selecionado caso seja alteração
        preencheAlteracao()

        //Função para escutar sempre que o texto for alterado
        populaEndereco()

        buttonCadastro.setOnClickListener {
            //Função para adicionar cliente com o click do botão
            val intent = intent
            if(intent.hasExtra("nomeCompleto")){
                alteraCliente()
            }else{
                adicionaCliente()
            }

        }
    }

    private fun adicionaCliente() {
        val dbHandler = DatabaseHandler(this)
        val cliente = buscaValoresCampos()
        dbHandler.addCliente(cliente)
        startActivity(Intent(this, MainActivity::class.java))
        Toast.makeText(this, "Cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
    }



    private fun preencheAlteracao() {
        val intent = intent
        if (intent.hasExtra("nomeCompleto")) {
            nomeCompletoCadastro.setText(intent.getStringExtra("nomeCompleto"))
            cpfCadastro.setText(intent.getStringExtra("cpf"))
            dataNascimentoCadastro.setText(intent.getStringExtra("dataNascimento"))
            cepCadastro.setText(intent.getStringExtra("cep"))
            logradouroCadastro.setText(intent.getStringExtra("logradouro"))
            bairroCadastro.setText(intent.getStringExtra("bairro"))
            ufCadastro.setText(intent.getStringExtra("uf"))
            numeroCadastro.setText(intent.getStringExtra("numero"))
        }
    }

    private fun populaEndereco() {
        cepCadastro.afterTextChanged {
            if (cepCadastro.length().equals(8)) {
                val call = RetrofitInitializer().enderecoService().preencheEndereco(cepCadastro.text.toString())
                call.enqueue(callback({ r ->
                    val endereco = r.body()
                    if (endereco != null) {
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

    private fun alteraCliente(){
        val dbHandler = DatabaseHandler(this)
        val intent = intent
        val cliente = buscaValoresCampos()
        dbHandler.updateCliente(cliente, intent.getStringExtra("id"))
        startActivity(Intent(this, MainActivity::class.java))
        Toast.makeText(this, "Atualizado com sucesso!", Toast.LENGTH_SHORT).show()
    }

    //Função que sobrescreve o retorno pra activity anterior
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun buscaValoresCampos(): Cliente {
        val cliente = Cliente(
            nomeCompleto = nomeCompletoCadastro.text.toString(),
            cpf = cpfCadastro.text.toString(),
            cep = cepCadastro.text.toString(),
            logradouro = logradouroCadastro.text.toString(),
            bairro = bairroCadastro.text.toString(),
            uf = ufCadastro.text.toString(),
            numero = numeroCadastro.text.toString(),
            //Mudar para datepicker !!
            dataNascimento = Calendar.getInstance().formataData()
        )
        return cliente
    }
}
