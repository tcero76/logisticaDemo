package com.logistica.demo.service;

import java.util.List;
import java.util.Optional;

import com.logistica.demo.model.Orec;
import com.logistica.demo.model.Usuario;
import com.logistica.demo.payload.OrecReq;

public interface OrecService {
	public List<Orec> listar();

    public Optional<Orec> save(OrecReq orecReq, Usuario usuario);

    public Optional<Orec> findById(Integer idorec);
}
