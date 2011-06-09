package com.astrium.faceo.client.bean.programming.sps2.request;/* * @(#)TaskingRequestBean.java	 1.0  08/2010 * * * PROJET       : SITE FACEO * * LANGUAGE     : Java * * DESCRIPTION  : Cette classe est un conteneur d'informations pour les requ&ecirc;tes  * des messages XML des op&eacute;rations 'GetFeasibility', 'Submit, 'Update' du standard OGC SPS 2.0 * * CREATION : * --------------------------------------------------------------------- * | Date       | Version | Description                                | * --------------------------------------------------------------------- * | 23/08/2010 |    1.0  |                                            | * --------------------------------------------------------------------- * * MODIFICATION : * --------------------------------------------------------------------- * | Date       | Version | Description                                | * --------------------------------------------------------------------- * |            |         |                                            | * --------------------------------------------------------------------- * *//* * sample of GetFeasibility Request *  * <sps:GetFeasibility service="SPS" version="2.0.0" xmlns:eop="http://www.esa.int/eop"  xmlns:eo="urn:ogc:def:property:CEOS" xmlns:sps="http://www.opengis.net/sps/2.0" xmlns:swe="http://www.opengis.net/swe/2.0" xmlns:swes="http://www.opengis.net/sweService/1.0">   <swes:extension>      <eo:FeasibilityLevel>FULL</eo:FeasibilityLevel>   </swes:extension>   <sps:sensorID>urn:ESA:sensors:Sentinel-1:C-SAR</sps:sensorID>   <sps:taskingParameters>      <sps:ParameterData>         <sps:encoding>            <swe:XMLEncoding namespace="urn:ogc:def:property:CEOS"/>         </sps:encoding>         <sps:values>            <eo:CoverageProgrammingRequest>               <eo:QualityOfService>                  <eo:Priority>HIGH</eo:Priority>               </eo:QualityOfService>               <eo:RegionOfInterest>                  <eo:Polygon>                     <eo:Exterior elementCount="5">                        <eo:Point>                           <eo:Lat>60.0</eo:Lat>                           <eo:Lon>60.0</eo:Lon>                        </eo:Point>                        <eo:Point>                           <eo:Lat>60.0</eo:Lat>                           <eo:Lon>61.0</eo:Lon>                        </eo:Point>                        <eo:Point>                           <eo:Lat>61.0</eo:Lat>                           <eo:Lon>61.0</eo:Lon>                        </eo:Point>                        <eo:Point>                           <eo:Lat>61.0</eo:Lat>                           <eo:Lon>60.0</eo:Lon>                        </eo:Point>                        <eo:Point>                           <eo:Lat>60.0</eo:Lat>                           <eo:Lon>60.0</eo:Lon>                        </eo:Point>                     </eo:Exterior>                  </eo:Polygon>               </eo:RegionOfInterest>               <eo:TimeOfInterest>                  <eo:SurveyPeriod>                     <eo:min>2010-05-20T00:00:00Z</eo:min>                     <eo:max>2010-05-30T00:00:00Z</eo:max>                  </eo:SurveyPeriod>               </eo:TimeOfInterest>               <eo:AcquisitionType>                  <eo:MonoscopicAcquisition>                     <eo:CoverageType>MULTIPASS</eo:CoverageType>                     <eo:IncidenceAngle>                        <eo:Azimuth>                           <eo:min>-180.0</eo:min>                           <eo:max>180.0</eo:max>                        </eo:Azimuth>                        <eo:Elevation>                           <eo:min>0.0</eo:min>                           <eo:max>15.0</eo:max>                        </eo:Elevation>                     </eo:IncidenceAngle>                     <eo:AcquisitionParametersOPT>                        <eo:GroundResolution>                           <eo:min>2.5</eo:min>                           <eo:max>10.0</eo:max>                        </eo:GroundResolution>                        <eo:InstrumentMode>PANCHROMATIC</eo:InstrumentMode>                        <eo:FusionAccepted>false</eo:FusionAccepted>                     </eo:AcquisitionParametersOPT>                  </eo:MonoscopicAcquisition>               </eo:AcquisitionType>               <eo:ValidationParametersOPT>                  <eo:MaxCloudCover>25.0</eo:MaxCloudCover>                  <eo:MaxSnowCover>100.0</eo:MaxSnowCover>                  <eo:HazeAccepted>true</eo:HazeAccepted>                  <eo:SandWindAccepted>false</eo:SandWindAccepted>               </eo:ValidationParametersOPT>            </eo:CoverageProgrammingRequest>         </sps:values>      </sps:ParameterData>   </sps:taskingParameters></sps:GetFeasibility> */// importimport java.io.Serializable;/** * <B>SITE FACEO</B> <BR> *  * <P> * Cette classe est un conteneur d'informations pour les op&eacute;rations SPS 'GetFeasibility', 'Submit' and 'Update' * </P> * </P> *  * @author  GR * @version 1.0, le 23/08/2010 */public class TaskingRequestBean implements Serializable {	/**	 * 	 */	private static final long serialVersionUID = 2501441193698300446L;	/** FeasibilityLevel */	private String feasibilityLevel = null;	/** Quality Of Service : PriorityLevel */	private QualityOfServiceBean qualityOfService = new QualityOfServiceBean();	/** Coverage Programmming Request */	private CoverageProgrammmingRequestBean coverageProgrammmingRequest = 		new CoverageProgrammmingRequestBean();		/** Validation Parameters */	private ValidationParametersBean validationParameters = 		new ValidationParametersBean();	/**	 * debut des methodes	 	 * 	 * Constructeur par defaut : vide	 */	public TaskingRequestBean() {	}	/** getters */	/** 	 * getter on feasibilityLevel	 * 	 * @return String : feasibility level	*/	public String getFeasibilityLevel() {		return (this.feasibilityLevel != null) ? this.feasibilityLevel : "";	}		/** 	 * getter on qualityOfService	 * 	 * @return QualityOfServiceBean : quality Of Service	*/	public QualityOfServiceBean getQualityOfService() {		return this.qualityOfService;	}		/** 	 * getter on coverageProgrammmingRequest	 * 	 * @return CoverageProgrammmingRequestBean : Coverage Programmming Request	*/	public CoverageProgrammmingRequestBean getCoverageProgrammmingRequest() {		return this.coverageProgrammmingRequest;	}	/** 	 * getter on validationParameters	 * 	 * @return ValidationParametersBean : validation parameters	*/	public ValidationParametersBean getValidationParameters() {		return this.validationParameters;	}		/** setters */	/** 	 * setter on feasibilityLevel	 * 	 * @param _feasibilityLevel (String): feasibility level value	*/	public void setFeasibilityLevel(String _feasibilityLevel) {		this.feasibilityLevel = _feasibilityLevel;	}		/** 	 * setter on qualityOfService	 * 	 * @param _qualityOfService (QualityOfServiceBean): quality Of Service value	*/	public void setQualityOfService(QualityOfServiceBean _qualityOfService) {		this.qualityOfService = _qualityOfService;	}		/** 	 * setter on coverageProgrammmingRequest	 * 	 * @param _coverageProgrammmingRequest (CoverageProgrammmingRequestBean): Coverage Programmming Request value	*/	public void setCoverageProgrammmingRequest(CoverageProgrammmingRequestBean _coverageProgrammmingRequest) {		this.coverageProgrammmingRequest = _coverageProgrammmingRequest;	}	/** 	 * setter on validationParameters	 * 	 * @param _validationParameters (ValidationParametersBean): validation parameters value	*/	public void setValidationParameters(ValidationParametersBean _validationParameters) {		this.validationParameters = _validationParameters;	}} // class