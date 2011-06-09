package com.astrium.faceo.client.rpc.programming.sps2;

import com.astrium.faceo.client.bean.programming.sps2.describeSensor.DescribeSensorRequestBean;
import com.astrium.faceo.client.bean.programming.sps2.describeSensor.DescribeSensorResponseBean;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * 
 * The DescribeSensor operation allows a client to request a detailed description 
 * of a sensor. The request can be targeted at a description that was valid 
 * at a certain point in or during a certain period of time in the past. 
 * 
 * @author ASTRIUM
 *
 */
public interface DescribeSensorServiceAsync {

	/**
     * This method returns the result for a 'DescribeSensor' SPS operation for one sensor
     * 
     * @param _operation (String)						: SPS 2.0 'DescribeSensor' operation
     * @param _parameters (DescribeSensorRequestBean)	: 'DescribeSensor' parameters for one sensor
     * @param _webAccessMode (boolean)					: true for using web services, false else (for using xml files)
     * 
     * @param _callback (DescribeSensorResponseBean) : 'DescribeSensor' response in 'HTML' format
     */
	public void getDescribeSensorResult(
			String _operation, DescribeSensorRequestBean _parameters, boolean _webAccessMode, AsyncCallback<DescribeSensorResponseBean> _callback);
	
} // class
