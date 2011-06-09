package com.astrium.faceo.server.rpc.programming.sps2;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.astrium.faceo.client.bean.programming.sps2.describeTasking.DescribeTaskingRequestBean;
import com.astrium.faceo.client.bean.programming.sps2.describeTasking.DescribeTaskingResponseBean;
import com.astrium.faceo.client.common.programming.sps2.Sps2GeneralConstants;
import com.astrium.faceo.client.rpc.programming.sps2.DescribeTaskingService;
import com.astrium.faceo.server.common.exception.sps2.FacadeSps2Exception;
import com.astrium.faceo.server.service.programming.sps2.FacadeSensorRequests;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Server side implementation of the DescribeTaskingService interface.
 * 
 * The DescribeTasking operation allows SPS clients to retrieve 
 * a description of the data structures for the tasking parameters 
 * of a given sensor. 
 * Such a data structure description is encoded in SWE Common 
 * (see clause 7.4 ―SPS tasking parameters representation).
 * 
 * @author ASTRIUM
 *
 */public class DescribeTaskingServiceImpl extends RemoteServiceServlet implements DescribeTaskingService {

	 /**
	 * 
	 */
	private static final long serialVersionUID = -1879032492739253236L;

	/** Le logger de cette classe */
	 private static Logger log = Logger.getLogger(DescribeTaskingServiceImpl.class);

	 String INFOS_CLASSE = "DescribeTaskingServiceImpl";

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
	  * @see com.astrium.faceo.client.rpc.programming.sps2.DescribeTaskingService#getDescribeTaskingResult(java.lang.String, DescribeTaskingRequestBean, boolean)
	  */

	 /**
	  * The annotation indicates that the returned List will only 
	  * contain objects of  type <com.astrium.faceo.bean.client.bean.programming.sps2.describeTasking.DescribeTaskingResponseBean>
	  * 
	  * This method returns the response of a 'DescribeTasking' resquest for one sensor
	  * 
      * 
      * @param _operation (String)						: operation of the FACEO service ('DescribeTasking')
      * @param _parameters (DescribeTaskingRequestBean)	: 'DesccribeTasking' parameters for one sensor
      * @param _webAccessMode (boolean)					: true for using web services, false else (for using kml files)
      * 
      * @return DescribeTaskingResponseBean : 'DescribeTasking' response in HTML format
	  * 
	  * @gwt.typeArgs <com.astrium.faceo.bean.client.bean.programming.sps2.describeTasking.DescribeTaskingResponseBean>
	  */
	 public DescribeTaskingResponseBean getDescribeTaskingResult(
			 String _operation, DescribeTaskingRequestBean _parameters, boolean _webAccessMode) {

		 DescribeTaskingResponseBean describeTaskingResponse = new DescribeTaskingResponseBean();

		 String messageErreur = null;
		 int code_erreur = 0;

		 log.debug("\n" + INFOS_CLASSE + " : operation : " + _operation );    
		 if (_operation == null){
			 messageErreur = "undefined operation";
			 code_erreur = 531;
		 } else {
			 if (!_operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_DESCRIBE_TASKING)){
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
			 describeTaskingResponse.getError().setMessage("error : " + messageErreur);
			 describeTaskingResponse.getError().setCode(code_erreur);

			 return describeTaskingResponse;
		 } // if (messageErreur != null ) {

		 try {
			 // lecture d'un fichier XML contenant une liste de demandes
			 HttpServletRequest request = this.getThreadLocalRequest();
			 String requestURL = request.getRequestURL().toString();
			 int pos = requestURL.lastIndexOf(Sps2GeneralConstants.DESCRIBE_TASKING_CONTROLLER);
			 String serverURL = requestURL;
			 if (pos >0) {
				 serverURL = requestURL.substring(0, pos);
			 }

			 FacadeSensorRequests facadeSensorsRequests = new FacadeSensorRequests();
			 describeTaskingResponse = 
				 facadeSensorsRequests.getSensorDescribeTasking(_parameters, serverURL);

			 return describeTaskingResponse;
		 } catch (FacadeSps2Exception exception) {
			 log.info("\n" + INFOS_CLASSE + " : getDescribeTaskingResult : FacadeSps2Exception : " + exception.getMessage());
			 return describeTaskingResponse;
		 } catch (Exception exception) {
			 log.info("\n" + INFOS_CLASSE + " : getDescribeTaskingResult : Exception : " + exception.getMessage());
			 describeTaskingResponse.getError().setMessage(exception.getMessage());
			 describeTaskingResponse.getError().setCode(FacadeSps2Exception.SPS_EXCEPTION);
			 return describeTaskingResponse;
		 } // try { 	

	 } // public DescribeTaskingResponseBean getDescribeTaskingResult(String _operation, DescribeTaskingRequestBean _parameters

 } // class
