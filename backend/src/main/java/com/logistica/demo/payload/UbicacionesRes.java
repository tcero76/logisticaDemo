package com.logistica.demo.payload;

import com.logistica.demo.model.Zona;

import java.util.Set;
import java.util.stream.Collectors;

public class UbicacionesRes {

    public UbicacionesRes(Zona zona) {
        this.nombre = zona.getNombre();
        this.id = zona.getIdzona();
        this.niveles = zona.getNiveles().stream()
                        .map(Nivel::new)
                        .collect(Collectors.toSet());
    }

    private String nombre;

    private Integer id;

    private Set<Nivel> niveles;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<Nivel> getNiveles() {
        return niveles;
    }

    public void setNiveles(Set<Nivel> niveles) {
        this.niveles = niveles;
    }

    private class Nivel {

        public Nivel(com.logistica.demo.model.Nivel nivel) {
            this.nombre = nivel.getNombre();
            this.id = nivel.getIdnivel();
            this.poses = nivel.getPoses().stream()
                    .map(Pos::new)
                    .collect(Collectors.toSet());
        }

        private String nombre;

        private Integer id;

        private Set<Pos> poses;

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Set<Pos> getPoses() {
            return poses;
        }

        public void setPoses(Set<Pos> poses) {
            this.poses = poses;
        }

        private class Pos {

            public Pos(com.logistica.demo.model.Pos pos) {
                this.nombre = pos.getNombre();
                this.id = pos.getIdpos();
            }

            private String nombre;

            private Integer id;

            public String getNombre() {
                return nombre;
            }

            public void setNombre(String nombre) {
                this.nombre = nombre;
            }

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }
        }

    }

}
