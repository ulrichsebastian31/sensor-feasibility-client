package com.astrium.faceo.client.ui.cartography.cartoLayers;

/*
 * @(#)CartoLayersPanel.java	 1.0  30/10/2008
 *
 *
 * PROJET       : FACEO
 *
 * LANGUAGE     : Java
 *
 * DESCRIPTION  : La classe CartoLayersPanel gere les layers de la cartographie
 *
 *
 * CREATION :
 * ---------------------------------------------------------------------
 * | Date       | Version | Description                                |
 * ---------------------------------------------------------------------
 * | 30/10/2008 |    1.0  |                                            |
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

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.astrium.faceo.client.bean.cartography.CartoLayerBean;
import com.astrium.faceo.client.common.GeneralConstant;
import com.astrium.faceo.client.common.ImagesConstant;
import com.astrium.faceo.client.common.cartography.cartoLayers.CartoLayersConstants;
import com.astrium.faceo.client.rpc.cartography.CartoLayersProxyService;
import com.astrium.faceo.client.rpc.cartography.CartoLayersProxyServiceAsync;
import com.astrium.faceo.client.service.facadeCartoWWD.FacadeCartoWwd;
import com.astrium.faceo.client.ui.CartographicPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONException;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.TextAlign;
import com.gwtext.client.core.UrlParam;
import com.gwtext.client.data.ArrayReader;
import com.gwtext.client.data.BooleanFieldDef;
import com.gwtext.client.data.FieldDef;
import com.gwtext.client.data.FloatFieldDef;
import com.gwtext.client.data.IntegerFieldDef;
import com.gwtext.client.data.Record;
import com.gwtext.client.data.RecordDef;
import com.gwtext.client.data.SimpleStore;
import com.gwtext.client.data.Store;
import com.gwtext.client.data.StringFieldDef;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.PagingToolbar;
import com.gwtext.client.widgets.ToolTip;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.event.PanelListenerAdapter;
import com.gwtext.client.widgets.form.ComboBox;
import com.gwtext.client.widgets.grid.BaseColumnConfig;
import com.gwtext.client.widgets.grid.CellMetadata;
import com.gwtext.client.widgets.grid.ColumnConfig;
import com.gwtext.client.widgets.grid.ColumnModel;
import com.gwtext.client.widgets.grid.EditorGridPanel;
import com.gwtext.client.widgets.grid.GridEditor;
import com.gwtext.client.widgets.grid.GridPanel;
import com.gwtext.client.widgets.grid.GridView;
import com.gwtext.client.widgets.grid.Renderer;
import com.gwtext.client.widgets.grid.RowParams;
import com.gwtext.client.widgets.grid.RowSelectionModel;
import com.gwtext.client.widgets.grid.event.EditorGridListenerAdapter;
import com.gwtext.client.widgets.grid.event.GridCellListenerAdapter;
import com.gwtext.client.widgets.layout.AnchorLayout;
import com.gwtext.client.widgets.layout.AnchorLayoutData;
import com.gwtextux.client.data.GWTProxy;
import com.gwtextux.client.data.PagingMemoryProxy;

/**
 * <B>SITE FACEO</B> <BR>
 * 
 * <P>
 * La classe CartoLayersPanel g&egrave;re le panneau des layers de la
 * cartographie World WindO
 * </P>
 * </P>
 * 
 * @author GR
 * @version 1.0, le 30/10/2008
 */
public class CartoLayersPanel extends CartographicPanel {

	// internationalisation
	private static final CartoLayersConstants layersConstants = (CartoLayersConstants) com.google.gwt.core.client.GWT
			.create(CartoLayersConstants.class);

	// constantes
	private static int CARTOGRAPHY_GRID_LAYERS_PANEL_WIDTH = GeneralConstant.EAST_PANEL_WIDTH - 10;

	private static String LAYER_NAME = "layerName";

	private static String ENABLED = "enabled";

	private static String OPACITY = "opacity";

	private static String MIN_ACTIVE_ALTITUDE = "minActiveAltitude";

	private static String MAX_ACTIVE_ALTITUDE = "maxActiveAltitude";

	private static String TYPE = "type";

	private static final int CARTO_LAYERS_PAGE_SIZE = 25;

	private static String SUN_NAME_LAYER = "Sun";

	// attributs
	private Store storeCartoLayers;

	private RecordDef recordDefCartoLayers;

	private EditorGridPanel editorGridPanelCartoLayers;

	private RowSelectionModel rowSelectionModelCartoLayers;

	private ColumnModel columnModelCartoLayers;

	private PagingToolbar pagingToolbar;

	private ToolbarButton refreshButton;

	private Renderer downActionRenderer;

	private Renderer upActionRenderer;

	private ColumnConfig nameColumn;

	private ColumnConfig enabledColumn;

	private ColumnConfig opacityColumn;

	private ColumnConfig downLayerColumn;

	private ColumnConfig upLayerColumn;
	
	// data
	/** ********************* Layers ********************* */ 
	/**
	 * Map object with identifier
	 * liste des identifiants des objets type 'Layer' g&eacute;n&eacute;r&eacute;s
	 * par la liste des 'Layers'
	 */
	private Map<Integer, CartoLayerBean> layers = 
		new HashMap<Integer, CartoLayerBean>();

	/**
	 * Proxy to manage the layers set on cartography
	 * 
	 */
	public static class CartoLayersProxyImpl extends GWTProxy {
		/**
		 * loading of the rows to show on the Grid Panel
		 * 
		 * @param _start
		 *            (int) : index of the first row to show
		 * @param _limit
		 *            (int) : number of rows to show
		 * @param _sort
		 *            (String) : the sort column
		 * @param _dir
		 *            (String) : the sort direction
		 * @param _o
		 *            (JavaScriptObject) :
		 * @param _baseParams
		 *            (UrlParam[]) : array of parameters
		 */
		public void load(int _start, int _limit, String _sort, String _dir,
				final JavaScriptObject _o, UrlParam[] _baseParams) {
			String[][] params = new String[_baseParams.length][];

			for (int i = 0; i < _baseParams.length; i++)
				params[i] = new String[] { _baseParams[i].getName(),
						_baseParams[i].getValue() };

			String JSON_LayersList = FacadeCartoWwd.JSgetLayersList();

			CartoLayersProxyServiceAsync cartoLayersProxyServiceAsync = CartoLayersProxyService.Util
					.getInstance();

			// Specify the URL at which our service implementation is running.
			// Note that the target URL must reside on the same domain and port
			// from which the host page was served.
			ServiceDefTarget endpoint = (ServiceDefTarget) cartoLayersProxyServiceAsync;
			String moduleRelativeURL = GWT.getModuleBaseURL()
					+ "/cartoLayersProxyController.srv";

			endpoint.setServiceEntryPoint(moduleRelativeURL);

			// Create an Asynchronous callback to handle the result.
			AsyncCallback<CartoLayersData> callback = new AsyncCallback<CartoLayersData>() {
				public void onSuccess(CartoLayersData response) {
					loadResponse(_o, true, response.getTotalRecords(), response
							.getData());
				} // public void onSuccess

				public void onFailure(Throwable caught) {
					com.google.gwt.user.client.Window.alert("onFailure : \n"
							+ caught.getLocalizedMessage());
					loadResponse(_o, false, 0, (JavaScriptObject) null);
				} // public void onFailure
			}; // AsyncCallback<CartoLayersData>

			cartoLayersProxyServiceAsync.getCartoLayers(_start, _limit, 6,
					CARTO_LAYERS_PAGE_SIZE, _sort, _dir, params,
					JSON_LayersList, callback);
		} // public void load(
	} // public static class CartoLayersProxyImpl extends GWTProxy {

	/**
	 * contructor
	 * 
	 * @param _panelId (String) : DOM panel identifier
	 */
	public CartoLayersPanel(String _panelId) {

		super();

		this.setId(_panelId);
		this.setLayout(new AnchorLayout());

		this.setTitle(layersConstants.title());
		this.setBorder(false);
		this.setAutoScroll(true);
		this.setAutoWidth(true);

		// ajout de la grille de gestion des layers
		FieldDef[] fieldsLayers = new FieldDef[] {
				new StringFieldDef(LAYER_NAME), new BooleanFieldDef(ENABLED),
				new FloatFieldDef(OPACITY),
				new FloatFieldDef(MIN_ACTIVE_ALTITUDE),
				new FloatFieldDef(MAX_ACTIVE_ALTITUDE),
				new IntegerFieldDef(TYPE) };

		this.recordDefCartoLayers = new RecordDef(fieldsLayers);

		Object[][] dataLayers = new Object[0][0];
		PagingMemoryProxy proxylayers = new PagingMemoryProxy(dataLayers);

		ArrayReader readerLayers = new ArrayReader(this.recordDefCartoLayers);
		this.storeCartoLayers = new Store(proxylayers, readerLayers, true);

		final SimpleStore opacityComboBoxStore = new SimpleStore(
				GeneralConstant.LAYER_OPACITY_VALUES, new Double[] { 0d, 0.1d,
						0.2d, 0.3d, 0.4d, 0.5d, 0.6d, 0.7d, 0.8d, 0.9d, 1d });
		opacityComboBoxStore.load();

		final ComboBox opacityComboBox = new ComboBox();
		opacityComboBox.setDisplayField(GeneralConstant.LAYER_OPACITY_VALUES);
		opacityComboBox.setStore(opacityComboBoxStore);
		opacityComboBox.setId(_panelId + "_opacityComboBox");

		final GridEditor opacityGridEditor = new GridEditor(opacityComboBox);

		// setup column model
		this.nameColumn = new ColumnConfig(layersConstants.name(), LAYER_NAME,
				200, false);
		this.nameColumn.setHidden(false);
		this.nameColumn.setFixed(true);

		this.enabledColumn = new ColumnConfig(layersConstants.enabled(),
				ENABLED, 50, false);
		this.enabledColumn.setAlign(TextAlign.CENTER);
		this.enabledColumn.setFixed(true);

		this.enabledColumn.setRenderer(new Renderer() {
			public String render(Object value, CellMetadata cellMetadata,
					Record record, int rowIndex, int colNum, Store store) {

				boolean checked = ((Boolean) value).booleanValue();

				return "<img class=\"faceo-image_button\" title=\""
						+ layersConstants.enabledColumnTip()
						+ "\" + alt=\""
						+ layersConstants.enabledColumnTip()
						+ "\" src=\""
						+ GWT.getHostPageBaseURL()
						+ (checked ? ImagesConstant.ICON_108_SHOW_LAYER
								: ImagesConstant.ICON_109_HIDE_LAYER)
						+ "\" width=\"22px\" height=\"22px\"/>";
			} // public String render
		});

		this.opacityColumn = new ColumnConfig(layersConstants.opacity(),
				OPACITY, 50, false);
		this.opacityColumn.setId(_panelId + "_opacityColumn");
		this.opacityColumn.setAlign(TextAlign.CENTER);
		this.opacityColumn.setFixed(true);
		this.opacityColumn.setEditor(opacityGridEditor);

		// *** down Layer Image renderer ***
		this.downActionRenderer = new Renderer() {
			public String render(Object value, CellMetadata cellMetadata,
					Record record, int rowIndex, int colNum, Store store) {
				// if ( (value != null) && (((String)
				// value).equalsIgnoreCase(layersConstants.layerDown())) )
				if (record.getAsString(LAYER_NAME).matches(SUN_NAME_LAYER)) {
					return "";
				} else {
					return "<img class=\"faceo-image_button\" title=\""
							+ layersConstants.downActionRendererTip()
							+ "\" + alt=\""
							+ layersConstants.downActionRendererTip()
							+ "\" src=\"" + GWT.getHostPageBaseURL()
							+ ImagesConstant.ICON_111_LAYER_DOWN
							+ "\" width=\"24px\" height=\"24px\"/>";
				}
				// else
				// return "";
			}
		}; // this.imageRenderer = new Renderer(){

		// *** up Layer Image renderer ***
		this.upActionRenderer = new Renderer() {
			public String render(Object value, CellMetadata cellMetadata,
					Record record, int rowIndex, int colNum, Store store) {

				if (record.getAsString(LAYER_NAME).matches(SUN_NAME_LAYER)) {
					return "";
				} else {
					return "<img class=\"faceo-image_button\" title=\""
							+ layersConstants.upActionRendererTip()
							+ "\" + alt=\""
							+ layersConstants.upActionRendererTip()
							+ "\" src=\"" + GWT.getHostPageBaseURL()
							+ ImagesConstant.ICON_112_LAYER_UP
							+ "\" width=\"24px\" height=\"24px\"/>";
				}
			}
		}; // this.imageRenderer = new Renderer(){

		this.downLayerColumn = new ColumnConfig("Down", "", 40, false,
				downActionRenderer);
		this.downLayerColumn.setAlign(TextAlign.CENTER);
		this.downLayerColumn.setFixed(true);

		this.upLayerColumn = new ColumnConfig("Up", "", 40, false,
				upActionRenderer);
		this.upLayerColumn.setAlign(TextAlign.CENTER);
		this.upLayerColumn.setFixed(true);

		BaseColumnConfig[] baseColumnConfigCartoLayers = new BaseColumnConfig[] {
				this.nameColumn, this.enabledColumn, this.opacityColumn,
				this.downLayerColumn, this.upLayerColumn };
		this.columnModelCartoLayers = new ColumnModel(
				baseColumnConfigCartoLayers);

		this.rowSelectionModelCartoLayers = new RowSelectionModel();

		this.editorGridPanelCartoLayers = new EditorGridPanel();
		this.editorGridPanelCartoLayers.setLayout(new AnchorLayout());
		this.editorGridPanelCartoLayers.setId(_panelId
				+ "_editorGridPanelCartoLayers");
		int mainMenuHeight = GeneralConstant.NORTH_PANEL_HEIGHT + 75;
		int clientHeight = com.google.gwt.user.client.Window.getClientHeight();
		if (clientHeight > (GeneralConstant.CARTOGRAPHY_GRID_LAYERS_PANEL_HEIGHT + mainMenuHeight))
			this.editorGridPanelCartoLayers.setHeight(clientHeight
					- mainMenuHeight);
		else
			this.editorGridPanelCartoLayers
					.setHeight(GeneralConstant.CARTOGRAPHY_GRID_LAYERS_PANEL_HEIGHT);
		this.editorGridPanelCartoLayers
				.setWidth(CARTOGRAPHY_GRID_LAYERS_PANEL_WIDTH);
		this.editorGridPanelCartoLayers.setId(_panelId
				+ "_editorGridPanelCartoLayers");
		this.editorGridPanelCartoLayers.setAutoScroll(true);
		this.editorGridPanelCartoLayers.setTitle(layersConstants.results());
		this.editorGridPanelCartoLayers.setStore(this.storeCartoLayers);
		this.editorGridPanelCartoLayers
				.setSelectionModel(this.rowSelectionModelCartoLayers);
		this.editorGridPanelCartoLayers
				.setColumnModel(this.columnModelCartoLayers);
		this.editorGridPanelCartoLayers.setClicksToEdit(1);

		this.editorGridPanelCartoLayers.setTrackMouseOver(true);
		this.editorGridPanelCartoLayers.setLoadMask(true);
		this.editorGridPanelCartoLayers.setFrame(true);
		this.editorGridPanelCartoLayers.setStripeRows(true);
		this.editorGridPanelCartoLayers.setIconCls("grid-icon");
		// Tool tip attached
		ToolTip editorGridPanelCartoLayersToolTip = new ToolTip(layersConstants
				.editorGridPanelCartoLayersTip());
		editorGridPanelCartoLayersToolTip
				.applyTo(this.editorGridPanelCartoLayers);

		GridView viewCatalogResults = new GridView() {
			public String getRowClass(Record record, int index,
					RowParams rowParams, Store store) {
				return "x-grid3-row-collapsed";
			}
		};
		viewCatalogResults.setForceFit(false);
		viewCatalogResults.setEnableRowBody(true);

		this.editorGridPanelCartoLayers.setView(viewCatalogResults);

		addEditorGridPanelCellListener(this.editorGridPanelCartoLayers);
		addGridPanelCellListener(this.editorGridPanelCartoLayers);

		this.pagingToolbar = new PagingToolbar(this.storeCartoLayers);
		this.pagingToolbar.setId(_panelId + "_pagingToolbar");
		this.pagingToolbar.setPageSize(CARTO_LAYERS_PAGE_SIZE);
		this.pagingToolbar.setDisplayInfo(true);
		this.pagingToolbar.setDisplayMsg(layersConstants.displayMsg()
				+ " {0} - {1} of {2}");
		this.pagingToolbar.setEmptyMsg(layersConstants.noLayerToDisplayMsg());

		this.pagingToolbar.addSeparator();

		this.editorGridPanelCartoLayers.setBottomToolbar(this.pagingToolbar);

		this.editorGridPanelCartoLayers.addListener(new PanelListenerAdapter() {
			public void onRender(Component component) {
				// ------------------------ 'Update Orders list' Button Listener
				// -------------------------------
				refreshButton = pagingToolbar.getRefreshButton();
				refreshButton.addListener(new ButtonListenerAdapter() {
					public void onClick(Button button, EventObject e) {
						updateEditorGridCartoLayers("");
					}
				});
			}
		});

		this.editorGridPanelCartoLayers.getView().setAutoFill(true);
		this.storeCartoLayers.load(0, this.pagingToolbar.getPageSize());
		this.editorGridPanelCartoLayers.show();

		// if (FacadeUtil.resizePanels())
		// addPanelAddListener();

		this.add(this.editorGridPanelCartoLayers, new AnchorLayoutData("100% 100%"));

	} // public CartoLayersPanel() {

	/**
	 * Adding a listener to a EditorGridPanel
	 * 
	 * @param _editorGridPanel
	 *            (EditorGridPanel) : the EditorGridPanel
	 */
	private void addEditorGridPanelCellListener(EditorGridPanel _editorGridPanel) {
		_editorGridPanel.addEditorGridListener(new EditorGridListenerAdapter() {
			public boolean doBeforeEdit(GridPanel grid, Record record,
					java.lang.String field, java.lang.Object value,
					int rowIndex, int colIndex) {

				return true;
			} // public boolean doBeforeEdit(GridPanel grid,

			public void onAfterEdit(GridPanel grid, Record record,
					java.lang.String field, java.lang.Object newValue,
					java.lang.Object oldValue, int rowIndex, int colIndex) {

				switch (colIndex) {
				case GeneralConstant.CARTO_LAYER_OPACITY:
					String layerName = record.getAsString(LAYER_NAME);
					if (layerName != null) {
						double opacity = 0;
						opacity = record.getAsDouble(OPACITY);
						record.set(OPACITY, opacity);
						record.commit();

						FacadeCartoWwd.JSmodifyLayerOpacity(layerName, opacity);
						storeCartoLayers.commitChanges();
					}
					break;
				default:
					;
				} // switch (colIndex) {

			} // public void onAfterEdit(GridPanel grid, Record record,
				// java.lang.String field, java.lang.Object newValue,
				// java.lang.Object oldValue, int rowIndex, int colIndex) {
		});
	} // private void addEditorGridPanelCellListener (EditorGridPanel
		// _editorGridPanel) {

	/**
	 * Adding a listener to a GridPanel
	 * 
	 * @param _editorGridPanel
	 *            (GridPanel) : the GridPanel
	 */
	private void addGridPanelCellListener(GridPanel _editorGridPanel) {
		_editorGridPanel.addGridCellListener(new GridCellListenerAdapter() {
			public void onCellClick(GridPanel _editorGrid, int rowIndex,
					int colIndex, EventObject e) {

				String layerName = "";
				Record record;

				switch (colIndex) {
				case GeneralConstant.CARTO_LAYER_ENABLED: // click sur la
															// colonne 'Enabled'
					layerName = _editorGrid.getView().getCell(rowIndex, 0)
							.getInnerText();
					record = storeCartoLayers.getAt(rowIndex);

					boolean checked = record.getAsBoolean(ENABLED);
					record.set(ENABLED, !checked);
					record.commit();
					if (layerName.contentEquals("Sun")) {
						FacadeCartoWwd.JSshowHideSun(record
								.getAsBoolean(ENABLED));
					} else {
						FacadeCartoWwd.JSshowHideLayer(layerName, record
								.getAsBoolean(ENABLED));
					}// if (layerName.contentEquals("Sun")) {

					storeCartoLayers.commitChanges();

					break;
				case GeneralConstant.CARTO_LAYER_DOWN:
					layerName = _editorGrid.getView().getCell(rowIndex, 0)
							.getInnerText();
					record = storeCartoLayers.getAt(rowIndex);
					if (layerName != null) {
						FacadeCartoWwd.JSdownLayer(layerName);
						storeCartoLayers.reload();
					}
					break;
				case GeneralConstant.CARTO_LAYER_UP:
					layerName = _editorGrid.getView().getCell(rowIndex, 0)
							.getInnerText();
					record = storeCartoLayers.getAt(rowIndex);
					if (layerName != null) {
						FacadeCartoWwd.JSupLayer(layerName);
						storeCartoLayers.reload();
					}
					break;
				default:
					;
				} // switch (colIndex) {

			} // public void onCellClick(GridPanel _editorGridPanel, int
				// rowIndex, int colindex,
		});
	} // private void addGridPanelCellListener (GridPanel _editorGridPanel) {

	/**
	 * update of the Grid which contains the cartography's layers
	 * 
	 * @param _objectLayerName
	 *            (String) : the layer name to add to the layer's list set the
	 *            parameter to "" to force the update of the layer's list
	 */
	public void updateEditorGridCartoLayers(String _objectLayerName) {

		boolean updateList = true;

		// on verifie que le layer a ajouter n'est pas deja present dans la
		// liste des layers
		if (_objectLayerName != null)
			if (!_objectLayerName.equalsIgnoreCase("")) {
				if (this.layers != null)
					if (this.layers.size() > 0)
						for (Entry<Integer, CartoLayerBean> layer : this.layers.entrySet()) {
							CartoLayerBean cartoLayerBean = layer.getValue();
							if (cartoLayerBean != null)
								if (cartoLayerBean.getLayerName()
										.equalsIgnoreCase(_objectLayerName))
									updateList = false;
						} // or (Entry<Integer, CartoLayerBean> layer : this.layers.entrySet()) {
			} // if (!_objectLayerName.equalsIgnoreCase("")) {

		if (updateList) {
			getCartoLayersList();
			ArrayReader reader = new ArrayReader(this.recordDefCartoLayers);
			this.storeCartoLayers = new Store(new CartoLayersProxyImpl(),
					reader, true);
			this.editorGridPanelCartoLayers.reconfigure(this.storeCartoLayers,
					this.editorGridPanelCartoLayers.getColumnModel());
			this.editorGridPanelCartoLayers.getView().setAutoFill(false);
			this.editorGridPanelCartoLayers.getView().setForceFit(false);

			this.pagingToolbar.setStore(this.storeCartoLayers);
			this.pagingToolbar.setPageSize(CARTO_LAYERS_PAGE_SIZE);
			// this.pagingToolbar.bind(this.storeCartoLayers);
			this.storeCartoLayers.load(0, this.pagingToolbar.getPageSize());
			this.pagingToolbar.updateInfo();

			this.editorGridPanelCartoLayers.show();
		} // if (updateList) {
	} // public void updateEditorGridCartoLayers(String _objectLayerName) {

	/**
	 * return the cartography's layers
	 * 
	 * // * @return Object[][] : the layers
	 */
	private void getCartoLayersList() {

		String JSON_LayersList = FacadeCartoWwd.JSgetLayersList();

		if (this.storeCartoLayers != null) {
			this.storeCartoLayers.removeAll();
		}

		if (this.layers != null) {
			this.layers.clear();
		}

		Map<Integer, CartoLayerBean> layers = new HashMap<Integer, CartoLayerBean>();
		// Object[][] dataLayers = null;

		try {
			// parse the response text into JSON
			JSONValue jsonValue = JSONParser.parse(JSON_LayersList);
			JSONArray jsonArray = jsonValue.isArray();

			if (jsonArray != null) {
				// JSON format {"layerList": [
				// {"name": "Etoiles", "enabled": true, "opacity": 1.0,
				// "minActiveAltitude": -1.7976931348623157E308,
				// "maxActiveAltitude": 1.7976931348623157E308, "type": 0},
				// {"name": "Atmosphère", "enabled": true, "opacity": 1.0,
				// "minActiveAltitude": -1.7976931348623157E308,
				// "maxActiveAltitude": 1.7976931348623157E308, "type": 0},
				// ...
				// {"name": "Labels", "enabled": true, "opacity": 1.0,
				// "minActiveAltitude": -1.7976931348623157E308,
				// "maxActiveAltitude": 1.7976931348623157E308, "type": 0},
				// {"name": "MS Virtual Earth", "enabled": true, "opacity": 0.8,
				// "minActiveAltitude": -1.7976931348623157E308,
				// "maxActiveAltitude": 1.7976931348623157E308, "type": 0}] }
				// Types :
				// World Wind Layer : CARTO_WWJ_LAYER_TYPE = 0
				// Area Of Interest : CARTO_AOI_LAYER_TYPE = 1
				// SSE EO Catalog Layer : CARTO_SSE_EO_ATALOG_LAYER_TYPE = 2
				// Programming : CARTO_PROGRAMMING_LAYER_TYPE = 3
				// Orbites : CARTO_ORBITES_LAYER_TYPE = 4
				// Images : CARTO_IMAGES_LAYER_TYPE = 5
				// Labels : CARTO_LABELS_LAYER_TYPE = 6
				// DataDoors : CARTO_DATADOORS_LAYER_TYPE = 7
				// Annotations : CARTO_ANNOTATIONS_LAYER_TYPE = 8
				// Satellites : CARTO_SATELITTES_LAYER_TYPE = 9
				// Flow : CARTO_FLOW_LAYER_TYPE = 10
				// SIT : CARTO_SIT_LAYER_TYPE = 11
				// WMSTiledImageLayer : CARTO_WMS_TILED_IMAGE_LAYER_TYPE = 12
				// TrackLayer : CARTO_TRACK_LAYER_TYPE = 13
				// TrackMarkerLayer : CARTO_TRACK_MARKER_LAYER_TYPE = 14

				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject jsLayer = null;

					jsLayer = jsonArray.get(i).isObject();

					if (jsLayer != null) {
						jsonValue = jsLayer.get("type");

						boolean addLayerToList = true;

						// verification des types des layers
						// on n'ajoute pas les layers de type 'WMS'
						if (((int) jsonValue.isNumber().doubleValue() == GeneralConstant.CARTO_WMS_TILED_IMAGE_LAYER_TYPE))
							addLayerToList = false;

						if (addLayerToList) {
							CartoLayerBean layerBean = new CartoLayerBean();

							jsonValue = jsLayer.get("name");
							if (jsonValue != null)
								if (jsonValue.isString() != null)
									layerBean.setLayerName(jsonValue.isString()
											.stringValue());

							jsonValue = jsLayer.get("enabled");
							if (jsonValue != null)
								if (jsonValue.isBoolean() != null)
									layerBean.setLayerEnabled(jsonValue
											.isBoolean().booleanValue());

							jsonValue = jsLayer.get("opacity");
							if (jsonValue != null)
								if (jsonValue.isNumber() != null)
									layerBean.setLayerTransparency(Double
											.valueOf(jsonValue.isNumber()
													.toString()));

							jsonValue = jsLayer.get("minActiveAltitude");
							if (jsonValue != null)
								if (jsonValue.isNumber() != null)
									layerBean.setLayerMinActiveAltitude(Float
											.valueOf(jsonValue.isNumber()
													.toString()));

							jsonValue = jsLayer.get("maxActiveAltitude");
							if (jsonValue != null)
								if (jsonValue.isNumber() != null)
									layerBean.setLayerMaxActiveAltitude(Float
											.valueOf(jsonValue.isNumber()
													.toString()));

							jsonValue = jsLayer.get("type");
							if (jsonValue != null)
								if (jsonValue.isNumber() != null)
									layerBean.setLayerType((int) jsonValue
											.isNumber().doubleValue());

							layers.put(i, layerBean);
						} // if (addLayerToList) {
					} // if (jsLayer != null)
				} // for (int i=0; i<jsonArray.size(); i++) {

				this.layers = layers;
			} else {
				com.google.gwt.user.client.Window.alert("Could not parse JSON");
			} // if (jsonArray != null) {
		} catch (JSONException jSONException) {
			com.google.gwt.user.client.Window.alert("Could not parse JSON");
		} catch (Exception exception) {
			com.google.gwt.user.client.Window.alert("Could not parse JSON");
		} // try

		// return dataLayers;

	} // private Object[][] getCartoLayersList() {

	/**
	 * getter on the refresh button of the grid which contains the cartography's
	 * layers
	 * 
	 * @return ToolbarButton : the refresh button
	 */
	public ToolbarButton getRefreshButton() {
		return this.refreshButton;
	}
	
	/**
	 * M&eacute;thode retournant la HashMap
	 * des objets 'Layers' de la repr&eacute;sentation World Wind de la Terre
	 * 
	 * @return Map<Integer, CartoLayerBean>	: la HashMap
	 */
	public Map<Integer, CartoLayerBean> getLayers() {
		return this.layers;
	}

} // class
