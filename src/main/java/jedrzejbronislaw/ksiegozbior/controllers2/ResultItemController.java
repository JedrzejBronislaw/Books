package jedrzejbronislaw.ksiegozbior.controllers2;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import jedrzejbronislaw.ksiegozbior.model.entities.Author;
import jedrzejbronislaw.ksiegozbior.model.entities.Book;
import jedrzejbronislaw.ksiegozbior.model.entities.Edition;
import jedrzejbronislaw.ksiegozbior.model.entities.Ent;
import jedrzejbronislaw.ksiegozbior.model.entities.Title;
import jedrzejbronislaw.ksiegozbior.model.projections.TheAuthor;
import jedrzejbronislaw.ksiegozbior.model.projections.TheBook;
import jedrzejbronislaw.ksiegozbior.model.projections.TheEdition;
import jedrzejbronislaw.ksiegozbior.model.projections.TheTitle;
import lombok.Getter;
import lombok.Setter;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ResultItemController implements Initializable {

	enum ContentType{EMPTY, BOOK, EDITION, TITLE, AUTHOR};
	
	private static final Paint normalColor     = Color.WHITE;
	private static final Paint mouseEnterColor = Color.LIGHTGRAY;
	private static final Paint mousePressColor = Color.DARKGRAY;
	
	
	@FXML private HBox mainPane;
	
	@FXML private Label  titleLabel;
	@FXML private Label authorLabel;
	@FXML private ImageView cover;

	@FXML private Label contentLetter;


	@Setter private Runnable onTitleClick;
	@Setter private Runnable onAuthorClick;
	@Setter private Runnable onCoverClick;
	@Setter private Runnable onClick;
	
	@Getter private ContentType contentType;
	@Getter private Ent content;
	
	
	@FXML
	private void mouseEntered() {
		changeBackground(mouseEnterColor);
	}

	@FXML
	private void mouseExited() {
		changeBackground(normalColor);
	}

	@FXML
	private void mousePressed() {
		changeBackground(mousePressColor);
	}

	@FXML
	private void mouseReleased() {
		changeBackground(mouseEnterColor);
	}
	

	@FXML
	public void titleClick() {
		System.out.println("title click");
		if (onTitleClick != null)
			onTitleClick.run();
	}

	@FXML
	public void authorClick() {
		System.out.println("author click");
		if (onAuthorClick != null)
			onAuthorClick.run();
	}
	
	@FXML
	public void coverClick() {
		System.out.println("cover Click");
		if (onCoverClick != null)
			onCoverClick.run();
	}
	
	@FXML
	public void paneClick() {
		System.out.println("pane Click");
		if (onClick != null)
			onClick.run();
	}

	public void setContent(Ent content) {
		if(content instanceof Author)  setAuthor( (Author)  content); else
		if(content instanceof Book)    setBook(   (Book)    content); else
		if(content instanceof Edition) setEdition((Edition) content); else
		if(content instanceof Title)   setTitle(  (Title)   content); else
			contentType = ContentType.EMPTY;
		
		fillContentLetter();
	}
	
	private void fillContentLetter() {
		if(contentType == ContentType.AUTHOR)  contentLetter.setText("A"); else
		if(contentType == ContentType.BOOK)    contentLetter.setText("B"); else
		if(contentType == ContentType.EDITION) contentLetter.setText("E"); else
		if(contentType == ContentType.TITLE)   contentLetter.setText("T"); else
			contentLetter.setText("-");
	}
	
	public void setAuthor(Author author) {
		content = author;
		contentType = ContentType.AUTHOR;
		TheAuthor theAuthor = new TheAuthor(author);
		
		titleLabel.setText(theAuthor.getName());
		authorLabel.setText(theAuthor.getLiveDates());
	}

	public void setBook(Book book) {
		content = book;
		contentType = ContentType.BOOK;
		TheBook theBook = new TheBook(book);
		
		titleLabel.setText(theBook.getTitle());
		authorLabel.setText(theBook.getAuthors().serialize_newLine());
	}
	
	public void setEdition(Edition edition) {
		content = edition;
		contentType = ContentType.EDITION;
		TheEdition theEdition = new TheEdition(edition);
		
		titleLabel.setText(theEdition.getTitle());
		authorLabel.setText(theEdition.getAuthors().serialize_newLine());
	}
	
	public void setTitle(Title title) {
		content = title;
		contentType = ContentType.TITLE;
		TheTitle theTitle = new TheTitle(title);
		
		titleLabel.setText(theTitle.getTitle());
		authorLabel.setText(theTitle.getAuthors().serialize_newLine());
	}


	private void changeBackground(Paint paint) {
		mainPane.setBackground(new Background(new BackgroundFill(paint, CornerRadii.EMPTY, Insets.EMPTY)));
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		changeBackground(normalColor);
	}
}
