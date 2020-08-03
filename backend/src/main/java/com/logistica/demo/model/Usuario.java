package com.logistica.demo.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Table(name = "usuario")
@Entity
public class Usuario implements UserDetails {
	
	public Usuario() {
	}

	public Usuario(Integer idusuario) {
		this.idusuario = idusuario;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idusuario;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "clave")
	private String clave;
	
	@Column(name = "activo")
	private Boolean activo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idrol")
	private Rol rol;

	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
			CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
	@JoinColumn(name = "idalmacen")
	private Almacen almacen;
	
	@OneToMany(mappedBy = "usuario")
	private Set<Orec> orecs;
	
	@OneToMany(mappedBy = "usuario")
	private Set<Oritem> oritems;
	
	@OneToMany(mappedBy = "usuario")
	private Set<Almacen> almacenes;
	
	@OneToMany(mappedBy = "usuario")
	private Set<Zona> zonas;
	
	@OneToMany(mappedBy = "usuario")
	private Set<Nivel> niveles;
	
	@OneToMany(mappedBy = "usuario")
	private Set<Pos> poses;
	
	@OneToMany(mappedBy = "usuario")
	private Set<Inventario> inventarios;

	@OneToMany(mappedBy = "usuario")
	private Set<Oditem> oditems;

	@OneToMany(mappedBy = "usuario")
	private Set<Od> ods;

	@OneToMany(mappedBy = "usuario")
	private Set<Cuenta> cuentas;

	@OneToMany(mappedBy = "usuario")
	private Set<Cuentaitem> cuentaitems;

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public Integer getIdusuario() {
		return idusuario;
	}

	public void setIdusuario(Integer idusuario) {
		this.idusuario = idusuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}
	
	public Set<Orec> getOrecs() {
		return orecs;
	}

	public void setOrecs(Set<Orec> orecs) {
		this.orecs = orecs;
	}

	public Set<Oritem> getOritems() {
		return oritems;
	}

	public void setOritems(Set<Oritem> oritems) {
		this.oritems = oritems;
	}

	public Set<Almacen> getAlmacenes() {
		return almacenes;
	}

	public void setAlmacenes(Set<Almacen> almacenes) {
		this.almacenes = almacenes;
	}

	public Set<Zona> getZonas() {
		return zonas;
	}

	public void setZonas(Set<Zona> zonas) {
		this.zonas = zonas;
	}

	public Set<Nivel> getNiveles() {
		return niveles;
	}

	public void setNiveles(Set<Nivel> niveles) {
		this.niveles = niveles;
	}

	public Set<Pos> getPoses() {
		return poses;
	}

	public void setPoses(Set<Pos> poses) {
		this.poses = poses;
	}

	public Set<Inventario> getInventarios() {
		return inventarios;
	}

	public void setInventarios(Set<Inventario> inventarios) {
		this.inventarios = inventarios;
	}

	public Almacen getAlmacen() {
		return almacen;
	}

	public void setAlmacen(Almacen almacen) {
		this.almacen = almacen;
	}

	public Set<Oditem> getOditems() {
		return oditems;
	}

	public void setOditems(Set<Oditem> oditems) {
		this.oditems = oditems;
	}

	public Set<Od> getOds() {
		return ods;
	}

	public void setOds(Set<Od> ods) {
		this.ods = ods;
	}

	public Set<Cuenta> getCuentas() {
		return cuentas;
	}

	public void setCuentas(Set<Cuenta> cuentas) {
		this.cuentas = cuentas;
	}

	public Set<Cuentaitem> getCuentaitems() {
		return cuentaitems;
	}

	public void setCuentaitems(Set<Cuentaitem> cuentaitems) {
		this.cuentaitems = cuentaitems;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idusuario == null) ? 0 : idusuario.hashCode());
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
		Usuario other = (Usuario) obj;
		if (idusuario == null) {
			if (other.idusuario != null)
				return false;
		} else if (!idusuario.equals(other.idusuario))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Usuario [nombre=" + nombre + "]";
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(this.getRol());
	}

	@Override
	public String getPassword() {
		return this.getClave();
	}

	@Override
	public String getUsername() {
		return this.getNombre();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.getActivo();
	}
	
}
