package com.astrium.faceo.client.service;

/*
 * @(#)FacadeUtil.java	 1.0  23/09/2009
 *
 *
 * PROJET       : FACEO
 *
 * LANGUAGE     : Java
 *
 * DESCRIPTION  : La classe FacadeUtil offre des m&eacute;thodes
 * permettant de g&eacute;rer differetes fonctions
 *
 *
 * CREATION :
 * ---------------------------------------------------------------------
 * | Date       | Version | Description                                |
 * ---------------------------------------------------------------------
 * | 23/09/2009 |    1.0  |                                            |
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
 * La classe FacadeUtil offre des m&eacute;thodes
 * permettant de gerer differentes fonctions
 * </P>
 * </P>
 * 
 * @author NF 23/09/2009
 */
public class FacadeUtil {

	/**
	 * constructor
	 */
	public FacadeUtil() {
	}

	// ---------------------- Utils Objects ----------------------

	/**
	 * M&eacute;thode permettant de recuperer le user agent du navigateur
	 * 
	 * @return String : user agent
	 *  
	 */
	public static native String getUserAgent() /*-{
    	return navigator.userAgent.toString();
	}-*/;

	/**
	 * M&eacute;thode permettant de tester le user agent du navigateur
	 * 
	 * @return boolean : true si MSIE 6.x
	 */        
	public static boolean isMSIE6() {
		return (getUserAgent().indexOf("MSIE 6.")!=-1);
	} //

	/**
	 * M&eacute;thode permettant de tester le user agent du navigateur
	 * 
	 * @return boolean : true si MSIE 7.x 
	 */        
	public static boolean isMSIE7() {
		return (getUserAgent().indexOf("MSIE 7.")!=-1);
	} //  

	/**
	 * M&eacute;thode permettant de tester le user agent du navigateur
	 * 
	 * @return boolean : true si MSIE 8.x 
	 */        
	public static boolean isMSIE8() {
		return (getUserAgent().indexOf("MSIE 8.")!=-1);
	} //   

	/**
	 * M&eacute;thode permettant de tester le user agent du navigateur
	 * 
	 * @return boolean : true si Gecko
	 */        
	public static boolean isGecko() {
		return (getUserAgent().indexOf("Gecko")!=-1)&&(!isSafari());
	} // 
	
	/**
	 * M&eacute;thode permettant de retourner la version du navigateur Firefox
	 * 
	 * @return String
	 */
	public static String versionGecko() {
		int index=getUserAgent().lastIndexOf("Firefox/");
		String[] str=getUserAgent().substring(index).split(" ");
		return(str[0]);
	}

	/**
	 * M&eacute;thode permettant de tester le user agent du navigateur
	 * 
	 * @return boolean : true si Safari
	 */        
	public static boolean isSafari() {
		return (getUserAgent().indexOf("AppleWebKit")!=-1)&&(!isChrome());
	} // 

	/**
	 * M&eacute;thode permettant de tester le user agent du navigateur
	 * 
	 * @return boolean : true si Chrome
	 */        
	public static boolean isChrome() {
		return (getUserAgent().indexOf("Chrome")!=-1);
	} //    
	
} // Class
