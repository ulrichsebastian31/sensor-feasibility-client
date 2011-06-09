package com.astrium.faceo.client.rpc.programming.sps2;

import com.astrium.faceo.client.bean.programming.sps2.describeTasking.DescribeTaskingRequestBean;
import com.astrium.faceo.client.bean.programming.sps2.describeTasking.DescribeTaskingResponseBean;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * 
 * The DescribeTasking operation allows SPS clients to retrieve 
 * a description of the data structures for the tasking parameters 
 * of a given sensor. 
 * Such a data structure description is encoded in SWE Common 
 * (see clause 7.4 ―SPS tasking parameters representation).
 * 
 * @author ASTRIUM
 *
 */
@RemoteServiceRelativePath("getDescribeTaskingController.srv")
public interface DescribeTaskingService extends RemoteService {

	/**
	 * 
	 * @author ASTRIUM
	 *
	 */
	public static class Util {
		/**
		 * 
		 * @return GWT.create(DescribeTaskingService.class)
		 */
		public static DescribeTaskingServiceAsync getInstance() {
			return GWT.create(DescribeTaskingService.class);
		}
	}
	
	/**
     * This method returns the result for a 'DescribeTasking' SPS operation for one sensor
     * 
     * @param _operation (String)						: SPS 2.0 'DesccribeTasking' operation
     * @param _parameters (DescribeTaskingRequestBean)	: 'DesccribeTasking' parameters for one sensor
     * @param _webAccessMode (boolean)					: true for using web services, false else (for using xml files)
     * 
     * @return DescribeTaskingResponseBean : 'DescribeTasking' response in HTML format
     */
	public DescribeTaskingResponseBean getDescribeTaskingResult(
			String _operation, DescribeTaskingRequestBean _parameters, boolean _webAccessMode);
}
