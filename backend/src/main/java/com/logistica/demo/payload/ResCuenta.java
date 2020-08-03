package com.logistica.demo.payload;

import com.logistica.demo.model.Cuenta;
import com.logistica.demo.model.Zona;

import java.util.Date;

public class ResCuenta {

    public ResCuenta(Cuenta cuenta) {
        this.idcuenta = cuenta.getIdcuenta();
        this.fecharegistro = cuenta.getFecharegistro();
        this.idzona = cuenta.getZona().getIdzona();
        this.nombre = cuenta.getZona().getNombre();
        this.status = cuenta.getStatus();
    }

    private Integer idcuenta;

    private Date fecharegistro;

    private Integer idzona;

    private String nombre;

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getIdcuenta() {
        return idcuenta;
    }

    public void setIdcuenta(Integer idcuenta) {
        this.idcuenta = idcuenta;
    }

    public Date getFecharegistro() {
        return fecharegistro;
    }

    public void setFecharegistro(Date fecharegistro) {
        this.fecharegistro = fecharegistro;
    }

    public Integer getIdzona() {
        return idzona;
    }

    public void setIdzona(Integer idzona) {
        this.idzona = idzona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
