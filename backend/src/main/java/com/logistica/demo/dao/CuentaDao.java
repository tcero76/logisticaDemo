package com.logistica.demo.dao;

import com.logistica.demo.model.Cuenta;

import java.util.List;
import java.util.Optional;

public interface CuentaDao {

    public List<Cuenta> findAll();

    public Cuenta load(Integer idcuenta);

    public Optional<Cuenta> findById(Integer idcuenta);
}
