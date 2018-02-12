/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.backendmadrid.aemet.modelos;

/**
 *  Consulta sumatorio prec select sum(prec) PRECIPITACION, id_estacion ID from observaciones group by id_estacion;
 * select sum(prec) PRECIPITACION, id_estacion ID 
   from observaciones 
   where fecha in ('2018-02-01', '2018-02-04') and prec is not null
   group by id_estacion;
 * @author USUARIO
 */
public class Resultado {
    private double resultado;
    private double latitud;
    private double longitud;

    public Resultado() {
    }

    public Resultado(Double resultado, Double latitud, Double longitud) {
        this.resultado = resultado;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public double getResultado() {
        return resultado;
    }

    public void setResultado(Double resultado) {
        this.resultado = resultado;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    
    
    
    
}
