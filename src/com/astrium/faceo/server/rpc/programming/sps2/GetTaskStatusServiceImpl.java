package com.astrium.faceo.server.rpc.programming.sps2;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.astrium.faceo.client.bean.programming.sps2.getStatus.GetStatusRequestBean;
import com.astrium.faceo.client.bean.programming.sps2.getStatus.GetStatusResponseBean;
import com.astrium.faceo.client.common.programming.sps2.Sps2GeneralConstants;
import com.astrium.faceo.client.rpc.programming.sps2.GetTaskStatusService;
import com.astrium.faceo.server.common.exception.sps2.FacadeSps2Exception;
import com.astrium.faceo.server.service.programming.sps2.FacadeTaskRequests;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Server side implementation of the GetTaskStatusService interface.
 *  
 * The GetStatus operation allows SPS clients to retrieve status reports 
 * about a tasking request or a task. 
 * This operation is the default mechanism to retrieve status information 
 * about a task or tasking request.
 * 
 * @author ASTRIUM
 *
 */public class GetTaskStatusServiceImpl extends RemoteServiceServlet implements GetTaskStatusService {

	 /**
	  * 
	  */
	 private static final long serialVersionUID = -5453188617331167298L;

	 /** Le logger de cette classe */
	 private static Logger log = Logger.getLogger(GetTaskStatusServiceImpl.class);

	 String INFOS_CLASSE = "GetTaskStatusServiceImpl";

	 /**
	  *  traitement de la requete HTTP GET
	  *  
	  * @param _request (HttpServletRequest)	: the request
	  * @param _response (HttpServletResponse)	: the response
	  *  
	  *  @throws ServletException
	  * 	@throws IOException
	  */
	 public void doGet (HttpServletRequest _request, HttpServletResponse _response)
	 throws ServletException, IOException {

	 }

	 /* (non-Javadoc)
	  * @see com.astrium.faceo.client.rpc.programming.sps2.GetTaskStatusService#getTaskStatus(java.lang.String, GetStatusRequestBean)
	  */

	 /**
	  * The annotation indicates that the returned List will only 
	  * contain objects of  type <com.astrium.faceo.bean.client.bean.programming.sps2.getStatus.GetStatusResponseBean>
	  * 
	  * This method returns the response of a SPS 'GetStatus' operation for one task 
	  * 
	  * @param _operation (String)	: operation of the FACEO service ('GetStatus')
	  * @param _parameters (GetStatusRequestBean) : parameters for GetStatus request
	  * 
	  * @return GetStatusResponseBean	: GetStatus response
	  * 
	  * @gwt.typeArgs <com.astrium.faceo.bean.client.bean.programming.sps2.getStatus.GetStatusResponseBean>
	  */
	 public GetStatusResponseBean getTaskStatus(String _operation, GetStatusRequestBean _parameters) {

		 GetStatusResponseBean getStatusResponse = new GetStatusResponseBean();

		 String messageErreur = null;
		 int code_erreur = 0;

		 log.debug("\n" + INFOS_CLASSE + " : operation : " + _operation );    
		 if (_operation == null){
			 messageErreur = "undefined operation";
			 code_erreur = 531;
		 } else {
			 if (!_operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_GET_TASK_STATUS)){
				 messageErreur = "bad operation";
				 code_erreur = 534;
			 }
			 if (_parameters.getTaskUser().equalsIgnoreCase("")) {
				 messageErreur = "undefined User Id";
				 code_erreur = 535;
			 }
			 if (_parameters.getTaskId().equalsIgnoreCase("")) {
				 messageErreur = "undefined task Id";
				 code_erreur = 536;
			 }
		 } // if (operation == null){

		 // redirection vers la page d'erreur, "action inconnue"
		 if (messageErreur != null ) {
			 // retourne le message d'erreur
			 log.debug("\n" + INFOS_CLASSE + " : error : " + messageErreur);
			 getStatusResponse.getError().setMessage("error : " + messageErreur);
			 getStatusResponse.getError().setCode(code_erreur);

			 return getStatusResponse;
		 } // if (messageErreur != null ) {

		 try {
			 log.debug("\n" + INFOS_CLASSE + " : facadeProgramming : ");    

			 // lecture d'un fichier XML contenant une liste de demandes
			 HttpServletRequest request = this.getThreadLocalRequest();
			 String requestURL = request.getRequestURL().toString();
			 int pos = requestURL.lastIndexOf(Sps2GeneralConstants.TASK_STATUS_CONTROLLER);
			 String serverURL = requestURL;
			 if (pos >0)
				 serverURL = requestURL.substring(0, pos);

			 FacadeTaskRequests facadeRequests = new FacadeTaskRequests();
			 getStatusResponse = facadeRequests.getTaskStatus(_parameters, serverURL);

			 return getStatusResponse;
		 } catch (FacadeSps2Exception exception) {
			 log.info("\n" + INFOS_CLASSE + " : getTaskStatus : FacadeSps2Exception : " + exception.getMessage());
			 return getStatusResponse;
		 } catch (Exception exception) {
			 getStatusResponse.getError().setMessage("Exception : " + exception.getMessage());
			 getStatusResponse.getError().setCode(FacadeSps2Exception.SPS_EXCEPTION);
			 return getStatusResponse;
		 } // try { 	

	 } // public GetStatusResponseBean getTaskStatus(String _operation, GetStatusRequestBean _parameters) {

 } // class
