package com.logistica.demo.exception;


public class NotFoundException extends RuntimeException {

    private String id;
    private String nombre;
    private Object valor;

    public NotFoundException(String id, String nombre, Object valor) {
        super(String.format("No fue posible encontrar el %s correspondiente a %s con valor %s", id, nombre, valor));
        this.id = id;
        this.nombre = nombre;
        this.valor = valor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }
}
