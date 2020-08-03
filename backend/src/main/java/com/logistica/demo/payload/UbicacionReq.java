package com.logistica.demo.payload;

public class UbicacionReq {

    public UbicacionReq(Integer idoritem, Integer idpos) {
        this.idoritem = idoritem;
        this.idpos = idpos;
    }

    private Integer idoritem;

    private Integer idpos;

    public Integer getIdoritem() {
        return idoritem;
    }

    public void setIdoritem(Integer idoritem) {
        this.idoritem = idoritem;
    }

    public Integer getIdpos() {
        return idpos;
    }

    public void setIdpos(Integer idpos) {
        this.idpos = idpos;
    }
}
