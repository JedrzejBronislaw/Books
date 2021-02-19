package jedrzejbronislaw.ksiegozbior.view;

import javafx.scene.control.Label;
import jedrzejbronislaw.ksiegozbior.lang.Internationalization;
import jedrzejbronislaw.ksiegozbior.tools.MyList;
import jedrzejbronislaw.ksiegozbior.tools.StringNumber;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MyLabel {

	private final Label label;
	

	public void setText(boolean value) {
		label.setText(Internationalization.get(value ? "yes" : "no"));
	}
	
	public void setText(StringNumber<?> value) {
		label.setText(value.str());
	}
	
	public void setText(MyList<?> value) {
		label.setText(value.serialize_newLine());
	}
}
