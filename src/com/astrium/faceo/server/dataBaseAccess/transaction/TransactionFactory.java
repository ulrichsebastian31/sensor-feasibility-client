package com.astrium.faceo.server.dataBaseAccess.transaction;

/*
 * @(#)TransactionFactory.java	 1.0  14/10/2008
 *
 *
 * PROJET       : SITE FACEO
 *
 * LANGUAGE     : Java
 *
 * DESCRIPTION  : Cette classe est une factory permettant de creer des transactions et de les
 * stocker lorsquelles sont encore valides. On l'utilise en faisant un
 * getInstance (singleton) qui renvoie une instance de la classe qui permet de
 * creer, de detruire ou de recuperer une transaction. Chaque transaction est
 * identifiee de facon unique par un long.
 *
 * CREATION :
 * ---------------------------------------------------------------------
 * | Date       | Version | Description                                |
 * ---------------------------------------------------------------------
 * | 14/10/2008 |    1.0  |                                            |
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
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.astrium.faceo.server.bean.exception.DBBeanException;

/**
 * <H1>SITE FACEO</H1>
 * <B>Description : </B>
 * <P>
 * Cette classe est une factory permettant de cr&eacute;er des transactions et de les
 * stocker lorsquelles sont encore valides. On l'utilise en faisant un
 * getInstance (singleton) qui renvoie une instance de la classe qui permet de
 * cr&eacute;er, de d&eacute;truire ou de r&eacute;cup&eacute;rer une transaction. Chaque transaction est
 * identifi&eacute;e de facon unique par un long.
 * </P>
 * 
 * @author  ASTRIUM  
 * @version 1.0, le 14/10/2008
 */

public class TransactionFactory {

	// place de la prochaine transaction creee dans la Map
	private static long idCounter = 1;

	// Structure stockant des transactions (instance de TransactionDB)
	private static Map<Long, TransactionDB> lesTransactions = null;

	// reference sur l'unique reference de la classe (voir design patterns
	// singleton)
	private static TransactionFactory factoryInstance = null;

	// constructeur privee appele par getInstance()
	private TransactionFactory() {
		// on initialise la structure de stockage et le compteur associe
		idCounter = 1;
		lesTransactions = new HashMap<Long, TransactionDB>();
	}

	/**
	 * Renvoie une reference sur l'unique instance de la classe
	 * si l'instance n'existe pas, on utilise le constructeur pour la creee
	 * 
	 * @return TransactionFactory
	 */
	public static synchronized TransactionFactory getInstance() {
		if (factoryInstance == null) {
			factoryInstance = new TransactionFactory();
		}
		return factoryInstance;
	}

	/**
	 * Cette m&eacute;thode statique permet de cr&eacute;er une transaction. Elle la rajoute
	 * ensuite dans son pool de transactions en lui associant un num&eacute;ro
	 * 
	 * @return l'identifiant de la transaction &agrave; utiliser lors d'une transaction
	 * 
	 * @throws DBBeanException
	 * @throws SQLException
	 * @throws Exception
	 *  
	 */
	public long createTransaction() throws DBBeanException, SQLException, Exception {
		Long id;
		synchronized (this) {
			id = new Long(idCounter);
			idCounter++;
		}
		TransactionDB transaction = new TransactionDB(id.longValue());
		lesTransactions.put(id, transaction);

		return id.longValue();
	}

	/**
	 * Cette m&eacute;thode statique permet de d&eacute;truire une transaction dans le pool &agrave;
	 * partir de son identifiant
	 * 
	 * @param transactionId (long) : identifiant de la transaction
	 *  
	 */
	public void deleteTransaction(long transactionId) {
		// La connection de la transaction a deja ete liberee dans la methode
		// commit ou rollback
		// de la classe transaction, seul endroit on on peut supprimer une
		// transaction

		// Suppression de la transaction du pool
		lesTransactions.remove(new Long(transactionId));
	}

	/**
	 * Cette m&eacute;thode statique permet de r&eacute;cup&eacute;rer une transaction en provenance
	 * du pool en fonction de son identifiant
	 * 
	 * @param transactionId (long) : identifiant de la transaction
	 * @return la transaction
	 *  
	 */
	public TransactionDB getTransaction(long transactionId) {
		return (TransactionDB) lesTransactions.get(new Long(transactionId));
	}

}