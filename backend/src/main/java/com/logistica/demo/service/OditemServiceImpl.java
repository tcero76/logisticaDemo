package com.logistica.demo.service;

import com.logistica.demo.dao.InventarioDao;
import com.logistica.demo.exception.BadRequestException;
import com.logistica.demo.exception.NotFoundException;
import com.logistica.demo.model.*;
import com.logistica.demo.payload.OdReq;
import com.logistica.demo.repository.InventarioRepo;
import com.logistica.demo.repository.OditemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OditemServiceImpl implements OditemService {

    @Autowired
    public OditemRepo oditemRepo;

    @Autowired
    public InventarioDao inventarioDao;

    @Autowired
    public InventarioRepo inventarioRepo;

    @Override
    @Transactional
    public Od addOditems(List<OdReq> odReq, Usuario usuario) {
        Od od = new Od();
        od.setFecharegistros(new Date());
        od.setUsuario(usuario);
        List<Oditem> oditems = null;
        oditems = odReq.stream()
                .map(odr -> {
                    Integer idinventario = inventarioDao
                            .ultInventario(odr.getIdmaterial(), odr.getIdpos())
                            .orElseThrow(() -> new NotFoundException("idmaterial e idpos","Material y Posición",odr.getIdmaterial()));
                    Inventario ultinv = inventarioRepo.findById(idinventario).get();
                    Oditem oditem = new Oditem(odr.getCantidadDespacho(), new Date(),
                            ultinv.getMaterial(), usuario, od,null);
                    Inventario inv = new Inventario(new Material(odr.getIdmaterial()),
                            odr.getCantidadDespacho(), ultinv.getPos(), new Date(), usuario,
                            ultinv.getCantidadtotal()-odr.getCantidadDespacho(),
                            null, oditem, null);
                    oditem.setInventario(inv);
                    if (inv.getCantidadtotal()<0) {
                        throw new BadRequestException(oditem);
                    }
                    return oditem;
                })
                .collect(Collectors.toList());
        oditemRepo.saveAll(oditems);
        return od;
    }

}
