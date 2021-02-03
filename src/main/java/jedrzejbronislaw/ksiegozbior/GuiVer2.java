package jedrzejbronislaw.ksiegozbior;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
		NodeAndController<MainView2Controller> nac = fxmlLoader.create(NEW_FXML_DIR + NEW_MAIN_VIEW_FXML_FILE);
		Parent rootNode = nac.getParent();
		controller = nac.getController();

		addCSS(rootNode);

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
		return fxmlLoader.create(fxmlPath("newUser.fxml")).getPane();
	}

	private Pane buildLogInPane() throws IOException {
		return fxmlLoader.create(fxmlPath("loginPanel.fxml")).getPane();
	}

	private Pane buildBookDetailsPane() throws IOException {
		String dir = FXML_DIR + FIRST_VERSION_FXML_DIR + "entitypreviews/";

		NodeAndController<BookDetailsController> bookDetailsNAC = fxmlLoader.create(fxmlPath("BookDetails.fxml"));
		BookDetailsController controller = bookDetailsNAC.getController();
		
		controller.setAuthorPreview( fxmlLoader.create(dir + "AuthorPreview.fxml"));
		controller.setTitlePreview(  fxmlLoader.create(dir + "TitlePreview.fxml"));
		controller.setEditionPreview(fxmlLoader.create(dir + "EditionPreview.fxml"));
		controller.setBookPreview(   fxmlLoader.create(dir + "BookPreview.fxml"));
		
		bookDetailsController = controller;
		
		return bookDetailsNAC.getPane();
	}


	private Pane buildNewBookPane() throws IOException {
		NodeAndController<NewBookController> newBookNAC = fxmlLoader.create(fxmlPath("NewBook.fxml"));
		NewBookController nbController = newBookNAC.getController();

		String dir = FXML_DIR + FIRST_VERSION_FXML_DIR;
		nbController.setAuthorForm( fxmlLoader.create(dir + "newAuthorPane.fxml"));
		nbController.setTitleForm(  fxmlLoader.create(dir + "newTitlePane.fxml"));
		nbController.setEditionForm(fxmlLoader.create(dir + "newEditionPane.fxml"));
		nbController.setBookForm(   fxmlLoader.create(dir + "newBookPane.fxml"));

		return newBookNAC.getPane();
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
		NodeAndController<ResultItemController> nac = fxmlLoader.create(fxmlPath("ResultItem.fxml"));
		
		ResultItemController controller = nac.getController();
		controller.setContent(ent);
		controller.setOnClick(() -> {
			if(ent instanceof Book)
				showBookDetails((Book) ent);
		});
		
		return nac.getPane();
	}

	private void showBookDetails(Book book) {
		bookDetailsController.set(book);
		controller.showBookDetailsPane();
	}

	private String fxmlPath(String fxmlName) {
		return NEW_FXML_DIR + fxmlName;
	}
}
