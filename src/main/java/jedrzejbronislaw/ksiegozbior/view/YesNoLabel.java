package jedrzejbronislaw.ksiegozbior.view;

import javafx.scene.control.Label;
import jedrzejbronislaw.ksiegozbior.lang.Internationalization;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class YesNoLabel {

	private final Label label;
	

	public void set(boolean value) {
		label.setText(Internationalization.get(value ? "yes" : "no"));
	}
}
