package com.logistica.demo.dao;

import com.logistica.demo.model.Od;

import java.util.List;
import java.util.Optional;

public interface OdDao {
    public Optional<Od> findById(Integer id);
}
