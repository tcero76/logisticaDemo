package com.logistica.demo.controller;

import com.logistica.demo.model.Inventario;
import com.logistica.demo.model.Oritem;
import com.logistica.demo.model.Usuario;
import com.logistica.demo.payload.*;
import com.logistica.demo.service.InventarioService;
import com.logistica.demo.service.OritemService;
import com.logistica.demo.util.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class InventarioController {

    private static final Logger log = LoggerFactory.getLogger(InventarioController.class);

    @Autowired
    public InventarioService inventarioService;

    @Autowired
    public OritemService oritemService;

    @GetMapping("/material/{idmaterial}/inventarios")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PageableRes> findInventarioByMaterial(@AuthenticationPrincipal Usuario usuario,
                                                                @PathVariable Integer idmaterial,
                                                                @RequestParam("page") Integer page,
                                                                @RequestParam("rows") Integer rows) {
        if(rows>AppConfig.PAGE_MAX_ITEMS || rows<=0){
            rows = AppConfig.PAGE_MAX_ITEMS;
        }
        Integer count = inventarioService.countByMaterial(usuario.getAlmacen(), idmaterial);
        Integer totalPag = ((((float)count/ rows)%1)>0f)?count/rows+1:count/rows;
        List<Inventario> inventarios =
                inventarioService.listarByMaterial(usuario.getAlmacen(),
                        idmaterial, rows,
                        (page-1)*rows);
        if(inventarios.size()==0)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new PageableRes<ResFlujo>(totalPag,page,inventarios.stream()
                .map(ResFlujo::new)
                .collect(Collectors.toList())));
    }

    @PatchMapping("/inventarios")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<OritemResponse>> ubicar(@RequestBody UbicacionReq ubicacionReq,
                                                       @AuthenticationPrincipal Usuario usuario) {
        inventarioService.addInventario(ubicacionReq, usuario);
        List<Oritem> oritems = oritemService.listarPendiente();
        return ResponseEntity.ok(oritems.stream()
                .map(OritemResponse::new)
                .collect(Collectors.toList()));
    }
}
