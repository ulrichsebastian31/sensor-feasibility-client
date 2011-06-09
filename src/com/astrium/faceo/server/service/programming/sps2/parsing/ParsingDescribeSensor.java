package com.astrium.faceo.server.service.programming.sps2.parsing;

/*
 * @(#)ParsingDescribeSensor.java	 1.0  19/11/2010
 *
 *
 * PROJET       : FACEO
 *
 * LANGUAGE     : Java
 *
 * DESCRIPTION  :  The ParsingDescribeSensor class parse the xml messages of SPS 2.0 'DescribeSensor' operation
 *
 * CREATION :
 * ---------------------------------------------------------------------
 * | Date       | Version | Description                                |
 * ---------------------------------------------------------------------
 * | 19/11/2010 |    1.0  |                                            |
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

import net.opengis.www.swes._2_0.DescribeSensorResponseDocument;

import org.vast.ows.sps.SPSUtils;

import com.astrium.faceo.client.bean.programming.sps2.describeSensor.DescribeSensorRequestBean;
import com.astrium.faceo.client.bean.programming.sps2.describeSensor.DescribeSensorResponseBean;
import com.astrium.faceo.client.common.programming.sps2.Sps2GeneralConstants;

/**
 * <B>SITE FACEO</B> <BR>
 * 
 * <P>
 * The ParsingDescribeSensor class parse the xml messages of SPS 2.0 'DescribeSensor' operation
 * 
 * The DescribeSensor operation allows a client to request a detailed description 
 * of a sensor. The request can be targeted at a description that was valid 
 * at a certain point in or during a certain period of time in the past. 
 * 
 * </P>
 * </P>
 * 
 * @author GR
 * @version 1.0, le 19/11/2010
 */
public class ParsingDescribeSensor {

	protected SPSUtils utils = new SPSUtils();

	/**
	 * constructor
	 */
	public ParsingDescribeSensor() {    
	}

	/**
	 * get the result of a 'DescribeSensor' operation
	 * 
	 * @param _parameters (DescribeSensorRequestBean) 				: 'DescribeSensor' parameters
	 * @param _responseDocument (DescribeSensorResponseDocument)	: 'DescribeSensor' xml response
	 * 
	 * @return DescribeSensorResponseBean
	 * 
	 * @throws java.lang.Exception 
	 */
	public DescribeSensorResponseBean parseDescribeSensorResponse(DescribeSensorRequestBean _parameters, 
			DescribeSensorResponseDocument _responseDocument)
	throws java.lang.Exception{

		DescribeSensorResponseBean response = null;

		if (_responseDocument != null) {
			response = new DescribeSensorResponseBean();
			
			// transform XML response in HTML format
			// response.setResponse(Sps2ParsingUtils.setHTMLResponse(_responseDocument.toString()));
			
			// remplacement des chevrons par leur code HTML
			String result = "<h3>" + _parameters.getSensorUrn() + "<br>" + Sps2GeneralConstants.SPS_DESCRIBE_SENSOR + "</h3><br><br>" 
								+ _responseDocument.toString().replace("<", "&lang;").replace(">", "&rang;<br>");
			response.setResponse(result);
		} // if (_responseDocument != null) {

		return response;

	} // public DescribeSensorResponseBean parseDescribeSensorResponse(DescribeSensorRequestBean _parameters, String _serverURL, DescribeSensorResponseDocument _responseDocument) throws java.lang.Exception{

} // class
