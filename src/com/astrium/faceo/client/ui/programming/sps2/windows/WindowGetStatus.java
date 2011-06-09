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
 * La classe WindowGetStatus g&egrave;re le panneau d'affichage 
 * de la r&eacute;ponse &agrave; l'op&eacute;ration SPS 'GetStatus'
 * </P>
 * </P>
 * 
 * @author GR
 * @version 1.0, le 29/06/2010
 */
public class WindowGetStatus {
	
	// constantes
	private static int WIDTH = 550;

	private static int HEIGHT = 500;

	private final String IFRAME_APPLET_GET_STATUS = "iframeAppletGetStatus";	

	// attributs
	private Window windowGetStatusResponse;  

	private VerticalPanel verticalWindowPanel;

	private HTML htmlGetStatusResponse;
	
	private boolean firstLoading = true;

	/**
	 * constructor
	 * 
	 * @param _windowId (String) : window identifier 
	 * 
	 */
	public WindowGetStatus(String _windowId) {

		Utils.addIframeToRootElement(IFRAME_APPLET_GET_STATUS);

		this.windowGetStatusResponse = 
			new Window("Status", WIDTH, HEIGHT, false, false);
		this.windowGetStatusResponse.setId(_windowId + "_windowGetStatusResponse");  
		this.windowGetStatusResponse.setMaximizable(false);  
		this.windowGetStatusResponse.setMinimizable(false);  
		this.windowGetStatusResponse.setResizable(true);
		this.windowGetStatusResponse.setDraggable(true);  
		this.windowGetStatusResponse.setMargins(0);
		this.windowGetStatusResponse.setPagePosition(250, 250);
		this.windowGetStatusResponse.setBodyStyle("background-color:#FEFEFE");
		this.windowGetStatusResponse.setCloseAction(Window.HIDE);
		this.windowGetStatusResponse.setAutoScroll(true);
//		this.windowGetStatusResponse.setSize("100%", "100%");

		this.verticalWindowPanel = new VerticalPanel();
		this.verticalWindowPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		this.verticalWindowPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		this.verticalWindowPanel.setSize("100%", "100%");

		this.htmlGetStatusResponse = new HTML("");
		this.htmlGetStatusResponse.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		this.htmlGetStatusResponse.setVisible(true);
		this.verticalWindowPanel.add(this.htmlGetStatusResponse);

		this.windowGetStatusResponse.setButtonAlign(Position.CENTER);
		this.windowGetStatusResponse.setBorder(false);

		this.windowGetStatusResponse.add(this.verticalWindowPanel);  

		this.windowGetStatusResponse.addListener(new WindowListenerAdapter() {
			public void onClose(Panel panel) {
				// appel de la fonction JavaScript permettant de passer la fenetre au 1er plan au-dessus de l'applet WorlWind
				Utils.putWindowTofront(false, windowGetStatusResponse.getId(), IFRAME_APPLET_GET_STATUS);
			} // public void onClose(Panel panel) {

			public void onHide(Component component) {
				// appel de la fonction JavaScript permettant de passer la fenetre au 1er plan au-dessus de l'applet WorlWind
				Utils.putWindowTofront(false, windowGetStatusResponse.getId(), IFRAME_APPLET_GET_STATUS);
			} // public void onClose(Panel panel) {

			public void onMove(BoxComponent component, int x, int y) {  
				// on verifie que la barre de titre de la fenetre est accessible
				Utils.resetWindowTop(x, y, windowGetStatusResponse);
				// appel de la fonction JavaScript permettant de passer la fenetre au 1er plan au-dessus de l'applet WorlWind
				Utils.putWindowTofront(true, windowGetStatusResponse.getId(), IFRAME_APPLET_GET_STATUS);
			} // public void onMove(BoxComponent component, int x, int y) { 

			public void onResize(Window source, int width, int height) {
				// appel de la fonction JavaScript permettant de passer la fenetre au 1er plan au-dessus de l'applet WorlWind
				Utils.putWindowTofront(true, windowGetStatusResponse.getId(), IFRAME_APPLET_GET_STATUS);
			} // public void onResize
		}); // window.addListener(new WindowListenerAdapter() {

	} // public WindowGetStatus() {

	/**
	 * M&eacute;thode permettant la mise &agrave; jour de la fen&ecirc;tre avec 
	 * la r&eacute;ponse &agrave; l'op&eacute;ration 'GetStatus'
	 * 
	 * @param _infos (String) : GetStatus response with HTML format
	 * 
	 */
	public void showGetStatusResponse(String _infos) {

		//		Widget widget = this.verticalWindowPanel.getWidget(0);
		//		((HTML) widget).setHTML(html);
		this.htmlGetStatusResponse.setHTML(_infos);

		this.windowGetStatusResponse.show();

		if (this.firstLoading) {
			this.windowGetStatusResponse.center();
			this.firstLoading = false;
		} // if (this.firstLoading) {
		
		Utils.putWindowTofront(
				true, this.windowGetStatusResponse.getId(), IFRAME_APPLET_GET_STATUS);

	} // public void showGetStatusResponse(String _infos) {

} // class
