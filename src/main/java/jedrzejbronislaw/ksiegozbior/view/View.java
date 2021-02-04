package jedrzejbronislaw.ksiegozbior.view;

import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import jedrzejbronislaw.ksiegozbior.controllers.listpreviews.MultiEntityViewController;
import jedrzejbronislaw.ksiegozbior.lang.Internationalization;
import jedrzejbronislaw.ksiegozbior.view.PaneSet.MultiEntityViewType;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class View {
	
	@NonNull private Pane addPane;
	@NonNull private Pane listPane;
	@NonNull private Pane previewPane;
	@NonNull private Label header;

	
	private Map<Views, PaneSet> paneMap = new HashMap<>();
	private Map<MultiEntityViewType, MultiEntityViewController> multiEntityViews = new HashMap<>();
	
	public boolean addPanes(Views view, PaneSet panes) {
		boolean outcome = paneMap.containsKey(view);
		paneMap.put(view, panes);		
		return outcome;
	}

	public boolean addMultiEntityView(MultiEntityViewType key, MultiEntityViewController multiView) {
		boolean outcome = multiEntityViews.containsKey(key);
		multiEntityViews.put(key, multiView);
		return outcome;
	}
	

	public boolean setView(Views view) {
		PaneSet viewPanes = paneMap.get(view);
		if(viewPanes == null)  return false;

		
		addPane    .getChildren().clear();
		listPane   .getChildren().clear();
		previewPane.getChildren().clear();
		
		addPane    .getChildren().add(viewPanes.addPane);
		previewPane.getChildren().add(viewPanes.previewPane);
		
		MultiEntityViewController multiView = multiEntityViews.get(viewPanes.multiEntityViewType);
		if(multiView != null)
			listPane.getChildren().add(multiView);
		
		
		String headerStr = getHeaderStr(view);
		header.setText(headerStr);
		
		multiView.setStrategy(viewPanes.multiEntityPreviewStrategy);
		multiView.setContent(headerStr, viewPanes.multiEntityPreviewStrategy.getList());
		
		
		return true;
	}
	
	private String getHeaderStr(Views view) {
		String headerStr;
		
		try {
			headerStr = Internationalization.get(view.toString().toLowerCase());
		}catch(MissingResourceException e) {
			headerStr = view.toString();
		}
		
		return headerStr;
	}
}
