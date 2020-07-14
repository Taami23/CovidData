package com.example.coviddata.Objetos;

public class Region {
    private Integer id;
    private String nombre;

    public Region(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Region() {
    }

    @Override
    public String toString() {
        return "Region{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
