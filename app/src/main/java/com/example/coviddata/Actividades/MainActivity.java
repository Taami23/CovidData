package com.example.coviddata.Actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.coviddata.R;
import com.example.coviddata.Servicio.ServicioWeb;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private ServicioWeb servicioWeb;
    private Button dataNacion;
    private Button dataPorRegiones;
    private Button dataRegionales;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataNacion = findViewById(R.id.nacionales);
        dataRegionales = findViewById(R.id.regionales);
        dataPorRegiones = findViewById(R.id.porRegion);

        dataNacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDataNacion();
            }
        });

        dataPorRegiones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDataPorRegiones();
            }
        });

        dataRegionales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDataRegionales();
            }
        });
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://covid.unnamed-chile.com/api/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        servicioWeb = retrofit.create(ServicioWeb.class);
    }

    private void initDataNacion(){
        Intent dNacion = new Intent(this, DataNacion.class);
        startActivity(dNacion);
        finish();
    }

    private void initDataPorRegiones(){
        Intent dPorRegiones = new Intent(this, MostrarRegiones.class);
        startActivity(dPorRegiones);
        finish();
    }

    private void initDataRegionales(){
        Intent dRegionales = new Intent(this, DataRegionales.class);
        startActivity(dRegionales);
        finish();
    }
}
