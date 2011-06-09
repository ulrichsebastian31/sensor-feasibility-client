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
 * La classe WindowConfirm g&egrave;re le panneau d'affichage 
 * de la r&eacute;ponse &agrave; l'op&eacute;ration SPS 'Confirm'
 * </P>
 * </P>
 * 
 * @author GR
 * @version 1.0, le 30/11/2010
 */
public class WindowConfirm {
	
	// constantes
	private static int WIDTH = 550;

	private static int HEIGHT = 500;

	private final String IFRAME_APPLET_CONFIRM = "iframeAppletConfirm";	

	// attributs
	private Window windowConfirmResponse;  

	private VerticalPanel verticalWindowPanel;

	private HTML htmlConfirmResponse;
	
	private boolean firstLoading = true;

	/**
	 * constructor
	 * 
	 * @param _windowId (String) : window identifier 
	 * 
	 */
	public WindowConfirm(String _windowId) {

		Utils.addIframeToRootElement(IFRAME_APPLET_CONFIRM);

		this.windowConfirmResponse = 
			new Window("Status", WIDTH, HEIGHT, false, false);
		this.windowConfirmResponse.setId(_windowId + "_windowConfirmResponse");  
		this.windowConfirmResponse.setMaximizable(false);  
		this.windowConfirmResponse.setMinimizable(false);  
		this.windowConfirmResponse.setResizable(true);
		this.windowConfirmResponse.setDraggable(true);  
		this.windowConfirmResponse.setMargins(0);
		this.windowConfirmResponse.setPagePosition(250, 250);
		this.windowConfirmResponse.setBodyStyle("background-color:#FEFEFE");
		this.windowConfirmResponse.setCloseAction(Window.HIDE);
		this.windowConfirmResponse.setAutoScroll(true);

		this.verticalWindowPanel = new VerticalPanel();
		this.verticalWindowPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		this.verticalWindowPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		this.verticalWindowPanel.setSize("100%", "100%");

		this.htmlConfirmResponse = new HTML("");
		this.htmlConfirmResponse.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		this.htmlConfirmResponse.setVisible(true);
		this.verticalWindowPanel.add(this.htmlConfirmResponse);

		this.windowConfirmResponse.setButtonAlign(Position.CENTER);
		this.windowConfirmResponse.setBorder(false);

		this.windowConfirmResponse.add(this.verticalWindowPanel);  

		this.windowConfirmResponse.addListener(new WindowListenerAdapter() {
			public void onClose(Panel panel) {
				// appel de la fonction JavaScript permettant de passer la fenetre au 1er plan au-dessus de l'applet WorlWind
				Utils.putWindowTofront(false, windowConfirmResponse.getId(), IFRAME_APPLET_CONFIRM);
			} // public void onClose(Panel panel) {

			public void onHide(Component component) {
				// appel de la fonction JavaScript permettant de passer la fenetre au 1er plan au-dessus de l'applet WorlWind
				Utils.putWindowTofront(false, windowConfirmResponse.getId(), IFRAME_APPLET_CONFIRM);
			} // public void onClose(Panel panel) {

			public void onMove(BoxComponent component, int x, int y) {  
				// on verifie que la barre de titre de la fenetre est accessible
				Utils.resetWindowTop(x, y, windowConfirmResponse);
				// appel de la fonction JavaScript permettant de passer la fenetre au 1er plan au-dessus de l'applet WorlWind
				Utils.putWindowTofront(true, windowConfirmResponse.getId(), IFRAME_APPLET_CONFIRM);
			} // public void onMove(BoxComponent component, int x, int y) { 

			public void onResize(Window source, int width, int height) {
				// appel de la fonction JavaScript permettant de passer la fenetre au 1er plan au-dessus de l'applet WorlWind
				Utils.putWindowTofront(true, windowConfirmResponse.getId(), IFRAME_APPLET_CONFIRM);
			} // public void onResize
		}); // window.addListener(new WindowListenerAdapter() {

	} // public WindowConfirm() {

	/**
	 * M&eacute;thode permettant la mise &agrave; jour de la fen&ecirc;tre avec 
	 * la r&eacute;ponse &agrave; l'op&eacute;ration 'Confirm'
	 * 
	 * @param _infos (String) : Confirm response with HTML format
	 * 
	 */
	public void showConfirmResponse(String _infos) {
		this.htmlConfirmResponse.setHTML(_infos);

		this.windowConfirmResponse.show();

		if (this.firstLoading) {
			this.windowConfirmResponse.center();
			this.firstLoading = false;
		} // if (this.firstLoading) {
		
		Utils.putWindowTofront(
				true, this.windowConfirmResponse.getId(), IFRAME_APPLET_CONFIRM);
	} // public void showConfirmResponse(String _infos) {

} // class
