package com.astrium.faceo.server.dataBaseAccess;

/*
 * @(#)TaskDB.java	 1.0  07/06/2010
 *
 *
 * PROJET       : SITE FACEO
 *
 * LANGUAGE     : Java
 *
 * DESCRIPTION  : Cette classe regroupe toutes les methodes permettant de recuperer, de
 * modifier, d'effacer, ou de creer l'objet metier OrderOM dans la base. On
 * trouve aussi des methodes sur des ensembles d'objet OrderOM. 
 *
 * CREATION :
 * ---------------------------------------------------------------------
 * | Date       | Version | Description                                |
 * ---------------------------------------------------------------------
 * | 07/06/2010 |    1.0  |                                            |
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.astrium.faceo.client.bean.programming.sps2.TaskBean;
import com.astrium.faceo.client.common.programming.sps2.Sps2GeneralConstants;
import com.astrium.faceo.server.bean.exception.DBBeanException;
import com.astrium.faceo.server.dataBaseAccess.common.AbstractDBBean;

/**
 * <H1>SITE FACEO</H1>
 * <B>Description : </B>
 * <P>
 * Cette classe regroupe toutes les m&eacute;thodes permettant de r&eacute;cup&eacute;rer, de
 * modifier, d'effacer, ou de cr&eacute;er l'objet m&eacute;tier TaskOM dans la base. On
 * trouve aussi des m&eacute;thodes sur des ensembles d'objet OrderOM. 
 * </P>
 * 
 * @author ASTRIUM
 * @version 1.0, le 07/06/2010
 */

public class TaskDB extends AbstractDBBean {

	/** Le logger de cette classe */
	private static Logger log = Logger.getLogger(TaskDB.class);

	String INFOS_CLASSE = "TaskDB"; // for message in log file

	// Reference sur l'unique instance de TaskDB
	private static TaskDB instance = null;

	// Origine d'une eventuelle exception
	private static String ORIGINE = "TaskDB";

	// DataBase name

	private static String TASKS = "tasks";

	// columns names for DataBase

	private static String TASK_ID = "TASK_ID";

	private static String TASK_USER = "TASK_USER";

	private static String TASK_NAME = "TASK_NAME";

	private static String TASK_OPERATION = "TASK_OPERATION";

	private static String TASK_STATUS = "TASK_STATUS";

	private static String TASK_SENSOR = "TASK_SENSOR";

	private static String TASK_REQUEST = "TASK_REQUEST";
	
	private static String TASK_REQUEST_STATUS = "TASK_REQUEST_STATUS";

	private static String TASK_RESPONSE = "TASK_RESPONSE";
	
	private static String TASK_RESPONSE_TYPE = "TASK_RESPONSE_TYPE";

	private static String TASK_CREATION = "TASK_CREATION";

	private static String TASK_MODIFICATION = "TASK_MODIFICATION";

	private static String TASK_UPDATED = "TASK_UPDATED";

	/**
	 * Constructeur priv&eacute; (singleton)
	 */
	private TaskDB() throws DBBeanException, SQLException, ClassNotFoundException, Exception { 
		super();
	}

	/**
	 * R&eacute;cup&egrave;re l'instance unique de la classe en la cr&eacute;ant si n&eacute;cessaire
	 * 
	 * @return TaskDB : instance unique de la classe
	 * 
	 * @throws DBBeanException
	 * @throws ClassNotFoundException
	 * @throws Exception
	 */
	public static synchronized TaskDB getInstance() throws DBBeanException, ClassNotFoundException, Exception {
		if (instance == null) {
			instance = new TaskDB();
		}
		return instance;
	}

	/**
	 * LES METHODES SUIVANTES EXECUTENT DES REQUETES POUR AGIR SUR LE STOCKAGE
	 * DE L'OBJET METIER USER OU POUR S'INFORMER. LES REQUETES SONT
	 * DEFINIES AVANT LA METHODE SOUS FORME DE CONSTANTE DE CLASSE LA METHODE
	 * VALORISE CES REQUETES, LES EXECUTE, ET INTERPRETE LES RESULTATS
	 */

	// used request
	static final String SQL_INSERT = "INSERT INTO " + TASKS + " ( "
	+ TASK_ID + " , " + TASK_NAME + " , " +  TASK_CREATION + " , " + TASK_OPERATION + " , " 
	+  TASK_STATUS + " , " + TASK_SENSOR + " , " + TASK_REQUEST + " , " + TASK_REQUEST_STATUS + " , "
	+ TASK_RESPONSE + " , "+ TASK_RESPONSE_TYPE + " , "  +  TASK_MODIFICATION + " , " +  TASK_USER  + " ) " 
	+ " VALUES (?, ?, NOW(), ?, ?, ?, ?, ?, ?, ?, NOW(), ?)";

	/**
	 * Insert one Task in DataBase
	 *
	 * @param _taskToInsert (TaskBean) 	: bean with task informations
	 * @param _transactionId (long)    	: transaction referency
	 * 
	 * @exception DBBeanException if null key
	 */
	public void insert(TaskBean _taskToInsert, long _transactionId)
	throws DBBeanException {

		String METHOD = "insert";
		String METHOD_FULL = ": M=public void " + METHOD + "(TaskBean _taskToInsert, long _transactionId)";

		// database access
		Connection connection = null;
		PreparedStatement pstmt = null;

		try {
			// getting a connection with a transaction
			// transactionID == 0 => no transaction
			connection = getConnection(_transactionId);

			// init preparedStatement with the request and the connection
			pstmt = connection.prepareStatement(SQL_INSERT);

			// setting request parameters with informations of task identifier
			setParameterInStatement(pstmt, 1, _taskToInsert.getId(),
					(_taskToInsert.getId()).getClass());
			// task name
			setParameterInStatement(pstmt, 2, _taskToInsert.getName(),
					(_taskToInsert.getName()).getClass());
			// task operation
			setParameterInStatement(pstmt, 3, _taskToInsert.getOperation(),
					(_taskToInsert.getOperation()).getClass());
			// task status
			setParameterInStatement(pstmt, 4, _taskToInsert.getStatus(),
					(_taskToInsert.getStatus()).getClass());
			// task sensor
			setParameterInStatement(pstmt, 5, _taskToInsert.getSensor(),
					(_taskToInsert.getSensor()).getClass());
			// task request
			setParameterInStatement(pstmt, 6, _taskToInsert.getRequest(),
					(_taskToInsert.getRequest()).getClass());
			// task request status
			setParameterInStatement(pstmt, 7, _taskToInsert.getRequestStatus(),
					(_taskToInsert.getRequestStatus()).getClass());
			// task response
			setParameterInStatement(pstmt, 8, _taskToInsert.getResponse(),
					(_taskToInsert.getResponse()).getClass());
			// task response type
			setParameterInStatement(pstmt, 9, _taskToInsert.getResponseType(),
					(_taskToInsert.getResponseType()).getClass());
			// task user
			setParameterInStatement(pstmt, 10, _taskToInsert.getUser(),
					(_taskToInsert.getUser()).getClass());

			// Execute request
			pstmt.execute();
		} catch (SQLException e) {
			throw new DBBeanException(
					e.getMessage(),
					DBBeanException.ERR_INSERT_SQL,
					ORIGINE + METHOD_FULL);
		} catch (Exception e) {
			throw new DBBeanException(
					e.getMessage(),
					DBBeanException.ERR_INSERT_SQL,
					ORIGINE + METHOD_FULL);
		} finally {
			try {
				// dans tous les cas, on libere la connexion et le preparedStatement
				// this.getDataBase().commitTransaction();
				// le commit est gere dans la transaction ainsi que la fermeture de la connexion
				// this.closeConnexion(this.getDataBase(), transactionId);
				// this.closeConnexion(connection, transactionId);

				if (pstmt != null) {
					this.closePreparedStatement(pstmt, INFOS_CLASSE, log, METHOD);
				}
			} catch (Exception e) {
				//La liberation de la connexion s'est mal passee
				throw new DBBeanException(
						e.getMessage(),
						DBBeanException.ERR_FERMER_CONN,
						ORIGINE + METHOD_FULL);
			}
		}

	} // public void insert(TaskBean _taskToInsert, long _transactionId)

	/**
	 * Update one Task in DataBase
	 *
	 * @param _taskToUpdate (TaskBean) 	: bean with task informations
	 * @param _transactionId (long)    	: transaction referency
	 * 
	 * @exception DBBeanException if null key
	 */
	public void update(TaskBean _taskToUpdate, long _transactionId)
	throws DBBeanException {

		String METHOD = "update";
		String METHOD_FULL = ": M=public void " + METHOD + "(TaskBean _taskToUpdate, long _transactionId)";

		// database access
		Connection connection = null;
		PreparedStatement pstmt = null;

		try {
			// getting a connection with a transaction
			// transactionID == 0 => no transaction
			connection = getConnection(_transactionId);

			// used request
			int updated = 0;
			if (_taskToUpdate.getUpdatedTask()) {
				updated = 1;
			} // if

			String SQL_UPDATE = "UPDATE " + TASKS + " SET "
			+ TASK_NAME + " ='" + _taskToUpdate.getName() + "', " 
			+ TASK_STATUS + " ='" + _taskToUpdate.getStatus() + "', " 
			+ TASK_REQUEST_STATUS + " ='" + _taskToUpdate.getRequestStatus() + "', " 
			+ TASK_RESPONSE + " ='" + _taskToUpdate.getResponse() + "', " 
			+ TASK_RESPONSE_TYPE + " ='" + _taskToUpdate.getResponseType() + "', " 
			+ TASK_MODIFICATION + " = NOW(), "
			+ TASK_UPDATED + " ='" + updated + "' " 
			+ " WHERE " + TASK_ID + " ='" + _taskToUpdate.getId() + "'";
			
			// init preparedStatement with the request and the connection
			pstmt = connection.prepareStatement(SQL_UPDATE);

			// Execute request
			pstmt.execute();
		} catch (SQLException e) {
			throw new DBBeanException(
					e.getMessage(),
					DBBeanException.ERR_UPDATE_SQL,
					ORIGINE + METHOD_FULL);
		} catch (Exception e) {
			throw new DBBeanException(
					e.getMessage(),
					DBBeanException.ERR_UPDATE_SQL,
					ORIGINE + METHOD_FULL);
		} finally {
			try {
				// dans tous les cas, on libere la connexion et le preparedStatement
				// this.getDataBase().commitTransaction();
				// le commit est gere dans la transaction ainsi que la fermeture de la connexion
				// this.closeConnexion(this.getDataBase(), transactionId);
				// this.closeConnexion(connection, transactionId);

				if (pstmt != null) {
					this.closePreparedStatement(pstmt, INFOS_CLASSE, log, METHOD);
				}
			} catch (Exception e) {
				//La liberation de la connexion s'est mal passee
				throw new DBBeanException(
						e.getMessage(),
						DBBeanException.ERR_FERMER_CONN,
						ORIGINE + METHOD_FULL);
			}
		}

	} // public void update(TaskBean _taskToUpdate, long _transactionId)
	
	/**
	 * Update one Task in DataBase
	 *
	 * @param _taskToModify (TaskBean) 	: bean with task informations
	 * @param _transactionId (long)    	: transaction referency
	 * 
	 * @exception DBBeanException if null key
	 */
	public void modify(TaskBean _taskToModify, long _transactionId)
	throws DBBeanException {

		String METHOD = "modify";
		String METHOD_FULL = ": M=public void " + METHOD + "(TaskBean _taskToModify, long _transactionId)";

		// database access
		Connection connection = null;
		PreparedStatement pstmt = null;

		try {
			// getting a connection with a transaction
			// transactionID == 0 => no transaction
			connection = getConnection(_transactionId);

			// used request
			int updated = 0;
			if (_taskToModify.getUpdatedTask()) {
				updated = 1;
			} // if

			String SQL_UPDATE = "UPDATE " + TASKS + " SET "
			+ TASK_NAME + " ='" + _taskToModify.getName() + "', " 
			+ TASK_OPERATION + " ='" + _taskToModify.getOperation() + "', " 
			+ TASK_STATUS + " ='" + _taskToModify.getStatus() + "', " 
			+ TASK_REQUEST + " ='" + _taskToModify.getRequest() + "', " 
			+ TASK_REQUEST_STATUS + " ='" + _taskToModify.getRequestStatus() + "', " 
			+ TASK_RESPONSE + " ='" + _taskToModify.getResponse() + "', " 
			+ TASK_RESPONSE_TYPE + " ='" + _taskToModify.getResponseType() + "', " 
			+ TASK_MODIFICATION + " = NOW(), "
			+ TASK_UPDATED + " ='" + updated + "' " 
			+ " WHERE " + TASK_ID + " ='" + _taskToModify.getId() + "'";
			
			// init preparedStatement with the request and the connection
			pstmt = connection.prepareStatement(SQL_UPDATE);

			// Execute request
			pstmt.execute();
		} catch (SQLException e) {
			throw new DBBeanException(
					e.getMessage(),
					DBBeanException.ERR_UPDATE_SQL,
					ORIGINE + METHOD_FULL);
		} catch (Exception e) {
			throw new DBBeanException(
					e.getMessage(),
					DBBeanException.ERR_UPDATE_SQL,
					ORIGINE + METHOD_FULL);
		} finally {
			try {
				// dans tous les cas, on libere la connexion et le preparedStatement
				// this.getDataBase().commitTransaction();
				// le commit est gere dans la transaction ainsi que la fermeture de la connexion
				// this.closeConnexion(this.getDataBase(), transactionId);
				// this.closeConnexion(connection, transactionId);

				if (pstmt != null) {
					this.closePreparedStatement(pstmt, INFOS_CLASSE, log, METHOD);
				}
			} catch (Exception e) {
				//La liberation de la connexion s'est mal passee
				throw new DBBeanException(
						e.getMessage(),
						DBBeanException.ERR_FERMER_CONN,
						ORIGINE + METHOD_FULL);
			}
		}

	} // public void modify(TaskBean _taskToModify, long _transactionId)

	/**
	 * Set one Task as readed in DataBase
	 *
	 * @param _taskToUpdate (TaskBean) 	: bean with task informations
	 * @param _transactionId (long)    	: transaction referency
	 * 
	 * @exception DBBeanException if null key
	 */
	public void setTaskAsReaded(TaskBean _taskToUpdate, long _transactionId)
	throws DBBeanException {

		String METHOD = "setTaskAsReaded";
		String METHOD_FULL = ": M=public void " + METHOD + "(TaskBean _taskToUpdate, long _transactionId)";

		// database access
		Connection connection = null;
		PreparedStatement pstmt = null;

		try {
			// getting a connection with a transaction
			// transactionID == 0 => no transaction
			connection = getConnection(_transactionId);

			// used request
			int updated = 0;
			if (_taskToUpdate.getUpdatedTask()) {
				updated = 1;
			} // if
			String SQL_UPDATE = "UPDATE " + TASKS + " SET "
			+ TASK_UPDATED + " ='" + updated + "' " 
			+ " WHERE " + TASK_ID + " ='" + _taskToUpdate.getId() + "'";
			
			// init preparedStatement with the request and the connection
			pstmt = connection.prepareStatement(SQL_UPDATE);

			// Execute request
			pstmt.execute();
		} catch (SQLException e) {
			throw new DBBeanException(
					e.getMessage(),
					DBBeanException.ERR_UPDATE_SQL,
					ORIGINE + METHOD_FULL);
		} catch (Exception e) {
			throw new DBBeanException(
					e.getMessage(),
					DBBeanException.ERR_UPDATE_SQL,
					ORIGINE + METHOD_FULL);
		} finally {
			try {
				// dans tous les cas, on libere la connexion et le preparedStatement
				// this.getDataBase().commitTransaction();
				// le commit est gere dans la transaction ainsi que la fermeture de la connexion
				// this.closeConnexion(this.getDataBase(), transactionId);
				// this.closeConnexion(connection, transactionId);

				if (pstmt != null) {
					this.closePreparedStatement(pstmt, INFOS_CLASSE, log, METHOD);
				}
			} catch (Exception e) {
				//La liberation de la connexion s'est mal passee
				throw new DBBeanException(
						e.getMessage(),
						DBBeanException.ERR_FERMER_CONN,
						ORIGINE + METHOD_FULL);
			}
		}

	} // public void setTaskAsReaded(TaskBean _taskToUpdate, long _transactionId)

	// Used request
	static final String SQL_LOAD_ONE_TASK = "SELECT * FROM " + TASKS + " WHERE " + TASK_ID + " = ?"; 

	/**
	 * Load one task from database
	 * 
	 * @param _taskId (String) 	: task identifier
	 * 
	 * @return TaskBean 		: loaded data from database
	 * 
	 * @exception DBBeanException if key is null
	 */
	public TaskBean loadOneTask(String _taskId) throws DBBeanException {

		String METHOD = "loadOneTask";
		String METHOD_FULL = ": M=public TaskBean " + METHOD + "(String _taskId)";

		// returned bean from database
		TaskBean taskToReturn = new TaskBean();

		// acces database
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			// getting one connection without transaction for consulatation
			// transactionID == 0 => no transaction
			connection = this.getConnection(0);

			// init preparedStatement with request and connection
			pstmt = connection.prepareStatement(SQL_LOAD_ONE_TASK);

			setParameterInStatement(pstmt, 1, _taskId, _taskId.getClass());

			// Execute request
			rs = pstmt.executeQuery();

			// Setting bean to return
			if (rs != null) {
				if (rs.next()) {
					// getting values to fill the bean
					taskToReturn = setTaskBean (rs, ORIGINE + METHOD_FULL, true, true);
				}
			}

		} catch (SQLException exception) {
			throw new DBBeanException(exception.getMessage(),
					DBBeanException.ERR_LOAD_SQL, ORIGINE + METHOD_FULL);
		} finally {
			try {
				// dans tous les cas, on libere la connexion et le
				// preparedStatement
				// transactionID == 0 car pas de transaction
				// DataBase dataBase = this.getDataBase();
				// this.closeConnexion(dataBase, 0);
				this.closeConnexion(connection, 0);

				if (pstmt != null) {
					this.closePreparedStatement(pstmt, INFOS_CLASSE, log, METHOD);
				} 

				if (rs != null) {
					this.closeResultSet(rs, INFOS_CLASSE, log, METHOD);
				}
			} catch (Exception exception) {
				// La liberation de la connexion s'est mal passee
				throw new DBBeanException(
						exception.getMessage(),
						DBBeanException.ERR_FERMER_CONN,
						ORIGINE + 
						METHOD_FULL);
			}
		}

		// return bean
		return taskToReturn;

	} // public TaskBean loadOneTask(String _taskId) throws DBBeanException {

	// Used request
	static final String SQL_LOAD_ALL_TASKS = "SELECT * FROM " + TASKS + " ORDER BY " + TASK_CREATION + " DESC"; 

	/**
	 * Load one task from database
	 * 
	 * @return Map<String, TaskBean> : loaded tasks from database
	 * 
	 * @exception DBBeanException if key is null
	 */
	public Map<String, TaskBean> loadAllTasks() throws DBBeanException {

		String METHOD = "loadAllTasks";
		String METHOD_FULL = ": M=public Map<String, TaskBean> " + METHOD + "()";

		// Bean to return corresponding at the object to load
		Map<String, TaskBean> tasksToReturn = new HashMap<String, TaskBean>();

		// acces base
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			// get a connexion necessarly without transaction for a consultation
			// transactionID == 0 => no transaction
			connection = this.getConnection(0);

			// init preparedStatement with the request and the connection
			pstmt = connection.prepareStatement(SQL_LOAD_ALL_TASKS);

			// Execute request
			rs = pstmt.executeQuery();

			//Fill the bean to return

			{if (rs != null) 
				while ( rs.next() ) { 
					// on recupere les valeurs pour completer le bean
					TaskBean task = new TaskBean();
					task = setTaskBean (rs, ORIGINE + METHOD_FULL, false, false);

					tasksToReturn.put(task.getId(), task);
				} //   while ( rs() ) { 
			} // if (rs != null) 
		} catch (SQLException exception) {
			log.info("\n" + ORIGINE + " : " + METHOD + " : SQLException : " + exception.getLocalizedMessage()); 
			throw new DBBeanException(exception.getMessage(),
					DBBeanException.ERR_LOAD_SQL, ORIGINE
					+ METHOD_FULL);
		} finally {
			try {
				// dans tous les cas, on libere la connexion et le
				// preparedStatement
				// transactionID == 0 car pas de transaction
				// DataBase dataBase = this.getDataBase();
				// this.closeConnexion(dataBase, 0);
				this.closeConnexion(connection, 0);

				if (pstmt != null) {
					this.closePreparedStatement(pstmt, INFOS_CLASSE, log, METHOD_FULL);
				} 

				if (rs != null) {
					this.closeResultSet(rs, INFOS_CLASSE, log, METHOD_FULL);
				}
			} catch (Exception exception) {
				// La liberation de la connexion s'est mal passee
				log.info("\n" + ORIGINE + " : " + METHOD + " : Exception : " + exception.getLocalizedMessage()); 
				throw new DBBeanException(
						exception.getMessage(),
						DBBeanException.ERR_FERMER_CONN,
						ORIGINE + METHOD_FULL);
			}
		}

		// return the results
		return tasksToReturn;

	} // public Map<String, TaskBean> loadAllTasks() throws DBBeanException {

	/**
	 * Load one task from database
	 * 
	 * @param _idUser (String)	: user name
	 * 
	 * @return Map<String, TaskBean> : loaded tasks from database
	 * 
	 * @exception DBBeanException if key is null
	 */
	public Map<String, TaskBean> loadAllTasksForOneUser(String _idUser) throws DBBeanException {

		String METHOD = "loadAllTasksForOneUser";
		String METHOD_FULL = ": M=public Map<String, TaskBean> " + METHOD + "(String _idUser)";

		// Bean to return corresponding at the object to load
		Map<String, TaskBean> tasksToReturn = new HashMap<String, TaskBean>();

		// acces base
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			// get a connexion necessarly without transaction for a consultation
			// transactionID == 0 => no transaction
			connection = this.getConnection(0);

			// Used request
			String SQL_LOAD_ALL_TASKS_FOR_ONE_USER = 
				"SELECT * FROM " + TASKS + " WHERE " + TASK_USER + "='" + _idUser + "' ORDER BY " + TASK_CREATION + " DESC"; 

			// init preparedStatement with the request and the connection
			pstmt = connection.prepareStatement(SQL_LOAD_ALL_TASKS_FOR_ONE_USER);

			// Execute request
			rs = pstmt.executeQuery();

			//Fill the bean to return

			{if (rs != null) 
				while ( rs.next() ) { 
					// on recupere les valeurs pour completer le bean
					TaskBean task = new TaskBean();
					task = setTaskBean (rs, ORIGINE + METHOD_FULL, false, false);

					tasksToReturn.put(task.getId(), task);
				} //   while ( rs() ) { 
			} // if (rs != null) 
		} catch (SQLException exception) {
			log.info("\n" + ORIGINE + " : " + METHOD + " : SQLException : " + exception.getLocalizedMessage()); 
			throw new DBBeanException(exception.getMessage(),
					DBBeanException.ERR_LOAD_SQL, ORIGINE
					+ METHOD_FULL);
		} finally {
			try {
				// dans tous les cas, on libere la connexion et le
				// preparedStatement
				// transactionID == 0 car pas de transaction
				// DataBase dataBase = this.getDataBase();
				// this.closeConnexion(dataBase, 0);
				this.closeConnexion(connection, 0);

				if (pstmt != null) {
					this.closePreparedStatement(pstmt, INFOS_CLASSE, log, METHOD_FULL);
				} 

				if (rs != null) {
					this.closeResultSet(rs, INFOS_CLASSE, log, METHOD_FULL);
				}
			} catch (Exception exception) {
				// La liberation de la connexion s'est mal passee
				log.info("\n" + ORIGINE + " : " + METHOD + " : Exception : " + exception.getLocalizedMessage()); 
				throw new DBBeanException(
						exception.getMessage(),
						DBBeanException.ERR_FERMER_CONN,
						ORIGINE + METHOD_FULL);
			}
		}

		// return the results
		return tasksToReturn;

	} // public Map<String, TaskBean> loadAllTasksForOneUser(String _idUser) throws DBBeanException {

	/**
	 * Load several tasks from database
	 * 
	 * @param _start (int)		: starting point (the first record is 0)
	 * @param _duration  (int) 	: duration (how many records to display)
	 * 
	 * @return Map<String, TaskBean> : loaded tasks from database
	 * 
	 * @exception DBBeanException if key is null
	 */
	public Map<String, TaskBean> loadSeveralTasks(int _start, int _duration) throws DBBeanException {

		String METHOD = "loadSeveralTasks";
		String METHOD_FULL = ": M=public Map<String, TaskBean> " + METHOD + "(int _start, int _duration)";

		// Bean to return corresponding at the object to load
		Map<String, TaskBean> tasksToReturn = new HashMap<String, TaskBean>();

		// acces base
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			// get a connexion necessarly without transaction for a consultation
			// transactionID == 0 => no transaction
			connection = this.getConnection(0);

			// Used request
			String SQL_LOAD_SEVERAL_TASKS = "SELECT * FROM " + TASKS 
			+ " ORDER BY " + TASK_CREATION 
			+ " DESC LIMIT " + _start + " , " + _duration; 

			// init preparedStatement with the request and the connection
			pstmt = connection.prepareStatement(SQL_LOAD_SEVERAL_TASKS);

			// Execute request
			rs = pstmt.executeQuery();

			//Fill the bean to return

			{if (rs != null) 
				while ( rs.next() ) { 
					// on recupere les valeurs pour completer le bean
					TaskBean task = new TaskBean();
					task = setTaskBean (rs, ORIGINE + METHOD_FULL, false, false);

					tasksToReturn.put(task.getId(), task);
				} //   while ( rs() ) { 
			} // if (rs != null) 
		} catch (SQLException exception) {
			log.info("\n" + ORIGINE + " : " + METHOD + " : SQLException : " + exception.getLocalizedMessage()); 
			throw new DBBeanException(exception.getMessage(),
					DBBeanException.ERR_LOAD_SQL, ORIGINE + METHOD_FULL);
		} finally {
			try {
				// dans tous les cas, on libere la connexion et le
				// preparedStatement
				// transactionID == 0 car pas de transaction
				// DataBase dataBase = this.getDataBase();
				// this.closeConnexion(dataBase, 0);
				this.closeConnexion(connection, 0);

				if (pstmt != null) {
					this.closePreparedStatement(pstmt, INFOS_CLASSE, log, METHOD_FULL);
				} 

				if (rs != null) {
					this.closeResultSet(rs, INFOS_CLASSE, log, METHOD_FULL);
				}
			} catch (Exception exception) {
				// La liberation de la connexion s'est mal passee
				log.info("\n" + ORIGINE + " : " + METHOD + " : Exception : " + exception.getLocalizedMessage()); 
				throw new DBBeanException(
						exception.getMessage(),
						DBBeanException.ERR_FERMER_CONN,
						ORIGINE + METHOD_FULL);
			}
		}

		// return the results
		return tasksToReturn;

	} // public Map<String, TaskBean> loadSeveralTasks(int _start, int _duration) throws DBBeanException {

	/**
	 * Load several tasks from database for one user
	 * 
	 * @param _idUser (String)	: user name
	 * @param _start (int)		: starting point (the first record is 0)
	 * @param _duration  (int) 	: duration (how many records to display)
	 * 
	 * @return Map<String, TaskBean> : loaded tasks from database
	 * 
	 * @exception DBBeanException if key is null
	 */
	public Map<String, TaskBean> loadSeveralTasksForOneUser(String _idUser, int _start, int _duration) throws DBBeanException {

		String METHOD = "loadSeveralTasksForOneUser";
		String METHOD_FULL = ": M=public Map<String, TaskBean> " + METHOD + "(String _idUser, int _start, int _duration)";

		// Bean to return corresponding at the object to load
		Map<String, TaskBean> tasksToReturn = new HashMap<String, TaskBean>();

		// acces base
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			// get a connexion necessarly without transaction for a consultation
			// transactionID == 0 => no transaction
			connection = this.getConnection(0);

			// Used request
			String SQL_LOAD_SEVERAL_TASKS = "SELECT * FROM " + TASKS 
			+ " WHERE " + TASK_USER + "='" + _idUser + "'"
			+ " ORDER BY " + TASK_CREATION 
			+ " DESC LIMIT " + _start + " , " + _duration; 

			// init preparedStatement with the request and the connection
			pstmt = connection.prepareStatement(SQL_LOAD_SEVERAL_TASKS);

			// Execute request
			rs = pstmt.executeQuery();

			//Fill the bean to return

			{if (rs != null) 
				while ( rs.next() ) { 
					// on recupere les valeurs pour completer le bean
					TaskBean task = new TaskBean();
					task = setTaskBean (rs, ORIGINE + METHOD_FULL, false, false);

					tasksToReturn.put(task.getId(), task);
				} //   while ( rs() ) { 
			} // if (rs != null) 
		} catch (SQLException exception) {
			log.info("\n" + ORIGINE + " : " + METHOD + " : SQLException : " + exception.getLocalizedMessage()); 
			throw new DBBeanException(exception.getMessage(),
					DBBeanException.ERR_LOAD_SQL, ORIGINE + METHOD_FULL);
		} finally {
			try {
				// dans tous les cas, on libere la connexion et le
				// preparedStatement
				// transactionID == 0 car pas de transaction
				// DataBase dataBase = this.getDataBase();
				// this.closeConnexion(dataBase, 0);
				this.closeConnexion(connection, 0);

				if (pstmt != null) {
					this.closePreparedStatement(pstmt, INFOS_CLASSE, log, METHOD_FULL);
				} 

				if (rs != null) {
					this.closeResultSet(rs, INFOS_CLASSE, log, METHOD_FULL);
				}
			} catch (Exception exception) {
				// La liberation de la connexion s'est mal passee
				log.info("\n" + ORIGINE + " : " + METHOD + " : Exception : " + exception.getLocalizedMessage()); 
				throw new DBBeanException(
						exception.getMessage(),
						DBBeanException.ERR_FERMER_CONN,
						ORIGINE + METHOD_FULL);
			}
		}

		// return the results
		return tasksToReturn;

	} // public Map<String, TaskBean> loadSeveralTasksForOneUser(int _start, int _duration) throws DBBeanException {

	/**
	 * Load all updated task from database
	 * 
	 * @param _idUser (String)	: user name
	 * 
	 * @return Map<String, TaskBean> : loaded tasks from database
	 * 
	 * @exception DBBeanException if key is null
	 */
	public Map<String, TaskBean> loadAllUpdatedTasksForOneUser(String _idUser) throws DBBeanException {

		String METHOD = "loadAllUpdatedTasksForOneUser";
		String METHOD_FULL = ": M=public Map<String, TaskBean> " + METHOD + "(String _idUser)";

		// Bean to return corresponding at the object to load
		Map<String, TaskBean> tasksToReturn = new HashMap<String, TaskBean>();

		// acces base
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			// get a connexion necessarly without transaction for a consultation
			// transactionID == 0 => no transaction
			connection = this.getConnection(0);

			// Used request
			String SQL_LOAD_ALL_TASKS_FOR_ONE_USER = 
				"SELECT * FROM " + TASKS 
				+ " WHERE " + TASK_USER + "='" + _idUser 
				+  " AND " + TASK_UPDATED + "='1' " 
				+ "' ORDER BY " + TASK_CREATION + " DESC"; 

			// init preparedStatement with the request and the connection
			pstmt = connection.prepareStatement(SQL_LOAD_ALL_TASKS_FOR_ONE_USER);

			// Execute request
			rs = pstmt.executeQuery();

			//Fill the bean to return

			{if (rs != null) 
				while ( rs.next() ) { 
					// on recupere les valeurs pour completer le bean
					TaskBean task = new TaskBean();
					task = setTaskBean (rs, ORIGINE + METHOD_FULL, false, false);

					tasksToReturn.put(task.getId(), task);
				} //   while ( rs() ) { 
			} // if (rs != null) 
		} catch (SQLException exception) {
			log.info("\n" + ORIGINE + " : " + METHOD + " : SQLException : " + exception.getLocalizedMessage()); 
			throw new DBBeanException(exception.getMessage(),
					DBBeanException.ERR_LOAD_SQL, ORIGINE
					+ METHOD_FULL);
		} finally {
			try {
				// dans tous les cas, on libere la connexion et le
				// preparedStatement
				// transactionID == 0 car pas de transaction
				// DataBase dataBase = this.getDataBase();
				// this.closeConnexion(dataBase, 0);
				this.closeConnexion(connection, 0);

				if (pstmt != null) {
					this.closePreparedStatement(pstmt, INFOS_CLASSE, log, METHOD_FULL);
				} 

				if (rs != null) {
					this.closeResultSet(rs, INFOS_CLASSE, log, METHOD_FULL);
				}
			} catch (Exception exception) {
				// La liberation de la connexion s'est mal passee
				log.info("\n" + ORIGINE + " : " + METHOD + " : Exception : " + exception.getLocalizedMessage()); 
				throw new DBBeanException(
						exception.getMessage(),
						DBBeanException.ERR_FERMER_CONN,
						ORIGINE + METHOD_FULL);
			}
		}

		// return the results
		return tasksToReturn;

	} // public Map<String, TaskBean> loadAllUpdatedTasksForOneUser(String _idUser) throws DBBeanException {

	// Used request
	/**
	 * Request Status :
	 * ACCEPTED
	 * INEXECUTION
	 * COMPLETED 
	 * PENDING
	 * RESERVED
     * EXPIRED
	 * CANCELLED
	 * FAILED
	 * REJECTED
	 * NEW_DATA
	 * 
	 * Segment or Cell Status :
	 * POTENTIAL
	 * PLANNED
	 * ACQUIRED
	 * VALIDATED
	 * CANCELLED
	 * REJECTED
	 * FAILED
	 */
	static final String SQL_LOAD_ALL_UNFINISHED_TASKS = "( SELECT * FROM " + TASKS 
				+ " WHERE " + TASK_REQUEST_STATUS + " IN ("
				+	" '" + Sps2GeneralConstants.STATUS_INEXECUTION + "', " 
				+	" '" + Sps2GeneralConstants.STATUS_PENDING + "', " 
				+	" '" + Sps2GeneralConstants.STATUS_ACCEPTED + "', " 
				+	" '" + Sps2GeneralConstants.STATUS_RESERVED + "' " 
				+ ") "
				+ " AND " + TASK_STATUS + " IN ("
				+	" '', " 
				+	" '" + Sps2GeneralConstants.STATUS_PLANNED + "', " 
				+	" '" + Sps2GeneralConstants.STATUS_INEXECUTION + "', " 
				+	" '" + Sps2GeneralConstants.STATUS_FAILED + "', " 
				+	" '" + Sps2GeneralConstants.STATUS_POTENTIAL + "', " 
				+	" '" + Sps2GeneralConstants.STATUS_VALIDATED + "' " 
				+ ")"
				+ " AND " + TASK_OPERATION + " NOT IN ("
				+	" '', " 
				+	" '" + Sps2GeneralConstants.SPS_CANCEL + "', " 
				+	" '" + Sps2GeneralConstants.SPS_VALIDATE + "' " 
				+ ") ) "
				+ " UNION "
				+ "( SELECT * FROM " + TASKS 
				+ " WHERE " + TASK_REQUEST_STATUS + " IN ("
				+	" '" + Sps2GeneralConstants.STATUS_INEXECUTION + "', " 
				+	" '" + Sps2GeneralConstants.STATUS_PENDING + "', " 
				+	" '" + Sps2GeneralConstants.STATUS_ACCEPTED + "', " 
				+	" '" + Sps2GeneralConstants.STATUS_RESERVED + "' " 
				+ ") "
				+ " AND " + TASK_STATUS + " IN ("
				+	" '', " 
				+	" '" + Sps2GeneralConstants.STATUS_PLANNED + "', " 
				+	" '" + Sps2GeneralConstants.STATUS_INEXECUTION + "', " 
				+	" '" + Sps2GeneralConstants.STATUS_FAILED + "', " 
				+	" '" + Sps2GeneralConstants.STATUS_POTENTIAL + "', " 
				+	" '" + Sps2GeneralConstants.STATUS_VALIDATED + "' " 
				+ ")"
				+ " AND " + TASK_OPERATION + " = '" + Sps2GeneralConstants.SPS_CANCEL + "' )"; 
	
	/**
	 * Load one task from database
	 * 
	 * @return Map<String, TaskBean> : loaded tasks from database
	 * 
	 * @exception DBBeanException if key is null
	 */
	public Map<String, TaskBean> loadAllUnFinishedTasks() throws DBBeanException {

		String METHOD = "loadAllUnFinishedTasks";
		String METHOD_FULL = ": M=public Map<String, TaskBean> " + METHOD + "()";

		// Bean to return corresponding at the object to load
		Map<String, TaskBean> tasksToReturn = new HashMap<String, TaskBean>();

		// acces base
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			// get a connexion necessarly without transaction for a consultation
			// transactionID == 0 => no transaction
			connection = this.getConnection(0);

			// init preparedStatement with the request and the connection
			pstmt = connection.prepareStatement(SQL_LOAD_ALL_UNFINISHED_TASKS);

			// Execute request
			rs = pstmt.executeQuery();

			//Fill the bean to return

			{if (rs != null) 
				while ( rs.next() ) { 
					// on recupere les valeurs pour completer le bean
					TaskBean task = new TaskBean();
					task = setTaskBean (rs, ORIGINE + METHOD_FULL, false, false);

					tasksToReturn.put(task.getId(), task);
				} //   while ( rs() ) { 
			} // if (rs != null) 
		} catch (SQLException exception) {
			log.info("\n" + ORIGINE + " : " + METHOD + " : SQLException : " + exception.getLocalizedMessage()); 
			throw new DBBeanException(exception.getMessage(),
					DBBeanException.ERR_LOAD_SQL, ORIGINE
					+ METHOD_FULL);
		} finally {
			try {
				// dans tous les cas, on libere la connexion et le
				// preparedStatement
				// transactionID == 0 car pas de transaction
				// DataBase dataBase = this.getDataBase();
				// this.closeConnexion(dataBase, 0);
				this.closeConnexion(connection, 0);

				if (pstmt != null) {
					this.closePreparedStatement(pstmt, INFOS_CLASSE, log, METHOD_FULL);
				} 

				if (rs != null) {
					this.closeResultSet(rs, INFOS_CLASSE, log, METHOD_FULL);
				}
			} catch (Exception exception) {
				// La liberation de la connexion s'est mal passee
				log.info("\n" + ORIGINE + " : " + METHOD + " : Exception : " + exception.getLocalizedMessage()); 
				throw new DBBeanException(
						exception.getMessage(),
						DBBeanException.ERR_FERMER_CONN,
						ORIGINE + METHOD_FULL);
			}
		}

		// return the results
		return tasksToReturn;

	} // public Map<String, TaskBean> loadAllUnFinishedTasks() throws DBBeanException {
	
	/**
	 * Delete one Task in DataBase
	 *
	 * @param _taskId (String) 		: task identifier
	 * @param _transactionId (long) : transaction referency
	 * 
	 * @exception DBBeanException if null key
	 */
	public void delete(String _taskId, long _transactionId) throws DBBeanException {

		String METHOD = "delete";
		String METHOD_FULL = ": M=public void " + METHOD + "(String _taskId, long _transactionId)";

		// acces base
		Connection connection = null;
		PreparedStatement pstmt = null;

		try {
			// on recupere une connexion en tenant compte de l'utilisation
			// d'une transaction
			// transactionID == 0 => pas de transaction
			connection = getConnection(_transactionId);

			// on initialise le preparedStatement avec la requete et la
			// connection
			// use request for deleting one task in database
			String deleteRequest = "DELETE FROM " + TASKS + " WHERE " + TASK_ID + " = '" + _taskId + "' ";

			pstmt = connection.prepareStatement(deleteRequest);

			// Execute request
			pstmt.execute();
		} catch (SQLException e) {
			throw new DBBeanException(
					e.getMessage(),
					DBBeanException.ERR_DELETE_SQL,
					ORIGINE + METHOD_FULL);
		} catch (Exception e) {
			throw new DBBeanException(
					e.getMessage(),
					DBBeanException.ERR_DELETE_SQL,
					ORIGINE + METHOD_FULL);
		} finally {
			try {
				// dans tous les cas, on libere la connexion et le preparedStatement
				// this.getDataBase().commitTransaction();
				// le commit est gere dans la transaction ainsi que la fermeture de la connexion
				// this.closeConnexion(this.getDataBase(), transactionId);
				// this.closeConnexion(connection, transactionId);

				if (pstmt != null) {
					this.closePreparedStatement(pstmt, INFOS_CLASSE, log, METHOD);
				}
			} catch (Exception e) {
				//La liberation de la connexion s'est mal passee
				throw new DBBeanException(
						e.getMessage(),
						DBBeanException.ERR_FERMER_CONN,
						ORIGINE + METHOD_FULL);
			}
		}

	} // public void delete(String _taskId, long _transactionId)

	/**
	 * 
	 * tester la connection a la base de donn&eacute;es
	 * 
	 * @return String Test KO si connection impossible, OK sinon
	 * @exception DBBeanException si la cl&eacute; est nulle
	 */
	public String testConnection() throws DBBeanException {

		String METHOD = "testConnection";
		String METHOD_FULL = ": M=public void " + METHOD + "()";

		String errorString = "Test KO";

		// acces base
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			// get a connexion necessarly without transaction for a consultation
			// transactionID == 0 => no transaction
			connection = this.getConnection(0);

			// init preparedStatement with the request and the connexion
			pstmt = connection.prepareStatement(SQL_LOAD_ONE_TASK);

			// Execute the request
			rs = pstmt.executeQuery();

			// Fill bean to return
			if (rs != null) {
				if (rs.next()) {
					errorString = "OK";
				}
			}

		} catch (SQLException exception) {
			throw new DBBeanException(exception.getMessage(),
					DBBeanException.ERR_LOAD_SQL, ORIGINE + METHOD_FULL);
		} finally {
			try {
				// dans tous les cas, on libere la connexion et le
				// preparedStatement
				// transactionID == 0 car pas de transaction
				// DataBase dataBase = this.getDataBase();
				// this.fermerConnexion(dataBase, 0);
				this.closeConnexion(connection, 0);

				if (pstmt != null) {
					this.closePreparedStatement(pstmt, INFOS_CLASSE, log, METHOD);
				} 

				if (rs != null) {
					this.closeResultSet(rs, INFOS_CLASSE, log, METHOD);
				}
			} catch (Exception exception) {
				//La liberation de la connexion s'est mal passee
				throw new DBBeanException(
						exception.getMessage(),
						DBBeanException.ERR_FERMER_CONN,
						ORIGINE + METHOD_FULL);
			}
		}

		return errorString;

	} // public String testConnection() throws DBBeanException {

	/**
	 * set one Task from DataBase data
	 *
	 * @param _resultSet (ResultSet)	: data
	 * @param _from (String)			: exception initiator
	 * @param _request (boolean)		: true to set the request attribute, false else
	 * @param _response (boolean)		: true to set the response attribute, false else
	 * 
	 * @return TaskBean : the object to fill
	 * 
	 * @throws DBBeanException 
	 */
	private TaskBean setTaskBean (ResultSet _resultSet, String _from, boolean _request, boolean _response) throws DBBeanException {

		TaskBean task = new TaskBean();

		try {
			task.setId(_resultSet.getString(TASK_ID));
			task.setUser(_resultSet.getString(TASK_USER));
			task.setName(_resultSet.getString(TASK_NAME));
			//			String theDate1 = _resultSet.getTimestamp(TASK_CREATION).toLocaleString();
			//			String theDate2 = _resultSet.getTimestamp(TASK_CREATION).toGMTString();
			//			String theDate3 = _resultSet.getTimestamp(TASK_CREATION).toString();
			task.setCreatedTime(_resultSet.getTimestamp(TASK_CREATION));
			task.setOperation(_resultSet.getString(TASK_OPERATION));
			task.setStatus(_resultSet.getString(TASK_STATUS));
			task.setLastUpdatedTime(_resultSet.getTimestamp(TASK_MODIFICATION));
			task.setSensor(_resultSet.getString(TASK_SENSOR));
			
			if (_resultSet.getInt(TASK_UPDATED) == 1) {
				task.setUpdatedTask(true);
			} else {
				task.setUpdatedTask(false);
			} // if

			if (_request) {
				task.setRequest(_resultSet.getString(TASK_REQUEST));
			} // if

			task.setRequestStatus(_resultSet.getString(TASK_REQUEST_STATUS));

			if (_response) {
				task.setResponse(_resultSet.getString(TASK_RESPONSE));
				task.setResponseType(_resultSet.getString(TASK_RESPONSE_TYPE));
			} // if

			return task;
		} catch (SQLException e) {
			throw new DBBeanException(
					e.getMessage(),
					DBBeanException.ERR_DELETE_SQL,
					_from);
		} catch (Exception e) {
			throw new DBBeanException(
					e.getMessage(),
					DBBeanException.ERR_DELETE_SQL,
					_from);
		} // try

	} // private TaskBean setTaskBean (ResultSet _resultSet, String _from, boolean _request, boolean _response) throws DBBeanException {

} // class
