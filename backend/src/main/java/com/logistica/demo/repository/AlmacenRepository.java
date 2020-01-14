package com.logistica.demo.repository;

import com.logistica.demo.model.Almacen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlmacenRepository extends JpaRepository<Almacen,Integer> {
}
