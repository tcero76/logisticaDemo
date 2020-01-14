package com.logistica.demo.service;

import com.logistica.demo.dao.InventarioDao;
import com.logistica.demo.model.Material;
import com.logistica.demo.model.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventarioServiceImpl implements InventarioService {

    @Autowired
    private InventarioDao inventarioDao;

    @Override
    @Transactional
    public Integer ultInventario(Material material, Pos pos) {
        return inventarioDao.ultInventario(material,pos);
    }
}
