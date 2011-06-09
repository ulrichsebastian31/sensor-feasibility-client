package com.astrium.faceo.server.service.programming.sps2.parsing;

/*
 * @(#)ParsingDescribeTasking.java	 1.0  01/07/2010
 *
 *
 * PROJET       : FACEO
 *
 * LANGUAGE     : Java
 *
 * DESCRIPTION  :  The ParsingDescribeTasking class parse the xml messages of SPS 2.0 'DescribeTasking' operation
 *
 * CREATION :
 * ---------------------------------------------------------------------
 * | Date       | Version | Description                                |
 * ---------------------------------------------------------------------
 * | 01/07/2010 |    1.0  |                                            |
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

import net.opengis.www.sps._2_0.DescribeTaskingResponseDocument;

import org.vast.ows.sps.SPSUtils;

import com.astrium.faceo.client.bean.programming.sps2.describeTasking.DescribeTaskingRequestBean;
import com.astrium.faceo.client.bean.programming.sps2.describeTasking.DescribeTaskingResponseBean;
import com.astrium.faceo.client.common.programming.sps2.Sps2GeneralConstants;

/**
 * <B>SITE FACEO</B> <BR>
 * 
 * <P>
 * The ParsingDescribeTasking class parse the xml messages of SPS 2.0 'DescribeTasking' operation
 * 
 * The DescribeTasking operation allows SPS clients to retrieve 
 * a description of the data structures for the tasking parameters 
 * of a given sensor. 
 * Such a data structure description is encoded in SWE Common 
 * (see clause 7.4 ―SPS tasking parameters representation).
 * 
 * </P>
 * </P>
 * 
 * @author GR
 * @version 1.0, le 01/07/2010
 */
public class ParsingDescribeTasking {

	protected SPSUtils utils = new SPSUtils();

	/**
	 * constructor
	 */
	public ParsingDescribeTasking() {    
	}

	/**
	 * get the result of a 'DescribeTasking' operation
	 * 
	 * @param _parameters (DescribeTaskingRequestBean) 				: 'DescribeTasking' parameters
	 * @param _responseDocument (DescribeTaskingResponseDocument)	: 'DescribeTasking' xml response
	 * 
	 * @return DescribeTaskingResponseBean
	 * 
	 * @throws java.lang.Exception 
	 */
	public DescribeTaskingResponseBean parseDescribeTaskingResponse(DescribeTaskingRequestBean _parameters, 
			DescribeTaskingResponseDocument _responseDocument)
	throws java.lang.Exception{

		DescribeTaskingResponseBean response = null;

		if (_responseDocument != null) {
			response = new DescribeTaskingResponseBean();
			
			// transform XML response in HTML format
			// response.setResponse(Sps2ParsingUtils.setHTMLResponse(_responseDocument.toString()));
			
			// remplacement des chevrons par leur code HTML
			String result = "<h3>" + _parameters.getSensorUrn() + "<br>" + Sps2GeneralConstants.SPS_DESCRIBE_TASKING + "</h3><br><br>" 
								+ _responseDocument.toString().replace("<", "&lang;").replace(">", "&rang;<br>");
			response.setResponse(result);
			
//			StringWriter stringWriter = new StringWriter();
//			_responseDocument.save(stringWriter, new XmlOptions().setSavePrettyPrint().setSavePrettyPrintIndent(4));
//
//			response.setResponse(stringWriter.toString());
		} // if (_responseDocument != null) {

		return response;

	} // public DescribeTaskingResponseBean parseDescribeTaskingResponse(DescribeTaskingRequestBean _parameters, String _serverURL, DescribeTaskingResponseDocument _responseDocument) throws java.lang.Exception{

} // class
