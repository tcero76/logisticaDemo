package com.logistica.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.logistica.demo.model.Oritem;
import org.springframework.stereotype.Repository;

@Repository
public interface OritemRepository extends JpaRepository<Oritem,Integer> {

}
