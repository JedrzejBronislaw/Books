package jedrzejbronislaw.ksiegozbior.view2;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import lombok.Setter;

@Component
public class ViewController {

	@Setter private BorderPane mainPane;
	
	private Map<Views, Pane> panes = new HashMap<>();


	public void register(Views view, Pane pane) {
		panes.put(view, pane);
	}
	
	public void set(Views view) {
		set(panes.get(view));
	}
	
	private void set(Pane pane) {
		if(pane == null) return;
		
		ScrollPane scrollPane = new ScrollPane(pane);
		scrollPane.setFitToWidth(true);
		mainPane.setCenter(scrollPane);
	}
}
