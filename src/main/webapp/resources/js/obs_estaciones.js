var obsEstaciones = {};

obsEstaciones.estaciones = null;
obsEstaciones.mapa = null;
obsEstaciones.clusterMarcadores = null;
obsEstaciones.marcadores = [];
obsEstaciones.infoWindow = null;

obsEstaciones.init = function() {
  var latLon = new google.maps.LatLng(36.14474, -5.35257);
  var options = {
    'zoom': 5,
    'center': latLon,
    'mapTypeId': 'terrain' //google.maps.MapTypeId.ROADMAP
  };

  obsEstaciones.mapa = new google.maps.Map(document.getElementById('mapa'), options);
  obsEstaciones.estaciones = datos;

  obsEstaciones.infoWindow = new google.maps.InfoWindow();

  obsEstaciones.putMarcadores();
};


obsEstaciones.putMarcadores = function() {    
    for (var i = 0; i < obsEstaciones.estaciones.length; i++) {
	var latLon = new google.maps.LatLng(obsEstaciones.estaciones[i].latitud,
					    obsEstaciones.estaciones[i].longitud);

	var urlImg = 'http://chart.apis.google.com/chart?cht=mm&chs=24x32&chco=' +
            'FFFFFF,008CFF,000000&ext=.png';
	var iconoMarcador = new google.maps.MarkerImage(urlImg, new google.maps.Size(24, 32));

	var marcador = new google.maps.Marker({
	    'position': latLon,
	    'icon': iconoMarcador
	});
	
	var fn = obsEstaciones.fClickMarcador(obsEstaciones.estaciones[i], latLon);
	google.maps.event.addListener(marcador, 'click', fn);

	obsEstaciones.marcadores.push(marcador);
    }
    obsEstaciones.clusterMarcadores = new MarkerClusterer(obsEstaciones.mapa, obsEstaciones.marcadores,
							  {imagePath: 'https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m'});
};


// interacción con estaciones
obsEstaciones.fClickMarcador = function(estacion, latlon) {
    return function(e) {
	e.cancelBubble = true;
	e.returnValue = false;
	if (e.stopPropagation) {
	    e.stopPropagation();
	    e.preventDefault();
	}

	var titulo = estacion.nombre + ', ' + estacion.provincia;
	var altitud = estacion.altitud;
	var fecha = estacion.fecha;
	var tmedia = estacion.tmed;
	var tmax = estacion.tmax;
	var tmin = estacion.tmin;
	var prec = estacion.prec;
	
	var infoHtml = '<div class="info"><h3>' + titulo +
	    '</h3></div><div>' +
	    '<table><tbody>' +
	    '<tr><td style="padding: 5px;">Altitud</td><td style="padding: 5px;">' + altitud + ' m</td></tr>' +
	    '<tr><td style="padding: 5px;">Fecha</td><td style="padding: 5px;">' + fecha + '</td></tr>' +
	    '<tr><td style="padding: 5px;">Temperatura media</td><td style="padding: 5px;">' + tmedia + ' &#186;C</td></tr>' +
	    '<tr><td style="padding: 5px;">Temperatura m&#225;xima</td><td style="padding: 5px;">' + tmax + ' &#186;C</td></tr>' +
	    '<tr><td style="padding: 5px;">Temperatura m&#237;nima</td><td style="padding: 5px;">' + tmin + ' &#186;C</td></tr>' +
	    '<tr><td style="padding: 5px;">Precipitaci&#243;n</td><td style="padding: 5px;">' + prec + ' mm</td></tr></tbody></table>' +
	    '</div>';
	
	obsEstaciones.infoWindow.setContent(infoHtml);
	obsEstaciones.infoWindow.setPosition(latlon);
	obsEstaciones.infoWindow.open(obsEstaciones.mapa);
    };
};

obsEstaciones.fSeleccionEstacion = function() {
    var idEstacion = document.getElementById('estacion').value;
    var datosEstaciones = datos;
    var lat;
    var lon;
    for (var i = 0; i < datosEstaciones.length; i++) {
        if (datosEstaciones[i].id === idEstacion) {
            lat = datosEstaciones[i].latitud;
            lon = datosEstaciones[i].longitud;
            break;
        }
    }
    obsEstaciones.mapa.setZoom(10);
    var latLon = new google.maps.LatLng(lat, lon);
    obsEstaciones.mapa.setCenter(latLon);
};