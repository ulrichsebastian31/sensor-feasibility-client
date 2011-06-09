package com.astrium.faceo.server.service.programming.sps2.parsing;

import net.opengis.www.eosps._2_0.ValidateResponseDocument;
import net.opengis.www.eosps._2_0.ValidateResponseType;
import net.opengis.www.sps._2_0.StatusReportPropertyType;
import net.opengis.www.sps._2_0.StatusReportType;

import org.apache.log4j.Logger;

import com.astrium.faceo.client.bean.programming.sps2.TaskStatusReportBean;
import com.astrium.faceo.client.bean.programming.sps2.validate.ValidateResponseBean;

/**
 * <B>SITE FACEO</B> <BR>
 * 
 * <P>
 * The ParsingValidate class parse the xml messages of SPS 2.0 'Validate' operation
 *  
 * Several acquisition attempts are sometimes necessary to obtain a satisfying result 
 * (case of optical satellites on zones with cloudy tendency for example). 
 * The Validate operation can be used by the customer to indicate to the server 
 * that an acquisition is satisfactory and thus to stop collecting new images for this area.
 * 
 * </P>
 * </P>
 * 
 * @author GR
 * @version 1.0, le 10/12/2010
 */
public class ParsingValidate {

	/** Le logger de cette classe */
	private static Logger log = Logger.getLogger(ParsingValidate.class);

	private static String ORIGINE = "ParsingValidate";

	/**
	 * constructor
	 */
	public ParsingValidate() {    
	}

	/**
	 * get the result of a 'Validate' operation
	 * 
	 * @param _responseDocument (ValidateResponseDocument)	: 'Validate' xml response
	 * 
	 * @return ValidateResponseBean
	 * 
	 * @throws java.lang.Exception 
	 */
	public ValidateResponseBean parseValidateResponse(ValidateResponseDocument _responseDocument)
	throws java.lang.Exception{
		String METHOD = "parseValidateResponse";

		ValidateResponseBean response = new ValidateResponseBean();
		TaskStatusReportBean statusReport = new TaskStatusReportBean();

		if ( (_responseDocument != null) && (_responseDocument.getValidateResponse() != null) ) {
			ValidateResponseType validateType = _responseDocument.getValidateResponse();

			if (validateType.getResult() != null) {
				// transform XML response in HTML format
				String htmlResponse = 
					"<br></h3><br><br>" + validateType.toString().replace("<", "&lang;").replace(">", "&rang;<br>");
				response.setResponse(htmlResponse);

				StatusReportPropertyType result = validateType.getResult();

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
	} // public ValidateResponseBean parseValidateResponse(

} // class
