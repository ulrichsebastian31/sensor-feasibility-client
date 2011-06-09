package com.astrium.faceo.server.rpc.programming.sps2;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.astrium.faceo.client.bean.programming.sps2.cancel.CancelRequestBean;
import com.astrium.faceo.client.bean.programming.sps2.cancel.CancelResponseBean;
import com.astrium.faceo.client.common.programming.sps2.Sps2GeneralConstants;
import com.astrium.faceo.client.rpc.programming.sps2.CancelTaskService;
import com.astrium.faceo.server.common.exception.sps2.FacadeSps2Exception;
import com.astrium.faceo.server.service.programming.sps2.FacadeTaskRequests;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Server side implementation of the Cancel interface.
 * 
 * The Cancel operation allows SPS clients to cancel an accepted task (see clause 6.3.6). 
 * The service may reject the cancellation for specific reason. 
 * The response should indicate why the cancellation did not succeed. 
 * If the cancellation was rejected, the task remains in its current state.
 * 
 * @author ASTRIUM
 *
 */
public class CancelTaskServiceImpl extends RemoteServiceServlet implements CancelTaskService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3853727433891362668L;

	/** Le logger de cette classe */
	private static Logger log = Logger.getLogger(CancelTaskServiceImpl.class);

	String INFOS_CLASSE = "CancelTaskServiceImpl";

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
	 * contain objects of type <com.astrium.faceo.client.bean.programming.sps2.GetStatusResponseBean>
	 * 
	 * @param _operation (String) 				: operation ('Cancel')
	 * @param _parameters (CancelRequestBean)	: Cancel parameters
	 * @param _webAccessMode (boolean)			: true for using web services, false else (for using xml files)
	 * 
	 * @return AsyncCallback<GetStatusResponseBean> : callback which return the response
	 * 
	 * @gwt.typeArgs <com.astrium.faceo.client.bean.programming.sps2.GetStatusResponseBean>
	 */
	public CancelResponseBean getCancelResponse(
			String _operation,
			CancelRequestBean _parameters,
			boolean _webAccessMode) {

		CancelResponseBean response = new CancelResponseBean();

		String messageErreur = null;
		int code_erreur = 0;

		log.debug("\n" + INFOS_CLASSE + " : operation : " + _operation );    
		if (_operation == null){
			messageErreur = "undefined operation";
			code_erreur = 531;
		} else {
			if (!_operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_CANCEL)){
				messageErreur = "bad operation";
				code_erreur = 534;
			}
			if (_parameters.getTaskId().equalsIgnoreCase("")) {
				messageErreur = "undefined task Id";
				code_erreur = 536;
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
			FacadeTaskRequests facadeProgramming = new FacadeTaskRequests();

			if (_webAccessMode) {
				// lecture d'un fichier XML contenant une liste de demandes
				HttpServletRequest request = this.getThreadLocalRequest();
				String requestURL = request.getRequestURL().toString();
				int pos = requestURL.lastIndexOf(Sps2GeneralConstants.CANCEL_TASK_CONTROLLER);
				String serverURL = requestURL;
				if (pos >0) {
					serverURL = requestURL.substring(0, pos);
				}

				log.debug("\n" + INFOS_CLASSE + " : facadeProgramming.getCancelResponse");  
				response = 
					facadeProgramming.getCancelResponse(_parameters, serverURL);
			} else {
				log.debug("\n" + INFOS_CLASSE + " : getSimulationValues");    
			} // if (_webAccessMode) {

			log.debug("\n ------------------------------------------------------------------------------");    
			log.debug("\n" + INFOS_CLASSE + " : response");    
			log.debug("\n Task Identifier : " + response.getStatusReport().getTask());    
			log.debug("\n Error Message : " + response.getError().getMessage());    
			log.debug("\n Error Code : " + response.getError().getCode());    
			log.debug("\n ------------------------------------------------------------------------------");    

			return response;
		} catch (FacadeSps2Exception exception) {
			log.info("\n" + INFOS_CLASSE + " : getCancelResponse : FacadeSps2Exception : " + exception.getMessage());
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

	} // public CancelResponseBean getCancelResponse(

} // class
