/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.backendmadrid.aemet.dao;

import com.backendmadrid.aemet.modelos.Estacion;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author USUARIO
 */
public interface EstacionDAO {
    
    public List<Estacion> listar();
    
    public List<Estacion> listarFecha(LocalDate fecha);
    
    public void insertar (Estacion e);
    
    public void borrar (String nombre);
    
    public List<Estacion> buscar(String nombre);
    
    public List<Estacion> getEstacionesProvincia(int idProvincia);
    
    
}
