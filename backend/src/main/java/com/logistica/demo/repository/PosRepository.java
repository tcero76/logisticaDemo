package com.logistica.demo.repository;

import com.logistica.demo.model.Pos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PosRepository extends JpaRepository<Pos,Integer> {
}
