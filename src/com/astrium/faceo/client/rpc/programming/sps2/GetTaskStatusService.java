package com.astrium.faceo.client.rpc.programming.sps2;

import com.astrium.faceo.client.bean.programming.sps2.getStatus.GetStatusRequestBean;
import com.astrium.faceo.client.bean.programming.sps2.getStatus.GetStatusResponseBean;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 *  
 * The GetStatus operation allows SPS clients to retrieve status reports 
 * about a tasking request or a task. 
 * This operation is the default mechanism to retrieve status information 
 * about a task or tasking request.
 * 
 * @author ASTRIUM
 *
 */
@RemoteServiceRelativePath("getTaskStatusController.srv")
public interface GetTaskStatusService extends RemoteService {

	/**
	 * 
	 * @author ASTRIUM
	 *
	 */
	public static class Util {
		/**
		 * 
		 * @return GWT.create(GetTaskStatusService.class)
		 */
		public static GetTaskStatusServiceAsync getInstance() {
			return GWT.create(GetTaskStatusService.class);
		}
	}
	
	/**
     * This method get the status of a SPS task using 'GetStatus' SPS 2.0 operation
     * 
     * @param _operation (String)	: SPS 2.0 'GetStatus' operation
     * @param _requestParameters (GetStatusRequestBean)	: parameters for the SPS 2.0 'GetStatus' operation
     * 
     * @return GetStatusResponseBean	: the Get Status response in 'HTML' format
     */
	public GetStatusResponseBean getTaskStatus(String _operation, GetStatusRequestBean _requestParameters);
	
}
