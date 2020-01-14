package com.logistica.demo.dao;

import com.logistica.demo.model.Material;
import com.logistica.demo.model.Pos;

public interface InventarioDao {

    public Integer ultInventario(Material material, Pos pos);
}
