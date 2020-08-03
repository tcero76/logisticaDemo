package com.logistica.demo.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.logistica.demo.model.Orec;

@Repository
public class OrecDaoImpl implements OrecDao {

	@Autowired
	private EntityManager em;
	
	@Override
	public List<Orec> listar() {
		Session ss = em.unwrap(Session.class);
		String hql = "select distinct o " +
				"from Orec o " +
				"left join fetch o.oritems ori";
		return (List<Orec>)ss.createQuery(hql).list();
	}

}
