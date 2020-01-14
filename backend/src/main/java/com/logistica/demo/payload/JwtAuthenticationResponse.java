package com.logistica.demo.payload;

public class JwtAuthenticationResponse {

		public JwtAuthenticationResponse(String accessToken, Integer idUsuario, String nombre, String almacen) {
			this.accessToken = accessToken;
			this.idUsuario = idUsuario;
			this.nombre = nombre;
			this.almacen = almacen;
		}
		
		private Integer idUsuario;
		
		private String nombre;

		private String accessToken;
		
		private String tokenType = "Bearer";

		private String almacen;

		public String getAlmacen() {
			return almacen;
		}

		public void setAlmacen(String almacen) {
			this.almacen = almacen;
		}

		public String getAccessToken() {
			return accessToken;
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

		public void setAccessToken(String accessToken) {
			this.accessToken = accessToken;
		}

		public String getTokenType() {
			return tokenType;
		}

		public void setTokenType(String tokenType) {
			this.tokenType = tokenType;
		}
}
