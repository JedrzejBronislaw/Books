package jedrzejbronislaw.ksiegozbior.view;

import javafx.scene.layout.Pane;
import jedrzejbronislaw.ksiegozbior.controllers.lists.ListManager;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
public class PaneSet {
	
	@NonNull Pane addPane;
	@NonNull Pane previewPane;
	         ListManager listManager;
}
