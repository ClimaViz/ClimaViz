/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.backendmadrid.aemet.dao;

import com.backendmadrid.aemet.modelos.Estacion;
import com.backendmadrid.aemet.modelos.Observacion;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author USUARIO
 */
public interface ObservacionDAO {
    
    public List<Observacion> listar();
    
    public void insertar (Observacion o);
    
    public void insertarLista(List<Observacion> lo);
    
    public void borrar (String id);
    
    public List<Observacion> buscar(String id);
    
    public LocalDate buscarUltimaFecha();
     
    public Observacion getObservacionFecha(String id, LocalDate fecha); 
}
