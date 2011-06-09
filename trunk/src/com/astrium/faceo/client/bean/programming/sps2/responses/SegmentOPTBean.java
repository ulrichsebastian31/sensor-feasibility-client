package com.astrium.faceo.client.bean.programming.sps2.responses;/* * @(#)SegmentOPTBean.java	 1.0  21/05/2010 * * * PROJET       : SITE FACEO * * LANGUAGE     : Java * * DESCRIPTION  : Cette classe est un conteneur d'informations pour les r&eacute;sultats  * de l'op&eacute;ration 'GetFeasibility', 'Submit', 'reserve' or 'Update' * * CREATION : * --------------------------------------------------------------------- * | Date       | Version | Description                                | * --------------------------------------------------------------------- * | 21/05/2010 |    1.0  |                                            | * --------------------------------------------------------------------- * * MODIFICATION : * --------------------------------------------------------------------- * | Date       | Version | Description                                | * --------------------------------------------------------------------- * |            |         |                                            | * --------------------------------------------------------------------- * */// importimport java.io.Serializable;/** * <B>SITE FACEO</B> <BR> *  * <P> * Cette classe est un conteneur d'informations pour les r&eacute;sultats * de l'op&eacute;ration 'GetFeasibility', 'Submit', 'reserve' or 'Update' * </P> * </P> *  * @author  GR * @version 1.0, le 21/05/2010 */public class SegmentOPTBean extends SegmentBean implements Serializable {	/**	 * 	 */	private static final long serialVersionUID = 5766591378588716377L;	/** luminosity */	private float luminosity = 0;	/**	 * d&eacute;but des m&eacute;thodes	 	 * 	 * Constructeur par d&eacute;faut : vide : convention Bean	 */	/**	 * Default Constructor. The Default Constructor's explicit declaration	 * is required for a serializable class. (GWT)	 */	public SegmentOPTBean() {	}	/** ----------------------- getters ----------------------- */	/** 	 * getter on luminosity	 * 	 * @return Double : luminosity	 */	public float getLuminosity() {		return this.luminosity;	}	/** ----------------------- setters ----------------------- */	/** 	 * setter on luminosity	 * 	 * @param _luminosity (float) : luminosity value	 */	public void setLuminosity(float _luminosity) {		this.luminosity = _luminosity;	}}  // class