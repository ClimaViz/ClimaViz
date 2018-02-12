/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.backendmadrid.aemet.opendata;

/**
 *
 * @author USUARIO
 */
import com.backendmadrid.aemet.dao.ProvinciaDAO;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.backendmadrid.aemet.modelos.Estacion;
import com.backendmadrid.aemet.modelos.MetaDato;
import com.backendmadrid.aemet.modelos.Observacion;
import com.backendmadrid.aemet.modelos.Provincia;
import java.util.TreeMap;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author USUARIO
 */
public class LectorAemet {
    @Autowired
    ProvinciaDAO provinciaDAO;

    private static final String API_KEY = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2UuYWx2cnpAZ21haWwuY29tIiwianRpIjoiODQzMGU2NzAtMmI2MS00OWE1LWFhOTQtM2VkMDVkODNjM2RlIiwiaXNzIjoiQUVNRVQiLCJpYXQiOjE1MTY4MTIwODcsInVzZXJJZCI6Ijg0MzBlNjcwLTJiNjEtNDlhNS1hYTk0LTNlZDA1ZDgzYzNkZSIsInJvbGUiOiIifQ.2nkpWDExJlPtAKhx96JPPrjlIbON6f71KviqSuvIh5Q";
    private ConectorAemet conAemet = new ConectorAemet();
    
    public List<Observacion> getObservaciones(List<MetaDato> metaDatos, LocalDate fechaInicio, LocalDate fechaFin) {
        List<Observacion> listadoObservacion = new ArrayList<>();
        OkHttpClient cliente = conAemet.getCliente();
        LocalDateTime dtInicio = fechaInicio.atStartOfDay();
        LocalDateTime dtFin = fechaFin.atStartOfDay();
        String urlRequest
                = "https://opendata.aemet.es/opendata/api/valores/climatologicos/diarios/datos"
                + "/fechaini/" + dtInicio + ":00UTC"
                + "/fechafin/" + dtFin + ":00UTC"
                + "/todasestaciones/";
        Request request = new Request.Builder()
                .url(urlRequest + "/?api_key=" + API_KEY)
                .build();
        String urlDatosRespuesta = null;
        try (Response response = cliente.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Excepción en conexión con AEMET: Código no esperado " + response);
            }
            String jsonDataStr = response.body().string();
            JSONObject jsonObject = new JSONObject(jsonDataStr);
            urlDatosRespuesta = (String) jsonObject.get("datos");
        } catch (IOException ioe) {
            System.out.println("Excepción en la conexión con AEMET OpenData para solicitar listado de estaciones meteo");
            System.out.println(ioe);
        }
        if (urlDatosRespuesta != null) {
            request = new Request.Builder().url(urlDatosRespuesta).build();
            try (Response response = cliente.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new IOException("Excepción en la conexión con AEMET OpenData: Código no esperado " + response);
                }
                String jsonDataStr = response.body().string().toLowerCase();
                JSONArray jsonArray = new JSONArray(jsonDataStr);

                // convertir en POJOs
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    // campos obligatorios: fecha y identificador 
                    String sFecha = object.getString("fecha");
                    LocalDate fechaObservacion = LocalDate.parse(sFecha);
                    String id = object.getString("indicativo");
                    Observacion obs = new Observacion(fechaObservacion, id.toUpperCase());
                    // campos opcionales: buscar en metadatos
                    for (MetaDato md : metaDatos) {
                        String codigo = md.getCodigo().toLowerCase();
                        LocalTime horaDato = null;
                        Double dato = null;
                        try {
                            if (codigo.contains("hora")) {
                                //int hh = object.getInt(codigo);
                                String sHora = object.getString(codigo);
                                if (sHora.toLowerCase().equals("varias")) {
                                    horaDato = LocalTime.of(00, 00, 59); // hora indeterminada (varias)
                                }
                                else {
                                    try {
                                        horaDato = LocalTime.parse(sHora);
                                    }
                                    catch(DateTimeParseException dtpe) {
                                        horaDato = LocalTime.of(00, 00, 49); // hora indeterminada (error en datos)
                                    }
                                    
                                }
                                
                            }
                            else {
                                String sDato = object.getString(codigo);
                                try {
                                    NumberFormat formato = NumberFormat.getInstance(Locale.FRANCE);
                                    Number num = formato.parse(sDato);
                                    dato = num.doubleValue();
                                }
                                catch (ParseException pe) {
                                }
                            }
                        }
                        catch (JSONException jsone) {
                                // ignorar el dato no presente o sin formato
                        }
                        switch(codigo) {
                            case "prec":
                                obs.setPrec(dato);
                                break;
                            case "tmax":
                                obs.settMax(dato);
                                break;
                            case "tmin":
                                obs.settMin(dato);
                                break;
                            case "tmed":
                                obs.settMed(dato);
                                break;
                            case "dir":
                                obs.setDir(dato);
                                break;
                            case "velmedia":
                                obs.setVelMedia(dato);
                                break;
                            case "racha":
                                obs.setRacha(dato);
                                break;
                            case "sol":
                                obs.setSol(dato);
                                break;
                            case "presmax":
                                obs.setPresMax(dato);
                                break;
                            case "presmin":
                                obs.setPresMin(dato);
                                break;
                            case "horapresmax": // hh:00:00
                                obs.setHoraPresMax(horaDato);
                                break;
                            case "horapresmin": // hh:00:00
                                obs.setHoraPresMin(horaDato);
                                break;
                            case "horatmax": // hh:mm:00
                                obs.setHoraTMax(horaDato);
                                break;
                            case "horatmin": // hh:mm:00
                                obs.setHoraTMin(horaDato);
                                break;
                            case "horaracha": // hh:mm:00 
                                obs.setHoraRacha(horaDato);
                                break;
                        }
                        
                    }
                    listadoObservacion.add(obs);
                }
            } catch (IOException ioe) {
                System.out.println("Excepción en la conexión con AEMET OpenData para solicitar respuesta listado de estaciones meteo");
                System.out.println(ioe);
            }
        }
        return listadoObservacion;
    }

    
    public List<MetaDato> getMetaDatos() {
        List<MetaDato> listadoMeta = new ArrayList<>();
        OkHttpClient cliente = conAemet.getCliente();
        String urlRequest
                = "https://opendata.aemet.es/opendata/api/valores/climatologicos/diarios/datos/fechaini/2018-01-01T00%3A00%3A00UTC/fechafin/2018-01-02T00%3A00%3A00UTC/todasestaciones/";
        Request request = new Request.Builder()
                .url(urlRequest + "/?api_key=" + API_KEY)
                .build();
        String urlDatosRespuesta = null;
        try (Response response = cliente.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Excepción en conexión con AEMET: Código no esperado " + response);
            }
            String jsonDataStr = response.body().string();
            JSONObject jsonObject = new JSONObject(jsonDataStr);
            urlDatosRespuesta = (String) jsonObject.get("metadatos");
        } catch (IOException ioe) {
            System.out.println("Excepción en la conexión con AEMET OpenData para solicitar listado de estaciones meteo");
            System.out.println(ioe);
        }
        if (urlDatosRespuesta != null) {
            request = new Request.Builder().url(urlDatosRespuesta).build();
            try (Response response = cliente.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new IOException("Excepción en la conexión con AEMET OpenData: Código no esperado " + response);
                }
                String jsonDataStr = response.body().string();
                JSONObject jsonObj = new JSONObject(jsonDataStr);
                JSONArray jsonArray = jsonObj.getJSONArray("campos");

                // convertir en POJOs
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    String id = (String) object.get("id");
                    String descripcion = (String) object.get("descripcion");
                    String unidad;
                    try {
                        unidad = (String) object.get("unidad");
                    }
                    catch(JSONException jsone) {
                        unidad = null;
                    }
                    listadoMeta.add(new MetaDato(id, descripcion, unidad));                    
                }
            } catch (IOException ioe) {
                System.out.println("Excepción en la conexión con AEMET OpenData para solicitar respuesta listado de estaciones meteo");
                System.out.println(ioe);
            }
        }
        return listadoMeta;
    }
    
    private static double coordenadaDecimal(String coordenadaDMS) {
        char dir = coordenadaDMS.charAt(coordenadaDMS.length()-1);
        coordenadaDMS = coordenadaDMS.substring(0, coordenadaDMS.length()-1);
        double grados = Double.parseDouble(coordenadaDMS.substring(0, 2));
        double min = Double.parseDouble(coordenadaDMS.substring(2, 4));
        double sec = Double.parseDouble(coordenadaDMS.substring(4, 6));
        
        double dec = grados + min/60 + sec/3600;
        if (dir == 'S' || dir == 'W') {
            dec = dec * -1;
        }
        return dec;
    }
    
    public List<Estacion> getEstaciones() {
        List<Estacion> listadoEstaciones = new ArrayList<>();
        OkHttpClient cliente = conAemet.getCliente();
        String urlRequest
                = "https://opendata.aemet.es/opendata/api/valores/climatologicos/inventarioestaciones/todasestaciones";
        Request request = new Request.Builder()
                .url(urlRequest + "/?api_key=" + API_KEY)
                .build();
        String urlDatosRespuesta = null;
        try (Response response = cliente.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Excepción en conexión con AEMET: Código no esperado " + response);
            }
            String jsonDataStr = response.body().string();
            JSONObject jsonObject = new JSONObject(jsonDataStr);
            urlDatosRespuesta = (String) jsonObject.get("datos");
        } catch (IOException ioe) {
            System.out.println("Excepción en la conexion con AEMET OpenData para solicitar listado de estaciones meteo");
            System.out.println(ioe);
        }
        if (urlDatosRespuesta != null) {
            request = new Request.Builder().url(urlDatosRespuesta).build();
            try (Response response = cliente.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }
                String jsonDataStr = response.body().string();
                JSONArray jsonArray = new JSONArray(jsonDataStr);

                TreeMap<String, Integer> idProvincias = provinciaDAO.getIdProvincias();
                // convertir en POJOs
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    String id = (String) object.get("indicativo");
                    String latitud = (String) object.get("latitud");
                    double lat = coordenadaDecimal(latitud);
                    String longitud = (String) object.get("longitud");
                    double lon = coordenadaDecimal(longitud);
                    double altitud = object.getDouble("altitud");
                    String provincia = (String) object.get("provincia");
                    String nombre = (String) object.get("nombre");
                    String idsinop = (String) object.get("indsinop");

                    // necesitamos inyectar provinciaDAO y recuperar lista prov (antes de for)
                    listadoEstaciones.add(new Estacion(lat, provincia, id, altitud, 
                            nombre, idsinop, lon, idProvincias.get(provincia)));
                }
            } catch (IOException ioe) {
                System.out.println("Excepcion en la conexión con AEMET OpenData para solicitar respuesta listado de estaciones meteo");
                System.out.println(ioe);
            }
        }
        return listadoEstaciones;
    }
}

