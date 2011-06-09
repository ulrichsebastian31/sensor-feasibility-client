package com.astrium.faceo.client.rpc.programming.sps2;

import com.astrium.faceo.client.bean.programming.sps2.getCapabilities.GetCapabilitiesRequestBean;
import com.astrium.faceo.client.bean.programming.sps2.getCapabilities.GetCapabilitiesResponseBean;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * 
 * @author ASTRIUM
 *
 */
@RemoteServiceRelativePath("getCapabilitiesServiceController.srv")
public interface GetCapabilitiesService extends RemoteService {

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
	public static class Util {
		/**
		 * 
		 * @return GWT.create(GetCapabilitiesService.class)
		 */
		public static GetCapabilitiesServiceAsync getInstance() {
			return GWT.create(GetCapabilitiesService.class);
		}
	}

	/**
	 * This method returns the result for a 'GetCapabilities' SPS operation for one sensor
	 * 
     * @param _operation (String)						: SPS 2.0 'GetCapabilities' operation
	 * @param _parameters (GetCapabilitiesRequestBean)	: 'GetCapabilities' parameters for one sensor
	 * @param _webAccessMode (boolean)					: true for using web services, false else (for using xml files)
	 * 
	 * @return GetCapabilitiesResponseBean : 'GetCapabilities' response in HTML format
	 */
	public GetCapabilitiesResponseBean getGetCapabilitiesResult(
			String _operation, GetCapabilitiesRequestBean _parameters, boolean _webAccessMode);
}
