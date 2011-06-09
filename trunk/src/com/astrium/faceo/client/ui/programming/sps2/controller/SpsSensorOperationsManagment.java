package com.astrium.faceo.client.ui.programming.sps2.controller;

import java.util.Date;

import com.astrium.faceo.client.bean.programming.sps2.describeResultAccess.DescribeResultAccessRequestBean;
import com.astrium.faceo.client.bean.programming.sps2.describeResultAccess.DescribeResultAccessResponseBean;
import com.astrium.faceo.client.bean.programming.sps2.describeSensor.DescribeSensorRequestBean;
import com.astrium.faceo.client.bean.programming.sps2.describeSensor.DescribeSensorResponseBean;
import com.astrium.faceo.client.bean.programming.sps2.describeTasking.DescribeTaskingRequestBean;
import com.astrium.faceo.client.bean.programming.sps2.describeTasking.DescribeTaskingResponseBean;
import com.astrium.faceo.client.bean.programming.sps2.getCapabilities.GetCapabilitiesRequestBean;
import com.astrium.faceo.client.bean.programming.sps2.getCapabilities.GetCapabilitiesResponseBean;
import com.astrium.faceo.client.bean.programming.sps2.getSensorAvailability.GetSensorAvailabilityRequestBean;
import com.astrium.faceo.client.bean.programming.sps2.getSensorAvailability.GetSensorAvailabilityResponseBean;
import com.astrium.faceo.client.bean.programming.sps2.request.SurveyPeriodBean;
import com.astrium.faceo.client.common.programming.sps2.Sps2GeneralConstants;
import com.astrium.faceo.client.rpc.programming.sps2.DescribeResultAccessService;
import com.astrium.faceo.client.rpc.programming.sps2.DescribeResultAccessServiceAsync;
import com.astrium.faceo.client.rpc.programming.sps2.DescribeSensorService;
import com.astrium.faceo.client.rpc.programming.sps2.DescribeSensorServiceAsync;
import com.astrium.faceo.client.rpc.programming.sps2.DescribeTaskingService;
import com.astrium.faceo.client.rpc.programming.sps2.DescribeTaskingServiceAsync;
import com.astrium.faceo.client.rpc.programming.sps2.GetCapabilitiesService;
import com.astrium.faceo.client.rpc.programming.sps2.GetCapabilitiesServiceAsync;
import com.astrium.faceo.client.rpc.programming.sps2.GetSensorAvailabilityService;
import com.astrium.faceo.client.rpc.programming.sps2.GetSensorAvailabilityServiceAsync;
import com.astrium.faceo.client.ui.programming.sps2.windows.WindowGetSensorAvailability;
import com.astrium.faceo.client.ui.programming.sps2.windows.WindowOtherOperations;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.form.DateField;
import com.gwtext.client.widgets.form.FormPanel;

/**
 * <B>SITE FACEO</B> <BR>
 * 
 * <P>
 * La classe SpsSensorOperationsManagment g&egrave;re les op&eacute;rations SPS
 * </P>
 * </P>
 * 
 * @author GR
 * @version 1.0, le 04/06/2010
 */
public class SpsSensorOperationsManagment {

	/**
	 * constructor
	 * 
	 */
	public SpsSensorOperationsManagment() {

	} // public SpsSensorOperationsManagment() {

	/**
	 * submit a SPS request for one sensor and show the response
	 * 
	 * @param _operation (String)		: the operation to use for the sensor
	 * @param _sensorUrn (String)		: sensor urn identifier
	 * @param _startPeriod (String)		: start period for sensor availability
	 * @param _endPeriod (String)		: end period for sensor availability
	 * @param _formPanel (FormPanel)	: the parameters panel
	 * @param _submitBtn (Button)		: the submit button
	 * @param _message (String)			: the message to show in pending panel
	 * @param _windowResults (WindowOtherOperations)	: window to show 'other operations 'results
	 * @param _windowSensorAvailability (WindowGetSensorAvailability)	: window to show 'GetSensorAvailability 'results
	 */
	public static void getSensorOperationResult(
			String _operation, 
			final String _sensorUrn, 
			final DateField _startPeriod,
			final DateField _endPeriod,
			final FormPanel _formPanel, 
			final Button _submitBtn, 
			String _message,
			final WindowOtherOperations _windowResults,
			final WindowGetSensorAvailability _windowSensorAvailability) {
		
		if (_operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_GET_CAPABILITIES)) {
			getSensorGetCapabilities(_sensorUrn, _formPanel, _submitBtn, _message, _windowResults);
		} else if (_operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_DESCRIBE_TASKING)) {
			getSensorDescribeTasking(_sensorUrn, _formPanel, _submitBtn, _message, _windowResults);
		} else if (_operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_DESCRIBE_SENSOR)) {
			getSensorDescribeSensor(_sensorUrn, _formPanel, _submitBtn, _message, _windowResults);
		} else if (_operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_DESCRIBE_RESULT_ACCESS)) {
			getSensorDescribeResultAccess(_sensorUrn, _formPanel, _submitBtn, _message, _windowResults);
		} else if (_operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_GET_SENSOR_AVAILABILITY)) {
			getSensorAvailability(_sensorUrn, _startPeriod, _endPeriod, _formPanel, _submitBtn, _message, _windowSensorAvailability);
		} // if
		
	} // public static void getSensorOperationResult(String _operation, final String _sensorUrn, final  FormPanel _formPanel, final  Button _submitBtn, String _message) {
	
	/**
	 * submit a 'GetCapabilities' request for one sensor and show the response
	 * 
	 * @param _sensorUrn (String)		: sensor urn identifier
	 * @param _formPanel (FormPanel)	: the parameters panel
	 * @param _submitBtn (Button)		: the submit button
	 * @param _message (String)			: the message to show in pending panel
	 * @param _windowResults (WindowOtherOperations)	: the window to show the results
	 */
	private static void getSensorGetCapabilities(
			final String _sensorUrn, 
			final FormPanel _formPanel, 
			final Button _submitBtn, 
			String _message,
			final WindowOtherOperations _windowResults) {
	
		if (_formPanel != null) {
			_formPanel.getEl().mask(_message, true);
		}
		
		if (_submitBtn != null) {
			_submitBtn.disable();
		}
		
		final GetCapabilitiesServiceAsync getCapabilitiesServiceAsync = 
			GetCapabilitiesService.Util.getInstance();

		ServiceDefTarget endpoint = (ServiceDefTarget) getCapabilitiesServiceAsync;
		String moduleRelativeURL = GWT.getModuleBaseURL() + Sps2GeneralConstants.GET_CAPABILITIES_CONTROLLER;
		endpoint.setServiceEntryPoint(moduleRelativeURL);

		// Create an Asynchronous callback to handle the result.
		AsyncCallback<GetCapabilitiesResponseBean> callbackGetCapabilities = 
			new AsyncCallback<GetCapabilitiesResponseBean>() {
			public void onSuccess(GetCapabilitiesResponseBean _response) {
				if (_formPanel != null) {
					_formPanel.getEl().unmask();
				}
				
				if (_submitBtn != null) {
					_submitBtn.enable();
				}	

				// do some UI stuff to show success
				if (_response != null) {
					if (_response.getError().getCode() == 0) {
						_windowResults.showOtherResponse(_response.getResponse());
					} else
						com.google.gwt.user.client.Window.alert("error : " + _response.getError().getMessage());
				} // if (_errorBean.getError().getErrorCode() == 0) {
			} // public void onSuccess(ErrorBean _response) {

			public void onFailure(Throwable caught) {
				if (_formPanel != null) {
					_formPanel.getEl().unmask();
				}
				
				if (_submitBtn != null) {
					_submitBtn.enable();
				}

				com.google.gwt.user.client.Window.alert("onFailure : " + caught.getMessage());
			}
		}; //  AsyncCallback<GetCapabilitiesResponseBean> callbackGetCapabilities

		GetCapabilitiesRequestBean _parameters = new GetCapabilitiesRequestBean();
		_parameters.setSensorUrn(_sensorUrn);
		
		getCapabilitiesServiceAsync.getGetCapabilitiesResult(
				Sps2GeneralConstants.SPS_GET_CAPABILITIES, _parameters, true, callbackGetCapabilities);

	} // private static void getSensorGetCapabilities(	
	
	/**
	 * submit a 'DescribeTasking' request for one sensor and show the response
	 * 
	 * @param _sensorUrn (String)		: sensor identifier
	 * @param _formPanel (FormPanel)	: the parameters panel
	 * @param _submitBtn (Button)		: the submit button
	 * @param _message (String)			: the message to show in pending panel
	 * @param _windowResults (WindowOtherOperations)	: the window to show the results
	 */
	private static void getSensorDescribeTasking(
			final String _sensorUrn, 
			final FormPanel _formPanel, 
			final Button _submitBtn, 
			String _message,
			final WindowOtherOperations _windowResults) {

		if (_formPanel != null) {
			_formPanel.getEl().mask(_message, true);
		}
		
		if (_submitBtn != null) {
			_submitBtn.disable();
		}
		
		final DescribeTaskingServiceAsync describeTaskingServiceAsync = 
			DescribeTaskingService.Util.getInstance();

		ServiceDefTarget endpoint = (ServiceDefTarget) describeTaskingServiceAsync;
		String moduleRelativeURL = GWT.getModuleBaseURL() + Sps2GeneralConstants.DESCRIBE_TASKING_CONTROLLER;
		endpoint.setServiceEntryPoint(moduleRelativeURL);

		// Create an Asynchronous callback to handle the result.
		AsyncCallback<DescribeTaskingResponseBean> callbackDescribeTasking = 
			new AsyncCallback<DescribeTaskingResponseBean>() {
			public void onSuccess(DescribeTaskingResponseBean _response) {
				if (_formPanel != null) {
					_formPanel.getEl().unmask();
				}
				
				if (_submitBtn != null) {
					_submitBtn.enable();
				}	

				// do some UI stuff to show success
				if (_response != null) {
					if (_response.getError().getCode() == 0) {
						_windowResults.showOtherResponse(_response.getResponse());
					} else
						com.google.gwt.user.client.Window.alert("error : " + _response.getError().getMessage());
				} // if (_errorBean.getError().getErrorCode() == 0) {
			} // public void onSuccess(ErrorBean _response) {

			public void onFailure(Throwable caught) {
				if (_formPanel != null) {
					_formPanel.getEl().unmask();
				}
				
				if (_submitBtn != null) {
					_submitBtn.enable();
				}

				com.google.gwt.user.client.Window.alert("onFailure : " + caught.getMessage());
			}
		}; //  AsyncCallback<DescribeTaskingResponseBean> callbackDescribeTasking

		DescribeTaskingRequestBean _parameters = new DescribeTaskingRequestBean();
		_parameters.setSensorUrn(_sensorUrn);
		
		describeTaskingServiceAsync.getDescribeTaskingResult(
				Sps2GeneralConstants.SPS_DESCRIBE_TASKING, _parameters, true, callbackDescribeTasking);

	} // private static void getSensorDescribeTasking(final String _sensorUrn, final  FormPanel _formPanel, final  Button _submitBtn, String _message) {

	/**
	 * submit a 'DescribeSensor' request for one sensor and show the response
	 * 
	 * @param _sensorUrn (String)		: sensor identifier
	 * @param _formPanel (FormPanel)	: the parameters panel
	 * @param _submitBtn (Button)		: the submit button
	 * @param _message (String)			: the message to show in pending panel
	 * @param _windowResults (WindowOtherOperations)	: the window to show the results
	 */
	private static void getSensorDescribeSensor(
			final String _sensorUrn, 
			final FormPanel _formPanel, 
			final Button _submitBtn, 
			String _message,
			final WindowOtherOperations _windowResults) {

		if (_formPanel != null) {
			_formPanel.getEl().mask(_message, true);
		}
		
		if (_submitBtn != null) {
			_submitBtn.disable();
		}
		
		final DescribeSensorServiceAsync describeSensorServiceAsync = 
			DescribeSensorService.Util.getInstance();

		ServiceDefTarget endpoint = (ServiceDefTarget) describeSensorServiceAsync;
		String moduleRelativeURL = GWT.getModuleBaseURL() + Sps2GeneralConstants.DESCRIBE_SENSOR_CONTROLLER;
		endpoint.setServiceEntryPoint(moduleRelativeURL);

		// Create an Asynchronous callback to handle the result.
		AsyncCallback<DescribeSensorResponseBean> callbackDescribeSensor = 
			new AsyncCallback<DescribeSensorResponseBean>() {
			public void onSuccess(DescribeSensorResponseBean _response) {
				if (_formPanel != null) {
					_formPanel.getEl().unmask();
				}
				
				if (_submitBtn != null) {
					_submitBtn.enable();
				}	

				// do some UI stuff to show success
				if (_response != null) {
					if (_response.getError().getCode() == 0) {
						_windowResults.showOtherResponse(_response.getResponse());
					} else
						com.google.gwt.user.client.Window.alert("error : " + _response.getError().getMessage());
				} // if (_errorBean.getError().getErrorCode() == 0) {
			} // public void onSuccess(ErrorBean _response) {

			public void onFailure(Throwable caught) {
				if (_formPanel != null) {
					_formPanel.getEl().unmask();
				}
				
				if (_submitBtn != null) {
					_submitBtn.enable();
				}

				com.google.gwt.user.client.Window.alert("onFailure : " + caught.getMessage());
			}
		}; //  AsyncCallback<DescribeSensorResponseBean> callbackDescribeSensor

		DescribeSensorRequestBean _parameters = new DescribeSensorRequestBean();
		_parameters.setSensorUrn(_sensorUrn);
		
		describeSensorServiceAsync.getDescribeSensorResult(
				Sps2GeneralConstants.SPS_DESCRIBE_SENSOR, _parameters, true, callbackDescribeSensor);

	} // private static void getSensorDescribeSensor(final String _sensorUrn, final  FormPanel _formPanel, final  Button _submitBtn, String _message) {
	
	/**
	 * submit a 'DescribeResultAccess' request for one sensor and show the response
	 * 
	 * @param _sensorUrn (String)		: sensor identifier
	 * @param _formPanel (FormPanel)	: the parameters panel
	 * @param _submitBtn (Button)		: the submit button
	 * @param _message (String)			: the message to show in pending panel
	 * @param _windowResults (WindowOtherOperations)	: the window to show the results
	 */
	private static void getSensorDescribeResultAccess(
			final String _sensorUrn, 
			final FormPanel _formPanel, 
			final Button _submitBtn, 
			String _message,
			final WindowOtherOperations _windowResults) {

		if (_formPanel != null) {
			_formPanel.getEl().mask(_message, true);
		}
		
		if (_submitBtn != null) {
			_submitBtn.disable();
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
				if (_formPanel != null) {
					_formPanel.getEl().unmask();
				}
				
				if (_submitBtn != null) {
					_submitBtn.enable();
				}	

				// do some UI stuff to show success
				if (_response != null) {
					if (_response.getError().getCode() == 0) {
						_windowResults.showOtherResponse(_response.getResponse());
					} else
						com.google.gwt.user.client.Window.alert("error : " + _response.getError().getMessage());
				} // if (_errorBean.getError().getErrorCode() == 0) {
			} // public void onSuccess(ErrorBean _response) {

			public void onFailure(Throwable caught) {
				if (_formPanel != null) {
					_formPanel.getEl().unmask();
				}
				
				if (_submitBtn != null) {
					_submitBtn.enable();
				}

				com.google.gwt.user.client.Window.alert("onFailure : " + caught.getMessage());
			}
		}; //  AsyncCallback<DescribeResultAccessResponseBean> callbackDescribeResultAccess

		DescribeResultAccessRequestBean _parameters = new DescribeResultAccessRequestBean();
		_parameters.setSensorUrn(_sensorUrn);
		_parameters.setType(Sps2GeneralConstants.DESCRIBE_ACCESS_SENSOR);
		
		describeResultAccessServiceAsync.getDescribeResultAccessResult(
				Sps2GeneralConstants.SPS_DESCRIBE_RESULT_ACCESS, _parameters, true, callbackDescribeResultAccess);

	} // private static void getSensorDescribeResultAccess(final String _sensorUrn, final  FormPanel _formPanel, final  Button _submitBtn, String _message) {

	/**
	 * submit a 'GetSensorAvailability' request for one sensor and show the response
	 * 
	 * @param _sensorUrn (String)		: sensor identifier
	 * @param _startPeriod (DateField)	: start period for sensor availability
	 * @param _endPeriod (DateField)	: end period for sensor availability
	 * @param _formPanel (FormPanel)	: the parameters panel
	 * @param _submitBtn (Button)		: the submit button
	 * @param _message (String)			: the message to show in pending panel
	 * @param _windowSensorAvailability (WindowGetSensorAvailability)	: the window to show the results
	 */
	private static void getSensorAvailability(
			final String _sensorUrn, 
			final DateField _startPeriod, 
			final DateField _endPeriod, 
			final FormPanel _formPanel, 
			final Button _submitBtn, 
			String _message,
			final WindowGetSensorAvailability _windowSensorAvailability) {

		if (_formPanel != null) {
			_formPanel.getEl().mask(_message, true);
		}
		
		if (_submitBtn != null) {
			_submitBtn.disable();
		}
		
		final GetSensorAvailabilityServiceAsync getSensorAvailabilityServiceAsync = 
			GetSensorAvailabilityService.Util.getInstance();

		ServiceDefTarget endpoint = (ServiceDefTarget) getSensorAvailabilityServiceAsync;
		String moduleRelativeURL = GWT.getModuleBaseURL() + Sps2GeneralConstants.GET_SENSOR_AVAILABILITY_CONTROLLER;
		endpoint.setServiceEntryPoint(moduleRelativeURL);

		// Create an Asynchronous callback to handle the result.
		AsyncCallback<GetSensorAvailabilityResponseBean> callbackGetSensorAvailability = 
			new AsyncCallback<GetSensorAvailabilityResponseBean>() {
			public void onSuccess(GetSensorAvailabilityResponseBean _response) {
				if (_formPanel != null) {
					_formPanel.getEl().unmask();
				}
				
				if (_submitBtn != null) {
					_submitBtn.enable();
				}	

				// do some UI stuff to show success
				if (_response != null) {
					if (_response.getError().getCode() == 0) {
						_windowSensorAvailability.showResponse(_response);
					} else
						com.google.gwt.user.client.Window.alert("error : " + _response.getError().getMessage());
				} // if (_errorBean.getError().getErrorCode() == 0) {
			} // public void onSuccess(ErrorBean _response) {

			public void onFailure(Throwable caught) {
				if (_formPanel != null) {
					_formPanel.getEl().unmask();
				}
				
				if (_submitBtn != null) {
					_submitBtn.enable();
				}

				com.google.gwt.user.client.Window.alert("onFailure : " + caught.getMessage());
			}
		}; //  AsyncCallback<GetSensorAvailabilityResponseBean> callbackGetSensorAvailability

		GetSensorAvailabilityRequestBean _parameters = new GetSensorAvailabilityRequestBean();
		_parameters.setSensorUrn(_sensorUrn);
		SurveyPeriodBean requestedPeriod = new SurveyPeriodBean();
		
		// "2010-06-01T00:00:00Z""
		DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat(Sps2GeneralConstants.REQUESTED_PERIOD_TIME_FORMAT);
		Date startPeriodDate = _startPeriod.getValue();
		String startPeriod = dateTimeFormat.format(startPeriodDate);
		Date endPeriodDate = _endPeriod.getValue();
		String endPeriod = dateTimeFormat.format(endPeriodDate);
		requestedPeriod.setStartDate(startPeriodDate);
		requestedPeriod.setStrStartDate(startPeriod);
		requestedPeriod.setEndDate(endPeriodDate);
		requestedPeriod.setStrEndDate(endPeriod);		
		_parameters.setRequestedPeriod(requestedPeriod);
		
		getSensorAvailabilityServiceAsync.getGetSensorAvailabilityResponse(
				Sps2GeneralConstants.SPS_GET_SENSOR_AVAILABILITY, _parameters, true, callbackGetSensorAvailability);

	} // private static void getSensorAvailability(final String _sensorUrn, final  FormPanel _formPanel, final  Button _submitBtn, String _message) {

} // class
