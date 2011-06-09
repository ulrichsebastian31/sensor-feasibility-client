package com.astrium.faceo.client.ui.programming.sps2.view;

import com.astrium.faceo.client.common.programming.sps2.Sps2GeneralConstants;
import com.gwtext.client.data.Record;

/**
 * <B>SITE FACEO</B> <BR>
 * 
 * <P>
 * La classe SpsTasksUtils 
 * </P>
 * </P>
 * 
 * @author ASTRIUM
 * @version 1.0, le 26/085/2010
 */
public class SpsTasksUtils {

	/**
	 * constructor
	 * 
	 */
	public SpsTasksUtils() {

	} // public SpsTasksUtils() {

	/**
	 * @param _record (Record) 		: grid record
	 * @param _operationId (String) : operation column identifier
	 * @param _requestStatusId (String)	: request status column identifier
	 * @param _taskStatusId (String) 	: task status column identifier
	 * 
	 * @return boolean : true if renderable, false else
	 */
	public static boolean showResultsRenderable(Record _record, String _operationId, 
			String _requestStatusId, String _taskStatusId) {

		String operation = _record.getAsString(_operationId);
		String requestStatus = _record.getAsString(_requestStatusId);
		String taskStatus = _record.getAsString(_taskStatusId);

		boolean ok = false;
		if (operation != null) {
			if (requestStatus != null) {
				if ( (operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_GET_FEASIBILITY)) 
						|| (operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_SUBMIT)) 
						|| (operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_UPDATE)) 
						|| (operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_CONFIRM)) 
						|| (operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_RESERVE)) 
						|| (operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_VALIDATE)) ) {
					if ( (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_FEASIBLE)) 
							|| (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_PENDING)) 
							|| (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_INEXECUTION)) 
							|| (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_ACCEPTED)) 
							|| (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_RESERVED)) 
							|| (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_NOT_FEASIBLE)) 
							|| (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_COMPLETED)) ) {
						ok = true;
					} // if
				} // if (operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_GET_FEASIBILITY)) {
			} // if (requestStatus != null) {

			if ((!ok) && (taskStatus != null)) {
				if ((operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_GET_FEASIBILITY))
						|| (operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_SUBMIT))
						|| (operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_UPDATE))
						|| (operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_CONFIRM))
						|| (operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_RESERVE))
						|| (operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_VALIDATE))) {
					if ((taskStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_FEASIBLE))
							|| (taskStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_PENDING))
							|| (taskStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_INEXECUTION))
							|| (taskStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_ACCEPTED))
							|| (taskStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_RESERVED))
							|| (taskStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_NOT_FEASIBLE))
							|| (taskStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_COMPLETED))) {
						ok = true;
					} // if
				} // if
			} // if ((!ok) && (taskStatus != null)) {
		} // if (operation != null) {

		return ok;
	} // public static boolean showResultsRenderable(Record _record, String _operationId, String _taskStatusId) {

	/**
	 * @param _record (Record) 		: grid record
	 * @param _operationId (String) : operation column identifier
	 * @param _requestStatusId (String)	: request status column identifier
	 * @param _taskStatusId (String) 	: task status column identifier
	 * 
	 * @return boolean : true if renderable, false else
	 */
	public static boolean getStatusRenderable(Record _record, String _operationId, 
			String _requestStatusId, String _taskStatusId) {

		String operation = _record.getAsString(_operationId);
		String requestStatus = _record.getAsString(_requestStatusId);
		String taskStatus = _record.getAsString(_taskStatusId);

		boolean ok = false;
		if (operation != null) {
			if (requestStatus != null) {
				if ((operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_GET_FEASIBILITY))
						|| (operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_SUBMIT))
						|| (operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_CANCEL))
						|| (operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_UPDATE))
						|| (operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_CONFIRM))
						|| (operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_VALIDATE))
						|| (operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_RESERVE))) {
					if ((requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_FEASIBLE))
							|| (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_PENDING))
							|| (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_INEXECUTION))
							|| (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_CANCELLED))
							|| (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_NOT_FEASIBLE))
							|| (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_RESERVED))
							|| (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_ACCEPTED))
							|| (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_COMPLETED))) {
						ok = true;
					} // if
				} // if
			} // if (requestStatus != null) {

			if ((!ok) && (taskStatus != null)) {
				if ((operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_GET_FEASIBILITY))
						|| (operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_SUBMIT))
						|| (operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_CANCEL))
						|| (operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_UPDATE))
						|| (operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_CONFIRM))
						|| (operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_VALIDATE))
						|| (operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_RESERVE))) {
					if ((taskStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_FEASIBLE))
							|| (taskStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_PENDING))
							|| (taskStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_INEXECUTION))
							|| (taskStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_CANCELLED))
							|| (taskStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_NOT_FEASIBLE))
							|| (taskStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_RESERVED))
							|| (taskStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_ACCEPTED))
							|| (taskStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_COMPLETED))) {
						ok = true;
					} // if
				} // if
			} // if ((!ok) && (taskStatus != null)) {
		} // if (operation != null) {

		return ok;
	} // public static boolean getStatusRenderable(Record _record, String _operationId, String _taskStatusId) {

	/**
	 * @param _record (Record) 			: grid record
	 * @param _operationId (String) 	: operation column identifier
	 * @param _requestStatusId (String)	: request status column identifier
	 * @param _taskStatusId (String) 	: task status column identifier
	 * 
	 * @return boolean : true if renderable, false else
	 */
	public static boolean cancelRenderable(Record _record, String _operationId, 
			String _requestStatusId, String _taskStatusId) {

		String operation = _record.getAsString(_operationId);
		String requestStatus = _record.getAsString(_requestStatusId);
		String taskStatus = _record.getAsString(_taskStatusId);

		boolean ok = false;

		if (operation != null) {
			if (requestStatus != null) {
				if (operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_SUBMIT)) {
					if ( (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_ACCEPTED)) 
							|| (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_PENDING))
							|| (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_RESERVED)) ) {
						ok = true;
					} // if
				} else if (operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_GET_FEASIBILITY)) {
					if ( (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_ACCEPTED)) 
							|| (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_PENDING))
							|| (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_RESERVED)) ) {
						ok = true;
					} // if
				} else if (operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_UPDATE)) {
					if ( (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_ACCEPTED)) 
							|| (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_PENDING))
							|| (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_RESERVED)) ) {
						ok = true;
					} // if
				} else if (operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_RESERVE)) {
					if ( (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_ACCEPTED)) 
							|| (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_RESERVED)) 
							|| (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_INEXECUTION)) 
							|| (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_PENDING))) {
						ok = true;
					} // if
				} else if (operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_CONFIRM)) {
					if ( (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_ACCEPTED)) 
							|| (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_RESERVED)) 
							|| (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_INEXECUTION)) 
							|| (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_PENDING))) {
						ok = true;
					} // if
				} // if
			} // if (requestStatus != null) {

			if ((!ok) && (taskStatus != null)) {
				if (operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_SUBMIT)) {
					if ((taskStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_ACCEPTED))
							|| (taskStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_PENDING))
							|| (taskStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_RESERVED))) {
						ok = true;
					} // if
				} else if (operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_GET_FEASIBILITY)) {
					if ((taskStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_ACCEPTED))
							|| (taskStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_PENDING))
							|| (taskStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_RESERVED))) {
						ok = true;
					} // if
				} else if (operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_UPDATE)) {
					if ((taskStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_ACCEPTED))
							|| (taskStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_PENDING))
							|| (taskStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_RESERVED))) {
						ok = true;
					} // if
				} else if (operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_RESERVE)) {
					if ((taskStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_ACCEPTED))
							|| (taskStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_RESERVED))
							|| (taskStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_INEXECUTION))
							|| (taskStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_PENDING))) {
						ok = true;
					} // if
				} else if (operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_CONFIRM)) {
					if ((taskStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_ACCEPTED))
							|| (taskStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_RESERVED))
							|| (taskStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_INEXECUTION))
							|| (taskStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_PENDING))) {
						ok = true;
					} // if
				} // if
			} // if ((!ok) && (taskStatus != null)) {
		} // if (operation != null) {

		return ok;
	} // public static boolean cancelRenderable(Record _record, String _operationId, String _taskStatusId) {

	/**
	 * @param _record (Record) 			: grid record
	 * @param _operationId (String) 	: operation column identifier
	 * @param _requestStatusId (String)	: request status column identifier
	 * @param _taskStatusId (String) 	: task status column identifier
	 * 
	 * @return boolean : true if renderable, false else
	 */
	public static boolean confirmRenderable(Record _record, String _operationId, String _requestStatusId,
			String _taskStatusId) {

		String operation = _record.getAsString(_operationId);
		String requestStatus = _record.getAsString(_requestStatusId);
		String taskStatus = _record.getAsString(_taskStatusId);

		boolean ok = false;

		if ((operation != null)
				&& ((operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_RESERVE)) 
						|| (operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_UPDATE)))) {
			if ((requestStatus != null)
					&& ((requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_ACCEPTED))
							|| (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_RESERVED))
							|| (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_PENDING)))) {
			} // 

			if ((!ok)
					&& (taskStatus != null)
					&& ((taskStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_ACCEPTED))
							|| (taskStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_RESERVED))
							|| (taskStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_PENDING)))) {
				ok = true;
			} // 
		} // 

		return ok;
	} // 

	/**
	 * @param _record (Record) 			: grid record
	 * @param _operationId (String) 	: operation column identifier
	 * @param _requestStatusId (String)	: request status column identifier
	 * @param _taskStatusId (String) 	: task status column identifier
	 * 
	 * @return boolean : true if renderable, false else
	 */
	public static boolean validateRenderable(Record _record, String _operationId, 
			String _requestStatusId, String _taskStatusId) {

		String operation = _record.getAsString(_operationId);
		String requestStatus = _record.getAsString(_requestStatusId);
		String taskStatus = _record.getAsString(_taskStatusId);

		boolean ok = false;

		if ((operation != null)
				&& ((operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_CONFIRM)) )) {
			if ((requestStatus != null)
					&& ((requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_ACCEPTED))
							|| (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_CONFIRMED))
							|| (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_PENDING)))) {
				ok = true;
			} // 

			if ((!ok)
					&& (taskStatus != null)
					&& ((taskStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_ACCEPTED))
							|| (taskStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_CONFIRMED))
							|| (taskStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_PENDING)))) {
				ok = true;
			} // 
		}// 

		return ok;
	} // 

	/**
	 * @param _record (Record) 		 : grid record
	 * @param _operationId (String) 	: operation column identifier
	 * @param _requestStatusId (String)	: request status column identifier
	 * @param _taskStatusId (String) : task status column identifier
	 * 
	 * @return boolean : true if renderable, false else
	 */
	public static boolean describeResultRenderable(Record _record, String _operationId,
			String _requestStatusId, String _taskStatusId) {
		String operation = _record.getAsString(_operationId);
		String requestStatus = _record.getAsString(_requestStatusId);
		String taskStatus = _record.getAsString(_taskStatusId);

		boolean ok = false;

		if (!operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_CANCEL)) {
			if ((requestStatus != null)
					&& ((requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_ACCEPTED))
							|| (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_FEASIBLE))
							|| (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_COMPLETED)) 
							|| (requestStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_RESERVED)))) {
				ok = true;
			} // 

			if ((!ok)
					&& (taskStatus != null)
					&& ((taskStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_ACCEPTED))
							|| (taskStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_FEASIBLE))
							|| (taskStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_COMPLETED)) 
							|| (taskStatus.equalsIgnoreCase(Sps2GeneralConstants.STATUS_RESERVED)))) {
				ok = true;
			} //
		} //

		return ok;
	} // 

} // class
