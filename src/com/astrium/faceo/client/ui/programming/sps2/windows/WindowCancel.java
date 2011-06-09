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
 * La classe WindowCancel g&egrave;re le panneau d'affichage 
 * de la r&eacute;ponse &agrave; l'op&eacute;ration SPS 'Cancel'
 * </P>
 * </P>
 * 
 * @author GR
 * @version 1.0, le 20/08/2010
 */
public class WindowCancel {
	
	// constantes
	private static int WIDTH = 550;

	private static int HEIGHT = 500;

	private final String IFRAME_APPLET_CANCEL = "iframeAppletCancel";	

	// attributs
	private Window windowCancelResponse;  

	private VerticalPanel verticalWindowPanel;

	private HTML htmlCancelResponse;
	
	private boolean firstLoading = true;

	/**
	 * constructor
	 * 
	 * @param _windowId (String) : window identifier 
	 * 
	 */
	public WindowCancel(String _windowId) {

		Utils.addIframeToRootElement(IFRAME_APPLET_CANCEL);

		this.windowCancelResponse = 
			new Window("Status", WIDTH, HEIGHT, false, false);
		this.windowCancelResponse.setId(_windowId + "_windowCancelResponse");  
		this.windowCancelResponse.setMaximizable(false);  
		this.windowCancelResponse.setMinimizable(false);  
		this.windowCancelResponse.setResizable(true);
		this.windowCancelResponse.setDraggable(true);  
		this.windowCancelResponse.setMargins(0);
		this.windowCancelResponse.setPagePosition(250, 250);
		this.windowCancelResponse.setBodyStyle("background-color:#FEFEFE");
		this.windowCancelResponse.setCloseAction(Window.HIDE);
		this.windowCancelResponse.setAutoScroll(true);
//		this.windowCancelResponse.setSize("100%", "100%");

		this.verticalWindowPanel = new VerticalPanel();
		this.verticalWindowPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		this.verticalWindowPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		this.verticalWindowPanel.setSize("100%", "100%");

		this.htmlCancelResponse = new HTML("");
		this.htmlCancelResponse.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		this.htmlCancelResponse.setVisible(true);
		this.verticalWindowPanel.add(this.htmlCancelResponse);

		this.windowCancelResponse.setButtonAlign(Position.CENTER);
		this.windowCancelResponse.setBorder(false);

		this.windowCancelResponse.add(this.verticalWindowPanel);  

		this.windowCancelResponse.addListener(new WindowListenerAdapter() {
			public void onClose(Panel panel) {
				// appel de la fonction JavaScript permettant de passer la fenetre au 1er plan au-dessus de l'applet WorlWind
				Utils.putWindowTofront(false, windowCancelResponse.getId(), IFRAME_APPLET_CANCEL);
			} // public void onClose(Panel panel) {

			public void onHide(Component component) {
				// appel de la fonction JavaScript permettant de passer la fenetre au 1er plan au-dessus de l'applet WorlWind
				Utils.putWindowTofront(false, windowCancelResponse.getId(), IFRAME_APPLET_CANCEL);
			} // public void onClose(Panel panel) {

			public void onMove(BoxComponent component, int x, int y) {  
				// on verifie que la barre de titre de la fenetre est accessible
				Utils.resetWindowTop(x, y, windowCancelResponse);
				// appel de la fonction JavaScript permettant de passer la fenetre au 1er plan au-dessus de l'applet WorlWind
				Utils.putWindowTofront(true, windowCancelResponse.getId(), IFRAME_APPLET_CANCEL);
			} // public void onMove(BoxComponent component, int x, int y) { 

			public void onResize(Window source, int width, int height) {
				// appel de la fonction JavaScript permettant de passer la fenetre au 1er plan au-dessus de l'applet WorlWind
				Utils.putWindowTofront(true, windowCancelResponse.getId(), IFRAME_APPLET_CANCEL);
			} // public void onResize
		}); // window.addListener(new WindowListenerAdapter() {

	} // public WindowCancel() {

	/**
	 * M&eacute;thode permettant la mise &agrave; jour de la fen&ecirc;tre avec 
	 * la r&eacute;ponse &agrave; l'op&eacute;ration 'Cancel'
	 * 
	 * @param _infos (String) : Cancel response with HTML format
	 * 
	 */
	public void showCancelResponse(String _infos) {

		//		Widget widget = this.verticalWindowPanel.getWidget(0);
		//		((HTML) widget).setHTML(html);
		this.htmlCancelResponse.setHTML(_infos);

		this.windowCancelResponse.show();

		if (this.firstLoading) {
			this.windowCancelResponse.center();
			this.firstLoading = false;
		} // if (this.firstLoading) {
		
		Utils.putWindowTofront(
				true, this.windowCancelResponse.getId(), IFRAME_APPLET_CANCEL);

	} // public void showCancelResponse(String _infos) {

} // class
