package com.logistica.demo.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.logistica.demo.dao.MaterialDao;
import com.logistica.demo.exception.NotFoundException;
import com.logistica.demo.model.Material;
import com.logistica.demo.repository.MaterialRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MaterialServiceImpl implements MaterialService {

	@Autowired
	private MaterialRepo materialRepo;

	@Override
	@Transactional
	public List<Material> findAll() {
		return materialRepo.findAll();
	}

	@Override
	@Transactional
	public void guardar(Material material) {
		materialRepo.save(material);
	}



}
