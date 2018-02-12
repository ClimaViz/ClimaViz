<%@page contentType="text/html" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>JSP Page</title>
    </head>
    <body>         
        ${requestScope.listaresultado.size()}
        <c:forEach items="${requestScope.listaresultado}" var="elem" >
            <p>${elem}</p>
        </c:forEach>
        <table border="1">
            <thead>
                <tr>
                    <td>Latitud</td>
                    <td>Longitud</td>
                    <td>Resultado</td>
                </tr>
            </thead>
            <c:forEach items="${requestScope.listaresultado}" var="rr" >
                <tr>
                    <td>${rr.latitud}</td>
                    <td>${rr.longitud}</td>
                    <td>${rr.resultado}</td>
                </tr>
            </c:forEach>
        </table>          
    </body>
</html>
