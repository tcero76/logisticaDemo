package com.logistica.demo.controller;

import com.logistica.demo.model.Material;
import com.logistica.demo.payload.ResMaterial;
import com.logistica.demo.service.MaterialService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MaterialController {

    private static final Logger log = LoggerFactory.getLogger(MaterialController.class);

    @Autowired
    private MaterialService materialService;

    @GetMapping("/materiales")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<ResMaterial> listarMateriales() {
        List<Material> materiales = materialService.findAll();
        return materiales.stream()
                .map(ResMaterial::new)
                .collect(Collectors.toList());
    }
}
