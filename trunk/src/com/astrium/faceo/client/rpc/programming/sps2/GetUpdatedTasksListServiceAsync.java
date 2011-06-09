package com.astrium.faceo.client.rpc.programming.sps2;

import java.util.Map;

import com.astrium.faceo.client.bean.programming.sps2.TaskBean;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * 
 * @author ASTRIUM
 *
 */
public interface GetUpdatedTasksListServiceAsync {

	/**
     * This method returns a list with the SPS tasks of one user 
     * with the 'GetUpdatedTasksList' operation
     * 
     * @param _operation (String)		: operation of the SPS service ('getUpdatedTasksList')
     * @param _idUser (String)			: id of the user
     * 
     * @param _callback (Map<String, TaskBean>) : HashMap of tasks
     */
	public void getUpdatedTasksList(
			String _operation, String _idUser, AsyncCallback<Map<String, TaskBean>> _callback);

}
