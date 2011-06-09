package com.astrium.faceo.client.rpc.programming.sps2;

import com.astrium.faceo.client.bean.programming.sps2.request.ProgrammingRequestBean;
import com.astrium.faceo.client.bean.programming.sps2.responses.TaskingResponseBean;
import com.google.gwt.user.client.rpc.AsyncCallback;

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
public interface GetFeasibilityServiceAsync {

	/**
     * This method send a SPS request 
     * with the 'GetFeasibility' operation
     * 
     * @param _operation (String) 					: operation ('GetFeasibility')
     * @param _parameters (ProgrammingRequestBean)	: GetFeasibility parameters
     * @param _webAccessMode (boolean)				: true for using web services, false else (for using xml files)
     * 
     * @param _callback (TaskingResponseBean) : callback which return the request ack
     */
	public void getGetFeasibilityResponse(
			String _operation, 
			ProgrammingRequestBean _parameters, 
		    boolean _webAccessMode, AsyncCallback<TaskingResponseBean> _callback);
	
} // class
