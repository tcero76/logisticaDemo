package com.logistica.demo.model;

import java.util.Set;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;


@Table(name="material")
@Entity
public class Material {
	
	public Material() {
	}

	public Material (Integer idmaterial) {
		this.idmaterial = idmaterial;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idmaterial;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "precio")
	private Double precio;
	
	@OneToMany(mappedBy = "material")
	@JsonIgnore
	private Set<Oritem> oritems;
	
	@OneToMany(mappedBy = "material")
	@JsonIgnore
	private Set<Inventario> inventarios;

	@OneToMany(mappedBy = "material")
	private Set<Oditem> oditems;

	@OneToMany(mappedBy = "material")
	private Set<Cuentaitem> cuentaitems;

	public Set<Oditem> getOditems() {
		return oditems;
	}

	public void setOditems(Set<Oditem> oditems) {
		this.oditems = oditems;
	}

	public Set<Cuentaitem> getCuentaitems() {
		return cuentaitems;
	}

	public void setCuentaitems(Set<Cuentaitem> cuentaitems) {
		this.cuentaitems = cuentaitems;
	}

	public Set<Oritem> getOritems() {
		return oritems;
	}

	public void setOritems(Set<Oritem> oritems) {
		this.oritems = oritems;
	}

	public Set<Inventario> getInventarios() {
		return inventarios;
	}

	public void setInventarios(Set<Inventario> inventarios) {
		this.inventarios = inventarios;
	}

	public Integer getIdmaterial() {
		return idmaterial;
	}

	public void setIdmaterial(Integer idmaterial) {
		this.idmaterial = idmaterial;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idmaterial == null) ? 0 : idmaterial.hashCode());
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
		Material other = (Material) obj;
		if (idmaterial == null) {
			if (other.idmaterial != null)
				return false;
		} else if (!idmaterial.equals(other.idmaterial))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return getNombre();
	}
	
	
}
