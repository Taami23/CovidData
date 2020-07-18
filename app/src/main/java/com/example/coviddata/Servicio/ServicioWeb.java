package com.example.coviddata.Servicio;

import com.example.coviddata.Respuestas.RespuestaWSDataNacion;
import com.example.coviddata.Respuestas.RespuestaWSDataRegion;
import com.example.coviddata.Respuestas.RespuestaWSDataRegiones;
import com.example.coviddata.Respuestas.RespuestaWSRegiones;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ServicioWeb {

    @POST("regiones")
    Call<RespuestaWSRegiones> regiones();

    @POST("data/all")
    Call<RespuestaWSDataRegiones> dataRegiones();


    @POST("data/nacional")
    Call<RespuestaWSDataNacion> nacional();


    @POST("data/{idregion}")
    Call<RespuestaWSDataRegion> region(@Path("idregion") Integer id);
}
