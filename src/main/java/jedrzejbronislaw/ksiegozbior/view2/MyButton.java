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
import lombok.NonNull;
import lombok.Setter;

public class MyButton {
	
	double r = .55;
	double g = .55;
	double b = .99;
	Color color = new Color(r, g, b, 1);

	private Stop[] normalGradientColors = new Stop[] {
//			new Stop(0, Color.LIGHTYELLOW),
//			new Stop(1, Color.GOLD)
			new Stop(0, color.brighter()),
			new Stop(1, color)
			};
	private Stop[] pressGradientColor = new Stop[] {
//			new Stop(0, Color.GOLD),
//			new Stop(1, Color.GOLD.darker())
			new Stop(0, color),
			new Stop(1, color.darker())
			};
	private Paint normalPaint = new LinearGradient(0, 0, 100, 80, false, CycleMethod.NO_CYCLE, normalGradientColors);
	private Paint pressPaint= new LinearGradient(0, 0, 100, 80, false, CycleMethod.NO_CYCLE, pressGradientColor);
	
	@NonNull
	private Rectangle rect;
	@NonNull
	private Text text;
	
	@Setter
	private Runnable onClicked = null;
	
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
		button.onMouseEnteredProperty().bindBidirectional(text.onMouseEnteredProperty());
		button.onMouseExitedProperty().bindBidirectional(text.onMouseExitedProperty());
		button.onMouseClickedProperty().bindBidirectional(text.onMouseClickedProperty());
		button.onMousePressedProperty().bindBidirectional(text.onMousePressedProperty());
		button.onMouseReleasedProperty().bindBidirectional(text.onMouseReleasedProperty());
		
		button.setOnMouseClicked(e -> {if(onClicked != null) onClicked.run();});
		
		button.setOnMouseEntered(e -> button.setStrokeWidth(2));
		button.setOnMouseExited(e -> button.setStrokeWidth(0));
		
		button.setOnMousePressed(e -> button.setFill(pressPaint));
		button.setOnMouseReleased(e -> button.setFill(normalPaint));
		
		button.setFill(normalPaint);
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
