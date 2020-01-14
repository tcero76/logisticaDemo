package com.logistica.demo.service;

import java.util.HashSet;
import java.util.List;

import com.logistica.demo.model.Oritem;
import com.logistica.demo.repository.OrecRepository;
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
	private OrecRepository orecrepository;

	@Override
	@Transactional
	public List<Orec> listar() {
		return orecdao.listar();
	}

	@Override
	public void guardar(Orec orec, List<Oritem> oritems) {
		orec.setOritems(new HashSet<Oritem>());
		for (Oritem ori:oritems){
			orec.getOritems().add(ori);
			orecrepository.save(orec);
		}
	}

}
