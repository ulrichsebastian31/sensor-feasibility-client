package com.astrium.faceo.client.ui.programming.sps2.view;

import com.astrium.faceo.client.bean.programming.sps2.SensorBean;
import com.astrium.faceo.client.bean.programming.sps2.request.TaskingParametersBean;
import com.astrium.faceo.client.common.GeneralConstant;
import com.astrium.faceo.client.common.programming.sps2.Sps2Constants;
import com.astrium.faceo.client.ui.programming.sps2.SpsSetTaskingParameters;
import com.astrium.faceo.client.ui.programming.sps2.SpsClientUtils;
import com.gwtext.client.data.SimpleStore;
import com.gwtext.client.data.Store;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.form.Checkbox;
import com.gwtext.client.widgets.form.ComboBox;
import com.gwtext.client.widgets.form.FieldSet;
import com.gwtext.client.widgets.form.MultiFieldPanel;
import com.gwtext.client.widgets.form.NumberField;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.layout.AnchorLayout;

/**
 * <B>SITE FACEO</B> <BR>
 * 
 * <P>
 * La classe SpsSensorsSettingsPanel g&egrave;re les widgets 
 * du panneau des param&egrave;tres de la requ&ecirc;te SPS getFeasibility
 * de la page d'accueil
 * </P>
 * </P>
 * 
 * @author GR
 * @version 1.0, le 26/05/2010
 */
public class SpsSensorSettingsPanel extends Panel {

	// internationalisation
	private static final Sps2Constants programmingConstants =
		(Sps2Constants) com.google.gwt.core.client.GWT.create(Sps2Constants.class);

	// constates
	private final int PROGRAMMING_FORM_PANEL_WIDTH = GeneralConstant.WEST_PANEL_WIDTH - 5;

	// attributs
	// Using a TextResource XML file to describe Tasking parameters 
	// for IHM
	private TaskingParametersBean taskingParameters = new TaskingParametersBean();

	// Task identifier
	private TextField idField;
	
	// Task operation
	private String taskOperation = "";	

	// -------------- Quality Of Service parameters --------------
	private ComboBox priorityLevel;

	// -------------- Validation parameters --------------

	// -------------- Validation SAR parameters --------------
	// Max Noise Level
	private NumberField maxNoiseLevel;

	// Max Ambiguity Level
	private NumberField maxAmbiguityLevel;

	// -------------- Validation OPT parameters --------------
	// Max Cloud Coverage
	private NumberField maxCloudCoverage;

	// Max Snow Coverage
	private NumberField maxSnowCoverage;

	// Haze accepted
	private Checkbox hazeAccepted;

	// Sand Wind accepted
	private Checkbox sandWindAccepted;

	// -------------- Acquisition parameters --------------

	// Fusion accepted
	private Checkbox fusionAccepted;

	// Ground resolution parameters

	private NumberField groundResolutionMin;

	private NumberField groundResolutionMax;

	// Lim Luminosity parameter (OPT only))
	private NumberField minLuminosity;

	// Instrument mode parameter
	private ComboBox acquisitionInstrumentMode;

	// Polarization mode parameter (SAR only)
	private ComboBox acquisitionPolarizationMode;

	// Coverage Type
	private ComboBox coverageType;

	// Acquisition Incidence Angles

	private NumberField azimuthIncidenceAngleMin;

	private NumberField azimuthIncidenceAngleMax;

	private NumberField elevationIncidenceAngleMin;

	private NumberField elevationIncidenceAngleMax;

	// Acquisition Pointing Angles

	private NumberField alongTrackAngleMin;

	private NumberField alongTrackAngleMax;

	private NumberField acrossTrackAngleMin;

	private NumberField acrossTrackAngleMax;

	/**
	 * constructor
	 * 
	 * @param _sensor (SensorBean) : sensor informations
	 */
	public SpsSensorSettingsPanel(SensorBean _sensor) {

		super() ;

		String panelId = _sensor.getPanelId();
		
		this.setId(panelId);
		this.setLayout(new AnchorLayout());

		this.setTitle(_sensor.getId());
		this.setBorder(false);
		this.setCollapsible(false);
		this.setCollapsed(false);
		this.setAutoScroll(true);
		this.setAnimCollapse(false);

		this.taskingParameters = SpsSetTaskingParameters.getTaskingParameters(_sensor);

		// --------------------- Task identifier ---------------------
		FieldSet idFieldSet = new FieldSet(programmingConstants.taskIdentifier());
		idFieldSet.setId(_sensor.getPanelId() + "_idFieldSet");
		idFieldSet.setWidth(PROGRAMMING_FORM_PANEL_WIDTH - 55);
		idFieldSet.setCollapsible(true);
		idFieldSet.setAutoHeight(true);
		idFieldSet.setHeader(false);

		this.idField = new TextField(programmingConstants.taskIdentifier(), "Id", 400);
		this.idField.setId(_sensor.getPanelId() + "_idField");
		this.idField.setAllowBlank(true);
		this.idField.setDisabled(true);
		this.idField.setMaxLength(100);
		this.idField.setTitle("");
		this.idField.setHideLabel(true);
		this.idField.setEmptyText("");

		idFieldSet.add(this.idField);
		this.add(idFieldSet);
		
		// --------------------- Quality Of Service parameters ---------------------
		if (this.taskingParameters.getQualityOfService()) {
			FieldSet qosFieldSet = new FieldSet(programmingConstants.qosParameters());
			qosFieldSet.setId(_sensor.getPanelId() + "_qosFieldSet");
			qosFieldSet.setWidth(PROGRAMMING_FORM_PANEL_WIDTH - 55);
			qosFieldSet.setCollapsible(true);
			qosFieldSet.setAutoHeight(true);
			qosFieldSet.setHeader(false);

			if (this.taskingParameters.getPriorityLevel()) {
				// pop-up for Priority Level
				this.priorityLevel = new ComboBox();

				//create a Store using local array data for Priority Level List
				final Store storePriorityLevel = new SimpleStore(
						new String[]{"label"}, getAllPriorityLevel());
				storePriorityLevel.load();

				SpsClientUtils.setComboBox(this.priorityLevel, (panelId + "_priorityLevel"), 
						"label", storePriorityLevel, 140, "Priority Level", "", 
						this.taskingParameters.getPriorityLevelDefaultValue(), false, true, true, false);

				qosFieldSet.add(this.priorityLevel);
			} // if (this.taskingParameters.getPriorityLevel()) {

			this.add(qosFieldSet);
		} // if (this.taskingParameters.getQualityOfService()) {

		// --------------------- Validation parameters ---------------------

		// -------------- Validation SAR parameters --------------
		if (this.taskingParameters.getValidationParametersSAR()) {
			FieldSet validationFieldSet = new FieldSet(programmingConstants.validationParameters());
			validationFieldSet.setId(panelId + "_validationFieldSet");
			validationFieldSet.setWidth(PROGRAMMING_FORM_PANEL_WIDTH - 55);
			validationFieldSet.setCollapsible(true);
			validationFieldSet.setAutoHeight(true);
			validationFieldSet.setHeader(false);

			// --------------------- Max Noise Level ---------------------
			if (this.taskingParameters.getMaxNoiseLevel()) {
				this.maxNoiseLevel = new NumberField(programmingConstants.maxNoiseLevel(), "maxNoiseLevel", 70);
				SpsClientUtils.setNumberField(this.maxNoiseLevel, (panelId + "_maxNoiseLevel"),
						this.taskingParameters.getMaxNoiseLevelValue(), 0, 100, 0, 
						false, false);

				validationFieldSet.add(this.maxNoiseLevel);
			} // if (this.taskingParameters.getMaxNoiseLevel()) {

			// --------------------- Max Ambiguity Level ---------------------
			if (this.taskingParameters.getMaxAmbiguityLevel()) {
				this.maxAmbiguityLevel = new NumberField(programmingConstants.maxAmbiguityLevel(), "maxAmbiguityLevel", 70);
				SpsClientUtils.setNumberField(this.maxAmbiguityLevel, (panelId + "_maxAmbiguityLevel"),
						this.taskingParameters.getMaxAmbiguityLevelValue(), 0, 100, 0, 
						false, false);

				validationFieldSet.add(this.maxAmbiguityLevel);
			} // if (this.taskingParameters.getMaxAmbiguityLevel()) {

			this.add(validationFieldSet);

		} // if (this.taskingParameters.getValidationParametersSAR()) {

		// --------------------- Validation OPT parameters ---------------------
		if (this.taskingParameters.getValidationParametersOPT()) {
			FieldSet validationFieldSet = new FieldSet(programmingConstants.validationParameters());
			validationFieldSet.setId(panelId + "_validationFieldSet");
			validationFieldSet.setWidth(PROGRAMMING_FORM_PANEL_WIDTH - 55);
			validationFieldSet.setCollapsible(true);
			validationFieldSet.setAutoHeight(true);
			validationFieldSet.setHeader(false);

			// --------------------- Max Cloud Coverage ---------------------
			if (this.taskingParameters.getMaxCloudCoverage()) {
				this.maxCloudCoverage = new NumberField(programmingConstants.maxCloudCoverage(), "maxCloudCoverage", 70);
				SpsClientUtils.setNumberField(this.maxCloudCoverage, (panelId + "_maxCloudCoverage"),
						this.taskingParameters.getMaxCloudCoverageValue(), 0, 100, 0, 
						false, false);

				validationFieldSet.add(this.maxCloudCoverage);
			} // if (this.taskingParameters.getMaxCloudCoverage()) {

			// --------------------- Max Snow Coverage ---------------------
			if (this.taskingParameters.getMaxSnowCoverage()) {
				this.maxSnowCoverage = new NumberField(programmingConstants.maxSnowCoverage(), "maxSnowCoverage", 70);
				SpsClientUtils.setNumberField(this.maxSnowCoverage, (panelId + "_maxSnowCoverage"),
						this.taskingParameters.getMaxSnowCoverageValue(), 0, 100, 0, 
						false, false);

				validationFieldSet.add(this.maxSnowCoverage);
			} // if (this.taskingParameters.getMaxSnowCoverage()) {

			// --------------------- Haze Accepted ---------------------
			if (this.taskingParameters.getHazeAccepted()) {
				this.hazeAccepted = new Checkbox(programmingConstants.hazeAccepted(), "hazeAccepted");
				this.hazeAccepted.setId(panelId + "_hazeAccepted");

				validationFieldSet.add(this.hazeAccepted);
			} // if (this.taskingParameters.getHazeAccepted()) {

			// --------------------- Sand Width Accepted ---------------------
			if (this.taskingParameters.getSandWindAccepted()) {
				this.sandWindAccepted = new Checkbox(programmingConstants.sandWindAccepted(), "getSandWindAccepted");
				this.sandWindAccepted.setId(panelId + "_getSandWindAccepted");

				validationFieldSet.add(this.sandWindAccepted);
			} // if (this.taskingParameters.getSandWindAccepted()) {			

			this.add(validationFieldSet);

		} // if (this.taskingParameters.getValidationParametersOPT()) {

		// --------------------- Acquisition parameters ---------------------
		if (this.taskingParameters.getAcquisitionType()) {
			FieldSet acquisitionFieldSet = new FieldSet(programmingConstants.acquisitionParameters());
			acquisitionFieldSet.setId(panelId + "_acquisitionFieldSet");
			acquisitionFieldSet.setWidth(PROGRAMMING_FORM_PANEL_WIDTH - 55);
			acquisitionFieldSet.setCollapsible(true);
			acquisitionFieldSet.setCollapsed(false);
			acquisitionFieldSet.setAutoHeight(true);

			if (this.taskingParameters.getAcquisitionRadarType() || (this.taskingParameters.getAcquisitionOpticalType())) {

				// --------------------- Fusion Accepted ---------------------
				if (this.taskingParameters.getFusionAccepted()) {
					this.fusionAccepted = new Checkbox(programmingConstants.fusionAccepted(), "fusionAccepted");
					this.fusionAccepted.setId(panelId + "_fusionAccepted");

					acquisitionFieldSet.add(this.fusionAccepted);
				} // if (this.taskingParameters.getFusionAccepted()) {

				// --------------------- Acquisition Instrument Mode ---------------------
				if (this.taskingParameters.getInstrumentMode()) {
					// pop-up for Acquisition Instrument Mode
					this.acquisitionInstrumentMode = new ComboBox();

					//create a Store using local array data for Instrument Mode List
					final Store storeAcquisitionInstrumentMode = new SimpleStore(
							new String[]{"label"}, getAllAcquisitionInstrumentMode());
					storeAcquisitionInstrumentMode.load();

					SpsClientUtils.setComboBox(this.acquisitionInstrumentMode, (panelId + "_acquisitionInstrumentMode"), 
							"label", storeAcquisitionInstrumentMode, 140, "Instrument Mode", "", 
							this.taskingParameters.getInstrumentModeDefaultValue(), false, true, true, false);

					acquisitionFieldSet.add(this.acquisitionInstrumentMode);
				} // if (this.taskingParameters.getInstrumentMode()) {

				// --------------------- Ground resolution ---------------------
				if (this.taskingParameters.getGroundResolution()) {
					MultiFieldPanel groundResolutionMultiFieldPanel = new MultiFieldPanel();

					this.groundResolutionMin = new NumberField(programmingConstants.groundResolution(), "groundResolutionMin", 75);
					// String valMin = Double.toString(this.taskingParameters.getGroundResolutionMin());
					double valMinLong = Math.abs(this.taskingParameters.getGroundResolutionMin());
					long valMaxLong = Math.round(this.taskingParameters.getGroundResolutionMax());
					int valMin = Double.valueOf(valMinLong).intValue();
					int valMax = Long.valueOf(valMaxLong).intValue();

					SpsClientUtils.setNumberField(this.groundResolutionMin, (panelId + "_groundResolutionMin"),
							this.taskingParameters.getGroundResolutionMin(), valMin, valMax, 2, 
							false, true);
					groundResolutionMultiFieldPanel.addToRow(this.groundResolutionMin, 240);

					this.groundResolutionMax = new NumberField("", "groundResolutionMax", 75);
					SpsClientUtils.setNumberField(this.groundResolutionMax, (panelId + "_groundResolutionMax"),
							this.taskingParameters.getGroundResolutionMax(), valMin, valMax, 2, 
							true, true);
					groundResolutionMultiFieldPanel.addToRow(this.groundResolutionMax, 240);

					acquisitionFieldSet.add(groundResolutionMultiFieldPanel);
				} // if (this.taskingParameters.getGroundResolution()) {

				// --------------------- Acquisition Instrument Mode (SAR only) ---------------------
				if (this.taskingParameters.getPolarizationMode()) {
					// pop-up for Acquisition Polarization Mode
					this.acquisitionPolarizationMode = new ComboBox();

					//create a Store using local array data for Polarization Mode List
					final Store storeAcquisitionPolarizationMode = new SimpleStore(
							new String[]{"label"}, getAllAcquisitionPolarizationMode());
					storeAcquisitionPolarizationMode.load();

					SpsClientUtils.setComboBox(this.acquisitionPolarizationMode, (panelId + "_acquisitionPolarizationMode"), 
							"label", storeAcquisitionPolarizationMode, 140, "Polarization Mode", "", 
							this.taskingParameters.getPolarizationModeDefaultValue(), false, true, true, false);

					acquisitionFieldSet.add(this.acquisitionPolarizationMode);
				} // if (this.taskingParameters.getPolarizationMode()) {

				// --------------------- Min Luminosity (SAR Only) ---------------------
				if (this.taskingParameters.getMinLuminosity()) {
					this.minLuminosity = new NumberField(programmingConstants.minLuminosity(), "minLuminosity", 75);

					SpsClientUtils.setNumberField(this.minLuminosity, (panelId + "_minLuminosity"),
							this.taskingParameters.getMinLuminosityValue(), 0, 100, 0, 
							false, true);

					acquisitionFieldSet.add(this.minLuminosity);
				} // if (this.taskingParameters.getMinLuminosity()) {

			} // if (this.taskingParameters.getAcquisitionRadarType() || (this.taskingParameters.getAcquisitionOpticalType())) {

			// --------------------- Coverage Type ---------------------
			if (this.taskingParameters.getCoverageType()) {
				// pop-up for Acquisition Coverage Type
				this.coverageType = new ComboBox();

				//create a Store using local array data for Coverage Type List
				final Store storeAcquisitionCoverageType = new SimpleStore(
						new String[]{"label"}, getAllAcquisitionCoverageType());
				storeAcquisitionCoverageType.load();

				SpsClientUtils.setComboBox(this.coverageType, (panelId + "_coverageType"), 
						"label", storeAcquisitionCoverageType, 140, "Coverage Type", "", 
						this.taskingParameters.getCoverageTypeDefaultValue(), false, true, true, false);

				acquisitionFieldSet.add(this.coverageType);
			} // if (this.taskingParameters.Coverage Type()) {

			// --------------------- Acquisition Incidence Angles ---------------------
			if ( (this.taskingParameters.getElevation()) || (this.taskingParameters.getAzimuth()) ) {
				FieldSet incidenceAngleFieldSet = new FieldSet(programmingConstants.incidenceAngle());
				incidenceAngleFieldSet.setId(panelId + "_incidenceAngleFieldSet");
				incidenceAngleFieldSet.setWidth(PROGRAMMING_FORM_PANEL_WIDTH - 85);
				incidenceAngleFieldSet.setAutoHeight(true);
				incidenceAngleFieldSet.setCollapsible(true);
				incidenceAngleFieldSet.setCollapsed(false);

				// --------------------- Elevation Incidence Angle ---------------------
				if (this.taskingParameters.getElevation()) {
					MultiFieldPanel elevationIncidenceMultiFieldPanel = new MultiFieldPanel();

					this.elevationIncidenceAngleMin = 
						new NumberField(programmingConstants.elevationIncidenceAngle(), "elevationIncidenceAngleMin", 75);
					this.elevationIncidenceAngleMin.setMinValue(this.taskingParameters.getElevationIncidenceMin());
					SpsClientUtils.setNumberField(this.elevationIncidenceAngleMin, (panelId + "_elevationIncidenceMin"), 
							this.taskingParameters.getElevationIncidenceMin(), this.taskingParameters.getElevationIncidenceMin(),this.taskingParameters.getElevationIncidenceMax(), 2, 
							false, true);
					elevationIncidenceMultiFieldPanel.addToRow(this.elevationIncidenceAngleMin, 240);

					this.elevationIncidenceAngleMax = 
						new NumberField("", "elevationIncidenceAngleMax", 75);
					this.elevationIncidenceAngleMax.setMaxValue(this.taskingParameters.getElevationIncidenceMax());
					SpsClientUtils.setNumberField(this.elevationIncidenceAngleMax, (panelId + "_elevationIncidenceMax"), 
							this.taskingParameters.getElevationIncidenceMax(), this.taskingParameters.getElevationIncidenceMin(),this.taskingParameters.getElevationIncidenceMax(), 2,  
							true, true);
					elevationIncidenceMultiFieldPanel.addToRow(this.elevationIncidenceAngleMax, 240);

					incidenceAngleFieldSet.add(elevationIncidenceMultiFieldPanel);
				} // if (this.taskingParameters.getElevation()) {

				// --------------------- Azimuth Incidence Angle ---------------------
				if (this.taskingParameters.getAzimuth()) {
					MultiFieldPanel azimuthIncidenceMultiFieldPanel = new MultiFieldPanel();

					this.azimuthIncidenceAngleMin = new NumberField(programmingConstants.azimuthIncidenceAngle(), "azimuthIncidenceAngleMin", 75);
					SpsClientUtils.setNumberField(this.azimuthIncidenceAngleMin, (panelId + "_azimuthIncidenceMin"),
							this.taskingParameters.getAzimuthIncidenceMin(), this.taskingParameters.getAzimuthIncidenceMin(),this.taskingParameters.getAzimuthIncidenceMax(), 2, 
							false, true);
					azimuthIncidenceMultiFieldPanel.addToRow(this.azimuthIncidenceAngleMin, 240);

					this.azimuthIncidenceAngleMax = new NumberField("", "azimuthIncidenceAngleMax", 75);
					SpsClientUtils.setNumberField(this.azimuthIncidenceAngleMax, (panelId + "_azimuthIncidenceMax"),
							this.taskingParameters.getAzimuthIncidenceMax(), this.taskingParameters.getAzimuthIncidenceMin(),this.taskingParameters.getAzimuthIncidenceMax(), 2, 
							true, true);
					azimuthIncidenceMultiFieldPanel.addToRow(this.azimuthIncidenceAngleMax, 240);

					incidenceAngleFieldSet.add(azimuthIncidenceMultiFieldPanel);
				} // if (this.taskingParameters.getAzimuth()) {
				
				acquisitionFieldSet.add(incidenceAngleFieldSet);
			} // if ( (this.taskingParameters.getElevation()) || (this.taskingParameters.getAzimuth()) ) {

			// Acquisition Pointing Angles
			if ( (this.taskingParameters.getAlongTrack()) || (this.taskingParameters.getAcrossTrack()) ) {
				FieldSet pointingAngleFieldSet = new FieldSet(programmingConstants.pointingAngle());
				pointingAngleFieldSet.setId(panelId + "_pointingAngleFieldSet");
				pointingAngleFieldSet.setWidth(PROGRAMMING_FORM_PANEL_WIDTH - 85);
				pointingAngleFieldSet.setAutoHeight(true);
				pointingAngleFieldSet.setCollapsible(true);
				pointingAngleFieldSet.setCollapsed(false);

				// --------------------- Along Track Angle ---------------------
				if (this.taskingParameters.getAlongTrack()) {
					MultiFieldPanel alongTrackMultiFieldPanel = new MultiFieldPanel();

					this.alongTrackAngleMin = new NumberField(programmingConstants.alongTrack(), "alongTrackAngleMin", 75);
					SpsClientUtils.setNumberField(this.alongTrackAngleMin, (panelId + "_alongTrackAngleMin"),
							this.taskingParameters.getAlongTrackMin(), this.taskingParameters.getAlongTrackMin(),this.taskingParameters.getAlongTrackMax(), 2, 
							false, true);
					alongTrackMultiFieldPanel.addToRow(this.alongTrackAngleMin, 240);

					this.alongTrackAngleMax = new NumberField("", "alongTrackAngleMax", 75);
					SpsClientUtils.setNumberField(this.alongTrackAngleMax, (panelId + "_alongTrackAngleMax"),
							this.taskingParameters.getAlongTrackMax(), this.taskingParameters.getAlongTrackMin(),this.taskingParameters.getAlongTrackMax(), 2, 
							true, true);
					alongTrackMultiFieldPanel.addToRow(this.alongTrackAngleMax, 240);

					pointingAngleFieldSet.add(alongTrackMultiFieldPanel);
				} // if (this.taskingParameters.getAlongTrack()) {

				// --------------------- Across Track Angle ---------------------
				if (this.taskingParameters.getAcrossTrack()) {
					MultiFieldPanel acrossTrackMultiFieldPanel = new MultiFieldPanel();

					this.acrossTrackAngleMin = new NumberField(programmingConstants.alongTrack(), "acrossTrackAngleMin", 75);
					SpsClientUtils.setNumberField(this.alongTrackAngleMin, (panelId + "_acrossTrackAngleMin"),
							this.taskingParameters.getAcrossTrackMin(), this.taskingParameters.getAcrossTrackMin(),this.taskingParameters.getAcrossTrackMax(), 2, 
							false, true);
					acrossTrackMultiFieldPanel.addToRow(this.acrossTrackAngleMin, 240);

					this.acrossTrackAngleMax = new NumberField("", "acrossTrackAngleMax", 75);
					SpsClientUtils.setNumberField(this.acrossTrackAngleMax, (panelId + "_acrossTrackAngleMax"),
							this.taskingParameters.getAcrossTrackMax(), this.taskingParameters.getAcrossTrackMin(),this.taskingParameters.getAcrossTrackMax(), 2, 
							true, true);
					acrossTrackMultiFieldPanel.addToRow(this.acrossTrackAngleMax, 240);

					pointingAngleFieldSet.add(acrossTrackMultiFieldPanel);
				} // if (this.taskingParameters.getAcrossTrack()) {

				acquisitionFieldSet.add(pointingAngleFieldSet);
			} // if ( (this.taskingParameters.getAlongTrack()) || (this.taskingParameters.getAcrossTrack()) ) {

			this.add(acquisitionFieldSet);
		} // if (this.taskingParameters.getAcquisitionType()) {
		// --------------------------------------------------------	
		
		// panel disabled by default : use checkbox sensor for activate panel
		this.disable();

	} // public SpsSensorSettingsPanel() {

	private String[][] getAllAcquisitionInstrumentMode() {

		String[][] values = null;
		if (this.taskingParameters.getInstrumentModeValues() != null) {
			values = new String [this.taskingParameters.getInstrumentModeValues().length][1];

			for (int i=0; i < this.taskingParameters.getInstrumentModeValues().length; i++) {
				values[i] = new String[]{this.taskingParameters.getInstrumentModeValues()[i]};
			} // for (int i=0; i < this.taskingParameters.getInstrumentModeValues().length; i++) {

		} else {
			values = new String [1][1];
			values[0] = new String[]{"No mode"};
		} // if (this.taskingParameters.getInstrumentModeValues != null) {

		
		return values;

	} // private static String[] getAllAcquisitionInstrumentMode() {

	private String[][] getAllAcquisitionPolarizationMode() {

		String[][] values = null;
		if (this.taskingParameters.getPolarizationModeValues() != null) {
			values = new String [this.taskingParameters.getPolarizationModeValues().length][1];

			for (int i=0; i < this.taskingParameters.getPolarizationModeValues().length; i++) {
				values[i] = new String[]{this.taskingParameters.getPolarizationModeValues()[i]};
			} // for (int i=0; i < this.taskingParameters.getPolarizationModeValues().length; i++) {
		} else {
			values = new String [1][1];
			values[0] = new String[]{"No mode"};
		} // if (this.taskingParameters.getPolarizationModeValues != null) {

		return values;

	} // private static String[] getAllAcquisitionPolarizationMode() {

	private String[][] getAllAcquisitionCoverageType() {
		
		String[][] values = null;
		if (this.taskingParameters.getCoverageTypeValues() != null) {
			values = new String [this.taskingParameters.getCoverageTypeValues().length][1];

			for (int i=0; i < this.taskingParameters.getCoverageTypeValues().length; i++) {
				values[i] = new String[]{this.taskingParameters.getCoverageTypeValues()[i]};
			} // for (int i=0; i < this.taskingParameters.getCoverageTypeValues().length; i++) {
		} else {
			values = new String [1][1];
			values[0] = new String[]{"No type"};
		} // if (this.taskingParameters.getCoverageTypeValues != null) {

		return values;

	} // private static String[] getAllAcquisitionCoverageType() {

	private String[][] getAllPriorityLevel() {
		
		String[][] values = null;
		if (this.taskingParameters.getPriorityLevelValues() != null) {
			values = new String [this.taskingParameters.getPriorityLevelValues().length][1];

			for (int i=0; i < this.taskingParameters.getPriorityLevelValues().length; i++) {
				values[i] = new String[]{this.taskingParameters.getPriorityLevelValues()[i]};
			} // for (int i=0; i < this.taskingParameters.getPriorityLevelValues().length; i++) {
		} else {
			values = new String [1][1];
			values[0] = new String[]{"No level"};
		} // if (this.taskingParameters.getPriorityLevelValues() != null) {

		return values;

	} // private static String[] getAllPriorityLevel() {

	// ---------------------- Getters -------------------------

	/** 
	 * getter on taskingParameters
	 * 
	 * @return TaskingParametersBean : tasking Parameters configuration
	 */
	public TaskingParametersBean getTaskingParameters() {
		return this.taskingParameters;
	}

	// ---------------------- Task identifier -------------------------
	/** 
	 * getter on idField
	 * 
	 * @return TextField : the Field for the task identifier
	 */
	public TextField getIdField() {
		return this.idField;
	}

	// ---------------------- Task operation -------------------------
	/** 
	 * getter on taskOperation
	 * 
	 * @return taskOperation : the task operation
	 */
	public String getTaskOperation() {
		return this.taskOperation;
	}

	// ---------------------- QOS Parameters -------------------------
	
	/** 
	 * getter on priorityLevel
	 * 
	 * @return ComboBox : priority Level
	 */
	public ComboBox getPriorityLevel() {
		return this.priorityLevel;
	}

	// ---------------------- Validation Parameters -------------------------
	
	/** 
	 * getter on maxCloudCoverage
	 * 
	 * @return NumberField : max Cloud Coverage Field
	 */
	public NumberField getMaxCloudCoverage() {
		return this.maxCloudCoverage;
	}

	/** 
	 * getter on maxSnowCoverage
	 * 
	 * @return NumberField : max Snow Coverage Field
	 */
	public NumberField getMaxSnowCoverage() {
		return this.maxSnowCoverage;
	}

	/** 
	 * getter on hazeAccepted
	 * 
	 * @return Checkbox : haze Accepted Checkbox
	 */
	public Checkbox getHazeAccepted() {
		return this.hazeAccepted;
	}

	/** 
	 * getter on sandWindAccepted
	 * 
	 * @return Checkbox : sand Wind Accepted Checkbox
	 */
	public Checkbox getSandWindAccepted() {
		return this.sandWindAccepted;
	}
	
	/** 
	 * getter on maxNoiseLevel
	 * 
	 * @return NumberField : max Noise Level Field
	 */
	public NumberField getMaxNoiseLevel() {
		return this.maxNoiseLevel;
	}
	
	/** 
	 * getter on maxAmbiguityLevel
	 * 
	 * @return NumberField : max Ambiguity Level Field
	 */
	public NumberField getMaxAmbiguityLevel() {
		return this.maxAmbiguityLevel;
	}
	
	// ---------------------- Acquisition Parameters -------------------------

	/** 
	 * getter on fusionAccepted
	 * 
	 * @return Checkbox : fusion Accepted
	 */
	public Checkbox getFusionAccepted() {
		return this.fusionAccepted;
	}

	/** 
	 * getter on groundResolutionMin
	 * 
	 * @return NumberField : ground ResolutionMin
	 */
	public NumberField getGroundResolutionMin() {
		return this.groundResolutionMin;
	}

	/** 
	 * getter on groundResolutionMax
	 * 
	 * @return NumberField : ground ResolutionMax
	 */
	public NumberField getGroundResolutionMax() {
		return this.groundResolutionMax;
	}

	/** 
	 * getter on minLuminosity
	 * 
	 * @return NumberField : min Luminosity
	 */
	public NumberField getMinLuminosity() {
		return this.minLuminosity;
	}

	/** 
	 * getter on acquisitionInstrumentMode
	 * 
	 * @return ComboBox : acquisition Instrument Mode
	 */
	public ComboBox getAcquisitionInstrumentMode() {
		return this.acquisitionInstrumentMode;
	}

	/** 
	 * getter on acquisitionPolarizationMode
	 * 
	 * @return ComboBox : acquisition Polarization Mode
	 */
	public ComboBox getAcquisitionPolarizationMode() {
		return this.acquisitionPolarizationMode;
	}

	// ---------------------- Acquisition Angles -------------------------
	
	/** 
	 * getter on coverageType
	 * 
	 * @return ComboBox : coverage Type
	 */
	public ComboBox getCoverageType() {
		return this.coverageType;
	}

	/** 
	 * getter on azimuthIncidenceAngleMin
	 * 
	 * @return NumberField : azimuth incidence Angle Min NumberField
	 */
	public NumberField getAzimuthIncidenceAngleMin() {
		return this.azimuthIncidenceAngleMin;
	}

	/** 
	 * getter on azimuthIncidenceAngleMax
	 * 
	 * @return NumberField : azimuth incidence Angle Max NumberField
	 */
	public NumberField getAzimuthIncidenceAngleMax() {
		return this.azimuthIncidenceAngleMax;
	}

	/** 
	 * getter on elevationIncidenceAngleMin
	 * 
	 * @return NumberField : elevation incidence Angle Min NumberField
	 */
	public NumberField getElevationIncidenceAngleMin() {
		return this.elevationIncidenceAngleMin;
	}

	/** 
	 * getter on elevationIncidenceAngleMax
	 * 
	 * @return NumberField : elevation Incidence Angle Max NumberField
	 */
	public NumberField getElevationIncidenceAngleMax() {
		return this.elevationIncidenceAngleMax;
	}

	/** 
	 * getter on alongTrackAngleMin
	 * 
	 * @return NumberField : along Track Angle Min NumberField
	 */
	public NumberField getAlongTrackAngleMin() {
		return this.alongTrackAngleMin;
	}

	/** 
	 * getter on alongTrackAngleMax
	 * 
	 * @return NumberField : along Track Angle Max NumberField
	 */
	public NumberField getAlongTrackAngleMax() {
		return this.alongTrackAngleMax;
	}

	/** 
	 * getter on acrossTrackAngleMin
	 * 
	 * @return NumberField : across Track Angle Min NumberField
	 */
	public NumberField getAcrossTrackAngleMin() {
		return this.acrossTrackAngleMin;
	}

	/** 
	 * getter on acrossTrackAngleMax
	 * 
	 * @return NumberField : across Track Angle Max NumberField
	 */
	public NumberField getAcrossTrackAngleMax() {
		return this.acrossTrackAngleMax;
	}

	// ----------------------- Setters -----------------------

	/** 
	 * getter on taskOperation
	 * 
	 * @param _taskOperation (String) : task operation value
	 */
	public void setTaskOperation(String _taskOperation) {
		this.taskOperation = _taskOperation;
	}

} // class
