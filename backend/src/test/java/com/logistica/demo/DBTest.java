package com.logistica.demo;

import com.logistica.demo.model.*;
import com.logistica.demo.repository.OdRepo;
import com.logistica.demo.service.InventarioService;
import com.logistica.demo.service.OdService;
import com.logistica.demo.service.OditemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DBTest {

    private static final Logger log = LoggerFactory.getLogger(DBTest.class);
    @Autowired
    public InventarioService inventarioService;

/*

    @Test
    public void material() {
        Material material = new Material(1);
        Pos pos = new Pos();
        pos.setIdpos(1);
        Integer inventario = inventarioService.ultInventario(material, pos);
        assertThat(inventario).isNotNull();
    }
*/

/*

    @Autowired
    public OditemRepo oditemRepo;

    @Test
    public void despacho() {
        Oditem oditem = new Oditem();
        oditem.setIdoditem(1);
        oditem.setMaterial(new Material(1));
        oditem.setCantidad(12d);
        oditem.setFecharegistro(new Date());
        oditem.setUsuario(new Usuario(1));
        oditemRepo.save(oditem);
        assertThat(oditemRepo).isNotNull();
        assertThat(oditemRepo.findById(1)).isNotNull();
    }
*/
/*
    @Test
    public void testListInventario() {
        Almacen almacen = new Almacen(1);
        List<Inventario> inventarios = inventarioService.listarInventario(almacen);
        assertThat(inventarios, notNullValue());
        assertThat(inventarioService, notNullValue());
        inventarios.stream().
                forEach(i -> assertThat(i.getPos().getNivel().getZona().getAlmacen().getNombre(),is("Santiago")));
    }*/

    @Autowired
    public OditemService oditemService;

    @Autowired
    public OdRepo odRepo;

    @Autowired
    public OdService odService;

    @Test
    public void testGuardarOditem() {
        Material material = new Material(1);
        Double cantidad = 15d;
        Od od = new Od();

        Inventario inventario = new Inventario();
        inventario.setCantidad(cantidad);
        inventario.setMaterial(material);
        inventario.setFecharegistro(new Date());

        Oditem oditem = new Oditem(cantidad,
                new Date(),
                material,
                new Usuario(1),
                null,
                inventario);

        Od odresp = odRepo.save(od);

        List<Oditem> oditems = new ArrayList<Oditem>();

        oditems.add(oditem);
//        od.setOditems(oditems);

        oditems.stream()
                .forEach(odi -> odi.setOd(odresp));

        oditemService.listSave(oditems);

        Od odfinal = odService.findById(odresp.getIdod());

        assertThat(odfinal, notNullValue());

    }
}
