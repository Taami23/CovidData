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
    private Button dataRegiones;
    private Button dataRegion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataNacion = findViewById(R.id.nacionales);
        dataRegiones = findViewById(R.id.regionales);
        dataRegion = findViewById(R.id.region);

        dataNacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDataNacion();
            }
        });

        dataRegiones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDataRegiones();
            }
        });

        dataRegion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDataRegion();
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
