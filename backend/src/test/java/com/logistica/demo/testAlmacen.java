package com.logistica.demo;

import com.logistica.demo.model.Almacen;
import com.logistica.demo.repository.AlmacenRepo;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class testAlmacen {
	private static final Logger log = LoggerFactory.getLogger(testAlmacen.class);

    @Autowired
    private AlmacenRepo almacenrepository;

    @Test
    public void crear() {
        Almacen almacen = almacenrepository.findById(1).get();
        Assert.notNull(almacen);
        Assert.isTrue(almacen.getZonas().size()>0);
        Assert.isTrue(almacen.getZonas().stream().findFirst().get().getNiveles().stream().findFirst().get().getPoses().size()>0);
    }
}
