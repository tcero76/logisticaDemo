package com.logistica.demo.payload;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UbicacionesReq {

    public UbicacionesReq(String nombre, Integer id, List<Object> niveles) {
        this.nombre = nombre;
        this.id = id;
        this.niveles = niveles.stream()
                .map(o -> new UbicacionesReq.Nivel(o))
                .collect(Collectors.toList());
    }

    private String nombre;

    private Integer id;

    private List<Nivel> niveles;

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

    public List<Nivel> getNiveles() {
        return niveles;
    }

    public void setNiveles(List<Nivel> niveles) {
        this.niveles = niveles;
    }

    public class Nivel {

        public Nivel(Object o) {
            this.nombre = (String) ((Map)o).get("nombre");
            this.id = (Integer) ((Map)o).get("id");
            this.poses = ((List<Object>) ((Map)o).get("poses")).stream()
                            .map(Pos::new)
                            .collect(Collectors.toList());
        }

        private String nombre;

        private Integer id;

        private List<UbicacionesReq.Nivel.Pos> poses;

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

        public List<Pos> getPoses() {
            return poses;
        }

        public void setPoses(List<Pos> poses) {
            this.poses = poses;
        }

        public class Pos {

            public Pos(Object o) {
                this.nombre = (String) ((Map)o).get("nombre");
                this.id = (Integer) ((Map)o).get("id");
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
