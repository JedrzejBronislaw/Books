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
import jedrzejbronislaw.ksiegozbior.controllers.lists.ListPreview;
import jedrzejbronislaw.ksiegozbior.controllers.lists.TreePreview;
import jedrzejbronislaw.ksiegozbior.controllers.lists.managers.AuthorListManager;
import jedrzejbronislaw.ksiegozbior.controllers.lists.managers.BookCollListManager;
import jedrzejbronislaw.ksiegozbior.controllers.lists.managers.BookListManager;
import jedrzejbronislaw.ksiegozbior.controllers.lists.managers.EditionCollListManager;
import jedrzejbronislaw.ksiegozbior.controllers.lists.managers.EditionListManager;
import jedrzejbronislaw.ksiegozbior.controllers.lists.managers.LanguageListManager;
import jedrzejbronislaw.ksiegozbior.controllers.lists.managers.LocationListManager;
import jedrzejbronislaw.ksiegozbior.controllers.lists.managers.PublishingHouseListManager;
import jedrzejbronislaw.ksiegozbior.controllers.lists.managers.TitleCollListManager;
import jedrzejbronislaw.ksiegozbior.controllers.lists.managers.TitleListManager;
import jedrzejbronislaw.ksiegozbior.lang.Internationalization;
import jedrzejbronislaw.ksiegozbior.lang.Languages;
import jedrzejbronislaw.ksiegozbior.tools.Injection;
import jedrzejbronislaw.ksiegozbior.tools.MyFXMLLoader;
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
	
	@Autowired private NewAuthorPaneController            newAuthor;
	@Autowired private NewTitlePaneController             newTitle;
	@Autowired private NewBookPaneController              newBook;
	@Autowired private NewEditionPaneController           newEdition;
	@Autowired private NewPublishingHousePaneController   newPublishingHouse;
	@Autowired private NewLanguagePaneController          newLanguage;
	@Autowired private NewLocationPaneController          newLocation;
	@Autowired private NewBookCollectionPaneController    newBookCollection;
	@Autowired private NewEditionCollectionPaneController newEditionCollection;
	@Autowired private NewTitleCollectionPaneController   newTitleCollection;

	@Autowired private BookPreviewController             bookPreview;
	@Autowired private EditionPreviewController       editionPreview;
	@Autowired private AuthorPreviewController         authorPreview;
	@Autowired private TitlePreviewController           titlePreview;
	@Autowired private PublisherPreviewController   publisherPreview;
	@Autowired private CollectionPreviewController collectionPreview;
	@Autowired private LocationPreviewController     locationPreview;
	@Autowired private LanguagePreviewController     languagePreview;
	
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
			
			new PathAndClass("lists/ListPreview.fxml",                      ListPreview.class),
			new PathAndClass("lists/TreePreview.fxml",                      TreePreview.class),
			new PathAndClass("entitypreviews/BookPreview.fxml",             BookPreviewController.class),
			new PathAndClass("entitypreviews/EditionPreview.fxml",       EditionPreviewController.class),
			new PathAndClass("entitypreviews/AuthorPreview.fxml",         AuthorPreviewController.class),
			new PathAndClass("entitypreviews/TitlePreview.fxml",           TitlePreviewController.class),
			new PathAndClass("entitypreviews/PublisherPreview.fxml",   PublisherPreviewController.class),
			new PathAndClass("entitypreviews/CollectionPreview.fxml", CollectionPreviewController.class),
			new PathAndClass("entitypreviews/LocationPreview.fxml",     LocationPreviewController.class),
			new PathAndClass("entitypreviews/LanguagePreview.fxml",     LanguagePreviewController.class)
	};

	@Autowired private MainViewController mainView;
	
	@Autowired private ListPreview listPreview;
	@Autowired private TreePreview treePreview;
	
	@Autowired private AuthorListManager                   authorListManager;
	@Autowired private TitleListManager                     titleListManager;
	@Autowired private BookListManager                       bookListManager;
	@Autowired private EditionListManager                 editionListManager;
	@Autowired private PublishingHouseListManager publishingHouseListManager;
	@Autowired private LanguageListManager               languageListManager;
	@Autowired private LocationListManager               locationListManager;
	@Autowired private BookCollListManager               bookCollListManager;
	@Autowired private EditionCollListManager         editionCollListManager;
	@Autowired private TitleCollListManager             titleCollListManager;

	@Autowired private View view;
	

	@Override
	protected Parent buildRootNode() throws IOException {
		MyFXMLLoader.create(fxmlPath(MAIN_VIEW_FXML_FILE), mainView);
		
		addCSS(mainView);
		
		mainView.setChangeGUILanguage(language -> Injection.run(changeGUILanguage, language));
		mainView.setLanguageMenu(Internationalization.getCurrentLanguage(), Languages.values());
		
		buildView();
		
		return mainView;
	}
	
    private void buildView() throws IOException {
		loadFxmls();

		view.addMultiEntityView(LIST, listPreview);
		view.addMultiEntityView(TREE, treePreview);

		addPanes();
    }

	private void loadFxmls() {
		for (PathAndClass fxml : FXMLS)
			MyFXMLLoader.create(fxmlPath(fxml.path), context.getBean(fxml.c));
	}

	private String fxmlPath(String fxmlFileName) {
		return FXML_DIR + FIRST_VERSION_FXML_DIR + fxmlFileName;
	}

	private void addPanes() {
		view.addPanes(Views.AUTHORS,             new PaneSet(newAuthor,                authorPreview, LIST,          authorListManager));
		view.addPanes(Views.TITLES,              new PaneSet(newTitle,                  titlePreview, LIST,           titleListManager));
		view.addPanes(Views.BOOKS,               new PaneSet(newBook,                    bookPreview, LIST,            bookListManager));
		view.addPanes(Views.EDITIONS,            new PaneSet(newEdition,              editionPreview, LIST,         editionListManager));
		view.addPanes(Views.PUBLISHING_HOUSES,   new PaneSet(newPublishingHouse,    publisherPreview, LIST, publishingHouseListManager));
		view.addPanes(Views.LANGUAGES,           new PaneSet(newLanguage,            languagePreview, LIST,        languageListManager));
		view.addPanes(Views.LOCATIONS,           new PaneSet(newLocation,            locationPreview, TREE,        locationListManager));
		view.addPanes(Views.BOOK_COLLECTIONS,    new PaneSet(newBookCollection,    collectionPreview, TREE,        bookCollListManager));
		view.addPanes(Views.EDITION_COLLECTIONS, new PaneSet(newEditionCollection, collectionPreview, TREE,     editionCollListManager));
		view.addPanes(Views.TITLE_COLLECTIONS,   new PaneSet(newTitleCollection,   collectionPreview, TREE,       titleCollListManager));
		
		view.addPanes(Views.WELCOME,             new PaneSet(
													new Pane(new Label("Welcome")),
													new Pane(new Label("NULL")),
													NONE));
	}
}
