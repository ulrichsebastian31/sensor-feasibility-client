package com.astrium.faceo.server.service.programming.sps2.parsing;

/*
 * @(#)ParsingUtils.java	 1.0  14/06/2010
 *
 *
 * PROJET       : FACEO
 *
 * LANGUAGE     : Java
 *
 * DESCRIPTION  :  The ParsingUtils class parse the xml messages of SPS 2.0
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

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.vast.ows.sps.DescribeTaskingResponse;
import org.vast.ows.sps.SPSUtils;
import org.vast.xml.DOMHelper;

import com.astrium.faceo.client.bean.programming.sps2.SensorBean;
import com.astrium.faceo.client.common.programming.sps2.Sps2GeneralConstants;

/**
 * <B>SITE FACEO</B> <BR>
 * 
 * <P>
 * The ParsingUtils class parse the xml messages of SPS 2.0
 * </P>
 * </P>
 * 
 * @author GR
 * @version 1.0, le 14/06/2010
 */
public class ParsingUtils {

	/** Le logger de cette classe */
	private static Logger log = Logger.getLogger(ParsingUtils.class);

	private static String ORIGINE = "ParsingUtils";
	
    private static Map<String, SensorBean> sensors = null; // Stockage de l'unique instance de ces données.

	/**
	 * constructor
	 */
	public ParsingUtils() {   

	} // public ParsingUtils() { 
	
	/**
	 * set the sensors informations
	 * 
	 * @param _serverURL (String)	: server url
	 */
	public static void setSensorsDescriptions(String _serverURL) {

        if (sensors == null) {
        	sensors = new HashMap<String, SensorBean>();

    		try {
    			// URL sensorsUrl = new URL(_serverURL + "/resources/sensors.xml");

    			SAXBuilder saxBuilder = new SAXBuilder();

    			Document document = saxBuilder.build(_serverURL + Sps2GeneralConstants.SENSORS_XML);

    			Element root = document.getRootElement();

    			if (root != null) {
    				@SuppressWarnings("unchecked")
    				List<Element> childrens = root.getChildren("sensor");

    				if (childrens != null) {
    					for (Iterator<Element> iterator = childrens.iterator(); iterator.hasNext();) {
    						Element elementSensor = (Element) iterator.next();

    						String id = elementSensor.getAttributeValue("id");
    						String activeStr = elementSensor.getAttributeValue("active");
    						boolean active = false;
    						if (activeStr.equalsIgnoreCase("true")) {
    							active = true;
    						} // if
    						String synch = elementSensor.getAttributeValue("synchronous");
    						boolean synchronous = false;
    						if (synch.equalsIgnoreCase("true")) {
    							synchronous = true;
    						} // if
    						String type = elementSensor.getAttributeValue("type");
    						String describeFile = elementSensor.getAttributeValue("describeFile");

    						Element elementUrn = elementSensor.getChild("urn");
    						if (elementUrn != null) {
    							SensorBean sensor = new SensorBean();
    							sensor.setId(id);
    							sensor.setActive(active);
    							sensor.setSynchronous(synchronous);
    							sensor.setType(type);
    							sensor.setDescribeFile(describeFile);

    							sensor.setUrn(elementUrn.getValue());

    							Element elementEndpoint = elementSensor.getChild("endpoint");
    							if (elementEndpoint != null) {
    								sensor.setEndPoint(elementEndpoint.getValue());
    							} // if

    							Element elementGetCapabilitiesUrl = elementSensor.getChild("getCapabilitiesUrl");
    							if (elementGetCapabilitiesUrl != null) {
    								sensor.setGetCapabilitiesUrl(elementGetCapabilitiesUrl.getValue());
    							} // if

    							sensors.put(sensor.getUrn(), sensor);
    						} // if (elementUrn != null) {
    					} // for (Iterator<Element> iterator = childrens.iterator(); iterator.hasNext();) {
    				} // if (childrens != null) {

    			} // if (root != null) {

    		} catch (Exception exception) {
    			log.info("\n" + ORIGINE + " : setSensorsDescriptions : Exception : " + exception.getMessage());
    		}
        } // if (sensors == null) {
         
	} // public static synchronized void setSensorsDescriptions(String _serverURL) {

	/**
	 * 
	 * @param _sensorUrn (String) : sensor urn (sample : urn:ogc:object:feature:Sensor:deimos-space:HMA:OPT-2)
	 * @param _serverUrl (String) : server URL
	 * 
	 * @return String : xml file location (sample : /resources/describeTaskingResponses/DescribeTaskingResponse_OPT2.xml)
	 */
	public static String getDescribeParametersXmlFile(String _sensorUrn, String _serverUrl) {

		String file = "";

		setSensorsDescriptions(_serverUrl);

		boolean find = false;
		for(Entry<String, SensorBean> sensor : sensors.entrySet()) {
			if (! find) {
				String sensorUrn = sensor.getKey();
				SensorBean sensorBean = sensor.getValue();

				if (_sensorUrn.equalsIgnoreCase(sensorUrn)) {
					file = sensorBean.getDescribeFile();
				} // if
			} // if (! find) {
		} // for(Entry<String, SensorBean> sensorsMap : sensors.entrySet()) {

		return file;

	} // public static String getDescribeParametersXmlFile(String _sensorUrn, String _serverUrl) {

	/**
	 * 
	 * @param _sensorUrn (String) : sensor urn (sample : urn:ogc:object:feature:Sensor:deimos-space:HMA:OPT-2)
	 * @param _serverUrl (String) : server URL
	 * 
	 * @return boolean : true if optical sensor, false else
	 */
	public static boolean getSensorOpticalType(String _sensorUrn, String _serverUrl) {

		boolean opticalType = false;

		setSensorsDescriptions(_serverUrl);

		boolean find = false;
		for(Entry<String, SensorBean> sensor : sensors.entrySet()) {
			if (! find) {
				String sensorUrn = sensor.getKey();
				SensorBean sensorBean = sensor.getValue();

				if (_sensorUrn.equalsIgnoreCase(sensorUrn)) {
					sensorBean.getType();
					if (sensorBean.getType().equalsIgnoreCase(Sps2GeneralConstants.SENSOR_OPTICAL)) {
						opticalType = true;
					} // if
				} // if
			} // if (! find) {
		} // for(Entry<String, SensorBean> sensorsMap : sensors.entrySet()) {

		return opticalType;

	} // public static boolean getSensorOpticalType(String _sensorUrn, String _serverUrl) {

	/**
	 * 
	 * @param _sensorUrn (String) : sensor urn (sample : urn:ogc:object:feature:Sensor:deimos-space:HMA:OPT-2)
	 * @param _serverUrl (String) : server URL
	 * 
	 * @return String : SOAP endpoint (sample : http://dione.deimos-space.com/HMA-FO/services/HMA-SPS)
	 */
	public static String getEndPoint(String _sensorUrn, String _serverUrl) {

		String endPoint = "";

		setSensorsDescriptions(_serverUrl);

		boolean find = false;
		for(Entry<String, SensorBean> sensor : sensors.entrySet()) {
			if (! find) {
				String sensorUrn = sensor.getKey();
				SensorBean sensorBean = sensor.getValue();

				if (_sensorUrn.equalsIgnoreCase(sensorUrn)) {
					endPoint = sensorBean.getEndPoint();
				} // if
			} // if (! find) {
		} // for(Entry<String, SensorBean> sensorsMap : sensors.entrySet()) {

		return endPoint;

	} // public static String getEndPoint(String _sensorUrn, String _serverUrl) {

	/**
	 * 
	 * @param _sensorUrn (String) : sensor urn (sample : urn:ogc:object:feature:Sensor:deimos-space:HMA:OPT-2)
	 * @param _serverUrl (String) : server URL
	 * 
	 * @return String : GetCapabilities URL (sample : http://dione.deimos-space.com/HMA-FO/GetCapabilitiesServlet?service=SPS&request=GetCapabilities)
	 */
	public static String getGetCapabilitiesUrl(String _sensorUrn, String _serverUrl) {

		String getCapabilitiesUrl = "";

		setSensorsDescriptions(_serverUrl);

		boolean find = false;
		for(Entry<String, SensorBean> sensor : sensors.entrySet()) {
			if (! find) {
				String sensorUrn = sensor.getKey();
				SensorBean sensorBean = sensor.getValue();

				if (_sensorUrn.equalsIgnoreCase(sensorUrn)) {
					getCapabilitiesUrl = sensorBean.getGetCapabilitiesUrl();
				} // if
			} // if (! find) {
		} // for(Entry<String, SensorBean> sensorsMap : sensors.entrySet()) {

		return getCapabilitiesUrl;

	} // public static String getGetCapabilitiesUrl(String _sensorUrn, String _serverUrl) {
	
	/**
	 * 
	 * @param _describeParametersXmlFile (String) : describe parameters xml file
	 * @param _serverURL (String) : 
	 * 
	 * @return DescribeTaskingResponse
	 * 
	 * @throws Exception
	 */
	public static DescribeTaskingResponse readDescribeTaskingResponse(String _describeParametersXmlFile, String _serverURL) throws Exception {

		// read parameter structure from example file
		String filePath = _describeParametersXmlFile;
		//		URL respUrl = getClass().getResource(filePath);
		String describeFilePath = _serverURL + filePath;
		URL respUrl = new URL(describeFilePath);
		String respUrlString = respUrl.toString();
		DOMHelper dom;
		DescribeTaskingResponse response = null;
		try {
			dom = new DOMHelper(respUrlString, false);

			try {
				SPSUtils utils = new SPSUtils();
				response = utils.readDescribeTaskingResponse(dom.getRootElement());
			} catch (Exception exception) {
				log.debug("\n" + ORIGINE + " : readDescribeTaskingResponse 1 : Exception : " + exception.getLocalizedMessage());
			}
		} catch (Exception exception) {
			log.debug("\n" + ORIGINE + " : readDescribeTaskingResponse 2 : Exception : " + exception.getLocalizedMessage());
		}

		return response;

	} // public static DescribeTaskingResponse readDescribeTaskingResponse(int _caseIndex, String _serverURL) throws Exception

	/**
	 * 
	 * @param _responseToTransform (String) : XML string to transfom in HTML format
	 * 
	 * @return String : string in HTML format
	 */
	public static String setHTMLResponse(String _responseToTransform) {

		StringBuffer response = new StringBuffer();

		if (! _responseToTransform.equalsIgnoreCase("")) {
			//Transformation du flux XML en reponse au format HTML.

			SAXBuilder builder = new SAXBuilder();
			Reader reader = new StringReader(_responseToTransform);

			Document document = null;  
			Element rootResponse = null;

			try {
				document = builder.build(reader);
				rootResponse = document.getRootElement();

				if (rootResponse != null) {
					@SuppressWarnings("unchecked")
					List<Element> elementNodes = rootResponse.getChildren();

					String spaces = "&nbsp;&nbsp;&nbsp;";

					if (elementNodes != null) {
						try {
							Iterator<Element> iterator2 = elementNodes.iterator();

							if (iterator2 != null)
								while(iterator2.hasNext()) {
									Element oneLevelDeep1 = (Element) iterator2.next();

									response.append("<br>" + spaces + "<b class='statusTitle'>" + oneLevelDeep1.getName() + " : </b>");

									@SuppressWarnings("unchecked")
									List<Element> twoLevelsDeep1 = oneLevelDeep1.getChildren();

									Iterator<Element> iterator3 = twoLevelsDeep1.iterator();
									if (iterator3 != null)
										while(iterator3.hasNext()) {
											Element oneLevelDeep2 = (Element) iterator3.next();

											response.append("<br>" + spaces + spaces + "<b class='statusTitle'>" + oneLevelDeep2.getName() + " : </b>");
											response.append("<i class='statusLabel'>" + oneLevelDeep2.getText() + "</i>");

											@SuppressWarnings("unchecked")
											List<Element> twoLevelsDeep2 = oneLevelDeep2.getChildren();

											Iterator<Element> iterator4 = twoLevelsDeep2.iterator();
											if (iterator4 != null)
												while(iterator4.hasNext()) {
													Element oneLevelDeep3 = (Element) iterator4.next();

													response.append("<br>" + spaces + spaces + spaces + "<b class='statusTitle'>" + oneLevelDeep3.getName() + " : </b>");
													response.append("<i class='statusLabel'>" + oneLevelDeep3.getText() + "</i>");
												} //  while(iterator4.hasNext()) {

										} //  while(iterator3.hasNext()) {

								} //  while(iterator2.hasNext()) {
						} catch (Exception exception) {
							log.info("\n" + ORIGINE + " : Exception : " + exception.getMessage());    
						} finally {
						}
					} // if (elementNodes != null) {

				} // if (rootResponse != null) {

			} catch (JDOMException jdomException) {
				log.info("\n" + ORIGINE + " : JDOMException : " + jdomException.getMessage());    
			} catch (IOException ioException) {
				log.info("\n" + ORIGINE + " : IOException : " + ioException.getMessage());    
			} catch (Exception exception) {
				log.info("\n" + ORIGINE + " : Exception : " + exception.getMessage());    
			}

		} // if (! _responseToTransform.equalsIgnoreCase("")) {
		// --------------------------------------------------------------		

		return response.toString();

	} // public static String setHTMLResponse(String _responseToTransform) {

	/** Getters **/

	/**
	 * getter on sensors
	 *  
	 * @param _serverUrl (String) 	: server URL
	 * 
	 * @return Map<String, SensorBean> : map for describing sensors parameters
	 */
	public static Map<String, SensorBean> getSensors(String _serverUrl) {
		
		if (sensors != null) {
		} else {
			setSensorsDescriptions(_serverUrl);
		}
			
		return sensors;
		
	} //public static Map<String, SensorBean> getSensors(String _serverUrl) {
	
/*	*//**
	 * 
	 * @param _response (org.jdom.Document) : XML document
	 * 
	 * @return TaskingResponseBean
	 * 
	 * @throws Exception 
	 *//* 
	public static TaskingResponseBean parseStatusReportHeader (org.jdom.Document _response)
	throws Exception {
		
		String METHOD = "parseStatusReportHeader";
		
		TaskingResponseBean response = new TaskingResponseBean();

		// parsing de l'element <FeasibilityResponseType>
		// on utilise XPath pour atteindre certains elements : procedure, task
        // sample response
		// <sps:StatusReport>
		// ...
		//    <sps:task>http://www.deimos-space.com/sps/2010-08-26T05:48:47.661Z</sps:task>
		//    <sps:estimatedToC>2010-08-26T05:53:48Z</sps:estimatedToC>
		//    <sps:procedure>urn:ogc:object:feature:Sensor:deimos-space:HMA:OPT-2</sps:procedure>
		//    <sps:requestStatus>Accepted</sps:requestStatus>
		//    <sps:updateTime>2010-08-26T05:48:48.389Z</sps:updateTime>
		// </sps:StatusReport>

		
		Element rootResponse = _response.getRootElement();
		log.debug("\n" + ORIGINE + " : " + METHOD + " : rootResponse : " + rootResponse.toString());

		// select title
		String filter = "//sps:title";
		XPath xPath = XPath.newInstance(filter);
		Element titleNode = (Element) xPath.selectSingleNode(rootResponse);
		if (titleNode != null) {
			log.debug("\n" + ORIGINE + " : " + METHOD + " : titleNode : " + titleNode.getTextTrim());

			// add title to returned bean
			response.getStatusReport().setTitle(titleNode.getTextTrim());
		} // if (titleNode != null) {

		// select abstract
		filter = "//sps:abstract";
		xPath = XPath.newInstance(filter);
		Element abstractNode = (Element) xPath.selectSingleNode(rootResponse);
		if (abstractNode != null) {
			log.debug("\n" + ORIGINE + " : " + METHOD + " : abstractNode : " + abstractNode.getTextTrim());

			// add abstract (infos) to returned bean
//			response.setInfos(abstractNode.getTextTrim());
		} // if (abstractNode != null) {

		// select sensor identifier
		filter = "//sps:procedure";
		xPath = XPath.newInstance(filter);
		Element sensorIdentifierNode = (Element) xPath.selectSingleNode(rootResponse);
		if (sensorIdentifierNode != null) {
			log.debug("\n" + ORIGINE + " : " + METHOD + " : sensorIdentifierNode : " + sensorIdentifierNode.getTextTrim());

			// add sensor identifier to returned bean
			response.getStatusReport().setSensorId(sensorIdentifierNode.getTextTrim());
		} // if (sensorIdentifierNode != null) {

		// select task identifier
		filter = "//sps:task";
		xPath = XPath.newInstance(filter);
		Element taskIdentifierNode = (Element) xPath.selectSingleNode(rootResponse);
		if (taskIdentifierNode != null) {
			log.debug("\n" + ORIGINE + " : " + METHOD + " : taskIdentifierNode : " + taskIdentifierNode.getTextTrim());

			// add task identifier to returned bean
			response.getStatusReport().setTask(taskIdentifierNode.getTextTrim());
		} // if (taskIdentifierNode != null) {

		// select update Time
		filter = "//sps:updateTime";
		xPath = XPath.newInstance(filter);
		Element updateTimeNode = (Element) xPath.selectSingleNode(rootResponse);
		if (updateTimeNode != null) {
			log.debug("\n" + ORIGINE + " : " + METHOD + " : updateTimeNode : " + updateTimeNode.getTextTrim());

			// add update Time to returned bean
			response.getStatusReport().setUpdateTime(updateTimeNode.getTextTrim());
		} // if (updateTimeNode != null) {

		// select status Code
		filter = "//sps:requestStatus";
		xPath = XPath.newInstance(filter);
		Element statusCodeNode = (Element) xPath.selectSingleNode(rootResponse);
		if (statusCodeNode != null) {
			log.debug("\n" + ORIGINE + " : " + METHOD + " : requestStatus : " + statusCodeNode.getTextTrim());

			// add status Code to returned bean
			response.getStatusReport().setStatusCode(statusCodeNode.getTextTrim());
		} // if (statusCodeNode != null) {

		// select estimated ToC
		filter = "//sps:estimatedToC";
		xPath = XPath.newInstance(filter);
		Element estimatedToCNode = (Element) xPath.selectSingleNode(rootResponse);
		if (estimatedToCNode != null) {
			log.debug("\n" + ORIGINE + " : " + METHOD + " : estimatedToC : " + estimatedToCNode.getTextTrim());

			// add estimated ToC to returned bean
			response.getStatusReport().setEstimatedToC(estimatedToCNode.getTextTrim());
		} // if (estimatedToCNode != null) {

		log.debug("\n" + ORIGINE + " : ----------------------------------------------------------------------");
		
		return response;

	} // public static TaskingResponseBean parseStatusReportHeader () {
*/
} // class
