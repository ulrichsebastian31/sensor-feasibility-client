package com.astrium.faceo.client.bean.programming.sps2;import java.io.Serializable;import java.util.Date;/** * <B>SITE FACEO</B> <BR> *  * <P> * Cette classe est un conteneur d'informations pour les taches SPS * </P> * </P> *  * @author  GR * @version 1.0, le 20/05/2010 */public class TaskBean implements Serializable {	/**	 * 	 */	private static final long serialVersionUID = -5818099728098070554L;	/** error Message */	private ErrorBean error = new ErrorBean();		/** task identifier */	private String id = null;		/** task name */	private String name = null;	/** task user */	private String user = null;		/** operation */	private String operation = null;		/** sensor */	private String sensor = null;	/** status */	private String status = null;		/** request */	private String request = null;		/** request status */	private String requestStatus = null;	/** response */	private String response = null;	/** response type */	private String responseType = null;	/** created Time */	private Date createdTime = null;		/** last Updated Time */	private Date lastUpdatedTime = null;		/** true if Updated task from updated Thread, false else */	private boolean updatedTask = false;	/**	 * d&eacute;but des m&eacute;thodes	 	 * 	 * Constructeur par d&eacute;faut : vide : convention Bean	 */	/**	 * Default Constructor. The Default Constructor's explicit declaration	 * is required for a serializable class. (GWT)	*/	public TaskBean() {	}	/** ----------------------- getters ----------------------- */		/** 	 * getter on error	 * 	 * @return ErrorBean : the error message	*/	public ErrorBean getError() {		return this.error;	}	/** 	 * getter on id	 * 	 * @return String : task identifier	*/	public String getId() {		return (this.id != null) ? this.id : "";	}		/** 	 * getter on name	 * 	 * @return String : the name	*/	public String getName() {		return (this.name != null) ? this.name : "";	}	/** 	 * getter on user	 * 	 * @return String : user task	*/	public String getUser() {		return (this.user != null) ? this.user : "";	}		/** 	 * getter on operation	 * 	 * @return String : operation	*/	public String getOperation() {		return (this.operation != null) ? this.operation : "";	}	/** 	 * getter on sensor	 * 	 * @return String : sensor identifier	*/	public String getSensor() {		return (this.sensor != null) ? this.sensor : "";	}	/** 	 * getter on status	 * 	 * @return String : status	*/	public String getStatus() {		return (this.status != null) ? this.status : "";	}	/** 	 * getter on request	 * 	 * @return String : request	*/	public String getRequest() {		return (this.request != null) ? this.request : "";	}		/** 	 * getter on requestStatus	 * 	 * @return String : request status	*/	public String getRequestStatus() {		return (this.requestStatus != null) ? this.requestStatus : "";	}	/** 	 * getter on response	 * 	 * @return String : response	*/	public String getResponse() {		return (this.response != null) ? this.response : "";	}	/** 	 * getter on responseType	 * 	 * @return String : response type : GetFeasibility, GetStatus, Submit	*/	public String getResponseType() {		return (this.responseType != null) ? this.responseType : "";	}	/** 	 * getter on createdTime	 * 	 * @return Date : created time	*/	public Date getCreatedTime() {		return this.createdTime;	}	/** 	 * getter on lastUpdatedTime	 * 	 * @return Date : last updated time	*/	public Date getLastUpdatedTime() {		return this.lastUpdatedTime;	}	/** 	 * getter on updatedTask	 * 	 * @return boolean : true if Updated task from updated Thread, false else	*/	public boolean getUpdatedTask() {		return this.updatedTask;	}	/** ----------------------- setters ----------------------- */	/** 	 * setter on error	 * 	 * @param _error (ErrorBean) : error message value	*/	public void setErrorMessage(ErrorBean _error) {		this.error = _error;	}		/** 	 * setter on id	 * 	 * @param _idTask (String) : task identifier value	*/	public void setId(String _idTask) {		this.id = _idTask;	}	/** 	 * setter on name	 * 	 * @param _name (String) : task name value	*/	public void setName(String _name) {		this.name = _name;	}	/** 	 * setter on user	 * 	 * @param _user (String) : user task value	*/	public void setUser(String _user) {		this.user = _user;	}		/** 	 * setter on operation	 * 	 * @param _operation (String) : operation value	*/	public void setOperation(String _operation) {		this.operation = _operation;	}	/** 	 * setter on sensor	 * 	 * @param _sensor (String) : sensor value	*/	public void setSensor(String _sensor) {		this.sensor = _sensor;	}	/** 	 * setter on status	 * 	 * @param _status (String) : status value	*/	public void setStatus(String _status) {		this.status = _status;	}	/** 	 * setter on request	 * 	 * @param _request (String) : request value	*/	public void setRequest(String _request) {		this.request = _request;	}	/** 	 * setter on requestStatus	 * 	 * @param _requestStatus (String) : request status value	*/	public void setRequestStatus(String _requestStatus) {		this.requestStatus = _requestStatus;	}	/** 	 * setter on response	 * 	 * @param _response (String) : response value	*/	public void setResponse(String _response) {		this.response = _response;	}	/** 	 * setter on responseType	 * 	 * @param _responseType (String) : response type value : GetFeasibility, GetStatus, Submit	*/	public void setResponseType(String _responseType) {		this.responseType = _responseType;	}	/** 	 * setter on createdTime	 * 	 * @param _createdTime (Date) : created time value	*/	public void setCreatedTime(Date _createdTime) {		this.createdTime = _createdTime;	}	/** 	 * setter on lastUpdatedTime	 * 	 * @param _lastUpdatedTime (Date) : last updated time value	*/	public void setLastUpdatedTime(Date _lastUpdatedTime) {		this.lastUpdatedTime = _lastUpdatedTime;	}		/** 	 * setter on updatedTask	 * 	 * @param _updatedTask (boolean) :  true if Updated task from updated Thread, false else	*/	public void setUpdatedTask(boolean _updatedTask) {		this.updatedTask = _updatedTask;	}	} // class