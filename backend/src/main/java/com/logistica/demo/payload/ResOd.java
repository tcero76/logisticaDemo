package com.logistica.demo.payload;

import com.logistica.demo.model.Od;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ResOd {

    public ResOd(Od od) {
        this.idod = od.getIdod();
        this.fecharegistro = od.getFecharegistros();
        this.oditems = od.getOditems().stream()
                .map(Oditem::new)
                .collect(Collectors.toList());
    }

    private Integer idod;

    private Date fecharegistro;

    private List<Oditem> oditems;

    private class Oditem {

        public Oditem(com.logistica.demo.model.Oditem oditem) {
            this.idoditem = oditem.getIdoditem();
            this.nombre = oditem.getMaterial().getNombre();
            this.idmaterial = oditem.getMaterial().getIdmaterial();
            this.cantidad = oditem.getCantidad();
        }

        private Integer idoditem;

        private String nombre;

        private Integer idmaterial;

        private Double cantidad;

        public Integer getIdoditem() {
            return idoditem;
        }

        public void setIdoditem(Integer idoditem) {
            this.idoditem = idoditem;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
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

    public Integer getIdod() {
        return idod;
    }

    public void setIdod(Integer idod) {
        this.idod = idod;
    }

    public Date getFecharegistro() {
        return fecharegistro;
    }

    public void setFecharegistro(Date fecharegistro) {
        this.fecharegistro = fecharegistro;
    }

    public List<Oditem> getOditems() {
        return oditems;
    }

    public void setOditems(List<Oditem> oditems) {
        this.oditems = oditems;
    }
}
