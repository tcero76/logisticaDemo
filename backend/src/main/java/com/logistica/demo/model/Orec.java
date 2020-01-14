package com.logistica.demo.model;

import java.util.Date;
import java.util.Set;

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

@Table(name = "orec")
@Entity
public class Orec {
	
	public Orec() {
	}

	public Orec(Usuario usuario, Date fecharegistro, String guiadespacho, Set<Oritem> oritems) {
		this.usuario = usuario;
		this.fecharegistro = fecharegistro;
		this.guiadespacho = guiadespacho;
		this.oritems = oritems;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idorec;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idusuario")
	private Usuario usuario;
	
	@Column(name = "fecharegistro")
	private Date fecharegistro;
	
	@Column(name = "guiadespacho")
	private String guiadespacho;
	
	@OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE,
			CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "orec")
	private Set<Oritem> oritems;

	public String getGuiadespacho() {
		return guiadespacho;
	}

	public void setGuiadespacho(String guiadespacho) {
		this.guiadespacho = guiadespacho;
	}

	public Set<Oritem> getOritems() {
		return oritems;
	}

	public void setOritems(Set<Oritem> oritems) {
		this.oritems = oritems;
	}

	public Integer getIdorec() {
		return idorec;
	}

	public void setIdorec(Integer idorec) {
		this.idorec = idorec;
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
		result = prime * result + ((idorec == null) ? 0 : idorec.hashCode());
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
		Orec other = (Orec) obj;
		if (idorec == null) {
			if (other.idorec != null)
				return false;
		} else if (!idorec.equals(other.idorec))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Orec [idorec=" + idorec + "]";
	}
	
}
