package com.logistica.demo.payload;


import com.logistica.demo.model.Cuentaitem;
import com.logistica.demo.model.Inventario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Stream;

public class ResCuentaCuadratura {

    private static final Logger log = LoggerFactory.getLogger(ResCuentaCuadratura.class);

    public ResCuentaCuadratura(Cuentaitem cuentaitem, List<Inventario> inventarioZona) {
        this.idmaterial = cuentaitem.getMaterial().getIdmaterial();
        this.nombre = cuentaitem.getMaterial().getNombre();
        this.idpos = cuentaitem.getPos().getIdpos();
        this.ubicacion = cuentaitem.getPos().toString();
        this.cantidadCuenta = cuentaitem.getCantidad();
         inventarioZona.stream()
                .filter(i -> i.getMaterial().getIdmaterial().equals(this.getIdmaterial())
                        && i.getPos().getIdpos().equals(this.getIdpos()))
                .map(i -> i.getCantidadtotal())
                        .findFirst()
                        .ifPresent(iz -> this.cantidadInventario=iz);
        this.idcuentaitem = cuentaitem.getIdcuentaitem();
    }

    public ResCuentaCuadratura(Inventario inventario) {
        this.idinventario = inventario.getIdinventario();
        this.idmaterial = inventario.getMaterial().getIdmaterial();
        this.nombre = inventario.getMaterial().getNombre();
        this.idpos = inventario.getPos().getIdpos();
        this.ubicacion = inventario.getPos().toString();
        this.cantidadCuenta = 0d;
        this.cantidadInventario = inventario.getCantidadtotal();
        this.idcuentaitem = null;
    }

    private Integer idcuentaitem;

    private Integer idinventario;

    private Integer idmaterial;

    private String nombre;

    private Double cantidadCuenta;

    private Double cantidadInventario;

    private Integer idpos;

    private String ubicacion;

    public Integer getIdinventario() {
        return idinventario;
    }

    public void setIdinventario(Integer idinventario) {
        this.idinventario = idinventario;
    }

    public Integer getIdcuentaitem() {
        return idcuentaitem;
    }

    public void setIdcuentaitem(Integer idcuentaitem) {
        this.idcuentaitem = idcuentaitem;
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

    public Double getCantidadCuenta() {
        return cantidadCuenta;
    }

    public void setCantidadCuenta(Double cantidadCuenta) {
        this.cantidadCuenta = cantidadCuenta;
    }

    public Double getCantidadInventario() {
        return cantidadInventario;
    }

    public void setCantidadInventario(Double cantidadInventario) {
        this.cantidadInventario = cantidadInventario;
    }

    public Integer getIdpos() {
        return idpos;
    }

    public void setIdpos(Integer idpos) {
        this.idpos = idpos;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
}
