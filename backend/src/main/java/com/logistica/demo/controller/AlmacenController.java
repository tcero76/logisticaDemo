package com.logistica.demo.controller;

import com.logistica.demo.model.Almacen;
import com.logistica.demo.model.Usuario;
import com.logistica.demo.model.Zona;
import com.logistica.demo.payload.UbicacionesReq;
import com.logistica.demo.payload.UbicacionesRes;
import com.logistica.demo.repository.AlmacenRepository;
import com.logistica.demo.repository.PosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/almacen")
public class AlmacenController {

    @Autowired
    private PosRepository posrepository;

    @Autowired
    private AlmacenRepository almacenRepository;

    @PostMapping("/save")
    public ResponseEntity<Boolean> save(@AuthenticationPrincipal Usuario usuario,
                                        @RequestBody List<UbicacionesReq> ubicacionesReq) {
        Almacen almacen = usuario.getAlmacen();
        List<Zona> zonas = ubicacionesReq.stream()
                .map(Zona::new)
                .collect(Collectors.toList());
        zonas.forEach(z -> z.setAlmacen(almacen));
        zonas.forEach(z -> z.setUsuario(usuario));
        zonas.forEach(z -> z.setFecharegistro(new Date()));
        almacen.setZonas(new HashSet<Zona>(zonas));
        almacenRepository.save(almacen);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/ubicaciones")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UbicacionesRes> getZonas(@AuthenticationPrincipal Usuario usuario) {
        List<UbicacionesRes> ubicacionesresp = usuario.getAlmacen().getZonas().stream()
                .map(UbicacionesRes::new)
                .collect(Collectors.toList());
        return ubicacionesresp;
    }
}
