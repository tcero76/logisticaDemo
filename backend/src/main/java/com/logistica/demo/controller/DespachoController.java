package com.logistica.demo.controller;

import com.logistica.demo.exception.BadRequestException;
import com.logistica.demo.model.*;
import com.logistica.demo.payload.InventarioResponse;
import com.logistica.demo.payload.OdRequest;
import com.logistica.demo.repository.InventarioRepository;
import com.logistica.demo.repository.MaterialRepository;
import com.logistica.demo.repository.OdRepo;
import com.logistica.demo.repository.PosRepository;
import com.logistica.demo.service.InventarioService;
import com.logistica.demo.service.OditemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/despacho")
public class DespachoController {

    @Autowired
    public InventarioService inventarioService;

    @Autowired
    public MaterialRepository materialRepo;

    @Autowired
    public PosRepository posRepo;

    @Autowired
    public InventarioRepository inventarioRepo;

    @Autowired
    public OditemService oditemService;

    @Autowired
    private OdRepo odRepo;

    @GetMapping("/inventario")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<InventarioResponse> listarInventario(@AuthenticationPrincipal Usuario usuario) {
        List<Inventario> inventario = inventarioService.listarInventario(usuario.getAlmacen());
        List<InventarioResponse> resp = inventario.stream()
                .map(InventarioResponse::new)
                .collect(Collectors.toList());
        return resp;
    };

    @PostMapping("/save")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> despachar(@AuthenticationPrincipal Usuario usuario,
    @RequestBody List<OdRequest> odRequest) {
        Od od = new Od();
        od.setFecharegistros(new Date());
        od.setUsuario(usuario);
        List<Oditem> oditems = null;
        try{
        oditems = odRequest.stream()
                                .map(odr -> {
                                    Material material = materialRepo.findById(Integer.valueOf(odr.getMaterial().get("idmaterial"))).get();
                                    Pos pos = posRepo.findById(Integer.valueOf(odr.getPos().get("idpos"))).get();
                                    Integer idultinv = inventarioService.ultInventario(material,pos);
                                    Inventario ultinv = inventarioRepo.findById(idultinv).get();
                                    Oditem oditem = new Oditem(odr.getCantidadDespacho(),
                                            new Date(),
                                            material,
                                            usuario,
                                            od,
                                            null);
                                    Inventario inv = new Inventario(material,
                                            odr.getCantidadDespacho(),
                                            pos,
                                            new Date(),
                                            usuario,
                                            ultinv.getCantidadtotal()-odr.getCantidadDespacho(),
                                            null,
                                            oditem);
                                    oditem.setInventario(inv);
                                    oditem.setMaterial(material);
                                    if (inv.getCantidadtotal()<0) {
                                         throw new BadRequestException(oditem);
                                    }

                                    oditem.setFecharegistro(new Date());
                                    oditem.setOd(od);
                                    oditem.setUsuario(usuario);
                                    oditem.setCantidad(odr.getCantidadDespacho());
                                    return oditem;
                                })
                                .collect(Collectors.toList());
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().body("Sin Stock\n" + e.getMessage());
        }
        oditemService.listSave(oditems);
        return ResponseEntity.ok().body("Guardado Orec Nº: " + od);
    }
}
