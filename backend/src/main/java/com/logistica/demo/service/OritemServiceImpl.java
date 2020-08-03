package com.logistica.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.logistica.demo.repository.OritemRepo;
import com.logistica.demo.dao.OritemDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.logistica.demo.model.Oritem;

@Service
public class OritemServiceImpl implements OritemService {
	
	@Autowired
	private OritemDao oritemDao;

	@Autowired
	private OritemRepo oritemRepo;

	@Override
	@Transactional
	public void guardar(Set<Oritem> oritems) {
		for(Oritem o:oritems) {
			oritemRepo.save(o);
		}
	}

    @Override
	@Transactional
    public List<Oritem> listarPendiente() {
		return oritemDao.listarPendiente();
    }

    @Override
	@Transactional
    public Optional<Oritem> findById(Integer idoritem) {
        return oritemRepo.findById(idoritem);
    }

}
