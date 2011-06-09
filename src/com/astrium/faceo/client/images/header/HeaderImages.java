package com.astrium.faceo.client.images.header;

/*
 * @(#)HeaderImages.java	 1.0  22/02/2010
 *
 *
 * PROJET       : FACEO
 *
 * LANGUAGE     : Java
 *
 * DESCRIPTION  : The class HeaderImages manages the header's images
 *
 *
 * CREATION :
 * ---------------------------------------------------------------------
 * | Date       | Version | Description                                |
 * ---------------------------------------------------------------------
 * | 22/02/2010 |    1.0  |                                            |
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


import com.astrium.faceo.client.common.ImagesConstant;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/**
 * <B>FACEO</B> <BR>
 * 
 * <P>
 * The class HeaderImages manages the header's images
 * </P>
 * </P>
 * 
 * @author GR
 * @version 1.0, le 22/02/2010
 */
public interface HeaderImages extends ClientBundle {

	/**
	 * @return ImageResource : Astrium logo
	 */
	@Source(ImagesConstant.ASTRIUM_LOGO)
	ImageResource astriumLogo();
	
	/**
	 * @return ImageResource : application logo
	 */
	@Source(ImagesConstant.APPLI_LOGO)
	ImageResource appliLogo();
	
} // class
