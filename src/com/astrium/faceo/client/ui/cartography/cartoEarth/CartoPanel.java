package com.astrium.faceo.client.ui.cartography.cartoEarth;

import com.astrium.faceo.client.bean.cartography.CartoObjectBean;
import com.astrium.faceo.client.common.GeneralConstant;
import com.astrium.faceo.client.common.ImagesConstant;
import com.astrium.faceo.client.common.Utils;
import com.astrium.faceo.client.common.cartoAoi.CartoAoiConstants;
import com.astrium.faceo.client.common.faceo.FaceoConstants;
import com.astrium.faceo.client.service.FacadeUtil;
import com.astrium.faceo.client.service.facadeCartoWWD.FacadeCartoWwd;
import com.astrium.faceo.client.ui.HomePage;
import com.astrium.faceo.client.ui.cartography.CartoAOIPanel;
import com.astrium.faceo.client.ui.cartography.CartoToponymsPanel;
import com.astrium.faceo.client.ui.cartography.cartoLayers.CartoLayersPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.xml.client.DOMException;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.QuickTipsConfig;
import com.gwtext.client.widgets.Toolbar;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.ToolbarMenuButton;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.menu.BaseItem;
import com.gwtext.client.widgets.menu.Item;
import com.gwtext.client.widgets.menu.Menu;
import com.gwtext.client.widgets.menu.event.MenuListenerAdapter;

/**
 * <B>SITE FACEO</B> <BR>
 * 
 * <P>
 * La classe CartoPanel g&egrave;re les widgets 
 * du panneau contenant les repr&eacute;sentations 3D (World Wind, Google Earth)
 * cartographiques
 * </P>
 * </P>
 * 
 * @author GR
 * @version 1.0, le 13/02/2009
 */
public class CartoPanel extends Panel {

	// internationalisation
	private static final FaceoConstants faceoConstants =
		(FaceoConstants) com.google.gwt.core.client.GWT.create(FaceoConstants.class);

	private static final CartoAoiConstants cartoAoiConstants =
		(CartoAoiConstants) com.google.gwt.core.client.GWT.create(CartoAoiConstants.class);

	// constantes
	private final String CARTOS_PANEL = "cartosPanel";

	// items du menu "AOI"
	private final String DRAW_AOI_RECT_ITEM_ID = CARTOS_PANEL + "_" + "drawAoiRectItemId";

	private final String DISPLAY_AOI_ITEM_ID = CARTOS_PANEL + "_" + "displayAoiItemId";

	private final String UNDISPLAY_AOI_ITEM_ID = CARTOS_PANEL + "_" + "unDisplayAoiItemId";	

	private final String SET_AOI_RECT_ITEM_ID = CARTOS_PANEL + "_" + "setAoiRectItemId";

	private final String IFRAME_APPLET_CARTO_MENU = "iframeAppletCartoMenu";

	// attributs
	private CartoAppletPanel cartoAppletPanel;

	private Toolbar cartoToolbar;

	private ToolbarMenuButton aoiToolBarMenuButton;

	private Menu aoiMenu;

	private Item drawAOIRectItem;

	private Item displayAoiItem;

	private Item unDisplayAoiItem;

	private Item setAOIRectItem;

	private ToolbarButton initCartoPositionButton;

	private ToolbarButton toponymButton;
	// --------------------------------------------------------------------

	private CartoToponymsPanel panelToponyms;

	private boolean cartoPanelToponymsLoaded = false;

	private CartoAOIPanel panelAOI;

	private boolean cartoAoiPanelLoaded = false;

	/**
	 * constructor
	 */
	public CartoPanel() {
		super() ;

		Utils.addIframeToRootElement(IFRAME_APPLET_CARTO_MENU);

		this.setId(CARTOS_PANEL);

		//create a toolbar and various menu items  
		this.cartoToolbar = new Toolbar();
		this.cartoToolbar.setId("cartoToolbar_" + CARTOS_PANEL);

		// ------------------------ AOI Menu ------------------------
		this.aoiMenu = new Menu();
		this.aoiMenu.setShadow(true);

		this.aoiMenu.setMinWidth(10);
		this.aoiMenu.setId(CARTOS_PANEL + "_aoiMenu");

		this.drawAOIRectItem = new Item();
		this.drawAOIRectItem.setText("<b>" + cartoAoiConstants.aoi() + "&nbsp;:&nbsp;" + faceoConstants.cartographyDrawRectangle() + "</b>");
		this.drawAOIRectItem.setId(DRAW_AOI_RECT_ITEM_ID);
		this.drawAOIRectItem.setIcon(ImagesConstant.ICON_14_DEFINE_RECTANGLE);

		this.displayAoiItem = new Item();
		this.displayAoiItem.setText("<b>" + cartoAoiConstants.aoi() + "&nbsp;:&nbsp;" + faceoConstants.cartographyDisplay() + "</b>");
		this.displayAoiItem.setId(DISPLAY_AOI_ITEM_ID);		
		this.displayAoiItem.setIcon(ImagesConstant.ICON_17_SHOW_AOI);

		this.unDisplayAoiItem = new Item();
		this.unDisplayAoiItem.setText("<b>" + cartoAoiConstants.aoi() + "&nbsp;:&nbsp;" + faceoConstants.cartographyUnDisplay() + "</b>");
		this.unDisplayAoiItem.setId(UNDISPLAY_AOI_ITEM_ID);		
		this.unDisplayAoiItem.setIcon(ImagesConstant.ICON_18_HIDE_AOI);

		this.setAOIRectItem = new Item();
		this.setAOIRectItem.setText("<b>" + cartoAoiConstants.aoi() + "&nbsp;:&nbsp;" + faceoConstants.cartographyRectangleCoordinates() + "</b>");
		this.setAOIRectItem.setId(SET_AOI_RECT_ITEM_ID);
		this.setAOIRectItem.setIcon(ImagesConstant.ICON_19_AOI_COORDINATES);

		this.aoiMenu.addItem(this.drawAOIRectItem);
		this.aoiMenu.addItem(this.displayAoiItem);
		this.aoiMenu.addItem(this.unDisplayAoiItem);
		this.aoiMenu.addItem(this.setAOIRectItem);

		addAoiMenuListener();

		this.aoiToolBarMenuButton =
			new ToolbarMenuButton("&nbsp;&nbsp;AOIs", this.aoiMenu);  
		this.aoiToolBarMenuButton.setId(CARTOS_PANEL + "_aoiToolBarMenuButton");
		this.aoiToolBarMenuButton.setIcon(ImagesConstant.ICON_13_DEFINE_AOI);

		// ------------------------ 'Init Carto Position' Icon ToolBar Button -------------------------------
		this.initCartoPositionButton = new ToolbarButton();
		this.initCartoPositionButton.setIcon(ImagesConstant.ICON_2_INIT_GLOBE_POSITION);
		this.initCartoPositionButton.setCls("x-btn-icon");
		this.initCartoPositionButton.setId(CARTOS_PANEL + "_iconInitCartoPositionButton");
		QuickTipsConfig iconInitCartoPositionTipsConfig = new QuickTipsConfig();
		iconInitCartoPositionTipsConfig.setText("<b>" + faceoConstants.cartographyGlobeInitText() + "</b><br/>");
		iconInitCartoPositionTipsConfig.setTitle(faceoConstants.cartography3DCenterTitle());
		this.initCartoPositionButton.setTooltip(iconInitCartoPositionTipsConfig);

		// ------------------------ 'Init Carto Position' Icon Button Listener -------------------------------
		this.initCartoPositionButton.addListener(new ButtonListenerAdapter() {  
			public void onClick(Button button, EventObject e) {
				centerEarth();
			} // 
		}); //    

		// ------------------ Toponyms Button ------------------
		this.toponymButton = new ToolbarButton();  
		this.toponymButton.setId(CARTOS_PANEL + "_toponymButton");
		this.toponymButton.setIcon(ImagesConstant.ICON_3_TOPONYM_SEARCH);
		this.toponymButton.setCls("x-btn-icon");
		QuickTipsConfig toponymSearchTipsConfig = new QuickTipsConfig();
		toponymSearchTipsConfig.setText("<b>" + faceoConstants.cartography3DToponymSearchText() + "</b><br/>");
		toponymSearchTipsConfig.setTitle(faceoConstants.cartography3DToponymTitle());
		this.toponymButton.setTooltip(toponymSearchTipsConfig);

		this.toponymButton.addListener(new ButtonListenerAdapter() {  
			public void onClick(Button button, EventObject e) {	
				getCartoToponymsPanel();
			} // 
		}); //

		// definition de la barre d'outils
		this.cartoToolbar.addButton( this.initCartoPositionButton);
		this.cartoToolbar.addText(faceoConstants.cartographyButtonInitText());
		this.cartoToolbar.addSeparator();

		this.cartoToolbar.addButton( this.toponymButton);
		this.cartoToolbar.addText(faceoConstants.cartographyButtonAdressSearchText());
		this.cartoToolbar.addSeparator();

		this.cartoToolbar.addButton(this.aoiToolBarMenuButton);
		// Tip attached to MenuButton
		QuickTipsConfig aoiToolBarMenuButtonTipsConfig = new QuickTipsConfig();
		aoiToolBarMenuButtonTipsConfig.setText("<b>" + faceoConstants.aoiToolBarMenuButtonTip() + "</b><br/>");
		this.aoiToolBarMenuButton.setTooltip(aoiToolBarMenuButtonTipsConfig);

		this.cartoToolbar.addSeparator();

		// ------------------------ AOI Search Panel -------------------------------
		this.setAutoWidth(false);
		this.setWidth("100%");
		this.setAutoHeight(false);
		this.setHeight("100%");
		this.setTopToolbar(this.cartoToolbar);
		this.setTitle("");
		this.setHeader(false);
		this.setAutoScroll(false);
		this.setDraggable(false);
		this.setFloating(false);

		// ------------------------ Cartography World Wind Panel -------------------------------
		this.cartoAppletPanel = new CartoAppletPanel("cartoAppletPanel");

		this.add(this.cartoAppletPanel);
	} // public CartoPanel() {

	/**
	 * M&eacute;thode permettant de centrer la terre
	 * sur la repr&eacute;sentation 3D de la Terre
	 * 
	 */
	public static void centerEarth() {
		FacadeCartoWwd.JSinitCartographyPosition();
	} // 

	/**
	 * M&eacute;thode permettant l'ajout d'un objet de type 'AOI' rectangulaire
	 * sur la repr&eacute;sentation 3D de la Terre
	 */
	public static void drawAoiRectangle() {
		// on supprime la zone d'interet precedente si elle existe
		if (HomePage.getHomePageBean().getIdAOICatalog() > 0)
			FacadeCartoWwd.delCarto3DObject(HomePage.getHomePageBean().getIdAOICatalog());

		// on ajoute la zone d'interet
		int idAOI = FacadeCartoWwd.JSdrawCartoRectWithMouseInJava(
				GeneralConstant.LAYER_AOI, "<b>AOI</b>", 0.16666667f, 1.0f, 1.0f, 0.5d, GeneralConstant.RECTANGLE_STANDARD_TYPE,true);

		if (idAOI > 0) {
			HomePage.getHomePageBean().setIdAOICatalog(idAOI);

			addAOIToHashMapCartoObjects(
					idAOI, GeneralConstant.LAYER_AOI, "<b>AOI</b>", 0.16666667f, 1.0f, 1.0f, 0.5d);

			if (HomePage.getHomePageBean().getInitCartoLayers())
				((CartoLayersPanel) HomePage.getEastPanel().getCartographicPanel(GeneralConstant.CARTO_LAYERS_PANEL_ID)).updateEditorGridCartoLayers(GeneralConstant.LAYER_AOI);
		}// if (idAOI > 0) {
	} // 

	/**
	 * M&eacute;thode permettant la suppression d'un objet de type 'AOI'
	 * sur la repr&eacute;sentation 3D de la Terre
	 * 
	 */
	public static void unDisplayAoi() {
		// on supprime la zone d'interet precedente si elle existe
		if (HomePage.getHomePageBean().getIdAOICatalog() > 0) {
			FacadeCartoWwd.delCarto3DObject(HomePage.getHomePageBean().getIdAOICatalog());
			HomePage.getHomePageBean().setIdAOICatalog(-1);
			if (HomePage.getHomePageBean().getInitCartoLayers())
				((CartoLayersPanel) HomePage.getEastPanel().getCartographicPanel(GeneralConstant.CARTO_LAYERS_PANEL_ID)).updateEditorGridCartoLayers(GeneralConstant.LAYER_AOI);
		} // 
	} // 

	/**
	 * M&eacute;thode permettant l'ajout d'un objet de type 'rectangle' sur la repr&eacute;sentation 3D de la Terre
	 * 
	 * @param _idAOI (int)			: identifiant de l'AOI dans la cartographie
	 * @param _layerName (String)	: nom du layer sur lequel on ajoute le rectangle
	 * @param _label (String)		: longitude du point sup&egrave;rieur du rectangle
	 * @param _hue (float)			: Hue color HSB parameter
	 * @param _saturation (float)	: Saturation color HSB parameter
	 * @param _brightness (float)	: Brightness color HSB parameter
	 * @param _transparency (double): valeur de la transparence du Layer
	 *
	 */
	public static void addAOIToHashMapCartoObjects(int _idAOI, String _layerName, String _label, 
			float _hue, float _saturation, float _brightness, double _transparency) {
		CartoObjectBean cartoObjectBean = new CartoObjectBean(_label, _idAOI, GeneralConstant.CARTO_CATALOG_AOI_TYPE,
				_layerName, _hue, _saturation, _brightness, true, _transparency);

		HomePage.getHomePageBean().setContentCartographyObjects(_idAOI, cartoObjectBean);
	} // 

	/**
	 * Add listener on AOI menu
	 * 
	 */
	private void addAoiMenuListener() {
		this.aoiMenu.addListener(new MenuListenerAdapter() {
			public void onShow(Menu menu) { // appel de la fonction JavaScript permettant de passer la fenetre au 1er plan au-dessus de l'applet WorlWind
				Utils.putMenuTofront(true, menu.getElement(), IFRAME_APPLET_CARTO_MENU);
			}

			public void onHide(Menu menu) { // appel de la fonction JavaScript permettant de passer la fenetre au 1er plan au-dessus de l'applet WorlWind
				Utils.putMenuTofront(false, menu.getElement(), IFRAME_APPLET_CARTO_MENU);
			}

			public void onItemClick(BaseItem item, EventObject e) {
				String menuItemId = item.getId();

				if (menuItemId.equalsIgnoreCase(DRAW_AOI_RECT_ITEM_ID))  
					drawAoiRectangle();
				else if (menuItemId.equalsIgnoreCase(DISPLAY_AOI_ITEM_ID))  
					cartoAppletPanel.displayProgrammingAoi();
				else if (menuItemId.equalsIgnoreCase(UNDISPLAY_AOI_ITEM_ID))  
					unDisplayAoi();
				else if (menuItemId.equalsIgnoreCase(SET_AOI_RECT_ITEM_ID)) 
					getCartoAoiPanel();
			} // 
		}); // 
	} // 

	/**
	 * fonction permettant de redimensionner les conteneurs
	 * d'un panel contenant la cartographie (applet World Wind, Google Earth)
	 *
	 *	@param _idElement (String)	: identifiant de l&eacute;l&eacute;ment &agrave; redimensionner
	 *	@param _width (String)		: largeur &agrave; utiliser
	 *	@param _height (String)		: hauteur &agrave; utiliser
	 */	
	public void resizeCartoPanel(String _idElement, java.lang.String _width, java.lang.String _height) {
		double appletWidth = 200;
		double appletHeight = 200;
		if (FacadeUtil.isMSIE6()) {
			String width = _width;
			String height = _height;

			if (HomePage.getCenterPanel() != null) {
				width = Integer.toString(HomePage.getCenterPanel().getWidth());
				height = Integer.toString(HomePage.getCenterPanel().getHeight());
			} // 

			Element elementApplet = DOM.getElementById(_idElement);

			try {
				if (elementApplet != null) {
					if (Float.valueOf(width) > 0) 
						appletWidth = Float.valueOf(width) - 4;
				}
			} catch (DOMException dOMException) {
				com.google.gwt.user.client.Window.alert("DOMException : width1 : " + dOMException);
				appletWidth = 200;
			} catch (NumberFormatException numberFormatException) {
				com.google.gwt.user.client.Window.alert("NumberFormatException : width1 : " + numberFormatException);
				appletWidth = 200;
			} finally {
			}

			if (elementApplet != null) {
				try {
					if (Float.valueOf(height) > 0)
						appletHeight = Float.valueOf(height) -2; 
					if (HomePage.getCenterPanel() != null)
						if (HomePage.getCenterPanel().getHeader().getHeight() > 0)
							appletHeight -= HomePage.getCenterPanel().getHeader().getHeight();
					if (this.cartoToolbar.getHeight() > 0)
						appletHeight -= this.cartoToolbar.getHeight();
				} catch (DOMException dOMException) {
					com.google.gwt.user.client.Window.alert("DOMException : _height1 : " + dOMException);
					appletHeight = 200;
				} catch (NumberFormatException numberFormatException) {
					com.google.gwt.user.client.Window.alert("NumberFormatException : _height1 : " + numberFormatException);
					appletHeight = 200;
				} finally {
				}
			} // 
		} // 

		Element elementApplet = DOM.getElementById(_idElement);
		if (elementApplet != null) {
			try {
				Style elementAppletStyle = elementApplet.getStyle();
				if (FacadeUtil.isMSIE6()) {
					elementAppletStyle.setWidth(appletWidth, Unit.PX);
				} else {
					elementAppletStyle.setWidth(100d, Unit.PCT);
				}
			} catch (DOMException dOMException) {
				com.google.gwt.user.client.Window.alert("DOMException : width : " + dOMException);
			} catch (Exception exception) {
				com.google.gwt.user.client.Window.alert("Exception : width : " + exception);
			} finally {
			}

			try {
				Style elementAppletStyle = elementApplet.getStyle();
				if (FacadeUtil.isMSIE6()) {
					elementAppletStyle.setHeight(appletHeight, Unit.PX);
				} else {
					elementAppletStyle.setHeight(100d, Unit.PCT);
				}
			} catch (DOMException dOMException) {
				com.google.gwt.user.client.Window.alert("DOMException : Height : " + dOMException);
			} catch (Exception exception) {
				com.google.gwt.user.client.Window.alert("Exception : Height : " + exception);
			} finally {
			}
		} // 
	} // 

	/**
	 * loading of the window for toponym's search
	 * 
	 */
	public void getCartoToponymsPanel() {
		if (! this.cartoPanelToponymsLoaded) {
			GWT.runAsync(new RunAsyncCallback() {
				public void onSuccess() {
					panelToponyms = new CartoToponymsPanel("panelToponymsPanel");
					cartoPanelToponymsLoaded = true;

					panelToponyms.showToponymSearch(true);
				} // public void onSuccess() {
				public void onFailure(Throwable arg0) {
					com.google.gwt.user.client.Window.alert("Pb on loading CartoToponymsPanel");
				} // public void onFailure
			}); // GWT.runAsync
		} else
			this.panelToponyms.showToponymSearch(false);
	} // 

	/**
	 * loading of the window for AOI coordinates
	 * 
	 */
	private void getCartoAoiPanel() {
		if (! this.cartoAoiPanelLoaded) {
			GWT.runAsync(new RunAsyncCallback() {
				public void onSuccess() {
					panelAOI = new CartoAOIPanel("panelAOIPanel");
					cartoAoiPanelLoaded = true;

					panelAOI.showWindowAoiRectangleCoordinates();
				} // public void onSuccess() {
				public void onFailure(Throwable arg0) {
					com.google.gwt.user.client.Window.alert("Pb on loading CartoAOIPanel");
				} // public void onFailure
			}); // GWT.runAsync
		} else
			this.panelAOI.showWindowAoiRectangleCoordinates();
	} // 

	/**
	 * return the panel for AOI
	 * 	
	 * @return CartoAOIPanel : the panel for AOI
	 */
	public CartoAOIPanel getPanelAOI() {
		return this.panelAOI;
	}

	/**
	 * getter on panelToponyms
	 * 
	 * @return CartoToponymsPanel : the Toponym panel
	 */
	public CartoToponymsPanel getPanelToponyms() {
		return this.panelToponyms;
	}

	/**
	 * getter on cartoAppletPanel
	 * 
	 * @return CartoAppletPanel : the Cartography applet panel
	 */
	public CartoAppletPanel getCartoAppletPanel() {
		return this.cartoAppletPanel;
	}

	/**
	 * getter on cartoPanelToponymsLoaded
	 * 
	 * @return Menu : true if the window cartoPanelToponyms is loaded
	 */	
	public boolean getCartoPanelToponymsLoaded() {
		return this.cartoPanelToponymsLoaded;
	}

	/**
	 * getter on cartoAoiPanelLoaded
	 * 
	 * @return Menu : true if the window cartoAoiPanel is loaded
	 */	
	public boolean getCartoAoiPanelLoaded() {
		return this.cartoAoiPanelLoaded;
	}

} // class
