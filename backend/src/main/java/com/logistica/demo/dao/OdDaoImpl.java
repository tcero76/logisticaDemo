package com.logistica.demo.dao;

import com.logistica.demo.model.Od;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class OdDaoImpl implements OdDao {

    @Autowired
    public EntityManager em;

    @Override
    public Optional<Od> findById(Integer id) {
        String hql = "select distinct o from Od o " +
                "left join fetch o.oditems odi " +
                "left join fetch odi.material m " +
                "left join fetch odi.inventario i " +
                "left join fetch i.pos p " +
                "where o.idod = :id";
        return Optional.ofNullable((Od)em
                .createQuery(hql)
                .setParameter("id", id)
                .getSingleResult());
    }
}
