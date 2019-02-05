package com.example.desafio.activity

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
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
import com.example.desafio.util.MascaraCpf
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

        //Máscara para CPF
        cpfCadastro.addTextChangedListener(MascaraCpf.mask("###.###.###-##", cpfCadastro))

        //Datepicker data nascimento
        datePickerNascimento()


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

    private fun datePickerNascimento() {
        dataNascimentoCadastro.isFocusable = false
        dataNascimentoCadastro.setOnClickListener({view ->
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                dataNascimentoCadastro.setText("${calendar.formataData()}")
            }, year, month, day)

            dpd.show()
        })
    }

    private fun validaCampos(): Boolean{
        var valida = true
        if(nomeCompletoCadastro.text.isNullOrBlank()){
            nomeCompletoCadastro.requestFocus()
            Toast.makeText(this, "Preencha o nome!", Toast.LENGTH_SHORT).show()
            valida =  false
        }else if(!validaCpf(cpfCadastro.text.toString()) || cpfCadastro.text.isNullOrBlank()){
            cpfCadastro.requestFocus()
            Toast.makeText(this, "Cpf inválido!", Toast.LENGTH_SHORT).show()
            valida = false
        }else if(dataNascimentoCadastro.text.isNullOrBlank()){
            dataNascimentoCadastro.callOnClick()
            Toast.makeText(this, "Selecione data de nascimento!", Toast.LENGTH_SHORT).show()
            valida =  false
        }else if(cepCadastro.text.isNullOrBlank()){
            cepCadastro.requestFocus()
            Toast.makeText(this, "Preencha o cep!", Toast.LENGTH_SHORT).show()
            valida =  false
        }else if(numeroCadastro.text.isNullOrBlank()){
            numeroCadastro.requestFocus()
            Toast.makeText(this, "Preencha o numero!", Toast.LENGTH_SHORT).show()
            valida =  false
        }else if(logradouroCadastro.text.isNullOrBlank()){
            logradouroCadastro.requestFocus()
            Toast.makeText(this, "Preencha o logradouro!", Toast.LENGTH_SHORT).show()
            valida =  false
        }else if(bairroCadastro.text.isNullOrBlank()){
            bairroCadastro.requestFocus()
            Toast.makeText(this, "Preencha o bairro!", Toast.LENGTH_SHORT).show()
            valida =  false
        }else if(ufCadastro.text.isNullOrBlank()){
            ufCadastro.requestFocus()
            Toast.makeText(this, "Preencha a uf!", Toast.LENGTH_SHORT).show()
            valida =  false
        }
        return valida
    }

    private fun adicionaCliente() {
        if(!validaCampos()){

        }else{
            val dbHandler = DatabaseHandler(this)
            val cliente = buscaValoresCampos()
            dbHandler.addCliente(cliente)
            startActivity(Intent(this, MainActivity::class.java))
            Toast.makeText(this, "Cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
        }
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
        if(!validaCampos()){

        }else {
            val dbHandler = DatabaseHandler(this)
            val intent = intent
            val cliente = buscaValoresCampos()
            dbHandler.updateCliente(cliente, intent.getStringExtra("id"))
            startActivity(Intent(this, MainActivity::class.java))
            Toast.makeText(this, "Atualizado com sucesso!", Toast.LENGTH_SHORT).show()
        }
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
            dataNascimento = dataNascimentoCadastro.text.toString()
        )
        return cliente
    }

    fun validaCpf(cpf : String) : Boolean{
        val cpfClean = cpf.replace(".", "").replace("-", "")

        //## check if size is eleven
        if (cpfClean.length != 11)
            return false

        //## check if is number
        try {
            val number  = cpfClean.toLong()
        }catch (e : Exception){
            return false
        }

        //continue
        var dvCurrent10 = cpfClean.substring(9,10).toInt()
        var dvCurrent11= cpfClean.substring(10,11).toInt()

        //the sum of the nine first digits determines the tenth digit
        val cpfNineFirst = IntArray(9)
        var i = 9
        while (i > 0 ) {
            cpfNineFirst[i-1] = cpfClean.substring(i-1, i).toInt()
            i--
        }
        //multiple the nine digits for your weights: 10,9..2
        var sumProductNine = IntArray(9)
        var weight = 10
        var position = 0
        while (weight >= 2){
            sumProductNine[position] = weight * cpfNineFirst[position]
            weight--
            position++
        }
        //Verify the nineth digit
        var dvForTenthDigit = sumProductNine.sum() % 11
        dvForTenthDigit = 11 - dvForTenthDigit //rule for tenth digit
        if(dvForTenthDigit > 9)
            dvForTenthDigit = 0
        if (dvForTenthDigit != dvCurrent10)
            return false

        //### verify tenth digit
        var cpfTenFirst = cpfNineFirst.copyOf(10)
        cpfTenFirst[9] = dvCurrent10
        //multiple the nine digits for your weights: 10,9..2
        var sumProductTen = IntArray(10)
        var w = 11
        var p = 0
        while (w >= 2){
            sumProductTen[p] = w * cpfTenFirst[p]
            w--
            p++
        }
        //Verify the nineth digit
        var dvForeleventhDigit = sumProductTen.sum() % 11
        dvForeleventhDigit = 11 - dvForeleventhDigit //rule for tenth digit
        if(dvForeleventhDigit > 9)
            dvForeleventhDigit = 0
        if (dvForeleventhDigit != dvCurrent11)
            return false

        return true
    }
}
