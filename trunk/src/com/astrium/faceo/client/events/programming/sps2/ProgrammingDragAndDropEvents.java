package com.astrium.faceo.client.events.programming.sps2;

import java.util.Map;

import com.astrium.faceo.client.bean.programming.sps2.responses.SegmentBean;
import com.astrium.faceo.client.common.GeneralConstant;
import com.astrium.faceo.client.common.Utils;
import com.astrium.faceo.client.common.programming.sps2.Sps2GeneralConstants;
import com.astrium.faceo.client.ui.programming.sps2.view.SpsPanels;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.Element;
import com.gwtext.client.data.Record;
import com.gwtext.client.dd.DragData;
import com.gwtext.client.dd.DragSource;
import com.gwtext.client.widgets.grid.GridDragData;
import com.gwtext.client.widgets.grid.GridPanel;


/**
 * <B>SITE FACEO</B> <BR>
 * 
 * <P>
 * La classe ProgrammingDragAnDDropEvents 
 * </P>
 * </P>
 * 
 * @author GR
 * @version 1.0, le 17/03/2010
 */
public class ProgrammingDragAndDropEvents extends GwtEvent<ProgrammingDragAndDropHandler> {

	// constantes
	private final String IFRAME_APPLET_DRAG_PROGRAMMING_SOLUTIONS = "iframeAppletProgrammingSolutions";

	// attributs
	private static final GwtEvent.Type<ProgrammingDragAndDropHandler> TYPE = 
		new GwtEvent.Type<ProgrammingDragAndDropHandler>();

	private int eventType;

	private DragSource dragSource;

	private DragData dragData;

	/**
	 * constructor
	 * 
	 * @param _eventType 
	 * @param _dragSource 
	 * @param _dragData 
	 * 
	 */
	public ProgrammingDragAndDropEvents(int _eventType, DragSource _dragSource, DragData _dragData) {
		this.eventType = _eventType;
		this.dragSource = _dragSource;
		this.dragData = _dragData;
	} //

	@Override
	protected void dispatch(ProgrammingDragAndDropHandler _handler) {
		switch (this.eventType) {
		case GeneralConstant.DND_EVENT_DROP: // drop
			_handler.onDrop(this);
			break;
		case GeneralConstant.DND_EVENT_DROP_OUT: // dropOut
			_handler.onDropOut(this);
			break;
		case GeneralConstant.DND_EVENT_DROP_OVER: // dropOver
			_handler.onDropOver(this);
			break;
		default:;
		} // switch (this.eventType) {
	} // 

	/**
	 * @return Type<ProgrammingDragAndDropHandler>
	 */
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ProgrammingDragAndDropHandler> getAssociatedType() {
		return TYPE;
	}

	/**
	 * @return Type<ProgrammingDragAndDropHandler>
	 */
	public static Type<ProgrammingDragAndDropHandler> getType() {
		return TYPE;
	}

	/**
	 * 
	 */
	public void onDrop() {
		if (this.dragData instanceof GridDragData) {
			GridDragData gridDragData = (GridDragData) this.dragData;
			GridPanel gridPanel = gridDragData.getGrid();
			String gridPanelId = gridPanel.getId();
			Record[] records = gridDragData.getSelections();

			for (int i = 0; i < records.length; i++) {
				Record record = records[i];

				if (gridPanelId.equalsIgnoreCase(Sps2GeneralConstants.GRID_PANEL_PROGRAMMING_TASKS)) {
				} else if (gridPanelId.equalsIgnoreCase(Sps2GeneralConstants.GRID_PANEL_PROGRAMMING_SOLUTIONS)) {
					String segmentId = SpsPanels.getSpsSolutionsPanel().getSegmentId(record);
					@SuppressWarnings("static-access")
					Map<String, SegmentBean> segments = 
						SpsPanels.getSpsSolutionsPanel().getFeasibilitySegments();
					if (segments.containsKey(segmentId)) {
						SegmentBean segment = segments.get(segmentId);
						if (segment != null) {
							if (!segment.getShowOnCartography()) {
								SpsPanels.getSpsSolutionsPanel().drawSegmentOnCartography(segment, true);
								record.set(Sps2GeneralConstants.SHOW_SEGMENT_ON_CARTOGRAPHY, true);
								record.commit();
								gridPanel.getStore().commitChanges();
							} // if (!segment.getShowOnCartography()) {
						} // if (segment != null) {
					} // if (segments.containsKey(segmentId)) {

					Element elementSegment = this.dragSource.getDragEl();
					Utils.putDragAndDropTofront(false, elementSegment, IFRAME_APPLET_DRAG_PROGRAMMING_SOLUTIONS);
				}
			} //  for (int i = 0; i < records.length; i++) {
		} // if (this.dragData instanceof GridDragData) {
	} // 

	/**
	 * 
	 * hide the html iframe over applet
	 */
	public void onDropOut() {
		manageIframeOverApplet(false);
	} //

	/**
	 * 
	 * show the hml iframe over applet
	 */
	public void onDropOver() {
		manageIframeOverApplet(true);
	} // 

	/**
	 * 
	 * @param _toShow (bolean) :
	 */
	private void manageIframeOverApplet(boolean _toShow) {
		if (this.dragData instanceof GridDragData) {
			GridDragData gridDragData = (GridDragData) this.dragData;
			GridPanel gridPanel = gridDragData.getGrid();
			String gridPanelId = gridPanel.getId();
			Element element = this.dragSource.getDragEl();

			if ( (element != null) && (gridPanelId.equalsIgnoreCase(Sps2GeneralConstants.GRID_PANEL_PROGRAMMING_SOLUTIONS)) ) {
				Utils.putDragAndDropTofront(_toShow, element, IFRAME_APPLET_DRAG_PROGRAMMING_SOLUTIONS);
			} //
		} // 
	} // 

} // class
