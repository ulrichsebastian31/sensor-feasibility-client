package com.astrium.faceo.client.rpc.programming.sps2;

import com.astrium.faceo.client.bean.programming.sps2.request.ProgrammingRequestBean;
import com.astrium.faceo.client.bean.programming.sps2.responses.TaskingResponseBean;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * 
 * The Reserve operation allows SPS clients to reserve a task. 
 * Reserving a task, except that the task is NOT perfored until the client sends a
 * Confirm request.
 * The reserve operation is part of the TransactionalSensorPlanner interface.
 * A reservation can be cancelled sending a Cancel request at any time.
 * 
 * @author ASTRIUM
 *
 */
public interface ReserveTaskServiceAsync {

	/**
     * This method send a SPS request 
     * with the 'Reserve' operation
     * 
     * @param _operation (String) 					: operation ('Reserve')
     * @param _parameters (ProgrammingRequestBean)	: Reserve parameters
     * @param _webAccessMode (boolean)				: true for using web services, false else (for using xml files)
     * 
     * @param _callback (TaskingResponseBean) : callback which return the request ack
     */
	public void getReserveResponse(
			String _operation, 
			ProgrammingRequestBean _parameters, 
		    boolean _webAccessMode, AsyncCallback<TaskingResponseBean> _callback);
	
} // class
