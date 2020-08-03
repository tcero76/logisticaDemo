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
	public List<Oritem> listarPendiente() {
		String hql = "select ori from Oritem ori " +
				"left join fetch ori.orec o " +
				"left join fetch ori.inventario i " +
				"where ori.inventario.idinventario = null";
		return em
				.createQuery(hql)
				.getResultList();
	}

}
