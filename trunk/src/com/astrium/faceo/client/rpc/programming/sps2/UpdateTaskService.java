package com.astrium.faceo.client.rpc.programming.sps2;

import com.astrium.faceo.client.bean.programming.sps2.request.ProgrammingRequestBean;
import com.astrium.faceo.client.bean.programming.sps2.responses.TaskingResponseBean;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * 
 * The Update operation allows SPS clients to update 
 * a previously submitted/reserved and accepted task.
 * 
 * @author ASTRIUM
 *
 */
@RemoteServiceRelativePath("getUpdateTaskController.srv")
public interface UpdateTaskService extends RemoteService {

	/**
	 * 
	 * @author ASTRIUM
	 *
	 */
	public static class Util {
		/**
		 * 
		 * @return UpdateTaskService
		 */
		public static  UpdateTaskServiceAsync getInstance() {
			return GWT.create(UpdateTaskService.class);
		}
	}

	/**
     * This method send a SPS request 
     * with the 'Update' operation
     * 
     * @param _operation (String) 					: operation ('Update')
     * @param _parameters (ProgrammingRequestBean)	: Submit parameters
     * @param _webAccessMode (boolean)				: true for using web services, false else (for using xml files)
     * 
     * @return AsyncCallback<TaskingResponseBean> : callback which return the request ack
     */
	public TaskingResponseBean getUpdateResponse(
			String _operation, 
			ProgrammingRequestBean _parameters, 
		    boolean _webAccessMode);
	
} // class
