package com.logistica.demo.model;

import java.util.Date;

import javax.persistence.*;

@Table(name = "inventario")
@Entity
public class Inventario {
	
	public Inventario() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idinventario;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idmaterial")
	private Material material;
	
	@Column(name = "cantidad")
	private Double cantidad;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idpos")
	private Pos pos;
	
	@Column(name = "fecharegistro")
	private Date fecharegistro;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idusuario")
	private Usuario usuario;
	
	@Column(name = "cantidadtotal")
	private Double cantidadtotal;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idoritem")
	private Oritem oritem;

	public Oritem getOritem() {
		return oritem;
	}

	public void setOritem(Oritem oritem) {
		this.oritem = oritem;
	}

	public Integer getIdinventario() {
		return idinventario;
	}

	public void setIdinventario(Integer idinventario) {
		this.idinventario = idinventario;
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

	public Pos getPos() {
		return pos;
	}

	public void setPos(Pos pos) {
		this.pos = pos;
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

	public Double getCantidadtotal() {
		return cantidadtotal;
	}

	public void setCantidadtotal(Double cantidadtotal) {
		this.cantidadtotal = cantidadtotal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idinventario == null) ? 0 : idinventario.hashCode());
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
		Inventario other = (Inventario) obj;
		if (idinventario == null) {
			if (other.idinventario != null)
				return false;
		} else if (!idinventario.equals(other.idinventario))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Inventario [idinventario=" + idinventario + "]";
	}
	
	
}
