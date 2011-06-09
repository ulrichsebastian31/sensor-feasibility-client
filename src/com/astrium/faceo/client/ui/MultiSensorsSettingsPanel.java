package com.astrium.faceo.client.ui;

import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import com.astrium.faceo.client.bean.programming.sps2.SensorBean;
import com.astrium.faceo.client.common.GeneralConstant;
import com.astrium.faceo.client.common.programming.sps2.Sps2Constants;
import com.astrium.faceo.client.common.programming.sps2.Sps2GeneralConstants;
import com.astrium.faceo.client.ui.configLoaders.ConfLoaderSpsSensorsSettingsPanels;
import com.astrium.faceo.client.ui.programming.sps2.SpsClientUtils;
import com.astrium.faceo.client.ui.programming.sps2.view.SpsSensorSettingsPanel;
import com.astrium.faceo.client.ui.programming.sps2.view.SpsSettingsPanel;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.QuickTipsConfig;
import com.gwtext.client.widgets.TabPanel;
import com.gwtext.client.widgets.Toolbar;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.form.Checkbox;
import com.gwtext.client.widgets.form.FieldSet;
import com.gwtext.client.widgets.form.event.CheckboxListenerAdapter;

/**
 * <B>SITE FACEO</B> <BR>
 * 
 * <P>
 * La classe MultiSensorsSettingsPanel g&egrave;re les composants 
 * du panneau des param&egrave;tres de la requ&ecirc;te SPS getFeasibility
 * </P>
 * </P>
 * 
 * @author GR
 * @version 1.0, le 26/05/2010
 */
public class MultiSensorsSettingsPanel extends com.gwtext.client.widgets.Panel {

	// internationalisation
	private static final Sps2Constants spsConstants =
		(Sps2Constants) com.google.gwt.core.client.GWT.create(Sps2Constants.class);

	// constates
	private final int PROGRAMMING_FORM_PANEL_WIDTH = GeneralConstant.WEST_PANEL_WIDTH - 5;

	// attributs
	private TabPanel sensorsTabPanel;

	private ToolbarButton activateBtn;

	// panels : 'OPTICAL 1', 'OPTICAL 2', 'SAR 1', 'SAR 2' ...
	private final SortedMap<String, SpsSensorSettingsPanel> sensorSettingsPanels = 
		new TreeMap<String, SpsSensorSettingsPanel>();

	// panels : 'OPTICAL 1', 'OPTICAL 2', 'SAR 1', 'SAR 2' ...
	private SortedMap<String, Checkbox> sensorCheckBox = 
		new TreeMap<String, Checkbox>();

	private int nbSensorsChecked = 0;

	private Toolbar toolbar;

	/**
	 * constructor
	 * 
	 * @param _panelId (String) : HTML identifier on the page
	 * @param _settingsPanel 
	 */
	public MultiSensorsSettingsPanel(String _panelId, final SpsSettingsPanel _settingsPanel) {

		super();

		this.setId(_panelId);

		this.setTitle(spsConstants.settings());
		this.setBorder(false);
		this.setCollapsible(false);
		this.setCollapsed(false);
		this.setAutoScroll(true);
		this.setAnimCollapse(false);

		//create a toolbar and various menu items  
		this.toolbar = new Toolbar();
		this.toolbar.setId(_panelId + "_toolbar");

		// bouton d'activation des case a cocher est des onglets
		// ------------------------ 'Init Carto Position' Icon ToolBar Button -------------------------------
		this.activateBtn = new ToolbarButton(spsConstants.activateSensors());
		this.activateBtn.setId(_panelId + "_activateBtn");
		QuickTipsConfig activateBtnTipsConfig = new QuickTipsConfig();
		activateBtnTipsConfig.setText("<b>" + spsConstants.activateSensorsTip() + "</b><br/>");
		activateBtnTipsConfig.setTitle(spsConstants.activateSensors());
		this.activateBtn.setTooltip(activateBtnTipsConfig);

		// ------------------------ 'Init Carto Position' Icon Button Listener -------------------------------
		this.activateBtn.addListener(new ButtonListenerAdapter() {  
			public void onClick(Button button, EventObject e) {
				for(Entry<String, SensorBean> sensors : Sps2GeneralConstants.getSensors().entrySet()) {
					SensorBean sensor = sensors.getValue();
					if ( (sensor != null) && (sensor.getActive()) ) {
						if (sensorCheckBox.get(sensor.getId()) != null) {
							sensorCheckBox.get(sensor.getId()).enable();

							SpsSensorSettingsPanel sensorSettingsPanel = 
								sensorSettingsPanels.get(sensor.getId());
							if (sensorSettingsPanel != null) {
								sensorSettingsPanel.getIdField().setValue("");
							} // if
						} // if (sensorCheckBox.get(sensor.getId()) != null) {
					} // if ( (sensor != null) && (sensor.getActive()) ) {
				} // for(Entry<String, SensorBean> sensors : Sps2GeneralConstants.getSensors().entrySet()) {

				// get default operations list
				SpsClientUtils.updateOperationsStore(Sps2GeneralConstants.SPS_DEFAULT, 
						_settingsPanel.getStoreOperations(), _settingsPanel.getOperations());
			} // public void onClick(
		}); // this.activateBtn.addListener(new ButtonListenerAdapter() {   

		// definition de la barre d'outils
		this.toolbar.addButton( this.activateBtn);
		this.setTopToolbar(this.toolbar);
		// ------------------------------------------

		ConfLoaderSpsSensorsSettingsPanels confLoaderPanels = ConfLoaderSpsSensorsSettingsPanels.getInstance();
		if (Sps2GeneralConstants.getSensors().size() ==0) {
			confLoaderPanels.setSpsSensors();
		} // if (Sps2GeneralConstants.getSensors().size() ==0) {
		confLoaderPanels.getSpsSensorSettingsPanels(this.sensorSettingsPanels);

		// sensors
		FieldSet sensorsFieldSet = new FieldSet(spsConstants.sensors());
		sensorsFieldSet.setId(_panelId + "_sensorsFieldSet");
		sensorsFieldSet.setWidth(PROGRAMMING_FORM_PANEL_WIDTH - 55);
		sensorsFieldSet.setCollapsible(true);
		sensorsFieldSet.setCollapsed(false);
		sensorsFieldSet.setAutoHeight(true);

		this.sensorsTabPanel = new TabPanel();
		this.sensorsTabPanel.setId(_panelId + "_sensorsTabPanel");

		for(Entry<String, SpsSensorSettingsPanel> sensorSettingsPanels : this.sensorSettingsPanels.entrySet()) {
			final SpsSensorSettingsPanel sensorSettingsPanel = sensorSettingsPanels.getValue();

			Checkbox checkBox = new Checkbox(sensorSettingsPanel.getTitle(), sensorSettingsPanel.getTitle());
			checkBox.setId(sensorSettingsPanel.getTitle());

			checkBox.addListener(new CheckboxListenerAdapter() {  
				public void onCheck(Checkbox checkBox, boolean checked)  {  
					String tabIdentifier = checkBox.getId();
					Element element = DOM.getElementById(tabIdentifier);

					if (element != null) {
						if (checked) {
							nbSensorsChecked ++;
							sensorSettingsPanel.enable();
							sensorsTabPanel.activate(Sps2GeneralConstants.SENSOR_SETTINGS_PANEL + tabIdentifier);
						} else {
							nbSensorsChecked --;
							sensorSettingsPanel.disable();
						} // if (checked) {
					} // if (element != null) {
				} // public void onCheck
			}); // checkBox.addListener

			this.sensorCheckBox.put(checkBox.getId(), checkBox);

			sensorsFieldSet.add(checkBox);
		} // for(Entry<String, SpsSensorSettingsPanel> sensorSettingsPanels : spsSensorSettingsPanels()) {

		this.add(sensorsFieldSet);

		// add each mission request panel
		for(Entry<String, SpsSensorSettingsPanel> sensorSettingsPanels : this.sensorSettingsPanels.entrySet()) {
			SpsSensorSettingsPanel sensorSettingsPanel = sensorSettingsPanels.getValue();

			this.sensorsTabPanel.add(sensorSettingsPanel);
		} // for(Entry<String, SpsSensorSettingsPanel> sensorSettingsPanels : spsSensorSettingsPanels()) {

		this.add(this.sensorsTabPanel);

	} // public MultiSensorsSettingsPanel(String _panelId) {

	// ------------------- Getters -------------------

	/**
	 * getter on sensorSettingsPanels
	 * 
	 * @return SortedMap<String, SpsSensorSettingsPanel> : the Sps Sensor Settings Panels
	 */
	public SortedMap<String, SpsSensorSettingsPanel> getSpsSensorSettingsPanels() {
		return this.sensorSettingsPanels;
	}

	/**
	 * getter on one sensorSettingsPanels
	 * 
	 * @param _identifier (String) :  Sps Sensor Settings Panel identifier
	 * 
	 * @return SpsSensorSettingsPanel : one Sps Sensor Settings Panel
	 */
	public SpsSensorSettingsPanel getSpsSensorSettingsPanel(String _identifier) {
		return this.sensorSettingsPanels.get(_identifier);
	}

	/**
	 * getter on one nbSensorsChecked
	 * 
	 * @return int : nb Sensors Checked
	 */
	public int getNbSensorsChecked() {
		return this.nbSensorsChecked;
	}

	/**
	 * getter on one sensorCheckBox
	 * 
	 * @return SortedMap<String, Checkbox> : sensors CheckBox
	 */
	public SortedMap<String, Checkbox> getSensorCheckBox() {
		return this.sensorCheckBox;
	}

	// ------------------- Getters -------------------

	/**
	 * setter on nbSensorsChecked
	 * 
	 * @param _nbSensorsChecked (int) : nb Sensors Checked
	 */
	public void setNbSensorsChecked(int _nbSensorsChecked) {
		this.nbSensorsChecked = _nbSensorsChecked;
	}

} // class
