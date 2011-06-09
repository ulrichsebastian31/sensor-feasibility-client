package com.astrium.faceo.client.common.programming.sps2;

//import

/**
 * <B>SITE FACEO</B> <BR>
 * 
 * <P>
 * Cette classe stocke des constantes 'color' (HSB, RGB) non localisables
 * </P>
 * </P>
 * 
 * @author GR 
 * @version 1.0, le 22/01/2011
 **/
public class Sps2ColorsConstants {

	/** ************************************ */
	/* 	    Segment Status HSB color 		 */
	/** ************************************ */	
	/** */
	static final public float[] DEFAULT_HSB = {0.78f, 0.75f, 0.80f};
	/** */
	static final public float[] POTENTIAL_HSB = {0.44f, 0.50f, 0.80f};
	/** */
	static final public float[] PLANNED_HSB = {0.33f, 0.75f, 0.79f};
	/** */
	static final public float[] VALIDATED_HSB = {0.35f, 0.63f, 0.63f};
	/** */
	static final public float[] CANCELLED_HSB = {0.12f, 1.0f, 0.96f};
	/** */
	static final public float[] FAILED_HSB = {0.07f, 1.0f, 0.96f};
	/** */
	static final public float[] ACQUIRED_HSB = {0.33f, 1.0f, 0.96f};
	/** */
	static final public float[] REJECTED_HSB = {0.0f, 1.0f, 0.96f};

	/** *************************************** */
	/* 	   Segment Status Hexdecimal color  	*/
	/** *************************************** */	
	/** */
	static final public String DEFAULT_HEXA = "#9932CC";
	/** */
	static final public String POTENTIAL_HEXA = "#66CDAA";
	/** */
	static final public String PLANNED_HEXA = "#32CD32";
	/** */
	static final public String VALIDATED_HEXA = "#3CB371";
	/** */
	static final public String CANCELLED_HEXA = "#FFD700";
	/** */
	static final public String FAILED_HEXA =  "#FFA500";
	/** */
	static final public String ACQUIRED_HEXA =  "#00FF00";
	/** */
	static final public String REJECTED_HEXA =  "#FF0000";

} // class
