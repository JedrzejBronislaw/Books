package jedrzejbronislaw.ksiegozbior;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import jedrzejbronislaw.ksiegozbior.controllers2.BookDetailsController;
import jedrzejbronislaw.ksiegozbior.controllers2.MainView2Controller;
import jedrzejbronislaw.ksiegozbior.controllers2.NewBookController;
import jedrzejbronislaw.ksiegozbior.controllers2.ResultItemController;
import jedrzejbronislaw.ksiegozbior.controllers2.SearchController;
import jedrzejbronislaw.ksiegozbior.model.entities.Book;
import jedrzejbronislaw.ksiegozbior.model.entities.Ent;
import jedrzejbronislaw.ksiegozbior.model.repositories.BookRepository;
import jedrzejbronislaw.ksiegozbior.tools.MyFXMLLoader;
import jedrzejbronislaw.ksiegozbior.tools.MyFXMLLoader.NodeAndController;
import jedrzejbronislaw.ksiegozbior.view2.ViewController;
import jedrzejbronislaw.ksiegozbior.view2.Views;

@Component
public class GuiVer2 extends Gui {

	@Autowired private ViewController view;
	@Autowired private MyFXMLLoader fxmlLoader;

	@Autowired private BookRepository bookRepository;
	
	private MainView2Controller controller;
	private BookDetailsController bookDetailsController;
	
	
	public GuiVer2() {
		super(1000, 600);
	}

	protected Parent buildRootNode() throws IOException {
		FXMLLoader fxmlLoader = getFXMLLoader("../" + NEW_FXML_DIR + NEW_MAIN_VIEW_FXML_FILE);
		Parent rootNode = fxmlLoader.load();
		controller = fxmlLoader.getController();

		rootNode.getStylesheets().add(getClass().getResource(CSS_LOCATION).toExternalForm());

		SearchController searcher =  buildSearcher(controller.getSearchResultPane());
		controller.setSearchAction(searcher::newSearchPhrase);

		view.register(Views.NEW_BOOK,     buildNewBookPane());
		view.register(Views.BOOK_DETAILS, buildBookDetailsPane());
		view.register(Views.NEW_USER,     buildSignUpPane());
		view.register(Views.LOGIN_PANEL,  buildLogInPane());

		loadAllBooks(controller);

		return rootNode;
	}

	private Pane buildSignUpPane() throws IOException {
		return (Pane) fxmlLoader.create(NEW_FXML_DIR + "newUser.fxml").getNode();
	}

	private Pane buildLogInPane() throws IOException {
		return (Pane) fxmlLoader.create(NEW_FXML_DIR + "loginPanel.fxml").getNode();
	}

	private Pane buildBookDetailsPane() throws IOException {
		String dir = FXML_DIR + FIRST_VERSION_FXML_DIR + "entitypreviews/";
		
		NodeAndController bookDetailsNAC = fxmlLoader.create(NEW_FXML_DIR + "BookDetails.fxml");
		BookDetailsController controller = (BookDetailsController) bookDetailsNAC.getController();
		
		controller.setAuthorPreview( fxmlLoader.create(dir + "AuthorPreview.fxml"));
		controller.setTitlePreview(  fxmlLoader.create(dir + "TitlePreview.fxml"));
		controller.setEditionPreview(fxmlLoader.create(dir + "EditionPreview.fxml"));
		controller.setBookPreview(   fxmlLoader.create(dir + "BookPreview.fxml"));
		
		bookDetailsController = controller;
		
		return (Pane) bookDetailsNAC.getNode();
	}


	private Pane buildNewBookPane() throws IOException {
		NodeAndController newBookNAC = fxmlLoader.create(NEW_FXML_DIR + "NewBook.fxml");
		NewBookController nbController = (NewBookController) newBookNAC.getController();

		String dir = FXML_DIR + FIRST_VERSION_FXML_DIR;
		nbController.setAuthorForm( fxmlLoader.create(dir + "newAuthorPane.fxml"));
		nbController.setTitleForm(  fxmlLoader.create(dir + "newTitlePane.fxml"));
		nbController.setEditionForm(fxmlLoader.create(dir + "newEditionPane.fxml"));
		nbController.setBookForm(   fxmlLoader.create(dir + "newBookPane.fxml"));

		return (Pane) newBookNAC.getNode();
	}


	private SearchController buildSearcher(Pane resultPane) {
		SearchController searcher = context.getBean(SearchController.class);
		
		searcher.setClearShearchResults(() -> resultPane.getChildren().clear());
		searcher.setShowSearchItem(book -> resultPane.getChildren().add(createResultItem(book)));
		
		return searcher;
	}

	private void loadAllBooks(MainView2Controller controller) throws IOException {
		
		bookRepository.findAllNotRemoved().stream()
			.map(this::createResultItem)
			.forEach(controller::addResultItem);
	}
	
	private Pane createResultItem(Ent ent) {
		NodeAndController nac = fxmlLoader.create(NEW_FXML_DIR + "ResultItem.fxml");
		
		ResultItemController controller = (ResultItemController) nac.getController();
		controller.setContent(ent);
		controller.setOnClick(() -> {
			if(ent instanceof Book)
				showBookDetails((Book) ent);
		});
		
		return (Pane) nac.getNode();
	}

	private void showBookDetails(Book book) {
		bookDetailsController.set(book);
		controller.showBookDetailsPane();
	}
}
