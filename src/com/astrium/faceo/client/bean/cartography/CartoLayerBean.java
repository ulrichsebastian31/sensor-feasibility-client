package com.astrium.faceo.client.bean.cartography;

import java.io.Serializable;

/*
 * @(#)CartoLayerBean.java	 1.0  09/2008
 *
 *
 * PROJET       : SITE FACEO
 *
 * LANGUAGE     : Java
 *
 * DESCRIPTION  : Cette classe est un conteneur d'informations 
 * pour les objets de type Layers
 * g&eacute;or&eacute;f&eacute;renc&eacute;s sur la cartographie
 *
 * CREATION :
 * ---------------------------------------------------------------------
 * | Date       | Version | Description                                |
 * ---------------------------------------------------------------------
 * | 09/2008 |    1.0  |                                            |
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

/**
 * <B>SITE FACEO</B> <BR>
 * 
 * <P>
 * Cette classe est un conteneur d'informations 
 * pour les objets de type layers
 * g&eacute;or&eacute;f&eacute;renc&eacute;s sur la cartographie * </P>
 * </P>
 * 
 * @author  GR
 * @version 1.0, le 09/2008
 */
public class CartoLayerBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5583932446067444125L;

	// Layer Name
	private String layerName = null;

	// Layer Title : for WMS Layer
	private String layerTitle = null;

	// Layer Enabled
	private boolean layerEnabled = false;

	// Layer Transparency
	private double layerTransparency = 1d;

	// Layer MinActiveAltitude
	private float layerMinActiveAltitude = 0f;

	// Layer MaxActiveAltitude
	private float layerMaxActiveAltitude = 0f;

	// Layer Type
	private int layerType = 0;

	// Layer WMS
	/**
	 * d&eacute;but des m&eacute;thodes	 
	 * 
	 * Constructeur par d&eacute;faut : vide : convention Bean
	 */
	/**
	 * Default Constructor. The Default Constructor's explicit declaration
	 * is required for a serializable class. (GWT)
	*/
	public CartoLayerBean() {
	}

	/** ----------------------- getters ----------------------- */
	
	/** 
	 * getter on layerName
	 * 
	 * @return String : layer Name
	*/
	public String getLayerName() {
		return (this.layerName != null) ? this.layerName : "";
	}

	/** 
	 * getter on layerTitle
	 * 
	 * @return String : layer Title
	*/
	public String getLayerTitle() {
		return (this.layerTitle != null) ? this.layerTitle : "";
	}

	/** 
	 * getter on layerTransparency
	 * 
	 * @return double : layer Transparency
	*/
	public double getLayerTransparency() {
		return this.layerTransparency;
	}

	/** 
	 * getter on layerMinActiveAltitude
	 * 
	 * @return float : layer Minimum Active Altitude
	*/
	public float getLayerMinActiveAltitude() {
		return this.layerMinActiveAltitude;
	}

	/** 
	 * getter on layerMaxActiveAltitude
	 * 
	 * @return float : layer Maximum Active Altitude
	*/
	public float getLayerMaxActiveAltitude() {
		return this.layerMaxActiveAltitude;
	}

	/** 
	 * getter on layerType
	 * 
	 * @return int : layer Type
	*/
	public int getLayerType() {
		return this.layerType;
	}

	/** 
	 * getter on layerEnabled
	 * 
	 * @return boolean : true if the layer is enabled
	*/
	public boolean getLayerEnabled() {
		return this.layerEnabled;
	}

	/** ----------------------- setters ----------------------- */

	/** 
	 * setter on layerName
	 * 
	 * @param _layerName (String) : layer Name
	*/
	public void setLayerName(String _layerName) {
		this.layerName = _layerName;
	}
	
	/** 
	 * setter on layerTitle
	 * 
	 * @param _layerTitle (String) : layer Title
	*/
	public void setLayerTitle(String _layerTitle) {
		this.layerTitle = _layerTitle;
	}

	/** 
	 * setter on layerTransparency
	 * 
	 * @param _layerTransparency (double) : layer Transparency
	*/
	public void setLayerTransparency(double _layerTransparency) {
		this.layerTransparency = _layerTransparency;
	}
	
	/** 
	 * setter on layerMinActiveAltitude
	 * 
	 * @param _layerMinActiveAltitude (float) : layer Minimum Active Altitude
	*/
	public void setLayerMinActiveAltitude(float _layerMinActiveAltitude) {
		this.layerMinActiveAltitude = _layerMinActiveAltitude;
	}

	/** 
	 * setter on layerMaxActiveAltitude
	 * 
	 * @param layerMaxActiveAltitude (float) : layer Maximum Active Altitude
	*/
	public void setLayerMaxActiveAltitude(float layerMaxActiveAltitude) {
		this.layerMaxActiveAltitude = layerMaxActiveAltitude;
	}

	/** 
	 * setter on layerType
	 * 
	 * @param _layerType (int) : layer Type
	*/
	public void setLayerType(int _layerType) {
		this.layerType = _layerType;
	}

	/** 
	 * setter on layerEnabled
	 * 
	 * @param _layerEnabled (boolean) : true if the layer is enabled
	*/
	public void setLayerEnabled(boolean _layerEnabled) {
		this.layerEnabled = _layerEnabled;
	}

} // class
