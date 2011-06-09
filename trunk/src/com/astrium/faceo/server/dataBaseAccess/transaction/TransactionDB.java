package com.astrium.faceo.server.dataBaseAccess.transaction;

/*
 * @(#)TransactionDB.java	 1.0  14/10/2008
 *
 *
 * PROJET       : SITE FACEO
 *
 * LANGUAGE     : Java
 *
 * DESCRIPTION  : Cette classe encapsule une Connection afin de permettre la gestion de
 * transactions. Elle possede un numero qui est son id de transaction. Elle
 * permet de realiser des operations de commit, rollback sur cette connexion
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

//imports
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.astrium.faceo.server.bean.exception.DBBeanException;
import com.astrium.faceo.server.dataBaseAccess.common.DataBase;

/**
 * <H1>SITE FACEO</H1>
 * <B>Description : </B>
 * <P>
 * Cette classe encapsule une Connection afin de permettre la gestion de
 * transactions. Elle poss&egrave;de un num&eacute;ro qui est son id de transaction. Elle
 * permet de r&eacute;aliser des op&eacute;rations de commit, rollback sur cette connexion
 * </P>
 * 
 * @author GR   
 * @version 1.0, le 14/10/2008
 */

public class TransactionDB {

	/** Le logger de cette classe */
	private static Logger log = Logger.getLogger(TransactionDB.class);

	String INFOS_CLASSE = "TransactionDB"; // Pour message dans fichier de log

	// la connexion a la base
	@SuppressWarnings("unused")
	private Connection _dbConnection = null;

	// la base de donnees
	private DataBase _dataBase = null;

	// le numero de la transaction
	private long transactionId;

	// Description pour les erreurs �ventuelles
	private static String ERR_GET_DATABASE = "Probleme lors de la recherche de la base de donnees.";

	private static String ORIGINE = "TransactionDB";

	/**
	 * Transaction constructor with identifier
	 * 
	 * @param _transId (long) : transaction identifier
	 * 
	 * @throws DBBeanException
	 * @throws SQLException
	 * @throws IOException
	 * @throws NullPointerException
	 * @throws Exception
	 */
	public TransactionDB(long _transId) 
	throws DBBeanException, SQLException, IOException, NullPointerException, Exception {
		//on stocke l'identifiant de la transaction
		transactionId = _transId;

		//Recuperation de la reference sur la base de donnees metisportal
		_dataBase = new DataBase();

		// on recupere une connexion
		try {
			// MAJ de la connection a la base de donnees
			_dataBase.majConnexion() ;		        		    
			_dataBase.setConnection(this.getConnection());
		}

		catch (SQLException sqlException) {
			log.info("\n" + ORIGINE + " : SQLException : " + sqlException.getSQLState());    
			log.info("\n" + ORIGINE + " : SQLException : " + sqlException.getErrorCode());    
			log.info("\n" + ORIGINE + " : SQLException : " + sqlException.getLocalizedMessage());    
		} catch (IOException ioException) {
			log.info("\n" + ORIGINE + " : IOException : " + ioException.getLocalizedMessage());    
		} catch (NullPointerException nullPointerException) {
			log.info("\n" + ORIGINE + " : NullPointerException : " + nullPointerException.getLocalizedMessage());    
		} finally {		        
			if (_dataBase == null) {
				throw new DBBeanException(
						ERR_GET_DATABASE,
						DBBeanException.ERR_GET_DATABASE,
						ORIGINE + ": M=public AbstractDBBean()");
			}
		}

		try {
			// 2 on demande une connexion a la reference sur la base de donnees
			_dbConnection = this.getConnection();

			// Demarrage d'une transaction sur la connection
			// _dbConnection.beginTransactions();
		} catch (Exception e) {
			throw new DBBeanException(e.getMessage(),
					DBBeanException.ERR_GET_CONN, ORIGINE
					+ ": M=public TransactionDB(long _transId)");
		}

	} // public TransactionDB(long _transId) throws DBBeanException, SQLException, IOException, NullPointerException {

	/**
	 * Cette m&eacute;thode permet de valider (commit) une transaction
	 * <P>
	 * Il suffit d'appeler la m&eacute;thode commitTransaction de l'objet DBConnection.
	 * Celui ci se charge d'effectuer ce commit.
	 * 
	 * @throws DBBeanException
	 * @throws SQLException
	 * @throws Exception
	 */
	public void commit() throws DBBeanException,SQLException, Exception {
		try {
			// _dbConnection.commitTransaction();
			_dataBase.commitTransaction();
		} catch (SQLException ex) {
			throw new DBBeanException(ex.getMessage(),
					DBBeanException.ERR_TRANSACTION_SQL, ORIGINE
					+ ": M=public void commit()");
		} finally {
			try {
				// on libere systematiquement la connexion apres le commit
				releaseConnection();
			} catch (Exception e) {
				//La liberation de la connexion s'est mal passee
				throw new DBBeanException(e.getMessage(),
						DBBeanException.ERR_TRANSACTION_SQL, ORIGINE
						+ ": M=public void commit()");
			}
		}
	}

	/**
	 * Cette m&eacute;thode permet d'annuler (commit) une transaction
	 * <P>
	 * Il suffit d'appeler la m&eacute;thode rollbackTransaction de l'objet
	 * DBConnection. Celui ci se charge d'effectuer ce rollback.
	 *  
	 * @throws DBBeanException
	 * @throws SQLException
	 * @throws Exception
	 */
	public void rollback() throws DBBeanException,SQLException, Exception { 
		try {
			// _dbConnection.rollback();
			_dataBase.rollbackTransaction();
		} catch (SQLException ex) {
			throw new DBBeanException(ex.getMessage(),
					DBBeanException.ERR_TRANSACTION_SQL, ORIGINE
					+ ": M=public void rollback()");
		} finally {
			try {
				// on libere systematiquement la connexion apres le commit
				releaseConnection();
			} catch (Exception e) {
				//La liberation de la connexion s'est mal passee
				throw new DBBeanException(e.getMessage(),
						DBBeanException.ERR_FERMER_CONN, ORIGINE
						+ ": M=public void rollback()");
			}
		}
	}

	/**
	 * Cette m&eacute;thode permet de relacher une connection &agrave; la base de donn&eacute;es
	 * <P>
	 * 
	 * @throws DBBeanException
	 * @throws SQLException
	 * @throws Exception
	 */
	public void releaseConnection() throws DBBeanException,SQLException, Exception {
		try {
			_dataBase.getCurrentConnection().close();

			if (_dataBase.getCurrentConnection() != null){
				if (_dataBase.getCurrentConnection().isClosed()){
					log.info("\n" + ORIGINE + " : releaseConnection : connection closed");    
				} else {
					log.info("\n" + ORIGINE + " : releaseConnection : connection open");    
				}
			} else {
				log.info("\n" + ORIGINE + " : releaseConnection : null connection");    
			}

		} catch (SQLException sqlException){
			log.info("\n" + ORIGINE + " : releaseConnection : SQLException : " + sqlException.getSQLState());    
			log.info("\n" + ORIGINE + " : releaseConnection : SQLException : " + sqlException.getErrorCode());    
			log.info("\n" + ORIGINE + " : releaseConnection : SQLException : " + sqlException.getLocalizedMessage());    
		} catch (Exception exception){
			log.info("\n" + ORIGINE + " : releaseConnection : Exception : " + exception.getLocalizedMessage());    
		} 	
	} // public void releaseConnection() throws DBBeanException,SQLException, Exception {

	/**
	 * Cette m&eacute;thode permet de r&eacute;cup&eacute;rer l'id de la transaction
	 * 
	 * @return l'identifiant de la transaction
	 */
	public long getTransactionId() {
		return transactionId;
	}

	/**
	 * Cette m&eacute;thode permet de r&eacute;cup&eacute;rer la connexion
	 * 
	 * @return Connection : la connexion &agrave; la base de donn&eacute;es
	 * @throws Exception
	 */
	public Connection getConnection() throws Exception{
		return _dataBase.getConnection();
	}

}