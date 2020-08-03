package com.logistica.demo.controller;

import com.logistica.demo.exception.NotFoundException;
import com.logistica.demo.model.Inventario;
import com.logistica.demo.model.Usuario;
import com.logistica.demo.payload.ResInventario;
import com.logistica.demo.service.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class InventarioLastController {

    @Autowired
    public InventarioService inventarioService;

    @GetMapping("/material/{idmaterial}/inventarioslast")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<ResInventario>> listarInventario(@AuthenticationPrincipal Usuario usuario,
                                                                @PathVariable("idmaterial") Integer idmaterial) {
        if(idmaterial==0) {
            throw new NotFoundException("idmaterial", "Material", 0);
        }
        List<Inventario> inventario = inventarioService
                .listarInventario(usuario.getAlmacen(), idmaterial);
        return ResponseEntity.ok(inventario.stream()
                .map(ResInventario::new)
                .collect(Collectors.toList()));
    }
}
