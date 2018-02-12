package com.backendmadrid.aemet.controller;

import com.backendmadrid.aemet.dao.EstacionDAO;
import com.backendmadrid.aemet.dao.ObservacionDAO;
import com.backendmadrid.aemet.dao.ProvinciaDAO;
import com.backendmadrid.aemet.dao.ResultadoDAO;
import com.backendmadrid.aemet.modelos.Estacion;
import com.backendmadrid.aemet.modelos.MetaDato;
import com.backendmadrid.aemet.modelos.Observacion;
import com.backendmadrid.aemet.modelos.Provincia;
import com.backendmadrid.aemet.modelos.Resultado;
import com.backendmadrid.aemet.opendata.LectorAemet;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EstacionController {

    @Autowired
    EstacionDAO estacionDAO;

    @Autowired
    ObservacionDAO observacionDAO;

    @Autowired
    ProvinciaDAO provinciaDAO;

    @Autowired
    ResultadoDAO resultadoDAO;

    @Autowired
    LectorAemet lectorAemet;

    @RequestMapping(value = {"/", "Home"})
    public ModelAndView listar(HttpServletResponse response) throws IOException {
        ModelAndView m = new ModelAndView("listar");

        LocalDate fechaInicio = LocalDate.now();
        fechaInicio = fechaInicio.minusDays(5);
        LocalDate ultimaFecha = observacionDAO.buscarUltimaFecha();
        if(ultimaFecha == null || fechaInicio.compareTo(ultimaFecha) > 0) {
            //Actualizar Estaciones
            List<Estacion> listaEstaciones = lectorAemet.getEstaciones();
            for (Estacion e : listaEstaciones) {
                System.out.println("insertar est " + e.getId() + ", PROVINCIA: " + e.getIdProvincia());
                estacionDAO.insertar(e);
            }
            //Actualizar Observaciones
            List<MetaDato> listaMeta = lectorAemet.getMetaDatos();
            List<Observacion> listaObs = lectorAemet.getObservaciones(
                    listaMeta, fechaInicio.minusDays(1), fechaInicio.plusDays(1));
            observacionDAO.insertarLista(listaObs);
        }
        ultimaFecha = observacionDAO.buscarUltimaFecha();
        List<Estacion> l = estacionDAO.listarFecha(ultimaFecha);
        m.addObject("listado", l);
        List<Provincia> provincias = provinciaDAO.getProvincias();
        m.addObject("provincias", provincias);
        m.addObject("fechaComp", ultimaFecha);
        return m;
    }
    
    @RequestMapping(value = "Listar")
    public ModelAndView listarFecha(HttpServletResponse response,
                                    @RequestParam(value = "fecha") String stFecha) 
            throws IOException {
        ModelAndView m = new ModelAndView("listar");

        LocalDate fechaInicio = LocalDate.parse(stFecha);
        List<Estacion> l = estacionDAO.listarFecha(fechaInicio);
        
        if(l == null || l.size() == 0) {
            //Actualizar Observaciones
            List<MetaDato> listaMeta = lectorAemet.getMetaDatos();
            List<Observacion> listaObs = lectorAemet.getObservaciones(
                    listaMeta, fechaInicio.minusDays(1), fechaInicio.plusDays(1));
            observacionDAO.insertarLista(listaObs);
            l = estacionDAO.listarFecha(fechaInicio);
        }
        m.addObject("listado", l);
        List<Provincia> provincias = provinciaDAO.getProvincias();
        m.addObject("provincias", provincias);
        m.addObject("fechaComp", fechaInicio);
        return m;
    }

    @RequestMapping(value = "/getEstaciones")//,produces = "application/json"
    @ResponseBody
    public String getEstaciones(HttpServletResponse response,
            @RequestParam(value = "provincia") int idProvincia) {

        List<Estacion> estaciones = estacionDAO.getEstacionesProvincia(idProvincia);
        String respuesta = "[";
        for (Estacion e : estaciones) {
            respuesta += "{\"id\":\"" + e.getId() + "\",\"nombre\":\"" + e.getNombre() + "\"},";
        }
        respuesta = respuesta.substring(0, respuesta.length() - 1) + "]";
        return respuesta;
    }

    @RequestMapping(value = "/GetObservacionFecha")
    public ModelAndView getobservacionfecha(
            HttpServletResponse response,
            @RequestParam(value = "estacion") String id,
            @RequestParam(value = "fechacons") String stFecha
    ) throws IOException {

        LocalDate fecha = LocalDate.parse(stFecha);
        Observacion o = observacionDAO.getObservacionFecha(id, fecha);
        ModelAndView mv = new ModelAndView("getobservacionfecha");
        mv.addObject("observacion", o);
        return mv;
    }

    @RequestMapping(value = "/ListarResultados")
    public ModelAndView listarres(
            HttpServletResponse response
            //@RequestParam(value = "fecha") String stFecha
    ) throws IOException {

        //LocalDate fecha = LocalDate.parse(stFecha);
        LocalDate fecha = LocalDate.parse("2018-01-21");
        List<Resultado> r = resultadoDAO.calculoMediaTemperatura(fecha);
        ModelAndView mv = new ModelAndView("listarresultados");
        System.out.println(">>resultado size: " + r.size());
        for (Resultado rr : r) {
            System.out.println("Lat " + rr.getLatitud() + " Lon " + rr.getLongitud() + " Res " + rr.getResultado());
        }
        mv.addObject("listaresultado", r);
        return mv;
    }
    
    @RequestMapping(value = "Analisis")
    public ModelAndView analisis(
            HttpServletResponse response,
            @RequestParam(value = "fechaanalisis") String stFecha,
            @RequestParam(value = "tiempo") String tiempo,
            @RequestParam(value = "tipoanalisis") String tipoAnalisis
    ) throws IOException {
        LocalDate fecha = LocalDate.parse(stFecha);
        fecha = fecha.withDayOfMonth(1);
        LocalDate fechaFin = fecha.withDayOfMonth(fecha.lengthOfMonth());
        int j = 0;
        for (int i = 3; i <= 34; i+=3) {
            List<MetaDato> listaMeta = lectorAemet.getMetaDatos();
            List<Observacion> listaObs = lectorAemet.getObservaciones(
                    listaMeta, fecha.plusDays(j), fecha.plusDays(i));
            observacionDAO.insertarLista(listaObs);
            j = i + 1;
        }
        
        //LocalDate fecha = LocalDate.parse("2018-01-21");
        
        List<Resultado> r = resultadoDAO.calculoMediaTemperatura(fecha);
        ModelAndView mv = new ModelAndView("analisis");
        mv.addObject("listado", r);
        return mv;
    }

}
