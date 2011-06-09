package com.astrium.faceo.client.rpc.programming.sps2;

import com.astrium.faceo.client.bean.programming.sps2.confirm.ConfirmRequestBean;
import com.astrium.faceo.client.bean.programming.sps2.confirm.ConfirmResponseBean;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * 
 * The Confirm operation allows SPS clients to confirm a previously reserved task. 
 * If accepted i;e; if task has'nt expired yet, the task shall transit from state reserved
 * to inExecution, otherwise the SPS shall reject the request
 * 
 * @author ASTRIUM
 *
 */
@RemoteServiceRelativePath("getConfirmTaskController.srv")
public interface ConfirmTaskService extends RemoteService {

	/**
	 * 
	 * @author ASTRIUM
	 *
	 */
	public static class Util {
		/**
		 * 
		 * @return ConfirmTaskServiceAsync
		 */
		public static  ConfirmTaskServiceAsync getInstance() {
			return GWT.create(ConfirmTaskService.class);
		}
	}

	/**
     * This method send a SPS request 
     * with the 'Confirm' operation
     * 
     * @param _operation (String) 				: operation ('Confirm')
     * @param _parameters (ConfirmRequestBean)	: Confirm parameters
     * @param _webAccessMode (boolean)			: true for using web services, false else (for using xml files)
     * 
     * @return AsyncCallback<ConfirmResponseBean> : callback which return the request ack
     */
	public ConfirmResponseBean getConfirmResponse(
			String _operation, 
			ConfirmRequestBean _parameters, 
		    boolean _webAccessMode);
	
} // class
