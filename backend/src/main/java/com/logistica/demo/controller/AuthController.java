package com.logistica.demo.controller;

import javax.validation.Valid;

import com.logistica.demo.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.logistica.demo.security.JwtTokenProvider;
import com.logistica.demo.payload.JwtAuthenticationResponse;
import com.logistica.demo.payload.LoginRequest;

@RestController
public class AuthController {
	
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	
    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsuario(),
                        loginRequest.getClave())
        );
        
        SecurityContextHolder.getContext().setAuthentication(auth);
        
        String jwt = jwtTokenProvider.generateToken(auth);
        
        JwtAuthenticationResponse jwtAuthResp =
        		new JwtAuthenticationResponse(jwt,
        				((Usuario)auth.getPrincipal()).getIdusuario(),
        				((Usuario)auth.getPrincipal()).getNombre(),
                        ((Usuario)auth.getPrincipal()).getAlmacen().getNombre());
        return ResponseEntity.ok(jwtAuthResp);
    }
}
