package com.logistica.demo.service;

import com.logistica.demo.model.Od;
import com.logistica.demo.model.Usuario;
import com.logistica.demo.payload.OdReq;

import java.util.List;

public interface OditemService {
    public Od addOditems(List<OdReq> odReq, Usuario usuario);
}
