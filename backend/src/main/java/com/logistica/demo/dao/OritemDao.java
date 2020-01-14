package com.logistica.demo.dao;

import com.logistica.demo.model.Oritem;

import java.util.List;

public interface OritemDao {
	public void guardar(Oritem oritem);
	public List<Oritem> listarPendiente();
}
