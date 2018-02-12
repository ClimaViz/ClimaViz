/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.backendmadrid.aemet.dao;

import com.backendmadrid.aemet.modelos.Estacion;
import com.backendmadrid.aemet.modelos.Observacion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author USUARIO
 */
public class EstacionDAOImpl implements EstacionDAO {

    class EstacionRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            return new Estacion(
                    rs.getDouble("latitud"),
                    rs.getString("provincia"),
                    rs.getString("id"),
                    rs.getDouble("altitud"),
                    rs.getString("nombre"),
                    rs.getString("indsinop"),
                    rs.getDouble("longitud"),
                    rs.getInt("idprovincia")
            );
        }
    }

    class EstacionResumenRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            return new Estacion(
                    rs.getDouble("latitud"),
                    rs.getString("provincia"),
                    rs.getString("id"),
                    rs.getDouble("altitud"),
                    rs.getString("nombre"),
                    rs.getString("indsinop"),
                    rs.getDouble("longitud"),
                    rs.getInt("idprovincia"),
                    new Observacion(
                            rs.getDate("fecha").toLocalDate(),
                            rs.getString("id_estacion"),
                            (Double) rs.getObject("tmed"),
                            (Double) rs.getObject("prec"),
                            (Double) rs.getObject("tmin"),
                            ((rs.getTime("horaTMin") != null) ? rs.getTime("horaTMin").toLocalTime() : null),
                            (Double) rs.getObject("tmax"),
                            ((rs.getTime("horaTMax") != null) ? rs.getTime("horaTMax").toLocalTime() : null),
                            (Double) rs.getObject("dir"),
                            (Double) rs.getObject("velmedia"),
                            (Double) rs.getObject("racha"),
                            ((rs.getTime("horaRacha") != null) ? rs.getTime("horaRacha").toLocalTime() : null),
                            (Double) rs.getObject("sol"),
                            (Double) rs.getObject("presmax"),
                            ((rs.getTime("horaPresMax") != null) ? rs.getTime("horaPresMax").toLocalTime() : null),
                            (Double) rs.getObject("presmin"),
                            ((rs.getTime("horaPresMin") != null) ? rs.getTime("horaPresMin").toLocalTime() : null)
                    )
            );
        }
    }

    @Autowired
    DataSource dataSource;

    @Override
    public List<Estacion> listar() {
        List<Estacion> l;

        JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        String sql = "select * from estaciones order by provincia asc";
        l = jdbc.query(sql, new EstacionRowMapper());
        return l;
    }
    
    @Override
    public List<Estacion> listarFecha(LocalDate fecha) {
        List<Estacion> l = null;
        
        JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        String sql = "select * from estaciones " +
                     "inner join observaciones on estaciones.id = observaciones.id_estacion " +
                     "where observaciones.fecha = ?";
        l = jdbc.query(sql, new Object[]{fecha}, new EstacionResumenRowMapper());
        return l;
    }

    @Override
    public void insertar(Estacion e) {
        String sql = "insert into estaciones(latitud,provincia,id,altitud,nombre,indsinop,longitud)values(?,?,?,?,?,?,?)";
        JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        try {
            jdbc.update(sql, new Object[]{e.getLatitud(), e.getProvincia(), e.getId(), e.getAltitud(), e.getNombre(), e.getIndsinop(), e.getLongitud()});
        } catch (DataAccessException dae) {
            // nada
        }

    }

    @Override
    public void borrar(String nombre) {

        String sql = "delete from estaciones where nombre=?";
        JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        int n = jdbc.update(sql, new Object[]{nombre});

    }

    @Override
    public List<Estacion> buscar(String nombre) {

        String sql = "select * from estaciones where nombre like ?";

        JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        List<Estacion> l = jdbc.query(sql, new EstacionRowMapper(), new Object[]{"%" + nombre + "%"});

        return l;
    }

    @Override
    public List<Estacion> getEstacionesProvincia(int idProvincia) {

        String sql = "select * from estaciones where idProvincia=? ";
        JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        System.out.println(jdbc);
        List<Estacion> estaciones = jdbc.query(sql, new EstacionRowMapper(), new Object[]{idProvincia});
        return estaciones;
    }

}
