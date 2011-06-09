package com.astrium.faceo.server.rpc.programming.sps2;

import java.util.HashMap;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.astrium.faceo.client.bean.programming.sps2.SensorBean;
import com.astrium.faceo.client.bean.programming.sps2.TaskBean;
import com.astrium.faceo.client.bean.programming.sps2.getStatus.GetStatusRequestBean;
import com.astrium.faceo.client.common.programming.sps2.Sps2GeneralConstants;
import com.astrium.faceo.server.service.programming.sps2.FacadeTasks;
import com.astrium.faceo.server.service.programming.sps2.FacadeThreadUpdateTask;

/**
 * Class for the thread for Taks updating
 *
 * @author Astrium
 * @version 1
 */

public class UpdateTasksThread extends Thread {	

	/** Le logger de cette classe */
	private static Logger log = Logger.getLogger(UpdateTasksThread.class);

	String ORIGINE = "UpdateTasksThread";

	private boolean runThread =true;

	@SuppressWarnings("unused")
	private String userId = "";
	private String serverURL = "";
	private static Map<String, SensorBean> sensors = 
		new HashMap<String, SensorBean>();

	/**
	 * Construtor of the class
	 * 
	 * @param _userId 
	 * @param _serverURL 
	 * @param _sensors 
	 * 
	 */
	public UpdateTasksThread(String _userId, String _serverURL, Map<String, SensorBean> _sensors) {
		userId = _userId;
		serverURL = _serverURL;
		sensors = _sensors;

		for(Entry<String, SensorBean> _sensors1 : sensors.entrySet()) {
			SensorBean sensor = _sensors1.getValue();
			log.info("\n" + ORIGINE + " : UpdateTasksThread : sensor URN : " + sensor.getUrn());
			log.info("\n" + ORIGINE + " : UpdateTasksThread : sensor EndPoint: " + sensor.getEndPoint());
		} // for
	}

	/**
	 * Run the thread
	 *            
	 */	
	public void run() {
		while(runThread) {
			try {
				Map<String, TaskBean> tasks = new HashMap<String, TaskBean>();
				
				// lecture d'une propriete stockee dans un fichier de properties
				long sleepTimeThread = 60000;
				try {
					PropertyResourceBundle prb = (PropertyResourceBundle)PropertyResourceBundle.getBundle(Sps2GeneralConstants.HMAFO_PROPERTIES);
					sleepTimeThread = Long.parseLong(prb.getString("sleepTimeThread").trim());
					sleepTimeThread = (sleepTimeThread < 60000) ? 60000 : sleepTimeThread;
					log.debug("\n" + ORIGINE + " : >>> Reading file '" + Sps2GeneralConstants.HMAFO_PROPERTIES + "' OK !!!");    
				} catch (Exception e) {
					log.info("\n" + ORIGINE + " : >>> Reading file '" + Sps2GeneralConstants.HMAFO_PROPERTIES + "' KO !!!");    
				}
				log.debug("\n ------------------------------------------------ ");
				log.debug("\n" + ORIGINE + " : UpdateTasksThread : sleepTimeThread : " + sleepTimeThread);
				log.debug("\n ------------------------------------------------ ");


				log.debug("\n" + ORIGINE + " : run " );    
				FacadeTasks facadeTasks = new FacadeTasks();
				tasks = facadeTasks.loadAllUnFinishedTasks();

				FacadeThreadUpdateTask facadeThreadUpdateTask = 
					new FacadeThreadUpdateTask();

				for(Entry<String, TaskBean> task : tasks.entrySet()) {
					TaskBean taskBean = task.getValue();

					GetStatusRequestBean parameters = new GetStatusRequestBean();
					parameters.setTaskId(taskBean.getId());
					parameters.setTaskUser(taskBean.getUser());
					parameters.setSensorUrn(taskBean.getSensor());

					facadeThreadUpdateTask.updateTaskStatus(parameters, serverURL, sensors);
				} // for(Entry<String, TaskBean> task : tasks.entrySet()) {

				try{
					Thread.sleep(sleepTimeThread); // sleep 60 seconds by default
				}catch (InterruptedException exception) {
					log.info("\n" + ORIGINE + " : InterruptedException : " + exception.getLocalizedMessage());    
				} catch (Exception exception) {
					log.info("\n" + ORIGINE + " : Sleep : Exception : " + exception.getLocalizedMessage());    
				}
			}catch (Exception exception) {
				log.info("\n" + ORIGINE + " : Exception : " + exception.getLocalizedMessage());    
			} // try {

		} // while(runThread) {

	} //public void run() {

} // class