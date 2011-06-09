package com.astrium.faceo.client.ui.programming.sps2.windows;

import java.util.Map.Entry;

import com.astrium.faceo.client.bean.programming.sps2.getSensorAvailability.GetSensorAvailabilityResponseBean;
import com.astrium.faceo.client.bean.programming.sps2.request.SurveyPeriodBean;
import com.astrium.faceo.client.common.Utils;
import com.astrium.faceo.client.common.programming.sps2.Sps2GeneralConstants;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwtext.client.core.Position;
import com.gwtext.client.widgets.BoxComponent;
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.Window;
import com.gwtext.client.widgets.event.WindowListenerAdapter;

/**
 * <B>SITE FACEO</B> <BR>
 * 
 * <P>
 * La classe WindowGetSensorAvailability g&egrave;re le panneau d'affichage de
 * la r&eacute;ponse &agrave; l'op&eacute;ration SPS 'GetSensorAvailibility'
 * </P>
 * </P>
 * 
 * @author GR
 * @version 1.0, le 09/11/2010
 */
public class WindowGetSensorAvailability {

	// constantes
	private static int WIDTH = 620;

	private static int HEIGHT = 550;

	private final String IFRAME_APPLET_GET_SENSOR_AVAILABILITY = "iframeAppletGetSensorAvailability";

	// attributs
	private Window windowResponse;

	private VerticalPanel verticalWindowPanel;

	private HTML htmlResponse;

	private Grid responseGrid;

	private boolean firstLoading = true;

	/**
	 * constructor
	 * 
	 * @param _windowId
	 *            (String) : window identifier
	 * 
	 */
	public WindowGetSensorAvailability(String _windowId) {

		Utils.addIframeToRootElement(IFRAME_APPLET_GET_SENSOR_AVAILABILITY);

		this.windowResponse = new Window("Response", WIDTH, HEIGHT, false, false);
		this.windowResponse.setId(_windowId + "_windowResponse");
		this.windowResponse.setMaximizable(false);
		this.windowResponse.setMinimizable(false);
		this.windowResponse.setResizable(true);
		this.windowResponse.setDraggable(true);
		this.windowResponse.setMargins(0);
		this.windowResponse.setPagePosition(250, 250);
		this.windowResponse.setBodyStyle("background-color:#FEFEFE");
		this.windowResponse.setCloseAction(Window.HIDE);
		this.windowResponse.setAutoScroll(true);

		this.verticalWindowPanel = new VerticalPanel();
		this.verticalWindowPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		this.verticalWindowPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		this.verticalWindowPanel.setSize("100%", "100%");

		this.htmlResponse = new HTML("");
		this.htmlResponse.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		this.htmlResponse.setVisible(true);
		this.verticalWindowPanel.add(this.htmlResponse);

		this.responseGrid = new Grid(1, 3);
		// You can use the CellFormatter to affect the layout of the grid's
		// cells.
		this.responseGrid.getColumnFormatter().setWidth(0, "200px");
		this.responseGrid.getColumnFormatter().setWidth(1, "200px");
		this.responseGrid.getColumnFormatter().setWidth(2, "200px");

		for (int i = 0; i < this.responseGrid.getRowCount(); i++) {
			this.responseGrid.getCellFormatter().setHorizontalAlignment(i, 0, HasHorizontalAlignment.ALIGN_CENTER);
			this.responseGrid.getCellFormatter().setHorizontalAlignment(i, 1, HasHorizontalAlignment.ALIGN_CENTER);
			this.responseGrid.getCellFormatter().setHorizontalAlignment(i, 2, HasHorizontalAlignment.ALIGN_CENTER);
		} // for

		this.responseGrid.setHTML(0, 0, "Id");
		this.responseGrid.setHTML(0, 1, "Begin");
		this.responseGrid.setHTML(0, 2, "End");
		this.verticalWindowPanel.add(this.responseGrid);

		this.windowResponse.setButtonAlign(Position.CENTER);
		this.windowResponse.setBorder(false);

		this.windowResponse.add(this.verticalWindowPanel);

		this.windowResponse.addListener(new WindowListenerAdapter() {
			public void onClose(Panel panel) {
				// appel de la fonction JavaScript permettant de passer la
				// fenetre au 1er plan au-dessus de l'applet WorlWind
				Utils.putWindowTofront(false, windowResponse.getId(), IFRAME_APPLET_GET_SENSOR_AVAILABILITY);
			} // public void onClose(Panel panel) {

			public void onHide(Component component) {
				// appel de la fonction JavaScript permettant de passer la
				// fenetre au 1er plan au-dessus de l'applet WorlWind
				Utils.putWindowTofront(false, windowResponse.getId(), IFRAME_APPLET_GET_SENSOR_AVAILABILITY);
			} // public void onClose(Panel panel) {

			public void onMove(BoxComponent component, int x, int y) {
				// on verifie que la barre de titre de la fenetre est accessible
				Utils.resetWindowTop(x, y, windowResponse);
				// appel de la fonction JavaScript permettant de passer la
				// fenetre au 1er plan au-dessus de l'applet WorlWind
				Utils.putWindowTofront(true, windowResponse.getId(), IFRAME_APPLET_GET_SENSOR_AVAILABILITY);
			} // public void onMove(BoxComponent component, int x, int y) {

			public void onResize(Window source, int width, int height) {
				// appel de la fonction JavaScript permettant de passer la
				// fenetre au 1er plan au-dessus de l'applet WorlWind
				Utils.putWindowTofront(true, windowResponse.getId(), IFRAME_APPLET_GET_SENSOR_AVAILABILITY);
			} // public void onResize
		}); // window.addListener(new WindowListenerAdapter() {

	} // public WindowOtherOperations() {

	/**
	 * M&eacute;thode permettant la mise &agrave; jour de la fen&ecirc;tre avec
	 * la r&eacute;ponse &agrave; l'op&eacute;ration 'GetSensorAvailability'
	 * 
	 * @param _response
	 *            (GetSensorAvailabilityResponseBean) : response to show
	 * 
	 */
	public void showResponse(GetSensorAvailabilityResponseBean _response) {

		// remplacement des chevrons par leur code HTML
		String html = "<h3>" + _response.getSensorUrn() + "<br>" + Sps2GeneralConstants.SPS_GET_SENSOR_AVAILABILITY + "</h3><br><br>"
				+ "<div><b>Response period :</b></div>" 
				+ "<div><b>begin : </b>"
				+ _response.getResponsePeriod().getStrStartDate() + "</div>" 
				+ "<div><b>end : </b>"
				+ _response.getResponsePeriod().getStrEndDate() + "</div>" 
				+ "<div>&nbsp;</div>"
				+ "<div><b>Available periods :</b></div>"
				+ "<div>&nbsp;</div>";
		this.htmlResponse.setHTML(html);
		this.htmlResponse.setHeight("50px");

		int nbRows = _response.getAvailabilityPeriod().size() + 1;
		this.responseGrid.resizeRows(nbRows);

		int num = 0;
		int row = 0;
		for (Entry<String, SurveyPeriodBean> periods : _response.getAvailabilityPeriod().entrySet()) {
			SurveyPeriodBean period = periods.getValue();
			String id = periods.getKey();

			row = num + 1;
			this.responseGrid.setHTML(row, 0, id);
			this.responseGrid.setHTML(row, 1, period.getStrStartDate());
			this.responseGrid.setHTML(row, 2, period.getStrEndDate());

			this.responseGrid.getCellFormatter().setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_CENTER);
			this.responseGrid.getCellFormatter().setHorizontalAlignment(row, 1, HasHorizontalAlignment.ALIGN_CENTER);
			this.responseGrid.getCellFormatter().setHorizontalAlignment(row, 2, HasHorizontalAlignment.ALIGN_CENTER);

			num++;
		} // for

		this.windowResponse.show();

		if (this.firstLoading) {
			this.windowResponse.center();
			this.firstLoading = false;
		} // if (this.firstLoading) {

		Utils.putWindowTofront(true, this.windowResponse.getId(), IFRAME_APPLET_GET_SENSOR_AVAILABILITY);

	} // public void showResponse(String _infos) {
} // class
