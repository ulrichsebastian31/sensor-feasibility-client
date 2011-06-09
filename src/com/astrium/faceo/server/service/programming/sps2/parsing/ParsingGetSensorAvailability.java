package com.astrium.faceo.server.service.programming.sps2.parsing;

/*
 * @(#)ParsingGetSensorAvailability.java	 1.0  03/11/2010
 *
 *
 * PROJET       : FACEO
 *
 * LANGUAGE     : Java
 *
 * DESCRIPTION  :  The ParsingGetSensorAvailability class parse the xml messages of SPS 2.0 'GetSensorAvailability' operation
 *
 * CREATION :
 * ---------------------------------------------------------------------
 * | Date       | Version | Description                                |
 * ---------------------------------------------------------------------
 * | 03/11/2010 |    1.0  |                                            |
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

import java.util.HashMap;

import net.opengis.www.eosps._2_0.GetSensorAvailabilityResponseDocument;
import net.opengis.www.eosps._2_0.GetSensorAvailabilityResponseType;
import net.opengis.www.eosps._2_0.GetSensorAvailabilityResponseType.AvailabilityPeriod;
import net.opengis.www.eosps._2_0.GetSensorAvailabilityResponseType.ResponsePeriod;
import net.opengis.www.gml._3_2.TimePeriodType;
import net.opengis.www.gml._3_2.TimePositionType;

import org.apache.log4j.Logger;
import org.vast.ows.sps.SPSUtils;

import com.astrium.faceo.client.bean.programming.sps2.getSensorAvailability.GetSensorAvailabilityRequestBean;
import com.astrium.faceo.client.bean.programming.sps2.getSensorAvailability.GetSensorAvailabilityResponseBean;
import com.astrium.faceo.client.bean.programming.sps2.request.SurveyPeriodBean;

/**
 * <B>SITE FACEO</B> <BR>
 * 
 * <P>
 * 'GetSensorAvailability' operation : (see chapter 8.1 of 'EO Satellite Tasking
 * Extension for SPS'	'OGC 10-135')
 * 
 * An EO system may not be available over a period of time for different reasons
 * such as workload, maintenance, etc. The GetSensorAvailability operation
 * allows the client to obtain a preview of the periods of availability of a
 * sensor before a feasibility study is requested.
 * 
 * The granularity of the provided information is up to the data provider who
 * can choose to describe its exact workload or simply list approximate periods
 * of availability. For instance, a provider may wish to list a period of one
 * week as soon as the sensor is available for tasking at least during 50% of
 * the period. This allows a provider to choose the most appropriate granularity
 * of information to help the users while maintaining its exact workload secret.
 * 
 * </P>
 * </P>
 * 
 * @author GR
 * @version 1.0, le 03/11/2010
 */
public class ParsingGetSensorAvailability {


	/** Le logger de cette classe */
	private static Logger log = Logger.getLogger(ParsingGetSensorAvailability.class);

	private static String ORIGINE = "ParsingGetSensorAvailability";

	protected SPSUtils utils = new SPSUtils();

	/**
	 * constructor
	 */
	public ParsingGetSensorAvailability() {
	}

	/**
	 * get the result of a 'GetSensorAvailability' operation
	 * 
	 * @param _parameters
	 *            (GetSensorAvailabilityRequestBean) : 'GetSensorAvailability'
	 *            parameters
	 * @param _responseDocument
	 *            (GetSensorAvailabilityResponseDocument) :
	 *            'GetSensorAvailability' xml response
	 * 
	 * @return DescribeResultAccessResponseBean
	 * 
	 * @throws java.lang.Exception
	 */
	public GetSensorAvailabilityResponseBean parseGetSensorAvailabilityResponse(
			GetSensorAvailabilityRequestBean _parameters, GetSensorAvailabilityResponseDocument _responseDocument)
			throws java.lang.Exception {

		GetSensorAvailabilityResponseBean response = null;

		if (_responseDocument != null) {
			response = new GetSensorAvailabilityResponseBean();
			
			response.setSensorUrn(_parameters.getSensorUrn());

			// transform XML response in HTML format
			// response.setResponse(Sps2ParsingUtils.setHTMLResponse(_responseDocument.toString()));

			// remplacement des chevrons par leur code HTML
			
/*			<GetSensorAvailabilityResponse xmlns="http://www.opengis.net/eosps/2.0" xmlns:gml="http://www.opengis.net/gml/3.2">
				<responsePeriod>
					<gml:TimePeriod gml:id="PERIOD">
						<gml:beginPosition>2010-06-01</gml:beginPosition>
						<gml:endPosition>2010-08-31</gml:endPosition>
					</gml:TimePeriod>
			    </responsePeriod>
			    <availabilityPeriod>
					<gml:TimePeriod gml:id="A1">
						<gml:beginPosition>2010-06-21</gml:beginPosition>
						<gml:endPosition>2010-07-04</gml:endPosition>
					</gml:TimePeriod>
			    </availabilityPeriod>
			    <availabilityPeriod>
					<gml:TimePeriod gml:id="A2">
						<gml:beginPosition>2010-08-21</gml:beginPosition>
						<gml:endPosition>2010-08-28</gml:endPosition>
				    </gml:TimePeriod>
				</availabilityPeriod>
	        </GetSensorAvailabilityResponse>*/
		
			String result = "<h3> Sensor : " + _parameters.getSensorUrn() + "<br></h3><br><br>";

			GetSensorAvailabilityResponseType responseType = _responseDocument.getGetSensorAvailabilityResponse();
			if (responseType != null) {
				if (responseType.getResponsePeriod() != null) {
					ResponsePeriod responsePeriod = responseType.getResponsePeriod();
					TimePeriodType timePeriod = responsePeriod.getTimePeriod();
					TimePositionType time1PositionType = timePeriod.getBeginPosition();
					String beginPeriod = time1PositionType.getStringValue();
					TimePositionType tim2PositionType = timePeriod.getEndPosition();
					String endPeriod = tim2PositionType.getStringValue();

					SurveyPeriodBean surveyPeriod = new SurveyPeriodBean();
					surveyPeriod.setStrStartDate(beginPeriod);
					surveyPeriod.setStrEndDate(endPeriod);
					response.setResponsePeriod(surveyPeriod);

					result = result + "<div><b>Response period :</b></div>";
					result = result + "<div><b>begin : </b>" + beginPeriod + "</div>";
					result = result + "<div><b>end : </b>" + endPeriod + "</div>";
					result = result + "<div></div>";
				} // if (responseType.getResponsePeriod() != null) {

				if ((responseType.getAvailabilityPeriodArray() != null)
						&& (responseType.getAvailabilityPeriodArray().length > 0)) {
					HashMap<String, SurveyPeriodBean> availabilities = new HashMap<String, SurveyPeriodBean>();
					
					result = result + "<div><b>Availability periods :</b></div>";
					for (int i = 0; i < responseType.getAvailabilityPeriodArray().length; i++) {
						AvailabilityPeriod availabilityPeriod = responseType.getAvailabilityPeriodArray()[i];

						TimePeriodType timePeriod;
						String beginPeriod;
						String endPeriod;
						try {
							timePeriod = availabilityPeriod.getTimePeriod();
							TimePositionType time1PositionType = timePeriod.getBeginPosition();
							beginPeriod = time1PositionType.getStringValue();
							TimePositionType tim2PositionType = timePeriod.getEndPosition();
							endPeriod = tim2PositionType.getStringValue();

							SurveyPeriodBean surveyPeriod = new SurveyPeriodBean();
							surveyPeriod.setStrStartDate(beginPeriod);
							surveyPeriod.setStrEndDate(endPeriod);
							availabilities.put(timePeriod.getId(), surveyPeriod);

							result = result + "<div><b>Period :</b>" + timePeriod.getId() + "</div>";
							result = result + "<div><b>begin : </b>" + beginPeriod + "</div>";
							result = result + "<div><b>end : </b>" + endPeriod + "</div>";
							result = result + "<div></div>";
						} catch (Exception exception) {
							log.info("\n " + ORIGINE + " : Exception : " + exception.getLocalizedMessage());
						}
					} // for (int i=0; i <

					response.setAvailabilityPeriod(availabilities);
				} // if ( (responseType.getAvailabilityPeriodArray() != null) &&
			} // if (responseType != null) {

			response.setResponse(result);
		} // if (_responseDocument != null) {

		return response;
	} // public GetSensorAvailabilityResponseBean parseGetSensorAvailabilityResponse(

} // class
