package com.astrium.faceo.client.ui.programming.sps2.view;

import com.astrium.faceo.client.common.GeneralConstant;
import com.astrium.faceo.client.common.programming.sps2.Sps2Constants;
import com.astrium.faceo.client.common.programming.sps2.Sps2GeneralConstants;
import com.astrium.faceo.client.ui.MissionRequestPanel;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.event.PanelListenerAdapter;
import com.gwtext.client.widgets.layout.AnchorLayoutData;

/**
 * <B>SITE FACEO</B> <BR>
 * 
 * <P>
 * ProgrammingPanels class manages the 'Programming' GUI
 * </P>
 * </P>
 * 
 * @author ASTRIUM
 * @version 1.0, le 03/05/2010
 */
public class SpsPanels extends MissionRequestPanel {

	// internationalisation
	private static final Sps2Constants sps2Constants =
		(Sps2Constants) com.google.gwt.core.client.GWT.create(Sps2Constants.class);

	// constantes

	// attributs

	// panneau d'affichage de la liste des taches SPS
	private static SpsTasksPanel panelProgrammingTasksPanel;

	// panneau d'affichage des parametres pour les requetes SPS CoMu GetFeasibility
	private static SpsSettingsPanel panelProgrammingSettingsPanel;

	// panneau d'affichage de la reponse a l'operation SPS GetFeasibility
	private static SpsSolutionsPanel panelProgrammingSolutionsPanel;
	
	// panneau d'affichage de la reponse a l'operation SPS GetFeasibility
	private static SpsOthersOperationsPanel panelProgrammingOthersOperationsPanel;

	// data 
	// initialisation de la liste des taches SPS
	// vaut true si la liste a ete initialisee
	private boolean initProgrammingTasks = false;

	// --------------------------------------------------------------------

	/**
	 * constructor
	 * 
	 * @param _panelId (String) : HTML identifier on the page
	 * @param _panelWidth (int) : panel width
	 */
	public SpsPanels(String _panelId, int _panelWidth) {

		super() ;

		this.setId(_panelId);
		this.setDeferredRender(true);
		this.setActiveTab(0);

		this.setTitle(sps2Constants.title());
		this.setAutoScroll(true);
		this.setWidth(_panelWidth);
		this.setCollapsible(false);
		this.setCollapsed(false);

		// mise a jour de la liste des taches 'SPS' lors du premier clic sur l'onglet 'Satellite Programming'
		this.addListener(new PanelListenerAdapter() {
			public void onActivate(Panel source) {
				if (source.getId().equalsIgnoreCase(GeneralConstant.SPS_PANEL_ID)) // click on tab 'Satellite Programming'
					if (!initProgrammingTasks) {
						initProgrammingTasks = true; // afin de ne pas mettre a jour la liste lors d'un prochain clic sur l'onglet
						panelProgrammingTasksPanel.getRefreshButton().fireEvent("click");
					}
			} // public void onActivate
		}); // _panel.addListener

		//------------------------ Programming Tasks Panel --------------------------
		panelProgrammingTasksPanel = new SpsTasksPanel(Sps2GeneralConstants.PROGRAMMING_TASKS_PANEL_ID);
		this.add(panelProgrammingTasksPanel, new AnchorLayoutData("100% 100%"));

		//------------------------ Programming Settings Panel --------------------------
		panelProgrammingSettingsPanel = new SpsSettingsPanel(Sps2GeneralConstants.PROGRAMMING_SETTINGS_PANEL_ID);
		this.add(panelProgrammingSettingsPanel, new AnchorLayoutData("100% 100%"));

		//------------------------ Programming Results Panel --------------------------
		panelProgrammingSolutionsPanel = new SpsSolutionsPanel(Sps2GeneralConstants.PROGRAMMING_SOLUTIONS_PANEL_ID);
		this.add(panelProgrammingSolutionsPanel, new AnchorLayoutData("100% 100%"));

		//------------------------ Programming Other operations Panel --------------------------
		panelProgrammingOthersOperationsPanel = new SpsOthersOperationsPanel(Sps2GeneralConstants.PROGRAMMING_OTHER_OPERATIONS_PANEL_ID);
		this.add(panelProgrammingOthersOperationsPanel, new AnchorLayoutData("100% 100%"));

	} // public ProgrammingPanels() {

	// ---------------------- Getters ---------------------

	/**
	 * getter on panelProgrammingSettingsPanel
	 * 
	 * @return SpsSettingsPanel : the Programming settings panel
	 */
	public static SpsSettingsPanel getSpsSettingsPanel() {
		return panelProgrammingSettingsPanel;
	} 

	/**
	 * getter on panelProgrammingSolutionsPanel
	 * 
	 * @return SpsSolutionsPanel : the Programming solutions panel
	 */
	public static SpsSolutionsPanel getSpsSolutionsPanel() {
		return panelProgrammingSolutionsPanel;
	}

	/**
	 * getter on panelProgrammingTasksPanel
	 * 
	 * @return SpsTasksPanel : the Programming tasks panel
	 */
	public static SpsTasksPanel getSpsTasksPanel() {
		return panelProgrammingTasksPanel;
	}

	// ---------------------- Setters ---------------------

} // class
