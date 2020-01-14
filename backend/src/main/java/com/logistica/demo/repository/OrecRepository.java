package com.logistica.demo.repository;

import com.logistica.demo.model.Orec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrecRepository extends JpaRepository<Orec,Integer> {
}
