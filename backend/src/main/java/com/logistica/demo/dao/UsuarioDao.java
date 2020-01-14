package com.logistica.demo.dao;

import com.logistica.demo.model.Usuario;

public interface UsuarioDao {
	public Usuario login(String nombre);
	public Usuario loadUserById(Integer userId);
}
