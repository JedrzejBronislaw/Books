package jedrzejbronislaw.ksiegozbior;

import java.io.IOException;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import jedrzejbronislaw.ksiegozbior.lang.Languages;
import lombok.Setter;

public abstract class ApplicationStarter implements ApplicationContextAware {

	private double sceneWidth = 1200;
	private double sceneHeight = 600;
	
	protected static final String cssLocation = "view/firstCss.css";
	protected static final String newFxmlDir = "view2/";
	protected static final String fxmlDir = "view/";
	protected static final String firstVersionfxmlDir = "firstVer/";
	protected static final String newMainViewFXMLFile = "mainView.fxml";
	protected static final String mainViewFXMLFile = "mainView.fxml";
	protected static final String langResourceLocation = "jedrzejbronislaw.ksiegozbior.lang.Labels";

	@Setter
	protected Consumer<Languages> changeGUILanguage; 
	
	protected ApplicationContext context;
    
	protected abstract Parent buildRootNode() throws IOException;
	
	public ApplicationStarter() {
	}
	public ApplicationStarter(double width, double height) {
		sceneWidth = width;
		sceneHeight = height;
	}
	
	
	public Scene createScene() throws IOException {
    	return new Scene(buildRootNode(), sceneWidth, sceneHeight);
	}
	
    protected FXMLLoader getFXMLLoader(String fxmlFile) {
    	FXMLLoader fxmlLoader = new FXMLLoader();
    	
    	fxmlLoader.setControllerFactory(context::getBean);
		fxmlLoader.setLocation(getClass().getResource(fxmlDir+fxmlFile));
		fxmlLoader.setResources(ResourceBundle.getBundle(langResourceLocation));
		
		return fxmlLoader;
    }
    
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}
}
