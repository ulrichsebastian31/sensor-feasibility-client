package com.astrium.faceo.server.rpc.programming.sps2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.astrium.faceo.client.bean.programming.sps2.TaskBean;
import com.astrium.faceo.client.common.programming.sps2.Sps2GeneralConstants;
import com.astrium.faceo.client.rpc.programming.sps2.GetUpdatedTasksListService;
import com.astrium.faceo.server.common.exception.sps2.FacadeSps2Exception;
import com.astrium.faceo.server.service.programming.sps2.FacadeTasks;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Server side implementation of the GetUpdatedTasksListService interface.
 * 
 * @author ASTRIUM
 *
 */
public class GetUpdatedTasksListServiceImpl extends RemoteServiceServlet implements GetUpdatedTasksListService {


	/**
	 * 
	 */
	private static final long serialVersionUID = -7626097100652575732L;

	/** Le logger de cette classe */
	private static Logger log = Logger.getLogger(GetUpdatedTasksListServiceImpl.class);

	String INFOS_CLASSE = "GetUpdatedTasksListService";

	/**
	 * @param _request (HttpServletRequest)		: the request
	 * @param _response (HttpServletResponse)	: the response
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doGet (HttpServletRequest _request, HttpServletResponse _response)
	throws ServletException, IOException {
	}

	/**
	 * The annotation indicates that the returned List will only 
	 * contain objects of  type <com.astrium.faceo.client.bean.programming.sps2.TaskBean>
	 * 
	 * @param _idUser (String)			: id User
	 * @param _operation (String)		: operation 'getUpdatedTasksList'
	 *
	 * @return Map<String, TaskBean> 	: HashMap of tasks
	 * 
	 * @gwt.typeArgs <com.astrium.faceo.client.bean.programming.sps2.TaskBean>
	 */
	public Map<String, TaskBean> getUpdatedTasksList(String _operation, String _idUser) {

		String METHOD = "getModifiedTasksList";

		Map<String, TaskBean> tasksBean = new HashMap<String, TaskBean>();

		// on retourne les donnees sous la forme JSON (JavaScript Object Notation)
		String messageErreur = null;
		int code_erreur = 0;

		log.debug("\n" + INFOS_CLASSE + " : operation : " + _operation );    
		if (_operation == null){
			messageErreur = "undefined operation";
			code_erreur = 531;
		} else {
			if (!_operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_GET_UPDATED_TASKS_LIST)){
				messageErreur = "bad operation";
				code_erreur = 534;
			}
			if (_idUser == null) {
				messageErreur = "undefined idUser";
				code_erreur = 535;
			}
		} // if (operation == null){

		// redirection vers la page d'erreur, "action inconnue"
		if (messageErreur != null ) {
			// retourne le message d'erreur
			log.debug("\n" + INFOS_CLASSE + " : pb : " + messageErreur);    
			TaskBean taskBean = new TaskBean();
			taskBean.getError().setMessage("error : " + messageErreur);
			taskBean.getError().setCode(code_erreur);
			tasksBean.put("0", taskBean);

			return tasksBean;
		} // if (messageErreur != null ) {

		try {
			log.debug("\n" + INFOS_CLASSE + " : " + METHOD + " : _idUser : " + _idUser);    

			FacadeTasks facadeTasks = new FacadeTasks();
			tasksBean = facadeTasks.getUpdatedTasksList(_idUser);

			log.debug("\n" + INFOS_CLASSE + " : tasksBean size : " + tasksBean.size()); 

			List<String> cles = new ArrayList<String>(tasksBean.keySet());
			Collections.sort(cles, new CreationDatesComparator(tasksBean));

			// reconstitution du tableau en fonction de la liste des cles triees
			Map<String, TaskBean> sortedTasks = 
				new HashMap<String, TaskBean>();

			for(String id : cles){
				TaskBean taskBean = tasksBean.get(id);
				sortedTasks.put(id, taskBean);
			} // for
			return sortedTasks;
		} catch (FacadeSps2Exception exception) {
			log.info("\n" + INFOS_CLASSE + " : getTasksList : FacadeSps2Exception : " + exception.getMessage());
			return tasksBean;
		} catch (Exception exception) {
			TaskBean taskBean = new TaskBean();
			taskBean.getError().setMessage("Exception : " + exception.getMessage());
			taskBean.getError().setCode(FacadeSps2Exception.SPS_EXCEPTION);
			tasksBean.put("0", taskBean);
			return tasksBean;
		} // try { 		

	} // public Map<String, TaskBean> getUpdatedTasksList(String _operation, String _idUser) {

	// compare creation dates
	private class CreationDatesComparator implements Comparator<String>{

		private Map<String, TaskBean> tasks; // keep a Map copy

		public CreationDatesComparator(Map<String, TaskBean> _tasks){
			this.tasks = _tasks; // store the copy to use it in compare()
		}

		public int compare(String _id1, String _id2){
			//			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS"); 

			// get Map answers by their identifier
			TaskBean task1 = this.tasks.get(_id1);
			TaskBean task2 = this.tasks.get(_id2);

			//compare the two creation dates.
			Date creation1 = task1.getCreatedTime();	 
			Date creation2 = task2.getCreatedTime();	 

			return creation1.compareTo(creation2);
		}

	} // private class CreationDatesComparator implements Comparator<Date>{

} // class
