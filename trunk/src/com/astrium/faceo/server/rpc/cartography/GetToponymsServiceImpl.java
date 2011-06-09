package com.astrium.faceo.server.rpc.cartography;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import yahooMaps.ResultSetDocument;
import yahooMaps.ResultSetDocument.ResultSet;

import com.astrium.faceo.client.bean.cartography.CartoToponymBean;
import com.astrium.faceo.client.common.GeneralConstant;
import com.astrium.faceo.client.rpc.cartography.GetToponymsService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.gwt.xml.client.DOMException;
import com.google.gwt.xml.client.impl.DOMParseException;

/**
* Server side implementation of the GetToponymsService interface.
*
* @author ASTRIUM
*
*/
public class GetToponymsServiceImpl extends RemoteServiceServlet implements GetToponymsService {

	private static final long serialVersionUID = 2L;
	
    /** Le logger de cette classe */
	private static Logger log = Logger.getLogger(GetToponymsServiceImpl.class);

    String INFOS_CLASSE = "GetToponymsServiceImpl";

	/**
	 * @param _request (HttpServletRequest)		: the request
	 * @param _response (HttpServletResponse)	: the response
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doGet (HttpServletRequest _request, HttpServletResponse _response)
	throws ServletException, IOException {
	}

	/**
	 * 
     * Cette m&eacute;thode permet de r&eacute;aliser de rechercher
     * les latitudes et longitudes d'un lieu en utilisant
     * un service web de Yahoo
     * 
     * @param _action (String) 				: operation ('GetToponyms')
     * @param _toponymToSearch (String) 	: lieu &agrave; rechercher
     * 
     * @return Map<Integer, CartoToponymBean> : les latitudes et longitudes des lieux trouv&eacute;s
     *
	 * The annotation indicates that the returned List will only 
	 * contain objects of  type <com.astrium.faceo.bean.ToponymBean>
	 * 
	 * @gwt.typeArgs <com.astrium.faceo.client.bean.ToponymBean>
	 */
	public Map<Integer, CartoToponymBean> getToponyms(String _action, String _toponymToSearch) {
		
		Map<Integer, CartoToponymBean> hashMapToponymBean = new HashMap<Integer, CartoToponymBean>();

     	// recuperation des parametres
    	String messageErreur = null;
    	int code_erreur = 0;

    	// verification des parametres
    	log.info("\n" + INFOS_CLASSE + " : action : " + _action );
     	if (_action == null) {
     		messageErreur = "undefined action";
    	    code_erreur = 531;
     	} else {
         	if (!_action.equalsIgnoreCase(GeneralConstant.TOPONYMS_GET_TOPONYMS)) {
         	    messageErreur = "bad action";
        	    code_erreur = 532;
     		} else { 
	         	if (_toponymToSearch == null){
	         	    messageErreur = "undefined toponym";
	        	    code_erreur = 533;
	     		}
     		}
     	} // if (action == null){
     	     	
     	// redirection vers la page d'erreur, "action inconnue"
        if (messageErreur != null ) {
       		// retourne le message d'erreur
   			log.info("\n" + INFOS_CLASSE + " : pb : " + messageErreur);    
   			CartoToponymBean toponymBean = new CartoToponymBean();
   			toponymBean.setErrorMessage("error : " + messageErreur);
   			toponymBean.setErrorCode(code_erreur);
			hashMapToponymBean.put(0, toponymBean);
	   		return hashMapToponymBean;
       } // if (messageErreur != null ) {
	     
        // redirection vers une page (jsp) ou un controleur (servlet)
		HttpURLConnection httpConnection = null;
		InputStream httpInputStream = null;
		BufferedReader entree = null;

        try {
   			log.info("\n" + INFOS_CLASSE + " : toponymToSearch : " + _toponymToSearch);    
 
//			String _url = "http://local.yahooapis.com/MapsService/V1/geocode?appid=+ " +
//			"YD-f7BUYpg_JX25g8v.EmGtMxpfMhpX2XIz17DeSzXV&location=" + 
//			_toponymToSearch.replaceAll(" ", "+");
			String adress = "http://local.yahooapis.com/MapsService/V1/geocode" +
			"?appid=YD-f7BUYpg_JX25g8v.EmGtMxpfMhpX2XIz17DeSzXV&location=" + 
            _toponymToSearch.replaceAll(" ", "+");
			
	        try {
				URL httpUrl = new URL(adress);
				httpConnection = (HttpURLConnection)httpUrl.openConnection();
	//				httpConnection.setReadTimeout(_timeout);
	//				httpConnection.setConnectTimeout(_timeout);
	//				httpConnection.setRequestMethod("GET") ; 
	//				httpConnection.connect();
	//				log.info("\n********************************************************");    
				if (httpConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
					log.info("\n" + INFOS_CLASSE + "httpConnection.getResponseMessage() : " + httpConnection.getResponseMessage());    
				}
					
				httpInputStream = httpConnection.getInputStream();  // Open data stream
				entree = new BufferedReader ( new InputStreamReader ( httpInputStream ));
				String line = null;
				String result = "";
				while ((line = entree.readLine()) != null) {
					result += line;
				}

// 				http://developer.yahoo.com/maps/rest/V1/geocode.html
//				The Geocoding service is limited to 5,000 queries per IP address per day. See information on rate limiting.
//				ResultSet  	Contains all of the query responses.
//				Result 	Contains each individual response. More than one result may be returned if the given address is ambiguous. Has attributes:
//
//				    * precision: The precision of the address used for geocoding, from specific street address all the way up to country, depending on the precision of the address that could be extracted. Possible values, from most specific to most general are:
//				          o address
//				          o street
//				          o zip+4
//				          o zip+2
//				          o zip
//				          o city
//				          o state
//				          o country
//				    * warning: If the exact address was not found, the closest available match will be noted here.
//
//				Latitude 	The latitude of the location.
//				Longitude 	The longitude of the location.
//				Address 	Street address of the result, if a specific location could be determined.
//				City 	City in which the result is located.
//				State 	State in which the result is located.
//				Zip 	Zip code, if known.
//				Country 	Country in which the result is located.
//				
	//			<ResultSet xsi:schemaLocation="urn:yahoo:maps http://api.local.yahoo.com/MapsService/V1/GeocodeResponse.xsd" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="urn:yahoo:maps">
	//				<Result precision="zip">
	//    				<Latitude>43.605740</Latitude>
	//    				<Longitude>1.448690</Longitude>
	//    				<Address/>
	//    				<City>Toulouse</City>
	//    				<State>France</State>
	//    				<Zip/>
	//    				<Country>FR</Country>
	//  			</Result>
	//			<ResultSet>
			
				ResultSetDocument resultSetDocument = ResultSetDocument.Factory.parse(result);
				ResultSet resultSet = resultSetDocument.getResultSet();
				List<yahooMaps.ResultType> listResults = resultSet.getResultList();
				
				//parcours de tous les resultats 
				int i = 1;
				for (yahooMaps.ResultType resultat : listResults){
					CartoToponymBean toponymBean = new CartoToponymBean();
					
					toponymBean.setLatitude(Float.parseFloat(resultat.getLatitude().toString()));

					toponymBean.setLongitude(Float.parseFloat(resultat.getLongitude().toString()));

					toponymBean.setCity(resultat.getCity());

					toponymBean.setCountry(resultat.getCountry());

					toponymBean.setState(resultat.getState());

					toponymBean.setZip(resultat.getZip());

					hashMapToponymBean.put(i, toponymBean);

					i++;
				}
			} catch (MalformedURLException malformedURLException) {
	   			log.info("\n" + INFOS_CLASSE + " : MalformedURLException : " + malformedURLException.getMessage());    
	   			CartoToponymBean toponymBean = new CartoToponymBean();
	   			toponymBean.setErrorMessage("error : " + messageErreur);
	   			toponymBean.setErrorCode(GeneralConstant.TOPONYM_MALFORMED_EXCEPTION);
				hashMapToponymBean.put(0, toponymBean);
		   		return hashMapToponymBean;
			} catch(SocketTimeoutException socketTimeoutException) {
				log.info("\n" + INFOS_CLASSE + " : SocketTimeoutException : " + socketTimeoutException.getMessage());    
	   			CartoToponymBean toponymBean = new CartoToponymBean();
	   			toponymBean.setErrorMessage("error : " + messageErreur);
	   			toponymBean.setErrorCode(GeneralConstant.TOPONYM_SOCKET_TIMEOUT_EXCEPTION);
				hashMapToponymBean.put(0, toponymBean);
		   		return hashMapToponymBean;
			} catch (IOException iOException) {
	   			log.info("\n" + INFOS_CLASSE + " : IOException : " + iOException.getMessage());    
	   			CartoToponymBean toponymBean = new CartoToponymBean();
	   			toponymBean.setErrorMessage("error : " + messageErreur);
	   			toponymBean.setErrorCode(GeneralConstant.TOPONYM_IO_EXCEPTION);
				hashMapToponymBean.put(0, toponymBean);
		   		return hashMapToponymBean;
			} catch (DOMParseException dOMParseException) {
	   			log.info("\n" + INFOS_CLASSE + " : DOMParseException : " + dOMParseException.getMessage());    
	   			CartoToponymBean toponymBean = new CartoToponymBean();
	   			toponymBean.setErrorMessage("error : " + messageErreur);
	   			toponymBean.setErrorCode(GeneralConstant.TOPONYM_DOM_PARSE_EXCEPTION);
				hashMapToponymBean.put(0, toponymBean);
		   		return hashMapToponymBean;
			} catch (DOMException dOMException) {
	   			log.info("\n" + INFOS_CLASSE + " : DOMException : " + dOMException.getMessage());    
	   			CartoToponymBean toponymBean = new CartoToponymBean();
	   			toponymBean.setErrorMessage("error : " + messageErreur);
	   			toponymBean.setErrorCode(GeneralConstant.TOPONYM_DOM_EXCEPTION);
				hashMapToponymBean.put(0, toponymBean);
		   		return hashMapToponymBean;
			} catch (Exception exception) {
				log.info("\n" + INFOS_CLASSE + " : Exception : " + exception.getMessage());    
	   			CartoToponymBean toponymBean = new CartoToponymBean();
	   			toponymBean.setErrorMessage("error : " + messageErreur);
	   			toponymBean.setErrorCode(GeneralConstant.TOPONYM_EXCEPTION);
				hashMapToponymBean.put(0, toponymBean);
		   		return hashMapToponymBean;
			} finally {
				log.info("\n" + INFOS_CLASSE + " : httpURLConnection.disconnect()");    
				httpInputStream.close();
				entree.close();
				httpConnection.disconnect();
			}

			// on retourne les resultats
			log.info("\n" + INFOS_CLASSE + " : return hashMapToponymBean ");    
    		return hashMapToponymBean;
    		
    	} catch (Exception exception) {
			log.info("\n" + INFOS_CLASSE + " : Exception : return null ");    
   			CartoToponymBean toponymBean = new CartoToponymBean();
   			toponymBean.setErrorMessage("error : " + messageErreur);
   			toponymBean.setErrorCode(GeneralConstant.TOPONYM_EXCEPTION);
			hashMapToponymBean.put(0, toponymBean);
	   		return hashMapToponymBean;
        } // try { 		

	} // public Map<Integer, ToponymBean> getToponyms(String _action, String _toponymToSearch)

} // class
