package com.astrium.faceo.client.common.programming.sps2;


//import
import java.util.HashMap;
import java.util.Map;

import com.astrium.faceo.client.bean.programming.sps2.SensorBean;

/**
 * <B>SITE FACEO</B> <BR>
 * 
 * <P>
 * Cette classe stocke des constantes 'client' non localisables
 * </P>
 * </P>
 * 
 * @author GR 
 * @version 1.0, le 04/05/2010
 **/
public class Sps2GeneralConstants {

	/** HMAFO properties file */
	static final public String HMAFO_PROPERTIES = "properties/faceo_hmafo";
	
	/** sensors description file */
	static final public String SENSORS_XML = "/resources/sensors.xml";

	//------------------------ Sps 2.0 Programming Panels --------------------------
	/** Satellites Programming panel id */
	//	static final public String SATELLITE_PROGRAMMING_PANEL_ID = "SatelliteSps2Panel";

	/** Sps 2.0 tasks panel id */
	static final public String PROGRAMMING_TASKS_PANEL_ID = "Sps2TasksPanel";

	/** Sps 2.0 settings panel id */
	static final public String PROGRAMMING_SETTINGS_PANEL_ID = "Sps2SettingsPanel";

	/** SPS sensors settings panel id */
	static final public String SENSOR_SETTINGS_PANEL = "SpsSensorSettingsPanel_";

	/**
	 * Map object with identifier
	 * liste des identifiants des objets repr&eacute;sentant les capteurs pour les op&eacute;rations SPS 2.0
	 */
	static final private Map<String, SensorBean> sensors = 
		new HashMap<String, SensorBean>();

	/** Segment Satus Codes : OGC 10-135 - 2010/06/11 v2.0 draft :
	 *  </br> </br>
	 * Segments are marked as potential when they are estimated in the context of a feasibility study.
	 *  </br> </br>
	 * POTENTIAL :
	 * Segments are marked as potential when they are estimated in the context 
	 * of a feasibility study.
	 *  </br> </br>
	 * PLANNED :
	 * The segment is planned for acquisition at the specified date. 
	 * This usually means that the segment has been entered in the satellite work plan 
	 * but has not yet been executed
	 *  </br> </br>
	 * ACQUIRED :
     * The segment was acquired by the on-board instrument and successfully downlinked 
     * to a ground station.
	 *  </br> </br>
	 * VALIDATED :
	 * The acquired segment was validated since it satisfied the validation criteria expressed 
	 * in the request (e.g. cloud cover was less than the maximum acceptable amount).
	 *  </br> </br>
	 * CANCELLED :
	 * The segment acquisition was cancelled by the requester.
	 *  </br> </br>
	 * REJECTED :
	 * An originally planned or potential segment acquisition was purposely cancelled 
	 * by the data provider because of a last minute conflict.
	 *  </br> </br>
	 * FAILED :
	 * The acquisition of a segment failed for an internal reason such 
	 * as a last minute conflict or a low image quality resulting 
	 * from bad downlink transmission.
	 */
	
	/** Grid Cell Satus Codes : OGC 10-135 - 2010/06/11 v2.0 draft :
	 *  </br> </br>
	 * State of coverage of this cell. 
	 * Descriptions of status codes are given in the table below. 
	 * The value shall be ‘POTENTIAL’ in the case of a feasibility study, 
	 * unless the cell cannot be covered at all in which case the status shall be ‘REJECTED’.
	 *  </br> </br>
	 * POTENTIAL :
	 * Cells are marked as potential when they are estimated in the context of a feasibility study.
     * </br> </br>
	 * PLANNED :
	 * At least one segment covering the cell has been planned. 
	 * This state shall be used when the first attempt to cover the cell is planned 
	 * or when additional attempts are planned after previous ones have failed 
	 * (i.e. previous image segments covering the cell did not pass the validation criteria such as cloud cover). 
	 * The nextAttemptDate shall be the forecasted acquisition date of the planned attempt 
	 * and the lastAttemptDate shall be the date of the previous (usually unsuccessful) attempt.
	 *  </br> </br>
	 * VALIDATED :
	 * The last acquired segment covering the cell has been validated. 
	 * This is usually a final state meaning that an acceptable image has been obtained 
	 * and that no more acquisition attempts need to be made to cover the cell.
	 *  </br> </br>
	 * CANCELLED :
	 * The cell has been cancelled by the client. 
	 * No more acquisition attempts will be made to cover it 
	 * so the nextAttemptDate field shall be omitted and the remainingAttempts field shall be 0.
	 *  </br> </br>
	 * REJECTED :
	 * The cell has been rejected by the programming system because it cannot be covered at all 
	 * before the end of the requested period. No more acquisition attempts can possibly cover it 
	 * so the nextAttemptDate field shall be omitted and the remainingAttempts field shall be 0.
	 *  </br> </br>
	 * ACQUIRED :
	 * Not used in grid cells.
	 *  </br> </br>
	 * FAILED :
	 * Not used in grid cells.
	 */
//	static final public String[] STATUS_CODES = {"POTENTIAL", "PLANNED", "VALIDATED", "CANCELLED", "REJECTED", "ACQUIRED", "FAILED"};
	
	/** 
	 * POTENTIAL : OGC 10-135 - 2010/06/11 v2.0 draft</br> </br>
	 * 
	 * Segment Status :
	 * Segments are marked as potential when they are estimated in the context 
	 * of a feasibility study.
	 *  </br> </br>
	 * Cell Status :
     * Cells are marked as potential when they are estimated in the context 
	 * of a feasibility study.
	 **/
	static final public String STATUS_POTENTIAL = "POTENTIAL";
	
	/** 
	 * PLANNED : OGC 10-135 - 2010/06/11 v2.0 draft</br> </br>
	 * 
	 * Segment Status :
	 * The segment is planned for acquisition at the specified date. 
	 * This usually means that the segment has been entered in the satellite work plan 
	 * but has not yet been executed
	 *  </br> </br>
	 * Cell Status :
	 * At least one segment covering the cell has been planned. 
	 * This state shall be used when the first attempt to cover the cell is planned 
	 * or when additional attempts are planned after previous ones have failed 
	 * (i.e. previous image segments covering the cell did not pass 
	 * the validation criteria such as cloud cover). 
	 * The nextAttemptDate shall be the forecasted acquisition date 
	 * of the planned attempt and the lastAttemptDate shall be the date 
	 * of the previous (usually unsuccessful) attempt.
	 */
	static final public String STATUS_PLANNED = "PLANNED";
	
	/** 
	 * VALIDATED : OGC 10-135 - 2010/06/11 v2.0 draft</br> </br>
	 * 
	 * Segment Status :
	 * The acquired segment was validated since it satisfied the validation criteria expressed 
	 * in the request (e.g. cloud cover was less than the maximum acceptable amount).
	 *  </br> </br>
	 * Cell Status :
	 * The last acquired segment covering the cell has been validated. 
	 * This is usually a final state meaning that an acceptable image has been obtained 
	 * and that no more acquisition attempts need to be made to cover the cell.
	 */
	static final public String STATUS_VALIDATED = "VALIDATED";
	
	/** 
	 * CANCELLED : OGC 10-135 - 2010/06/11 v2.0 draft</br> </br>
	 * 
	 * Segment Status :
	 * The segment acquisition was cancelled by the requester.
	 *  </br> </br>
	 * Cell Status :
	 * The cell has been cancelled by the client. 
	 * No more acquisition attempts will be made to cover it so 
	 * the nextAttemptDate field shall be omitted 
	 * and the remainingAttempts field shall be 0.
	 */
	static final public String STATUS_CANCELLED = "CANCELLED";
	
	/** 
	 * REJECTED : OGC 10-135 - 2010/06/11 v2.0 draft</br> </br>
	 * 
	 * Segment Status :
	 * An originally planned or potential segment acquisition was purposely cancelled 
	 * by the data provider because of a last minute conflict. 
	 *  </br> </br>
	 * Cell Status :
	 * The cell has been rejected by the programming system because 
	 * it cannot be covered at all before the end of the requested period. 
	 * No more acquisition attempts can possibly cover it so 
	 * the nextAttemptDate field shall be omitted 
	 * and the remainingAttempts field shall be 0.
	 **/
	static final public String STATUS_REJECTED = "REJECTED";
	
	/** 
	 * ACQUIRED : OGC 10-135 - 2010/06/11 v2.0 draft</br> </br>
	 * 
 	 * Segment Status :
     * The segment was acquired by the on-board instrument and successfully downlinked 
     * to a ground station.
     *  </br> </br>
	 * Cell Status :
	 * Not used in grid cells.
	 **/
	static final public String STATUS_ACQUIRED = "ACQUIRED";
	
	/** 
	 * FAILED : OGC 10-135 - 2010/06/11 v2.0 draft</br> </br>
	 * 
 	 * Segment Status :
	 * The acquisition of a segment failed for an internal reason such 
	 * as a last minute conflict or a low image quality resulting 
	 * from bad downlink transmission.
     *  </br> </br>
	 * Cell Status :
	 * Not used in grid cells.
	 **/
	static final public String STATUS_FAILED = "FAILED";
	/** FEASIBLE */
	static final public String STATUS_FEASIBLE = "FEASIBLE";
	/** FEASIBLE */
	static final public String STATUS_NOT_FEASIBLE = "NOT FEASIBLE";

	/** PENDING */
	static final public String STATUS_PENDING = "PENDING";
	/** */
	static final public String STATUS_ACCEPTED = "ACCEPTED";

	/** */
	static final public String STATUS_RESERVED = "RESERVED";
	/** */
	static final public String STATUS_CONFIRMED = "CONFIRMED";
	/** */
	static final public String STATUS_INEXECUTION = "INEXECUTION";
	/** */
	static final public String STATUS_COMPLETED = "COMPLETED";
	/** */
	static final public String STATUS_EXPIRED = "EXPIRED";
	/** */
	static final public String STATUS_NEW_DATA = "NEW_DATA";

	// -------------- end STAUS CODES -------------------

	/** sensor prefix id */
	static final public String SENSOR_RADIO_BUTTON = "sensorRadioButton_";

	/** sensor prefix id */
	static final public String SENSOR_RADIO_BUTTON_PREFIX_ID = "sensorRadioButtonPrefix_";

	/** Sps 2.0 solutions panel id */
	static final public String PROGRAMMING_SOLUTIONS_PANEL_ID = "Sps2SolutionsPanel";

	/** Sps 2.0 other operations panel id */
	static final public String PROGRAMMING_OTHER_OPERATIONS_PANEL_ID = "Sps2OthersOperationsPanel";

	/** ********* SPS 2.0 PROGRAMMING PANEL ******** */
	/** */
	static final public int PROGRAMMING_GRID_PANEL_NB_COLUMNS = 9;

	/** ************************************ */
	/*             SPS 2.0 Programming           */
	/** ************************************ */
	/** */
	static final public int SPS_TAB_TASKS = 0;
	/** */
	static final public int SPS_TAB_SETTINGS = 1;
	/** */
	static final public int SPS_TAB_RESULTS = 2;

	/** */
	static final public String TRUE = "true";
	/** */
	static final public String FALSE = "false";

	/** */
	static final public String GRID_PANEL_PROGRAMMING_TASKS = "gridPanelSps2Tasks";
	/** */
	static final public String GRID_PANEL_PROGRAMMING_SOLUTIONS = "gridPanelSps2Solutions";

	/** EO-SPS Service **/
	static final public String EOSPS_SERVICE = "EOSPS";
	/** SPS Service **/
	static final public String SPS2_SERVICE = "SPS";
	/** Version **/
	static final public String SPS2_VERSION = "2.0.0";

	/** Feasibility level **/
	/** */
	static final public String FEASIBILITY_LEVEL_FULL = "FULL";
	/** */
	static final public String FEASIBILITY_LEVEL_QUICK = "QUICK";
	/** ------------------------------------- **/

	/** sensor Type **/
	/** */
	static final public String SENSOR_OPTICAL = "optical";
	/** */
	static final public String SENSOR_RADAR = "radar";
	/** ------------------------------------- **/

	/** QOS : Quality Of Service **/
	/** */
	static final public String QOS_PRIORITY_HIGH = "HIGH";
	/** */
	static final public String QOS_PRIORITY_STANDARD = "STANDARD";
	/** ------------------------------------- **/

	/** Time **/
	static final public String TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
	/** Time Format for GetSensorAvailability **/
	static final public String REQUESTED_PERIOD_TIME_FORMAT = "yyyy-MM-dd";

	/** Time types**/
	static final public int TIME_SURVEY_PERIOD = 1;
	/** */
	static final public int TIME_TEMPORAL_SERIES = 2;
	/** ------------------------------------- **/

	/** Acquisition Type **/
	/** */
	static final public String ACQUISITION_TYPE_MONO = "MONO";
	/** */
	static final public String ACQUISITION_TYPE_STEREO = "STEREO";
	/** */
	static final public String ACQUISITION_TYPE_UNKOWN = "UNKOWN";
	/** ------------------------------------- **/

	/** ----------- Coverage Type ----------- **/

	/**
	 * MONOPASS: The region of interest must be covered by one or more
	 * segments acquired from the same orbit (some agile satellites can cover
	 * large zones even when satisfying this constraint).
	 */
	static final public String COVERAGE_TYPE_MONOPASS = "MONOPASS";

	/**
	 * MULTIPASS: The region of interest can be covered by using images
	 * extracted from several segments acquired at different dates provided
	 * that they are all acquired within the requested time period
	 */
	static final public String COVERAGE_TYPE_MULTIPASS = "MULTIPASS";

	/** 
	 * SINGLE_SWATH: The region of interest should be covered by a single
	 * segment.
	 */
	static final public String COVERAGE_TYPE_SINGLE_SWATH = "SINGLE_SWATH";
	/** ------------------------------------- **/

	/** ------------ Polarization Mode ----------- **/
	/** */
	static final public String POLARIZATION_HH = "HH";
	/** */
	static final public String POLARIZATION_VV = "VV";
	/** */
	static final public String POLARIZATION_HH_HV = "HH/HV";
	/** */
	static final public String POLARIZATION_VV_VH = "VV/VH";
	/** ------------------------------------- **/

	/** Instrument Mode  **/

	/** OPTICAL **/
	/** */
	static final public String INSTRUMENT_OPT_PANCHROMATIC = "PANCHROMATIC";
	/** */
	static final public String INSTRUMENT_OPT_MULTISPECTRAL = "MULTISPECTRAL";

	/** RADAR **/
	/** */
	static final public String INSTRUMENT_SAR_SM = "SM";
	/** */
	static final public String INSTRUMENT_SAR_EW = "EW";
	/** */
	static final public String INSTRUMENT_SAR_IW = "IW";
	/** */
	static final public String INSTRUMENT_SAR_WV = "WV";

	/** Resolutions **/

	/** */
	static final public String SPS_RESOLUTION_2_5 = "2.5";
	/** */
	static final public String SPS_RESOLUTION_5 = "5";
	/** */
	static final public String SPS_RESOLUTION_10 = "10";
	/** */
	static final public String SPS_RESOLUTION_20 = "20";

	/** ***** SPS 2.0 Operations **** */
	/** */
	static final public String SPS_DEFAULT = "default";
	/** */
	static final public String SPS_GET_FEASIBILITY = "GetFeasibility";
	/** */
	static final public String SPS_GET_STATUS = "GetStatus";
	/** */
	static final public String SPS_RESERVE = "Reserve";
	/** */
	static final public String SPS_CONFIRM = "Confirm";
	/** */
	static final public String SPS_CANCEL = "Cancel";
	/** */
	static final public String SPS_SUBMIT = "Submit";
	/** */
	static final public String SPS_UPDATE = "Update";
	/** */
	static final public String SPS_VALIDATE = "Validate";
	/** */
	static final public String SPS_GET_TASKS_LIST = "GetTasksList";
	/** */
	static final public String SPS_GET_TASK_RESULT = "GetTaskResult";
	/** */
	static final public String SPS_GET_UPDATED_TASKS_LIST = "GetUpdatedTasksList";
	/** */
	static final public String SPS_UPDATE_TASKS_STATUS = "UpdateTasksStatus";
	

	/** ***** Other Operations **** */
	/** */
	static final public String SPS_DELETE_TASK = "DeleteTask";
	/** */
	static final public String SPS_GET_TASK_STATUS = "GetTaskStatus";
	/** */
	static final public String SPS_DESCRIBE_TASKING = "DescribeTasking";
	/** */
	static final public String SPS_GET_CAPABILITIES = "GetCapabilities";
	/** */
	static final public String SPS_DESCRIBE_SENSOR = "DescribeSensor";
	/** */
	static final public String SPS_DESCRIBE_RESULT_ACCESS = "DescribeResultAccess";
	/** */
	static final public String SPS_GET_SENSOR_AVAILABILITY = "GetSensorAvailability";

	/** Grid Panel of the Request's results */
	static final public String SHOW_SEGMENT_ON_CARTOGRAPHY = "showSolutionOnCartography";

	/** ***** SPS 2.0 Controleurs **** */
	/** */
	static final public String GET_FEASIBILITY_CONTROLLER = "/getSps2FeasibilityController.srv";
	/** */
	static final public String GET_TASKS_LIST_CONTROLLER = "/getTasksListController.srv";
	/** */
	static final public String TASK_RESULT_CONTROLLER = "/getTaskResultController.srv";
	/** */
	static final public String TASK_STATUS_CONTROLLER = "/getTaskStatusController.srv";
	/** */
	static final public String DESCRIBE_TASKING_CONTROLLER = "/getDescribeTaskingController.srv";
	/** */
	static final public String DESCRIBE_SENSOR_CONTROLLER = "/getDescribeSensorController.srv";
	/** */
	static final public String GET_CAPABILITIES_CONTROLLER = "/getCapabilitiesServiceController.srv";
	/** */
	static final public String DESCRIBE_RESULT_ACCESS_CONTROLLER = "/getDescribeResultAccessController.srv";
	/** */
	static final public String SUBMIT_CONTROLLER = "/getSubmitController.srv";
	/** */
	static final public String RESERVE_CONTROLLER = "/getReserveTaskController.srv";
	/** */
	static final public String CONFIRM_TASK_CONTROLLER = "/getConfirmTaskController.srv";
	/** */
	static final public String CANCEL_TASK_CONTROLLER = "/getCancelTaskController.srv";
	/** */
	static final public String UPDATE_TASK_CONTROLLER = "/getUpdateTaskController.srv";
	/** */
	static final public String VALIDATE_TASK_CONTROLLER = "/getValidateTaskController.srv";
	/** */
	static final public String GET_UPDATED_TASKS_LIST_CONTROLLER = "/getUpdatedTasksListController.srv";
	/** */
	static final public String GET_SENSOR_AVAILABILITY_CONTROLLER = "/getSensorAvailabilityController.srv";

	/** ***** Other Controllers **** */
	/** */
	static final public String DELETE_TASK_CONTROLLER = "/deleteTaskController.srv";

	/** ***** SPS 2.0 Exceptions **** */

	/** Type for Describe Result Access : sensor or task describe */
	static final public int DESCRIBE_ACCESS_SENSOR = 1;
	/** */
	static final public int DESCRIBE_ACCESS_TASK = 2;
	
	/** */
	static final public int REQUEST_NOT_COMPLETED = 0;
	/** */
	static final public int REQUEST_COMPLETED = 1;
	
	/** getters */

	/** 
	 * getter on sensors
	 * 
	 * @return Map<String, SensorBean> : sensors informations (id, urn)
	*/
	public static Map<String, SensorBean> getSensors() {
		return sensors;
	}

} // class
