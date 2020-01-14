package com.logistica.demo.repository;

import java.util.List;

import com.logistica.demo.model.Zona;
import org.springframework.data.jpa.repository.JpaRepository;

import com.logistica.demo.model.Almacen;

public interface ZonaRepository extends JpaRepository<Zona,Integer> {
	
	public List<Zona> findByAlmacen(Almacen almacen);
}
