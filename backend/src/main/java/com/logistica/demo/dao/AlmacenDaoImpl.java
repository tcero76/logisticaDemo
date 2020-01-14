package com.logistica.demo.dao;

import com.logistica.demo.model.Almacen;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class AlmacenDaoImpl implements AlmacenDao {

    @Autowired
    private EntityManager em;

    @Override
    public Almacen listar() {
        Session ss = em.unwrap(Session.class);
        String hql = "select distinct a from Almacen a left join fetch a.zona z left join fetch z.nivel n left join fetch n.pos p";
        return (Almacen) ss.createQuery(hql).list();
    }
}
