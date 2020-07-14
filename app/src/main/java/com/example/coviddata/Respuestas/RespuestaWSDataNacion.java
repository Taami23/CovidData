package com.example.coviddata.Respuestas;

import com.example.coviddata.Objetos.Reporte;

public class RespuestaWSDataNacion {
    private String info;
    private String fecha;
    private boolean estado;
    private Reporte reporte;

    public RespuestaWSDataNacion(String info, String fecha, boolean estado, Reporte reporte) {
        this.info = info;
        this.fecha = fecha;
        this.estado = estado;
        this.reporte = reporte;
    }

    public RespuestaWSDataNacion() {
    }

    @Override
    public String toString() {
        return "RespuestaWSDataNacion{" +
                "info='" + info + '\'' +
                ", fecha='" + fecha + '\'' +
                ", estado=" + estado +
                ", reporte=" + reporte +
                '}';
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Reporte getReporte() {
        return reporte;
    }

    public void setReporte(Reporte reporte) {
        this.reporte = reporte;
    }
}
