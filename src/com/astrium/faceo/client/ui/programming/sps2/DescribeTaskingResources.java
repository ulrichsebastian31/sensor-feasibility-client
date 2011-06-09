package com.astrium.faceo.client.ui.programming.sps2;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;

/**
 * @author ASTRIUM
 *
 *
 * 27/05/2010
 */
public interface DescribeTaskingResources extends ClientBundle {
	
	/**
	 * 
	 */
	DescribeTaskingResources INSTANCE = GWT.create(DescribeTaskingResources.class);

	/**
	 * @return TextResource
	 */
	@Source("resources/describeTaskingResponses/DescribeTaskingParameters_SAR2.xml")
	TextResource parametersSAR2();

	/**
	 * @return TextResource
	 */
	@Source("resources/describeTaskingResponses/DescribeTaskingParameters_OPT2.xml")
	TextResource parametersOPT2();

	/**
	 * @return TextResource
	 */
	@Source("resources/describeTaskingResponses/DescribeTaskingParameters_SAR1.xml")
	TextResource parametersSAR1();

	/**
	 * @return TextResource
	 */
	@Source("resources/describeTaskingResponses/DescribeTaskingParameters_OPT1.xml")
	TextResource parametersOPT1();

	/**
	 * @return TextResource
	 */
	@Source("resources/describeTaskingResponses/DescribeTaskingParameters_SPOT5.xml")
	TextResource parametersSPOT5();

//	@Source("resources/describeTaskingResponses/DescribeTaskingResponse_SAR1.xml")
//	ExternalTextResource asynchronous();

} // class
