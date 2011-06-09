package com.astrium.faceo.client.bean.programming.sps2.describeSensor;import java.io.Serializable;import com.astrium.faceo.client.bean.programming.sps2.ErrorBean;/** * <B>FACEO HMAFO</B> <BR> *  * <P> * This class is a informations container for the operation's results  * 'DescribeSensor' which returns SPS 'DescribeSensor' details for one sensor *   * The DescribeSensor operation allows a client to request a detailed description  * of a sensor. The request can be targeted at a description that was valid  * at a certain point in or during a certain period of time in the past.  *  * </P> * </P> *  * @author  GR * @version 1.0, le 19/11/2010 */public class DescribeSensorResponseBean implements Serializable {	/**	 * 	 */	private static final long serialVersionUID = 4570405706543716466L;	/** error Message */	private ErrorBean error = new ErrorBean();		/** DescribeTaking response */	private String response = null;	/**	 * methods starting	 	 * 	 * default constructor : empty : Bean convention	 */	/**	 * Default Constructor. The Default Constructor's explicit declaration	 * is required for a serializable class. (GWT)	*/	public DescribeSensorResponseBean() {	}	/** ----------------------- getters ----------------------- */		/** 	 * getter on error	 * 	 * @return ErrorBean : the error message	*/	public ErrorBean getError() {		return this.error;	}	/** 	 * getter on response	 * 	 * @return String : DescribeSensor response	*/	public String getResponse() {		return (this.response != null) ? this.response : "";	}	/** ----------------------- setters ----------------------- */	/** 	 * setter on error	 * 	 * @param _error (ErrorBean) : error message value	*/	public void setErrorMessage(ErrorBean _error) {		this.error = _error;	}		/** 	 * setter on response	 * 	 * @param _response (String) : DescribeSensor response value	*/	public void setResponse(String _response) {		this.response = _response;	}} // class