package com.logistica.demo.controller;

import com.logistica.demo.payload.ResFlujo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Informe")
public class InformeController {

    public List<ResFlujo> listFlujo() {
        return null;
    }
}
