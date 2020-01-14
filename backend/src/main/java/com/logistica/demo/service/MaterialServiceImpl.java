package com.logistica.demo.service;

import java.util.List;

import javax.transaction.Transactional;

import com.logistica.demo.dao.MaterialDao;
import com.logistica.demo.model.Material;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MaterialServiceImpl implements MaterialService {
	
	@Autowired
	private MaterialDao materialdao;

	@Override
	@Transactional
	public List<Material> listar() {
		return materialdao.listar();
	}

	@Override
	public void guardar(Material material) {
		materialdao.guardar(material);
	}

	

}
