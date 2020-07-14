package com.example.coviddata.Objetos;

public class Reporte {
    private String region;
   private Integer acumulado_total;
   private Integer casos_nuevos_total;
   private Integer casos_nuevos_csintomas;
   private Integer casos_nuevos_ssintomas;
   private Integer casos_nuevos_snotificar;
   private Integer fallecidos;
   private Integer casos_activos_confirmados;

    public Reporte(String region, Integer acumulado_total, Integer casos_nuevos_total,
                   Integer casos_nuevos_csintomas, Integer casos_nuevos_ssintomas,
                   Integer casos_nuevos_snotificar, Integer fallecidos, Integer casos_activos_confirmados) {
        this.region = region;
        this.acumulado_total = acumulado_total;
        this.casos_nuevos_total = casos_nuevos_total;
        this.casos_nuevos_csintomas = casos_nuevos_csintomas;
        this.casos_nuevos_ssintomas = casos_nuevos_ssintomas;
        this.casos_nuevos_snotificar = casos_nuevos_snotificar;
        this.fallecidos = fallecidos;
        this.casos_activos_confirmados = casos_activos_confirmados;
    }

    public Reporte() {
    }

    @Override
    public String toString() {
        return "Reporte{" +
                "region='" + region + '\'' +
                ", acumulado_total=" + acumulado_total +
                ", casos_nuevos_total=" + casos_nuevos_total +
                ", casos_nuevos_csintomas=" + casos_nuevos_csintomas +
                ", casos_nuevos_ssintomas=" + casos_nuevos_ssintomas +
                ", casos_nuevos_snotificar=" + casos_nuevos_snotificar +
                ", fallecidos=" + fallecidos +
                ", casos_activos_confirmados=" + casos_activos_confirmados +
                '}';
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Integer getAcumulado_total() {
        return acumulado_total;
    }

    public void setAcumulado_total(Integer acumulado_total) {
        this.acumulado_total = acumulado_total;
    }

    public Integer getCasos_nuevos_total() {
        return casos_nuevos_total;
    }

    public void setCasos_nuevos_total(Integer casos_nuevos_total) {
        this.casos_nuevos_total = casos_nuevos_total;
    }

    public Integer getCasos_nuevos_csintomas() {
        return casos_nuevos_csintomas;
    }

    public void setCasos_nuevos_csintomas(Integer casos_nuevos_csintomas) {
        this.casos_nuevos_csintomas = casos_nuevos_csintomas;
    }

    public Integer getCasos_nuevos_ssintomas() {
        return casos_nuevos_ssintomas;
    }

    public void setCasos_nuevos_ssintomas(Integer casos_nuevos_ssintomas) {
        this.casos_nuevos_ssintomas = casos_nuevos_ssintomas;
    }

    public Integer getCasos_nuevos_snotificar() {
        return casos_nuevos_snotificar;
    }

    public void setCasos_nuevos_snotificar(Integer casos_nuevos_snotificar) {
        this.casos_nuevos_snotificar = casos_nuevos_snotificar;
    }

    public Integer getFallecidos() {
        return fallecidos;
    }

    public void setFallecidos(Integer fallecidos) {
        this.fallecidos = fallecidos;
    }

    public Integer getCasos_activos_confirmados() {
        return casos_activos_confirmados;
    }

    public void setCasos_activos_confirmados(Integer casos_activos_confirmados) {
        this.casos_activos_confirmados = casos_activos_confirmados;
    }
}
