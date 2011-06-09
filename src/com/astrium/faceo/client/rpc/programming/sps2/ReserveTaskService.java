package com.astrium.faceo.client.rpc.programming.sps2;

import com.astrium.faceo.client.bean.programming.sps2.request.ProgrammingRequestBean;
import com.astrium.faceo.client.bean.programming.sps2.responses.TaskingResponseBean;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

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
@RemoteServiceRelativePath("getReserveTaskController.srv")
public interface ReserveTaskService extends RemoteService {

	/**
	 * 
	 * @author ASTRIUM
	 *
	 */
	public static class Util {
		/**
		 * 
		 * @return ReserveTaskServiceAsync
		 */
		public static  ReserveTaskServiceAsync getInstance() {
			return GWT.create(ReserveTaskService.class);
		}
	}

	/**
     * This method send a SPS request 
     * with the 'Reserve' operation
     * 
     * @param _operation (String) 					: operation ('Reserve')
     * @param _parameters (ProgrammingRequestBean)	: Reserve parameters
     * @param _webAccessMode (boolean)				: true for using web services, false else (for using xml files)
     * 
     * @return AsyncCallback<TaskingResponseBean> : callback which return the request ack
     */
	public TaskingResponseBean getReserveResponse(
			String _operation, 
			ProgrammingRequestBean _parameters, 
		    boolean _webAccessMode);
	
} // class
