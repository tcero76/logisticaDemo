package com.logistica.demo.payload;

public class ReqCuenta {

    public ReqCuenta() {
    }

    public ReqCuenta(Integer idzona) {
        this.idzona = idzona;
    }

    private Integer idzona;

    public Integer getIdzona() {
        return idzona;
    }

    public void setIdzona(Integer idzona) {
        this.idzona = idzona;
    }
}
