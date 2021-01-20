package jedrzejbronislaw.ksiegozbior.controllers2;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import jedrzejbronislaw.ksiegozbior.controllers.EntityFormController;
import jedrzejbronislaw.ksiegozbior.controllers.NewAuthorPaneController;
import jedrzejbronislaw.ksiegozbior.controllers.NewBookPaneController;
import jedrzejbronislaw.ksiegozbior.controllers.NewEditionPaneController;
import jedrzejbronislaw.ksiegozbior.controllers.NewTitlePaneController;
import jedrzejbronislaw.ksiegozbior.model.entities.Author;
import jedrzejbronislaw.ksiegozbior.model.entities.Book;
import jedrzejbronislaw.ksiegozbior.model.entities.Edition;
import jedrzejbronislaw.ksiegozbior.model.entities.Edition_Title;
import jedrzejbronislaw.ksiegozbior.model.entities.Ent;
import jedrzejbronislaw.ksiegozbior.model.entities.Title;
import jedrzejbronislaw.ksiegozbior.model.projections.TheEdition;
import jedrzejbronislaw.ksiegozbior.model.projections.TheTitle;
import jedrzejbronislaw.ksiegozbior.tools.MyFXMLLoader;
import jedrzejbronislaw.ksiegozbior.tools.MyFXMLLoader.NodeAndController;
import jedrzejbronislaw.ksiegozbior.tools.MyList;
import jedrzejbronislaw.ksiegozbior.view2.MyButton;
import lombok.NonNull;

@Component
public class NewBookController implements Initializable {

	enum Steps{SearchTitle, SearchOrigTitle, SearchAuthor, FillForms};

	private Steps currentStep;

	@FXML
	private GridPane searchTitlePane;
	@FXML
	private GridPane searchOriTitlePane;
	@FXML
	private GridPane searchAuthorPane;
	@FXML
	private GridPane resultsPane;
	@FXML
	private VBox formPane;
	@FXML
	private StackPane bottomButtonsPane;
	
	
	@FXML
	private TextField titleSearchField;
	@FXML
	private Rectangle titleRect;
	@FXML
	private Text titleText;

	@FXML
	private TextField oriTitleSearchField;
	@FXML
	private Rectangle oriTitleRect;
	@FXML
	private Text oriTitleText;

	@FXML
	private TextField authorSearchField;
	@FXML
	private Rectangle authorRect;
	@FXML
	private Text authorText;
	
	@FXML
	private Rectangle resetRect;
	@FXML
	private Text resetText;
	
	@FXML
	private Rectangle addRect;
	@FXML
	private Text addText;
	
	@FXML
	private Rectangle noneRect;
	@FXML
	private Text noneText;

	@FXML
	private VBox results;
	@FXML
	private Label searchInfoLabel;
	
	private MyButton titleButton;
	private MyButton oriTitleButton;
	private MyButton authorButton;
	private MyButton resetButton;
	private MyButton addButton;
	private MyButton noneButton;
	
	private NodeAndController authorForm;
	private NodeAndController titleForm;
	private NodeAndController editionForm;
	private NodeAndController bookForm;

	private boolean authorFormVisible = true;
	private boolean titleFormVisible = true;
	private boolean editionFormVisible = true;
	private boolean bookFormVisible = true;
	
	@Autowired
	private SearchController searcher;
	
	@Autowired
	private MyFXMLLoader fxmlLoader;
	
	
	private Ent selectedExistingEnt;
	
	
	private Steps nextStep() {
		Steps newStep = Steps.SearchTitle;
		
		switch (currentStep) {
		case SearchTitle:
			newStep = Steps.SearchOrigTitle; break;
		case SearchOrigTitle:
			newStep = Steps.SearchAuthor; break;
		case SearchAuthor:
			newStep = Steps.FillForms; break;
		case FillForms:
			newStep = Steps.FillForms; break;
		default:
			newStep = Steps.SearchTitle; break;
		}
		
		showStep(newStep);
		return newStep;
	}
	
	private void showStep(Steps step) {
		switch (step) {
		case SearchTitle:
			setPanesVisible(true, false, false, true, false);
			break;

		case SearchOrigTitle:
			setPanesVisible(false, true, false, true, false);
			break;

		case SearchAuthor:
			setPanesVisible(false, false, true, true, false);
			break;

		case FillForms:
			setPanesVisible(false, false, false, false, true);
			break;

		default:
			break;
		}
		
		currentStep = step;
	}

	private void setPanesVisible(boolean searchTitle, boolean searchOriTitle, boolean searchAuthor, boolean results, boolean forms) {
		searchTitlePane.setVisible(searchTitle);
		searchTitlePane.setManaged(searchTitle);
		
		searchOriTitlePane.setVisible(searchOriTitle);
		searchOriTitlePane.setManaged(searchOriTitle);
		
		searchAuthorPane.setVisible(searchAuthor);
		searchAuthorPane.setManaged(searchAuthor);
		
		resultsPane.setVisible(results);
		resultsPane.setManaged(results);
		
		formPane.setVisible(forms);
		formPane.setManaged(forms);
		
		bottomButtonsPane.setVisible(forms);
		bottomButtonsPane.setManaged(forms);
	}
	
	
	private void fillForms(Ent entity) {
		clearAllForms();
		hideAllForms();
		enableAllForms(true);
		
		((NewBookPaneController)bookForm.getController()).setEdition(null);

		if(entity instanceof Author) {
			Author author = (Author) entity;
			
			setAuthorDetails(author);
			((NewTitlePaneController)titleForm.getController()).setAuthor(author);
			
			showForm(titleForm);
			showForm(editionForm);
			showForm(bookForm);
		} else if(entity instanceof Title) {
			Title title = (Title) entity;
			TheTitle theEdition = new TheTitle(title);

			setTitleDetails(title);
			
			MyList<Author> authors = theEdition.getAuthors();
			if(authors.size() == 1)
				setAuthorDetails(authors.get(0));
			

			((NewEditionPaneController)editionForm.getController()).setTitle(title);
			showForm(editionForm);
			showForm(bookForm);
		} else if(entity instanceof Edition) {
			Edition edition = (Edition) entity;
			TheEdition theEdition = new TheEdition(edition);
			
			setEditionDetails(edition);
			
			Set<Edition_Title> titles = edition.getTitles();
			if(titles.size() == 1)
				setTitleDetails(titles.iterator().next().getTitleObj());
			
			MyList<Author> authors = theEdition.getAuthors();
			if(authors.size() == 1)
				setAuthorDetails(authors.get(0));
			
			((NewBookPaneController)bookForm.getController()).setEdition(edition);
			showForm(bookForm);
		}
	};
	
	private NodeAndController[] getAllForms() {
		return new NodeAndController[] {
				authorForm,
				titleForm,
				editionForm,
				bookForm
		};
	}
	private boolean[] getAllFormsVisibility() {
		return new boolean[] {
				authorFormVisible,
				titleFormVisible,
				editionFormVisible,
				bookFormVisible
		};
	}
	
	public void setAuthorForm(@NonNull NodeAndController authorForm) {
		if (authorForm.getController() instanceof EntityFormController) {
			this.authorForm = authorForm;
			refreshFormsPanes();
		}
	}
	public void setTitleForm(@NonNull NodeAndController titleForm) {
		if (authorForm.getController() instanceof EntityFormController) {
			this.titleForm = titleForm;
			refreshFormsPanes();
		}
	}
	public void setEditionForm(@NonNull NodeAndController editionForm) {
		if (authorForm.getController() instanceof EntityFormController) {
			this.editionForm = editionForm;
			refreshFormsPanes();
		}
	}
	public void setBookForm(@NonNull NodeAndController bookForm) {
		if (authorForm.getController() instanceof EntityFormController) {
			this.bookForm = bookForm;
			refreshFormsPanes();
		}
	}
	private void refreshFormsPanes() {
		NodeAndController form;
		Pane fieldPane;
		NodeAndController[] forms = getAllForms();
		boolean[] vis = getAllFormsVisibility();
		
		formPane.getChildren().clear();
		for(int i=0; i<forms.length; i++) {
			if(!vis[i]) continue;
			
			form = forms[i];
			if(form != null && (fieldPane = getFieldPane(form)) != null) {
				fieldPane.setStyle("-fx-border-color: #000;");
				formPane.getChildren().add(fieldPane);
			}
		}
	}
	
	private Pane getFieldPane(NodeAndController x) {
		if (x.getController() instanceof EntityFormController) {
			Pane pane = ((EntityFormController)x.getController()).getFieldsPane();
			return pane;
		} else
			return null;
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		titleButton = new MyButton(titleRect, titleText);
		oriTitleButton = new MyButton(oriTitleRect, oriTitleText);
		authorButton = new MyButton(authorRect, authorText);
		resetButton = new MyButton(resetRect, resetText);
		addButton = new MyButton(addRect, addText);
		noneButton = new MyButton(noneRect, noneText);
		
		titleButton.setHeight(25);
		oriTitleButton.setHeight(25);
		authorButton.setHeight(25);
		resetButton.setHeight(25);
		noneButton.setHeight(25);

		titleButton.setOnClicked(() -> searcher.newSearchEdition(titleSearchField.getText()));
		oriTitleButton.setOnClicked(() -> searcher.newSearchTitle(oriTitleSearchField.getText()));
		authorButton.setOnClicked(() -> searcher.newSearchAuthor(authorSearchField.getText()));
		resetButton.setOnClicked(() -> {reset(); showStep(Steps.SearchTitle);});
		addButton.setOnClicked(() -> {save(); reset(); showStep(Steps.SearchTitle);});
		noneButton.setOnClicked(() -> {
			reset();
			nextStep();
		});
		
		searcher.setClearShearchResults(() -> results.getChildren().clear());
		searcher.setShowSearchItem(book -> results.getChildren().add(createResultItem(book)));
		searcher.setShowSearchInfo((phrase, count)-> searchInfoLabel.setText("\"" + phrase + "\" -> " + Integer.toString(count)));
		
		showStep(Steps.SearchTitle);
	}

	private void reset() {
		selectedExistingEnt = null;
		results.getChildren().clear();
		clearAllForms();
		enableAllForms(true);
		showAllForms();
		titleSearchField.setText(null);
		oriTitleSearchField.setText(null);
		authorSearchField.setText(null);
		searchInfoLabel.setText(null);
	}
	
	private Pane createResultItem(Ent ent) {
		NodeAndController x = fxmlLoader.create("view2/" + "ResultItem.fxml");
		
		ResultItemController controller = (ResultItemController) x.getController();
		controller.setContent(ent);
		controller.setOnClick(() -> {
			selectedExistingEnt = controller.getContent();
			fillForms(controller.getContent());
			showStep(Steps.FillForms);
		});
			
		return (Pane) x.getNode();

	}
	
	private void setAuthorDetails(Author author) {
		if (authorForm != null) {
			((NewAuthorPaneController)authorForm.getController()).set(author);
//			((EntityFormController)authorForm.getController()).setEnable(false);
			enableForm(authorForm, false);
			showForm(authorForm);
		}
	}
	private void setEditionDetails(Edition content) {
		if (editionForm != null) {
			((NewEditionPaneController)editionForm.getController()).set(content);
//			((EntityFormController)editionForm.getController()).setEnable(false);
			enableForm(editionForm, false);
			showForm(editionForm);
		}
	}
	
	private void setTitleDetails(Title content) {
		if (titleForm != null) {
			((NewTitlePaneController)titleForm.getController()).set(content);
//			((EntityFormController)titleForm.getController()).setEnable(false);
			enableForm(titleForm, false);
			showForm(titleForm);
		}
	}
	
	private void hideAllForms() {
		NodeAndController[] forms = getAllForms();		
		Pane pane;
		
		for(NodeAndController form : forms)
			if(form != null)
				if((pane = getFieldPane(form)) != null) {
					pane.setVisible(false);
					pane.setManaged(false);
				}
	}

	private void showAllForms() {
		NodeAndController[] forms = getAllForms();		

		for(NodeAndController form : forms)
			showForm(form);
	}

	private void showForm(NodeAndController form) {
		Pane pane;
		
		if(form != null)
			if((pane = getFieldPane(form)) != null) {
				pane.setVisible(true);
				pane.setManaged(true);
			}
	}

	private void enableAllForms(boolean enable) {
		NodeAndController[] forms = getAllForms();		

		for(NodeAndController form : forms)
			enableForm(form, enable);
	}
	
	private void enableForm(NodeAndController form, boolean enable) {
		Pane pane;
		
		if(form != null)
			if((pane = getFieldPane(form)) != null) {
				pane.setDisable(!enable);
			}
	}
	
	private void clearAllForms() {
		NodeAndController[] forms = getAllForms();
		
		for(NodeAndController form : forms)
			if(form != null) {
				EntityFormController controller = (EntityFormController)form.getController();
				controller.clearFields();
				controller.enableAllFields();
			}	
	}
	
	private void save() {
		
		if(selectedExistingEnt instanceof Edition) {
			saveBook();			
		} else if(selectedExistingEnt instanceof Title) {
			
			
			Edition e = saveEdition();
			addEditionToBook(e);
			saveBook();				
		} else if(selectedExistingEnt instanceof Author) {
			Title t = saveTitle();
			addTitleToEdition(t);
			Edition e = saveEdition();
			addEditionToBook(e);
			saveBook();							
		} else {
			Author a = saveAuthor();
			addAuthorToTitle(a);
			Title t = saveTitle();
			addTitleToEdition(t);
			Edition e = saveEdition();
			addEditionToBook(e);
			saveBook();							
		}
	}

	private void addEditionToBook(Edition edition) {
		NodeAndController form = bookForm;
		
		if(form != null && form.getController() != null) {
			if(form.getController() instanceof NewBookPaneController) {
				NewBookPaneController controller = (NewBookPaneController) form.getController();
				controller.setEdition(edition);
			}
		}		
	}

	private void addTitleToEdition(Title title) {
		NodeAndController form = editionForm;
		
		if(form != null && form.getController() != null) {
			if(form.getController() instanceof NewEditionPaneController) {
				NewEditionPaneController controller = (NewEditionPaneController) form.getController();
				controller.setTitle(title);
			}
		}		
	}

	private void addAuthorToTitle(Author author) {
		NodeAndController form = titleForm;
		
		if(form != null && form.getController() != null) {
			if(form.getController() instanceof NewTitlePaneController) {
				NewTitlePaneController controller = (NewTitlePaneController) form.getController();
				controller.setAuthor(author);
			}
		}		
	}

	private Author saveAuthor() {
		return (Author) saveForm(authorForm);
	}

	private Title saveTitle() {
		return (Title) saveForm(titleForm);
	}
	
	private Edition saveEdition() {
		return (Edition) saveForm(editionForm);
	}
 
	private Book saveBook() {
		return (Book) saveForm(bookForm);
	}

	private Ent saveForm(NodeAndController form) {
		if(form != null && form.getController() != null) {
			if(form.getController() instanceof EntityFormController) {
				EntityFormController controller = (EntityFormController) form.getController();
				return controller.save();
			}
		}
		
		return null;
	}
}
