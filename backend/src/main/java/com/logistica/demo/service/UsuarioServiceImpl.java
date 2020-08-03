package com.logistica.demo.service;

import com.logistica.demo.dao.UsuarioDao;
import com.logistica.demo.exception.NotFoundException;
import com.logistica.demo.exception.UnauthorizedHandler;
import com.logistica.demo.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UserDetailsService, UsuarioService {
	
	@Autowired
	private UsuarioDao usuarioDao;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) {
		return usuarioDao.login(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with username or email : " + username));
	}

	@Override
	@Transactional
	public Usuario loadUserById(Integer userId) {
		return usuarioDao.loadUserById(userId);
	}

	@Override
	@Transactional
	public List<Usuario> listUsuario() {
		return usuarioDao.listarUsuario();
	}

}
