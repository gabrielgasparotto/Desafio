package com.example.desafio.extension

import android.support.v7.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Calendar

fun Calendar.formataData(): String{
    val formato = "dd/MM/yyyy"
    val formatador = SimpleDateFormat(formato)
    return formatador.format(this.time)
}

fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int, type: Int) -> Unit): T {
    itemView.setOnClickListener {
        event.invoke(getAdapterPosition(), getItemViewType())
    }
    return this
}