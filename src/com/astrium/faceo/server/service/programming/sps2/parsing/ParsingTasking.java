package com.astrium.faceo.server.service.programming.sps2.parsing;

/*
 * @(#)ParsingTasking.java	 1.0  14/06/2010
 *
 *
 * PROJET       : FACEO
 *
 * LANGUAGE     : Java
 *
 * DESCRIPTION  :  The ParsingTasking class parse the xml messages of SPS 2.0
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

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.opengis.www.eosps._2_0.ValidateResponseDocument;
import net.opengis.www.eosps._2_0.ValidateResponseType;
import net.opengis.www.sps._2_0.GetFeasibilityResponseDocument;
import net.opengis.www.sps._2_0.GetFeasibilityResponseType;
import net.opengis.www.sps._2_0.GetStatusResponseDocument;
import net.opengis.www.sps._2_0.GetStatusResponseType;
import net.opengis.www.sps._2_0.ReserveResponseDocument;
import net.opengis.www.sps._2_0.ReserveResponseType;
import net.opengis.www.sps._2_0.StatusReportPropertyType;
import net.opengis.www.sps._2_0.StatusReportType;
import net.opengis.www.sps._2_0.SubmitResponseDocument;
import net.opengis.www.sps._2_0.SubmitResponseType;
import net.opengis.www.sps._2_0.TaskingRequestDocument;
import net.opengis.www.sps._2_0.TaskingResponseType.Result;
import net.opengis.www.sps._2_0.UpdateResponseDocument;
import net.opengis.www.sps._2_0.UpdateResponseType;

import org.apache.log4j.Logger;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.vast.ows.sps.DescribeTaskingResponse;
import org.vast.ows.sps.GetStatusResponse;
import org.vast.ows.sps.SPSUtils;
import org.vast.ows.sps.TaskingRequest;
import org.vast.ows.sps.TaskingResponse;
import org.vast.util.DateTime;
import org.vast.util.DateTimeFormat;
import org.vast.xml.DOMHelper;

import com.astrium.faceo.client.bean.programming.sps2.SensorBean;
import com.astrium.faceo.client.bean.programming.sps2.TaskBean;
import com.astrium.faceo.client.bean.programming.sps2.TaskStatusReportBean;
import com.astrium.faceo.client.bean.programming.sps2.request.AcquisitionAngleRangeBean;
import com.astrium.faceo.client.bean.programming.sps2.request.AcquisitionParametersOPTBean;
import com.astrium.faceo.client.bean.programming.sps2.request.AcquisitionParametersSARBean;
import com.astrium.faceo.client.bean.programming.sps2.request.AcquisitionTypeBean;
import com.astrium.faceo.client.bean.programming.sps2.request.CoverageProgrammmingRequestBean;
import com.astrium.faceo.client.bean.programming.sps2.request.IncidenceAngleRangeBean;
import com.astrium.faceo.client.bean.programming.sps2.request.MonoscopicAcquisitionBean;
import com.astrium.faceo.client.bean.programming.sps2.request.PointingAngleRangeBean;
import com.astrium.faceo.client.bean.programming.sps2.request.ProgrammingRequestBean;
import com.astrium.faceo.client.bean.programming.sps2.request.QualityOfServiceBean;
import com.astrium.faceo.client.bean.programming.sps2.request.RegionOfInterestBean;
import com.astrium.faceo.client.bean.programming.sps2.request.StereoscopicAcquisitionBean;
import com.astrium.faceo.client.bean.programming.sps2.request.SurveyPeriodBean;
import com.astrium.faceo.client.bean.programming.sps2.request.TaskingParametersBean;
import com.astrium.faceo.client.bean.programming.sps2.request.TimeOfInterestBean;
import com.astrium.faceo.client.bean.programming.sps2.request.ValidationParametersBean;
import com.astrium.faceo.client.bean.programming.sps2.request.ValidationParametersOPTBean;
import com.astrium.faceo.client.bean.programming.sps2.request.ValidationParametersSARBean;
import com.astrium.faceo.client.bean.programming.sps2.responses.PointBean;
import com.astrium.faceo.client.bean.programming.sps2.responses.SegmentBean;
import com.astrium.faceo.client.bean.programming.sps2.responses.TaskingResponseBean;
import com.astrium.faceo.client.common.ConfigConstant;
import com.astrium.faceo.client.common.programming.sps2.Sps2GeneralConstants;
import com.spotimage.eosps.EOConstants.AcquisitionType;
import com.spotimage.eosps.EOOpticalParamHelper;
import com.spotimage.eosps.EOOpticalReportHelper;
import com.spotimage.eosps.EOParamHelper;
import com.spotimage.eosps.EORadarParamHelper;
import com.spotimage.eosps.EORadarReportHelper;
import com.spotimage.eosps.EOReportHelper;
import com.spotimage.eosps.ValidateResponse;
import com.spotimage.eosps.ValidateResponseReaderV20;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Polygon;

/**
 * <B>SITE FACEO</B> <BR>
 * 
 * <P>
 * The ParsingTasking class parse the xml messages of SPS 2.0 for 'GetFeasibility' and 'Submit' operations
 * 
 * The GetFeasibility operation allows SPS clients to obtain information 
 * about the feasibility of a tasking request. Dependent on the types 
 * of assets facaded by the SPS, the SPS server action may be as simple 
 * as checking that the request parameters are valid, and are consistent 
 * with certain business rules, or it may be a complex operation 
 * that calculates the utilizability of the asset to perform a specific task 
 * at the defined location, time, orientation, calibration etc. 
 * (see clause 6.3.4 for further details on task feasibility).
 * 
 * The Submit operation allows SPS clients to submit a tasking request. 
 * The SPS shall perform an internal feasibility check for the intended task and 
 * – if the task is feasible – shall perform the task.
 * 
 * </P>
 * </P>
 * 
 * @author GR
 * @version 1.0, le 14/06/2010
 */
public class ParsingTasking {

	/** Le logger de cette classe */
	private static Logger log = Logger.getLogger(ParsingTasking.class);

	private static String ORIGINE = "ParsingTasking";

	protected SPSUtils utils = new SPSUtils();

	/**
	 * constructor
	 */
	public ParsingTasking() {    
	}

	/**
	 * This method parse the request of a 'GetFeasibility', 'Submit', 'Reserve' or 'Update' operation using the EO SPS Spot Library
	 * 
	 * @param _task (TaskBean) 									: task informations
	 * @param _sensorType (String) 								: sensor type : "optical" or "radar"
	 * @param _taskingRequestDocument (TaskingRequestDocument) 	: tasking request
	 * @param _serverURL (String) 								: server URL
	 * @param _taskingParameters (TaskingParametersBean)		: tasking parameters for one sensor
	 * @param _sensors (Map<String, SensorBean>)				: sensors descriptions
	 * 
	 * @return ProgrammingRequestBean : result of 'GetFeasibility', 'Submit', 'Reserve' or 'Update' request parsing
	 * @throws Exception
	 */
	public ProgrammingRequestBean parseTaskingRequest(
			TaskBean _task, 
			String _sensorType, 
			TaskingRequestDocument _taskingRequestDocument,
			String _serverURL,
			TaskingParametersBean _taskingParameters,
			Map<String, SensorBean> _sensors) 
	throws Exception {

		ProgrammingRequestBean programmingRequest = new ProgrammingRequestBean();

		// read tasking params
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		outputStream.write(_taskingRequestDocument.toString().getBytes());

		// read back from buffer
		ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
		inputStream = new ByteArrayInputStream(outputStream.toByteArray());

		// read response using DescribeTasking data definition
		DOMHelper domHelper = new DOMHelper(inputStream, false);

		// read tasking parameters
		DescribeTaskingResponse response = 
			ParsingUtils.readDescribeTaskingResponse(
					ParsingUtils.getDescribeParametersXmlFile(_task.getSensor(), _serverURL), _serverURL);

		TaskingRequest taskingRequest = 
			(TaskingRequest)utils.readParameterizedRequest(domHelper, domHelper.getRootElement(), response.getTaskingParameters());

		// instantiate helpers
		boolean opticalType = true;

		opticalType = ParsingUtils.getSensorOpticalType(_task.getSensor(), _serverURL);

		EOParamHelper helper = null;
		EORadarParamHelper helperRadar = null;
		EOOpticalParamHelper helperOptical = null;

		if (opticalType) {
			helperOptical = new EOOpticalParamHelper(taskingRequest);
			helper = (EOParamHelper) helperOptical;
		} else {
			helperRadar = new EORadarParamHelper(taskingRequest);
			helper = (EOParamHelper) helperRadar;
		}

		// setting request parameters from database data
		programmingRequest.getSensor().setUrn(_task.getSensor());

		// user name
		programmingRequest.setUser(ConfigConstant.SPS_FACEO_USER);

		// request name
		programmingRequest.setName(_task.getName());

		// ---------------------- Coverage Programmming Request -------------------------
		CoverageProgrammmingRequestBean coverageProgrammmingRequest = 
			new CoverageProgrammmingRequestBean();

		// ---------------------- set Region of Interest : AOI (polygon : rectangle) ----------------------
		RegionOfInterestBean regionOfInterest = new RegionOfInterestBean();

		int numPoints = 0;
		try {
			numPoints = helper.getPolygonROI().getNumPoints();
		} catch (Exception exception) {
			log.info("\n" + ORIGINE + " : Exception : " + exception.getLocalizedMessage());
		}
		if (numPoints == 5) {
			Coordinate[] coordinates = helper.getPolygonROI().getCoordinates();

			regionOfInterest.setLeftLower(coordinates[0].y);
			regionOfInterest.setLeftUpper(coordinates[1].y);
			regionOfInterest.setRightUpper(coordinates[0].x);
			regionOfInterest.setRightLower(coordinates[2].x);
		} // if (numPoints == 5) {

		coverageProgrammmingRequest.setRegionOfInterest(regionOfInterest);
		// ---------------------- end Region of Interest -------------------------

		// ---------------------- set Time of Interest : Period ----------------------
		TimeOfInterestBean timeOfInterest = new TimeOfInterestBean();

		timeOfInterest.setType(Sps2GeneralConstants.TIME_SURVEY_PERIOD);

		// "2010-06-01T00:00:00Z""
		DateTime begin = new DateTime(helper.getSurveyStartDate());
		DateTime end = new DateTime(helper.getSurveyEndDate());

		DateTimeFormat dateTimeFormat = new DateTimeFormat();
		dateTimeFormat.applyPattern(Sps2GeneralConstants.TIME_FORMAT);
		String startPeriod = dateTimeFormat.format(begin);
		String endPeriod = dateTimeFormat.format(end);

		SurveyPeriodBean surveyPeriod = new SurveyPeriodBean();

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Sps2GeneralConstants.TIME_FORMAT);
		Date beginDate = simpleDateFormat.parse(startPeriod);
		Date endDate = simpleDateFormat.parse(endPeriod);

		surveyPeriod.setStartDate(beginDate);
		surveyPeriod.setStrStartDate(startPeriod);
		surveyPeriod.setEndDate(endDate);
		surveyPeriod.setStrEndDate(endPeriod);
		timeOfInterest.setSurveyPeriod(surveyPeriod);

		coverageProgrammmingRequest.setTimeOfInterest(timeOfInterest);
		// ---------------------- end Period -------------------------

		programmingRequest.getSensor().setType(_sensorType);

		// ---------------------- Validation parameters ----------------------
		ValidationParametersBean validationParameters = new ValidationParametersBean();

		if (opticalType) {
			ValidationParametersOPTBean validationParametersOPT  = new ValidationParametersOPTBean ();

			// Max Cloud Coverage
			if (_taskingParameters.getMaxSnowCoverage()) {
				validationParametersOPT.setMaxCloudCoverage(Double.valueOf(helperOptical.getMaxCloudCover()));
			} // 

			// Max Snow Coverage
			if (_taskingParameters.getMaxSnowCoverage()) {
				validationParametersOPT.setMaxSnowCoverage(Double.valueOf(helperOptical.getMaxSnowCover()));
			} // 

			// Haze Accepted
			if (_taskingParameters.getHazeAccepted()) {
				validationParametersOPT.setHazeAccepted(helperOptical.isHazeAccepted());
			} // 

			// Sand Wind Accepted
			if (_taskingParameters.getSandWindAccepted()) {
				validationParametersOPT.setSandWindAccepted(helperOptical.isSandWindAccepted());
			} // 

			validationParameters.setValidationParametersOPT(validationParametersOPT);
		}  else {
			ValidationParametersSARBean validationParametersSAR  = new ValidationParametersSARBean ();

			// Max Noise Level
			if (_taskingParameters.getMaxNoiseLevel()) {
				validationParametersSAR.setMaxNoiseLevel(Double.valueOf(helperRadar.getMaxNoiseLevel()));
			} //

			// Max Ambiguity Level
			if (_taskingParameters.getMaxAmbiguityLevel()) {
				validationParametersSAR.setMaxAmbiguityLevel(Double.valueOf(helperRadar.getMaxAmbiguityLevel()));
			} //

			validationParameters.setValidationParametersSAR(validationParametersSAR);
		} // if (opticalType) {

		programmingRequest.getTasking().setValidationParameters(validationParameters);
		// ---------------------- end Validation Parameters -------------------------

		// ---------------------- QOS : Quality Of Service -------------------------
		QualityOfServiceBean qualityOfService = new QualityOfServiceBean();
		qualityOfService.setPriorityLevel(helper.getPriorityLevel());
		programmingRequest.getTasking().setQualityOfService(qualityOfService);
		// ---------------------- end QOS : Quality Of Service -------------------------

		// ---------------------- Acquisition Parameters -------------------------

		// set Acquisition Type
		AcquisitionTypeBean acquisitionType = new AcquisitionTypeBean();

		if (helper.getAcquisitionType() == AcquisitionType.MONO) {
			acquisitionType.setType(Sps2GeneralConstants.ACQUISITION_TYPE_MONO);
		} else if (helper.getAcquisitionType() == AcquisitionType.STEREO) {
			acquisitionType.setType(Sps2GeneralConstants.ACQUISITION_TYPE_STEREO);
		} else {
			acquisitionType.setType(Sps2GeneralConstants.ACQUISITION_TYPE_UNKOWN);
		}

		// set Type Monoscopic
		MonoscopicAcquisitionBean monoscopicAcquisition = new MonoscopicAcquisitionBean();

		// set Type Stereoscopic
		StereoscopicAcquisitionBean stereoscopicAcquisition = new StereoscopicAcquisitionBean();

		if (_taskingParameters.getCoverageType()) {
			if (helper.getAcquisitionType() == AcquisitionType.MONO) {
				monoscopicAcquisition.setCoverageType(helper.getCoverageType().toString());
			} else if (helper.getAcquisitionType() == AcquisitionType.STEREO) {
				stereoscopicAcquisition.setCoverageType(helper.getCoverageType().toString());
			} else {
				monoscopicAcquisition.setCoverageType(helper.getCoverageType().toString());
			} //
		} // 

		if (_taskingParameters.getBHRatio()) {
			//			stereoscopicAcquisition.setbHRatio(_bHRatio);
		} // 

		if (_taskingParameters.getMaxCoupleDelay()) {
			//			stereoscopicAcquisition.setMaxCoupleDelay(_maxCoupleDelay);
		} // 

		AcquisitionAngleRangeBean acquisitionAngleRange = new AcquisitionAngleRangeBean();

		// ---------------------- Incidence Angle Parameters -------------------------
		IncidenceAngleRangeBean incidenceAngleRange = new IncidenceAngleRangeBean();

		// azimuth
		incidenceAngleRange.setAzimuthIncidenceMin(Float.valueOf(Double.toString(helper.getMinAzimuthIncidence())));
		incidenceAngleRange.setAzimuthIncidenceMax(Float.valueOf(Double.toString(helper.getMaxAzimuthIncidence())));

		// elevation
		incidenceAngleRange.setElevationIncidenceMin(Float.valueOf(Double.toString(helper.getMinElevationIncidence())));
		incidenceAngleRange.setElevationIncidenceMax(Float.valueOf(Double.toString(helper.getMaxElevationIncidence())));

		acquisitionAngleRange.setIncidenceAngleRange(incidenceAngleRange);

		// ---------------------- Pointing Angle Parameters -------------------------
		PointingAngleRangeBean pointingAngleRange = new PointingAngleRangeBean();

		// Pointing Angle Along
		//		pointingAngleRange.setAlongTrackMin(Float.valueOf(helper.get));
		//		pointingAngleRange.setAlongTrackMax(Float.valueOf(sensorPanel.getAlongTrackAngleMax().getValueAsString()));

		// Pointing Angle Across
		//		pointingAngleRange.setAcrosTrackMin(Float.valueOf(sensorPanel.getAcrossTrackAngleMin().getValueAsString()));
		//		pointingAngleRange.setAcrosTrackMax(Float.valueOf(sensorPanel.getAcrossTrackAngleMax().getValueAsString()));

		acquisitionAngleRange.setPointingAngleRange(pointingAngleRange);				

		// ---------------------- end Acquisition Angle -------------------------

		if (helper.getAcquisitionType() == AcquisitionType.MONO) {
			monoscopicAcquisition.setAcquisitionAngleRange(acquisitionAngleRange);

			acquisitionType.setMonoscopicAcquisition(monoscopicAcquisition);
		} else if (helper.getAcquisitionType() == AcquisitionType.STEREO) {
			stereoscopicAcquisition.setAcquisitionAngle1(acquisitionAngleRange);
			//				stereoscopicAcquisition.setAcquisitionAngle2(acquisitionAngleRange);

			acquisitionType.setStereoscopicAcquisition(stereoscopicAcquisition);
		} // 

		// ---------------------- Acquisition parameters -------------------------

		if (opticalType) {
			// OPTICAL : OPT acquisition parameters
			AcquisitionParametersOPTBean acquisitionParametersOPT = new AcquisitionParametersOPTBean();

			if (_taskingParameters.getFusionAccepted()) {
				acquisitionParametersOPT.setFusionAccepted(helper.isFusionAccepted());
			} // 

			if (_taskingParameters.getInstrumentMode()) {
				acquisitionParametersOPT.setInstrumentMode(helper.getInstrumentMode());
			} // 

			if (_taskingParameters.getGroundResolution()) {
				acquisitionParametersOPT.setGroundResolutionMin(Double.valueOf(helper.getMinGroundResolution()));
				acquisitionParametersOPT.setGroundResolutionMax(Double.valueOf(helper.getMaxGroundResolution()));
			} // 

			if (_taskingParameters.getMinLuminosity()) {
				acquisitionParametersOPT.setMinLuminosity(Float.valueOf(Double.toString(helperOptical.getMinLuminosity())));
			} // 

			acquisitionType.setAcquisitionParametersOPT(acquisitionParametersOPT);
		} else {
			// RADAR : SAR acquisition parameters
			AcquisitionParametersSARBean acquisitionParametersSAR = new AcquisitionParametersSARBean();

			if (_taskingParameters.getFusionAccepted()) {
				acquisitionParametersSAR.setFusionAccepted(helper.isFusionAccepted());
			} // 

			if (_taskingParameters.getInstrumentMode()) {
				acquisitionParametersSAR.setInstrumentMode(helper.getInstrumentMode());
			} // 

			if (_taskingParameters.getGroundResolution()) {
				acquisitionParametersSAR.setGroundResolutionMin(Double.valueOf(helper.getMinGroundResolution()));
				acquisitionParametersSAR.setGroundResolutionMax(Double.valueOf(helper.getMaxGroundResolution()));
			} // 

			if (_taskingParameters.getPolarizationMode()) {
				acquisitionParametersSAR.setPolarizationMode(helperRadar.getPolarizationMode());
			} // 

			acquisitionType.setAcquisitionParametersSAR(acquisitionParametersSAR);
		} // if (opticalType) {

		coverageProgrammmingRequest.setAcquisitionType(acquisitionType);
		// ---------------------- end Acquisition Type -------------------------

		programmingRequest.getTasking().setCoverageProgrammmingRequest(coverageProgrammmingRequest);
		// ---------------------- end Coverage Programmming Request -------------------------

		return programmingRequest;
	} // public ProgrammingRequestBean parseTaskingRequest(

	/**
	 * This method parse the response of a 'GetFeasibility' operation using the EO SPS Spot Library
	 * 
	 * @param _sensorId (String) 									: sensor identifier
	 * @param _sensorType (String) 									: sensor type : "optical" or "radar"
	 * @param _responseDocument (GetFeasibilityResponseDocument) 	: 'GetFeasibility' response
	 * @param _serverURL (String) 									: server URL
	 * 
	 * @return TaskingResponseBean : result of 'GetFeasibility' response parsing
	 * @throws Exception
	 */
	public TaskingResponseBean parseGetFeasibilityResponse(
			String _sensorId, 
			String _sensorType, 
			GetFeasibilityResponseDocument _responseDocument,
			String _serverURL) 
	throws Exception {

		/**
		 * The FeasibilityStudy class represents extended data included in Feasibility Results
		 * received via GetFeasibility.
		 * 
		 * The results of a feasibility study are composed of an overall feasibility assessment, plus a
		 * detailed feasibility, per cell or per segment, depending on the mission.
		 * In some cases, feasible segments can be estimated long in advance and be provided
		 * directly in the results (usually for deterministic missions, i.e. not agile). 
		 * For each segment, several properties can be specified:
		 *		- The segment identifier, unique within the feasibility study
		 *		- The combination of platform, instrument and mode used to acquire this segment
		 *		- The status which should always be “ESTIMATED” in the case of a feasibility
		 *		  study
		 *		- The Start and Stop acquisition times
		 *		- The acquisition angles, specified either as pointing or incidence angles along one
		 *		  or two directions
		 *		- Orbit information in terms of orbit, track and frame number according to the grid
		 *		  defined for the mission of interest.
		 *		- The ground footprint as a polygon geometry
		 *		- The preview URL is obviously out of scope in the case of a feasibility study
		 *		  Some properties specific to the type of instrument:
		 *		- The polarization mode used for a SAR instrument
		 *		- The estimated luminosity at the acquisition time for an Optical instrument
		 *
		 *	For certain missions, usually agile satellites, feasible segments are usually not known in
		 *	advance but the region of interest is instead divided in several smaller sub-areas called
		 *	cells. 
		 *	Each cell can then be covered by one or more possible segments (or attempts), but
		 *	the actual chosen/successful segment is not known with certainty at the time of the
		 *	feasibility study. 
		 *	In this case, the feasibility thus consists of the cells only, with the
		 *	following estimated properties:
		 *		- The cell identifier, unique within the feasibility study
		 *  	- The combination of platform, instrument and mode that are foreseen as being
		 * 	      used to cover this cell
		 *  	- The cell status which should always be “ESTIMATED” in the case of a feasibility
		 * 	      study
		 *  	- The earliest possible acquisition date
		 *   	- The estimated worst acquisition date (i.e. latest date at which the cell should be
		 *  	  covered and validated)
		 *   	- The number of remaining attempts, which should be the total number or possible
		 *  	  attempts (within the survey period) in the case of a feasibility study
		 * 	    - For advanced users, we also keep the possibility to include all possible accesses to
		 * 		  the cell, as a list of segments.
		 */

		TaskingResponseBean responseBean = new TaskingResponseBean();

		log.debug("\n" + ORIGINE + " : _responseDocument : " + _responseDocument.toString());
		log.debug("\n" + ORIGINE + " : ----------------------------------------------------------------------");

		// parsing de l'element <FeasibilityResponseType>
		GetFeasibilityResponseType responseType = null;
		if (_responseDocument != null) {
			responseType = _responseDocument.getGetFeasibilityResponse();
		} // 

		if (responseType != null) {
			Result result = responseType.getResult();
			if ((result != null) && (result.getStatusReport() != null)) {
				log.debug("\n" + ORIGINE + " : ------------------------- Result -------------------------");
				InputStream inputStream = result.newInputStream();

				if (inputStream != null) {
					responseBean = getTaskingResponse(_sensorId, _sensorType, _responseDocument.toString(),
							inputStream, _serverURL, result.getStatusReport(), Sps2GeneralConstants.SPS_GET_FEASIBILITY);
				} //
			} // if (result != null) {
		} // if (responseType != null) {

		log.debug("\n" + ORIGINE + " : ----------------------------------------------------------------------");	

		return responseBean;
	} // private TaskingResponseBean parseGetFeasibilityResponse(String _sensorId, 

	/**
	 * This method parse the response of a 'GetFeasibility' operation using the EO SPS Spot Library
	 * 
	 * @param _taskBean (TaskBean) 							: task informations
	 * @param _sensorType (String) 							: sensor type : "optical" or "radar"
	 * @param _responseDocument (GetStatusResponseDocument) : 'GetStatus' response
	 * @param _serverURL (String) 							: server URL
	 * 
	 * @return TaskingResponseBean : result of 'GetFeasibility' response parsing
	 * @throws Exception
	 */
	public TaskingResponseBean parseGetStatusResponse(
			TaskBean _taskBean, 
			String _sensorType, 
			GetStatusResponseDocument _responseDocument,
			String _serverURL) 
	throws Exception {

		/**
		 * The FeasibilityStudy class represents extended data included in Feasibility Results
		 * received via GetFeasibility.
		 * 
		 * The results of a feasibility study are composed of an overall feasibility assessment, plus a
		 * detailed feasibility, per cell or per segment, depending on the mission.
		 * In some cases, feasible segments can be estimated long in advance and be provided
		 * directly in the results (usually for deterministic missions, i.e. not agile). 
		 * For each segment, several properties can be specified:
		 *		- The segment identifier, unique within the feasibility study
		 *		- The combination of platform, instrument and mode used to acquire this segment
		 *		- The status which should always be “ESTIMATED” in the case of a feasibility
		 *		  study
		 *		- The Start and Stop acquisition times
		 *		- The acquisition angles, specified either as pointing or incidence angles along one
		 *		  or two directions
		 *		- Orbit information in terms of orbit, track and frame number according to the grid
		 *		  defined for the mission of interest.
		 *		- The ground footprint as a polygon geometry
		 *		- The preview URL is obviously out of scope in the case of a feasibility study
		 *		  Some properties specific to the type of instrument:
		 *		- The polarization mode used for a SAR instrument
		 *		- The estimated luminosity at the acquisition time for an Optical instrument
		 *
		 *	For certain missions, usually agile satellites, feasible segments are usually not known in
		 *	advance but the region of interest is instead divided in several smaller sub-areas called
		 *	cells. 
		 *	Each cell can then be covered by one or more possible segments (or attempts), but
		 *	the actual chosen/successful segment is not known with certainty at the time of the
		 *	feasibility study. 
		 *	In this case, the feasibility thus consists of the cells only, with the
		 *	following estimated properties:
		 *		- The cell identifier, unique within the feasibility study
		 *  	- The combination of platform, instrument and mode that are foreseen as being
		 * 	      used to cover this cell
		 *  	- The cell status which should always be “ESTIMATED” in the case of a feasibility
		 * 	      study
		 *  	- The earliest possible acquisition date
		 *   	- The estimated worst acquisition date (i.e. latest date at which the cell should be
		 *  	  covered and validated)
		 *   	- The number of remaining attempts, which should be the total number or possible
		 *  	  attempts (within the survey period) in the case of a feasibility study
		 * 	    - For advanced users, we also keep the possibility to include all possible accesses to
		 * 		  the cell, as a list of segments.
		 */

		TaskingResponseBean responseBean = new TaskingResponseBean();

		log.debug("\n" + ORIGINE + " : _responseDocument : " + _responseDocument.toString());
		log.debug("\n" + ORIGINE + " : ----------------------------------------------------------------------");

		// parsing de l'element <GetStatusResponseType>
		GetStatusResponseType responseType = null;
		if (_responseDocument != null) {
			responseType = _responseDocument.getGetStatusResponse();
			log.debug("\n" + ORIGINE + " : responseType : " + _responseDocument.toString());

			if ((responseType != null) && (responseType.getStatusArray() != null)) {
				if ((responseType.getStatusArray().length > 0)
						&& (responseType.getStatusArray(0).getStatusReport() != null)) {
					InputStream inputStream = responseType.newInputStream();
					log.debug("\n" + ORIGINE + " : ------------------------- Result -------------------------");

					try {
						responseBean = getTaskingResponse(_taskBean.getSensor(), _sensorType,
								_responseDocument.toString(), inputStream, _serverURL, responseType.getStatusArray(0)
										.getStatusReport(), _taskBean.getResponseType());
					} catch (Exception e) {
						log.info("\n" + ORIGINE + " : parseGetStatusResponse : Exception : " + e.getLocalizedMessage());
					}
				} //
			} //
		} //

		log.debug("\n" + ORIGINE + " : ----------------------------------------------------------------------");	

		return responseBean;
	} // private TaskingResponseBean parseGetStatusResponse(String _sensorId, 

	/**
	 * This method parse the response of a 'Submit' operation using the EO SPS Spot Library
	 * 
	 * @param _sensorId (String) 						: sensor identifier
	 * @param _sensorType (String) 						: sensor type : "optical" or "radar"
	 * @param _responseDocument (SubmitResponseDocument): 'Submit' response
	 * @param _serverURL (String) 	: server URL
	 * 
	 * @return TaskingResponseBean : result of 'Submit' response parsing
	 * @throws Exception
	 */
	public TaskingResponseBean parseSubmitResponse(
			String _sensorId, 
			String _sensorType, 
			SubmitResponseDocument _responseDocument,
			String _serverURL) 
	throws Exception {

		/**
		 * The Submit operation allows SPS clients to submit a tasking request. 
		 * The SPS shall perform an internal feasibility check for the intended task and 
		 * – if the task is feasible – shall perform the task.
		 */
		TaskingResponseBean responseBean = new TaskingResponseBean();

		log.debug("\n" + ORIGINE + " : _responseDocument : " + _responseDocument.toString());
		log.debug("\n" + ORIGINE + " : ----------------------------------------------------------------------");

		// parsing de l'element <SubmitResponseType>
		SubmitResponseType responseType = null;
		if (_responseDocument != null) {
			responseType = _responseDocument.getSubmitResponse();
		} // 

		if ((responseType != null) && (responseType.getResult() != null)) {
			Result result = responseType.getResult();

			if (result.getStatusReport() != null) {
				InputStream inputStream = result.newInputStream();
				log.debug("\n" + ORIGINE + " : ------------------------- Result -------------------------");

				responseBean = getTaskingResponse(_sensorId, _sensorType, _responseDocument.toString(), inputStream,
						_serverURL, result.getStatusReport(), Sps2GeneralConstants.SPS_SUBMIT);
			} //
		} //

		log.debug("\n" + ORIGINE + " : ----------------------------------------------------------------------");	

		return responseBean;
	} // private TaskingResponseBean parseSubmitResponse(String _sensorId, 

	/**
	 * This method parse the response of a 'Reserve' operation using the EO SPS Spot Library
	 * 
	 * @param _sensorId (String) 							: sensor identifier
	 * @param _sensorType (String) 							: sensor type : "optical" or "radar"
	 * @param _responseDocument (ReserveResponseDocument)	: 'Reserve' response
	 * @param _serverURL (String) 	: server URL
	 * 
	 * @return TaskingResponseBean : result of 'Reserve' response parsing
	 * @throws Exception
	 */
	public TaskingResponseBean parseReserveResponse(
			String _sensorId, 
			String _sensorType, 
			ReserveResponseDocument _responseDocument,
			String _serverURL) 
	throws Exception {

		/**
		 * The Reserve operation allows SPS clients to reserve a task. 
		 * Reserving a task, except that the task is NOT perfored until the client sends a
		 * Confirm request.
		 * The reserve operation is part of the TransactionalSensorPlanner interface.
		 * A reservation can be cancelled sending a Cancel request at any time.
		 */

		TaskingResponseBean responseBean = new TaskingResponseBean();

		log.debug("\n" + ORIGINE + " : _responseDocument : " + _responseDocument.toString());
		log.debug("\n" + ORIGINE + " : ----------------------------------------------------------------------");

		// parsing de l'element <ReserveResponseType>
		ReserveResponseType responseType = null;
		if (_responseDocument != null) {
			responseType = _responseDocument.getReserveResponse();
		} // 

		if ((responseType != null) && (responseType.getResult() != null)) {
			Result result = responseType.getResult();

			if (result.getStatusReport() != null) {
				InputStream inputStream = result.newInputStream();
				log.debug("\n" + ORIGINE + " : ------------------------- Result -------------------------");

				responseBean = getTaskingResponse(_sensorId, _sensorType, _responseDocument.toString(), inputStream,
						_serverURL, result.getStatusReport(), Sps2GeneralConstants.SPS_RESERVE);
			} //
		} //

		log.debug("\n" + ORIGINE + " : ----------------------------------------------------------------------");	

		return responseBean;
	} // private TaskingResponseBean parseReserveResponse(String _sensorId, 

	/**
	 * This method parse the response of a 'Validate' operation using the EO SPS Spot Library
	 * 
	 * @param _sensorId (String) 							: sensor identifier
	 * @param _sensorType (String) 							: sensor type : "optical" or "radar"
	 * @param _validateDocument (ValidateResponseDocument)	: 'Validate' response
	 * @param _serverURL (String) 	: server URL
	 * 
	 * @return TaskingResponseBean : result of 'Validate' response parsing
	 * @throws Exception
	 */
	public TaskingResponseBean parseValidateResponse(
			String _sensorId, 
			String _sensorType, 
			ValidateResponseDocument _validateDocument,
			String _serverURL) 
	throws Exception {

		/**
		 * The Validate operation allows SPS clients to validate a task.
		 * 
		 * Several acquisition attempts are sometimes necessary to obtain a satisfying result 
		 * (case of optical satellites on zones with cloudy tendency for example). 
		 * The Validate operation can be used by the customer to indicate to the server 
		 * that an acquisition is satisfactory and thus to stop collecting new images for this area.
		 */

		TaskingResponseBean responseBean = new TaskingResponseBean();

		log.debug("\n" + ORIGINE + " : _responseDocument : " + _validateDocument.toString());
		log.debug("\n" + ORIGINE + " : ----------------------------------------------------------------------");

		// parsing de l'element <ValidateResponseType>
		ValidateResponseType responseType = null;
		if (_validateDocument != null) {
			responseType = _validateDocument.getValidateResponse();
		} //

		if ( (responseType != null) && (responseType.getResult() != null) ) {
				StatusReportPropertyType result = responseType.getResult();

				if (result.getStatusReport() != null) {
					InputStream	inputStream = result.newInputStream();				 
					log.debug("\n" + ORIGINE + " : ------------------------- Result -------------------------");

					responseBean = 
						getTaskingResponse(_sensorId, _sensorType, _validateDocument.toString(), 
								inputStream, _serverURL, result.getStatusReport(), Sps2GeneralConstants.SPS_VALIDATE);
				} //
		} // 

		log.debug("\n" + ORIGINE + " : ----------------------------------------------------------------------");	

		return responseBean;
	} // private TaskingResponseBean parseValidateResponse(String _sensorId, 
	
	/**
	 * This method parse the response of a 'Update' operation using the EO SPS Spot Library
	 * 
	 * The Update operation allows SPS clients to update 
	 * a previously submitted/reserved and accepted task.
	 * 
	 * @param _sensorId (String) 						: sensor identifier
	 * @param _sensorType (String) 						: sensor type : "optical" or "radar"
	 * @param _responseDocument (UpdateResponseDocument): 'Update' response
	 * @param _serverURL (String) 	: server URL
	 * 
	 * @return TaskingResponseBean : result of 'Update' response parsing
	 * @throws Exception
	 */
	public TaskingResponseBean parseUpdateResponse(
			String _sensorId, 
			String _sensorType, 
			UpdateResponseDocument _responseDocument,
			String _serverURL) 
	throws Exception {

		/**
		 * The Update operation allows SPS clients to submit a tasking request. 
		 * The SPS shall perform an internal feasibility check for the intended task and 
		 * – if the task is feasible – shall perform the task.
		 */

		TaskingResponseBean responseBean = new TaskingResponseBean();

		log.debug("\n" + ORIGINE + " : _responseDocument : " + _responseDocument.toString());
		log.debug("\n" + ORIGINE + " : ----------------------------------------------------------------------");

		// parsing de l'element <UpdateResponseType>
		UpdateResponseType responseType = null;
		if (_responseDocument != null) {
			responseType = _responseDocument.getUpdateResponse();
		} // 

		if ((responseType != null) && (responseType.getResult() != null)) {
			Result result = responseType.getResult();

			if (result.getStatusReport() != null) {
				InputStream inputStream = result.newInputStream();
				log.debug("\n" + ORIGINE + " : ------------------------- Result -------------------------");

				responseBean = getTaskingResponse(_sensorId, _sensorType, _responseDocument.toString(), inputStream,
						_serverURL, result.getStatusReport(), Sps2GeneralConstants.SPS_UPDATE);
			} //
		} //

		log.debug("\n" + ORIGINE + " : ----------------------------------------------------------------------");	

		return responseBean;
	} // private TaskingResponseBean parseUpdateResponse(String _sensorId, 

	/**
	 * This method parse the response of a 'GetFeasibility', 'Submit', 'Reserve' or 'Update' operation using the EO SPS Spot Library
	 * 
	 * @param _sensorId (String) 			: sensor identifier
	 * @param _sensorType (String) 			: sensor type : "optical" or "radar"
	 * @param _responseDocument (String) 	: 'GetFeasibility' or 'Submit' response
	 * @param _inputStream (InputStream) 	: 
	 * @param _serverURL (String) 			: server URL
	 * @param _statusReportType 
	 * @param _responseType					: 'GetStatus', 'GetFeasibility', ... 
	 * 
	 * @return TaskingResponseBean : result of 'GetFeasibility', 'Submit', 'Reserve' or 'Update' response parsing
	 * @throws Exception
	 */
	public TaskingResponseBean getTaskingResponse(
			String _sensorId, 
			String _sensorType, 
			String _responseDocument,
			InputStream _inputStream,
			String _serverURL,
			StatusReportType _statusReportType,
			String _responseType) 
	throws Exception {

		/**
		 * The FeasibilityStudy class represents extended data included in Feasibility Results
		 * received via GetFeasibility.
		 * 
		 * The results of a feasibility study are composed of an overall feasibility assessment, plus a
		 * detailed feasibility, per cell or per segment, depending on the mission.
		 * In some cases, feasible segments can be estimated long in advance and be provided
		 * directly in the results (usually for deterministic missions, i.e. not agile). 
		 * For each segment, several properties can be specified:
		 *		- The segment identifier, unique within the feasibility study
		 *		- The combination of platform, instrument and mode used to acquire this segment
		 *		- The status which should always be “ESTIMATED” in the case of a feasibility
		 *		  study
		 *		- The Start and Stop acquisition times
		 *		- The acquisition angles, specified either as pointing or incidence angles along one
		 *		  or two directions
		 *		- Orbit information in terms of orbit, track and frame number according to the grid
		 *		  defined for the mission of interest.
		 *		- The ground footprint as a polygon geometry
		 *		- The preview URL is obviously out of scope in the case of a feasibility study
		 *		  Some properties specific to the type of instrument:
		 *		- The polarization mode used for a SAR instrument
		 *		- The estimated luminosity at the acquisition time for an Optical instrument
		 *
		 *	For certain missions, usually agile satellites, feasible segments are usually not known in
		 *	advance but the region of interest is instead divided in several smaller sub-areas called
		 *	cells. 
		 *	Each cell can then be covered by one or more possible segments (or attempts), but
		 *	the actual chosen/successful segment is not known with certainty at the time of the
		 *	feasibility study. 
		 *	In this case, the feasibility thus consists of the cells only, with the
		 *	following estimated properties:
		 *		- The cell identifier, unique within the feasibility study
		 *  	- The combination of platform, instrument and mode that are foreseen as being
		 * 	      used to cover this cell
		 *  	- The cell status which should always be “ESTIMATED” in the case of a feasibility
		 * 	      study
		 *  	- The earliest possible acquisition date
		 *   	- The estimated worst acquisition date (i.e. latest date at which the cell should be
		 *  	  covered and validated)
		 *   	- The number of remaining attempts, which should be the total number or possible
		 *  	  attempts (within the survey period) in the case of a feasibility study
		 * 	    - For advanced users, we also keep the possibility to include all possible accesses to
		 * 		  the cell, as a list of segments.
		 */

		String METHOD = "getTaskingResponse";
		log.debug("\n" + ORIGINE + " : " + METHOD + " : _responseDocument : " + _responseDocument);	

		TaskingResponseBean responseBean = new TaskingResponseBean();

		// parsing de l'element <FeasibilityResponseType>

		//Transformation du flux XML en instance d'objet Java (beans).
		BufferedInputStream bufferedInputStream = null;
		bufferedInputStream = new BufferedInputStream(_inputStream);

		SAXBuilder saxBuilder = new SAXBuilder();
		org.jdom.Document response = null;
		response = saxBuilder.build(bufferedInputStream);

		try {
			responseBean = 
				parseTaskingResponse(_sensorId, _sensorType, _responseDocument.toString(), 
						_serverURL, response, _statusReportType, _responseType);
		} catch (Exception e) {
			log.info("\n" + ORIGINE + " : getTaskingResponse : Exception : " + e.getLocalizedMessage());
		}

		log.debug("\n" + ORIGINE + " : " + METHOD + " : ----------------------------------------------------------------------");	

		return responseBean;
	} // public TaskingResponseBean getTaskingResponse(

	/**
	 * This method parse the response of a 'GetFeasibility' or 'Submit' operation using the EO SPS Spot Library
	 * 
	 * @param _sensorId (String) 			: sensor identifier
	 * @param _sensorType (String) 			: sensor type : "optical" or "radar"
	 * @param _responseDocument (String) 	: 'GetFeasibility', 'Submit', 'Reserve' or 'Update' response
	 * @param _serverURL (String) 			: server URL
	 * @param _response (org.jdom.Document) : XML document
	 * @param _statusReportType 
	 * @param _responseType					: 'GetStatus', 'GetFeasibility', ... 
	 * 
	 * @return TaskingResponseBean : result of 'GetFeasibility', 'Submit', 'Reserve' or 'Update' response parsing
	 * @throws Exception
	 */
	public TaskingResponseBean parseTaskingResponse(
			String _sensorId, 
			String _sensorType, 
			String _responseDocument,
			String _serverURL,
			org.jdom.Document _response,
			StatusReportType _statusReportType,
			String _responseType) 
	throws Exception {

		/**
		 * The FeasibilityStudy class represents extended data included in Feasibility Results
		 * received via GetFeasibility.
		 * 
		 * The results of a feasibility study are composed of an overall feasibility assessment, plus a
		 * detailed feasibility, per cell or per segment, depending on the mission.
		 * In some cases, feasible segments can be estimated long in advance and be provided
		 * directly in the results (usually for deterministic missions, i.e. not agile). 
		 * For each segment, several properties can be specified:
		 *		- The segment identifier, unique within the feasibility study
		 *		- The combination of platform, instrument and mode used to acquire this segment
		 *		- The status which should always be “ESTIMATED” in the case of a feasibility
		 *		  study
		 *		- The Start and Stop acquisition times
		 *		- The acquisition angles, specified either as pointing or incidence angles along one
		 *		  or two directions
		 *		- Orbit information in terms of orbit, track and frame number according to the grid
		 *		  defined for the mission of interest.
		 *		- The ground footprint as a polygon geometry
		 *		- The preview URL is obviously out of scope in the case of a feasibility study
		 *		  Some properties specific to the type of instrument:
		 *		- The polarization mode used for a SAR instrument
		 *		- The estimated luminosity at the acquisition time for an Optical instrument
		 *
		 *	For certain missions, usually agile satellites, feasible segments are usually not known in
		 *	advance but the region of interest is instead divided in several smaller sub-areas called
		 *	cells. 
		 *	Each cell can then be covered by one or more possible segments (or attempts), but
		 *	the actual chosen/successful segment is not known with certainty at the time of the
		 *	feasibility study. 
		 *	In this case, the feasibility thus consists of the cells only, with the
		 *	following estimated properties:
		 *		- The cell identifier, unique within the feasibility study
		 *  	- The combination of platform, instrument and mode that are foreseen as being
		 * 	      used to cover this cell
		 *  	- The cell status which should always be “ESTIMATED” in the case of a feasibility
		 * 	      study
		 *  	- The earliest possible acquisition date
		 *   	- The estimated worst acquisition date (i.e. latest date at which the cell should be
		 *  	  covered and validated)
		 *   	- The number of remaining attempts, which should be the total number or possible
		 *  	  attempts (within the survey period) in the case of a feasibility study
		 * 	    - For advanced users, we also keep the possibility to include all possible accesses to
		 * 		  the cell, as a list of segments.
		 */

		String METHOD = "parseTaskingResponse";

		TaskingResponseBean response = new TaskingResponseBean();
		// parsing de l'element <FeasibilityResponseType>
		//   <sps:StatusReport>
		//      <swes:name xmlns:swes="http://www.opengis.net/swes/2.0">CFI based Feasibility Study</swes:name>
		//      <swes:description xmlns:swes="http://www.opengis.net/swes/2.0">Task not feasible. The number of segments is 0.</swes:description>
		//      <swes:extension xmlns:swes="http://www.opengis.net/swes/2.0">
		//         <eot:FeasibilityStudy xmlns:eot="http://www.opengis.net/eosps/2.0">
		//            <eot:expirationDate>2010-08-27T10:06:17Z</eot:expirationDate>
		//            <eot:estimatedCost uom="http://www.opengis.net/def/unit/OGC/0/currency/EUR">0.0</eot:estimatedCost>
		//            <eot:successRate>0.0</eot:successRate>
		//         </eot:FeasibilityStudy>
		//      </swes:extension>
		//      <sps:task>http://www.deimos-space.com/sps/2010-08-26T10:06:16.585Z</sps:task>
		//      <sps:estimatedToC>2010-08-26T10:11:17Z</sps:estimatedToC>
		//      <sps:procedure>urn:ogc:object:feature:Sensor:deimos-space:HMA:OPT-2</sps:procedure>
		//      <sps:requestStatus>Rejected</sps:requestStatus>
		//      <sps:updateTime>2010-08-26T10:06:17.241Z</sps:updateTime>
		//   </sps:StatusReport>
		// or
		// parsing de l'element <eot:ProgrammingStatus>
		//  <sps:StatusReport>
		//    <swes:description xmlns:swes="http://www.opengis.net/swes/2.0">Task completed successfully</swes:description>
		//    <swes:name xmlns:swes="http://www.opengis.net/swes/2.0">CFI based Reserve Request</swes:name>
		//    <swes:extension xmlns:swes="http://www.opengis.net/swes/2.0">
		//      <eot:ProgrammingStatus xmlns:eot="http://www.opengis.net/eosps/2.0">
		//        <eot:percentCompletion>0.0</eot:percentCompletion>
		//         <eot:segment>
		//           <eot:Segment gml:id="SEG_001" xmlns:gml="http://www.opengis.net/gml/3.2">
		//            <eot:footprint>
		//              <gml:Polygon gml:id="FSEG_001" srsName="http://www.opengis.net/def/crs/EPSG/0/4326">
		//                <gml:exterior>
		//                  <gml:LinearRing>
		//                    <gml:posList>50.8345062344006 -12.400658887141887 51.2540124110108 -9.927103603649497 54.53817165004297 -11.316906663921491 54.09634967079408 -13.975145190835274 50.8345062344006 -12.400658887141887</gml:posList>
		//                  </gml:LinearRing>
		//                </gml:exterior>
		//              </gml:Polygon>
		//            </eot:footprint>
		//          </eot:Segment>
		//        </eot:segment>
		//       </eot:ProgrammingStatus>
		//    </swes:extension>
		//    <sps:task>http://www.deimos-space.com/sps/2011-01-20T10:16:19.143Z</sps:task>
		//    <sps:estimatedToC>2011-01-20T11:32:25Z</sps:estimatedToC>
		//    <sps:procedure>urn:ogc:object:feature:Sensor:deimos-space:HMA:SAR-2</sps:procedure>

		ParsingStatusReport parsing = new ParsingStatusReport();
		TaskStatusReportBean statusReportBean = 
			parsing.parseStatusReportResponse(_statusReportType);
		//		response = ParsingUtils.parseStatusReportHeader(_response);
		response.setStatusReport(statusReportBean);

		log.debug("\n" + ORIGINE + " : ----------------------------------------------------------------------");	

		// TODO
		// request or task status : FEASIBLE, ACCEPTED or RESERVED
		String statusCode = (response.getStatusReport().getStatusCode() != null) ? response.getStatusReport().getStatusCode():"";
		String requestStatus = (response.getStatusReport().getRequestStatus() != null) ? response.getStatusReport().getRequestStatus():"";

		if ( (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_FEASIBLE)) 
				|| (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_ACCEPTED))
				|| (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_RESERVED))
				|| (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_COMPLETED))
				|| (statusCode.equalsIgnoreCase(Sps2GeneralConstants.STATUS_FEASIBLE))
				|| (statusCode.equalsIgnoreCase(Sps2GeneralConstants.STATUS_ACCEPTED))
				|| (statusCode.equalsIgnoreCase(Sps2GeneralConstants.STATUS_COMPLETED))
				|| (statusCode.equalsIgnoreCase(Sps2GeneralConstants.STATUS_RESERVED)) ) {

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			try {
				//				out.write(_responseDocument.toString().getBytes());
				outputStream.write(_responseDocument.getBytes());
			} catch (Exception exception) {
				log.info("\n" + ORIGINE + " : " + METHOD + " : Exception : " + exception.getMessage());
			}

			log.debug("\n" + ORIGINE + " : ---------------------------- outputStream -------------------------");
			log.debug("\n" + ORIGINE + " : out2 : " + outputStream);
			log.debug("\n" + ORIGINE + " : ---------------------------- end outputStream -------------------------");

			// read response using DescribeTasking data definition
			//		FeasibilityReport feasibilityReport = new FeasibilityReport();

			// read back from buffer
			ByteArrayInputStream in = new ByteArrayInputStream(outputStream.toByteArray());
			in = new ByteArrayInputStream(outputStream.toByteArray());

			// read response using DescribeTasking data definition
			DOMHelper domHelper = new DOMHelper(in, false);

			TaskingResponse taskingResponse = null;
			ValidateResponse validateResponse = null;
			GetStatusResponse statusResponse = null;

			if (_responseType.equalsIgnoreCase(Sps2GeneralConstants.SPS_GET_STATUS)) {
				try {
					statusResponse = 
						utils.readStatusResponse(domHelper, domHelper.getRootElement(), "2.0");
				} catch (Exception exception) {
					log.info("\n " + ORIGINE + " : Exception : " + exception.getLocalizedMessage());
				}
			} else if (_responseType.equalsIgnoreCase(Sps2GeneralConstants.SPS_VALIDATE)) {
				try {
					ValidateResponseReaderV20 reader = new ValidateResponseReaderV20();
					validateResponse = reader.readXMLResponse(domHelper, domHelper.getRootElement());
//					StatusReport statusReport = validateResponse.getReport();
//				    Map<QName, Object> results = statusReport.getExtensions();
//					for (Entry<QName, Object> result : results.entrySet()) {
//						if (result instanceof ProgrammingStatus) {
//							ProgrammingStatus progStatus = (ProgrammingStatus) result.getValue();
//							List<Segment> segments = progStatus.getSegments();
//						}
//					} // 
				} catch (Exception exception) {
					log.info("\n " + ORIGINE + " : Exception : " + exception.getLocalizedMessage());
				}
			} else {
				try {
					taskingResponse = 
						utils.readTaskingResponse(domHelper, domHelper.getRootElement(), "2.0");
				} catch (Exception exception) {
					log.info("\n " + ORIGINE + " : Exception : " + exception.getLocalizedMessage());
				}
			} // if (_responseType.equalsIgnoreCase(Sps2GeneralConstants.SPS_GET_STATUS)) {

			// instantiate helpers
			boolean ok = false;
			if (taskingResponse != null) {
				ok = true;
			} else if (validateResponse != null) {
					ok = true;
			} else if ( (_responseType.equalsIgnoreCase(Sps2GeneralConstants.SPS_GET_STATUS)) && (statusResponse != null) ) {
				if (statusResponse.getReportList() != null) {
					if (statusResponse.getReportList().size() > 0) {
						ok = true;
					}
				} // if (statusResponse.getReportList() != null) {
			}  // if (taskingResponse != null) {
			
			if (ok == true) {
				EOReportHelper helper = null;
				EORadarReportHelper helperRadar = null;
				EOOpticalReportHelper helperOptical = null;
				if (_sensorType.equalsIgnoreCase(Sps2GeneralConstants.SENSOR_RADAR)) {
					if (taskingResponse != null) {
						helperRadar = new EORadarReportHelper(taskingResponse);
					} else if (validateResponse != null) {
						helperRadar = new EORadarReportHelper(validateResponse.getReport());
					} else {
						helperRadar = new EORadarReportHelper(statusResponse.getReportList().get(0));
					} // if (taskingResponse != null) {
					helper = (EOReportHelper) helperRadar;
				} else {
					if (taskingResponse != null) {
						helperOptical = new EOOpticalReportHelper(taskingResponse);
					} else if (validateResponse != null) {
						helperOptical = new EOOpticalReportHelper(validateResponse.getReport());
					} else {
						helperOptical = new EOOpticalReportHelper(statusResponse.getReportList().get(0));
					} // if (taskingResponse != null) {
					helper = (EOReportHelper) helperOptical;
				} // if (_sensorType().equalsIgnoreCase(Sps2GeneralConstants.SENSOR_RADAR)) {

				// estimated To Completed date and update date
				DateTimeFormat dateTimeFormat = new DateTimeFormat();
				dateTimeFormat.applyPattern(Sps2GeneralConstants.TIME_FORMAT);

				// estimated Cost
				try {
					double estimatedCost = helper.getEstimatedCost();
					response.getFeasibilityStudy().setEstimatedCost(estimatedCost);

					log.debug("\n" + ORIGINE + " : estimatedCost : " + estimatedCost);
				} catch (Exception exception) {
					log.info("\n" + ORIGINE + " - Exception : " + METHOD + " : estimatedCost : " + exception.getMessage());
				}

				// Estimated Success Date
				try {
					DateTime estimatedSuccessDate = helper.getEstimatedSuccessDate();
					if (estimatedSuccessDate != null) {
						String estimatedSuccessDateStr = dateTimeFormat.format(estimatedSuccessDate);
						response.getFeasibilityStudy().setEstimatedSuccessDate(estimatedSuccessDateStr);

						log.debug("\n" + ORIGINE + " : estimatedSuccessDate : " + estimatedSuccessDate.toString());
					}
				} catch (Exception exception) {
					log.info("\n" + ORIGINE + " - Exception : " + METHOD + " : estimatedSuccessDate : " + exception.getMessage());
				}

				// segments
				try {
					if (helper.hasSegments()) {
						// number of Segments
						int numSegments = 0;
						try {
							numSegments = helper.getNumSegments();

							log.debug("\n" + ORIGINE + " : numSegments : " + numSegments);
						} catch (Exception exception) {
							log.info("\n" + ORIGINE + " - Exception : " + METHOD + " : numSegments : " + exception.getMessage());
						}

						HashMap<String, SegmentBean> segments = new HashMap<String, SegmentBean>();

						if (numSegments > 0) {
							for (int s=0; s<numSegments; s++) {
								SegmentBean segment = new SegmentBean();
								helper.loadSegment(s);

								try {
									String id = helper.getID();
									segment.setId(id);
									log.debug("\n" + ORIGINE + " : id : " + id);
								} catch (Exception exception) {
									log.info("\n" + ORIGINE + " - Exception : " + METHOD + " : id : " + exception.getMessage());
								}

								try {
									@SuppressWarnings("deprecation")
									String stereoSegmentID = helper.getStereoSegmentID();
									segment.setStereoSegmentId(stereoSegmentID);
									log.debug("\n" + ORIGINE + " : stereoSegmentID : " + stereoSegmentID);
								} catch (Exception exception) {
									log.info("\n" + ORIGINE + " - Exception : " + METHOD + " : stereoSegmentID : " + exception.getMessage());
								}

								try {
									String platformID = helper.getPlatformID();
									segment.setPlatformId(platformID);
									log.debug("\n" + ORIGINE + " : platformID : " + platformID);
								} catch (Exception exception) {
									log.info("\n" + ORIGINE + " - Exception : " + METHOD + " : platformID : " + exception.getMessage());
								}

								try {
									String platformName = helper.getPlatformName();
									segment.setPlatformName(platformName);
									log.debug("\n" + ORIGINE + " : platformName : " + platformName);
								} catch (Exception exception) {
									log.info("\n" + ORIGINE + " - Exception : " + METHOD + " : platformName : " + exception.getMessage());
								}

								try {
									String instrumentID = helper.getInstrumentID();
									segment.setInstrumentId(instrumentID);
									log.debug("\n" + ORIGINE + " : instrumentID : " + instrumentID);
								} catch (Exception exception) {
									log.info("\n" + ORIGINE + " - Exception : " + METHOD + " : instrumentID : " + exception.getMessage());
								}

								try {
									String instrumentName = helper.getInstrumentName();
									segment.setInstrumentName(instrumentName);
									log.debug("\n" + ORIGINE + " : instrumentName : " + instrumentName);
								} catch (Exception exception) {
									log.info("\n" + ORIGINE + " - Exception : " + METHOD + " : instrumentName : " + exception.getMessage());
								}

								try {
									String instrumentMode = helper.getInstrumentMode();
									segment.setInstrumentMode(instrumentMode);
									log.debug("\n" + ORIGINE + " : instrumentMode : " + instrumentMode);
								} catch (Exception exception) {
									log.info("\n" + ORIGINE + " - Exception : " + METHOD + " : instrumentMode : " + exception.getMessage());
								}

								try {
									DateTime acquisitionStart = helper.getAcquisitionStart();

									SimpleDateFormat dateFormat = new SimpleDateFormat(Sps2GeneralConstants.TIME_FORMAT);
									segment.setAcquisitionStart(dateFormat.format(acquisitionStart));
									log.debug("\n" + ORIGINE + " : acquisitionStart : " + acquisitionStart.toString());
								} catch (Exception exception) {
									log.info("\n" + ORIGINE + " - Exception : " + METHOD + " : acquisitionStart : " + exception.getMessage());
								}

								try {
									DateTime acquisitionStop = helper.getAcquisitionStop();

									SimpleDateFormat dateFormat = new SimpleDateFormat(Sps2GeneralConstants.TIME_FORMAT);
									segment.setAcquisitionStop(dateFormat.format(acquisitionStop));
									log.debug("\n" + ORIGINE + " : acquisitionStop : " + acquisitionStop.toString());
								} catch (Exception exception) {
									log.info("\n" + ORIGINE + " - Exception : " + METHOD + " : acquisitionStop : " + exception.getMessage());
								}

								try {
									double elevationIncidence = helper.getElevationIncidence();
									segment.getAcquisitionAngleRange().getIncidenceAngleRange().setElevationIncidenceMin(elevationIncidence);
									segment.getAcquisitionAngleRange().getIncidenceAngleRange().setElevationIncidenceMax(elevationIncidence);
									log.debug("\n" + ORIGINE + " : elevationIncidence : " + elevationIncidence);
								} catch (Exception exception) {
									log.info("\n" + ORIGINE + " - Exception : " + METHOD + " : elevationIncidence : " + exception.getMessage());
								}

								try {
									@SuppressWarnings("deprecation")
									double azimuthIncidence = helper.getAzimuthIncidence();
									segment.getAcquisitionAngleRange().getIncidenceAngleRange().setAzimuthIncidenceMin(azimuthIncidence);
									segment.getAcquisitionAngleRange().getIncidenceAngleRange().setAzimuthIncidenceMax(azimuthIncidence);
									log.debug("\n" + ORIGINE + " : azimuthIncidence : " + azimuthIncidence);
								} catch (Exception exception) {
									log.info("\n" + ORIGINE + " - Exception : " + METHOD + " : azimuthIncidence : " + exception.getMessage());
								}
								try {
									double orbitNumber = helper.getOrbitNumber();
									segment.setOrbitNumber(orbitNumber);
									log.debug("\n" + ORIGINE + " : orbitNumber : " + orbitNumber);
								} catch (Exception exception) {
									log.info("\n" + ORIGINE + " : " + METHOD + " : orbitNumber : " + exception.getMessage());
								}

								try {
									double trackNumber = helper.getTrackNumber();
									segment.setTrackNumber(trackNumber);
									log.debug("\n" + ORIGINE + " : trackNumber : " + trackNumber);
								} catch (Exception exception) {
									log.info("\n" + ORIGINE + " - Exception : " + METHOD + " : trackNumber : " + exception.getMessage());
								}

								try {
									double frameNumber = helper.getFrameNumber();
									segment.setFrameNumber(frameNumber);
									log.debug("\n" + ORIGINE + " : frameNumber : " + frameNumber);
								} catch (Exception exception) {
									log.info("\n" + ORIGINE + " - Exception : " + METHOD + " : frameNumber : " + exception.getMessage());
								}

								// footPrint
								try {
									Polygon footPrint = helper.getFootprint();
									Map<Integer, PointBean> points = new HashMap<Integer, PointBean>();

									//						int numPoints = footPrint.getNumPoints();
									Coordinate[] coordinates = footPrint.getCoordinates();

									//	 <gml:Polygon xmlns:gml=\"http://www.opengis.net/gml\" srsName=\"EPSG:4326\">
									//		<gml:exterior>
									//			<gml:LinearRing srsName=\"EPSG:4326\">
									//				<gml:pos>35.81 -5.35</gml:pos>
									//				<gml:pos>35.60 -6.53</gml:pos> 
									//				<gml:pos>37.85 -7.16</gml:pos>
									//				<gml:pos>38.06 -5.95</gml:pos>
									//				<gml:pos>35.81 -5.35</gml:pos>
									//			</gml:LinearRing>
									//		</gml:exterior>
									//	</gml:Polygon>

									// construction de l'objet GML representant le polygone
									Namespace gmlNameSpace = Namespace.getNamespace("gml", "http://www.opengis.net/gml");
									Element polygon =  new Element("Polygon", gmlNameSpace);
									polygon.setAttribute("srsName", "EPSG:4326");
									Element exterior =  new Element("exterior", gmlNameSpace);
									Element linearRing = new Element("LinearRing", gmlNameSpace);
									Element[] posTab = new Element[coordinates.length];
									// -------------------------------------------------------

									for (int i=0; i<coordinates.length; i++) {
										int rang = i;

										// inversion of third and fourth point
										//								if (i == 2) {
										//									if (coordinates.length > 2) {
										//										rang = 3;
										//									}
										//								} else if (i == 3) {
										//									rang = 2;
										//								} // if
										PointBean point = new PointBean();
										point.setLatitude(coordinates[rang].y);
										point.setLongitude(coordinates[rang].x);

										points.put(i, point);

										// GML : <pos>
										posTab[i] = new Element("pos", gmlNameSpace);
										posTab[i].setText(point.getLatitude() + " " + point.getLongitude());

										linearRing.addContent(posTab[i]);
										// -------------------------------------------------------
									} // for (int i=0; i<coordinates.length; i++) {

									segment.setPoints(points);

									// polygon in GML format
									exterior.addContent(linearRing);
									polygon.addContent(exterior);
									try {
										//On utilise ici un affichage classique avec getPrettyFormat()
										XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
										segment.setPointsXML(sortie.outputString(polygon));
									} catch (Exception exception){
										log.info("\n" + ORIGINE + " : " + METHOD + " : segment.setPointsXML : Exception : " + exception.getMessage());    
									}
									// -------------------------------------------------------

									// segment status
									try {
//										String status = helper.getStatus(); // getStatus is deprecated in EO SPS 2.0.2
										String status = helper.getObjectStatus().toString();
										segment.setStatus(status);
										log.debug("\n" + ORIGINE + " : status : " + status);
									} catch (Exception exception) {
										log.info("\n" + ORIGINE + " - Exception : " + METHOD + " : status : " + exception.getMessage());
									}

									// segment preview URL
									try {
										@SuppressWarnings("deprecation")
										String previewUrl = helper.getPreviewUrl();
										segment.setPreviewUrl(previewUrl);
										log.debug("\n" + ORIGINE + " : previewUrl : " + previewUrl);
									} catch (Exception exception) {
										log.info("\n" + ORIGINE + " - Exception : " + METHOD + " : previewUrl : " + exception.getMessage());
									}

									segments.put(segment.getId(), segment);					
									log.debug("\n" + ORIGINE + " : footPrint : " + footPrint.toString());	
								} catch (Exception exception) {
									log.info("\n" + ORIGINE + " - Exception : " + METHOD + " : footPrint : " + exception.getMessage());
								}
							} // for (int s=0; s<numSegments; s++) {
						} // if (numSegments > 0) {

						// add segments to returned bean
						response.getFeasibilityStudy().setSegments(segments);
					} //if (helper.hasSegments()) {
				} catch (Exception exception) {
					log.info("\n" + ORIGINE + " - Exception : " + METHOD + " : footPrint : " + exception.getMessage());
				}
			} // if (ok == true) {
		} // if (responseBean.getStatusReport().getStatusCode().equalsIgnoreCase(Sps2GeneralConstants.STATUS_FEASIBLE)) {

		return response;
	} // private TaskingResponseBean parseTaskingResponse(String _sensorId,

} // class
