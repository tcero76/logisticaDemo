package com.logistica.demo.payload;

import com.logistica.demo.model.Inventario;
import com.logistica.demo.model.Material;
import com.logistica.demo.model.Pos;

import java.util.HashMap;
import java.util.Map;

public class ResInventario {

    public ResInventario(Inventario inventario) {
        this.idInventario = inventario.getIdinventario();
        this.material = new HashMap();
        this.material.put("idmaterial",inventario.getMaterial().getIdmaterial().toString());
        this.material.put("nombre",inventario.getMaterial().getNombre());
        this.cantidad = inventario.getCantidadtotal();
        this.pos = new HashMap<>();
        this.pos.put("idpos", inventario.getPos().getIdpos().toString());
        this.pos.put("nombre",inventario.getPos().toString());
    }

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
}
