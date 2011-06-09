package com.astrium.faceo.client.rpc.programming.sps2;

import com.astrium.faceo.client.bean.programming.sps2.getSensorAvailability.GetSensorAvailabilityRequestBean;
import com.astrium.faceo.client.bean.programming.sps2.getSensorAvailability.GetSensorAvailabilityResponseBean;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * 
 * 'GetSensorAvailability' operation : 
 * (see chapter 8.1 of 'EO Satellite Tasking Extension for SPS'	'OGC 10-135') 
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
 * @author ASTRIUM
 *
 */
@RemoteServiceRelativePath("getSensorAvailabilityController.srv")
public interface GetSensorAvailabilityService extends RemoteService {

	/**
	 * 
	 * @author ASTRIUM
	 *
	 */
	public static class Util {
		/**
		 * 
		 * @return GetSensorAvailabilityServiceAsync
		 */
		public static  GetSensorAvailabilityServiceAsync getInstance() {
			return GWT.create(GetSensorAvailabilityService.class);
		}
	}

	/**
     * This method send a SPS request 
     * with the 'GetSensorAvailability' operation
     * 
     * @param _operation (String) 				: operation ('GetSensorAvailability')
     * @param _parameters (GetSensorAvailabilityRequestBean) : GetSensorAvailability parameters
     * @param _webAccessMode (boolean)			: true for using web services, false else (for using xml files)
     * 
     * @return AsyncCallback<GetSensorAvailabilityResponseBean> : callback which return the request ack
     */
	public GetSensorAvailabilityResponseBean getGetSensorAvailabilityResponse(
			String _operation, 
			GetSensorAvailabilityRequestBean _parameters, 
		    boolean _webAccessMode);
	
} // class
