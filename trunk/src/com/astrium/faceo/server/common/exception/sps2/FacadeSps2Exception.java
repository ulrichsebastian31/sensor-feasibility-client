package com.astrium.faceo.server.common.exception.sps2;

import java.util.HashMap;
import java.util.Map;


/**
 * <H1>SITE FACEO</H1>
 * <B>Description : </B>
 * <P>
 * Cette classe encapsule les exceptions lev&eacute;es dans la couche donn&eacute;ees en y
 * ajoutant un message et un code, ainsi que l'origine (classe : m&eacute;thode)
 * </P>
 * 
 * @author  Gl
 * @version 1.0, le 07/07/2010
 */

public class FacadeSps2Exception extends Exception {

	static final long serialVersionUID = 2;

	// liste des codes messages, a enrichir en fonction des besoins
	/** */
	public static final int SPS_ERR_UNDEFINED = 0;
	/** */
	public static final int SPS_NUMBER_FORMAT_EXCEPTION = 1;
	/** */
	public static final int SPS_EXCEPTION = 2;
	/** */
	public static final int SPS_AXIS_FAULT = 3;
	/** */
	public static final int SPS_INVALIDATE_DOCUMENT = 4;
	/** */
	public static final int SPS_XML_EXCEPTION = 5;
	/** */
	public static final int SPS_REMOTE_EXCEPTION = 6;
	/** */
	public static final int SPS_SERVICE_EXCEPTION_REPORT = 7;
	/** */
	public static final int SPS_IO_EXCEPTION = 8;
	/** */
	public static final int SPS_MALFORMED_EXCEPTION = 9;
	/** */
	public static final int SPS_SOCKET_TIMEOUT_EXCEPTION = 10;
	/** */
	public static final int SPS_DOM_PARSE_EXCEPTION = 11;
	/** */
	public static final int SPS_DOM_EXCEPTION = 12;
	/** */
	public static final int SPS_SIMULATION_FILE = 13;

	// liste des messages associes aux codes messages

	/** */
	public static Map<Integer, String> messagesCodes = null; // Stockage de l'unique instance de ces données.

	// Code de l'erreur
	private int code;

	// Classe et fonction d'ou provient l'erreur
	private String origine = null;

	// on autorise que 2 types de constructeurs

	/**
	 * Cr&eacute;e une nouvelle exception
	 * 
	 * @param _message
	 *            la raison de l'exception
	 * @param _code
	 *            le code correspondant a l'exception
	 * @param _origine
	 *            origine de l'exception
	 */
	public FacadeSps2Exception(String _message, int _code, String _origine) {
		super(_message);
		this.code = _code;
		this.origine = _origine;
	}

	/**
	 * Cr&eacute;e une nouvelle exception
	 * 
	 * @param _message
	 *            la raison de l'exception
	 * @param _origine
	 *            origine de l'exception
	 */
	public FacadeSps2Exception(String _message, String _origine) {
		super(_message);
		this.code = SPS_ERR_UNDEFINED;
		this.origine = _origine;
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
	 * set the messages exceptions
	 */
	private static synchronized void setMessages() {

		if (messagesCodes == null) {
			messagesCodes = new HashMap<Integer, String>();
			
			messagesCodes.put(SPS_ERR_UNDEFINED, "Not defined error");
			messagesCodes.put(SPS_NUMBER_FORMAT_EXCEPTION, "Number format exception");
			messagesCodes.put(SPS_EXCEPTION, "exception");
			messagesCodes.put(SPS_AXIS_FAULT, "axis fault");
			messagesCodes.put(SPS_INVALIDATE_DOCUMENT, "invalidate document");
			messagesCodes.put(SPS_XML_EXCEPTION, "XML exception");
			messagesCodes.put(SPS_REMOTE_EXCEPTION, "remote exception");
			messagesCodes.put(SPS_SERVICE_EXCEPTION_REPORT, "service report exception");
			messagesCodes.put(SPS_IO_EXCEPTION, "IO exception");
			messagesCodes.put(SPS_MALFORMED_EXCEPTION, "malformed exception");
			messagesCodes.put(SPS_SOCKET_TIMEOUT_EXCEPTION, "socket timeout exception");
			messagesCodes.put(SPS_DOM_PARSE_EXCEPTION, "DOM parse exception");
			messagesCodes.put(SPS_DOM_EXCEPTION, "DOM exception");
			messagesCodes.put(SPS_SIMULATION_FILE, "simulation file exception");
		} // if (messagesCodes == null) {

	} // private static synchronized void setMessages() { 
	
	/**
	 * R&eacute;cup&egrave;re le message de l'exception
	 * 
	 * @return le message de l'exception
	 */
	public String getMessage() {

		String sCode = null;
		String sOrigine = null;
		
		setMessages();

			if (this.code == SPS_ERR_UNDEFINED)
				sCode = "";
			else
				if (messagesCodes.containsKey(this.code)) {
					sCode = " C=" + messagesCodes.get(getCode());
				} // if

			if (getOrigine() == null)
				sOrigine = "";
			else
				sOrigine = " O=" + getOrigine();

			return super.getMessage() + sCode + sOrigine;

	} // public String getMessage() {

	/**
	 * R&eacute;cup&egrave;re l'origine de l'exception
	 * 
	 * @return l'origine de l'exception
	 */
	public String getOrigine() {
		return this.origine;
	}

	/**
	 * R&eacute;cup&egrave;re le message associ&eacute; &agrave; l'exception sans tenir compte du code et de
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