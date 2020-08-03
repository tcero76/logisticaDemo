package com.logistica.demo.payload;

import com.logistica.demo.model.Inventario;

import javax.validation.constraints.NotNull;
import java.util.Map;

public class OdReq {

    public OdReq(Double cantidadDespacho, Integer idinventario, Integer idmaterial, Double cantidad, Integer idpos) {
        this.cantidadDespacho = cantidadDespacho;
        this.idmaterial = idmaterial;
        this.idpos = idpos;
    }

    @NotNull
    private Double cantidadDespacho;

    @NotNull
    private Integer idmaterial;

    @NotNull
    private Integer idpos;

    public Integer getIdmaterial() {
        return idmaterial;
    }

    public void setIdmaterial(Integer idmaterial) {
        this.idmaterial = idmaterial;
    }

    public Integer getIdpos() {
        return idpos;
    }

    public void setIdpos(Integer idpos) {
        this.idpos = idpos;
    }

    public Double getCantidadDespacho() {
        return cantidadDespacho;
    }

    public void setCantidadDespacho(Double cantidadDespacho) {
        this.cantidadDespacho = cantidadDespacho;
    }
}
