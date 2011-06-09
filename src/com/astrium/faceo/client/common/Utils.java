package com.astrium.faceo.client.common;

import com.astrium.faceo.client.service.FacadeUtil;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.xml.client.DOMException;
import com.gwtext.client.widgets.Window;
import com.gwtext.client.widgets.form.NumberField;

/**
 * <B>SITE FACEO</B> <BR>
 * 
 * <P>
 * La classe Utils propose des classes utilitaires
 * </P>
 * </P>
 * 
 * @author GR
 * @version 1.0, le 23/10/2008
 */
public class Utils {

	/**
	 * constructor
	 */
	public Utils() {
		super() ;
	} // 

	/**
	 * fonction permettant d'afficher un &eacute;l&eacute;ment DIV
	 * au dessus d'une l'applet World Wind 
	 * en utilisant un &eacute;l&eacute;ment IFRAME
	 *
	 *	@param _show (boolean)		: true si on affiche le DIV, false sinon
	 *	@param _idWindow (String)	: identifiant de l'&eacute;l&eacute;ment DIV &agrave; afficher
	 *	@param _idIframe (String)	: identifiant de l'&eacute;l&eacute;ment IFRAME utilis&eacute;
	 */	
	public static void putWindowTofront (Boolean _show, String _idWindow, String _idIframe) {
		try {
			Element divIframeElement = DOM.getElementById(_idWindow);
			Element iframeElement = DOM.getElementById(_idIframe);

			int eltDIVzIndex = Integer.valueOf(divIframeElement.getStyle().getZIndex());
			if (eltDIVzIndex < 1) {
				eltDIVzIndex = 1000;
			}
			eltDIVzIndex--;

			if(_show) {
				try {
					Style divIframeElementStyle = divIframeElement.getStyle();
					divIframeElementStyle.setDisplay(Style.Display.BLOCK);
					divIframeElementStyle.setVisibility(Visibility.VISIBLE);
										
					Style iframeElementStyle = iframeElement.getStyle();
					
					iframeElementStyle.setPosition(Style.Position.ABSOLUTE);
					
					iframeElementStyle.setProperty("top", divIframeElementStyle.getProperty("top"));
					iframeElementStyle.setProperty("left", divIframeElementStyle.getProperty("left"));

					iframeElementStyle.setWidth(divIframeElement.getOffsetWidth(), Unit.PX);
					iframeElementStyle.setHeight(divIframeElement.getOffsetHeight(), Unit.PX);
					
					iframeElementStyle.setZIndex(eltDIVzIndex);
					iframeElementStyle.setDisplay(Style.Display.BLOCK);

					iframeElement.setAttribute("frameborder", "0");
					iframeElement.setAttribute("src", "javascript:false;");
				} catch (DOMException dOMException) {
					com.google.gwt.user.client.Window.alert("DOMException : putWindowDIVTofront1 : " + dOMException.getLocalizedMessage());
				} catch (NumberFormatException exception) {
					com.google.gwt.user.client.Window.alert("NumberFormatException : putWindowDIVTofront1 : " + exception.getLocalizedMessage());
				} finally {
				}
			} else {
				try {
					Style divIframeElementStyle = divIframeElement.getStyle();
					divIframeElementStyle.setDisplay(Style.Display.NONE);
					divIframeElementStyle.setVisibility(Visibility.HIDDEN);

					Style iframeElementStyle = iframeElement.getStyle();
					iframeElementStyle.setDisplay(Style.Display.NONE);
										
				} catch (DOMException dOMException) {
					com.google.gwt.user.client.Window.alert("DOMException : putWindowDIVTofront2 : " + dOMException.getLocalizedMessage());
				} catch (NumberFormatException exception) {
					com.google.gwt.user.client.Window.alert("NumberFormatException : putWindowDIVTofront2 : " + exception.getLocalizedMessage());
				} finally {
				}
			}
		} catch (DOMException dOMException) {
			com.google.gwt.user.client.Window.alert("DOMException : putWindowDIVTofront3 : " + dOMException.getLocalizedMessage());
		} catch (NumberFormatException exception) {
			com.google.gwt.user.client.Window.alert("NumberFormatException : putWindowDIVTofront3 : " + exception.getLocalizedMessage());
		} catch (Exception exception) {
			// com.google.gwt.user.client.Window.alert("Exception : putWindowDIVTofront4 : " + exception);
		} finally {
		}
	} //  

	/**
	 * fonction permettant d'afficher un &eacute;l&eacute;ment DIV
	 * au dessus d'une l'applet World Wind 
	 * en utilisant un &eacute;l&eacute;ment IFRAME
	 *
	 *	@param _show (boolean)			: true si on affiche le DIV, false sinon
	 *	@param _dragElement (Element) 	: &eacute;l&eacute,ment dragg&eacute;
	 *	@param _idIframe (String) 		: identifiant de l'&eacute;l&eacute;ment IFRAME utilis&eacute;
	 */	
	public static void putDragAndDropTofront(Boolean _show, Element _dragElement, String _idIframe) {
		if (_dragElement != null) {
			Element iframeElement = DOM.getElementById(_idIframe);

			int left = _dragElement.getAbsoluteLeft() + 5;
			int top = _dragElement.getAbsoluteTop();
			int width = _dragElement.getOffsetWidth();
			int height = _dragElement.getOffsetHeight();

			int eltDIVzIndex = _dragElement.getPropertyInt("z-index");
			if (eltDIVzIndex < 1) {
				eltDIVzIndex = 1000;
			}
			eltDIVzIndex--;

			setIframe(_show, iframeElement, _idIframe, eltDIVzIndex, 
					left, top, (width - 5), (height - 5));
		} // if (_dragElement != null) {
	} //  {

	/**
	 * fonction permettant d'afficher un &eacute;l&eacute;ment DIV
	 * au dessus d'une l'applet World Wind 
	 * en utilisant un &eacute;l&eacute;ment IFRAME
	 *
	 *	@param _show (boolean)			: true si on affiche le DIV, false sinon
	 *	@param _menuElement (Element) 	: &eacute;l&eacute;ment menu
	 *	@param _idIframe (String) 		: identifiant de l'&eacute;l&eacute;ment IFRAME utilis&eacute;
	 */	
	public static void putMenuTofront (Boolean _show, Element _menuElement, String _idIframe) {
		if (_menuElement != null) {
			Element iframeElement = DOM.getElementById(_idIframe);

			int left = _menuElement.getAbsoluteLeft();
			int top = _menuElement.getAbsoluteTop();
			int width = _menuElement.getOffsetWidth();
			int height = _menuElement.getOffsetHeight();
			
			int deltaX = 2;
			int deltaWidth = deltaX + 5;
			int deltaY = 2;
			if (FacadeUtil.isMSIE6()) {
				deltaWidth += 5;
				deltaY += 3;
			}

			int eltDIVzIndex = _menuElement.getPropertyInt("z-index");
			if (eltDIVzIndex < 1) {
				eltDIVzIndex = 1000;
			}
			eltDIVzIndex--;

			setIframe(_show, iframeElement, _idIframe, eltDIVzIndex, 
					(left + deltaX), top, (width - deltaWidth), (height - deltaY));
		} // if (_menuElement != null) {
	} // 

	/**
	 * fonction permettant d'afficher un &eacute;l&eacute;ment DIV
	 * au dessus d'une l'applet World Wind 
	 * en utilisant un &eacute;l&eacute;ment IFRAME
	 *
	 *	@param  _show (Boolean)			: true si on affiche le DIV, false sinon
	 *	@param _element (Element)		: element to show
	 *	@param _idIframe (String)		: identifiant de &eacute;l&eacute;ment IFRAME utilis&eacute;
	 *	@param _eltDIVzIndex (int)		: zIndex of the element
	 *	@param _left (int)				: left position of the element
	 *	@param _top (int)				: top position of the element
	 *	@param _width (int)				: width position of the element
	 *	@param _height (int)			: height position of the element
	 */	
	private static void setIframe(Boolean _show, Element _element, 
			String _idIframe, int _eltDIVzIndex, int _left, int _top, int _width, int _height) {
		Element iframeElement = DOM.getElementById(_idIframe);

		if(_show) {
			try {
				Style divIframeElementStyle = _element.getStyle();
				divIframeElementStyle.setDisplay(Display.BLOCK);
				divIframeElementStyle.setVisibility(Visibility.VISIBLE);

				Style iframeElementStyle = iframeElement.getStyle();
				
				iframeElementStyle.setWidth(_width, Unit.PX);
				iframeElementStyle.setHeight(_height, Unit.PX);
				iframeElementStyle.setTop(_top, Unit.PX);
				iframeElementStyle.setLeft(_left, Unit.PX);

				iframeElementStyle.setZIndex(_eltDIVzIndex);
				iframeElementStyle.setDisplay(Display.BLOCK);
			} catch (DOMException dOMException) {
				com.google.gwt.user.client.Window.alert("DOMException : setIframe1 : " + dOMException);
			} catch (Exception exception) {
				com.google.gwt.user.client.Window.alert("Exception : setIframe1 : " + exception);
			} finally {
			}
		} else {
			try {
				Style divIframeElementStyle = _element.getStyle();
				divIframeElementStyle.setDisplay(Display.NONE);
				divIframeElementStyle.setVisibility(Visibility.HIDDEN);

				Style iframeElementStyle = iframeElement.getStyle();
				iframeElementStyle.setDisplay(Display.NONE);
			} catch (DOMException dOMException) {
				com.google.gwt.user.client.Window.alert("DOMException : setIframe2 : " + dOMException);
			} catch (Exception exception) {
				com.google.gwt.user.client.Window.alert("Exception : setIframe2 : " + exception);
			} finally {
			}
		}
	} // 

	/**
	 * set the NumberFields for the AOI definition
	 * 
	 * @param _numberField (NumberField): the NumberField to define
	 * @param _id (String)				: NumberField's id
	 * @param _value (String)			: NumberField's value
	 * @param _hideLabel (boolean)		: true to hide the NumberField's label
	 * @param _precision (int)			: decimal precision
	 * @param _minValue (int)			: minimum value
	 * @param _maxValue (int)			: maximum value
	 */
	public static void setAOINumberField(NumberField _numberField, String _id, String _value, boolean _hideLabel,
			int _precision, int _minValue, int _maxValue) {
		_numberField.setId(_id);
		_numberField.setAllowBlank(false);
		_numberField.setValue(_value);
		_numberField.setDecimalPrecision(_precision);
		_numberField.setMinValue(_minValue);
		_numberField.setMaxValue(_maxValue);
		_numberField.setHideLabel(_hideLabel);
	} // public static void setAOINumberField

	/**
	 * This function restores the window's position on top if it is partially invisible
	 * 
	 * @param _left (int) : left window's position
	 * @param _top (int) : top window's position
	 * @param _window (Window) : the window to move vertically to top = 0
	 */
	public static void resetWindowTop(int _left, int _top, Window _window) {
		if (_top < 0)
			_window.setPosition(_left, 0);
	} // 
	
	/**
	 * Adding an 'IFRAME' HTML element on the page
	 * 
	 * @param _idElement (String) : the element's identifier
	 */
	public static void addIframeToRootElement(String _idElement) {
		com.google.gwt.user.client.Element iframeElement = DOM.createIFrame();
		iframeElement.setId(_idElement);
		iframeElement.setAttribute("style", "position:absolute; top:0px; left:0px; display:none;");
		
		DOM.appendChild(RootPanel.getBodyElement(), iframeElement);
	} // 
	
} // class
