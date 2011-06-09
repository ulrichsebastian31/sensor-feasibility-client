package com.astrium.faceo.client.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.astrium.faceo.client.bean.cartography.CartoObjectBean;
import com.astrium.faceo.client.common.GeneralConstant;

/**
 * <B>SITE FACEO</B> <BR>
 * 
 * <P>
 * La classe HomePageBean g&egrave;re les objets de la page d'accueil
 * </P>
 * </P>
 * 
 * @author ASTRIUM
 * @version 1.0, le 31/10/2008
 */
public class HomePageBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7955060388389524298L;
	
	// configuration de l'application
	private ConfigBean configBean;

	/**
	 * Mode de s&eacute;lection des objets de la cartographie
	 * 		"1" : s&eacute;lection par un simple clic
	 * 		"2" : s&eacute;lection par un survol de l'objet
	 */
	private String selectionMode = GeneralConstant.CARTO_OBJECTS_CLIC_MODE_SELECTION;

	private String navigatorPlatform = "";

	private String jreVersion = "";

	// identifiant de l'objet courant selectionne dans la cartographie
	private int idCurrentCartoObject = -1;

	// identifiant de la zone d'interet dans la cartographie
	private int idAOICatalog = 0;

	// initialisation de la liste des layers de la cartographie
	// vaut true si la liste a ete initialisee
	private boolean initCartoLayers = false;

	// initialisation de la fenetre 'AOI'
	private boolean setAoiWindowOpenOnce = false;
	
	/** ********************* Cartography Objects ********************* */ 
	/**
	 * Map object with identifier
	 * liste des identifiants des objets affich&eacute;s dans la carto World Wind Java
	 * AOI (Rectangle), Emprise des produits (polygone ou multipolygone GML)
	 */
	private Map<Integer, CartoObjectBean> cartographyObjects = 
		new HashMap<Integer, CartoObjectBean>();

	/**
	 * debut des methodes	 
	 * 
	 * Constructeur par defaut : vide
	 */
	public HomePageBean() {
	}

	/** --------------- getters --------------- */

	/**
	 * M&eacute;thode retournant la configuration de l'application
	 * 
	 * @return ConfigBean	: la configuration de l'application
	 */
	public ConfigBean getConfigBean() {
		return this.configBean;
	}

	/**
	 * M&eacute;thode retournant 
	 * le mode de s&eacute;lection des objets de la cartographie
	 * 		"1" : s&eacute;lection par un simple clic
	 * 		"2" : s&eacute;lection par un survol de l'objet
	 * 
	 * @return String	: le mode de s&eacute;lection des objets de la cartographie
	 */
	public String getSelectionMode() {
		return this.selectionMode;
	}

	/**
	 * 
	 * @return String :
	 */
	public String getNavigatorPlatform() {
		return this.navigatorPlatform;
	}

	/**
	 * getter on jreVersion
	 * 
	 * @return String : the JRE Version
	 */
	public String getJreVersion() {
		return this.jreVersion;
	}

	/**
	 * M&eacute;thode retournant l'identifiant 
	 * de la zone d'interet (AOI) dans la cartographie
	 * 
	 * @return int	: l'identifiant
	 */
	public int getIdAOICatalog() {
		return this.idAOICatalog;
	}

	/**
	 * M&eacute;thode retournant l'identifiant 
	 * de l'objet courant selectionne dans la cartographie
	 * 
	 * @return int	: l'identifiant
	 */
	public int getIdCurrentCartoObject() {
		return this.idCurrentCartoObject;
	}

	/**
	 * M&eacute;thode retournant l'&eacute;tat de 
	 * l'initialisation de la liste des layers de la cartographie
	 * vaut true si la liste a &eacute;t&eacute; initialis&eacute;e
	 * 
	 * @return boolean : &eacute;tat de l'initialisation de la liste des layers de la cartographie
	 */
	public boolean getInitCartoLayers() {
		return this.initCartoLayers;
	}

	/**
	 * M&eacute;thode retournant l'&eacute;tat
	 * de l'initialisation de la fen&ecirc;tre AOI
	 * 
	 * @return boolean : true if the window has been already open
	 */
	public boolean getAoiWindowOpenOnce() {
		return this.setAoiWindowOpenOnce;
	}

	/** ********************* Cartography Objects ********************* */ 
	/**
	 * M&eacute;thode retournant la HashMap
	 * des objets de la repr&eacute;sentation 3D World Wind de la Terre
	 * 
	 * @return Map<Integer, CartoObjectBean>	: la HashMap
	 */
	public Map<Integer, CartoObjectBean> getCartographyObjects() {
		return this.cartographyObjects;
	}

	/** --------------- setters --------------- */

	/**
	 * setter on configBean
	 * 
	 * @param _configBean (configBean)	: application configuration
	 */
	public void setConfigBean(ConfigBean _configBean) {
		this.configBean = _configBean;
	}

	/**
	 * M&eacute;thode mettant &agrave jour le mode de s&eacute;lection des objets de la cartographie
	 * 		"1" : s&eacute;lection par un simple clic
	 * 		"2" : s&eacute;lection par un survol de l'objet
	 * 
	 * @param _selectionMode (String) : le mode de s&eacute;lection des objets de la cartographie
	 */
	public void setSelectionMode(String _selectionMode) {
		this.selectionMode = _selectionMode;
	}

	/**
	 * setter on navigatorPlatform 
	 * 
	 * @param _navigatorPlatform (String) : the navigator platform
	 */
	public void setNavigatorPlatform(String _navigatorPlatform) {
 		this.navigatorPlatform = _navigatorPlatform;
	}

	/**
	 * setter on jreVersion
	 * 
	 * @param _jreVersion (String) : the JRE Version value
	 */
	public void setJreVersion(String _jreVersion) {
		this.jreVersion = _jreVersion;
	}

	/**
	 * M&eacute;thode permettant la mise &agrave; jour
	 * de l'identifiant de l'objet courant selectionne dans la cartographie
	 * 
	 * @param _idCurrentCartoObject (int) : identifiant pour la cartographie (World Wind Java)
	 */
	public void setIdCurrentCartoObject(int _idCurrentCartoObject) {
		this.idCurrentCartoObject = _idCurrentCartoObject;
	}

	/**
	 * M&eacute;thode permettant la mise &agrave; jour
	 * de l'identifiant de la zone d'int&eacute;r&ecirc;t dans la cartographie
	 * 
	 * @param _idAOICatalog (int) : l'identifiant de la zone d'int&eacute;r&ecirc;t dans la cartographie
	 */
	public void setIdAOICatalog(int _idAOICatalog) {
		this.idAOICatalog = _idAOICatalog;
	}

	/**
	 * M&eacute;thode permettant la mise &agrave; jour
	 * de l'&eacute;tat de l'initialisation de la liste des layers de la cartographie
	 * vaut true si la liste a ete initialisee
	 * 
	 * @param _initCartoLayers (boolean) : &eacute;tat de l'initialisation de la liste des layers de la cartographie
	 */
	public void setInitCartoLayers(boolean _initCartoLayers) {
		this.initCartoLayers = _initCartoLayers;
	}
	
	/**
	 * M&eacute;thode permettant la mise &agrave; jour
	 * de l'initialisation de la fen&ecirc;tre AOI
	 * 
	 * @param _aoiWindowOpenOnce (boolean) : true if the window has been already open
	 */
	public void setAoiWindowOpenOnce(boolean _aoiWindowOpenOnce) {
		this.setAoiWindowOpenOnce = _aoiWindowOpenOnce;
	}
	
	/** ********************* Cartography Objects ********************* */ 
	/**
	 * M&eacute;thode permettant la mise &agrave; jour de la HashMap
	 * des objets de la repr&eacute;sentation 3D World Wind de la Terre
	 * 
	 * @param _cartographyObjects (Map<Integer, CartoObjectBean>)	: cartographyObjects's HashMap
	 */
	public void setCartographyObjects(
			Map<Integer, CartoObjectBean> _cartographyObjects) {
		this.cartographyObjects = _cartographyObjects;
	}

	/** ********************* Cartography Objects ********************* */ 
	/**
	 * M&eacute;thode permettant l'ajout d'un objet dans la Hashmap
	 * des objets de la repr&eacute;sentation 3D World Wind de la Terre
	 * 
	 * @param _id (int) : identifiant de l'objet ajout&eacute; dans la repr&eacute;sentation World Wind
	 * @param _cartographyObjects (CartoObjectBean) : cartography's objects
	 */
	public void setContentCartographyObjects(int _id, CartoObjectBean _cartographyObjects) {
		this.cartographyObjects.put(_id, _cartographyObjects);
	} // public static void setContentCartographyObjects(int _id, CartoObjectBean _cartographyObjects) {

	
} // class
