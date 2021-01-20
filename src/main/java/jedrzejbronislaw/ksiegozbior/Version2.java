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
import jedrzejbronislaw.ksiegozbior.model.repositories.Repositories;
import jedrzejbronislaw.ksiegozbior.tools.MyFXMLLoader;
import jedrzejbronislaw.ksiegozbior.tools.MyFXMLLoader.NodeAndController;
import jedrzejbronislaw.ksiegozbior.view2.ViewController;
import jedrzejbronislaw.ksiegozbior.view2.Views;

@Component
public class Version2 extends ApplicationStarter {

	@Autowired
	private ViewController view;
	@Autowired
	private MyFXMLLoader fxmlLoader;
	
	private MainView2Controller controller;
	private NodeAndController bookDetailsNAC;
	
	
	public Version2() {
		super(1000, 600);
	}

	protected Parent buildRootNode() throws IOException {
		FXMLLoader fxmlLoader = getFXMLLoader("../" + newFxmlDir + newMainViewFXMLFile);
		Parent rootNode = fxmlLoader.load();
		controller = fxmlLoader.getController();

		rootNode.getStylesheets().add(getClass().getResource(cssLocation).toExternalForm());


		SearchController searcher =  buildSearcher(controller.getSearchResultPane());
		controller.setSearchAction(phrase -> searcher.newSearchPhrase(phrase));

//		view.setNewBookPane(buildNewBookPane(controller));
//		
//		view.setBookDetailsPane(buildBookDetailsPane(controller));
//		
//		view.setNewUserPane(buildSignUpPane(controller));
//		
//		view.setLoginPanel(buildLogInPane(controller));
		view.register(Views.NewBook, buildNewBookPane(controller));
		
		view.register(Views.BookDetails, buildBookDetailsPane(controller));
		
		view.register(Views.NewUser, buildSignUpPane(controller));
		
		view.register(Views.LoginPanel, buildLogInPane(controller));

		loadAllBooks(controller);

		return rootNode;
	}

	private Pane buildSignUpPane(MainView2Controller controller) throws IOException {
		NodeAndController signUpNAC = fxmlLoader.create(newFxmlDir + "newUser.fxml");
//		NewUserController bdontroller = (NewUserController) signUpNAC.getController();
		
//		String dir = fxmlDir + firstVersionfxmlDir + "entitypreviews/";
//		bdontroller.setAuthorPreview(fxmlLoader.create(dir + "AuthorPreview.fxml"));
//		bdontroller.setTitlePreview(fxmlLoader.create(dir + "TitlePreview.fxml"));
//		bdontroller.setEditionPreview(fxmlLoader.create(dir + "EditionPreview.fxml"));
//		bdontroller.setBookPreview(fxmlLoader.create(dir + "BookPreview.fxml"));
		
//		controller.setNewUserPane((Pane) signUpNAC.getNode());
		return (Pane) signUpNAC.getNode();
	}

	private Pane buildLogInPane(MainView2Controller controller) throws IOException {
		NodeAndController logInNAC = fxmlLoader.create(newFxmlDir + "loginPanel.fxml");
		
//		controller.setLoginPanel((Pane) logInNAC.getNode());
		return (Pane) logInNAC.getNode();
	}

	private Pane buildBookDetailsPane(MainView2Controller controller) throws IOException {
		bookDetailsNAC = fxmlLoader.create(newFxmlDir + "BookDetails.fxml");
		BookDetailsController bdontroller = (BookDetailsController) bookDetailsNAC.getController();
		
		String dir = fxmlDir + firstVersionfxmlDir + "entitypreviews/";
		bdontroller.setAuthorPreview(fxmlLoader.create(dir + "AuthorPreview.fxml"));
		bdontroller.setTitlePreview(fxmlLoader.create(dir + "TitlePreview.fxml"));
		bdontroller.setEditionPreview(fxmlLoader.create(dir + "EditionPreview.fxml"));
		bdontroller.setBookPreview(fxmlLoader.create(dir + "BookPreview.fxml"));
		
//		controller.setBookDetailsPane((Pane) bookDetailsNAC.getNode());
		return (Pane) bookDetailsNAC.getNode();
	}


	private Pane buildNewBookPane(MainView2Controller controller) throws IOException {
		NodeAndController newBookNAC = fxmlLoader.create(newFxmlDir + "NewBook.fxml");
		NewBookController nbController = (NewBookController) newBookNAC.getController();

		String dir = fxmlDir + firstVersionfxmlDir;
		nbController.setAuthorForm(fxmlLoader.create(dir + "newAuthorPane.fxml"));
		nbController.setTitleForm(fxmlLoader.create(dir + "newTitlePane.fxml"));
		nbController.setEditionForm(fxmlLoader.create(dir + "newEditionPane.fxml"));
		nbController.setBookForm(fxmlLoader.create(dir + "newBookPane.fxml"));

//		controller.setNewBookPane((Pane) newBookNAC.getNode());
		return (Pane) newBookNAC.getNode();
	}


	private SearchController buildSearcher(Pane resultPane) {
//		Pane searchResultPane = controller.getSearchResultPane();
		SearchController searcher = context.getBean(SearchController.class);
		
		searcher.setClearShearchResults(() -> resultPane.getChildren().clear());
		searcher.setShowSearchItem(book -> resultPane.getChildren().add(createResultItem(book)));
		return searcher;
//		controller.setSearchAction(phrase -> searcher.newSearchPhrase(phrase));
	}

	private void loadAllBooks(MainView2Controller controller) throws IOException {
		Repositories.getBookRepository().findAllNotRemoved().forEach(book -> 
			controller.addResultItem(createResultItem(book))
		);
		
//		List<Book> books = Repositories.getBookRepository().findAllNotRemoved();
//		for (int i = 0; i < books.size(); i++) {	
//			controller.addResultItem(createResultItem(books.get(i)));
//		}
	}
	
	private Pane createResultItem(Ent ent) {
		NodeAndController x = fxmlLoader.create("view2/" + "ResultItem.fxml");
		
		ResultItemController controller = (ResultItemController) x.getController();
		controller.setContent(ent);
		controller.setOnClick(() -> {
			if(ent instanceof Book)
				showBookDetails((Book) ent);
		});
		
		return (Pane) x.getNode();
	}

	private void showBookDetails(Book book) {
//		NodeAndController x = fxmlLoader.create("view2/" + "BookDetails.fxml");
		((BookDetailsController)bookDetailsNAC.getController()).set(book);
		controller.showBookDetailsPane();
	}
}
