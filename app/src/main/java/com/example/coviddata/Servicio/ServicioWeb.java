package com.example.coviddata.Servicio;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ServicioWeb {
    @FormUrlEncoded
    @POST("regiones")
    Call<> regiones ()
}
