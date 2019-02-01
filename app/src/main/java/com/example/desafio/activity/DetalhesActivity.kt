package com.example.desafio.activity

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.desafio.R
import com.example.desafio.dao.RetrofitInitializer
import com.example.desafio.extension.callback
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_detalhes.*
import java.math.BigDecimal


class DetalhesActivity : AppCompatActivity() {

    lateinit var mapDetalhes: SupportMapFragment
    lateinit var googleMap: GoogleMap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        //Chamada dos métodos para popular os detalhes da Activity
        //E também a criação do mapa com o cep do Cliente
        populaDetalhes()
    }

    //Função que sobrescreve o retorno pra activity anterior
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    //Função para popular os dados do Detalhes com informações vindas da lista
    private fun populaDetalhes(){
        val intent = intent
        nomeDetalhes.text = intent.getStringExtra("nomeCompleto")
        dataNascimentoDetalhes.text = intent.getStringExtra("dataNascimento")
        cpfDetalhes.text = intent.getStringExtra("cpf")
        val cep = intent.getStringExtra("cep")
        val logradouro = intent.getStringExtra("logradouro")
        val bairro = intent.getStringExtra("bairro")
        val numero = intent.getStringExtra("numero")
        criaMapa(cep, logradouro, bairro, numero)
    }

    //Função que cria o mapa usando o Cep do Usuario cadastrado
    private fun criaMapa(cep: String, logradouro: String, bairro: String, numero: String) {
        val (latitude, longitude) = transformaLatLong("${logradouro}, ${bairro} ${numero}")
        configuraMapa(latitude, longitude)
    }

    //Função que configura o mapa pela Api do Google Maps
    private fun configuraMapa(latitude: Double, longitude: Double) {
        mapDetalhes = supportFragmentManager.findFragmentById(R.id.mapDetalhes) as SupportMapFragment
        mapDetalhes.getMapAsync(OnMapReadyCallback {
            googleMap = it
            val latLng = LatLng(latitude, longitude)
            googleMap.addMarker(MarkerOptions().position(latLng).title("Residencia"))
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))

        })
    }

    //Função que transforma cep em latitude e longitude
    private fun transformaLatLong(endereco: String): Pair<Double, Double> {
        var geocoder = Geocoder(this)
        lateinit var lista: List<Address>
        var latitude = 0.0
        var longitude = 0.0
        lista = geocoder.getFromLocationName(endereco, 1)
        if (lista.size > 0) {
            latitude = lista.get(0).getLatitude()
            longitude = lista.get(0).getLongitude()
        }
        return Pair(latitude, longitude)
    }

}
