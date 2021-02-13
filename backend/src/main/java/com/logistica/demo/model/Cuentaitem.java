package com.logistica.demo.model;

import com.logistica.demo.payload.ReqCuentaItem;
import com.logistica.demo.util.StatusCuentaItem;

import javax.persistence.*;

@Table(name="cuentaitem")
@Entity
public class Cuentaitem {

    public Cuentaitem() {
    }

    public Cuentaitem(Material material, Double cantidad, StatusCuentaItem status, Pos pos, Usuario usuario, Cuenta cuenta, Inventario inventario) {
        this.material = material;
        this.cantidad = cantidad;
        this.status = status;
        this.pos = pos;
        this.usuario = usuario;
        this.cuenta = cuenta;
        this.inventario = inventario;
    }

    public Cuentaitem(ReqCuentaItem reqCuentaItem, Usuario usuario) {
        this.material = new Material(reqCuentaItem.getIdmaterial());
        this.cantidad = reqCuentaItem.getCantidad();
        this.usuario = usuario;
        this.status = StatusCuentaItem.PENDIENTE;
        this.pos = new Pos(reqCuentaItem.getIdpos());
        this.cuenta = new Cuenta(reqCuentaItem.getIdcuenta());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idcuentaitem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idmaterial")
    private Material material;

    @Column(name = "cantidad")
    private Double cantidad;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusCuentaItem status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idpos")
    private Pos pos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idusuario")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idcuenta")
    private Cuenta cuenta;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "cuentaitem")
    private Inventario inventario;

    public StatusCuentaItem getStatus() {
        return status;
    }

    public void setStatus(StatusCuentaItem status) {
        this.status = status;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public Integer getIdcuentaitem() {
        return idcuentaitem;
    }

    public void setIdcuentaitem(Integer idcuentaitem) {
        this.idcuentaitem = idcuentaitem;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
