package jedrzejbronislaw.ksiegozbior.view;

import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;


public class MyComboxCallBack<T> implements Callback<ListView<T>, ListCell<T>>{

	public interface Presentation<T>{
		public String toStirng(T element);
	}

	private Presentation<T> presentation = null;
	
	public MyComboxCallBack() {
	}
	
	public MyComboxCallBack(ComboBox<T> combobox) {
		combobox.setButtonCell(this.call(null));
		combobox.setCellFactory(this);
	}
	
	public MyComboxCallBack(ComboBox<T> combobox, Presentation<T> presentation) {
		combobox.setButtonCell(this.call(null));
		combobox.setCellFactory(this);
		this.presentation = presentation;
	}
	
	@Override
	public ListCell<T> call(ListView<T> arg0) {

		return new ListCell<T>() {
			@Override
			protected void updateItem(T element, boolean empty) {
				super.updateItem(element, empty);
				if(!empty || element != null)
					setText(presentation != null ? presentation.toStirng(element) : element.toString());
				else
					setText(null);
//					setGraphic(null);
			}
		};
	}
	
}