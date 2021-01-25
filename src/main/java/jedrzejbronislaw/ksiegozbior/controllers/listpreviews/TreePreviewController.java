package jedrzejbronislaw.ksiegozbior.controllers.listpreviews;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.stream.Collectors;

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

	@FXML private Label title;
	@FXML private TreeView<EntWithLabel> tree;


	@Override
	public void set(String header, List<EntWithLabel> elements) {
		title.setText(header);
		listRefresh(elements);
	}

	@Override
	protected void listRefresh(List<EntWithLabel> elements) {	
		TreeItem<EntWithLabel> root = new TreeItem<>();
		
		tree.setRoot(root);
		tree.setShowRoot(false);
		
		if (elements == null) return;
		
		List<TreeItem<EntWithLabel>> items = elements.stream()
				.filter(e -> (e.getEntity() instanceof HierarhicalEnt))
				.map(TreeItem::new)
				.collect(Collectors.toList());
		
		createTree(root, items);
	}

	private void createTree(TreeItem<EntWithLabel> root, List<TreeItem<EntWithLabel>> items) {
		HierarhicalEnt ent;
		
		for (TreeItem<EntWithLabel> item : items) {
			
			ent = (HierarhicalEnt) item.getValue().getEntity();
			
			if (ent.isRoot() || !moveItemToSuperitem(items, item))
				root.getChildren().add(item);
		}
	}

	private boolean moveItemToSuperitem(List<TreeItem<EntWithLabel>> items, TreeItem<EntWithLabel> subItem) {

		for (TreeItem<EntWithLabel> item : items) {
			
			if (isSubItem(subItem, item)) {
				item.getChildren().add(subItem);
				return true;
			}
		}
		
		return false;
	}

	private boolean isSubItem(TreeItem<EntWithLabel> subItem, TreeItem<EntWithLabel> item) {
		HierarhicalEnt subEntity = (HierarhicalEnt)subItem.getValue().getEntity();
		HierarhicalEnt entity    = (HierarhicalEnt)item   .getValue().getEntity();
		
		return entity.getId() == subEntity.getSuper().getId();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		tree.setCellFactory(createCallFactory(ent -> ent.getLabel()));
	}

	private Callback<TreeView<EntWithLabel>, TreeCell<EntWithLabel>> createCallFactory(Function<EntWithLabel, String> converter) {
		return new Callback<TreeView<EntWithLabel>, TreeCell<EntWithLabel>>() {
			
			@Override
			public TreeCell<EntWithLabel> call(TreeView<EntWithLabel> arg0) {
				return new TreeCell<EntWithLabel>() {
					
					@Override
					protected void updateItem(EntWithLabel element, boolean empty) {
						super.updateItem(element, empty);
						
						if(!empty || element != null)
							setText(converter.apply(element)); else
							setText(null);
					}
				};
			}
		};
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
