package com.astrium.faceo.server.rpc.programming.sps2;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.astrium.faceo.client.bean.programming.sps2.SensorBean;
import com.astrium.faceo.client.common.ConfigConstant;
import com.astrium.faceo.client.common.programming.sps2.Sps2GeneralConstants;

/**
 * Server side UpdateTasksStatusService Servlet.
 * 
 * This servlet update taks in DataBase with the results of GetStatus operation.
 * 
 * @author ASTRIUM
 *
 */
public class UpdateTasksStatusService extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4917341718219525493L;

	/** Le logger de cette classe */
	private static Logger log = Logger.getLogger(UpdateTasksStatusService.class);

	String ORIGINE = "UpdateTasksStatusService";

	//---------------------------------------------------------------
	//	 Methode Init chargee au demarrage de la servlet
	//---------------------------------------------------------------
	/**
	 * @param _config (ServletConfig)
	 * 
	 * @throws ServletException
	 */
	public void init(ServletConfig _config) throws ServletException {
		
		UpdateTasksThread updateTasksThread;
		
		// initialisation : toujours passer l'objet config a la super classe
		super.init(_config);
		
		 log.info("\n" + ORIGINE + " : init " );    
	     String path =  _config.getServletContext().getRealPath("") 
	     + File.separatorChar + "www" + File.separatorChar + "com.astrium.faceo.HomePage";
		 int pos = path.lastIndexOf(Sps2GeneralConstants.TASK_STATUS_CONTROLLER);
		 String serverURL = path;
		 if (pos > 0) {
			 serverURL = path.substring(0, pos);
		 }
		
		 log.info("\n" + ORIGINE + " : setSensorsDescriptions " );    
		 Map<String, SensorBean> sensors = setSensorsDescriptions(serverURL);
		 
		 log.info("\n" + ORIGINE + " : UpdateTasksThread " );    
		 updateTasksThread = new UpdateTasksThread(ConfigConstant.SPS_FACEO_USER, serverURL, sensors);
		 updateTasksThread.start();
		
	} // public void init

	/**
	 * set the sensors informations
	 * 
	 * @param _serverURL (String)	: server url
	 */
	private Map<String, SensorBean> setSensorsDescriptions(String _serverURL) {

		Map<String, SensorBean> sensorsHashMap = new HashMap<String, SensorBean>();

		try {
			//			URL sensorsUrl = new URL(_serverURL + "/resources/sensors.xml");

			SAXBuilder saxBuilder = new SAXBuilder();

			Document document = saxBuilder.build(_serverURL + Sps2GeneralConstants.SENSORS_XML);
			log.info("\n" + ORIGINE + " : setSensorsDescriptions : _serverURL : " + _serverURL );    
			log.info("\n" + ORIGINE + " : setSensorsDescriptions : SENSORS_XML : " + Sps2GeneralConstants.SENSORS_XML );    

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

							sensorsHashMap.put(sensor.getUrn(), sensor);
						} // if (elementUrn != null) {
					} // for (Iterator<Element> iterator = childrens.iterator(); iterator.hasNext();) {
				} // if (childrens != null) {

			} // if (root != null) {

		} catch (Exception exception) {
			log.info("\n" + ORIGINE + " : setSensorsDescriptions : Exception : " + exception.getMessage());
		}

		return sensorsHashMap;

	} // private Map<String, SensorBean> setSensorsDescriptions(String _serverURL) {

	/**
	 *  traitement de la requete HTTP GET
	 *  
	 * @param _request (HttpServletRequest)		: the request
	 * @param _response (HttpServletResponse)	: the response
	 *  
	 *  @throws ServletException
	 * 	@throws IOException
	 */
	public void doGet (HttpServletRequest _request, HttpServletResponse _response)
	throws ServletException, IOException {

	}

} // class
