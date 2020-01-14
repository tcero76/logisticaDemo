package com.logistica.demo.service;

import com.logistica.demo.dao.AlmacenDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.logistica.demo.model.Almacen;

@Service
public class AlmacenServiceImpl implements AlmacenService {

    @Autowired
    private AlmacenDao almacendao;

    @Override
    @Transactional
    public Almacen listar() {
        return almacendao.listar();
    }
}
