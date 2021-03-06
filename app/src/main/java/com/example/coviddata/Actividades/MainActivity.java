package com.example.coviddata.Actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.coviddata.R;
import com.example.coviddata.Respuestas.RespuestaWSDataNacion;
import com.example.coviddata.Servicio.ServicioWeb;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    private Button dataNacion;
    private Button dataPorRegiones;
    private Button dataRegionales;
    private ServicioWeb servicioWeb;
    private static String uniqueID = null;
    private static final String PREF_UNIQUE_ID = "PREF_UNIQUE_ID";
    public static final String CREDENTIALS = MainActivity.class.getPackage().getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        dataNacion = findViewById(R.id.nacionales);
        dataRegionales = findViewById(R.id.regionales);
        dataPorRegiones = findViewById(R.id.porRegion);

        dataNacion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            servicio();
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

    public void servicio() {

        final Call<RespuestaWSDataNacion> respuestaWSDataNacionCall = servicioWeb.nacional();
        respuestaWSDataNacionCall.enqueue(new Callback<RespuestaWSDataNacion>() {
            @Override
            public void onResponse(Call<RespuestaWSDataNacion> call, Response<RespuestaWSDataNacion> response) {
                if (response != null) {
                    RespuestaWSDataNacion respuestaWSDataNacion = response.body();
                    Log.d("Retrofit", respuestaWSDataNacion.toString());
                    savePreferences(respuestaWSDataNacion);
                    initData();
                }
            }

            @Override
            public void onFailure(Call<RespuestaWSDataNacion> call, Throwable t) {

            }
        });

    }

    private void initData() {
        Intent data = new Intent(this, Data.class);
        startActivity(data);
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

    public void savePreferences(RespuestaWSDataNacion respuestaWSDataNacion) {
        SharedPreferences preferences = getSharedPreferences(MainActivity.CREDENTIALS, MODE_PRIVATE);

        String info = respuestaWSDataNacion.getInfo();
        Integer idActivity = 1;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("info", info);
        editor.putString("fecha", respuestaWSDataNacion.getFecha());
        //editor.putString("estado", respuestaWSDataNacion.);
        editor.putInt("acumulado_total", respuestaWSDataNacion.getReporte().getAcumulado_total());
        editor.putInt("casos_nuevos_total", respuestaWSDataNacion.getReporte().getCasos_nuevos_total());
        editor.putInt("casos_nuevos_csintomas", respuestaWSDataNacion.getReporte().getCasos_nuevos_csintomas());
        editor.putInt("casos_nuevos_ssintomas", respuestaWSDataNacion.getReporte().getCasos_nuevos_ssintomas());
        editor.putInt("casos_nuevos_snotificar", respuestaWSDataNacion.getReporte().getCasos_nuevos_snotificar());
        editor.putInt("fallecidos", respuestaWSDataNacion.getReporte().getFallecidos());
        editor.putInt("casos_activos_confirmados", respuestaWSDataNacion.getReporte().getCasos_activos_confirmados());
        editor.putInt("Actividad", idActivity);
        editor.commit();
    }

    public synchronized static String id(Context context) {
        if (uniqueID == null) {
            SharedPreferences sharedPrefs = context.getSharedPreferences(
                    PREF_UNIQUE_ID, Context.MODE_PRIVATE);
            uniqueID = sharedPrefs.getString(PREF_UNIQUE_ID, null);
            if (uniqueID == null) {
                uniqueID = UUID.randomUUID().toString();
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString(PREF_UNIQUE_ID, uniqueID);
                editor.commit();
            }
        }
        return uniqueID;
    }
}
