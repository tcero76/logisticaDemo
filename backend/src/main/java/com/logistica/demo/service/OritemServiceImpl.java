package com.logistica.demo.service;

import java.util.List;
import java.util.Set;

import com.logistica.demo.repository.OritemRepository;
import com.logistica.demo.dao.OritemDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.logistica.demo.model.Oritem;

@Service
public class OritemServiceImpl implements OritemService {
	
	@Autowired
	private OritemDao oritemdao;

	@Autowired
	private OritemRepository oritemRepository;

	@Override
	@Transactional
	public void guardar(Set<Oritem> oritems) {
		for(Oritem o:oritems) {
			oritemdao.guardar(o);
		}
		
	}

    @Override
	@Transactional
    public List<Oritem> listarPendiente() {
		return oritemdao.listarPendiente();
    }

}
