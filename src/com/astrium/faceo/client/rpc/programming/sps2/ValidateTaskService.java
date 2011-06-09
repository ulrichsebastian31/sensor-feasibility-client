package com.astrium.faceo.client.rpc.programming.sps2;

import com.astrium.faceo.client.bean.programming.sps2.validate.ValidateRequestBean;
import com.astrium.faceo.client.bean.programming.sps2.validate.ValidateResponseBean;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * Several acquisition attempts are sometimes necessary to obtain a satisfying result 
 * (case of optical satellites on zones with cloudy tendency for example). 
 * The Validate operation can be used by the customer to indicate to the server 
 * that an acquisition is satisfactory and thus to stop collecting new images for this area.
 * 
 * @author ASTRIUM
 *
 */
@RemoteServiceRelativePath("getValidateTaskController.srv")
public interface ValidateTaskService extends RemoteService {

	/**
	 * 
	 * @author ASTRIUM
	 *
	 */
	public static class Util {
		/**
		 * 
		 * @return ValidateTaskService
		 */
		public static  ValidateTaskServiceAsync getInstance() {
			return GWT.create(ValidateTaskService.class);
		}
	}

	/**
     * This method send a SPS request 
     * with the 'Validate' operation
     * 
     * @param _operation (String) 				: operation ('Validate')
     * @param _parameters (ValidateRequestBean)	: Validate parameters
     * @param _webAccessMode (boolean)			: true for using web services, false else (for using xml files)
     * 
     * @return AsyncCallback<ValidateResponseBean> : callback which return the request ack
     */
	public ValidateResponseBean getValidateResponse(
			String _operation, 
			ValidateRequestBean _parameters, 
		    boolean _webAccessMode);
	
} // class
