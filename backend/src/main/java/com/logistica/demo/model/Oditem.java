package com.logistica.demo.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "oditem")
public class Oditem {

    public Oditem() {
    }

    public Oditem(Double cantidad, Date fecharegistro, Material material, Usuario usuario, Od od, Inventario inventario) {
        this.cantidad = cantidad;
        this.fecharegistro = fecharegistro;
        this.material = material;
        this.usuario = usuario;
        this.od = od;
        this.inventario = inventario;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idoditem;

    @Column(name = "cantidad")
    private Double cantidad;

    @Column(name = "fecharegistro")
    private Date fecharegistro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idmaterial")
    private Material material;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idusuario")
    private Usuario usuario;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "idod")
    private Od od;

    @OneToOne(mappedBy = "oditem",cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private Inventario inventario;

    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }

    public Integer getIdoditem() {
        return idoditem;
    }

    public void setIdoditem(Integer idoditem) {
        this.idoditem = idoditem;
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

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Od getOd() {
        return od;
    }

    public void setOd(Od od) {
        this.od = od;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Oditem oditem = (Oditem) o;

        return idoditem.equals(oditem.idoditem);
    }

    @Override
    public int hashCode() {
        return idoditem.hashCode();
    }

    @Override
    public String toString() {
        return "Oditem{" +
                "idoditem=" + idoditem +
                '}';
    }
}


