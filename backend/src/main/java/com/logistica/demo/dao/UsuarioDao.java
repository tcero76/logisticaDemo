package com.logistica.demo.dao;

import com.logistica.demo.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioDao {
	public Optional<Usuario> login(String nombre);
	public Usuario loadUserById(Integer userId);
	public List<Usuario> listarUsuario();

}
