package com.example.coviddata.Respuestas;

import com.example.coviddata.Objetos.Reporte;

public class RespuestaWSDataRegion {

    private String info;
    private String fecha;
    private boolean estado;
    private Reporte reporte;

    public RespuestaWSDataRegion(String info, String fecha, boolean estado, Reporte reporte) {
        this.info = info;
        this.fecha = fecha;
        this.estado = estado;
        this.reporte = reporte;
    }

    public RespuestaWSDataRegion() {
    }

    @Override
    public String toString() {
        return "RespuestaWSDataRegion{" +
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
