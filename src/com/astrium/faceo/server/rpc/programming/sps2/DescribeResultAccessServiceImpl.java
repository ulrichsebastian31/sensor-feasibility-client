package com.astrium.faceo.server.rpc.programming.sps2;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.astrium.faceo.client.bean.programming.sps2.describeResultAccess.DescribeResultAccessRequestBean;
import com.astrium.faceo.client.bean.programming.sps2.describeResultAccess.DescribeResultAccessResponseBean;
import com.astrium.faceo.client.common.programming.sps2.Sps2GeneralConstants;
import com.astrium.faceo.client.rpc.programming.sps2.DescribeResultAccessService;
import com.astrium.faceo.server.common.exception.sps2.FacadeSps2Exception;
import com.astrium.faceo.server.service.programming.sps2.FacadeSensorRequests;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Server side implementation of the DescribeResultAccessService interface.
 * 
 * The DescribeResultAccess operation allows SPS clients to retrieve 
 * information how to access data that was produced by a specific task, 
 * or how to retrieve data for a given sensor that is tasked by this SPS in general.
 * The response may point to a SOS, WMS, WFS any other OGC Web Service 
 * that provides data any data file or folder on a ftp server any data file 
 * or file container that is accessible over the Internet Clients provide the ID 
 * of either a sensor or task to identify what information they are interested in. 
 * 
 * @author ASTRIUM
 *
 */
public class DescribeResultAccessServiceImpl extends RemoteServiceServlet implements DescribeResultAccessService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3374595351631623932L;

	/** Le logger de cette classe */
	private static Logger log = Logger.getLogger(DescribeResultAccessServiceImpl.class);

	String INFOS_CLASSE = "DescribeResultAccessServiceImpl";

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
	 * @see com.astrium.faceo.client.rpc.programming.sps2.DescribeResultAccessService#getDescribeResultAccessResult(java.lang.String, DescribeResultAccessRequestBean, boolean)
	 */

	/**
	 * The annotation indicates that the returned List will only 
	 * contain objects of  type <com.astrium.faceo.bean.client.bean.programming.sps2.describeResultAccess.DescribeResultAccessResponseBean>
	 * 
	 * This method returns the response of a 'DescribeResultAccess' resquest for one sensor and one task
	 * 
	 * 
	 * @param _operation (String)						: operation of the FACEO service ('DescribeResultAccess')
	 * @param _parameters (DescribeResultAccessRequestBean)	: 'DescribeResultAccess' parameters for one sensor
	 * @param _webAccessMode (boolean)					: true for using web services, false else (for using kml files)
	 * 
	 * @return DescribeResultAccessResponseBean : 'DescribeResultAccess' response in HTML format
	 * 
	 * @gwt.typeArgs <com.astrium.faceo.bean.client.bean.programming.sps2.describeResultAccess.DescribeResultAccessResponseBean>
	 */
	public DescribeResultAccessResponseBean getDescribeResultAccessResult(
			String _operation, DescribeResultAccessRequestBean _parameters, boolean _webAccessMode) {

		DescribeResultAccessResponseBean describeResultAccessResponse = new DescribeResultAccessResponseBean();

		String messageErreur = null;
		int code_erreur = 0;

		log.debug("\n" + INFOS_CLASSE + " : operation : " + _operation );    
		if (_operation == null){
			messageErreur = "undefined operation";
			code_erreur = 531;
		} else {
			if (!_operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_DESCRIBE_RESULT_ACCESS)){
				messageErreur = "bad operation";
				code_erreur = 534;
			}
			if (_parameters.getType() == Sps2GeneralConstants.DESCRIBE_ACCESS_SENSOR) {
				if (_parameters.getSensorUrn().equalsIgnoreCase("")) {
					messageErreur = "undefined sensor Id";
					code_erreur = 536;
				}
			} else {
				if (_parameters.getTaskId().equalsIgnoreCase("")) {
					messageErreur = "undefined task Id";
					code_erreur = 537;
				}
			} // if (_parameters.getType() == DESCRIBE_ACCESS_SENSOR) {
		} // if (operation == null){

		// redirection vers la page d'erreur, "action inconnue"
		if (messageErreur != null ) {
			// retourne le message d'erreur
			log.debug("\n" + INFOS_CLASSE + " : error : " + messageErreur);
			describeResultAccessResponse.getError().setMessage("error : " + messageErreur);
			describeResultAccessResponse.getError().setCode(code_erreur);

			return describeResultAccessResponse;
		} // if (messageErreur != null ) {

		try {
			// lecture d'un fichier XML contenant une liste de demandes
			HttpServletRequest request = this.getThreadLocalRequest();
			String requestURL = request.getRequestURL().toString();
			int pos = requestURL.lastIndexOf(Sps2GeneralConstants.DESCRIBE_RESULT_ACCESS_CONTROLLER);
			String serverURL = requestURL;
			if (pos >0) {
				serverURL = requestURL.substring(0, pos);
			}

			FacadeSensorRequests facadeSensorsRequests = new FacadeSensorRequests();
			describeResultAccessResponse = 
				facadeSensorsRequests.getSensorDescribeResultAccess(_parameters, serverURL);

			return describeResultAccessResponse;
		} catch (FacadeSps2Exception exception) {
			log.info("\n" + INFOS_CLASSE + " : getDescribeResultAccessResult : FacadeSps2Exception : " + exception.getMessage());
			describeResultAccessResponse.getError().setMessage(exception.getMessage());
			describeResultAccessResponse.getError().setCode(exception.getCode());
			return describeResultAccessResponse;
		} catch (Exception exception) {
			log.info("\n" + INFOS_CLASSE + " : getDescribeResultAccessResult : Exception : " + exception.getMessage());
			describeResultAccessResponse.getError().setMessage(exception.getMessage());
			describeResultAccessResponse.getError().setCode(FacadeSps2Exception.SPS_EXCEPTION);
			return describeResultAccessResponse;
		} // try { 	

	} // public DescribeResultAccessResponseBean getDescribeResultAccessResult(String _operation, DescribeResultAccessRequestBean _parameters

} // class
