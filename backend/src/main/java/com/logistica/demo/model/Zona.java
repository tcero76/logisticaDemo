package com.logistica.demo.model;

import com.logistica.demo.payload.UbicacionesReq;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Table(name = "zona")
@Entity
public class Zona {
	
	public Zona() {
	}

	public Zona(@NotNull Integer idzona) {
		this.idzona = idzona;
	}

	public Zona(UbicacionesReq ubicacionesReq) {
		this.nombre = ubicacionesReq.getNombre();
		this.idzona = ubicacionesReq.getId();
		this.niveles = ubicacionesReq.getNiveles().stream()
				.map(Nivel::new)
				.map(n -> {
					n.setZona(this);
					return n;
				})
				.collect(Collectors.toSet());
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	private Integer idzona;
	
	@Column(name = "nombre")
	private String nombre;
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
			CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
	@JoinColumn(name = "idalmacen")
	private Almacen almacen;
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
			CascadeType.PERSIST, CascadeType.REFRESH},fetch = FetchType.LAZY)
	@JoinColumn(name = "idusuario")
	private Usuario usuario;
	
	@Column(name = "fecharegistro")
	private Date fecharegistro;
	
	@OneToMany(mappedBy = "zona",cascade = {CascadeType.DETACH, CascadeType.MERGE,
			CascadeType.PERSIST, CascadeType.REFRESH})
	private Set<Nivel> niveles;

	@OneToMany(mappedBy = "zona")
	private Set<Cuenta> cuentas;

	public Set<Cuenta> getCuentas() {
		return cuentas;
	}

	public void setCuentas(Set<Cuenta> cuentas) {
		this.cuentas = cuentas;
	}

	public Set<Nivel> getNiveles() {
		return niveles;
	}

	public void setNiveles(Set<Nivel> niveles) {
		this.niveles = niveles;
	}

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

	public Almacen getAlmacen() {
		return almacen;
	}

	public void setAlmacen(Almacen almacen) {
		this.almacen = almacen;
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
		result = prime * result + ((idzona == null) ? 0 : idzona.hashCode());
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
		Zona other = (Zona) obj;
		if (idzona == null) {
			if (other.idzona != null)
				return false;
		} else if (!idzona.equals(other.idzona))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Zona [nombre=" + nombre + "]";
	}

}
