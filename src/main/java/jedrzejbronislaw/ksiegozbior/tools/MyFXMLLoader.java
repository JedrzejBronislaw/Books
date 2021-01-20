package jedrzejbronislaw.ksiegozbior.tools;

import java.io.IOException;
import java.util.ResourceBundle;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Component
public class MyFXMLLoader implements ApplicationContextAware{

	@RequiredArgsConstructor
	public class NodeAndController{

		@NonNull
		@Getter
		private Node node;

		@NonNull
		@Getter
		private Initializable controller;
	}

	private static final String langResourceLocation = "jedrzejbronislaw.ksiegozbior.lang.Labels";
	private static final String mainDir = "/jedrzejbronislaw/ksiegozbior/";
	
	private ApplicationContext context;
	
	public NodeAndController create(String fxmlFilePath) {
		FXMLLoader fxmlLoader = new FXMLLoader();
		
    	fxmlLoader.setControllerFactory(context::getBean);
    	fxmlLoader.setLocation(getClass().getResource(mainDir + fxmlFilePath));
    	fxmlLoader.setResources(ResourceBundle.getBundle(langResourceLocation));
   	
    	Node node;
		try {
			node = fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
    	
		
		return new NodeAndController(node, fxmlLoader.getController());
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}
}
