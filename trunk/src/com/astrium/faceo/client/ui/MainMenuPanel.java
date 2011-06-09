package com.astrium.faceo.client.ui;

import com.astrium.faceo.client.common.Utils;
import com.astrium.faceo.client.images.header.HeaderImages;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.gwtext.client.core.Function;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.Tool;
import com.gwtext.client.widgets.Tool.ToolType;

/**
 * <B>SITE FACEO</B> <BR>
 * 
 * <P>
 * La classe MainMenuPanel g&egrave;re le menu principal
 * </P>
 * </P>
 * 
 * @author GR
 * @version 1.0, le 06/01/2009
 */
public class MainMenuPanel extends Panel {

	/** */
	private final String IFRAME_APPLET_MAIN_MENU = "iframeAppletMainMenu";

	// attributs
	private DockPanel dockPanel;

	private Image imageLogosFACEO;

	private Image imagefaceoTitle;

	private VersionsWindow versionsWindow;

	/**
	 * constructor
	 */
	public MainMenuPanel() {
		super();

		Utils.addIframeToRootElement(IFRAME_APPLET_MAIN_MENU);

		this.setId("faceoMenuPanel");
		this.setBorder(false);

		this.dockPanel = new DockPanel();
		this.dockPanel.getElement().setId("dockPanel_MainMenuPanel");
		this.dockPanel.setHorizontalAlignment(DockPanel.ALIGN_CENTER);
		this.dockPanel.setVerticalAlignment(DockPanel.ALIGN_MIDDLE);
		this.dockPanel.setBorderWidth(0);

		// definition des images du dock
		HeaderImages headerImages = (HeaderImages) GWT.create(HeaderImages.class);

		// ajout des images au dock
		this.imageLogosFACEO = new Image(headerImages.astriumLogo());
		this.imageLogosFACEO.getElement().setId("imageLogosFACEO_MainMenuPanel");
		this.dockPanel.add(this.imageLogosFACEO, DockPanel.WEST);
		this.dockPanel.setCellWidth(this.dockPanel.getWidget(0), 
				(Integer.toString(this.imageLogosFACEO.getWidth()) + " px"));
		this.dockPanel.setCellWidth(this.dockPanel.getWidget(0), 
				(Integer.toString(this.imageLogosFACEO.getWidth()) + " px"));
		this.dockPanel.setCellHorizontalAlignment(this.dockPanel.getWidget(0),
				HasHorizontalAlignment.ALIGN_LEFT);
		this.dockPanel.setCellVerticalAlignment(this.dockPanel.getWidget(0),
				HasVerticalAlignment.ALIGN_MIDDLE);

		this.imagefaceoTitle = new Image(headerImages.appliLogo());
		this.imagefaceoTitle.getElement().setId("imagefaceoTitle_MainMenuPanel");
		this.dockPanel.add(this.imagefaceoTitle, DockPanel.EAST);
		this.dockPanel.setCellWidth(this.dockPanel.getWidget(1), 
				(Integer.toString(this.imageLogosFACEO.getWidth()) + " px"));
		this.dockPanel.setCellHorizontalAlignment(this.dockPanel.getWidget(1),
				HasHorizontalAlignment.ALIGN_RIGHT);
		this.dockPanel.setCellVerticalAlignment(this.dockPanel.getWidget(1),
				HasVerticalAlignment.ALIGN_MIDDLE);

		this.versionsWindow = new VersionsWindow();

		String iconVersion = "versions";
		Tool versionButton = new Tool(new ToolType(iconVersion), new Function() {  
			public void execute() {
				versionsWindow.showWindow();  
			}
		}, "Versions");
		this.addTool(versionButton);

		this.add(this.dockPanel);

	} // public MainMenuPanel() {

	/**
	 * getter on versionWindow
	 * 
	 * @return VersionWindow : display in window the different versions
	 */
	public VersionsWindow getVersionWindow() {
		return this.versionsWindow;
	}

} // class
