package com.logistica.demo.model;

import com.logistica.demo.payload.UbicacionesReq;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.*;

@Table(name = "nivel")
@Entity
public class Nivel {
	
	public Nivel() {
	}

	public Nivel (UbicacionesReq.Nivel nivel){
		this.nombre = nivel.getNombre();
		this.idnivel = nivel.getId();
		this.poses = nivel.getPoses().stream()
				.map(Pos::new)
				.map(p -> {
					p.setNivel(this);
					return p;
				})
				.collect(Collectors.toSet());
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idnivel;
	
	@Column(name = "nombre")
	private String nombre;

	@Column(name = "fecharegistro")
	private Date fecharegistro;
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
			CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
	@JoinColumn(name = "idzona")
	private Zona zona;
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
			CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
	@JoinColumn(name = "idusuario")
	private Usuario usuario;
	
	@OneToMany(mappedBy = "nivel", cascade = {CascadeType.DETACH, CascadeType.MERGE,
			CascadeType.PERSIST, CascadeType.REFRESH})
	private Set<Pos> poses;

	public Set<Pos> getPoses() {
		return poses;
	}

	public void setPoses(Set<Pos> poses) {
		this.poses = poses;
	}

	public Integer getIdnivel() {
		return idnivel;
	}

	public void setIdnivel(Integer idnivel) {
		this.idnivel = idnivel;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Zona getZona() {
		return zona;
	}

	public void setZona(Zona zona) {
		this.zona = zona;
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
		result = prime * result + ((idnivel == null) ? 0 : idnivel.hashCode());
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
		Nivel other = (Nivel) obj;
		if (idnivel == null) {
			if (other.idnivel != null)
				return false;
		} else if (!idnivel.equals(other.idnivel))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Nivel [nombre=" + nombre + "]";
	}
	
	
}
