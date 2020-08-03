package com.logistica.demo.payload;

import java.util.List;
import java.util.stream.Collectors;

public class ResZonaDetail {

    public ResZonaDetail(com.logistica.demo.model.Zona zona) {
        this.idzona = zona.getIdzona();
        this.nombre = zona.getNombre();
        this.niveles = zona.getNiveles().stream()
                .map(Nivel::new)
                .collect(Collectors.toList());
    }

    private Integer idzona;

    private String nombre;

    private List<Nivel> niveles;

    public Integer getIdzona() {
        return idzona;
    }

    public void setIdzona(Integer idzona) {
        this.idzona = idzona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Nivel> getNiveles() {
        return niveles;
    }

    public void setNiveles(List<Nivel> niveles) {
        this.niveles = niveles;
    }

    private class Nivel {

        public Nivel(com.logistica.demo.model.Nivel nivel) {
            this.idnivel = nivel.getIdnivel();
            this.nombre = nivel.getNombre();
            this.poses = nivel.getPoses().stream()
                    .map(Pos::new)
                    .collect(Collectors.toList());
        }

        private Integer idnivel;

        private String nombre;

        private List<Pos> poses;

        public List<Pos> getPoses() {
            return poses;
        }

        public void setPoses(List<Pos> poses) {
            this.poses = poses;
        }

        private class Pos {

            public Pos(com.logistica.demo.model.Pos pos) {
                this.idpos = pos.getIdpos();
                this.nombre = pos.getNombre();
            }

            private Integer idpos;

            private String nombre;

            public Integer getIdpos() {
                return idpos;
            }

            public void setIdpos(Integer idpos) {
                this.idpos = idpos;
            }

            public String getNombre() {
                return nombre;
            }

            public void setNombre(String nombre) {
                this.nombre = nombre;
            }
        }

        public Integer getIdnivel() {
            return idnivel;
        }

        public void setIdnivel(Integer idnivel) {
            this.idnivel = idnivel;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }
    }
}
