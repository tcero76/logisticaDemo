package com.logistica.demo.payload;

public class CurrentUserResponse {

	public CurrentUserResponse(Integer idUsuario, String nombre, Boolean isAuthenticated, String almacen) {
		this.idUsuario = idUsuario;
		this.nombre = nombre;
		this.isAuthenticated = isAuthenticated;
		this.almacen = almacen;
	}

	private Integer idUsuario;
	
	private String nombre;
	
	private Boolean isAuthenticated;

	private String almacen;

	public String getAlmacen() {
		return almacen;
	}

	public void setAlmacen(String almacen) {
		this.almacen = almacen;
	}

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

	public Boolean getIsAuthenticated() {
		return isAuthenticated;
	}

	public void setIsAuthenticated(Boolean isAuthenticated) {
		this.isAuthenticated = isAuthenticated;
	}
	
}
