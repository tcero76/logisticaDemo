package com.logistica.demo.service;

import com.logistica.demo.dao.CuentaItemDao;
import com.logistica.demo.exception.NotFoundException;
import com.logistica.demo.model.*;
import com.logistica.demo.payload.ReqCuentaItem;
import com.logistica.demo.repository.CuentaItemRepo;
import com.logistica.demo.repository.CuentaRepo;
import com.logistica.demo.repository.MaterialRepo;
import com.logistica.demo.repository.PosRepo;
import com.logistica.demo.util.StatusCuentaItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CuentaItemServiceImpl implements CuentaItemService {

    private static final Logger log = LoggerFactory.getLogger(CuentaItemServiceImpl.class);

    @Autowired
    private CuentaItemDao cuentaItemDao;

    @Autowired
    private CuentaItemRepo cuentaitemRepo;

    @Autowired
    private MaterialRepo materialRepo;

    @Autowired
    private PosRepo posRepo;

    @Autowired
    private CuentaRepo cuentaRepo;

    @Override
    @Transactional
    public List<Cuentaitem> findByCuenta(Integer idcuenta, Integer rows, Integer offset) {
        return cuentaItemDao
                .findByCuenta(idcuenta, rows, offset)
                .orElseThrow(() -> new NotFoundException("idcuenta","Cuenta", idcuenta));
    }

    @Override
    @Transactional
    public List<Cuentaitem> findByCuenta(Integer idcuenta) {
        return cuentaItemDao
                .findByCuenta(idcuenta)
                .orElseThrow(() -> new NotFoundException("idcuenta","Cuenta", idcuenta));
    }

    @Override
    @Transactional
    public Cuentaitem addCuentaItem(ReqCuentaItem reqCuentaItem, Usuario usuario) {
        Material material = materialRepo.findById(reqCuentaItem.getIdmaterial())
                .orElseThrow(() -> new NotFoundException("idmaterial", "Material", reqCuentaItem.getIdmaterial()));
        Pos pos = posRepo.findById(reqCuentaItem.getIdpos())
                .orElseThrow(() -> new NotFoundException("idpos", "Pos", reqCuentaItem.getIdpos()));
        Cuenta cuenta = cuentaRepo.findById(reqCuentaItem.getIdcuenta())
                .orElseThrow(() -> new NotFoundException("idcuenta", "Cuenta", reqCuentaItem.getIdcuenta()));

        List<Cuentaitem> cuentaitems = cuentaitemRepo.findByPosAndMaterialAndCuenta(pos, material, cuenta);

        Cuentaitem cuentaitem = null;
        if(cuentaitems.size()==0) {
            cuentaitem = new Cuentaitem(material, reqCuentaItem.getCantidad(), StatusCuentaItem.PENDIENTE,pos,
                    usuario, cuenta, null);
        } else {
            cuentaitems.get(0).setCantidad(reqCuentaItem.getCantidad());
            cuentaitem = cuentaitems.get(0);
        }

        return cuentaitemRepo.save(cuentaitem);
    }

    @Override
    @Transactional
    public Long countByCuenta(Integer idcuenta) {
        return cuentaItemDao.countByIdcuenta(idcuenta);
    }

}
