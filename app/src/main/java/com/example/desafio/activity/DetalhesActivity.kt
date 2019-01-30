package com.example.desafio.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.desafio.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_main.*

class DetalhesActivity : AppCompatActivity() {

    lateinit var mapDetalhes: SupportMapFragment
    lateinit var googleMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes)
        setSupportActionBar(toolbar)

        mapDetalhes = supportFragmentManager.findFragmentById(R.id.mapDetalhes) as SupportMapFragment
        mapDetalhes.getMapAsync(OnMapReadyCallback {
            googleMap = it

            val latLng = LatLng(13.03, 77.80)
            googleMap.addMarker(MarkerOptions().position(latLng).title("Casa"))
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng ,8f))

        })
    }
}
