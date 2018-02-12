/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.backendmadrid.aemet.modelos;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author USUARIO
 */
public class Observacion {
    private LocalDate fecha;
    private String id;
    private Double tMed;
    private Double prec;
    private Double tMin;
    private LocalTime horaTMin;
    private Double tMax;
    private LocalTime horaTMax;
    private Double dir;
    private Double velMedia;
    private Double racha;
    private LocalTime horaRacha;
    private Double sol;
    private Double presMax;
    private LocalTime horaPresMax;
    private Double presMin;
    private LocalTime horaPresMin;

    public Observacion() {
    }

    public Observacion(LocalDate fecha, String id) {
        this.fecha = fecha;
        this.id = id;
    }

    public Observacion(LocalDate fecha, String id, Double tMed, Double prec, Double tMin, LocalTime horaTMin, Double tMax, LocalTime horaTMax, Double dir, Double velMedia, Double racha, LocalTime horaRacha, Double sol, Double presMax, LocalTime horaPresMax, Double presMin, LocalTime horaPresMin) {
        this.fecha = fecha;
        this.id = id;
        this.tMed = tMed;
        this.prec = prec;
        this.tMin = tMin;
        this.horaTMin = horaTMin;
        this.tMax = tMax;
        this.horaTMax = horaTMax;
        this.dir = dir;
        this.velMedia = velMedia;
        this.racha = racha;
        this.horaRacha = horaRacha;
        this.sol = sol;
        this.presMax = presMax;
        this.horaPresMax = horaPresMax;
        this.presMin = presMin;
        this.horaPresMin = horaPresMin;
    }
    

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double gettMed() {
        return tMed;
    }

    public void settMed(Double tMed) {
        this.tMed = tMed;
    }

    public Double getPrec() {
        return prec;
    }

    public void setPrec(Double prec) {
        this.prec = prec;
    }

    public Double gettMin() {
        return tMin;
    }

    public void settMin(Double tMin) {
        this.tMin = tMin;
    }

    public LocalTime getHoraTMin() {
        return horaTMin;
    }

    public void setHoraTMin(LocalTime horaTMin) {
        this.horaTMin = horaTMin;
    }

    public Double gettMax() {
        return tMax;
    }

    public void settMax(Double tMax) {
        this.tMax = tMax;
    }

    public LocalTime getHoraTMax() {
        return horaTMax;
    }

    public void setHoraTMax(LocalTime horaTMax) {
        this.horaTMax = horaTMax;
    }

    public Double getDir() {
        return dir;
    }

    public void setDir(Double dir) {
        this.dir = dir;
    }

    public Double getVelMedia() {
        return velMedia;
    }

    public void setVelMedia(Double velMedia) {
        this.velMedia = velMedia;
    }

    public Double getRacha() {
        return racha;
    }

    public void setRacha(Double racha) {
        this.racha = racha;
    }

    public LocalTime getHoraRacha() {
        return horaRacha;
    }

    public void setHoraRacha(LocalTime horaRacha) {
        this.horaRacha = horaRacha;
    }

    public Double getSol() {
        return sol;
    }

    public void setSol(Double sol) {
        this.sol = sol;
    }

    public Double getPresMax() {
        return presMax;
    }

    public void setPresMax(Double presMax) {
        this.presMax = presMax;
    }

    public LocalTime getHoraPresMax() {
        return horaPresMax;
    }

    public void setHoraPresMax(LocalTime horaPresMax) {
        this.horaPresMax = horaPresMax;
    }

    public Double getPresMin() {
        return presMin;
    }

    public void setPresMin(Double presMin) {
        this.presMin = presMin;
    }

    public LocalTime getHoraPresMin() {
        return horaPresMin;
    }

    public void setHoraPresMin(LocalTime horaPresMin) {
        this.horaPresMin = horaPresMin;
    }

    
   
    
}