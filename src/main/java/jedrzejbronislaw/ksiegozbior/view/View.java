package jedrzejbronislaw.ksiegozbior.view;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import jedrzejbronislaw.ksiegozbior.controllers.lists.ListManager;
import jedrzejbronislaw.ksiegozbior.controllers.lists.MultiEntityPreview;
import jedrzejbronislaw.ksiegozbior.lang.Internationalization;
import jedrzejbronislaw.ksiegozbior.view.PaneSet.MultiEntityViewType;
import lombok.NonNull;
import lombok.Setter;

@Component
public class View {
	
	@Setter @NonNull private Pane addPane;
	@Setter @NonNull private Pane listPane;
	@Setter @NonNull private Pane previewPane;
	@Setter @NonNull private Label header;

	private Map<Views, PaneSet> paneMap = new HashMap<>();
	private Map<MultiEntityViewType, MultiEntityPreview> multiEntityViews = new HashMap<>();
	
	
	public PaneSet addPanes(Views view, PaneSet panes) {
		return paneMap.put(view, panes);
	}

	public MultiEntityPreview addMultiEntityView(MultiEntityViewType key, MultiEntityPreview multiView) {
		return multiEntityViews.put(key, multiView);
	}
	

	public boolean setView(Views view) {
		PaneSet panes = paneMap.get(view);
		if (panes == null)  return false;

		String headerStr = getHeaderStr(view);
		header.setText(headerStr);
		
		addPane    .getChildren().clear();
		previewPane.getChildren().clear();
		listPane   .getChildren().clear();
		
		addPane    .getChildren().add(panes.addPane);
		previewPane.getChildren().add(panes.previewPane);
		
		MultiEntityPreview multiView = multiEntityViews.get(panes.multiEntityViewType);
		if (multiView != null) {
			listPane.getChildren().add(multiView);
		
			ListManager listManager = panes.listManager;
			multiView.setListManager(listManager);
			multiView.setContent(headerStr, listManager.get());
		}
		
		return true;
	}
	
	private String getHeaderStr(Views view) {
		return Internationalization.get(view.toString().toLowerCase(), view.toString());
	}
}
