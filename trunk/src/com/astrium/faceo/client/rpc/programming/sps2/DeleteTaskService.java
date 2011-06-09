package com.astrium.faceo.client.rpc.programming.sps2;

import com.astrium.faceo.client.bean.programming.sps2.ErrorBean;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * 
 * @author ASTRIUM
 *
 */
@RemoteServiceRelativePath("deleteTaskController.srv")
public interface DeleteTaskService extends RemoteService {

	/**
	 * 
	 * @author ASTRIUM
	 *
	 */
	public static class Util {
		/**
		 * 
		 * @return GWT.create(DeleteTaskService.class)
		 */
		public static DeleteTaskServiceAsync getInstance() {
			return GWT.create(DeleteTaskService.class);
		}
	}
	
	/**
     * This method delete of a SPS task in database 
     * 
     * @param _idUser (String)		: user identifier
     * @param _idTask (String)		: task identifier
     * @param _operation (String)	: operation of the FACEO service ('DeleteTask')
     * 
     * @return ErrorBean			: to get the code and the message error if operation is KO
     */
	public ErrorBean deleteTask(String _operation, String _idUser, String _idTask);
	
}
