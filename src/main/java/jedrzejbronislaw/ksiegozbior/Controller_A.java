package jedrzejbronislaw.ksiegozbior;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jedrzejbronislaw.ksiegozbior.model.entities.Author;
import jedrzejbronislaw.ksiegozbior.model.repositories.AuthorRepository;

//@SpringBootApplication
@Controller
public class Controller_A{
	
	@Autowired
	public AuthorRepository autorRepository;
	
	@RequestMapping("/db")
    @ResponseBody
    public String test1() {
    	StringBuilder sb = new StringBuilder();
    	Author author1 = new Author();
    	
    	author1.setName("Henryk");
    	author1.setSurname("Sienkiewicz");
    	author1.setDescription("autor Quo Vadis");
    	
    	autorRepository.save(author1);
    	
    	for(Author a : autorRepository.findAll()) {
    		sb.append(a.toString()).append("<br>");
    	}
    	
    	return sb.toString();
    }
}