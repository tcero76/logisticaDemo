package com.logistica.demo.dao;

import com.logistica.demo.model.Usuario;

import java.util.List;

public interface UsuarioDao {
	public Usuario login(String nombre);
	public Usuario loadUserById(Integer userId);
	public List<Usuario> listarUsuario();

}
