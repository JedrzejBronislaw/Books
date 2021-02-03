package jedrzejbronislaw.ksiegozbior;

import static jedrzejbronislaw.ksiegozbior.view.PaneSet.MultiEntityViewType.LIST;
import static jedrzejbronislaw.ksiegozbior.view.PaneSet.MultiEntityViewType.NONE;
import static jedrzejbronislaw.ksiegozbior.view.PaneSet.MultiEntityViewType.TREE;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import jedrzejbronislaw.ksiegozbior.controllers.MainViewController;
import jedrzejbronislaw.ksiegozbior.controllers.listpreviews.AuthorListPreviewController;
import jedrzejbronislaw.ksiegozbior.controllers.listpreviews.BookCollectionListPreviewController;
import jedrzejbronislaw.ksiegozbior.controllers.listpreviews.BookListPreviewController;
import jedrzejbronislaw.ksiegozbior.controllers.listpreviews.EditionCollectionListPreviewController;
import jedrzejbronislaw.ksiegozbior.controllers.listpreviews.EditionListPreviewController;
import jedrzejbronislaw.ksiegozbior.controllers.listpreviews.LanguageListPreviewController;
import jedrzejbronislaw.ksiegozbior.controllers.listpreviews.ListPreviewController;
import jedrzejbronislaw.ksiegozbior.controllers.listpreviews.LocationListPreviewController;
import jedrzejbronislaw.ksiegozbior.controllers.listpreviews.PublishingHouseListPreviewController;
import jedrzejbronislaw.ksiegozbior.controllers.listpreviews.TitleCollectionListPreviewController;
import jedrzejbronislaw.ksiegozbior.controllers.listpreviews.TitleListPreviewController;
import jedrzejbronislaw.ksiegozbior.controllers.listpreviews.TreePreviewController;
import jedrzejbronislaw.ksiegozbior.lang.Internationalization;
import jedrzejbronislaw.ksiegozbior.lang.Languages;
import jedrzejbronislaw.ksiegozbior.tools.Injection;
import jedrzejbronislaw.ksiegozbior.tools.MyFXMLLoader;
import jedrzejbronislaw.ksiegozbior.tools.MyFXMLLoader.NodeAndController;
import jedrzejbronislaw.ksiegozbior.view.PanePlusControl;
import jedrzejbronislaw.ksiegozbior.view.PaneSet;
import jedrzejbronislaw.ksiegozbior.view.View;
import jedrzejbronislaw.ksiegozbior.view.Views;

@Component
public class GuiVer1 extends Gui {
	
	private static final String[] FXML_PATHS = {
			"NewAuthorPane.fxml",
			"NewTitlePane.fxml",
			"NewBookPane.fxml",
			"NewEditionPane.fxml",
			"NewPublishingHousePane.fxml",
			"NewLanguagePane.fxml",
			"NewLocationPane.fxml",
			"NewBookCollectionPane.fxml",
			"NewEditionCollectionPane.fxml",
			"NewTitleCollectionPane.fxml",
			
			"entitypreviews/BookPreview.fxml",
			"listpreviews/ListPreview.fxml",
			"listpreviews/TreePreview.fxml",
			"entitypreviews/EditionPreview.fxml",
			"entitypreviews/AuthorPreview.fxml",
			"entitypreviews/TitlePreview.fxml",
			"entitypreviews/PublisherPreview.fxml",
			"entitypreviews/CollectionPreview.fxml",
			"entitypreviews/LocationPreview.fxml",
			"entitypreviews/LanguagePreview.fxml"
	};
	
	@Autowired AuthorListPreviewController                       authorListPreviewController;
	@Autowired TitleListPreviewController                         titleListPreviewController;
	@Autowired BookListPreviewController                           bookListPreviewController;
	@Autowired EditionListPreviewController                     editionListPreviewController;
	@Autowired PublishingHouseListPreviewController     publishingHouseListPreviewController;
	@Autowired LanguageListPreviewController                   languageListPreviewController;
	@Autowired LocationListPreviewController                   locationListPreviewController;
	@Autowired BookCollectionListPreviewController       bookCollectionListPreviewController;
	@Autowired EditionCollectionListPreviewController editionCollectionListPreviewController;
	@Autowired TitleCollectionListPreviewController     titleCollectionListPreviewController;

	@Autowired private MyFXMLLoader fxmlLoader;
	
	
	@Override
	protected Parent buildRootNode() throws IOException {
		MainViewController controller;
    	Parent node;
    	View view;
    	NodeAndController<MainViewController> nac = fxmlLoader.create(fxmlPath(MAIN_VIEW_FXML_FILE));
    	
    	node = nac.getParent();
    	addCSS(node);
    	controller = nac.getController();
    	
    	controller.setChangeGUILanguage(language -> Injection.run(changeGUILanguage, language));
    	controller.setLanguageMenu(Internationalization.getCurrentLanguage(), Languages.values());
    	
    	view = createView(
    			controller.getSPane(),
    			controller.getMainPane(),
    			controller.getPreviewPane(),
    			controller.getHeader1()
    	);
    	controller.setView(view);

    	return node;
	}
	
    private View createView(Pane addPane, Pane viewPane, Pane previewPane, Label header) throws IOException {
    	
		View view = new View(addPane, viewPane, previewPane, header);
    	Pane[] panes = new Pane[FXML_PATHS.length];
    	ListPreviewController listPreviewController = null;
    	TreePreviewController treePreviewController = null;
    	
    	
    	for (int i=0; i<FXML_PATHS.length; i++) {
			NodeAndController<Initializable> nac = fxmlLoader.create(fxmlPath(i));
			panes[i] = (Pane)nac.getNode();

			if (i == 11) listPreviewController = (ListPreviewController) nac.getController();
			if (i == 12) treePreviewController = (TreePreviewController) nac.getController();
    	}
    	
    	view.addMultiEntityView(LIST, new PanePlusControl(panes[11], listPreviewController));
    	view.addMultiEntityView(TREE, new PanePlusControl(panes[12], treePreviewController));
    	
    	addPanes(view, panes);
    	
    	return view;
    }

	private String fxmlPath(String fxmlFileName) {
		return FXML_DIR + FIRST_VERSION_FXML_DIR + fxmlFileName;
	}

	private String fxmlPath(int i) {
		return fxmlPath(FXML_PATHS[i]);
	}

	private void addPanes(View view, Pane[] panes) {
		view.addPanes(Views.AUTHORS,             new PaneSet(panes[0], panes[11], panes[14], LIST, authorListPreviewController));
		view.addPanes(Views.TITLES,              new PaneSet(panes[1], panes[11], panes[15], LIST, titleListPreviewController));
		view.addPanes(Views.BOOKS,               new PaneSet(panes[2], panes[11], panes[10], LIST, bookListPreviewController));
		view.addPanes(Views.EDITIONS,            new PaneSet(panes[3], panes[11], panes[13], LIST, editionListPreviewController));
		view.addPanes(Views.PUBLISHING_HOUSES,   new PaneSet(panes[4], panes[11], panes[16], LIST, publishingHouseListPreviewController));
		view.addPanes(Views.LANGUAGES,           new PaneSet(panes[5], panes[11], panes[19], LIST, languageListPreviewController));
		view.addPanes(Views.LOCATIONS,           new PaneSet(panes[6], panes[12], panes[18], TREE, locationListPreviewController));
		view.addPanes(Views.BOOK_COLLECTIONS,    new PaneSet(panes[7], panes[12], panes[17], TREE, bookCollectionListPreviewController));
		view.addPanes(Views.EDITION_COLLECTIONS, new PaneSet(panes[8], panes[12], panes[17], TREE, editionCollectionListPreviewController));
		view.addPanes(Views.TITLE_COLLECTIONS,   new PaneSet(panes[9], panes[12], panes[17], TREE, titleCollectionListPreviewController));
		
		view.addPanes(Views.WELCOME,             new PaneSet(
													new Pane(new Label("Welcome")),
													new Pane(new Label("Welcome")),
													new Pane(new Label("NULL")),
													NONE));
	}
}
