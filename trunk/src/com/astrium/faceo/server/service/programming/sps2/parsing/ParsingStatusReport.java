package com.astrium.faceo.server.service.programming.sps2.parsing;

/*
 * @(#)ParsingStatusReport.java	 1.0  20/08/2010
 *
 *
 * PROJET       : FACEO
 *
 * LANGUAGE     : Java
 *
 * DESCRIPTION  :  The ParsingStatusReport class parse the xml messages of SPS 2.0 'GetStatus', 'Cancel' operation
 *
 * CREATION :
 * ---------------------------------------------------------------------
 * | Date       | Version | Description                                |
 * ---------------------------------------------------------------------
 * | 14/06/2010 |    1.0  |                                            |
 * ---------------------------------------------------------------------
 *
 * MODIFICATION :
 * ---------------------------------------------------------------------
 * | Date       | Version | Description                                |
 * ---------------------------------------------------------------------
 * |            |         |                                            |
 * ---------------------------------------------------------------------
 *
 */

// import 

import java.util.Date;

import net.opengis.www.sps._2_0.StatusReportType;

import org.apache.log4j.Logger;
import org.vast.ows.sps.SPSUtils;
import org.vast.util.DateTimeFormat;

import com.astrium.faceo.client.bean.programming.sps2.TaskStatusReportBean;
import com.astrium.faceo.client.common.programming.sps2.Sps2GeneralConstants;

/**
 * <B>SITE FACEO</B> <BR>
 * 
 * <P>
 * The ParsingStatusReport class parse the xml messages of SPS 2.0 'GetStatus', Cancel' operations
 *  
 * The GetStatus operation allows SPS clients to retrieve status reports 
 * about a tasking request or a task. 
 * This operation is the default mechanism to retrieve status information 
 * about a task or tasking request.
 * 
 * </P>
 * </P>
 * 
 * @author GR
 * @version 1.0, le 14/06/2010
 */
public class ParsingStatusReport {

	/** Le logger de cette classe */
	private static Logger log = Logger.getLogger(ParsingStatusReport.class);

	private static String ORIGINE = "ParsingStatusReport";

	protected SPSUtils utils = new SPSUtils();

	/**
	 * constructor
	 */
	public ParsingStatusReport() {    
	}

	/**
	 * get the result of a 'GetStatus', 'Cancel' operations
	 * 
	 * @param _statusReportType (StatusReportDocument)	: 'GetStatus' or 'Cancel' xml response
	 * 
	 * @return TaskStatusReportBean
	 * 
	 * @throws java.lang.Exception 
	 */
	public TaskStatusReportBean parseStatusReportResponse(StatusReportType _statusReportType)
	throws java.lang.Exception{
		String METHOD = "parseStatusReportResponse";

		TaskStatusReportBean statusReport = null;

		if (_statusReportType != null) {
			log.debug("\n " + ORIGINE + " : " + METHOD + " : statusReportDocument : " + _statusReportType.toString());

			statusReport = new TaskStatusReportBean();

			// parsing de l'element <StatusReport>
			//	<sps:result>
			//		<sps:StatusReport>
			//			<sps:title>CFI based Feasibility Study</sps:title>
			//			<sps:abstract>Task cancelled successfully</sps:abstract>
			//			<sps:sensorIdentifier>urn:ogc:object:feature:Sensor:deimos-space:HMA:SAR-1</sps:sensorIdentifier>
			//			<sps:taskIdentifier>http://www.deimos-space.com/sps/2010-08-06T07:55:22.120Z</sps:taskIdentifier>
			//		 	<sps:updateTime>2010-08-06T07:55:32.352Z</sps:updateTime>
			//		 	<sps:statusCode>CANCELLED</sps:statusCode>
			//		  	<sps:estimatedToC>2010-08-06T08:00:22Z</sps:estimatedToC>
			//		</sps:StatusReport>
			//	</sps:result>

			// title
			if (_statusReportType.getIdentifier() != null) {
				statusReport.setTitle(_statusReportType.getIdentifier());
			}
			
//			_statusReportType.getId();

			// description
			if (_statusReportType.getDescription() != null) {
				statusReport.setDescription(_statusReportType.getDescription());
			}

			// event
			if (_statusReportType.getEvent() != null) {
				statusReport.setEvent(_statusReportType.getEvent());
			}

			// request status
			if (_statusReportType.getRequestStatus() != null) {
				statusReport.setRequestStatus(_statusReportType.getRequestStatus());
			}

			// percent completion
			statusReport.setPercentCompletion(_statusReportType.getPercentCompletion());

			// sensor identifier
			if (_statusReportType.getProcedure() != null) {
				statusReport.setSensorId(_statusReportType.getProcedure());
			}

			// task identifier
			if (_statusReportType.getTask() != null) {
				statusReport.setTask(_statusReportType.getTask());
			}

			// estimated To Completed date and update date
			DateTimeFormat dateTimeFormat = new DateTimeFormat();
			dateTimeFormat.applyPattern(Sps2GeneralConstants.TIME_FORMAT);
			if (_statusReportType.getEstimatedToC() != null) {
				Date estimatedToCDate = _statusReportType.getEstimatedToC().getTime();
				String estimatedToC = dateTimeFormat.format(estimatedToCDate);
				statusReport.setEstimatedToC(estimatedToC);
			}
			if (_statusReportType.getUpdateTime() != null) {
				Date updateTimeDate = _statusReportType.getUpdateTime().getTime();
				String updateTime = dateTimeFormat.format(updateTimeDate);
				statusReport.setUpdateTime(updateTime);
			}

			// status code
			if (_statusReportType.getTaskStatus() != null) {
				statusReport.setStatusCode(_statusReportType.getTaskStatus());
			}
			
			// response
			statusReport.setResponse(_statusReportType.toString());
		} // if (_statusReport != null) {

		return statusReport;
	} // public TaskStatusReportBean parseStatusReportResponse(StatusReportType _statusReportType)

} // class
