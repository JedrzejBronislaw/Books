package jedrzejbronislaw.ksiegozbior.view;

import javafx.scene.layout.Pane;
import jedrzejbronislaw.ksiegozbior.controllers.listpreviews.MultiEntityViewControllerStrategy;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PaneSet {
	
	public enum MultiEntityViewType{None, List, Tree};
	
	@NonNull
	Pane addPane;
	@NonNull
	Pane listPane;
	@NonNull
	Pane previewPane;
	@NonNull
	MultiEntityViewType multiEntityViewType;
	
//	@NonNull
	MultiEntityViewControllerStrategy multiEntityPreviewStrategy;
	
	public PaneSet(Pane addPane, Pane listPane, Pane previewPane, MultiEntityViewType multiEntityViewType, MultiEntityViewControllerStrategy multiEntityPreviewStrategy) {
		this.addPane = addPane;
		this.listPane = listPane;
		this.previewPane = previewPane;
		this.multiEntityViewType = multiEntityViewType;
		this.multiEntityPreviewStrategy = multiEntityPreviewStrategy;
	}
}
