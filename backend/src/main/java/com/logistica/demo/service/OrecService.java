package com.logistica.demo.service;

import java.util.List;

import com.logistica.demo.model.Orec;
import com.logistica.demo.model.Oritem;

public interface OrecService {
	public List<Orec> listar();
	public void guardar(Orec orec, List<Oritem> oritems);
}
