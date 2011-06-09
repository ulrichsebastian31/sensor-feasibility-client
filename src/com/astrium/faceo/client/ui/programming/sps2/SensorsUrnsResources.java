package com.astrium.faceo.client.ui.programming.sps2;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;

/**
 * @author ASTRIUM
 *
 *
 * 06/07/2010
 */
public interface SensorsUrnsResources extends ClientBundle {
	
	/**
	 * 
	 */
	SensorsUrnsResources INSTANCE = GWT.create(SensorsUrnsResources.class);

	/**
	 * @return TextResource
	 */
	@Source("resources/sensors.xml")
	TextResource sensorsUrl();

} // class
