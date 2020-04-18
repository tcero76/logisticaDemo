package com.logistica.demo.controller;

import com.logistica.demo.model.Material;
import com.logistica.demo.model.Usuario;
import com.logistica.demo.payload.ResFlujo;
import com.logistica.demo.service.InventarioService;
import com.logistica.demo.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/informe")
public class InformeController {

    @Autowired
    public InventarioService inventarioService;

    @PostMapping("/movmat")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<ResFlujo> listFlujo(@AuthenticationPrincipal Usuario usuario, @RequestBody Material material) {
        return inventarioService.listarByMaterial(usuario.getAlmacen(), material.getIdmaterial()).stream()
                .map(ResFlujo::new)
                .collect(Collectors.toList());
    }
}
