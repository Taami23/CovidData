package com.example.coviddata.Actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import com.example.coviddata.Objetos.Region;
import com.example.coviddata.R;
import com.example.coviddata.Respuestas.RespuestaWSDataRegion;
import com.example.coviddata.Respuestas.RespuestaWSRegiones;
import com.example.coviddata.Servicio.ServicioWeb;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MostrarRegiones extends AppCompatActivity {
    LinearLayout contenedorRegiones;
    private ServicioWeb servicioWeb;
    private static String uniqueID = null;
    private static final  String PREF_UNIQUE_ID = "PREF_UNIQUE_ID";
    public static final String CREDENTIALS = MostrarRegiones.class.getPackage().getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_regiones);
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
            boton.setOnClickListener(enviarDatos);
            boton.setId(regiones[i].getId());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT );
            params.setMargins(10, 30, 10, 0);
            boton.setLayoutParams(params);
            boton.setBackgroundResource(R.drawable.prueba);
            boton.setTextAppearance(getApplicationContext(), R.style.AppButton );
            //boton.setTextColor(Color.parseColor("#FFE3F2FD"));
            //int color = Color.parseColor("#468BA6");
            //boton.setBackgroundColor(color);
            contenedor.addView(boton);


        }
    }

    private View.OnClickListener enviarDatos = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final Call<RespuestaWSDataRegion> respuestaWSDataRegionCall = servicioWeb.region(v.getId());
            respuestaWSDataRegionCall.enqueue(new Callback<RespuestaWSDataRegion>() {
                @Override
                public void onResponse(Call<RespuestaWSDataRegion> call, Response<RespuestaWSDataRegion> response) {
                    if (response != null){
                        if (response.body()!=null && response.code()==200){
                            RespuestaWSDataRegion respuestaWSDataRegion = response.body();
                            Log.d("Retrofit", respuestaWSDataRegion.toString());
                            savePreferences(respuestaWSDataRegion);
                            initData();
                        }else if (response.code()==400){
                            try {
                                JSONObject jsonObject = new JSONObject(response.errorBody().string());
                                String mensaje = jsonObject.getString("info");
                                new MaterialAlertDialogBuilder(MostrarRegiones.this)
                                        .setTitle("Upps! Ha ocurrido un error")
                                        .setMessage(mensaje)
                                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        })
                                        .show();
                            }catch (JSONException | IOException e){
                                e.printStackTrace();
                            }
                        }

                    }
                }

                @Override
                public void onFailure(Call<RespuestaWSDataRegion> call, Throwable t) {

                }
            });
        }
    };

    private void initData(){
        Intent data = new Intent(this, Data.class);
        startActivity(data);
        finish();
    }

    public void savePreferences(RespuestaWSDataRegion respuestaWSDataRegion){
        SharedPreferences preferences = getSharedPreferences(CREDENTIALS, MODE_PRIVATE);

        String info = respuestaWSDataRegion.getInfo();
        Integer idActivity = 2;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("info", info);
        editor.putString("fecha", respuestaWSDataRegion.getFecha());
        //editor.putString("estado", respuestaWSDataRegion.);
        editor.putInt("acumulado_total", respuestaWSDataRegion.getReporte().getAcumulado_total());
        editor.putInt("casos_nuevos_total", respuestaWSDataRegion.getReporte().getCasos_nuevos_total());
        editor.putInt("casos_nuevos_csintomas", respuestaWSDataRegion.getReporte().getCasos_nuevos_csintomas());
        editor.putInt("casos_nuevos_ssintomas", respuestaWSDataRegion.getReporte().getCasos_nuevos_ssintomas());
        editor.putInt("casos_nuevos_snotificar", respuestaWSDataRegion.getReporte().getCasos_nuevos_snotificar());
        editor.putInt("fallecidos", respuestaWSDataRegion.getReporte().getFallecidos());
        editor.putInt("casos_activos_confirmados", respuestaWSDataRegion.getReporte().getCasos_activos_confirmados());
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
