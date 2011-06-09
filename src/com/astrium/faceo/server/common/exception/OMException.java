package com.astrium.faceo.server.common.exception;

/*
*
* @(#)OMException.java	 1.0  14/05/2008
*
*
* PROJET       : SITE faceo
*
* LANGUAGE     : Java
*
* DESCRIPTION  : Cette classe encapsule les exceptions levees dans la couche metier en y
 * ajoutant un message et un code, ainsi que l'origine (classe : methode)
*
* CREATION :
* ---------------------------------------------------------------------
* | Date       | Version | Description                                |
* ---------------------------------------------------------------------
* | 14/05/2008 |    1.0  |                                            |
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

//import
/**
 * <H1>SITE faceo</H1>
 * <B>Description : </B>
 * <P>
 * Cette classe encapsule les exceptions lev&eacute;es dans la couche m&eacute;tier en y
 * ajoutant un message et un code, ainsi que l'origine (classe : methode)
 * </P>
 * 
 * @author  GR
 * @version 1.0, le 14/05/2008
 */

public class OMException extends Exception {
    
	static final long serialVersionUID = 0;
	   
	// liste des codes messages, a enrichir en fonction des besoins
	/** */
	public static final int ERR_UNDEFINED = 0;

	/** */
	public static final int ERR_ROLLBACK = 1;

	/** */
	public static final int ERR_CREER_IF_MQ = 2;

	/** */
	public static final int ERR_POSTER_MQ = 3;

	/** */
	public static final int ERR_COMMIT_MQ = 4;

	/** */
	public static final int ERR_IO = 5;
	
	/** */
	public static final int ERR_DELETE = 13;
	
	/** */
	public static final int ERR_LOAD = 14;
	
	/** */
	public static final int ERR_INSERT = 15;
	
	/** */
	public static final int ERR_UPDATE = 16;

	// liste des messages associes aux codes messages
	/** */
	public static final String[] MessagesCodes = { "Erreur non definie",
			"Pb lors du rollback MQ ou de la transaction",
			"Pb lors de la creation de l'interface MQ",
			"Pb lors du postage du message dans MQ",
			"Pb lors du commit dans MQ", "Erreur IO", 
			"ERR_DELETE", "ERR_LOAD", "ERR_INSERT", "ERR_UPDATE" };

	// Code de l'erreur
	private int code;

	// Classe et fonction d'ou provient l'erreur
	private String origine = null;

	// on autorise que 2 types de constructeurs

	/**
	 * Cr&eacute;e une nouvelle exception
	 * 
	 * @param msg
	 *            la raison de l'exception
	 * @param code
	 *            le code correspondant a l'exception
	 * @param origine
	 *            origine de l'exception
	 */
	public OMException(String msg, int code, String origine) {
		super(msg);
		this.code = code;
		this.origine = origine;
	}

	/**
	 * Cr&eacute;e une nouvelle exception
	 * 
	 * @param msg
	 *            la raison de l'exception
	 * @param origine
	 *            origine de l'exception
	 */
	public OMException(String msg, String origine) {
		super(msg);
		this.code = ERR_UNDEFINED;
		this.origine = origine;
	}

	/***************************************************************************
	 * M&eacute;thodes de consultation des informations de l'exception
	 **************************************************************************/
	/**
	 * R&eacute;cup&egrave;re le code associ&eacute; &agrave; l'exception
	 * 
	 * @return le code de l'exception
	 */
	public int getCode() {
		return code;
	}

	/**
	 * R&eacute;cup&egrave;re le message de l'exception
	 * 
	 * @return le message de l'exception
	 */
	public String getMessage() {
		String sCode = null;
		String sOrigine = null;

		if (this.code == ERR_UNDEFINED)
			sCode = "";
		else
			sCode = " C=" + MessagesCodes[getCode()];

		if (getOrigine() == null)
			sOrigine = "";
		else
			sOrigine = " O=" + getOrigine();

		return super.getMessage() + sCode + sOrigine;
	}

	/**
	 * R&eacute;cup&egrave;re l'origine de l'exception
	 * 
	 * @return l'origine de l'exception
	 */
	public String getOrigine() {
		return this.origine;
	}

	/**
	 * R&eacute;cup&egrave;re le message associ&eacute; &agrave;a l'exception sans tenir compte du code et de
	 * l'origine
	 * 
	 * @return le message simple
	 */
	public String getSimpleMessage() {
		return super.getMessage();
	}

//	/**
//	 * Dumper la pile
//	 */
//	public void printStackTrace() {
//		System.err.println(getMessage());
//		super.printStackTrace();
//	}
}