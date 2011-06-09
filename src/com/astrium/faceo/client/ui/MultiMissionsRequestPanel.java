package com.astrium.faceo.client.ui;

import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;

import com.astrium.faceo.client.ui.configLoaders.ConfLoaderMissionRequestPanels;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.TabPanel;
import com.gwtext.client.widgets.layout.FitLayout;

/**
 * <B>SITE FACEO</B> <BR>
 * 
 * <P>
 * MultiMissionsRequestPanel class manages the 'FACEO' GUI
 * </P>
 * </P>
 * 
 * @author ASTRIUM
 * @version 1.0, le 04/05/2010
 */
public class MultiMissionsRequestPanel extends Panel {

	// internationalisation

	// constantes

	// attributs
	
	// Tab Panel for panels : 'Image Catalogue', 'Feasibility Study', 'Mission simulation' ...
	private TabPanel westTabPanel;

	// panels : 'Image Catalogue', 'Feasibility Study', 'Mission simulation' ...
	private SortedMap<String, MissionRequestPanel> missionRequestPanels = 
		new TreeMap<String, MissionRequestPanel>();

	// data 

	// --------------------------------------------------------------------

	/**
	 * constructor
	 * 
	 * @param _panelId (String) 	: HTML identifier on the page
	 * @param _panelTitle (String) 	: panel title
	 * @param _panelWidth (int) 	: panel width
	 * @param _panelHeight (int) 	: panel height
	 */
	public MultiMissionsRequestPanel(String _panelId, String _panelTitle, int _panelWidth, int _panelHeight) {

		super() ;

		this.setTitle(_panelTitle);
		this.setCollapsible(true);
		this.setCollapsed(true);
		this.setWidth(_panelWidth);
		this.setHeight(_panelHeight);
		this.setLayout(new FitLayout());
		this.setId(_panelId);
		this.setTitleCollapse(true);

		this.westTabPanel = new TabPanel();
		this.westTabPanel.setDeferredRender(true);
		this.westTabPanel.setActiveTab(0);
		this.westTabPanel.setCollapsible(false);
		this.westTabPanel.setCollapsed(false);
		
		ConfLoaderMissionRequestPanels confLoaderPanels = ConfLoaderMissionRequestPanels.getInstance();
		confLoaderPanels.getMissionsRequestPanels(this.missionRequestPanels);

		// add each mission request panel
		for(Entry<String, MissionRequestPanel> missionRequestPanels : this.missionRequestPanels.entrySet()) {
			MissionRequestPanel missionRequestPanel = missionRequestPanels.getValue();
			
			this.westTabPanel.add(missionRequestPanel);
		} // for(Entry<String, MissionRequestPanel> missionRequestPanels : this.missionRequestPanels.entrySet()) {

		this.add(this.westTabPanel);
		
	} // public MultiMissionsRequestPanel() {

	/**
	 * getter on westTabPanel
	 * 
	 * @return TabPanel : the west TabPanel
	 */
	public TabPanel getWestTabPanel() {
		return this.westTabPanel;
	}

	/**
	 * getter on missionRequestPanels
	 * 
	 * @return SortedMap<String, MissionRequestPanel> : the west TabPanels
	 */
	public SortedMap<String, MissionRequestPanel> getMissionRequestPanels() {
		return this.missionRequestPanels;
	}
	
	/**
	 * getter on one missionRequestPanel
	 * 
	 * @param _identifier (String) :  tab panel identifier
	 * 
	 * @return MissionRequestPanel : one west TabPanel
	 */
	public MissionRequestPanel getMissionRequestPanel(String _identifier) {
		return this.missionRequestPanels.get(_identifier);
	}

} // class
