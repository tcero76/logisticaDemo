package com.logistica.demo.dao;

import com.logistica.demo.model.*;

import java.util.List;
import java.util.Optional;

public interface InventarioDao {

    public Optional<Integer> ultInventario(Integer idmaterial, Integer idpos);
    public Optional<List<Inventario>> listarInventario(Almacen almacen, Integer idmaterial);
    public List<Inventario> listarInventario(Integer idzona);
    public Optional<List<Inventario>> listarByMaterial(Almacen almacen, Integer idmaterial, Integer pageSize, Integer offset);

    public Optional<Integer> countByAlmacenAndMaterial(Integer idalmacen, Integer idmaterial);
}
