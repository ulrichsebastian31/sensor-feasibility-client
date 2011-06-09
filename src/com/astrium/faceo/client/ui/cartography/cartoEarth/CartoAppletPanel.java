package com.astrium.faceo.client.ui.cartography.cartoEarth;

import com.astrium.faceo.client.bean.cartography.CartoObjectBean;
import com.astrium.faceo.client.common.GeneralConstant;
import com.astrium.faceo.client.common.Utils;
import com.astrium.faceo.client.common.programming.sps2.Sps2GeneralConstants;
import com.astrium.faceo.client.events.programming.sps2.ProgrammingDragAndDropEvents;
import com.astrium.faceo.client.events.programming.sps2.ProgrammingDragAndDropHandler;
import com.astrium.faceo.client.events.programming.sps2.ProgrammingSelectRowEvents;
import com.astrium.faceo.client.events.programming.sps2.ProgrammingSelectRowHandler;
import com.astrium.faceo.client.service.FacadeUtil;
import com.astrium.faceo.client.service.facadeCartoWWD.FacadeCartoWwd;
import com.astrium.faceo.client.ui.HomePage;
import com.astrium.faceo.client.ui.cartography.cartoLayers.CartoLayersPanel;
import com.astrium.faceo.client.ui.programming.sps2.view.SpsPanels;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.dd.DragData;
import com.gwtext.client.dd.DragSource;
import com.gwtext.client.dd.DropTarget;
import com.gwtext.client.dd.DropTargetConfig;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.form.NumberField;
import com.gwtext.client.widgets.grid.GridDragData;
import com.gwtext.client.widgets.grid.GridPanel;

/**
 * <B>SITE FACEO</B> <BR>
 * 
 * <P>
 * La classe CartoAppletPanel g&egrave;re les widgets 
 * du panneau contenant la repr&eacute;sentation3D de la carte
 * de la page d'accueil en utilisant World Wind
 * </P>
 * </P>
 * 
 * @author GR
 * @version 1.0, le 18/08/2008
 */
public class CartoAppletPanel extends Panel {
	// constantes
	private final String IFRAME_APPLET_DRAG_PROGRAMMING_SOLUTIONS = "iframeAppletProgrammingSolutions";

	// attributs
	private boolean setApplet = true;

	/** */
	@SuppressWarnings("unused")
	private DropTarget cartoDropTarget = null;

	final private HandlerManager handlerManager = new HandlerManager(this);

	/**
	 * constructor
	 * 
	 * @param _idPanel (String) : panel identifier
	 */
	public CartoAppletPanel(String _idPanel) {
		super() ;

		if (HomePage.getHomePageBean().getConfigBean().getSps2Panel()) {
			Utils.addIframeToRootElement(IFRAME_APPLET_DRAG_PROGRAMMING_SOLUTIONS);
		}

		// ------------------------ Cartography Panel -------------------------------
		this.setId(_idPanel);

		this.setHeader(false);
		this.setBorder(false);
		this.setTitle("");
		this.setAutoWidth(true);
		this.setAutoHeight(true);
		this.setSize("100%", "100%");
		this.setAutoScroll(false);
		this.setDraggable(false);
		this.setFloating(false);
		
		// ------------------------ Applet Element Definition -------------------------------
		setAppletContent();

		// ------------------------ Cartography Drag and Drop Listeners -------------------------------
		DropTargetConfig dropTargetConfig = new DropTargetConfig();
		dropTargetConfig.setdDdGroup(GeneralConstant.CARTO_DRAG_DROP_GROUP);
		dropTargetConfig.setTarget(true);

		// ------------------------ Cartography Drag and Drop Listeners -------------------------------

		// Programming
		
		// Drag & Drop
		this.addProgrammingDragAndDropHandler(new ProgrammingDragAndDropHandler() {
			public void onDrop(ProgrammingDragAndDropEvents _event) {
				_event.onDrop();
			}

			public void onDropOut(ProgrammingDragAndDropEvents _event) {
				_event.onDropOut();
			}

			public void onDropOver(ProgrammingDragAndDropEvents _event) {
				_event.onDropOver();
			}
		});
		
		// Select row
		this.addProgrammingSelectRowHandler(new ProgrammingSelectRowHandler() {
			public void onSelectRow(ProgrammingSelectRowEvents _event) {
				_event.onSelectRow();
			}
		});
		
		// Drag Ad Drop Managment
		this.cartoDropTarget = new DropTarget(this, dropTargetConfig){
			public boolean notifyDrop(DragSource _dragSource,
					EventObject _eventObject,
					DragData _dragData) {

				// utilisation des gestionnaires GWT personnalisés
				GridDragData gridDragData = ((GridDragData) _dragData);
				GridPanel gridPanel = gridDragData.getGrid();
				String gridPanelId = gridPanel.getId();

				if ( (gridPanelId.equalsIgnoreCase(Sps2GeneralConstants.GRID_PANEL_PROGRAMMING_TASKS)) 
						|| (gridPanelId.equalsIgnoreCase(Sps2GeneralConstants.GRID_PANEL_PROGRAMMING_SOLUTIONS)) ) {
					handlerManager.fireEvent(
							new ProgrammingDragAndDropEvents(GeneralConstant.DND_EVENT_DROP, _dragSource, _dragData));
				}

				return true;
			} // public boolean notifyDrop(DragSource source,

			public java.lang.String notifyEnter(DragSource dragSource,
					EventObject eventObject,
					DragData dragData) {
				return "";
			} // public java.lang.String notifyEnter(DragSource dragSource,

			public void notifyOut(DragSource _dragSource,
					EventObject _eventObject,
					DragData _dragData) {

				// utilisation des gestionnaires GWT personnalisés
				GridDragData gridDragData = ((GridDragData) _dragData);
				GridPanel gridPanel = gridDragData.getGrid();
				String gridPanelId = gridPanel.getId();

				if ( (gridPanelId.equalsIgnoreCase(Sps2GeneralConstants.GRID_PANEL_PROGRAMMING_TASKS)) 
						|| (gridPanelId.equalsIgnoreCase(Sps2GeneralConstants.GRID_PANEL_PROGRAMMING_SOLUTIONS)) ) {
					handlerManager.fireEvent(
							new ProgrammingDragAndDropEvents(GeneralConstant.DND_EVENT_DROP_OUT, _dragSource, _dragData));
				}
			} // public void notifyOut(DragSource dragSource,

			public java.lang.String notifyOver(DragSource _dragSource,
					EventObject _eventObject,
					DragData _dragData) {

				// utilisation des gestionnaires GWT personnalisés
				GridDragData gridDragData = ((GridDragData) _dragData);
				GridPanel gridPanel = gridDragData.getGrid();
				String gridPanelId = gridPanel.getId();

				if ( (gridPanelId.equalsIgnoreCase(Sps2GeneralConstants.GRID_PANEL_PROGRAMMING_TASKS)) 
						|| (gridPanelId.equalsIgnoreCase(Sps2GeneralConstants.GRID_PANEL_PROGRAMMING_SOLUTIONS)) ) {
					handlerManager.fireEvent(
							new ProgrammingDragAndDropEvents(GeneralConstant.DND_EVENT_DROP_OVER, _dragSource, _dragData));
				}

				return "x-tree-drop-ok-append";
			} // public java.lang.String notifyOver(DragSource source,
		}; // DropTarget CartoDropTarget = new DropTarget(this.cartoPanel, dropTargetConfig){

	} // public CartoAppletPanel() {

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
	} // public void addAOIToHashMapCartoObjects(int _idAOI, String _layerName, String _label,

	/**
	 * Add AOI Object on Cartography
	 * 
	 * @param _leftUpper (NumberField)	: field for AOI left upper (North latitude)
	 * @param _rightUpper (NumberField) : field for AOI right upper (West longitude)
	 * @param _leftLower (NumberField)	: field for AOI left lower (Sud latitude)
	 * @param _rightLower (NumberField) : field for AOI right lower (East longitude)
	 * 
	 */
	public static void displayAOIOnCarto(
			NumberField _leftUpper, NumberField _rightUpper, NumberField _leftLower, NumberField _rightLower) {

		if (_leftUpper.isValid())  
			if (_rightUpper.isValid())  
				if (_leftLower.isValid())  
					if (_rightLower.isValid()) {
						double latitudeNord = Double.valueOf(_leftUpper.getValueAsString());
						double longitudeOuest = Double.valueOf(_rightUpper.getValueAsString());

						double latitudeSud = Double.valueOf(_leftLower.getValueAsString());
						double longitudeEst = Double.valueOf(_rightLower.getValueAsString());

						// on supprime la zone d'interet precedente si elle existe
						if (HomePage.getHomePageBean().getIdAOICatalog() > 0)  {
							FacadeCartoWwd.delCarto3DObject(HomePage.getHomePageBean().getIdAOICatalog());
						} // 

						// on ajoute la zone d'interet
						int idAOI = FacadeCartoWwd.JSdrawTheRectInJava(
								GeneralConstant.LAYER_AOI, true, 
								latitudeNord, longitudeOuest, latitudeSud, longitudeEst,
								"<b>AOI</b>", 0.16666667f, 1.0f, 1.0f, 0.5d, true);

						if (idAOI > 0) {
							HomePage.getHomePageBean().setIdAOICatalog(idAOI);

							// mise a jour des valeurs de l'AOI pour les differents panneaux
							FacadeCartoWwd.updateAOIFields(idAOI, latitudeNord, longitudeOuest, latitudeSud, longitudeEst);

							CartoAppletPanel.addAOIToHashMapCartoObjects(
									idAOI, GeneralConstant.LAYER_AOI, "<b>AOI</b>", 0.1f, 0.5f, 0.1f, 0.5d);

							if (HomePage.getHomePageBean().getInitCartoLayers()) {
								((CartoLayersPanel) HomePage.getEastPanel().getCartographicPanel(GeneralConstant.CARTO_LAYERS_PANEL_ID)).updateEditorGridCartoLayers(GeneralConstant.LAYER_AOI);
							} // 
						} // 
					} // if (_rightLower.isValid()) {

	} // private static void displayAOIOnCarto(NumberField _leftUpper, NumberField _rightUpper, NumberField _leftLower, NumberField _rightLower) {

	/**
	 * Definition of the applet that contains the cartography
	 * 
	 */
	private void setAppletContent() {
		// ------------------------ Applet Element Definition -------------------------------
		final Element divAppletElement = DOM.createDiv();
		divAppletElement.setId(GeneralConstant.ID_DIV_CARTO_APPLET);
		divAppletElement.getStyle().setZIndex(-1);
		if (FacadeUtil.isMSIE6()) {
			divAppletElement.getStyle().setHeight(600d, Unit.PX);
			divAppletElement.getStyle().setWidth(600d, Unit.PX);
		} else {
			divAppletElement.getStyle().setHeight(100d, Unit.PCT);
			divAppletElement.getStyle().setWidth(100d, Unit.PCT);
		}

		// on ne cree l'applet que si Java est active sur le navigateur client
		if (this.setApplet) 
			if (FacadeCartoWwd.isJavaEnabled()) {
				Element appletElement = DOM.createElement("applet");
				appletElement.setId(GeneralConstant.ID_CARTO_APPLET);
				if (FacadeUtil.isMSIE6()) {
					appletElement.getStyle().setHeight(500d, Unit.PX);
					appletElement.getStyle().setWidth(500d, Unit.PX);
				} else {
					appletElement.getStyle().setHeight(100d, Unit.PCT);
					appletElement.getStyle().setWidth(100d, Unit.PCT);
				}
				appletElement.setAttribute("mayscript", "");

				final String repAppletLibraries = GWT.getModuleBaseURL() + "applet/";

				StringBuffer archiveApplet = new StringBuffer();
				archiveApplet.append("http://download.java.net/media/applet-launcher/applet-launcher.jar, ");
				archiveApplet.append(repAppletLibraries + "appletCarto.jar, ");
				archiveApplet.append(repAppletLibraries + "worldwind.jar, ");
				archiveApplet.append(repAppletLibraries + "json_simple.jar, ");
				archiveApplet.append(repAppletLibraries + "commons-codec-1.3.jar, ");
				archiveApplet.append("http://download.java.net/media/jogl/builds/archive/jsr-231-webstart-current/jogl.jar, ");
				archiveApplet.append("http://download.java.net/media/gluegen/webstart/gluegen-rt.jar");

				appletElement.setAttribute("code", "org.jdesktop.applet.util.JNLPAppletLauncher");
				appletElement.setAttribute("archive", archiveApplet.toString());

				Element param1 = DOM.createElement("param");
				param1.setAttribute("name", "centerimage");
				param1.setAttribute("value", "true");

				Element param2 = DOM.createElement("param");
				param2.setAttribute("name", "codebase_lookup");
				param2.setAttribute("value", "false");

				Element param3 = DOM.createElement("param");
				param3.setAttribute("name", "subapplet.classname");
				param3.setAttribute("value", "com.eads.astrium.faceo.carto.applet.WWJApplet");

				Element param4 = DOM.createElement("param");
				param4.setAttribute("name", "subapplet.displayname");
				param4.setAttribute("value", "Cartography");

				Element param5 = DOM.createElement("param");
				param5.setAttribute("name", "noddraw.check");
				param5.setAttribute("value", "true");

				Element param6 = DOM.createElement("param");
				param6.setAttribute("name", "progressbar");
				param6.setAttribute("value", "true");

				Element param7 = DOM.createElement("param");
				param7.setAttribute("name", "jnlpNumExtensions");
				param7.setAttribute("value", "1");

				Element param8 = DOM.createElement("param");
				param8.setAttribute("name", "SelectionMode");

				if (HomePage.getHomePageBean().getNavigatorPlatform().equalsIgnoreCase(GeneralConstant.PLATFORM_MULTITOUCH_FF3)) {
					param8.setAttribute("value", GeneralConstant.CARTO_OBJECTS_OVER_MODE_SELECTION);
				} else {
					param8.setAttribute("value", GeneralConstant.CARTO_OBJECTS_CLIC_MODE_SELECTION);
				}

				Element param9 = DOM.createElement("param");
				param9.setAttribute("name", "jnlpExtension1");
				param9.setAttribute("value", "http://download.java.net/media/jogl/builds/archive/jsr-231-webstart-current/jogl.jnlp");

				Element param10 = DOM.createElement("param");
				param10.setAttribute("name", "java_arguments");
				param10.setAttribute("value", "-Xmx128m -Dsun.java2d.noddraw=true");

				Element param11 = DOM.createElement("param");
				param11.setAttribute("name", "separate_jvm");
				param11.setAttribute("value", "true");

				// appel en mode JNLP : a partir de java 1.6.0.10
				final Element param12 = DOM.createElement("param");
				param12.setAttribute("name", "jnlp_href");									

				param12.setAttribute("value", repAppletLibraries + "cartoapplet.jnlp");

				Element param13 = DOM.createElement("param");
				param13.setAttribute("name", "image");
				param13.setAttribute("value", repAppletLibraries + "appletWaitingMessage.jpg");					

				Element param14 = DOM.createElement("param");
				param14.setAttribute("name", "boxmessage");
				param14.setAttribute("value", "loading...");				

				appletElement.appendChild(param1);

				appletElement.appendChild(param1);
				appletElement.appendChild(param2);
				appletElement.appendChild(param3);
				appletElement.appendChild(param4);
				appletElement.appendChild(param5);
				appletElement.appendChild(param6);
				appletElement.appendChild(param7);
				appletElement.appendChild(param8);
				appletElement.appendChild(param9);
				appletElement.appendChild(param10);
				appletElement.appendChild(param11);
				appletElement.appendChild(param13);
				appletElement.appendChild(param14);

				// appel en mode JNLP : a partir de java 1.6.0.10
				appletElement.appendChild(param12);

				divAppletElement.appendChild(appletElement);						
			} // if (FacadeCarto3D.isJavaEnabled()) {

		this.setContentEl(divAppletElement);
	} // private void setAppletContent(Panel _cartoPanel) {

	/**
	 * getter on setApplet
	 * 
	 * @return boolean : true it the applet is active
	 */
	public boolean getSetApplet() {
		return this.setApplet;
	}

	/* (non-Javadoc)
	 * @see com.astrium.faceo.client.events.HasProgrammingDragAndDropHandler#addProgrammingDragAndDropHandler(com.astrium.faceo.client.events.ProgrammingDragAndDropHandler)
	 */
	/**
	 * add a ProgrammingDragAndDrop handler
	 * 
	 * @param _handler ProgrammingDragAndDropHandler : 
	 * 
	 * @return HandlerRegistration
	 */
	private HandlerRegistration addProgrammingDragAndDropHandler(ProgrammingDragAndDropHandler _handler) {
		return this.handlerManager.addHandler(ProgrammingDragAndDropEvents.getType(), _handler);
	}

	
	/* (non-Javadoc)
	 * @see com.astrium.faceo.client.events.HasProgrammingSelectRowHandler#addProgrammingSelectRowHandler(com.astrium.faceo.client.events.ProgrammingSelectRowHandler)
	 */
	/**
	 * add a ProgrammingSelectRow handler
	 * 
	 * @param _handler ProgrammingSelectRowHandler : 
	 * 
	 * @return HandlerRegistration
	 */
	private HandlerRegistration addProgrammingSelectRowHandler(ProgrammingSelectRowHandler _handler) {
		return this.handlerManager.addHandler(ProgrammingSelectRowEvents.getType(), _handler);
	}
	
	/**
	 * 
	 * @param _currentCartoIndex (int)	: current carto object identifier
	 * 
	 */
	public void selectProgrammingProductRow(int _currentCartoIndex) {
		this.handlerManager.fireEvent(
				new ProgrammingSelectRowEvents(GeneralConstant.SELECT_ROW_EVENT, _currentCartoIndex));
	} // 

	/**
	 * 
	 * 
	 */
	public void displayProgrammingAoi() {
		displayAOIOnCarto(
				SpsPanels.getSpsSettingsPanel().getLeftUpperNumberField(), SpsPanels.getSpsSettingsPanel().getRightUpperNumberField(), 
				SpsPanels.getSpsSettingsPanel().getLeftLowerNumberField(), SpsPanels.getSpsSettingsPanel().getRightLowerNumberField());
	} // 

} // class
