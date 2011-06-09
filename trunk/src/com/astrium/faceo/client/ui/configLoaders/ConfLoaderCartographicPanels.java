package com.astrium.faceo.client.ui.configLoaders;

import java.util.Map;

import com.astrium.faceo.client.common.GeneralConstant;
import com.astrium.faceo.client.ui.CartographicPanel;
import com.astrium.faceo.client.ui.HomePage;
import com.astrium.faceo.client.ui.cartography.cartoLayers.CartoLayersPanel;

/**
 * <B>SITE FACEO</B> <BR>
 * 
 * <P>
 * ConfLoaderPanels class manages the 'FACEO' GUI
 * </P>
 * </P>
 * 
 * @author ASTRIUM
 * @version 1.0, le 04/05/2010
 */
public class ConfLoaderCartographicPanels {

    /**
     * Cr&eacute;ation de l'instance au niveau de la variable.
     */
    private static final ConfLoaderCartographicPanels instance = 
    	new ConfLoaderCartographicPanels();

    /**
     * La présence d'un constructeur priv&eacute; supprime
     * le constructeur public par d&eacute;faut.
     */
    private ConfLoaderCartographicPanels() {
    }
    
    /**
     * @param _cartographicPanels (Map<String, CartographicPanel>) : panels for 'CartographicPanel'
     */
    public void getCartographicPanels(Map<String, CartographicPanel> _cartographicPanels) {
    	
		//------------------------ Carto Layers Panel --------------------------
		if (HomePage.getHomePageBean().getConfigBean().getLayersPanel()) {
			_cartographicPanels.put(GeneralConstant.CARTO_LAYERS_PANEL_ID,
					new CartoLayersPanel(GeneralConstant.CARTO_LAYERS_PANEL_ID));
		} // if (HomePage.getHomePageBean().getConfigBean().getLayersPanel()) {

		//--------------------------------------------------------							

   } // public void getCartographicPanels(Map<String, CartographicPanel> _cartographicPanels) {

    /**
     * Dans ce cas pr&eacute;sent, le mot-cl&eacute; synchronized n'est pas utile.
     * L'unique instanciation du singleton se fait avant
     * l'appel de la m&eacute;thode getInstance(). Donc aucun risque d'acc&egrave;s concurrents.
     * Retourne l'instance du singleton.
     * 
     * @return ConfLoaderCartographicPanels
     */
    public final static ConfLoaderCartographicPanels getInstance() {
        return instance;
    }

} // class
