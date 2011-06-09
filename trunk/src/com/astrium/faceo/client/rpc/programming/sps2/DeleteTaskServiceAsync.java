package com.astrium.faceo.client.rpc.programming.sps2;

import com.astrium.faceo.client.bean.programming.sps2.ErrorBean;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * 
 * @author ASTRIUM
 *
 */
public interface DeleteTaskServiceAsync {

	/**
     * This method delete of a SPS task in database 
     * 
     * @param _idUser (String)		: id User
     * @param _idTask (String)		: id of the task
     * @param _operation (String)	: operation of the FACEO service ('DeleteTask')
     * 
     * @param _callback (ErrorBean) : to get the code and the message error if operation is KO
     */
	public void deleteTask(
			String _operation, String _idUser, String _idTask, AsyncCallback<ErrorBean> _callback);
	
}
