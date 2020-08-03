package com.logistica.demo.repository;

import com.logistica.demo.model.Almacen;
import com.logistica.demo.model.Inventario;
import com.logistica.demo.model.Material;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventarioRepo extends JpaRepository<Inventario,Integer> {
}
