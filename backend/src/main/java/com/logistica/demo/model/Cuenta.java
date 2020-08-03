package com.logistica.demo.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Table(name ="cuenta")
@Entity
public class Cuenta {

    public Cuenta() {
    }

    public Cuenta(Date fecharegistro, String status, Usuario usuario, Zona zona, Set<Cuentaitem> cuentaitems) {
        this.fecharegistro = fecharegistro;
        this.status = status;
        this.usuario = usuario;
        this.zona = zona;
        this.cuentaitems = cuentaitems;
    }

    public Cuenta(Integer idcuenta) {
        this.idcuenta = idcuenta;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idcuenta;

    @Column(name = "fecharegistro")
    private Date fecharegistro;

    @Column(name = "status")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idusuario")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idzona")
    private Zona zona;

    @OneToMany(mappedBy = "cuenta", cascade = {CascadeType.DETACH, CascadeType.MERGE,
                                                CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<Cuentaitem> cuentaitems;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getIdcuenta() {
        return idcuenta;
    }

    public void setIdcuenta(Integer idcuenta) {
        this.idcuenta = idcuenta;
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

    public Zona getZona() {
        return zona;
    }

    public void setZona(Zona zona) {
        this.zona = zona;
    }

    public Set<Cuentaitem> getCuentaitems() {
        return cuentaitems;
    }

    public void setCuentaitems(Set<Cuentaitem> cuentaitems) {
        this.cuentaitems = cuentaitems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cuenta cuenta = (Cuenta) o;

        return idcuenta != null ? idcuenta.equals(cuenta.idcuenta) : cuenta.idcuenta == null;
    }

    @Override
    public int hashCode() {
        return idcuenta != null ? idcuenta.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Cuenta{" +
                "idcuenta=" + idcuenta +
                '}';
    }
}
