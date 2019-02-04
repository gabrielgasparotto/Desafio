package com.example.desafio.util

import android.content.Context
import android.content.Intent
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.desafio.R
import com.example.desafio.activity.CadastroActivity
import com.example.desafio.activity.DetalhesActivity
import com.example.desafio.extension.listen
import com.example.desafio.model.Cliente
import kotlinx.android.synthetic.main.cliente_item.view.*


class ClienteListAdapter(val clientes: ArrayList<Cliente>,
                         val context: Context) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder = ViewHolder(LayoutInflater.from(context).inflate(R.layout.cliente_item, parent, false))
        viewHolder.listen { position,_ ->

            val builder = AlertDialog.Builder(context)
            val cliente = clientes.get(position)

            builder.setTitle("${cliente.id} - ${cliente.nomeCompleto}")
            builder.setMessage("Deseja alterar ou ver mais detalhes sobre o cliente?")

            builder.setPositiveButton("Detalhes"){_,_ ->
                val intent = Intent(context, DetalhesActivity::class.java)
                putExtras(cliente, intent)
            }

            builder.setNegativeButton("Alterar"){_,_ ->
                val intent = Intent(context, CadastroActivity::class.java)
                putExtras(cliente, intent)
            }

            builder.setNeutralButton("Cancelar"){_,_ ->
            }

            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

        return viewHolder
    }

    private fun putExtras(cliente: Cliente, intent: Intent) {
        intent.putExtra("id", cliente.id.toString())
        intent.putExtra("nomeCompleto", cliente.nomeCompleto)
        intent.putExtra("dataNascimento", cliente.dataNascimento)
        intent.putExtra("cpf", cliente.cpf)
        intent.putExtra("cep", cliente.cep)
        intent.putExtra("logradouro", cliente.logradouro)
        intent.putExtra("bairro", cliente.bairro)
        intent.putExtra("numero", cliente.numero)
        context.startActivity(intent)
    }

    override fun getItemCount(): Int {
        return clientes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var cliente = clientes.get(position)
        holder.nomeItem.text = cliente.nomeCompleto
        holder.nascimentoItem.text = cliente.dataNascimento
        holder.cpfItem.text = cliente.cpf
    }



}


class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val nomeItem = view.nomeItem
    val nascimentoItem = view.dataNascimentoItem
    val cpfItem = view.cpfItem
}

