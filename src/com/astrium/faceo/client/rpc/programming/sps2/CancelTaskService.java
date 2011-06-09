package com.astrium.faceo.client.rpc.programming.sps2;

import com.astrium.faceo.client.bean.programming.sps2.cancel.CancelRequestBean;
import com.astrium.faceo.client.bean.programming.sps2.cancel.CancelResponseBean;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * 
 * The Cancel operation allows SPS clients to cancel an accepted task (see clause 6.3.6). 
 * The service may reject the cancellation for specific reason. 
 * The response should indicate why the cancellation did not succeed. 
 * If the cancellation was rejected, the task remains in its current state.
 * 
 * @author ASTRIUM
 *
 */
@RemoteServiceRelativePath("getCancelController.srv")
public interface CancelTaskService extends RemoteService {

	/**
	 * 
	 * @author ASTRIUM
	 *
	 */
	public static class Util {
		/**
		 * 
		 * @return CancelTaskServiceAsync
		 */
		public static  CancelTaskServiceAsync getInstance() {
			return GWT.create(CancelTaskService.class);
		}
	}

	/**
     * This method send a SPS request 
     * with the 'Cancel' operation
     * 
     * @param _operation (String) 				: operation ('Cancel')
     * @param _parameters (CancelRequestBean)	: Cancel parameters
     * @param _webAccessMode (boolean)			: true for using web services, false else (for using xml files)
     * 
     * @return AsyncCallback<CancelResponseBean> : callback which return the request ack
     */
	public CancelResponseBean getCancelResponse(
			String _operation, 
			CancelRequestBean _parameters, 
		    boolean _webAccessMode);
	
} // class
