package com.logistica.demo.dao;

import com.logistica.demo.model.*;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class InventarioDaoImpl implements InventarioDao {

    @Autowired
    private EntityManager em;

    @Override
    public Integer ultInventario(Material material, Pos pos) {
        Session ss = em.unwrap(Session.class);
        String hql = "select max(inv.idinventario) from Inventario inv join  inv.material m join inv.pos p " +
                "where m.idmaterial = :idmaterial and p.idpos = :idpos";
        return (Integer) ss
                .createQuery(hql)
                .setParameter("idmaterial",material.getIdmaterial())
                .setParameter("idpos", pos.getIdpos())
                .uniqueResult();
    }

    @Override
    public List<Inventario> listarInventario(Almacen almacen) {
        Session ss = em.unwrap(Session.class);
        String sql = "select {i.*}, {m.*}, {p.*}, {n.*}, {z.*}, {a.*} from (SELECT \n" +
                "    MAX(i.idinventario) as idinventario\n" +
                "FROM\n" +
                "    logistica.inventario i\n" +
                "    GROUP BY i.idmaterial , i.idpos) as ult\n" +
                "    left join logistica.inventario as i on i.idinventario = ult.idinventario\n" +
                "    left join logistica.material as m on m.idmaterial=i.idmaterial\n" +
                "    left join logistica.pos as p on i.idpos = p.idpos\n" +
                "    left join logistica.nivel as n on n.idnivel = p.idnivel\n" +
                "    left join logistica.zona as z on z.idzona = n.idzona\n" +
                "    left join logistica.almacen as a on a.idalmacen = z.idalmacen\n" +
                "    where a.idalmacen = :idalmacen ;";
        List<Object[]> resp = ss
                .createSQLQuery(sql)
                .setParameter("idalmacen", almacen.getIdalmacen())
                .addEntity("i", Inventario.class)
                .addEntity("m", Material.class)
                .addEntity("p", Pos.class)
                .addEntity("n", Nivel.class)
                .addEntity("z", Zona.class)
                .addEntity("a", Almacen.class)
                .list();

        return resp.stream()
                .map(i -> (Inventario)i[0])
                .collect(Collectors.toList());
    }

    @Override
    public List<Inventario> listarByMaterial(Almacen almacen, Integer idmaterial) {
        Session ss = em.unwrap(Session.class);
        String hql = "select i from Inventario i left join i.material m left join fetch i.pos p where m.idmaterial = :idmaterial";
        return ss.createQuery(hql)
                .setParameter("idmaterial", idmaterial)
                .list();
    }
}
