package com.logistica.demo.controller;

import com.logistica.demo.model.Almacen;
import com.logistica.demo.model.Usuario;
import com.logistica.demo.model.Zona;
import com.logistica.demo.payload.ApiResponse;
import com.logistica.demo.payload.UbicacionesReq;
import com.logistica.demo.payload.UbicacionesRes;
import com.logistica.demo.service.AlmacenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/almacenes")
public class AlmacenController {

    @Autowired
    private AlmacenService almacenService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UbicacionesRes> getZonas(@AuthenticationPrincipal Usuario usuario) {
        Almacen almacen = almacenService.findByAlmacen(usuario.getAlmacen());
        return almacen.getZonas().stream()
            .map(UbicacionesRes::new)
            .collect(Collectors.toList());
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> save(@AuthenticationPrincipal Usuario usuario,
                                        @RequestBody List<UbicacionesReq> ubicacionesReq) {
        Almacen almacen = usuario.getAlmacen();
        List<Zona> zonas = ubicacionesReq.stream()
                .map(Zona::new)
                .collect(Collectors.toList());
        zonas.forEach(z -> z.setAlmacen(almacen));
        zonas.forEach(z -> z.setUsuario(usuario));
        zonas.forEach(z -> z.setFecharegistro(new Date()));
        almacen.setZonas(new HashSet<Zona>(zonas));
        List<UbicacionesRes> ubicacionesresp = null;
        try {
            almacenService.guardar(almacen);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "No se pudo almacenar las modificaciones"));
        }
        return ResponseEntity
                .ok(new ApiResponse(true, "Cambios almacenados en almacen: " + almacen));
    }
}
