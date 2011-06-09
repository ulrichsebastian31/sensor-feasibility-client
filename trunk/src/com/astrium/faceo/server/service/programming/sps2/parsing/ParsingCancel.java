package com.astrium.faceo.server.service.programming.sps2.parsing;

/*
 * @(#)ParsingCancel.java	 1.0  13/07/2010
 *
 *
 * PROJET       : FACEO
 *
 * LANGUAGE     : Java
 *
 * DESCRIPTION  :  The ParsingCancel.java class parse the xml messages of SPS 2.0 'Cancel' operation
 *
 * CREATION :
 * ---------------------------------------------------------------------
 * | Date       | Version | Description                                |
 * ---------------------------------------------------------------------
 * | 13/07/2010 |    1.0  |                                            |
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

import net.opengis.www.sps._2_0.CancelResponseDocument;
import net.opengis.www.sps._2_0.CancelResponseType;
import net.opengis.www.sps._2_0.CancelResponseType.Result;
import net.opengis.www.sps._2_0.StatusReportType;

import org.apache.log4j.Logger;

import com.astrium.faceo.client.bean.programming.sps2.TaskStatusReportBean;
import com.astrium.faceo.client.bean.programming.sps2.cancel.CancelResponseBean;

/**
 * <B>SITE FACEO</B> <BR>
 * 
 * <P>
 * The ParsingCancel.java class parse the xml messages of SPS 2.0 'Cancel' operation
 *  
 * The Cancel operation allows SPS clients to cancel an accepted task (see clause 6.3.6). 
 * The service may reject the cancellation for specific reason. 
 * The response should indicate why the cancellation did not succeed. 
 * If the cancellation was rejected, the task remains in its current state.
 * 
 * </P>
 * </P>
 * 
 * @author GR
 * @version 1.0, le 13/07/2010
 */
public class ParsingCancel {

	/** Le logger de cette classe */
	private static Logger log = Logger.getLogger(ParsingCancel.class);

	private static String ORIGINE = "ParsingCancel";

	/**
	 * constructor
	 */
	public ParsingCancel() {    
	}

	/**
	 * get the result of a 'Cancel' operation
	 * 
	 * @param _responseDocument (CancelResponseDocument)	: 'Cancel' xml response
	 * 
	 * @return CancelResponseBean
	 * 
	 * @throws java.lang.Exception 
	 */
	public CancelResponseBean parseCancelResponse(CancelResponseDocument _responseDocument)
	throws java.lang.Exception{
		String METHOD = "parseCancelResponse";

		CancelResponseBean response = new CancelResponseBean();
		TaskStatusReportBean statusReport = new TaskStatusReportBean();

		if ( (_responseDocument != null) && (_responseDocument.getCancelResponse() != null) ) {
			CancelResponseType cancelType = _responseDocument.getCancelResponse();

			if (cancelType.getResult() != null) {
				// transform XML response in HTML format
				String htmlResponse = 
					"<br></h3><br><br>" + cancelType.toString().replace("<", "&lang;").replace(">", "&rang;<br>");
				response.setResponse(htmlResponse);

				Result result = cancelType.getResult();

				if (result.getStatusReport() != null) {
					StatusReportType statusReportType = result.getStatusReport();

					if (statusReportType != null) {
						try {
							log.debug("\n " + ORIGINE + " : " + METHOD + " : statusReportType : " + statusReportType.toString());
							ParsingStatusReport parsing = new ParsingStatusReport();
							statusReport = parsing.parseStatusReportResponse(statusReportType);
							response.setStatusReport(statusReport);
						} catch (Exception exception) {
							log.info("\n " + ORIGINE + " : " + METHOD + " : Exception : " + exception.getMessage());
						}
					} // if (statusReportType != null) {
				} // if (result.getStatusReport() != null) {
			} // if (cancelType.getResult() != null) {
		} // if (_responseDocument != null) {

		return response;
	} // public CancelResponseBean parseCancelResponse(

} // class
