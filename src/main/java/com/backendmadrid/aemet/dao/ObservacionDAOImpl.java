/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.backendmadrid.aemet.dao;

import com.backendmadrid.aemet.modelos.Observacion;
import com.sun.prism.impl.Disposer.Record;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
public class ObservacionDAOImpl implements ObservacionDAO {

    class ObservacionRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            return new Observacion(
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
            );
        }
    }

    @Autowired
    DataSource dataSource;

    public List<Observacion> listar() {
        List<Observacion> l;

        JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        String sql = "select * from observaciones order by id_estacion asc";

        l = jdbc.query(sql, new ObservacionRowMapper());
        return l;
    }

    public void insertarLista(List<Observacion> lo) {
        String sql = "insert into observaciones(fecha,id_estacion,tMed,prec,tMin,horaTMin,tMax,horaTMax,dir,velMedia,racha,horaRacha,sol,presMax,horaPresMax,presMin,horaPresMin)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            Connection con = dataSource.getConnection();
            con.setAutoCommit(false);
            PreparedStatement ps = con.prepareStatement(sql);
            for (Observacion obs : lo) {
                ps.setDate(1, java.sql.Date.valueOf(obs.getFecha()));
                ps.setString(2, obs.getId());
                ps.setObject(3, obs.gettMed());
                ps.setObject(4, obs.getPrec());
                ps.setObject(5, obs.gettMin());
                if (obs.getHoraTMin() != null)
                    ps.setObject(6, java.sql.Time.valueOf(obs.getHoraTMin()));
                else
                    ps.setNull(6, java.sql.Types.TIME);
                ps.setObject(7, obs.gettMax());
                if (obs.getHoraTMax() != null)
                    ps.setObject(8, java.sql.Time.valueOf(obs.getHoraTMax()));
                else
                    ps.setNull(8, java.sql.Types.TIME);
                ps.setObject(9, obs.getDir());
                ps.setObject(10, obs.getVelMedia());
                ps.setObject(11, obs.getRacha());
                if (obs.getHoraRacha() != null)
                    ps.setObject(12, java.sql.Time.valueOf(obs.getHoraRacha()));
                else
                    ps.setNull(12, java.sql.Types.TIME);
                ps.setObject(13, obs.getSol());
                ps.setObject(14, obs.getPresMax());
                if (obs.getHoraPresMax() != null)
                    ps.setTime(15, java.sql.Time.valueOf(obs.getHoraPresMax()));
                else
                    ps.setNull(15, java.sql.Types.TIME);
                ps.setObject(16, obs.getPresMin());
                if (obs.getHoraPresMin() != null)
                    ps.setObject(17, java.sql.Time.valueOf(obs.getHoraPresMin()));
                else
                    ps.setNull(17, java.sql.Types.TIME);
                 
                ps.addBatch();
            }
            ps.executeBatch();
            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException sqle) {
            System.out.println("Excepcion en insercion multiple de observaciones");
        }
    }

    public void insertar(Observacion o) {
        String sql = "insert into observaciones(fecha,id_estacion,tMed,prec,tMin,horaTMin,tMax,horaTMax,dir,velMedia,racha,horaRacha,sol,presMax,horaPresMax,presMin,horaPresMin)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        try {
            jdbc.update(sql, new Object[]{o.getFecha(),
                o.getId(),
                o.gettMed(),
                o.getPrec(),
                o.gettMin(),
                o.getHoraTMin(),
                o.gettMax(),
                o.getHoraTMax(),
                o.getDir(),
                o.getVelMedia(),
                o.getRacha(),
                o.getHoraRacha(),
                o.getSol(),
                o.getPresMax(),
                o.getHoraPresMax(),
                o.getPresMin(),
                o.getHoraPresMin()});
        } catch (DataAccessException dae) {
            //
        }
    }

    public void borrar(String id) {

        String sql = "delete from observaciones where id_estacion=?";
        JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        int n = jdbc.update(sql, new Object[]{id});

    }

    public List<Observacion> buscar(String id) {

        String sql = "select * from observaciones where id_estacion like ?";

        JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        List<Observacion> l = jdbc.query(sql, new ObservacionRowMapper(), new Object[]{"%" + id + "%"});

        return l;
    }

    public LocalDate buscarUltimaFecha() {
        String sql = "select MAX(fecha)  from observaciones";
        JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        LocalDate fecha = (LocalDate) jdbc.queryForObject(sql, LocalDate.class
        );
        return fecha;
    }

    public Observacion getObservacionFecha(String id, LocalDate fecha) {
        Observacion o = null;

        String sql = "select * from observaciones where id_estacion=? and fecha=?";
        JdbcTemplate jdbc = new JdbcTemplate(dataSource);

        o = (Observacion) jdbc.queryForObject(sql, new Object[]{id, fecha}, new ObservacionRowMapper());
        return o;
    }

}
