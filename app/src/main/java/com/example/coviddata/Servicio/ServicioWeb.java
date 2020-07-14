package com.example.coviddata.Servicio;

import com.example.coviddata.Respuestas.RespuestaWSDataRegiones;
import com.example.coviddata.Respuestas.RespuestaWSRegiones;

import retrofit2.Call;
import retrofit2.http.POST;

public interface ServicioWeb {

    @POST("regiones")
    Call<RespuestaWSRegiones> regiones ();

    @POST("data/all")
    Call<RespuestaWSDataRegiones> dataRegiones();
}
