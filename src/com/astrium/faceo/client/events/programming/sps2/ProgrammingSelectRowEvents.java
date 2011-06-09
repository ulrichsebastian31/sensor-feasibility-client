package com.astrium.faceo.client.events.programming.sps2;

import com.astrium.faceo.client.common.GeneralConstant;
import com.astrium.faceo.client.ui.programming.sps2.view.SpsPanels;
import com.google.gwt.event.shared.GwtEvent;


/**
 * <B>SITE FACEO</B> <BR>
 * 
 * <P>
 * La classe ProgrammingSelectRowEvents 
 * </P>
 * </P>
 * 
 * @author GR
 * @version 1.0, le 05/05/2010
 */
public class ProgrammingSelectRowEvents extends GwtEvent<ProgrammingSelectRowHandler> {

	// constantes

	// attributs
	private static final GwtEvent.Type<ProgrammingSelectRowHandler> TYPE = 
		new GwtEvent.Type<ProgrammingSelectRowHandler>();

	private int eventType;

	int currentCartoIndex;

	/**
	 * constructor
	 * 
	 * @param _eventType (int)			:
	 * @param _currentCartoIndex (int)	:
	 * 
	 */
	public ProgrammingSelectRowEvents(int _eventType, 
			int _currentCartoIndex) {

		this.eventType = _eventType;
		this.currentCartoIndex = _currentCartoIndex;

	} // public ProgrammingSelectRowEvents() {

	@Override
	protected void dispatch(ProgrammingSelectRowHandler _handler) {

		switch (this.eventType) {
		case GeneralConstant.SELECT_ROW_EVENT: // select row
			_handler.onSelectRow(this);
			break;
		default:;
		} // switch (this.eventType) {

	} // protected void dispatch(ProgrammingSelectRowHandler _handler) {

	/**
	 * @return Type<ProgrammingSelectRowHandler>
	 */
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ProgrammingSelectRowHandler> getAssociatedType() {
		return TYPE;
	}

	/**
	 * @return Type<ProgrammingSelectRowHandler>
	 */
	public static Type<ProgrammingSelectRowHandler> getType() {
		return TYPE;
	}

	/**
	 * 
	 */
	public void onSelectRow() {
		
//		((ProgrammingPanels) HomePage.getWestPanel().getMissionRequestPanel(GeneralConstant.PROGRAMMING_PANEL_ID))
//		.getPanelProgrammingSolutionsPanel().selectProductRow(currentCartoIndex);

		SpsPanels.getSpsSolutionsPanel().selectProductRow(this.currentCartoIndex);

	} // public void onSelectRow

} // class
