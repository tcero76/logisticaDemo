package com.logistica.demo.repository;

import com.logistica.demo.model.Cuenta;
import com.logistica.demo.model.Cuentaitem;
import com.logistica.demo.model.Material;
import com.logistica.demo.model.Pos;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface CuentaItemRepo extends JpaRepository<Cuentaitem, Integer> {

    public List<Cuentaitem> findByPosAndMaterialAndCuenta(Pos pos, Material material, Cuenta cuenta);

}
