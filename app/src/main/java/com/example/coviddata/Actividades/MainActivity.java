package com.example.coviddata.Actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.coviddata.R;
import com.example.coviddata.Servicio.ServicioWeb;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private ServicioWeb servicioWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://covid.unnamed-chile.com/api/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        servicioWeb = retrofit.create(ServicioWeb.class);
    }

    private void initDataNacion(){
        Intent dNacion = new Intent(this, DataNacion.class);
        startActivity(dNacion);
        finish();
    }

    private void initDataRegiones(){
        Intent dRegiones = new Intent(this, DataRegiones.class);
        startActivity(dRegiones);
        finish();
    }

    private void initDataRegion(){
        Intent dRegion = new Intent(this, DataRegion.class);
        startActivity(dRegion);
        finish();
    }
}
