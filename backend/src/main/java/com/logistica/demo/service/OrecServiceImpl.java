package com.logistica.demo.service;

import java.util.*;
import java.util.stream.Collectors;

import com.logistica.demo.exception.NotFoundException;
import com.logistica.demo.model.Material;
import com.logistica.demo.model.Oritem;
import com.logistica.demo.model.Usuario;
import com.logistica.demo.payload.OrecReq;
import com.logistica.demo.repository.MaterialRepo;
import com.logistica.demo.repository.OrecRepo;
import com.logistica.demo.dao.OrecDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.logistica.demo.model.Orec;

@Service
public class OrecServiceImpl implements OrecService {
	
	@Autowired
	private OrecDao orecdao;

	@Autowired
	private OrecRepo orecRepo;

	@Autowired
	private MaterialRepo materialRepo;

	@Override
	@Transactional
	public List<Orec> listar() {
		return orecdao.listar();
	}

	@Override
	@Transactional
	public Optional<Orec> save(OrecReq orecReq, Usuario usuario) {
		Orec orec = new Orec(usuario, new Date(), orecReq.getGuiadespacho(), null);
		Set<Oritem> oritems = orecReq.getOritems().stream()
								.map(oi -> {
									Material material = materialRepo.findById(oi.getIdmaterial())
											.orElseThrow(() -> new NotFoundException("idmaterial", "Material", oi.getCantidad()));
									return new Oritem(material, oi.getCantidad(), new Date(),
													usuario, orec, null, oi.getPos());
								})
								.collect(Collectors.toSet());
		orec.setOritems(oritems);
		orecRepo.save(orec);
		return Optional.ofNullable(orec);
	}

	@Override
	public Optional<Orec> findById(Integer idorec) {
		return orecRepo.findById(idorec);
	}

}
