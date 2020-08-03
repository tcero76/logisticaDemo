package com.logistica.demo.service;

import com.logistica.demo.model.Cuentaitem;
import com.logistica.demo.model.Usuario;
import com.logistica.demo.payload.ReqCuentaItem;

import java.util.Arrays;
import java.util.List;

public interface CuentaItemService {
    public List<Cuentaitem> findByCuenta(Integer idcuenta, Integer rows, Integer offset);

    public Cuentaitem addCuentaItem(ReqCuentaItem reqCuentaItem, Usuario usuario);

    public Long countByCuenta(Integer idcuenta);

    public List<Cuentaitem> findByCuenta(Integer idcuenta);
}
