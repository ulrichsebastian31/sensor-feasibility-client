package com.astrium.faceo.client.rpc.programming.sps2;

import com.astrium.faceo.client.bean.programming.sps2.confirm.ConfirmRequestBean;
import com.astrium.faceo.client.bean.programming.sps2.confirm.ConfirmResponseBean;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * 
 * The Confirm operation allows SPS clients to confirm a previously reserved task. 
 * If accepted i;e; if task has'nt expired yet, the task shall transit from state reserved
 * to inExecution, otherwise the SPS shall reject the request
 * 
 * @author ASTRIUM
 *
 */
public interface ConfirmTaskServiceAsync {

	/**
     * This method send a SPS request 
     * with the 'Confirm' operation
     * 
     * @param _operation (String) 				: operation ('Confirm')
     * @param _parameters (ConfirmRequestBean)	: Confirm parameters
     * @param _webAccessMode (boolean)			: true for using web services, false else (for using xml files)
     * 
     * @param _callback (ConfirmResponseBean) : callback which return the request ack
     */
	public void getConfirmResponse(
			String _operation, 
			ConfirmRequestBean _parameters, 
		    boolean _webAccessMode, AsyncCallback<ConfirmResponseBean> _callback);
	
} // class
