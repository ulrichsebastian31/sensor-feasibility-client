package com.astrium.faceo.server.rpc.programming.sps2;

import java.io.IOException;
import java.util.PropertyResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.astrium.faceo.client.bean.programming.sps2.TaskStatusReportBean;
import com.astrium.faceo.client.bean.programming.sps2.confirm.ConfirmRequestBean;
import com.astrium.faceo.client.bean.programming.sps2.confirm.ConfirmResponseBean;
import com.astrium.faceo.client.common.programming.sps2.Sps2GeneralConstants;
import com.astrium.faceo.client.rpc.programming.sps2.ConfirmTaskService;
import com.astrium.faceo.server.common.exception.sps2.FacadeSps2Exception;
import com.astrium.faceo.server.service.programming.sps2.FacadeTaskRequests;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Server side implementation of the ConfirmTaskService interface.
 * 
 * The Confirm operation allows SPS clients to confirm a previously reserved task. 
 * If accepted i;e; if task has'nt expired yet, the task shall transit from state reserved
 * to inExecution, otherwise the SPS shall reject the request
 * 
 * @author ASTRIUM
 *
 */
public class ConfirmTaskServiceImpl extends RemoteServiceServlet implements ConfirmTaskService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1378356778999705597L;

	/** Le logger de cette classe */
	private static Logger log = Logger.getLogger(ConfirmTaskServiceImpl.class);

	String INFOS_CLASSE = "ConfirmTaskServiceImpl";

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
	 * @param _operation (String) 				: operation ('Confirm')
	 * @param _parameters (ConfirmRequestBean)	: Confirm parameters
	 * @param _webAccessMode (boolean)			: true for using web services, false else (for using kml files)
	 * 
	 * @return AsyncCallback<ConfirmResponseBean> : callback which return the response
	 * 
	 * @gwt.typeArgs <com.astrium.faceo.client.bean.programming.sps2.confirm.ConfirmResponseBean>
	 */
	public ConfirmResponseBean getConfirmResponse(
			String _operation,
			ConfirmRequestBean _parameters,
			boolean _webAccessMode) {

		ConfirmResponseBean response = new ConfirmResponseBean();

		String messageErreur = null;
		int code_erreur = 0;

		log.debug("\n" + INFOS_CLASSE + " : operation : " + _operation );    
		if (_operation == null){
			messageErreur = "undefined operation";
			code_erreur = 531;
		} else {
			if (!_operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_CONFIRM)){
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
			FacadeTaskRequests facadeProgramming = new FacadeTaskRequests();

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
					int pos = requestURL.lastIndexOf(Sps2GeneralConstants.CONFIRM_TASK_CONTROLLER);
					String serverURL = requestURL;
					if (pos >0) {
						serverURL = requestURL.substring(0, pos);
					}

					log.debug("\n" + INFOS_CLASSE + " : facadeProgramming.getConfirmResponse");  
					response = 
						facadeProgramming.getConfirmResponse(_parameters, serverURL);
				} else {
					response = getSimulatedValues("123456789");
				} // if (simulationProgramming.equalsIgnoreCase(Sps2GeneralConstants.FALSE)) {
			} else {
				log.debug("\n" + INFOS_CLASSE + " : getSimulationValues");    
				response = getSimulatedValues("123456789");
			} // if (_webAccessMode) {

			log.debug("\n ------------------------------------------------------------------------------");    
			log.debug("\n" + INFOS_CLASSE + " : response");    
			log.debug("\n Task Identifier : " + response.getStatusReport().getTask());    
			log.debug("\n Error Message : " + response.getError().getMessage());    
			log.debug("\n Error Code : " + response.getError().getCode());    
			log.debug("\n ------------------------------------------------------------------------------");    

			// on verifie que le statut de la requete est 'confirmed' sinon on retourne une erreur
			// ex : Id duplicated
			//			if (!sps2GetFeasibilityResponseBean.getStatus().equalsIgnoreCase(SPS_GET_FEASIBILITY_CONFIRMED)) {
			//				log.debug("\n" + INFOS_CLASSE + " : pb : Status != " + SPS_GET_FEASIBILITY_CONFIRMED);
			//				sps2GetFeasibilityResponseBean.setErrorCode(0);
			//				sps2GetFeasibilityResponseBean.setErrorMessage("error : " + ((Sps2OrderBean) sps2GetFeasibilityResponseBean).getStatus());
			//			}

			return response;
		} catch (FacadeSps2Exception exception) {
			log.info("\n" + INFOS_CLASSE + " : getConfirmResponse : FacadeSps2Exception : " + exception.getMessage());
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

	} // public ConfirmResponseBean getConfirmResponse(

	/**
	 * 
	 * @param _requestId (in) : request identifier
	 * 
	 * @return ConfirmResponseBean
	 */
	private ConfirmResponseBean getSimulatedValues(String _requestId) {

		ConfirmResponseBean  responseBean = new ConfirmResponseBean();

		responseBean.getError().setMessage("");
		responseBean.getError().setCode(0);
		TaskStatusReportBean statusReport = new TaskStatusReportBean();
		statusReport.setTask("task_id_test");
		responseBean.setStatusReport(statusReport);

		return responseBean;

	} // private ConfirmResponseBean getSimulatedValues(String _requestId) {

} // class
