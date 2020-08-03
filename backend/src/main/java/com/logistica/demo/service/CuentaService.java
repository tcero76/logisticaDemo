package com.logistica.demo.service;

import com.logistica.demo.model.Cuenta;
import com.logistica.demo.model.Usuario;

import java.util.List;

public interface CuentaService {

    public List<Cuenta> findPendiente();

    public Cuenta load(Integer idcuenta);

    Cuenta addCuenta(Integer idzona, Usuario usuario);
}
