package com.astrium.faceo.client.rpc.programming.sps2;

import com.astrium.faceo.client.bean.programming.sps2.TaskResultBean;
import com.astrium.faceo.client.bean.programming.sps2.request.TaskingParametersBean;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * 
 * @author ASTRIUM
 *
 */
@RemoteServiceRelativePath("getTaskResultController.srv")
public interface GetTaskResultService extends RemoteService {

	/**
	 * 
	 * @author ASTRIUM
	 *
	 */
	public static class Util {
		/**
		 * 
		 * @return GWT.create(GetTaskResultService.class)
		 */
		public static GetTaskResultServiceAsync getInstance() {
			return GWT.create(GetTaskResultService.class);
		}
	}
	
	/**
     * This method returns the detail of a SPS task of one user in database
     * an with the 'GetStatus' operation
     * 
     * @param _operation (String)		: operation of the FACEO service ('GetTaskResult')
     * @param _idUser (String)			: id User
     * @param _idTask (String)			: id of the task
     * @param _taskingParameters (TaskingParametersBean)	: tasking parameters for one sensor
     * @param _webAccessMode (boolean)	: true for using web services, false else (for using xml files)
     * 
     * @return TaskResultBean : a task
     */
	public TaskResultBean getTaskResult(
			String _operation, String _idUser, String _idTask, TaskingParametersBean _taskingParameters, boolean _webAccessMode);
	
}
