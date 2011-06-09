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
 * La classe WindowDescribeResultAccess g&egrave;re le panneau d'affichage 
 * de la r&eacute;ponse aux op&eacute;rations SPS :
 * 		- DescribeResultAccess
 * </P>
 * </P>
 * 
 * @author GR
 * @version 1.0, le 08/07/2010
 */
public class WindowDescribeResultAccess {
	
	// constantes
	private static int WIDTH = 700;

	private static int HEIGHT = 500;

	private final String IFRAME_APPLET_DESCRIBE_RESULT_ACCESS = "iframeAppletDescribeResultAccess";	

	// attributs
	private Window windowDescribeResultAccess;  

	private VerticalPanel verticalWindowPanel;

	private HTML htmlDescribeResultAccessResponse;
	
	private boolean firstLoading = true;

	/**
	 * constructor
	 * 
	 * @param _windowId (String) : window identifier 
	 * 
	 */
	public WindowDescribeResultAccess(String _windowId) {

		Utils.addIframeToRootElement(IFRAME_APPLET_DESCRIBE_RESULT_ACCESS);

		this.windowDescribeResultAccess = 
			new Window("DescribeResultAccess", WIDTH, HEIGHT, false, false);
		this.windowDescribeResultAccess.setId(_windowId + "_windowDescribeResultAccess");  
		this.windowDescribeResultAccess.setMaximizable(false);  
		this.windowDescribeResultAccess.setMinimizable(false);  
		this.windowDescribeResultAccess.setResizable(true);
		this.windowDescribeResultAccess.setDraggable(true);  
		this.windowDescribeResultAccess.setMargins(0);
		this.windowDescribeResultAccess.setPagePosition(250, 250);
		this.windowDescribeResultAccess.setBodyStyle("background-color:#FEFEFE");
		this.windowDescribeResultAccess.setCloseAction(Window.HIDE);
		this.windowDescribeResultAccess.setAutoScroll(true);

		this.verticalWindowPanel = new VerticalPanel();
		this.verticalWindowPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		this.verticalWindowPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		this.verticalWindowPanel.setSize("100%", "100%");

		this.htmlDescribeResultAccessResponse = new HTML("");
		this.htmlDescribeResultAccessResponse.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		this.htmlDescribeResultAccessResponse.setVisible(true);
		this.verticalWindowPanel.add(this.htmlDescribeResultAccessResponse);

		this.windowDescribeResultAccess.setButtonAlign(Position.CENTER);
		this.windowDescribeResultAccess.setBorder(false);

		this.windowDescribeResultAccess.add(this.verticalWindowPanel);  

		this.windowDescribeResultAccess.addListener(new WindowListenerAdapter() {
			public void onClose(Panel panel) {
				// appel de la fonction JavaScript permettant de passer la fenetre au 1er plan au-dessus de l'applet WorlWind
				Utils.putWindowTofront(false, windowDescribeResultAccess.getId(), IFRAME_APPLET_DESCRIBE_RESULT_ACCESS);
			} // public void onClose(Panel panel) {

			public void onHide(Component component) {
				// appel de la fonction JavaScript permettant de passer la fenetre au 1er plan au-dessus de l'applet WorlWind
				Utils.putWindowTofront(false, windowDescribeResultAccess.getId(), IFRAME_APPLET_DESCRIBE_RESULT_ACCESS);
			} // public void onClose(Panel panel) {

			public void onMove(BoxComponent component, int x, int y) {  
				// on verifie que la barre de titre de la fenetre est accessible
				Utils.resetWindowTop(x, y, windowDescribeResultAccess);
				// appel de la fonction JavaScript permettant de passer la fenetre au 1er plan au-dessus de l'applet WorlWind
				Utils.putWindowTofront(true, windowDescribeResultAccess.getId(), IFRAME_APPLET_DESCRIBE_RESULT_ACCESS);
			} // public void onMove(BoxComponent component, int x, int y) { 

			public void onResize(Window source, int width, int height) {
				// appel de la fonction JavaScript permettant de passer la fenetre au 1er plan au-dessus de l'applet WorlWind
				Utils.putWindowTofront(true, windowDescribeResultAccess.getId(), IFRAME_APPLET_DESCRIBE_RESULT_ACCESS);
			} // public void onResize
		}); // window.addListener(new WindowListenerAdapter() {

	} // public SpsWindowOtherOperations() {

	/**
	 * M&eacute;thode permettant la mise &agrave; jour de la fen&ecirc;tre avec 
	 * la r&eacute;ponse aux op&eacute;rations :
     * 		- DescribeResultAccess
	 * 
	 * @param _infos (String) : response with HTML format
	 * 
	 */
	public void showResponse(String _infos) {

		this.htmlDescribeResultAccessResponse.setHTML(_infos);

		this.windowDescribeResultAccess.show();

		if (this.firstLoading) {
			this.windowDescribeResultAccess.center();
			this.firstLoading = false;
		} // if (this.firstLoading) {
		
		Utils.putWindowTofront(
				true, this.windowDescribeResultAccess.getId(), IFRAME_APPLET_DESCRIBE_RESULT_ACCESS);

	} // public void showResponse(String _infos) {

} // class
