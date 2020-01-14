package com.logistica.demo.controller;

import com.logistica.demo.model.Usuario;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.logistica.demo.payload.CurrentUserResponse;

@RestController
@RequestMapping("/user")
public class UsuarioController {

	@GetMapping("/usuario")
	public CurrentUserResponse currentUser(@AuthenticationPrincipal Usuario usuario) {
		if(usuario==null) {
			return new CurrentUserResponse(null, null, false, null);
		}
		CurrentUserResponse resp =
				new CurrentUserResponse(usuario.getIdusuario(), usuario.getNombre(),
						SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
						, usuario.getAlmacen().getNombre());
        return resp;
	}
}
