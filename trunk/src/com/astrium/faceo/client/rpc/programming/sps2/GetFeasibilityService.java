package com.astrium.faceo.client.rpc.programming.sps2;

import com.astrium.faceo.client.bean.programming.sps2.request.ProgrammingRequestBean;
import com.astrium.faceo.client.bean.programming.sps2.responses.TaskingResponseBean;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
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
 * @author ASTRIUM
 *
 */
@RemoteServiceRelativePath("getSps2FeasibilityController.srv")
public interface GetFeasibilityService extends RemoteService {

	/**
	 * 
	 * @author ASTRIUM
	 *
	 */
	public static class Util {
		/**
		 * 
		 * @return GetFeasibilityServiceAsync
		 */
		public static GetFeasibilityServiceAsync getInstance() {
			return GWT.create(GetFeasibilityService.class);
		}
	}

	/**
     * This method send a SPS request 
     * with the 'GetFeasibility' operation
     * 
     * @param _operation (String) 					: operation ('GetFeasibility')
     * @param _parameters (ProgrammingRequestBean)	: GetFeasibility parameters
     * @param _webAccessMode (boolean)				: true for using web services, false else (for using xml files)
     * 
     * @return AsyncCallback<TaskingResponseBean> : callback which return the request ack
     */
	public TaskingResponseBean getGetFeasibilityResponse(
			String _operation, 
			ProgrammingRequestBean _parameters, 
		    boolean _webAccessMode);
	
} // class
