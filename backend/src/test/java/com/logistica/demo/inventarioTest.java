package com.logistica.demo;

import com.logistica.demo.model.Material;
import com.logistica.demo.model.Pos;
import com.logistica.demo.service.InventarioService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class inventarioTest {
    private static final Logger log = LoggerFactory.getLogger(inventarioTest.class);

    @Autowired
    public InventarioService inventarioservice;

    @Test
    public void crear() {
        Material material = new Material(1);
        Pos pos = new Pos();
        pos.setIdpos(1);
//        Inventario inventario = inventarioservice.ultInventario(material, pos);
//        Assert.notNull(inventario);
    }
}
