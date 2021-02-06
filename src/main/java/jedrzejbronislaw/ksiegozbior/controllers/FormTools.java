package jedrzejbronislaw.ksiegozbior.controllers;

import java.sql.Date;

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
		return text.isBlank() ? null : text.strip();
	}
	
	public static Date getDate(DatePicker datePicker) {
		return datePicker.getValue() == null ? null : Date.valueOf(datePicker.getValue());
	}
	
	public static short parseShort(String s, short def) {
		try {
			return Short.parseShort(s);
		} catch (NumberFormatException e) {
			return def;
		}
	}

	public static long parseLong(String s, long def) {
		try {
			return Short.parseShort(s);
		} catch (NumberFormatException e) {
			return def;
		}
	}
}
