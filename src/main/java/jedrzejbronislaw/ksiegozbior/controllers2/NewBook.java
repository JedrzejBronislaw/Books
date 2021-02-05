package jedrzejbronislaw.ksiegozbior.controllers2;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import jedrzejbronislaw.ksiegozbior.controllers.forms.AuthorForm;
import jedrzejbronislaw.ksiegozbior.controllers.forms.BookForm;
import jedrzejbronislaw.ksiegozbior.controllers.forms.EditionForm;
import jedrzejbronislaw.ksiegozbior.controllers.forms.EntityForm;
import jedrzejbronislaw.ksiegozbior.controllers.forms.TitleForm;
import jedrzejbronislaw.ksiegozbior.model.entities.Author;
import jedrzejbronislaw.ksiegozbior.model.entities.Book;
import jedrzejbronislaw.ksiegozbior.model.entities.Edition;
import jedrzejbronislaw.ksiegozbior.model.entities.Edition_Title;
import jedrzejbronislaw.ksiegozbior.model.entities.Ent;
import jedrzejbronislaw.ksiegozbior.model.entities.Title;
import jedrzejbronislaw.ksiegozbior.model.projections.TheEdition;
import jedrzejbronislaw.ksiegozbior.model.projections.TheEntGenerator;
import jedrzejbronislaw.ksiegozbior.model.projections.TheTitle;
import jedrzejbronislaw.ksiegozbior.search.Searcher;
import jedrzejbronislaw.ksiegozbior.tools.MyFXMLLoader;
import jedrzejbronislaw.ksiegozbior.tools.MyFXMLLoader.NodeAndController;
import jedrzejbronislaw.ksiegozbior.tools.MyList;
import jedrzejbronislaw.ksiegozbior.view2.MyButton;
import lombok.NonNull;

@Component
public class NewBook extends StackPane implements Initializable {

	enum Steps{SEARCH_TITLE, SEARCH_ORIG_TITLE, SEARCH_AUTHOR, FILL_FORMS};
	
	@Autowired private Searcher searcher;
	@Autowired private MyFXMLLoader fxmlLoader;
	
	@Autowired TheEntGenerator theEntGenerator;
	@Autowired TheEdition theEdition;

	@FXML private GridPane searchTitlePane;
	@FXML private GridPane searchOriTitlePane;
	@FXML private GridPane searchAuthorPane;
	@FXML private GridPane resultsPane;
	@FXML private VBox formPane;
	@FXML private StackPane bottomButtonsPane;
	
	
	@FXML private TextField titleSearchField;
	@FXML private Rectangle titleRect;
	@FXML private Text titleText;

	@FXML private TextField oriTitleSearchField;
	@FXML private Rectangle oriTitleRect;
	@FXML private Text oriTitleText;

	@FXML private TextField authorSearchField;
	@FXML private Rectangle authorRect;
	@FXML private Text authorText;
	
	@FXML private Rectangle resetRect;
	@FXML private Text resetText;
	
	@FXML private Rectangle addRect;
	@FXML private Text addText;
	
	@FXML private Rectangle noneRect;
	@FXML private Text noneText;

	@FXML private VBox results;
	@FXML private Label searchInfoLabel;
	
	private MyButton titleButton;
	private MyButton oriTitleButton;
	private MyButton authorButton;
	private MyButton resetButton;
	private MyButton addButton;
	private MyButton noneButton;
	
	private AuthorForm   authorForm;
	private TitleForm     titleForm;
	private EditionForm editionForm;
	private BookForm       bookForm;

	private boolean authorFormVisible  = true;
	private boolean titleFormVisible   = true;
	private boolean editionFormVisible = true;
	private boolean bookFormVisible    = true;
	
	private Ent selectedExistingEnt;
	private Steps currentStep;
	

	public void setAuthorForm(@NonNull AuthorForm authorForm) {
		this.authorForm = authorForm;
		addBorder(authorForm);
		refreshFormsPanes();
	}
	public void setTitleForm(@NonNull TitleForm titleForm) {
		this.titleForm = titleForm;
		addBorder(titleForm);
		refreshFormsPanes();
	}
	public void setEditionForm(@NonNull EditionForm editionForm) {
		this.editionForm = editionForm;
		addBorder(editionForm);
		refreshFormsPanes();
	}
	public void setBookForm(@NonNull BookForm bookForm) {
		this.bookForm = bookForm;
		addBorder(bookForm);
		refreshFormsPanes();
	}
	
	private void addBorder(EntityForm form) {
		form.getFieldsPane().setStyle("-fx-border-color: #000;");
	}
	
	private List<EntityForm> getAllForms() {
		return Arrays.asList(
				authorForm,
				titleForm,
				editionForm,
				bookForm
		);
	}
	private boolean[] getAllFormsVisibility() {
		return new boolean[] {
				authorFormVisible,
				titleFormVisible,
				editionFormVisible,
				bookFormVisible
		};
	}
	
	
	private Steps nextStep() {
		Steps newStep = Steps.SEARCH_TITLE;
		
		switch (currentStep) {
			case SEARCH_TITLE:      newStep = Steps.SEARCH_ORIG_TITLE; break;
			case SEARCH_ORIG_TITLE: newStep = Steps.SEARCH_AUTHOR;     break;
			case SEARCH_AUTHOR:     newStep = Steps.FILL_FORMS;        break;
			case FILL_FORMS:        newStep = Steps.FILL_FORMS;        break;
			
			default:                newStep = Steps.SEARCH_TITLE;      break;
		}
		
		showStep(newStep);
		return newStep;
	}
	
	private void showStep(Steps step) {
		switch (step) {
			case SEARCH_TITLE:      setPanesVisible(true,  false, false, true,  false); break;
			case SEARCH_ORIG_TITLE: setPanesVisible(false, true,  false, true,  false); break;
			case SEARCH_AUTHOR:     setPanesVisible(false, false, true,  true,  false); break;
			case FILL_FORMS:        setPanesVisible(false, false, false, false, true ); break;
		
			default: break;
		}
		
		currentStep = step;
	}

	private void setPanesVisible(boolean searchTitle, boolean searchOriTitle, boolean searchAuthor, boolean results, boolean forms) {
		setPaneVisibility(searchTitlePane,    searchTitle);
		setPaneVisibility(searchOriTitlePane, searchOriTitle);
		setPaneVisibility(searchAuthorPane,   searchAuthor);
		setPaneVisibility(resultsPane,        results);
		setPaneVisibility(formPane,           forms);
		setPaneVisibility(bottomButtonsPane,  forms);
	}
	
	private void setPaneVisibility(Pane pane, boolean visibility) {
		pane.setVisible(visibility);
		pane.setManaged(visibility);
	}
	
	
	private void fillForms(Ent entity) {
		clearAllForms();
		hideAllForms();
		enableAllForms(true);
		
		bookForm.setEdition(null);

		if (entity instanceof Author)  fillAutor(  (Author)  entity); else
		if (entity instanceof Title)   fillTitle(  (Title)   entity); else
		if (entity instanceof Edition) fillEdition((Edition) entity);
	}
	
	private void fillAutor(Author author) {
		setAuthorDetails(author);
		titleForm.setAuthor(author);
		
		showForm(titleForm);
		showForm(editionForm);
		showForm(bookForm);
	}
	
	private void fillTitle(Title title) {
		TheTitle theEdition = (TheTitle) theEntGenerator.generate(title);

		setTitleDetails(title);
		
		MyList<Author> authors = theEdition.getAuthors();
		if (authors.size() == 1)
			setAuthorDetails(authors.get(0));
		

		editionForm.setTitle(title);
		showForm(editionForm);
		showForm(bookForm);
	}
	
	private void fillEdition(Edition edition) {
		theEdition.setEdition(edition);
		
		setEditionDetails(edition);
		
		Set<Edition_Title> titles = edition.getTitles();
		if (titles.size() == 1)
			setTitleDetails(titles.iterator().next().getTitleObj());
		
		MyList<Author> authors = theEdition.getAuthors();
		if (authors.size() == 1)
			setAuthorDetails(authors.get(0));
		
		bookForm.setEdition(edition);
		showForm(bookForm);
	}
	
	private void refreshFormsPanes() {
		Pane fieldPane;
		List<EntityForm> forms = getAllForms();
		boolean[] formsVisibility = getAllFormsVisibility();
		
		formPane.getChildren().clear();
		
		for (int i=0; i<forms.size(); i++) {
			if (!formsVisibility[i])  continue;
			if (forms.get(i) == null) continue;
			
			fieldPane = getFieldPane(forms.get(i));
			if (fieldPane == null)    continue;
			
			formPane.getChildren().add(fieldPane);
		}
	}
	
	private Pane getFieldPane(EntityForm nac) {
		return nac.getFieldsPane();
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		   titleButton = new MyButton(titleRect,    titleText);
		oriTitleButton = new MyButton(oriTitleRect, oriTitleText);
		  authorButton = new MyButton(authorRect,   authorText);
		   resetButton = new MyButton(resetRect,    resetText);
		     addButton = new MyButton(addRect,      addText);
		    noneButton = new MyButton(noneRect,     noneText);
		
		   titleButton.setHeight(25);
		oriTitleButton.setHeight(25);
		  authorButton.setHeight(25);
		   resetButton.setHeight(25);
		    noneButton.setHeight(25);

		   titleButton.setOnClicked(() -> searcher.newSearchEdition( titleSearchField.getText()));
		oriTitleButton.setOnClicked(() -> searcher.newSearchTitle(oriTitleSearchField.getText()));
		  authorButton.setOnClicked(() -> searcher.newSearchAuthor( authorSearchField.getText()));
		   resetButton.setOnClicked(() -> {        reset(); showStep(Steps.SEARCH_TITLE);});
		     addButton.setOnClicked(() -> {save(); reset(); showStep(Steps.SEARCH_TITLE);});
		    noneButton.setOnClicked(() -> {        reset(); nextStep();});
		
		searcher.setClearShearchResults(results.getChildren()::clear);
		searcher.setShowSearchItem(book -> results.getChildren().add(createResultItem(book)));
		searcher.setShowSearchInfo((phrase, count) -> searchInfoLabel.setText("\"" + phrase + "\" -> " + Integer.toString(count)));
		
		showStep(Steps.SEARCH_TITLE);
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
		NodeAndController<ResultItem> nac = fxmlLoader.create("view2/" + "ResultItem.fxml");
		
		ResultItem controller = nac.getController();
		controller.setContent(ent);
		controller.setOnClick(() -> {
			selectedExistingEnt = controller.getContent();
			fillForms(controller.getContent());
			showStep(Steps.FILL_FORMS);
		});
			
		return nac.getPane();
	}
	
	private void setAuthorDetails(Author author) {
		if (authorForm == null) return;
		
		authorForm.set(author);
		enableForm(authorForm, false);
		showForm(authorForm);
	}
	
	private void setEditionDetails(Edition content) {
		if (editionForm == null) return;
		
		editionForm.set(content);
		enableForm(editionForm, false);
		showForm(editionForm);
	}
	
	private void setTitleDetails(Title content) {
		if (titleForm == null) return;
		
		titleForm.set(content);
		enableForm(titleForm, false);
		showForm(titleForm);
	}
	
	private void hideAllForms() {
		Pane pane;
		
		for(EntityForm form : getAllForms()) {
			if (form == null) continue;
			
			pane = getFieldPane(form);
			if (pane == null) continue;
			
			setPaneVisibility(pane, false);
		}
	}

	private void showAllForms() {
		getAllForms().stream()
			.map(this::getFieldPane)
			.forEach(this::showForm);
	}

	private void showForm(Pane form) {
		if (form == null) return;
		
		setPaneVisibility(form, true);
	}

	private void enableAllForms(boolean enable) {
		getAllForms().stream()
			.map(this::getFieldPane)
			.forEach(form -> enableForm(form, enable));
	}
	
	private void enableForm(Node form, boolean enable) {
		if (form == null) return;
		
		form.setDisable(!enable);
	}
	
	private void clearAllForms() {

		for(EntityForm form : getAllForms()) {
			if(form == null) continue;
				
			form.clearFields();
			form.enableAllFields();
		}
	}
	
	private void save() {
		
		if (selectedExistingEnt instanceof Edition) {
			saveBook();			
		} else if(selectedExistingEnt instanceof Title) {
			Edition e = saveEdition();
			addEditionToBook(e);
			saveBook();				
		} else if(selectedExistingEnt instanceof Author) {
			addTitleToEdition(saveTitle());
			addEditionToBook (saveEdition());
			saveBook();							
		} else {
			addAuthorToTitle (saveAuthor());
			addTitleToEdition(saveTitle());
			addEditionToBook (saveEdition());
			saveBook();							
		}
	}

	private void addEditionToBook(Edition edition) {
		bookForm.setEdition(edition);
	}

	private void addTitleToEdition(Title title) {
		editionForm.setTitle(title);
	}

	private void addAuthorToTitle(Author author) {
		titleForm.setAuthor(author);
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

	private Ent saveForm(EntityForm form) {
		return form.save();
	}
}
