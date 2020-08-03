package com.logistica.demo.controller;

import com.logistica.demo.exception.NotFoundException;
import com.logistica.demo.model.Od;
import com.logistica.demo.model.Usuario;
import com.logistica.demo.payload.ApiResponse;
import com.logistica.demo.payload.OdReq;
import com.logistica.demo.payload.ResOd;
import com.logistica.demo.service.OdService;
import com.logistica.demo.service.OditemService;
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

@RestController
public class OditemController {

    private static final Logger log = LoggerFactory.getLogger(OditemController.class);

    @Autowired
    public OditemService oditemService;

    @Autowired
    private OdService odService;

    @PostMapping("/od")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> addOditem(@AuthenticationPrincipal Usuario usuario,
                                       @RequestBody List<OdReq> odReq) {
        if(odReq.size()==0) {
            return ResponseEntity.noContent().build();
        }
        Od od = oditemService.addOditems(odReq, usuario);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/od/{idod}")
                .buildAndExpand(od.getIdod()).toUri();

            return ResponseEntity
                .created(location)
                .body(new ApiResponse(true, "Orden de Despacho creada: "+ od.getIdod()));
    }

    @GetMapping("/od/{idod}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResOd getOditem(@AuthenticationPrincipal Usuario usuario,
                           @PathVariable Integer idod) {
        return new ResOd(odService.findById(idod)
            .orElseThrow(() -> new NotFoundException("idod", "Od", idod)));
    }
}
