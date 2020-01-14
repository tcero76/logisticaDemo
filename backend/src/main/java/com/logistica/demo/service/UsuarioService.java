package com.logistica.demo.service;

import com.logistica.demo.dao.UsuarioDao;
import com.logistica.demo.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService implements UserDetailsService {
	
	@Autowired
	private UsuarioDao usuariodao;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return usuariodao.login(username);
	}

	@Transactional
	public Usuario loadUserById(Integer userId) {
		return usuariodao.loadUserById(userId);
	}

}
