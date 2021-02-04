package jedrzejbronislaw.ksiegozbior;

import static jedrzejbronislaw.ksiegozbior.view.PaneSet.MultiEntityViewType.LIST;
import static jedrzejbronislaw.ksiegozbior.view.PaneSet.MultiEntityViewType.NONE;
import static jedrzejbronislaw.ksiegozbior.view.PaneSet.MultiEntityViewType.TREE;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import jedrzejbronislaw.ksiegozbior.controllers.MainViewController;
import jedrzejbronislaw.ksiegozbior.controllers.NewAuthorPaneController;
import jedrzejbronislaw.ksiegozbior.controllers.NewBookCollectionPaneController;
import jedrzejbronislaw.ksiegozbior.controllers.NewBookPaneController;
import jedrzejbronislaw.ksiegozbior.controllers.NewEditionCollectionPaneController;
import jedrzejbronislaw.ksiegozbior.controllers.NewEditionPaneController;
import jedrzejbronislaw.ksiegozbior.controllers.NewLanguagePaneController;
import jedrzejbronislaw.ksiegozbior.controllers.NewLocationPaneController;
import jedrzejbronislaw.ksiegozbior.controllers.NewPublishingHousePaneController;
import jedrzejbronislaw.ksiegozbior.controllers.NewTitleCollectionPaneController;
import jedrzejbronislaw.ksiegozbior.controllers.NewTitlePaneController;
import jedrzejbronislaw.ksiegozbior.controllers.entitypreviews.AuthorPreviewController;
import jedrzejbronislaw.ksiegozbior.controllers.entitypreviews.BookPreviewController;
import jedrzejbronislaw.ksiegozbior.controllers.entitypreviews.CollectionPreviewController;
import jedrzejbronislaw.ksiegozbior.controllers.entitypreviews.EditionPreviewController;
import jedrzejbronislaw.ksiegozbior.controllers.entitypreviews.LanguagePreviewController;
import jedrzejbronislaw.ksiegozbior.controllers.entitypreviews.LocationPreviewController;
import jedrzejbronislaw.ksiegozbior.controllers.entitypreviews.PublisherPreviewController;
import jedrzejbronislaw.ksiegozbior.controllers.entitypreviews.TitlePreviewController;
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
import jedrzejbronislaw.ksiegozbior.view.PanePlusControl;
import jedrzejbronislaw.ksiegozbior.view.PaneSet;
import jedrzejbronislaw.ksiegozbior.view.View;
import jedrzejbronislaw.ksiegozbior.view.Views;
import lombok.AllArgsConstructor;

@Component
public class GuiVer1 extends Gui {
	
	@AllArgsConstructor
	static class PathAndClass {
		String path;
		Class<? extends Pane> c;
	}
	
	private static final PathAndClass[] FXMLS = {
			new PathAndClass("NewAuthorPane.fxml",                        NewAuthorPaneController.class),
			new PathAndClass("NewTitlePane.fxml",                          NewTitlePaneController.class),
			new PathAndClass("NewBookPane.fxml",                            NewBookPaneController.class),
			new PathAndClass("NewEditionPane.fxml",                      NewEditionPaneController.class),
			new PathAndClass("NewPublishingHousePane.fxml",      NewPublishingHousePaneController.class),
			new PathAndClass("NewLanguagePane.fxml",                    NewLanguagePaneController.class),
			new PathAndClass("NewLocationPane.fxml",                    NewLocationPaneController.class),
			new PathAndClass("NewBookCollectionPane.fxml",        NewBookCollectionPaneController.class),
			new PathAndClass("NewEditionCollectionPane.fxml",  NewEditionCollectionPaneController.class),
			new PathAndClass("NewTitleCollectionPane.fxml",      NewTitleCollectionPaneController.class),
			
			new PathAndClass("entitypreviews/BookPreview.fxml",             BookPreviewController.class),
			new PathAndClass("listpreviews/ListPreview.fxml",               ListPreviewController.class),
			new PathAndClass("listpreviews/TreePreview.fxml",               TreePreviewController.class),
			new PathAndClass("entitypreviews/EditionPreview.fxml",       EditionPreviewController.class),
			new PathAndClass("entitypreviews/AuthorPreview.fxml",         AuthorPreviewController.class),
			new PathAndClass("entitypreviews/TitlePreview.fxml",           TitlePreviewController.class),
			new PathAndClass("entitypreviews/PublisherPreview.fxml",   PublisherPreviewController.class),
			new PathAndClass("entitypreviews/CollectionPreview.fxml", CollectionPreviewController.class),
			new PathAndClass("entitypreviews/LocationPreview.fxml",     LocationPreviewController.class),
			new PathAndClass("entitypreviews/LanguagePreview.fxml",     LanguagePreviewController.class)
	};

	@Autowired private MainViewController mainView;
	
	@Autowired private ListPreviewController listPreview;
	@Autowired private TreePreviewController treePreview;
	
	@Autowired private AuthorListPreviewController                       authorListPreviewController;
	@Autowired private TitleListPreviewController                         titleListPreviewController;
	@Autowired private BookListPreviewController                           bookListPreviewController;
	@Autowired private EditionListPreviewController                     editionListPreviewController;
	@Autowired private PublishingHouseListPreviewController     publishingHouseListPreviewController;
	@Autowired private LanguageListPreviewController                   languageListPreviewController;
	@Autowired private LocationListPreviewController                   locationListPreviewController;
	@Autowired private BookCollectionListPreviewController       bookCollectionListPreviewController;
	@Autowired private EditionCollectionListPreviewController editionCollectionListPreviewController;
	@Autowired private TitleCollectionListPreviewController     titleCollectionListPreviewController;


	@Override
	protected Parent buildRootNode() throws IOException {
		MyFXMLLoader.create(fxmlPath(MAIN_VIEW_FXML_FILE), mainView);
		
		addCSS(mainView);
		
		mainView.setChangeGUILanguage(language -> Injection.run(changeGUILanguage, language));
		mainView.setLanguageMenu(Internationalization.getCurrentLanguage(), Languages.values());
		
		View view = createView(
				mainView.getSPane(),
				mainView.getMainPane(),
				mainView.getPreviewPane(),
				mainView.getHeader1()
				);
		mainView.setView(view);
		
		return mainView;
	}
	
    private View createView(Pane addPane, Pane viewPane, Pane previewPane, Label header) throws IOException {
		View view = new View(addPane, viewPane, previewPane, header);
		Pane[] panes = loadFxmls();

		view.addMultiEntityView(LIST, new PanePlusControl(panes[11], listPreview));
		view.addMultiEntityView(TREE, new PanePlusControl(panes[12], treePreview));

		addPanes(view, panes);

		return view;
    }

	private Pane[] loadFxmls() {
		Pane[] panes = new Pane[FXMLS.length];
		
		for (int i=0; i<FXMLS.length; i++) {
			Pane pane  = context.getBean(FXMLS[i].c);
			MyFXMLLoader.create(fxmlPath(FXMLS[i].path), pane);
			panes[i] = pane;
		}
		
		return panes;
	}

	private String fxmlPath(String fxmlFileName) {
		return FXML_DIR + FIRST_VERSION_FXML_DIR + fxmlFileName;
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
