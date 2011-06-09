package com.astrium.faceo.server.dataBaseAccess.common;

/*
 * @(#)DataBase.java	 1.0  14/10/2008
 *
 *
 * PROJET       : FACEO
 *
 * LANGUAGE     : Java
 *
 * DESCRIPTION  : Cette classe definit la connexion a la base MySQl faceo
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

import java.sql.Connection;
import java.sql.SQLException;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.astrium.faceo.client.common.programming.sps2.Sps2GeneralConstants;
/**
 * <B>FACEO</B> <BR>
 * 
 * <P>
 * Cette classe d&eacute;finit la connexion &agrave; la base MySQl faceo
 * </P>
 * </P>
 * 
 * @author  GR
 * @version 1.0, le 14/10/2008
 **/
public class DataBase {


	/** Le logger de cette classe */
	private static Logger log = Logger.getLogger(DataBase.class);

	String INFOS_CLASSE = "DataBase";

	/**
	 * connection &agrave; la base de donn&eacute;es
	 */
	private Connection _connection;

	/**
	 * _dataSource : data source
	 */
	public static DataSource _dataSource = null;

	/** datasource JNDI
	 * permet de se connecter &agrave; la base de donn&eacute;es sans aucun param&egrave;tre
	 * le contexte est d&eacute;fini dans le fichier context.xml de Tomcat
	 * avec toutes les informations de connexion (driver, login, password ...)
	 */
	private static String DATASOURCE_JNDI_CONTEXT = "java:comp/env/jdbc/hmafoDS";

	/**
	 * Mise &agrave; jour des champs CONSTANTES ...
	 */
	static {
		PropertyResourceBundle prb = (PropertyResourceBundle)PropertyResourceBundle.getBundle(Sps2GeneralConstants.HMAFO_PROPERTIES);

		try {
			DATASOURCE_JNDI_CONTEXT = prb.getString("hmafoDataSource").trim();
			log.debug("\n DataBase : >>> Reading of parameter file '" + Sps2GeneralConstants.HMAFO_PROPERTIES + "' OK !!!");    
		} catch (Exception exception) {
			log.debug("\n DataBase :  >>> Reading of parameter file '" + Sps2GeneralConstants.HMAFO_PROPERTIES + "' KO !!!");    
		}

		log.debug("\n DataBase : ---------- Informations 'hmafo' [DEB] ----------");    
		log.debug("\n DataBase : DATASOURCE_JNDI_CONTEXT : " + DATASOURCE_JNDI_CONTEXT);    
		log.debug("\n DataBase : ---------- Informations 'hmafo' [FIN] ----------");    
	}

	/**
	 * @return Connection : connection a la base de donnees
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */
	public Connection getConnection() throws SQLException, Exception {
		// MAJ de la connection a la base de donnees
		//if ((_connection == null) | (_connection.isClosed()))  {
		if ((_dataSource == null) || (_connection == null)  || (_connection.isClosed()))  {
			majConnexion() ;
		}
		return _connection;
	}

	/**
	 * @return Connection : connection &agrave; la base de donn&eacute;es
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */
	public Connection getCurrentConnection() throws SQLException, Exception {
		return _connection;
	}

	/**
	 * @param connection (Connection) : la connection &agrave; valoriser
	 */
	public void setConnection(Connection connection) {
		_connection = connection;
	}

	/** 
	 * R&eacutecup&eacute;ration de la connexion &agrave; la base de donn&eacute;es. 
	 * 
	 * @throws SQLException
	 * @throws Exception
	 * */
	public void majConnexion() throws SQLException, Exception {

		try {
			if (DATASOURCE_JNDI_CONTEXT != null) {
				Context initialContext = new InitialContext();
				DataSource datasource = (DataSource) initialContext.lookup(DATASOURCE_JNDI_CONTEXT);
				if (datasource != null){
					_dataSource = datasource;

					this.setConnection(_dataSource.getConnection());
					// on desactive le mode AutoCommit par defaut
					this.getConnection().setAutoCommit(false);

				} else {
					log.info("\n DataBase : cannot lookup datasource");    
					throw new MissingResourceException("cannot lookup datasource", null, null);
				}
			} else {
				log.info("\n DataBase : cannot get properties");    
				throw new MissingResourceException("Cannot get properties", null, null);
			}

		} catch ( NamingException namingException ) {
			log.info("\n DataBase : NamingException : cannot get connection : " + namingException.getLocalizedMessage());    
			throw new MissingResourceException("Cannot get connection " + namingException, null, null);
		}

	} // public void MajConnexion()

	/** 
	 * Commencer une transaction sur la base de donn&eacute;es. 
	 * 
	 * @throws : SQLException
	 * @throws : Exception
	 * */
	/* public Savepoint beginTransaction() throws SQLException {
	    return this.connection.setSavepoint("NewAuthor");
	} // public void beginTransaction()  */

	/** 
	 * Terminer une transaction sur la base de donn&eacute;es. 
	 * 
	 * @throws : SQLException
	 * @throws : Exception
	 * */
	/* public void endTransaction(Savepoint savepoint) throws SQLException {
	    this.connection.releaseSavepoint(savepoint);
	} // public void endTransaction() */

	/** 
	 * Valider la transaction sur la base de donn&eacute;es. 
	 * 
	 * @throws SQLException
	 * @throws Exception
	 * */
	public void commitTransaction() throws SQLException, Exception {
		this.getConnection().commit();
	} // public void commitTransaction()

	/** 
	 * Annuler la transaction sur la base de donn&eacute;es. 
	 * 
	 * @throws SQLException
	 * @throws Exception
	 * */
	public void rollbackTransaction() throws SQLException, Exception {
		this.getConnection().rollback();
	} // public void rollbackTransaction()

	/**
	 * fermer la connection &agrave; la data source (base de donn&eacute;es)
	 * 
	 * @throws SQLException
	 */
	public void closeConnection() throws SQLException{

		try {
			this.getConnection().close();

			if (this.getConnection() != null){
				if (this.getConnection().isClosed()){
					log.info("\n DataBase : closeConnection : connection close");    
				} else {
					log.info("\n DataBase : closeConnection : connection open");    
				}
			} else {
				log.info("\n DataBase : closeConnection : null connection");    
			}

		} catch (SQLException sqlex){
			log.info("\n DataBase : closeConnection : SQLException : " + sqlex.getLocalizedMessage());    
		} catch (Exception exception){
			log.info("\n DataBase : closeConnection : Exception : " + exception.getLocalizedMessage());    
		} 	

	} // public void closeConnection()

} // class
