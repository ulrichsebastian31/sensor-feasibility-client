package com.astrium.faceo.client.common.faceo;

/*
 * @(#)FaceoConstants.java	 1.0  11/06/2008
 *
 *
 * PROJET       : FACEO
 *
 * LANGUAGE     : Java
 *
 * DESCRIPTION  : La classe FaceoConstants gere les constantes du site
 *
 *
 * CREATION :
 * ---------------------------------------------------------------------
 * | Date       | Version | Description                                |
 * ---------------------------------------------------------------------
 * | 11/06/2008 |    1.0  |                                            |
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

import com.google.gwt.i18n.client.Constants;

/**
 * <B>SITE FACEO</B> <BR>
 * 
 * <P>
 * La classe FaceoConstants g&eagrave;re les constantes utilis&eacute;es par le site
 * </P>
 * </P>
 * 
 * @author GR
 * @version 1.0, le 11/06/2008
 */
public interface FaceoConstants extends Constants {
	
	/* ---------------------- Menu ---------------------- */
	/**
	 * Translated "Menu".
	 * 
	 * @return translated "Menu"
	*/
	@Key("menuTitle")
	String menuTitle();

	/* ---------------------- Window Main Menu ---------------------- */
	/**
	 * Translated "Pilot".
	 * 
	 * @return translated "Pilot"
	*/
	@Key("windowMainMenuTitle")
	String windowMainMenuTitle();

	/* ---------------------- Functions ---------------------- */
	/**
	 * Translated "Functions".
	 * 
	 * @return translated "Functions"
	*/
	@Key("functionsTitle")
	String functionsTitle();
	
	/* ---------------------- Cartography ---------------------- */
	/**
	 * Translated "Cartography".
	 * 
	 * @return translated "Cartography"
	*/
	@Key("cartographyTitle")
	String cartographyTitle();

	/**
	 * Translated "3D Cartography".
	 * 
	 * @return translated "3D Cartography"
	*/
	@Key("cartography3DTitle")
	String cartography3DTitle();
	
	/**
	 * Translated "AOI".
	 * 
	 * @return translated "AOI"
	*/
	@Key("cartographyAoi")
	String cartographyAoi();
	
	/**
	 * Translated "draw rectangle".
	 * 
	 * @return translated "draw rectangle"
	*/
	@Key("cartographyDrawRectangle")
	String cartographyDrawRectangle();

	/**
	 * Translated "undraw".
	 * 
	 * @return translated "undraw"
	*/
	@Key("cartographyUnDraw")
	String cartographyUnDraw();	
	
	/**
	 * Translated "display".
	 * 
	 * @return translated "display"
	*/
	@Key("cartographyDisplay")
	String cartographyDisplay();	
	
	/**
	 * Translated "undisplay".
	 * 
	 * @return translated "undisplay"
	*/
	@Key("cartographyUnDisplay")
	String cartographyUnDisplay();	
	
	/**
	 * Translated "rectangle coordinates".
	 * 
	 * @return translated "rectangle coordinates"
	*/
	@Key("cartographyRectangleCoordinates")
	String cartographyRectangleCoordinates();
	
	/**
	 * Translated "Full screen".
	 * 
	 * @return translated "Full screen"
	*/
	@Key("cartographyFullScreen")
	String cartographyFullScreen();

	/**
	 * Translated "Country".
	 * 
	 * @return translated "Country"
	*/
	@Key("cartographyCountry")
	String cartographyCountry();	

	/* ---------------------- Cartography Management ---------------------- */
	/**
	 * Translated "Cartography Management".
	 * 
	 * @return translated "Cartography Management"
	*/
	@Key("cartographyManagementTitle")
	String cartographyManagementTitle();
		
	/* ---------------------- Cartography parameters ---------------------- */
	/**
	 * Translated "Cartography Projections".
	 * 
	 * @return translated "Cartography Projections"
	*/
	@Key("cartographyProjectionsTitle")
	String cartographyProjectionsTitle();
		
	/* ---------------------- Cartography parameters ---------------------- */
	/**
	 * Translated "cartography parameters".
	 * 
	 * @return translated "cartography parameters"
	*/
	@Key("cartographyParametersTitle")
	String cartographyParametersTitle();
		
	/* ---------------------- Layers ---------------------- */
	/**
	 * Translated "Layers".
	 * 
	 * @return translated "Layers"
	*/
	@Key("layersTitle")
	String layersTitle();
	
	/**
	 * Translated "Press the Load button to load the Layers'list".
	 * 
	 * @return translated "Press the Load button to load the Layers'list"
	*/
	@Key("updateGridLayers")
	String updateGridLayers();
	
	/**
	 * Translated "No Layer".
	 * 
	 * @return translated "No Layer"
	*/
	@Key("emptyGridLayers")
	String emptyGridLayers();
	
	/**
	 * Translated "Opacity values".
	 * 
	 * @return translated "Opacity values"
	*/
	@Key("opacityValuesTitle")
	String opacityValuesTitle();
	
	
	/* ---------------------- GPS layers ---------------------- */	
	/**
	 * Translated "GPS Layers".
	 * 
	 * @return translated "GPS Layers"
	*/
	@Key("gpsLayers")
	String gpsLayers();

	/**
	 * Translated "GPS layers".
	 * 
	 * @return translated "GPS layers"
	*/
	@Key("GPSlayersTitle")
	String gpslayersTitle();	
	
	/* ---------------------- Mission simulation ---------------------- */
	/**
	 * Translated "Mission simulation".
	 * 
	 * @return translated "Mission simulation"
	*/
	@Key("missionSimulation")
	String missionSimulation();

	/* ---------------------- Sat layers ---------------------- */
	/**
	 * Translated "Sat layers".
	 * 
	 * @return translated "Sat layers"
	*/
	@Key("satTrackingTitle")
	String satTrackingTitle();		

	/**
	 * Translated "Real Time Data".
	 * 
	 * @return translated "Real Time Data"
	*/
	@Key("realTimeData")
	String realTimeData();	
	
	/**
	 * Translated "get Sat".
	 * 
	 * @return translated "get Sat"
	*/
	@Key("getSat")
	String getSat();

	/**
	 * Translated "Sat name".
	 * 
	 * @return translated "Sat name"
	*/
	@Key("satName")
	String satName();	

	/**
	 * Translated "Name".
	 * 
	 * @return translated "Name"
	*/
	@Key("name")
	String name();	
	
	/**
	 * Translated "Speed".
	 * 
	 * @return translated "Speed"
	*/
	@Key("speed")
	String speed();	
	
	/**
	 * Translated "Search".
	 * 
	 * @return translated "Search"
	*/
	@Key("search")
	String search();	

	/**
	 * Translated "Add".
	 * 
	 * @return translated "Add"
	*/
	@Key("add")
	String add();	
	
	/**
	 * Translated "Sat".
	 * 
	 * @return translated "Sat"
	*/
	@Key("sat")
	String sat();		
	
	/**
	 * Translated "Satellites".
	 * 
	 * @return translated "Satellite"
	*/
	@Key("Satellites")
	String Satellites();		
		
	/**
	 * Translated "Satellites tracking".
	 * 
	 * @return translated "Satellite tracking"
	*/
	@Key("SatellitesTracking")
	String SatellitesTracking();	
	
	/**
	 * Translated "Mission plan simulation".
	 * 
	 * @return translated "Mission plan simulation"
	*/
	@Key("MissionPlanSimulation")
	String MissionPlanSimulation();	
	
	/**
	 * Translated "Receiving station".
	 * 
	 * @return translated "Receiving station"
	*/
	@Key("ReceivingStation")
	String ReceivingStation();	

	/* ---------------------- WMS layers ---------------------- */
	/**
	 * Translated "WMS layers".
	 * 
	 * @return translated "WMS layers"
	*/
	@Key("WMSlayersTitle")
	String wmslayersTitle();
	
	/**
	 * Translated "Load layers's list".
	 * 
	 * @return translated "Load layers's list"
	*/
	@Key("buttonLoadLayersTitle")
	String buttonLoadLayersTitle();
	
	/**
	 * Translated "WMS Layers".
	 * 
	 * @return translated "WMS Layers"
	*/
	@Key("wmsLayers")
	String wmsLayers();
	
	/**
	 * Translated "Measure".
	 * 
	 * @return translated "Measure"
	*/
	@Key("cartographyMeasure")
	String measure();	
	
	/**
	 * Translated "add WMS Server".
	 * 
	 * @return translated "add WMS Server"
	*/
	@Key("wmsLayerAdd")
	String wmsLayerAdd();
	
	/**
	 * Translated "get WMS Layers".
	 * 
	 * @return translated "get WMS Layers"
	*/
	@Key("getWMSLayers")
	String getWMSLayers();
	
	/**
	 * Translated "WMS URL".
	 * 
	 * @return translated "WMS URL"
	*/
	@Key("wmsUrl")
	String wmsUrl();
	
	/* ---------------------- Orbits ---------------------- */
	/**
	 * Translated "Orbits".
	 * 
	 * @return translated "Orbits"
	*/
	@Key("orbitsTitle")
	String orbitsTitle();

	/**
	 * Translated "Orbits List".
	 * 
	 * @return translated "Orbits List"
	*/
	@Key("orbitsList")
	String orbitsList();
	
	/**
	 * Translated "TLEdata.txt".
	 * 
	 * @return translated "TLEdata.txt"
	*/
	@Key("fileTLE")
	String fileTLE();
	
	/* ---------------------- KML ---------------------- */
	/**
	 * Translated "KML".
	 * 
	 * @return translated "KML"
	*/
	@Key("kmlTitle")
	String kmlTitle();
	
	/**
	 * Translated "search KML".
	 * 
	 * @return translated "search KML"
	*/
	@Key("searchKML")
	String searchKML();

	/**
	 * Translated "KML Data Add".
	 * 
	 * @return translated "KML Data Add"
	*/
	@Key("kmlDataAdd")
	String kmlDataAdd();

	/**
	 * Translated "KML Upload".
	 * 
	 * @return translated "KML Upload"
	*/
	@Key("kmlUploadButton")
	String kmlUploadButton();

	/**
	 * Translated "KML add".
	 * 
	 * @return translated "KML add"
	*/
	@Key("kmlDataAddButton")
	String kmlDataAddButton();

	/**
	 * Translated "KML Upload Error".
	 * 
	 * @return translated "KML Upload Error"
	*/
	@Key("kmlUploadError")
	String kmlUploadError();

	/* ---------------------- Toponyms ---------------------- */
	/**
	 * Translated "Toponyms".
	 * 
	 * @return translated "Toponyms"
	*/
	@Key("toponymsTitle")
	String toponymsTitle();
	
	/**
	 * Translated "search Toponym".
	 * 
	 * @return translated "search Toponym"
	*/
	@Key("searchToponyms")
	String searchToponyms();

	/**
	 * Translated "Search".
	 * 
	 * @return translated "Search"
	*/
	@Key("fieldSearchToponyms")
	String fieldSearchToponyms();
	/**
	 * Translated "search".
	 * 
	 * @return translated "search"
	*/
	@Key("searchToponymsButton")
	String searchToponymsButton();

	/* ---------------------- Cartography 2D ---------------------- */
	/**
	 * Translated "Open Layers".
	 * 
	 * @return translated "Open Layers"
	*/
	@Key("cartography2DMapTitle")
	String cartography2DMapTitle();

	/* ---------------------- Cartography 3D ---------------------- */
	/**
	 * Translated "World Wind Applet"
	 * 
	 * @return translated "World Wind Applet"
	*/
	@Key("cartography3DMapTitle")
	String cartography3DMapTitle();

	/**
	 * Translated "AOI".
	 * 
	 * @return translated "AOI"
	*/
	@Key("cartography3DAddAOITitle")
	String cartography3DAddAOITitle();

	/**
	 * Translated "add AOI".
	 * 
	 * @return translated "add AOI"
	*/
	@Key("cartography3DAddAOIText")
	String cartography3DAddAOIText();	
	
	/**
	 * Translated "enter AOI".
	 * 
	 * @return translated "enter AOI"
	*/
	@Key("cartography3DEnterAOIText")
	String cartography3DEnterAOIText();		
	
	/**
	 * Translated "Tag".
	 * 
	 * @return translated "Tag"
	*/
	@Key("cartography3DAddLabelTitle")
	String cartography3DAddLabelTitle();

	/**
	 * Translated "show Street View".
	 * 
	 * @return translated "show Street View"
	*/
	@Key("cartography3DShowStreetViewText")
	String cartography3DShowStreetViewText();		
	
	/**
	 * Translated "Street View".
	 * 
	 * @return translated "Street View"
	*/
	@Key("cartography3DStreetViewTitle")
	String cartography3DStreetViewTitle();	
	
	/**
	 * Translated "center Earth".
	 * 
	 * @return translated "center Earth"
	*/
	@Key("cartographyGlobeInitText")
	String cartographyGlobeInitText();		
	
	/**
	 * Translated "Center".
	 * 
	 * @return translated "Center"
	*/
	@Key("cartography3DCenterTitle")
	String cartography3DCenterTitle();	
	
	/**
	 * Translated "Init".
	 * 
	 * @return translated "Init"
	*/
	@Key("cartographyButtonInitText")
	String cartographyButtonInitText();

	/**
	 * Translated "Address".
	 * 
	 * @return translated "Address"
	*/
	@Key("cartographyButtonAdressSearchText")
	String cartographyButtonAdressSearchText();
	
	/**
	 * Translated "Tools".
	 * 
	 * @return translated "Tools"
	*/
	@Key("toolsButtonText")
	String toolsButtonText();
	
	/**
	 * Translated "Catalog".
	 * 
	 * @return translated "Catalog"
	*/
	@Key("catalogButtonText")
	String catalogButtonText();
	
	/**
	 * Translated "WMS".
	 * 
	 * @return translated "WMS"
	*/
	@Key("cartographyWmsButtonText")
	String cartographyWmsButtonText();

	/**
	 * Translated "Screen rotation".
	 * 
	 * @return translated "Screen rotation"
	*/
	@Key("cartographyScreenRotation")
	String cartographyScreenRotation();

	/**
	 * Translated "POI".
	 * 
	 * @return translated "POI"
	*/
	@Key("cartographyPOI")
	String cartographyPOI();	
	
	/**
	 * Translated "Print screen".
	 * 
	 * @return translated "Print screen"
	*/
	@Key("cartographyPrintScreen")
	String cartographyPrintScreen();	
	
	/**
	 * Translated "toponym search".
	 * 
	 * @return translated "toponym search"
	*/
	@Key("cartography3DToponymSearchText")
	String cartography3DToponymSearchText();		
	
	/**
	 * Translated "Toponym".
	 * 
	 * @return translated "Toponym"
	*/
	@Key("cartography3DToponymTitle")
	String cartography3DToponymTitle();
	
	/**
	 * Translated "Allows you to choose the cartographic projection mode ".
	 * 
	 * @return translated "Allows you to choose the cartographic projection mode "
	*/
	@Key("cartographyProjectionsToolBarMenuButtonTip")
	String projectionsToolBarMenuButtonTip();
	
	/**
	 * Translated "Define an Area Of Interest on cartography".
	 * 
	 * @return translated "Define an Area Of Interest on cartography "
	*/
	@Key("cartographyAoiToolBarMenuButtonTip")
	String aoiToolBarMenuButtonTip();

	/**
	 * Translated "Statistics".
	 * 
	 * @return translated "Statistics"
	*/
	@Key("cartography3DStatisticsText")
	String cartography3DStatisticsText();	
	
	
	/**
	 * Translated "View type".
	 * 
	 * @return translated "View type"
	*/
	@Key("cartography3DViewTypeText")
	String cartography3DViewTypeText();	

	/**
	 * Translated "Stereo".
	 * 
	 * @return translated "Stereo"
	*/
	@Key("cartography3DStereoText")
	String cartography3DStereoText();	

	/**
	 * Translated "Mono".
	 * 
	 * @return translated "Mono"
	*/
	@Key("cartography3DMonoText")
	String cartography3DMonoText();		
	
	/**
	 * Translated "add Tag".
	 * 
	 * @return translated "add Tag"
	*/
	@Key("cartography3DAddLabelText")
	String cartography3DAddLabelText();

	/**
	 * Translated "Google Earth".
	 * 
	 * @return translated "Google Earth"
	*/
	@Key("googleEarthTitle")
	String googleEarthTitle();

	/* ---------------------- Cartography parameters ---------------------- */
	/**
	 * Translated "Parameters".
	 * 
	 * @return translated "Parameters"
	*/
	@Key("cartographyParameters")
	String cartographyParameters();

	/**
	 * Translated "Projection".
	 * 
	 * @return translated "Projection"
	*/
	@Key("cartographyProjectionsFieldSetTitle")
	String cartographyProjectionsFieldSetTitle();

	/**
	 * Translated "Projections types".
	 * 
	 * @return translated "Projections types"
	*/
	@Key("cartographyProjectionsTypesTitle")
	String cartographyProjectionsTypesTitle();

	/**
	 * Translated "Types".
	 * 
	 * @return translated "Types"
	*/
	@Key("cartographyProjectionsTypesLabel")
	String cartographyProjectionsTypesLabel();

	/* ---------------------- CSS parameters ---------------------- */
	/**
	 * Translated "CSS Style".
	 * 
	 * @return translated "CSS Style"
	*/
	@Key("cssStyleFieldSetTitle")
	String cssStyleFieldSetTitle();

	/**
	 * Translated "CSS Style".
	 * 
	 * @return translated "CSS Style"
	*/
	@Key("cssStylesTitle")
	String cssStylesTitle();

	/**
	 * Translated "Themes".
	 * 
	 * @return translated "Themes"
	*/
	@Key("cssStylesLabel")
	String cssStylesLabel();

	/* ---------------------- Others ---------------------- */
	/**
	 * Translated "This function is'nt available for ".
	 * 
	 * @return translated "This function is'nt available for "
	*/
	@Key("unavailableFunction")
	String unavailableFunction();
	
	/* ---------------------- JRE version test ---------------------- */
	/**
	 * Translated "Upload new JRE version".
	 * 
	 * @return translated "Upload new JRE version"
	*/
	@Key("uploadNewJreVersion")
	String uploadNewJreVersion();

	/**
	 * Translated "to use GMES and WMS functions".
	 * 
	 * @return translated "to use GMES and WMS functions"
	*/
	@Key("toUseWmsFunctions")
	String toUseWmsFunctions();

	/**
	 * Translated "Help".
	 * 
	 * @return translated "Help"
	*/
	@Key("helpWindowTitle")
	String helpWindowTitle();

	/**
	 * Translated "Help".
	 * 
	 * @return translated "Help"
	*/
	@Key("versionsWindowTitle")
	String versionsWindowTitle();

} // class
