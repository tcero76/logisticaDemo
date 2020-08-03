package com.logistica.demo.service;

import com.logistica.demo.dao.AlmacenDao;
import com.logistica.demo.repository.AlmacenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.logistica.demo.model.Almacen;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlmacenServiceImpl implements AlmacenService {

    @Autowired
    private AlmacenDao almacenDao;

    @Autowired
    private AlmacenRepo almacenRepo;

    @Override
    @Transactional
    public Almacen findByAlmacen(Almacen almacen) {
        return almacenDao.findByAlmacen(almacen.getIdalmacen());
    }

    @Override
    @Transactional
    public Almacen guardar(Almacen almacen) {
        return almacenRepo.save(almacen);
    }
}
