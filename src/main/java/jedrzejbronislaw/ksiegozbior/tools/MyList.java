package jedrzejbronislaw.ksiegozbior.tools;

import java.util.List;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MyList<T> {

	@NonNull
	private List<T> list;

	
	public T get(int index){
		return list.get(index);
	}
	public int size() {
		return list.size();
	}
	
	
	public String serialize(String separator) {
		return serializeList(list, separator);
	}
	public String serialize_coma() {
		return serializeList(list, ", ");
	}
	public String serialize_newLine() {
		return serializeList(list, System.lineSeparator());
	}
	
	private String serializeList(List<T> list, String separator) {
		StringBuilder sb = new StringBuilder();
		
		for(int i=0; i<list.size()-1; i++) {
			sb.append(list.get(i).toString());
			sb.append(separator);
		}
		
		if(list.size() >= 1)
			sb.append(list.get(list.size()-1));
		
		return sb.toString();
	}
}
