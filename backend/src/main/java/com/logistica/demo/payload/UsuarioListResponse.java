package com.logistica.demo.payload;

import com.logistica.demo.model.Usuario;

public class UsuarioListResponse {

    public UsuarioListResponse(Usuario usuario) {
        this.idUsuario = usuario.getIdusuario();
        this.nombre = usuario.getNombre();
        this.idRol = usuario.getRol().getIdrol();
        Rol = usuario.getRol().getNombre();
    }

    private Integer idUsuario;

    private String nombre;

    private Integer idRol;

    private String Rol;

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

    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    public String getRol() {
        return Rol;
    }

    public void setRol(String rol) {
        Rol = rol;
    }
}
