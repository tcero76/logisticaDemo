package com.logistica.demo.dao;

import com.logistica.demo.model.Oditem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class OditemDAOImpl implements OditemDAO {

    @Autowired
    EntityManager em;

}
