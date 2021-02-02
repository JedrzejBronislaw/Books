package jedrzejbronislaw.ksiegozbior.controllers.listpreviews;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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
import jedrzejbronislaw.ksiegozbior.model.projections.TheEntGenerator;

@Component
public class TreePreviewController extends MultiEntityViewController implements Initializable {

	@Autowired TheEntGenerator theEntGenerator;

	@FXML private Label title;
	@FXML private TreeView<Ent> tree;


	@Override
	protected void set(String header, List<Ent> elements) {
		title.setText(header);
		listRefresh(elements);
	}

	@Override
	protected void listRefresh(List<Ent> elements) {
		TreeItem<Ent> root = new TreeItem<>();
		
		tree.setRoot(root);
		tree.setShowRoot(false);
		
		if (elements == null) return;
		
		List<TreeItem<Ent>> items = elements.stream()
				.filter(e -> (e instanceof HierarhicalEnt))
				.map(TreeItem::new)
				.collect(Collectors.toList());
		
		createTree(root, items);
	}

	private void createTree(TreeItem<Ent> root, List<TreeItem<Ent>> items) {
		HierarhicalEnt ent;
		
		for (TreeItem<Ent> item : items) {
			
			ent = (HierarhicalEnt) item.getValue();
			
			if (ent.isRoot() || !moveItemToSuperitem(items, item))
				root.getChildren().add(item);
		}
	}

	private boolean moveItemToSuperitem(List<TreeItem<Ent>> items, TreeItem<Ent> subItem) {

		for (TreeItem<Ent> item : items) {
			
			if (isSubItem(subItem, item)) {
				item.getChildren().add(subItem);
				return true;
			}
		}
		
		return false;
	}

	private boolean isSubItem(TreeItem<Ent> subItem, TreeItem<Ent> item) {
		HierarhicalEnt subEntity = (HierarhicalEnt)subItem.getValue();
		HierarhicalEnt entity    = (HierarhicalEnt)item   .getValue();
		
		return entity.getId() == subEntity.getSuper().getId();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		tree.setCellFactory(createCallFactory(entity -> theEntGenerator.generate(entity).getLabel()));
	}

	private Callback<TreeView<Ent>, TreeCell<Ent>> createCallFactory(Function<Ent, String> converter) {
		return new Callback<TreeView<Ent>, TreeCell<Ent>>() {
			
			@Override
			public TreeCell<Ent> call(TreeView<Ent> arg0) {
				return new TreeCell<Ent>() {
					
					@Override
					protected void updateItem(Ent element, boolean empty) {
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
		return tree.getSelectionModel().getSelectedItem().getValue();
	}
	@Override
	protected boolean isSelectedItem() {
		return (tree.getSelectionModel().getSelectedIndex() > -1);
	}
}
