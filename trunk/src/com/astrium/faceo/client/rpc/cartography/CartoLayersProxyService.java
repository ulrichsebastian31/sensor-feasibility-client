package com.astrium.faceo.client.rpc.cartography;

import com.astrium.faceo.client.ui.cartography.cartoLayers.CartoLayersData;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * @author astrium
 *
 */
@RemoteServiceRelativePath("cartoLayersProxyController.srv")
/**
 * 
 * @author ASTRIUM
 *
 */
public interface CartoLayersProxyService extends RemoteService {

	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		/**
		 * @return CartoLayersProxyServiceAsync
		 */
		public static CartoLayersProxyServiceAsync getInstance() {
			return GWT.create(CartoLayersProxyService.class);
		}
	}
	
	/**
     * This method return layers of
     * the list of the cartography's layers 
     * 
     * @param _start (int) 				: start position in the list (0 for first element)
     * @param _limit (int) 				: number of elements to return
     * @param _nbColumns (int)			: number of list's columns
     * @param _pageSize (int) 			: number maximum of elements per page
     * @param _sort (String)			: name of the field to sort
     * @param _dir (String)				: direction of the sort
     * @param _params (String[][])		: array of params
     * @param _jsonLayersList (String)	: complete JSON list to parse
     * 
     * @return CartoLayersData : callback which return the list
     */
    public CartoLayersData getCartoLayers(
    		int _start, 
    		int _limit, 
    		int _nbColumns, 
    		int _pageSize, 
    		String _sort, 
    		String _dir, 
    		String[][] _params, 
    		String _jsonLayersList
    );
    
} // class
