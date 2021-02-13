package com.logistica.demo.dao;

import com.logistica.demo.model.Cuenta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class CuentaDaoImpl implements CuentaDao {

    @Autowired
    private EntityManager em;

    @Override
    public List<Cuenta> findAll() {
        em.clear();
        String jpql = "select c from Cuenta c " +
                "where c.status = :status";
        return em.createQuery(jpql)
                .setParameter("status", "PENDIENTE")
                .getResultList();
    }

    @Override
    public Cuenta load(Integer idcuenta) {
        String hql = "select distinct c from Cuenta c " +
                "left join c.cuentaitems ci " +
                "left join ci.material m " +
                "left join c.zona z " +
                "where c.idcuenta = :idcuenta";
        return (Cuenta) em.createQuery(hql)
                .setParameter("idcuenta",idcuenta)
                .getSingleResult();
    }

    @Override
    public Optional<Cuenta> findById(Integer idcuenta) {
        em.clear();
        String jpql = "select c from Cuenta c " +
                "left join fetch c.cuentaitems ci " +
                "left join fetch c.zona z " +
                "where c.idcuenta = :idcuenta";
        return Optional.ofNullable((Cuenta)em
        .createQuery(jpql)
        .setParameter("idcuenta", idcuenta)
        .getSingleResult());
    }
}
