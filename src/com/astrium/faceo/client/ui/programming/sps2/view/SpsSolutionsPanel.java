package com.astrium.faceo.client.ui.programming.sps2.view;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.astrium.faceo.client.bean.cartography.CartoObjectBean;
import com.astrium.faceo.client.bean.programming.sps2.TaskResultBean;
import com.astrium.faceo.client.bean.programming.sps2.responses.PointBean;
import com.astrium.faceo.client.bean.programming.sps2.responses.SegmentBean;
import com.astrium.faceo.client.bean.programming.sps2.responses.TaskingResponseBean;
import com.astrium.faceo.client.common.GeneralConstant;
import com.astrium.faceo.client.common.ImagesConstant;
import com.astrium.faceo.client.common.programming.sps2.Sps2ColorsConstants;
import com.astrium.faceo.client.common.programming.sps2.Sps2Constants;
import com.astrium.faceo.client.common.programming.sps2.Sps2GeneralConstants;
import com.astrium.faceo.client.service.facadeCartoWWD.FacadeCartoWwd;
import com.astrium.faceo.client.ui.HomePage;
import com.astrium.faceo.client.ui.cartography.cartoLayers.CartoLayersPanel;
import com.astrium.faceo.client.ui.programming.sps2.SpsClientUtils;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.TextAlign;
import com.gwtext.client.data.ArrayReader;
import com.gwtext.client.data.BooleanFieldDef;
import com.gwtext.client.data.FieldDef;
import com.gwtext.client.data.MemoryProxy;
import com.gwtext.client.data.Record;
import com.gwtext.client.data.RecordDef;
import com.gwtext.client.data.Store;
import com.gwtext.client.data.StringFieldDef;
import com.gwtext.client.util.Format;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.Toolbar;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.event.CycleButtonListenerAdapter;
import com.gwtext.client.widgets.grid.BaseColumnConfig;
import com.gwtext.client.widgets.grid.CellMetadata;
import com.gwtext.client.widgets.grid.ColumnConfig;
import com.gwtext.client.widgets.grid.ColumnModel;
import com.gwtext.client.widgets.grid.GridPanel;
import com.gwtext.client.widgets.grid.GridView;
import com.gwtext.client.widgets.grid.Renderer;
import com.gwtext.client.widgets.grid.RowParams;
import com.gwtext.client.widgets.grid.RowSelectionModel;
import com.gwtext.client.widgets.grid.event.GridCellListenerAdapter;
import com.gwtext.client.widgets.layout.AnchorLayout;
import com.gwtext.client.widgets.layout.AnchorLayoutData;

/**
 * <B>SITE FACEO</B> <BR>
 * 
 * <P>
 * La classe SpsSolutionsPanel g&egrave;re les widgets du panneau des
 * r&eacute;ltats d'une requ&ecirc;te 'getFeasibility' SPS
 * </P>
 * </P>
 * 
 * @author GR
 * @version 1.0, le 10/05/2010
 */
public class SpsSolutionsPanel extends Panel {

	// internationalisation
	private static final Sps2Constants spsConstants = (Sps2Constants) com.google.gwt.core.client.GWT
	.create(Sps2Constants.class);

	// attributs
	private final int PROGRAMMING_SEGMENTS_SHOW = 0;

	private final String SEGMENT_ID = "segmentId";
	
	private final String SEGMENT_STATUS = "segmentStatus";

	private final String INSTRUMENT_MODE = "instrumentMode";

	private final String GEO_LOCATION = "geoLocation";

	private final String SHOW_ALL_MESHES = "showAllMeshes";

	// attributs
	private Store storeSegments;

	private RecordDef recordDefSegments;

	private GridPanel gridPanelSegments;

	private ColumnModel columnModelSegments;

	private RowSelectionModel rowSelectionModelProgSegments;

	private GridView viewSegments;

	private Toolbar resultsToolbar;

	private ToolbarButton showHideSegments;

	// data
	/**
	 * SPS 'GetFeasibility', 'Submit' segments
	 */
	private static Map<String, SegmentBean> feasibilitySegments = new HashMap<String, SegmentBean>();

	/**
	 * constructor
	 * 
	 * @param _panelId
	 *            (String) : HTML identifier on the page
	 */
	public SpsSolutionsPanel(String _panelId) {
		super();

		this.setId(_panelId);
		this.setLayout(new AnchorLayout());

		this.setTitle(spsConstants.solutions());
		this.setBorder(false);
		this.setAutoScroll(true);
		this.setAutoWidth(true);
		this.setAnimCollapse(true);
		this.setCollapsible(true);
		this.setCollapsed(false);
		this.setClosable(false);

		FieldDef[] fieldsProgrammingSegments = new FieldDef[] {
				new BooleanFieldDef(Sps2GeneralConstants.SHOW_SEGMENT_ON_CARTOGRAPHY), 
				new StringFieldDef(SEGMENT_ID),
				new StringFieldDef(SEGMENT_STATUS),
				new StringFieldDef(INSTRUMENT_MODE), 
				new StringFieldDef(GEO_LOCATION) };
		this.recordDefSegments = new RecordDef(fieldsProgrammingSegments);

		Object[][] dataProgrammingSegments = new Object[0][0];
		MemoryProxy proxyProgrammingSegments = new MemoryProxy(dataProgrammingSegments);
		ArrayReader readerProgrammingSegments = new ArrayReader(this.recordDefSegments);
		this.storeSegments = new Store(proxyProgrammingSegments, readerProgrammingSegments, false);

		ColumnConfig showColumn = new ColumnConfig(spsConstants.meshesShow(),
				Sps2GeneralConstants.SHOW_SEGMENT_ON_CARTOGRAPHY, 45, false);
		showColumn.setAlign(TextAlign.CENTER);

		ColumnConfig segmentIdColumn = new ColumnConfig(spsConstants.mesheId(), SEGMENT_ID, 410, false, renderSegment);
		segmentIdColumn.setCss("white-space:normal;");

		ColumnConfig segmentStatusColumn = new ColumnConfig(spsConstants.mesheStatus(), SEGMENT_STATUS, 70, false, renderStatus);
		segmentStatusColumn.setAlign(TextAlign.CENTER);

		ColumnConfig instrumentModeColumn = new ColumnConfig(spsConstants.meshesInstrumentMode(), INSTRUMENT_MODE, 80,
				false);
		instrumentModeColumn.setAlign(TextAlign.LEFT);

		ColumnConfig geoLocationColumn = new ColumnConfig(spsConstants.meshesGeoLocation(), GEO_LOCATION, 200, false);
		geoLocationColumn.setAlign(TextAlign.LEFT);
		geoLocationColumn.setHidden(true);

		showColumn.setRenderer(new Renderer() {
			public String render(Object value, CellMetadata cellMetadata, Record record, int rowIndex, int colNum,
					Store store) {

				boolean checked = ((Boolean) value).booleanValue();

				return "<img class=\"faceo-image_button\" src=\"" + GWT.getHostPageBaseURL()
				+ (checked ? ImagesConstant.ICON_174_HIDE_FOOTPRINT : ImagesConstant.ICON_171_SHOW_FOOTPRINT)
				+ "\" width=\"24px\" height=\"24px\"" + "onMouseOver=\"changecursor();\"" + "/>";
			} // 
		});

		BaseColumnConfig[] baseColumnConfigMeshes = new BaseColumnConfig[] { 
				showColumn, 
				segmentIdColumn,
				segmentStatusColumn,
				instrumentModeColumn, 
				geoLocationColumn };
		this.columnModelSegments = new ColumnModel(baseColumnConfigMeshes);

		this.gridPanelSegments = new GridPanel();
		this.gridPanelSegments.setId(Sps2GeneralConstants.GRID_PANEL_PROGRAMMING_SOLUTIONS);
		this.gridPanelSegments.setLayout(new AnchorLayout());

		this.gridPanelSegments.setAutoScroll(true);
		// this.gridPanelProgrammingSegments.setAutoWidth(true);
		this.gridPanelSegments.setAutoWidth(true);
		this.gridPanelSegments.setAutoHeight(true);
		this.gridPanelSegments.setTitle(spsConstants.solutions());

		this.gridPanelSegments.setStore(this.storeSegments);
		this.gridPanelSegments.setColumnModel(this.columnModelSegments);

		this.gridPanelSegments.setTrackMouseOver(true);
		// this.gridPanelSegments.setLoadMask(true);

		this.rowSelectionModelProgSegments = new RowSelectionModel();
		this.gridPanelSegments.setSelectionModel(this.rowSelectionModelProgSegments);

		this.gridPanelSegments.setFrame(true);
		this.gridPanelSegments.setStripeRows(true);
		this.gridPanelSegments.setIconCls("grid-icon");

		this.viewSegments = new GridView() {
			public String getRowClass(Record record, int index, RowParams rowParams, Store store) {
				rowParams.setBody(Format.format("<p>{0}</p>", record.getAsString(GEO_LOCATION)));
				return "x-grid3-row-expanded";
			}
		};
		this.viewSegments.setForceFit(false);
		this.viewSegments.setEnableRowBody(true);

		this.gridPanelSegments.setView(this.viewSegments);

		addGridPanelCellListener(this.gridPanelSegments);

		this.gridPanelSegments.getView().setAutoFill(true);

		this.gridPanelSegments.setEnableDragDrop(true);
		this.gridPanelSegments.setDdGroup(GeneralConstant.CARTO_DRAG_DROP_GROUP);
		this.gridPanelSegments.setDragDropText("Drag Programming Solution");

		// --------------------------------------------------------------
		// toolbar
		// create a toolbar and various menu items
		this.resultsToolbar = new Toolbar();
		this.resultsToolbar.setId(this.getId() + "_resultsToolbar");
		this.resultsToolbar.setStyleName("testResultsToolbar");
		this.resultsToolbar.setSize("100%", "30px");

		// create a CycleButton
		this.showHideSegments = new ToolbarButton(spsConstants.displayAllMeshes());
		this.showHideSegments.setId(this.getId() + "_" + SHOW_ALL_MESHES);

		this.showHideSegments.addListener(new CycleButtonListenerAdapter() {
			public void onClick(Button button, EventObject e) {
				if (button.getText().equalsIgnoreCase(spsConstants.displayAllMeshes())) {
					gridPanelSegments.getEl().mask("Load segments");
					showHideAllSegmentsOnCartography(true);
					button.setText(spsConstants.hideAllMeshes());
				} else if (button.getText().equalsIgnoreCase(spsConstants.hideAllMeshes())) {
					gridPanelSegments.getEl().mask("Hide segments");
					showHideAllSegmentsOnCartography(false);
					button.setText(spsConstants.displayAllMeshes());
				}
				gridPanelSegments.getEl().unmask();
			}
		});

		this.resultsToolbar.addButton(this.showHideSegments);
		this.resultsToolbar.addSeparator();

		this.setTopToolbar(this.resultsToolbar);
		// --------------------------------------------------------------

		this.add(this.gridPanelSegments, new AnchorLayoutData("100% 100%"));

		this.setAutoScroll(true); // ajout des scrolls si necessaire
	} // public SpsSolutionsPanel() {

	/**
	 * add Cell Click Listener on a Grid Panel
	 * 
	 * @param _gridPanel
	 *            (GridPanel) : the Grid Panel
	 */
	private void addGridPanelCellListener(GridPanel _gridPanel) {
		_gridPanel.addGridCellListener(new GridCellListenerAdapter() {
			public void onCellClick(GridPanel _editorGrid, int rowIndex, int colIndex, EventObject e) {

				switch (colIndex) {
				case PROGRAMMING_SEGMENTS_SHOW: { // click on 'Show' column
					Record record = _editorGrid.getStore().getAt(rowIndex);

					boolean checked = record.getAsBoolean(Sps2GeneralConstants.SHOW_SEGMENT_ON_CARTOGRAPHY);
					SegmentBean segment = feasibilitySegments.get(getSegmentId(record));

					if (!checked) { // draw mesh on cartography
						if (segment != null) {
							drawSegmentOnCartography(segment, true);
						}
					} else { // remove mesh from cartography
						if (segment != null) {
							hideSegmentOnCartography(segment);
						}
					} // if

					record.set(Sps2GeneralConstants.SHOW_SEGMENT_ON_CARTOGRAPHY, !checked);
					record.commit();

					_editorGrid.getStore().commitChanges();

					break;
				} // PROGRAMMING_SEGMENTS_SHOW
				
				default: {
					Record record = storeSegments.getAt(rowIndex);
					if (record.getAsBoolean(Sps2GeneralConstants.SHOW_SEGMENT_ON_CARTOGRAPHY)) {
						// get object corresponding to selected row
						SegmentBean segment = feasibilitySegments.get(getSegmentId(record));

						if ((segment != null) && (segment.getIdCartography() > -1)) {
							FacadeCartoWwd.JSselectFaceoObject(segment.getIdCartography());
						}
					}
					break;
				} // default
				} // switch (colIndex) {

			} // public void onCellClick(GridPanel _gridPanel, int rowIndex, int
		});
	} // private void addGridPanelCellListener (GridPanel _editorGridPanel) {

	/**
	 * draw a programming segment on the Cartography
	 * 
	 * @param _segment
	 *            (SegmentBean) : the programming segment
	 * @param _moveTo
	 *            (boolean) : true to move to the position of the Product
	 * 
	 * @return boolean : true if the programming segment has been darwn on
	 *         cartography
	 */
	public boolean drawSegmentOnCartography(SegmentBean _segment, boolean _moveTo) {
		boolean drawSegment = false;

		// recuperation du segment
		String strSegment = _segment.getPointsXML();

		double transparency = 0.5d;
		/** */
		float[] SAR2_HSB = SpsClientUtils.getStatusHsvColorBy(_segment.getStatus());
		float hue = SAR2_HSB[0];
		float saturation = SAR2_HSB[1];
		float brightness = SAR2_HSB[2];

		int idCartoObject = FacadeCartoWwd.JSdrawMultiExtentOfInJava(GeneralConstant.LAYER_PROG_SOLUTIONS, strSegment,
				"", "Segment " + _segment.getId(), hue, saturation, brightness, transparency, false, false, true);

		if (idCartoObject > -1) {
			drawSegment = true;

			CartoObjectBean cartoObjectBean = new CartoObjectBean("Segment" + _segment.getId(), idCartoObject,
					GeneralConstant.CARTO_PROGRAMMING_OBJECT_TYPE, GeneralConstant.LAYER_PROG_SOLUTIONS, hue,
					saturation, brightness, false, transparency);
			HomePage.getHomePageBean().setContentCartographyObjects(idCartoObject, cartoObjectBean);

			if (_segment != null) {
				_segment.setIdCartography(idCartoObject);// mise a jour de
				// l'identifiant de
				// l'objet dans la
				// cartographie
				_segment.setShowOnCartography(true);

				PointBean segment = _segment.getPoints().get(0);

				if (segment != null) {
					if (_moveTo) {
						FacadeCartoWwd.JSgotoLatLon(_segment.getPoints().get(0).getLatitude(), _segment.getPoints()
								.get(0).getLongitude(), 1000000);
					} // if (_moveTo) {
					if (HomePage.getHomePageBean().getInitCartoLayers()) {
						((CartoLayersPanel) HomePage.getEastPanel().getCartographicPanel(
								GeneralConstant.CARTO_LAYERS_PANEL_ID))
								.updateEditorGridCartoLayers(GeneralConstant.LAYER_PROG_SOLUTIONS);
					} // if (HomePage.getHomePageBean().getInitCartoLayers()) {
				} // if (segment != null) {
			} // if (_segmentBean != null) {
		} // if (idCartoObject > -1) {

		return drawSegment;
	} // public boolean drawSegmentOnCartography(SegmentBean _segment, boolean

	/**
	 * hide the Polygon of the Segment on the Cartography
	 * 
	 * @param _segment
	 *            (SegmentBean) : the Segment to hide on cartography
	 * 
	 * @return boolean : true si OK
	 */
	private boolean hideSegmentOnCartography(SegmentBean _segment) {
		boolean hideSegment = false;

		if (_segment != null) {
			int idcartoObject = _segment.getIdCartography();
			if (idcartoObject > 0) {
				FacadeCartoWwd.delCarto3DObject(idcartoObject);
			} // if

			_segment.setIdCartography(-1);
			_segment.setShowOnCartography(false);

			hideSegment = true;
		} // if (_segment != null) {

		return hideSegment;
	} // private boolean hideSegmentOnCartography(SegmentBean _segment) {

	/**
	 * show or hide all the programming's responses in the grid on the
	 * Cartography
	 * 
	 * @param _showSegmentsOnCartography
	 *            (boolean) : true to show the results on the cartography, else
	 *            false
	 */
	private void showHideAllSegmentsOnCartography(boolean _showSegmentsOnCartography) {
		if ( (feasibilitySegments != null) && (feasibilitySegments.size() > 0) ) {
			boolean moveToPosition = false;

			int rang = 0;
			for (Entry<String, SegmentBean> segments : feasibilitySegments.entrySet()) {
				SegmentBean segment = segments.getValue();

				boolean drawSegment = false;
				if (_showSegmentsOnCartography) {
					if (rang == (feasibilitySegments.size() - 1)) { // on se positionne sur le dernier element ajoute
						moveToPosition = true;
					} // if (rang == (feasibilitySegments.size() -1)) {

					drawSegment = drawSegmentOnCartography(segment, moveToPosition);
				} else {
					drawSegment = hideSegmentOnCartography(segment);
				}

				if (drawSegment) {
					int index = storeSegments.find(SEGMENT_ID, segment.getId(), 0, true, true);
					if (index > -1) {
						Record record = storeSegments.getRecordAt(index);
						if (record != null) {
							record.set(Sps2GeneralConstants.SHOW_SEGMENT_ON_CARTOGRAPHY, _showSegmentsOnCartography);
							record.commit();
							storeSegments.commitChanges();
						}
					} // if (index > -1) {
				} // if (drawSegment) {

				rang++;
			} // for(Entry<String, SegmentBean> segments :
			// feasibilitySegments.entrySet()) {

			// on efface le layer contenant tous les footprints
			if (!_showSegmentsOnCartography) {
				FacadeCartoWwd.delCartoLayerObjects(GeneralConstant.LAYER_PROG_SOLUTIONS);
			} // if

			// mise a jour de la liste des layers de la cartographie
			if (HomePage.getHomePageBean().getInitCartoLayers()) {
				((CartoLayersPanel) HomePage.getEastPanel().getCartographicPanel(
						GeneralConstant.CARTO_LAYERS_PANEL_ID))
						.updateEditorGridCartoLayers(GeneralConstant.LAYER_CATALOGS_FOOTPRINTS);
			} // if (HomePage.getHomePageBean().getInitCartoLayers()) {
		} // if (feasibilitySegments != null) &&
	} // private void showHideAllSegmentsOnCartography(boolean

	/**
	 * update the segments list of a SPS request 'GetFeasibility', 'Submit'
	 * 
	 * @param _taskResultBean
	 *            (TaskResultBean) : task informations of the request
	 */
	public void updateGridSegments(TaskResultBean _taskResultBean) {
		clearGridProgrammingSegments();

		boolean hasSegments = false;
		int nbRows = 0;

		TaskingResponseBean taskingResponseBean = _taskResultBean.getTaskingResponseBean();
		HashMap<String, SegmentBean> segments = taskingResponseBean.getFeasibilityStudy().getSegments();
		if ( (segments != null) && (segments.size() > 0) ) {
			hasSegments = true;
			nbRows = segments.size();
		}

		Object[][] data = new Object[0][0];
		int i = 0;
		if (hasSegments) {
			data = new Object[nbRows][5];

			for (Entry<String, SegmentBean> segment : segments.entrySet()) {
				SegmentBean segmentBean = segment.getValue();

				data[i][0] = segmentBean.getShowOnCartography();
				data[i][1] = taskingResponseBean.getStatusReport().getTask() + "/" + segmentBean.getId();
				data[i][2] = segmentBean.getStatus();
				data[i][3] = segmentBean.getInstrumentMode();

				String strSegment = "<div><b>" + segmentBean.getSensorId() + "</b></div>"
				+ spsConstants.solutionStartTimeStopTime() + "<div>";
				// la reponse est de la forme
				// <swe:value>2009-01-01T11:10:40.0Z</swe:value>
				DateTimeFormat dateFormat1 = DateTimeFormat.getFormat(Sps2GeneralConstants.TIME_FORMAT);
				DateTimeFormat dateFormat2 = DateTimeFormat.getFormat(GeneralConstant.SHORT_FORMAT_DATE);
				Date achievementDate = dateFormat1.parse(segmentBean.getAcquisitionStart());
				strSegment += dateFormat2.format(achievementDate) + " - " + segmentBean.getAcquisitiontop() + "</div>";
				data[i][4] = strSegment;

				i++;
			} // for(Entry<String, SegmentBean> segment : segments.entrySet()) {

			setContentFeasibilitySegments(segments);
		} // if (hasSegments) {

		this.expand();

		MemoryProxy proxy = new MemoryProxy(data);
		ArrayReader reader = new ArrayReader(this.recordDefSegments);
		this.storeSegments = new Store(proxy, reader, true);
		this.gridPanelSegments.reconfigure(this.storeSegments, this.gridPanelSegments.getColumnModel());
		this.gridPanelSegments.getView().setAutoFill(false);
		this.gridPanelSegments.getView().setForceFit(false);

		this.storeSegments.load();

		this.gridPanelSegments.show();
	} // public void updateGridSegments

	/**
	 * M&eacute;thode permettant la s&eacute;lection d'un segment 'Feasibility'
	 * ou 'Submit' dans la grille des segments
	 * 
	 * @param _currentCartoIndex
	 *            (int) : identifiant de l'objet sur la cartographie
	 */
	public void selectProductRow(int _currentCartoIndex) {
		try {
			Map<String, SegmentBean> segments = feasibilitySegments;

			String id = "";

			for (Entry<String, SegmentBean> segment : segments.entrySet()) {
				SegmentBean segmentBean = segment.getValue();

				if ((segmentBean.getShowOnCartography()) && (segmentBean.getIdCartography() == _currentCartoIndex)) {
					id = segmentBean.getId();
				}
			} // for(Entry<String, SegmentBean> segment : segments.entrySet()) {

			this.rowSelectionModelProgSegments.clearSelections();

			if (!id.equalsIgnoreCase("")) {
				int idGrid = this.storeSegments.find(SEGMENT_ID, id, 0, true, true);
				if ((idGrid > -1) && (idGrid < feasibilitySegments.size())) {
					this.rowSelectionModelProgSegments.selectRow(idGrid);
				}
			} // if (!id.equalsIgnoreCase("")) {
		} catch (Exception exception) {
		}
	} // 

	/**
	 * M&eacute;thode permettant l'effacement de la liste des segments d'une
	 * requ&ecirc;te SPS 'GetFeasibility', 'Submit' et la suppression des
	 * segments affich&eacute;es sur la cartographie
	 */
	public void clearGridProgrammingSegments() {
		// suppression des segments affich&eacute;s sur la cartographie
		if (this.storeSegments != null) {
			Record[] gridSegments = this.storeSegments.getRecords();
			for (Record record : gridSegments) {
				// On supprime les scenes sur la cartographie
				String segmentId = getSegmentId(record);

				Map<String, SegmentBean> segments = feasibilitySegments;
				if (segments != null) {
					SegmentBean feasibilitySegmentBean = segments.get(segmentId);
					if (feasibilitySegmentBean != null) {
						int idcartoObject = feasibilitySegmentBean.getIdCartography();
						if (idcartoObject > 0) {
							FacadeCartoWwd.delCarto3DObject(idcartoObject);
						}
					}
				} // if (segments != null) {
			} // for(Record record : gridSegments) {
		} // if (this.storeProgrammingSegments != null) {

		if (this.storeSegments != null) {
			this.storeSegments.removeAll();
		} // if

		if (feasibilitySegments != null) {
			feasibilitySegments.clear();
		} // if

		if (this.gridPanelSegments != null) {
			this.gridPanelSegments.clear();
		} // if

		if (this.showHideSegments != null) {
			this.showHideSegments.setText(spsConstants.displayAllMeshes());
		} // if (this.showHideSegments != null) {
	} // public void clearGridProgrammingSegments() {

	/**
	 * M&eacute;thode permettant la mise &agrave; jour de la HashMap des objets
	 * repr&eacute;sentant les segments d'une requ&ecirc;te SPS
	 * 'getFeasibility', 'Submit'
	 * 
	 * @param _feasibilitySegments
	 *            (Map<String, Sps2SegmentBean>) : feasibility Segments's
	 *            HashMap
	 */
	private void setContentFeasibilitySegments(Map<String, SegmentBean> _feasibilitySegments) {
		feasibilitySegments.clear();

		for (Entry<String, SegmentBean> hashMap : _feasibilitySegments.entrySet()) {
			String cle = hashMap.getKey();
			SegmentBean feasibilitySegmentBean = hashMap.getValue();

			feasibilitySegments.put(cle, feasibilitySegmentBean);
		}
	} //

	private Renderer renderSegment = new Renderer() {
		public String render(Object value, CellMetadata cellMetadata, Record record, int rowIndex, int colNum,
				Store store) {
			return Format.format("<b>{0}</b></br>", new String[] { (String) value, record.getAsString(SEGMENT_ID) });
		}
	}; // 

	private Renderer renderStatus = new Renderer() {
		public String render(Object value, CellMetadata cellMetadata, Record record, int rowIndex, int colNum,
				Store store) {
			String segmentStatus = record.getAsString(SEGMENT_STATUS);
			String colorHexa = Sps2ColorsConstants.DEFAULT_HEXA;
			if ((segmentStatus != null) && (!segmentStatus.equalsIgnoreCase(""))) {
				colorHexa = SpsClientUtils.getStatusHexaColor(segmentStatus);
			}
//			cellMetadata.setCssClass(colorCss);
			return Format.format(
					"<b style=\"color:" + colorHexa + ";\">{0}</b></br>",
					new String[] { (String) value, segmentStatus });
		}
	}; // 

	// ----------------------- Getters -----------------------
	/**
	 * return segmet identifier
	 * 
	 * @param _record
	 *            (Record) : the correponding record in the grid
	 * 
	 * @return int : qsegment identifier
	 */
	public String getSegmentId(Record _record) {
		String recordSegmentId = _record.getAsString(SEGMENT_ID);
		String segmentId = "";

		int pos = recordSegmentId.lastIndexOf("/");
		if (pos > 0) {
			segmentId = recordSegmentId.substring(pos + 1);
		} // if

		return segmentId;
	} // 

	/**
	 * getter on feasibilitySegments
	 * 
	 * @return Map<String, Sps2SegmentBean> : the programming segments
	 */
	public static Map<String, SegmentBean> getFeasibilitySegments() {
		return feasibilitySegments;
	} //

} // class
