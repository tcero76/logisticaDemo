package com.logistica.demo.service;

import java.util.List;

import com.logistica.demo.model.Material;

public interface MaterialService {
	public List<Material> findAll();
	public void guardar(Material material);

}
