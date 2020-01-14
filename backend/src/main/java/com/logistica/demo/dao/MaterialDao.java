package com.logistica.demo.dao;

import java.util.List;
import com.logistica.demo.model.Material;

public interface MaterialDao {
	public List<Material> listar();
	public void guardar(Material material);
}
