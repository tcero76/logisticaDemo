package com.logistica.demo.service;

import com.logistica.demo.model.Material;
import com.logistica.demo.model.Pos;

public interface InventarioService {
    public Integer ultInventario(Material material, Pos pos);
}
