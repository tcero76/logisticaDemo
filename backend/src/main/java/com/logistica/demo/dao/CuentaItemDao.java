package com.logistica.demo.dao;

import com.logistica.demo.model.Cuentaitem;

import java.util.List;
import java.util.Optional;

public interface CuentaItemDao {
    public Optional<List<Cuentaitem>> findByCuenta(Integer idcuenta, Integer rows, Integer offset);

    public Long countByIdcuenta(Integer idcuenta);

    Optional<List<Cuentaitem>> findByCuenta(Integer idcuenta);
}

