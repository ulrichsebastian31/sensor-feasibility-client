package com.astrium.faceo.client.rpc.programming.sps2;

import java.util.Map;

import com.astrium.faceo.client.bean.programming.sps2.TaskBean;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * 
 * @author ASTRIUM
 *
 */
@RemoteServiceRelativePath("getTasksListController.srv")
public interface GetTasksListService extends RemoteService {

	/**
	 * 
	 * @author ASTRIUM
	 *
	 */
	public static class Util {
		/**
		 * 
		 * @return GetTasksListServiceAsync
		 */
		public static GetTasksListServiceAsync getInstance() {
			return GWT.create(GetTasksListService.class);
		}
	}

	/**
     * This method returns a list with the SPS tasks of one user 
     * with the 'GetTasksList' operation
     * 
     * @param _operation (String)	: operation of the SPS service ('GetTasksList')
     * @param _idUser (String)		: id of the user
     * @param _webAccessMode (String)	: true for using web services, false else (for using kml files)
     * 
     * @return Map<String, TaskBean> : HashMap of tasks
     */
	public Map<String, TaskBean> getTasksList(
			String _operation, String _idUser, boolean _webAccessMode);

}
