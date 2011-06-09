package com.astrium.faceo.client.rpc.cartography;

import java.util.Map;

import com.astrium.faceo.client.bean.cartography.CartoToponymBean;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author astrium
 *
 */
public interface GetToponymsServiceAsync {

	/**
     * Cette m&eacute;thode permet de r&eacute;aliser de rechercher
     * les latitudes et longitudes d'un lieu en utilisant
     * un service web de Yahoo
     * 
     * @param _action (String) 				: operation ('GetToponyms')
     * @param _toponymToSearch (String) 	: lieu &agrave; rechercher
     * @param _callback (Map<Integer, CartoToponymBean>) : les latitudes et longitudes des lieux trouv&eacute;s
     */
	public void getToponyms(String _action, String _toponymToSearch, AsyncCallback<Map<Integer, CartoToponymBean>> _callback);
	
}
