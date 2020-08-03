package com.logistica.demo.payload;

import com.logistica.demo.model.Cuentaitem;

public class ResCuentaItem {

    public ResCuentaItem() {
    }

    public ResCuentaItem(Cuentaitem cuentaitem) {
        this.idcuentaitem = cuentaitem.getIdcuentaitem();
        this.idmaterial = cuentaitem.getMaterial().getIdmaterial();
        this.nombre = cuentaitem.getMaterial().getNombre();
        this.ubicacion = cuentaitem.getPos().toString();
        this.idpos = cuentaitem.getPos().getIdpos();
        this.cantidad = cuentaitem.getCantidad();
        this.usuarionombre = cuentaitem.getUsuario().getNombre();
    }

    private String usuarionombre;

    private Integer idcuentaitem;

    private Integer idmaterial;

    private String nombre;

    private String ubicacion;

    private Integer idpos;

    private Double cantidad;

    public String getUsuarionombre() {
        return usuarionombre;
    }

    public void setUsuarionombre(String usuarionombre) {
        this.usuarionombre = usuarionombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Integer getIdpos() {
        return idpos;
    }

    public void setIdpos(Integer idpos) {
        this.idpos = idpos;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
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

    public Integer getIdcuentaitem() {
        return idcuentaitem;
    }

    public void setIdcuentaitem(Integer idcuentaitem) {
        this.idcuentaitem = idcuentaitem;
    }

    @Override
    public String toString() {
        return "ResCuentaItem{" +
                "usuarionombre='" + usuarionombre + '\'' +
                ", idcuentaitem=" + idcuentaitem +
                ", idmaterial=" + idmaterial +
                ", nombre='" + nombre + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                ", idpos=" + idpos +
                ", cantidad=" + cantidad +
                '}';
    }
}
