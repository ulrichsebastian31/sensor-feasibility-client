package com.astrium.faceo.client.bean.cartography;

/*
 * @(#)CartoToponymBean.java	 1.0  10/2008
 *
 *
 * PROJET       : SITE FACEO
 *
 * LANGUAGE     : Java
 *
 * DESCRIPTION  : Cette classe est un conteneur d'informations 
 * pour les objets de type Toponymes
 * g&eacute;or&eacute;f&eacute;renc&eacute;s sur la cartographie
 *
 * CREATION :
 * ---------------------------------------------------------------------
 * | Date       | Version | Description                                |
 * ---------------------------------------------------------------------
 * | 10/2008 |    1.0  |                                            |
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
import java.io.Serializable;

/**
 * <B>SITE FACEO</B> <BR>
 * 
 * <P>
 * Cette classe est un conteneur d'informations 
 * pour les objets de type Toponymes
 * g&eacute;or&eacute;f&eacute;renc&eacute;s sur la cartographie * </P>
 * </P>
 * 
 * @author  GR
 * @version 1.0, le 10/2008
 */
public class CartoToponymBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5620094580290543450L;

	/** errorMessage */
	private String errorMessage = null;
	
	/** errorCode */
	private int errorCode = 0;
	
	/** _idCartography 
	* identifiant de l'objet Toponym par l'applet WWJ
	* lorqu'il est cr&eacute;sent&eacute;e
	* sur la cartographie de type World Wind Java
	*/
	private int idCartography = 0;
	
	// precision
	private String precision = null;

	// precision
	private String warning = null;

	// Latitude
	private float latitude = 0;
	
	// Longitude
	private float longitude = 0;
	
	// Address
	private String address = null;

	// City
	private String city = null;

	// State
	private String state = null;

	// Zip
	private String zip = null;

	// Country
	private String country = null;

	/**
	 * d&eacute;but des m&eacute;thodes	 
	 * 
	 * Constructeur par d&eacute;faut : vide : convention Bean
	 */
	/**
	 * Default Constructor. The Default Constructor's explicit declaration
	 * is required for a serializable class. (GWT)
	*/
	public CartoToponymBean() {
	}

	/** ----------------------- getters ----------------------- */

	/** 
	 * getter on errorMessage
	 * 
	 * @return String : error Message
	*/
	public String getErrorMessage() {
		return (this.errorMessage != null) ? this.errorMessage : "";
	}

	/** 
	 * getter on errorCode
	 * 
	 * @return String : error Code
	*/
	public int getErrorCode() {
		return this.errorCode;
	}

	/** 
	 * getter on idCartography
	 * 
	 * @return int : label cartography identifier
	*/
	public int getIdCartography() {
		return this.idCartography;
	}

	/** 
	 * getter on precision
	 * 
	 * @return String : precision value
	*/
	public String getPrecision() {
		return (this.precision != null) ? this.precision : "";
	}

	/** 
	 * getter on warning
	 * 
	 * @return String : warning value
	*/
	public String getWarning() {
		return (warning != null) ? warning : "";
	}

	/** 
	 * getter on latitude
	 * 
	 * @return float : latitude value
	*/
	public float getLatitude() {
		return this.latitude;
	}

	/** 
	 * getter on longitude
	 * 
	 * @return float : longitude value
	*/
	public float getLongitude() {
		return this.longitude;
	}

	/** 
	 * getter on address
	 * 
	 * @return String : address value
	*/
	public String getAddress() {
		return (this.address != null) ? this.address : "";
	}

	/** 
	 * getter on city
	 * 
	 * @return String : city value
	*/
	public String getCity() {
		return (this.city != null) ? this.city : "";
	}

	/** 
	 * getter on state
	 * 
	 * @return String : state value
	*/
	public String getState() {
		return (this.state != null) ? this.state : "";
	}

	/** 
	 * getter on zip
	 * 
	 * @return String : zip value
	*/
	public String getZip() {
		return (this.zip != null) ? this.zip : "";
	}

	/** 
	 * getter on country
	 * 
	 * @return String : country value
	*/
	public String getCountry() {
		return (this.country != null) ? this.country : "";
	}

	/** ----------------------- setters ----------------------- */
	
	/** 
	 * setter on idCartography
	 * 
	 * @param _idCartography (int) : label cartography identifier
	*/
	public void setIdCartography(int _idCartography) {
		this.idCartography = _idCartography;
	}

	/** 
	 * setter on precision
	 * 
	 * @param _precision (String) : precision value
	*/
	public void setPrecision(String _precision) {
		this.precision = _precision;
	}

	/** 
	 * setter on warning
	 * 
	 * @param _warning (String) : warning value
	*/
	public void setWarning(String _warning) {
		this.warning = _warning;
	}

	/** 
	 * setter on latitude
	 * 
	 * @param _latitude (float) : latitude value
	*/
	public void setLatitude(float _latitude) {
		this.latitude = _latitude;
	}

	/** 
	 * setter on longitude
	 * 
	 * @param _longitude (float) : longitude value
	*/
	public void setLongitude(float _longitude) {
		this.longitude = _longitude;
	}

	/** 
	 * setter on address
	 * 
	 * @param _address (String) : address value
	*/
	public void setAddress(String _address) {
		this.address = _address;
	}

	/** 
	 * setter on city
	 * 
	 * @param _city (String) : city value
	*/
	public void setCity(String _city) {
		this.city = _city;
	}

	/** 
	 * setter on state
	 * 
	 * @param _state (String) : state value
	*/
	public void setState(String _state) {
		this.state = _state;
	}

	/** 
	 * setter on zip
	 * 
	 * @param _zip (String) : zip value
	*/
	public void setZip(String _zip) {
		this.zip = _zip;
	}

	/** 
	 * setter on country
	 * 
	 * @param _country (String) : country value
	*/
	public void setCountry(String _country) {
		this.country = _country;
	}

	/** 
	 * setter on errorMessage
	 * 
	 * @param _errorMessage (String) : error Message
	*/
	public void setErrorMessage(String _errorMessage) {
		this.errorMessage = _errorMessage;
	}

	/** 
	 * setter on errorCode
	 * 
	 * @param _errorCode (int) : error Code
	*/
	public void setErrorCode(int _errorCode) {
		this.errorCode = _errorCode;
	}

} // class
