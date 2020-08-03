package com.logistica.demo.payload;

import com.logistica.demo.model.Zona;

public class ResZona {
    public ResZona(Zona zona) {
        this.nombre = zona.getNombre();
        this.idzona = zona.getIdzona();
    }

    private Integer idzona;

    private String nombre;

    public Integer getIdzona() {
        return idzona;
    }

    public void setIdzona(Integer idzona) {
        this.idzona = idzona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
