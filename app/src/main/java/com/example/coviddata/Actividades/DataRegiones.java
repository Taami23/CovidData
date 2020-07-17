package com.example.coviddata.Actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.coviddata.Objetos.Region;
import com.example.coviddata.R;
import com.example.coviddata.Respuestas.RespuestaWSRegiones;
import com.example.coviddata.Servicio.ServicioWeb;
import com.google.gson.JsonArray;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataRegiones extends AppCompatActivity {
    LinearLayout contenedorRegiones;
    private ServicioWeb servicioWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_regiones);
        contenedorRegiones = findViewById(R.id.contenedorRegiones);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://covid.unnamed-chile.com/api/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        servicioWeb = retrofit.create(ServicioWeb.class);
        holi();

    }

    public void holi(){

        final Call<RespuestaWSRegiones> respuestaWSRegionesCall = servicioWeb.regiones();
        respuestaWSRegionesCall.enqueue(new Callback<RespuestaWSRegiones>() {
            @Override
            public void onResponse(Call<RespuestaWSRegiones> call, Response<RespuestaWSRegiones> response) {
                if (response != null){
                    RespuestaWSRegiones respuestaWSRegiones = response.body();
                    Log.d("Retrofit", respuestaWSRegiones.toString());

                    Region regiones[] = respuestaWSRegiones.getRegiones();
                    agregarBotones(regiones, contenedorRegiones);
                }
            }

            @Override
            public void onFailure(Call<RespuestaWSRegiones> call, Throwable t) {

            }
        });

    }

    public void agregarBotones (Region regiones[], LinearLayout contenedor){
        for (int i = 0; i < regiones.length ; i++) {
            Button boton = new Button (getApplicationContext());
            boton.setText(regiones[i].getNombre());
            boton.setId(i);
            contenedor.addView(boton);
        }
    }

    private void initMain(){
        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            initMain();
        }
        return super.onKeyDown(keyCode, event);

    }
}
