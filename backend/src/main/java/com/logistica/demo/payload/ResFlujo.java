package com.logistica.demo.payload;

import com.logistica.demo.model.Inventario;

import java.util.Date;

public class ResFlujo {

    public ResFlujo(Inventario inventario) {
        this.idmaterial = inventario.getMaterial().getIdmaterial();
        this.nombre = inventario.getMaterial().getNombre();
        this.cantidad = inventario.getCantidad();
        this.total = inventario.getCantidadtotal();
        this.pos = inventario.getPos().toString();
        this.positivo = inventario.getOditem()==null;
        this.idinventario = inventario.getIdinventario();
        this.fecharegistro = inventario.getFecharegistro();
    }

    private Date fecharegistro;

    private Integer idmaterial;

    private String nombre;

    private Double cantidad;

    private Double total;

    private String pos;

    private Boolean positivo;

    private Integer idinventario;

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Date getFecharegistro() {
        return fecharegistro;
    }

    public void setFecharegistro(Date fecharegistro) {
        this.fecharegistro = fecharegistro;
    }

    public Integer getIdinventario() {
        return idinventario;
    }

    public void setIdinventario(Integer idinventario) {
        this.idinventario = idinventario;
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

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public Boolean getPositivo() {
        return positivo;
    }

    public void setPositivo(Boolean positivo) {
        this.positivo = positivo;
    }
}