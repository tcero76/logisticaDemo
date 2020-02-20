package com.logistica.demo.dao;

import com.logistica.demo.model.Almacen;
import com.logistica.demo.model.Inventario;
import com.logistica.demo.model.Material;
import com.logistica.demo.model.Pos;

import java.util.List;

public interface InventarioDao {

    public Integer ultInventario(Material material, Pos pos);
    public List<Inventario> listarInventario(Almacen almacen);
}
