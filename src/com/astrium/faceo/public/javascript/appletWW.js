 /** javascript :  appletWW.js
 *-----------------------------------------------
 * Auteur 	:  
 *-----------------------------------------------
 * Creation : 08/2008
 *-----------------------------------------------
 * Prerequis :
 *-----------------------------------------------
 */

var ID_CARTO_APPLET = "cartoApplet";

var cartoApplet = null;

function getCartoApplet() {
	if (cartoApplet == null) {
		cartoApplet = document.getElementById(ID_CARTO_APPLET);
	}
	// See if we're using the old Java Plug-In and the JNLPAppletLauncher
	try {
		cartoApplet = cartoApplet.getSubApplet();
	} catch (error) {
		// Using new-style applet -- ignore
		// the methods of the applet are directly call without 'getSubApplet()'
	}
    return cartoApplet;
} // function getCartoApplet() {

// -------------------- Cartography Objects --------------------
/**
 * set the position of the globe to the initial position.
 * 
 */
function initCartographyPosition() {
	try {
		getCartoApplet().initCartographyPosition();
	} catch (error) {
		alert("initCartographyPosition : " + error);
	} finally {
	}
} // function initCartographyPosition()

// -------------------- Objects -------------------- 
/**
* ajout d'un rectangle dans l'applet World Wind de repr&eacute;sentation 3D de la Terre
* 
* @param _layerName (String)	: nom du layer sur lequel on ajoute le rectangle
* @param _editable (boolean)	: vaut true si le rectangle est &eacute;ditable
* @param _lowerLat (double)		: latitude du point inf&egrave;rieur du rectangle
* @param _lowerLon (double)		: longitude du point inf&egrave;rieur du rectangle
* @param _upperLat (double)		: latitude du point sup&egrave;rieur du rectangle
* @param _upperLon (double)		: longitude du point sup&egrave;rieur du rectangle
* @param _label (String)		: label du rectangle
* @param _hue (float)			: the hue HSB component object color
* @param _saturation (float)	: the saturation HSB component object color
* @param _brightness (float)	: the brightness HSB component object color
* @param _transparency (double)	: valeur de la transparence du Layer : 0 transparent  1 : opaque
* @param _pick (boolean)		: the pick status of the layer
* 
* @return  int	: identifiant du rectangle
*/	
function drawWWJRectangle(_layerName, _editable, _lowerLat, _lowerLon, _upperLat, _upperLon, _label, _hue, _saturation, _brightness, _transparency, _pick) {
	var idRectangle = -1;
	
	try {
		idRectangle = getCartoApplet().drawRectangle(_layerName, _editable,_lowerLat, _lowerLon, _upperLat, _upperLon, _label, _hue, _saturation, _brightness, _transparency, _pick);
		getCartoApplet().movetoViewPosition(((_lowerLat+_upperLat)/2), ((_lowerLon+_upperLon)/2), 1000000, 0, 0);
	} catch (error) {
		alert("drawWWJRectangle : " + error);
	} finally {
	}
	
	return idRectangle;
} // function drawWWJRectangle(_layerName, _editable, _lowerLat, _lowerLon, _upperLat, _upperLon, _label, _hue, _saturation, _brightness, _transparency) {

/**
* ajout d'un rectangle dans l'applet World Wind 
* en d&eacute;finissant les deux points du rectangle
* par deux clics souris
* le rectangle cr&eacute;&eacute; est d&eacute;pla&ccedil;able
* et modifiable
*
* @param _layerName (String)	: nom du layer sur lequel on ajoute le rectangle
* @param _label (String)		: longitude du point sup&egrave;rieur du rectangle
* @param _hue (float)			: hue color HSB
* @param _saturation (float)	: saturation color HSB
* @param _brightness (float)	: brightness color HSB
* @param _transparency (double)	: valeur de la transparence du Layer : 0 transparent  1 : opaque
* @param _type (int)			: the type of the rectangle
* @param _pick (boolean)		: the pick status of the layer
*
*/	
function drawCartoRectWithMouse(_layerName, _label, _hue, _saturation, _brightness, _transparency, _type, _pick) {
	var idRectangle = -1;
	
	try {
		idRectangle = getCartoApplet().createRectangleWithMouse(_layerName, _label, _hue, _saturation, _brightness, _transparency, _type, _pick);
	} catch (error) {
		alert("drawCartoRectWithMouse : " + error);
	} finally {
	}
	
	return idRectangle;
} // function drawCartoRectWithMouse(_layerName, _label, _hue, _saturation, _brightness, _transparency, _type, _pick) {

/**
* ajout d'un objet GML de type 'polygone' ou 'multipolygone' sur la repr&eacute;sentation 3D de la Terre
* 
* @param _layerName (String)	: nom du layer sur lequel on ajoute le rectangle
* @param _xmlGML (String)		: &eacute;l&eacute;ment xml au format GML repr&eacute;sentant l'emprise du produit
* @param _xmlCenterOfGML (String): element xml au format GML repr&eacute;sentant le centre de l'emprise du produit
* @param _label (String)		: label du rectangle
* @param _hue (float)			: the hue HSB component object color
* @param _saturation (float)	: the saturation HSB component object color
* @param _brightness (float)	: the brightness HSB component object color
* @param _transparency (double)	: valeur de la transparence du Layer : 0 transparent  1 : opaque
* @param _area (boolean)		: true si l'aire est affiché dasn le label
* @param _perimeter (boolean)	: true si le perimetre est affiché dasn le label
* @param _pick (boolean)		: the pick status of the layer
* 
* @return  int					: identifiant du polygone
*/	
function drawWWJGML(_layerName, _xmlGML, _xmlCenterOfGML, _label, _hue, _saturation, _brightness, _transparency, _area, _perimeter, _pick) {
	var idGML = -1;
	try {
		idGML = getCartoApplet().drawGML(_layerName, _xmlGML, _xmlCenterOfGML, _label, _hue, _saturation, _brightness, _transparency, _area, _perimeter, _pick);
	} catch (error) {
		alert("drawWWJGML : " + error);
	} finally {
	}
	return idGML;
} // function drawWWJGML(_layerName, _xmlGML, _xmlCenterOfGML, _label, _hue, _saturation, _brightness, _transparency, _area, _perimeter, _pick) {

/**
 * Go to a point defined by latitude and longitude
 * 
 * @param _latitude (String)	: latitude du lieu
 * @param _longitude (String)	: longitude du lieu
 * @param _altitude (String)	: altitude du positionnement
 */
function gotoLatLon(_latitude, _longitude, _altitude) {
	try {
		getCartoApplet().movetoViewPosition(_latitude, _longitude, _altitude, 0, 0);
	} catch (error) {
		alert("gotoLatLon : " + error);
	} finally {
	}
} // function gotoLatLon(_latitude, _longitude, _altitude) {

/**
 * Move the current view position, zoom, heading and pitch
 * 
 * @param _latitude (double)	: the target latitude in decimal degrees
 * @param _longitude (double)	: the target longitude in decimal degrees
 * @param _longitude (double)	: the target eye distance in meters
 * @param _heading (double)	: the target heading in decimal degrees
 * @param _pitch (double)		: the target pitch in decimal degrees
 */
function movetoViewPosition(_latitude, _longitude, _altitude, _heading, _pitch) {
	try {
		getCartoApplet().movetoViewPosition(_latitude, _longitude, _altitude, 0, 0);
	} catch (error) {
		alert("movetoViewPosition : " + error);
	} finally {
	}
} // function movetoViewPosition(_latitude, _longitude, _altitude, _heading, _pitch) {

/**
* fonction permettant de supprimer un objet
* de l'applet World Wind 
*
*	@param {int} _idObject		: identifiant de l'objet &agrave; supprimer
* 	@param (String) _idLayer	: identifiant du layer contenant l'objet &agrave; supprimer
*/	
function delCarto3DObject(_idObject, _idLayer) {
	// var message ="";
	// message += "_idObject : " + _idObject + "\n";
	// message += "_idLayer : " + _idLayer + "\n";
	// alert(message);
	try {
		getCartoApplet().delCartoObject(_idObject, _idLayer);
	} catch (error) {
		alert("delCartoObject : " + error);
	} finally {
	}

} // function delCarto3DObject(_idObject,_idLayer) {

// -------------------- Layers -------------------- 
/**
 * M&eacute;thode retournant les propri&eacute;t&eacute;s del'ensemble des layers de la carto
 * 
 * @return the string use JSON format {"layerList": [{"name": "�toiles",
 *         "enabled": "true", "opacity": "1.0", "minActiveAltitude": "200",
 *         "maxActiveAltitude": "600, "wms": true}, {"name": "Atmos ... ]}
 */
function getLayersList() {
	var jsonList = "";
	try {
		jsonList = getCartoApplet().getLayersList();
	} catch (error) {
		alert("getLayersList : " + error);
	} finally {
	}
	
	return jsonList;
} // function getLayersList() {

/**
 * Set the layer configuration list
 * 
 * @param _layerList (String)	: la chaine au format JSON &agrave; parser
 * {"layerList": [{"name": "Eoiles",
 *         "enabled": "true", "opacity": "1.0", "minActiveAltitude": "200",
 *         "maxActiveAltitude": "600, "wms": true}, {"name": "Atmos ... ]}
 *
*/
 function setLayersList(_layerList) {
	try {
		jsonList = getCartoApplet().setLayersList(_layerList);
	} catch (error) {
		alert("setLayersList : " + error);
	} finally {
	}
} // function setLayersList() {

/**
 * M&eacute;thode retournant les propri&eacute;t&eacute;s d'un layer de la carto
 * 
 * @param _layerName (String)		: nom du layer sur lequel on ajoute l'orbite
 *
 * @return the string use JSON format {"layerList": [{"name": "�toiles",
 *         "enabled": "true", "opacity": "1.0", "minActiveAltitude": "200",
 *         "maxActiveAltitude": "600, "wms": true}]}
 */
function getLayerProperties(_layerName) {
	var jsonList = "";
	try {
		jsonList = getCartoApplet().getLayerProperties(_layerName);
	} catch (error) {
		alert("getLayerProperties : " + error);
	} finally {
	}
	
	return jsonList;
} // function getLayerProperties(_layerName) {

/**
 * Down layer in the layer list
 * 
 * @param _layerName (String)		: nom du layer sur lequel on ajoute l'orbite
 */
function downLayer(_layerName) {
	// la liste des layers va du layer d'arriere plan au layer de premier plan
	// wwd.getModel().getLayers().get(0) : layer d'arriere plan
	// wwd.getModel().getLayers().get(wwd.getModel().getLayers().size()) : layer de 1er plan
	try {
		getCartoApplet().downLayer(_layerName);
	} catch (error) {
		alert("downLayer : " + error + " : layerName : " + _layerName);
	} finally {
	}
} // function downLayer(_layerName) {

/**
 * Up layer in the layer list
 * 
 * @param _layerName (String)		: nom du layer sur lequel on ajoute l'orbite
 */
function upLayer(_layerName) {
	// la liste des layers va du layer d'arriere plan au layer de premier plan
	// wwd.getModel().getLayers().get(0) : layer d'arriere plan
	// wwd.getModel().getLayers().get(wwd.getModel().getLayers().size()) : layer de 1er plan
	try {
		getCartoApplet().upLayer(_layerName);
	} catch (error) {
		alert("upLayer : " + error);
	} finally {
	}
} // function upLayer(_layerName) {

/**
 * Show/Hide layer
 * 
 * @param _layerName	(String): the layer name
 * @param _enabled (boolean)	: true to display layer
 *            					  false to hide layer
 */
function showHideLayer(_layerName, _enabled) {
	try {
		getCartoApplet().showHideLayer(_layerName, _enabled);
	} catch (error) {
		alert("showHideLayer : " + error);
	} finally {
	}
} // function showHideLayer(_layerName, _enabled) {

/**
 * Modify layer opacity
 * 
 * @param _layerName (String)	: the layer name
 * @param _opacity (double)	: value of the opacity attribute
 */
function modifyLayerOpacity(_layerName, _opacity) {
	try {
		getCartoApplet().modifyLayerOpacity(_layerName, _opacity);
	} catch (error) {
		alert("modifyLayerOpacity : " + error);
	} finally {
	}
} // function modifyLayerOpacity(_layerName, _opacity) {

/**
* fonction permettant de supprimer un Layer
* de l'applet World Wind 
*
*	@param {String} _idLayer	: identifiant du Layer &agrave; supprimer
*/	
function delCartoLayer(_idLayer) {
	try {
		getCartoApplet().delCartoLayer(_idLayer);
	} catch (error) {
		alert("delCartoLayer : " + error);
	} finally {
	}

} // function delCartoLayer(_idLayer) {

/**
* fonction permettant de supprimer un layer et tous les objets d'un Layer
* de l'applet World Wind 
*
*	@param {String} _idLayer	: identifiant du Layer &agrave; supprimer
*/	
function delCartoLayerObjects(_idLayer) {
	try {
		getCartoApplet().delCartoLayerObjects(_idLayer);
	} catch (error) {
		alert("delCartoLayerObjects : " + error);
	} finally {
	}

} // function delCartoLayerObjects(_idLayer) {

// -------------------- Primitives -------------------- 

/**
 * select an objet on cartography
 * 
 * @param _id (int) 	: the object identifier
 */
function selectFaceoObject(_id) {

	try {
		idLabel = getCartoApplet().selectFaceoObject(_id);
	} catch (error) {
		alert("selectFaceoObject : " + error);
	} finally {
	}
		
} // function selectFaceoObject(_id) {

/**
 * return WW applet's version and World Wind's version
 * 
 * @return String : return the versions of WW applet's and World Wind
 * 
 */
function getVersionsAppletWWD() {
	var versionsAppletWWD = "";
	try {
		 versionsAppletWWD = getCartoApplet().getVersionsAppletWWD();
	} catch (error) {
		alert("getVersionsAppletWWD: " + error);
	} finally {
	}
	return versionsAppletWWD;
} // function getVersionsAppletWWD() { 
	
/**
 * Show/Hide Sun
 * 
 * @param _enabled (boolean)	: true to display sun
 *            					  false to hide sun
 */
function showHideSun( _enabled) {
	try {
		getCartoApplet().showHideSun( _enabled);
	} catch (error) {
		alert("showHideSun : " + error);
	} finally {
	}
} // function showHideSun( _enabled) {	
