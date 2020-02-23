package com.logistica.demo.service;

import com.logistica.demo.dao.UsuarioDao;
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
	private UsuarioDao usuariodao;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return usuariodao.login(username);
	}

	@Override
	@Transactional
	public Usuario loadUserById(Integer userId) {
		return usuariodao.loadUserById(userId);
	}

	@Override
	@Transactional
	public List<Usuario> listUsuario() {
		return usuariodao.listarUsuario();
	}

}
