package jedrzejbronislaw.ksiegozbior.tools;

import java.util.ArrayList;
import java.util.List;

public interface Named {

	public String getName();
	
	static List<String> convertListToNamesList(List<? extends Named> list){
		List<String> namesList = new ArrayList<String>();
		
		list.forEach(c -> namesList.add(c.getName()));	

		return namesList;
	}
}
