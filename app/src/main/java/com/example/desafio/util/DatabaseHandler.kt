package com.example.desafio.util

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.desafio.model.Cliente
import java.util.*

class DatabaseHandler(val context: Context): SQLiteOpenHelper(context, DatabaseHandler.DB_NAME, null, DatabaseHandler.DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_CLIENTES_TABLE = ("CREATE TABLE "+
                "${TABLE_CLIENTES} ("+
                "${ID} INTEGER PRIMARY KEY,"+
                "${NOME_COMPLETO} TEXT,"+
                "${CPF} TEXT,"+
                "${CEP} TEXT,"+
                "${LOGRADOURO} TEXT,"+
                "${BAIRRO} TEXT,"+
                "${UF} TEXT,"+
                "${NUMERO} TEXT,"+
                "${DATA_NASCIMENTO} TEXT)"
                )

        db.execSQL(CREATE_CLIENTES_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENTES)
        onCreate(db)
    }

    companion object {

        private val DB_VERSION = 1
        private val DB_NAME = "cliente.db"
        private val TABLE_CLIENTES = "clientes"

        private val ID = "_id"
        private val NOME_COMPLETO = "nome_completo"
        private val CPF = "cpf"
        private val CEP = "cep"
        private val LOGRADOURO = "logradouro"
        private val BAIRRO = "bairro"
        private val UF = "uf"
        private val NUMERO = "numero"
        private val DATA_NASCIMENTO = "data_nascimento"
    }

    fun addCliente(cliente: Cliente){
        val db = writableDatabase
        val values = ContentValues()
        values.put(NOME_COMPLETO, cliente.nomeCompleto)
        values.put(CPF, cliente.cpf)
        values.put(CEP, cliente.cep)
        values.put(LOGRADOURO, cliente.logradouro)
        values.put(BAIRRO, cliente.bairro)
        values.put(UF, cliente.uf)
        values.put(NUMERO, cliente.numero)
        values.put(DATA_NASCIMENTO, cliente.dataNascimento)
        db.insert(TABLE_CLIENTES, null, values)
        db.close()
    }

    fun listCliente() : ArrayList<Cliente>{
        val db = writableDatabase
        val clienteList = ArrayList<Cliente>()
        val query = "SELECT * FROM ${TABLE_CLIENTES}"
        val cursor = db.rawQuery(query, null)
        if(cursor != null){
            cursor.moveToFirst()
            while (cursor.moveToNext()){
                val cliente = Cliente (
                    id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID))),
                    nomeCompleto = cursor.getString(cursor.getColumnIndex(NOME_COMPLETO)),
                    dataNascimento = cursor.getString(cursor.getColumnIndex(DATA_NASCIMENTO)),
                    cpf = cursor.getString(cursor.getColumnIndex(CPF)),
                    cep = cursor.getString(cursor.getColumnIndex(CEP)),
                    logradouro = cursor.getString(cursor.getColumnIndex(LOGRADOURO)),
                    bairro = cursor.getString(cursor.getColumnIndex(BAIRRO)),
                    uf = cursor.getString(cursor.getColumnIndex(UF)),
                    numero = cursor.getString(cursor.getColumnIndex(NUMERO))
                )
                clienteList.add(cliente)
            }
        }
        cursor.close()
        return clienteList
    }

    fun updateCliente(cliente: Cliente, id: String){
        val db = writableDatabase
        val values = ContentValues()
        values.put(NOME_COMPLETO, cliente.nomeCompleto)
        values.put(CPF, cliente.cpf)
        values.put(CEP, cliente.cep)
        values.put(LOGRADOURO, cliente.logradouro)
        values.put(BAIRRO, cliente.bairro)
        values.put(UF, cliente.uf)
        values.put(NUMERO, cliente.numero)
        values.put(DATA_NASCIMENTO, cliente.dataNascimento)
        db.update(TABLE_CLIENTES, values, "${ID}=${id}", null)
        db.close()
    }
}