package com.astrium.faceo.server.service.programming.sps2;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import net.opengis.www.eosps._2_0.ValidateDocument;
import net.opengis.www.eosps._2_0.ValidateResponseDocument;
import net.opengis.www.eosps._2_0.ValidateType;
import net.opengis.www.ows._1_1.ExceptionReportDocument.ExceptionReport;
import net.opengis.www.ows._1_1.ExceptionType;
import net.opengis.www.sps._2_0.CancelDocument;
import net.opengis.www.sps._2_0.CancelResponseDocument;
import net.opengis.www.sps._2_0.CancelType;
import net.opengis.www.sps._2_0.ConfirmResponseDocument;
import net.opengis.www.sps._2_0.ConfirmResponseType.Result;
import net.opengis.www.sps._2_0.ConfirmType;
import net.opengis.www.sps._2_0.GetFeasibilityDocument;
import net.opengis.www.sps._2_0.GetFeasibilityResponseDocument;
import net.opengis.www.sps._2_0.GetStatusDocument;
import net.opengis.www.sps._2_0.GetStatusResponseDocument;
import net.opengis.www.sps._2_0.GetStatusType;
import net.opengis.www.sps._2_0.ReserveDocument;
import net.opengis.www.sps._2_0.ReserveResponseDocument;
import net.opengis.www.sps._2_0.SubmitDocument;
import net.opengis.www.sps._2_0.SubmitResponseDocument;
import net.opengis.www.sps._2_0.TaskingRequestDocument;
import net.opengis.www.sps._2_0.UpdateDocument;
import net.opengis.www.sps._2_0.UpdateResponseDocument;

import org.apache.axis2.AxisFault;
import org.apache.log4j.Logger;
import org.vast.cdm.common.DataEncoding;
import org.vast.cdm.common.XmlEncoding;
import org.vast.ows.sps.DescribeTaskingResponse;
import org.vast.ows.sps.GetFeasibilityRequest;
import org.vast.ows.sps.ReserveRequest;
import org.vast.ows.sps.SPSUtils;
import org.vast.ows.sps.SubmitRequest;
import org.vast.ows.sps.TaskingRequest;
import org.vast.ows.sps.UpdateRequest;
import org.vast.util.DateTimeFormat;

import com.astrium.faceo.client.bean.programming.sps2.ErrorBean;
import com.astrium.faceo.client.bean.programming.sps2.TaskBean;
import com.astrium.faceo.client.bean.programming.sps2.TaskStatusReportBean;
import com.astrium.faceo.client.bean.programming.sps2.cancel.CancelRequestBean;
import com.astrium.faceo.client.bean.programming.sps2.cancel.CancelResponseBean;
import com.astrium.faceo.client.bean.programming.sps2.confirm.ConfirmRequestBean;
import com.astrium.faceo.client.bean.programming.sps2.confirm.ConfirmResponseBean;
import com.astrium.faceo.client.bean.programming.sps2.getStatus.GetStatusRequestBean;
import com.astrium.faceo.client.bean.programming.sps2.getStatus.GetStatusResponseBean;
import com.astrium.faceo.client.bean.programming.sps2.request.ProgrammingRequestBean;
import com.astrium.faceo.client.bean.programming.sps2.responses.TaskingResponseBean;
import com.astrium.faceo.client.bean.programming.sps2.validate.ValidateRequestBean;
import com.astrium.faceo.client.bean.programming.sps2.validate.ValidateResponseBean;
import com.astrium.faceo.client.common.programming.sps2.Sps2GeneralConstants;
import com.astrium.faceo.server.bean.exception.TaskException;
import com.astrium.faceo.server.common.exception.sps2.FacadeSps2Exception;
import com.astrium.faceo.server.service.programming.sps2.parsing.ParsingCancel;
import com.astrium.faceo.server.service.programming.sps2.parsing.ParsingGetStatus;
import com.astrium.faceo.server.service.programming.sps2.parsing.ParsingStatusReport;
import com.astrium.faceo.server.service.programming.sps2.parsing.ParsingTasking;
import com.astrium.faceo.server.service.programming.sps2.parsing.ParsingUtils;
import com.astrium.faceo.server.service.programming.sps2.parsing.ParsingValidate;
import com.spotimage.eosps.EOConstants;
import com.spotimage.eosps.EOConstants.AcquisitionType;
import com.spotimage.eosps.EOConstants.CoverageType;
import com.spotimage.eosps.EOConstants.ToiType;
import com.spotimage.eosps.EOOpticalParamHelper;
import com.spotimage.eosps.EOParamHelper;
import com.spotimage.eosps.EORadarParamHelper;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;

/**
 * <B>SITE FACEO</B> <BR>
 * 
 * <P>
 * La classe FacadeTaskRequests reprend les services utilis&eacute;s pendant le 
 * processus de lecture des r&eacute;ponses aux op&eacute;tions SPS 2.0
 * </P>
 * </P>
 * 
 * @author GR
 * @version 1.0, le 12/05/2010
 */
public class FacadeTaskRequests {

	/** Le logger de cette classe */
	private static Logger log = Logger.getLogger(FacadeTaskRequests.class);

	private static String ORIGINE = "FacadeTaskRequests";

	/**
	 * constructor
	 */
	public FacadeTaskRequests() {    
	}

	/**
	 * Construction of the SPS 'Submit' request
	 * 
	 * @param _requestBean (ProgrammingRequestBean) : 'Submit' operation parameters
	 * @param _serverURL (String) 					: server URL
	 * 
	 * @return SubmitDocument : XML document that represents the XML request for the SPS operation 'Submit'
	 * @throws FacadeSps2Exception
	 */
	private net.opengis.www.sps._2_0.SubmitDocument getSubmitRequest(
			ProgrammingRequestBean _requestBean, String _serverURL) 
	throws FacadeSps2Exception {
		try {
			net.opengis.www.sps._2_0.SubmitDocument submitDocument = 
				net.opengis.www.sps._2_0.SubmitDocument.Factory.newInstance();

			// create 'Submit' request with EO SPS Library (Spot Image : EO Application profile for SPS 2.0)
			SubmitRequest submitRequest = new SubmitRequest();
			submitRequest.setSensorID(_requestBean.getSensor().getUrn());
			submitRequest.setVersion(_requestBean.getVersion());
			submitRequest.setService(Sps2GeneralConstants.SPS2_SERVICE);
			submitRequest.setOperation(Sps2GeneralConstants.SPS_SUBMIT);

			submitDocument = (SubmitDocument) getTaskingRequest(
					Sps2GeneralConstants.SPS_SUBMIT, submitRequest, _requestBean, _serverURL);

			return submitDocument;

		} catch (Exception exception) {
			log.info("\n" + ORIGINE + " Exception : " + exception.getMessage());

			return null;
		} 
	} // private SubmitDocument getSubmitRequest(ProgrammingRequestBean _requestBean,

	/**
	 * Construction of the SPS 'Reserve' request
	 * 
	 * @param _requestBean (ProgrammingRequestBean) : 'Reserve' operation parameters
	 * @param _serverURL (String) 					: server URL
	 * 
	 * @return ReserveDocument : XML document that represents the XML request for the SPS operation 'Reserve'
	 * @throws FacadeSps2Exception
	 */
	private net.opengis.www.sps._2_0.ReserveDocument getReserveRequest(
			ProgrammingRequestBean _requestBean, String _serverURL) 
	throws FacadeSps2Exception {

		try {
			net.opengis.www.sps._2_0.ReserveDocument reserveDocument = 
				net.opengis.www.sps._2_0.ReserveDocument.Factory.newInstance();

			// sample request
			/*		      <Reserve version="?" service="?">
		         <procedure>?</procedure>
		         <taskingParameters>
		            <ParameterData>
		               <encoding>
		                  <AbstractEncoding/>
		               </encoding>
		               <values>?</values>
		            </ParameterData>
		         </taskingParameters>
		      </Reserve>*/

			// create 'Reserve' request with EO SPS Library (Spot Image : EO Application profile for SPS 2.0)
			ReserveRequest reserveRequest = new ReserveRequest();
			reserveRequest.setSensorID(_requestBean.getSensor().getUrn());
			reserveRequest.setVersion(_requestBean.getVersion());
			reserveRequest.setService(Sps2GeneralConstants.SPS2_SERVICE);
			reserveRequest.setOperation(Sps2GeneralConstants.SPS_RESERVE);

			reserveDocument = (ReserveDocument) getTaskingRequest(
					Sps2GeneralConstants.SPS_RESERVE, reserveRequest, _requestBean, _serverURL);

			return reserveDocument;

		} catch (Exception exception) {
			log.info("\n" + ORIGINE + " Exception : " + exception.getMessage());

			return null;
		} 
	} // private ReserveDocument getReserveRequest(ProgrammingRequestBean _requestBean,

	/**
	 * Construction of the SPS 'Confirm' request
	 * 
	 * @param _requestBean (ConfirmRequestBean) : 'Confirm' operation parameters
	 * @param _serverURL (String) 				: server URL
	 * 
	 * @return ReserveDocument : XML document that represents the XML request for the SPS operation 'Confirm'
	 * @throws FacadeSps2Exception
	 */
	private net.opengis.www.sps._2_0.ConfirmDocument getConfirmRequest(
			ConfirmRequestBean _requestBean, String _serverURL) 
	throws FacadeSps2Exception {

		try {
			net.opengis.www.sps._2_0.ConfirmDocument confirmDocument = 
				net.opengis.www.sps._2_0.ConfirmDocument.Factory.newInstance();

			// sample request
			/*		      <Confirm version="?" service="?">
		         <task>?</task>
		      </Confirm>*/

			// create 'Confirm' request with EO SPS Library (Spot Image : EO Application profile for SPS 2.0)
			//			ConfirmRequest confirmRequest = new ConfirmRequest();
			//			confirmRequest.setVersion(_requestBean.getVersion());
			//			confirmRequest.setService(Sps2GeneralConstants.SPS2_SERVICE);
			//			confirmRequest.setTaskID(confirmRequest.getTaskID());

			ConfirmType confirmType = confirmDocument.addNewConfirm();
			confirmType.setService(Sps2GeneralConstants.SPS2_SERVICE);
			confirmType.setVersion(Sps2GeneralConstants.SPS2_VERSION);
			confirmType.setTask(_requestBean.getTaskId());

			return confirmDocument;

		} catch (Exception exception) {
			log.info("\n" + ORIGINE + " Exception : " + exception.getMessage());

			return null;
		} 
	} // private ConfirmDocument getConfirmRequest(ConfirmRequestBean _requestBean,

	/**
	 * Construction of the SPS 'Update' request
	 * 
	 * @param _requestBean (ProgrammingRequestBean) : 'Update' operation parameters
	 * @param _serverURL (String) 					: server URL
	 * @param taskBean (TaskBean)					: task (to update) informations
	 * 
	 * @return UpdateDocument : XML document that represents the XML request for the SPS operation 'Update'
	 * @throws FacadeSps2Exception
	 */
	private net.opengis.www.sps._2_0.UpdateDocument getUpdateRequest(
			ProgrammingRequestBean _requestBean, String _serverURL, TaskBean _taskBean) 
	throws FacadeSps2Exception {

		try {
			net.opengis.www.sps._2_0.UpdateDocument updateDocument = 
				net.opengis.www.sps._2_0.UpdateDocument.Factory.newInstance();

			// sample request
			/*		      <Update version="2.0" service="SPS">
		         <procedure>?</procedure>
		         <taskingParameters>
		            <ParameterData>
		               <encoding>
		                  <AbstractEncoding/>
		               </encoding>
		               <values>?</values>
		            </ParameterData>
		         </taskingParameters>
		         <task>?</task>
		      </Update>*/

			// create 'Update' request with EO SPS Library (Spot Image : EO Application profile for SPS 2.0)
			UpdateRequest updateRequest = new UpdateRequest();
			updateRequest.setSensorID(_requestBean.getSensor().getUrn());
			updateRequest.setVersion(_requestBean.getVersion());
			updateRequest.setTaskID(_requestBean.getIdentifier());
			updateRequest.setService(Sps2GeneralConstants.SPS2_SERVICE);
			updateRequest.setOperation(Sps2GeneralConstants.SPS_UPDATE);

			updateDocument = (UpdateDocument) getTaskingRequest(
					_taskBean.getOperation(), updateRequest, _requestBean, _serverURL);

			//			UpdateType updateType = updateDocument.addNewUpdate();
			//			updateType.setProcedure(_requestBean.getSensor().getUrn());
			//			updateType.setVersion(_requestBean.getVersion());
			//			updateType.setTask(_requestBean.getIdentifier());
			//			updateType.setService(Sps2GeneralConstants.SPS2_SERVICE);

			return updateDocument;
		} catch (Exception exception) {
			log.info("\n" + ORIGINE + " Exception : " + exception.getMessage());
			return null;
		} 
	} // private UpdateDocument getUpdateRequest(ProgrammingRequestBean _requestBean,

	/**
	 * Construction of the SPS 'GetFeasibility' request
	 * 
	 * @param _requestBean (ProgrammingRequestBean) : 'GetFeasibility' operation parameters
	 * @param _serverURL (String) 					: server URL
	 * 
	 * @return GetFeasibilityDocument : XML document that represents the XML request for the SPS operation 'GetFeasibility'
	 * @throws FacadeSps2Exception
	 */
	private net.opengis.www.sps._2_0.GetFeasibilityDocument getGetFeasibilityRequest(
			ProgrammingRequestBean _requestBean, String _serverURL) 
	throws FacadeSps2Exception {
		try {
			net.opengis.www.sps._2_0.GetFeasibilityDocument getFeasibilityDocument = 
				net.opengis.www.sps._2_0.GetFeasibilityDocument.Factory.newInstance();

			// create 'GetFeasibility' request with EO SPS Library (Spot Image : EO Application profile for SPS 2.0)
			GetFeasibilityRequest getFeasibilityRequest = new GetFeasibilityRequest();
			getFeasibilityRequest.setSensorID(_requestBean.getSensor().getUrn());
			getFeasibilityRequest.setVersion(_requestBean.getVersion());
			getFeasibilityRequest.setService(Sps2GeneralConstants.SPS2_SERVICE);
			getFeasibilityRequest.setOperation(Sps2GeneralConstants.SPS_GET_FEASIBILITY);

			getFeasibilityDocument = (GetFeasibilityDocument) getTaskingRequest(
					Sps2GeneralConstants.SPS_GET_FEASIBILITY, getFeasibilityRequest, _requestBean, _serverURL);

			return getFeasibilityDocument;

		} catch (Exception exception) {
			log.info("\n" + ORIGINE + " Exception : " + exception.getMessage());

			return null;
		} 
	} // private GetFeasibilityDocument getGetFeasibilityRequest(ProgrammingRequestBean _requestBean

	/**
	 * Construction of the SPS 'GetFeasibility', 'Submit', 'Reserve' or 'Update' request
	 * 
	 * @param _operation (String) 					: 'GetFeasibility', 'Submit', 'Reserve' or 'Update' operation
	 * @param _taskingRequest (TaskingRequest) 		: tasking request
	 * @param _requestBean (ProgrammingRequestBean) : 'GetFeasibility', 'Submit', 'Reserve' or 'Update' operation parameters
	 * @param _serverURL (String) 					: server URL
	 * 
	 * @return TaskingRequestDocument : XML document that represents the XML request 
	 * for the SPS operation 'GetFeasibility', 'Submit', 'Reserve' or 'Update'
	 * @throws FacadeSps2Exception
	 */
	private net.opengis.www.sps._2_0.TaskingRequestDocument getTaskingRequest(
			String _operation,
			TaskingRequest _taskingRequest,
			ProgrammingRequestBean _requestBean, 
			String _serverURL) 
	throws FacadeSps2Exception {

		// tasking request sample
		/*		<sps:taskingParameters>
				<sps:ParameterData>
					<sps:encoding>
						<swe:XMLEncoding namespace="http://www.opengis.net/eosps/2.0"/>
					</sps:encoding>
					<sps:values>
						<eop:CoverageProgrammingRequest>
		          				<eop:QualityOfService>
		            					<eop:PriorityLevel>HIGH</eop:PriorityLevel>
		          				</eop:QualityOfService>
							<eop:RegionOfInterest>
								<eop:Polygon>
									<eop:Exterior elementCount="5">
										<eop:Point>
											<eop:Lat>60.0</eop:Lat>
											<eop:Lon>60.0</eop:Lon>
										</eop:Point>
										<eop:Point>
											<eop:Lat>61.0</eop:Lat>
											<eop:Lon>60.0</eop:Lon>
										</eop:Point>
										<eop:Point>
											<eop:Lat>61.0</eop:Lat>
											<eop:Lon>61.0</eop:Lon>
										</eop:Point>
										<eop:Point>
											<eop:Lat>60.0</eop:Lat>
											<eop:Lon>61.0</eop:Lon>
										</eop:Point>
										<eop:Point>
											<eop:Lat>60.0</eop:Lat>
											<eop:Lon>60.0</eop:Lon>
										</eop:Point>
									</eop:Exterior>
								</eop:Polygon>
							</eop:RegionOfInterest>
							<eop:TimeOfInterest>
								<eop:SurveyPeriod>
									<eop:min>2010-10-04T00:00:00Z</eop:min>
									<eop:max>2010-11-14T00:00:00Z</eop:max>
								</eop:SurveyPeriod>
							</eop:TimeOfInterest>
							<eop:AcquisitionType>
								<eop:MonoscopicAcquisition>
									<eop:CoverageType>MULTIPASS</eop:CoverageType>
									<eop:AcquisitionAngle>
					                			<eop:AzimuthAngle>
		                  							<eop:min>-180.0</eop:min>
		                  							<eop:max>180.0</eop:max>
		               							</eop:AzimuthAngle>
										<eop:ElevationAngle>
											<eop:min>5.0</eop:min>
											<eop:max>31.0</eop:max>
										</eop:ElevationAngle>
									</eop:AcquisitionAngle>
									<eop:AcquisitionParameters>
		                						<eop:GroundResolution>
		                  							<eop:min>2.5</eop:min>
		                  							<eop:max>20.0</eop:max>
		                						</eop:GroundResolution>
		                						<eop:InstrumentMode>PANCHROMATIC</eop:InstrumentMode>
		                						<eop:FusionAccepted>false</eop:FusionAccepted>
		              						</eop:AcquisitionParameters>
								</eop:MonoscopicAcquisition>>					
							</eop:AcquisitionType>
		          				<eop:ValidationParameters>
		            					<eop:MaxCloudCover>85.0</eop:MaxCloudCover>
		            					<eop:MaxSnowCover>100.0</eop:MaxSnowCover>
		            					<eop:HazeAccepted>true</eop:HazeAccepted>
		            					<eop:SandWindAccepted>true</eop:SandWindAccepted>
		          				</eop:ValidationParameters>
						</eop:CoverageProgrammingRequest>
					</sps:values>
				</sps:ParameterData>
			</sps:taskingParameters>*/

		try {
			net.opengis.www.sps._2_0.TaskingRequestDocument taskingRequestDocument = 
				net.opengis.www.sps._2_0.TaskingRequestDocument.Factory.newInstance();

			// read tasking params
			String describeFile = 
				ParsingUtils.getDescribeParametersXmlFile(_requestBean.getSensor().getUrn(), _serverURL);
			DescribeTaskingResponse describeTaskingResponse = 
				ParsingUtils.readDescribeTaskingResponse(describeFile, _serverURL);

			// instantiate helpers
			EOParamHelper helper = null;
			EORadarParamHelper helperRadar = null;
			EOOpticalParamHelper helperOptical = null;
			if (_requestBean.getSensor().getType().equalsIgnoreCase(Sps2GeneralConstants.SENSOR_RADAR)) {
				helperRadar = new EORadarParamHelper(_taskingRequest, describeTaskingResponse.getTaskingParameters());
				helper = (EOParamHelper) helperRadar;
			} else {
				helperOptical = new EOOpticalParamHelper(_taskingRequest, describeTaskingResponse.getTaskingParameters());
				helper = (EOParamHelper) helperOptical;
			} // if (_requestBean.getSensor().getType().equalsIgnoreCase(Sps2GeneralConstants.SENSOR_RADAR)) {

			// feasibility level
			if (_requestBean.getTasking().getFeasibilityLevel().equalsIgnoreCase(Sps2GeneralConstants.FEASIBILITY_LEVEL_FULL)) {
				helper.setFeasibilityLevel(com.spotimage.eosps.EOConstants.FeasibilityLevel.COMPLETE);
			} else if (_requestBean.getTasking().getFeasibilityLevel().equalsIgnoreCase(Sps2GeneralConstants.FEASIBILITY_LEVEL_QUICK)) {
				helper.setFeasibilityLevel(com.spotimage.eosps.EOConstants.FeasibilityLevel.SIMPLE);
			}

			// QoS : Quality Of Service
			helper.setPriorityLevel(_requestBean.getTasking().getQualityOfService().getPriorityLevel());

			// ---------------- TOI : Time Of Interest ----------------
			if (_requestBean.getTasking().getCoverageProgrammmingRequest().getTimeOfInterest().getType() == Sps2GeneralConstants.TIME_SURVEY_PERIOD) {
				helper.selectTimeOfInterestType(ToiType.PERIOD);

				String startDate =
					_requestBean.getTasking().getCoverageProgrammmingRequest().getTimeOfInterest().getSurveyPeriod().getStrStartDate();
				//				double start = DateTimeFormat.parseIso("2010-06-01");
				double start = DateTimeFormat.parseIso(startDate);
				String endDate =
					_requestBean.getTasking().getCoverageProgrammmingRequest().getTimeOfInterest().getSurveyPeriod().getStrEndDate();
				double end = DateTimeFormat.parseIso(endDate);
				//				helper.setSurveyPeriod(start, start + 48*3600*10);
				helper.setSurveyPeriod(start, end);
			} // if (_requestBean.getCoverageProgrammmingRequest().getTimeOfInterest().getType() == Sps2GeneralConstants.TIME_SURVEY_PERIOD) {
			// ------------------------------------------------

			// ---------------- ROI : Region Of Interest ----------------
			GeometryFactory fac = new GeometryFactory();
			double leftLower = 
				_requestBean.getTasking().getCoverageProgrammmingRequest().getRegionOfInterest().getLeftLower();
			double leftUpper = 
				_requestBean.getTasking().getCoverageProgrammmingRequest().getRegionOfInterest().getLeftUpper();
			double rightLower = 
				_requestBean.getTasking().getCoverageProgrammmingRequest().getRegionOfInterest().getRightLower();
			double rightUpper = 
				_requestBean.getTasking().getCoverageProgrammmingRequest().getRegionOfInterest().getRightUpper();

			// Coordinate : longitude - latitude
			LinearRing outer = fac.createLinearRing(
					new Coordinate[]{
							new Coordinate(rightUpper, leftLower),
							new Coordinate(rightUpper, leftUpper),
							new Coordinate(rightLower, leftUpper),
							new Coordinate(rightLower, leftLower),
							new Coordinate(rightUpper, leftLower), 
					});
			helper.setPolygonROI(fac.createPolygon(outer, null));
			// ------------------------------------------------

			// ---------------- Acquisition angles ----------------
			if (_requestBean.getTasking().getCoverageProgrammmingRequest().getAcquisitionType().getType().equalsIgnoreCase(Sps2GeneralConstants.ACQUISITION_TYPE_MONO)) {
				helper.setPolygonROI(fac.createPolygon(outer, null));
				helper.selectAcquisitionType(AcquisitionType.MONO);

				String coverageType = _requestBean.getTasking().getCoverageProgrammmingRequest().getAcquisitionType().getMonoscopicAcquisition().getCoverageType();
				if (coverageType.equalsIgnoreCase(Sps2GeneralConstants.COVERAGE_TYPE_MONOPASS)) {
					helper.setCoverageType(CoverageType.MONOPASS);
				} else if (coverageType.equalsIgnoreCase(Sps2GeneralConstants.COVERAGE_TYPE_MULTIPASS)) {
					helper.setCoverageType(CoverageType.MULTIPASS);
				} else if (coverageType.equalsIgnoreCase(Sps2GeneralConstants.COVERAGE_TYPE_SINGLE_SWATH)) {
					helper.setCoverageType(CoverageType.SINGLE_SWATH);
				} else  {
					helper.setCoverageType(CoverageType.SINGLE_SWATH);
				}

				// azimuth
				helper.setAzimuthIncidenceRange(
						_requestBean.getTasking().getCoverageProgrammmingRequest().getAcquisitionType().getMonoscopicAcquisition().getAcquisitionAngleRange().getIncidenceAngleRange().getAzimuthIncidenceMin(), 
						_requestBean.getTasking().getCoverageProgrammmingRequest().getAcquisitionType().getMonoscopicAcquisition().getAcquisitionAngleRange().getIncidenceAngleRange().getAzimuthIncidenceMax());
				// elevation
				helper.setElevationIncidenceRange(
						_requestBean.getTasking().getCoverageProgrammmingRequest().getAcquisitionType().getMonoscopicAcquisition().getAcquisitionAngleRange().getIncidenceAngleRange().getElevationIncidenceMin(), 
						_requestBean.getTasking().getCoverageProgrammmingRequest().getAcquisitionType().getMonoscopicAcquisition().getAcquisitionAngleRange().getIncidenceAngleRange().getElevationIncidenceMax());
			} else if (_requestBean.getTasking().getCoverageProgrammmingRequest().getAcquisitionType().getType().equalsIgnoreCase(Sps2GeneralConstants.ACQUISITION_TYPE_STEREO)) {
				helper.selectAcquisitionType(AcquisitionType.STEREO);

				// azimuth
				helper.setAzimuthIncidenceRange(
						_requestBean.getTasking().getCoverageProgrammmingRequest().getAcquisitionType().getStereoscopicAcquisition().getAcquisitionAngle1().getIncidenceAngleRange().getAzimuthIncidenceMin(), 
						_requestBean.getTasking().getCoverageProgrammmingRequest().getAcquisitionType().getStereoscopicAcquisition().getAcquisitionAngle1().getIncidenceAngleRange().getAzimuthIncidenceMax());
				// elevation
				helper.setElevationIncidenceRange(
						_requestBean.getTasking().getCoverageProgrammmingRequest().getAcquisitionType().getStereoscopicAcquisition().getAcquisitionAngle1().getIncidenceAngleRange().getElevationIncidenceMin(), 
						_requestBean.getTasking().getCoverageProgrammmingRequest().getAcquisitionType().getStereoscopicAcquisition().getAcquisitionAngle1().getIncidenceAngleRange().getElevationIncidenceMax());
			} else if (_requestBean.getTasking().getCoverageProgrammmingRequest().getAcquisitionType().getType().equalsIgnoreCase(Sps2GeneralConstants.ACQUISITION_TYPE_UNKOWN)) {
				helper.selectAcquisitionType(AcquisitionType.UNKNOWN);
			} //if (_requestBean.getCoverageProgrammmingRequest().getAcquisitionType().getType().equalsIgnoreCase(Sps2GeneralConstants.ACQUISITION_TYPE_MONO)) {
			// ------------------------------------------------

			// ---------------- Acquisition parameters ----------------
			if (_requestBean.getSensor().getType().equalsIgnoreCase(Sps2GeneralConstants.SENSOR_OPTICAL)) {
				// optical
				//		helper.setInstrumentMode("PANCHROMATIC");
				//		helper.setMaxCloudCover(25.0);
				//		helper.setMaxSnowCover(100.0);
				//		helper.setHazeAccepted(true);
				//		helper.setSandWindAccepted(true);

				helperOptical.setFusionAccepted(
						_requestBean.getTasking().getCoverageProgrammmingRequest().getAcquisitionType().getAcquisitionParametersOPT().getFusionAccepted());
				helperOptical.setInstrumentMode(
						_requestBean.getTasking().getCoverageProgrammmingRequest().getAcquisitionType().getAcquisitionParametersOPT().getInstrumentMode());

				helperOptical.setGroundResolutionRange(
						_requestBean.getTasking().getCoverageProgrammmingRequest().getAcquisitionType().getAcquisitionParametersOPT().getGroundResolutionMin(), 
						_requestBean.getTasking().getCoverageProgrammmingRequest().getAcquisitionType().getAcquisitionParametersOPT().getGroundResolutionMax());

				// OPTICAL specificity
				helperOptical.setMinLuminosity(
						_requestBean.getTasking().getCoverageProgrammmingRequest().getAcquisitionType().getAcquisitionParametersOPT().getMinLuminosity());
			} else if (_requestBean.getSensor().getType().equalsIgnoreCase(Sps2GeneralConstants.SENSOR_RADAR)) {
				// radar
				//		EORadarParamHelper helper = new EORadarParamHelper(gfRequest);
				//		helper.setInstrumentMode("SM");
				//		helper.setInstrumentMode("EW");
				//		helper.setPolarizationMode("HH");
				//		helper.setPolarizationMode("HH/HV");

				helperRadar.setFusionAccepted(
						_requestBean.getTasking().getCoverageProgrammmingRequest().getAcquisitionType().getAcquisitionParametersSAR().getFusionAccepted());
				helperRadar.setInstrumentMode(
						_requestBean.getTasking().getCoverageProgrammmingRequest().getAcquisitionType().getAcquisitionParametersSAR().getInstrumentMode());

				helperRadar.setGroundResolutionRange(
						_requestBean.getTasking().getCoverageProgrammmingRequest().getAcquisitionType().getAcquisitionParametersSAR().getGroundResolutionMin(), 
						_requestBean.getTasking().getCoverageProgrammmingRequest().getAcquisitionType().getAcquisitionParametersSAR().getGroundResolutionMax());

				// RADAR specificity
				helperRadar.setPolarizationMode(
						_requestBean.getTasking().getCoverageProgrammmingRequest().getAcquisitionType().getAcquisitionParametersSAR().getPolarizationMode());
			} // if (__requestBean.getSensor().getType().equalsIgnoreCase(Sps2GeneralConstants.SENSOR_OPTICAL)) {
			// ------------------------------------------------

			// ---------------- Validation parameters ----------------
			if (_requestBean.getSensor().getType().equalsIgnoreCase(Sps2GeneralConstants.SENSOR_OPTICAL)) {
				helperOptical.setHazeAccepted(_requestBean.getTasking().getValidationParameters().getValidationParametersOPT().getHazeAccepted());
				helperOptical.setMaxCloudCover(_requestBean.getTasking().getValidationParameters().getValidationParametersOPT().getMaxCloudCoverage());
				helperOptical.setMaxSnowCover(_requestBean.getTasking().getValidationParameters().getValidationParametersOPT().getMaxSnowCoverage());
				helperOptical.setSandWindAccepted(_requestBean.getTasking().getValidationParameters().getValidationParametersOPT().getSandWindAccepted());
			} else if (_requestBean.getSensor().getType().equalsIgnoreCase(Sps2GeneralConstants.SENSOR_RADAR)) {
				helperRadar.setMaxAmbiguityLevel(_requestBean.getTasking().getValidationParameters().getValidationParametersSAR().getMaxAmbiguityLevel());
				helperRadar.setMaxNoiseLevel(_requestBean.getTasking().getValidationParameters().getValidationParametersSAR().getMaxNoiseLevel());
			} // if (__requestBean.getSensor().getType().equalsIgnoreCase(Sps2GeneralConstants.SENSOR_OPTICAL)) {
			// ------------------------------------------------

			// encoding
			//				DataEncoding encoding = new XmlEncoding(EOConstants.EO_PREFIX, EOConstants.EO_NAMESPACE);
			DataEncoding encoding = new XmlEncoding(EOConstants.EOT_PREFIX, EOConstants.EOT_NAMESPACE);
			_taskingRequest.getParameters().setDataEncoding(encoding);

			// write to console and to buffer
			ByteArrayOutputStream out = new ByteArrayOutputStream(8000);

			// write to console
			//		utils.writeXMLQuery(System.out, gfRequest);
			// write to buffer

			SPSUtils utils = new SPSUtils();
			utils.writeXMLQuery(out, _taskingRequest);

			// validate written message against SPS schemas
			//		ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
			//		com.sXMLSchemaValidation.validate(in);

			// read back from buffer
			//		in = new ByteArrayInputStream(out.toByteArray());
			//		TaskingRequest req2 = readTaskingRequest(in, 0);

			// check consistency
			//		checkParameters(req1, req2);

			log.debug("\n" + ORIGINE + " : -------------------------------------------");
			log.debug("\n" + ORIGINE + " : out : " + out.toString());
			log.debug("\n" + ORIGINE + " : -------------------------------------------");

			taskingRequestDocument = TaskingRequestDocument.Factory.parse(out.toString());
			log.debug("\n" + ORIGINE + " : taskingRequestDocument : " + taskingRequestDocument.toString());
			log.debug("\n" + ORIGINE + " : ----------------------------------------------------------------------");
			// ------------------------- end of create feasibility request with EO SPS Library (Spot Image : EO Application profile for SPS 2.0)

			if ( (_operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_GET_FEASIBILITY))
					|| (_operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_SUBMIT)) 
					||  (_operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_RESERVE))
					|| (_operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_UPDATE)) ) {
				log.debug("\n" + ORIGINE + " : taskingRequestDocument : " + taskingRequestDocument.toString());
				return taskingRequestDocument;
			} else {
				return null;
			}
			// ------------- end of the SPS Request -------------
		} catch (Exception exception) {
			log.info("\n" + ORIGINE + " Exception : " + exception.getMessage());
			return null;
		} 
	} // private TaskingRequestDocument getTaskingRequest(TaskingRequest _taskingRequest, ProgrammingRequestBean _paramGetFeasibilityBean) throws FacadeSps2Exception {

	/**
	 * This method submit a SPS 'GetFeasibility' operation
	 * 
	 * @param _requestBean (ProgrammingRequestBean)	: 'GetFeasibility' parameters
	 * @param _serverURL (String) 					: server URL
	 * 
	 * @return TaskingResponseBean : response
	 * @throws FacadeSps2Exception
	 */
	public TaskingResponseBean getGetFeasibilityResponse(
			ProgrammingRequestBean _requestBean, String _serverURL) throws FacadeSps2Exception {
		TaskingResponseBean response = new TaskingResponseBean();

		try {
			log.debug("\n" + ORIGINE + " : getGetFeasibilityResponse 1 : ");    

			// SOAP endpoint
			String endPointSOAP = "http://dione.deimos-space.com/HMA-FO/services/HMA-SPS";
			endPointSOAP = FacadeUtils.getEndPoint(_requestBean.getSensor().getUrn(), _serverURL);
			//			if (! _requestBean.getSensor().getEndPoint().equalsIgnoreCase("")) {
			//				endPointSOAP = _requestBean.getSensor().getEndPoint();
			//			} // if

			// stub creation
			com.deimos_space.www.hma_sps.HMASPSStub stub =
				new com.deimos_space.www.hma_sps.HMASPSStub(endPointSOAP);

			// request creation
			net.opengis.www.sps._2_0.GetFeasibilityDocument getFeasibilityDocument = 
				getGetFeasibilityRequest(_requestBean, _serverURL);

			log.info("\n ------------------------------------------------ ");
			log.info("\n" + ORIGINE + " : getFeasibilityDocument : " + getFeasibilityDocument.toString() +"\n");
			log.info("\n ------------------------------------------------ ");

			// 'GetFeasibility' service invocation
			GetFeasibilityResponseDocument getFeasibilityResponseDocument = null;
			try {
				getFeasibilityResponseDocument = 
					stub.getFeasibility(getFeasibilityDocument);
			} catch (AxisFault exception) {
				net.opengis.www.ows._1_1.ExceptionReportDocument exceptionReportDocument = 
					net.opengis.www.ows._1_1.ExceptionReportDocument.Factory.parse(exception.getDetail().toString());

				ErrorBean error = getOwsExceptionMessage(exceptionReportDocument);
				populateResponseError(response.getError(), error.getCode(), error.getMessage());
				return response;
			} catch (Exception exception) {
				populateResponseError(response.getError(), FacadeSps2Exception.SPS_EXCEPTION, "Exception: " + exception.getMessage());
				throw new FacadeSps2Exception(response.getError().getMessage(), 
						response.getError().getCode(), 
						ORIGINE);
			}

			// GetFeasibility response parsing
			if (getFeasibilityResponseDocument != null) {
				ParsingTasking parsing = new ParsingTasking();
				response = 
					parsing.parseGetFeasibilityResponse(
							_requestBean.getSensor().getUrn(), 
							_requestBean.getSensor().getType(), 
							getFeasibilityResponseDocument, 
							_serverURL);

				// ----------------------- save task's request and task's response in dataBase -----------------------
				FacadeTasks facadeTask = new FacadeTasks();
				
				String statusCode = (response.getStatusReport().getStatusCode() != null) ? response.getStatusReport().getStatusCode():"";
				String requestStatus = (response.getStatusReport().getRequestStatus() != null) ? response.getStatusReport().getRequestStatus():"";

				TaskBean _taskBean = new TaskBean();
				_taskBean.setId(response.getStatusReport().getTask());
				_taskBean.setName(_requestBean.getName());
				_taskBean.setUser(_requestBean.getUser());
				_taskBean.setOperation(Sps2GeneralConstants.SPS_GET_FEASIBILITY);
				_taskBean.setStatus(statusCode);
				_taskBean.setSensor(response.getStatusReport().getSensorId());
				_taskBean.setRequest(getFeasibilityDocument.toString());
				_taskBean.setRequestStatus(requestStatus);

				if ( (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_ACCEPTED)) 
						|| (statusCode.equalsIgnoreCase(Sps2GeneralConstants.STATUS_FEASIBLE)) ) {
					_taskBean.setResponse(getFeasibilityResponseDocument.toString());
					_taskBean.setResponseType(Sps2GeneralConstants.SPS_GET_FEASIBILITY);
				} // if ( (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_ACCEPTED)) 

				if ( (! requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_REJECTED))
						&& (! requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_FAILED))
						&& (! requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_EXPIRED))
						&& (! statusCode.equalsIgnoreCase(Sps2GeneralConstants.STATUS_REJECTED))
						&& (! statusCode.equalsIgnoreCase(Sps2GeneralConstants.STATUS_FAILED))
						&& (! statusCode.equalsIgnoreCase(Sps2GeneralConstants.STATUS_EXPIRED)) ) {
					facadeTask.insertTask(_taskBean);
				} // if (statusCode.equalsIgnoreCase(Sps2GeneralConstants.STATUS_FEASIBLE)) {
			} else {
				populateResponseError(response.getError(), FacadeSps2Exception.SPS_EXCEPTION, "Exception : null response");
				return response;
			} // if (getFeasibilityResponseDocument != null) {
			return response;
		} catch (AxisFault axisFault){
			populateResponseError(response.getError(), FacadeSps2Exception.SPS_AXIS_FAULT, "AxisFault: " + axisFault.getMessage());
			return response;
		} catch (Exception exception){
			populateResponseError(response.getError(), FacadeSps2Exception.SPS_EXCEPTION, "Exception: " + exception.getMessage());
			return response;
		} finally {
		}
	} // public TaskingResponseBean getGetFeasibilityResponse(ProgrammingRequestBean _requestBean,

	/**
	 * This method submit a SPS 'Submit' operation
	 * 
	 * @param _requestBean (ProgrammingRequestBean) : 'Submit' parameters
	 * @param _serverURL (String) 					: server URL
	 * 
	 * @return TaskingResponseBean : response
	 * @throws FacadeSps2Exception
	 */
	public TaskingResponseBean getSubmitResponse(
			ProgrammingRequestBean _requestBean, String _serverURL) throws FacadeSps2Exception {
		TaskingResponseBean response = new TaskingResponseBean();

		try {
			log.debug("\n" + ORIGINE + " : getSubmitResponse 1 : ");    

			// SOAP endpoint
			String endPointSOAP = "http://dione.deimos-space.com/HMA-FO/services/HMA-SPS";
			endPointSOAP = FacadeUtils.getEndPoint(_requestBean.getSensor().getUrn(), _serverURL);
			//			if (! _requestBean.getSensor().getEndPoint().equalsIgnoreCase("")) {
			//				endPointSOAP = _requestBean.getSensor().getEndPoint();
			//			} // if

			// stub creation
			com.deimos_space.www.hma_sps.HMASPSStub stub =
				new com.deimos_space.www.hma_sps.HMASPSStub(endPointSOAP);

			// request creation
			net.opengis.www.sps._2_0.SubmitDocument submitDocument = 
				getSubmitRequest(_requestBean, _serverURL);

			log.info("\n ------------------------------------------------ ");
			log.info("\n" + ORIGINE + " : submitDocument : " + submitDocument.toString() +"\n");
			log.info("\n ------------------------------------------------ ");

			// 'Submit' service invocation
			SubmitResponseDocument submitResponseDocument = null;
			try {
				submitResponseDocument = 
					stub.submit(submitDocument);
			} catch (AxisFault exception) {
				net.opengis.www.ows._1_1.ExceptionReportDocument exceptionReportDocument = 
					net.opengis.www.ows._1_1.ExceptionReportDocument.Factory.parse(exception.getDetail().toString());

				ErrorBean error = getOwsExceptionMessage(exceptionReportDocument);
				populateResponseError(response.getError(), error.getCode(), error.getMessage());
				return response;
			} catch (Exception exception) {
				populateResponseError(response.getError(), FacadeSps2Exception.SPS_EXCEPTION, "Exception: " + exception.getMessage());
				throw new FacadeSps2Exception(response.getError().getMessage(), 
						response.getError().getCode(), 
						ORIGINE);
			}

			// Submit response parsing
			if (submitResponseDocument != null) {
				ParsingTasking parsing = new ParsingTasking();
				response = 
					parsing.parseSubmitResponse(
							_requestBean.getSensor().getUrn(), 
							_requestBean.getSensor().getType(), 
							submitResponseDocument, 
							_serverURL);

				// ----------------------- save task's request and task's response in dataBase -----------------------
				FacadeTasks facadeTask = new FacadeTasks();

				String statusCode = (response.getStatusReport().getStatusCode() != null) ? response.getStatusReport().getStatusCode():"";
				String requestStatus = (response.getStatusReport().getRequestStatus() != null) ? response.getStatusReport().getRequestStatus():"";

				TaskBean _taskBean = new TaskBean();
				_taskBean.setId(response.getStatusReport().getTask());
				_taskBean.setName(_requestBean.getName());
				_taskBean.setUser(_requestBean.getUser());
				_taskBean.setOperation(Sps2GeneralConstants.SPS_SUBMIT);
				_taskBean.setStatus(statusCode);
				_taskBean.setSensor(response.getStatusReport().getSensorId());
				_taskBean.setRequest(submitDocument.toString());
				_taskBean.setRequestStatus(requestStatus);
				if ( (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_FEASIBLE))
						|| (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_ACCEPTED)) 
						|| (statusCode.equalsIgnoreCase(Sps2GeneralConstants.STATUS_FEASIBLE)) 
						|| (statusCode.equalsIgnoreCase(Sps2GeneralConstants.STATUS_ACCEPTED)) ) {
					_taskBean.setResponse(submitResponseDocument.toString());
				} else if ( (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_REJECTED)) 
						|| (statusCode.equalsIgnoreCase(Sps2GeneralConstants.STATUS_REJECTED)) ) {
					_taskBean.setResponse(submitResponseDocument.toString());
				} // if (statusCode.equalsIgnoreCase(Sps2GeneralConstants.STATUS_FEASIBLE)) {
				_taskBean.setResponseType(Sps2GeneralConstants.SPS_SUBMIT);

				if ( (! requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_REJECTED))
						&& (! requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_FAILED))
						&& (! requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_EXPIRED))
						&& (! statusCode.equalsIgnoreCase(Sps2GeneralConstants.STATUS_REJECTED))
						&& (! statusCode.equalsIgnoreCase(Sps2GeneralConstants.STATUS_FAILED))
						&& (! statusCode.equalsIgnoreCase(Sps2GeneralConstants.STATUS_EXPIRED)) ) {
					facadeTask.insertTask(_taskBean);
				} // if (statusCode.equalsIgnoreCase(Sps2GeneralConstants.STATUS_FEASIBLE)) {
			} else {
				populateResponseError(response.getError(), FacadeSps2Exception.SPS_EXCEPTION, "Exception : null response");
				return response;
			} // if (submitResponseDocument != null) {
			return response;
		} catch (AxisFault axisFault){
			populateResponseError(response.getError(), FacadeSps2Exception.SPS_AXIS_FAULT, "AxisFault : " + axisFault.getMessage());
			return response;
		} catch (Exception exception){
			populateResponseError(response.getError(), FacadeSps2Exception.SPS_EXCEPTION, "Exception : " + exception.getMessage());
			return response;
		} finally {
		}
	} // public TaskingResponseBean getSubmitResponse(ProgrammingRequestBean _requestBean,

	/**
	 * This method submit a SPS 'Reserve' operation
	 * 
	 * @param _requestBean (ProgrammingRequestBean) : 'Reserve' parameters
	 * @param _serverURL (String) 					: server URL
	 * 
	 * @return TaskingResponseBean : response
	 * @throws FacadeSps2Exception
	 */
	public TaskingResponseBean getReserveResponse(
			ProgrammingRequestBean _requestBean, String _serverURL) throws FacadeSps2Exception {
		TaskingResponseBean response = new TaskingResponseBean();

		try {
			log.debug("\n" + ORIGINE + " : getReserveResponse 1 : ");    

			// SOAP endpoint
			String endPointSOAP = "http://dione.deimos-space.com/HMA-FO/services/HMA-SPS";
			endPointSOAP = FacadeUtils.getEndPoint(_requestBean.getSensor().getUrn(), _serverURL);

			// stub creation
			com.deimos_space.www.hma_sps.HMASPSStub stub =
				new com.deimos_space.www.hma_sps.HMASPSStub(endPointSOAP);

			// request creation
			net.opengis.www.sps._2_0.ReserveDocument reserveDocument = 
				getReserveRequest(_requestBean, _serverURL);

			log.info("\n ------------------------------------------------ ");
			log.info("\n" + ORIGINE + " : reserveDocument : " + reserveDocument.toString() +"\n");
			log.info("\n ------------------------------------------------ ");

			// 'Reserve' service invocation
			ReserveResponseDocument reserveResponseDocument = null;
			try {
				reserveResponseDocument = stub.reserve(reserveDocument);
			} catch (AxisFault exception) {
				net.opengis.www.ows._1_1.ExceptionReportDocument exceptionReportDocument = 
					net.opengis.www.ows._1_1.ExceptionReportDocument.Factory.parse(exception.getDetail().toString());

				ErrorBean error = getOwsExceptionMessage(exceptionReportDocument);
				populateResponseError(response.getError(), error.getCode(), error.getMessage());
				return response;
			} catch (Exception exception) {
				populateResponseError(response.getError(), FacadeSps2Exception.SPS_EXCEPTION, "Exception: " + exception.getMessage());
				throw new FacadeSps2Exception(response.getError().getMessage(), 
						response.getError().getCode(), 
						ORIGINE);
			}

			//		     <taskID>http://www.deimos-space.com/sps/2010-08-11T13:16:48.113Z</taskID>
			//		     <sensorID>urn:ogc:object:feature:Sensor:deimos-space:HMA:SAR-1</sensorID>
			//		     <sps:title>CFI based Feasibility Study</sps:title>
			//		     <sps:abstract>Task reserved successfully. Task Feasible. The percentage chance of weather affecting the acquisition is: 0.0. Sensor SAR-1 available. Station North available. Station South available.</sps:abstract>
			//		     <sps:sensorIdentifier>urn:ogc:object:feature:Sensor:deimos-space:HMA:SAR-1</sps:sensorIdentifier>
			//		     <sps:taskIdentifier>http://www.deimos-space.com/sps/2010-08-11T13:16:48.113Z</sps:taskIdentifier>
			//		     <sps:updateTime>2010-08-11T13:17:08.903Z</sps:updateTime>
			//		     <sps:statusCode>RESERVED</sps:statusCode>
			//		     <sps:estimatedToC>2010-08-11T13:22:08Z</sps:estimatedToC>

			// Reserve response parsing
			if (reserveResponseDocument != null) {
				ParsingTasking parsing = new ParsingTasking();
				response = 
					parsing.parseReserveResponse(
							_requestBean.getSensor().getUrn(), 
							_requestBean.getSensor().getType(), 
							reserveResponseDocument, 
							_serverURL);

				// ----------------------- save task's request and task's response in dataBase -----------------------
				FacadeTasks facadeTask = new FacadeTasks();

				String statusCode = (response.getStatusReport().getStatusCode() != null) ? response.getStatusReport().getStatusCode():"";
				String requestStatus = (response.getStatusReport().getRequestStatus() != null) ? response.getStatusReport().getRequestStatus():"";

				TaskBean _taskBean = new TaskBean();
				_taskBean.setId(response.getStatusReport().getTask());
				_taskBean.setName(_requestBean.getName());
				_taskBean.setUser(_requestBean.getUser());
				_taskBean.setOperation(Sps2GeneralConstants.SPS_RESERVE);
				_taskBean.setStatus(statusCode);
				_taskBean.setSensor(response.getStatusReport().getSensorId());
				_taskBean.setRequest(reserveDocument.toString());
				_taskBean.setRequestStatus(requestStatus);
				if ( (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_ACCEPTED)) 
						|| (statusCode.equalsIgnoreCase(Sps2GeneralConstants.STATUS_FEASIBLE)) ) {
					_taskBean.setResponse(reserveResponseDocument.toString());
				} // if ( (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_ACCEPTED)) 
				_taskBean.setResponseType(Sps2GeneralConstants.SPS_RESERVE);

				if ( (! requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_REJECTED))
						&& (! requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_FAILED))
						&& (! requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_EXPIRED))
						&& (! statusCode.equalsIgnoreCase(Sps2GeneralConstants.STATUS_REJECTED))
						&& (! statusCode.equalsIgnoreCase(Sps2GeneralConstants.STATUS_FAILED))
						&& (! statusCode.equalsIgnoreCase(Sps2GeneralConstants.STATUS_EXPIRED)) ) {
					facadeTask.insertTask(_taskBean);
				} // if (statusCode.equalsIgnoreCase(Sps2GeneralConstants.STATUS_FEASIBLE)) {
			} else {
				populateResponseError(response.getError(), FacadeSps2Exception.SPS_EXCEPTION, "Exception : null response");
				return response;
			} // if (reserveResponseDocument != null) {
			return response;
		} catch (AxisFault axisFault){
			populateResponseError(response.getError(), FacadeSps2Exception.SPS_AXIS_FAULT, "AxisFault: " + axisFault.getLocalizedMessage());
			return response;
		} catch (Exception exception){
			populateResponseError(response.getError(), FacadeSps2Exception.SPS_EXCEPTION, "Exception: " + exception.getLocalizedMessage());
			return response;
		} finally {
		}

	} // public TaskingResponseBean getReserveResponse(ProgrammingRequestBean _requestBean,

	/**
	 * This method submit a SPS 'Confirm' operation
	 * 
	 * @param _requestBean (ConfirmRequestBean) : 'Confirm' parameters
	 * @param _serverURL (String) 				: server URL
	 * 
	 * @return ConfirmResponseBean : response
	 * @throws FacadeSps2Exception
	 */
	public ConfirmResponseBean getConfirmResponse(
			ConfirmRequestBean _requestBean, String _serverURL) throws FacadeSps2Exception {

		ConfirmResponseBean response = new ConfirmResponseBean();
		TaskStatusReportBean statusReport = new TaskStatusReportBean();

		try {
			log.debug("\n" + ORIGINE + " : getConfirmResponse 1 : ");    

			// SOAP endpoint
			String endPointSOAP = "http://dione.deimos-space.com/HMA-FO/services/HMA-SPS";
			endPointSOAP = FacadeUtils.getEndPoint(_requestBean.getSensorUrn(), _serverURL);

			// stub creation
			com.deimos_space.www.hma_sps.HMASPSStub stub =
				new com.deimos_space.www.hma_sps.HMASPSStub(endPointSOAP);

			// request creation
			net.opengis.www.sps._2_0.ConfirmDocument confirmDocument = 
				getConfirmRequest(_requestBean, _serverURL);

			log.info("\n ------------------------------------------------ ");
			log.info("\n" + ORIGINE + " : confirmDocument : " + confirmDocument.toString() +"\n");
			log.info("\n ------------------------------------------------ ");

			// 'Confirm' service invocation
			ConfirmResponseDocument confirmResponseDocument = null;
			try {
				confirmResponseDocument = stub.confirm(confirmDocument);
			} catch (AxisFault exception) {
				net.opengis.www.ows._1_1.ExceptionReportDocument exceptionReportDocument = 
					net.opengis.www.ows._1_1.ExceptionReportDocument.Factory.parse(exception.getDetail().toString());

				ErrorBean error = getOwsExceptionMessage(exceptionReportDocument);
				populateResponseError(response.getError(), error.getCode(), error.getMessage());
				return response;
			} catch (Exception exception) {
				populateResponseError(response.getError(), FacadeSps2Exception.SPS_EXCEPTION, "Exception: " + exception.getMessage());
				throw new FacadeSps2Exception(response.getError().getMessage(), 
						response.getError().getCode(), 
						ORIGINE);
			}

			// Confirm response parsing
			if (confirmResponseDocument != null) {
				response.setResponse(confirmResponseDocument.toString());

				if ( (confirmResponseDocument.getConfirmResponse() != null) &&  (confirmResponseDocument.getConfirmResponse().getResult() != null) ) {
					Result result= confirmResponseDocument.getConfirmResponse().getResult();

					//			            <sps:StatusReport>
					//			               <sps:title>CFI based Feasibility Study</sps:title>
					//			               <sps:abstract>Task confirmed successfully</sps:abstract>
					//			               <sps:sensorIdentifier>urn:ogc:object:feature:Sensor:deimos-space:HMA:SAR-1</sps:sensorIdentifier>
					//			               <sps:taskIdentifier>http://www.deimos-space.com/sps/2010-08-11T13:16:48.113Z</sps:taskIdentifier>
					//			               <sps:updateTime>2010-08-11T18:55:52.905Z</sps:updateTime>
					//			               <sps:statusCode>ACCEPTED</sps:statusCode>
					//			               <sps:estimatedToC>2010-08-11T13:22:08Z</sps:estimatedToC>
					//			            </sps:StatusReport>

					ParsingStatusReport parsing = new ParsingStatusReport();
					statusReport = 
						parsing.parseStatusReportResponse(result.getStatusReport());

					response.setStatusReport(statusReport);

					// ----------------------- save task's request and task's response in dataBase -----------------------
					FacadeTasks facadeTask = new FacadeTasks();

					TaskBean _taskBean = facadeTask.loadOneTask(_requestBean.getTaskId());

					if ( (_taskBean != null) && (! _taskBean.getId().equalsIgnoreCase("")) ) {
						String operation = _taskBean.getOperation();
						
						String statusCode = (statusReport.getStatusCode() != null) ? statusReport.getStatusCode():"";
						String requestStatus = (statusReport.getRequestStatus() != null) ? statusReport.getRequestStatus():"";

						if ( (! operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_CONFIRM)) 
								&& ( (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_ACCEPTED)) 
										|| (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_INEXECUTION))
										|| (statusCode.equalsIgnoreCase(Sps2GeneralConstants.STATUS_ACCEPTED))
										|| (statusCode.equalsIgnoreCase(Sps2GeneralConstants.STATUS_INEXECUTION))) ) {
							_taskBean.setOperation(Sps2GeneralConstants.SPS_CONFIRM);
							_taskBean.setStatus(statusCode);
							_taskBean.setRequestStatus(requestStatus);
							_taskBean.setUpdatedTask(false);

							facadeTask.modifyTask(_taskBean);
						} // if (! operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_CONFIRM)) {
					} // if (_taskBean != null) {
				} else {
					populateResponseError(response.getError(), FacadeSps2Exception.SPS_EXCEPTION, "Exception : null response");
					return response;
				} // if (confirmResponseDocument.getConfirmResponse() != null) {
			} else {
				populateResponseError(response.getError(), FacadeSps2Exception.SPS_EXCEPTION, "Exception : null response");
				return response;
			} // if (confirmResponseDocument != null) {
			return response;
		} catch (AxisFault axisFault){
			populateResponseError(response.getError(), FacadeSps2Exception.SPS_AXIS_FAULT, "AxisFault: " + axisFault.getLocalizedMessage());
			return response;
		} catch (Exception exception){
			populateResponseError(response.getError(), FacadeSps2Exception.SPS_EXCEPTION, "Exception: " + exception.getLocalizedMessage());
			return response;
		} finally {
		}
	} // public ConfirmResponseBean getConfirmResponse(ProgrammingRequestBean _requestBean,

	/**
	 * This method submit a SPS 'Update' operation ('GetFeasibility' or 'Submit').
	 * 
	 * @param _requestBean (ProgrammingRequestBean) : 'Update' parameters
	 * @param _serverURL (String) 					: server URL
	 * 
	 * @return TaskingResponseBean : response
	 * @throws FacadeSps2Exception
	 */
	public TaskingResponseBean getUpdateResponse(
			ProgrammingRequestBean _requestBean, String _serverURL) throws FacadeSps2Exception {
		TaskingResponseBean response = new TaskingResponseBean();

		// load Task in DataBase
		FacadeTasks facadeTask = new FacadeTasks();
		TaskBean taskBean = new TaskBean();
		try {
			taskBean = facadeTask.loadOneTask(_requestBean.getIdentifier());
		} catch(Exception exception) {
			log.debug("\n" + ORIGINE + " : Exception : null response");
			return response;
		}

		if (taskBean != null) {
			if (! taskBean.getId().equalsIgnoreCase("")) {
				try {
					log.debug("\n" + ORIGINE + " : getUpdateResponse 1 : ");    

					// SOAP endpoint
					String endPointSOAP = "http://dione.deimos-space.com/HMA-FO/services/HMA-SPS";
					endPointSOAP = FacadeUtils.getEndPoint(_requestBean.getSensor().getUrn(), _serverURL);

					// stub creation
					com.deimos_space.www.hma_sps.HMASPSStub stub =
						new com.deimos_space.www.hma_sps.HMASPSStub(endPointSOAP);

					// request creation
					net.opengis.www.sps._2_0.UpdateDocument updateDocument = 
						getUpdateRequest(_requestBean, _serverURL, taskBean);

					log.info("\n ------------------------------------------------ ");
					log.info("\n" + ORIGINE + " : updateDocument : " + updateDocument.toString() +"\n");
					log.info("\n ------------------------------------------------ ");

					// 'Update' service invocation
					UpdateResponseDocument updateResponseDocument = null;
					try {
						updateResponseDocument = 
							stub.update(updateDocument);
					} catch (AxisFault exception) {
						net.opengis.www.ows._1_1.ExceptionReportDocument exceptionReportDocument = 
							net.opengis.www.ows._1_1.ExceptionReportDocument.Factory.parse(exception.getDetail().toString());

						ErrorBean error = getOwsExceptionMessage(exceptionReportDocument);
						populateResponseError(response.getError(), error.getCode(), error.getMessage());
						return response;
					} catch (Exception exception) {
						populateResponseError(response.getError(), FacadeSps2Exception.SPS_EXCEPTION, "Exception: " + exception.getMessage());
						throw new FacadeSps2Exception(response.getError().getMessage(), 
								response.getError().getCode(), 
								ORIGINE);
					}

					// Update response parsing
					if (updateResponseDocument != null) {
						ParsingTasking parsing = new ParsingTasking();
						response = 
							parsing.parseUpdateResponse(_requestBean.getSensor().getUrn(), 
									_requestBean.getSensor().getType(), 
									updateResponseDocument, 
									_serverURL);
						// ----------------------- save task's request and task's response in dataBase -----------------------

						String statusCode = (response.getStatusReport().getStatusCode() != null) ? response.getStatusReport().getStatusCode():"";
						String requestStatus = (response.getStatusReport().getRequestStatus() != null) ? response.getStatusReport().getRequestStatus():"";

						TaskBean _taskBean = new TaskBean();
						
						_taskBean.setId(_requestBean.getIdentifier());
						_taskBean.setName(_requestBean.getName());
						_taskBean.setUser(_requestBean.getUser());
						_taskBean.setOperation(Sps2GeneralConstants.SPS_UPDATE);
						_taskBean.setStatus(statusCode);
						_taskBean.setSensor(response.getStatusReport().getSensorId());
						_taskBean.setRequest(updateDocument.toString());
						_taskBean.setRequestStatus(requestStatus);
						if ( (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_FEASIBLE))
								|| (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_ACCEPTED)) 
								|| (statusCode.equalsIgnoreCase(Sps2GeneralConstants.STATUS_FEASIBLE)) 
								|| (statusCode.equalsIgnoreCase(Sps2GeneralConstants.STATUS_ACCEPTED)) ) {
							_taskBean.setResponse(updateResponseDocument.toString());
						} else if ( (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_REJECTED)) 
								|| (statusCode.equalsIgnoreCase(Sps2GeneralConstants.STATUS_REJECTED)) ) {
							_taskBean.setResponse(updateResponseDocument.toString());
						} // if (statusCode.equalsIgnoreCase(Sps2GeneralConstants.STATUS_FEASIBLE)) {
						_taskBean.setResponseType(Sps2GeneralConstants.SPS_UPDATE);
						_taskBean.setUpdatedTask(false);

						facadeTask.modifyTask(_taskBean);
					} else {
						populateResponseError(response.getError(), FacadeSps2Exception.SPS_EXCEPTION, "Exception : null response");
						return response;
					} // if (submitResponseDocument != null) {
					return response;
				} catch (AxisFault axisFault){
					populateResponseError(response.getError(), FacadeSps2Exception.SPS_AXIS_FAULT, "Exception: " + axisFault.getMessage());
					return response;
				} catch (Exception exception){
					populateResponseError(response.getError(), FacadeSps2Exception.SPS_EXCEPTION, "Exception: " + exception.getMessage());
					return response;
				} finally {
				}
			} else {
				log.debug("\n" + ORIGINE + " : taskBean.getId() == ''");
				return response;
			} // if (! taskBean.getId().equalsIgnoreCase("")) {
		} else {
			log.debug("\n" + ORIGINE + " : taskBean == null");
			return response;
		} // if (taskBean != null) {
	} // public TaskingResponseBean getUpdateResponse(ProgrammingRequestBean _requestBean,

	/**
	 * Construction of the SPS 'Validate' request
	 * 
	 * @param _requestBean (ValidateRequestBean): 'Validate' operation parameters
	 * @param _serverURL (String) 				: server URL
	 * @param taskBean (TaskBean)				: task (to validate) informations
	 * 
	 * @return ValidateDocument : XML document that represents the XML request for the SPS operation 'Validate'
	 * @throws FacadeSps2Exception
	 */
	private ValidateDocument getValidateRequest(ValidateRequestBean _requestBean,
			String _serverURL, TaskBean _taskBean) 
	throws FacadeSps2Exception {
		try {
			ValidateDocument validateDocument = 
				ValidateDocument.Factory.newInstance();

			// sample request
			/*		     
			 * <Validate version="2.0.0" service="EOSPS">
         			<task>http://www.deimos-space.com/sps/2010-11-26T13:06:07.648Z</task>
         			<!--1 or more repetitions:-->
         			<segmentID>SEG_001</segmentID>
      		   </Validate>
			 */

			// create 'Validate' request with EO SPS Library (Spot Image : EO Application profile for SPS 2.0)
			// 			ValidateRequest validateRequest = new ValidateRequest();
			//			validateRequest.setVersion(Sps2GeneralConstants.SPS2_VERSION);
			//			validateRequest.setTaskID(_requestBean.getTaskId());
			//			validateRequest.setService(Sps2GeneralConstants.SPS2_SERVICE);
			//			validateRequest.setOperation(Sps2GeneralConstants.SPS_VALIDATE);
			//			validateRequest.setSegmentIDs(_requestBean.getSegmentsId());

			ValidateType validateType = validateDocument.addNewValidate();
			validateType.setTask(_requestBean.getTaskId());
			validateType.setService(Sps2GeneralConstants.EOSPS_SERVICE);
			validateType.setVersion(Sps2GeneralConstants.SPS2_VERSION);

			// add all segments identifiers to validate
			for (String segmentId : _requestBean.getSegmentsId()) {
				validateType.addSegmentID(segmentId);
			}

			validateDocument.setValidate(validateType);

			return validateDocument;
		} catch (Exception exception) {
			log.info("\n" + ORIGINE + " Exception : " + exception.getMessage());
			return null;
		} 
	} // private ValidateDocument getValidateRequest(ValidateRequestBean _requestBean,

	/**
	 * This method submit a SPS 'Validate' operation.
	 * 
	 * @param _requestBean (ValidateRequestBean) 	: 'Validate' parameters
	 * @param _serverURL (String) 					: server URL
	 * 
	 * @return ValidateResponseBean : response
	 * @throws FacadeSps2Exception
	 */
	public ValidateResponseBean getValidateResponse(
			ValidateRequestBean _requestBean, String _serverURL) throws FacadeSps2Exception {
		ValidateResponseBean response = new ValidateResponseBean();

		// load Task in DataBase
		FacadeTasks facadeTask = new FacadeTasks();
		TaskBean taskBean = new TaskBean();
		try {
			taskBean = facadeTask.loadOneTask(_requestBean.getTaskId());
		} catch(Exception exception) {
			log.debug("\n" + ORIGINE + " : Exception : null response");
			return response;
		}

		if (taskBean != null) {
			if (! taskBean.getId().equalsIgnoreCase("")) {
				try {
					log.debug("\n" + ORIGINE + " : getValidateResponse 1 : ");    

					// SOAP endpoint
					String endPointSOAP = "http://dione.deimos-space.com/HMA-FO/services/HMA-SPS";
					endPointSOAP = FacadeUtils.getEndPoint(_requestBean.getSensorUrn(), _serverURL);

					// stub creation
					com.deimos_space.www.hma_sps.HMASPSStub stub =
						new com.deimos_space.www.hma_sps.HMASPSStub(endPointSOAP);

					// request creation
					ValidateDocument validateDocument = 
						getValidateRequest(_requestBean, _serverURL, taskBean);

					log.info("\n ------------------------------------------------ ");
					log.info("\n" + ORIGINE + " : validateDocument : " + validateDocument.toString() +"\n");
					log.info("\n ------------------------------------------------ ");

					// 'Validate' service invocation
					ValidateResponseDocument validateResponseDocument = null;
					try {
						validateResponseDocument = 
							stub.validate(validateDocument);
					} catch (AxisFault exception) {
						net.opengis.www.ows._1_1.ExceptionReportDocument exceptionReportDocument = 
							net.opengis.www.ows._1_1.ExceptionReportDocument.Factory.parse(exception.getDetail().toString());

						ErrorBean error = getOwsExceptionMessage(exceptionReportDocument);
						populateResponseError(response.getError(), error.getCode(), error.getMessage());
						return response;
					} catch (Exception exception) {
						populateResponseError(response.getError(), FacadeSps2Exception.SPS_EXCEPTION, "Exception: " + exception.getMessage());
						throw new FacadeSps2Exception(response.getError().getMessage(), 
								response.getError().getCode(), 
								ORIGINE);
					}

					// Validate response parsing
					if (validateResponseDocument != null) {
						ParsingValidate parsing = new ParsingValidate();
						response = parsing.parseValidateResponse(validateResponseDocument);
						// ----------------------- save task's request and task's response in dataBase -----------------------
	
						String statusCode = (response.getStatusReport().getStatusCode() != null) ? response.getStatusReport().getStatusCode():"";
						String requestStatus = (response.getStatusReport().getRequestStatus() != null) ? response.getStatusReport().getRequestStatus():"";

						TaskBean _taskBean = new TaskBean();
						_taskBean.setId(_requestBean.getTaskId());
						_taskBean.setName(_requestBean.getName());
						_taskBean.setUser(_requestBean.getUser());
						_taskBean.setOperation(Sps2GeneralConstants.SPS_VALIDATE);
						_taskBean.setStatus(statusCode);
						_taskBean.setSensor(response.getStatusReport().getSensorId());
						_taskBean.setRequest(taskBean.getRequest());
						_taskBean.setRequestStatus(requestStatus);
						if ( (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_ACCEPTED)) 
								|| (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_COMPLETED)) 
								|| (statusCode.equalsIgnoreCase(Sps2GeneralConstants.STATUS_ACCEPTED))
								|| (statusCode.equalsIgnoreCase(Sps2GeneralConstants.STATUS_COMPLETED))) {
							_taskBean.setResponse(validateResponseDocument.toString());
							_taskBean.setResponseType(Sps2GeneralConstants.SPS_VALIDATE);
						} else {
							_taskBean.setResponse(taskBean.getResponse());
							_taskBean.setResponseType(taskBean.getResponseType());
						}
						_taskBean.setUpdatedTask(false);

						facadeTask.modifyTask(_taskBean);
					} else {
						populateResponseError(response.getError(), FacadeSps2Exception.SPS_EXCEPTION, "Exception : null response");
						return response;
					} // if (submitResponseDocument != null) {
					return response;
				} catch (AxisFault axisFault){
					populateResponseError(response.getError(), FacadeSps2Exception.SPS_AXIS_FAULT, "AxisFault: " + axisFault.getMessage());
					return response;
				} catch (Exception exception){
					populateResponseError(response.getError(), FacadeSps2Exception.SPS_EXCEPTION, "Exception: " + exception.getMessage());
					return response;
				} finally {
				}
			} else {
				log.debug("\n" + ORIGINE + " : taskBean.getId() == ''");
				return response;
			} // if (! taskBean.getId().equalsIgnoreCase("")) {
		} else {
			log.debug("\n" + ORIGINE + " : taskBean == null");
			return response;
		} // if (taskBean != null) {
	} // public ValidateResponseBean getValidateResponse(ValidateRequestBean _requestBean,

	/**
	 * Construction of the SPS 'Cancel' request
	 * 
	 * @param _requestBean (CancelRequestBean) : 'Cancel' operation parameters
	 * @param _serverURL (String) 				: server URL
	 * 
	 * @return ReserveDocument : XML document that represents the XML request for the SPS operation 'Cancel'
	 * @throws FacadeSps2Exception
	 */
	private CancelDocument getCancelRequest(CancelRequestBean _requestBean,
			String _serverURL) 
	throws FacadeSps2Exception {
		try {
			net.opengis.www.sps._2_0.CancelDocument cancelDocument = 
				net.opengis.www.sps._2_0.CancelDocument.Factory.newInstance();

			// create 'Cancel' request with EO SPS Library (Spot Image : EO Application profile for SPS 2.0)
			//			CancelRequest cancelRequest = new CancelRequest();
			//			cancelRequest.setVersion(Sps2GeneralConstants.SPS2_VERSION);
			//			cancelRequest.setService(Sps2GeneralConstants.SPS2_SERVICE);
			//			cancelRequest.setTaskID(cancelRequest.getTaskID());

			// sample reqquest
			/*
			 * <Cancel service="SPS" version="2.0.0" xmlns="http://www.opengis.net/sps/2.0">
			 *		<task>http://www.deimos-space.com/sps/2010-06-04T06:59:34.893Z</task>
			 * </Cancel>
			 */

			CancelType cancelType = cancelDocument.addNewCancel();
			cancelType.setService(Sps2GeneralConstants.SPS2_SERVICE);
			cancelType.setVersion(Sps2GeneralConstants.SPS2_VERSION);
			cancelType.setTask(_requestBean.getTaskId());

			log.debug("\n ------------------------------------------------ ");
			log.debug("\n" + ORIGINE + " : cancelDocument : " + cancelDocument.toString() +"\n");
			log.debug("\n ------------------------------------------------ ");

			return cancelDocument;
		} catch (Exception exception) {
			log.info("\n" + ORIGINE + " Exception : " + exception.getMessage());

			return null;
		} 
	} // private CancelDocument getCancelRequest(CancelRequestBean _requestBean,

	/**
	 * To get the Task Status of one Task
	 * 
	 * @param _parameters (CancelRequestBean) 	: 'Cancel' parameters
	 * @param _serverURL (String) 				: server URL
	 * 
	 * @return CancelResponseBean 				: 'Cancel' response
	 * 
	 * @throws FacadeSps2Exception
	 */
	public CancelResponseBean getCancelResponse(
			CancelRequestBean _parameters, 
			String _serverURL) 
	throws FacadeSps2Exception {
		CancelResponseBean response = new CancelResponseBean();

		try {
			// SOAP endpoint
			String endPointSOAP = "http://dione.deimos-space.com/HMA-FO/services/HMA-SPS";
			endPointSOAP = FacadeUtils.getEndPoint(_parameters.getSensorUrn(), _serverURL);

			// stub creation
			com.deimos_space.www.hma_sps.HMASPSStub stub =
				new com.deimos_space.www.hma_sps.HMASPSStub(endPointSOAP);

			// request creation
			net.opengis.www.sps._2_0.CancelDocument cancelDocument = 
				getCancelRequest(_parameters, _serverURL) ;

			log.debug("\n ------------------------------------------------ ");
			log.debug("\n" + ORIGINE + " : cancelDocument : " + cancelDocument.toString() +"\n");
			log.debug("\n ------------------------------------------------ ");

			// 'Cancel' service invocation
			CancelResponseDocument responseDocument = null;
			try {
				responseDocument = stub.cancel(cancelDocument);
			} catch (AxisFault exception) {
				net.opengis.www.ows._1_1.ExceptionReportDocument exceptionReportDocument = 
					net.opengis.www.ows._1_1.ExceptionReportDocument.Factory.parse(exception.getDetail().toString());

				ErrorBean error = getOwsExceptionMessage(exceptionReportDocument);
				populateResponseError(response.getError(), error.getCode(), error.getMessage());
				return response;
			} catch (Exception exception) {
				populateResponseError(response.getError(), FacadeSps2Exception.SPS_EXCEPTION, "Exception: " + exception.getMessage());
				return response;
			}

			log.debug("\n ------------------------------------------------ ");
			log.debug("\n" + ORIGINE + " : cancelResponseDocument : " + responseDocument.toString() +"\n");
			log.debug("\n ------------------------------------------------ ");

			// Cancel response parsing
			if (responseDocument != null) {
				// parse 'Cancel' response
				ParsingCancel parsing = new ParsingCancel();
				response = parsing.parseCancelResponse(responseDocument);

				//	update Task in database if status has changed
				updateTaskInDatabase(_parameters.getTaskId(), 
						response.getStatusReport(), 
						responseDocument.toString(), 
						Sps2GeneralConstants.SPS_CANCEL,
						false);
			} // if (responseDocument != null) {

			return response;
		} catch (AxisFault axisFault){
			populateResponseError(response.getError(), FacadeSps2Exception.SPS_AXIS_FAULT, "AxisFault: " + axisFault.getMessage());
			return response;
		} catch (Exception exception){
			populateResponseError(response.getError(), FacadeSps2Exception.SPS_EXCEPTION, "Exception: " + exception.getMessage());
			return response;
		} finally {
		}
	} // public CancelRequestBean getCancelResponse(

	/**
	 * Construction of the SPS 'GetStatus' request
	 * 
	 * @param _requestBean (GetStatusRequestBean) : 'GetStatus' operation parameters
	 * @param _serverURL (String) 				: server URL
	 * 
	 * @return ReserveDocument : XML document that represents the XML request for the SPS operation 'GetStatus'
	 * @throws FacadeSps2Exception
	 */
	private GetStatusDocument getStatusRequest(GetStatusRequestBean _requestBean,
			String _serverURL) 
	throws FacadeSps2Exception {
		try {
			net.opengis.www.sps._2_0.GetStatusDocument getStatusDocument =
				GetStatusDocument.Factory.newInstance();

			// create 'GetStatus' request with EO SPS Library (Spot Image : EO Application profile for SPS 2.0)
			//			GetStatusRequest getStatusRequest = new GetStatusRequest();
			//			getStatusRequest.setVersion(Sps2GeneralConstants.SPS2_VERSION);
			//			getStatusRequest.setService(Sps2GeneralConstants.SPS2_SERVICE);
			//			getStatusRequest.setTaskID(getStatusRequest.getTaskID());

			/*
			 * <GetStatus service="SPS" version="2.0.0" xmlns="http://www.opengis.net/sps/2.0">
		         <!--Zero or more repetitions:-->
		         <extension>?</extension>
		         <task>http://www.deimos-space.com/sps/2010-06-04T06:59:34.893Z</task>
		         <!--Optional:-->
		         <since>2010-01-01T00:00:00</since>
			 * </GetStatus>
			 */

			GetStatusType getStatusType = getStatusDocument.addNewGetStatus();
			getStatusType.setService(Sps2GeneralConstants.SPS2_SERVICE);
			getStatusType.setVersion(Sps2GeneralConstants.SPS2_VERSION);
			getStatusType.setTask(_requestBean.getTaskId());
			
			// add since tag with date in past to obtain all the meshes
			// "2010-01-01T00:00:00Z"
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"); 
			Date sinceDate = dateFormat.parse("2010-01-01T00:00:00Z");
			Calendar sinceCalendar = Calendar.getInstance();
			sinceCalendar.setTime(sinceDate);
			getStatusType.setSince(sinceCalendar);

			log.debug("\n ------------------------------------------------ ");
			log.debug("\n" + ORIGINE + " : getStatusDocument : " + getStatusDocument.toString() +"\n");
			log.debug("\n ------------------------------------------------ ");

			return getStatusDocument;

		} catch (Exception exception) {
			log.info("\n" + ORIGINE + " Exception : " + exception.getMessage());

			return null;
		} 
	} // private GetStatusDocument getStatusRequest(GetStatusRequestBean _requestBean,

	/**
	 * To get the Task Status of one Task
	 * 
	 * @param _parameters (GetStatusRequestBean) : 'GetStatus' parameters
	 * @param _serverURL (String) 	: server URL
	 * 
	 * @return GetStatusResponseBean : 'GetStatus' response
	 * 
	 * @throws FacadeSps2Exception
	 */
	public GetStatusResponseBean getTaskStatus(
			GetStatusRequestBean _parameters, 
			String _serverURL) 
	throws FacadeSps2Exception {
		GetStatusResponseBean response = new GetStatusResponseBean();

		try {
			// SOAP endpoint
			String endPointSOAP = "http://dione.deimos-space.com/HMA-FO/services/HMA-SPS";
			endPointSOAP = FacadeUtils.getEndPoint(_parameters.getSensorUrn(), _serverURL);

			// stub creation
			com.deimos_space.www.hma_sps.HMASPSStub stub =
				new com.deimos_space.www.hma_sps.HMASPSStub(endPointSOAP);

			// request creation
			net.opengis.www.sps._2_0.GetStatusDocument getStatusDocument = 
				getStatusRequest(_parameters, _serverURL);

			// 'GetStatus' service invocation
			GetStatusResponseDocument responseDocument = null;
			try {
				responseDocument = stub.getStatus(getStatusDocument);
			} catch (AxisFault exception) {
				net.opengis.www.ows._1_1.ExceptionReportDocument exceptionReportDocument = 
					net.opengis.www.ows._1_1.ExceptionReportDocument.Factory.parse(exception.getDetail().toString());

				ErrorBean error = getOwsExceptionMessage(exceptionReportDocument);
				populateResponseError(response.getError(), error.getCode(), error.getMessage());
				return response;
			} catch (Exception exception) {
				populateResponseError(response.getError(), FacadeSps2Exception.SPS_EXCEPTION, "Exception: " + exception.getMessage());
				return response;
			}

			// GetStatus response parsing
			if (responseDocument != null) {
				log.debug("\n ------------------------------------------------ ");
				log.debug("\n" + ORIGINE + " : getTaskStatus : responseDocument : " + responseDocument.toString() +"\n");
				log.debug("\n ------------------------------------------------ ");

				// parse 'GetStatus' response
				ParsingGetStatus parsing = new ParsingGetStatus();
				response = 
					parsing.parseGetStatusResponse(_parameters, _serverURL, responseDocument);

				//	update Task in database if status has changed
				updateTaskInDatabase(_parameters.getTaskId(), 
						response.getStatusReport(), 
						responseDocument.toString(), 
						Sps2GeneralConstants.SPS_GET_STATUS,
						false);
			} // if (responseDocument != null) {

			return response;
		} catch (AxisFault axisFault){
			populateResponseError(response.getError(), FacadeSps2Exception.SPS_AXIS_FAULT, "AxisFault: " + axisFault.getMessage());
			return response;
		} catch (Exception exception){
			populateResponseError(response.getError(), FacadeSps2Exception.SPS_EXCEPTION, "Exception: " + exception.getMessage());
			return response;
		} finally {
		}
	} // public GetStatusResponseBean getTaskStatus

	/**
	 * @param _updatedTask (boolean) :  true if Updated task from updated Thread, false else
	 */
	private void updateTaskInDatabase(String _taskId, 
			TaskStatusReportBean _statusReport, 
			String response, 
			String _responseType,
			boolean _updatedTask) {

		// ----------------------- load task's request and task's response from dataBase -----------------------
		FacadeTasks facadeTask = new FacadeTasks();

		TaskBean _taskBean = null;
		try {
			_taskBean = facadeTask.loadOneTask(_taskId);

			if ( (_taskBean != null) && (! _taskBean.getId().equalsIgnoreCase("")) ) {
					//	update Task in database if status has changed
					boolean updateTask = false;

					String statusCode = (_statusReport.getStatusCode() != null) ? _statusReport.getStatusCode():"";
					String requestStatus = (_statusReport.getRequestStatus() != null) ? _statusReport.getRequestStatus():"";

					if ( (! statusCode.equalsIgnoreCase(_taskBean.getStatus())) 
						|| (! requestStatus.equalsIgnoreCase(_taskBean.getRequestStatus())) 
						|| (! _statusReport.getResponse().equalsIgnoreCase(_taskBean.getResponse())) ) {
						updateTask = true;
					} // 

					if ( (_responseType.equalsIgnoreCase(Sps2GeneralConstants.SPS_CANCEL)) 
							&& ( (statusCode.length() == 0) 
								||(statusCode.equalsIgnoreCase(Sps2GeneralConstants.STATUS_CANCELLED)) 
								|| (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_CANCELLED)) ) ) {
							updateTask = true;
							_taskBean.setOperation(Sps2GeneralConstants.SPS_CANCEL);
					} // if 

					if (updateTask) {
						if ( (statusCode.equalsIgnoreCase(Sps2GeneralConstants.STATUS_FEASIBLE)) 
								|| (statusCode.equalsIgnoreCase(Sps2GeneralConstants.STATUS_ACCEPTED))
								|| (statusCode.equalsIgnoreCase(Sps2GeneralConstants.STATUS_COMPLETED))
								|| (statusCode.equalsIgnoreCase(Sps2GeneralConstants.STATUS_CANCELLED))
								|| (statusCode.equalsIgnoreCase(Sps2GeneralConstants.STATUS_RESERVED)) ) {
							_taskBean.setResponse(response);
							_taskBean.setResponseType(_responseType);
						} // if 
						if ( (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_FEASIBLE)) 
								|| (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_ACCEPTED))
								|| (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_COMPLETED))
								|| (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_CANCELLED))
								|| (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_RESERVED)) ) {
							_taskBean.setResponse(response);
							_taskBean.setResponseType(_responseType);
						} // if ( (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_FEASIBLE)) 

						_taskBean.setStatus(statusCode);
						_taskBean.setRequestStatus(requestStatus);
						_taskBean.setUpdatedTask(_updatedTask);

						facadeTask.modifyTask(_taskBean);
					} // if (updateTask) {
			} // if ( (_taskBean != null) 
		} catch (TaskException exception) {
			log.info("\n" + ORIGINE + " : updateTaskInDatabase : Exception : " + exception.getMessage());
		}
	} // private void updateTaskInDatabase() {

	private ErrorBean getOwsExceptionMessage(net.opengis.www.ows._1_1.ExceptionReportDocument _exceptionReportDocument) {
		ErrorBean error = new ErrorBean();

		int code = FacadeSps2Exception.SPS_EXCEPTION;
		String message = "Exception : " + _exceptionReportDocument.toString();

		ExceptionReport report = _exceptionReportDocument.getExceptionReport();
		if ( (report != null) && (report.getExceptionArray(0) != null) ) {
			ExceptionType exception = report.getExceptionArray(0);
			if (exception.getExceptionTextArray(0) != null) {
				message = exception.getExceptionCode() + "\n" + exception.getExceptionTextArray(0);
			} // if (exception.getExceptionTextArray(0) != null) {
		} // if (report != null) {

		error.setCode(code);
		error.setMessage(message);

		return error;
	} // private ErrorBean getOwsExceptionMessage(net.opengis.www.ows._1_1.ExceptionReportDocument _exceptionReportDocument) {

	private void populateResponseError(ErrorBean error, int errorCode, String ErrorMessage) {
		error.setCode(errorCode);
		error.setMessage(ErrorMessage);
		log.info("\n" + ORIGINE + " : " + ErrorMessage);
	}

} // class
