package com.astrium.faceo.client.rpc.programming.sps2;

import com.astrium.faceo.client.bean.programming.sps2.request.ProgrammingRequestBean;
import com.astrium.faceo.client.bean.programming.sps2.responses.TaskingResponseBean;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * 
 * The Update operation allows SPS clients to update 
 * a previously submitted/reserved and accepted task.
 * 
 * @author ASTRIUM
 *
 */
public interface UpdateTaskServiceAsync {

	/**
     * This method send a SPS request 
     * with the 'Update' operation
     * 
     * @param _operation (String) 					: operation ('Update')
     * @param _parameters (ProgrammingRequestBean)	: Submit parameters
     * @param _webAccessMode (boolean)				: true for using web services, false else (for using xml files)
     * 
     * @param _callback (TaskingResponseBean) : callback which return the request ack
     */
	public void getUpdateResponse(
			String _operation, 
			ProgrammingRequestBean _parameters, 
		    boolean _webAccessMode, AsyncCallback<TaskingResponseBean> _callback);
	
} // class
