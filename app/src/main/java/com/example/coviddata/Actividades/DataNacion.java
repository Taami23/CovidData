package com.example.coviddata.Actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.example.coviddata.R;
import com.example.coviddata.Respuestas.RespuestaWSDataNacion;
import com.example.coviddata.Servicio.ServicioWeb;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataNacion extends AppCompatActivity {
    private ServicioWeb servicioWeb;
    private static String uniqueID = null;
    private static final String PREF_UNIQUE_ID = "PREF_UNIQUE_ID";
    public static final String CREDENTIALS = DataNacion.class.getPackage().getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_data);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://covid.unnamed-chile.com/api/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        servicioWeb = retrofit.create(ServicioWeb.class);

        servicio();
    }

    public void servicio(){

        final Call<RespuestaWSDataNacion> respuestaWSDataNacionCall = servicioWeb.nacional();
        respuestaWSDataNacionCall.enqueue(new Callback<RespuestaWSDataNacion>() {
            @Override
            public void onResponse(Call<RespuestaWSDataNacion> call, Response<RespuestaWSDataNacion> response) {
                if (response != null){
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

    private void initData(){
        Intent data = new Intent(this, Data.class);
        startActivity(data);
        finish();
    }

    private void initMain() {
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


    public void savePreferences(RespuestaWSDataNacion respuestaWSDataNacion) {
        SharedPreferences preferences = getSharedPreferences(CREDENTIALS, MODE_PRIVATE);

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
