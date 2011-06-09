package com.astrium.faceo.client.bean.programming.sps2.request;/* * @(#)AcquisitionParametersSARBean.java	 1.0  20/04/2010 * * * PROJET       : SITE FACEO * * LANGUAGE     : Java * * DESCRIPTION  : The AcquisitionType class is used to specify if the acquisition is monoscopic or * stereoscopic, as well as contain specific acquisition parameters. * * CREATION : * --------------------------------------------------------------------- * | Date       | Version | Description                                | * --------------------------------------------------------------------- * | 20/04/2010 |    1.0  |                                            | * --------------------------------------------------------------------- * * MODIFICATION : * --------------------------------------------------------------------- * | Date       | Version | Description                                | * --------------------------------------------------------------------- * |            |         |                                            | * --------------------------------------------------------------------- * *//* * sample of Acquisition type for GetFeasibility Request *  *   <eo:AcquisitionType> *       <eo:MonoscopicAcquisition> *            <eo:CoverageType>MULTIPASS</eo:CoverageType> *            <eo:IncidenceAngle> *               <eo:Azimuth> *                  <eo:min>-180.0</eo:min> *                  <eo:max>180.0</eo:max> *               </eo:Azimuth> *               <eo:Elevation> *                  <eo:min>0.0</eo:min> *                  <eo:max>15.0</eo:max> *               </eo:Elevation> *            </eo:IncidenceAngle> *            <eo:AcquisitionParametersOPT> *               <eo:GroundResolution> *                  <eo:min>2.5</eo:min> *                  <eo:max>10.0</eo:max> *               </eo:GroundResolution> *               <eo:InstrumentMode>PANCHROMATIC</eo:InstrumentMode> *               <eo:FusionAccepted>false</eo:FusionAccepted> *            </eo:AcquisitionParametersOPT> *       </eo:MonoscopicAcquisition> *   </eo:AcquisitionType> */// importimport java.io.Serializable;/** * <B>SITE FACEO</B> <BR> *  * <P> * Acquisition Type informations : * The AcquisitionType class is used to specify if the acquisition is monoscopic or * stereoscopic, as well as contain specific acquisition parameters. * </P> * </P> <BR> *  * sample of Acquisition type for GetFeasibility Request *  *   <eo:AcquisitionType> *       <eo:MonoscopicAcquisition> *            <eo:CoverageType>MULTIPASS</eo:CoverageType> *            <eo:IncidenceAngle> *               <eo:Azimuth> *                  <eo:min>-180.0</eo:min> *                  <eo:max>180.0</eo:max> *               </eo:Azimuth> *               <eo:Elevation> *                  <eo:min>0.0</eo:min> *                  <eo:max>15.0</eo:max> *               </eo:Elevation> *            </eo:IncidenceAngle> *            <eo:AcquisitionParametersOPT> *               <eo:GroundResolution> *                  <eo:min>2.5</eo:min> *                  <eo:max>10.0</eo:max> *               </eo:GroundResolution> *               <eo:InstrumentMode>PANCHROMATIC</eo:InstrumentMode> *               <eo:FusionAccepted>false</eo:FusionAccepted> *            </eo:AcquisitionParametersOPT> *       </eo:MonoscopicAcquisition> *   </eo:AcquisitionType> *  * @author  GR * @version 1.0, le 20/04/2010 */public class AcquisitionParametersSARBean extends AcquisitionParametersBean implements Serializable {	/**	 * 	 */	private static final long serialVersionUID = -7599628005767789397L;	/**	 * Receive/Transmit polarization mode of a SAR instrument	 */	private String polarizationMode = null;	/**	 * debut des methodes	 	 * 	 * Constructeur par defaut : vide	 */	public AcquisitionParametersSARBean() {	}	/** getters */		/** 	 * getter on polarizationMode	 * 	 * @return String : polarization Mode	*/		public String getPolarizationMode() {		return (this.polarizationMode != null) ? this.polarizationMode : "";	}	/** setters */	/** 	 * setter on polarizationMode	 * 	 * @param _polarizationMode (String): polarization Mode value	*/	public void setPolarizationMode(String _polarizationMode) {		this.polarizationMode = _polarizationMode;	}} // class