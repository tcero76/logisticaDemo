package com.logistica.demo.payload;

import com.logistica.demo.model.Inventario;

import java.util.Map;

public class OdRequest {

    private Double cantidadDespacho;

    private Integer idInventario;

    private Map<String,String> material;

    private Double cantidad;

    private Map<String, String> pos;

    public Map<String, String> getMaterial() {
        return material;
    }

    public void setMaterial(Map<String, String> material) {
        this.material = material;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Map<String, String> getPos() {
        return pos;
    }

    public void setPos(Map<String, String> pos) {
        this.pos = pos;
    }

    public Integer getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(Integer idInventario) {
        this.idInventario = idInventario;
    }

    public Double getCantidadDespacho() {
        return cantidadDespacho;
    }

    public void setCantidadDespacho(Double cantidadDespacho) {
        this.cantidadDespacho = cantidadDespacho;
    }
}
