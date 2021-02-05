package jedrzejbronislaw.ksiegozbior;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import jedrzejbronislaw.ksiegozbior.controllers.entities.AuthorPreview;
import jedrzejbronislaw.ksiegozbior.controllers.entities.BookPreview;
import jedrzejbronislaw.ksiegozbior.controllers.entities.EditionPreview;
import jedrzejbronislaw.ksiegozbior.controllers.entities.TitlePreview;
import jedrzejbronislaw.ksiegozbior.controllers.forms.AuthorForm;
import jedrzejbronislaw.ksiegozbior.controllers.forms.BookForm;
import jedrzejbronislaw.ksiegozbior.controllers.forms.EditionForm;
import jedrzejbronislaw.ksiegozbior.controllers.forms.TitleForm;
import jedrzejbronislaw.ksiegozbior.controllers2.BookDetails;
import jedrzejbronislaw.ksiegozbior.controllers2.LoginPanel;
import jedrzejbronislaw.ksiegozbior.controllers2.MainView2;
import jedrzejbronislaw.ksiegozbior.controllers2.NewBook;
import jedrzejbronislaw.ksiegozbior.controllers2.NewUser;
import jedrzejbronislaw.ksiegozbior.controllers2.ResultItem;
import jedrzejbronislaw.ksiegozbior.controllers2.Searcher;
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
	
	@Autowired private AuthorPreview   authorPreview;
	@Autowired private BookPreview       bookPreview;
	@Autowired private EditionPreview editionPreview;
	@Autowired private TitlePreview     titlePreview;
	
	@Autowired private MainView2          mainView;
	@Autowired private NewUser         newUserView;
	@Autowired private LoginPanel        loginView;
	@Autowired private BookDetails bookDetailsView;
	@Autowired private NewBook         newBookView;

	
	public GuiVer2() {
		super(1000, 600);
	}

	protected Parent buildRootNode() throws IOException {
		MyFXMLLoader.create(NEW_FXML_DIR + NEW_MAIN_VIEW_FXML_FILE, mainView);

		addCSS(mainView);

		Searcher searcher = buildSearcher(mainView.getSearchResultPane());
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

		String dir = FXML_DIR + FIRST_VERSION_FXML_DIR + ENTITIES_FXML_DIR;
		MyFXMLLoader.create(dir + "AuthorPreview.fxml",   authorPreview);
		MyFXMLLoader.create(dir + "TitlePreview.fxml",     titlePreview);
		MyFXMLLoader.create(dir + "EditionPreview.fxml", editionPreview);
		MyFXMLLoader.create(dir + "BookPreview.fxml",       bookPreview);
		
		bookDetailsView.setAuthorPreview(  authorPreview);
		bookDetailsView.setTitlePreview(    titlePreview);
		bookDetailsView.setEditionPreview(editionPreview);
		bookDetailsView.setBookPreview(      bookPreview);
		
		return bookDetailsView;
	}


	private Pane buildNewBookPane() throws IOException {
		MyFXMLLoader.create(fxmlPath("NewBook.fxml"), newBookView);

		String dir = FXML_DIR + FIRST_VERSION_FXML_DIR + FORMS_FXML_DIR;
		MyFXMLLoader.create(dir + "AuthorForm.fxml",   authorForm);
		MyFXMLLoader.create(dir + "TitleForm.fxml",     titleForm);
		MyFXMLLoader.create(dir + "EditionForm.fxml", editionForm);
		MyFXMLLoader.create(dir + "BookForm.fxml",       bookForm);
		
		newBookView.setAuthorForm(  authorForm);
		newBookView.setTitleForm(    titleForm);
		newBookView.setEditionForm(editionForm);
		newBookView.setBookForm(      bookForm);

		return newBookView;
	}


	private Searcher buildSearcher(Pane resultPane) {
		Searcher searcher = context.getBean(Searcher.class);
		
		searcher.setClearShearchResults(() -> resultPane.getChildren().clear());
		searcher.setShowSearchItem(book -> resultPane.getChildren().add(createResultItem(book)));
		
		return searcher;
	}

	private void loadAllBooks(MainView2 mainView) throws IOException {
		
		bookRepository.findAllNotRemoved().stream()
			.map(this::createResultItem)
			.forEach(mainView::addResultItem);
	}
	
	private Pane createResultItem(Ent ent) {
		ResultItem resultItem = context.getBean(ResultItem.class);
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
