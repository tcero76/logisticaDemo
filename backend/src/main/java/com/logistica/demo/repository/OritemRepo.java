package com.logistica.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.logistica.demo.model.Oritem;
import org.springframework.stereotype.Repository;

public interface OritemRepo extends JpaRepository<Oritem,Integer> {

}
