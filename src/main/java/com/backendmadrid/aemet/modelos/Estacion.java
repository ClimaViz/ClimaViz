/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.backendmadrid.aemet.modelos;

/**
 *
 * @author USUARIO
 */
public class Estacion {
    private double latitud;
    private String provincia;
    private String id;
    private double altitud;
    private String nombre;
    private String indsinop;
    private double longitud;
    private int idProvincia;
    private Observacion resumen;

    public Estacion() {
    }
    
    public Estacion(double latitud, String provincia, String id, double altitud, String nombre, String indsinop, double longitud, int idProvincia) {
        this.latitud = latitud;
        this.provincia = provincia;
        this.id = id;
        this.altitud = altitud;
        this.nombre = nombre;
        this.indsinop = indsinop;
        this.longitud = longitud;
        this.idProvincia = idProvincia; 
    }
    
    public Estacion(double latitud, String provincia, String id, double altitud, String nombre, String indsinop, double longitud, int idProvincia, Observacion resumen) {
        this(latitud, provincia, id, altitud, nombre, indsinop, longitud, idProvincia);
        this.resumen = resumen;
    }

    public Observacion getResumen() {
        return resumen;
    }

    public void setResumen(Observacion resumen) {
        this.resumen = resumen;
    }

    
    
    public Estacion(String provincia) {
        this.provincia = provincia;
    }

    public int getIdProvincia() {
        return idProvincia;
    }

    public void setIdProvincia(int idProvincia) {
        this.idProvincia = idProvincia;
    }
    
    

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getAltitud() {
        return altitud;
    }

    public void setAltitud(double altitud) {
        this.altitud = altitud;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIndsinop() {
        return indsinop;
    }

    public void setIndsinop(String indsinop) {
        this.indsinop = indsinop;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
    
    
    
}
