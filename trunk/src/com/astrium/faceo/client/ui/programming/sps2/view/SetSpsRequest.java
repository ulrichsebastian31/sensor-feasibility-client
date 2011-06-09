package com.astrium.faceo.client.ui.programming.sps2.view;

import java.util.Date;

import com.astrium.faceo.client.bean.programming.sps2.SensorBean;
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
import com.astrium.faceo.client.common.ConfigConstant;
import com.astrium.faceo.client.common.programming.sps2.Sps2GeneralConstants;
import com.google.gwt.i18n.client.DateTimeFormat;

/**
 * <B>HMA-Fo</B> <BR>
 * 
 * <P>
 * La classe SetSpsRequest 
 * </P>
 * </P>
 * 
 * @author GR
 * @version 1.0, le 19/09/2010
 */
public class SetSpsRequest {
	
	private SpsSettingsPanel settingsPanel;
	private SensorBean sensor;

	/**
	 * constructor
	 * 
	 * @param _settingsPanel (SpsSettingsPanel) : settings Panel
	 * @param _sensor (SensorBean) : sensor informations
	 */
	public SetSpsRequest(SpsSettingsPanel _settingsPanel, SensorBean _sensor) {

		settingsPanel = _settingsPanel;
		sensor = _sensor;

	} // public SetSpsRequest() {

	/**
	 * set the Programming Request for SPS GetFeasibility, Submit, Update operation
	 * 
	 * @return ProgrammingRequestBean : the request for SPS operation
	 * 
	 */
	public ProgrammingRequestBean setRequest() {

		// set Programming Request
		ProgrammingRequestBean programmingRequest = new ProgrammingRequestBean();

		//		programmingRequest.getSensor().setUrn(sensor.getUrn());
		//		programmingRequest.getSensor().setDescribeFile(sensorO.getDescribeFile());
		programmingRequest.setSensor(sensor);

		// user name
		programmingRequest.setUser(ConfigConstant.SPS_FACEO_USER);

		// request name
		programmingRequest.setName(settingsPanel.getNameField().getValueAsString());

		programmingRequest.setService(Sps2GeneralConstants.SPS2_SERVICE);

		programmingRequest.setVersion(Sps2GeneralConstants.SPS2_VERSION);

		programmingRequest.getTasking().setFeasibilityLevel(Sps2GeneralConstants.FEASIBILITY_LEVEL_FULL);

		SpsSensorSettingsPanel sensorPanel = 
			settingsPanel.getMultiSensorsSettingsPanel().getSpsSensorSettingsPanel(sensor.getId());

		// request identifier (for 'cancel', 'Update' operations on current task)
		if (sensorPanel != null) {
			programmingRequest.setIdentifier(sensorPanel.getIdField().getValueAsString());
		} // if (sensorPanel != null) {

		// ---------------------- Coverage Programmming Request -------------------------
		CoverageProgrammmingRequestBean coverageProgrammmingRequest = 
			new CoverageProgrammmingRequestBean();

		// ---------------------- set Region of Interest : AOI (polygon : rectangle) ----------------------
		RegionOfInterestBean regionOfInterest = new RegionOfInterestBean();

		regionOfInterest.setLeftLower(settingsPanel.getLeftLowerNumberField().getValue().doubleValue());
		regionOfInterest.setLeftUpper(settingsPanel.getLeftUpperNumberField().getValue().doubleValue());
		regionOfInterest.setRightLower(settingsPanel.getRightLowerNumberField().getValue().doubleValue());
		regionOfInterest.setRightUpper(settingsPanel.getRightUpperNumberField().getValue().doubleValue());

		coverageProgrammmingRequest.setRegionOfInterest(regionOfInterest);
		// ---------------------- end Region of Interest -------------------------

		// ---------------------- set Time of Interest : Period ----------------------
		TimeOfInterestBean timeOfInterest = new TimeOfInterestBean();

		timeOfInterest.setType(Sps2GeneralConstants.TIME_SURVEY_PERIOD);

		// "2010-06-01T00:00:00Z""
		DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat(Sps2GeneralConstants.TIME_FORMAT);
		Date startPeriodDate = settingsPanel.getStartPeriodDateField().getValue();
		String startPeriod = dateTimeFormat.format(startPeriodDate);
		Date endPeriodDate = settingsPanel.getEndPeriodDateField().getValue();
		String endPeriod = dateTimeFormat.format(endPeriodDate);
		SurveyPeriodBean surveyPeriod = new SurveyPeriodBean();
		surveyPeriod.setStartDate(startPeriodDate);
		surveyPeriod.setStrStartDate(startPeriod);
		surveyPeriod.setEndDate(endPeriodDate);
		surveyPeriod.setStrEndDate(endPeriod);
		timeOfInterest.setSurveyPeriod(surveyPeriod);

		coverageProgrammmingRequest.setTimeOfInterest(timeOfInterest);
		// ---------------------- end Period -------------------------

		if (sensorPanel != null) {
			TaskingParametersBean parameters = sensorPanel.getTaskingParameters();

			programmingRequest.getSensor().setType(parameters.getSensor().getType());

			// ---------------------- Validation parameters ----------------------
			ValidationParametersBean validationParameters = new ValidationParametersBean();

			if (parameters.getValidationParametersOPT()) {
				ValidationParametersOPTBean validationParametersOPT = new ValidationParametersOPTBean ();

				// Max Cloud Coverage
				if (parameters.getMaxCloudCoverage()) {
					validationParametersOPT.setMaxCloudCoverage(Double.valueOf(sensorPanel.getMaxCloudCoverage().getValueAsString()));
				} // if (parameters.getMaxCloudCoverage()) {

				// Max Snow Coverage
				if (parameters.getMaxSnowCoverage()) {
					validationParametersOPT.setMaxSnowCoverage(Double.valueOf(sensorPanel.getMaxSnowCoverage().getValueAsString()));
				} // if (parameters.getMaxSnowCoverage()) {

				// Haze Accepted
				if (parameters.getHazeAccepted()) {
					validationParametersOPT.setHazeAccepted(sensorPanel.getHazeAccepted().getValue());
				} // if (parameters.getHazeAccepted()) {

				// Sand Wind Accepted
				if (parameters.getSandWindAccepted()) {
					validationParametersOPT.setSandWindAccepted(sensorPanel.getSandWindAccepted().getValue());
				} // if (parameters.getSandWindAccepted()) {

				validationParameters.setValidationParametersOPT(validationParametersOPT);
			} // if (parameters.getValidationParametersOPT()) {

			if (parameters.getValidationParametersSAR()) {
				ValidationParametersSARBean validationParametersSAR = new ValidationParametersSARBean ();

				// Max Noise Level
				if (parameters.getMaxNoiseLevel()) {
					validationParametersSAR.setMaxNoiseLevel(Double.valueOf(sensorPanel.getMaxNoiseLevel().getValueAsString()));
				} // if (parameters.getMaxNoiseLevel()) {

				// Max Ambiguity Level
				if (parameters.getMaxAmbiguityLevel()) {
					validationParametersSAR.setMaxAmbiguityLevel(Double.valueOf(sensorPanel.getMaxAmbiguityLevel().getValueAsString()));
				} // if (parameters.getMaxAmbiguityLevel()) {

				validationParameters.setValidationParametersSAR(validationParametersSAR);
			} // if (parameters.getValidationParametersSAR()) {

			programmingRequest.getTasking().setValidationParameters(validationParameters);
			// ---------------------- end Validation Parameters -------------------------

			// ---------------------- QOS : Quality Of Service -------------------------
			if (parameters.getQualityOfService()) {
				QualityOfServiceBean qualityOfService = new QualityOfServiceBean();
				qualityOfService.setPriorityLevel(sensorPanel.getPriorityLevel().getValueAsString());
				programmingRequest.getTasking().setQualityOfService(qualityOfService);
			} // if (parameters.getQualityOfService()) {
			// ---------------------- end QOS : Quality Of Service -------------------------

			// ---------------------- Acquisition Parameters -------------------------

			// set Acquisition Type
			AcquisitionTypeBean acquisitionType = new AcquisitionTypeBean();

			if (parameters.getAcquisitionMonoscopic()) {
				acquisitionType.setType(Sps2GeneralConstants.ACQUISITION_TYPE_MONO);
			} else if (parameters.getAcquisitionStereoscopic()) {
				acquisitionType.setType(Sps2GeneralConstants.ACQUISITION_TYPE_STEREO);
			} else {
				acquisitionType.setType(Sps2GeneralConstants.ACQUISITION_TYPE_UNKOWN);
			}

			// set Type Monoscopic
			MonoscopicAcquisitionBean monoscopicAcquisition = new MonoscopicAcquisitionBean();

			// set Type Stereoscopic
			StereoscopicAcquisitionBean stereoscopicAcquisition = new StereoscopicAcquisitionBean();

			if (parameters.getCoverageType()) {
				monoscopicAcquisition.setCoverageType(sensorPanel.getCoverageType().getValueAsString());

				stereoscopicAcquisition.setCoverageType(sensorPanel.getCoverageType().getValueAsString());
			} // if (parameters.getCoverageType()) {

			//			stereoscopicAcquisition.setbHRatio(_bHRatio);
			//			stereoscopicAcquisition.setMaxCoupleDelay(_maxCoupleDelay);

			AcquisitionAngleRangeBean acquisitionAngleRange = new AcquisitionAngleRangeBean();

			// ---------------------- Incidence Angle Parameters -------------------------
			if ( (parameters.getAzimuth()) || (parameters.getElevation()) ) {
				IncidenceAngleRangeBean incidenceAngleRange = new IncidenceAngleRangeBean();

				// azimuth
				if (parameters.getAzimuth()) {
					incidenceAngleRange.setAzimuthIncidenceMin(Float.valueOf(sensorPanel.getAzimuthIncidenceAngleMin().getValueAsString()));
					incidenceAngleRange.setAzimuthIncidenceMax(Float.valueOf(sensorPanel.getAzimuthIncidenceAngleMax().getValueAsString()));
				} // if (parameters.getAzimuth()) {

				// elevation
				if (parameters.getElevation()) {
					incidenceAngleRange.setElevationIncidenceMin(Float.valueOf(sensorPanel.getElevationIncidenceAngleMin().getValueAsString()));
					incidenceAngleRange.setElevationIncidenceMax(Float.valueOf(sensorPanel.getElevationIncidenceAngleMax().getValueAsString()));
				} // if (parameters.getElevation()) {

				acquisitionAngleRange.setIncidenceAngleRange(incidenceAngleRange);
			} // if ( (parameters.getAzimuth()) || (parameters.getElevation()) ) {

			// ---------------------- Pointing Angle Parameters -------------------------
			if ( (parameters.getAlongTrack()) || (parameters.getAcrossTrack()) ) {
				PointingAngleRangeBean pointingAngleRange = new PointingAngleRangeBean();

				// Pointing Angle Along
				pointingAngleRange.setAlongTrackMin(Float.valueOf(sensorPanel.getAlongTrackAngleMin().getValueAsString()));
				pointingAngleRange.setAlongTrackMax(Float.valueOf(sensorPanel.getAlongTrackAngleMax().getValueAsString()));

				// Pointing Angle Across
				pointingAngleRange.setAcrosTrackMin(Float.valueOf(sensorPanel.getAcrossTrackAngleMin().getValueAsString()));
				pointingAngleRange.setAcrosTrackMax(Float.valueOf(sensorPanel.getAcrossTrackAngleMax().getValueAsString()));

				acquisitionAngleRange.setPointingAngleRange(pointingAngleRange);				
			} // if ( (parameters.getAlongTrack()) || (parameters.getAcrossTrack()) ) {

			// ---------------------- end Acquisition Angle -------------------------

			if (parameters.getAcquisitionMonoscopic()) {
				monoscopicAcquisition.setAcquisitionAngleRange(acquisitionAngleRange);

				acquisitionType.setMonoscopicAcquisition(monoscopicAcquisition);
			} else if (parameters.getAcquisitionStereoscopic()) {
				// TODO
				stereoscopicAcquisition.setAcquisitionAngle1(acquisitionAngleRange);
				//				stereoscopicAcquisition.setAcquisitionAngle2(acquisitionAngleRange);

				acquisitionType.setStereoscopicAcquisition(stereoscopicAcquisition);
			} // if

			// ---------------------- Acquisition parameters -------------------------

			if (parameters.getAcquisitionOpticalType()) {
				// OPTICAL : OPT acquisition parameters

				AcquisitionParametersOPTBean acquisitionParametersOPT = new AcquisitionParametersOPTBean();

				if (parameters.getFusionAccepted()) {
					acquisitionParametersOPT.setFusionAccepted(sensorPanel.getFusionAccepted().getValue());
				} // if (parameters.getFusionAccepted()) {

				if (parameters.getInstrumentMode()) {
					acquisitionParametersOPT.setInstrumentMode(sensorPanel.getAcquisitionInstrumentMode().getValueAsString());
				} // if (parameters.getInstrumentMode()) {

				if (parameters.getGroundResolution()) {
					acquisitionParametersOPT.setGroundResolutionMin(Double.valueOf(sensorPanel.getGroundResolutionMin().getValueAsString()));
					acquisitionParametersOPT.setGroundResolutionMax(Double.valueOf(sensorPanel.getGroundResolutionMax().getValueAsString()));
				} // if (parameters.getGroundResolution()) {

				if (parameters.getMinLuminosity()) {
					acquisitionParametersOPT.setMinLuminosity(Float.valueOf(sensorPanel.getMinLuminosity().getValueAsString()));
				} // if (parameters.getMinLuminosity()) {

				acquisitionType.setAcquisitionParametersOPT(acquisitionParametersOPT);
			} else if (parameters.getAcquisitionRadarType()) {
				// RADAR : SAR acquisition parameters

				AcquisitionParametersSARBean acquisitionParametersSAR = new AcquisitionParametersSARBean();

				if (parameters.getFusionAccepted()) {
					acquisitionParametersSAR.setFusionAccepted(sensorPanel.getFusionAccepted().getValue());
				} // if (parameters.getFusionAccepted()) {

				if (parameters.getInstrumentMode()) {
					acquisitionParametersSAR.setInstrumentMode(sensorPanel.getAcquisitionInstrumentMode().getValueAsString());
				} // if (parameters.getInstrumentMode()) {

				if (parameters.getGroundResolution()) {
					acquisitionParametersSAR.setGroundResolutionMin(Double.valueOf(sensorPanel.getGroundResolutionMin().getValueAsString()));
					acquisitionParametersSAR.setGroundResolutionMax(Double.valueOf(sensorPanel.getGroundResolutionMax().getValueAsString()));
				} // if (parameters.getGroundResolution()) {

				if (parameters.getPolarizationMode()) {
					acquisitionParametersSAR.setPolarizationMode(sensorPanel.getAcquisitionPolarizationMode().getValueAsString());
				} // if (parameters.getPolarizationMode()) {

				acquisitionType.setAcquisitionParametersSAR(acquisitionParametersSAR);
			} // if (parameters.getAcquisitionOpticalType()) {

			coverageProgrammmingRequest.setAcquisitionType(acquisitionType);
			// ---------------------- end Acquisition Type -------------------------

		} // if (sensorPanel != null) {

		programmingRequest.getTasking().setCoverageProgrammmingRequest(coverageProgrammmingRequest);
		// ---------------------- end Coverage Programmming Request -------------------------

		return programmingRequest;

	} // public ProgrammingRequestBean setRequest(SensorBean _sensor) {

} // class
