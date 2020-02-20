package com.logistica.demo.service;

import com.logistica.demo.dao.OdDao;
import com.logistica.demo.model.Od;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OdServiceImpl implements OdService {

    @Autowired
    public OdDao odDao;


    @Override
    @Transactional
    public Od findById(Integer id) {
        return odDao.findById(id);
    }
}
