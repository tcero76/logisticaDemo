package com.logistica.demo.controller;

import com.logistica.demo.model.Cuenta;
import com.logistica.demo.model.Usuario;
import com.logistica.demo.payload.PageableRes;
import com.logistica.demo.payload.ReqCuenta;
import com.logistica.demo.payload.ResCuenta;
import com.logistica.demo.payload.ResCuentaItem;
import com.logistica.demo.service.CuentaItemService;
import com.logistica.demo.service.CuentaService;
import com.logistica.demo.util.AppConfig;
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
@RequestMapping("/cuentas")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private CuentaItemService cuentaItemService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<ResCuenta>> listarCuenta() {
        return ResponseEntity.ok(cuentaService.findPendiente().stream()
                .map(ResCuenta::new)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{idcuenta}")
    public PageableRes<ResCuentaItem> list(@PathVariable("idcuenta") Integer idcuenta,
                                            @RequestParam("page") Integer page,
                                            @RequestParam("rows") Integer rows) {
        if(rows > AppConfig.PAGE_MAX_ITEMS) {
            rows = AppConfig.PAGE_MAX_ITEMS;
        }
        Integer rowsTotal = cuentaItemService.countByCuenta(idcuenta).intValue();
        Integer totalPag = ((((float)rowsTotal/ rows)%1)>0f)?rowsTotal/rows+1:rowsTotal/rows;
        return new PageableRes<ResCuentaItem>(totalPag,page,cuentaItemService
                .findByCuenta(idcuenta,rows, (page-1)*rows).stream()
                .map(ResCuentaItem::new)
                .collect(Collectors.toList()));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<ResCuenta>> addCuenta(@AuthenticationPrincipal Usuario usuario,
                                            @RequestBody ReqCuenta reqCuenta) {
        List<ResCuenta> resCuentas = null;
            Cuenta cuenta = cuentaService.addCuenta(reqCuenta.getIdzona(), usuario);
            URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/cuenta/{idcuenta}").buildAndExpand(cuenta.getIdcuenta()).toUri();
            resCuentas = cuentaService.findPendiente().stream()
                    .map(ResCuenta::new)
                    .collect(Collectors.toList());
            return ResponseEntity.created(location)
                    .body(resCuentas);

    }

}
