package com.astrium.faceo.client.common;


//import

/**
 * <B>SITE FACEO</B> <BR>
 * 
 * <P>
 * Cette classe stocke des constantes 'client' non localisables
 * </P>
 * </P>
 * 
 * @author GR 
 * @version 1.0, le 10/10/2008
 **/
public class GeneralConstant {

	/** ************************************ */
	/*          Application Version          */
	/** ************************************ */

	static final public String APPLICATION_VERSION = "1.1.30";
	
	/** ************************************ */
	/*               User Agent              */
	/** ************************************ */

	static final public String PLATFORM_MULTITOUCH_FF3 = "MultiTouchFF3";
	
	/** ************************************ */
	/*      Mode de S&eacute;lection des     */
	/*       objets de la cartographie       */
	/** ************************************ */

	/**
	 * 	parameter to determine the selection mode for objects on World Wind cartography
	 */
	static final public String CARTO_OBJECTS_PARAM_MODE_SELECTION = "cartoSelectMode";
	
	/**
	 *  CLICK selection mode for objects on World Wind cartography
	 */
	static final public String CARTO_OBJECTS_CLIC_MODE_SELECTION = "2";
	
	/**
	 *  OVER selection mode for objects on World Wind cartography
	 */
	static final public String CARTO_OBJECTS_OVER_MODE_SELECTION = "1";
	
	/** ************************************ */
	/*              JRE Version              */
	/** ************************************ */

	/**  */
	static final public int JRE_VERSION_MIN = 18;

	/**  */
	static final public String JRE_FULL_VERSION_MIN = "1.5.0.18";

	/** ************************************ */
	/*     Cartographie (World Wind Java)    */
	/* 	           Type des objets    		 */
	/** ************************************ */

	/**  */
	static final public int CARTO_CATALOG_AOI_TYPE = 1;
	/**  */
	static final public int CARTO_PROGRAMMING_OBJECT_TYPE = 3;
	
	/** ************************************ */
	/*     Cartographie (World Wind Java)    */
	/* 	           Type des layers    		 */
	/** ************************************ */
	/** WMSTiledImageLayer */
	static final public int CARTO_WMS_TILED_IMAGE_LAYER_TYPE = 12;
	
	/** ************************************ */
	/*              GWT panels               */
	/** ************************************ */

	/** border panel id */
	static final public String BORDER_PANEL_ID = "BorderPanel";
	
	/** north panel id */
	static final public String NORTH_PANEL_ID = "NorthPanel";
	
	/** center panel id */
	static final public String CENTER_PANEL_ID = "CenterPanel";
	
	//------------------------ Multi-Mission Requests Panels --------------------------
	/** west panel id */
	static final public String WEST_PANEL_PANEL_ID = "WestPanel";
	
	//------------------------ Programming Panels --------------------------
	/** SPS 2panel id */
	static final public String SPS_PANEL_ID = "3_SpsPanel";

	//------------------------ Cartographic Panels --------------------------
	/** east panel id */
	static final public String EAST_PANEL_PANEL_ID = "EastPanel";

	//------------------------ Cartographic Layers Panels --------------------------
	/** cartography layers management panel id */
	static final public String CARTO_LAYERS_PANEL_ID = "1_CartoLayersPanel";

	/** ********* NORTH PANEL ******** */
	/** cartography north panel height */
	static final public int NORTH_PANEL_HEIGHT = 75;

	/** ********* WEST PANEL ******** */
	/** cartography west panel width */
	static final public int WEST_PANEL_WIDTH = 600;

	/** cartography west panel split width min */
	static final public int WEST_PANEL_SPLIT_WIDTH_MIN = 250;

	/** ********* CENTER PANEL ******** */
	static final public int CENTER_PANEL_WIDTH_MIN = 100;
	/** */
	static final public int CENTER_PANEL_WIDTH_MAX = 1900;
	
	/** ********* World Wind Java Applet Cartography ******** */
	static final public String ID_DIV_CARTO_APPLET = "divWwjApplet";
	/** */
	static final public String ID_CARTO_APPLET = "cartoApplet";

	/** ********* definition des groupes 'Drag & Drop' ******** */
	static final public String CARTO_DRAG_DROP_GROUP = "cartoGridDragDropGroup";

	/** ********* EAST PANEL ******** */
	static final public int EAST_PANEL_WIDTH = 450;

	/** */
	static final public int EAST_PANEL_SPLIT_WIDTH_MIN = 150;

	/** ********* CARTOGRAPHY LAYERS PANEL ******** */
	static final public int CARTOGRAPHY_GRID_LAYERS_PANEL_HEIGHT = 305;
	
	/** ************************************ */
	/*                 Layers                */
	/** ************************************ */
	static final public String LAYER_AOI = "AOI";
	/** */
	static final public String LAYER_CATALOGS_FOOTPRINTS = "Catalogs Footprints";
	/** */
	static final public String LAYER_PROG_SOLUTIONS = "Solutions";
	/** */
	static final public String LAYER_OPACITY_VALUES = "Opacity";
	/** */
	static final public int CARTO_LAYER_ENABLED = 1;
	/** */
	static final public int CARTO_LAYER_OPACITY = 2;
	/** */
	static final public int CARTO_LAYER_DOWN = 3;
	/** */
	static final public int CARTO_LAYER_UP = 4;
	
	/** ***** Toponyms Operations **** */
	static final public String TOPONYMS_GET_TOPONYMS = "GetToponyms";

	/** ************************************ */
	/*             Toponyms Exceptions       */
	/** ************************************ */
	static final public int TOPONYM_MALFORMED_EXCEPTION = 551;
	/** */
	static final public int TOPONYM_SOCKET_TIMEOUT_EXCEPTION = 552;
	/** */
	static final public int TOPONYM_IO_EXCEPTION = 553;
	/** */
	static final public int TOPONYM_DOM_PARSE_EXCEPTION = 554;
	/** */
	static final public int TOPONYM_DOM_EXCEPTION = 555;
	/** */
	static final public int TOPONYM_EXCEPTION = 556;

	/** ************************************ */
	/*            formats de dates           */
	/** ************************************ */
	static final public String LONG_FORMAT_DATE = "yyyy-MM-dd'T'HH:mm:ss'.0Z'";
	/** */
	static final public String SHORT_FORMAT_DATE = "dd-MM-yyyy HH:mm";
	
	/** ************************************ */
	/*                   AOI	             */
	/** ************************************ */
	static final public String AOI_LEFT_UPPER_NUMBER_FIELD = "44.1739";
	/** */
	static final public String AOI_RIGHT_UPPER_NUMBER_FIELD = "-1.8255";
	/** */
	static final public String AOI_LEFT_LOWER_NUMBER_FIELD = "42.6460";
	/** */
	static final public String AOI_RIGHT_LOWER_NUMBER_FIELD = "3.9110";

	
	/** ************************************ */
	/*     Cartographie (World Wind Java)    */
	/* 	          Type des rectangles  		 */
	/** ************************************ */

	/** */
	static final public int RECTANGLE_STANDARD_TYPE = 0;	
	
	/** ************************************ */
	/* 	   		DragAnDDropEvents			 */
	/** ************************************ */		

	/** */
	static final public int DND_EVENT_DROP = 1;

	/** */
	static final public int DND_EVENT_DROP_OUT = 2;

	/** */
	static final public int DND_EVENT_DROP_OVER = 3;

	/** ************************************ */
	/* 	   		UpdateAoiEvents				 */
	/** ************************************ */		

	/** */
	static final public int UPDATE_AOI_EVENT = 4;

	/* 	   		SelectRowEvents				 */
	/** ************************************ */		

	/** */
	static final public int SELECT_ROW_EVENT = 5;
	
	/** ************************************ */
	/* 	   		DisplayAoiEvents			 */
	/** ************************************ */		

	/** */
	static final public int DISPLAY_AOI_EVENT = 6;

} // class
