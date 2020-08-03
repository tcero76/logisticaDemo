package com.logistica.demo.model;

import com.logistica.demo.payload.UbicacionesReq;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

@Table(name = "almacen")
@Entity
public class Almacen {
	
	public Almacen() {
	}

	public Almacen(Integer idalmacen) {
		this.idalmacen = idalmacen;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idalmacen;
	
	@Column(name = "nombre")
	private String nombre;
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
			CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
	@JoinColumn(name = "idusuario")
	private Usuario usuario;
	
	@Column(name = "fecharegistro")
	private Date fecharegistro;
	
	@OneToMany(mappedBy = "almacen",cascade = {CascadeType.DETACH, CascadeType.MERGE,
			CascadeType.PERSIST, CascadeType.REFRESH})
	private Set<Zona> zonas;

	@OneToMany(mappedBy = "almacen")
	private Set<Usuario> usuarios;

    public Set<Zona> getZonas() {
		return zonas;
	}

	public void setZonas(Set<Zona> zonas) {
		this.zonas = zonas;
	}

	public Integer getIdalmacen() {
		return idalmacen;
	}

	public void setIdalmacen(Integer idalmacen) {
		this.idalmacen = idalmacen;
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
		result = prime * result + ((idalmacen == null) ? 0 : idalmacen.hashCode());
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
		Almacen other = (Almacen) obj;
		if (idalmacen == null) {
			if (other.idalmacen != null)
				return false;
		} else if (!idalmacen.equals(other.idalmacen))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Almacen [nombre=" + nombre + "]";
	}
	
}
