package com.astrium.faceo.client.rpc.programming.sps2;

import com.astrium.faceo.client.bean.programming.sps2.validate.ValidateRequestBean;
import com.astrium.faceo.client.bean.programming.sps2.validate.ValidateResponseBean;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Several acquisition attempts are sometimes necessary to obtain a satisfying result 
 * (case of optical satellites on zones with cloudy tendency for example). 
 * The Validate operation can be used by the customer to indicate to the server 
 * that an acquisition is satisfactory and thus to stop collecting new images for this area.
 * 
 * @author ASTRIUM
 *
 */
public interface ValidateTaskServiceAsync {

	/**
     * This method send a SPS request 
     * with the 'Validate' operation
     * 
     * @param _operation (String) 				: operation ('Validate')
     * @param _parameters (ValidateRequestBean)	: Validate parameters
     * @param _webAccessMode (boolean)			: true for using web services, false else (for using xml files)
     * 
     * @param _callback (ValidateResponseBean) : callback which return the request ack
     */
	public void getValidateResponse(
			String _operation, 
			ValidateRequestBean _parameters, 
		    boolean _webAccessMode, AsyncCallback<ValidateResponseBean> _callback);
	
} // class
