package com.astrium.faceo.client.ui.programming.sps2;

import com.astrium.faceo.client.bean.programming.sps2.SensorBean;
import com.astrium.faceo.client.bean.programming.sps2.request.TaskingParametersBean;
import com.astrium.faceo.client.common.programming.sps2.Sps2GeneralConstants;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.Text;
import com.google.gwt.xml.client.XMLParser;

/**
 * <B>SITE FACEO</B> <BR>
 * 
 * <P>
 * La classe SpsSetTaskingParameters d&eacute; the tasking parameters
 * that are available on IHM for one sensor
 * </P>
 * </P>
 * 
 * @author GR
 * @version 1.0, le 28/05/2010
 */
public class SpsSetTaskingParameters {

	/**
	 * constructor
	 * 
	 */
	public SpsSetTaskingParameters() {

	}

	/**
	 * return the parameters configuration
	 * 
	 * @param _sensor (SensorBean) : sensor informations
	 * 
	 * @return TaskingParametersBean : tasking parameters configuration
	 */
	public static TaskingParametersBean getTaskingParameters(SensorBean _sensor) {

		/** sensor SAR1 id */
		String SENSOR_SAR1_ID = "SAR1";

		/** sensor OPT1 id */
		String SENSOR_OPT1_ID = "OPT1";

		/** sensor SAR2 id */
		String SENSOR_SAR2_ID = "SAR2";

		/** sensor OPT2 id */
		String SENSOR_OPT2_ID = "OPT2";

		/** sensor SPOT5 id */
		String SENSOR_SPOT5_ID = "SPOT";

		TaskingParametersBean taskingParameters = new TaskingParametersBean();

		// Using a TextResource XML file to describe Tasking parameters 
		// for IHM
		String describeTaskingParameters = "";
		com.google.gwt.xml.client.Document document1 = null;

		taskingParameters.setSensor(_sensor);

		// get tasking parameters configuration from xml files
		if (_sensor.getPanelId().equalsIgnoreCase((Sps2GeneralConstants.SENSOR_SETTINGS_PANEL + SENSOR_SAR1_ID))) {
			describeTaskingParameters = DescribeTaskingResources.INSTANCE.parametersSAR1().getText();
		} else if (_sensor.getPanelId().equalsIgnoreCase((Sps2GeneralConstants.SENSOR_SETTINGS_PANEL + SENSOR_OPT1_ID))) {
			describeTaskingParameters = DescribeTaskingResources.INSTANCE.parametersOPT1().getText();
		} else if (_sensor.getPanelId().equalsIgnoreCase((Sps2GeneralConstants.SENSOR_SETTINGS_PANEL + SENSOR_SAR2_ID))) {
			describeTaskingParameters = DescribeTaskingResources.INSTANCE.parametersSAR2().getText();
		} else if (_sensor.getPanelId().equalsIgnoreCase((Sps2GeneralConstants.SENSOR_SETTINGS_PANEL + SENSOR_OPT2_ID))) {
			describeTaskingParameters = DescribeTaskingResources.INSTANCE.parametersOPT2().getText();
		} else if (_sensor.getPanelId().equalsIgnoreCase((Sps2GeneralConstants.SENSOR_SETTINGS_PANEL + SENSOR_SPOT5_ID))) {
			describeTaskingParameters = DescribeTaskingResources.INSTANCE.parametersSPOT5().getText();
		} // if (_sensor.getPanelId().equalsIgnoreCase

		taskingParameters.getSensor().setType(_sensor.getType());

		document1 = XMLParser.parse(describeTaskingParameters);

		NodeList nodes = document1.getElementsByTagName("field");
		if (nodes != null) {
			for (int i=0; i < nodes.getLength(); i++) {
				String name = ((com.google.gwt.xml.client.Element) nodes.item(i)).getAttribute("name");

				// --------------------- Quality Of Service parameters ---------------------
				if (name.equalsIgnoreCase("QualityOfService")) {
					taskingParameters.setQualityOfService(true);

					com.google.gwt.xml.client.Document document4 = 
						XMLParser.parse(((com.google.gwt.xml.client.Element) nodes.item(i)).toString());

					NodeList childNodes = document4.getElementsByTagName("field");
					if (childNodes != null) {
						if (childNodes.getLength() > 0) {
							for (int j=0; j < childNodes.getLength(); j++) {
								String name2 = ((com.google.gwt.xml.client.Element) childNodes.item(j)).getAttribute("name");

								// Priority Level
								if (name2.equalsIgnoreCase("PriorityLevel")) {
									taskingParameters.setPriorityLevel(true);

									com.google.gwt.xml.client.Document document3 = 
										XMLParser.parse(((com.google.gwt.xml.client.Element) childNodes.item(j)).toString());

									// Get values list : enumeration or several values
									// samples :
									// <swe:AllowedTokens>
									//    <swe:enumeration>STANDARD HIGH</swe:enumeration>
									// </swe:AllowedTokens>
									//      or
									// <swe:AllowedTokens>
									//    <swe:value>STANDARD</swe:value>
									// 	  <swe:value>HIGH</swe:value>
									// </swe:AllowedTokens>
									taskingParameters.setPriorityLevelValues(getAllowedValues(document3));

									// Default value
									NodeList childNodes3 = document3.getElementsByTagName("value");
									if (childNodes3 != null) {
										if (childNodes3.getLength() > 0) {
											for (int k=0; k < childNodes3.getLength(); k++) {
												Text bodyNode = (Text)childNodes3.item(k).getFirstChild();

												taskingParameters.setPriorityLevelDefaultValue(bodyNode.getData());
											} // for (int k=0; k < childNodes3.getLength(); k++) {
										} // if (childNodes3.getLength() > 0) {
									} // if (childNodes3 != null) {
								} // if (name2.equalsIgnoreCase("Priority")) {

							} // for (int j=0; j < childNodes.getLength(); j++) {
						} // if (childNodes.getLength() > 0) {
					} // if (childNodes != null) { 

				} // if (name.equalsIgnoreCase("QualityOfService")) {

				// --------------------- Validation parameters ---------------------

				// --------------------- Validation parameters ---------------------
				if (name.equalsIgnoreCase("ValidationParameters")) {
					if (_sensor.getType().equalsIgnoreCase(Sps2GeneralConstants.SENSOR_RADAR)) {
						taskingParameters.setValidationParametersSAR(true);

						com.google.gwt.xml.client.Document document2 = 
							XMLParser.parse(((com.google.gwt.xml.client.Element) nodes.item(i)).toString());

						NodeList childNodes = document2.getElementsByTagName("field");
						if (childNodes != null) {
							if (childNodes.getLength() > 0) {
								for (int j=0; j < childNodes.getLength(); j++) {
									String name2 = ((com.google.gwt.xml.client.Element) childNodes.item(j)).getAttribute("name");

									// Max Cloud Coverage
									if (name2.equalsIgnoreCase("MaxNoiseLevel")) {
										taskingParameters.setMaxNoiseLevel(true);

										com.google.gwt.xml.client.Document document3 = 
											XMLParser.parse(((com.google.gwt.xml.client.Element) childNodes.item(j)).toString());

										NodeList childNodes3 = document3.getElementsByTagName("value");
										if (childNodes3 != null) {
											if (childNodes3.getLength() > 0) {
												for (int k=0; k < childNodes3.getLength(); k++) {
													Text bodyNode = (Text)childNodes3.item(k).getFirstChild();

													double value1 = Double.valueOf(bodyNode.getData());
													taskingParameters.setMaxNoiseLevelValue((int)(value1));
//													taskingParameters.setMaxNoiseLevelValue(Integer.valueOf(bodyNode.getData()));
												} // for (int k=0; k < childNodes3.getLength(); k++) {
											} // if (childNodes3.getLength() > 0) {
										} // if (childNodes3 != null) {
									} // if (name2.equalsIgnoreCase("MaxNoiseLevel")) {

									// Max Cloud Coverage
									if (name2.equalsIgnoreCase("MaxAmbiguityLevel")) {
										taskingParameters.setMaxAmbiguityLevel(true);

										com.google.gwt.xml.client.Document document3 = 
											XMLParser.parse(((com.google.gwt.xml.client.Element) childNodes.item(j)).toString());

										NodeList childNodes3 = document3.getElementsByTagName("value");
										if (childNodes3 != null) {
											if (childNodes3.getLength() > 0) {
												for (int k=0; k < childNodes3.getLength(); k++) {
													Text bodyNode = (Text)childNodes3.item(k).getFirstChild();

													double value1 = Double.valueOf(bodyNode.getData());
													taskingParameters.setMaxAmbiguityLevelValue((int)(value1));
//													taskingParameters.setMaxAmbiguityLevelValue(Integer.valueOf(bodyNode.getData()));
												} // for (int k=0; k < childNodes3.getLength(); k++) {
											} // if (childNodes3.getLength() > 0) {
										} // if (childNodes3 != null) {
									} // if (name2.equalsIgnoreCase("MaxAmbiguityLevel")) {

								} // for (int j=0; j < childNodes.getLength(); j++) {

							} // if (childNodes.getLength() > 0) {
						} // if (childNodes != null) {

					} // if (_sensor.getType().equalsIgnoreCase(Sps2GeneralConstants.SENSOR_RADAR)) {
				} //if (name.equalsIgnoreCase("ValidationParametersSAR")) {

				// --------------------- Validation OPT parameters ---------------------
				if (name.equalsIgnoreCase("ValidationParameters")) {
					if (_sensor.getType().equalsIgnoreCase(Sps2GeneralConstants.SENSOR_OPTICAL)) {
						taskingParameters.setValidationParametersOPT(true);

						com.google.gwt.xml.client.Document document2 = 
							XMLParser.parse(((com.google.gwt.xml.client.Element) nodes.item(i)).toString());

						NodeList childNodes = document2.getElementsByTagName("field");
						if (childNodes != null) {
							if (childNodes.getLength() > 0) {
								for (int j=0; j < childNodes.getLength(); j++) {
									String name2 = ((com.google.gwt.xml.client.Element) childNodes.item(j)).getAttribute("name");

									// Max Cloud Coverage
									if (name2.equalsIgnoreCase("MaxCloudCover")) {
										taskingParameters.setMaxCloudCoverage(true);

										com.google.gwt.xml.client.Document document3 = 
											XMLParser.parse(((com.google.gwt.xml.client.Element) childNodes.item(j)).toString());

										NodeList childNodes3 = document3.getElementsByTagName("value");
										if (childNodes3 != null) {
											if (childNodes3.getLength() > 0) {
												for (int k=0; k < childNodes3.getLength(); k++) {
													Text bodyNode = (Text)childNodes3.item(k).getFirstChild();

													double value1 = Double.valueOf(bodyNode.getData());
													taskingParameters.setMaxCloudCoverageValue((int)(value1));
//													taskingParameters.setMaxCloudCoverageValue(Integer.valueOf(bodyNode.getData()));
												} // for (int k=0; k < childNodes3.getLength(); k++) {
											} // if (childNodes3.getLength() > 0) {
										} // if (childNodes3 != null) {
									} // if (name2.equalsIgnoreCase("MaxCloudCover")) {

									// Max Cloud Coverage
									if (name2.equalsIgnoreCase("MaxSnowCover")) {
										taskingParameters.setMaxSnowCoverage(true);

										com.google.gwt.xml.client.Document document3 = 
											XMLParser.parse(((com.google.gwt.xml.client.Element) childNodes.item(j)).toString());

										NodeList childNodes3 = document3.getElementsByTagName("value");
										if (childNodes3 != null) {
											if (childNodes3.getLength() > 0) {
												for (int k=0; k < childNodes3.getLength(); k++) {
													Text bodyNode = (Text)childNodes3.item(k).getFirstChild();

													double value1 = Double.valueOf(bodyNode.getData());
													taskingParameters.setMaxSnowCoverageValue((int)(value1));
//													taskingParameters.setMaxSnowCoverageValue(Integer.valueOf(bodyNode.getData()));
												} // for (int k=0; k < childNodes3.getLength(); k++) {
											} // if (childNodes3.getLength() > 0) {
										} // if (childNodes3 != null) {
									} // if (name2.equalsIgnoreCase("MaxSnowCover")) {

									// HazeAccepted
									if (name2.equalsIgnoreCase("HazeAccepted")) {
										com.google.gwt.xml.client.Document document3 = 
											XMLParser.parse(((com.google.gwt.xml.client.Element) childNodes.item(j)).toString());

										NodeList childNodes3 = document3.getElementsByTagName("value");
										if (childNodes3 != null) {
											if (childNodes3.getLength() == 1) {
												for (int k=0; k < childNodes3.getLength(); k++) {
													Text bodyNode = (Text)childNodes3.item(k).getFirstChild();

													if (bodyNode.getData().equalsIgnoreCase("true")) {
														taskingParameters.setHazeAccepted(true);
													} // if
												} // for (int k=0; k < childNodes3.getLength(); k++) {
											} // if (childNodes3.getLength() == 1) {
										} // if (childNodes3 != null) {
									} // if (name2.equalsIgnoreCase("HazeAccepted")) {

									// SandWindAccepted
									if (name2.equalsIgnoreCase("SandWindAccepted")) {
										com.google.gwt.xml.client.Document document3 = 
											XMLParser.parse(((com.google.gwt.xml.client.Element) childNodes.item(j)).toString());

										NodeList childNodes3 = document3.getElementsByTagName("value");
										if (childNodes3 != null) {
											if (childNodes3.getLength() == 1) {
												for (int k=0; k < childNodes3.getLength(); k++) {
													Text bodyNode = (Text)childNodes3.item(k).getFirstChild();

													if (bodyNode.getData().equalsIgnoreCase("true")) {
														taskingParameters.setSandWindAccepted(true);
													} // if
												} // for (int k=0; k < childNodes3.getLength(); k++) {
											} // if (childNodes3.getLength() == 1) {
										} // if (childNodes3 != null) {
									} // if (name2.equalsIgnoreCase("SandWindAccepted")) {

								} // for (int j=0; j < childNodes.getLength(); j++) {

							} // if (childNodes.getLength() > 0) {
						} // if (childNodes != null) {

					} // if (_sensor.getType().equalsIgnoreCase(Sps2GeneralConstants.SENSOR_OPTICAL)) {
				} //if (name.equalsIgnoreCase("ValidationParametersOPT")) {

				// --------------------- Acquisition parameters ---------------------
				if (name.equalsIgnoreCase("AcquisitionType")) {
					taskingParameters.setAcquisitionType(true);

					com.google.gwt.xml.client.Document document4 = 
						XMLParser.parse(((com.google.gwt.xml.client.Element) nodes.item(i)).toString());

					// tag <field> or tag <item> for "MonoscopicAcquisition" or "StereoscopicAcquisition"
					NodeList childNodes01 = document4.getElementsByTagName("field");
					if (childNodes01 != null) {
						if (childNodes01.getLength() > 0) {
							for (int j=0; j < childNodes01.getLength(); j++) {
								String name2 = ((com.google.gwt.xml.client.Element) childNodes01.item(j)).getAttribute("name");

								// Monoscopic Acquisition
								if (name2.equalsIgnoreCase("MonoscopicAcquisition")) {
									taskingParameters.setAcquisitionMonoscopic(true);
								}

								// Stereoscopic Acquisition
								if (name2.equalsIgnoreCase("StereoscopicAcquisition")) {
									taskingParameters.setAcquisitionStereoscopic(true);
								}
							} // for (int j=0; j < childNodes01.getLength(); j++) {
						} // if (childNodes01.getLength() > 0) {
					} // if (childNodes01 != null) { 

					NodeList childNodes02 = document4.getElementsByTagName("item");
					if (childNodes02 != null) {
						if (childNodes02.getLength() > 0) {
							for (int j=0; j < childNodes02.getLength(); j++) {
								String name2 = ((com.google.gwt.xml.client.Element) childNodes02.item(j)).getAttribute("name");

								// Monoscopic Acquisition
								if (name2.equalsIgnoreCase("MonoscopicAcquisition")) {
									taskingParameters.setAcquisitionMonoscopic(true);
								}

								// Stereoscopic Acquisition
								if (name2.equalsIgnoreCase("StereoscopicAcquisition")) {
									taskingParameters.setAcquisitionStereoscopic(true);
								}
							} // for (int j=0; j < childNodes02.getLength(); j++) {
						} // if (childNodes02.getLength() > 0) {
					} // if (childNodes02 != null) { 

					if ( (taskingParameters.getAcquisitionMonoscopic()) || (taskingParameters.getAcquisitionStereoscopic()) ) {
						com.google.gwt.xml.client.Document document2 = 
							XMLParser.parse(((com.google.gwt.xml.client.Element) nodes.item(i)).toString());

						NodeList childNodes = document2.getElementsByTagName("field");
						if (childNodes != null) {
							if (childNodes.getLength() > 0) {
								for (int j=0; j < childNodes.getLength(); j++) {
									String name2 = ((com.google.gwt.xml.client.Element) childNodes.item(j)).getAttribute("name");

									// Coverage Type
									if (name2.equalsIgnoreCase("CoverageType")) {
										taskingParameters.setCoverageType(true);

										com.google.gwt.xml.client.Document document3 = 
											XMLParser.parse(((com.google.gwt.xml.client.Element) childNodes.item(j)).toString());

										// Get values list : enumeration or several values
										// samples :
										// <swe:AllowedTokens>
										//    <swe:enumeration>SINGLE_SWATH MONOPASS MULTIPASS</swe:enumeration>
										// </swe:AllowedTokens>
										//     or
										// <swe:AllowedTokens>
										//    <swe:value>SINGLE SWATH</swe:value>
										//    <swe:value>MONOPASS</swe:value>
										//    <swe:value>MULTIPASS</swe:value>
										taskingParameters.setCoverageTypeValues(getAllowedValues(document3));

										// dafault value
										NodeList childNodes3 = document3.getElementsByTagName("value");
										if (childNodes3 != null) {
											if (childNodes3.getLength() > 0) {
												for (int k=0; k < childNodes3.getLength(); k++) {
													Text bodyNode = (Text)childNodes3.item(k).getFirstChild();

													taskingParameters.setCoverageTypeDefaultValue(bodyNode.getData());
												} // for (int k=0; k < childNodes3.getLength(); k++) {
											} // if (childNodes3.getLength() > 0) {
										} // if (childNodes3 != null) {
									} // if (name2.equalsIgnoreCase("CoverageType")) {

								} // for (int j=0; j < childNodes.getLength(); j++) {

							} // if (childNodes.getLength() > 0) {
						} // if (childNodes != null) { 				
					} // if ( (taskingParameters.getAcquisitionMonoscopic()) || (taskingParameters.getAcquisitionStereoscopic()) ) {

				} // if (name.equalsIgnoreCase("AcquisitionType")) {

				if ( name.equalsIgnoreCase("AcquisitionParameters")) {
					// SAR sensor
					if (_sensor.getType().equalsIgnoreCase(Sps2GeneralConstants.SENSOR_RADAR)) {
						taskingParameters.setAcquisitionRadarType(true);
					}

					// OPT sensor
					if (_sensor.getType().equalsIgnoreCase(Sps2GeneralConstants.SENSOR_OPTICAL)) {
						taskingParameters.setAcquisitionOpticalType(true);
					} // if (_sensor.getType().equalsIgnoreCase(Sps2GeneralConstants.SENSOR_OPTICAL)) {

					com.google.gwt.xml.client.Document document2 = 
						XMLParser.parse(((com.google.gwt.xml.client.Element) nodes.item(i)).toString());

					NodeList childNodes = document2.getElementsByTagName("field");
					if (childNodes != null) {
						if (childNodes.getLength() > 0) {
							for (int j=0; j < childNodes.getLength(); j++) {
								String name2 = ((com.google.gwt.xml.client.Element) childNodes.item(j)).getAttribute("name");

								// Fusion Accepted (OPT and SAR)
								if (name2.equalsIgnoreCase("FusionAccepted")) {
									com.google.gwt.xml.client.Document document3 = 
										XMLParser.parse(((com.google.gwt.xml.client.Element) childNodes.item(j)).toString());

									NodeList childNodes3 = document3.getElementsByTagName("value");
									if (childNodes3 != null) {
										if (childNodes3.getLength() == 1) {
											for (int k=0; k < childNodes3.getLength(); k++) {
												Text bodyNode = (Text)childNodes3.item(k).getFirstChild();

												if (bodyNode.getData().equalsIgnoreCase("true")) {
													taskingParameters.setFusionAccepted(true);
												} // if
											} // for (int k=0; k < childNodes3.getLength(); k++) {
										} // if (childNodes3.getLength() == 1) {
									} // if (childNodes3 != null) {
								} // if (name2.equalsIgnoreCase("FusionAccepted")) {

								// Instrument Mode (OPT and SAR)
								if (name2.equalsIgnoreCase("InstrumentMode")) {
									taskingParameters.setInstrumentMode(true);

									com.google.gwt.xml.client.Document document3 = 
										XMLParser.parse(((com.google.gwt.xml.client.Element) childNodes.item(j)).toString());

									// Get values list : enumeration or several values
									// samples :
									// <swe:AllowedTokens>
									//    <swe:enumeration>PANCHROMATIC MULTISPECTRAL</swe:enumeration>
									// </swe:AllowedTokens>
									//     or
									// <swe:AllowedTokens>
									//    <swe:value>PANCHROMATIC</swe:value>
									//    <swe:value>MULTISPECTRAL</swe:value>
									taskingParameters.setInstrumentModeValues(getAllowedValues(document3));

									// default value
									NodeList childNodes3 = document3.getElementsByTagName("value");
									if (childNodes3 != null) {
										if (childNodes3.getLength() > 0) {
											for (int k=0; k < childNodes3.getLength(); k++) {
												Text bodyNode = (Text)childNodes3.item(k).getFirstChild();

												taskingParameters.setInstrumentModeDefaultValue(bodyNode.getData());
											} // for (int k=0; k < childNodes3.getLength(); k++) {
										} // if (childNodes3.getLength() > 0) {
									} // if (childNodes3 != null) {
								} // iif (name2.equalsIgnoreCase("InstrumentMode")) {

								// Ground Resolution (OPT and SAR)
								if (name2.equalsIgnoreCase("GroundResolution")) {
									taskingParameters.setGroundResolution(true);

									com.google.gwt.xml.client.Document document3 = 
										XMLParser.parse(((com.google.gwt.xml.client.Element) childNodes.item(j)).toString());

									NodeList childNodes3 = document3.getElementsByTagName("value");
									if (childNodes3 != null) {
										if (childNodes3.getLength() > 0) {
											for (int k=0; k < childNodes3.getLength(); k++) {
												Text bodyNode = (Text)childNodes3.item(k).getFirstChild();

												String[] values = bodyNode.getData().split(" ");

												taskingParameters.setGroundResolutionMin(Double.valueOf(values[0].replace("+", "")));
												taskingParameters.setGroundResolutionMax(Double.valueOf(values[1].replace("+", "")));
											} // for (int k=0; k < childNodes3.getLength(); k++) {
										} // if (childNodes3.getLength() > 0) {
									} // if (childNodes3 != null) {
								} // if (name2.equalsIgnoreCase("GroundResolution")) {

								// Polarization Mode (SAR only)
								if (name2.equalsIgnoreCase("PolarizationMode")) {
									taskingParameters.setPolarizationMode(true);

									com.google.gwt.xml.client.Document document3 = 
										XMLParser.parse(((com.google.gwt.xml.client.Element) childNodes.item(j)).toString());

									// Get values list : enumeration or several values
									// samples :
									// <swe:AllowedTokens>
									//    <swe:enumeration>... ...</swe:enumeration>
									// </swe:AllowedTokens>
									//     or
									// <swe:AllowedTokens>
									//    <swe:value>...</swe:value>
									//    <swe:value>...</swe:value>
									taskingParameters.setPolarizationModeValues(getAllowedValues(document3));

									// default value
									NodeList childNodes3 = document3.getElementsByTagName("value");
									if (childNodes3 != null) {
										if (childNodes3.getLength() > 0) {
											for (int k=0; k < childNodes3.getLength(); k++) {
												Text bodyNode = (Text)childNodes3.item(k).getFirstChild();

												taskingParameters.setPolarizationModeDefaultValue(bodyNode.getData());
											} // for (int k=0; k < childNodes3.getLength(); k++) {
										} // if (childNodes3.getLength() > 0) {
									} // if (childNodes3 != null) {
								} // if (name2("PolarizationMode")) {

								// Min Luminosity (SAR only)
								if (name2.equalsIgnoreCase("MinLuminosity")) {
									taskingParameters.setMinLuminosity(true);

									com.google.gwt.xml.client.Document document3 = 
										XMLParser.parse(((com.google.gwt.xml.client.Element) childNodes.item(j)).toString());

									NodeList childNodes3 = document3.getElementsByTagName("value");
									if (childNodes3 != null) {
										if (childNodes3.getLength() > 0) {
											for (int k=0; k < childNodes3.getLength(); k++) {
												Text bodyNode = (Text)childNodes3.item(k).getFirstChild();

												double value1 = Double.valueOf(bodyNode.getData());
												taskingParameters.setMinLuminosityValue((int)(value1));
//												taskingParameters.setMinLuminosityValue(Integer.valueOf(bodyNode.getData()));
											} // for (int k=0; k < childNodes3.getLength(); k++) {
										} // if (childNodes3.getLength() > 0) {
									} // if (childNodes3 != null) {
								} // if (name2.equalsIgnoreCase("MinLuminosity")) {

							} // for (int j=0; j < childNodes.getLength(); j++) {

						} // if (childNodes.getLength() > 0) {
					} // if (childNodes != null) {
				} // if (name.equalsIgnoreCase("AcquisitionParameters")) {

				// --------------------- Acquisition Incidence Angle ---------------------

				// Elevation Incidence Angle parameter
				if (name.equalsIgnoreCase("ElevationAngle")) {
					taskingParameters.setElevation(true);

					com.google.gwt.xml.client.Document document2 = 
						XMLParser.parse(((com.google.gwt.xml.client.Element) nodes.item(i)).toString());

					NodeList childNodes = document2.getElementsByTagName("value");
					if (childNodes != null) {
						for (int j=0; j < childNodes.getLength(); j++) {
							//				    Text bodyNode = (Text)document2.getElementsByTagName("value").item(j).getFirstChild();
							Text bodyNode = (Text)childNodes.item(j).getFirstChild();

							String[] values = bodyNode.getData().split(" ");

							double value1 = Double.valueOf(values[0].replace("+", ""));
							taskingParameters.setElevationIncidenceMin((int)(value1));
//							taskingParameters.setElevationIncidenceMin(Integer.valueOf(values[0].replace("+", "")));
							
							double value2 = Double.valueOf(values[1].replace("+", ""));
							taskingParameters.setElevationIncidenceMax((int)(value2));
//							taskingParameters.setElevationIncidenceMax(Integer.valueOf(values[1].replace("+", "")));
						} // for (int j=0; j < childNodes.getLength(); j++) {						
					} // if (childNodes != null) {
				} // if (name.equalsIgnoreCase("ElevationAngle")) {

				// Azimuth Incidence Angle parameter
				if (name.equalsIgnoreCase("AzimuthAngle")) {
					taskingParameters.setAzimuth(true);

					com.google.gwt.xml.client.Document document2 = 
						XMLParser.parse(((com.google.gwt.xml.client.Element) nodes.item(i)).toString());

					NodeList childNodes = document2.getElementsByTagName("value");
					if (childNodes != null) {
						for (int j=0; j < childNodes.getLength(); j++) {
							Text bodyNode = (Text)childNodes.item(j).getFirstChild();

							String[] values = bodyNode.getData().split(" ");

							double value1 = Double.valueOf(values[0].replace("+", ""));
							taskingParameters.setAzimuthIncidenceMin((int)(value1));
//							taskingParameters.setAzimuthIncidenceMin(Integer.valueOf(values[0].replace("+", "")));
							
							double value2 = Double.valueOf(values[1].replace("+", ""));
							taskingParameters.setAzimuthIncidenceMax((int)(value2));
//							taskingParameters.setAzimuthIncidenceMax(Integer.valueOf(values[1].replace("+", "")));
						} // for (int j=0; j < childNodes.getLength(); j++) {
					} // if (childNodes != null) {
				} // if (name.equalsIgnoreCase("AzimuthAngle")) {

				// Along Track Angle parameter
				if (name.equalsIgnoreCase("AlongTrack")) {
					taskingParameters.setAlongTrack(true);

					com.google.gwt.xml.client.Document document2 = 
						XMLParser.parse(((com.google.gwt.xml.client.Element) nodes.item(i)).toString());

					NodeList childNodes = document2.getElementsByTagName("value");
					if (childNodes != null) {
						for (int j=0; j < childNodes.getLength(); j++) {
							Text bodyNode = (Text)childNodes.item(j).getFirstChild();

							String[] values = bodyNode.getData().split(" ");

							double value1 = Double.valueOf(values[0].replace("+", ""));
							taskingParameters.setAlongTrackMin((int)(value1));
//							taskingParameters.setAlongTrackMin(Integer.valueOf(values[0].replace("+", "")));
							
							double value2 = Double.valueOf(values[1].replace("+", ""));
							taskingParameters.setAlongTrackMax((int)(value2));
//							taskingParameters.setAlongTrackMax(Integer.valueOf(values[1].replace("+", "")));
						} // for (int j=0; j < childNodes.getLength(); j++) {
					} // if (childNodes != null) {
				} // if (name.equalsIgnoreCase("AlongTrack")) {

				// Across Track Angle parameter
				if (name.equalsIgnoreCase("AcrossTrack")) {
					taskingParameters.setAcrossTrack(true);

					com.google.gwt.xml.client.Document document2 = 
						XMLParser.parse(((com.google.gwt.xml.client.Element) nodes.item(i)).toString());

					NodeList childNodes = document2.getElementsByTagName("value");
					if (childNodes != null) {
						for (int j=0; j < childNodes.getLength(); j++) {
							Text bodyNode = (Text)childNodes.item(j).getFirstChild();

							String[] values = bodyNode.getData().split(" ");

							double value1 = Double.valueOf(values[0].replace("+", ""));
							taskingParameters.setAcrossTrackMin((int)(value1));
//							taskingParameters.setAcrossTrackMin(Integer.valueOf(values[0].replace("+", "")));
							
							double value2 = Double.valueOf(values[1].replace("+", ""));
							taskingParameters.setAcrossTrackMax((int)(value2));
//							taskingParameters.setAcrossTrackMax(Integer.valueOf(values[1].replace("+", "")));
						} // for (int j=0; j < childNodes.getLength(); j++) {
					} // if (childNodes != null) {
				} // if (name.equalsIgnoreCase("AcrossTrack")) {

			} // for (int i=0; j < nodes.getLength(); j++) {			
		} // if (nodes != null) {

		return taskingParameters;

	} // public static TaskingParametersBean getTaskingParameters(String _panelId) {

	private static String[] getAllowedValues(com.google.gwt.xml.client.Document _document) {

		// Get values list : enumeration or several values
		// samples :
		// <swe:AllowedTokens>
		//    <swe:enumeration>STANDARD HIGH</swe:enumeration>
		// </swe:AllowedTokens>
		//      or
		// <swe:AllowedTokens>
		//    <swe:value>STANDARD</swe:value>
		// 	  <swe:value>HIGH</swe:value>
		// </swe:AllowedTokens>

		String empty = "";
		String[] values = empty.split(" ");

		NodeList childNodes1 = _document.getElementsByTagName("AllowedTokens");
		if ( (childNodes1 != null)  && (childNodes1.getLength() > 0) ) {
			com.google.gwt.xml.client.Document document1 = 
				XMLParser.parse(((com.google.gwt.xml.client.Element) childNodes1.item(0)).toString());

			NodeList childNodes2 = document1.getElementsByTagName("enumeration");
			boolean valuesInit = false;
			for (int k=0; k < childNodes2.getLength(); k++) {
				Text bodyNode = (Text)childNodes2.item(k).getFirstChild();

				values = bodyNode.getData().split(" ");
				valuesInit = true;
			} // for (int k=0; k < childNodes.getLength(); k++) {

			if (! valuesInit) {
				NodeList childValues = document1.getElementsByTagName("value");
				if (childValues != null) {
					if (childValues.getLength() > 0) {
						String list = "";
						for (int k=0; k < childValues.getLength(); k++) {
							Text bodyNode = (Text)childValues.item(k).getFirstChild();

							list += bodyNode.getData() + ",";
						} // for (int k=0; k < childValues.getLength(); k++) {
						values = list.split(",");
					} // if (childValues.getLength() > 0) {
				} // if (childValues != null) {
			} // if (! valuesInit) {
		} // if ( (childNodes1 != null)  && (childNodes1.getLength() > 0) ) {

		return values;

	} // private static String[] getAllowedValues(com.google.gwt.xml.client.Document _document) {

} // class
