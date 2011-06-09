package com.astrium.faceo.client.ui.configLoaders;

import java.util.Map;
import java.util.Map.Entry;

import com.astrium.faceo.client.bean.programming.sps2.SensorBean;
import com.astrium.faceo.client.common.programming.sps2.Sps2GeneralConstants;
import com.astrium.faceo.client.ui.HomePage;
import com.astrium.faceo.client.ui.programming.sps2.SensorsUrnsResources;
import com.astrium.faceo.client.ui.programming.sps2.view.SpsSensorSettingsPanel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.Text;
import com.google.gwt.xml.client.XMLParser;

/**
 * <B>SITE FACEO</B> <BR>
 * 
 * <P>
 * ConfLoaderSpsSensorsSettingsPanels class manages the 'FACEO' GUI
 * </P>
 * </P>
 * 
 * @author ASTRIUM
 * @version 1.0, le 26/05/2010
 */
public class ConfLoaderSpsSensorsSettingsPanels {

	/**
	 * Cr&eacute;ation de l'instance au niveau de la variable.
	 */
	private static final ConfLoaderSpsSensorsSettingsPanels instance = 
		new ConfLoaderSpsSensorsSettingsPanels();

	/**
	 * La présence d'un constructeur priv&eacute; supprime
	 * le constructeur public par d&eacute;faut.
	 */
	private ConfLoaderSpsSensorsSettingsPanels() {
	}

	/**
	 * configuration de l'IHM de d&eacute;finition des param&egrave;tres
	 * de l'op&eacute;ration 'GetFeasibiity' pour chacun des capteurs &agrave; impl&eacute;menter
	 * 
	 */
	public void setSpsSensors() {

		//------------------------ SPS 2.0 Sensors Settings Panels --------------------------
		if (HomePage.getHomePageBean().getConfigBean().getSps2Panel()) {
			// get sensors urls from xml file
			String sensorsUrns = SensorsUrnsResources.INSTANCE.sensorsUrl().getText();
			getSensorsUrns(sensorsUrns);
		} // if (homePageBean.getConfigBean().getSps2Panel()) {

	} // public void setSpsSensors() {


	/**
	 * configuration de l'IHM de d&eacute;finition des param&egrave;tres
	 * de l'op&eacute;ration 'GetFeasibiity' pour chacun des capteurs &agrave; impl&eacute;menter
	 * 
	 * @param _spsSensorSettingsPanels (Map<String, SpsSensorSettingsPanel>) : panels for 'SpsSensorSettingsPanel'
	 */
	public void getSpsSensorSettingsPanels(Map<String, SpsSensorSettingsPanel> _spsSensorSettingsPanels) {

		//------------------------ SPS 2.0 Sensors Settings Panels --------------------------
		if (HomePage.getHomePageBean().getConfigBean().getSps2Panel()) {
			for(Entry<String, SensorBean> sensors : Sps2GeneralConstants.getSensors().entrySet()) {
				SensorBean sensor = sensors.getValue();

				if (sensor != null) {
					if (sensor.getActive()) {
						_spsSensorSettingsPanels.put(sensor.getId(), 
								new SpsSensorSettingsPanel(sensor));
					} // if (sensor.getActive()) {
				} // if (sensor != null) {
			} // for(Entry<String, SensorBean> sensors : Sps2GeneralConstants.getSensors().entrySet()) {
		} // if (homePageBean.getConfigBean().getSps2Panel()) {

	} // public void getSpsSensorSettingsPanels(Map<String, SpsSensorSettingsPanel> _spsSensorSettingsPanels) {

	/**
	 * configuration of each sensor to implement
	 * 
	 * @param _sensorsRadios (Map<String, RadioButton >) : return a radio button for each sensor in a Map
	 */
	public void getSpsSensorsRadiosButtons(Map<String, RadioButton > _sensorsRadios) {

		//------------------------ SPS 2.0 Sensors Settings Panels --------------------------
		if (HomePage.getHomePageBean().getConfigBean().getSps2Panel()) {
			// get sensors urls from xml file
			if (Sps2GeneralConstants.getSensors().isEmpty()) {
				String sensorsUrns = SensorsUrnsResources.INSTANCE.sensorsUrl().getText();
				getSensorsUrns(sensorsUrns);
			} // if

			int rang = 0;
			for(Entry<String, SensorBean> sensors : Sps2GeneralConstants.getSensors().entrySet()) {
				// one sensor
				SensorBean sensor = sensors.getValue();

				if (sensor != null) {
					if (sensor.getActive()) {
						RadioButton radio1 = new RadioButton (Sps2GeneralConstants.SENSOR_RADIO_BUTTON, sensor.getId());
						radio1.getElement().setId(sensor.getRadioButtonId());
						if (rang == 0) {
							radio1.setValue(true);
						} else {
							radio1.setValue(false);
						}
						_sensorsRadios.put(sensor.getId(), radio1);
					} //  if (sensor.getActive()) {
				} // if (sensor != null) {

				rang++;
			} // for(Entry<String, SensorBean> sensors : Sps2GeneralConstants.getSensors().entrySet()) {
		} // if (homePageBean.getConfigBean().getSps2Panel()) {

	} // public void getSpsSensorsRadiosButtons(Map<String, RadioButton > _sensorsRadios) {

	/**
	 * get the sensor Id corresponding to a radio button identifier
	 * 
	 * @param _radioButtonId (String) : the radio button identifier
	 * 
	 * @return String : the sensor identifier
	 */
	public String getRadioButtonSensorId(String _radioButtonId) {

		String sensorId = "";

		//------------------------ SPS 2.0 Sensors Settings Panels --------------------------
		if (HomePage.getHomePageBean().getConfigBean().getSps2Panel()) {
			for(Entry<String, SensorBean> sensors : Sps2GeneralConstants.getSensors().entrySet()) {
				SensorBean sensor = sensors.getValue();

				if (sensor != null) {
					if (sensor.getActive()) {
						if (sensor.getRadioButtonId().equalsIgnoreCase(_radioButtonId)) {
							sensorId = sensor.getId();
						} // if (sensor.getRadioButtonId().equalsIgnoreCase(_radioButtonId)) {
					} //  if (sensor.getActive()) {
				} // if (sensor1 != null) {
			} //for(Entry<String, SensorBean> sensors : Sps2GeneralConstants.getSensors().entrySet()) {
		} // if (homePageBean.getConfigBean().getSps2Panel()) {

		return sensorId;

	} // public void getRadioButtonSensorId(String _radioButtonId) {

	/**
	 * Dans ce cas pr&eacute;sent, le mot-cl&eacute; synchronized n'est pas utile.
	 * L'unique instanciation du singleton se fait avant
	 * l'appel de la m&eacute;thode getInstance(). Donc aucun risque d'acc&egrave;s concurrents.
	 * Retourne l'instance du singleton.
	 * 
	 * @return ConfLoaderMissionRequestPanels
	 */
	public final static ConfLoaderSpsSensorsSettingsPanels getInstance() {
		return instance;
	}

	/**
	 * return the SOAP endPoints for all the sensors
	 * 
	 * @param _sensorsUrns (String)	: content of xml file describing the endPoints
	 */
	private void getSensorsUrns(String _sensorsUrns) {

		Sps2GeneralConstants.getSensors().clear();

		com.google.gwt.xml.client.Document document1 = XMLParser.parse(_sensorsUrns);

		NodeList nodes = document1.getElementsByTagName("sensor");
		if (nodes != null) {
			if (nodes.getLength() > 0) {
				for (int i=0; i < nodes.getLength(); i++) {
					String id = ((com.google.gwt.xml.client.Element) nodes.item(i)).getAttribute("id");
					String activeStr = ((com.google.gwt.xml.client.Element) nodes.item(i)).getAttribute("active");
					boolean active = false;
					if (activeStr.equalsIgnoreCase("true")) {
						active = true;
					} // if
					String synch = ((com.google.gwt.xml.client.Element) nodes.item(i)).getAttribute("synchronous");
					boolean synchronous = false;
					if (synch.equalsIgnoreCase("true")) {
						synchronous = true;
					} // if
					String type = ((com.google.gwt.xml.client.Element) nodes.item(i)).getAttribute("type");
					String describeFile = ((com.google.gwt.xml.client.Element) nodes.item(i)).getAttribute("describeFile");

					com.google.gwt.xml.client.Document document2 = 
						XMLParser.parse(((com.google.gwt.xml.client.Element) nodes.item(i)).toString());

					NodeList childNodes1 = document2.getElementsByTagName("urn");
					if (childNodes1 != null) {
						if (childNodes1.getLength() == 1) {
							SensorBean sensor = new SensorBean();
							sensor.setId(id);
							sensor.setActive(active);
							sensor.setSynchronous(synchronous);
							sensor.setType(type);
							sensor.setDescribeFile(describeFile);

							sensor.setPanelId(Sps2GeneralConstants.SENSOR_SETTINGS_PANEL + id);
							sensor.setRadioButtonId(Sps2GeneralConstants.SENSOR_RADIO_BUTTON_PREFIX_ID + id);

							Text bodyNode1 = (Text)childNodes1.item(0).getFirstChild();

							if (bodyNode1 != null) {
								String urn = bodyNode1.getData();
								sensor.setUrn(urn);
								
								NodeList childNodes2 = document2.getElementsByTagName("endpoint");
								if (childNodes2 != null) {
									if (childNodes2.getLength() == 1) {
										Text bodyNode2 = (Text)childNodes2.item(0).getFirstChild();

										if (bodyNode2 != null) {
											String endPoint = bodyNode2.getData();
											sensor.setEndPoint(endPoint);
										} // if (bodyNode2 != null) {
									} // if (childNodes2.getLength() == 1) {
								} // if (childNodes2 != null) {
								
								NodeList childNodes3 = document2.getElementsByTagName("getCapabilitiesUrl");
								if (childNodes3 != null) {
									if (childNodes3.getLength() == 1) {
										Text bodyNode3 = (Text)childNodes3.item(0).getFirstChild();
//										String parameters = ((com.google.gwt.xml.client.Element) childNodes3.item(0)).getAttribute("parameters");

										if (bodyNode3 != null) {
											String getCapabilitiesUrl = bodyNode3.getData();
											sensor.setGetCapabilitiesUrl(getCapabilitiesUrl);
										} // if (bodyNode3 != null) {
									} // if (childNodes3.getLength() == 1) {
								} // if (childNodes3 != null) {

								Sps2GeneralConstants.getSensors().put(sensor.getId(), sensor);
							} // if (bodyNode1 != null) {
						} // if (childNodes1.getLength() == 1) {
					} // if (childNodes1 != null) {
				} // for (int i=0; i < nodes.getLength(); i++) {
			} // if (nodes.getLength() > 0) {
		} // if (nodes != null) {

	} // private void Map<String, SensorBean> getSensorsUrns(String _sensorsUrns) {

} // class
