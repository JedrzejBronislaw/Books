package jedrzejbronislaw.ksiegozbior;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jedrzejbronislaw.ksiegozbior.lang.Internationalization;
import jedrzejbronislaw.ksiegozbior.lang.Languages;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
public class App extends Application {
	
	private ConfigurableApplicationContext springContext;
	private Stage theStage;
	public static Gui gui;
	private static int guiVersion = 1;
	
	
    public static void main( String[] args ) {
    	handleArgs(args);
    	launch(args);
    }

	private static void handleArgs(String[] args) {
		if (args.length < 1) return;
		
		if (args[0].equals("1")) guiVersion = 1;
		if (args[0].equals("2")) guiVersion = 2;
	}

    @Override
    public void init() throws Exception {
    	springContext = SpringApplication.run(App.class);    	
    }

    @Override
    public void start(Stage primagestage) throws Exception {

    	TestData td = (TestData) springContext.getBean("testData");
    	td.save();
    	
    	theStage = primagestage;
    	gui = getGuiBean();
    	buildView(Languages.POLISH);
    	gui.setChangeGUILanguage(this::buildView);
    }

	private Gui getGuiBean() {
		switch (guiVersion) {
			case 1 : return (Gui) springContext.getBean(GuiVer1.class);
			case 2 : return (Gui) springContext.getBean(GuiVer2.class);
			
			default: return (Gui) springContext.getBean(GuiVer1.class);
		}
	}
    
    @Override
    public void stop() throws Exception {
    	springContext.close();
    }
    
    private void buildView(Languages language) {
    	if(language != null)
    		Internationalization.setLanguage(language);
    	
    	try {
			setScene(gui.createScene());
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
    	
    }
    
    private void setScene(Scene scene) {
    	theStage.setTitle(Internationalization.get("application.name"));
    	theStage.setScene(scene);
    	theStage.show();
    	
    	theStage.setOnCloseRequest(h -> Platform.exit());
    }
}
