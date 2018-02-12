/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.backendmadrid.aemet.modelos;


public class MetaDato {
    private String codigo;
    private String descripcion;
    private String unidades;
    
    public MetaDato(){
        
    }
    
    public MetaDato (String codigo, String descripcion, String unidades) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.unidades = unidades;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUnidades() {
        return unidades;
    }

    public void setUnidades(String unidades) {
        this.unidades = unidades;
    }
    
    
}



