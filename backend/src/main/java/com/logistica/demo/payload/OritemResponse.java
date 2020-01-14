package com.logistica.demo.payload;

import com.logistica.demo.model.Oritem;

public class OritemResponse {

    public OritemResponse(Oritem oritem) {
        this.idoritem = oritem.getIdoritem();
        this.idorec = oritem.getOrec().getIdorec();
        this.material = new Material(oritem);
        this.cantidad = oritem.getCantidad();
        this.pos = oritem.getPos();
    }

    private Integer pos;

    private Integer idoritem;

    private Integer idorec;

    private Material material;

    private Double cantidad;

    public Integer getIdoritem() {
        return idoritem;
    }

    private class Material {

        public Material(Oritem oritem) {
            this.idmaterial = oritem.getMaterial().getIdmaterial();
            this.nombre = oritem.getMaterial().getNombre();
        }

        private Integer idmaterial;

        private String nombre;

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
    }

    public Integer getPos() {
        return pos;
    }

    public void setPos(Integer pos) {
        this.pos = pos;
    }

    public void setIdoritem(Integer idoritem) {
        this.idoritem = idoritem;
    }

    public Integer getIdorec() {
        return idorec;
    }

    public void setIdorec(Integer idorec) {
        this.idorec = idorec;
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
}
