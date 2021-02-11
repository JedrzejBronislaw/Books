package jedrzejbronislaw.ksiegozbior.controllers;

import java.sql.Date;
import java.time.LocalDate;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FormTools {

	public static String getText(TextField field) {
		return getText(field.getText());
	}

	public static String getText(TextArea field) {
		return getText(field.getText());
	}
	
	private static String getText(String text) {
		return textExists(text) ? text.strip() : null;
	}
	
	public static boolean textExists(String string) {
		return string != null && !string.isBlank();
	}
	
	public static Date getDate(DatePicker datePicker) {
		return datePicker.getValue() == null ? null : Date.valueOf(datePicker.getValue());
	}
	
	public static Short parseShort(String s, Short def) {
		try {
			return Short.parseShort(s);
		} catch (NumberFormatException e) {
			return def;
		}
	}

	public static Long parseLong(String s, Long def) {
		try {
			return Long.parseLong(s);
		} catch (NumberFormatException e) {
			return def;
		}
	}
	
	public static LocalDate localDate(Date date) {
		return (date == null) ? null : date.toLocalDate();
	}
}
