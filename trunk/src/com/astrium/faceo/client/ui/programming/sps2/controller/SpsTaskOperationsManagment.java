package com.astrium.faceo.client.ui.programming.sps2.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.astrium.faceo.client.bean.programming.sps2.ErrorBean;
import com.astrium.faceo.client.bean.programming.sps2.SensorBean;
import com.astrium.faceo.client.bean.programming.sps2.TaskResultBean;
import com.astrium.faceo.client.bean.programming.sps2.cancel.CancelRequestBean;
import com.astrium.faceo.client.bean.programming.sps2.cancel.CancelResponseBean;
import com.astrium.faceo.client.bean.programming.sps2.confirm.ConfirmRequestBean;
import com.astrium.faceo.client.bean.programming.sps2.confirm.ConfirmResponseBean;
import com.astrium.faceo.client.bean.programming.sps2.describeResultAccess.DescribeResultAccessRequestBean;
import com.astrium.faceo.client.bean.programming.sps2.describeResultAccess.DescribeResultAccessResponseBean;
import com.astrium.faceo.client.bean.programming.sps2.getStatus.GetStatusRequestBean;
import com.astrium.faceo.client.bean.programming.sps2.getStatus.GetStatusResponseBean;
import com.astrium.faceo.client.bean.programming.sps2.request.ProgrammingRequestBean;
import com.astrium.faceo.client.bean.programming.sps2.request.TaskingParametersBean;
import com.astrium.faceo.client.bean.programming.sps2.responses.SegmentBean;
import com.astrium.faceo.client.bean.programming.sps2.responses.TaskingResponseBean;
import com.astrium.faceo.client.bean.programming.sps2.validate.ValidateRequestBean;
import com.astrium.faceo.client.bean.programming.sps2.validate.ValidateResponseBean;
import com.astrium.faceo.client.common.ConfigConstant;
import com.astrium.faceo.client.common.programming.sps2.Sps2Constants;
import com.astrium.faceo.client.common.programming.sps2.Sps2GeneralConstants;
import com.astrium.faceo.client.rpc.programming.sps2.CancelTaskService;
import com.astrium.faceo.client.rpc.programming.sps2.CancelTaskServiceAsync;
import com.astrium.faceo.client.rpc.programming.sps2.ConfirmTaskService;
import com.astrium.faceo.client.rpc.programming.sps2.ConfirmTaskServiceAsync;
import com.astrium.faceo.client.rpc.programming.sps2.DeleteTaskService;
import com.astrium.faceo.client.rpc.programming.sps2.DeleteTaskServiceAsync;
import com.astrium.faceo.client.rpc.programming.sps2.DescribeResultAccessService;
import com.astrium.faceo.client.rpc.programming.sps2.DescribeResultAccessServiceAsync;
import com.astrium.faceo.client.rpc.programming.sps2.GetFeasibilityService;
import com.astrium.faceo.client.rpc.programming.sps2.GetFeasibilityServiceAsync;
import com.astrium.faceo.client.rpc.programming.sps2.GetTaskResultService;
import com.astrium.faceo.client.rpc.programming.sps2.GetTaskResultServiceAsync;
import com.astrium.faceo.client.rpc.programming.sps2.GetTaskStatusService;
import com.astrium.faceo.client.rpc.programming.sps2.GetTaskStatusServiceAsync;
import com.astrium.faceo.client.rpc.programming.sps2.ReserveTaskService;
import com.astrium.faceo.client.rpc.programming.sps2.ReserveTaskServiceAsync;
import com.astrium.faceo.client.rpc.programming.sps2.SubmitService;
import com.astrium.faceo.client.rpc.programming.sps2.SubmitServiceAsync;
import com.astrium.faceo.client.rpc.programming.sps2.UpdateTaskService;
import com.astrium.faceo.client.rpc.programming.sps2.UpdateTaskServiceAsync;
import com.astrium.faceo.client.rpc.programming.sps2.ValidateTaskService;
import com.astrium.faceo.client.rpc.programming.sps2.ValidateTaskServiceAsync;
import com.astrium.faceo.client.ui.programming.sps2.view.SpsPanels;
import com.astrium.faceo.client.ui.programming.sps2.view.SpsSensorSettingsPanel;
import com.astrium.faceo.client.ui.programming.sps2.view.SpsSettingsPanel;
import com.astrium.faceo.client.ui.programming.sps2.view.SpsSolutionsPanel;
import com.astrium.faceo.client.ui.programming.sps2.view.SpsTasksPanel;
import com.astrium.faceo.client.ui.programming.sps2.windows.WindowCancel;
import com.astrium.faceo.client.ui.programming.sps2.windows.WindowConfirm;
import com.astrium.faceo.client.ui.programming.sps2.windows.WindowDescribeResultAccess;
import com.astrium.faceo.client.ui.programming.sps2.windows.WindowGetStatus;
import com.astrium.faceo.client.ui.programming.sps2.windows.WindowValidate;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.grid.GridPanel;

/**
 * <B>SITE FACEO</B> <BR>
 * 
 * <P>
 * La classe SpsTaskOperationsManagment g&egrave;re les op&eacute;rations SPS
 * </P>
 * </P>
 * 
 * @author GR
 * @version 1.0, le 20/08/2010
 */
public class SpsTaskOperationsManagment {

	// internationalisation
	private static final Sps2Constants sps2Constants =
		(Sps2Constants) com.google.gwt.core.client.GWT.create(Sps2Constants.class);

	/**
	 * constructor
	 * 
	 */
	public SpsTaskOperationsManagment() {

	} // public SpsTaskOperationsManagment() {

	/**
	 * send a SPS 2.0 'GetFeasibility' request and show the 'GetFeasibility' response 
	 * for synchronous and asynchronous sensor
	 * 
	 * @param _request (ProgrammingRequestBean)	: 
	 * @param _formSubmitPanel (Panel)			: 
	 */
	public static void sendGetFeasibility(ProgrammingRequestBean _request, final Panel _formSubmitPanel) {
		GetFeasibilityServiceAsync getFeasibilityServiceAsync = 
			GetFeasibilityService.Util.getInstance();

		// Specify the URL at which our service implementation is running.
		// Note that the target URL must reside on the same domain and port
		// from which the host page was served.
		ServiceDefTarget endpoint = (ServiceDefTarget) getFeasibilityServiceAsync;
		String moduleRelativeURL = GWT.getModuleBaseURL() + Sps2GeneralConstants.GET_FEASIBILITY_CONTROLLER;

		endpoint.setServiceEntryPoint(moduleRelativeURL);

		_formSubmitPanel.getEl().mask("send GetFeasibility");

		// Create an Asynchronous callback to handle the result.
		AsyncCallback<TaskingResponseBean> callback = new AsyncCallback<TaskingResponseBean>() {
			public void onSuccess(TaskingResponseBean _response) {
				_formSubmitPanel.getEl().unmask();

				// do some UI stuff to show success
				if (_response == null) {
					com.google.gwt.user.client.Window.alert(sps2Constants.submitOnSuccess() 
							+ " : " + sps2Constants.submitOnSuccessNoResult());
					SpsSettingsPanel.setTaskIdentifier("");
				} else {
					if (!_response.getError().getMessage().equalsIgnoreCase("")) {
						com.google.gwt.user.client.Window.alert("error : " + _response.getError().getMessage());
						SpsSettingsPanel.setTaskIdentifier("");
					} else {
						com.google.gwt.user.client.Window.alert("Infos : " + _response.getStatusReport().getDescription());

						final String taskIdentifier1 = _response.getStatusReport().getTask();
						SpsSettingsPanel.setTaskIdentifier(taskIdentifier1);

						// update sensor Tab panel
						updateSensorPanelTaskOperation(_response);

						if ( (_response.getStatusReport().getStatusCode().equalsIgnoreCase(Sps2GeneralConstants.STATUS_FEASIBLE))
								|| (_response.getStatusReport().getStatusCode().equalsIgnoreCase(Sps2GeneralConstants.STATUS_ACCEPTED))
								|| (_response.getStatusReport().getRequestStatus().equalsIgnoreCase(Sps2GeneralConstants.STATUS_FEASIBLE))
								|| (_response.getStatusReport().getRequestStatus().equalsIgnoreCase(Sps2GeneralConstants.STATUS_ACCEPTED)) ) {
							com.google.gwt.user.client.Window.alert(
									sps2Constants.taskCompletedStatus() + " " + SpsSettingsPanel.getTaskIdentifier());

							TaskResultBean _taskResultBean = new TaskResultBean();
							_taskResultBean.setTaskingResponseBean(_response);

							// results list update
							SpsPanels.getSpsSolutionsPanel().updateGridSegments(_taskResultBean);

							// tasks historical update
							SpsPanels.getSpsTasksPanel().getRefreshButton().fireEvent("click");
						} else if ( (_response.getStatusReport().getStatusCode().equalsIgnoreCase(Sps2GeneralConstants.STATUS_FAILED)) 
								|| (_response.getStatusReport().getRequestStatus().equalsIgnoreCase(Sps2GeneralConstants.STATUS_FAILED)) ) {
							com.google.gwt.user.client.Window.alert(
									sps2Constants.taskFailedStatus() + " " + SpsSettingsPanel.getTaskIdentifier());
						} else if (_response.getStatusReport().getRequestStatus().equalsIgnoreCase(Sps2GeneralConstants.STATUS_PENDING)) {
							// tasks historical update
							SpsPanels.getSpsTasksPanel().getRefreshButton().fireEvent("click");
						} // if
					}
				} // if (results == null) {

			} // public void onSuccess(TaskingResponseBean _response) {

			public void onFailure(Throwable caught) {
				_formSubmitPanel.getEl().unmask();

				com.google.gwt.user.client.Window.alert(sps2Constants.submitOnFailure() +
						" : " + caught.getLocalizedMessage());
			} // public void onFailure(Throwable caught) {
		}; //  AsyncCallback<SPSGetFeasibilityRequestAckBean> callback

		getFeasibilityServiceAsync.getGetFeasibilityResponse(
				Sps2GeneralConstants.SPS_GET_FEASIBILITY, 
				_request,
				SpsTasksPanel.getSpsWebServiceMode(), 
				callback);
	} // public static void sendGetFeasibility(ProgrammingRequestBean _request) {

	/**
	 * send a SPS 2.0 'Submit' request and show the 'Submit' response 
	 * for synchronous and asynchronous sensor
	 * 
	 * @param _programmingRequest (ProgrammingRequestBean)	: 
	 * @param _formSubmitPanel (Panel)						: 
	 */
	public static void sendSubmit(ProgrammingRequestBean _programmingRequest, final Panel _formSubmitPanel) {
		SubmitServiceAsync submitServiceAsync = 
			SubmitService.Util.getInstance();

		// Specify the URL at which our service implementation is running.
		// Note that the target URL must reside on the same domain and port
		// from which the host page was served.
		ServiceDefTarget endpoint = (ServiceDefTarget) submitServiceAsync;
		String moduleRelativeURL = GWT.getModuleBaseURL() + Sps2GeneralConstants.SUBMIT_CONTROLLER;

		endpoint.setServiceEntryPoint(moduleRelativeURL);

		_formSubmitPanel.getEl().mask("send Submit");

		// Create an Asynchronous callback to handle the result.
		AsyncCallback<TaskingResponseBean> callback = new AsyncCallback<TaskingResponseBean>() {
			public void onSuccess(TaskingResponseBean response) {
				_formSubmitPanel.getEl().unmask();

				// do some UI stuff to show success
				if (response == null) {
					com.google.gwt.user.client.Window.alert(sps2Constants.submitOnSuccess() 
							+ " : " + sps2Constants.submitOnSuccessNoResult());
					SpsSettingsPanel.setTaskIdentifier("");
				} else {
					if (!response.getError().getMessage().equalsIgnoreCase("")) {
						com.google.gwt.user.client.Window.alert("error : " + response.getError().getMessage());
						SpsSettingsPanel.setTaskIdentifier("");
					} else {
						com.google.gwt.user.client.Window.alert("Infos : " + response.getStatusReport().getDescription());

						final String taskIdentifier1 = response.getStatusReport().getTask();
						SpsSettingsPanel.setTaskIdentifier(taskIdentifier1);

						if ( (response.getStatusReport().getStatusCode().equalsIgnoreCase(Sps2GeneralConstants.STATUS_FEASIBLE)) 
								|| (response.getStatusReport().getRequestStatus().equalsIgnoreCase(Sps2GeneralConstants.STATUS_FEASIBLE)) ) {
							com.google.gwt.user.client.Window.alert(
									sps2Constants.taskCompletedStatus() + " " + SpsSettingsPanel.getTaskIdentifier());

							TaskResultBean _taskResultBean = new TaskResultBean();
							_taskResultBean.setTaskingResponseBean(response);

							// results list update
							SpsPanels.getSpsSolutionsPanel().updateGridSegments(_taskResultBean);

							// tasks historical update
							SpsPanels.getSpsTasksPanel().getRefreshButton().fireEvent("click");
						} else if ( (response.getStatusReport().getStatusCode().equalsIgnoreCase(Sps2GeneralConstants.STATUS_FAILED)) 
							|| (response.getStatusReport().getRequestStatus().equalsIgnoreCase(Sps2GeneralConstants.STATUS_FAILED)) ) {
							com.google.gwt.user.client.Window.alert(
									sps2Constants.taskFailedStatus() + " " + SpsSettingsPanel.getTaskIdentifier());
						} else if (response.getStatusReport().getRequestStatus().equalsIgnoreCase(Sps2GeneralConstants.STATUS_PENDING)) {
								// tasks historical update
								SpsPanels.getSpsTasksPanel().getRefreshButton().fireEvent("click");
						} // if
					}
				} // if (results == null) {

			} // public void onSuccess(TaskingResponseBean response) {

			public void onFailure(Throwable caught) {
				_formSubmitPanel.getEl().unmask();

				com.google.gwt.user.client.Window.alert(sps2Constants.submitOnFailure() +
						" : " + caught.getLocalizedMessage());
			} // public void onFailure(Throwable caught) {
		}; //  AsyncCallback<SPSGetFeasibilityRequestAckBean> callback

		submitServiceAsync.getSubmitResponse(
				Sps2GeneralConstants.SPS_SUBMIT, 
				_programmingRequest,
				SpsTasksPanel.getSpsWebServiceMode(), 
				callback);
	} // public static void sendSubmit(ProgrammingRequestBean _programmingRequest) {

	/**
	 * send a SPS 2.0 'Reserve' request and show the 'Reserve' response 
	 * for synchronous and asynchronous sensor
	 * 
	 * @param _programmingRequest (ProgrammingRequestBean)	: 
	 * @param _formSubmitPanel (Panel)						: 
	 */
	public static void sendReserve(ProgrammingRequestBean _programmingRequest, final Panel _formSubmitPanel) {
		ReserveTaskServiceAsync reserveServiceAsync = 
			ReserveTaskService.Util.getInstance();

		// Specify the URL at which our service implementation is running.
		// Note that the target URL must reside on the same domain and port
		// from which the host page was served.
		ServiceDefTarget endpoint = (ServiceDefTarget) reserveServiceAsync;
		String moduleRelativeURL = GWT.getModuleBaseURL() + Sps2GeneralConstants.RESERVE_CONTROLLER;

		endpoint.setServiceEntryPoint(moduleRelativeURL);

		_formSubmitPanel.getEl().mask("send Reserve");

		// Create an Asynchronous callback to handle the result.
		AsyncCallback<TaskingResponseBean> callback = new AsyncCallback<TaskingResponseBean>() {
			public void onSuccess(TaskingResponseBean response) {
				_formSubmitPanel.getEl().unmask();

				// do some UI stuff to show success
				if (response == null) {
					com.google.gwt.user.client.Window.alert(sps2Constants.submitOnSuccess() 
							+ " : " + sps2Constants.submitOnSuccessNoResult());
					SpsSettingsPanel.setTaskIdentifier("");
				} else {
					if (!response.getError().getMessage().equalsIgnoreCase("")) {
						com.google.gwt.user.client.Window.alert("error : " + response.getError().getMessage());
						SpsSettingsPanel.setTaskIdentifier("");
					} else {
						com.google.gwt.user.client.Window.alert("Infos : " + response.getStatusReport().getDescription());

						final String taskIdentifier1 = response.getStatusReport().getTask();
						SpsSettingsPanel.setTaskIdentifier(taskIdentifier1);

						if ( (response.getStatusReport().getStatusCode().equalsIgnoreCase(Sps2GeneralConstants.STATUS_FEASIBLE))  
							|| (response.getStatusReport().getRequestStatus().equalsIgnoreCase(Sps2GeneralConstants.STATUS_FEASIBLE)) ) {

							com.google.gwt.user.client.Window.alert(
									sps2Constants.taskCompletedStatus() + " " + SpsSettingsPanel.getTaskIdentifier());

							TaskResultBean _taskResultBean = new TaskResultBean();
							_taskResultBean.setTaskingResponseBean(response);

							// results list update
							SpsPanels.getSpsSolutionsPanel().updateGridSegments(_taskResultBean);

							// tasks historical update
							SpsPanels.getSpsTasksPanel().getRefreshButton().fireEvent("click");
						} else if ( (response.getStatusReport().getStatusCode().equalsIgnoreCase(Sps2GeneralConstants.STATUS_FAILED)) 
							|| (response.getStatusReport().getRequestStatus().equalsIgnoreCase(Sps2GeneralConstants.STATUS_FAILED)) ) {
							com.google.gwt.user.client.Window.alert(
									sps2Constants.taskFailedStatus() + " " + SpsSettingsPanel.getTaskIdentifier());
						} else if (response.getStatusReport().getRequestStatus().equalsIgnoreCase(Sps2GeneralConstants.STATUS_PENDING)) {
							// tasks historical update
							SpsPanels.getSpsTasksPanel().getRefreshButton().fireEvent("click");
						} // if
					}
				} // if (results == null) {

			} // public void onSuccess(TaskingResponseBean response) {

			public void onFailure(Throwable caught) {
				_formSubmitPanel.getEl().unmask();

				com.google.gwt.user.client.Window.alert(sps2Constants.submitOnFailure() +
						" : " + caught.getLocalizedMessage());
			} // public void onFailure(Throwable caught) {
		}; //  AsyncCallback<SPSGetFeasibilityRequestAckBean> callback

		reserveServiceAsync.getReserveResponse(
				Sps2GeneralConstants.SPS_RESERVE, 
				_programmingRequest,
				SpsTasksPanel.getSpsWebServiceMode(), 
				callback);
	} // public static void sendReserve(ProgrammingRequestBean _programmingRequest) {

	/**
	 * send a SPS 2.0 'Confirm' request and show the 'Confirm' response 
	 * for synchronous and asynchronous sensor
	 * 
	 * @param _programmingRequest (ProgrammingRequestBean)	: 
	 * @param _formSubmitPanel (Panel)						: 
	 */
	public static void sendConfirm(ProgrammingRequestBean _programmingRequest, final Panel _formSubmitPanel) {
		ConfirmTaskServiceAsync confirmServiceAsync = 
			ConfirmTaskService.Util.getInstance();

		// Specify the URL at which our service implementation is running.
		// Note that the target URL must reside on the same domain and port
		// from which the host page was served.
		ServiceDefTarget endpoint = (ServiceDefTarget) confirmServiceAsync;
		String moduleRelativeURL = GWT.getModuleBaseURL() + Sps2GeneralConstants.CONFIRM_TASK_CONTROLLER;

		endpoint.setServiceEntryPoint(moduleRelativeURL);

		_formSubmitPanel.getEl().mask("send Confirm");

		// Create an Asynchronous callback to handle the result.
		AsyncCallback<ConfirmResponseBean> callback = new AsyncCallback<ConfirmResponseBean>() {
			public void onSuccess(ConfirmResponseBean response) {
				_formSubmitPanel.getEl().unmask();

				// do some UI stuff to show success
				if (response == null) {
					com.google.gwt.user.client.Window.alert(sps2Constants.submitOnSuccess() 
							+ " : " + sps2Constants.submitOnSuccessNoResult());
					SpsSettingsPanel.setTaskIdentifier("");
				} else {
					if (!response.getError().getMessage().equalsIgnoreCase("")) {
						com.google.gwt.user.client.Window.alert("error : " + response.getError().getMessage());
						SpsSettingsPanel.setTaskIdentifier("");
					} else {
						com.google.gwt.user.client.Window.alert("Infos : " + response.getStatusReport().getDescription());

						final String taskIdentifier1 = response.getStatusReport().getTask();
						SpsSettingsPanel.setTaskIdentifier(taskIdentifier1);

						if ( (response.getStatusReport().getStatusCode().equalsIgnoreCase(Sps2GeneralConstants.STATUS_ACCEPTED)) 
							|| (response.getStatusReport().getRequestStatus().equalsIgnoreCase(Sps2GeneralConstants.STATUS_ACCEPTED)) ) {

							com.google.gwt.user.client.Window.alert(
									sps2Constants.taskCompletedStatus() + " " + SpsSettingsPanel.getTaskIdentifier());

							// tasks historical update
							SpsPanels.getSpsTasksPanel().getRefreshButton().fireEvent("click");
						} else if ( (response.getStatusReport().getStatusCode().equalsIgnoreCase(Sps2GeneralConstants.STATUS_REJECTED)) 
							|| (response.getStatusReport().getRequestStatus().equalsIgnoreCase(Sps2GeneralConstants.STATUS_REJECTED)) ) {
							com.google.gwt.user.client.Window.alert(
									sps2Constants.taskFailedStatus() + " " + SpsSettingsPanel.getTaskIdentifier());
						}
					}
				} // if (results == null) {

			} // public void onSuccess(TaskingResponseBean response) {

			public void onFailure(Throwable caught) {
				_formSubmitPanel.getEl().unmask();

				com.google.gwt.user.client.Window.alert(sps2Constants.submitOnFailure() +
						" : " + caught.getLocalizedMessage());
			} // public void onFailure(Throwable caught) {
		}; //  AsyncCallback<SPSGetFeasibilityRequestAckBean> callback

		ConfirmRequestBean confirmRequestBean = new ConfirmRequestBean();
		confirmRequestBean.setSensorUrn(_programmingRequest.getSensor().getUrn());
		confirmRequestBean.setTaskId(_programmingRequest.getIdentifier());

		confirmServiceAsync.getConfirmResponse(
				Sps2GeneralConstants.SPS_CONFIRM, 
				confirmRequestBean,
				SpsTasksPanel.getSpsWebServiceMode(), 
				callback);
	} // public static void sendConfirm(ProgrammingRequestBean _programmingRequest) {

	/**
	 * send a SPS 2.0 'GetFeasibility' request and show the 'GetFeasibility' response 
	 * for synchronous and asynchronous sensor
	 * 
	 * @param _request (ProgrammingRequestBean)	: 
	 * @param _formSubmitPanel (Panel)			: 
	 */
	public static void sendUpdate(ProgrammingRequestBean _request, final Panel _formSubmitPanel) {
		UpdateTaskServiceAsync updateServiceAsync = 
			UpdateTaskService.Util.getInstance();

		// Specify the URL at which our service implementation is running.
		// Note that the target URL must reside on the same domain and port
		// from which the host page was served.
		ServiceDefTarget endpoint = (ServiceDefTarget) updateServiceAsync;
		String moduleRelativeURL = GWT.getModuleBaseURL() + Sps2GeneralConstants.UPDATE_TASK_CONTROLLER;

		endpoint.setServiceEntryPoint(moduleRelativeURL);

		_formSubmitPanel.getEl().mask("send Update");

		// Create an Asynchronous callback to handle the result.
		AsyncCallback<TaskingResponseBean> callback = new AsyncCallback<TaskingResponseBean>() {
			public void onSuccess(TaskingResponseBean response) {
				_formSubmitPanel.getEl().unmask();

				// do some UI stuff to show success
				if (response == null) {
					com.google.gwt.user.client.Window.alert(sps2Constants.submitOnSuccess() 
							+ " : " + sps2Constants.submitOnSuccessNoResult());
					SpsSettingsPanel.setTaskIdentifier("");
				} else {
					if (!response.getError().getMessage().equalsIgnoreCase("")) {
						com.google.gwt.user.client.Window.alert("error : " + response.getError().getMessage());
						SpsSettingsPanel.setTaskIdentifier("");
					} else {
						com.google.gwt.user.client.Window.alert("Infos : " + response.getStatusReport().getDescription());

						final String taskIdentifier1 = response.getStatusReport().getTask();
						SpsSettingsPanel.setTaskIdentifier(taskIdentifier1);

						if ( (response.getStatusReport().getStatusCode().equalsIgnoreCase(Sps2GeneralConstants.STATUS_FEASIBLE)) 
							|| (response.getStatusReport().getRequestStatus().equalsIgnoreCase(Sps2GeneralConstants.STATUS_FEASIBLE)) ) {
							com.google.gwt.user.client.Window.alert(
									sps2Constants.taskCompletedStatus() + " " + SpsSettingsPanel.getTaskIdentifier());

							TaskResultBean _taskResultBean = new TaskResultBean();
							_taskResultBean.setTaskingResponseBean(response);

							// results list update
							SpsPanels.getSpsSolutionsPanel().updateGridSegments(_taskResultBean);

							// tasks historical update
							SpsPanels.getSpsTasksPanel().getRefreshButton().fireEvent("click");
						} else if ( (response.getStatusReport().getStatusCode().equalsIgnoreCase(Sps2GeneralConstants.STATUS_FAILED)) 
							|| (response.getStatusReport().getRequestStatus().equalsIgnoreCase(Sps2GeneralConstants.STATUS_FAILED)) ) {
							com.google.gwt.user.client.Window.alert(
									sps2Constants.taskFailedStatus() + " " + SpsSettingsPanel.getTaskIdentifier());
						} else if (response.getStatusReport().getRequestStatus().equalsIgnoreCase(Sps2GeneralConstants.STATUS_PENDING)) {
							// tasks historical update
							SpsPanels.getSpsTasksPanel().getRefreshButton().fireEvent("click");
						}
					}
				} // if (results == null) {

			} // public void onSuccess(SPSGetFeasibilityRequestAckBean requestAck) {

			public void onFailure(Throwable caught) {
				_formSubmitPanel.getEl().unmask();

				com.google.gwt.user.client.Window.alert(sps2Constants.submitOnFailure() +
						" : " + caught.getLocalizedMessage());
			} // public void onFailure(Throwable caught) {
		}; //  AsyncCallback<SPSGetFeasibilityRequestAckBean> callback

		updateServiceAsync.getUpdateResponse(
				Sps2GeneralConstants.SPS_UPDATE, 
				_request,
				SpsTasksPanel.getSpsWebServiceMode(), 
				callback);
	} // public static void sendUpdate(ProgrammingRequestBean _request) {

	/**
	 * send a SPS 2.0 'GetFeasibility' request and show the 'GetFeasibility' response 
	 * for synchronous and asynchronous sensor
	 * 
	 * @param _request (ProgrammingRequestBean)	: 
	 * @param _formSubmitPanel (Panel)			: 
	 */
	public static void sendValidate(ProgrammingRequestBean _request, final Panel _formSubmitPanel) {
		ValidateTaskServiceAsync validateServiceAsync = 
			ValidateTaskService.Util.getInstance();

		// Specify the URL at which our service implementation is running.
		// Note that the target URL must reside on the same domain and port
		// from which the host page was served.
		ServiceDefTarget endpoint = (ServiceDefTarget) validateServiceAsync;
		String moduleRelativeURL = GWT.getModuleBaseURL() + Sps2GeneralConstants.VALIDATE_TASK_CONTROLLER;

		endpoint.setServiceEntryPoint(moduleRelativeURL);

		_formSubmitPanel.getEl().mask("send Validate");

		// Create an Asynchronous callback to handle the result.
		AsyncCallback<ValidateResponseBean> callback = new AsyncCallback<ValidateResponseBean>() {
			public void onSuccess(ValidateResponseBean response) {
				_formSubmitPanel.getEl().unmask();

				// do some UI stuff to show success
				if (response == null) {
					com.google.gwt.user.client.Window.alert(sps2Constants.submitOnSuccess() 
							+ " : " + sps2Constants.submitOnSuccessNoResult());
					SpsSettingsPanel.setTaskIdentifier("");
				} else {
					if (!response.getError().getMessage().equalsIgnoreCase("")) {
						com.google.gwt.user.client.Window.alert("error : " + response.getError().getMessage());
						SpsSettingsPanel.setTaskIdentifier("");
					} else {
						com.google.gwt.user.client.Window.alert("Infos : " + response.getStatusReport().getDescription());

						final String taskIdentifier1 = response.getStatusReport().getTask();
						SpsSettingsPanel.setTaskIdentifier(taskIdentifier1);

						if ( (response.getStatusReport().getStatusCode().equalsIgnoreCase(Sps2GeneralConstants.STATUS_ACCEPTED)) 
							|| (response.getStatusReport().getRequestStatus().equalsIgnoreCase(Sps2GeneralConstants.STATUS_ACCEPTED)) ) {
							com.google.gwt.user.client.Window.alert(
									sps2Constants.taskCompletedStatus() + " " + SpsSettingsPanel.getTaskIdentifier());

//							TaskResultBean _taskResultBean = new TaskResultBean();
//							_taskResultBean.setTaskingResponseBean(response);
//
//							// results list update
//							SpsPanels.getSpsSolutionsPanel().updateGridSegments(_taskResultBean);

							// tasks historical update
							SpsPanels.getSpsTasksPanel().getRefreshButton().fireEvent("click");
						} else if ( (response.getStatusReport().getStatusCode().equalsIgnoreCase(Sps2GeneralConstants.STATUS_FAILED)) 
							|| (response.getStatusReport().getRequestStatus().equalsIgnoreCase(Sps2GeneralConstants.STATUS_FAILED)) ) {
							com.google.gwt.user.client.Window.alert(
									sps2Constants.taskFailedStatus() + " " + SpsSettingsPanel.getTaskIdentifier());
						} else if (response.getStatusReport().getRequestStatus().equalsIgnoreCase(Sps2GeneralConstants.STATUS_PENDING)) {
							// tasks historical update
							SpsPanels.getSpsTasksPanel().getRefreshButton().fireEvent("click");
						}
					}
				} // if (results == null) {

			} // public void onSuccess(SPSGetFeasibilityRequestAckBean requestAck) {

			public void onFailure(Throwable caught) {
				_formSubmitPanel.getEl().unmask();

				com.google.gwt.user.client.Window.alert(sps2Constants.submitOnFailure() +
						" : " + caught.getLocalizedMessage());
			} // public void onFailure(Throwable caught) {
		}; //  AsyncCallback<SPSGetFeasibilityRequestAckBean> callback

		ValidateRequestBean validateRequestBean = new ValidateRequestBean();
		validateRequestBean.setUser(_request.getUser());
		validateRequestBean.setName(_request.getName());
		validateRequestBean.setSensorUrn(_request.getSensor().getUrn());
		validateRequestBean.setTaskId(_request.getIdentifier());
		String segmentsIds[] = new String[SpsSolutionsPanel.getFeasibilitySegments().keySet().size()];
	    SpsSolutionsPanel.getFeasibilitySegments().keySet().toArray(segmentsIds);
	    ArrayList<String> idsList = new ArrayList<String>();
	    for (String id : segmentsIds) {
	    	idsList.add(id);
	    }
    	validateRequestBean.setSegmentsId(idsList);

		validateServiceAsync.getValidateResponse(
				Sps2GeneralConstants.SPS_VALIDATE, 
				validateRequestBean,
				SpsTasksPanel.getSpsWebServiceMode(), 
				callback);
	} // public static void sendValidate(ProgrammingRequestBean _request) {

	/**
	 * GetStatus of one Task and show the GetStatus response
	 * 
	 * @param _taskId (String)			: task identifier
	 * @param _sensorUrn (String)		: sensor urn
	 * @param _gridPanel (GridPanel)	: the grid panel which contains the historical tasks
	 * @param _message (String)			: the pending message
	 * @param _window (WindowGetStatus)	: the window to show the response
	 */
	public static void getTaskStatus(
			final String _taskId, 
			final String _sensorUrn,
			final GridPanel _gridPanel, 
			String _message,
			final WindowGetStatus _window) {

		if (_gridPanel != null) {
			_gridPanel.getEl().mask(_message, true);
		}

		final GetTaskStatusServiceAsync getTaskStatusServiceAsync = 
			GetTaskStatusService.Util.getInstance();
		ServiceDefTarget endpoint = (ServiceDefTarget) getTaskStatusServiceAsync;
		String moduleRelativeURL = GWT.getModuleBaseURL() + Sps2GeneralConstants.TASK_STATUS_CONTROLLER;
		endpoint.setServiceEntryPoint(moduleRelativeURL);

		// Create an Asynchronous callback to handle the result.
		AsyncCallback<GetStatusResponseBean> callbackGetStatusTask = new AsyncCallback<GetStatusResponseBean>() {
			public void onSuccess(GetStatusResponseBean _response) {
				if (_gridPanel != null) {
					_gridPanel.getEl().unmask();
				}

				// do some UI stuff to show success
				if (_response != null) {
					if (_response.getError().getCode() == 0) {
						//						com.google.gwt.user.client.Window.alert("task '" + _taskId + "' \n : " + _response.getResponse());

						_window.showGetStatusResponse(_response.getResponse());

						// tasks historical update
						//						SpsPanels.getSpsTasksPanel().getRefreshButton().fireEvent("click");
					} else
						com.google.gwt.user.client.Window.alert("error : " + _response.getError().getMessage());
				} // if (_errorBean.getError().getErrorCode() == 0) {
			} // public void onSuccess(GetStatusResponseBean _response) {

			public void onFailure(Throwable caught) {
				if (_gridPanel != null) {
					_gridPanel.getEl().unmask();
				}

				com.google.gwt.user.client.Window.alert("onFailure : " + caught.getMessage());
			}
		}; //  AsyncCallback<GetStatusResponseBean> callbackGetStatusTask

		GetStatusRequestBean _parameters = new GetStatusRequestBean();
		_parameters.setTaskId(_taskId);
		_parameters.setTaskUser(ConfigConstant.SPS_FACEO_USER);
		_parameters.setSensorUrn(_sensorUrn);

		getTaskStatusServiceAsync.getTaskStatus(
				Sps2GeneralConstants.SPS_GET_TASK_STATUS, _parameters, callbackGetStatusTask);
	} // public static void getTaskStatus(final String _taskId, 

	/**
	 * Cancel one Task and show the Cancel response
	 * 
	 * @param _taskId (String)			: task identifier
	 * @param _sensorUrn (String)		: sensor urn
	 * @param _gridPanel (GridPanel)	: the grid panel which contains the historical tasks
	 * @param _message (String)			: the pending message
	 * @param _window (WindowCancel)	: the window to show the response
	 */
	public static void cancelTask(
			final String _taskId, 
			final String _sensorUrn,
			final GridPanel _gridPanel, 
			String _message,
			final WindowCancel _window) {

		if (_gridPanel != null) {
			_gridPanel.getEl().mask(_message, true);
		}

		final CancelTaskServiceAsync cancelTaskServiceAsync = 
			CancelTaskService.Util.getInstance();

		ServiceDefTarget endpoint = (ServiceDefTarget) cancelTaskServiceAsync;
		String moduleRelativeURL = GWT.getModuleBaseURL() + Sps2GeneralConstants.CANCEL_TASK_CONTROLLER;
		endpoint.setServiceEntryPoint(moduleRelativeURL);

		// Create an Asynchronous callback to handle the result.
		AsyncCallback<CancelResponseBean> callbackCancelTask = new AsyncCallback<CancelResponseBean>() {
			public void onSuccess(CancelResponseBean _response) {
				if (_gridPanel != null) {
					_gridPanel.getEl().unmask();
				}

				// do some UI stuff to show success
				if (_response != null) {
					if (_response.getError().getCode() == 0) {
						if (_window != null) {
							_window.showCancelResponse(_response.getResponse());
						} // if (_window != null) {

						// tasks historical update
						SpsPanels.getSpsTasksPanel().getRefreshButton().fireEvent("click");
					} else
						com.google.gwt.user.client.Window.alert("error : " + _response.getError().getMessage());
				} // if (_errorBean.getError().getErrorCode() == 0) {
			} // public void onSuccess(GetStatusResponseBean _response) {

			public void onFailure(Throwable caught) {
				if (_gridPanel != null) {
					_gridPanel.getEl().unmask();
				}

				com.google.gwt.user.client.Window.alert("onFailure : " + caught.getMessage());
			}
		}; //  AsyncCallback<CancelResponseBean> callbackCancelTask

		CancelRequestBean _parameters = new CancelRequestBean();
		_parameters.setTaskId(_taskId);
		_parameters.setSensorUrn(_sensorUrn);

		cancelTaskServiceAsync.getCancelResponse(
				Sps2GeneralConstants.SPS_CANCEL, _parameters, true, callbackCancelTask);
	} // public static void cancelTask(final String _taskId, 

	/**
	 * Confirm one Reserved Task and show the Confirm response
	 * 
	 * @param _taskId (String)			: task identifier
	 * @param _sensorUrn (String)		: sensor urn
	 * @param _gridPanel (GridPanel)	: the grid panel which contains the historical tasks
	 * @param _message (String)			: the pending message
	 * @param _window (WindowConfirm)	: the window to show the response
	 */
	public static void confirmTask(
			final String _taskId, 
			final String _sensorUrn,
			final GridPanel _gridPanel, 
			String _message,
			final WindowConfirm _window) {

		if (_gridPanel != null) {
			_gridPanel.getEl().mask(_message, true);
		}

		final ConfirmTaskServiceAsync confirmTaskServiceAsync = 
			ConfirmTaskService.Util.getInstance();

		ServiceDefTarget endpoint = (ServiceDefTarget) confirmTaskServiceAsync;
		String moduleRelativeURL = GWT.getModuleBaseURL() + Sps2GeneralConstants.CONFIRM_TASK_CONTROLLER;
		endpoint.setServiceEntryPoint(moduleRelativeURL);

		// Create an Asynchronous callback to handle the result.
		AsyncCallback<ConfirmResponseBean> callbackConfirmTask = new AsyncCallback<ConfirmResponseBean>() {
			public void onSuccess(ConfirmResponseBean _response) {
				if (_gridPanel != null) {
					_gridPanel.getEl().unmask();
				}

				// do some UI stuff to show success
				if (_response != null) {
					if (_response.getError().getCode() == 0) {
						if (_window != null) {
							_window.showConfirmResponse(_response.getResponse());
						} // if (_window != null) {

						// tasks historical update
						SpsPanels.getSpsTasksPanel().getRefreshButton().fireEvent("click");
					} else
						com.google.gwt.user.client.Window.alert("error : " + _response.getError().getMessage());
				} // if (_errorBean.getError().getErrorCode() == 0) {
			} // public void onSuccess(GetStatusResponseBean _response) {

			public void onFailure(Throwable caught) {
				if (_gridPanel != null) {
					_gridPanel.getEl().unmask();
				}

				com.google.gwt.user.client.Window.alert("onFailure : " + caught.getMessage());
			}
		}; //  AsyncCallback<ConfirmResponseBean> callbackConfirmTask

		ConfirmRequestBean _parameters = new ConfirmRequestBean();
		_parameters.setTaskId(_taskId);
		_parameters.setSensorUrn(_sensorUrn);

		confirmTaskServiceAsync.getConfirmResponse(
				Sps2GeneralConstants.SPS_CONFIRM, _parameters, true, callbackConfirmTask);
	} // public static void confirmTask(final String _taskId, 

	/**
	 * Validate one Reserved Task and show the Validate response
	 * 
	 * @param _taskId (String)			: task identifier
	 * @param _taskName (String)		: task name
	 * @param _sensorUrn (String)		: sensor urn
	 * @param _ids (ArrayList<String>)	: segments identifiers
	 * @param _gridPanel (GridPanel)	: the grid panel which contains the historical tasks
	 * @param _message (String)			: the pending message
	 * @param _window (WindowValidate)	: the window to show the response
	 */
	public static void sendValidateOperation(
			final String _taskId, 
			final String _taskName,
			final String _sensorUrn,
			final ArrayList<String> _ids,
			final GridPanel _gridPanel, 
			final String _message,
			final WindowValidate _window) {

		final ValidateTaskServiceAsync validateTaskServiceAsync = 
			ValidateTaskService.Util.getInstance();

		ServiceDefTarget endpoint = (ServiceDefTarget) validateTaskServiceAsync;
		String moduleRelativeURL = GWT.getModuleBaseURL() + Sps2GeneralConstants.VALIDATE_TASK_CONTROLLER;
		endpoint.setServiceEntryPoint(moduleRelativeURL);

		// Create an Asynchronous callback to handle the result.
		AsyncCallback<ValidateResponseBean> callbackValidateTask = new AsyncCallback<ValidateResponseBean>() {
			public void onSuccess(ValidateResponseBean _response) {
				if (_gridPanel != null) {
					_gridPanel.getEl().unmask();
				}

				// do some UI stuff to show success
				if ((_response != null) && (_response.getError().getCode() == 0)) {
					if (_window != null) {
						_window.showValidateResponse(_response.getResponse());
					} //

					// tasks historical update
					SpsPanels.getSpsTasksPanel().getRefreshButton().fireEvent("click");
				} else {
					com.google.gwt.user.client.Window.alert("error : " + _response.getError().getMessage());
				} // if (_errorBean.getError().getErrorCode() == 0) {
			} // public void onSuccess(GetStatusResponseBean _response) {

			public void onFailure(Throwable caught) {
				if (_gridPanel != null) {
					_gridPanel.getEl().unmask();
				}

				com.google.gwt.user.client.Window.alert("onFailure : " + caught.getMessage());
			}
		}; //  AsyncCallback<ValidateResponseBean> callbackValidateTask

		ValidateRequestBean _parameters = new ValidateRequestBean();
		_parameters.setUser(ConfigConstant.SPS_FACEO_USER);
		_parameters.setName(_taskName);
		_parameters.setSensorUrn(_sensorUrn);
		_parameters.setTaskId(_taskId);
	    _parameters.setSegmentsId(_ids);

		validateTaskServiceAsync.getValidateResponse(
				Sps2GeneralConstants.SPS_VALIDATE, _parameters, true, callbackValidateTask);
	} // public static void sendValidateOperation 

	/**
	 * Validate one Reserved Task and show the Validate response
	 * 
	 * @param _taskId (String)			: task identifier
	 * @param _taskName (String)		: task name
	 * @param _sensorUrn (String)		: sensor urn
	 * @param _gridPanel (GridPanel)	: the grid panel which contains the historical tasks
	 * @param _message (String)			: the pending message
	 * @param _window (WindowValidate)	: the window to show the response
	 */
	public static void validateTask(
			final String _taskId, 
			final String _taskName,
			final String _sensorUrn,
			final GridPanel _gridPanel, 
			final String _message,
			final WindowValidate _window) {	
		if (_gridPanel != null) {
			_gridPanel.getEl().mask(_message, true);
		}
		
		// First : get the segments ids from the DataBase
		final GetTaskResultServiceAsync getTaskResultServiceAsync = GetTaskResultService.Util.getInstance();
		ServiceDefTarget endpoint = (ServiceDefTarget) getTaskResultServiceAsync;
		String moduleRelativeURL = GWT.getModuleBaseURL() + Sps2GeneralConstants.TASK_RESULT_CONTROLLER;
		endpoint.setServiceEntryPoint(moduleRelativeURL);

		// Create an Asynchronous callback to handle the result.
		AsyncCallback<TaskResultBean> callbackTaskResult = new AsyncCallback<TaskResultBean>() {
			public void onSuccess(TaskResultBean _taskResultBean) {
				TaskingResponseBean taskingResponseBean = _taskResultBean.getTaskingResponseBean();
				ArrayList<String> ids = new ArrayList<String>();
				if (taskingResponseBean != null) {
					HashMap<String, SegmentBean> segments = taskingResponseBean.getFeasibilityStudy().getSegments();
					if ( (segments != null) && (segments.size() > 0) ) {
						for (Entry<String, SegmentBean> segment : segments.entrySet()) {
							ids.add(segment.getValue().getId());
						} 
					}
				} // 
				
				// Second : invoke the Validate operation
				sendValidateOperation(_taskId, _taskName, _sensorUrn, ids, _gridPanel, _message, _window);
			} // public void onSuccess

			public void onFailure(Throwable caught) {
				if (_gridPanel != null) {
					_gridPanel.getEl().unmask();
				}

				com.google.gwt.user.client.Window.alert(sps2Constants.submitOnFailure() + " : "
						+ caught.getLocalizedMessage());
			}
		}; // AsyncCallback<TaskResultBean> callbackTaskResult

		TaskingParametersBean parameters = SpsTasksPanel.getSensorTaskParameters(_sensorUrn);
		getTaskResultServiceAsync.getTaskResult(Sps2GeneralConstants.SPS_GET_TASK_RESULT, ConfigConstant.SPS_FACEO_USER, _taskId, parameters,
				true, callbackTaskResult);
	} // public static validateTask
	
	/**
	 * DescribeResultAccess of one Task and show the DescribeResultAccess response
	 * 
	 * @param _taskId (String)						: task identifier
	 * @param _sensorUrn (String)					: sensor urn
	 * @param _gridPanel (GridPanel)				: the grid panel which contains the historical tasks
	 * @param _message (String)						: the pending message
	 * @param _window (WindowDescribeResultAccess)	: the window to show the response
	 */
	public static void describeTaskResultAccess(
			final String _taskId, 
			final String _sensorUrn,
			final GridPanel _gridPanel, 
			String _message,
			final WindowDescribeResultAccess _window) {

		if (_gridPanel != null) {
			_gridPanel.getEl().mask(_message, true);
		}

		final DescribeResultAccessServiceAsync describeResultAccessServiceAsync = 
			DescribeResultAccessService.Util.getInstance();

		ServiceDefTarget endpoint = (ServiceDefTarget) describeResultAccessServiceAsync;
		String moduleRelativeURL = GWT.getModuleBaseURL() + Sps2GeneralConstants.DESCRIBE_RESULT_ACCESS_CONTROLLER;
		endpoint.setServiceEntryPoint(moduleRelativeURL);

		// Create an Asynchronous callback to handle the result.
		AsyncCallback<DescribeResultAccessResponseBean> callbackDescribeResultAccess = 
			new AsyncCallback<DescribeResultAccessResponseBean>() {
			public void onSuccess(DescribeResultAccessResponseBean _response) {
				if (_gridPanel != null) {
					_gridPanel.getEl().unmask();
				}

				// do some UI stuff to show success
				if (_response != null) {
					if (_response.getError().getCode() == 0) {
						_window.showResponse(_response.getResponse());
					} else
						com.google.gwt.user.client.Window.alert("error : " + _response.getError().getMessage());
				} // if (_errorBean.getError().getErrorCode() == 0) {
			} // public void onSuccess(DescribeResultAccessResponseBean _response) {

			public void onFailure(Throwable caught) {
				if (_gridPanel != null) {
					_gridPanel.getEl().unmask();
				}

				com.google.gwt.user.client.Window.alert("onFailure : " + caught.getMessage());
			}
		}; //  AsyncCallback<DescribeResultAccessResponseBean> callbackDescribeResultAccess

		DescribeResultAccessRequestBean _parameters = new DescribeResultAccessRequestBean();
		_parameters.setTaskId(_taskId);
		_parameters.setSensorUrn(_sensorUrn);
		_parameters.setType(Sps2GeneralConstants.DESCRIBE_ACCESS_TASK);

		describeResultAccessServiceAsync.getDescribeResultAccessResult(
				Sps2GeneralConstants.SPS_DESCRIBE_RESULT_ACCESS, _parameters, true, callbackDescribeResultAccess);
	} // public static void describeTaskResultAccess(final String _taskId, final GridPanel _gridPanel, String _message, WindowGetStatus _window) {

	/**
	 * Delete one Task in database
	 * 
	 * @param _taskId (String)				: task identifier
	 */
	public static void deleteTask(final String _taskId) {
		final DeleteTaskServiceAsync deleteTaskServiceAsync = 
			DeleteTaskService.Util.getInstance();
		ServiceDefTarget endpoint = (ServiceDefTarget) deleteTaskServiceAsync;
		String moduleRelativeURL = GWT.getModuleBaseURL() + Sps2GeneralConstants.DELETE_TASK_CONTROLLER;
		endpoint.setServiceEntryPoint(moduleRelativeURL);

		// Create an Asynchronous callback to handle the result.
		AsyncCallback<ErrorBean> callbackTaskDelete = new AsyncCallback<ErrorBean>() {
			public void onSuccess(ErrorBean _errorBean) {
				// do some UI stuff to show success
				if (_errorBean != null) {
					if (! _errorBean.getError()) {
						com.google.gwt.user.client.Window.alert("task '" + _taskId + "' has been deleted");

						// tasks historical update
						SpsPanels.getSpsTasksPanel().getRefreshButton().fireEvent("click");
					} else {
						com.google.gwt.user.client.Window.alert("error : " + _errorBean.getMessage());
					}
				} // if (_errorBean != null) {
			} // public void onSuccess(ErrorBean _errorBean) {

			public void onFailure(Throwable caught) {
				com.google.gwt.user.client.Window.alert("onFailure : " + caught.getMessage());
			}
		}; //  AsyncCallback<ErrorBean> callbackTaskDelete

		deleteTaskServiceAsync.deleteTask(
				Sps2GeneralConstants.SPS_DELETE_TASK, ConfigConstant.SPS_FACEO_USER, _taskId, callbackTaskDelete);
	} // 

	private static void updateSensorPanelTaskOperation(TaskingResponseBean _response) {
		boolean find = false;
		for(Entry<String, SensorBean> sensors : Sps2GeneralConstants.getSensors().entrySet()) {
			if (! find) {
				SensorBean sensor = sensors.getValue();

				if (sensor.getActive()) {
					if (_response.getStatusReport().getSensorId().equalsIgnoreCase(sensor.getUrn())) {
						SpsSensorSettingsPanel sensorsSettingsPanel = 
							SpsPanels.getSpsSettingsPanel()
							.getMultiSensorsSettingsPanel()
							.getSpsSensorSettingsPanel(sensor.getId());

						sensorsSettingsPanel.setTaskOperation(_response.getTaskOperation());

						find = true;
					} //
				} // 
			} // 
		} // for(Entry<String, SensorBean> sensors : Sps2GeneralConstants.getSensors().entrySet()) {
	} // 

} // class
