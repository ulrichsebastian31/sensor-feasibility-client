package com.astrium.faceo.server.rpc.programming.sps2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PropertyResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.astrium.faceo.client.bean.programming.sps2.TaskBean;
import com.astrium.faceo.client.common.programming.sps2.Sps2GeneralConstants;
import com.astrium.faceo.client.rpc.programming.sps2.GetTasksListService;
import com.astrium.faceo.server.common.exception.sps2.FacadeSps2Exception;
import com.astrium.faceo.server.service.programming.sps2.FacadeTasks;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Server side implementation of the GetTasksListService interface.
 * 
 * @author ASTRIUM
 *
 */
public class GetTasksListServiceImpl extends RemoteServiceServlet implements GetTasksListService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6337329322867567780L;

	/** Le logger de cette classe */
	private static Logger log = Logger.getLogger(GetTasksListServiceImpl.class);

	String INFOS_CLASSE = "GetTasksListServiceImpl";

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
	 * @param _operation (String)		: operation 'GetTasksList'
	 * @param _webAccessMode (String)	: true for using web services, false else (for using XML files)
	 *
	 * @return Map<String, TaskBean> 	: HashMap of tasks
	 * 
	 * @gwt.typeArgs <com.astrium.faceo.client.bean.programming.sps2.TaskBean>
	 */
	public Map<String, TaskBean> getTasksList(String _operation, String _idUser, boolean _webAccessMode) {

		Map<String, TaskBean> tasksBean = new HashMap<String, TaskBean>();

		// on retourne les donnees sous la forme JSON (JavaScript Object Notation)
		String messageErreur = null;
		int code_erreur = 0;

		log.debug("\n" + INFOS_CLASSE + " : operation : " + _operation );    
		if (_operation == null){
			messageErreur = "undefined operation";
			code_erreur = 531;
		} else {
			if (!_operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_GET_TASKS_LIST)){
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
			//
			//			HttpServletRequest request = this.getThreadLocalRequest();
			//			// recuperation de la session de l'utilisateur
			//			HttpSession session = request.getSession();
			//			session.setMaxInactiveInterval(GeneralConstant.MAX_INACTIVE_INTERVAL_SESSION); // 1 heure 30 soit 5 400 secondes
			//			session.removeAttribute("tasksBean");
			//			session.setAttribute("tasksBean", tasksBean);

			return tasksBean;
		} // if (messageErreur != null ) {

		try {
			log.debug("\n" + INFOS_CLASSE + " : _idUser : " + _idUser);    

			// lecture d'une propriete stockee dans un fichier de properties
			String simulationProgramming = Sps2GeneralConstants.FALSE;
			try {
				PropertyResourceBundle prb = (PropertyResourceBundle)PropertyResourceBundle.getBundle(Sps2GeneralConstants.HMAFO_PROPERTIES);
				simulationProgramming = prb.getString("simulationProgramming").trim();
				log.debug("\n" + INFOS_CLASSE + " : >>> Reading file '" + Sps2GeneralConstants.HMAFO_PROPERTIES + "' OK !!!");
			} catch (Exception e) {
				log.info("\n" + INFOS_CLASSE + " : >>> Reading file '" + Sps2GeneralConstants.HMAFO_PROPERTIES + "' KO !!!");
			}
			log.debug("\n ------------------------------------------------ ");  
			log.debug("\n" + INFOS_CLASSE + " : simulationProgramming : " + simulationProgramming);
			log.debug("\n ------------------------------------------------ ");  

			log.debug("\n" + INFOS_CLASSE + " : facadeProgramming.getTasksList "); 

			// lecture d'un fichier XML contenant une liste de demandes
			HttpServletRequest request1 = this.getThreadLocalRequest();
			String requestURL = request1.getRequestURL().toString();
			int pos = requestURL.lastIndexOf(Sps2GeneralConstants.GET_TASKS_LIST_CONTROLLER);
			String serverURL = requestURL;
			if (pos >0) {
				serverURL = requestURL.substring(0, pos);
			}

			if (_webAccessMode) {
				if (simulationProgramming.equalsIgnoreCase(Sps2GeneralConstants.FALSE)) {
					log.debug("\n" + INFOS_CLASSE + " : facadeProgramming.getTasksList " );    
					FacadeTasks facadeTasks = new FacadeTasks();
					tasksBean = facadeTasks.getTasksList(_idUser);

					log.debug("\n" + INFOS_CLASSE + " : tasksBean size : " + tasksBean.size()); 

					String keyError = "0";
					if ( (tasksBean.size() == 1) && (tasksBean.containsKey(keyError)) ) {
						// key with 0 value : error
						TaskBean taskBean = tasksBean.get(keyError);
						if (taskBean != null) {
							tasksBean.remove(keyError);

							// get the tasks list in a XML file
							tasksBean = getSimulateTasksResults(serverURL);
						}  
					} else if (tasksBean.size() > 1) { // descending sort of the tasks on creation date
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
					}
				} else {
					log.debug("\n" + INFOS_CLASSE + " : getSimulationValues");    
					tasksBean = getSimulateTasksResults(serverURL);
				} // if (simulationProgramming.equalsIgnoreCase(Constant.FALSE)) {
			} else {
				log.debug("\n" + INFOS_CLASSE + " : getSimulationValues");    
				tasksBean = getSimulateTasksResults(serverURL);
			} // if (_webAccessMode) {

			//			HttpServletRequest request = this.getThreadLocalRequest();
			//			// recuperation de la session de l'utilisateur
			//			HttpSession session = request.getSession();
			//			session.removeAttribute("tasksBean");
			//			session.setAttribute("tasksBean", tasksBean);

			//			log.debug("\n" + INFOS_CLASSE + " : session.setAttribute(tasksBean);");    
			log.debug("\n" + INFOS_CLASSE + " : return tasksBean;"); 
			return tasksBean;
		} catch (FacadeSps2Exception exception) {
			log.info("\n" + INFOS_CLASSE + " : getTasksList : FacadeSps2Exception : " + exception.getMessage());
			return tasksBean;
		} catch (NumberFormatException numberFormatException) {
			TaskBean taskBean = new TaskBean();
			taskBean.getError().setMessage("NumberFormatException : " + numberFormatException.getMessage());
			taskBean.getError().setCode(FacadeSps2Exception.SPS_NUMBER_FORMAT_EXCEPTION);
			tasksBean.put("0", taskBean);
			return tasksBean;
		} catch (Exception exception) {
			TaskBean taskBean = new TaskBean();
			taskBean.getError().setMessage("Exception : " + exception.getMessage());
			taskBean.getError().setCode(FacadeSps2Exception.SPS_EXCEPTION);
			tasksBean.put("0", taskBean);
			return tasksBean;
		} // try { 		

	} // public Map<String, TaskBean> getTasksList(String _operation, String _idUser) {

	/**
	 * this method return simulate tasks list
	 * 
	 * @param _serverURL (String ) : the URL server
	 * 
	 * @return Map<String, TaskBean> : the tasks list
	 */
	private Map<String, TaskBean> getSimulateTasksResults(String _serverURL) {

		Map<String, TaskBean> tasksBean = new HashMap<String, TaskBean>();

		try {
			return tasksBean;
		} catch (Exception exception) {
			TaskBean taskBean = new TaskBean();
			taskBean.getError().setMessage("Exception : " + exception.getMessage());
			taskBean.getError().setCode(FacadeSps2Exception.SPS_EXCEPTION);
			tasksBean.put("0", taskBean);
			return tasksBean;
		}
	} // private Map<String, TaskBean> getSimulateTasksResults(String _serverURL) {

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
