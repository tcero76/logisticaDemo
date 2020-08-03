package com.logistica.demo.dao;

import com.logistica.demo.model.Cuentaitem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class CuentaItemDaoImpl implements CuentaItemDao {

    @Autowired
    private EntityManager em;

    @Override
    public Optional<List<Cuentaitem>> findByCuenta(Integer idcuenta, Integer rows, Integer offset) {
        em.clear();
        String hql = "select ci from Cuentaitem ci "+
                "left join ci.cuenta c " +
                "left join ci.material m " +
                "left join ci.pos p " +
                "left join p.nivel l " +
                "left join l.zona z " +
                "left join ci.usuario u " +
                "where c.idcuenta=:idcuenta ";
        return Optional.ofNullable(em
                .createQuery(hql)
                .setParameter("idcuenta", idcuenta)
                .setMaxResults(rows)
                .setFirstResult(offset)
                .getResultList());
    }

    @Override
    public Optional<List<Cuentaitem>> findByCuenta(Integer idcuenta) {
        em.clear();
        String hql = "select ci from Cuentaitem ci "+
                "where c.idcuenta=:idcuenta ";
        return Optional.ofNullable(em
                .createQuery(hql)
                .setParameter("idcuenta", idcuenta)
                .getResultList());
    }

    @Override
    public Long countByIdcuenta(Integer idcuenta) {
        String jpql = "select count(ci) from Cuentaitem ci " +
                "left join ci.cuenta c " +
                "where c.idcuenta = :idcuenta ";
        return (Long)em
                .createQuery(jpql)
                .setParameter("idcuenta", idcuenta)
                .getSingleResult();
    }
}
