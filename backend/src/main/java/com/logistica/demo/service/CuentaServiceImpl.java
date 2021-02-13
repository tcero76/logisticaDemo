package com.logistica.demo.service;

import com.logistica.demo.dao.CuentaDao;
import com.logistica.demo.exception.BadRequestException;
import com.logistica.demo.exception.NotFoundException;
import com.logistica.demo.model.Cuenta;
import com.logistica.demo.model.Usuario;
import com.logistica.demo.model.Zona;
import com.logistica.demo.repository.CuentaRepo;
import com.logistica.demo.repository.ZonaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class CuentaServiceImpl implements CuentaService {

    @Autowired
    private CuentaRepo cuentaRepo;

    @Autowired
    private CuentaDao cuentaDao;

    @Autowired
    private ZonaRepo zonaRepo;

    @Override
    @Transactional
    public List<Cuenta> findPendiente() {
        return cuentaDao.findAll();
    }

    @Override
    @Transactional
    public Cuenta load(Integer idcuenta) {
        return cuentaDao.load(idcuenta);
    }

    @Override
    @Transactional
    public Cuenta addCuenta(Integer idzona, Usuario usuario) {
        Zona zona = zonaRepo.findById(idzona)
                .orElseThrow(() -> new NotFoundException("idzona", "Zona", idzona));
        cuentaRepo.findByZonaAndStatus(zona,"PENDIENTE")
                .ifPresent(cuenta -> {
                    throw new BadRequestException(cuenta);
                });
        Cuenta cuenta = new Cuenta(new Date(), "PENDIENTE", usuario, zona, null);
        return cuentaRepo.save(cuenta);
    }
}
