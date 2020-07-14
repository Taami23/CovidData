package com.example.coviddata.Respuestas;

import com.example.coviddata.Objetos.Region;

import java.util.Arrays;

public class RespuestaWSRegiones {
    private String info;
    private boolean estado;
    private Region[] regiones;

    public RespuestaWSRegiones(String info, boolean estado, Region[] regiones) {
        this.info = info;
        this.estado = estado;
        this.regiones = regiones;
    }

    public RespuestaWSRegiones() {
    }

    @Override
    public String toString() {
        return "RespuestaWSRegiones{" +
                "info='" + info + '\'' +
                ", estado=" + estado +
                ", regiones=" + Arrays.toString(regiones) +
                '}';
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Region[] getRegiones() {
        return regiones;
    }

    public void setRegiones(Region[] regiones) {
        this.regiones = regiones;
    }
}
