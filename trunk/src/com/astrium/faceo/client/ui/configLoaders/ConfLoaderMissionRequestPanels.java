package com.astrium.faceo.client.ui.configLoaders;

import java.util.Map;

import com.astrium.faceo.client.common.GeneralConstant;
import com.astrium.faceo.client.ui.HomePage;
import com.astrium.faceo.client.ui.MissionRequestPanel;
import com.astrium.faceo.client.ui.programming.sps2.view.SpsPanels;

/**
 * <B>SITE FACEO HMA-FO</B> <BR>
 * 
 * <P>
 * ConfLoaderPanels class manages the 'FACEO' GUI
 * </P>
 * </P>
 * 
 * @author ASTRIUM
 * @version 1.0, le 04/05/2010
 */
public class ConfLoaderMissionRequestPanels {

    /**
     * Cr&eacute;ation de l'instance au niveau de la variable.
     */
    private static final ConfLoaderMissionRequestPanels instance = 
    	new ConfLoaderMissionRequestPanels();

    /**
     * La présence d'un constructeur priv&eacute; supprime
     * le constructeur public par d&eacute;faut.
     */
    private ConfLoaderMissionRequestPanels() {
    }
    
    /**
     * @param _missionRequestPanels (Map<String, MissionRequestPanel>) : panels for 'MultiMissionsRequestPanel'
     */
    public void getMissionsRequestPanels(Map<String, MissionRequestPanel> _missionRequestPanels) {
    	
	   	//------------------------ SPS 2.0 Panels --------------------------
		if (HomePage.getHomePageBean().getConfigBean().getSps2Panel()) {
  				_missionRequestPanels.put(GeneralConstant.SPS_PANEL_ID, 
  						new SpsPanels(GeneralConstant.SPS_PANEL_ID, GeneralConstant.WEST_PANEL_WIDTH - 15));
		} // if (homePageBean.getConfigBean().getSps2Panel()) {

   } // public void getMissionsRequestPanels(Map<String, MissionRequestPanel> _missionRequestPanels) {

    /**
     * Dans ce cas pr&eacute;sent, le mot-cl&eacute; synchronized n'est pas utile.
     * L'unique instanciation du singleton se fait avant
     * l'appel de la m&eacute;thode getInstance(). Donc aucun risque d'acc&egrave;s concurrents.
     * Retourne l'instance du singleton.
     * 
     * @return ConfLoaderMissionRequestPanels
     */
    public final static ConfLoaderMissionRequestPanels getInstance() {
        return instance;
    }

} // class
