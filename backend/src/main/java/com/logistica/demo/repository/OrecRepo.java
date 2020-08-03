package com.logistica.demo.repository;

import com.logistica.demo.model.Orec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface OrecRepo extends JpaRepository<Orec,Integer> {
}
