/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.backendmadrid.aemet.dao;

import com.backendmadrid.aemet.modelos.Provincia;
import java.util.List;
import java.util.TreeMap;

/**
 *
 * @author USUARIO
 */
public interface ProvinciaDAO{
    
     public List<Provincia> getProvincias();
     
     public TreeMap<String, Integer> getIdProvincias();
    
}
