package com.logistica.demo.payload;

import com.logistica.demo.model.Material;

public class ResMaterial {

    public ResMaterial(Material material) {
        this.idmaterial = material.getIdmaterial();
        this.nombre = material.getNombre();
        this.precio = material.getPrecio();
    }

    private Integer idmaterial;

    private String nombre;

    private Double precio;

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getIdmaterial() {
        return idmaterial;
    }

    public void setIdmaterial(Integer idmaterial) {
        this.idmaterial = idmaterial;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
