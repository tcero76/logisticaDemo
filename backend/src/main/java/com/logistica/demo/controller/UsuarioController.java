package com.logistica.demo.controller;

import com.logistica.demo.model.Usuario;
import com.logistica.demo.payload.UsuarioListResponse;
import com.logistica.demo.payload.UsuarioRequest;
import com.logistica.demo.service.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.logistica.demo.payload.CurrentUserResponse;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	public UsuarioServiceImpl usuarioService;

	@GetMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<UsuarioListResponse> listarUsuarios() {
		List<UsuarioListResponse> usuarioListResponse = usuarioService.listUsuario().stream()
				.map(UsuarioListResponse::new)
				.collect(Collectors.toList());
		return usuarioListResponse;
	}

	public ResponseEntity<Boolean> usuarioEdit(@AuthenticationPrincipal Usuario usuario,
											   @RequestBody UsuarioRequest usuarioResquest) {

		return ResponseEntity.ok(true);
	}

	@GetMapping("/current")
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
