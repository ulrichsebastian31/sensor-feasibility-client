package com.astrium.faceo.client.ui;

import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;

import com.astrium.faceo.client.ui.configLoaders.ConfLoaderCartographicPanels;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.TabPanel;
import com.gwtext.client.widgets.layout.AnchorLayoutData;
import com.gwtext.client.widgets.layout.FitLayout;

/**
 * <B>SITE FACEO</B> <BR>
 * 
 * <P>
 * MultiCartographicPanel class manages the 'FACEO' GUI
 * </P>
 * </P>
 * 
 * @author ASTRIUM
 * @version 1.0, le 04/05/2010
 */
public class MultiCartographicPanel extends Panel {

	// internationalisation

	// constantes

	// attributs
	
	// Tab Panel for cartographic panels : 'Layers', 'WMS', 'KML' ...
	private TabPanel eastTabPanel;

	// panels : 'Layers', 'WMS', 'KML' ...
	private SortedMap<String, CartographicPanel> cartographicPanels = 
		new TreeMap<String, CartographicPanel>();

	// data 

	// --------------------------------------------------------------------

	/**
	 * constructor
	 * 
	 * @param _panelId (String) 	: HTML identifier on the page
	 * @param _panelTitle (String) 	: panel title
	 * @param _panelWidth (int) 	: panel width
	 */
	public MultiCartographicPanel(String _panelId, String _panelTitle, int _panelWidth) {

		super() ;

		this.setId(_panelId);
		this.setTitle(_panelTitle);
		this.setCollapsible(true);
		this.setWidth(_panelWidth);
		this.setLayout(new FitLayout());
		this.setTitleCollapse(true);
		this.setCollapsed(true);
		
		this.eastTabPanel = new TabPanel();
		this.eastTabPanel.setDeferredRender(true);
		this.eastTabPanel.setActiveTab(0);
		this.eastTabPanel.setCollapsible(false);
		this.eastTabPanel.setCollapsed(false);
		this.eastTabPanel.setAutoScroll(true);
		
		ConfLoaderCartographicPanels confLoaderPanels = ConfLoaderCartographicPanels.getInstance();
		confLoaderPanels.getCartographicPanels(this.cartographicPanels);

		// add each cartographic panel
		for(Entry<String, CartographicPanel> cartographicPanels : this.cartographicPanels.entrySet()) {
			CartographicPanel cartographicPanel = cartographicPanels.getValue();
			
			this.eastTabPanel.add(cartographicPanel, new AnchorLayoutData("100% 100%"));
		} // for(Entry<String, CartographicPanel> cartographicPanels : this.cartographicPanels.entrySet()) {

		this.add(this.eastTabPanel);
		
	} // public MultiCartographicPanel() {

	/**
	 * getter on eastTabPanel
	 * 
	 * @return TabPanel : the east TabPanel
	 */
	public TabPanel getEastTabPanel() {
		return this.eastTabPanel;
	}

	/**
	 * getter on CartographicPanel
	 * 
	 * @return SortedMap<String, CartographicPanel> : the west TabPanels
	 */
	public SortedMap<String, CartographicPanel> getCartographicPanels() {
		return this.cartographicPanels;
	}
	
	/**
	 * getter on one CartographicPanel
	 * 
	 * @param _identifier (String) :  tab panel identifier
	 * 
	 * @return CartographicPanel : one west TabPanel
	 */
	public CartographicPanel getCartographicPanel(String _identifier) {
		return this.cartographicPanels.get(_identifier);
	}

} // class
