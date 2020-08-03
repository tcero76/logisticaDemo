package com.logistica.demo.repository;

import com.logistica.demo.model.Cuenta;
import com.logistica.demo.model.Zona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface CuentaRepo extends JpaRepository<Cuenta, Integer> {

    public Optional<Cuenta> findById(Integer idcuenta);

    public Optional<Cuenta> findByZonaAndStatus(Zona zona, String pendiente);
}
