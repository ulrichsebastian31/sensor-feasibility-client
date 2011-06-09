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
 * La classe WindowValidate g&egrave;re le panneau d'affichage 
 * de la r&eacute;ponse &agrave; l'op&eacute;ration SPS 'Validate'
 * </P>
 * </P>
 * 
 * @author GR
 * @version 1.0, le 15/12/2010
 */
public class WindowValidate {
	
	// constantes
	private static int WIDTH = 550;

	private static int HEIGHT = 500;

	private final String IFRAME_APPLET_VALIDATE = "iframeAppletValidate";	

	// attributs
	private Window windowValidateResponse;  

	private VerticalPanel verticalWindowPanel;

	private HTML htmlValidateResponse;
	
	private boolean firstLoading = true;

	/**
	 * constructor
	 * 
	 * @param _windowId (String) : window identifier 
	 * 
	 */
	public WindowValidate(String _windowId) {
		Utils.addIframeToRootElement(IFRAME_APPLET_VALIDATE);

		this.windowValidateResponse = 
			new Window("Status", WIDTH, HEIGHT, false, false);
		this.windowValidateResponse.setId(_windowId + "_windowConfirmResponse");  
		this.windowValidateResponse.setMaximizable(false);  
		this.windowValidateResponse.setMinimizable(false);  
		this.windowValidateResponse.setResizable(true);
		this.windowValidateResponse.setDraggable(true);  
		this.windowValidateResponse.setMargins(0);
		this.windowValidateResponse.setPagePosition(250, 250);
		this.windowValidateResponse.setBodyStyle("background-color:#FEFEFE");
		this.windowValidateResponse.setCloseAction(Window.HIDE);
		this.windowValidateResponse.setAutoScroll(true);

		this.verticalWindowPanel = new VerticalPanel();
		this.verticalWindowPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		this.verticalWindowPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		this.verticalWindowPanel.setSize("100%", "100%");

		this.htmlValidateResponse = new HTML("");
		this.htmlValidateResponse.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		this.htmlValidateResponse.setVisible(true);
		this.verticalWindowPanel.add(this.htmlValidateResponse);

		this.windowValidateResponse.setButtonAlign(Position.CENTER);
		this.windowValidateResponse.setBorder(false);

		this.windowValidateResponse.add(this.verticalWindowPanel);  

		this.windowValidateResponse.addListener(new WindowListenerAdapter() {
			public void onClose(Panel panel) {
				// appel de la fonction JavaScript permettant de passer la fenetre au 1er plan au-dessus de l'applet WorlWind
				Utils.putWindowTofront(false, windowValidateResponse.getId(), IFRAME_APPLET_VALIDATE);
			} // public void onClose(Panel panel) {

			public void onHide(Component component) {
				// appel de la fonction JavaScript permettant de passer la fenetre au 1er plan au-dessus de l'applet WorlWind
				Utils.putWindowTofront(false, windowValidateResponse.getId(), IFRAME_APPLET_VALIDATE);
			} // public void onClose(Panel panel) {

			public void onMove(BoxComponent component, int x, int y) {  
				// on verifie que la barre de titre de la fenetre est accessible
				Utils.resetWindowTop(x, y, windowValidateResponse);
				// appel de la fonction JavaScript permettant de passer la fenetre au 1er plan au-dessus de l'applet WorlWind
				Utils.putWindowTofront(true, windowValidateResponse.getId(), IFRAME_APPLET_VALIDATE);
			} // public void onMove(BoxComponent component, int x, int y) { 

			public void onResize(Window source, int width, int height) {
				// appel de la fonction JavaScript permettant de passer la fenetre au 1er plan au-dessus de l'applet WorlWind
				Utils.putWindowTofront(true, windowValidateResponse.getId(), IFRAME_APPLET_VALIDATE);
			} // public void onResize
		}); // window.addListener(new WindowListenerAdapter() {

	} // public WindowValidate() {

	/**
	 * M&eacute;thode permettant la mise &agrave; jour de la fen&ecirc;tre avec 
	 * la r&eacute;ponse &agrave; l'op&eacute;ration 'Confirm'
	 * 
	 * @param _infos (String) : Confirm response with HTML format
	 * 
	 */
	public void showValidateResponse(String _infos) {
		this.htmlValidateResponse.setHTML(_infos);

		this.windowValidateResponse.show();

		if (this.firstLoading) {
			this.windowValidateResponse.center();
			this.firstLoading = false;
		} // if (this.firstLoading) {
		
		Utils.putWindowTofront(
				true, this.windowValidateResponse.getId(), IFRAME_APPLET_VALIDATE);
	} // public void showValidateResponse(String _infos) {

} // class
