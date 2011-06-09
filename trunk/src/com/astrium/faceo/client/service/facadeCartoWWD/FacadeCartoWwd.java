package com.astrium.faceo.client.service.facadeCartoWWD;

/*
 * @(#)FacadeCartoWwd.java	 1.0  11/09//2008
 *
 *
 * PROJET       : FACEO
 *
 * LANGUAGE     : Java
 *
 * DESCRIPTION  : La classe FacadeCartoWwd offre des m&eacute;thodes
 * permettant de g&eacute;rer les objets de la carto 3D
 *
 *
 * CREATION :
 * ---------------------------------------------------------------------
 * | Date       | Version | Description                                |
 * ---------------------------------------------------------------------
 * | 11/09/2008 |    1.0  |                                            |
 * ---------------------------------------------------------------------
 *
 * MODIFICATION :
 * ---------------------------------------------------------------------
 * | Date       | Version | Description                                |
 * ---------------------------------------------------------------------
 * |            |         |                                            |
 * ---------------------------------------------------------------------
 *
 */

// import 

// java libraries

// FACEO libraries
import com.astrium.faceo.client.bean.cartography.CartoObjectBean;
import com.astrium.faceo.client.common.GeneralConstant;
import com.astrium.faceo.client.common.faceo.FaceoConstants;
import com.astrium.faceo.client.ui.HomePage;
import com.astrium.faceo.client.ui.cartography.cartoLayers.CartoLayersPanel;
import com.astrium.faceo.client.ui.programming.sps2.view.SpsPanels;
import com.google.gwt.user.client.Window;
import com.google.gwt.xml.client.DOMException;

/**
 * <B>SITE FACEO</B> <BR>
 * 
 * <P>
 * La classe FacadeCartoWwd offre des m&eacute;thodes
 * permettant de g&eacute;rer les objets de la carto 3D
 * </P>
 * </P>
 * 
 * @author GR 11/09/2008
 */
public class FacadeCartoWwd {

	/**
	 * constructor
	 */
	public FacadeCartoWwd() {
	}

	// ---------------------- Cartography Objects ----------------------
	/**
	 * M&eacute;thode permettant la suppression d'un objet sur la cartographie
	 * 
	 * @param _idcartoObject (int) 	: identifiant de l'objet &agrave; supprimer dans la cartographie
	 */
	public static void delCarto3DObject(int _idcartoObject) {
		try {
			if (_idcartoObject > 0) {
				CartoObjectBean cartoObjectBean = 
					HomePage.getHomePageBean().getCartographyObjects().get(_idcartoObject);

				if (cartoObjectBean != null)  
					JSdelCarto3DObject(cartoObjectBean.getCartoId(), cartoObjectBean.getCartoLayerName());
			} // if (_idcartoObject > 0) {
		} catch (Exception exception) {
			Window.alert("Exception : " + exception);
		} finally {
		}
	} // public void delWWJObject(int _idcartoObject) {

	/**
	 * A Java method using JSNI (JavaScript Native Interface)
	 * $wnd is a JSNI synonym for 'window'
	 * appel de la fonction JavaScript 'delCarto3DObject'
	 * d&eacute;finie dans le fichier JavaScript 'appletWW.js'
	 * This function delete an object
	 * 
	 * @param _idcartoObject (int)	: identifiant de l'objet &agrave; supprimer dans la cartographie
	 * @param _idLayer (String)		: identifiant du layer contenant l'objet &agrave; supprimer
	 */
	public static native void JSdelCarto3DObject(int _idcartoObject, String _idLayer)/*-{
	  	$wnd.delCarto3DObject(_idcartoObject, _idLayer); 
	}-*/;

	/**
	 * A Java method using JSNI (JavaScript Native Interface)
	 * $wnd is a JSNI synonym for 'window'
	 * appel de la fonction JavaScript 'drawCartoRectWithMouse'
	 * d&eacute;finie dans le fichier JavaScript 'appletWW.js'
	 * 
	 * M&eacute;thode permettant l'ajout d'un objet de type 'rectangle' sur la cartographie
	 * 
	 * @param _layerName (String)	: nom du layer sur lequel on ajoute le rectangle
	 * @param _label (String)		: longitude du point sup&egrave;rieur du rectangle
	 * @param _hue (float)			: Hue color HSB
	 * @param _saturation (float)	: Saturation color HSB
	 * @param _brightness (float)	: Brightness color HSB
	 * @param _transparency (double): valeur de la transparence du Layer
	 * @param _type (int)			: type du rectangle
	 * @param _pick (boolean)		: the pick status of the layer
	 *
	 * @return int					: identifiant de l'objet cr&eacute;&eacute;
	 */
	public static native int JSdrawCartoRectWithMouseInJava(String _layerName, String _label, float _hue, float _saturation, float _brightness, double _transparency, int _type, boolean _pick)/*-{
	  	return $wnd.drawCartoRectWithMouse(_layerName, _label, _hue, _saturation, _brightness, _transparency, _type, _pick);
	}-*/;

	/**
	 * A Java method using JSNI (JavaScript Native Interface)
	 * $wnd is a JSNI synonym for 'window'
	 * appel de la fonction JavaScript 'drawWWJGML'
	 * d&eacute;finie dans le fichier JavaScript 'appletWW.js'
	 * 
	 * M&eacute;thode permettant l'ajout d'un objet GML de type 'polygone' ou 'multipolygone' sur la cartographie
	 * 
	 * @param _layerName (String)	: nom du layer sur lequel on ajoute le rectangle
	 * @param _xmlGML (String)		: &eacute;l&eacute;ment xml au format GML repr&eacute;sentant l'emprise du produit
	 * @param _xmlCenterOfGML (String): element xml au format GML repr&eacute;sentant le centre de l'emprise du produit
	 * @param _label (String)		: label du rectangle
	 * @param _hue (float)			: Hue color HSB
	 * @param _saturation (float)	: Saturation color HSB
	 * @param _brightness (float)	: Brightness color HSB
	 * @param _transparency (double): valeur de la transparence du Layer
	 * @param _area (boolean)		: true si l'aire est affiché dasn le label
	 * @param _perimeter (boolean)	: true si le perimetre est affiché dans le label
	 * @param _pick (boolean)		: the pick status of the layer
	 *
	 * @return int					: identifiant de l'objet cr&eacute;&eacute;
	 */
	public static native int JSdrawMultiExtentOfInJava(String _layerName, String _xmlGML, String _xmlCenterOfGML, String _label, 
			float _hue, float _saturation, float _brightness, double _transparency, boolean _area, boolean _perimeter, boolean _pick)/*-{
	  	return $wnd.drawWWJGML(_layerName, _xmlGML, _xmlCenterOfGML, _label, _hue, _saturation, _brightness, _transparency, _area, _perimeter, _pick);
	}-*/;

	/**
	 * select an objet on cartography
	 * 
	 * @param _id (int) 	: the object identifier
	 */
	public static native void JSselectFaceoObject(int _id)/*-{
	  	return $wnd.selectFaceoObject(_id);
	}-*/;
	
	/**
	 * A Java method using JSNI (JavaScript Native Interface)
	 * $wnd is a JSNI synonym for 'window'
	 * appel de la fonction JavaScript 'drawWWJRectangle'
	 * d&eacute;finie dans le fichier JavaScript 'appletWW.js'
	 * 
	 * M&eacute;thode permettant l'ajout d'un objet de type 'rectangle' sur la cartographie
	 * 
	 * @param _layerName (String)	: nom du layer sur lequel on ajoute le rectangle
	 * @param _editable (boolean)	: vaut true si le rectangle est &eacute;ditable
	 * @param _lowerLat (double)	: latitude du point inf&egrave;rieur du rectangle
	 * @param _lowerLon (double)	: longitude du point inf&egrave;rieur du rectangle
	 * @param _upperLat (double)	: latitude du point sup&egrave;rieur du rectangle
	 * @param _upperLon (double)	: longitude du point sup&egrave;rieur du rectangle
	 * @param _label (String)		: label du rectangle
	 * @param _hue (float)			: Hue color HSB
	 * @param _saturation (float)	: Saturation color HSB
	 * @param _brightness (float)	: Brightness color HSB
	 * @param _transparency (double): valeur de la transparence du Layer
	 * @param _pick (boolean)		: the pick status of the layer
	 *
	 * @return the new rect identifier
	 */
	public static native int JSdrawTheRectInJava(String _layerName, boolean _editable, double _lowerLat, double _lowerLon, double _upperLat, double _upperLon,
			String _label, float _hue, float _saturation, float _brightness, double _transparency, boolean _pick)/*-{
	  	return $wnd.drawWWJRectangle(_layerName, _editable, _lowerLat, _lowerLon, _upperLat, _upperLon, _label, _hue, _saturation, _brightness, _transparency, _pick); 
	}-*/;

	// ---------------------- Layers ----------------------
	/**
	 * A Java method using JSNI (JavaScript Native Interface)
	 * $wnd is a JSNI synonym for 'window'
	 * appel de la fonction JavaScript 'getLayersList'
	 * d&eacute;finie dans le fichier JavaScript 'appletWW.js'
	 * 
	 * M&eacute;thode retournant les propri&eacute;t&eacute;s 
	 * de l'ensemble des layers de la cartographie
	 * 
	 * @return the string use JSON format {"layerList": [{"name": "Etoiles",
	 *         "enabled": "true", "opacity": "1.0", "minActiveAltitude": "200",
	 *         "maxActiveAltitude": "600, "wms": "true",}, {"name": "Atmos ... ]}
	 */
	public static native String JSgetLayersList()/*-{
		return $wnd.getLayersList();
	}-*/;

	/**
	 * A Java method using JSNI (JavaScript Native Interface)
	 * $wnd is a JSNI synonym for 'window'
	 * appel de la fonction JavaScript 'setLayersList'
	 * d&eacute;finie dans le fichier JavaScript 'appletWW.js'
	 * 
	 * Set the layer configuration list
	 * 
	 * @param _layerList (String)	: la chaine au format JSON &agrave; parser
	 * {"layerList": [{"name": "Eoiles",
	 *         "enabled": "true", "opacity": "1.0", "minActiveAltitude": "200",
	 *         "maxActiveAltitude": "600, "wms": true}, {"name": "Atmos ... ]}
	 */
	public static native void JSsetLayersList(String _layerList)/*-{
		$wnd.setLayersList(_layerList);
	}-*/;

	/**
	 * A Java method using JSNI (JavaScript Native Interface)
	 * $wnd is a JSNI synonym for 'window'
	 * appel de la fonction JavaScript 'getLayerProperties'
	 * d&eacute;finie dans le fichier JavaScript 'appletWW.js'
	 * 
	 * M&eacute;thode retournant les propri&eacute;t&eacute;s d'un layer de la carto
	 * 
	 * @param _layerName (String)	: nom du layer sur lequel on ajoute le rectangle
	 *
	 * @return the string use JSON format {"layerList": [{"name": "ï¿½toiles",
	 *         "enabled": "true", "opacity": "1.0", "minActiveAltitude": "200",
	 *         "maxActiveAltitude": "600, "wms": "true",}, {"name": "Atmos ... ]}
	 */
	public static native String JSgetLayerProperties(String _layerName)/*-{
		return $wnd.getLayerProperties(_layerName);
	}-*/;

	/**
	 * A Java method using JSNI (JavaScript Native Interface)
	 * $wnd is a JSNI synonym for 'window'
	 * appel de la fonction JavaScript 'downLayer'
	 * d&eacute;finie dans le fichier JavaScript 'appletWW.js'
	 * 
	 * Mise en arri&eagrave;re plan d'un layer de la carto
	 *
	 * @param _layerName (String)	: nom du layer sur lequel on ajoute le rectangle
	 */
	public static native void JSdownLayer(String _layerName)/*-{
		$wnd.downLayer(_layerName);
	}-*/;

	/**
	 * A Java method using JSNI (JavaScript Native Interface)
	 * $wnd is a JSNI synonym for 'window'
	 * appel de la fonction JavaScript 'upLayer'
	 * d&eacute;finie dans le fichier JavaScript 'appletWW.js'
	 * 
	 * Mise en avant plan d'un layer de la carto
	 *
	 * @param _layerName (String)	: nom du layer sur lequel on ajoute le rectangle
	 */
	public static native void JSupLayer(String _layerName)/*-{
		$wnd.upLayer(_layerName);
	}-*/;

	/**
	 * A Java method using JSNI (JavaScript Native Interface)
	 * $wnd is a JSNI synonym for 'window'
	 * appel de la fonction JavaScript 'modifyLayerOpacity'
	 * d&eacute;finie dans le fichier JavaScript 'appletWW.js'
	 * 
	 * Modify layer opacity
	 * 
	 * @param _layerName (String)	: the layer name
	 * @param _opacity (double)		: value of the opacity attribute
	 */
	public static native void JSmodifyLayerOpacity(String _layerName, double _opacity)/*-{
		$wnd.modifyLayerOpacity(_layerName, _opacity);
	}-*/;

	/**
	 * A Java method using JSNI (JavaScript Native Interface)
	 * $wnd is a JSNI synonym for 'window'
	 * appel de la fonction JavaScript 'showHideLayer'
	 * d&eacute;finie dans le fichier JavaScript 'appletWW.js'
	 * 
	 * Affichage/d&eacute;saffichage d'un layer
	 * 
	 * @param _layerName (String)	: the layer name
	 * @param _enabled (boolean)	: true to display layer
	 *            					  false to hide layer
	 */
	public static native void JSshowHideLayer(String _layerName, boolean _enabled)/*-{
		$wnd.showHideLayer(_layerName, _enabled);
	}-*/;

	/**
	 * A Java method using JSNI (JavaScript Native Interface)
	 * $wnd is a JSNI synonym for 'window'
	 * appel de la fonction JavaScript 'delCartoLayer'
	 * d&eacute;finie dans le fichier JavaScript 'appletWW.js'
	 * 
	 * Suppression d'un layer
	 * 
	 * @param _layerName (String)	: the layer name
	 * 
	 */
	public static native void JSdelCartoLayer(String _layerName)/*-{
		$wnd.delCartoLayer(_layerName);
	}-*/;	

	/**
	 * A Java method using JSNI (JavaScript Native Interface)
	 * $wnd is a JSNI synonym for 'window'
	 * appel de la fonction JavaScript 'delCarto3DLayer'
	 * d&eacute;finie dans le fichier JavaScript 'appletWW.js'
	 * This function delete a Layer
	 * 
	 * @param _idlayer (String)	: identifiant du Layer &agrave; supprimer dans la cartographie
	 */
	public static native void JSdelCarto3DLayer(String _idlayer)/*-{
	  	$wnd.delCarto3DLayer(_idlayer); 
	}-*/;

	/**
	 * M&eacute;thode permettant la suppression d'un Layer sur la cartographie
	 * 
	 * @param _idcartoLayer (String) : nom du Layer &agrave; supprimer dans la cartographie
	 */
	public static void delCarto3DLayer(String _idcartoLayer) {
		try {
			if (_idcartoLayer != null) {
				JSdelCarto3DLayer(_idcartoLayer);
			} 
		} catch (Exception exception) {
			Window.alert("Exception : " + exception);
		} finally {
		}
	} // public void delCarto3DLayer(String _idcartoLayer) {

	/**
	 * M&eacute;thode permettant la suppression d'un Layer
	 * et de ses objets sur la cartographie
	 * 
	 * @param _idcartoLayer (String): nom du Layer &agrave; supprimer dans la cartographie
	 */
	public static void delCartoLayerObjects(String _idcartoLayer) {
		try {
			if (_idcartoLayer != null) {
				JSdelCarto3DLayerObjects(_idcartoLayer);
			} 
		} catch (Exception exception) {
			Window.alert("Exception : " + exception);
		} finally {
		}
	} // public void delCartoLayerObjects(String _idcartoLayer) {

	/**
	 * A Java method using JSNI (JavaScript Native Interface)
	 * $wnd is a JSNI synonym for 'window'
	 * appel de la fonction JavaScript 'delCartoLayerObjects'
	 * d&eacute;finie dans le fichier JavaScript 'appletWW.js'
	 * This function delete all the objects of a Layer
	 * 
	 * @param _idlayer (String)	: identifiant du Layer &agrave; supprimer dans la cartographie
	 */
	public static native void JSdelCarto3DLayerObjects(String _idlayer)/*-{
	  	$wnd.delCartoLayerObjects(_idlayer); 
	}-*/;

	// ---------------------- Position ----------------------
	/**
	 * set the position of the globe to the initial position.
	 * 
	 */
	public static native void JSinitCartographyPosition()/*-{
		return $wnd.initCartographyPosition();
	}-*/;

	/**
	 * A Java method using JSNI (JavaScript Native Interface)
	 * $wnd is a JSNI synonym for 'window'
	 * appel de la fonction JavaScript 'gotoLatLon'
	 * d&eacute;finie dans le fichier JavaScript 'appletWW.js'
	 * 
	 * on se positionne sur le point d&eacute;fini par sa latitude et sa longitude
	 *
	 * @param _latitude (double)	: latitude du lieu
	 * @param _longitude (double)	: longitude du lieu
	 * @param _altitude (double)	: altitude du positionnement
	 */
	public static native void JSgotoLatLon(double _latitude, double _longitude, double _altitude)/*-{
		$wnd.gotoLatLon(_latitude, _longitude, _altitude);
	}-*/;

	/**
	 * A Java method using JSNI (JavaScript Native Interface)
	 * $wnd is a JSNI synonym for 'window'
	 * appel de la fonction JavaScript 'JSmovetoViewPosition'
	 * d&eacute;finie dans le fichier JavaScript 'appletWW.js'
	 * 
	 * on se positionne sur le point de vue
	 *
	 * @param _latitude (double)	: latitude du lieu
	 * @param _longitude (double)	: longitude du lieu
	 * @param _altitude (double)	: altitude du positionnement
	 * @param _heading (double)		: the target heading in decimal degrees
	 * @param _pitch (double)		: the target pitch in decimal degrees
	 */
	public static native void JSmovetoViewPosition(double _latitude, double _longitude, double _altitude,
			double _heading, double _pitch)/*-{
		$wnd.movetoViewPosition(_latitude, _longitude, _altitude, _heading, _pitch);
	}-*/;

	/**
	 * A Java method using JSNI (JavaScript Native Interface)
	 * $wnd is a JSNI synonym for 'window'
	 * creation de la fonction JavaScript 'informAppliSelectionChange'
	 * effectu&eacute;e dans la m&eacute;thode 'onModuleLoad' du module cf. 'HomePage.java'
	 * qui est invoqu&eacute;e lors du changement de s&eacute;lection
	 * d'un objet de la cartographie
	 *
	 * param I (int): identifiant de l'objet pr&eacute;c&eacute;demment s&eacute;lectionn&eacute;
	 * param I (int): identifiant de l'objet s&eacute;lectionn&eacute;
	 */
	public static native void informAppliSelectionChange() /*-{
      $wnd.informAppliSelectionChange =
         @com.astrium.faceo.client.service.facadeCartoWWD.FacadeCartoWwd::alertSelectionChange(II);
   	}-*/;

	/**
	 * Gestion du changement de s&eacute;lection sur la cartographie
	 *
	 * @param _previousCartoIndex (int)	: identifiant de l'objet pr&eacute;c&eacute;demment s&eacute;lectionn&eacute;
	 * @param _currentCartoIndex (int)	: identifiant de l'objet s&eacute;lectionn&eacute;
	 */
	private static void alertSelectionChange(int _previousCartoIndex, int _currentCartoIndex)  {

		//		com.google.gwt.user.client.Window.alert(
		//				"Change : From : " + _previousCartoIndex + " To : " + _currentCartoIndex);

		if (HomePage.getHomePageBean().getCartographyObjects() != null) {
			CartoObjectBean cartoObjectBean = 
				HomePage.getHomePageBean().getCartographyObjects().get(_currentCartoIndex);

			if (cartoObjectBean != null) {
				HomePage.getHomePageBean().setIdCurrentCartoObject(_currentCartoIndex);

				int objectType = cartoObjectBean.getCartoObjectType();

				// selection de l'objet dans l'IHM
				switch (objectType) {
				case GeneralConstant.CARTO_CATALOG_AOI_TYPE: 			// objet de type AOI
					break;
				case GeneralConstant.CARTO_PROGRAMMING_OBJECT_TYPE: 	// objet de type Programming (Scenes)
					HomePage.getPanelCartoPanel().getCartoAppletPanel().selectProgrammingProductRow(_currentCartoIndex);

//					ProgrammingPanels.getPanelProgrammingSolutionsPanel().selectProductRow(_currentCartoIndex);
					break;
				default:;
				} // switch (objectType) {

			} // if (cartoObjectBean != null) {
		} // if (HomePage.getHomePageBean().getCartographyObjects() != null) {

	} // private static void alertSelectionChange(int _previousCartoIndex, int _currentCartoIndex)  {

	/**
	 * A Java method using JSNI (JavaScript Native Interface)
	 * $wnd is a JSNI synonym for 'window'
	 * creation de la fonction JavaScript 'updateAOIFields'
	 * effectu&eacute;e dans la meacute;thode 'onModuleLoad' du module cf. 'HomePage.java'
	 * qui est invoqu&eacute;e lors du changement de la zone d'int&eacute;r&ecirc;t (AOI)
	 * sur la cartographie
	 *
	 *	param _id (int)			: identifiant de l'objet repr&eacute;sentant la zone d'int&eacute;r&ecirc;t (AOI)
	 *	param _lowerLat (double) 	: latitude du 1er point
	 *	param _lowerLon (double)	: longitude du 1er point
	 *	param _upperLat (double)	: latitude du 2eme point
	 *	param _upperLon (double)	: longitude du 2eme point
	 */
	public static native void updateAOIFields() /*-{
      $wnd.upDateLatLonAOIRect =
         @com.astrium.faceo.client.service.facadeCartoWWD.FacadeCartoWwd::updateAOIFields(IDDDD);
   	}-*/;

	/**
	 * MAJ des latitudes et des longitudes des deux points d&eacute;finissant
	 * la zone d'int&eacute;r&eacirc;t (AOI)
	 *
	 *	@param _id (int)			: identifiant de l'objet repr&eacute;sentant la zone d'int&eacute;r&ecirc;t (AOI)
	 *	@param _lowerLat (double)	: latitude du 1er point
	 *	@param _lowerLon (double)	: longitude du 1er point
	 *	@param _upperLat (double) 	: latitude du 2eme point
	 *	@param _upperLon (double)	: longitude du 2eme point
	 */
	public static void updateAOIFields(
			int _id, double _lowerLat, double _lowerLon, double _upperLat, double _upperLon)  {

		//update of Programming SPS 2.0 AOI
		try {
			if (HomePage.getHomePageBean().getConfigBean().getWestPanel()) 
				if (HomePage.getHomePageBean().getConfigBean().getSps2Panel()) {
					if (HomePage.getWestPanel().getMissionRequestPanel(GeneralConstant.SPS_PANEL_ID) != null) {
//						SpsPanels spsPanel = (SpsPanels) HomePage.getWestPanel().getMissionRequestPanel(GeneralConstant.SPS_PANEL_ID);
						
						SpsPanels.getSpsSettingsPanel().getLeftUpperNumberField().setValue(_lowerLat);
						SpsPanels.getSpsSettingsPanel().getRightUpperNumberField().setValue(_lowerLon);
						SpsPanels.getSpsSettingsPanel().getLeftLowerNumberField().setValue(_upperLat);
						SpsPanels.getSpsSettingsPanel().getRightLowerNumberField().setValue(_upperLon);
					} // if (HomePage.getWestPanel().getMissionRequestPanel(GeneralConstant.SPS_PANEL_ID) != null) {
				}
		} catch (DOMException dOMException) {
			com.google.gwt.user.client.Window.alert("SPS DOMException : " + dOMException.getLocalizedMessage());
		} finally {
		}

		//update of Carto AOI 
		try {
			if (HomePage.getHomePageBean().getConfigBean().getWestPanel()) 
				if (HomePage.getHomePageBean().getConfigBean().getCartoAOIPanel()) {
					if (HomePage.getPanelCartoPanel().getCartoAoiPanelLoaded()) {
						HomePage.getPanelCartoPanel().getPanelAOI().getLeftUpperNumberField().setValue(_lowerLat);
						HomePage.getPanelCartoPanel().getPanelAOI().getRightUpperNumberField().setValue(_lowerLon);
						HomePage.getPanelCartoPanel().getPanelAOI().getLeftLowerNumberField().setValue(_upperLat);
						HomePage.getPanelCartoPanel().getPanelAOI().getRightLowerNumberField().setValue(_upperLon);
					} // if (HomePage.getPanelCartoPanel().getCartoAoiPanelLoaded()) {
				} // if (HomePage.getHomePageBean().getConfigBean().getCartoAOIPanel()) {
		} catch (DOMException dOMException) {
			com.google.gwt.user.client.Window.alert("AOI Window DOMException : " + dOMException.getLocalizedMessage());
		} finally {
		}

	} // public static void updateAOIFields

	/**
	 * A Java method using JSNI (JavaScript Native Interface)
	 * $wnd is a JSNI synonym for 'window'
	 * creation de la fonction JavaScript 'appletInit'
	 * effectu&eacute;e dans la m&eacute;thode 'onModuleLoad' du module cf. 'HomePage.java'
	 * fonction appel&eacute;e par l'applet World Wind 
	 * lors de l'initialisation
	 *
	 */
	public static native void initApplet() /*-{
      $wnd.appletInit =
         @com.astrium.faceo.client.service.facadeCartoWWD.FacadeCartoWwd::updateCartoLayersList();
   	}-*/;

	/**
	 * Initialisation de la liste des layers de la cartographie
	 *
	 */
	private static void updateCartoLayersList()  {
		try {
			if (!HomePage.getHomePageBean().getInitCartoLayers()) {
				HomePage.getHomePageBean().setInitCartoLayers(true); // afin de ne pas mettre a jour la liste lors d'un prochain clic sur l'onglet
				((CartoLayersPanel) HomePage.getEastPanel().getCartographicPanel(GeneralConstant.CARTO_LAYERS_PANEL_ID)).getRefreshButton().fireEvent("click");
			}
		} catch (Exception exception) {
			com.google.gwt.user.client.Window.alert("updateCartoLayersList : " + exception.getLocalizedMessage());
		} finally {
		}
	} // private static void updateCartoLayersList

	/**
	 * A Java method using JSNI (JavaScript Native Interface)
	 * $wnd is a JSNI synonym for 'window'
	 * creation de la fonction JavaScript 'appletInit'
	 * effectu&eacute;e dans la m&eacute;thode 'onModuleLoad' du module cf. 'HomePage.java'
	 * fonction appel&eacute;e par l'applet World Wind 
	 * par la m&eacute;thode 'start'
	 *
	 */
	public static native void startApplet() /*-{
      $wnd.appletStart =
        @com.astrium.faceo.client.service.facadeCartoWWD.FacadeCartoWwd::updateCartoLayersList();
   	}-*/;

	/**
	 * A Java method using JSNI (JavaScript Native Interface)
	 * $wnd is a JSNI synonym for 'window'
	 * creation de la fonction JavaScript 'appletInit'
	 * effectu&eacute;e dans la m&eacute;thode 'onModuleLoad' du module cf. 'HomePage.java'
	 * fonction appel&eacute;e par l'applet World Wind 
	 * par la m&eacute;thode 'start'
	 *
	 */
	public static native void resizeAppletStart() /*-{
    $wnd.resizeAppletStart =
      @com.astrium.faceo.client.service.facadeCartoWWD.FacadeCartoWwd::updateDimCartoApplet(Ljava/lang/String;Ljava/lang/String;);
 	}-*/;

	/**
	 * A Java method using JSNI (JavaScript Native Interface)
	 * $wnd is a JSNI synonym for 'window'
	 * creation de la fonction JavaScript 'showTheAppletMessage'
	 * effectu&eacute;e dans la m&eacute;thode 'onModuleLoad' du module cf. 'HomePage.java'
	 * fonction appel&eacute;e par l'applet World Wind 
	 * par la m&eacute;thode 'start' pour afficher des messages
	 *
	 */
	public static native void showAppletMessage() /*-{
    $wnd.showAppletMessage =
      @com.astrium.faceo.client.service.facadeCartoWWD.FacadeCartoWwd::showTheAppletMessage(Ljava/lang/String;);
 	}-*/;

	/**
	 * A Java method using JSNI (JavaScript Native Interface)
	 * $wnd is a JSNI synonym for 'window'
	 * creation de la fonction JavaScript 'testJreVersion'
	 * effectu&eacute;e dans la m&eacute;thode 'onModuleLoad' du module cf. 'HomePage.java'
	 * fonction appel&eacute;e par l'applet World Wind 
	 * par la m&eacute;thode 'start' pour afficher des messages
	 *
	 */
	public static native void testJreVersion() /*-{
    $wnd.testJreVersion =
      @com.astrium.faceo.client.service.facadeCartoWWD.FacadeCartoWwd::testTheJreVersion(Ljava/lang/String;Ljava/lang/String;IIII);
 	}-*/;

	/**
	 * MAJ des conteneurs de l'applet World Wind et de Google Earth
	 *
	 *	@param _width (String)	: container's width
	 *	@param _height (String)	: container's height
	 */
	private static void updateDimCartoApplet(String _width, String _height)  {
		try {
			String idElement = GeneralConstant.ID_CARTO_APPLET;
			if (!HomePage.getPanelCartoPanel().getCartoAppletPanel().getSetApplet())
				idElement = GeneralConstant.ID_DIV_CARTO_APPLET;
			HomePage.getPanelCartoPanel().resizeCartoPanel(idElement, _width, _height);
		} catch (Exception exception){
			com.google.gwt.user.client.Window.alert("setDimCartoApplet : " + exception.getLocalizedMessage());
		} finally {
		}
	} // private static void updateDimCartoApplet(

	/**
	 * Affichage d'un message de l'applet World Wind lors de son d&eacute;marrage
	 *
	 *	@param _message (String)	: applet's message
	 */
	private static void showTheAppletMessage(String _message)  {
		try {
			com.google.gwt.user.client.Window.alert("Applet message : " + _message);
		} catch (Exception exception){
			com.google.gwt.user.client.Window.alert("showTheAppletMessage : " + exception.getLocalizedMessage());
		} finally {
		}
	} // private static void showTheAppletMessage(

	/**
	 * Affichage d'un message de l'applet World Wind lors de son d&eacute;marrage
	 *
	 *	@param _versionFull (String)	: JRE version (1.6.0_13)
	 *	@param _vendor (String)	: 
	 *	@param _majorVersion (int)	: example JRE version (1.6.0_13) : 1
	 *	@param _version (int)		: example JRE version (1.6.0_13) : 6
	 *	@param _minorVersion (int)	: example JRE version (1.6.0_13) : 0
	 *	@param _releaseVersion (int): example JRE version (1.6.0_13) : 13
	 */
	private static void testTheJreVersion(String _versionFull, String _vendor, 
			int _majorVersion, int _version, int _minorVersion, int _releaseVersion )  {

		try {
			boolean error = false;
			boolean errorJRE = false;

			if (_majorVersion < 1) 
				error = true; 
			else if (_majorVersion == 1) { 
				if (_version < 5)
					error = true; 
				else if (_version == 5) { 
					if (_minorVersion < 0)
						error = true; 
					else if (_minorVersion == 0) { 
						if (_releaseVersion < GeneralConstant.JRE_VERSION_MIN)
							errorJRE = true; 
					} // if (_minorVersion < 0)
				} // if (_version < 6)
			} // if (_majorVersion < 1)

			HomePage.getHomePageBean().setJreVersion(
					_majorVersion + "." + _version + "." + _minorVersion + "_" + _releaseVersion);

			if (error) {
				FaceoConstants faceoConstants =
					(FaceoConstants) com.google.gwt.core.client.GWT.create(FaceoConstants.class);
				com.google.gwt.user.client.Window.alert("JRE : \n version : " + _versionFull 
						+ "\n" + faceoConstants.uploadNewJreVersion()
						+ " ( >= " + GeneralConstant.JRE_FULL_VERSION_MIN + " )");
			}  else if (errorJRE){
				FaceoConstants faceoConstants =
					(FaceoConstants) com.google.gwt.core.client.GWT.create(FaceoConstants.class);
				com.google.gwt.user.client.Window.alert("JRE : \n version : " + _versionFull 
						+ "\n" + faceoConstants.uploadNewJreVersion() + " " + faceoConstants.toUseWmsFunctions()
						+ " ( >= " + GeneralConstant.JRE_FULL_VERSION_MIN + " )" + "\n"
						+ "<a href =\"https://cds.sun.com/is-bin/INTERSHOP.enfinity/WFS/CDS-CDS_Developer-Site/en_US/-/USD/ViewProductDetail-Start?ProductRef=jre-6u10-oth-JPR@CDS-CDS_Developer\"" + "\">" + "upload java version</a>");
			} // if (error) {
		} catch (Exception exception){
			com.google.gwt.user.client.Window.alert("testTheJreVersion : " + exception.getLocalizedMessage());
		} finally {
		}

	} // private static void testTheJreVersion(String _version, String _vendor)  {

	/**
	 *
	 *
	 */
	private static void appletStart()  {
	} // public static void appletStart

	/**
	 * A Java method using JSNI (JavaScript Native Interface)
	 * $wnd is a JSNI synonym for 'window'
	 * creation de la fonction JavaScript 'appletInit'
	 * effectu&eacute;e dans la m&eacute;thode 'onModuleLoad' du module cf. 'HomePage.java'
	 * fonction appel&eacute;e par l'applet World Wind 
	 * lors de son arr&ecirc;t
	 *
	 */
	public static native void stopApplet() /*-{
      $wnd.appletStop =
         @com.astrium.faceo.client.service.facadeCartoWWD.FacadeCartoWwd::appletStop();
   	}-*/;

	/**
	 * 
	 *
	 */
	private static void appletStop()  {
	} // public static void appletStop

	/**
	 * A Java method using JSNI (JavaScript Native Interface)
	 * $wnd is a JSNI synonym for 'window'
	 * creation de la fonction JavaScript qui retourne la propri&eacute;t&eacute; platform
	 * effectu&eacute;e dans la m&eacute;thode 'onModuleLoad' du module cf. 'HomePage.java'
	 *
	 * @return String : User Platform
	 */
	public static native String JSgetUserPlatform() /*-{
		try {
      		return navigator.platform;
		} catch (error) {
      		return "error";
		} finally {
		}
   	}-*/;

	/**
	 * A Java method using JSNI (JavaScript Native Interface)
	 * $wnd is a JSNI synonym for 'window'
	 * 
	 * @return boolean : true is Java is enabled on the browser, false else
	 */
	public static native boolean isJavaEnabled() /*-{
      return navigator.javaEnabled();
   	}-*/;	

	/**
	 * A Java method using JSNI (JavaScript Native Interface)
	 * $wnd is a JSNI synonym for 'window'
	 * appel de la fonction JavaScript 'getVersionsAppletWWD'
	 * d&eacute;finie dans le fichier JavaScript 'appletWW.js'
	 * 
	 * M&eacute;thode permettant de renvoyer les versions de l'applet et de World Wind
	 * 
	 * @return String : 	The result 
	 * 
	 */
	public static native String JSgetVersionsAppletWWD()/*-{
	  	return $wnd.getVersionsAppletWWD();
	}-*/;

	/**
	 * A Java method using JSNI (JavaScript Native Interface)
	 * $wnd is a JSNI synonym for 'window'
	 * appel de la fonction JavaScript 'showHideSun'
	 * d&eacute;finie dans le fichier JavaScript 'appletWW.js'
	 * 
	 * Affichage/d&eacute;saffichage lme soleil
	 * 
	 * @param _enabled (boolean)	: true to display layer
	 *            					  false to hide layer
	 */
	public static native void JSshowHideSun(boolean _enabled)/*-{
		$wnd.showHideSun(_enabled);
	}-*/;	

} // Class
