package jedrzejbronislaw.ksiegozbior;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import jedrzejbronislaw.ksiegozbior.controllers.entitypreviews.AuthorPreviewController;
import jedrzejbronislaw.ksiegozbior.controllers.entitypreviews.BookPreviewController;
import jedrzejbronislaw.ksiegozbior.controllers.entitypreviews.EditionPreviewController;
import jedrzejbronislaw.ksiegozbior.controllers.entitypreviews.TitlePreviewController;
import jedrzejbronislaw.ksiegozbior.controllers.forms.AuthorForm;
import jedrzejbronislaw.ksiegozbior.controllers.forms.BookForm;
import jedrzejbronislaw.ksiegozbior.controllers.forms.EditionForm;
import jedrzejbronislaw.ksiegozbior.controllers.forms.TitleForm;
import jedrzejbronislaw.ksiegozbior.controllers2.BookDetailsController;
import jedrzejbronislaw.ksiegozbior.controllers2.LoginPanelController;
import jedrzejbronislaw.ksiegozbior.controllers2.MainView2Controller;
import jedrzejbronislaw.ksiegozbior.controllers2.NewBookController;
import jedrzejbronislaw.ksiegozbior.controllers2.NewUserController;
import jedrzejbronislaw.ksiegozbior.controllers2.ResultItemController;
import jedrzejbronislaw.ksiegozbior.controllers2.SearchController;
import jedrzejbronislaw.ksiegozbior.model.entities.Book;
import jedrzejbronislaw.ksiegozbior.model.entities.Ent;
import jedrzejbronislaw.ksiegozbior.model.repositories.BookRepository;
import jedrzejbronislaw.ksiegozbior.tools.MyFXMLLoader;
import jedrzejbronislaw.ksiegozbior.view2.ViewController;
import jedrzejbronislaw.ksiegozbior.view2.Views;

@Component
public class GuiVer2 extends Gui {

	@Autowired private ViewController view;

	@Autowired private BookRepository bookRepository;

	@Autowired private AuthorForm   authorForm;
	@Autowired private TitleForm     titleForm;
	@Autowired private BookForm       bookForm;
	@Autowired private EditionForm editionForm;
	
	@Autowired private AuthorPreviewController   authorPreview;
	@Autowired private BookPreviewController       bookPreview;
	@Autowired private EditionPreviewController editionPreview;
	@Autowired private TitlePreviewController     titlePreview;
	
	@Autowired private MainView2Controller          mainView;
	@Autowired private NewUserController         newUserView;
	@Autowired private LoginPanelController        loginView;
	@Autowired private BookDetailsController bookDetailsView;
	@Autowired private NewBookController         newBookView;

	
	public GuiVer2() {
		super(1000, 600);
	}

	protected Parent buildRootNode() throws IOException {
		MyFXMLLoader.create(NEW_FXML_DIR + NEW_MAIN_VIEW_FXML_FILE, mainView);

		addCSS(mainView);

		SearchController searcher =  buildSearcher(mainView.getSearchResultPane());
		mainView.setSearchAction(searcher::newSearchPhrase);

		view.register(Views.NEW_BOOK,     buildNewBookPane());
		view.register(Views.BOOK_DETAILS, buildBookDetailsPane());
		view.register(Views.NEW_USER,     buildSignUpPane());
		view.register(Views.LOGIN_PANEL,  buildLogInPane());

		loadAllBooks(mainView);

		return mainView;
	}

	private Pane buildSignUpPane() throws IOException {
		MyFXMLLoader.create(fxmlPath("newUser.fxml"), newUserView);
		return newUserView;
	}

	private Pane buildLogInPane() throws IOException {
		MyFXMLLoader.create(fxmlPath("loginPanel.fxml"), loginView);
		return loginView;
	}

	private Pane buildBookDetailsPane() throws IOException {
		MyFXMLLoader.create(fxmlPath("BookDetails.fxml"), bookDetailsView);
		BookDetailsController controller = bookDetailsView;

		String dir = FXML_DIR + FIRST_VERSION_FXML_DIR + "entitypreviews/";
		MyFXMLLoader.create(dir + "AuthorPreview.fxml",   authorPreview);
		MyFXMLLoader.create(dir + "TitlePreview.fxml",     titlePreview);
		MyFXMLLoader.create(dir + "EditionPreview.fxml", editionPreview);
		MyFXMLLoader.create(dir + "BookPreview.fxml",       bookPreview);
		
		controller.setAuthorPreview(  authorPreview);
		controller.setTitlePreview(    titlePreview);
		controller.setEditionPreview(editionPreview);
		controller.setBookPreview(      bookPreview);
		
		return bookDetailsView;
	}


	private Pane buildNewBookPane() throws IOException {
		MyFXMLLoader.create(fxmlPath("NewBook.fxml"), newBookView);
		NewBookController nbController = newBookView;

		String dir = FXML_DIR + FIRST_VERSION_FXML_DIR + FORMS_FXML_DIR;
		MyFXMLLoader.create(dir + "AuthorForm.fxml",   authorForm);
		MyFXMLLoader.create(dir + "TitleForm.fxml",     titleForm);
		MyFXMLLoader.create(dir + "EditionForm.fxml", editionForm);
		MyFXMLLoader.create(dir + "BookForm.fxml",       bookForm);
		
		nbController.setAuthorForm(  authorForm);
		nbController.setTitleForm(    titleForm);
		nbController.setEditionForm(editionForm);
		nbController.setBookForm(      bookForm);

		return newBookView;
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
		ResultItemController resultItem = context.getBean(ResultItemController.class);
		MyFXMLLoader.create(fxmlPath("ResultItem.fxml"), resultItem);
		
		resultItem.setContent(ent);
		resultItem.setOnClick(() -> {
			if(ent instanceof Book)
				showBookDetails((Book) ent);
		});
		
		return resultItem;
	}

	private void showBookDetails(Book book) {
		bookDetailsView.set(book);
		mainView.showBookDetailsPane();
	}

	private String fxmlPath(String fxmlName) {
		return NEW_FXML_DIR + fxmlName;
	}
}
