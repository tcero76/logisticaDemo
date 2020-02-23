package com.logistica.demo.service;

import com.logistica.demo.model.Usuario;

import java.util.List;

public interface UsuarioService {
    public Usuario loadUserById(Integer userId);
    public List<Usuario> listUsuario();
}
