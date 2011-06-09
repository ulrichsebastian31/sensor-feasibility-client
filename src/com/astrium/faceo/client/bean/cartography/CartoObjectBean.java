package com.astrium.faceo.client.bean.cartography;

/*
 * @(#)CartoObjectBean.java	 1.0  09/2008
 *
 *
 * PROJET       : SITE FACEO
 *
 * LANGUAGE     : Java
 *
 * DESCRIPTION  : Cette classe est un conteneur d'informations 
 * pour les tous les objets
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

/**
 * <B>SITE FACEO</B> <BR>
 * 
 * <P>
 * Cette classe est un conteneur d'informations 
 * pour les tous les objets
 * g&eacute;or&eacute;f&eacute;renc&eacute;s sur la cartographie * </P>
 * </P>
 * 
 * @author  GR
 * @version 1.0, le 09/2008
 */
public class CartoObjectBean {

	/** object name on cartography */
	private String cartoName = null;

	/** object identifier on cartography */
	private int cartoId = 0;

	/** object type on cartography */
	private int cartoObjectType = 0;

	/** layer name which content object on cartography */
	private String cartoLayerName = null;

	/** hue HSB color parameter */
	private float hue = 0f;

	/** saturation HSB color parameter */
	private float saturation = 0f;

	/** brightness HSB color parameter */
	private float brightness = 0f;

	/** true if editable objet */
	private boolean editable = false;

	/** layer transparency */
	private double transparency = 50;

	/**
	 * d&eacute;but des m&eacute;thodes	 
	 * 
	 * Constructeur par d&eacute;faut : vide : convention Bean
	 */
	/**
	 * Default Constructor. The Default Constructor's explicit declaration
	 * is required for a serializable class. (GWT)
	*/
	public CartoObjectBean() {
	}

	/**
    * Cette m&eacute;thode est un constructeur de l'objet CartoObjectBean 
    * &agrave; partir de param&egrave;tres
    * 
    * @param _cartoName (String)		: nom de l'objet
    * @param _cartoId (int)				: identifiant de l'objet
    * @param _cartoObjectType (int)		: type de l'objet
    * @param _cartoLayerName (String)	: nom du layer qui contient l'objet
	* @param _hue (float)				: Hue color HSB parameter
	* @param _saturation (float)		: Saturation color HSB parameter
	* @param _brightness (float)		: Brightness color HSB parameter
    * @param _editable (boolean)		: true si l'objet est &eacute;ditable
    * @param _transparency (double)		: valeur de la transparence de 0 &agrave; 1
    * 									  0 : transparent, 1 : opaque
	*/
	public CartoObjectBean(String _cartoName, int _cartoId, 
			int _cartoObjectType, String _cartoLayerName,
			float _hue, float _saturation, float _brightness,
			boolean _editable, double _transparency ) {
		this.cartoName = _cartoName;
		this.cartoId = _cartoId;
		this.cartoObjectType = _cartoObjectType;
		this.cartoLayerName = _cartoLayerName;
		this.hue = _hue;
		this.saturation = _saturation;
		this.brightness = _brightness;
		this.editable = _editable;
		this.transparency = _transparency;
	}	
	
	/** ----------------------- getters ----------------------- */
	
	/**
	 * getter on cartoName
	 * 
	 * @return String : object name on cartography
	 */
	public String getCartoName() {
		return (this.cartoName != null) ? this.cartoName : "";
	}

	/**
	 * getter on cartoId
	 * 
	 * @return int : object identifier on cartography
	 */
	public int getCartoId() {
		return this.cartoId;
	}
	
	/**
	 * getter on cartoObjectType
	 * 
	 * @return int : object type on cartography
	 */
	public int getCartoObjectType() {
		return this.cartoObjectType;
	}

	/**
	 * getter on cartoLayerName
	 * 
	 * @return String : layer name which include the object on cartography
	 */
	public String getCartoLayerName() {
		return (this.cartoLayerName != null) ? this.cartoLayerName : "";
	}

	/**
	 * getter on hue
	 * 
	 * @return float : hue HSB color parameter
	 */
	public float getHue() {
		return this.hue;
	}

	/**
	 * getter on saturation
	 * 
	 * @return float : saturation HSB color parameter
	 */
	public float getSaturation() {
		return this.saturation;
	}

	/**
	 * getter on brightness
	 * 
	 * @return float : brightness HSB color parameter
	 */
	public float getBrightness() {
		return this.brightness;
	}

	/**
	 * getter on editable
	 * 
	 * @return boolean : true if object is editable
	 */
	public boolean getEditable() {
		return this.editable;
	}

	/**
	 * getter on transparency
	 * 
	 * @return double : transparency
	 */
	public double getTransparency() {
		return this.transparency;
	}

	/** ----------------------- setters ----------------------- */

	/**
	 * setter on cartoName
	 * 
	 * @param _cartoName (String) : object name on cartography
	 */
	public void setCartoName(String _cartoName) {
		this.cartoName = _cartoName;
	}
	
	/**
	 * setter on cartoId
	 * 
	 * @param _cartoId (int) : object identifier on cartography
	 */
	public void setCartoId(int _cartoId) {
		this.cartoId = _cartoId;
	}
	
	/**
	 * setter on cartoObjectType
	 * 
	 * @param _cartoObjectType (int) : object type on cartography
	 */
	public void setCartoObjectType(int _cartoObjectType) {
		this.cartoObjectType = _cartoObjectType;
	}

	/**
	 * setter on cartoLayerName
	 * 
	 * @param _cartoLayerName (String) : layer name which include the object on cartography
	 */
	public void setCartoLayerName(String _cartoLayerName) {
		this.cartoLayerName = _cartoLayerName;
	}

	/**
	 * setter on red
	 * 
	 * @param _hue (float) : hue HSB color parameter
	 */
	public void setHue(float _hue) {
		this.hue = _hue;
	}

	/**
	 * setter on saturation
	 * 
	 * @param _saturation (float) : saturation HSB color parameter
	 */
	public void setSaturation(float _saturation) {
		this.saturation = _saturation;
	}

	/**
	 * setter on saturation
	 * 
	 * @param _brightness (float) : brightness HSB color parameter
	 */
	public void setBrightness(float _brightness) {
		this.brightness = _brightness;
	}

	/**
	 * setter on editable
	 * 
	 * @param _editable (boolean) : true if object is editable
	 */
	public void setEditable(boolean _editable) {
		this.editable = _editable;
	}

	/**
	 * setter on transparency
	 * 
	 * @param _transparency (double) : 
	 */
	public void setTransparency(double _transparency) {
		this.transparency = _transparency;
	}

} // class
