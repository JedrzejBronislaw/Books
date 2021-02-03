package jedrzejbronislaw.ksiegozbior.tools;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import jedrzejbronislaw.ksiegozbior.lang.Internationalization;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Component
public class MyFXMLLoader implements ApplicationContextAware {

	private final static String MAIN_DIR = "/jedrzejbronislaw/ksiegozbior";
	private final static String lang = Internationalization.RESOURCE_LOCATION;
	
	@RequiredArgsConstructor
	public static class NodeAndController<T>{
		@NonNull @Getter private Node node;
		@NonNull @Getter private T controller;
		
		public Parent getParent() {
			return (Parent) node;
		}
		
		public Pane getPane() {
			return (Pane) node;
		}
	}
	
	private ApplicationContext context;
	
	
	private static String path(String resource) {
		return MAIN_DIR + "/"  + resource;
	}

	private URL url(String resource) {
		return getClass().getResource(path(resource));
	}
	
	public <T> NodeAndController<T> create(String fxmlFilePath) {
		FXMLLoader fxmlLoader = new FXMLLoader();

		fxmlLoader.setControllerFactory(context::getBean);
		fxmlLoader.setLocation(url(fxmlFilePath));
		fxmlLoader.setResources(ResourceBundle.getBundle(lang));

    	Node node;
		try {
			node = fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
    	
		return new NodeAndController<T>(node, fxmlLoader.getController());
	}
	
	public static boolean create(String fxmlFilePath, Node node) {
		FXMLLoader fxmlLoader = new FXMLLoader();

    	fxmlLoader.setLocation(node.getClass().getResource(path(fxmlFilePath)));
		fxmlLoader.setResources(ResourceBundle.getBundle(lang));
		
		fxmlLoader.setRoot(node);
		fxmlLoader.setController(node);
		
		try {
			fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}
}
