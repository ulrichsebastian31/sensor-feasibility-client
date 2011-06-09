package com.astrium.faceo.server.rpc.programming.sps2;

import java.io.IOException;
import java.util.PropertyResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.astrium.faceo.client.bean.programming.sps2.validate.ValidateRequestBean;
import com.astrium.faceo.client.bean.programming.sps2.validate.ValidateResponseBean;
import com.astrium.faceo.client.common.programming.sps2.Sps2GeneralConstants;
import com.astrium.faceo.client.rpc.programming.sps2.ValidateTaskService;
import com.astrium.faceo.server.common.exception.sps2.FacadeSps2Exception;
import com.astrium.faceo.server.service.programming.sps2.FacadeTaskRequests;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Server side implementation of the ValidateService interface.
 * 
 * Several acquisition attempts are sometimes necessary to obtain a satisfying result 
 * (case of optical satellites on zones with cloudy tendency for example). 
 * The Validate operation can be used by the customer to indicate to the server 
 * that an acquisition is satisfactory and thus to stop collecting new images for this area.
 * 
 * @author ASTRIUM
 *
 */
public class ValidateTaskServiceImpl extends RemoteServiceServlet implements ValidateTaskService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7989706156334984598L;

	/** Le logger de cette classe */
	private static Logger log = Logger.getLogger(ValidateTaskServiceImpl.class);

	String INFOS_CLASSE = "ValidateTaskServiceImpl";

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
	 * contain objects of type <com.astrium.faceo.client.bean.programming.sps2.validate.ValidateResponseBean>
	 * 
     * @param _operation (String) 				: operation ('Validate')
     * @param _parameters (ValidateRequestBean)	: Validate parameters
     * @param _webAccessMode (boolean)			: true for using web services, false else (for using xml files)
	 * 
	 * @return AsyncCallback<TaskingResponseBean> : callback which return the request ack
	 * 
	 * @gwt.typeArgs <com.astrium.faceo.client.bean.programming.sps2.validate.ValidateResponseBean>
	 */
	public ValidateResponseBean getValidateResponse(
			String _operation,
			ValidateRequestBean _parameters,
			boolean _webAccessMode) {

		ValidateResponseBean response = new ValidateResponseBean();

		String messageErreur = null;
		int code_erreur = 0;

		log.debug("\n" + INFOS_CLASSE + " : operation : " + _operation );    
		if (_operation == null){
			messageErreur = "undefined operation";
			code_erreur = 531;
		} else {
			if (!_operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_VALIDATE)){
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
		} // if 

		try {
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
					int pos = requestURL.lastIndexOf(Sps2GeneralConstants.VALIDATE_TASK_CONTROLLER);
					String serverURL = requestURL;
					if (pos >0) {
						serverURL = requestURL.substring(0, pos);
					}

					log.debug("\n" + INFOS_CLASSE + " : facadeTaskRequests.getValidateResponse");  
					FacadeTaskRequests facadeTaskRequests = new FacadeTaskRequests();
					response = 
						facadeTaskRequests.getValidateResponse(_parameters, serverURL);
				} else {
					response = getSimulatedValues("123456789");
				} // if (simulationProgramming.equalsIgnoreCase(Sps2GeneralConstants.FALSE)) {
			} else {
				log.debug("\n" + INFOS_CLASSE + " : getSimulationValues");    
				response = getSimulatedValues("123456789");
			} // if (_webAccessMode) {

			log.debug("\n ------------------------------------------------------------------------------");    
			log.debug("\n" + INFOS_CLASSE + " : getValidateResponse");    
			log.debug("\n Task Identifier : " + response.getTaskId());    
			log.debug("\n Error Message : " + response.getError().getMessage());    
			log.debug("\n Error Code : " + response.getError().getCode());    
			log.debug("\n ------------------------------------------------------------------------------");    

			return response;
		} catch (FacadeSps2Exception exception) {
			log.info("\n" + INFOS_CLASSE + " : getValidateResponse : FacadeSps2Exception : " + exception.getMessage());
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

	} // public ValidateResponseBean getValidateResponse(

	/**
	 * 
	 * @param _requestId (in) : request identifier
	 * 
	 * @return ValidateResponseBean
	 */
	private ValidateResponseBean getSimulatedValues(String _requestId) {
		ValidateResponseBean  responseBean = new ValidateResponseBean();

		responseBean.getError().setMessage("");
		responseBean.getError().setCode(0);
		responseBean.setTaskId("Task 01");

		return responseBean;
	} // 

} // class
