package com.logistica.demo.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.logistica.demo.model.Material;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MaterialDaoImpl implements MaterialDao {

	@Autowired
	private EntityManager em;
	

}
