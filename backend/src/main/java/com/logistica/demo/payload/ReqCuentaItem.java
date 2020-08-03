package com.logistica.demo.payload;

import javax.validation.constraints.NotNull;

public class ReqCuentaItem {

    public ReqCuentaItem(Integer idcuenta, Integer idpos, Integer idmaterial, Double cantidad) {
        this.idcuenta = idcuenta;
        this.idpos = idpos;
        this.idmaterial = idmaterial;
        this.cantidad = cantidad;
    }

    @NotNull
    private Integer idcuenta;

    @NotNull
    private Integer idpos;

    @NotNull
    private Integer idmaterial;

    @NotNull
    private Double cantidad;

    public Integer getIdcuenta() {
        return idcuenta;
    }

    public void setIdcuenta(Integer idcuenta) {
        this.idcuenta = idcuenta;
    }

    public Integer getIdpos() {
        return idpos;
    }

    public void setIdpos(Integer idpos) {
        this.idpos = idpos;
    }

    public Integer getIdmaterial() {
        return idmaterial;
    }

    public void setIdmaterial(Integer idmaterial) {
        this.idmaterial = idmaterial;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }
}
