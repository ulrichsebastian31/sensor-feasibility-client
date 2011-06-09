package com.astrium.faceo.client.common.programming.sps2;

/*
 * @(#)Sps2Constants.java	 1.0  01/06/2010
 *
 *
 * PROJET       : FACEO
 *
 * LANGUAGE     : Java
 *
 * DESCRIPTION  : La classe ProgrammingConstants gere les constantes 'programming' du site
 *
 *
 * CREATION :
 * ---------------------------------------------------------------------
 * | Date       | Version | Description                                |
 * ---------------------------------------------------------------------
 * | 01/06/2010 |    1.0  |                                            |
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

import com.google.gwt.i18n.client.Constants;

/**
 * <B>SITE FACEO</B> <BR>
 * 
 * <P>
 * La classe Sps2Constants g&eagrave;re les constantes utilis&eacute;es par le site
 * </P>
 * </P>
 * 
 * @author GR
 * @version 1.0, le 01/06/2010
 */
public interface Sps2Constants extends Constants {
	
	/* ---------------------- Programming ---------------------- */
	/**
	 * Translated "Programming".
	 * 
	 * @return translated "Programming"
	*/
	@Key("title")
	String title();

	/**
	 * Translated "programming Settings".
	 * 
	 * @return translated "programming Settings"
	*/
	@Key("settings")
	String settings();

	/**
	 * Translated "request Parameters".
	 * 
	 * @return translated "request Parameters"
	*/
	@Key("requestParameters")
	String requestParameters();
	
	/**
	 * Translated "request pending".
	 * 
	 * @return translated "request pending"
	*/
	@Key("requestPending")
	String requestPending();

	/**
	 * Translated "Operation Selection".
	 * 
	 * @return translated "Operation Selection"
	*/
	@Key("operationSelection")
	String operationSelection();

	/**
	 * Translated "Others Operations".
	 * 
	 * @return translated "Others Operations"
	*/
	@Key("otherOperations")
	String otherOperations();
	
	/**
	 * Translated "task Name".
	 * 
	 * @return translated "task Name"
	*/
	@Key("taskName")
	String taskName();
	
	/**
	 * Translated "task name is mandatory".
	 * 
	 * @return translated "task name is mandatory"
	*/
	@Key("taskNameIsMandatory")
	String taskNameIsMandatory();

	/**
	 * Translated "Date".
	 * 
	 * @return translated "Date"
	*/
	@Key("fieldSet")
	String fieldSet();

	/**
	 * Translated "Requested Period (Only for GetSensorAvailability)".
	 * 
	 * @return translated "Requested Period (Only for GetSensorAvailability)"
	*/
	@Key("periodGetSensorAvailability")
	String periodGetSensorAvailability();

	/**
	 * Translated "Begin/End".
	 * 
	 * @return translated "Begin/End"
	*/
	@Key("period")
	String period();

	/**
	 * Translated "programming start period".
	 * 
	 * @return translated "programming start period"
	*/
	@Key("startPeriod")
	String startPeriod();

	/**
	 * Translated "programming end period".
	 * 
	 * @return translated "programming end period"
	*/
	@Key("endPeriod")
	String endPeriod();
	
	/**
	 * Translated "Open the calendar to change the start date of period".
	 * 
	 * @return translated "Open the calendar to change the start date of period"
	*/
	@Key("startPeriodDateFieldTip")
	String startPeriodDateFieldTip();
	
	/**
	 * Translated "Open the calendar to change the end date of period".
	 * 
	 * @return translated "Open the calendar to change the end date of period"
	*/
	@Key("endPeriodDateFieldTip")
	String endPeriodDateFieldTip();

	/**
	 * Translated "AOI".
	 * 
	 * @return translated "AOI"
	*/
	@Key("aoi")
	String aoi();
	
	/**
	 * Translated "Upper".
	 * 
	 * @return translated "Upper"
	*/
	@Key("aoiUpper")
	String aoiUpper();

	/**
	 * Translated "left upper".
	 * 
	 * @return translated "left upper"
	*/
	@Key("aoiLeftUpper")
	String aoiLeftUpper();
	
	/**
	 * Translated "right upper".
	 * 
	 * @return translated "right upper"
	*/
	@Key("aoiRightUpper")
	String aoiRightUpper();
	
	
	/**
	 * Translated "Lower".
	 * 
	 * @return translated "Lower"
	*/
	@Key("aoiLower")
	String aoiLower();
	
	/**
	 * Translated "left lower".
	 * 
	 * @return translated "left lower"
	*/
	@Key("aoiLeftLower")
	String aoiLeftLower();

	/**
	 * Translated "right lower".
	 * 
	 * @return translated "right lower"
	*/
	@Key("aoiRightLower")
	String aoiRightLower();

	/**
	 * Translated "Feasibility Level".
	 * 
	 * @return translated "Feasibility Level"
	*/
	@Key("feasibilityLevelRadioLabel")
	String feasibilityLevelRadioLabel();
	
	/**
	 * Translated "Quick".
	 * 
	 * @return translated "Quick"
	*/
	@Key("quickLevelRadioLabel")
	String quickLevelRadioLabel();
	
	/**
	 * Translated "Full".
	 * 
	 * @return translated "Full"
	*/
	@Key("fullLevelRadioLabel")
	String fullLevelRadioLabel();
	
	/**
	 * Translated "New AOI".
	 * 
	 * @return translated "New AOI"
	*/
	@Key("newAOI")
	String newAOI();
	
	/**
	 * Translated "Draw AOI".
	 * 
	 * @return translated "Draw AOI"
	*/
	@Key("drawAOI")
	String drawAOI();
	
	/**
	 * Translated "Display".
	 * 
	 * @return translated "Display"
	*/
	@Key("display")
	String display();
	
	/**
	 * Translated "current AOI".
	 * 
	 * @return translated "current AOI"
	*/
	@Key("currentAOI")
	String currentAOI();

	/**
	 * Translated "New area of interest".
	 * 
	 * @return translated "New area of interest"
	*/
	@Key("newAOITip")
	String newAOITip();
	
	/**
	 * Translated "Draw an area of interest".
	 * 
	 * @return translated "Draw an area of interest"
	*/
	@Key("drawAOITip")
	String drawAOITip();

	/**
	 * Translated "Sensor".
	 * 
	 * @return translated "Sensor"
	*/
	@Key("sensor")
	String sensor();

	/**
	 * Translated "Specific Parameters".
	 * 
	 * @return translated "Specific Parameters"
	*/
	@Key("specificParameters")
	String specificParameters();
	
	/**
	 * Translated "Sensors".
	 * 
	 * @return translated "Sensors"
	*/
	@Key("sensors")
	String sensors();
	
	/**
	 * Translated "Sensor(s) Selection".
	 * 
	 * @return translated "Sensor(s) Selection"
	*/
	@Key("sensorsSelection")
	String sensorsSelection();
	
	/**
	 * Translated "Quality Of Service parameters".
	 * 
	 * @return translated "Quality Of Service parameters"
	*/
	@Key("qosParameters")
	String qosParameters();
	
	/**
	 * Translated "Validation parameters".
	 * 
	 * @return translated "Validation parameters"
	*/
	@Key("validationParameters")
	String validationParameters();
	
	/**
	 * Translated "Acquisition parameters".
	 * 
	 * @return translated "Acquisition parameters"
	*/
	@Key("acquisitionParameters")
	String acquisitionParameters();
	
	/**
	 * Translated "Max Noise Level".
	 * 
	 * @return translated "Max Noise Level"
	*/
	@Key("maxNoiseLevel")
	String maxNoiseLevel();
	
	/**
	 * Translated "Max Ambiguity Level".
	 * 
	 * @return translated "Max Ambiguity Level"
	*/
	@Key("maxAmbiguityLevel")
	String maxAmbiguityLevel();

	/**
	 * Translated "Max Cloud Coverage".
	 * 
	 * @return translated "Max Cloud Coverage"
	*/
	@Key("maxCloudCoverage")
	String maxCloudCoverage();

	/**
	 * Translated "Max Snow Coverage".
	 * 
	 * @return translated "Max Snow Coverage"
	*/
	@Key("maxSnowCoverage")
	String maxSnowCoverage();

	/**
	 * Translated "Haze Accepted".
	 * 
	 * @return translated "Haze Accepted"
	*/
	@Key("hazeAccepted")
	String hazeAccepted();
	
	/**
	 * Translated "Sand Wind Accepted".
	 * 
	 * @return translated "Sand Wind Accepted"
	*/
	@Key("sandWindAccepted")
	String sandWindAccepted();

	/**
	 * Translated "Acquisition Mode".
	 * 
	 * @return translated "Acquisition Mode"
	*/
	@Key("acquisitionMode")
	String acquisitionMode();
	
	/**
	 * Translated "Fusion Accepted".
	 * 
	 * @return translated "Fusion Accepted"
	*/
	@Key("fusionAccepted")
	String fusionAccepted();
	
	/**
	 * Translated "Ground Resolution".
	 * 
	 * @return translated "Ground Resolution"
	*/
	@Key("groundResolution")
	String groundResolution();

	/**
	 * Translated "Min Luminosity".
	 * 
	 * @return translated "Min Luminosity"
	*/
	@Key("minLuminosity")
	String minLuminosity();

	/**
	 * Translated "Incidence Angle".
	 * 
	 * @return translated "Incidence Angle"
	*/
	@Key("incidenceAngle")
	String incidenceAngle();
	
	/**
	 * Translated "Elevation Incidence Angle".
	 * 
	 * @return translated "Elevation Incidence Angle"
	*/
	@Key("elevationIncidenceAngle")
	String elevationIncidenceAngle();
	
	/**
	 * Translated "Azimuth Incidence Angle".
	 * 
	 * @return translated "Azimuth Incidence Angle"
	*/
	@Key("azimuthIncidenceAngle")
	String azimuthIncidenceAngle();
	
	/**
	 * Translated "Pointing Angle".
	 * 
	 * @return translated "Pointing Angle"
	*/
	@Key("pointingAngle")
	String pointingAngle();
	
	/**
	 * Translated "Along Track Angle".
	 * 
	 * @return translated "Along Track Angle"
	*/
	@Key("alongTrack")
	String alongTrack();
	
	/**
	 * Translated "Across Track Angle".
	 * 
	 * @return translated "Across Track Angle"
	*/
	@Key("acrossTrack")
	String acrossTrack();
	
	/**
	 * Translated "Choose At Least One Sensor".
	 * 
	 * @return translated "Choose At Least One Sensor"
	*/
	@Key("chooseAtLeastOneSensor")
	String chooseAtLeastOneSensor();
	
	/**
	 * Translated "Angle Max must be greater than Angle Min".
	 * 
	 * @return translated "Angle Max must be greater than Angle Min"
	*/
	@Key("angleMaxInvalid")
	String angleMaxInvalid();
	
	/**
	 * Translated "Start period must be in the future".
	 * 
	 * @return translated "Start period must be in the future"
	*/
	@Key("startPeriodMustBeInTheFuture")
	String startPeriodMustBeInTheFuture();
	
	/**
	 * Translated "End period must be in the future".
	 * 
	 * @return translated "End period must be in the future"
	*/
	@Key("endPeriodMustBeInTheFuture")
	String endPeriodMustBeInTheFuture();
	
	/**
	 * Translated "End period must be > Start period".
	 * 
	 * @return translated "End period must be > Start period"
	*/
	@Key("badStartPeriod")
	String badStartPeriod();

	/**
	 * Translated "Submit Request".
	 * 
	 * @return translated "Submit Request"
	*/
	@Key("submitRequest")
	String submitRequest();
	
	/**
	 * Translated "Activate Sensors".
	 * 
	 * @return translated "Activate Sensors"
	*/
	@Key("activateSensors")
	String activateSensors();
	
	/**
	 * Translated "Activate Sensors Tip".
	 * 
	 * @return translated "Activate Sensors Tip"
	*/
	@Key("activateSensorsTip")
	String activateSensorsTip();
	
	/**
	 * Translated "Cancel Request".
	 * 
	 * @return translated "Cancel Request"
	*/
	@Key("cancelRequest")
	String cancelRequest();	
	
	/**
	 * Translated "on Success".
	 * 
	 * @return translated "on Success"
	*/
	@Key("submitOnSuccess")
	String submitOnSuccess();
	
	/**
	 * Translated "task".
	 * 
	 * @return translated "task"
	*/
	@Key("task")
	String task();
	
	/**
	 * Translated "Not feasible".
	 * 
	 * @return translated "Not feasible"
	*/
	@Key("notFeasible")
	String notFeasible();
	
	/**
	 * Translated "rejected".
	 * 
	 * @return translated "rejected"
	*/
	@Key("rejected")
	String rejected();
	
	/**
	 * Translated "failed".
	 * 
	 * @return translated "failed"
	*/
	@Key("failed")
	String failed();
	
	/**
	 * Translated "pending".
	 * 
	 * @return translated "pending"
	*/
	@Key("pending")
	String pending();
	
	/**
	 * Translated "expired".
	 * 
	 * @return translated "expired"
	*/
	@Key("expired")
	String expired();
	
	/**
	 * Translated "cancelled".
	 * 
	 * @return translated "cancelled"
	*/
	@Key("cancelled")
	String cancelled();
	
	/**
	 * Translated "reserved".
	 * 
	 * @return translated "reserved"
	*/
	@Key("reserved")
	String reserved();
	
	/**
	 * Translated "planned".
	 * 
	 * @return translated "planned"
	*/
	@Key("planned")
	String planned();
	
	/**
	 * Translated "potential".
	 * 
	 * @return translated "potential"
	*/
	@Key("potential")
	String potential();
	
	/**
	 * Translated "validated".
	 * 
	 * @return translated "validated"
	*/
	@Key("validated")
	String validated();
	
	/**
	 * Translated "acquired".
	 * 
	 * @return translated "acquired"
	*/
	@Key("acquired")
	String acquired();
	
	/**
	 * Translated "on Failure".
	 * 
	 * @return translated "on Failure"
	*/
	@Key("submitOnFailure")
	String submitOnFailure();
	
	/**
	 * Translated "no result".
	 * 
	 * @return translated "no result"
	*/
	@Key("submitOnSuccessNoResult")
	String submitOnSuccessNoResult();
	
	/* ---------------------- Task Solutions ---------------------- */
	/**
	 * Translated "Solutions".
	 * 
	 * @return translated "Solutions"
	*/
	@Key("solutions")
	String solutions();

	/**
	 * Translated "Show All Segments".
	 * 
	 * @return translated "Show All Segments"
	*/
	@Key("displayAllMeshes")
	String displayAllMeshes();
	
	/**
	 * Translated "Hide All Segments".
	 * 
	 * @return translated "Hide All Segments"
	*/
	@Key("hideAllMeshes")
	String hideAllMeshes();

	/**
	 * Translated "Solutions".
	 * 
	 * @return translated "Solutions"
	*/
	@Key("displayMsgSolutions")
	String displayMsgSolutions();

	/**
	 * Translated "No solution to display".
	 * 
	 * @return translated "No solution to display"
	*/
	@Key("noSolutionToDisplayMsg")
	String noSolutionToDisplayMsg();

	/**
	 * Translated "Show Preview".
	 * 
	 * @return translated "Show Preview"
	*/
	@Key("solutionShowPreview")
	String solutionShowPreview();

	/**
	 * Translated "Scenes".
	 * 
	 * @return translated "Scenes"
	*/
	@Key("solutionScenes")
	String solutionScenes();

	/**
	 * Translated "Start Time - Stop Time".
	 * 
	 * @return translated "Start Time - Stop Time"
	*/
	@Key("solutionStartTimeStopTime")
	String solutionStartTimeStopTime();

	/* ---------------------- Tasks ---------------------- */
	
	/**
	 * Translated "Task Request pending".
	 * 
	 * @return translated "Tasks Request pending"
	*/
	@Key("tasksRequestPending")
	String tasksRequestPending();
	
	/**
	 * Translated "programming Tasks".
	 * 
	 * @return translated "programming Tasks"
	*/
	@Key("tasksTitle")
	String tasksTitle();
	
	/**
	 * Translated "Operation".
	 * 
	 * @return translated "Operation"
	*/
	@Key("taskOperation")
	String taskOperation();
	
	/**
	 * Translated "Show results".
	 * 
	 * @return translated "Show results"
	*/
	@Key("taskShowResults")
	String taskShowResults();
	
	/**
	 * Translated "Status".
	 * 
	 * @return translated "Status"
	*/
	@Key("taskStatus")
	String taskStatus();
	
	/**
	 * Translated "Status Details".
	 * 
	 * @return translated "Status Details"
	*/
	@Key("taskStatusDetails")
	String taskStatusDetails();
	
	/**
	 * Translated "Cancel".
	 * 
	 * @return translated "Cancel"
	*/
	@Key("taskCancel")
	String taskCancel();
	
	/**
	 * Translated "Confirm".
	 * 
	 * @return translated "Confirm"
	*/
	@Key("taskConfirm")
	String taskConfirm();
	
	/**
	 * Translated "Validate".
	 * 
	 * @return translated "Validate"
	*/
	@Key("taskValidate")
	String taskValidate();
	
	/**
	 * Translated "Results Access".
	 * 
	 * @return translated "Results Access"
	*/
	@Key("taskResultAccess")
	String taskResultAccess();
	
	/**
	 * Translated "Created Time".
	 * 
	 * @return translated "Created Time"
	*/
	@Key("taskCreatedTime")
	String taskCreatedTime();
	
	/**
	 * Translated "Last Updated Time".
	 * 
	 * @return translated "Last Updated Time"
	*/
	@Key("taskLastUpdatedTime")
	String taskLastUpdatedTime();
	
	/**
	 * Translated "Delete".
	 * 
	 * @return translated "Delete"
	*/
	@Key("taskDelete")
	String taskDelete();
	
	/**
	 * Translated "Updated".
	 * 
	 * @return translated "Updated"
	*/
	@Key("taskUpdated")
	String taskUpdated();
	
	/**
	 * Translated "Display Msg".
	 * 
	 * @return translated "Display Msg"
	*/
	@Key("tasksDisplayMsg")
	String tasksDisplayMsg();
	
	/**
	 * Translated "No Task Msg".
	 * 
	 * @return translated "No Task Msg"
	*/
	@Key("tasksNoTaskMsg")
	String tasksNoTaskMsg();
	
	/**
	 * Translated "Show Preview".
	 * 
	 * @return translated "Show Preview"
	*/
	@Key("taskShowPreview")
	String taskShowPreview();

	/**
	 * Translated "Show solutions".
	 * 
	 * @return translated "Show solutions"
	*/
	@Key("taskShowSolutions")
	String taskShowSolutions();

	/**
	 * Translated "Task window".
	 * 
	 * @return translated "Task window"
	*/
	@Key("taskWindow")
	String taskWindow();
	
	/**
	 * Translated "Task information".
	 * 
	 * @return translated "Task information"
	*/
	@Key("taskInformation")
	String taskInformation();
	
	/**
	 * Translated "Task Feasibility Request".
	 * 
	 * @return translated "Task Feasibility Request"
	*/
	@Key("taskFeasibilityRequest")
	String taskFeasibilityRequest();
	
	/**
	 * Translated "Id".
	 * 
	 * @return translated "Id"
	*/
	@Key("taskIdentifier")
	String taskIdentifier();
	
	/**
	 * Translated "Identifier must be defined".
	 * 
	 * @return translated "Identifier must be defined"
	*/
	@Key("taskIdentifierMustBeDefined")
	String taskIdentifierMustBeDefined();

	/**
	 * Translated "Level".
	 * 
	 * @return translated "Level"
	*/
	@Key("taskLevelFeasibilityRequest")
	String taskLevelFeasibilityRequest();
	
	/**
	 * Translated "Task Feasibility Response".
	 * 
	 * @return translated "Task Feasibility Response"
	*/
	@Key("taskFeasibilityResponse")
	String taskFeasibilityResponse();

	/**
	 * Translated "Id".
	 * 
	 * @return translated "Id"
	*/
	@Key("taskIdFeasibilityResponse")
	String taskIdFeasibilityResponse();
	
	/**
	 * Translated "Mission id".
	 * 
	 * @return translated "Mission id"
	*/
	@Key("taskMissionIdFeasibilityResponse")
	String taskMissionIdFeasibilityResponse();

	/**
	 * Translated "Achievement Date".
	 * 
	 * @return translated "Achievement Date"
	*/
	@Key("taskAchievDateFeasResp")
	String taskAchievDateFeasResp();
	
	/**
	 * Translated "Geo Location".
	 * 
	 * @return translated "Geo Location"
	*/
	@Key("taskGeoLocationFeasResp")
	String taskGeoLocationFeasResp();

	/**
	 * Translated "Satellite".
	 * 
	 * @return translated "Satellite"
	*/
	@Key("taskSatelliteFeasResp")
	String taskSatelliteFeasResp();

	/**
	 * Translated "Sensor Id".
	 * 
	 * @return translated "Sensor Id"
	*/
	@Key("taskSensorIdFeasResp")
	String taskSensorIdFeasResp();

	/**
	 * Translated "Resolution".
	 * 
	 * @return translated "Resolution"
	*/
	@Key("taskResolutionFeasResp")
	String taskResolutionFeasResp();

	/**
	 * Translated "Success Rate".
	 * 
	 * @return translated "Success Rate"
	*/
	@Key("taskSuccessRateFeasResp")
	String taskSuccessRateFeasResp();

	/**
	 * Translated "Mission Programming Request Id".
	 * 
	 * @return translated "Mission Programming Request Id"
	*/
	@Key("missionProgrammingRequestId")
	String missionProgrammingRequestId();

	/**
	 * Translated "Mission Involved".
	 * 
	 * @return translated "Mission Involved"
	*/
	@Key("missionInvolved")
	String missionInvolved();

	/**
	 * Translated "Solution Descriptor".
	 * 
	 * @return translated "Solution Descriptor"
	*/
	@Key("solutionDescriptor")
	String solutionDescriptor();

	/**
	 * Translated "Update".
	 * 
	 * @return translated "Update"
	*/
	@Key("tasksListSubmitRequest")
	String tasksListSubmitRequest();
	
	/**
	 * Translated "Update Tasks list".
	 * 
	 * @return translated "Update getTasksList list"
	*/
	@Key("tasksListSubmitRequestTip")
	String tasksListSubmitRequestTip();
	
	/**
	 * Translated "on Failure".
	 * 
	 * @return translated "on Failure"
	*/
	@Key("tasksListSubmitOnFailure")
	String tasksListSubmitOnFailure();
	
	/**
	 * Translated "on Success".
	 * 
	 * @return translated "on Success"
	*/
	@Key("tasksListSubmitOnSuccess")
	String tasksListSubmitOnSuccess();
	
	/**
	 * Translated "Completed status for task ".
	 * 
	 * @return translated "Completed status for task "
	*/
	@Key("taskCompletedStatus")
	String taskCompletedStatus();
	
	/**
	 * Translated "Failed status for task ".
	 * 
	 * @return translated "Failed status for task "
	*/
	@Key("taskFailedStatus")
	String taskFailedStatus();
	
	/* ---------------------- Grid of Meshes ---------------------- */
	/**
	 * Translated "Show".
	 * 
	 * @return translated "Show"
	*/
	@Key("meshesShow")
	String meshesShow();

	/**
	 * Translated "Id".
	 * 
	 * @return translated "Id"
	*/
	@Key("mesheId")
	String mesheId();

	/**
	 * Translated "Status".
	 * 
	 * @return translated "Status"
	*/
	@Key("mesheStatus")
	String mesheStatus();

	/**
	 * Translated "Mission".
	 * 
	 * @return translated "Mission"
	*/
	@Key("meshesMission")
	String meshesMission();

	/**
	 * Translated "Resolution".
	 * 
	 * @return translated "Resolution"
	*/
	@Key("meshesResolution")
	String meshesResolution();

	/**
	 * Translated "Instrument Mode".
	 * 
	 * @return translated "Instrument Mode"
	*/
	@Key("meshesInstrumentMode")
	String meshesInstrumentMode();

	/**
	 * Translated "Geolocation".
	 * 
	 * @return translated "Geolocation"
	*/
	@Key("meshesGeoLocation")
	String meshesGeoLocation();

} // class
