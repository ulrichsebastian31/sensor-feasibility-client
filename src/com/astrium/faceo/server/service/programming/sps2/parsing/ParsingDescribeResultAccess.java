package com.astrium.faceo.server.service.programming.sps2.parsing;

/*
 * @(#)ParsingDescribeResultAccess.java	 1.0  08/07/2010
 *
 *
 * PROJET       : FACEO
 *
 * LANGUAGE     : Java
 *
 * DESCRIPTION  :  The ParsingDescribeResultAccess class parse the xml messages of SPS 2.0 'DescribeResultAccess' operation
 *
 * CREATION :
 * ---------------------------------------------------------------------
 * | Date       | Version | Description                                |
 * ---------------------------------------------------------------------
 * | 08/07/2010 |    1.0  |                                            |
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

import net.opengis.www.sps._2_0.DescribeResultAccessResponseDocument;

import org.vast.ows.sps.SPSUtils;

import com.astrium.faceo.client.bean.programming.sps2.describeResultAccess.DescribeResultAccessRequestBean;
import com.astrium.faceo.client.bean.programming.sps2.describeResultAccess.DescribeResultAccessResponseBean;
import com.astrium.faceo.client.common.programming.sps2.Sps2GeneralConstants;

/**
 * <B>SITE FACEO</B> <BR>
 * 
 * <P>
 * The ParsingDescribeResultAccess class parse the xml messages of SPS 2.0 'DescribeResultAccess' operation
 * 
 * The DescribeResultAccess operation allows SPS clients to retrieve 
 * information how to access data that was produced by a specific task, 
 * or how to retrieve data for a given sensor that is tasked by this SPS in general.
 * The response may point to a SOS, WMS, WFS any other OGC Web Service 
 * that provides data any data file or folder on a ftp server any data file 
 * or file container that is accessible over the Internet Clients provide the ID 
 * of either a sensor or task to identify what information they are interested in. 
 * 
 * </P>
 * </P>
 * 
 * @author GR
 * @version 1.0, le 08/07/2010
 */
public class ParsingDescribeResultAccess {

	protected SPSUtils utils = new SPSUtils();

	/**
	 * constructor
	 */
	public ParsingDescribeResultAccess() {    
	}

	/**
	 * get the result of a 'DescribeResultAccess' operation
	 * 
	 * @param _parameters (DescribeResultAccessRequestBean) 			: 'DescribeResultAccess' parameters
	 * @param _responseDocument (DescribeResultAccessResponseDocument)	: 'DescribeResultAccess' xml response
	 * 
	 * @return DescribeResultAccessResponseBean
	 * 
	 * @throws java.lang.Exception 
	 */
	public DescribeResultAccessResponseBean parseDescribeResultAccessResponse(DescribeResultAccessRequestBean _parameters, 
			DescribeResultAccessResponseDocument _responseDocument)
	throws java.lang.Exception{

		DescribeResultAccessResponseBean response = null;

		if (_responseDocument != null) {
			response = new DescribeResultAccessResponseBean();

			// transform XML response in HTML format
			// response.setResponse(Sps2ParsingUtils.setHTMLResponse(_responseDocument.toString()));

			// remplacement des chevrons par leur code HTML
			String result = "<h3>";
			if (_parameters.getType() == Sps2GeneralConstants.DESCRIBE_ACCESS_SENSOR) {
				result = result + _parameters.getSensorUrn();
			} else {
				result = result + "Task : "  + _parameters.getTaskId();
			} // if (_parameters.getType() == Sps2GeneralConstants.DESCRIBE_ACCESS_SENSOR) {

			result = result + "<br></h3><br><br>"
					+ _responseDocument.toString().replace("<", "&lang;").replace(">", "&rang;<br>");
			response.setResponse(result);
			
//			StringWriter stringWriter = new StringWriter();
//			_responseDocument.save(stringWriter, new XmlOptions().setSavePrettyPrint().setSavePrettyPrintIndent(4));
//
//			response.setResponse(stringWriter.toString());
		} // if (_responseDocument != null) {

		return response;

	} // public DescribeResultAccessResponseBean parseDescribeResultAccessResponse(DescribeResultAccessRequestBean _parameters, String _serverURL, DescribeResultAccessResponseDocument _responseDocument) throws java.lang.Exception{

} // class
