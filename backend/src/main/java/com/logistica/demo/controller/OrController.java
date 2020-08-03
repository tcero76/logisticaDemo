package com.logistica.demo.controller;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import com.logistica.demo.exception.NotFoundException;
import com.logistica.demo.model.Usuario;
import com.logistica.demo.payload.OrecRes;
import com.logistica.demo.payload.OritemResponse;
import com.logistica.demo.service.OrecService;
import com.logistica.demo.model.Material;
import com.logistica.demo.repository.MaterialRepo;
import com.logistica.demo.service.OritemService;
import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.logistica.demo.model.Orec;
import com.logistica.demo.model.Oritem;
import com.logistica.demo.payload.OrecReq;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/ors")
public class OrController {

	private static final Logger log = LoggerFactory.getLogger(OrController.class);

	@Autowired
	private MaterialRepo materialrepo;

	@Autowired
	private OrecService orecService;


	@Autowired
	private OritemService oritemService;

	@GetMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<OritemResponse> listar() {
		List<Oritem> oritems = oritemService.listarPendiente();
		return oritems.stream()
				.map(OritemResponse::new)
				.collect(Collectors.toList());
	}

	@PostMapping(consumes = "application/json")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Integer> save(@AuthenticationPrincipal Usuario usuario,
											@RequestBody OrecReq orecReq) {
		log.info(orecReq.toString());
		Orec orec = orecService.save(orecReq, usuario)
				.orElseThrow(() -> new InternalException("Dato no almacenado"));
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idorec}")
				.buildAndExpand(orec.getIdorec()).toUri();
		return ResponseEntity.created(location).body(orec.getIdorec());
	}

	@GetMapping("/{idorec}")
	public OrecRes loadById(@AuthenticationPrincipal Usuario usuario,
							@PathParam("idorec") Integer idorec) {
			Orec orec = orecService.findById(idorec)
							.orElseThrow(() -> new NotFoundException("idorec", "Orec", idorec));
			return new OrecRes(orec);

	}
}
