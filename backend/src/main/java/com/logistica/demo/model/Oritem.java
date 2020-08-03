package com.logistica.demo.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Table(name="oritem")
@Entity
public class Oritem {
	
	public Oritem() {
	}

	public Oritem(Material material, Double cantidad, Date fecharegistro, Usuario usuario, Orec orec, Inventario inventario, Integer pos) {
		this.material = material;
		this.cantidad = cantidad;
		this.fecharegistro = fecharegistro;
		this.usuario = usuario;
		this.orec = orec;
		this.inventario = inventario;
		this.pos = pos;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idoritem;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idmaterial")
	private Material material;
	
	@Column(name = "cantidad")
	private Double cantidad;
	
	@Column(name = "fecharegistro")
	private Date fecharegistro;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idusuario")
	private Usuario usuario;
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
				CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
	@JoinColumn(name = "idorec")
	private Orec orec;

	@OneToOne(mappedBy = "oritem")
	private Inventario inventario;

	@Column(name = "pos")
	private Integer pos;

	public Inventario getInventario() {
		return inventario;
	}

	public void setInventario(Inventario inventario) {
		this.inventario = inventario;
	}

	public Integer getPos() {
		return pos;
	}

	public void setPos(Integer pos) {
		this.pos = pos;
	}

	public Integer getIdoritem() {
		return idoritem;
	}

	public void setIdoritem(Integer idoritem) {
		this.idoritem = idoritem;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public Double getCantidad() {
		return cantidad;
	}

	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}

	public Date getFecharegistro() {
		return fecharegistro;
	}

	public void setFecharegistro(Date fecharegistro) {
		this.fecharegistro = fecharegistro;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Orec getOrec() {
		return orec;
	}

	public void setOrec(Orec orec) {
		this.orec = orec;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idoritem == null) ? 0 : idoritem.hashCode());
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
		Oritem other = (Oritem) obj;
		if (idoritem == null) {
			if (other.idoritem != null)
				return false;
		} else if (!idoritem.equals(other.idoritem))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Oritem [idoritem=" + idoritem + "]";
	}
	
	
}
