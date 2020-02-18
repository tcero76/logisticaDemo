package com.logistica.demo.dao;

import com.logistica.demo.model.Od;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class OdDaoImpl implements OdDao {

    @Autowired
    public EntityManager em;

    @Override
    public Od findById(Integer id) {
        Session ss = em.unwrap(Session.class);
        String hql = "select distinct o from Od o " +
                "left join fetch o.oditems odi " +
                "left join odi.material m " +
                "left join odi.inventario i " +
                "left join i.pos p " +
                "where o.idod = :id";
        return (Od)ss
                .createQuery(hql)
                .setParameter("id", id)
                .uniqueResult();
    }
}
