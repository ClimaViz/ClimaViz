/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.backendmadrid.aemet.dao;

import com.backendmadrid.aemet.modelos.Resultado;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author USUARIO
 */
public interface ResultadoDAO {
    
    public List<Resultado> calculoMediaTemperatura(LocalDate fecha); 
    
    
}
