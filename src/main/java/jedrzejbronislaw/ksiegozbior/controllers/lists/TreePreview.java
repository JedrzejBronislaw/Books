package jedrzejbronislaw.ksiegozbior.controllers.lists;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import jedrzejbronislaw.ksiegozbior.model.entities.Ent;
import jedrzejbronislaw.ksiegozbior.model.entities.HierarhicalEnt;
import jedrzejbronislaw.ksiegozbior.model.projections.TheEntGenerator;
import jedrzejbronislaw.ksiegozbior.view.SimpleTreeCallback;

@Component
public class TreePreview extends MultiEntityPreview implements Initializable {

	@Autowired TheEntGenerator theEntGenerator;

	@FXML private Label title;
	@FXML private TreeView<Ent> tree;


	@Override
	protected void set(String header, List<Ent> elements) {
		title.setText(header);
		refresh(elements);
	}

	@Override
	protected void refresh(List<Ent> elements) {
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
		tree.setCellFactory(new SimpleTreeCallback<Ent>(entity -> theEntGenerator.generate(entity).getLabel()));
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
