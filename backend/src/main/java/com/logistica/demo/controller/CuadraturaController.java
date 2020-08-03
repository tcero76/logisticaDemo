package com.logistica.demo.controller;

import com.logistica.demo.model.Usuario;
import com.logistica.demo.payload.CuadrarCuentaReq;
import com.logistica.demo.payload.CuadrarInventarioReq;
import com.logistica.demo.payload.ResCuentaCuadratura;
import com.logistica.demo.service.CuentaService;
import com.logistica.demo.service.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuadraturas")
public class CuadraturaController {

    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private InventarioService inventarioService;

    @GetMapping("/{idcuenta}")
    public List<ResCuentaCuadratura> listCuentaCuadratura(@PathVariable("idcuenta") Integer idcuenta) {
        return inventarioService.findCuadratura(idcuenta);
    }

    @PatchMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<ResCuentaCuadratura>> ubicar(@RequestBody CuadrarCuentaReq cuadrarCuentaReq,
                                                       @AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity
                .created(null)
                .body(inventarioService.addInventario(cuadrarCuentaReq, usuario));
    }
}
