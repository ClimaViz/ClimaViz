/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.backendmadrid.aemet.dao;

import com.backendmadrid.aemet.modelos.Provincia;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.TreeMap;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author USUARIO
 */
public class ProvinciaDAOImpl implements ProvinciaDAO {

    class ProvinciaRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            return new Provincia(rs.getInt("id"), rs.getString("nombre"));
        }

    }

    @Autowired
    DataSource dataSource;

    public List<Provincia> getProvincias() {
        String sql = "select * from provincias order by nombre asc";
        JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        List<Provincia> lista = jdbc.query(sql, new ProvinciaRowMapper());
        System.out.println(lista);
        return lista;
    }

    public TreeMap<String, Integer> getIdProvincias() {
        TreeMap<String, Integer> res = new TreeMap<>();
        List<Provincia> lista = getProvincias();
        for (Provincia p : lista) {
            res.put(p.getNombre(), p.getId());
        }
        return res;
    }
}
