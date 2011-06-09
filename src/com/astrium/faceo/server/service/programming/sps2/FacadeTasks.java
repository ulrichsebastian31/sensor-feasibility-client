package com.astrium.faceo.server.service.programming.sps2;


// import 
import java.util.HashMap;
import java.util.Map;

import net.opengis.www.eosps._2_0.ValidateResponseDocument;
import net.opengis.www.sps._2_0.GetFeasibilityDocument;
import net.opengis.www.sps._2_0.GetFeasibilityResponseDocument;
import net.opengis.www.sps._2_0.GetStatusResponseDocument;
import net.opengis.www.sps._2_0.ReserveDocument;
import net.opengis.www.sps._2_0.ReserveResponseDocument;
import net.opengis.www.sps._2_0.SubmitDocument;
import net.opengis.www.sps._2_0.SubmitResponseDocument;
import net.opengis.www.sps._2_0.UpdateDocument;
import net.opengis.www.sps._2_0.UpdateResponseDocument;

import org.apache.log4j.Logger;

import com.astrium.faceo.client.bean.programming.sps2.ErrorBean;
import com.astrium.faceo.client.bean.programming.sps2.TaskBean;
import com.astrium.faceo.client.bean.programming.sps2.TaskResultBean;
import com.astrium.faceo.client.bean.programming.sps2.request.TaskingParametersBean;
import com.astrium.faceo.client.common.programming.sps2.Sps2GeneralConstants;
import com.astrium.faceo.server.bean.exception.DBBeanException;
import com.astrium.faceo.server.bean.exception.TaskException;
import com.astrium.faceo.server.common.exception.OMException;
import com.astrium.faceo.server.common.exception.sps2.FacadeSps2Exception;
import com.astrium.faceo.server.dataBaseAccess.TaskDB;
import com.astrium.faceo.server.dataBaseAccess.common.DataBase;
import com.astrium.faceo.server.dataBaseAccess.transaction.TransactionDB;
import com.astrium.faceo.server.dataBaseAccess.transaction.TransactionFactory;
import com.astrium.faceo.server.service.programming.sps2.parsing.ParsingTasking;
import com.astrium.faceo.server.service.programming.sps2.parsing.ParsingUtils;


/**
 * <B>SITE FACEO</B> <BR>
 * 
 * <P>
 * La classe FacadeTasks reprend les services utilis&eacute;s pendant le 
 * processus de publication d'une t&agrave;che (SPS).
 * </P>
 * </P>
 * 
 * @author ASTRIUM
 * @version 1.0, le 14/10/2008
 */
public class FacadeTasks {
	/** class logger */
	private static Logger log = Logger.getLogger(FacadeTasks.class);

	String INFOS_CLASSE = "FacadeTasks"; // Pour message dans fichier de log

	/** referncy on database */
	DataBase transDatabase = null;

	/**
	 * Constructor
	 */
	public FacadeTasks() {    
	}

	/**
	 * to save a task in database
	 * 
	 * @param _taskBean (TaskBean) :  bean with task informations
	 * 
	 * @throws TaskException
	 */
	public void insertTask(TaskBean _taskBean) throws TaskException {
		String METHOD = "insertTask";

		long transactionId = 0;
		TransactionFactory transactionFactory = null;
		TransactionDB transactionDB = null;

		try {
			if (_taskBean != null) {
				// On cree une transaction
				// maConnection.getTransactionIsolation(); */

				transactionFactory = TransactionFactory.getInstance();
				transactionId = transactionFactory.createTransaction();
				transactionDB = transactionFactory.getTransaction(transactionId);

				// objet utilise pour stocker les infos de l'objet dans la base de donnees
				// Demander une reference sur l'objet correspondant
				TaskDB taskDB = TaskDB.getInstance();

				// create new task in database
				taskDB.insert(_taskBean, transactionId);
				//-------------------------------------------------------------------------

				//Tout s'est bien passe donc on commit
				transactionDB.commit();
			} // if (_taskBean != null) {
		} catch(DBBeanException ex) {
			log.info("\n" + INFOS_CLASSE + " : " + METHOD + " : DBBeanException : " + ex.getMessage());    
			this.rollback(transactionDB);
			throw new TaskException(ex.getMessage(), " : " + INFOS_CLASSE + " : " + METHOD);
		} catch(OMException ex) {
			log.info("\n" + INFOS_CLASSE + " : " + METHOD + " : OMException : " + ex.getMessage());    
			this.rollback(transactionDB);
			throw new TaskException(ex.getMessage(), " : " + INFOS_CLASSE + " : " + METHOD);
		} catch(Exception ex) {
			log.info("\n" + INFOS_CLASSE + " : " + METHOD + " : Exception : " + ex.getMessage());    
			this.rollback(transactionDB);
			throw new TaskException(ex.getMessage(),  " : " + INFOS_CLASSE + " : " + METHOD);
		}
	} // public void insertTask(TaskBean _taskBean) throws TaskException {

	/**
	 * to update a task in database
	 * 
	 * @param _taskBean (TaskBean) :  bean with task informations
	 * 
	 * @throws TaskException
	 */
	public void updateTask(TaskBean _taskBean) throws TaskException {
		String METHOD = "updateTask";

		long transactionId = 0;
		TransactionFactory transactionFactory = null;
		TransactionDB transactionDB = null;

		try {
			if (_taskBean != null) {
				// On cree une transaction
				// maConnection.getTransactionIsolation(); */

				transactionFactory = TransactionFactory.getInstance();
				transactionId = transactionFactory.createTransaction();
				transactionDB = transactionFactory.getTransaction(transactionId);

				// objet utilise pour stocker les infos de l'objet dans la base de donnees
				// Demander une reference sur l'objet correspondant
				TaskDB taskDB = TaskDB.getInstance();

				// update a task in database
				taskDB.update(_taskBean, transactionId);
				//-------------------------------------------------------------------------

				//Tout s'est bien passe donc on commit
				transactionDB.commit();
			} // if (_taskBean != null) {
		} catch(DBBeanException ex) {
			log.info("\n" + INFOS_CLASSE + " : " + METHOD + " : DBBeanException : " + ex.getMessage());    
			this.rollback(transactionDB);
			throw new TaskException(ex.getMessage(), " : " + INFOS_CLASSE + " : " + METHOD);
		} catch(OMException ex) {
			log.info("\n" + INFOS_CLASSE + " : " + METHOD + " : OMException : " + ex.getMessage());    
			this.rollback(transactionDB);
			throw new TaskException(ex.getMessage(), " : " + INFOS_CLASSE + " : " + METHOD);
		} catch(Exception ex) {
			log.info("\n" + INFOS_CLASSE + " : " + METHOD + " : Exception : " + ex.getMessage());    
			this.rollback(transactionDB);
			throw new TaskException(ex.getMessage(),  " : " + INFOS_CLASSE + " : " + METHOD);
		}
	} // public void updateTask(TaskBean _taskBean) throws TaskException {
	
	/**
	 * to update a task in database for UPDATE operation
	 * 
	 * @param _taskBean (TaskBean) :  bean with task informations
	 * 
	 * @throws TaskException
	 */
	public void modifyTask(TaskBean _taskBean) throws TaskException {
		String METHOD = "modifyTask";

		long transactionId = 0;
		TransactionFactory transactionFactory = null;
		TransactionDB transactionDB = null;

		try {
			if (_taskBean != null) {
				// On cree une transaction
				// maConnection.getTransactionIsolation(); */

				transactionFactory = TransactionFactory.getInstance();
				transactionId = transactionFactory.createTransaction();
				transactionDB = transactionFactory.getTransaction(transactionId);

				// objet utilise pour stocker les infos de l'objet dans la base de donnees
				// Demander une reference sur l'objet correspondant
				TaskDB taskDB = TaskDB.getInstance();

				// update a task in database
				taskDB.modify(_taskBean, transactionId);
				//-------------------------------------------------------------------------

				//Tout s'est bien passe donc on commit
				transactionDB.commit();
			} // if (_taskBean != null) {
		} catch(DBBeanException ex) {
			log.info("\n" + INFOS_CLASSE + " : " + METHOD + " : DBBeanException : " + ex.getMessage());    
			this.rollback(transactionDB);
			throw new TaskException(ex.getMessage(), " : " + INFOS_CLASSE + " : " + METHOD);
		} catch(OMException ex) {
			log.info("\n" + INFOS_CLASSE + " : " + METHOD + " : OMException : " + ex.getMessage());    
			this.rollback(transactionDB);
			throw new TaskException(ex.getMessage(), " : " + INFOS_CLASSE + " : " + METHOD);
		} catch(Exception ex) {
			log.info("\n" + INFOS_CLASSE + " : " + METHOD + " : Exception : " + ex.getMessage());    
			this.rollback(transactionDB);
			throw new TaskException(ex.getMessage(),  " : " + INFOS_CLASSE + " : " + METHOD);
		}
	} // public void modifyTask(TaskBean _taskBean) throws TaskException {

	/**
	 * to set a task as readed in database
	 * 
	 * @param _taskBean (TaskBean) :  bean with task informations
	 * 
	 * @throws TaskException
	 */
	public void setTaskAsReaded(TaskBean _taskBean) throws TaskException {
		String METHOD = "setTaskAsReaded";

		long transactionId = 0;
		TransactionFactory transactionFactory = null;
		TransactionDB transactionDB = null;

		try {
			if (_taskBean != null) {
				// On cree une transaction
				// maConnection.getTransactionIsolation(); */

				transactionFactory = TransactionFactory.getInstance();
				transactionId = transactionFactory.createTransaction();
				transactionDB = transactionFactory.getTransaction(transactionId);

				// objet utilise pour stocker les infos de l'objet dans la base de donnees
				// Demander une reference sur l'objet correspondant
				TaskDB taskDB = TaskDB.getInstance();

				// update a task in database
				taskDB.setTaskAsReaded(_taskBean, transactionId);
				//-------------------------------------------------------------------------

				//Tout s'est bien passe donc on commit
				transactionDB.commit();
			} // if (_taskBean != null) {
		} catch(DBBeanException ex) {
			log.info("\n" + INFOS_CLASSE + " : " + METHOD + " : DBBeanException : " + ex.getMessage());    
			this.rollback(transactionDB);
			throw new TaskException(ex.getMessage(), " : " + INFOS_CLASSE + " : " + METHOD);
		} catch(OMException ex) {
			log.info("\n" + INFOS_CLASSE + " : " + METHOD + " : OMException : " + ex.getMessage());    
			this.rollback(transactionDB);
			throw new TaskException(ex.getMessage(), " : " + INFOS_CLASSE + " : " + METHOD);
		} catch(Exception ex) {
			log.info("\n" + INFOS_CLASSE + " : " + METHOD + " : Exception : " + ex.getMessage());    
			this.rollback(transactionDB);
			throw new TaskException(ex.getMessage(),  " : " + INFOS_CLASSE + " : " + METHOD);
		}
	} // public void setTaskAsReaded(TaskBean _taskBean) throws TaskException {

	/**
	 * This method submit a FACEO 'GetTaskResults' operation
	 * 
	 * @param _idUser (String) 		: task's user
	 * @param _idTask (String) 		: task's identifier
	 * @param _serverURL (String) 	: server URL
	 * @param _taskingParameters (TaskingParametersBean)	: tasking parameters for one sensor
	 * 
	 * @return TaskResultBean : the task response
	 * 
	 * @throws FacadeSps2Exception
	 */
	public TaskResultBean getTaskResults (
			String _idUser, String _idTask, String _serverURL, TaskingParametersBean _taskingParameters) 
	throws FacadeSps2Exception {
		TaskResultBean taskResponse = new TaskResultBean();

		try {
			TaskBean taskBean = loadOneTask(_idTask);

			if (taskBean != null) {
				if (! taskBean.getId().equalsIgnoreCase("")) {
					taskResponse = getTaskResponse(taskBean, _serverURL, _taskingParameters);
					
					taskBean.setUpdatedTask(false);
					setTaskAsReaded(taskBean);
				} // if (! taskBean.getId().equalsIgnoreCase("")) {
			} // if (taskBean != null) {

			return taskResponse;
		} catch (Exception exception) {
			taskResponse.getError().setMessage("Exception : " + exception.getMessage());
			taskResponse.getError().setCode(FacadeSps2Exception.SPS_EXCEPTION);
			return taskResponse;
		}
	} // public TaskResultBean getTaskResults(String _idUser, String _idUser, String _idTask, String _serverURL, TaskingParametersBean _taskingParameters) throws FacadeSps2Exception {

	/**
	 * loading informations for all tasks in database
	 * 	 
	 * @return TaskBean : informations's task
	 * 
	 * @throws TaskException
	 */
	public Map<String, TaskBean> loadAllTasks() throws TaskException {
		String METHOD = "loadAllTasks";

		Map<String, TaskBean> tasksBean = new HashMap<String, TaskBean>();

		try {
			// objets utilises pour recuperer et stocker les infos de l'objet metier
			TaskDB taskDB = TaskDB.getInstance();

			// Load tasks informations
			tasksBean = taskDB.loadAllTasks();
		} catch(OMException exception) {
			log.info("\n" + INFOS_CLASSE + " : " + METHOD + " : OMException : " + exception.getMessage()); 
			throw new TaskException(exception.getMessage(), METHOD);
		} catch(Exception exception) {
			log.info("\n" + INFOS_CLASSE + " : " + METHOD + " : Exception : " + exception.getMessage()); 
			throw new TaskException(exception.getMessage(), METHOD);
		}

		return tasksBean;
	} // public Map<String, TaskBean> loadAllTasks() throws TaskException {

	/**
	 * To get the tasks list
	 * 
	 * @param _idUser (String) : id of the user
	 * 
	 * @return Map<String, TaskBean> : hashMap of tasks
	 * 
	 * @throws FacadeSps2Exception
	 */
	public Map<String, TaskBean> getTasksList(String _idUser) throws FacadeSps2Exception {
		Map<String, TaskBean> tasks = new HashMap<String, TaskBean>();

		try {
			tasks = loadAllTasksForOneUser(_idUser);

			return tasks;
		} catch (Exception exception) {
			TaskBean task = new TaskBean();
			task.getError().setMessage("Exception : " + exception.getMessage());
			tasks.put("0", task);
			log.info("\n" + INFOS_CLASSE + " : getTasksList : Exception : " + exception.getMessage()); 
			return tasks;
		}
	} // public Map<String, TaskBean> getTasksList(String _idUser) throws FacadeSps2Exception {

	/**
	 * To get the updated tasks list
	 * 
	 * @param _idUser (String) : id of the user
	 * 
	 * @return Map<String, TaskBean> : hashMap of tasks
	 * 
	 * @throws FacadeSps2Exception
	 */
	public Map<String, TaskBean> getUpdatedTasksList(String _idUser) throws FacadeSps2Exception {
		Map<String, TaskBean> tasks = new HashMap<String, TaskBean>();

		try {
			tasks = loadAllUpdatedTasksForOneUser(_idUser);

			return tasks;
		} catch (Exception exception) {
			TaskBean task = new TaskBean();
			task.getError().setMessage("Exception : " + exception.getMessage());
			tasks.put("0", task);
			log.info("\n" + INFOS_CLASSE + " : getUpdatedTasksList : Exception : " + exception.getMessage()); 
			return tasks;
		}
	} // public Map<String, TaskBean> getUpdatedTasksList(String _idUser) throws FacadeSps2Exception {

	/**
	 * To get the tasks list
	 * 
	 * @param _idUser (String) 	: id of the user
	 * @param _start (int)		: starting point (the first record is 0)
	 * @param _duration  (int) 	: duration (how many records to display)
	 * 
	 * @return Map<String, TaskBean> : hashMap of tasks
	 * 
	 * @throws FacadeSps2Exception
	 */
	public Map<String, TaskBean> getSeveralTasksList(String _idUser, int _start, int _duration) throws FacadeSps2Exception {
		Map<String, TaskBean> tasks = new HashMap<String, TaskBean>();

		try {
			tasks = loadSeveralTasks(_start, _duration);

			return tasks;
		} catch (Exception exception) {
			TaskBean task = new TaskBean();
			task.getError().setMessage("Exception : " + exception.getMessage());
			tasks.put("0", task);
			log.info("\n" + INFOS_CLASSE + " : getSeveralTasksList : Exception : " + exception.getMessage()); 
			return tasks;
		}
	} // public Map<String, TaskBean> getSeveralTasksList(String _idUser, int _start, int _duration) throws FacadeSps2Exception {

	/**
	 * To delete one task in database
	 * 
	 * @param _taskId (String) : task identifier
	 * 
	 * @return ErrorBean : 
	 * 
	 * @throws FacadeSps2Exception
	 */
	public ErrorBean deleteATask(String _taskId) throws FacadeSps2Exception {
		ErrorBean errorBean = new ErrorBean();
		errorBean.setError(false);

		try {
			deleteOneTask(_taskId);
		} catch (Exception exception) {
			errorBean.setError(true);
			errorBean.setMessage("Exception : " + exception.getMessage());
			errorBean.setCode(535);
			return errorBean;
		}

		return errorBean;
	} // public ErrorBean deleteOneTask(String _taskId) throws FacadeSps2Exception {

	/**
	 * loading informations for one task
	 * 
	 * @param _taskId (String) : task identifier
	 * 
	 * @return TaskBean : informations's task
	 * 
	 * @throws TaskException
	 */
	public TaskBean loadOneTask(String _taskId) throws TaskException {
		String METHOD = "loadOneTask";
		TaskBean taskBean = null;

		try {
			// objets utilises pour recuperer et stocker les infos de l'objet metier
			TaskDB taskDB = TaskDB.getInstance();

			// Get task informations
			taskBean = taskDB.loadOneTask(_taskId);
		} catch(OMException ex) {
			throw new TaskException(ex.getMessage(), METHOD);
		} catch(Exception ex) {
			throw new TaskException(ex.getMessage(), METHOD);
		}

		return taskBean;
	} // public TaskBean loadOneTask(String _taskId) throws TaskException {

	/**
	 * loading informations for all tasks in database
	 * 	 
	 * @param _idUser (String) : id of the user
	 * 
	 * @return TaskBean : informations's task
	 * 
	 * @throws TaskException
	 */
	private Map<String, TaskBean> loadAllTasksForOneUser(String _idUser) throws TaskException {
		String METHOD = "loadAllTasksForOneUser";

		Map<String, TaskBean> tasksBean = new HashMap<String, TaskBean>();

		try {
			// objets utilises pour recuperer et stocker les infos de l'objet metier
			TaskDB taskDB = TaskDB.getInstance();

			// Load tasks informations for one user
			tasksBean = taskDB.loadAllTasksForOneUser(_idUser);
		} catch(OMException exception) {
			log.info("\n" + INFOS_CLASSE + " : " + METHOD + " : OMException : " + exception.getMessage()); 
			throw new TaskException(exception.getMessage(), METHOD);
		} catch(Exception exception) {
			log.info("\n" + INFOS_CLASSE + " : " + METHOD + " : Exception : " + exception.getMessage()); 
			throw new TaskException(exception.getMessage(), METHOD);
		}

		return tasksBean;
	} // private Map<String, TaskBean> loadAllTasksForOneUser(String _idUser) throws TaskException {

	/**
	 * loading informations for all updated tasks in database
	 * 	 
	 * @param _idUser (String) : id of the user
	 * 
	 * @return TaskBean : informations's task
	 * 
	 * @throws TaskException
	 */
	private Map<String, TaskBean> loadAllUpdatedTasksForOneUser(String _idUser) throws TaskException {
		String METHOD = "loadAllUpdatedTasksForOneUser";

		Map<String, TaskBean> tasksBean = new HashMap<String, TaskBean>();

		try {
			// objets utilises pour recuperer et stocker les infos de l'objet metier
			TaskDB taskDB = TaskDB.getInstance();

			// Load tasks informations for one user
			tasksBean = taskDB.loadAllTasksForOneUser(_idUser);
		} catch(OMException exception) {
			log.info("\n" + INFOS_CLASSE + " : " + METHOD + " : OMException : " + exception.getMessage()); 
			throw new TaskException(exception.getMessage(), METHOD);
		} catch(Exception exception) {
			log.info("\n" + INFOS_CLASSE + " : " + METHOD + " : Exception : " + exception.getMessage()); 
			throw new TaskException(exception.getMessage(), METHOD);
		}

		return tasksBean;
	} // private Map<String, TaskBean> loadAllUpdatedTasksForOneUser(String _idUser) throws TaskException {

	/**
	 * loading informations for several tasks in database
	 * 	 
	 * @param _start (int)		: starting point (the first record is 0)
	 * @param _duration  (int) 	: duration (how many records to display)

	 * @return TaskBean : informations's task
	 * 
	 * @throws TaskException
	 */
	private Map<String, TaskBean> loadSeveralTasks(int _start, int _duration) throws TaskException {
		String METHOD = "loadSeveralTasks";

		Map<String, TaskBean> tasksBean = new HashMap<String, TaskBean>();

		try {
			// objets utilises pour recuperer et stocker les infos de l'objet metier
			TaskDB taskDB = TaskDB.getInstance();

			// Load tasks informations
			tasksBean = taskDB.loadSeveralTasks(_start, _duration);
		} catch(OMException exception) {
			log.info("\n" + INFOS_CLASSE + " : " + METHOD + " : OMException : " + exception.getMessage()); 
			throw new TaskException(exception.getMessage(), METHOD);
		} catch(Exception exception) {
			log.info("\n" + INFOS_CLASSE + " : " + METHOD + " : Exception : " + exception.getMessage()); 
			throw new TaskException(exception.getMessage(), METHOD);
		}

		return tasksBean;
	} // private Map<String, TaskBean> loadSeveralTasks(int _start, int _duration) throws TaskException {

	/**
	 * load unfinished tasks in database
	 * 	 
	 * @return TaskBean : informations's task
	 * 
	 * @throws TaskException
	 */
	public Map<String, TaskBean> loadAllUnFinishedTasks() throws TaskException {
		String METHOD = "loadAllUnFinishedTasks";
		
		Map<String, TaskBean> tasksBean = new HashMap<String, TaskBean>();

		try {
			// objets utilises pour recuperer et stocker les infos de l'objet metier
			TaskDB taskDB = TaskDB.getInstance();

			// Load tasks informations
			tasksBean = taskDB.loadAllUnFinishedTasks();
		} catch(OMException exception) {
			log.info("\n" + INFOS_CLASSE + " : " + METHOD + " : OMException : " + exception.getMessage()); 
			throw new TaskException(exception.getMessage(), METHOD);
		} catch(Exception exception) {
			log.info("\n" + INFOS_CLASSE + " : " + METHOD + " : Exception : " + exception.getMessage()); 
			throw new TaskException(exception.getMessage(), METHOD);
		}

		return tasksBean;
	} // public Map<String, TaskBean> loadAllUnFinishedTasks() throws TaskException {

	/**
	 * to delete one task in database
	 * 
	 * @param _taskId (String) :  task identifier
	 * 
	 * @throws TaskException
	 */
	private void deleteOneTask(String _taskId) throws TaskException {
		String METHOD = "deleteOneTask";

		long transactionId = 0;
		TransactionFactory transactionFactory = null;
		TransactionDB transactionDB = null;

		try {
			// On cree une transaction
			// maConnection.getTransactionIsolation(); */

			transactionFactory = TransactionFactory.getInstance();
			transactionId = transactionFactory.createTransaction();
			transactionDB = transactionFactory.getTransaction(transactionId);

			// objet utilise pour stocker les infos de l'objet dans la base de donnees
			// Demander une reference sur l'objet correspondant
			TaskDB taskDB = TaskDB.getInstance();

			// delete one task in database
			taskDB.delete(_taskId, transactionId);
			//-------------------------------------------------------------------------

			//Tout s'est bien passe donc on commit
			transactionDB.commit();
		} catch(DBBeanException ex) {
			log.info("\n" + INFOS_CLASSE + " : " + METHOD + " : DBBeanException : " + ex.getMessage());    
			this.rollback(transactionDB);
			throw new TaskException(ex.getMessage(), " : " + INFOS_CLASSE + " : " + METHOD);
		} catch(OMException ex) {
			log.info("\n" + INFOS_CLASSE + " : " + METHOD + " : OMException : " + ex.getMessage());    
			this.rollback(transactionDB);
			throw new TaskException(ex.getMessage(), " : " + INFOS_CLASSE + " : " + METHOD);
		} catch(Exception ex) {
			log.info("\n" + INFOS_CLASSE + " : " + METHOD + " : Exception : " + ex.getMessage());    
			this.rollback(transactionDB);
			throw new TaskException(ex.getMessage(),  " : " + INFOS_CLASSE + " : " + METHOD);
		}
	} // private void deleteOneTask(String _taskId) throws TaskException {

	/**
	 * parsing of the 'GetFeasibility' or 'Submit' response
	 * 
	 * @param _sensorId (String) 	: sensor identifier
	 * @param _response (String) 	: the xml response 'GetFeasibilityResponse' or 'SubmitResponse'
	 * @param _serverUrl (String) 	: server URL
	 * @param _taskingParameters (TaskingParametersBean)	: tasking parameters for one sensor
	 * 
	 * @return TaskResultBean : the task response
	 * 
	 * @throws FacadeSps2Exception
	 */
	private TaskResultBean getTaskResponse(TaskBean _taskBean, String _serverUrl, TaskingParametersBean _taskingParameters) throws FacadeSps2Exception {
		String METHOD = "getTaskResponse";

		TaskResultBean taskResponse = new TaskResultBean();

		try {
			String sensorType = _taskingParameters.getSensor().getType();

			ParsingUtils.setSensorsDescriptions(_serverUrl);

			// parse request and response
			ParsingTasking parsing = new ParsingTasking();

			taskResponse.setId(_taskBean.getId());
			taskResponse.setRequestStatus(_taskBean.getRequestStatus());
			taskResponse.setStatus(_taskBean.getStatus());
			taskResponse.setOperation(_taskBean.getOperation());

			// -------------------------------- request parsing --------------------------------
			if (_taskBean.getOperation().equalsIgnoreCase(Sps2GeneralConstants.SPS_GET_FEASIBILITY)) {
				// GetFeasibility XML document
				GetFeasibilityDocument getFeasibilityDocument = 
					GetFeasibilityDocument.Factory.parse(_taskBean.getRequest());

				taskResponse.setProgrammingRequestBean(
						parsing.parseTaskingRequest(_taskBean , sensorType, getFeasibilityDocument, _serverUrl, _taskingParameters, ParsingUtils.getSensors(_serverUrl)));
			} else if (_taskBean.getOperation().equalsIgnoreCase(Sps2GeneralConstants.SPS_SUBMIT)) {
				// SubmitDocument XML document
				SubmitDocument submitDocument = 
					SubmitDocument.Factory.parse(_taskBean.getRequest());

				taskResponse.setProgrammingRequestBean(
						parsing.parseTaskingRequest(_taskBean , sensorType, submitDocument, _serverUrl, _taskingParameters, ParsingUtils.getSensors(_serverUrl)));
			} else if ( (_taskBean.getOperation().equalsIgnoreCase(Sps2GeneralConstants.SPS_RESERVE)) 
					|| (_taskBean.getOperation().equalsIgnoreCase(Sps2GeneralConstants.SPS_CONFIRM))
					|| (_taskBean.getOperation().equalsIgnoreCase(Sps2GeneralConstants.SPS_VALIDATE))) {
				if ( (_taskBean.getResponseType().equalsIgnoreCase(Sps2GeneralConstants.SPS_RESERVE)) 
						|| (_taskBean.getResponseType().equalsIgnoreCase(Sps2GeneralConstants.SPS_CONFIRM))
						|| (_taskBean.getResponseType().equalsIgnoreCase(Sps2GeneralConstants.SPS_VALIDATE))
						|| (_taskBean.getResponseType().equalsIgnoreCase(Sps2GeneralConstants.SPS_GET_STATUS)) ) {
					// ReserveDocument XML document
					ReserveDocument reserveDocument = 
						ReserveDocument.Factory.parse(_taskBean.getRequest());

					taskResponse.setProgrammingRequestBean(
							parsing.parseTaskingRequest(_taskBean , sensorType, reserveDocument, _serverUrl, _taskingParameters, ParsingUtils.getSensors(_serverUrl)));
				}
			} else if (_taskBean.getOperation().equalsIgnoreCase(Sps2GeneralConstants.SPS_UPDATE)) {
				// UpdateDocument XML document
				UpdateDocument updateDocument = 
					UpdateDocument.Factory.parse(_taskBean.getRequest());

				taskResponse.setProgrammingRequestBean(
						parsing.parseTaskingRequest(_taskBean , sensorType, updateDocument, _serverUrl, _taskingParameters, ParsingUtils.getSensors(_serverUrl)));
			} // if (_taskBean.getOperation().equalsIgnoreCase(Sps2GeneralConstants.SPS_GET_FEASIBILITY)) {

			
			// -------------------------------- response parsing --------------------------------
			if (! _taskBean.getResponse().equalsIgnoreCase("")) {
				if (_taskBean.getOperation().equalsIgnoreCase(Sps2GeneralConstants.SPS_GET_FEASIBILITY)) {
					if (_taskBean.getResponseType().equalsIgnoreCase(Sps2GeneralConstants.SPS_GET_FEASIBILITY)) {
						GetFeasibilityResponseDocument getFeasibilityResponseDocument = 
							GetFeasibilityResponseDocument.Factory.parse(_taskBean.getResponse());

						taskResponse.setTaskingResponseBean(
								parsing.parseGetFeasibilityResponse(_taskBean.getSensor(), sensorType, getFeasibilityResponseDocument, _serverUrl));
					} else if (_taskBean.getResponseType().equalsIgnoreCase(Sps2GeneralConstants.SPS_GET_STATUS)) {
						GetStatusResponseDocument getStatusResponseDocument = 
							GetStatusResponseDocument.Factory.parse(_taskBean.getResponse());

						taskResponse.setTaskingResponseBean(
								parsing.parseGetStatusResponse(_taskBean, sensorType, getStatusResponseDocument, _serverUrl));
					} // if (_taskBean.getResponseType().equalsIgnoreCase(Sps2GeneralConstants.SPS_GET_FEASIBILITY)) {
				} else if (_taskBean.getOperation().equalsIgnoreCase(Sps2GeneralConstants.SPS_SUBMIT)) {
					if (_taskBean.getResponseType().equalsIgnoreCase(Sps2GeneralConstants.SPS_SUBMIT)) {
						SubmitResponseDocument submitResponseDocument = 
							SubmitResponseDocument.Factory.parse(_taskBean.getResponse());

						taskResponse.setTaskingResponseBean(
								parsing.parseSubmitResponse(_taskBean.getSensor(), sensorType, submitResponseDocument, _serverUrl));
					} else if (_taskBean.getResponseType().equalsIgnoreCase(Sps2GeneralConstants.SPS_GET_STATUS)) {
						GetStatusResponseDocument getStatusResponseDocument = 
							GetStatusResponseDocument.Factory.parse(_taskBean.getResponse());

						taskResponse.setTaskingResponseBean(
								parsing.parseGetStatusResponse(_taskBean, sensorType, getStatusResponseDocument, _serverUrl));
					} // if (_taskBean.getResponseType().equalsIgnoreCase(Sps2GeneralConstants.SPS_SUBMIT)) {
				} else if ( (_taskBean.getOperation().equalsIgnoreCase(Sps2GeneralConstants.SPS_RESERVE)) 
						|| (_taskBean.getOperation().equalsIgnoreCase(Sps2GeneralConstants.SPS_CONFIRM)) ) {
					if ( (_taskBean.getRequestStatus().equalsIgnoreCase(Sps2GeneralConstants.STATUS_RESERVED))
						    || (_taskBean.getRequestStatus().equalsIgnoreCase(Sps2GeneralConstants.STATUS_ACCEPTED)) 
						    || (_taskBean.getRequestStatus().equalsIgnoreCase(Sps2GeneralConstants.STATUS_INEXECUTION)) 
						    || (_taskBean.getRequestStatus().equalsIgnoreCase(Sps2GeneralConstants.STATUS_COMPLETED)) 
						    || (_taskBean.getStatus().equalsIgnoreCase(Sps2GeneralConstants.STATUS_RESERVED)) 
						    || (_taskBean.getStatus().equalsIgnoreCase(Sps2GeneralConstants.STATUS_ACCEPTED)) 
						    || (_taskBean.getStatus().equalsIgnoreCase(Sps2GeneralConstants.STATUS_INEXECUTION)) 
						    || (_taskBean.getStatus().equalsIgnoreCase(Sps2GeneralConstants.STATUS_COMPLETED)) ) {
						if (_taskBean.getResponseType().equalsIgnoreCase(Sps2GeneralConstants.SPS_RESERVE)) {
							ReserveResponseDocument reserveResponseDocument = 
								ReserveResponseDocument.Factory.parse(_taskBean.getResponse());

							taskResponse.setTaskingResponseBean(
									parsing.parseReserveResponse(_taskBean.getSensor(), sensorType, reserveResponseDocument, _serverUrl));
						} if (_taskBean.getResponseType().equalsIgnoreCase(Sps2GeneralConstants.SPS_GET_STATUS)) {
							GetStatusResponseDocument getStatusResponseDocument = 
								GetStatusResponseDocument.Factory.parse(_taskBean.getResponse());

							taskResponse.setTaskingResponseBean(
									parsing.parseGetStatusResponse(_taskBean, sensorType, getStatusResponseDocument, _serverUrl));
						} // if (_taskBean.getResponseType().equalsIgnoreCase(Sps2GeneralConstants.SPS_RESERVE)) {
					} // if
				} else if (_taskBean.getOperation().equalsIgnoreCase(Sps2GeneralConstants.SPS_VALIDATE)) {
					if ( (_taskBean.getRequestStatus().equalsIgnoreCase(Sps2GeneralConstants.STATUS_RESERVED))
						    || (_taskBean.getRequestStatus().equalsIgnoreCase(Sps2GeneralConstants.STATUS_ACCEPTED)) 
						    || (_taskBean.getRequestStatus().equalsIgnoreCase(Sps2GeneralConstants.STATUS_INEXECUTION)) 
						    || (_taskBean.getRequestStatus().equalsIgnoreCase(Sps2GeneralConstants.STATUS_COMPLETED)) 
						    || (_taskBean.getStatus().equalsIgnoreCase(Sps2GeneralConstants.STATUS_RESERVED)) 
						    || (_taskBean.getStatus().equalsIgnoreCase(Sps2GeneralConstants.STATUS_ACCEPTED)) 
						    || (_taskBean.getStatus().equalsIgnoreCase(Sps2GeneralConstants.STATUS_INEXECUTION)) 
						    || (_taskBean.getStatus().equalsIgnoreCase(Sps2GeneralConstants.STATUS_COMPLETED)) ) {
						if (_taskBean.getResponseType().equalsIgnoreCase(Sps2GeneralConstants.SPS_VALIDATE)) {
							ValidateResponseDocument validateResponseDocument = 
								ValidateResponseDocument.Factory.parse(_taskBean.getResponse());

							taskResponse.setTaskingResponseBean(
									parsing.parseValidateResponse(_taskBean.getSensor(), sensorType, validateResponseDocument, _serverUrl));
						} if (_taskBean.getResponseType().equalsIgnoreCase(Sps2GeneralConstants.SPS_GET_STATUS)) {
							GetStatusResponseDocument getStatusResponseDocument = 
								GetStatusResponseDocument.Factory.parse(_taskBean.getResponse());

							taskResponse.setTaskingResponseBean(
									parsing.parseGetStatusResponse(_taskBean, sensorType, getStatusResponseDocument, _serverUrl));
						} // if (_taskBean.getResponseType().equalsIgnoreCase(Sps2GeneralConstants.SPS_RESERVE)) {
					} // if
				} else if (_taskBean.getOperation().equalsIgnoreCase(Sps2GeneralConstants.SPS_UPDATE)) {
					if (_taskBean.getResponseType().equalsIgnoreCase(Sps2GeneralConstants.SPS_UPDATE)) {
						UpdateResponseDocument updateResponseDocument = 
							UpdateResponseDocument.Factory.parse(_taskBean.getResponse());

						taskResponse.setTaskingResponseBean(
								parsing.parseUpdateResponse(_taskBean.getSensor(), sensorType, updateResponseDocument, _serverUrl));
					} else if (_taskBean.getResponseType().equalsIgnoreCase(Sps2GeneralConstants.SPS_GET_STATUS)) {
						GetStatusResponseDocument getStatusResponseDocument = 
							GetStatusResponseDocument.Factory.parse(_taskBean.getResponse());

						taskResponse.setTaskingResponseBean(
								parsing.parseGetStatusResponse(_taskBean, sensorType, getStatusResponseDocument, _serverUrl));
					} // if (_taskBean.getResponseType().equalsIgnoreCase(Sps2GeneralConstants.SPS_UPDATE)) {
				} // if (_taskBean.getOperation().equalsIgnoreCase(Sps2GeneralConstants.SPS_GET_FEASIBILITY)) {
			} // if (! _taskBean.getResponse().equalsIgnoreCase("")) {

			return taskResponse;
		} catch (Exception exception) {
			taskResponse.getError().setMessage("Exception : " + METHOD + " : " + exception.getMessage());
			taskResponse.getError().setCode(FacadeSps2Exception.SPS_EXCEPTION);
			return taskResponse;
		}
	} // private TaskResultBean getTaskResponse(TaskBean _taskBean, String _serverUrl, TaskingParametersBean _taskingParameters) throws FacadeSps2Exception {

	/**
	 * to do a rollback
	 * 
	 * @param _transactionDB : transaction identifier
	 * 
	 */
	private void rollback(TransactionDB _transactionDB) throws TaskException {
		try {
			if (_transactionDB != null) {
				_transactionDB.rollback();
			}  			
		} catch(DBBeanException ex) {
			throw new TaskException(ex.getMessage(),"rollback");
		} catch(Exception ex) {
			throw new TaskException(ex.getMessage(), "rollback");
		}
	} // 

} // class
