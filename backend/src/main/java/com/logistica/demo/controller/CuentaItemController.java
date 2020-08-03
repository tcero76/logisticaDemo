package com.logistica.demo.controller;

import com.logistica.demo.model.Cuentaitem;
import com.logistica.demo.model.Usuario;
import com.logistica.demo.payload.CuadrarInventarioReq;
import com.logistica.demo.payload.ReqCuentaItem;
import com.logistica.demo.payload.ResCuentaCuadratura;
import com.logistica.demo.payload.ResCuentaItem;
import com.logistica.demo.service.CuentaItemService;
import com.logistica.demo.service.InventarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cuentaitem")
public class CuentaItemController {

    private static final Logger log = LoggerFactory.getLogger(CuentaItemController.class);

    @Autowired
    private CuentaItemService cuentaItemService;

    @Autowired
    private InventarioService inventarioService;


    @PostMapping
    public ResponseEntity<List<ResCuentaItem>> addCuentaItem(@AuthenticationPrincipal Usuario usuario,
                                                             @RequestBody ReqCuentaItem reqCuentaItem) {
    Cuentaitem cuentaItem = cuentaItemService.addCuentaItem(reqCuentaItem, usuario);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{idcuenta}").buildAndExpand(cuentaItem.getIdcuentaitem()).toUri();
    List<ResCuentaItem> resCuentaItems = cuentaItemService
            .findByCuenta(reqCuentaItem.getIdcuenta()).stream()
            .map(ResCuentaItem::new)
            .collect(Collectors.toList());
    return ResponseEntity.created(location).body(resCuentaItems);
    }



    @PatchMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<ResCuentaCuadratura>> ubicar(@RequestBody CuadrarInventarioReq cuadrarCuentaReq,
                                                            @AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity
                .created(null)
                .body(inventarioService.addInventario(cuadrarCuentaReq, usuario));
    }
}
