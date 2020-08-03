package com.logistica.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.logistica.demo.model.Oritem;

public interface OritemService {
	public void guardar(Set<Oritem> oritems);
	public List<Oritem> listarPendiente();

    public Optional<Oritem> findById(Integer idoritem);
}
