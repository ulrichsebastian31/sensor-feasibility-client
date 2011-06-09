package com.astrium.faceo.client.common.cartoAoi;

/*
 * @(#)CartoAoiConstants.java	 1.0  04/05/2010
 *
 *
 * PROJET       : FACEO
 *
 * LANGUAGE     : Java
 *
 * DESCRIPTION  : La classe CartoAoiConstants gere les constantes 'aoi' du site
 *
 *
 * CREATION :
 * ---------------------------------------------------------------------
 * | Date       | Version | Description                                |
 * ---------------------------------------------------------------------
 * | 04/05/2010 |    1.0  |                                            |
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
 * La classe CartoAoiConstants g&eagrave;re les constantes utilis&eacute;es par le site
 * </P>
 * </P>
 * 
 * @author GR
 * @version 1.0, le 04/05/2010
 */
public interface CartoAoiConstants extends Constants {
		
	/* ---------------------- AOI ---------------------- */

	/**
	 * Translated "AOI".
	 * 
	 * @return translated "AOI"
	*/
	@Key("aoi")
	String aoi();
	
	/**
	 * Translated "Upper".
	 * 
	 * @return translated "Upper"
	*/
	@Key("aoiUpper")
	String aoiUpper();
	
	/**
	/**
	 * Translated "left upper".
	 * 
	 * @return translated "left upper"
	*/
	@Key("aoiLeftUpper")
	String aoiLeftUpper();
	
	/**
	 * Translated "right upper".
	 * 
	 * @return translated "right upper"
	*/
	@Key("aoiRightUpper")
	String aoiRightUpper();
	
	/**
	 * Translated "Lower".
	 * 
	 * @return translated "Lower"
	*/
	@Key("aoiLower")
	String aoiLower();
	
	/**
	 * Translated "left lower".
	 * 
	 * @return translated "left lower"
	*/
	@Key("aoiLeftLower")
	String aoiLeftLower();

	/**
	 * Translated "right lower".
	 * 
	 * @return translated "right lower"
	*/
	@Key("aoiRightLower")
	String aoiRightLower();

	/**
	 * Translated "New AOI".
	 * 
	 * @return translated "New AOI"
	*/
	@Key("newAOI")
	String newAOI();
	
	/**
	 * Translated "Use mouse to create an area of interest on cartography".
	 * 
	 * @return translated "Use mouse to create an area of interest on cartography"
	*/
	@Key("newAOITip")
	String newAOITip();

	/**
	 * Translated "onCartography".
	 * 
	 * @return translated "onCartography"
	*/
	@Key("onCartography")
	String onCartography();

	/**
	 * Translated "Draw AOI".
	 * 
	 * @return translated "Draw AOI"
	*/
	@Key("drawAOI")
	String drawAOI();
	
	/**
	 * Translated "Display AOI".
	 * 
	 * @return translated "Display AOI"
	*/
	@Key("displayAOI")
	String displayAOI();
	
	/**
	 * Translated "Display".
	 * 
	 * @return translated "Display"
	*/
	@Key("display")
	String display();
	
	/**
	 * Translated "current AOI".
	 * 
	 * @return translated "current AOI"
	*/
	@Key("currentAOI")
	String currentAOI();


}
