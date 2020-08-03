package com.logistica.demo.payload;

import com.logistica.demo.model.Orec;

import java.util.Set;
import java.util.stream.Collectors;

public class OrecRes {

    private final Set<Oritem> oritems;
    private String guiadedespacho;

    public OrecRes(Orec orec) {
        this.guiadedespacho = orec.getGuiadespacho();
        this.oritems = orec.getOritems().stream()
                .map(Oritem::new)
                .collect(Collectors.toSet());
    }

    class Oritem {

        private Integer idmaterial;
        private Double cantidad;

        public Oritem(com.logistica.demo.model.Oritem oritem) {
            this.cantidad = oritem.getCantidad();
            this.idmaterial = oritem.getMaterial().getIdmaterial();
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

    public Set<Oritem> getOritems() {
        return oritems;
    }

    public String getGuiadedespacho() {
        return guiadedespacho;
    }

    public void setGuiadedespacho(String guiadedespacho) {
        this.guiadedespacho = guiadedespacho;
    }
}
