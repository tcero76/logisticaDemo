package com.logistica.demo.service;

import com.logistica.demo.dao.InventarioDao;
import com.logistica.demo.model.Almacen;
import com.logistica.demo.model.Inventario;
import com.logistica.demo.model.Material;
import com.logistica.demo.model.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InventarioServiceImpl implements InventarioService {

    @Autowired
    private InventarioDao inventarioDao;

    @Override
    @Transactional
    public Integer ultInventario(Material material, Pos pos) {
        return inventarioDao.ultInventario(material,pos);
    }

    @Override
    @Transactional
    public List<Inventario> listarInventario(Almacen almacen) {
        return inventarioDao.listarInventario(almacen);
    }

    @Override
    @Transactional
    public List<Inventario> listarByMaterial(Almacen almacen, Integer idmaterial) {
        return inventarioDao.listarByMaterial(almacen, idmaterial);
    }
}
