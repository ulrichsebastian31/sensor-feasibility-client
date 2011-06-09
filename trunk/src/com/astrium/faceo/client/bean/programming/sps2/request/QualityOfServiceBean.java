package com.astrium.faceo.client.bean.programming.sps2.request;/* * @(#)QualityOfServiceBean.java	 1.0  20/04/2010 * * * PROJET       : SITE FACEO * * LANGUAGE     : Java * * DESCRIPTION  : Cette classe est un conteneur d'informations pour les requ&ecirc;tes  * des messages XML de l'op&eacute;ration 'GetFeasibility' du standard OGC SPS 2.0 * Elle contient les informations relatives &agrave la qualit&eacute; de service. *  * Levels of Urgency and Priority of the programming request. * * CREATION : * --------------------------------------------------------------------- * | Date       | Version | Description                                | * --------------------------------------------------------------------- * | 20/04/2010 |    1.0  |                                            | * --------------------------------------------------------------------- * * MODIFICATION : * --------------------------------------------------------------------- * | Date       | Version | Description                                | * --------------------------------------------------------------------- * |            |         |                                            | * --------------------------------------------------------------------- * *//* * sample of GetFeasibility Request *  *         *     <eo:QualityOfService>            <eo:Priority>HIGH</eo:Priority>       </eo:QualityOfService> */// importimport java.io.Serializable;/** * <B>SITE FACEO</B> <BR> *  * <P> * Cette classe est un conteneur d'informations pour les informations relatives &agrave  * la qualit&eacute; de service * </P> * Levels of Urgency and Priority of the programming request. * </P> *  * @author  GR * @version 1.0, le 20/04/2010 */public class QualityOfServiceBean implements Serializable {	/**	 * 	 */	private static final long serialVersionUID = -7414497781609688842L;	/** PriorityLevel	 *	 * STANDARD or HIGH = Level of priority previously agreed upon with provider	 */	private String priorityLevel = null;	/**	 * debut des methodes	 	 * 	 * Constructeur par defaut : vide	 */	public QualityOfServiceBean() {	}	/** getters */		/** 	 * getter on priorityLevel	 * 	 * @return String : priority level	*/	public String getPriorityLevel() {		return (this.priorityLevel != null) ? this.priorityLevel : "";	}	/** setters */		/** 	 * setter on priorityLevel	 * 	 * @param _priorityLevel (String): priority level value	*/	public void setPriorityLevel(String _priorityLevel) {		this.priorityLevel = _priorityLevel;	}} // class