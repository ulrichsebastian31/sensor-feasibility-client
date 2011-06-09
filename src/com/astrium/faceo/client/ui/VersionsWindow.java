package com.astrium.faceo.client.ui;

import com.astrium.faceo.client.common.GeneralConstant;
import com.astrium.faceo.client.common.Utils;
import com.astrium.faceo.client.common.faceo.FaceoConstants;
import com.astrium.faceo.client.service.FacadeUtil;
import com.astrium.faceo.client.service.facadeCartoWWD.FacadeCartoWwd;
import com.gwtext.client.widgets.BoxComponent;
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.Window;
import com.gwtext.client.widgets.event.WindowListenerAdapter;

/**
 * <B>SITE FACEO</B> <BR>
 * 
 * <P>
 * La classe VersionWindow g&egrave;re la fen&ecirc;tre d'information sur les versions
 * </P>
 * </P>
 * 
 * @author GR
 * @version 1.0, le 10/09/2009
 */
public class VersionsWindow {

	// internationalisation
	private static final FaceoConstants faceoConstants =
		(FaceoConstants) com.google.gwt.core.client.GWT.create(FaceoConstants.class);

	// constantes
	private final String IFRAME_APPLET_VERSION_WINDOW = "iframeAppletVersionWindow";

	private final int WINDOW_WIDTH = 280;

	private final int WINDOW_HEIGHT = 145;

	// attributs
	private Window windowsVersion;

	/**
	 * constructor
	 * 
	 */
	public VersionsWindow() {
		
		Utils.addIframeToRootElement(IFRAME_APPLET_VERSION_WINDOW);

		this.windowsVersion = new Window(faceoConstants.versionsWindowTitle(),
				WINDOW_WIDTH, WINDOW_HEIGHT, false, false);
		this.windowsVersion.setModal(false);  
		this.windowsVersion.setMaximizable(false);  
		this.windowsVersion.setMinimizable(false);  
		this.windowsVersion.setDraggable(true);  
		this.windowsVersion.setMargins(0);
		this.windowsVersion.setPagePosition(150, 150);
		this.windowsVersion.setBodyStyle("background-color:#FEFEFE");
		this.windowsVersion.setCloseAction(Window.HIDE);

		this.windowsVersion.addListener(new WindowListenerAdapter() {
			public void onClose(Panel panel) {
				// appel de la fonction JavaScript permettant de passer la fenetre au 1er plan au-dessus de l'applet WorlWind
				Utils.putWindowTofront(false, windowsVersion.getId(), IFRAME_APPLET_VERSION_WINDOW);
			} // public void onClose

			public void onResize(Window windowJ, int width, int height) { 
				// appel de la fonction JavaScript permettant de passer la fenetre au 1er plan au-dessus de l'applet WorlWind
				Utils.putWindowTofront(true, windowsVersion.getId(), IFRAME_APPLET_VERSION_WINDOW);
			} // public void onResize

			public void onMove(BoxComponent component, int x, int y) {  
				// on verifie que la barre de titre de la fenetre est accessible
				Utils.resetWindowTop(x, y, windowsVersion);
				// appel de la fonction JavaScript permettant de passer la fenetre au 1er plan au-dessus de l'applet WorlWind
				Utils.putWindowTofront(true, windowsVersion.getId(), IFRAME_APPLET_VERSION_WINDOW);
			} // public void onMove

			public void onHide(Component component) {  
				// appel de la fonction JavaScript permettant de passer la fenetre au 1er plan au-dessus de l'applet WorlWind
				Utils.putWindowTofront(false, windowsVersion.getId(), IFRAME_APPLET_VERSION_WINDOW);
			} // public void onHide  
		}); // window.addListener

		this.windowsVersion.setHtml("");

	} // public VersionWindow() {

	/**
	 * display all versions 
	 *
	 */			
	public void showWindow() {

		String html = FacadeCartoWwd.JSgetVersionsAppletWWD();
		String str[]= html.split("-");

		String webBrowser = "";
		if (FacadeUtil.isMSIE6()) webBrowser="MSIE6";
		else if (FacadeUtil.isMSIE7()) webBrowser="MSIE7";
		else if (FacadeUtil.isMSIE8()) webBrowser="MSIE8";
		else if (FacadeUtil.isGecko()) webBrowser=FacadeUtil.versionGecko();
		else if (FacadeUtil.isChrome()) webBrowser="Chrome";
		else if (FacadeUtil.isSafari()) webBrowser="Safari"; 

		String html0 = "<table style=\"font-size:12px\" width=\"100%\" height=\"100%\"><tr><td><b>Web Browser : </b></td><td align=\"right\">" + webBrowser + "</td></tr>"
//		+ "<tr><td><b>User agent : </b></td><td align=\"right\">" + FacadeUtil.getUserAgent() + "</td></tr>"
		+ "<tr><td><b>Java Run Time Environment : </b></td><td align=\"right\">" + HomePage.getHomePageBean().getJreVersion() + "</td></tr>"
		+ "<tr><td><b>World Wind Applet : </b></td><td align=\"right\">" + str[0] + "</td></tr>"
		+ "<tr><td><b>World Wind : </b></td><td align=\"right\">" + str[3] + "</td></tr>"
		+ "<tr><td><b>Application : </b></td><td align=\"right\">" + GeneralConstant.APPLICATION_VERSION + "</td></tr></table></font>";

		this.windowsVersion.setHtml(html0);
		this.windowsVersion.show();
		this.windowsVersion.center();

		Utils.putWindowTofront(
				true, this.windowsVersion.getId(), IFRAME_APPLET_VERSION_WINDOW);

	} // private void showWindow() {

} // class
