package com.logistica.demo.payload;

import javax.validation.constraints.NotNull;

public class LoginRequest {

	public LoginRequest() {
	}

	public LoginRequest(String usuario, String clave) {
		this.usuario = usuario;
		this.clave = clave;
	}

	@NotNull
	private String usuario;

	@NotNull
	private String clave;

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

}
