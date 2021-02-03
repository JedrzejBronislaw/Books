package jedrzejbronislaw.ksiegozbior.view2;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import jedrzejbronislaw.ksiegozbior.tools.Injection;
import lombok.NonNull;
import lombok.Setter;

public class MyButton {
	
	private static final double R = .55;
	private static final double G = .55;
	private static final double B = .99;
	private static final Color COLOR = new Color(R, G, B, 1);

	private static final Stop[] NORMAL_GRADIENT = new Stop[] {
			new Stop(0, COLOR.brighter()),
			new Stop(1, COLOR)
	};
	
	private static final Stop[] PRESS_GRADIENT = new Stop[] {
			new Stop(0, COLOR),
			new Stop(1, COLOR.darker())
	};
	
	private static final Paint NORMAL_PAINT = new LinearGradient(0, 0, 100, 80, false, CycleMethod.NO_CYCLE, NORMAL_GRADIENT);
	private static final Paint PRESS_PAINT  = new LinearGradient(0, 0, 100, 80, false, CycleMethod.NO_CYCLE, PRESS_GRADIENT);
	
	@NonNull private Rectangle rect;
	@NonNull private Text text;
	
	@Setter private Runnable onClicked = null;
	
	public MyButton(String text) {
		this.rect = new Rectangle();
		this.text = new Text(text);
		prepeareButton(this.rect, this.text);
	}
	
	public MyButton(Rectangle rect, Text text) {
		this.rect = rect;
		this.text = text;
		prepeareButton(rect, text);
	}
	
	public void setWidth(double width) {
		rect.setWidth(width);
	}
	public void setHeight(double height) {
		rect.setHeight(height);
	}
	
	
	private void prepeareButton(Rectangle button, Text text) {
		button.onMouseEnteredProperty() .bindBidirectional(text.onMouseEnteredProperty());
		button.onMouseExitedProperty()  .bindBidirectional(text.onMouseExitedProperty());
		button.onMouseClickedProperty() .bindBidirectional(text.onMouseClickedProperty());
		button.onMousePressedProperty() .bindBidirectional(text.onMousePressedProperty());
		button.onMouseReleasedProperty().bindBidirectional(text.onMouseReleasedProperty());
		
		button.setOnMouseClicked(e -> Injection.run(onClicked));
		
		button.setOnMouseEntered(e -> button.setStrokeWidth(2));
		button.setOnMouseExited( e -> button.setStrokeWidth(0));
		
		button.setOnMousePressed( e -> button.setFill(PRESS_PAINT));
		button.setOnMouseReleased(e -> button.setFill(NORMAL_PAINT));
		
		button.setFill(NORMAL_PAINT);
		button.setStrokeWidth(0);
		
		
		button.setWidth(150);
		button.setHeight(80);
		
		button.setArcHeight(0);
		button.setArcWidth(0);
	}

	public Node createNode() {
		return new StackPane(rect, text);
	}
}
