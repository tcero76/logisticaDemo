package com.logistica.demo.payload;

public class UsuarioRequest {

    public UsuarioRequest() {
    }

    private Integer idUsuario;

    private String nombre;

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
