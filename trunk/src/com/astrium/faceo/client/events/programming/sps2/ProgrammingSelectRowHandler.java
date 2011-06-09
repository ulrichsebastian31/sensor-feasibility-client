package com.astrium.faceo.client.events.programming.sps2;

import com.google.gwt.event.shared.EventHandler;


/**
 * <B>SITE FACEO</B> <BR>
 * 
 * <P>
 * La classe ProgrammingSelectRowHandler permet l'enregistrement d'un handler de type ProgrammingSelectRowHandler
 * </P>
 * </P>
 * 
 * @author GR
 * @version 1.0, le 05/05/2010
 */
public interface ProgrammingSelectRowHandler extends EventHandler {

	/**
	 * 
	 * 	 
	 * @param _event 
	 */
	public void onSelectRow(ProgrammingSelectRowEvents _event) ;

} // class
