package com.logistica.demo.repository;

import com.logistica.demo.model.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MaterialRepo extends JpaRepository<Material,Integer> {

}
