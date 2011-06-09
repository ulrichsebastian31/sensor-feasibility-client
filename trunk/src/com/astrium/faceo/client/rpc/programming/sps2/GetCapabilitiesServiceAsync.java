package com.astrium.faceo.client.rpc.programming.sps2;

import com.astrium.faceo.client.bean.programming.sps2.getCapabilities.GetCapabilitiesRequestBean;
import com.astrium.faceo.client.bean.programming.sps2.getCapabilities.GetCapabilitiesResponseBean;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * 
 * The mandatory GetCapabilities operation allows clients 
 * to retrieve service metadata from a server. 
 * The response to a GetCapabilities request shall be service metadata 
 * about the server, including specific information about the sensors provided by the service, 
 * supported data encodings and – if supported by the binding employed by the service – 
 * also metadata about the supported notification functionality.
 * 
 * @author ASTRIUM
 *
 */
public interface GetCapabilitiesServiceAsync {

	/**
     * This method returns the result for a 'GetCapabilities' SPS operation for one sensor
     * 
     * @param _operation (String)						: SPS 2.0 'GetCapabilities' operation
	 * @param _parameters (GetCapabilitiesRequestBean)	: 'GetCapabilities' parameters for one sensor
	 * @param _webAccessMode (boolean)					: true for using web services, false else (for using xml files)
     * 
     * @param _callback (GetCapabilitiesResponseBean) : 'GetCapabilities' response in 'HTML' format
     */
	public void getGetCapabilitiesResult(
			String _operation, GetCapabilitiesRequestBean _parameters, boolean _webAccessMode, AsyncCallback<GetCapabilitiesResponseBean> _callback);
	
} // class
