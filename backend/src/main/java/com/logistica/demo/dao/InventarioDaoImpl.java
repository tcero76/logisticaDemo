package com.logistica.demo.dao;

import com.logistica.demo.model.*;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class InventarioDaoImpl implements InventarioDao {

    @Autowired
    private EntityManager em;

    @Override
    public Optional<Integer> ultInventario(Integer idmaterial, Integer idpos) {
        String jpql = "select max(inv.idinventario) " +
                "from Inventario inv " +
                "join inv.material m " +
                "join inv.pos p " +
                "where m.idmaterial = :idmaterial " +
                "and p.idpos = :idpos";
        return Optional.ofNullable((Integer)em
                .createQuery(jpql)
                .setParameter("idmaterial",idmaterial)
                .setParameter("idpos", idpos)
                .getSingleResult());
    }

    @Override
    public Optional<List<Inventario>> listarInventario(Almacen almacen, Integer idmaterial) {
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
                "    where a.idalmacen = :idalmacen and m.idmaterial = :idmaterial ;";
        List<Object[]> resp = ss
                .createSQLQuery(sql)
                .setParameter("idalmacen", almacen.getIdalmacen())
                .setParameter("idmaterial", idmaterial)
                .addEntity("i", Inventario.class)
                .addEntity("m", Material.class)
                .addEntity("p", Pos.class)
                .addEntity("n", Nivel.class)
                .addEntity("z", Zona.class)
                .addEntity("a", Almacen.class)
                .list();

        return Optional.of(resp.stream()
                .map(i -> (Inventario)i[0])
                .collect(Collectors.toList()));
    }

    @Override
    public Optional<List<Inventario>> listarByMaterial(Almacen almacen, Integer idmaterial, Integer pageSize, Integer offset) {
        Session ss = em.unwrap(Session.class);
        String hql = "select i from Inventario i " +
                "left join i.material m " +
                "left join fetch i.pos p " +
                "left join fetch p.nivel n " +
                "left join fetch n.zona z " +
                "where m.idmaterial = :idmaterial";
        return Optional.of(ss.createQuery(hql)
                .setParameter("idmaterial", idmaterial)
                .setMaxResults(pageSize)
                .setFirstResult(offset)
                .list());
    }

    @Override
    public Optional<Integer> countByAlmacenAndMaterial(Integer idalmacen, Integer idmaterial) {
        String jpql = "select count(i) from Inventario i " +
                "left join i.pos p " +
                "left join p.nivel n " +
                "left join n.zona z " +
                "left join z.almacen a " +
                "left join i.material m " +
                "where m.idmaterial = :idmaterial and " +
                "a.idalmacen = :idalmacen ";
        return Optional.of(((Long)em
                .createQuery(jpql)
                .setParameter("idalmacen",idalmacen)
                .setParameter("idmaterial", idmaterial)
                .getSingleResult()).intValue());
    }

    @Override
    public List<Inventario> listarInventario(Integer idzona) {
        Session ss = em.unwrap(Session.class);
        String sql = "SELECT \n" +
                "    {i.*}, {m.*}, {p.*}, {n.*}, {z.*}, {a.*}\n" +
                "FROM\n" +
                "    (SELECT \n" +
                "        MAX(i.idinventario) AS idinventario\n" +
                "    FROM\n" +
                "        logistica.inventario i\n" +
                "    GROUP BY i.idmaterial , i.idpos) AS ult\n" +
                "        LEFT JOIN\n" +
                "    logistica.inventario AS i ON i.idinventario = ult.idinventario\n" +
                "        LEFT JOIN\n" +
                "    logistica.material AS m ON m.idmaterial = i.idmaterial\n" +
                "        LEFT JOIN\n" +
                "    logistica.pos AS p ON i.idpos = p.idpos\n" +
                "        LEFT JOIN\n" +
                "    logistica.nivel AS n ON n.idnivel = p.idnivel\n" +
                "        LEFT JOIN\n" +
                "    logistica.zona AS z ON z.idzona = n.idzona\n" +
                "        LEFT JOIN\n" +
                "    logistica.almacen AS a ON a.idalmacen = z.idalmacen\n" +
                "WHERE\n" +
                "    z.idzona = :idzona ;";
         List<Object[]> query = ss
                .createSQLQuery(sql)
                .setParameter("idzona", idzona)
                .addEntity("i", Inventario.class)
                .addEntity("m", Material.class)
                .addEntity("p", Pos.class)
                .addEntity("n", Nivel.class)
                .addEntity("z", Zona.class)
                .addEntity("a", Almacen.class)
                 .list();
         return query.stream()
                 .map(q -> (Inventario)q[0])
                 .collect(Collectors.toList());
    }
}
