package com.astrium.faceo.client.ui.cartography.cartoLayers;

import java.io.Serializable;

/**
 * 
 * @author ASTRIUM
 *
 */
public class CartoLayersData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5156887724465806986L;

	/** */
	private String[][] data;

	/** */
	private int totalRecords;

	/**
	 * 
	 * @param _data (String[][]) : data
	 * @param _totalRecords (int):
	 */
	public CartoLayersData(String[][] _data, int _totalRecords) {
		this.data = _data;
		this.totalRecords = _totalRecords;
	}

	/**
	 * 
	 */
	public CartoLayersData() {
	}

	/**
	 * getter on data
	 * 
	 * @return String[][] : data
	 */
	public String[][] getData() {
		return this.data;
	}

	/**
	 * getter on totalRecords
	 * 
	 * @return int : total records
	 */
	public int getTotalRecords() {
		return this.totalRecords;
	}

} // class
