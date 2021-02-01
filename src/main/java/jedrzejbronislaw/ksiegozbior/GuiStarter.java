package jedrzejbronislaw.ksiegozbior;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jedrzejbronislaw.ksiegozbior.lang.Internationalization;
import jedrzejbronislaw.ksiegozbior.lang.Languages;
import lombok.Setter;

@Component
public class GuiStarter {
	
	@Autowired private GuiVer1 guiVer1;
	@Autowired private GuiVer2 guiVer2;
	
	private Gui gui;
	private int guiVersion = 1;
	
	@Setter private Stage stage;
	
	
	public void handleAppArgs(String[] args) {
		if (args.length < 1) return;
		
		if (args[0].equals("1")) guiVersion = 1;
		if (args[0].equals("2")) guiVersion = 2;
	}
	
    public void start(Languages language) {
    	gui = getGui();
    	gui.setChangeGUILanguage(this::buildView);

    	buildView(language);
    }
	
	private Gui getGui() {
		switch (guiVersion) {
			case 1 : return guiVer1;
			case 2 : return guiVer2;
			
			default: return guiVer1;
		}
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
    	if (stage == null) throw new IllegalStateException("The main stage is not set");
    	
    	stage.setTitle(Internationalization.get("application.name"));
    	stage.setScene(scene);
    	stage.show();
    	
    	stage.setOnCloseRequest(h -> Platform.exit());
    }
}
