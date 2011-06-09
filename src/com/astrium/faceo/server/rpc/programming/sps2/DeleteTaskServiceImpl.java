package com.astrium.faceo.server.rpc.programming.sps2;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.astrium.faceo.client.bean.programming.sps2.ErrorBean;
import com.astrium.faceo.client.common.programming.sps2.Sps2GeneralConstants;
import com.astrium.faceo.client.rpc.programming.sps2.DeleteTaskService;
import com.astrium.faceo.server.common.exception.sps2.FacadeSps2Exception;
import com.astrium.faceo.server.service.programming.sps2.FacadeTasks;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Server side implementation of the DeleteTaskService interface.
 * 
 * @author ASTRIUM
 *
 */public class DeleteTaskServiceImpl extends RemoteServiceServlet implements DeleteTaskService {

	 /**
	  * 
	  */
	 private static final long serialVersionUID = 6139090365066302368L;

	 /** Le logger de cette classe */
	 private static Logger log = Logger.getLogger(DeleteTaskServiceImpl.class);

	 String INFOS_CLASSE = "DeleteTaskServiceImpl";

	 /**
	  *  traitement de la requete HTTP GET
	  *  
	  * @param _request (HttpServletRequest)		: the request
	  * @param _response (HttpServletResponse)	: the response
	  *  
	  *  @throws ServletException
	  * 	@throws IOException
	  */
	 public void doGet (HttpServletRequest _request, HttpServletResponse _response)
	 throws ServletException, IOException {

	 }

	 /* (non-Javadoc)
	  * @see com.astrium.faceo.client.rpc.programming.sps2.DeleteTaskService#deleteTask(java.lang.String, java.lang.String, java.lang.String)
	  */

	 /**
	  * The annotation indicates that the returned List will only 
	  * contain objects of  type <com.astrium.faceo.bean.client.bean.programming.sps2.ErrorBean>
	  * 
	  * This method delete of a SPS task in database 
	  * 
	  * @param _userId (String)		: user identifier
	  * @param _taskId (String)		: task identifier
	  * @param _operation (String)	: operation of the FACEO service ('DeleteTask')
	  * 
	  * @return ErrorBean			: to get the code and the message error if operation is KO
	  * 
	  * @gwt.typeArgs <com.astrium.faceo.bean.client.bean.programming.sps2.ErrorBean>
	  */
	 public ErrorBean deleteTask(String _operation, String _userId, String _taskId) {

		 ErrorBean errorBean = new ErrorBean();
		 errorBean.setError(false);

		 String messageErreur = null;
		 int code_erreur = 0;

		 log.debug("\n" + INFOS_CLASSE + " : operation : " + _operation );    
		 if (_operation == null){
			 messageErreur = "undefined operation";
			 code_erreur = 531;
		 } else {
			 if (!_operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_DELETE_TASK)){
				 messageErreur = "bad operation";
				 code_erreur = 534;
			 }
			 if (_userId == null) {
				 messageErreur = "undefined User Id";
				 code_erreur = 535;
			 }
			 if (_taskId == null) {
				 messageErreur = "undefined task Id";
				 code_erreur = 536;
			 }
		 } // if (operation == null){

		 // redirection vers la page d'erreur, "action inconnue"
		 if (messageErreur != null ) {
			 // retourne le message d'erreur
			 log.debug("\n" + INFOS_CLASSE + " : error : " + messageErreur);
			 errorBean.setError(true);
			 errorBean.setMessage("error : " + messageErreur);
			 errorBean.setCode(code_erreur);

			 return errorBean;
		 } // if (messageErreur != null ) {

		 try {
			 log.debug("\n" + INFOS_CLASSE + " : _idUser : " + _userId);    

			 FacadeTasks facadeProgramming = new FacadeTasks();
			 errorBean = facadeProgramming.deleteATask(_taskId);

			 return errorBean;
		 } catch (FacadeSps2Exception exception) {
			 log.info("\n" + INFOS_CLASSE + " : deleteTask : FacadeSps2Exception : " + exception.getMessage());
			 return errorBean;
		 } catch (Exception exception) {
			 errorBean.setError(true);
			 errorBean.setMessage("Exception : " + exception.getMessage());
			 errorBean.setCode(FacadeSps2Exception.SPS_EXCEPTION);
			 return errorBean;
		 } // try { 	

	 } // public ErrorBean deleteTask(String _operation, String _userId, String _taskId) {

 } // class
