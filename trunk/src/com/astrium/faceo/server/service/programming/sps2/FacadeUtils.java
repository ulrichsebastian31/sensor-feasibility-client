package com.astrium.faceo.server.service.programming.sps2;

import java.util.PropertyResourceBundle;

import org.apache.log4j.Logger;

import com.astrium.faceo.client.common.programming.sps2.Sps2GeneralConstants;
import com.astrium.faceo.server.service.programming.sps2.parsing.ParsingUtils;

/**
 * <B>SITE FACEO</B> <BR>
 * 
 * <P>
 * La classe FacadeUtils 
 * </P>
 * </P>
 * 
 * @author GR
 * @version 1.0, le 24/08/2010
 */
public class FacadeUtils {

	/** Le logger de cette classe */
	private static Logger log = Logger.getLogger(FacadeUtils.class);

	private static String ORIGINE = "FacadeUtils";

	/**
	 * To get the SPS endPoint for one sensor
	 * 
	 * @param _sensorUrn (String)	: sensor URN
	 * @param _serverURL (String)	: server URL
	 * 
	 * @return String : the SPS endPoint for one sensor
	 */
	public static String getEndPoint(String _sensorUrn, String _serverURL) {

		// SOAP endpoint
		String endPointSOAP = "http://dione.deimos-space.com/HMA-FO/services/HMA-SPS";

		// lecture d'une propriete stockee dans un fichier de properties
		try {
			PropertyResourceBundle prb = 
				(PropertyResourceBundle)PropertyResourceBundle.getBundle(Sps2GeneralConstants.HMAFO_PROPERTIES);
			endPointSOAP = prb.getString("sps2Url").trim();
			log.debug("\n" + ORIGINE + " : >>> Reading file '" + Sps2GeneralConstants.HMAFO_PROPERTIES + "' OK !!!");    
		} catch (Exception e) {
			log.info("\n" + ORIGINE + " : >>> Reading file '" + Sps2GeneralConstants.HMAFO_PROPERTIES + "' KO !!!");    
		}
		log.info("\n ------------------------------------------------ ");
		log.info("\n" + ORIGINE + " : sps2 endPoint : " + endPointSOAP);
		log.info("\n ------------------------------------------------ ");

		if (! _sensorUrn.equalsIgnoreCase("")) {
			String endPoint = ParsingUtils.getEndPoint(_sensorUrn, _serverURL);
			if (! endPoint.equalsIgnoreCase("")) {
				endPointSOAP = endPoint;
			}
		} // if

		return endPointSOAP;

	} // public static String getEndPoint(String _sensorUrn, String _serverURL) {

} // class
