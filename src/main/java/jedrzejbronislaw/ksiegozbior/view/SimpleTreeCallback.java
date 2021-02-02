package jedrzejbronislaw.ksiegozbior.view;

import java.util.function.Function;

import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.util.Callback;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public class SimpleTreeCallback<T> implements Callback<TreeView<T>, TreeCell<T>> {

	@Setter @NonNull private Function<T, String> presentation;


	@Override
	public TreeCell<T> call(TreeView<T> arg0) {
		return new TreeCell<T>() {
			
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
