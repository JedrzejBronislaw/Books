package jedrzejbronislaw.ksiegozbior;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javafx.application.Application;
import javafx.stage.Stage;
import jedrzejbronislaw.ksiegozbior.lang.Languages;

@SpringBootApplication
@EnableAutoConfiguration
@EnableJpaAuditing
@ComponentScan
public class App extends Application {
	
	private static String[] args;
	private ConfigurableApplicationContext springContext;
	
	
    public static void main( String[] args ) {
    	App.args = args;
    	launch(args);
    }

    @Override
    public void init() throws Exception {
    	springContext = SpringApplication.run(App.class);    	
    }

    @Override
    public void start(Stage primagestage) throws Exception {
		saveTestData();
		startGui(primagestage);
    }

	private void saveTestData() {
		TestData td = (TestData) springContext.getBean("testData");
		td.save();
	}

	private void startGui(Stage primagestage) {
		GuiStarter guiStarter = springContext.getBean(GuiStarter.class);
		guiStarter.handleAppArgs(args);
		guiStarter.setStage(primagestage);
		guiStarter.start(Languages.POLISH);
	}

    @Override
    public void stop() throws Exception {
    	springContext.close();
    }
}
