package com.logistica.demo.service;

import com.logistica.demo.dao.UsuarioDao;
import com.logistica.demo.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class UsuarioServiceImpl implements UserDetailsService, UsuarioService {
	
	@Autowired
	private UsuarioDao usuarioDao;

	@Autowired
	private LoginAttemptService loginAttemptService;

	@Autowired
	private HttpServletRequest request;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) {
		String ip = getClientIP();
		if (loginAttemptService.isBlocked(ip)) {
			throw new InternalAuthenticationServiceException("Usuario Bloqueado: " + username);
		}
		return usuarioDao.login(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with username or email : " + username));
	}

	private String getClientIP() {
		String xfHeader = request.getHeader("X-Forwarded-For");
		if (xfHeader == null){
			return request.getRemoteAddr();
		}
		return xfHeader.split(",")[0];
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
