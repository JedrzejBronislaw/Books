package jedrzejbronislaw.ksiegozbior.view;

import javafx.scene.layout.Pane;
import jedrzejbronislaw.ksiegozbior.controllers.listpreviews.MultiEntityViewController;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PanePlusControl{
	@NonNull
	Pane pane;
	@NonNull
	MultiEntityViewController controller;
}
