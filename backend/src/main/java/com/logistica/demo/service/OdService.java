package com.logistica.demo.service;

import com.logistica.demo.model.Od;

import java.util.Optional;


public interface OdService {
    public Optional<Od> findById(Integer id);
}
