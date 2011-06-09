package com.astrium.faceo.server.bean.exception;

/**
 * <H1>SITE FACEO</H1>
 * <B>Description : </B>
 * <P>
 * Cette classe encapsule les exceptions lev&eacute;es dans la couche donn&eacute;ees en y
 * ajoutant un message et un code, ainsi que l'origine (classe : m&eacute;thode)
 * </P>
 * 
 * @author  ASTRIUM
 * @version 1.0, le 14/10/2008
 */

public class TaskException extends Exception {
	   
	/**
	 * 
	 */
	private static final long serialVersionUID = -5482987354662532743L;

	// liste des codes messages, a enrichir en fonction des besoins
	/** undefined error */
	public static final int ERR_UNDEFINED = 0;

	/** get database error */
	public static final int ERR_GET_DATABASE = 1;

	/** load SQL error */
	public static final int ERR_LOAD_SQL = 2;

	/** load connexion manager error */
	public static final int ERR_LOAD_CONN = 3;

	/** close connexion error */
	public static final int ERR_FERMER_CONN = 4;

	/** get connexion error */
	public static final int ERR_GET_CONN = 5;

	/** SQL transaction error */
	public static final int ERR_TRANSACTION_SQL = 6;

	/** SQL insert error */
	public static final int ERR_INSERT_SQL = 7;

	/** SQL insert connexion manager error */
	public static final int ERR_INSERT_CONN_MANAGER = 8;

	/** SQL delete error */
	public static final int ERR_DELETE_SQL = 9;

	/** SQL delete connexion manager error */
	public static final int ERR_DELETE_CONN_MANAGER = 10;

	/** SQL update error */
	public static final int ERR_UPDATE_SQL = 11;

	/** SQL update connexion manager error */
	public static final int ERR_UPDATE_CONN_MANAGER = 12;

	/** SQL request error */
	public static final int ERR_VALORISATION_REQUETE = 13;

	/** SQL id error */
	public static final int ERR_CLE_ABSENTE = 14;

	// liste des messages associes aux codes messages
	/**
	 * 
	 */
	public static final String[] MessagesCodes = { "Erreur non definie",
			"Impossible de recuperer une reference sur la base de donnees",
			"Probleme de gestionnaire de connexion lors d'une consultation",
			"Probleme a la fermeture d'une connexion",
			"Probleme d'obtention de connexion",
			"Probleme SQL sur une transaction",
			"Probleme SQL lors d'une insertion",
			"Probleme de gestionnaire de connexion lors d'une insertion",
			"Probleme SQL lors d'une suppression",
			"Probleme de gestionnaire de connexion lors d'une suppression",
			"Probleme SQL lors d'une mise a jour",
			"Probleme de gestionnaire de connexion lors d'une mise a jour",
			"Probleme lors de la valorisation des parametres d'une requete",
			"Probleme : la clef n'est pas valorisee" };

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
	public TaskException(String msg, int code, String origine) {
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
	public TaskException(String msg, String origine) {
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
	 * R&eacute;cup&egrave;re le message associ&eacute; &agrave; l'exception sans tenir compte du code et de
	 * l'origine
	 * 
	 * @return le message simple
	 */
	public String getSimpleMessage() {
		return super.getMessage();
	}

}