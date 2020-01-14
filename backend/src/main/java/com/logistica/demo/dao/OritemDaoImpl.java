package com.logistica.demo.dao;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.logistica.demo.model.Oritem;

import java.util.List;

@Repository
public class OritemDaoImpl implements OritemDao {

	@Autowired
	private EntityManager em;
	
	@Override
	public void guardar(Oritem oritem) {
		Session ss = em.unwrap(Session.class);
		ss.save(oritem);
	}

	@Override
	public List<Oritem> listarPendiente() {
		Session ss = em.unwrap(Session.class);
		String hql = "from Oritem ori left join fetch ori.orec o left join fetch ori.inventario where ori.inventario.idinventario = null";
		return ss.createQuery(hql).list();
	}

}
