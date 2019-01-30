package com.example.desafio.util

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.desafio.R
import com.example.desafio.extension.formataData
import com.example.desafio.extension.listen
import com.example.desafio.model.Cliente
import kotlinx.android.synthetic.main.cliente_item.view.*

class ClienteListAdapter(val clientes: ArrayList<Cliente>,
                         val context: Context) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder = ViewHolder(LayoutInflater.from(context).inflate(R.layout.cliente_item, parent, false))
        viewHolder.listen { position, type ->
            //Ação de click na lista
        }

        return viewHolder
    }

    override fun getItemCount(): Int {
        return clientes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var cliente = clientes.get(position)
        holder.nomeItem.text = cliente.nomeCompleto
        holder.nascimentoItem.text = cliente.dataNascimento.formataData()
        holder.cpfItem.text = cliente.cpf
    }



}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val nomeItem = view.nomeItem
    val nascimentoItem = view.dataNascimentoItem
    val cpfItem = view.cpfItem
}

