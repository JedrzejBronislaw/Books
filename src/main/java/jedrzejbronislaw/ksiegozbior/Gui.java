package jedrzejbronislaw.ksiegozbior;

import java.io.IOException;
import java.util.function.Consumer;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javafx.scene.Parent;
import javafx.scene.Scene;
import jedrzejbronislaw.ksiegozbior.lang.Languages;
import lombok.Setter;

public abstract class Gui implements ApplicationContextAware {

	private double sceneWidth = 1200;
	private double sceneHeight = 600;
	
	protected static final String  LANG_RESOURCE_LOCATION = "jedrzejbronislaw.ksiegozbior.lang.Labels";
	protected static final String            CSS_LOCATION = "view/firstCss.css";
	protected static final String                FXML_DIR = "view/";
	protected static final String            NEW_FXML_DIR = "view2/";
	protected static final String  FIRST_VERSION_FXML_DIR = "firstVer/";
	protected static final String NEW_MAIN_VIEW_FXML_FILE = "mainView.fxml";
	protected static final String     MAIN_VIEW_FXML_FILE = "mainView.fxml";
	protected static final String          FORMS_FXML_DIR = "forms/";
	protected static final String       ENTITIES_FXML_DIR = "entities/";

	protected ApplicationContext context;
	private Scene scene;
	
	@Setter
	protected Consumer<Languages> changeGUILanguage; 
	
	
	protected abstract Parent buildRootNode() throws IOException;
	
	
	public Gui() {}
	
	public Gui(double width, double height) {
		sceneWidth = width;
		sceneHeight = height;
	}
	
	
	public Scene createScene() throws IOException {
		if (scene == null)
			scene = new Scene(buildRootNode(), sceneWidth, sceneHeight); else
			scene.setRoot(buildRootNode());
		
		return scene;
	}
    
	protected void addCSS(Parent parent) {
		parent.getStylesheets().add(getClass().getResource(CSS_LOCATION).toExternalForm());
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}
}
