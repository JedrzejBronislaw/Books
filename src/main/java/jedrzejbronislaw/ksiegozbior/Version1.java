package jedrzejbronislaw.ksiegozbior;

import java.io.IOException;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import javafx.fxml.FXMLLoader;
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
import jedrzejbronislaw.ksiegozbior.view.PanePlusControl;
import jedrzejbronislaw.ksiegozbior.view.PaneSet;
import jedrzejbronislaw.ksiegozbior.view.View;
import jedrzejbronislaw.ksiegozbior.view.Views;
import jedrzejbronislaw.ksiegozbior.view.PaneSet.MultiEntityViewType;

@Component
public class Version1 extends ApplicationStarter {

	@Override
	protected Parent buildRootNode() throws IOException {
    	Parent rootNode;
    	View view;
    	FXMLLoader fxmlLoader = getFXMLLoader(firstVersionfxmlDir+mainViewFXMLFile);

    	rootNode = fxmlLoader.load();
		rootNode.getStylesheets().add(getClass().getResource(cssLocation).toExternalForm());
    	MainViewController mainViewController = fxmlLoader.getController();
    	
    	mainViewController.setChangeGUILanguage(language->{
    		if(changeGUILanguage != null)
    			changeGUILanguage.accept(language);
    		});
//    	mainViewController.setChangeGUILanguage(language->{buildView(language);});
    	mainViewController.setLanguageMenu(Internationalization.getCurrentLanguage(), Languages.values());
    	
    	view = createView(
    			mainViewController.getSPane(),
    			mainViewController.getMainPane(),
    			mainViewController.getPreviewPane(),
    			mainViewController.getHeader1()
    			);
    	mainViewController.setView(view);

    	return rootNode;
	}

    private View createView(Pane addPane, Pane viewPane, Pane previewPane, Label header) throws IOException{
		View view = new View(addPane, viewPane, previewPane, header);
    	FXMLLoader fxmlLoader;
    	String[] fxmlFiles = {
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

    	Pane[] panes = new Pane[fxmlFiles.length];
    	ListPreviewController listPreviewController = null;
    	TreePreviewController treePreviewController = null;
    	
    	for(int i=0; i<fxmlFiles.length; i++) {
    		fxmlLoader = new FXMLLoader();
    		fxmlLoader.setResources(ResourceBundle.getBundle(langResourceLocation));
    		fxmlLoader.setControllerFactory(context::getBean);
    		fxmlLoader.setLocation(getClass().getResource(fxmlDir+firstVersionfxmlDir+fxmlFiles[i]));
    		panes[i] = fxmlLoader.load();
    		panes[i].getStylesheets().add(getClass().getResource(cssLocation).toExternalForm());
    		
    		if(i==11)
    			listPreviewController = fxmlLoader.getController();
    		if(i==12)
    			treePreviewController = fxmlLoader.getController();
    	}
    	
    	Pane nullPane = new Pane(new Label("NULL")); //TODO delete
    	
    	view.addMultiEntityView(MultiEntityViewType.LIST, new PanePlusControl(panes[11], listPreviewController));
    	view.addMultiEntityView(MultiEntityViewType.TREE, new PanePlusControl(panes[12], treePreviewController));
    	
    	view.addPanes(Views.AUTHORS, new PaneSet(panes[0], panes[11], panes[14], MultiEntityViewType.LIST, context.getBean(AuthorListPreviewController.class)));//new Pane(new Label("Authors"))));
    	view.addPanes(Views.TITLES, new PaneSet(panes[1], panes[11], panes[15], MultiEntityViewType.LIST, context.getBean(TitleListPreviewController.class)));//new Pane(new Label("Titles"))));
    	view.addPanes(Views.BOOKS, new PaneSet(panes[2], panes[11], panes[10], MultiEntityViewType.LIST, context.getBean(BookListPreviewController.class)));//new Pane(new Label("Book"))));
    	view.addPanes(Views.EDITIONS, new PaneSet(panes[3], panes[11], panes[13], MultiEntityViewType.LIST, context.getBean(EditionListPreviewController.class)));//new Pane(new Label("Editions"))));
    	view.addPanes(Views.PUBLISHING_HOUSES, new PaneSet(panes[4], panes[11],  panes[16], MultiEntityViewType.LIST, context.getBean(PublishingHouseListPreviewController.class)));//new Pane(new Label("PublishingHouses"))));
    	view.addPanes(Views.LANGUAGES, new PaneSet(panes[5], panes[11], panes[19], MultiEntityViewType.LIST, context.getBean(LanguageListPreviewController.class)));//, springContext.getBean()));//new Pane(new Label("Languages"))));
    	view.addPanes(Views.LOCATIONS, new PaneSet(panes[6], panes[12], panes[18], MultiEntityViewType.TREE, context.getBean(LocationListPreviewController.class)));//, springContext.getBean()));//new Pane(new Label("Locations"))));
    	view.addPanes(Views.BOOK_COLLECTIONS, new PaneSet(panes[7], panes[12], panes[17], MultiEntityViewType.TREE, context.getBean(BookCollectionListPreviewController.class)));//, springContext.getBean()));// new Pane(new Label("BookCollections"))));
    	view.addPanes(Views.EDITION_COLLECTIONS, new PaneSet(panes[8], panes[12], panes[17], MultiEntityViewType.TREE, context.getBean(EditionCollectionListPreviewController.class)));//, springContext.getBean()));// new Pane(new Label("EditionCollections"))));
    	view.addPanes(Views.TITLE_COLLECTIONS, new PaneSet(panes[9], panes[12], panes[17], MultiEntityViewType.TREE, context.getBean(TitleCollectionListPreviewController.class)));//, springContext.getBean()));// new Pane(new Label("TitleCollections"))));
    	
    	view.addPanes(Views.WELCOME, new PaneSet(new Pane(new Label("Welcome")), new Pane(new Label("Welcome")), nullPane, MultiEntityViewType.NONE));

    	
//    	view.setListPreviewController(listPreviewController);
//    	view.setTreePreviewController(treePreviewController);

    	return view;
    }
}
