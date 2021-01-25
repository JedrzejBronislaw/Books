package jedrzejbronislaw.ksiegozbior.view;

import javafx.scene.layout.Pane;
import jedrzejbronislaw.ksiegozbior.controllers.listpreviews.MultiEntityViewControllerStrategy;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
public class PaneSet {
	
	public enum MultiEntityViewType{NONE, LIST, TREE};
	
	@NonNull Pane addPane;
	@NonNull Pane listPane;
	@NonNull Pane previewPane;
	@NonNull MultiEntityViewType multiEntityViewType;
	         MultiEntityViewControllerStrategy multiEntityPreviewStrategy;
}
