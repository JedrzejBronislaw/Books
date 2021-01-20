package jedrzejbronislaw.ksiegozbior.controllers.listpreviews;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.util.Callback;
import jedrzejbronislaw.ksiegozbior.model.entities.Ent;
import jedrzejbronislaw.ksiegozbior.model.entities.HierarhicalEnt;

@Component
public class TreePreviewController extends MultiEntityViewController implements Initializable {
	
	
	@FXML
	private Label title;
	@FXML
	private TreeView<EntWithLabel> tree;
	

	@Override
	public void set(String header, List<EntWithLabel> elements) {
		title.setText(header);
		listRefresh(elements);
	}

	@Override
	protected void listRefresh(List<EntWithLabel> elements) {	
		TreeItem<EntWithLabel> root = new TreeItem<EntWithLabel>();
		tree.setRoot(root);
		tree.setShowRoot(false);
		boolean found;
		HierarhicalEnt hEnt;
		TreeItem<EntWithLabel> item2;
		
		if(elements == null) return;
		
		List<TreeItem<EntWithLabel>> items = new ArrayList<TreeItem<EntWithLabel>>();
		
		for(EntWithLabel e : elements) {
			if(e.getEntity() instanceof HierarhicalEnt)
				items.add(new TreeItem<EntWithLabel>(e));
		}
		
		for(TreeItem<EntWithLabel> item : items) {
			hEnt = (HierarhicalEnt)item.getValue().getEntity();
			
			found = false;	

			if(hEnt.getSuper() != null)
				for(int i=0; i<items.size(); i++) {
					item2 = items.get(i);
					if(((HierarhicalEnt)item2.getValue().getEntity()).getId() == hEnt.getSuper().getId()) {
						item2.getChildren().add(item);
						found = true;
						break;
					}
				}
			
			if(!found)
				root.getChildren().add(item);
		}
			
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		tree.setCellFactory(new Callback<TreeView<EntWithLabel>, TreeCell<EntWithLabel>>() {
			
			@Override
			public TreeCell<EntWithLabel> call(TreeView<EntWithLabel> arg0) {
				// TODO Auto-generated method stub
				return new TreeCell<EntWithLabel>() {
					@Override
					protected void updateItem(EntWithLabel element, boolean empty) {
						// TODO Auto-generated method stub
						super.updateItem(element, empty);
						if(!empty || element != null)
							setText(element.getLabel());
						else
							setText(null);
					}
				};
			}
		});
		
	}

	@Override
	protected Ent getSelectedItem() {
		return tree.getSelectionModel().getSelectedItem().getValue().getEntity();
	}
	@Override
	protected boolean isSelectedItem() {
		return (tree.getSelectionModel().getSelectedIndex() > -1);
	}


}
