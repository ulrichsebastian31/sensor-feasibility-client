package com.astrium.faceo.client.rpc.cartography;

import java.util.Map;

import com.astrium.faceo.client.bean.cartography.CartoToponymBean;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * @author astrium
 *
 */
@RemoteServiceRelativePath("GetToponymsService")
/**
 * 
 * @author ASTRIUM
 *
 */
public interface GetToponymsService extends RemoteService {

	/**
     * Cette m&eacute;thode permet de r&eacute;aliser de rechercher
     * les latitudes et longitudes d'un lieu en utilisant
     * un service web de Yahoo
     * 
     * @param _action (String) 				: operation ('GetToponyms')
     * @param _toponymToSearch (String) 	: lieu &agrave; rechercher
     * 
     * @return Map<Integer, CartoToponymBean> : les latitudes et longitudes des lieux trouv&eacute;s
     */
	public Map<Integer, CartoToponymBean> getToponyms(String _action, String _toponymToSearch);
	
}
