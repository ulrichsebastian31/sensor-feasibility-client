package com.astrium.faceo.server.service.programming.sps2.parsing;

/*
 * @(#)ParsingGetStatus.java	 1.0  14/06/2010
 *
 *
 * PROJET       : FACEO
 *
 * LANGUAGE     : Java
 *
 * DESCRIPTION  :  The ParsingGetStatus class parse the xml messages of SPS 2.0 'GetStatus' operation
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import net.opengis.www.sps._2_0.GetStatusResponseDocument;
import net.opengis.www.sps._2_0.GetStatusResponseType;
import net.opengis.www.sps._2_0.GetStatusResponseType.Status;

import org.apache.log4j.Logger;
import org.vast.ows.sps.GetStatusResponse;
import org.vast.ows.sps.ReservationReport;
import org.vast.ows.sps.SPSUtils;
import org.vast.ows.sps.StatusReport;
import org.vast.ows.sps.TaskingResponse;
import org.vast.util.DateTime;
import org.vast.util.DateTimeFormat;
import org.vast.xml.DOMHelper;

import com.astrium.faceo.client.bean.programming.sps2.TaskStatusReportBean;
import com.astrium.faceo.client.bean.programming.sps2.getStatus.GetStatusRequestBean;
import com.astrium.faceo.client.bean.programming.sps2.getStatus.GetStatusResponseBean;
import com.astrium.faceo.client.common.programming.sps2.Sps2GeneralConstants;

/**
 * <B>SITE FACEO</B> <BR>
 * 
 * <P>
 * The ParsingGetStatus class parse the xml messages of SPS 2.0 'GetStatus' operation
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
public class ParsingGetStatus {

	/** Le logger de cette classe */
	private static Logger log = Logger.getLogger(ParsingGetStatus.class);

	private static String ORIGINE = "ParsingGetStatus";

	protected SPSUtils utils = new SPSUtils();

	/**
	 * constructor
	 */
	public ParsingGetStatus() {    
	}

	/**
	 * get the result of a 'GetStatus' operation
	 * 
	 * @param _parameters (GetStatusRequestBean) 		: 'GetStatus' parameters
	 * @param _serverURL (String) 							: server URL
	 * @param _responseDocument (GetStatusResponseDocument)	: 'GetStatus' xml response
	 * 
	 * @return GetStatusResponseBean
	 * 
	 * @throws java.lang.Exception 
	 */
	public GetStatusResponseBean parseGetStatusResponse(GetStatusRequestBean _parameters, 
			String _serverURL,
			GetStatusResponseDocument _responseDocument)
	throws java.lang.Exception{

		String METHOD = "parseGetStatusResponse";

		GetStatusResponseBean response = new GetStatusResponseBean();

		if (_responseDocument != null) {
			log.debug("\n " + ORIGINE + " : " + METHOD + " : _responseDocument : " + _responseDocument.toString());

			// read back from buffer
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			outputStream.write(_responseDocument.toString().getBytes());

			log.debug("\n " + ORIGINE + " : ---------------------------- outputStream -------------------------");
			log.debug("\n " + ORIGINE + " : " + METHOD + " : outputStream : " + outputStream);
			log.debug("\n " + ORIGINE + " : ---------------------------- end outputStream -------------------------");

			// read response using DescribeTasking data definition

			// read back from buffer
			ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

			// read tasking params
			//			DescribeTaskingResponse describeTaskingResponse = 
			//				ParsingUtils.readDescribeTaskingResponse(ParsingUtils.getDescribeParametersXmlFile(_parameters.getSensorUrn(), _serverURL), _serverURL);

			// read response using DescribeTasking data definition
			DOMHelper domHelper = new DOMHelper(inputStream, false);

			TaskingResponse taskingResponse = null;
			GetStatusResponse statusResponse = null;
			try {
				//				taskingResponse = 
				//					(TaskingResponse)utils.readParameterizedResponse(domHelper, 
				//							domHelper.getRootElement(), 
				//							describeTaskingResponse.getFeasibilityReportExtendedData(), 
				//							Sps2GeneralConstants.SPS2_VERSION);
				//				taskingResponse = 
				//					(TaskingResponse)utils.readXMLResponse(domHelper, domHelper.getRootElement(), "SPS", "TaskingResponse");
				taskingResponse = 
					utils.readTaskingResponse(domHelper, domHelper.getRootElement(), "2.0");

			} catch (Exception exception) {
				log.debug("\n " + ORIGINE + " : Exception : " + exception.getLocalizedMessage());
			}

			if (taskingResponse == null) {
				// read back from buffer
				ByteArrayOutputStream outputStream2 = new ByteArrayOutputStream();
				outputStream2.write(_responseDocument.toString().getBytes());

				log.debug("\n " + ORIGINE + " : ---------------------------- outputStream2 -------------------------");
				log.debug("\n " + ORIGINE + " : " + METHOD + " : outputStream2 : " + outputStream2);
				log.debug("\n " + ORIGINE + " : ---------------------------- end outputStream2 -------------------------");

				// read response using DescribeTasking data definition

				// read back from buffer
				ByteArrayInputStream inputStream2 = new ByteArrayInputStream(outputStream2.toByteArray());

				// read tasking params
				//			DescribeTaskingResponse describeTaskingResponse = 
				//				ParsingUtils.readDescribeTaskingResponse(ParsingUtils.getDescribeParametersXmlFile(_parameters.getSensorUrn(), _serverURL), _serverURL);

				// read response using DescribeTasking data definition
				DOMHelper domHelper2 = new DOMHelper(inputStream2, false);

				try {
					statusResponse = 
						utils.readStatusResponse(domHelper2, domHelper2.getRootElement(), "2.0");
				} catch (Exception exception) {
					log.info("\n " + ORIGINE + " : Exception : " + exception.getLocalizedMessage());
				}
			} // if (taskingResponse == null) {

			// check that both requests are of the same type!
			//						assertTrue(taskingResponse.getReport().getClass() == FeasibilityReport.class);

			//			try {
			//				FeasibilityReport feasibilityReport = (FeasibilityReport) taskingResponse.getReport();
			//			} catch (Exception exception) {
			//				System.out.println("feasibilityReport : " + exception.getMessage());
			//			}

			StatusReport statusReport = null;
			if (taskingResponse != null) {
				if (taskingResponse.getReport().getClass() == StatusReport.class)  {
					statusReport  = taskingResponse.getReport();
				} else if (taskingResponse.getReport().getClass() == ReservationReport.class)  {
					statusReport  = (ReservationReport) taskingResponse.getReport();
				}
			} else if (statusResponse != null) {
				if (statusResponse.getReportList() != null) {
					if (statusResponse.getReportList().size() > 0) {
						if (statusResponse.getReportList().get(0).getClass() == StatusReport.class)  {
							statusReport  = statusResponse.getReportList().get(0);
						} else if (statusResponse.getReportList().get(0).getClass() == ReservationReport.class)  {
							statusReport  = (ReservationReport) statusResponse.getReportList().get(0);
						}
					} // if (statusResponse.getReportList().size() > 0) {
				} // if (statusResponse.getReportList() != null) {
			} // if (taskingResponse != null) {

			if (statusReport != null) {
				try {
					response.getStatusReport().setTask(statusReport.getTaskID());
					response.getStatusReport().setSensorId(_parameters.getSensorUrn());
					if (statusReport.getTaskStatus() != null) {
						response.getStatusReport().setStatusCode(statusReport.getTaskStatus().toString());
					}
					if (statusReport.getRequestStatus() != null) {
						response.getStatusReport().setRequestStatus(statusReport.getRequestStatus().toString());
					}
					response.getStatusReport().setTitle(statusReport.getTitle());
					response.getStatusReport().setDescription(statusReport.getDescription());
					DateTimeFormat dateTimeFormat = new DateTimeFormat();
					dateTimeFormat.applyPattern(Sps2GeneralConstants.TIME_FORMAT);
					if (statusReport.getEstimatedToC() != null) {
						DateTime estimatedToCDate = statusReport.getEstimatedToC();
						String estimatedToC = dateTimeFormat.format(estimatedToCDate);
						response.getStatusReport().setEstimatedToC(estimatedToC);
					}
					if (statusReport.getLastUpdate() != null) {
						DateTime updateTimeDate = statusReport.getLastUpdate();
						String updateTime = dateTimeFormat.format(updateTimeDate);
						response.getStatusReport().setUpdateTime(updateTime);
					}
					
					response.getStatusReport().setResponse(_responseDocument.toString());
				} catch (Exception exception) {
					log.info("\n " + ORIGINE + " : " + METHOD + " : taskingResponse.getReport().getClass() : " + exception.getMessage());
				}
			} else {
				//Transformation du flux XML en instance d'objet Java (beans).
				GetStatusResponseType responseType = _responseDocument.getGetStatusResponse();
				if (responseType != null) {
					Status[] statusArray = responseType.getStatusArray();

					if (statusArray != null) {
						if (statusArray.length > 0) {
							Status statusReport2 = responseType.getStatusArray(0);

							if (statusReport2 != null) {
								if (statusReport2.getStatusReport() != null) {
									ParsingStatusReport parsing = new ParsingStatusReport();
									TaskStatusReportBean statusReportBean = 
										parsing.parseStatusReportResponse(statusReport2.getStatusReport());
									response.setStatusReport(statusReportBean);
								} // if (statusReport2.getStatusReport() != null) {
							} // if (statusReport2 != null) {
						} // if (statusArray.length > 0) {
					} // if (statusArray != null) {
				} // if (responseType != null) {
			} // if (statusReport != null) {

			// transform XML response in HTML format
			// remplacement des chevrons par leur code HTML
			String result = "<h3>Task : "  + _parameters.getTaskId() + "<br></h3><br><br>" 
			+ ParsingUtils.setHTMLResponse(_responseDocument.toString());
			response.setResponse(result);
		} // if (_responseDocument != null) {

		return response;
	} // public GetStatusResponseBean parseGetStatusResponse(GetStatusRequestBean _parameters, 

} // class
