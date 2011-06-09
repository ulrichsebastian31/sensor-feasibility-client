package com.astrium.faceo.client.ui;

/*
 * @(#)HomePage.java	 1.0  09/06/2008
 *
 *
 * PROJET       : FACEO
 *
 * LANGUAGE     : Java
 *
 * DESCRIPTION  : La classe HomePage gere les widgets de la page d'accueil
 *
 *
 * CREATION :
 * ---------------------------------------------------------------------
 * | Date       | Version | Description                                |
 * ---------------------------------------------------------------------
 * | 09/06/2008 |    1.0  |                                            |
 * ---------------------------------------------------------------------
 *
 * MODIFICATION :
 * ---------------------------------------------------------------------
 * | Date       | Version | Description                                |
 * ---------------------------------------------------------------------
 * |            |         |                                            |
 * ---------------------------------------------------------------------
 *
 */

// import 

// GWT libraries (Google Web Toolkit)
import com.astrium.faceo.client.bean.ConfigBean;
import com.astrium.faceo.client.bean.HomePageBean;
import com.astrium.faceo.client.common.GeneralConstant;
import com.astrium.faceo.client.common.faceo.FaceoConstants;
import com.astrium.faceo.client.service.facadeCartoWWD.FacadeCartoWwd;
import com.astrium.faceo.client.ui.cartography.cartoEarth.CartoPanel;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.gwtext.client.core.Margins;
import com.gwtext.client.core.RegionPosition;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.Viewport;
import com.gwtext.client.widgets.event.PanelListenerAdapter;
import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.layout.BorderLayoutData;
import com.gwtext.client.widgets.layout.FitLayout;

/**
 * <B>SITE FACEO</B> <BR>
 * 
 * <P>
 * La classe HomePage g&egrave;re les widgets de la page d'accueil
 * </P>
 * </P>
 * 
 * @author ASTRIUM
 * @version 1.0, le 09/06/2008
 */
public class HomePage implements EntryPoint {

	private static final FaceoConstants faceoConstants =
		(FaceoConstants) com.google.gwt.core.client.GWT.create(FaceoConstants.class);

	// panneau South
	private static Panel southPanel;	

	// panneau central
	private static Panel centerPanel;

	// panneau North
	private static MainMenuPanel northPanel;

	// panneau West
	private static MultiMissionsRequestPanel westPanel;

	// panneau East
	private static MultiCartographicPanel eastPanel;

	// panneau d'affichage de la cartographie
	private static CartoPanel panelCartoPanel;

	// objet 'bean' contenant les objets de la page
	private static HomePageBean homePageBean;

	/**
	 * 
	 */
	public void onModuleLoad() {	

		homePageBean = new HomePageBean();

		homePageBean.setConfigBean(new ConfigBean());

		homePageBean.setNavigatorPlatform(FacadeCartoWwd.JSgetUserPlatform());

		// recuperation du parametre "cartoSelectMode"
		try {
			String selectionMode = Window.Location.getParameter(GeneralConstant.CARTO_OBJECTS_PARAM_MODE_SELECTION);
			if (selectionMode != null) {
				if ( (selectionMode.equalsIgnoreCase(GeneralConstant.CARTO_OBJECTS_CLIC_MODE_SELECTION))
						|| (selectionMode.equalsIgnoreCase(GeneralConstant.CARTO_OBJECTS_OVER_MODE_SELECTION)) ) {
					HomePage.getHomePageBean().setSelectionMode(selectionMode);
					com.google.gwt.user.client.Window.alert(
							"SelectionMode : " + HomePage.getHomePageBean().getSelectionMode());
				}
			}
		} catch (Exception exeption) {
		} finally {
		}

		Panel mainPanel = new Panel();
		mainPanel.setBorder(false);
		mainPanel.setPaddings(0);
		mainPanel.setAutoScroll(true);
		mainPanel.setLayout(new FitLayout());
		mainPanel.setId(faceoConstants.functionsTitle());

		Panel borderPanel = new Panel();
		borderPanel.setLayout(new BorderLayout());
		borderPanel.setId(GeneralConstant.BORDER_PANEL_ID);

		//------------------------ North Panel --------------------------
		northPanel = new MainMenuPanel();
		northPanel.setHeight(GeneralConstant.NORTH_PANEL_HEIGHT);
		northPanel.setId(GeneralConstant.NORTH_PANEL_ID);
		northPanel.setCollapsible(true);
		northPanel.setTitle(faceoConstants.menuTitle());
		northPanel.setTitleCollapse(true);
		// --------------------------------------------------------

		northPanel.setLayout(new FitLayout());

		BorderLayoutData northData = new BorderLayoutData(RegionPosition.NORTH);
		northData.setMargins(new Margins(0, 0, 0, 0));
		northData.setAutoHide(false);
		northData.setCollapseModeMini(true);
		northData.setSplit(false);
		northData.setMinSize(20);
		northData.setMaxSize(GeneralConstant.NORTH_PANEL_HEIGHT);

		borderPanel.add(northPanel, northData);

		//------------------------ South Panel --------------------------

		//------------------------ Center Panel --------------------------
		centerPanel = new Panel();
		centerPanel.setTitle("");
		centerPanel.setCollapsible(false);
		centerPanel.setWidth("100%");
		centerPanel.setHeight("100%");
		centerPanel.setLayout(new FitLayout());
		centerPanel.setId(GeneralConstant.CENTER_PANEL_ID);

		BorderLayoutData centerData = new BorderLayoutData(RegionPosition.CENTER);
		centerData.setSplit(true);
		centerData.setMinSize(GeneralConstant.CENTER_PANEL_WIDTH_MIN);
		centerData.setMaxSize(GeneralConstant.CENTER_PANEL_WIDTH_MAX);
		centerData.setMargins(new Margins(0, 0, 0, 0));

		borderPanel.add(centerPanel, centerData);

		panelCartoPanel = new CartoPanel();
		centerPanel.add(panelCartoPanel);

		centerPanel.addListener(new PanelListenerAdapter() {  
			public void onBodyResize(Panel panel,
					java.lang.String width,
					java.lang.String height) {
				if (panel != null) {
					String idElement = GeneralConstant.ID_CARTO_APPLET;
					if (!panelCartoPanel.getCartoAppletPanel().getSetApplet())
						idElement = GeneralConstant.ID_DIV_CARTO_APPLET;
					panelCartoPanel.resizeCartoPanel(idElement, width, height);
				} // if (panel != null) {
			} // public void onBodyResize
		}); // centerPanel.addListener(new PanelListenerAdapter() { 

		//------------------------ West Panel --------------------------
		if (homePageBean.getConfigBean().getWestPanel()) {
			int clientHeight = com.google.gwt.user.client.Window.getClientHeight();	
			westPanel = 
				new MultiMissionsRequestPanel(GeneralConstant.WEST_PANEL_PANEL_ID, 
						faceoConstants.functionsTitle(), 
						GeneralConstant.WEST_PANEL_WIDTH,
						(clientHeight - GeneralConstant.NORTH_PANEL_HEIGHT));

			westPanelAddListener(westPanel);

			BorderLayoutData westData = new BorderLayoutData(RegionPosition.WEST);
			westData.setMargins(new Margins(0, 5, 0, 0));
			westData.setAutoHide(false);
			westData.setCollapseModeMini(true);
			westData.setMinSize(GeneralConstant.WEST_PANEL_SPLIT_WIDTH_MIN);
			westData.setMaxSize(GeneralConstant.WEST_PANEL_WIDTH);

			borderPanel.add(westPanel, westData);
		} // if (homePageBean.getConfigBean().getWestPanel()) {

		//------------------------ East Panel --------------------------
		if (homePageBean.getConfigBean().getEastPanel()) {
			eastPanel = 
				new MultiCartographicPanel(GeneralConstant.EAST_PANEL_PANEL_ID, 
						faceoConstants.cartographyManagementTitle(),
						GeneralConstant.EAST_PANEL_WIDTH);

			eastPanelAddListener(eastPanel);

			BorderLayoutData eastData = new BorderLayoutData(RegionPosition.EAST);
			eastData.setMargins(new Margins(0, 0, 0, 0));
			eastData.setAutoHide(false);
			eastData.setCollapseModeMini(true);
			eastData.setMinSize(GeneralConstant.EAST_PANEL_SPLIT_WIDTH_MIN);
			eastData.setMaxSize(GeneralConstant.EAST_PANEL_WIDTH);

			borderPanel.add(eastPanel, eastData);
		} // if (homePageBean.getConfigBean().getEastPanel()) {

		mainPanel.add(borderPanel);

		new Viewport(mainPanel);

		//--------------------------------------------------------
		// creation de fonctions JavaScript
		FacadeCartoWwd.informAppliSelectionChange();
		FacadeCartoWwd.updateAOIFields();
		FacadeCartoWwd.initApplet();
		FacadeCartoWwd.startApplet();
		FacadeCartoWwd.stopApplet();
		FacadeCartoWwd.resizeAppletStart();
		FacadeCartoWwd.showAppletMessage();
		FacadeCartoWwd.testJreVersion();

	} // public void onModuleLoad() {

	/**
	 * getter on westPanel
	 * 
	 * @return MultiMissionsRequestPanel : the west Panel
	 */
	public static MultiMissionsRequestPanel getWestPanel() {
		return westPanel;
	}

	/**
	 * getter on eastPanel
	 * 
	 * @return MultiCartographicPanel : the east panel
	 */
	public static MultiCartographicPanel getEastPanel() {
		return eastPanel;
	}

	/**
	 * getter on centerPanel
	 * 
	 * @return Panel : the center panel
	 */
	public static Panel getCenterPanel() {
		return centerPanel;
	}

	/**
	 * getter on northPanel
	 * 
	 * @return Panel : the north panel
	 */
	public static MainMenuPanel getNorthPanel() {
		return northPanel;
	}

	/**
	 * getter on southPanel
	 * 
	 * @return Panel : the south panel
	 */
	public static Panel getSouthPanel() {
		return southPanel;
	}

	/**
	 * getter on panelCartoPanel
	 * 
	 * @return CartoPanel : the cartography panel
	 */
	public static CartoPanel getPanelCartoPanel() {
		return panelCartoPanel;
	}

	/**
	 * getter on homePageBean
	 * 
	 * @return HomePageBean : the HomePageBean
	 */
	public static HomePageBean getHomePageBean() {
		return homePageBean;
	}

	/**
	 * Adding a listener to the westPanel
	 * management of the Panel according to the cartography's type
	 * 'World Wind Java' or 'Google Earth'
	 * 
	 * @param _panel (Panel _panel) : the Panel
	 */
	private static void westPanelAddListener(Panel _panel) {
		_panel.addListener(new PanelListenerAdapter() {
			public boolean doBeforeExpand(Panel _panel, boolean _animate) {
				boolean expand = true;

				// on ferme le panneau de droite s'il est ouvert
				if (! HomePage.getEastPanel().isCollapsed())
					HomePage.getEastPanel().collapse();

				return expand;
			} // public boolean doBeforeExpand(Panel _panel, boolean _animate) {
		}); // _panel.addListener
	} // private static void westPanelAddListener(Panel _panel) {

	/**
	 * Adding a listener to the eastPanel
	 * management of the Panel according to the cartography's type
	 * 'World Wind Java' or 'Google Earth'
	 * 
	 * @param _panel (Panel) : the Panel
	 */
	private static void eastPanelAddListener(Panel _panel) {
		_panel.addListener(new PanelListenerAdapter() {
			public boolean doBeforeExpand(Panel _panel,
					boolean animate) {
				boolean expand = true;

				// on ferme le panneau de gauche s'il est ouvert
				if (! HomePage.getWestPanel().isCollapsed())
					HomePage.getWestPanel().collapse();

				return expand;
			} // public boolean doBeforeExpand(Panel _panel,
		}); // _panel.addListener
	} // private static void eastPanelAddListener(Panel _panel) {

} // class
