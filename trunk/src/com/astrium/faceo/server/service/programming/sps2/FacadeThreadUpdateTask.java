package com.astrium.faceo.server.service.programming.sps2;

// import 

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PropertyResourceBundle;

import net.opengis.www.sps._2_0.GetStatusDocument;
import net.opengis.www.sps._2_0.GetStatusResponseDocument;
import net.opengis.www.sps._2_0.GetStatusType;

import org.apache.axis2.AxisFault;
import org.apache.log4j.Logger;

import com.astrium.faceo.client.bean.programming.sps2.SensorBean;
import com.astrium.faceo.client.bean.programming.sps2.TaskBean;
import com.astrium.faceo.client.bean.programming.sps2.TaskStatusReportBean;
import com.astrium.faceo.client.bean.programming.sps2.getStatus.GetStatusRequestBean;
import com.astrium.faceo.client.bean.programming.sps2.getStatus.GetStatusResponseBean;
import com.astrium.faceo.client.common.programming.sps2.Sps2GeneralConstants;
import com.astrium.faceo.server.bean.exception.TaskException;
import com.astrium.faceo.server.common.exception.sps2.FacadeSps2Exception;
import com.astrium.faceo.server.service.programming.sps2.parsing.ParsingGetStatus;

/**
 * <B>SITE FACEO</B> <BR>
 * 
 * <P>
 * La classe FacadeThreadUpdateTask reprend les services utilis&eacute;s pendant le 
 * processus de lecture des r&eacute;ponses aux op&eacute;tions SPS 2.0
 * </P>
 * </P>
 * 
 * @author GR
 * @version 1.0, le 12/05/2010
 */
public class FacadeThreadUpdateTask {

	/** Le logger de cette classe */
	private static Logger log = Logger.getLogger(FacadeThreadUpdateTask.class);

	private static String ORIGINE = "FacadeThreadUpdateTask";

	/**
	 * constructor
	 */
	public FacadeThreadUpdateTask() {    
	}

	/**
	 * Construction of the SPS 'GetStatus' request
	 * 
	 * @param _requestBean (GetStatusRequestBean) : 'GetStatus' operation parameters
	 * @param _serverURL (String) 				: server URL
	 * 
	 * @return ReserveDocument : XML document that represents the XML request for the SPS operation 'GetStatus'
	 * @throws FacadeSps2Exception
	 */
	private GetStatusDocument getStatusRequest(GetStatusRequestBean _requestBean,
			String _serverURL) 
	throws FacadeSps2Exception {

		try {
			net.opengis.www.sps._2_0.GetStatusDocument getStatusDocument =
				GetStatusDocument.Factory.newInstance();

			// create 'GetStatus' request with EO SPS Library (Spot Image : EO Application profile for SPS 2.0)
			//			GetStatusRequest getStatusRequest = new GetStatusRequest();
			//			getStatusRequest.setVersion(Sps2GeneralConstants.SPS2_VERSION);
			//			getStatusRequest.setService(Sps2GeneralConstants.SPS2_SERVICE);
			//			getStatusRequest.setTaskID(getStatusRequest.getTaskID());

			/*
			 * <GetStatus service="SPS" version="2.0.0" xmlns="http://www.opengis.net/sps/2.0">
		         <!--Zero or more repetitions:-->
		         <extension>?</extension>
		         <task>http://www.deimos-space.com/sps/2010-06-04T06:59:34.893Z</task>
		         <!--Optional:-->
		         <since>2010-01-01T00:00:00</since>
			 * </GetStatus>
			 */

			GetStatusType getStatusType = getStatusDocument.addNewGetStatus();
			getStatusType.setService(Sps2GeneralConstants.SPS2_SERVICE);
			getStatusType.setVersion(Sps2GeneralConstants.SPS2_VERSION);
			getStatusType.setTask(_requestBean.getTaskId());
			
			// add since tag with date in past to obtain all the meshes
			// "2010-01-01T00:00:00Z"
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"); 
			Date sinceDate = dateFormat.parse("2010-01-01T00:00:00Z");
			Calendar sinceCalendar = Calendar.getInstance();
			sinceCalendar.setTime(sinceDate);
			getStatusType.setSince(sinceCalendar);

			log.debug("\n ------------------------------------------------ ");
			log.debug("\n" + ORIGINE + " : getStatusDocument : " + getStatusDocument.toString() +"\n");
			log.debug("\n ------------------------------------------------ ");

			return getStatusDocument;

		} catch (Exception exception) {
			log.info("\n" + ORIGINE + " Exception : " + exception.getMessage());

			return null;
		} 

	} // private GetStatusDocument getStatusRequest(GetStatusRequestBean _requestBean,

	/**
	 * To get the Task Status of one Task
	 * 
	 * @param _parameters (GetStatusRequestBean) : 'GetStatus' parameters
	 * @param _serverURL (String) 	: server URL
	 * @param _sensors (Map<String, SensorBean>) : sensors informations
	 * 
	 * @throws FacadeSps2Exception
	 */
	public void updateTaskStatus(
			GetStatusRequestBean _parameters, 
			String _serverURL,
			Map<String, SensorBean> _sensors) 
	throws FacadeSps2Exception {

		GetStatusResponseBean response = new GetStatusResponseBean();

		try {
			// SOAP endpoint
			String endPointSOAP = "http://dione.deimos-space.com/HMA-FO/services/HMA-SPS";
			endPointSOAP = getEndPoint(_parameters.getSensorUrn(), _serverURL, _sensors);

			// stub creation
			com.deimos_space.www.hma_sps.HMASPSStub stub =
				new com.deimos_space.www.hma_sps.HMASPSStub(endPointSOAP);

			// request creation
			net.opengis.www.sps._2_0.GetStatusDocument getStatusDocument = 
				getStatusRequest(_parameters, _serverURL);

			// 'GetStatus' service invocation
			GetStatusResponseDocument responseDocument = null;
			try {
				responseDocument = stub.getStatus(getStatusDocument);
			} catch (AxisFault exception) {
				net.opengis.www.ows._1_1.ExceptionReportDocument exceptionReportDocument = 
					net.opengis.www.ows._1_1.ExceptionReportDocument.Factory.parse(exception.getDetail().toString());

				log.info("\n" + ORIGINE + " : AxisFault : updateTaskStatus : " + exceptionReportDocument.toString() +"\n");
			} catch (Exception exception) {
				log.info("\n" + ORIGINE + " : updateTaskStatus : Exception : " + exception.toString() +"\n");
			}

			// GetStatus response parsing
			if (responseDocument != null) {
				log.debug("\n ------------------------------------------------ ");
				log.debug("\n" + ORIGINE + " : updateTaskStatus : responseDocument : " + responseDocument.toString() +"\n");
				log.debug("\n ------------------------------------------------ ");

				// parse 'GetStatus' response
				ParsingGetStatus parsing = new ParsingGetStatus();
				response = 
					parsing.parseGetStatusResponse(_parameters, _serverURL, responseDocument);

				//	update Task in database if status has changed
				updateTaskInDatabase(_parameters.getTaskId(), 
						response.getStatusReport(), 
						responseDocument.toString(), 
						Sps2GeneralConstants.SPS_GET_STATUS,
						true);
			} // if (responseDocument != null) {
		} catch (AxisFault axisFault){
			log.info("\n" + ORIGINE + " : AxisFault : " + axisFault.getMessage());
		} catch (Exception exception){
			log.info("\n" + ORIGINE + " : Exception : " + exception.getMessage());
		} finally {
		}

	} // public void updateTaskStatus

	/**
	 * To get the SPS endPoint for one sensor
	 * 
	 * @param _sensorUrn (String)	: sensor URN
	 * @param _serverURL (String)	: server URL
	 * @param _sensors (Map<String, SensorBean>)	: sensors informations
	 * 
	 * @return String : the SPS endPoint for one sensor
	 */
	private String getEndPoint(String _sensorUrn, String _serverURL, Map<String, SensorBean> _sensors) {

		// SOAP endpoint
		String endPointSOAP = "http://dione.deimos-space.com/HMA-FO/services/HMA-SPS";

		// lecture d'une propriete stockee dans un fichier de properties
		try {
			PropertyResourceBundle prb = 
				(PropertyResourceBundle)PropertyResourceBundle.getBundle(Sps2GeneralConstants.HMAFO_PROPERTIES);
			endPointSOAP = prb.getString("sps2Url").trim();
			log.debug("\n" + ORIGINE + " : >>> Reading file '" + Sps2GeneralConstants.HMAFO_PROPERTIES + "' OK !!!");    
		} catch (Exception e) {
			log.info("\n" + ORIGINE + " : >>> Reading file '" + Sps2GeneralConstants.HMAFO_PROPERTIES + "' KO !!!");    
		}
		log.debug("\n ------------------------------------------------ ");
		log.debug("\n" + ORIGINE + " : getEndPoint endPoint 1 : " + endPointSOAP);

		if (! _sensorUrn.equalsIgnoreCase("")) {
			boolean find = false;
			for(Entry<String, SensorBean> sensor : _sensors.entrySet()) {
				if (! find) {
					String sensorUrn = sensor.getKey();
					SensorBean sensorBean = sensor.getValue();

					if (_sensorUrn.equalsIgnoreCase(sensorUrn)) {
						endPointSOAP = sensorBean.getEndPoint();
					} // if
				} // if (! find) {
			} // for(Entry<String, SensorBean> sensorsMap : sensors.entrySet()) {
		} // if

		log.debug("\n" + ORIGINE + " : getEndPoint endPoint 2 : " + endPointSOAP);
		log.debug("\n ------------------------------------------------ ");
		return endPointSOAP;

	} // private String getEndPoint(String _sensorUrn, String _serverURL, Map<String, SensorBean> _sensors) {

	/**
	 * @param _updatedTask (boolean) :  true if Updated task from updated Thread, false else
	 */
	private void updateTaskInDatabase(String _taskId, 
			TaskStatusReportBean _statusReport, 
			String response, 
			String _responseType,
			boolean _updatedTask) {

		// ----------------------- load task's request and task's response from dataBase -----------------------
		FacadeTasks facadeTask = new FacadeTasks();

		TaskBean _taskBean = null;
		try {
			_taskBean = facadeTask.loadOneTask(_taskId);

			if ((_taskBean != null) && (!_taskBean.getId().equalsIgnoreCase(""))) {
				// update Task in database if status has changed
				boolean updateTask = false;
				
				String statusCode = (_statusReport.getStatusCode() != null) ? _statusReport.getStatusCode():"";
				String requestStatus = (_statusReport.getRequestStatus() != null) ? _statusReport.getRequestStatus():"";
				String statusResponse = _statusReport.getResponse();
				
				if (!statusCode.equalsIgnoreCase(_taskBean.getStatus()))  {
//					|| (!requestStatus.equalsIgnoreCase(_taskBean.getRequestStatus()))  
//					|| (!_statusResponse.equalsIgnoreCase(_taskBean.getResponse())) ) {
					updateTask = true;
				} else if (!requestStatus.equalsIgnoreCase(_taskBean.getRequestStatus())) {
					updateTask = true;
				} else if ( (statusResponse != null) && (!statusResponse.equalsIgnoreCase(_taskBean.getResponse())) ) {
					updateTask = true;
				} else if ((_responseType.equalsIgnoreCase(Sps2GeneralConstants.SPS_CANCEL))
						&& ((statusCode.equalsIgnoreCase(Sps2GeneralConstants.STATUS_CANCELLED)) 
								|| (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_CANCELLED)))) {
					updateTask = true;
				} // if

				if (updateTask) {
					if ((statusCode.equalsIgnoreCase(Sps2GeneralConstants.STATUS_FEASIBLE))
							|| (statusCode.equalsIgnoreCase(Sps2GeneralConstants.STATUS_ACCEPTED))
							|| (statusCode.equalsIgnoreCase(Sps2GeneralConstants.STATUS_COMPLETED))
							|| (statusCode.equalsIgnoreCase(Sps2GeneralConstants.STATUS_RESERVED))) {
						_taskBean.setResponse(response);
						_taskBean.setResponseType(_responseType);
					} else if ((requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_FEASIBLE))
							|| (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_ACCEPTED))
							|| (requestStatus
									.equalsIgnoreCase(Sps2GeneralConstants.STATUS_COMPLETED))
							|| (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_RESERVED))) {
						_taskBean.setResponse(response);
						_taskBean.setResponseType(_responseType);
					} // if (

					_taskBean.setStatus(statusCode);
					_taskBean.setRequestStatus(requestStatus);
					_taskBean.setUpdatedTask(_updatedTask);

					facadeTask.updateTask(_taskBean);
				} // if (updateTask) {
			} // if ((_taskBean != null)
		} catch (TaskException exception) {
			log.info("\n" + ORIGINE + " : updateTaskInDatabase : Exception : " + exception.getMessage());
		}

	} // private void updateTaskInDatabase() {

} // class
