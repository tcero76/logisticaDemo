package com.logistica.demo.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import com.logistica.demo.exception.UnauthorizedHandler;
import com.logistica.demo.model.Usuario;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UsuarioDaoImpl implements UsuarioDao {

	@Autowired
	private EntityManager em;
	
	@Override
	public Optional<Usuario> login(String nombre) {
		String jpql = "select u from Usuario u " +
				"left join fetch u.rol r " +
				"left join fetch u.almacen a " +
				"where u.nombre = :nombre";
		return Optional.ofNullable((Usuario) em.createQuery(jpql)
					.setParameter("nombre", nombre)
					.getSingleResult());
	}

	@Override
	public Usuario loadUserById(Integer userId) {
		String hql = "select u from Usuario u " +
				"left join fetch u.rol r " +
				"left join fetch u.almacen a " +
				"where u.idusuario = :idusuario";
		return (Usuario)em.createQuery(hql)
				.setParameter("idusuario", userId)
				.getSingleResult();
	}

	@Override
	public List<Usuario> listarUsuario() {
		Session ss = em.unwrap(Session.class);
		String hql = "from Usuario u";
		return ss.createQuery(hql).list();
	}

}
