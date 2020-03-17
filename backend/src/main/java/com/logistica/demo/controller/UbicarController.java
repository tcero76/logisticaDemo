package com.logistica.demo.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.logistica.demo.model.*;
import com.logistica.demo.payload.OritemResponse;
import com.logistica.demo.payload.UbicacionRequest;
import com.logistica.demo.payload.UbicacionesRes;
import com.logistica.demo.repository.OritemRepository;
import com.logistica.demo.repository.InventarioRepository;
import com.logistica.demo.repository.PosRepository;
import com.logistica.demo.service.InventarioService;
import com.logistica.demo.service.OritemService;
import com.logistica.demo.repository.ZonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ubicar")
public class UbicarController {
	
	private Zona zona = null;

	@Autowired
	private OritemRepository oritemrepository;

	@Autowired
	private OritemService oritemservice;

	@Autowired
	private ZonaRepository zonarepository;

	@Autowired
	private PosRepository posrepository;

	@Autowired
	private InventarioRepository inventarioRespository;

	@Autowired
	private InventarioService inventarioService;

	@GetMapping("/listado")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<OritemResponse> listar() {
		List<Oritem> oritems = oritemservice.listarPendiente();
		return oritems.stream()
					.map(OritemResponse::new)
					.collect(Collectors.toList());
	}

	@GetMapping("/ubicaciones")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<UbicacionesRes> getZonas(@AuthenticationPrincipal Usuario usuario) {
		List<UbicacionesRes> ubicacionesresp = usuario.getAlmacen().getZonas().stream()
				.map(UbicacionesRes::new)
				.collect(Collectors.toList());
		return ubicacionesresp;
	}

	@PatchMapping("/ubicacion")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<OritemResponse>> ubicar(@RequestBody UbicacionRequest ubicacionRequest,
											   @AuthenticationPrincipal Usuario usuario) {
		Pos pos = (Pos)posrepository.findById(ubicacionRequest.getIdpos()).get();
		Oritem oritem = oritemrepository.findById(ubicacionRequest.getIdoritem()).get();
		Inventario i = new Inventario();
		i.setPos(pos);
		i.setMaterial(oritem.getMaterial());
		i.setCantidad(oritem.getCantidad());
		i.setFecharegistro(new Date());
		i.setUsuario(usuario);
		i.setOritem(oritem);
		Integer idUltInventario = inventarioService.ultInventario(oritem.getMaterial(), pos);
		if (idUltInventario!=null) {
			Inventario ultInv = inventarioRespository.findById(idUltInventario).get();
			i.setCantidadtotal(ultInv.getCantidadtotal()+i.getCantidad());
		} else {
			i.setCantidadtotal(i.getCantidad());
		}
		inventarioRespository.save(i);
		List<Oritem> oritems = oritemservice.listarPendiente();
		return ResponseEntity.ok(oritems.stream()
				.map(OritemResponse::new)
				.collect(Collectors.toList()));
	}

}
