package com.example.coviddata.Respuestas;

import com.example.coviddata.Objetos.Reporte;

import java.util.Arrays;

public class RespuestaWSDataRegiones {
    private String fecha;
    private String info;
    private boolean estado;
    private Reporte[] reporte;

    public RespuestaWSDataRegiones(String fecha, String info, boolean estado, Reporte[] reporte) {
        this.fecha = fecha;
        this.info = info;
        this.estado = estado;
        this.reporte = reporte;
    }

    public RespuestaWSDataRegiones() {
    }

    @Override
    public String toString() {
        return "RespuestaWSDataRegiones{" +
                "fecha='" + fecha + '\'' +
                ", info='" + info + '\'' +
                ", estado=" + estado +
                ", reporte=" + Arrays.toString(reporte) +
                '}';
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
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

    public Reporte[] getReporte() {
        return reporte;
    }

    public void setReporte(Reporte[] reporte) {
        this.reporte = reporte;
    }
}
