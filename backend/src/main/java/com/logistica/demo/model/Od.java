package com.logistica.demo.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "od")
public class Od {

    public Od() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idod;

    @Column(name = "fecharegistro")
    private Date fecharegistros;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idusuario")
    private Usuario usuario;

    @OneToMany(mappedBy = "od", cascade = {CascadeType.DETACH, CascadeType.MERGE,
                                            CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<Oditem> oditems;

    public Set<Oditem> getOditems() {
        return oditems;
    }

    public void setOditems(Set<Oditem> oditems) {
        this.oditems = oditems;
    }

    public Integer getIdod() {
        return idod;
    }

    public void setIdod(Integer idod) {
        this.idod = idod;
    }

    public Date getFecharegistros() {
        return fecharegistros;
    }

    public void setFecharegistros(Date fecharegistros) {
        this.fecharegistros = fecharegistros;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Od od = (Od) o;

        return idod != null ? idod.equals(od.idod) : od.idod == null;
    }

    @Override
    public int hashCode() {
        return idod != null ? idod.hashCode() : 0;
    }

    @Override
    public String toString() {
        return String.valueOf(idod);
    }
}
