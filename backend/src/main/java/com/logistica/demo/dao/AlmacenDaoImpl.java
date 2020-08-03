package com.logistica.demo.dao;

import com.logistica.demo.model.Almacen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class AlmacenDaoImpl implements AlmacenDao {

    @Autowired
    private EntityManager em;

    @Override
    public Almacen findByAlmacen(Integer idalmacen) {
        String jpql = "select a from Almacen a " +
                "left join fetch a.zonas z " +
                "left join fetch  z.niveles n " +
                "left join fetch  n.poses p " +
                "where a.idalmacen = :idalmacen";
        return (Almacen)em.createQuery(jpql)
                .setParameter("idalmacen", idalmacen)
                .getSingleResult();
    }
}
