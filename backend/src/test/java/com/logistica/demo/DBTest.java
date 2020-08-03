package com.logistica.demo;

import com.logistica.demo.model.*;
import com.logistica.demo.payload.OdReq;
import com.logistica.demo.payload.ReqCuentaItem;
import com.logistica.demo.payload.UbicacionReq;
import com.logistica.demo.repository.OditemRepo;
import com.logistica.demo.service.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LogisticaBackApplication.class)
 public class DBTest {

    private static final Logger log = LoggerFactory.getLogger(DBTest.class);

    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private CuentaItemService cuentaItemService;

    @Autowired
    private InventarioService inventarioService;

    @Autowired
    private OditemService oditemService;

    @Autowired
    private OdService odService;

    @Autowired
    private AlmacenService almacenService;

    @Autowired
    private MaterialService materialSevice;

    @Autowired
    private OritemService oritemService;

    @Autowired
    private OditemRepo oditemRepo;

    @Test
    public void countCuentaItemByCuenta() {
        Integer idcuenta = 1;
        Long countCuenta = cuentaItemService.countByCuenta(idcuenta);
        log.info("el número de ítems es {}", countCuenta);
        assertTrue(countCuenta!=null);
    }


    public void addInventario() {
        UbicacionReq ubicacionReq = new UbicacionReq( 49,1);
        inventarioService.addInventario(ubicacionReq,new Usuario(1));
    }

    public void addOditem() {
        List<OdReq> odReqs = new ArrayList<>();
        OdReq odReq1 = new OdReq(2d, null, 1, null, 1);
        odReqs.add(odReq1);
        oditemService.addOditems(odReqs,new Usuario(1));
    }

    public void inventariocountByMaterial() {
        Integer cuenta = inventarioService.countByMaterial(new Almacen(1),
                1);
        log.info("El número de movimientos por material es -> {}", cuenta);
        assertTrue(cuenta>0);
    }

    public void getAlmacens() {
        Almacen almacen = almacenService.findByAlmacen(new Almacen(1));
        log.info("El almacen es -> {}",almacen);
        assertTrue(almacen.getZonas().size()>0);
        log.info("Sus zonas son -> {}",almacen.getZonas());
    }

    public void oritemPendiente() {
        List<Oritem> oritems = oritemService.listarPendiente();
        log.info("El listado de Oritems -> {}", oritems);
        assertTrue(oritems.size()>0);
    }

    public void findByOd() {
        Optional<Od> od = odService.findById(30);
        log.info("la od es -> {}",od.get());
        assertTrue(od.get() != null);
    }

    public void inventarioByAlmacen() {
        List<Inventario> almacens = inventarioService.listarInventario(new Almacen(1),1);
        log.info("El inventario del almacen es -> {}", almacens);
        assertTrue(almacens.size()>0);
    }
//    public void inventarioUlt() {
//        Inventario inventario = inventarioService.ultInventario(1,1);
//        log.info("El inventario es -> {}",inventario);
//        assertTrue(inventario!=null);
//    }

    public void InventarioZona() {
        List<Inventario> inventario = inventarioService.listarInventario(2);
        log.info("Inventario es -> {}", String.valueOf(inventario.size()));
        assertTrue(inventario.size()>0);
    }

    public void InventarioByMaterial() {
        Integer idmaterial = 1;
        Almacen almacen = new Almacen(1);
        List<Inventario> inventarios = inventarioService.listarByMaterial(almacen, idmaterial,10,1);
        assertThat(inventarios, notNullValue());
    }
    public void findMaterial() {
        List<Material> materials = materialSevice.findAll();
        log.info("El listado de materiales -> {}",materials);
        assertTrue(materials.size()>0);
    }

    public void addCuentaitem() {
        ReqCuentaItem reqCuentaItem = new ReqCuentaItem(1,1,1,10d);
        Cuentaitem cuentaitem = cuentaItemService.addCuentaItem(reqCuentaItem, new Usuario(1));
        log.info("el listado de items de la cuenta es: {}", cuentaitem);
        assertTrue(cuentaitem!=null);
    }

    public void findItemByCuenta() {
        List<Cuentaitem> cuentaitems = cuentaItemService.findByCuenta(1, 6, 1);
        log.info("el listado de items de la cuenta es: {}", cuentaitems);
        assertTrue(cuentaitems.size()>0);
    }

    public void findByIdCuentaServiceJoin() {
        Cuenta cuenta = cuentaService.load(1);
        log.info("La cuenta es -> {}",cuenta);
    }

}
