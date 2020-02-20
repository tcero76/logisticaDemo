package com.logistica.demo.model;

import java.util.Date;

import javax.persistence.*;

@Table(name = "pos")
@Entity
public class Pos {
	
	public Pos() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idpos;
	
	@Column(name = "nombre")
	private String nombre;
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
			CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
	@JoinColumn(name = "idusuario")
	private Usuario usuario;
	
	@Column(name = "fecharegistro")
	private Date fecharegistro;
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
			CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
	@JoinColumn(name = "idnivel")
	private Nivel nivel;

	public Nivel getNivel() {
		return nivel;
	}

	public void setNivel(Nivel nivel) {
		this.nivel = nivel;
	}

	public Integer getIdpos() {
		return idpos;
	}

	public void setIdpos(Integer idpos) {
		this.idpos = idpos;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Date getFecharegistro() {
		return fecharegistro;
	}

	public void setFecharegistro(Date fecharegistro) {
		this.fecharegistro = fecharegistro;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idpos == null) ? 0 : idpos.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pos other = (Pos) obj;
		if (idpos == null) {
			if (other.idpos != null)
				return false;
		} else if (!idpos.equals(other.idpos))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return nombre +
				"/" +
				getNivel().getNombre() +
				"/" +
				getNivel().getZona().getNombre();
	}
	
}
