package com.logistica.demo.service;

import com.logistica.demo.model.*;
import com.logistica.demo.payload.CuadrarCuentaReq;
import com.logistica.demo.payload.CuadrarInventarioReq;
import com.logistica.demo.payload.ResCuentaCuadratura;
import com.logistica.demo.payload.UbicacionReq;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface InventarioService {
    public List<Inventario> listarInventario(Almacen almacen, Integer idmaterial);
    public List<Inventario> listarInventario(Integer idzona);
    public List<Inventario> listarByMaterial(Almacen almacen, Integer idmaterial, Integer pageSize, Integer offset);

    public void saverAll(List<Inventario> inventarios);

    public Integer countByMaterial(Almacen almacen, Integer idmaterial);

    public void addInventario(UbicacionReq ubicacionReq, Usuario usuario);

    public List<ResCuentaCuadratura> addInventario(CuadrarCuentaReq cuadrarCuentaReq, Usuario usuario);

    public List<ResCuentaCuadratura> findCuadratura(Integer idcuenta);

    public List<ResCuentaCuadratura> addInventario(CuadrarInventarioReq cuadrarCuentaReq, Usuario usuario);
}
