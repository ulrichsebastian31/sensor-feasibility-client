package com.astrium.faceo.client.common.cartography.cartoLayers;

/*
 * @(#)CartoLayersConstants.java	 1.0  30/10/2008
 *
 *
 * PROJET       : FACEO
 *
 * LANGUAGE     : Java
 *
 * DESCRIPTION  : La classe CartoLayersConstants gere les constantes 'Layers' du site
 *
 *
 * CREATION :
 * ---------------------------------------------------------------------
 * | Date       | Version | Description                                |
 * ---------------------------------------------------------------------
 * | 30/10/2008 |    1.0  |                                            |
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
 * La classe CartoLayersConstants g&eagrave;re les constantes utilis&eacute;es par le site
 * </P>
 * </P>
 * 
 * @author GR
 * @version 1.0, le 30/10/2008
 */
public interface CartoLayersConstants extends Constants {
		
	/* ---------------------- Catalog ---------------------- */
	/**
	 * Translated "Layers".
	 * 
	 * @return translated "Layers"
	*/
	@Key("layersTitle")
	String title();

	/**
	 * Translated "Update".
	 * 
	 * @return translated "Update"
	*/
	@Key("layersUpdateList")
	String updateList();
	
	/**
	 * Translated "Update list".
	 * 
	 * @return translated "Update list"
	*/
	@Key("layersUpdateListTip")
	String updateListTip();
	
	/**
	 * Translated "Results".
	 * 
	 * @return translated "Results"
	*/
	@Key("layersResults")
	String results();

	/**
	 * Translated "Opacity values".
	 * 
	 * @return translated "Opacity values"
	*/
	@Key("layersOpacityValuesTitle")
	String opacityValuesTitle();

	/**
	 * Translated "Name".
	 * 
	 * @return translated "Name"
	*/
	@Key("layersName")
	String name();

	/**
	 * Translated "URL".
	 * 
	 * @return translated "URL"
	*/
	@Key("layersUrl")
	String url();
	
	/**
	 * Translated "Enabled".
	 * 
	 * @return translated "Enabled"
	*/
	@Key("layersEnabled")
	String enabled();
	
	/**
	 * Translated "Opacity".
	 * 
	 * @return translated "Opacity"
	*/
	@Key("layersOpacity")
	String opacity();
	
	/**
	 * Translated "Down".
	 * 
	 * @return translated "Down"
	*/
	@Key("layerDown")
	String down();

	/**
	 * Translated "Up".
	 * 
	 * @return translated "Up"
	*/
	@Key("layerUp")
	String up();

	/**
	 * Translated "MinActiveAltitude".
	 * 
	 * @return translated "MinActiveAltitude"
	*/
	@Key("layersMinActiveAltitude")
	String minActiveAltitude();
	
	/**
	 * Translated "MaxActiveAltitude".
	 * 
	 * @return translated "MaxActiveAltitude"
	*/
	@Key("layersMaxActiveAltitude")
	String maxActiveAltitude();
	
	/**
	 * Translated "WMS".
	 * 
	 * @return translated "WMS"
	*/
	@Key("layersWms")
	String wms();
	
	/**
	 * Translated "Layers".
	 * 
	 * @return translated "Layers"
	*/
	@Key("layersDisplayMsg")
	String displayMsg();

	/**
	 * Translated "No layer avalaible on WMS server ".
	 * 
	 * @return translated "No layer avalaible on WMS server "
	*/
	@Key("noLayerAvalaible")
	String noLayerAvalaible();

	/**
	 * Translated "No layer to display".
	 * 
	 * @return translated "No layer to display"
	*/
	@Key("layersNoLayerToDisplayMsg")
	String noLayerToDisplayMsg();

	/**
	 * Translated "Show Preview".
	 * 
	 * @return translated "Show Preview"
	*/
	@Key("layersShowPreview")
	String showPreview();
	
	/**
	 * Translated "Add or remove the layer on cartograhy".
	 * 
	 * @return translated "Add or remove the layer on cartograhy"
	*/
	@Key("layersEnabledColumnTip")
	String enabledColumnTip();
	
	/**
	 * Translated "Down the layer in the layer stack".
	 * 
	 * @return translated "Down the layer in the layer stack"
	*/
	@Key("layersDownActionRendererTip")
	String downActionRendererTip();
	
	/**
	 * Translated "Up the layer in the layer stack".
	 * 
	 * @return translated "Up the layer in the layer stack"
	*/
	@Key("layersUpActionRendererTip")
	String upActionRendererTip();
	
	/**
	 * Translated "Modify the layer opacity".
	 * 
	 * @return translated "Modify the layer opacity"
	*/
	@Key("layersEditorGridPanelCartoLayersTip")
	String editorGridPanelCartoLayersTip();
	
} // class
