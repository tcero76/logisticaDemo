package com.logistica.demo.repository;

import com.logistica.demo.model.Oditem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OditemRepo extends JpaRepository<Oditem,Integer> {
}
