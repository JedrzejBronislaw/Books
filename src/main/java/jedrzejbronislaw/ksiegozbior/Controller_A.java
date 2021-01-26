package jedrzejbronislaw.ksiegozbior;

import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jedrzejbronislaw.ksiegozbior.model.entities.Author;
import jedrzejbronislaw.ksiegozbior.model.repositories.AuthorRepository;

@Controller
public class Controller_A {
	
	@Autowired private AuthorRepository authorRepository;
	
	
	@RequestMapping("/db")
    @ResponseBody
    public String test1() {
		saveNewAuthor();
    	return getAuthors();
    }

	private void saveNewAuthor() {
		Author author = new Author();
    	
    	author.setName("Henryk");
    	author.setSurname("Sienkiewicz");
    	author.setDescription("autor Quo Vadis");
    	
    	authorRepository.save(author);
	}
	
	private String getAuthors() {
		StringBuilder sb = new StringBuilder();
		
		Stream.of(authorRepository.findAll())
		.map(a -> a.toString())
		.forEach(a -> sb.append(a).append("<BR>"));
		
		return sb.toString();
	}
}