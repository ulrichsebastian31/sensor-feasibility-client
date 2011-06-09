package com.astrium.faceo.server.rpc.programming.sps2;

import java.io.IOException;
import java.util.PropertyResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.astrium.faceo.client.bean.programming.sps2.TaskResultBean;
import com.astrium.faceo.client.bean.programming.sps2.request.TaskingParametersBean;
import com.astrium.faceo.client.common.programming.sps2.Sps2GeneralConstants;
import com.astrium.faceo.client.rpc.programming.sps2.GetTaskResultService;
import com.astrium.faceo.server.common.exception.sps2.FacadeSps2Exception;
import com.astrium.faceo.server.service.programming.sps2.FacadeTasks;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Server side implementation of the GetTaskResultService interface.
 * 
 * @author ASTRIUM
 *
 */
public class GetTaskResultServiceImpl extends RemoteServiceServlet implements GetTaskResultService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7223850515328053907L;

	/** Le logger de cette classe */
	private static Logger log = Logger.getLogger(GetTaskResultServiceImpl.class);

	String INFOS_CLASSE = "GetTaskResultServiceImpl";

	/**
	 *  traitement de la requete HTTP GET
	 *  
	 * @param _request (HttpServletRequest)		: the request
	 * @param _response (HttpServletResponse)	: the response
	 *  
	 *  @throws ServletException
	 * 	@throws IOException
	 */
	public void doGet (HttpServletRequest _request, HttpServletResponse _response)
	throws ServletException, IOException {

	}

	/**
	 * The annotation indicates that the returned List will only 
	 * contain objects of  type <com.astrium.faceo.client.bean.programming.sps2.TaskResultBean>
	 * 
	 * @param _operation (String)		: operation ('GetTaskResult')
	 * @param _idUser (String)			: user identifier
	 * @param _idTask (String)			: task identifier
	 * @param _taskingParameters (TaskingParametersBean)	: tasking parameters for one sensor
	 * @param _webAccessMode (boolean)	: true for using web services, false else (for using kml files)
	 *
	 * @return TaskResultBean : the task
	 * 
	 * @gwt.typeArgs <com.astrium.faceo.client.bean.programming.sps2.TaskResultBean>
	 */
	public TaskResultBean getTaskResult(String _operation, String _idUser, String _idTask, 
			TaskingParametersBean _taskingParameters, boolean _webAccessMode) {

		TaskResultBean taskResultBean = new TaskResultBean();

		// on retourne les donnees sous la forme JSON (JavaScript Object Notation)
		String messageErreur = null;
		int code_erreur = 0;

		log.debug("\n" + INFOS_CLASSE + " : operation : " + _operation );    
		if (_operation == null){
			messageErreur = "undefined operation";
			code_erreur = 531;
		} else {
			if (!_operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_GET_TASK_RESULT)){
				messageErreur = "bad operation";
				code_erreur = 534;
			}
			if (_idUser == null) {
				messageErreur = "undefined id User";
				code_erreur = 535;
			}
			if (_idTask == null) {
				messageErreur = "undefined id Task";
				code_erreur = 536;
			}
		} // if (operation == null){

		// redirection vers la page d'erreur, "action inconnue"
		if (messageErreur != null ) {
			// retourne le message d'erreur
			log.debug("\n" + INFOS_CLASSE + " : pb : " + messageErreur);    
			taskResultBean.getError().setMessage("error : " + messageErreur);
			taskResultBean.getError().setCode(code_erreur);

			return taskResultBean;
		} // if (messageErreur != null ) {

		// redirection vers une page (jsp) ou un controleur (servlet)
		try {
			log.debug("\n" + INFOS_CLASSE + " : _idOrder : " + _idTask);    

			// read property stored in properties file
			String simulationProgramming = Sps2GeneralConstants.FALSE;
			try {
				PropertyResourceBundle prb = (PropertyResourceBundle)PropertyResourceBundle.getBundle(Sps2GeneralConstants.HMAFO_PROPERTIES);
				simulationProgramming = prb.getString("simulationProgramming").trim();
				log.debug("\n" + INFOS_CLASSE + " >>> Reading file '" + Sps2GeneralConstants.HMAFO_PROPERTIES + "' OK !!!");    
			} catch (Exception e) {
				log.info("\n" + INFOS_CLASSE + " >>> Reading file '" + Sps2GeneralConstants.HMAFO_PROPERTIES + "' KO !!!");    
			}
			log.debug("\n" + INFOS_CLASSE + " : ------------------------------------------------" );    
			log.debug("\n" + INFOS_CLASSE + " : simulationProgramming : " + simulationProgramming);    
			log.debug("\n" + INFOS_CLASSE + " : ------------------------------------------------" );    

			// read XML file of tasks
			HttpServletRequest request1 = this.getThreadLocalRequest();
			String requestURL = request1.getRequestURL().toString();
			int pos = requestURL.lastIndexOf(Sps2GeneralConstants.TASK_RESULT_CONTROLLER);
			String serverURL = requestURL;
			if (pos >0)
				serverURL = requestURL.substring(0, pos);

			if (_webAccessMode) {
				if (simulationProgramming.equalsIgnoreCase(Sps2GeneralConstants.FALSE)) {
					log.debug("\n" + INFOS_CLASSE + " : getTaskResult " );    
					FacadeTasks facadeTasks = new FacadeTasks();
					taskResultBean = facadeTasks.getTaskResults(_idUser, _idTask, serverURL, _taskingParameters);
				} else {
					log.debug("\n" + INFOS_CLASSE + " : getSimulationValues");    
					taskResultBean.getError().setMessage("getSimulationValues : ");
					taskResultBean.getError().setCode(FacadeSps2Exception.SPS_SIMULATION_FILE);
				} // if (simulationProgramming.equalsIgnoreCase(Constant.FALSE)) {
			} else {
				log.debug("\n" + INFOS_CLASSE + " : getSimulationValues");    
				taskResultBean.getError().setMessage("getSimulationValues : ");
				taskResultBean.getError().setCode(FacadeSps2Exception.SPS_SIMULATION_FILE);
				return taskResultBean;
			} // if (_webAccessMode) {

			log.debug("\n" + INFOS_CLASSE + " : return taskResultBean;"); 
			return taskResultBean;
		} catch (FacadeSps2Exception exception) {
			log.info("\n" + INFOS_CLASSE + " : getTaskResult : FacadeSps2Exception : " + exception.getMessage());
			return taskResultBean;
		} catch (NumberFormatException numberFormatException) {
			taskResultBean.getError().setMessage("NumberFormatException : " + numberFormatException.getMessage());
			taskResultBean.getError().setCode(FacadeSps2Exception.SPS_NUMBER_FORMAT_EXCEPTION);
			return taskResultBean;
		} catch (Exception exception) {
			taskResultBean.getError().setMessage("Exception : " + exception.getMessage());
			taskResultBean.getError().setCode(FacadeSps2Exception.SPS_EXCEPTION);
			return taskResultBean;
		} // try { 		

	} // public TaskResultBean getTaskResult(String _operation, String _idUser, String _idTask, boolean _webAccessMode) {

} // class
