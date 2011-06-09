package com.astrium.faceo.client.ui.programming.sps2.view;

import java.util.Date;
import java.util.Map.Entry;

import com.astrium.faceo.client.bean.programming.sps2.SensorBean;
import com.astrium.faceo.client.bean.programming.sps2.request.ProgrammingRequestBean;
import com.astrium.faceo.client.common.GeneralConstant;
import com.astrium.faceo.client.common.Utils;
import com.astrium.faceo.client.common.programming.sps2.Sps2Constants;
import com.astrium.faceo.client.common.programming.sps2.Sps2GeneralConstants;
import com.astrium.faceo.client.ui.MultiSensorsSettingsPanel;
import com.astrium.faceo.client.ui.cartography.cartoEarth.CartoAppletPanel;
import com.astrium.faceo.client.ui.cartography.cartoEarth.CartoPanel;
import com.astrium.faceo.client.ui.programming.sps2.SpsClientUtils;
import com.astrium.faceo.client.ui.programming.sps2.controller.SpsTaskOperationsManagment;
import com.astrium.faceo.client.ui.utils.FieldFormatPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Position;
import com.gwtext.client.data.SimpleStore;
import com.gwtext.client.data.Store;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.ToolTip;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.form.Checkbox;
import com.gwtext.client.widgets.form.ComboBox;
import com.gwtext.client.widgets.form.DateField;
import com.gwtext.client.widgets.form.Field;
import com.gwtext.client.widgets.form.FieldSet;
import com.gwtext.client.widgets.form.FormPanel;
import com.gwtext.client.widgets.form.MultiFieldPanel;
import com.gwtext.client.widgets.form.NumberField;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.form.event.CheckboxListenerAdapter;
import com.gwtext.client.widgets.form.event.FieldListenerAdapter;
import com.gwtext.client.widgets.layout.AnchorLayout;
import com.gwtext.client.widgets.layout.AnchorLayoutData;
import com.gwtext.client.widgets.layout.ColumnLayoutData;

/**
 * <B>SITE FACEO</B> <BR>
 * 
 * <P>
 * La classe SpsSettingsPanel g&egrave;re les widgets 
 * du panneau des param&egrave;tres des requ&ecirc;tes SPS 'GetFeasibility', 'Submit', 'Reserve', 'Update' and 'Cancel'
 * de la page d'accueil
 * </P>
 * </P>
 * 
 * @author GR
 * @version 1.0, le 17/05/2010
 */
public class SpsSettingsPanel extends Panel {

	// internationalisation
	private static final Sps2Constants programmingConstants =
		(Sps2Constants) com.google.gwt.core.client.GWT.create(Sps2Constants.class);

	// constates
	private final int PROGRAMMING_FORM_PANEL_WIDTH = GeneralConstant.WEST_PANEL_WIDTH - 5;

	private final int PROGRAMMING_FORM_PANEL_LABELS_WIDTH = 145;

	private final int PROGRAMMING_FORM_PANEL_HEIGHT = 415;

	// attributs

	// Task name
	private TextField nameField;

	// Area Of Interest (AOI)
	private NumberField leftUpperNumberField;

	private NumberField rightUpperNumberField;

	private NumberField leftLowerNumberField;

	private NumberField rightLowerNumberField;

	private FieldFormatPanel rightLowerFormatPanel;

	private Button drawAOIButton;

	private Checkbox displayAOICheckBox;

	private DateField startPeriodDateField;

	private DateField endPeriodDateField;

	// others parameters
	private MultiSensorsSettingsPanel multiSensorsSettingsPanel;

	private FieldSet fieldSetParameters;

	private FormPanel formParametersPanel;

	// SPS 2.0 operations : 'GetFeasibility', 'Submit', 'Reserve', 'Update' and 'Cancel'
	private ComboBox operations;

	private HorizontalPanel horizontalPanel;

	private Button submitBtn;

	private Panel formSubmitPanel;

	private Date dayDate;
	
	private Store storeOperations;

	// current task identifier
	private static String taskIdentifier = "";	

	/**
	 * constructor
	 * 
	 * @param _panelId (String) : HTML identifier on the page
	 */
	public SpsSettingsPanel(String _panelId) {

		super() ;

		this.setId(_panelId);
		this.setLayout(new AnchorLayout());

		this.setTitle(programmingConstants.settings());
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

		//add some fields  
		this.fieldSetParameters = new FieldSet(programmingConstants.requestParameters());
		this.fieldSetParameters.setId(_panelId + "_fieldSetParameters");
		this.fieldSetParameters.setLayout(new AnchorLayout());
		this.fieldSetParameters.setButtonAlign(Position.CENTER);
		this.fieldSetParameters.setLabelWidth(PROGRAMMING_FORM_PANEL_LABELS_WIDTH);

		// --------------------- Task name ---------------------
		this.nameField = new TextField(programmingConstants.taskName(), "Name", 300);
		this.nameField.setId(_panelId + "_nameField");
		this.nameField.setAllowBlank(false);
		this.nameField.setMaxLength(100);
		this.nameField.setFieldMsgTarget("qtip");
		this.nameField.setTitle(programmingConstants.taskName());
		this.nameField.setHideLabel(false);
		this.nameField.setEmptyText(programmingConstants.taskName());

		this.fieldSetParameters.add(this.nameField);

		// --------------------- AOI ---------------------
		// Area of Interest (AOI)
		FieldSet aoiFieldSet = new FieldSet(programmingConstants.aoi());  
		aoiFieldSet.setId(_panelId + "_aoiFieldSet");
		aoiFieldSet.setWidth(PROGRAMMING_FORM_PANEL_WIDTH - 35);
		aoiFieldSet.setAutoHeight(true);
		aoiFieldSet.setCollapsible(true);

		// Upper
		MultiFieldPanel aoiUpperMultiFieldPanel = new MultiFieldPanel();
		aoiUpperMultiFieldPanel.setId(_panelId + "_aoiUpperMultiFieldPanel");
		aoiUpperMultiFieldPanel.setMargins(0);
		aoiUpperMultiFieldPanel.setPaddings(0);

		this.leftUpperNumberField = new NumberField(programmingConstants.aoiUpper(), "AOI_Programming_left_upper", 70);
		Utils.setAOINumberField(
				this.leftUpperNumberField, "leftUpperNumberField_SPS2", GeneralConstant.AOI_LEFT_UPPER_NUMBER_FIELD, false, 3, -90, 90);
		aoiNumberFieldAddListener(this.leftUpperNumberField);
		aoiUpperMultiFieldPanel.addToRow(this.leftUpperNumberField, 225);

		this.rightUpperNumberField = new NumberField(programmingConstants.aoiRightUpper(), "AOI_Programming_right_upper", 70);
		Utils.setAOINumberField(
				this.rightUpperNumberField, "rightUpperNumberField_SPS2", GeneralConstant.AOI_RIGHT_UPPER_NUMBER_FIELD, true, 3, -180, 180);
		aoiNumberFieldAddListener(this.rightUpperNumberField);

		FieldFormatPanel rightUpperFormatPanel = new FieldFormatPanel(0, this.rightUpperNumberField, 70);
		aoiUpperMultiFieldPanel.addToRow(rightUpperFormatPanel, 70 + 0 + 10);
		//		aoiUpperMultiFieldPanel.addToRow(this.rightUpperNumberField, new ColumnLayoutData(0.5));

		// --------------------- Draw AOI Button ---------------------
		HTML htmlDrawAOI = new HTML();
		htmlDrawAOI.setHTML("&nbsp;&nbsp;&nbsp;");
		aoiUpperMultiFieldPanel.addToRow(htmlDrawAOI, 50);

		this.drawAOIButton = new Button(programmingConstants.drawAOI(), new ButtonListenerAdapter() {  
			public void onClick(Button button, EventObject e) {
				CartoPanel.drawAoiRectangle();
			} // onClick
		}); //

		// Tip attached to the button
		ToolTip toolTip = new ToolTip(programmingConstants.drawAOITip());  
		toolTip.applyTo(this.drawAOIButton);

		aoiUpperMultiFieldPanel.addToRow(this.drawAOIButton, 90);
		// --------------------------------------------------------

		aoiFieldSet.add(aoiUpperMultiFieldPanel);

		// Lower
		MultiFieldPanel aoiLowerMultiFieldPanel = new MultiFieldPanel();
		aoiLowerMultiFieldPanel.setId(_panelId + "_aoiLowerMultiFieldPanel");
		aoiLowerMultiFieldPanel.setMargins(0);
		aoiLowerMultiFieldPanel.setPaddings(0);

		this.leftLowerNumberField = new NumberField(programmingConstants.aoiLower(), "AOI_Programming_left_lower", 70);
		Utils.setAOINumberField(
				this.leftLowerNumberField, "leftLowerNumberField_SPS2", GeneralConstant.AOI_LEFT_LOWER_NUMBER_FIELD, false, 3, -90, 90);
		aoiNumberFieldAddListener(this.leftLowerNumberField);
		aoiLowerMultiFieldPanel.addToRow(this.leftLowerNumberField, 225);

		this.rightLowerNumberField = new NumberField(programmingConstants.aoiRightLower(), "AOI_Programming_right_lower", 70);
		Utils.setAOINumberField(
				this.rightLowerNumberField, "rightLowerNumberField_SPS2", GeneralConstant.AOI_RIGHT_LOWER_NUMBER_FIELD, true, 3, -180, 180);
		aoiNumberFieldAddListener(this.rightLowerNumberField);

		this.rightLowerFormatPanel = new FieldFormatPanel(0, this.rightLowerNumberField, 70);
		aoiLowerMultiFieldPanel.addToRow(this.rightLowerFormatPanel, 70 + 0 + 10);
		//		aoiLowerMultiFieldPanel.addToRow(this.rightLowerNumberField, new ColumnLayoutData(0.5));

		// --------------------- Display AOI CheckBox ---------------------
		//create display AOI Checkbox  
		this.displayAOICheckBox = 
			new Checkbox(("&nbsp;&nbsp;&nbsp;" + programmingConstants.display() + "&nbsp;" + programmingConstants.currentAOI()), 
					new CheckboxListenerAdapter() {
				public void onCheck(Checkbox _field, boolean _checked) {
					if (_checked) 
						CartoAppletPanel.displayAOIOnCarto(
								getLeftUpperNumberField(), getRightUpperNumberField(), 
								getLeftLowerNumberField(), getRightLowerNumberField());
					else 
						CartoPanel.unDisplayAoi();
				} // onCheck
			});

		this.displayAOICheckBox.setId(_panelId + "_displayAOICheckBox");
		this.displayAOICheckBox.setLabel(" ");
		this.displayAOICheckBox.setCls("faceo_checkBox");

		FieldFormatPanel displayAOIFormatPanel = 
			new FieldFormatPanel(10, this.displayAOICheckBox, 175);
		displayAOIFormatPanel.setId(_panelId + "_displayAOIFormatPanel");
		aoiLowerMultiFieldPanel.setCls("faceo_checkBox");
		aoiLowerMultiFieldPanel.addToRow(displayAOIFormatPanel, 10 + 175 + 30);

		aoiFieldSet.add(aoiLowerMultiFieldPanel);
		// --------------------------------------------------------

		this.fieldSetParameters.add(aoiFieldSet);
		// --------------------------------------------------------

		// --------------------- Period ---------------------
		FieldSet periodFieldSet = new FieldSet(programmingConstants.fieldSet());
		periodFieldSet.setId(_panelId + "_periodFieldSet");
		periodFieldSet.setWidth(PROGRAMMING_FORM_PANEL_WIDTH - 55);
		periodFieldSet.setCollapsible(true);

		MultiFieldPanel periodeMultiFieldPanel = new MultiFieldPanel();
		periodeMultiFieldPanel.setId(_panelId + "_periodMultiFieldPanel");

		this.startPeriodDateField = new DateField(programmingConstants.period(), "start", 100);
		this.startPeriodDateField.setId(_panelId + "_startPeriodDateField");
		this.startPeriodDateField.setAllowBlank(false);
		this.startPeriodDateField.setFormat("d/m/Y");
		this.dayDate = new Date();
		long dureeJournee = 24 * 60 * 60 * 1000;
		Date startDate = new Date((dayDate.getTime() + dureeJournee));
		this.startPeriodDateField.setValue(startDate);

		// Tip attached to the calendar's button
		ToolTip startPeriodDateFieldToolTip = new ToolTip(programmingConstants.startPeriodDateFieldTip());  
		startPeriodDateFieldToolTip.applyTo(this.startPeriodDateField); 

		periodeMultiFieldPanel.addToRow(this.startPeriodDateField, 260);

		this.endPeriodDateField = new DateField(programmingConstants.endPeriod(), "end", 100);
		this.endPeriodDateField.setId(_panelId + "_endPeriodDateField");
		this.endPeriodDateField.setAllowBlank(false);
		this.endPeriodDateField.setFormat("d/m/Y");

		Date endDate = new Date((this.dayDate.getTime() + (30 * dureeJournee)));
		this.endPeriodDateField.setValue(endDate);
		this.endPeriodDateField.setHideLabel(true);
		periodeMultiFieldPanel.addToRow(this.endPeriodDateField, new ColumnLayoutData(1));

		FieldFormatPanel endPeriodFormatPanel = new FieldFormatPanel(0, this.endPeriodDateField, 100);
		periodeMultiFieldPanel.addToRow(endPeriodFormatPanel, 100 + 0 + 10);

		// Tip attached to the calendar's button
		ToolTip endPeriodDateFieldToolTip = new ToolTip(programmingConstants.endPeriodDateFieldTip());  
		endPeriodDateFieldToolTip.applyTo(this.endPeriodDateField); 

		periodFieldSet.add(periodeMultiFieldPanel);
		this.fieldSetParameters.add(periodFieldSet);
		// --------------------------------------------------------

		// tasking parameters for each sensor
		FieldSet settingsFieldSet =  new FieldSet(programmingConstants.specificParameters());
		this.multiSensorsSettingsPanel = 
			new MultiSensorsSettingsPanel((_panelId + "_multiSensorsSettingsPanel"), this);
		settingsFieldSet.add(this.multiSensorsSettingsPanel);

		this.fieldSetParameters.add(settingsFieldSet);
		// --------------------------------------------------------

		this.formParametersPanel.add(this.fieldSetParameters, new AnchorLayoutData("100% -50"));

		// --------------------------------------------------------

		MultiFieldPanel parametersMultiFieldPanel = new MultiFieldPanel();
		parametersMultiFieldPanel.setLayout(new AnchorLayout());
		parametersMultiFieldPanel.add(this.formParametersPanel, new AnchorLayoutData("100% 100%"));
		//		this.add(this.formParametersPanel, new AnchorLayoutData("100% 100%"));

		this.add(parametersMultiFieldPanel, new AnchorLayoutData("100% -55"));

		this.formSubmitPanel = new Panel();
		this.formSubmitPanel.setFrame(true);
		this.formSubmitPanel.setHeaderAsText(false);
		this.formSubmitPanel.setButtonAlign(Position.CENTER);
		this.formSubmitPanel.setSize("100%", "50px");
		this.formSubmitPanel.setId(_panelId + "_formSubmitPanel");

		// Create a Horizontal Panel
		this.horizontalPanel = new HorizontalPanel();
		this.horizontalPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
		this.horizontalPanel.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
		//		this.horizontalPanel.setHeight("45px");
		//		this.horizontalPanel.setBorderWidth(10);

		// pop-up for SPS 2.0 operations : 'GetFeasibility', 'Submit', 'Reserve', 'Update' and 'Cancel'
		this.operations = new ComboBox();

		//create a Store using local array data for SPS 2.0 operations List
		this.storeOperations = new SimpleStore(
				new String[]{SpsClientUtils.LABEL}, SpsClientUtils.getDefaultOperations());
		this.storeOperations.load();

		SpsClientUtils.setComboBox(this.operations, (_panelId + "_operations"), 
				SpsClientUtils.LABEL, storeOperations, 140, "Operations", "", 
				Sps2GeneralConstants.SPS_GET_FEASIBILITY, false, true, true, false);

		this.horizontalPanel.add(this.operations);
		this.horizontalPanel.setCellWidth(this.horizontalPanel.getWidget(0), "250px");
		this.horizontalPanel.setCellHorizontalAlignment(this.horizontalPanel.getWidget(0), HorizontalPanel.ALIGN_CENTER);
		this.horizontalPanel.setCellVerticalAlignment(this.horizontalPanel.getWidget(0), VerticalPanel.ALIGN_MIDDLE);

		// --------------------- Request Submission Button ---------------------
		this.submitBtn = new Button(programmingConstants.submitRequest(), new ButtonListenerAdapter() {  
			public void onClick(Button button, EventObject e) { 

				if (multiSensorsSettingsPanel.getNbSensorsChecked() > 0) {
					for(Entry<String, Checkbox> checkBox : multiSensorsSettingsPanel.getSensorCheckBox().entrySet()) {
						Checkbox checked = checkBox.getValue();

						if (checked.getValue()) {
							SensorBean sensor = Sps2GeneralConstants.getSensors().get(checked.getId());

							if (sensor != null) {
								ProgrammingRequestBean programmingRequest = 
									setProgrammingRequest(sensor);

								if (programmingRequest.getTasking().getCoverageProgrammmingRequest()
										.getTimeOfInterest().getSurveyPeriod().getStartDate().getTime() < dayDate.getTime()) {
									com.google.gwt.user.client.Window.alert(programmingConstants.startPeriodMustBeInTheFuture());
								} else if (programmingRequest.getTasking().getCoverageProgrammmingRequest()
										.getTimeOfInterest().getSurveyPeriod().getEndDate().getTime() < dayDate.getTime()) {
									com.google.gwt.user.client.Window.alert(programmingConstants.endPeriodMustBeInTheFuture());
								} else if (programmingRequest.getTasking().getCoverageProgrammmingRequest()
										.getTimeOfInterest().getSurveyPeriod().getEndDate().getTime() 
										< programmingRequest.getTasking().getCoverageProgrammmingRequest().getTimeOfInterest().getSurveyPeriod().getStartDate().getTime()) {
									com.google.gwt.user.client.Window.alert(programmingConstants.badStartPeriod());
								} else if (programmingRequest.getName().length() < 1) {
									com.google.gwt.user.client.Window.alert(programmingConstants.taskNameIsMandatory());
								} else {
									// get the SPS operation
									if (operations.getValueAsString().equalsIgnoreCase(Sps2GeneralConstants.SPS_GET_FEASIBILITY)) {
										SpsTaskOperationsManagment.sendGetFeasibility(programmingRequest, formSubmitPanel);
									} else if (operations.getValueAsString().equalsIgnoreCase(Sps2GeneralConstants.SPS_SUBMIT)) {
										SpsTaskOperationsManagment.sendSubmit(programmingRequest, formSubmitPanel);
									} else if (operations.getValueAsString().equalsIgnoreCase(Sps2GeneralConstants.SPS_RESERVE)) {
										SpsTaskOperationsManagment.sendReserve(programmingRequest, formSubmitPanel);
									} else if (operations.getValueAsString().equalsIgnoreCase(Sps2GeneralConstants.SPS_CONFIRM)) {
										SpsTaskOperationsManagment.sendConfirm(programmingRequest, formSubmitPanel);
									} else if (operations.getValueAsString().equalsIgnoreCase(Sps2GeneralConstants.SPS_UPDATE)) {
										SpsTaskOperationsManagment.sendUpdate(programmingRequest, formSubmitPanel);
									} else if (operations.getValueAsString().equalsIgnoreCase(Sps2GeneralConstants.SPS_VALIDATE)) {
										SpsTaskOperationsManagment.sendValidate(programmingRequest, formSubmitPanel);
									} // if (operations.getValueAsString()
								} // if
							} // if (sensor != null) {
						} // if (checked.getValue()) {
					} // for(Entry<String, Checkbox> checkBox : multiSensorsSettingsPanel.getSensorCheckBox().entrySet()) {
				} else {
					com.google.gwt.user.client.Window.alert(programmingConstants.chooseAtLeastOneSensor());
				} // if (multiSensorsSettingsPanel.getNbSensorsChecked() > 0) {

			} // public void onClick(Button button, EventObject e) { 
		}); // this.submitBtn

		this.submitBtn.setId(_panelId + "_submitBtn");

		this.formSubmitPanel.addButton(this.submitBtn);

		// --------------------------------------------------------

		this.horizontalPanel.add(this.formSubmitPanel);

		this.add(this.horizontalPanel);

	} // public SpsSettingsPanel() {

	private void aoiNumberFieldAddListener(NumberField _aoiNumberField) {
		_aoiNumberField.addListener(new FieldListenerAdapter() {  
			public void onChange(Field field, java.lang.Object newVal, java.lang.Object oldVal) {
				if (field.isValid()) {
					CartoAppletPanel.displayAOIOnCarto(
							getLeftUpperNumberField(), getRightUpperNumberField(), 
							getLeftLowerNumberField(), getRightLowerNumberField());
				}
			}  // public void onChange(
		}); // _aoiNumberField.addListener(new FieldListenerAdapter() {  
	} // private static void aoiNumberFieldAddListener(NumberField _leftUpperNumberField) {
	
	/**
	 * set the Programming Request for SPS GetFeasibility, Submit, Update operation
	 * 
	 * @param _sensor (SensorBean) : the sensor to use for SPS operation
	 * 
	 * @return ProgrammingRequestBean : the request for SPS operation
	 
	 */
	private ProgrammingRequestBean setProgrammingRequest(SensorBean _sensor) {
		ProgrammingRequestBean request = new ProgrammingRequestBean();
		
		SetSpsRequest utils = new SetSpsRequest(this, _sensor);
		request = utils.setRequest();
		return request;
	} // private ProgrammingRequestBean setProgrammingRequest(SensorBean _sensor) {

	// ----------------------- Getters -----------------------

	/** 
	 * getter on leftUpperNumberField
	 * 
	 * @return NumberField : the Field for the Left Upper of Area of Interest
	 */
	public NumberField getLeftUpperNumberField() {
		return this.leftUpperNumberField;
	}

	/** 
	 * getter on rightUpperNumberField
	 * 
	 * @return NumberField : the Field for the Right Upper of Area of Interest
	 */
	public NumberField getRightUpperNumberField() {
		return this.rightUpperNumberField;
	}

	/** 
	 * getter on leftLowerNumberField
	 * 
	 * @return NumberField : the Field for the Left Lower of Area of Interest
	 */
	public NumberField getLeftLowerNumberField() {
		return this.leftLowerNumberField;
	}

	/** 
	 * getter on rightLowerNumberField
	 * 
	 * @return NumberField : the Field for the Right Lower of Area of Interest
	 */
	public NumberField getRightLowerNumberField() {
		return this.rightLowerNumberField;
	}

	/** 
	 * getter on endPeriodDateField
	 * 
	 * @return DateField : end Period DateField
	 */
	public DateField getEndPeriodDateField() {
		return this.endPeriodDateField;
	}

	/** 
	 * getter on startPeriodDateField
	 * 
	 * @return NumberField : start Period DateField
	 */
	public DateField getStartPeriodDateField() {
		return this.startPeriodDateField;
	}

	/** 
	 * getter on multiSensorsSettingsPanel
	 * 
	 * @return MultiSensorsSettingsPanel : the SPS multi sensors Settings Panel
	 */
	public MultiSensorsSettingsPanel getMultiSensorsSettingsPanel() {
		return  this.multiSensorsSettingsPanel;
	}

	/**
	 * getter on taskIdentifier
	 * 
	 * @return String : task Identifier
	 */
	public static String getTaskIdentifier() {
		return taskIdentifier;
	}

	/**
	 * getter on operations
	 * 
	 * @return ComboBox : operations list
	 */
	public ComboBox getOperations() {
		return this.operations;
	}

	/**
	 * getter on storeOperations
	 * 
	 * @return Store : store for all applicable Operations
	 */
	public Store getStoreOperations() {
		return this.storeOperations;
	}

	/**
	 * getter on nameField
	 * 
	 * @return String : name Field
	 */
	public TextField getNameField() {
		return nameField;
	}

	// ----------------------- Setters -----------------------

	/** 
	 * setter on nameField
	 * 
	 * @param _name (String) : Task name value
	 */
	public void setNameField(String _name) {
		this.nameField.setValue(_name);
	}

	/**
	 * setter on taskIdentifier
	 * 
	 * @param _taskIdentifier (String) : task Identifier value
	 */
	public static void setTaskIdentifier(String _taskIdentifier) {
		SpsSettingsPanel.taskIdentifier = _taskIdentifier;
	}
	
} // class
