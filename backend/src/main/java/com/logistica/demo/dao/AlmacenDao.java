package com.logistica.demo.dao;

import com.logistica.demo.model.Almacen;

public interface AlmacenDao {
    Almacen findByAlmacen(Integer idalmacen);
}