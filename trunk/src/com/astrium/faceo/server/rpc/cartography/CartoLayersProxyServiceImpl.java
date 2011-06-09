package com.astrium.faceo.server.rpc.cartography;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.simple.JSONObject;

import com.astrium.faceo.client.bean.cartography.CartoLayerBean;
import com.astrium.faceo.client.common.GeneralConstant;
import com.astrium.faceo.client.rpc.cartography.CartoLayersProxyService;
import com.astrium.faceo.client.ui.cartography.cartoLayers.CartoLayersData;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Server side implementation of the CartoLayersProxyService interface.
 * 
 * @author ASTRIUM
 *
 */
public class CartoLayersProxyServiceImpl extends RemoteServiceServlet implements CartoLayersProxyService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8168418819235617497L;

	/** Le logger de cette classe */
	private static Logger log = Logger.getLogger(CartoLayersProxyServiceImpl.class);

	String INFOS_CLASSE = "CartoLayersProxyServiceImpl";

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
			String _jsonLayersList) {

		_dir = "ASC".equals(_dir) ? "+" : "-";

		int total_count = 0;    

		Object[][] dataLayers = null;  
		SortedMap<Integer, CartoLayerBean> layers = new TreeMap<Integer, CartoLayerBean>();
		try {
			// parse the response text into JSON
			org.json.simple.parser.JSONParser jSONParser = 
				new org.json.simple.parser.JSONParser();

			java.io.StringReader stringReader = new java.io.StringReader(_jsonLayersList);
			Object object = jSONParser.parse(stringReader);

			org.json.simple.JSONArray jsonArray = (org.json.simple.JSONArray) object;

			if (jsonArray != null) {
				// JSON format {"layerList":  [
				// {"name": "Etoiles", "enabled": true, "opacity": 1.0, "minActiveAltitude": -1.7976931348623157E308, "maxActiveAltitude": 1.7976931348623157E308, "type": 0}, 
				// {"name": "Atmosphère", "enabled": true, "opacity": 1.0, "minActiveAltitude": -1.7976931348623157E308, "maxActiveAltitude": 1.7976931348623157E308, "type": 0}, 
				// ...
				// {"name": "Labels", "enabled": true, "opacity": 1.0, "minActiveAltitude": -1.7976931348623157E308, "maxActiveAltitude": 1.7976931348623157E308, "type": 0}, 
				// {"name": "MS Virtual Earth", "enabled": true, "opacity": 0.8, "minActiveAltitude": -1.7976931348623157E308, "maxActiveAltitude": 1.7976931348623157E308, "type": 0}] }					
				//	Types :
				// World Wind Layer 	: CARTO_WWJ_LAYER_TYPE = 0
				// Area Of Interest 	: CARTO_AOI_LAYER_TYPE = 1
				// SSE EO Catalog Layer : CARTO_SSE_EO_ATALOG_LAYER_TYPE = 2
				// Programming 			: CARTO_PROGRAMMING_LAYER_TYPE = 3
				// Orbites 				: CARTO_ORBITES_LAYER_TYPE = 4
				// Images 				: CARTO_IMAGES_LAYER_TYPE = 5
				// Labels 				: CARTO_LABELS_LAYER_TYPE = 6
				// DataDoors 			: CARTO_DATADOORS_LAYER_TYPE = 7
				// Annotations 			: CARTO_ANNOTATIONS_LAYER_TYPE = 8
				// Satellites 			: CARTO_SATELITTES_LAYER_TYPE = 9
				// Flow 				: CARTO_FLOW_LAYER_TYPE = 10
				// SIT 					: CARTO_SIT_LAYER_TYPE = 11
				// WMSTiledImageLayer 	: CARTO_WMS_TILED_IMAGE_LAYER_TYPE = 12
				// TrackLayer 			: CARTO_TRACK_LAYER_TYPE = 13
				// TrackMarkerLayer 	: CARTO_TRACK_MARKER_LAYER_TYPE = 14

				for (int i=0; i<jsonArray.size(); i++) {
					org.json.simple.JSONObject jsLayer = null;

					jsLayer = (JSONObject) jsonArray.get(i);

					if ( jsLayer != null ) {
						object = jsLayer.get("type");

						boolean addLayerToList = true;

						// verification des types des layers
						// on n'ajoute pas les layers de type 'Satellites' ('Orbites'), 'GPS' et 'WMS'
						if (Integer.valueOf(object.toString()) == GeneralConstant.CARTO_WMS_TILED_IMAGE_LAYER_TYPE)
							addLayerToList = false;

						if (addLayerToList) {
							CartoLayerBean layerBean = new CartoLayerBean();

							object = jsLayer.get("name");
							if (object != null)
								if (object.toString() != null)
									layerBean.setLayerName(object.toString());

							object = jsLayer.get("enabled");
							if (object != null)
								layerBean.setLayerEnabled(Boolean.valueOf(object.toString()));

							object = jsLayer.get("opacity");
							if (object != null)
								layerBean.setLayerTransparency(Double.valueOf(object.toString()));

							object = jsLayer.get("minActiveAltitude");
							if (object != null)
								layerBean.setLayerMinActiveAltitude(Float.valueOf(object.toString()));

							object = jsLayer.get("maxActiveAltitude");
							if (object != null)
								layerBean.setLayerMaxActiveAltitude(Float.valueOf(object.toString()));

							object = jsLayer.get("type");
							if (object != null)
								layerBean.setLayerType(Integer.valueOf(object.toString()));

							layers.put(Integer.valueOf(i), layerBean);
						} // if (addLayerToList) {
					} // if (jsLayer != null) 
				} // for (int i=0; i<jsonArray.size(); i++) {

				total_count = layers.size();

				_start = Math.min(total_count - 1, _start == -1 ? 0 : _start);
				//				_limit = Math.min(10, _limit == -1 ? 10 : _limit);
				_limit = Math.min(_pageSize, _limit == -1 ? _pageSize : _limit);
				_limit = Math.min(_limit, total_count - _start);
				log.debug("\n" + INFOS_CLASSE + " : start : " + _start);    
				log.debug("\n" + INFOS_CLASSE + " : limit : " + _limit);    
				log.debug("\n" + INFOS_CLASSE + " : pageSize : " + _pageSize);    
				log.debug("\n" + INFOS_CLASSE + " : total_count : " + total_count);    				

				int rows = layers.size();

				dataLayers = new Object[rows][_nbColumns];  
				int i = 1;
				// on inverse l'ordre des layers
				for(Entry<Integer, CartoLayerBean> layer : layers.entrySet()) {
					CartoLayerBean layerBean = layer.getValue();
					dataLayers[rows - i][0] = layerBean.getLayerName();				// "layerName"
					dataLayers[rows - i][1] = layerBean.getLayerEnabled();			// "enabled"
					dataLayers[rows - i][2] = layerBean.getLayerTransparency();		// "opacity"
					dataLayers[rows - i][3] = layerBean.getLayerMinActiveAltitude();// "minActiveAltitude"
					dataLayers[rows - i][4] = layerBean.getLayerMaxActiveAltitude();// "maxActiveAltitude"
					dataLayers[rows - i][5] = layerBean.getLayerType();				// "type"

					i++;
				} // for(Entry<Integer, LayerBean> layer : layers.entrySet()) {

			} else {
				log.info("\n" + INFOS_CLASSE + " : No layer : jsonArray == null" );    
			} // if (jsonArray != null) {
		} catch (JSONException jSONException) {
			log.info("\n" + INFOS_CLASSE + " : JSONException : " + jSONException);    
		}  catch (Exception exception) {
			log.info("\n" + INFOS_CLASSE + " : Exception : " + exception);    
		} // try        

		List<Object> items = new ArrayList<Object>();
		if (dataLayers != null) {
			for (int i = _start; i < _start + _limit; i++) {
				Object[] object = dataLayers[i];
				//				log.info("\n" + INFOS_CLASSE + " : List<Object> : ArrayList :" + i + ":" + Integer.toString(((Integer) object[5]).intValue())); 
				items.add(new String[]{
						object[0].toString(), 									// "layerName"
						Boolean.toString(((Boolean) object[1]).booleanValue()), // "enabled"
						Double.toString(((Double) object[2]).doubleValue()),	// "opacity"
						Double.toString(((Float) object[3]).floatValue()),		// "minActiveAltitude"
						Double.toString(((Float) object[4]).floatValue()),		// "maxActiveAltitude"
						Integer.toString(((Integer) object[5]).intValue()),		// "type"
				});
			} // for (int i = start; i < start + limit; i++) {
		} // if (dataLayers != null) {

		return new CartoLayersData(((String[][]) items.toArray(new String[0][])), total_count);
		
	} // public CartoLayersData getCartoLayers(

} // class
