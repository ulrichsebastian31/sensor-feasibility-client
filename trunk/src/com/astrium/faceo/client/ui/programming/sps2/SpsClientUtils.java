package com.astrium.faceo.client.ui.programming.sps2;

import java.util.Map.Entry;
import java.util.SortedMap;

import com.astrium.faceo.client.bean.programming.sps2.SensorBean;
import com.astrium.faceo.client.bean.programming.sps2.TaskResultBean;
import com.astrium.faceo.client.bean.programming.sps2.request.AcquisitionAngleRangeBean;
import com.astrium.faceo.client.bean.programming.sps2.request.AcquisitionParametersOPTBean;
import com.astrium.faceo.client.bean.programming.sps2.request.AcquisitionParametersSARBean;
import com.astrium.faceo.client.bean.programming.sps2.request.AcquisitionTypeBean;
import com.astrium.faceo.client.bean.programming.sps2.request.CoverageProgrammmingRequestBean;
import com.astrium.faceo.client.bean.programming.sps2.request.ProgrammingRequestBean;
import com.astrium.faceo.client.bean.programming.sps2.request.QualityOfServiceBean;
import com.astrium.faceo.client.bean.programming.sps2.request.TaskingParametersBean;
import com.astrium.faceo.client.bean.programming.sps2.request.ValidationParametersOPTBean;
import com.astrium.faceo.client.bean.programming.sps2.request.ValidationParametersSARBean;
import com.astrium.faceo.client.common.programming.sps2.Sps2ColorsConstants;
import com.astrium.faceo.client.common.programming.sps2.Sps2GeneralConstants;
import com.astrium.faceo.client.ui.MultiSensorsSettingsPanel;
import com.astrium.faceo.client.ui.cartography.cartoEarth.CartoAppletPanel;
import com.astrium.faceo.client.ui.programming.sps2.view.SpsPanels;
import com.astrium.faceo.client.ui.programming.sps2.view.SpsSensorSettingsPanel;
import com.astrium.faceo.client.ui.programming.sps2.view.SpsSettingsPanel;
import com.gwtext.client.data.SimpleStore;
import com.gwtext.client.data.Store;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.form.Checkbox;
import com.gwtext.client.widgets.form.ComboBox;
import com.gwtext.client.widgets.form.NumberField;

/**
 * <B>SITE FACEO</B> <BR>
 * 
 * <P>
 * La classe SpsClientUtils 
 * </P>
 * </P>
 * 
 * @author ASTRIUM
 * @version 1.0, le 05/2010
 */
public class SpsClientUtils extends Panel {

	/** */
	public static String LABEL = "label";

	/**
	 * 
	 */
	public SpsClientUtils() {
		super() ;
	} // 

	/**
	 * set the ComboBox for SPS 2.0 parameters
	 * 
	 * @param _comboBox (ComboBox)		: the ComboBox to define
	 * @param _id (String)				: ComboBox's id
	 * @param _displayField (String)	: the display field
	 * @param _store (Store)			: the Store for the values available for the comboBox
	 * @param _width (int)				: width in pixels
	 * @param _value (String)			: ComboBox's value
	 * @param _label (String)			: ComboBox's label
	 * @param _title (String)			: ComboBox's title
	 * @param _hideLabel (boolean)		: true to hide the ComboBox's label
	 * @param _allowBlank (boolean)		: true to allow blank for the ComboBox
	 * @param _forceSelection (boolean)	: true to force selection for the ComboBox
	 * @param _editable (boolean)		: false to prevent the user from typing text directly into the ComboBox
	 */
	public static void setComboBox(ComboBox _comboBox, String _id, String _displayField,
			Store _store, int _width, String _label, String _title,
			String _value, boolean _hideLabel, boolean _allowBlank, 
			boolean _forceSelection, boolean _editable) {

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
		_comboBox.setEditable(_editable);
	} // 

	/**
	 * set the NumberFields for the AOI definition, ...
	 * 
	 * @param _numberField (NumberField): the NumberField to define
	 * @param _id (String)				: NumberField's id
	 * @param _value (double)			: NumberField's value
	 * @param _minValue (int)			: min value
	 * @param _maxValue (int)			: max value
	 * @param _decimalPrecision (int)	: decimal precision value
	 * @param _hideLabel (boolean)		: true to hide the NumberField's label
	 * @param _allowBlank (boolean)		: true to allow blank for the NumberField
	 */
	public static void setNumberField(NumberField _numberField, String _id, 
			double _value, int _minValue, int _maxValue, 
			int _decimalPrecision, boolean _hideLabel, boolean _allowBlank) {

		_numberField.setId(_id);
		_numberField.setAllowBlank(_allowBlank);
		_numberField.setHideLabel(_hideLabel);
		_numberField.setMinValue(_minValue);
		_numberField.setMaxValue(_maxValue);
		_numberField.setDecimalPrecision(_decimalPrecision);
		_numberField.setValue(_value);
	} // 

	/**
	 * update of the parameters of the Tasking ('GetFeasibility' or 'Submit') SPS operation
	 * 
	 * @param _taskResultBean (TaskResultBean) : java bean object including Tasking ('GetFeasibility' or 'Submit') request informations
	 */
	public static void updatePanelSettings(TaskResultBean _taskResultBean) {
		ProgrammingRequestBean programmingRequestBean = 
			_taskResultBean.getProgrammingRequestBean();

		SpsSettingsPanel settingsPanel = SpsPanels.getSpsSettingsPanel();

		if (programmingRequestBean != null) {
			if (settingsPanel != null) {
				// Task name :
				settingsPanel.setNameField(programmingRequestBean.getName());

				// operations list
				updateOperationsStore(_taskResultBean.getOperation(), 
						settingsPanel.getStoreOperations(), 
						settingsPanel.getOperations());

				CoverageProgrammmingRequestBean coverageBean = 
					programmingRequestBean.getTasking().getCoverageProgrammmingRequest();

				// Area Of Interest : AOI
				settingsPanel.getLeftUpperNumberField().setValue(coverageBean.getRegionOfInterest().getLeftUpper());
				settingsPanel.getRightUpperNumberField().setValue(coverageBean.getRegionOfInterest().getRightUpper());
				settingsPanel.getLeftLowerNumberField().setValue(coverageBean.getRegionOfInterest().getLeftLower());
				settingsPanel.getRightLowerNumberField().setValue(coverageBean.getRegionOfInterest().getRightLower());

				// draw the current AOI after deleting previous draw AOI if it exits
				CartoAppletPanel.displayAOIOnCarto(
						settingsPanel.getLeftUpperNumberField(), 
						settingsPanel.getRightUpperNumberField(), 
						settingsPanel.getLeftLowerNumberField(), 
						settingsPanel.getRightLowerNumberField());

				// Time Of Interest
				settingsPanel.getStartPeriodDateField().
				setValue(coverageBean.getTimeOfInterest().getSurveyPeriod().getStartDate());
				settingsPanel.getEndPeriodDateField().
				setValue(coverageBean.getTimeOfInterest().getSurveyPeriod().getEndDate());

				// update the operation comboBox
				settingsPanel.getOperations().setValue(_taskResultBean.getOperation());

				// check the corresponding sensor
				String sensorId = programmingRequestBean.getSensor().getUrn();

				MultiSensorsSettingsPanel sensorsPanel = settingsPanel.getMultiSensorsSettingsPanel();

				// unchecking all the sensors checkboxs
				SortedMap<String, Checkbox> checkBoxs = sensorsPanel.getSensorCheckBox();
				for(Entry<String, Checkbox> chkBox : checkBoxs.entrySet()) {
					Checkbox checkBox = chkBox.getValue();
					checkBox.setChecked(false);
				} // checkBoxs
				sensorsPanel.setNbSensorsChecked(0);

				Checkbox checkbox = null;
				SpsSensorSettingsPanel sensorSettingsPanel = null;

				boolean find = false;
				for(Entry<String, SensorBean> sensors : Sps2GeneralConstants.getSensors().entrySet()) {
					SensorBean sensor = sensors.getValue();

					if (sensor.getActive()) {
						sensorsPanel.getSensorCheckBox().get(sensor.getId()).setChecked(false);
						sensorsPanel.getSensorCheckBox().get(sensor.getId()).disable();

						if (! find) {
							if (sensorId.equalsIgnoreCase(sensor.getUrn())) {
								checkbox = sensorsPanel.getSensorCheckBox().get(sensor.getId());
								sensorSettingsPanel = sensorsPanel.getSpsSensorSettingsPanel(sensor.getId());

								find = true;
							} // if
						} // if (! find) {
					} // if (sensor.getActive()) {
				} // for(Entry<String, SensorBean> sensors : Sps2GeneralConstants.getSensors().entrySet()) {

				if (checkbox != null) {
					checkbox.enable();
					checkbox.setChecked(true);
					sensorsPanel.setNbSensorsChecked(1);

					if (sensorSettingsPanel != null) {
						TaskingParametersBean parameters = sensorSettingsPanel.getTaskingParameters();

						// Task identifier :
						sensorSettingsPanel.getIdField().setValue(_taskResultBean.getId());

						// Task operation :
						sensorSettingsPanel.setTaskOperation(_taskResultBean.getOperation());

						// ---------------------- Validation parameters ----------------------
						if (parameters.getValidationParametersOPT()) {
							ValidationParametersOPTBean validationParametersOPT = 
								programmingRequestBean.getTasking().getValidationParameters().getValidationParametersOPT();

							// Max Cloud Coverage
							if (parameters.getMaxCloudCoverage()) {
								sensorSettingsPanel.getMaxCloudCoverage().setValue(validationParametersOPT.getMaxCloudCoverage());
							} // if (parameters.getMaxCloudCoverage()) {

							// Max Snow Coverage
							if (parameters.getMaxSnowCoverage()) {
								sensorSettingsPanel.getMaxSnowCoverage().setValue(validationParametersOPT.getMaxSnowCoverage());
							} // if (parameters.getMaxSnowCoverage()) {

							// Haze Accepted
							if (parameters.getHazeAccepted()) {
								sensorSettingsPanel.getHazeAccepted().setChecked(validationParametersOPT.getHazeAccepted());
							} // if (parameters.getHazeAccepted()) {

							// Sand Wind Accepted
							if (parameters.getSandWindAccepted()) {
								sensorSettingsPanel.getSandWindAccepted().setChecked(validationParametersOPT.getSandWindAccepted());
							} // if (parameters.getSandWindAccepted()) {
						} // if (parameters.getValidationParametersOPT()) {

						if (parameters.getValidationParametersSAR()) {
							ValidationParametersSARBean validationParametersSAR = 
								programmingRequestBean.getTasking().getValidationParameters().getValidationParametersSAR();

							// Max Noise Level
							if (parameters.getMaxNoiseLevel()) {
								sensorSettingsPanel.getMaxNoiseLevel().setValue(validationParametersSAR.getMaxNoiseLevel());
							} // if (parameters.getMaxNoiseLevel()) {

							// Max Ambiguity Level
							if (parameters.getMaxAmbiguityLevel()) {
								sensorSettingsPanel.getMaxAmbiguityLevel().setValue(validationParametersSAR.getMaxAmbiguityLevel());
							} // if (parameters.getMaxAmbiguityLevel()) {
						} // if (parameters.getValidationParametersSAR()) {

						// ---------------------- end Validation Parameters -------------------------

						// ---------------------- QOS : Quality Of Service -------------------------
						if (parameters.getQualityOfService()) {
							QualityOfServiceBean qualityOfService = 
								programmingRequestBean.getTasking().getQualityOfService();
							sensorSettingsPanel.getPriorityLevel().setValue(qualityOfService.getPriorityLevel());
						} // if (parameters.getQualityOfService()) {
						// ---------------------- end QOS : Quality Of Service -------------------------

						// ---------------------- Acquisition Parameters -------------------------

						// set Acquisition Type
						AcquisitionTypeBean acquisitionType = coverageBean.getAcquisitionType();

						if (parameters.getCoverageType()) {
							if (parameters.getAcquisitionMonoscopic()) {
								sensorSettingsPanel.getCoverageType().setValue(acquisitionType.getMonoscopicAcquisition().getCoverageType());
							} else if (parameters.getAcquisitionStereoscopic()) {
								sensorSettingsPanel.getCoverageType().setValue(acquisitionType.getStereoscopicAcquisition().getCoverageType());
							} // if (parameters.getAcquisitionMonoscopic()) {
						} // if (parameters.getCoverageType()) {

						AcquisitionAngleRangeBean acquisitionAngleRange = null;

						if (parameters.getAcquisitionMonoscopic()) {
							acquisitionAngleRange = acquisitionType.getMonoscopicAcquisition().getAcquisitionAngleRange();
						} else if (parameters.getAcquisitionStereoscopic()) {
							acquisitionAngleRange = acquisitionType.getStereoscopicAcquisition().getAcquisitionAngle1();
						} // if (parameters.getAcquisitionMonoscopic()) {

						// ---------------------- Incidence Angle Parameters -------------------------
						if (acquisitionAngleRange != null) {
							if ( (parameters.getAzimuth()) || (parameters.getElevation()) ) {
								// azimuth
								if (parameters.getAzimuth()) {
									sensorSettingsPanel.getAzimuthIncidenceAngleMin().setValue(acquisitionAngleRange.getIncidenceAngleRange().getAzimuthIncidenceMin());
									sensorSettingsPanel.getAzimuthIncidenceAngleMax().setValue(acquisitionAngleRange.getIncidenceAngleRange().getAzimuthIncidenceMax());
								} // if (parameters.getAzimuth()) {

								// elevation
								if (parameters.getElevation()) {
									sensorSettingsPanel.getElevationIncidenceAngleMin().setValue(acquisitionAngleRange.getIncidenceAngleRange().getElevationIncidenceMin());
									sensorSettingsPanel.getElevationIncidenceAngleMax().setValue(acquisitionAngleRange.getIncidenceAngleRange().getElevationIncidenceMax());
								} // if (parameters.getElevation()) {
							} // if ( (parameters.getAzimuth()) || (parameters.getElevation()) ) {

							// ---------------------- Pointing Angle Parameters -------------------------
							if ( (parameters.getAlongTrack()) || (parameters.getAcrossTrack()) ) {
								// Pointing Angle Along
								sensorSettingsPanel.getAlongTrackAngleMin().setValue(acquisitionAngleRange.getPointingAngleRange().getAlongTrackMin());
								sensorSettingsPanel.getAlongTrackAngleMax().setValue(acquisitionAngleRange.getPointingAngleRange().getAlongTrackMax());

								// Pointing Angle Across
								sensorSettingsPanel.getAcrossTrackAngleMin().setValue(acquisitionAngleRange.getPointingAngleRange().getAcrossTrackMin());
								sensorSettingsPanel.getAcrossTrackAngleMax().setValue(acquisitionAngleRange.getPointingAngleRange().getAcrossTrackMax());
							} // if ( (parameters.getAlongTrack()) || (parameters.getAcrossTrack()) ) {

						} // if (acquisitionAngleRange != null) {
						// ---------------------- end Acquisition Angle -------------------------

						// ---------------------- Acquisition parameters -------------------------

						if (parameters.getAcquisitionOpticalType()) {
							// OPTICAL : OPT acquisition parameters
							AcquisitionParametersOPTBean acquisitionParametersOPT = 
								coverageBean.getAcquisitionType().getAcquisitionParametersOPT();

							if (parameters.getFusionAccepted()) {
								sensorSettingsPanel.getFusionAccepted().setValue(acquisitionParametersOPT.getFusionAccepted());
							} // if (parameters.getFusionAccepted()) {

							if (parameters.getInstrumentMode()) {
								sensorSettingsPanel.getAcquisitionInstrumentMode().setValue(acquisitionParametersOPT.getInstrumentMode());
							} // if (parameters.getInstrumentMode()) {

							if (parameters.getGroundResolution()) {
								sensorSettingsPanel.getGroundResolutionMin().setValue(acquisitionParametersOPT.getGroundResolutionMin());
								sensorSettingsPanel.getGroundResolutionMax().setValue(acquisitionParametersOPT.getGroundResolutionMax());
							} // if (parameters.getGroundResolution()) {

							if (parameters.getMinLuminosity()) {
								sensorSettingsPanel.getMinLuminosity().setValue(acquisitionParametersOPT.getMinLuminosity());
							} // if (parameters.getMinLuminosity()) {
						} else if (parameters.getAcquisitionRadarType()) {
							// RADAR : SAR acquisition parameters
							AcquisitionParametersSARBean acquisitionParametersSAR = coverageBean.getAcquisitionType().getAcquisitionParametersSAR();

							if (parameters.getFusionAccepted()) {
								sensorSettingsPanel.getFusionAccepted().setValue(acquisitionParametersSAR.getFusionAccepted());
							} // if (parameters.getFusionAccepted()) {

							if (parameters.getInstrumentMode()) {
								sensorSettingsPanel.getAcquisitionInstrumentMode().setValue(acquisitionParametersSAR.getInstrumentMode());
							} // if (parameters.getInstrumentMode()) {

							if (parameters.getGroundResolution()) {
								sensorSettingsPanel.getGroundResolutionMin().setValue(acquisitionParametersSAR.getGroundResolutionMin());
								sensorSettingsPanel.getGroundResolutionMax().setValue(acquisitionParametersSAR.getGroundResolutionMax());
							} // if (parameters.getGroundResolution()) {

							if (parameters.getPolarizationMode()) {
								sensorSettingsPanel.getAcquisitionPolarizationMode().setValue(coverageBean.getAcquisitionType().getAcquisitionParametersSAR().getPolarizationMode());
							} // if (parameters.getPolarizationMode()) {
						} // if (parameters.getAcquisitionOpticalType()) {

						// ---------------------- end Acquisition Type -------------------------

					} // if (sensorSettingsPanel != null) {
				} // if (settingsPanel != null) {
			} // if (programmingRequestBean != null) {
		} // if (checkbox != null) {
	} // public static void updatePanelSettings(TaskResultBean _taskResultBean) {

	/**
	 * @param _operation :
	 * @param _store :
	 * @param _comboBox :
	 */
	public static void updateOperationsStore(String _operation, Store _store, ComboBox _comboBox) {
		_store.removeAll();

		if (_operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_DEFAULT)) {
			_store = new SimpleStore(
					new String[]{SpsClientUtils.LABEL}, SpsClientUtils.getDefaultOperations());
		} else if (_operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_GET_FEASIBILITY)) {
			_store = new SimpleStore(
					new String[]{SpsClientUtils.LABEL}, SpsClientUtils.getGetFeasibilityOperations());
		} else if (_operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_RESERVE)) {
			_store = new SimpleStore(
					new String[]{SpsClientUtils.LABEL}, SpsClientUtils.getReserveOperations());
		} else if (_operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_CONFIRM)) {
			_store = new SimpleStore(
					new String[]{SpsClientUtils.LABEL}, SpsClientUtils.getConfirmOperations());
		} else if (_operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_VALIDATE)) {
			_store = new SimpleStore(
					new String[]{SpsClientUtils.LABEL}, SpsClientUtils.getValidateOperations());
		} else if (_operation.equalsIgnoreCase(Sps2GeneralConstants.SPS_SUBMIT)) {
			_store = new SimpleStore(
					new String[]{SpsClientUtils.LABEL}, SpsClientUtils.getSubmitOperations());
		}
		_store.load();

		_comboBox.setStore(_store);
	} // 

	private static String[][] getGetFeasibilityOperations() {
		return new String[][]{
				new String[]{Sps2GeneralConstants.SPS_SUBMIT, Sps2GeneralConstants.SPS_SUBMIT}
		};
	} //

	private static String[][] getReserveOperations() {
		return new String[][]{
				new String[]{Sps2GeneralConstants.SPS_CONFIRM, Sps2GeneralConstants.SPS_CONFIRM},
				new String[]{Sps2GeneralConstants.SPS_UPDATE, Sps2GeneralConstants.SPS_UPDATE}
		};
	} // 

	private static String[][] getConfirmOperations() {
		return new String[][]{
				new String[]{Sps2GeneralConstants.SPS_VALIDATE, Sps2GeneralConstants.SPS_VALIDATE}
		};
	} // 

	private static String[][] getValidateOperations() {
		return new String[][]{
				new String[]{Sps2GeneralConstants.SPS_VALIDATE, Sps2GeneralConstants.SPS_VALIDATE}
		};
	} //

	private static String[][] getSubmitOperations() {
		return new String[][]{
				new String[]{Sps2GeneralConstants.SPS_UPDATE, Sps2GeneralConstants.SPS_UPDATE}
		};
	} // 

	@SuppressWarnings("unused")
	private static String[][] getAllOperations() {
		return new String[][]{
				new String[]{Sps2GeneralConstants.SPS_GET_FEASIBILITY, Sps2GeneralConstants.SPS_GET_FEASIBILITY},
				new String[]{Sps2GeneralConstants.SPS_SUBMIT, Sps2GeneralConstants.SPS_SUBMIT},
				new String[]{Sps2GeneralConstants.SPS_RESERVE, Sps2GeneralConstants.SPS_RESERVE}
		};
	} // 

	/**
	 * @return String[][]
	 */
	public static String[][] getDefaultOperations() {
		return new String[][]{
				new String[]{Sps2GeneralConstants.SPS_GET_FEASIBILITY, Sps2GeneralConstants.SPS_GET_FEASIBILITY},
				new String[]{Sps2GeneralConstants.SPS_SUBMIT, Sps2GeneralConstants.SPS_SUBMIT},
				new String[]{Sps2GeneralConstants.SPS_RESERVE, Sps2GeneralConstants.SPS_RESERVE}
		};
	} // 

	/**
	 * Get the color corresponding to the segment status
	 * 
	 * @param segmentStatus (String)	: the segment status
	 *  
	 * @return float[] :	the array with hsv color
	 * 							hue (float)			: Hue color HSB parameter
	 * 							saturation (float)	: Saturation color HSB parameter
	 * 							brightness (float)	: Brightness color HSB parameter
	 */
	public static float[] getStatusHsvColorBy(String segmentStatus) {
		if (segmentStatus != null) {
			if (Sps2GeneralConstants.STATUS_ACQUIRED.equalsIgnoreCase(segmentStatus)) {
				return Sps2ColorsConstants.ACQUIRED_HSB;
			} else if (Sps2GeneralConstants.STATUS_CANCELLED.equalsIgnoreCase(segmentStatus)) {
				return Sps2ColorsConstants.CANCELLED_HSB;
			} else if (Sps2GeneralConstants.STATUS_FAILED.equalsIgnoreCase(segmentStatus)) {
				return Sps2ColorsConstants.FAILED_HSB;
			} else if (Sps2GeneralConstants.STATUS_PLANNED.equalsIgnoreCase(segmentStatus)) {
				return Sps2ColorsConstants.PLANNED_HSB;
			} else if (Sps2GeneralConstants.STATUS_POTENTIAL.equalsIgnoreCase(segmentStatus)) {
				return Sps2ColorsConstants.POTENTIAL_HSB;
			} else if (Sps2GeneralConstants.STATUS_REJECTED.equalsIgnoreCase(segmentStatus)) {
				return Sps2ColorsConstants.REJECTED_HSB;
			} else if (Sps2GeneralConstants.STATUS_VALIDATED.equalsIgnoreCase(segmentStatus)) {
				return Sps2ColorsConstants.VALIDATED_HSB;
			} else {
				return Sps2ColorsConstants.DEFAULT_HSB;
			}
		} else {
			return Sps2ColorsConstants.DEFAULT_HSB;
		}
	} // 

	/**
	 * Get the color corresponding to the segment status
	 * 
	 * @param segmentStatus (String)	: the segment status
	 *  
	 * @return String :	the String with Hexdecimal color
	 */
	public static String getStatusHexaColor(String segmentStatus) {
		if (segmentStatus != null) {
			if (Sps2GeneralConstants.STATUS_ACQUIRED.equalsIgnoreCase(segmentStatus)) {
				return Sps2ColorsConstants.ACQUIRED_HEXA;
			} else if (Sps2GeneralConstants.STATUS_CANCELLED.equalsIgnoreCase(segmentStatus)) {
				return Sps2ColorsConstants.CANCELLED_HEXA;
			} else if (Sps2GeneralConstants.STATUS_FAILED.equalsIgnoreCase(segmentStatus)) {
				return Sps2ColorsConstants.FAILED_HEXA;
			} else if (Sps2GeneralConstants.STATUS_PLANNED.equalsIgnoreCase(segmentStatus)) {
				return Sps2ColorsConstants.PLANNED_HEXA;
			} else if (Sps2GeneralConstants.STATUS_POTENTIAL.equalsIgnoreCase(segmentStatus)) {
				return Sps2ColorsConstants.POTENTIAL_HEXA;
			} else if (Sps2GeneralConstants.STATUS_REJECTED.equalsIgnoreCase(segmentStatus)) {
				return Sps2ColorsConstants.REJECTED_HEXA;
			} else if (Sps2GeneralConstants.STATUS_VALIDATED.equalsIgnoreCase(segmentStatus)) {
				return Sps2ColorsConstants.VALIDATED_HEXA;
			} else {
				return Sps2ColorsConstants.DEFAULT_HEXA;
			}
		} else {
			return Sps2ColorsConstants.DEFAULT_HEXA;
		}
	} // 

} // class
