package com.logistica.demo.controller;

import com.logistica.demo.exception.NotFoundException;
import com.logistica.demo.model.Usuario;
import com.logistica.demo.payload.ResZona;
import com.logistica.demo.payload.ResZonaDetail;
import com.logistica.demo.repository.ZonaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/zona")
public class ZonaController {

    @Autowired
    private ZonaRepo zonaRepo;

    @GetMapping
    public List<ResZona> list(@AuthenticationPrincipal Usuario usuario) {
        return zonaRepo.findByAlmacen(usuario.getAlmacen()).stream()
                .map(ResZona::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{idzona}")
    public ResZonaDetail ZonaById(@PathVariable("idzona") Integer idzona) {
        return new ResZonaDetail(zonaRepo.findById(idzona)
        .orElseThrow(() -> new NotFoundException("idzona","Zona",idzona)));
    }

}
