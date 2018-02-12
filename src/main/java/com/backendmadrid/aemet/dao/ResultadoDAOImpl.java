/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.backendmadrid.aemet.dao;

import com.backendmadrid.aemet.modelos.Resultado;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author USUARIO
 */
public class ResultadoDAOImpl implements ResultadoDAO{
    
     class ResultadoRowMapper implements RowMapper{
        
     @Override 
        public Object mapRow(ResultSet rs, int i) throws SQLException{
            return new Resultado(
            (Double)rs.getObject("resultado"),
            (Double)rs.getObject("latitud"),
            (Double)rs.getObject("longitud")
            );
        }
     }
    
    @Autowired
    DataSource dataSource;
    
    public List<Resultado> calculoMediaTemperatura(LocalDate fecha){
        String sql="select avg(prec) resultado, latitud, longitud \n" +
        "   from observaciones \n" +
        "   inner join estaciones on observaciones.id_estacion=estaciones.id\n" +
        "   where extract(YEAR_MONTH  from fecha)=extract(YEAR_MONTH from ?) and prec is not null\n" +
        "   group by id_estacion";
        
        JdbcTemplate jdbc=new JdbcTemplate(dataSource);
        
        List<Resultado> l=jdbc.query(sql,new ResultadoRowMapper(),new Object[]{fecha});
        return l;
    }
}
