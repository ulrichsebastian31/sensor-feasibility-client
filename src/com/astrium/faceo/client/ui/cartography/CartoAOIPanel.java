package com.astrium.faceo.client.ui.cartography;

import com.astrium.faceo.client.common.GeneralConstant;
import com.astrium.faceo.client.common.Utils;
import com.astrium.faceo.client.common.cartoAoi.CartoAoiConstants;
import com.astrium.faceo.client.ui.HomePage;
import com.astrium.faceo.client.ui.cartography.cartoEarth.CartoAppletPanel;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Position;
import com.gwtext.client.widgets.BoxComponent;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.ToolTip;
import com.gwtext.client.widgets.Window;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.event.WindowListenerAdapter;
import com.gwtext.client.widgets.form.Field;
import com.gwtext.client.widgets.form.FieldSet;
import com.gwtext.client.widgets.form.FormPanel;
import com.gwtext.client.widgets.form.MultiFieldPanel;
import com.gwtext.client.widgets.form.NumberField;
import com.gwtext.client.widgets.form.event.FieldListenerAdapter;
import com.gwtext.client.widgets.layout.ColumnLayoutData;

/**
 * <B>SITE FACEO</B> <BR>
 * 
 * <P>
 * La classe CartoAOIPanel g&egrave;re les widgets 
 * du panneau AOI
 * </P>
 * </P>
 * 
 * @author GR
 * @version 1.0, le 26/02/2009
 */
public class CartoAOIPanel {

	// internationalisation
	private static final CartoAoiConstants cartoAoiConstants =
		(CartoAoiConstants) com.google.gwt.core.client.GWT.create(CartoAoiConstants.class);

	// constantes
	private final int AOI_INFOS_FORM_PANEL_WIDTH = 400;

	private final int AOI_INFOS_FORM_PANEL_HEIGHT = 105;

	private final int AOI_INFOS_FORM_PANEL_LABELS_WIDTH = 85;

	private final int WINDOW_AOI_INFOS_WIDTH = AOI_INFOS_FORM_PANEL_WIDTH + 10;

	private final int WINDOW_AOI_INFOS_HEIGHT = AOI_INFOS_FORM_PANEL_HEIGHT + 30;

	private final String IFRAME_APPLET_AOI_INFOS = "iframeAppletAOIInfos";

	// attributs
	private Panel panelAoiPanel;

	private FormPanel formAOIPanel;

	private NumberField leftUpperNumberField;

	private NumberField rightUpperNumberField;

	private NumberField leftLowerNumberField;

	private NumberField rightLowerNumberField;

	private Button displayAOIButton;

	private Window windowAOIInfos;

	/**
	 * constructor
	 * 
	 * @param _idPanel (String) : panel identifier
	 */
	public CartoAOIPanel(String _idPanel) {
		
		Utils.addIframeToRootElement(IFRAME_APPLET_AOI_INFOS);

		this.panelAoiPanel = new Panel();

		this.panelAoiPanel.setHeader(false);
		this.panelAoiPanel.setBorder(false);
		this.panelAoiPanel.setAutoScroll(true);
		this.panelAoiPanel.setAutoWidth(true);
		this.panelAoiPanel.setAutoHeight(true);
		this.panelAoiPanel.setId(_idPanel + "_panelAoiPanel");

		this.formAOIPanel = new FormPanel(Position.LEFT);
		this.formAOIPanel.setId(_idPanel + "_formAOIPanel");
		this.formAOIPanel.setFrame(true);
		this.formAOIPanel.setHeaderAsText(false);
		this.formAOIPanel.setWidth(AOI_INFOS_FORM_PANEL_WIDTH);
		this.formAOIPanel.setHeight(AOI_INFOS_FORM_PANEL_HEIGHT);
		this.formAOIPanel.setLabelWidth(AOI_INFOS_FORM_PANEL_LABELS_WIDTH);

		// --------------------- AOI ---------------------
		// Area of Interest (AOI)
		FieldSet aoiFieldSet = new FieldSet(cartoAoiConstants.aoi());  
		//		aoiFieldSet.setAutoWidth(true);
		//		aoiFieldSet.setAutoHeight(true);
		aoiFieldSet.setId(_idPanel + "_aoiFieldSet");
		aoiFieldSet.setButtonAlign(Position.CENTER);

		// Upper
		MultiFieldPanel aoiUpperMultiFieldPanel = new MultiFieldPanel();
		aoiUpperMultiFieldPanel.setId(_idPanel + "_aoiUpperMultiFieldPanel");
		aoiUpperMultiFieldPanel.setAutoWidth(true);

		this.leftUpperNumberField = new NumberField(cartoAoiConstants.aoiUpper(), "AOI_left_upper", 80);
		Utils.setAOINumberField(this.leftUpperNumberField, "leftUpperNumberField", GeneralConstant.AOI_LEFT_UPPER_NUMBER_FIELD, false, 3, -90, 90);
		this.aoiNumberFieldAddOnChangeListener(this.leftUpperNumberField);
		aoiUpperMultiFieldPanel.addToRow(this.leftUpperNumberField, 200);

		this.rightUpperNumberField = new NumberField(cartoAoiConstants.aoiRightUpper(), "AOI_right_upper", 80);
		Utils.setAOINumberField(this.rightUpperNumberField, "rightUpperNumberField", GeneralConstant.AOI_RIGHT_UPPER_NUMBER_FIELD, true, 3, -180, 180);
		this.aoiNumberFieldAddOnChangeListener(this.rightUpperNumberField);
		aoiUpperMultiFieldPanel.addToRow(this.rightUpperNumberField, new ColumnLayoutData(0.5));

		aoiFieldSet.add(aoiUpperMultiFieldPanel);

		// Lower
		MultiFieldPanel aoiLowerMultiFieldPanel = new MultiFieldPanel();
		aoiLowerMultiFieldPanel.setId(_idPanel + "_aoiLowerMultiFieldPanel");
		aoiLowerMultiFieldPanel.setAutoWidth(true);

		this.leftLowerNumberField = new NumberField(cartoAoiConstants.aoiLower(), "AOI_left_lower", 80);
		Utils.setAOINumberField(this.leftLowerNumberField, "leftLowerNumberField", GeneralConstant.AOI_LEFT_LOWER_NUMBER_FIELD, false, 3, -90, 90);
		this.aoiNumberFieldAddOnChangeListener(this.leftLowerNumberField);
		aoiLowerMultiFieldPanel.addToRow(this.leftLowerNumberField, 200);

		this.rightLowerNumberField = new NumberField(cartoAoiConstants.aoiRightLower(), "AOI_right_lower", 80);
		Utils.setAOINumberField(this.rightLowerNumberField, "rightLowerNumberField", GeneralConstant.AOI_RIGHT_LOWER_NUMBER_FIELD, true, 3, -180, 180);
		this.aoiNumberFieldAddOnChangeListener(this.rightLowerNumberField);
		aoiLowerMultiFieldPanel.addToRow(this.rightLowerNumberField, new ColumnLayoutData(0.5));

		// --------------------- Display AOI Button ---------------------
		this.displayAOIButton = new Button(cartoAoiConstants.displayAOI());
		this.displayAOIButton.setId(_idPanel + "_displayAOIButton");

		this.displayAOIButton.addListener(new ButtonListenerAdapter() {  
			public void onClick(Button button, EventObject e) {
				CartoAppletPanel.displayAOIOnCarto(
						getLeftUpperNumberField(), getRightUpperNumberField(), 
						getLeftLowerNumberField(), getRightLowerNumberField());
			} // onClick
		});

		// Tip attached to the button
		ToolTip toolTip = new ToolTip(cartoAoiConstants.displayAOI() + " " + cartoAoiConstants.onCartography());  
		toolTip.applyTo(this.displayAOIButton);  

		aoiLowerMultiFieldPanel.addToRow(this.displayAOIButton, new ColumnLayoutData(0.5));		
		// --------------------------------------------------------

		aoiFieldSet.add(aoiLowerMultiFieldPanel);

		this.formAOIPanel.add(aoiFieldSet);

		this.panelAoiPanel.add(this.formAOIPanel);

		this.panelAoiPanel.setAutoScroll(true); // ajout des scrolls si necessaire

		// ------------------------ AOI View Window -------------------------------
		this.windowAOIInfos = new Window("<p align='center'>" + cartoAoiConstants.aoi() + "</p>", 
				WINDOW_AOI_INFOS_WIDTH, WINDOW_AOI_INFOS_HEIGHT, true, false);
		this.windowAOIInfos.setClosable(true);
		this.windowAOIInfos.setPlain(true);
		this.windowAOIInfos.setMaximizable(false);
		this.windowAOIInfos.setMinimizable(false);
		this.windowAOIInfos.setResizable(false);
		this.windowAOIInfos.setDraggable(true);
		this.windowAOIInfos.setBorder(false);
		this.windowAOIInfos.setPlain(true);  
		this.windowAOIInfos.setIconCls("paste-icon");
		this.windowAOIInfos.add(this.panelAoiPanel);
		this.windowAOIInfos.setId(_idPanel + "_windowAOIInfos");
		this.windowAOIInfos.setCloseAction(Window.HIDE);
		this.windowAOIInfos.setBodyBorder(false);
		this.windowAOIInfos.setHideBorders(true);
		this.windowAOIInfos.setModal(false);

		this.windowAOIInfos.addListener(new WindowListenerAdapter() {
			public void onClose(Panel panel) {
				Utils.putWindowTofront(false, windowAOIInfos.getId(), IFRAME_APPLET_AOI_INFOS);
			} // public void onClose(Panel panel) {

			public void onHide(Component component) {  
				Utils.putWindowTofront(true, windowAOIInfos.getId(), IFRAME_APPLET_AOI_INFOS);
			} // public void onHide(Component component) {  

			public void onMove(BoxComponent component,
					int x,
					int y) {
				// on verifie que la barre de titre de la fenetre est accessible
				Utils.resetWindowTop(x, y, windowAOIInfos);
				
				Utils.putWindowTofront(true, windowAOIInfos.getId(), IFRAME_APPLET_AOI_INFOS);
			}
		}); // windowAOIInfos.addListener(new WindowListenerAdapter() {

	} // public CartoAOIPanel() {

	//  ------------ Listeners ------------
	private void aoiNumberFieldAddOnChangeListener(NumberField _aoiNumberField) {
		_aoiNumberField.addListener(new FieldListenerAdapter() {  
			public void onChange(Field field, java.lang.Object newVal, java.lang.Object oldVal) {
				if (field.isValid()) {
					CartoAppletPanel.displayAOIOnCarto(
							getLeftUpperNumberField(), getRightUpperNumberField(), 
							getLeftLowerNumberField(), getRightLowerNumberField());
				}
			}  // public void onChange(
		}); // _aoiNumberField.addListener(new FieldListenerAdapter() {  

	} // private static void aoiNumberFieldAddOnChangeListener(NumberField _leftUpperNumberField) {
	
	/**
	 * M&eacute;thode permettant la d&eacute;finition d'un objet de type 'AOI' rectangulaire
	 * sur la repr&eacute;sentation 3D de la Terre
	 * 
	 */
	public void showWindowAoiRectangleCoordinates() {

		if (this.windowAOIInfos != null) {
			this.windowAOIInfos.show();
			if (!HomePage.getHomePageBean().getAoiWindowOpenOnce()) {
				this.windowAOIInfos.center();
				HomePage.getHomePageBean().setAoiWindowOpenOnce(true);
			}
			Utils.putWindowTofront(true, this.windowAOIInfos.getId(), IFRAME_APPLET_AOI_INFOS);
		}

	} // public void showWindowAoiRectangleCoordinates() {

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
	 * getter on windowAOIInfos
	 * 
	 * @return Window : the window AOI Infos
	 */
	public Window getWindowAOIInfos() {
		return this.windowAOIInfos;
	}

} // class
