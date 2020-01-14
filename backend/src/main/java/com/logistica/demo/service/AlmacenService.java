package com.logistica.demo.service;

import com.logistica.demo.model.Almacen;
import org.springframework.stereotype.Service;

@Service
public interface AlmacenService {
    public Almacen listar();
}