<%@page contentType="text/html" pageEncoding="utf-8"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>JSP Page</title>
    </head>
    <body>
        <table border="1">
            <thead>
                <tr>
                    <td>Fecha</td>
                    <td>Id</td>
                    <td>Temperatura Media</td>
                    <td>Precipitacion</td>
                    <td>Temperatura Minima</td>
                    <td>Hora Temperatura Minima</td>
                    <td>Temperatura Maxima</td>
                    <td>Hora Temperatura Maxima</td>
                    <td>Direccion</td>
                    <td>Velocidad Media</td>
                    <td>Racha</td>
                    <td>Hora Racha</td>
                    <td>Insolacion</td>
                    <td>Presion Maxima</td>
                    <td>Hora Presion Maxima</td>
                    <td>Presion Minima</td>
                    <td>Hora Presion Minima</td>
                </tr>
            </thead>
            
                <tr>
                    <td>${requestScope.observacion.fecha}</td>
                    <td>${requestScope.observacion.id}</td>
                    <td>${requestScope.observacion.tMed}</td>
                    <td>${requestScope.observacion.prec}</td>
                    <td>${requestScope.observacion.tMin}</td>
                    <td>${requestScope.observacion.horaTMin}</td>
                    <td>${requestScope.observacion.tMax}</td>
                    <td>${requestScope.observacion.horaTMax}</td>
                    <td>${requestScope.observacion.dir}</td>
                    <td>${requestScope.observacion.velMedia}</td>
                    <td>${requestScope.observacion.racha}</td>
                    <td>${requestScope.observacion.horaRacha}</td>
                    <td>${requestScope.observacion.sol}</td>
                    <td>${requestScope.observacion.presMax}</td>
                    <td>${requestScope.observacion.horaPresMax}</td>
                    <td>${requestScope.observacion.presMin}</td>
                    <td>${requestScope.observacion.horaPresMin}</td>
                </tr>
            
        </table>
    </body>
</html>
