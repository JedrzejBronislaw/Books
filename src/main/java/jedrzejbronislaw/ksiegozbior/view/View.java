package jedrzejbronislaw.ksiegozbior.view;

import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
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
	private Map<MultiEntityViewType, PanePlusControl> multiEntityViews = new HashMap<>();
	
	public boolean addPanes(Views view, PaneSet panes) {
		boolean outcome = paneMap.containsKey(view);
		paneMap.put(view, panes);		
		return outcome;
	}

	public boolean addMultiEntityView(MultiEntityViewType key, PanePlusControl panePlusControl) {
		boolean outcome = multiEntityViews.containsKey(key);
		multiEntityViews.put(key, panePlusControl);
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
		
		PanePlusControl multi = multiEntityViews.get(viewPanes.multiEntityViewType);
		if(multi != null)
			listPane.getChildren().add(multi.pane);
		
		
		String headerStr = getHeaderStr(view);
		header.setText(headerStr);
		
		multi.controller.setStrategy(viewPanes.multiEntityPreviewStrategy);
		multi.controller.set(headerStr, viewPanes.multiEntityPreviewStrategy.getLabeledList());
		
		
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
