package com.astrium.faceo.client.rpc.programming.sps2;

import com.astrium.faceo.client.bean.programming.sps2.request.ProgrammingRequestBean;
import com.astrium.faceo.client.bean.programming.sps2.responses.TaskingResponseBean;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * 
 * The Submit operation allows SPS clients to submit a tasking request. 
 * The SPS shall perform an internal feasibility check for the intended task and 
 * – if the task is feasible – shall perform the task.
 * 
 * @author ASTRIUM
 *
 */
@RemoteServiceRelativePath("getSubmitController.srv")
public interface SubmitService extends RemoteService {

	/**
	 * 
	 * @author ASTRIUM
	 *
	 */
	public static class Util {
		/**
		 * 
		 * @return SubmitServiceAsync
		 */
		public static  SubmitServiceAsync getInstance() {
			return GWT.create(SubmitService.class);
		}
	}

	/**
     * This method send a SPS request 
     * with the 'Submit' operation
     * 
     * @param _operation (String) 					: operation ('Submit')
     * @param _parameters (ProgrammingRequestBean)	: Submit parameters
     * @param _webAccessMode (boolean)				: true for using web services, false else (for using xml files)
     * 
     * @return AsyncCallback<TaskingResponseBean> : callback which return the request ack
     */
	public TaskingResponseBean getSubmitResponse(
			String _operation, 
			ProgrammingRequestBean _parameters, 
		    boolean _webAccessMode);
	
} // class
