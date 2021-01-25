package jedrzejbronislaw.ksiegozbior.tools;

import java.util.List;
import java.util.stream.Collectors;

public interface Named {

	public String getName();
	
	static List<String> convertListToNamesList(List<? extends Named> list) {
		
		return list.stream()
			.map(element -> element.getName())
			.collect(Collectors.toList());
	}
}
