package com.astrium.faceo.server.dataBaseAccess.common;

/*
 * @(#)AbstractDBBean.java	 1.0  14/10/2008
 *
 *
 * PROJET       : SITE FACEO
 *
 * LANGUAGE     : Java
 *
 * DESCRIPTION  : Cette classe abstraite centralise l'acces au pool de connexions a la
 * base en tenant compte de l'utilisation d'une transaction. Tous les objets
 * d'acces aux donnees doivent heriter de cette classe. On trouve aussi des
 * methodes de formattage entre l'appli et la base.
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
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.log4j.Logger;

import com.astrium.faceo.server.bean.exception.DBBeanException;
import com.astrium.faceo.server.dataBaseAccess.transaction.TransactionDB;
import com.astrium.faceo.server.dataBaseAccess.transaction.TransactionFactory;

/**
 * <H1>SITE FACEO</H1>
 * <B>Description : </B>
 * <P>
 * Cette classe abstraite centralise l'acc&eacute;s au pool de connexions &agrave; la
 * base en tenant compte de l'utilisation d'une transaction. Tous les objets
 * d'acc&eacute;s aux donn&eacute;es doivent h&eacute;riter de cette classe. On trouve aussi des
 * m&eacute;thodes de formattage entre l'application et la base.
 * </P>
 * 
 * @author  ASTRIUM  
 * @version 1.0, le 14/10/2008
 */

public abstract class AbstractDBBean {

	/** Le logger de cette classe */
	private static Logger log = Logger.getLogger(AbstractDBBean.class);

	String INFOS_CLASSE = "AbstractDBBean";

	private DataBase _dataBase = null;

	private static String ERR_GET_DATABASE = "Problème lors de la recherche de la base de données FACEO.";

	private static String ORIGINE = "AbstractDBBean";

	/**
	 * Constructeur vide
	 * @throws DBBeanException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws Exception
	 */
	public AbstractDBBean() throws DBBeanException, SQLException, ClassNotFoundException, Exception {
	        
		//Recuperation de la reference sur la base de donnees metisportal
		_dataBase = new DataBase();

		try {
			// MAJ de la connection a la base de donnees
	         _dataBase.majConnexion() ;
	         // _dbConnection = _dataBase.getConnection();
	     } catch (SQLException sqlException) {
			log.info("\n" + INFOS_CLASSE + " : SQLException : " + sqlException.getErrorCode());    
			log.info("\n" + INFOS_CLASSE + " : SQLException : " + sqlException.getSQLState());    
			log.info("\n" + INFOS_CLASSE + " : SQLException : " + sqlException.getLocalizedMessage());    
		} catch (IOException ioException) {
			log.info("\n" + INFOS_CLASSE + " : IOException : " + ioException.getLocalizedMessage());    
		} catch (NullPointerException nullPointerException) {
			log.info("\n" + INFOS_CLASSE + " : NullPointerException : " + nullPointerException.getLocalizedMessage());    
		} finally {		        
			if (_dataBase == null) {
				throw new DBBeanException(
						ERR_GET_DATABASE,
						DBBeanException.ERR_GET_DATABASE,
						ORIGINE + ": M=public AbstractDBBean()");
			}
		}
	     
	} // public AbstractDBBean() throws DBBeanException {

	// Renvoie la DataBase
	protected DataBase getDataBase() throws DBBeanException {
	    return _dataBase;
	}
	
	/**
	 * Renvoie une connexion sur la base metisportal en tenant compte d'une &eacute;ventuelle transaction
	 * @param _transactionId (long) :  r&eacute;f&eacute;rence de la transaction
	 *          _transactionId == 0 => pas de transaction
	 * @return Connection : la connection
	 * @throws DBBeanException
	 */
	protected Connection getConnection(long _transactionId) throws DBBeanException {
		
	    Connection connection = null;

		try {
			if (_transactionId == 0) {
				// pas de transaction, on demande une connection 
				connection = _dataBase.getConnection();
			} else {
				// on recupere la connection dans la transaction
				TransactionFactory tf = TransactionFactory.getInstance();
				TransactionDB tdb = tf.getTransaction(_transactionId);
				connection = tdb.getConnection();
			}
		} catch (Exception e) {
			throw new DBBeanException(
					e.getMessage(),
					DBBeanException.ERR_GET_CONN,
					ORIGINE + ": M=protected Connection getConnection (long _transactionId)");
		}

		// on renvoie la connection recuperee
		return connection;
	}

	/**
	 * Ferme une connexion sur la base metisportal en tenant compte d'une &eacute;ventuelle transaction
	 * @param _dataBase (DataBase)  : r&eacute;f&eacute;rence &agrave; l'objet dataBase
	 * @param _transactionId (long) : r&eacute;f&eacute;rence de la transaction
	 *          _transactionId == 0 => pas de transaction
	 * @throws DBBeanException
	 */
	protected void closeConnexion(DataBase _dataBase, long _transactionId)
			throws DBBeanException {
		try {
			// si on est pas dans une transaction, on libere proprement la
			// connexion
			Connection connection = _dataBase.getConnection();
			if ((connection != null) && (_transactionId == 0)) {
			    _dataBase.closeConnection();
			}
		} catch (Exception e) {
			throw new DBBeanException(
					e.getMessage(),
					DBBeanException.ERR_FERMER_CONN,
					ORIGINE + ": M=protected void closeConnexion(DataBase _dataBase,long _transactionId)");
		}
	} // protected void closeConnexion(DataBase _dataBase, long _transactionId)

	/**
	 * Ferme une connexion sur la base metisportal en tenant compte d'une &eacute;ventuelle transaction
	 * @param _connection (Connection) : connection &agrave; fermer
	 * @param _transactionId (long)    : r&eacute;f&eacute;rence de la transaction
	 *          _transactionId == 0 => pas de transaction
	 * @throws DBBeanException
	 */
	protected void closeConnexion(Connection _connection, long _transactionId)
			throws DBBeanException {
		try {
			// si on est pas dans une transaction, on libere proprement la
			// connexion
			if ((_connection != null) && (_transactionId == 0)) {
				if (! _connection.isClosed()) {
					_connection.close();
					if (_connection != null){
						if (_connection.isClosed()){
							log.info("\n" + INFOS_CLASSE + " : closeConnection : connection closed");    
						} else {
							log.info("\n" + INFOS_CLASSE + " : closeConnection : connection open");    
						}
					} // if (_connection != null){
				} // if (! _connection.isClosed()) {
			} // if ((_connection != null) && (_transactionId == 0)) {
		} catch (Exception e) {
			throw new DBBeanException(
					e.getMessage(),
					DBBeanException.ERR_FERMER_CONN,
					ORIGINE + ": M=protected void closeConnexion(Connection _connection,long _transactionId)");
		}
	} // protected void closeConnexion(Connection _connection, long _transactionId)

	/**
	 * Fonction de conversion de type vers la base String -> VARCHAR2 Date ->
	 * DATE Integer (pas de chiffres apres la virgule) -> NUMERIC(x, 0) Double
	 * (au moins un chiffre apr&eacute;s la virgule) -> NUMERIC(x,>0)
	 * @param _statement (PreparedStatement) : la requ&ecirc;te pr&eacute;par&eacute;e
	 * @param _i (int)          : rang d u param&egrave;tre
	 * @param _obj (Object)     : l'objet &agrave; pr&eacute;parer
	 * @param _objClass (Class) : la classe de l'objet &agrave;a pr&eacute;parer
	 * @throws DBBeanException
	 */
	@SuppressWarnings("rawtypes")
	public void setParameterInStatement(PreparedStatement _statement, int _i,
			Object _obj, Class _objClass) throws DBBeanException {
		try {
			String tmpName = _objClass.getName();
			
			// Type Date, formate en DATE a la maniere de MySQL
			if (tmpName.equals("java.util.Date")) {
				if (_obj != null)
					_statement.setTimestamp(_i, new Timestamp(((Date) _obj).getTime()));
				else
					_statement.setNull(_i, java.sql.Types.TIMESTAMP);
			}
			
			if (tmpName.equals("java.sql.Timestamp")) {
				if (_obj != null)
					_statement.setTimestamp(_i, new Timestamp(((Date) _obj).getTime()));
				else
					_statement.setNull(_i, java.sql.Types.TIMESTAMP);
			}
			
			// Type String, formate en remplacant les ' par des ''
			else if (tmpName.equals("java.lang.String")) {
				if (_obj != null)
					_statement.setString(_i, (String) _obj);
				else
					_statement.setNull(_i, java.sql.Types.VARCHAR);
			}
			
			// Type BigDecimal, formate en BigDecimal
			else if (tmpName.equals("java.math.BigDecimal")) {
				if (_obj != null)
					_statement.setBigDecimal(_i, (BigDecimal) _obj);
				else
					_statement.setNull(_i, java.sql.Types.DECIMAL);
			}
			
			// Type Integer, formate en Integer
			else if (tmpName.equals("java.lang.Integer")) {
				if (_obj != null)
					_statement.setInt(_i, ((Integer) _obj).intValue());
				else
					_statement.setNull(_i, java.sql.Types.NUMERIC);
			}

			// Type Long, formate en Long
			else if (tmpName.equals("java.lang.Long")) {
				if (_obj != null)
					_statement.setLong(_i, ((Long) _obj).longValue());
				else
					_statement.setNull(_i, java.sql.Types.NUMERIC);
			}
			
		} catch (Exception e) {
			throw new DBBeanException(
					e.getMessage(),
					DBBeanException.ERR_VALORISATION_REQUETE,
					ORIGINE
							+ ": M=public void setParameterInStatement(PreparedStatement _statement, int _i, Object _obj, Class _objClass)");
		}
	} // public void setParameterInStatement(PreparedStatement _statement, int _i,

	/**
	 * Ferme un PreparedStatement sur la base de donn&eacute;es
	 * @param _preparedStatement (PreparedStatement) : requete SQL &agrave; fermer
	 * @param _INFOS_CLASSE (String) : origine de l'appel
	 * @param _log (Logger) : r&eacute;f&eacute;rence du log
	 * @param _methode (String) : m&eacute;thode appelante
	 * @throws DBBeanException
	 */
	public void closePreparedStatement(PreparedStatement _preparedStatement, String _INFOS_CLASSE, 
				Logger _log, String _methode)
				throws DBBeanException {
		
		if (_preparedStatement != null) {
			try {
				_preparedStatement.close();
			} catch (SQLException error) {
				_log.info("\n" + _INFOS_CLASSE + " : : _preparedStatement.close : SQLException : " + _methode+ " : " + error.getMessage());    
			} catch (Exception error) {
				_log.info("\n" + _INFOS_CLASSE + " : : _preparedStatement.close : Exception : " + _methode+ " : " + error.getMessage());    
			} finally {
				_preparedStatement = null;
	}
		} // if (preparedStatement != null) {
		
	} // public void closePreparedStatement(PreparedStatement _preparedStatement, String _INFOS_CLASSE, Logger _log)

	/**
	 * Ferme un ResultSet sur la base de donn&eacute;es
	 * @param _resultSet (ResultSet) : r&eacute;sultat de la requete SQL &agrave; fermer
	 * @param _INFOS_CLASSE (String) : origine de l'appel
	 * @param _log (Logger) : r&eacute;f&eacute;rence du log
	 * @param _methode (String) : m&eacute;thode appelante
	 * @throws DBBeanException
	 */
	public void closeResultSet(ResultSet _resultSet, String _INFOS_CLASSE, 
				Logger _log, String _methode)
				throws DBBeanException {
		
		if (_resultSet != null) {
			try {
				_resultSet.close();
			} catch (SQLException error) {
				_log.info("\n" + _INFOS_CLASSE + " : : _resultSet.close : SQLException : " + _methode+ " : " + error.getMessage());    
			} catch (Exception error) {
				_log.info("\n" + _INFOS_CLASSE + " : : _resultSet.close : Exception : " + _methode+ " : " + error.getMessage());    
			} finally {
				_resultSet = null;
			}
		} // if (_resultSet != null) {

	} // public void closeResultSet(ResultSet _resultSet, String _INFOS_CLASSE, Logger _log)

} // class
