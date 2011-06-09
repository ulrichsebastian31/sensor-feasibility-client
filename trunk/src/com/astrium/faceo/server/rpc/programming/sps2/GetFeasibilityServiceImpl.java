package com.astrium.faceo.server.rpc.programming.sps2;

import java.io.IOException;
import java.util.PropertyResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.astrium.faceo.client.bean.programming.sps2.request.ProgrammingRequestBean;
import com.astrium.faceo.client.bean.programming.sps2.responses.TaskingResponseBean;
import com.astrium.faceo.client.common.programming.sps2.Sps2GeneralConstants;
import com.astrium.faceo.client.rpc.programming.sps2.GetFeasibilityService;
import com.astrium.faceo.server.common.exception.sps2.FacadeSps2Exception;
import com.astrium.faceo.server.service.programming.sps2.FacadeTaskRequests;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Server side implementation of the GetFeasibilityService interface.
 * 
 * The GetFeasibility operation allows SPS clients to obtain information 
 * about the feasibility of a tasking request. Dependent on the types 
 * of assets facaded by the SPS, the SPS server action may be as simple 
 * as checking that the request parameters are valid, and are consistent 
 * with certain business rules, or it may be a complex operation 
 * that calculates the utilizability of the asset to perform a specific task 
 * at the defined location, time, orientation, calibration etc. 
 * (see clause 6.3.4 for further details on task feasibility).
 * 
 * @author ASTRIUM
 *
 */
public class GetFeasibilityServiceImpl extends RemoteServiceServlet implements GetFeasibilityService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7604809534456639222L;

	/** Le logger de cette classe */
	private static Logger log = Logger.getLogger(GetFeasibilityServiceImpl.class);

	String INFOS_CLASSE = "GetFeasibilityServiceImpl";

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
	 * contain objects of type <com.astrium.faceo.client.bean.programming.sps2.responses.TaskingResponseBean>
	 * 
	 * @param _operation (String) 					: operation ('GetFeasibility')
	 * @param _parameters (ProgrammingRequestBean)	: 'GetFeasibility' parameters
	 * @param _webAccessMode (boolean)				: true for using web services, false else (for using kml files)
	 * 
	 * @return AsyncCallback<TaskingResponseBean> : callback which return the request ack
	 * 
	 * @gwt.typeArgs <com.astrium.faceo.client.bean.programming.sps2.responses.TaskingResponseBean>
	 */
	public TaskingResponseBean getGetFeasibilityResponse(
			String _operation,
			ProgrammingRequestBean _parameters,
			boolean _webAccessMode) {

		TaskingResponseBean response = new TaskingResponseBean();

		// on retourne les donnees sous la forme JSON (JavaScript Object Notation)
		String messageErreur = null;
		int code_erreur = 0;

		log.debug("\n" + INFOS_CLASSE + " : operation : " + _operation );    
		if (_operation == null){
			messageErreur = "undefined operation";
			code_erreur = 531;
		} else {
			if (!_operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_GET_FEASIBILITY)){
				messageErreur = "bad operation";
				code_erreur = 534;
			}
		} // if (_operation == null){

		// redirection vers la page d'erreur, "action inconnue"
		if (messageErreur != null ) {
			// retourne le message d'erreur
			log.debug("\n" + INFOS_CLASSE + " : pb : " + messageErreur);    
			response.getError().setCode(code_erreur);
			response.getError().setMessage("error : " + messageErreur);

			return response;
		} // if (messageErreur != null ) {

		// redirection vers une page (jsp) ou un controleur (servlet)
		try {
			// gestion du contexte	    
			//       	WebApplicationContext webApplicationContext = 
			//       		WebApplicationContextUtils.getRequiredWebApplicationContext(
			//       			this.getServletContext());
			//       	String aa = (String) webApplicationContext.getBean("ss");

			// lecture d'une propriete stockee dans un fichier de properties
			String simulationProgramming = Sps2GeneralConstants.FALSE;
			try {
				PropertyResourceBundle prb = (PropertyResourceBundle)PropertyResourceBundle.getBundle(Sps2GeneralConstants.HMAFO_PROPERTIES);
				simulationProgramming = prb.getString("simulationProgramming").trim();
				log.debug("\n" + INFOS_CLASSE + " : >>> Reading file '" + Sps2GeneralConstants.HMAFO_PROPERTIES + "' OK !!!");    
			} catch (Exception e) {
				log.info("\n" + INFOS_CLASSE + " : >>> Reading file '" + Sps2GeneralConstants.HMAFO_PROPERTIES + "' KO !!!");    
			}
			log.debug("\n ------------------------------------------------ ");
			log.debug("\n" + INFOS_CLASSE + " : simulationProgramming : " + simulationProgramming);
			log.debug("\n ------------------------------------------------ ");

			if (_webAccessMode) {
				if (simulationProgramming.equalsIgnoreCase(Sps2GeneralConstants.FALSE)) {
					// lecture d'un fichier XML contenant une liste de demandes
					HttpServletRequest request = this.getThreadLocalRequest();
					String requestURL = request.getRequestURL().toString();
					int pos = requestURL.lastIndexOf(Sps2GeneralConstants.GET_FEASIBILITY_CONTROLLER);
					String serverURL = requestURL;
					if (pos >0) {
						serverURL = requestURL.substring(0, pos);
					}

					log.debug("\n" + INFOS_CLASSE + " : facadeTaskRequests.getGetFeasibilityResponse");  
					FacadeTaskRequests facadeTaskRequests = new FacadeTaskRequests();
					response = 
						facadeTaskRequests.getGetFeasibilityResponse(_parameters, serverURL);
				} else {
					response = getSimulatedValues("123456789");
				} // if (simulationProgramming.equalsIgnoreCase(Sps2GeneralConstants.FALSE)) {
			} else {
				log.debug("\n" + INFOS_CLASSE + " : getSimulationValues");    
				response = getSimulatedValues("123456789");
			} // if (_webAccessMode) {

			log.debug("\n ------------------------------------------------------------------------------");    
			log.debug("\n" + INFOS_CLASSE + " : getGetFeasibilityResponse");    
			log.debug("\n Task Identifier : " + response.getStatusReport().getTask());    
			log.debug("\n Error Message : " + response.getError().getMessage());    
			log.debug("\n Error Code : " + response.getError().getCode());    
			log.debug("\n ------------------------------------------------------------------------------");    

			return response;
		} catch (FacadeSps2Exception exception) {
			log.info("\n" + INFOS_CLASSE + " : getGetFeasibilityResponse : FacadeSps2Exception : " + exception.getMessage());
			return response;
		} catch (NumberFormatException numberFormatException) {
			response.getError().setMessage("NumberFormatException : " + numberFormatException.getMessage());
			response.getError().setCode(FacadeSps2Exception.SPS_NUMBER_FORMAT_EXCEPTION);
			return response;
		} catch (Exception exception) {
			response.getError().setMessage("Exception : " + exception.getMessage());
			response.getError().setCode(FacadeSps2Exception.SPS_EXCEPTION);
			return response;
		} // try { 

	} // public TaskingResponseBean getGetFeasibilityResponse(

	/**
	 * 
	 * @param _requestId (in) : request identifier
	 * 
	 * @return TaskingResponseBean
	 */
	private TaskingResponseBean getSimulatedValues(String _requestId) {

		TaskingResponseBean  responseBean = new TaskingResponseBean();

		responseBean.getError().setMessage("");
		responseBean.getError().setCode(0);
		responseBean.getStatusReport().setTask("Task 01");

		return responseBean;

	} // private TaskingResponseBean getSimulatedValues(String _requestId) {

} // class
