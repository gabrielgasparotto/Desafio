package com.example.desafio.activity

import android.location.Address
import android.location.Geocoder
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.desafio.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_main.*

class DetalhesActivity : AppCompatActivity() {

    lateinit var mapDetalhes: SupportMapFragment
    lateinit var googleMap: GoogleMap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes)
        setSupportActionBar(toolbar)

        //Trocar para endereco que vai ser "Longradouro, bairro"
        var (latitude, longitude) = transformaLatLong("Praça da Sé, Sé")

        mapDetalhes = supportFragmentManager.findFragmentById(R.id.mapDetalhes) as SupportMapFragment
        mapDetalhes.getMapAsync(OnMapReadyCallback {
            googleMap = it
            val latLng = LatLng(latitude, longitude)
            googleMap.addMarker(MarkerOptions().position(latLng).title("Casa"))
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng ,15f))

        })
    }

    private fun transformaLatLong(endereco: String): Pair<Double, Double> {
        var geocoder = Geocoder(this)
        lateinit var lista: List<Address>
        var latitude = 0.0;
        var longitude = 0.0;
        lista = geocoder.getFromLocationName(endereco, 1)
        if (lista.size > 0) {
            latitude = lista.get(0).getLatitude();
            longitude = lista.get(0).getLongitude();
        }
        return Pair(latitude, longitude)
    }
}
