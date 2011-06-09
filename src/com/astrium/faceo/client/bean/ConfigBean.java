package com.astrium.faceo.client.bean;

import java.io.Serializable;

/**
 * <B>SITE FACEO</B> <BR>
 * 
 * <P>
 * The ConfigBean class offer methods to manage application configuration
 * </P>
 * </P>
 * 
 * @author Astrium : 01/04/2009
 */

public class ConfigBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8812418893601498040L;
	
	// ---------------- West Panel ----------------

	/** 
	 * westPanel : true if the panel must be on the IHM, false else
	*/
	private boolean westPanel = true;

	/** 
	 * sps2Panel : true if the panel must be on the IHM, false else
	*/
	private boolean sps2Panel = true;

	// ---------------- East Panel ----------------

	/** 
	 * eastPanel : true if the panel must be on the IHM, true else
	*/
	private boolean eastPanel = true;

	/** 
	 * layersPanel : true if the panel must be on the IHM, false else
	*/
	private boolean layersPanel = true;

	// ---------------- North Panel ----------------
	
	/** 
	 * addHelpButton : true if the button must be on the IHM, false else
	*/
	private boolean addHelpButton = true;
	
	// ---------------- Carto AOI Panel ----------------

	/** 
	 * cartoAOIPanel : true if the panel must be on the IHM, false else
	*/
	private boolean cartoAOIPanel = true;

	/** ----------------------- getters ----------------------- */
	
	// ---------------- West Panel ----------------

	/** 
	 * getter on westPanel
	 * 
	 * @return boolean : true if the panel must be on the IHM, false else
	*/
	public boolean getWestPanel() {
		return this.westPanel;
	}
	
	/** 
	 * getter on sps2Panel
	 * 
	 * @return boolean : true if the panel must be on the IHM, false else
	*/
	public boolean getSps2Panel() {
		return this.sps2Panel;
	}

	// ---------------- East Panel ----------------

	/** 
	 * getter on eastPanel
	 * 
	 * @return boolean : true if the panel must be on the IHM, false else
	*/
	public boolean getEastPanel() {
		return this.eastPanel;
	}

	/** 
	 * getter on layersPanel
	 * 
	 * @return boolean : true if the panel must be on the IHM, false else
	*/
	public boolean getLayersPanel() {
		return this.layersPanel;
	}
	
	// ---------------- North Panel ----------------

	/** 
	 * getter on addHelpButton
	 * 
	 * @return boolean : true if the button must be on the IHM, false else
	*/	
	public boolean getAddHelpButton() {
		return this.addHelpButton;
	}
	
	// ---------------- Carto AOI Panel ----------------
	
	/** 
	 * getter on cartoAOIPanel
	 * 
	 * @return boolean : true if the panel must be on the IHM, false else
	*/
	public boolean getCartoAOIPanel() {
		return cartoAOIPanel;
	}

} // class
