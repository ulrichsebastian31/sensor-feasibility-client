package com.astrium.faceo.client.bean.programming.sps2.request;/* * @(#)MonoscopicAcquisitionBean.java	 1.0  20/04/2010 * * * PROJET       : SITE FACEO * * LANGUAGE     : Java * * DESCRIPTION  : The AcquisitionType class is used to specify if the acquisition is monoscopic or * stereoscopic, as well as contain specific acquisition parameters. * * CREATION : * --------------------------------------------------------------------- * | Date       | Version | Description                                | * --------------------------------------------------------------------- * | 20/04/2010 |    1.0  |                                            | * --------------------------------------------------------------------- * * MODIFICATION : * --------------------------------------------------------------------- * | Date       | Version | Description                                | * --------------------------------------------------------------------- * |            |         |                                            | * --------------------------------------------------------------------- * *//* * sample of Acquisition type for GetFeasibility Request *  *   <eo:AcquisitionType> *       <eo:MonoscopicAcquisition> *            <eo:CoverageType>MULTIPASS</eo:CoverageType> *            <eo:IncidenceAngle> *               <eo:Azimuth> *                  <eo:min>-180.0</eo:min> *                  <eo:max>180.0</eo:max> *               </eo:Azimuth> *               <eo:Elevation> *                  <eo:min>0.0</eo:min> *                  <eo:max>15.0</eo:max> *               </eo:Elevation> *            </eo:IncidenceAngle> *            <eo:AcquisitionParametersOPT> *               <eo:GroundResolution> *                  <eo:min>2.5</eo:min> *                  <eo:max>10.0</eo:max> *               </eo:GroundResolution> *               <eo:InstrumentMode>PANCHROMATIC</eo:InstrumentMode> *               <eo:FusionAccepted>false</eo:FusionAccepted> *            </eo:AcquisitionParametersOPT> *       </eo:MonoscopicAcquisition> *   </eo:AcquisitionType> */// importimport java.io.Serializable;/** * <B>SITE FACEO</B> <BR> *  * <P> * Acquisition Type informations : * The AcquisitionType class is used to specify if the acquisition is monoscopic or * stereoscopic, as well as contain specific acquisition parameters. * </P> * </P> <BR> *  * sample of Acquisition type for GetFeasibility Request *  *   <eo:AcquisitionType> *       <eo:MonoscopicAcquisition> *            <eo:CoverageType>MULTIPASS</eo:CoverageType> *            <eo:IncidenceAngle> *               <eo:Azimuth> *                  <eo:min>-180.0</eo:min> *                  <eo:max>180.0</eo:max> *               </eo:Azimuth> *               <eo:Elevation> *                  <eo:min>0.0</eo:min> *                  <eo:max>15.0</eo:max> *               </eo:Elevation> *            </eo:IncidenceAngle> *            <eo:AcquisitionParametersOPT> *               <eo:GroundResolution> *                  <eo:min>2.5</eo:min> *                  <eo:max>10.0</eo:max> *               </eo:GroundResolution> *               <eo:InstrumentMode>PANCHROMATIC</eo:InstrumentMode> *               <eo:FusionAccepted>false</eo:FusionAccepted> *            </eo:AcquisitionParametersOPT> *       </eo:MonoscopicAcquisition> *   </eo:AcquisitionType> *  * @author  GR * @version 1.0, le 20/04/2010 */public class MonoscopicAcquisitionBean implements Serializable {	/**	 * 	 */	private static final long serialVersionUID = 4378925121706392755L;	/** 	 * coverage type :	 * 	 * Specifies if the imagery should be acquired in one or multiple passes.	 * SINGLE_SWATH: The region of interest should be covered by a single segment.	 * MONOPASS: The region of interest must be covered by one or more	 * segments acquired from the same orbit (some agile satellites can cover	 * large zones even when satisfying this constraint).	 * MULTIPASS: The region of interest can be covered by using images	 * extracted from several segments acquired at different dates provided	 * that they are all acquired within the requested time period	 */	private String coverageType = null;		/** 	 * acquisition angle : 	 *           choice : incidence angle range or pointing angle range	 * Acceptable acquisition angles can be specified either by indicating a range of incidence	 * angles (i.e. the angle with which the look vector intersects the earth surface) or a range of	 * pointing angles (i.e. the angle of the look vector in the nadir oriented frame of reference	 * attached to the satellite).		 * incidence angle range :	 * Specification of allowed acquisition angles in terms of angles expressed relative to a	 * frame of reference attached to the location of the acquisition (i.e. on the ground).			 * Azimuth Range :	 * Range of acceptable azimuth incidence angles. 	 * The azimuth angle is the angle that the look vector (projected vertically on the earth surface) 	 * makes with the north direction. It thus indicates from which geographic direction (i.e. North, East, West,	 * South) the region of interest should be imaged.		 * Pointing Angle Range :	 * Specification of allowed acquisition angles in terms of angles expressed in the satellite	 * reference frame and relative to the nadir direction..	 * Elevation Range :	 * 	 * Range of acceptable elevation incidence angles. 	 * The elevation angle is the angle that the look vector makes	 * with the local vertical. It thus indicates how vertically the region should be imaged.	 * This is the traditional meaning of incidence angle	 */	private AcquisitionAngleRangeBean acquisitionAngleRange = new AcquisitionAngleRangeBean();	/**	 * debut des methodes	 	 * 	 * Constructeur par defaut : vide	 */	public MonoscopicAcquisitionBean() {	}	/** getters */		/** 	 * getter on coverageType	 * 	 * @return String : coverage type	*/	public String getCoverageType() {		return (this.coverageType != null) ? this.coverageType : "";	}		/**  acquisition angle : Azimuth Range ane Elevation Range */		/** 	 * getter on acquisitionAngleRange	 * 	 * @return AcquisitionAngleRangeBean : acquisition Angle Range	*/	public AcquisitionAngleRangeBean getAcquisitionAngleRange() {		return this.acquisitionAngleRange;	}		/** setters */	/** 	 * setter on coverageType	 * 	 * @param _coverageType (String): coverage Type value	*/	public void setCoverageType(String _coverageType) {		this.coverageType = _coverageType;	}	/**  acquisition angle */	/** 	 * setter on acquisitionAngleRange	 * 	 * @param _acquisitionAngleRange (AcquisitionAngleRangeBean): acquisition Angle Range value	*/	public void setAcquisitionAngleRange(AcquisitionAngleRangeBean _acquisitionAngleRange) {		this.acquisitionAngleRange = _acquisitionAngleRange;	}} // class