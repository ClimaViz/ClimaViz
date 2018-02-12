function vaciarEstaciones() {
    var estacion = document.getElementById("estacion");
    while (estacion.length > 0) {
        estacion.remove(0);
    }

}
function estacionesRecibidas(jxHQR, status) {
    var estacion = document.getElementById("estacion");
    var o = JSON.parse(jxHQR.responseText);
    var opcion;
    vaciarEstaciones();
    var i;
    for (i in o) {
        opcion = document.createElement("option");
        opcion.value = o[i].id;
        opcion.text = o[i].nombre;
        estacion.add(opcion);
    }
    estacion.enabled = true;
}

function provinciaCambiada() {
    var estacion = document.getElementById("estacion");
    var provincia = document.getElementById("provincia");
    var valorProvincia = provincia.value;
    var opcion;
    vaciarEstaciones();
    opcion = document.createElement("option");
    opcion.text = "Cargando estaciones...";
    estacion.add(opcion);
    estacion.enabled = false;
    jQuery.ajax("getEstaciones?provincia=" + valorProvincia, {complete: estacionesRecibidas});

}


