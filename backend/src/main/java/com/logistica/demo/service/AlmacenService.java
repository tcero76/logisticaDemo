package com.logistica.demo.service;

import com.logistica.demo.model.Almacen;
import com.logistica.demo.model.Zona;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AlmacenService {
    Almacen findByAlmacen(Almacen almacen);

    Almacen guardar(Almacen almacen);
}