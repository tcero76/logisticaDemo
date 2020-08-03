package com.logistica.demo.repository;

import com.logistica.demo.model.Almacen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlmacenRepo extends JpaRepository<Almacen,Integer> {
}
