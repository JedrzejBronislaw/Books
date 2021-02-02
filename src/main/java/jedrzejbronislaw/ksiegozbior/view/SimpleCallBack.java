package jedrzejbronislaw.ksiegozbior.view;

import java.util.function.Function;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public class SimpleCallback<T> implements Callback<ListView<T>, ListCell<T>> {

	@Setter @NonNull private Function<T, String> presentation;
	

	@Override
	public ListCell<T> call(ListView<T> arg0) {

		return new ListCell<T>() {
			
			@Override
			protected void updateItem(T element, boolean empty) {
				super.updateItem(element, empty);
				
				if(!empty || element != null)
					setText(presentation.apply(element)); else
					setText(null);
			}
		};
	}
}
