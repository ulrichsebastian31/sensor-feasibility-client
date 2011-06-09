package com.astrium.faceo.client.ui.utils;

import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.form.Checkbox;
import com.gwtext.client.widgets.form.ComboBox;
import com.gwtext.client.widgets.form.Field;
import com.gwtext.client.widgets.layout.FormLayout;

/**
 * <B>SITE FACEO</B> <BR>
 * 
 * <P>
 * The FieldFormatPanel class allows you to define
 * the label width in a FormPanel
 * </P>
 * </P>
 * 
 * @author GR
 * @version 1.0, le 01/10/2009
 * 
 * cf. http://www.gwt-ext.com/forum/viewtopic.php?f=2&t=3586&hilit=enter+click
 */
public class FieldFormatPanel extends Panel {

	/**
	 * constructor
	 * 
	 * @param _field
	 */
	public FieldFormatPanel(Field _field) {
		super();
		setLayout(new FormLayout());
		setBorder(false);
		add(_field);
	}

	/**
	 * define the field width
	 * 
	 * @param _field 		: the field
	 * @param _fieldWidth	: the field width
	 */
	public FieldFormatPanel(Field _field, int _fieldWidth) {
		this(_field);
		_field.setWidth(_fieldWidth);
	}

	/**
	 * define the label width of a field
	 * 
	 * @param _labelWidth	: the label width
	 * @param _field 		: the field
	 */
	public FieldFormatPanel(int _labelWidth, Field _field) {
		this(_field);
		setAttribute("labelWidth", _labelWidth, true);
	}

	/**
	 * define the field width and the label width of a field
	 * 
	 * @param _labelWidth	: the label width
	 * @param _field 		: the field
	 * @param _fieldWidth	: the field width
	 */
	public FieldFormatPanel(int _labelWidth, Field _field, int _fieldWidth) {
		this(_field);
		setAttribute("labelWidth", _labelWidth, true);
		_field.setWidth(_fieldWidth);
	}

	/**
	 * define the field width and the label width of a comboBox
	 * 
	 * @param _labelWidth		: the label width
	 * @param _comboBox 		: the comboBox
	 * @param _comboBoxWidth	: the comboBox width
	 */
	public FieldFormatPanel(int _labelWidth, ComboBox _comboBox, int _comboBoxWidth) {
		this(_comboBox);
		setAttribute("labelWidth", _labelWidth, true);
		_comboBox.setWidth(_comboBoxWidth);
	}

	/**
	 * constructor
	 * 
	 * @param _button (Button)
	 */
	public FieldFormatPanel(Button _button) {
		super();
		setLayout(new FormLayout());
		setBorder(false);
		add(_button);
	}

	/**
	 * define the field width and the label width of a button
	 * 
	 * @param _labelWidth	: the label width
	 * @param _button 		: the button
	 * @param _buttonWidth	: the field width
	 */
	public FieldFormatPanel(int _labelWidth, Button _button, String _buttonWidth) {
		this(_button);
		setAttribute("labelWidth", _labelWidth, true);
		_button.setWidth(_buttonWidth);
	}

	/**
	 * constructor
	 * 
	 * @param _checkBox (Checkbox)
	 */
	public FieldFormatPanel(Checkbox _checkBox) {
		super();
		setLayout(new FormLayout());
		setBorder(false);
		add(_checkBox);
	}

	/**
	 * define the field width and the label width of a checkBox
	 * 
	 * @param _labelWidth		: the label width
	 * @param _checkBox 		: the checkBox
	 * @param _checkBoxWidth	: the field width
	 */
	public FieldFormatPanel(int _labelWidth, Checkbox _checkBox, int _checkBoxWidth) {
		this(_checkBox);
		setAttribute("labelWidth", _labelWidth, true);
		_checkBox.setWidth(_checkBoxWidth);
	}

} // class
