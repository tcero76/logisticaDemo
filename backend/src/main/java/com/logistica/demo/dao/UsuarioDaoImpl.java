package com.logistica.demo.dao;

import javax.persistence.EntityManager;

import com.logistica.demo.model.Usuario;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioDaoImpl implements UsuarioDao {

	@Autowired
	private EntityManager em;
	
	@Override
	public Usuario login(String nombre) {
		Session ss = em.unwrap(Session.class);
		String hql = "from Usuario u where u.nombre = :nombre";
		return (Usuario)ss.createQuery(hql).setParameter("nombre",nombre).uniqueResult();
	}

	@Override
	public Usuario loadUserById(Integer userId) {
		Session ss = em.unwrap(Session.class);
		return (Usuario)ss.get(Usuario.class, userId);
	}

}
