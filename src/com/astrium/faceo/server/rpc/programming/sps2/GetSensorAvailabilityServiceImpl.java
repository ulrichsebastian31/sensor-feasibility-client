package com.astrium.faceo.server.rpc.programming.sps2;

import java.io.IOException;
import java.util.HashMap;
import java.util.PropertyResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.astrium.faceo.client.bean.programming.sps2.getSensorAvailability.GetSensorAvailabilityRequestBean;
import com.astrium.faceo.client.bean.programming.sps2.getSensorAvailability.GetSensorAvailabilityResponseBean;
import com.astrium.faceo.client.bean.programming.sps2.request.SurveyPeriodBean;
import com.astrium.faceo.client.common.programming.sps2.Sps2GeneralConstants;
import com.astrium.faceo.client.rpc.programming.sps2.GetSensorAvailabilityService;
import com.astrium.faceo.server.common.exception.sps2.FacadeSps2Exception;
import com.astrium.faceo.server.service.programming.sps2.FacadeSensorRequests;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Server side implementation of the GetSensorAvailabilityService interface.
 * 
 * An EO system may not be available over a period of time for different reasons
 * such as workload, maintenance, etc. The GetSensorAvailability operation
 * allows the client to obtain a preview of the periods of availability of a
 * sensor before a feasibility study is requested.
 * 
 * The granularity of the provided information is up to the data provider who
 * can choose to describe its exact workload or simply list approximate periods
 * of availability. For instance, a provider may wish to list a period of one
 * week as soon as the sensor is available for tasking at least during 50% of
 * the period. This allows a provider to choose the most appropriate granularity
 * of information to help the users while maintaining its exact workload secret.
 * 
 * @author ASTRIUM
 *
 */
public class GetSensorAvailabilityServiceImpl extends RemoteServiceServlet implements GetSensorAvailabilityService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1532239772443157687L;

	/** Le logger de cette classe */
	private static Logger log = Logger.getLogger(GetSensorAvailabilityServiceImpl.class);

	String INFOS_CLASSE = "GetSensorAvailabilityServiceImpl";

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
     * This method send a SPS request 
     * with the 'GetSensorAvailability' operation
     * 
     * @param _operation (String) 				: operation ('GetSensorAvailability')
     * @param _parameters (GetSensorAvailabilityRequestBean) : GetSensorAvailability parameters
     * @param _webAccessMode (boolean)			: true for using web services, false else (for using xml files)
     * 
     * @return AsyncCallback<GetSensorAvailabilityResponseBean> : callback which return the request ack
	 * 
	 * @gwt.typeArgs <com.astrium.faceo.client.bean.programming.sps2.getSensorAvailability.GetSensorAvailabilityResponseBean>
	 */
	public GetSensorAvailabilityResponseBean getGetSensorAvailabilityResponse(
			String _operation,
			GetSensorAvailabilityRequestBean _parameters,
			boolean _webAccessMode) {

		GetSensorAvailabilityResponseBean response = new GetSensorAvailabilityResponseBean();

		String messageErreur = null;
		int code_erreur = 0;

		log.debug("\n" + INFOS_CLASSE + " : operation : " + _operation );    
		if (_operation == null){
			messageErreur = "undefined operation";
			code_erreur = 531;
		} else {
			if (!_operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_GET_SENSOR_AVAILABILITY)){
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
			FacadeSensorRequests facadeSensorRequests = new FacadeSensorRequests();

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
					HttpServletRequest request = this.getThreadLocalRequest();
					String requestURL = request.getRequestURL().toString();
					int pos = requestURL.lastIndexOf(Sps2GeneralConstants.GET_SENSOR_AVAILABILITY_CONTROLLER);
					String serverURL = requestURL;
					if (pos >0) {
						serverURL = requestURL.substring(0, pos);
					}

					log.debug("\n" + INFOS_CLASSE + " : facadeSensorRequests.getSensorAvailability");  
					response = 
						facadeSensorRequests.getSensorAvailability(_parameters, serverURL);
				} else {
					response = getSimulatedValues();
				} // if (simulationProgramming.equalsIgnoreCase(Sps2GeneralConstants.FALSE)) {
			} else {
				log.debug("\n" + INFOS_CLASSE + " : getSimulationValues");    
				response = getSimulatedValues();
			} // if (_webAccessMode) {

			log.debug("\n ------------------------------------------------------------------------------");    
			log.debug("\n" + INFOS_CLASSE + " : response");    
			log.debug("\n Error Message : " + response.getError().getMessage());    
			log.debug("\n Error Code : " + response.getError().getCode());    
			log.debug("\n ------------------------------------------------------------------------------");    

			return response;
		} catch (FacadeSps2Exception exception) {
			log.info("\n" + INFOS_CLASSE + " : getSensorAvailability : FacadeSps2Exception : " + exception.getMessage());
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

	} // public GetSensorAvailabilityResponseBean getGetSensorAvailabilityResponse(

	/**
	 * 
	 * @return GetSensorAvailabilityResponseBean
	 */
	private GetSensorAvailabilityResponseBean getSimulatedValues() {
		GetSensorAvailabilityResponseBean  responseBean = new GetSensorAvailabilityResponseBean();

		responseBean.getError().setMessage("");
		responseBean.getError().setCode(0);
		SurveyPeriodBean responsePeriod = new SurveyPeriodBean();
		responseBean.setResponsePeriod(responsePeriod);
		HashMap<String, SurveyPeriodBean> availabilitis = new HashMap<String, SurveyPeriodBean>();
		responseBean.setAvailabilityPeriod(availabilitis);

		return responseBean;
	} // private ConfirmResponseBean getSimulatedValues(String _requestId) {

} // class
