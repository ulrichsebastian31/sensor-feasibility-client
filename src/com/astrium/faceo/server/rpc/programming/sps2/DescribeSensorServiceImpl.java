package com.astrium.faceo.server.rpc.programming.sps2;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.astrium.faceo.client.bean.programming.sps2.describeSensor.DescribeSensorRequestBean;
import com.astrium.faceo.client.bean.programming.sps2.describeSensor.DescribeSensorResponseBean;
import com.astrium.faceo.client.common.programming.sps2.Sps2GeneralConstants;
import com.astrium.faceo.client.rpc.programming.sps2.DescribeSensorService;
import com.astrium.faceo.server.common.exception.sps2.FacadeSps2Exception;
import com.astrium.faceo.server.service.programming.sps2.FacadeSensorRequests;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Server side implementation of the DescribeSensorService interface.
 * 
 * The DescribeSensor operation allows a client to request a detailed description 
 * of a sensor. The request can be targeted at a description that was valid 
 * at a certain point in or during a certain period of time in the past. 
 * 
 * @author ASTRIUM
 *
 */public class DescribeSensorServiceImpl extends RemoteServiceServlet implements DescribeSensorService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1821683905728300101L;

	/** Le logger de cette classe */
	 private static Logger log = Logger.getLogger(DescribeSensorServiceImpl.class);

	 String INFOS_CLASSE = "DescribeSensorServiceImpl";

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

	 /* (non-Javadoc)
	  * @see com.astrium.faceo.client.rpc.programming.sps2.DescribeSensorService#getDescribeSensorResult(java.lang.String, DescribeSensorRequestBean, boolean)
	  */

	 /**
	  * The annotation indicates that the returned List will only 
	  * contain objects of  type <com.astrium.faceo.bean.client.bean.programming.sps2.describeTasking.DescribeSensorResponseBean>
	  * 
	  * This method returns the response of a 'DescribeSensor' resquest for one sensor
	  * 
      * 
      * @param _operation (String)						: operation of the FACEO service ('DescribeSensor')
      * @param _parameters (DescribeSensorRequestBean)	: 'DescribeSensor' parameters for one sensor
      * @param _webAccessMode (boolean)					: true for using web services, false else (for using kml files)
      * 
      * @return DescribeSensorResponseBean : 'DescribeSensor' response in HTML format
	  * 
	  * @gwt.typeArgs <com.astrium.faceo.bean.client.bean.programming.sps2.describeSensor.DescribeSensorResponseBean>
	  */
	 public DescribeSensorResponseBean getDescribeSensorResult(
			 String _operation, DescribeSensorRequestBean _parameters, boolean _webAccessMode) {

		 DescribeSensorResponseBean describeSensorResponse = new DescribeSensorResponseBean();

		 String messageErreur = null;
		 int code_erreur = 0;

		 log.debug("\n" + INFOS_CLASSE + " : operation : " + _operation );    
		 if (_operation == null){
			 messageErreur = "undefined operation";
			 code_erreur = 531;
		 } else {
			 if (!_operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_DESCRIBE_SENSOR)){
				 messageErreur = "bad operation";
				 code_erreur = 534;
			 }
			 if (_parameters.getSensorUrn().equalsIgnoreCase("")) {
				 messageErreur = "undefined sensor Id";
				 code_erreur = 536;
			 }
		 } // if (operation == null){

		 // redirection vers la page d'erreur, "action inconnue"
		 if (messageErreur != null ) {
			 // retourne le message d'erreur
			 log.debug("\n" + INFOS_CLASSE + " : error : " + messageErreur);
			 describeSensorResponse.getError().setMessage("error : " + messageErreur);
			 describeSensorResponse.getError().setCode(code_erreur);

			 return describeSensorResponse;
		 } // if (messageErreur != null ) {

		 try {
			 // lecture d'un fichier XML contenant une liste de demandes
			 HttpServletRequest request = this.getThreadLocalRequest();
			 String requestURL = request.getRequestURL().toString();
			 int pos = requestURL.lastIndexOf(Sps2GeneralConstants.DESCRIBE_SENSOR_CONTROLLER);
			 String serverURL = requestURL;
			 if (pos >0) {
				 serverURL = requestURL.substring(0, pos);
			 }

			 FacadeSensorRequests facadeSensorsRequests = new FacadeSensorRequests();
			 describeSensorResponse = 
				 facadeSensorsRequests.getSensorDescribeSensor(_parameters, serverURL);

			 return describeSensorResponse;
		 } catch (FacadeSps2Exception exception) {
			 log.info("\n" + INFOS_CLASSE + " : getDescribeSensorResult : FacadeSps2Exception : " + exception.getMessage());
			 return describeSensorResponse;
		 } catch (Exception exception) {
			 log.info("\n" + INFOS_CLASSE + " : getDescribeSensorResult : Exception : " + exception.getMessage());
			 describeSensorResponse.getError().setMessage(exception.getMessage());
			 describeSensorResponse.getError().setCode(FacadeSps2Exception.SPS_EXCEPTION);
			 return describeSensorResponse;
		 } // try { 	

	 } // public DescribeSensorResponseBean getDescribeSensorResult(String _operation, DescribeSensorRequestBean _parameters

 } // class
