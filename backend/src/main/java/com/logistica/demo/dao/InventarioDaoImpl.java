package com.logistica.demo.dao;

import com.logistica.demo.model.Material;
import com.logistica.demo.model.Pos;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class InventarioDaoImpl implements InventarioDao {

    @Autowired
    private EntityManager em;

    @Override
    public Integer ultInventario(Material material, Pos pos) {
        Session ss = em.unwrap(Session.class);
        String hql = "select max(inv.idinventario) from Inventario inv join  inv.material m join inv.pos p " +
                "where m.idmaterial = :idmaterial and p.idpos = :idpos";
        return (Integer) ss
                .createQuery(hql)
                .setParameter("idmaterial",material.getIdmaterial())
                .setParameter("idpos", pos.getIdpos())
                .uniqueResult();
    }
}
