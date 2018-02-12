<%@page contentType="text/html" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>ClimaViz</title>
        <link rel="stylesheet" href="<c:url value="/resources/css/estilo.css" />" type="text/css">
        <!-- selector de estaciones -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script type="text/javascript" src="<c:url value="/resources/js/selectorestacion.js" />"></script>
        <!-- google maps + datos -->
        <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDX0glbz4AEylpHF0e5eq16NDkB2h5gAtg"></script>
        <script>
            <c:set var="elems" value="${requestScope.listado.size()}" />
            var datos = [
            <c:forEach items="${requestScope.listado}" var="estacion">
                <c:set var="elems" value="${elems - 1}" />
            {"id": "${estacion.id}", "nombre": "${estacion.nombre}", "provincia": "${estacion.provincia}", "fecha": "${estacion.resumen.fecha}", "latitud": "${estacion.latitud}",
                    "longitud": "${estacion.longitud}", "altitud": "${estacion.altitud}", "tmed" : "${estacion.resumen.tMed}", "prec" : "${estacion.resumen.prec}", "tmin" : "${estacion.resumen.tMin}", "tmax" : "${estacion.resumen.tMax}"}
                <c:if test="${elems != 0}">,</c:if>
            </c:forEach>
            ];
        </script>
        <script type="text/javascript" src="<c:url value="/resources/js/markerclusterer.js" />"></script>
        <script type="text/javascript" src="<c:url value="/resources/js/obs_estaciones.js" />"></script>
        <script>google.maps.event.addDomListener(window, 'load', obsEstaciones.init);</script>
    </head>
    <body style="background-image: url('resources/image/skyline.png'); background-size: 1400px 1000px">
        <div id="panel">
            <div>
                <h1>ClimaViz</h1>
            </div>
            <div>
                <h2>Fecha</h2>
                <form action="Listar" method="POST">
                    <input id="fechaobs" type="date" name="fecha" value="${requestScope.fechaComp}" onblur="document.selestacion.fechacons.value = this.value; document.analisis.fechaanalisis.value = this.value;"></input>
                    <input type="submit" value="Mostrar"></input>
                </form>
                
            </div>
            <div>
                <h2>Estación</h2>
                <form name="selestacion" action="GetObservacionFecha" method="POST">
                    <input id="fechaconsulta" type="date" name="fechacons" value="${requestScope.fechaComp}" hidden="true"></input>
                    <select id="provincia" name="provincia" onchange="provinciaCambiada();">
                    <c:forEach items="${requestScope.provincias}" var="p">
                        <option value="${p.id}">${p.nombre}</option>
                    </c:forEach>
                    </select>
                    <p>
                        <select id="estacion" name="estacion" onchange="obsEstaciones.fSeleccionEstacion();"></select>
                    </p>
                    <p>
                        <input type="submit" value="Ver todos los datos"></input>
                    </p>
                </form>
            </div>
            <div>
                <h2>Análisis</h2>
                <form name="analisis" action="Analisis" method="POST">
                <input id="fechaanalisis" type="date" name="fechaanalisis" value="${requestScope.fechaComp}" hidden="true"></input>
                <select id="tipoanalisis" name="tipoanalisis">
                    <option value="temp">Temperatura media</option>
                    <option value="tmax">Temperatura máxima</option>
                    <option value="tmax">Temperatura mínima</option>
                    <option value="prec">Precipitación</option>
                </select>
                <p>
                    <select id="tiempo" name="tiempo">
                        <option value="diario">Diario</option>
                        <option value="mensual">Mensual</option>
                    </select>
                </p>
                <p>
                    <input type="submit" value="Análisis (Beta version)"></input>
                </p>
                </form>
            </div>
        </div>
        <div id="contenedor-mapa">
            <div id="mapa"></div>
        </div>
    </body>
</html>
