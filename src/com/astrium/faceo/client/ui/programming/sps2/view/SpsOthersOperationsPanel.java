package com.astrium.faceo.client.ui.programming.sps2.view;

import java.util.Date;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import com.astrium.faceo.client.bean.programming.sps2.SensorBean;
import com.astrium.faceo.client.common.GeneralConstant;
import com.astrium.faceo.client.common.programming.sps2.Sps2Constants;
import com.astrium.faceo.client.common.programming.sps2.Sps2GeneralConstants;
import com.astrium.faceo.client.ui.configLoaders.ConfLoaderSpsSensorsSettingsPanels;
import com.astrium.faceo.client.ui.programming.sps2.controller.SpsSensorOperationsManagment;
import com.astrium.faceo.client.ui.programming.sps2.windows.WindowGetSensorAvailability;
import com.astrium.faceo.client.ui.programming.sps2.windows.WindowOtherOperations;
import com.astrium.faceo.client.ui.utils.FieldFormatPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Position;
import com.gwtext.client.data.SimpleStore;
import com.gwtext.client.data.Store;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.ToolTip;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.form.ComboBox;
import com.gwtext.client.widgets.form.DateField;
import com.gwtext.client.widgets.form.Field;
import com.gwtext.client.widgets.form.FieldSet;
import com.gwtext.client.widgets.form.FormPanel;
import com.gwtext.client.widgets.form.MultiFieldPanel;
import com.gwtext.client.widgets.form.event.ComboBoxListenerAdapter;
import com.gwtext.client.widgets.layout.AnchorLayout;
import com.gwtext.client.widgets.layout.AnchorLayoutData;
import com.gwtext.client.widgets.layout.ColumnLayoutData;

/**
 * <B>SITE FACEO</B> <BR>
 * 
 * <P>
 * La classe SpsSetOthersOperations allows user to invoke others SPS 2.0 operations for one sensor :
 * 		- GetCapabilities
 * 		- DescribeTasking
 * 		- DescribeSensor
 * 		- DescribeResultAccess
 * 		- GetSensorAvailability 
 * </P>
 * 
 * @author GR
 * @version 1.0, le 30/06/2010
 */
public class SpsOthersOperationsPanel extends Panel {

	// internationalisation
	private static final Sps2Constants programmingConstants =
		(Sps2Constants) com.google.gwt.core.client.GWT.create(Sps2Constants.class);

	// constates
	private final int PROGRAMMING_FORM_PANEL_WIDTH = GeneralConstant.WEST_PANEL_WIDTH - 5;

	private final int PROGRAMMING_FORM_PANEL_LABELS_WIDTH = 145;

	private final int PROGRAMMING_FORM_PANEL_HEIGHT = 415;

	// attributs

	// others parameters
	private FieldSet sensorsFieldSet;
	
	private FieldSet operationsFieldSet;

	private FieldSet otherOperationsFieldSet;

	private Button submitBtn;
	
	private VerticalPanel verticalPanel;
	
	private VerticalPanel sensorsVerticalPanel;

	private FormPanel formParametersPanel;

	private Panel formSubmitPanel;

	private Date dayDate;

	private FieldSet periodFiedSet;

	private DateField beginRequestPeriod;

	private DateField endRequestPeriod;

	private SortedMap<String, RadioButton> sensorRadioButtons = 
		new TreeMap<String, RadioButton>();
	
	// Instrument mode parameter
	private ComboBox operations;
	
	private ConfLoaderSpsSensorsSettingsPanels confLoaderPanels;
	
	private WindowOtherOperations windowResults;
	
	private WindowGetSensorAvailability windowGetSensorAvailability;

	/**
	 * constructor
	 * 
	 * @param _panelId (String) : HTML identifier on the page
	 */
	public SpsOthersOperationsPanel(String _panelId) {
		super() ;

		this.setId(_panelId);
		this.setLayout(new AnchorLayout());

		this.setTitle(programmingConstants.otherOperations());
		this.setBorder(false);
		this.setCollapsible(true);
		this.setCollapsed(false);
		this.setAutoScroll(true);
		this.setAnimCollapse(false);
		
		this.formParametersPanel = new FormPanel(Position.RIGHT);
		this.formParametersPanel.setId(_panelId + "_formParametersPanel");
		this.formParametersPanel.setLayout(new AnchorLayout());

		this.formParametersPanel.setAutoScroll(true);

		this.formParametersPanel.setFrame(true);
		this.formParametersPanel.setHeaderAsText(false);
		this.formParametersPanel.setLabelWidth(PROGRAMMING_FORM_PANEL_LABELS_WIDTH);
		this.formParametersPanel.setWidth(PROGRAMMING_FORM_PANEL_WIDTH);
		this.formParametersPanel.setHeight(PROGRAMMING_FORM_PANEL_HEIGHT);

		this.otherOperationsFieldSet = new FieldSet();
		this.otherOperationsFieldSet.setId(_panelId + "_otherOperationsFieldSet");
		this.otherOperationsFieldSet.setLayout(new AnchorLayout());
		this.otherOperationsFieldSet.setButtonAlign(Position.CENTER);
		this.otherOperationsFieldSet.setLabelWidth(PROGRAMMING_FORM_PANEL_LABELS_WIDTH);

		// tasking parameters for each sensor
		this.verticalPanel = new VerticalPanel();
		this.verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		this.verticalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		this.verticalPanel.setSize("100%", "100%");
		
		this.sensorsVerticalPanel = new VerticalPanel();
		this.sensorsVerticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		this.sensorsVerticalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		this.sensorsVerticalPanel.setSize("100%", "100%");

		this.sensorsFieldSet = new FieldSet(programmingConstants.sensorsSelection());
		this.sensorsFieldSet.setId(_panelId + "_sensorsFieldSet");
		this.sensorsFieldSet.setWidth(PROGRAMMING_FORM_PANEL_WIDTH - 55);
		this.sensorsFieldSet.setCollapsible(false);
		this.sensorsFieldSet.setAutoHeight(true);
		this.sensorsFieldSet.setHeader(false);
		
		// sensors : 'OPTICAL 1', 'OPTICAL 2', 'SAR 1', 'SAR 2' ...
		this.confLoaderPanels = ConfLoaderSpsSensorsSettingsPanels.getInstance();
		if (Sps2GeneralConstants.getSensors().size() ==0) {
			this.confLoaderPanels.setSpsSensors();
		} // if (Sps2GeneralConstants.getSensors().size() ==0) {
		this.confLoaderPanels.getSpsSensorsRadiosButtons(this.sensorRadioButtons);
		
		for(Entry<String, RadioButton> sensorsRadios : this.sensorRadioButtons.entrySet()) {
			final RadioButton radioButton = sensorsRadios.getValue();

			this.sensorsVerticalPanel.add(radioButton);
		} // for(Entry<String, Radio> sensorsRadios : this.sensorRadioButtons.entrySet()) {
		
		this.sensorsFieldSet.add(this.sensorsVerticalPanel);
		// --------------------------------------------------------
		
		this.verticalPanel.add(this.sensorsFieldSet);
		
		// define period : only for 'GetSensorAvailability' operation
		// --------------------- Period ---------------------
		this.periodFiedSet = new FieldSet(programmingConstants.periodGetSensorAvailability());
		this.periodFiedSet.setId(_panelId + "_periodFiedSet");
		this.periodFiedSet.setButtonAlign(Position.CENTER);
		this.periodFiedSet.setLabelWidth(PROGRAMMING_FORM_PANEL_LABELS_WIDTH);

		MultiFieldPanel requestPeriodMultiFieldPanel = new MultiFieldPanel();
		requestPeriodMultiFieldPanel.setId(_panelId + "_requestPeriodMultiFieldPanel");

		this.beginRequestPeriod = new DateField(programmingConstants.period(), "begin", 100);
		this.beginRequestPeriod.setId(_panelId + "_beginRequestPeriod");
		this.beginRequestPeriod.setAllowBlank(false);
		this.beginRequestPeriod.setFormat("d/m/Y");
		this.dayDate = new Date();
		long dureeJournee = 24 * 60 * 60 * 1000;
		Date startDate = new Date((dayDate.getTime() + dureeJournee));
		this.beginRequestPeriod.setValue(startDate);

		// Tip attached to the calendar's button
		ToolTip beginPeriodDateFieldToolTip = new ToolTip(programmingConstants.startPeriodDateFieldTip());  
		beginPeriodDateFieldToolTip.applyTo(this.beginRequestPeriod); 

		requestPeriodMultiFieldPanel.addToRow(this.beginRequestPeriod, 260);

		this.endRequestPeriod = new DateField(programmingConstants.endPeriod(), "end", 100);
		this.endRequestPeriod.setId(_panelId + "_endRequestPeriod");
		this.endRequestPeriod.setAllowBlank(false);
		this.endRequestPeriod.setFormat("d/m/Y");

		Date endDate = new Date((this.dayDate.getTime() + (30 * dureeJournee)));
		this.endRequestPeriod.setValue(endDate);
		this.endRequestPeriod.setHideLabel(true);
		requestPeriodMultiFieldPanel.addToRow(this.endRequestPeriod, new ColumnLayoutData(1));

		FieldFormatPanel endPeriodFormatPanel = new FieldFormatPanel(0, this.endRequestPeriod, 100);
		requestPeriodMultiFieldPanel.addToRow(endPeriodFormatPanel, 100 + 0 + 10);

		// Tip attached to the calendar's button
		ToolTip endPeriodDateFieldToolTip = new ToolTip(programmingConstants.endPeriodDateFieldTip());  
		endPeriodDateFieldToolTip.applyTo(this.endRequestPeriod); 

		this.periodFiedSet.add(requestPeriodMultiFieldPanel);
		this.periodFiedSet.disable();
		this.verticalPanel.add(this.periodFiedSet);
		// --------------------------------------------------------
		
		// list of operations
		this.operationsFieldSet = new FieldSet(programmingConstants.operationSelection());
		this.operationsFieldSet.setId(_panelId + "_operationsFieldSet");
		this.operationsFieldSet.setWidth(PROGRAMMING_FORM_PANEL_WIDTH - 55);
		this.operationsFieldSet.setCollapsible(false);
		this.operationsFieldSet.setAutoHeight(true);
		this.operationsFieldSet.setHeader(false);

		this.operations = new ComboBox();
		this.operations.setEditable(false);

		//create a Store using local array data for Instrument Mode List
		final Store storeOperations = new SimpleStore(
				new String[]{"op", "operations"}, getAllOperations());
		storeOperations.load();
		
		setComboBox(this.operations, (_panelId + "_operations"), 
				"operations", storeOperations, 140, "Operations", "", 
				storeOperations.getAt(0).getAsString("operations"), false, false, true);
		
		this.operationsFieldSet.add(this.operations);
		this.verticalPanel.add(this.operationsFieldSet);
		
		// --------------------- Operations ComboBox ---------------------
		this.operations.addListener( new ComboBoxListenerAdapter() {
			public void onChange(Field field, Object newVal, Object oldVal) {
				if (((String) newVal).equalsIgnoreCase(Sps2GeneralConstants.SPS_GET_SENSOR_AVAILABILITY)) {
					periodFiedSet.enable();
				} else {
					periodFiedSet.disable();
				}
			} // public void onChange
		});

		this.otherOperationsFieldSet.add(this.verticalPanel);		

		this.formParametersPanel.add(this.otherOperationsFieldSet, new AnchorLayoutData("100% -50"));

		// --------------------------------------------------------

		MultiFieldPanel submitMultiFieldPanel = new MultiFieldPanel();
		submitMultiFieldPanel.setLayout(new AnchorLayout());
		submitMultiFieldPanel.add(this.formParametersPanel, new AnchorLayoutData("100% 100%"));

		this.add(submitMultiFieldPanel, new AnchorLayoutData("100% -55"));

		this.formSubmitPanel = new Panel();
		this.formSubmitPanel.setFrame(true);
		this.formSubmitPanel.setHeaderAsText(false);
		this.formSubmitPanel.setButtonAlign(Position.CENTER);
		this.formSubmitPanel.setSize("100%", "50px");
		this.formSubmitPanel.setId(_panelId + "_formSubmitPanel");

		// --------------------- Request Submission Button ---------------------
		this.submitBtn = new Button(programmingConstants.submitRequest(), new ButtonListenerAdapter() {  
			public void onClick(Button button, EventObject e) { 
				for(Entry<String, RadioButton> radioButtons : sensorRadioButtons.entrySet()) {
					RadioButton radioButton = radioButtons.getValue();

					if (radioButton.getValue()) {
						String sensorId = confLoaderPanels.getRadioButtonSensorId(radioButton.getElement().getId());
						SensorBean sensor = Sps2GeneralConstants.getSensors().get(sensorId);
						if (sensor != null) {
							SpsSensorOperationsManagment.getSensorOperationResult(
									operations.getValue(),
									sensor.getUrn(),
									beginRequestPeriod,
									endRequestPeriod,
									formParametersPanel, 
									submitBtn, 
									programmingConstants.requestPending(),
									windowResults,
									windowGetSensorAvailability);
						} // if (sensor != null) {
					} // if (radioButton.getValue()) {
				} // for(Entry<String, RadioButton> radioButtons : sensorRadioButtons.entrySet()) {
			} // public void onClick(Button button, EventObject e) { 
		}); // this.submitBtn

		this.submitBtn.setId(_panelId + "submitBtn");

		this.formSubmitPanel.addButton(this.submitBtn);
		// --------------------------------------------------------

		this.add(this.formSubmitPanel);
		
		this.windowResults = new WindowOtherOperations(_panelId + "_windowResults");
		
		this.windowGetSensorAvailability = new WindowGetSensorAvailability(_panelId + "_windowGetSensorAvailability");

	} // public SpsOthersOperationsPanel() {

	/**
	 * set the map of the sps available operations 
	 * 
	 * @return String[][] : map of the sps available operations
	 */
	private String[][] getAllOperations() {

		 /** 		
		 * 		- GetCapabilities
		 * 		- DescribeTasking
		 * 		- DescribeSensor
		 * 		- DescribeResultAccess
		 * 		- GetSensorAvailability 
		 */
		 return new String[][]{
				new String[]{Sps2GeneralConstants.SPS_GET_CAPABILITIES, Sps2GeneralConstants.SPS_GET_CAPABILITIES},
				new String[]{Sps2GeneralConstants.SPS_DESCRIBE_TASKING, Sps2GeneralConstants.SPS_DESCRIBE_TASKING},
 				new String[]{Sps2GeneralConstants.SPS_DESCRIBE_SENSOR, Sps2GeneralConstants.SPS_DESCRIBE_SENSOR},
				new String[]{Sps2GeneralConstants.SPS_DESCRIBE_RESULT_ACCESS, Sps2GeneralConstants.SPS_DESCRIBE_RESULT_ACCESS},
 				new String[]{Sps2GeneralConstants.SPS_GET_SENSOR_AVAILABILITY, Sps2GeneralConstants.SPS_GET_SENSOR_AVAILABILITY}
		};

	} // private static String[] getAllOperations() {

	/**
	 * set the ComboBox for SPS 2.0 parameters
	 * 
	 * @param _comboBox (ComboBox)		: the ComboBox to define
	 * @param _id (String)				: ComboBox's id
	 * @param _displayField (String)	: the display field
	 * @param _store (Store)			: the Store for the values available for the comboBox
	 * @param _value (String)			: ComboBox's value
	 * @param _label (String)			: ComboBox's label
	 * @param _title (String)			: ComboBox's title
	 * @param _hideLabel (boolean)		: true to hide the ComboBox's label
	 * @param _allowBlank (boolean)		: true to allow blank for the ComboBox
	 * @param _forceSelection (boolean)	: true to force selection for the ComboBox
	 */
	private void setComboBox(ComboBox _comboBox, String _id, String _displayField,
			Store _store, int _width, String _label, String _title,
			String _value, boolean _hideLabel, boolean _allowBlank, boolean _forceSelection) {

		_comboBox.setId(_id);
		_comboBox.setForceSelection(_forceSelection);
		_comboBox.setAllowBlank(_allowBlank);
		_comboBox.setHideLabel(_hideLabel);
		_comboBox.setLabel(_label);
		_comboBox.setTitle(_title);
		_comboBox.setStore(_store);
		_comboBox.setDisplayField(_displayField);
		_comboBox.setMode(ComboBox.LOCAL);
		_comboBox.setTriggerAction(ComboBox.ALL);
		_comboBox.setEmptyText("  ");
		_comboBox.setLoadingText("Searching...");
		_comboBox.setTypeAhead(true);
		_comboBox.setSelectOnFocus(true);
		_comboBox.setWidth(_width);
		_comboBox.setValue(_value);

	} // private void setComboBox(ComboBox _comboBox

	// ----------------------- Getters -----------------------

	/** 
	 * getter on sensorRadioButtons
	 * 
	 * @return SortedMap<String, RadioButton> : sensors radios buttons
	 */
	public SortedMap<String, RadioButton> getSensorRadioButtons() {
		return  this.sensorRadioButtons;
	}
	
	/** 
	 * getter on windowResults
	 * 
	 * @return WindowOtherOperations : window to show results
	 */
	public WindowOtherOperations getWindowResults() {
		return this.windowResults;
	}

	/** 
	 * getter on windowGetSensorAvailability
	 * 
	 * @return WindowGetSensorAvailability : window to show results
	 */
	public WindowGetSensorAvailability getWindowGetSensorAvailability() {
		return windowGetSensorAvailability;
	}

} // class
