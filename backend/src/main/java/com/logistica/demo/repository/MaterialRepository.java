package com.logistica.demo.repository;

import com.logistica.demo.model.Material;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialRepository extends JpaRepository<Material,Integer> {

}
