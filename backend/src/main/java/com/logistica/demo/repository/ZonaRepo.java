package com.logistica.demo.repository;

import java.util.List;
import java.util.Optional;

import com.logistica.demo.model.Zona;
import org.springframework.data.jpa.repository.JpaRepository;

import com.logistica.demo.model.Almacen;
import org.springframework.stereotype.Repository;

public interface ZonaRepo extends JpaRepository<Zona,Integer> {
	
	public List<Zona> findByAlmacen(Almacen almacen);
	public Zona findByIdzona(Integer idzona);
}
