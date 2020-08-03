package com.logistica.demo.service;

import com.logistica.demo.dao.CuentaDao;
import com.logistica.demo.dao.InventarioDao;
import com.logistica.demo.exception.NotFoundException;
import com.logistica.demo.model.*;
import com.logistica.demo.payload.CuadrarCuentaReq;
import com.logistica.demo.payload.CuadrarInventarioReq;
import com.logistica.demo.payload.ResCuentaCuadratura;
import com.logistica.demo.payload.UbicacionReq;
import com.logistica.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class InventarioServiceImpl implements InventarioService {

    @Autowired
    private InventarioDao inventarioDao;

    @Autowired
    private InventarioRepo inventarioRepo;

    @Autowired
    private OritemRepo oritemRepo;

    @Autowired
    private PosRepo posRepo;

    @Autowired
    private CuentaItemRepo cuetaitemRepo;

    @Autowired
    private CuentaDao cuentaDao;

    @Autowired
    private CuentaRepo cuentaRepo;

    @Autowired
    private CuentaItemRepo cuentaitemRepo;

    @Override
    @Transactional
    public List<Inventario> listarInventario(Almacen almacen, Integer idmaterial) {
        return inventarioDao.listarInventario(almacen, idmaterial)
                .orElseThrow(() -> new NotFoundException("idmaterial", "Material", idmaterial));
    }

    @Override
    @Transactional
    public List<Inventario> listarByMaterial(Almacen almacen, Integer idmaterial, Integer pageSize, Integer offset) {
        return inventarioDao.listarByMaterial(almacen, idmaterial, pageSize, offset)
                .orElseThrow(() -> new NotFoundException("idmaterial", "Material", idmaterial));
    }

    @Override
    @Transactional
    public void saverAll(List<Inventario> inventarios) {
        inventarioRepo.saveAll(inventarios);
    }

    @Override
    @Transactional
    public Integer countByMaterial(Almacen almacen, Integer idmaterial) {
        return inventarioDao.countByAlmacenAndMaterial(almacen.getIdalmacen(), idmaterial)
                .orElseThrow(() -> new NotFoundException("idmaterial","Material",idmaterial));
    }

    @Override
    @Transactional
    public void addInventario(UbicacionReq ubicacionReq, Usuario usuario) {

//  Traer Orden de recepción referida en el request
    Oritem oritem = oritemRepo.findById(ubicacionReq.getIdoritem())
            .orElseThrow(() -> new NotFoundException("idoritem", "Oritem", ubicacionReq.getIdoritem()));

//  Traer posición referida en el request
        Pos pos = posRepo.findById(ubicacionReq.getIdpos())
                .orElseThrow(() -> new NotFoundException("idpos", "Pos", ubicacionReq.getIdpos()));

//       Buscar registro del material en dicha posición.
        Optional<Integer> idinventario = inventarioDao
                .ultInventario(oritem.getMaterial().getIdmaterial(), ubicacionReq.getIdpos());

        Inventario inventario = null;
        if(idinventario.isPresent()) {
//      agregar material en dicha posición.
            Inventario inventarioAnt = inventarioRepo.findById(idinventario.get()).get();
            inventario = new Inventario(inventarioAnt.getMaterial(), oritem.getCantidad(), pos, new Date(), usuario,
                    oritem.getCantidad() + inventarioAnt.getCantidadtotal(),
                    oritem, null, null);
        } else {
//       Generar el nuevo registro de inventario.
            inventario = new Inventario(oritem.getMaterial(), oritem.getCantidad(), pos, new Date(), usuario,
                    oritem.getCantidad(), oritem, null, null);
        }
        inventarioRepo.save(inventario);
    }

    @Override
    @Transactional
    public List<ResCuentaCuadratura> addInventario(CuadrarCuentaReq cuadrarCuentaReq, Usuario usuario) {
        Cuentaitem cuentaitem = cuetaitemRepo.findById(cuadrarCuentaReq.getIdcuentaitem())
                .orElseThrow(() -> new NotFoundException("idcuenta", "Cuentaitem", cuadrarCuentaReq.getIdcuentaitem()));
        Optional<Integer> idInventario = inventarioDao.ultInventario(cuentaitem.getMaterial().getIdmaterial(), cuentaitem.getPos().getIdpos());
        Inventario inventario = null;
        cuentaitem.setStatus("realizado");
        if(idInventario.isPresent()) {
            Inventario inventarioAnt = inventarioRepo.findById(idInventario.get()).get();
            inventario = new Inventario(cuentaitem.getMaterial(),
                    cuentaitem.getCantidad()-inventarioAnt.getCantidadtotal(),cuentaitem.getPos(),
                    new Date(), usuario, cuentaitem.getCantidad(), null, null, cuentaitem);
        } else {
            inventario = new Inventario(cuentaitem.getMaterial(), cuentaitem.getCantidad(), cuentaitem.getPos(),
                    new Date(), usuario, cuentaitem.getCantidad(),null, null, cuentaitem);
        }
        inventarioRepo.save(inventario);
        return findCuadratura(cuentaitem.getCuenta().getIdcuenta());
    }

    @Override
    @Transactional
    public List<ResCuentaCuadratura> findCuadratura(Integer idcuenta) {
        Cuenta cuenta = cuentaDao.findById(idcuenta)
                .orElseThrow(() -> new NotFoundException("idcuenta", "Cuenta" , idcuenta));

        List<Inventario> inventarioZona = listarInventario(cuenta.getZona().getIdzona());

        List<ResCuentaCuadratura> inventarioCuenta = cuenta.getCuentaitems().stream()
                .map(c -> new ResCuentaCuadratura(c,inventarioZona))
                .collect(Collectors.toList());

        List<ResCuentaCuadratura> inventarioNoCuenta = new ArrayList<ResCuentaCuadratura>();
        for(Inventario i:inventarioZona) {
            Boolean encontrado = false;
            for(Cuentaitem ci:cuenta.getCuentaitems()) {
                if(i.getMaterial().getIdmaterial() == ci.getMaterial().getIdmaterial() &&
                        i.getPos().getIdpos() == ci.getPos().getIdpos()) {
                    encontrado=true;
                }
            }
            if(!encontrado) {
                inventarioNoCuenta.add(new ResCuentaCuadratura(i));
            }
        }
        inventarioCuenta.addAll(inventarioNoCuenta);
        return inventarioCuenta;
    }

    @Override
    @Transactional
    public List<ResCuentaCuadratura> addInventario(CuadrarInventarioReq cuadrarCuentaReq, Usuario usuario) {
        Cuenta cuenta = cuentaRepo.findById(cuadrarCuentaReq.getIdcuenta())
                .orElseThrow(() -> new NotFoundException("idCuenta","Cuenta", cuadrarCuentaReq.getIdcuenta()));
        Inventario inventario = inventarioRepo.findById(cuadrarCuentaReq.getIdinventario())
                .orElseThrow(() -> new NotFoundException("idinventario","Inventario", cuadrarCuentaReq.getIdinventario()));

        Cuentaitem cuentaitem = new Cuentaitem(inventario.getMaterial(), inventario.getCantidadtotal(),
                "realizado", inventario.getPos(), usuario, cuenta, inventario);
        cuentaitemRepo.save(cuentaitem);

        return findCuadratura(cuadrarCuentaReq.getIdcuenta());
    }

    @Override
    @Transactional
    public List<Inventario> listarInventario(Integer idzona) {
        return inventarioDao.listarInventario(idzona);
    }

}
