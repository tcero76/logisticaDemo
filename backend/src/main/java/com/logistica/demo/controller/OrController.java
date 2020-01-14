package com.logistica.demo.controller;

import java.util.*;
import java.util.stream.Collectors;

import com.logistica.demo.model.Usuario;
import com.logistica.demo.service.OrecService;
import com.logistica.demo.model.Material;
import com.logistica.demo.repository.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.logistica.demo.model.Orec;
import com.logistica.demo.model.Oritem;
import com.logistica.demo.payload.OrecRequest;

import javax.validation.Valid;


@RestController
@RequestMapping("/material")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class OrController {
	
	@Autowired
	private MaterialRepository materialrepo;

	@Autowired
	private OrecService orecservice;

	@GetMapping("/materiales")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<Material> listarMateriales() {
		List<Material> materiales = materialrepo.findAll();
		return materiales;
	}

	@PostMapping("/save")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Integer> guardar(@Valid @RequestBody OrecRequest orecreqs,
										@AuthenticationPrincipal Usuario usuario) {
		List<Material> materiales = materialrepo.findAll();
		Orec orec = new Orec(usuario,new Date(), orecreqs.getGuiadespacho(),null);
		List<Oritem> oritems = orecreqs.getOritems().stream()
				.filter((orecreq) -> materiales.contains(orecreq.getMaterial()))
				.map( oritem -> {
					oritem.setOrec(orec);
					oritem.setFecharegistro(new Date());
					oritem.setUsuario(usuario);
					return oritem;
				})
				.collect(Collectors.toList());
		orecservice.guardar(orec,oritems);
		return ResponseEntity.ok(orec.getIdorec());
	}

}
