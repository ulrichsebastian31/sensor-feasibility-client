package com.astrium.faceo.client.ui.programming.sps2.windows;

import com.astrium.faceo.client.common.Utils;
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
 * La classe WindowOtherOperations g&egrave;re le panneau d'affichage 
 * de la r&eacute;ponse aux op&eacute;rations SPS :
 * 		- GetCapabilities
 * 		- DescribeTasking
 * 		- DescribeSensor
 * 		- GetSensorAvailability 
 * </P>
 * </P>
 * 
 * @author GR
 * @version 1.0, le 29/06/2010
 */
public class WindowOtherOperations {
	
	// constantes
	private static int WIDTH = 700;

	private static int HEIGHT = 500;

	private final String IFRAME_APPLET_OTHER_OPERATIONS = "iframeAppletOtherOperations";	

	// attributs
	private Window windowOtherResponse;  

	private VerticalPanel verticalWindowPanel;

	private HTML htmlGetStatusResponse;
	
	private boolean firstLoading = true;

	/**
	 * constructor
	 * 
	 * @param _windowId (String) : window identifier 
	 * 
	 */
	public WindowOtherOperations(String _windowId) {

		Utils.addIframeToRootElement(IFRAME_APPLET_OTHER_OPERATIONS);

		this.windowOtherResponse = 
			new Window("Response", WIDTH, HEIGHT, false, false);
		this.windowOtherResponse.setId(_windowId + "_windowOtherResponse");  
		this.windowOtherResponse.setMaximizable(false);  
		this.windowOtherResponse.setMinimizable(false);  
		this.windowOtherResponse.setResizable(true);
		this.windowOtherResponse.setDraggable(true);  
		this.windowOtherResponse.setMargins(0);
		this.windowOtherResponse.setPagePosition(250, 250);
		this.windowOtherResponse.setBodyStyle("background-color:#FEFEFE");
		this.windowOtherResponse.setCloseAction(Window.HIDE);
		this.windowOtherResponse.setAutoScroll(true);

		this.verticalWindowPanel = new VerticalPanel();
		this.verticalWindowPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		this.verticalWindowPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		this.verticalWindowPanel.setSize("100%", "100%");

		this.htmlGetStatusResponse = new HTML("");
		this.htmlGetStatusResponse.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		this.htmlGetStatusResponse.setVisible(true);
		this.verticalWindowPanel.add(this.htmlGetStatusResponse);

		this.windowOtherResponse.setButtonAlign(Position.CENTER);
		this.windowOtherResponse.setBorder(false);

		this.windowOtherResponse.add(this.verticalWindowPanel);  

		this.windowOtherResponse.addListener(new WindowListenerAdapter() {
			public void onClose(Panel panel) {
				// appel de la fonction JavaScript permettant de passer la fenetre au 1er plan au-dessus de l'applet WorlWind
				Utils.putWindowTofront(false, windowOtherResponse.getId(), IFRAME_APPLET_OTHER_OPERATIONS);
			} // public void onClose(Panel panel) {

			public void onHide(Component component) {
				// appel de la fonction JavaScript permettant de passer la fenetre au 1er plan au-dessus de l'applet WorlWind
				Utils.putWindowTofront(false, windowOtherResponse.getId(), IFRAME_APPLET_OTHER_OPERATIONS);
			} // public void onClose(Panel panel) {

			public void onMove(BoxComponent component, int x, int y) {  
				// on verifie que la barre de titre de la fenetre est accessible
				Utils.resetWindowTop(x, y, windowOtherResponse);
				// appel de la fonction JavaScript permettant de passer la fenetre au 1er plan au-dessus de l'applet WorlWind
				Utils.putWindowTofront(true, windowOtherResponse.getId(), IFRAME_APPLET_OTHER_OPERATIONS);
			} // public void onMove(BoxComponent component, int x, int y) { 

			public void onResize(Window source, int width, int height) {
				// appel de la fonction JavaScript permettant de passer la fenetre au 1er plan au-dessus de l'applet WorlWind
				Utils.putWindowTofront(true, windowOtherResponse.getId(), IFRAME_APPLET_OTHER_OPERATIONS);
			} // public void onResize
		}); // window.addListener(new WindowListenerAdapter() {

	} // public WindowOtherOperations() {

	/**
	 * M&eacute;thode permettant la mise &agrave; jour de la fen&ecirc;tre avec 
	 * la r&eacute;ponse aux op&eacute;rations :
	 * 		- GetCapabilities
	 * 		- DescribeTasking
	 * 		- DescribeSensor
	 * 		- DescribeResultAccess
	 * 		- GetSensorAvailability 
	 * 
	 * @param _infos (String) : response with HTML format
	 * 
	 */
	public void showOtherResponse(String _infos) {

		this.htmlGetStatusResponse.setHTML(_infos);

		this.windowOtherResponse.show();

		if (this.firstLoading) {
			this.windowOtherResponse.center();
			this.firstLoading = false;
		} // if (this.firstLoading) {
		
		Utils.putWindowTofront(
				true, this.windowOtherResponse.getId(), IFRAME_APPLET_OTHER_OPERATIONS);

	} // public void showOtherResponse(String _infos) {

} // class
