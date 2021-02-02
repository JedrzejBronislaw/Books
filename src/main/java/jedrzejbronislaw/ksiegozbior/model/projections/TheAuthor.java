package jedrzejbronislaw.ksiegozbior.model.projections;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import jedrzejbronislaw.ksiegozbior.model.entities.Author;
import jedrzejbronislaw.ksiegozbior.model.entities.Ent;
import jedrzejbronislaw.ksiegozbior.model.entities.Title;
import jedrzejbronislaw.ksiegozbior.model.repositories.AuthorshipRepository;
import jedrzejbronislaw.ksiegozbior.model.repositories.TitleRepository;
import jedrzejbronislaw.ksiegozbior.tools.MyList;
import jedrzejbronislaw.ksiegozbior.tools.StringNumber;
import lombok.NonNull;
import lombok.Setter;

@Component
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TheAuthor implements TheEnt {

	@Autowired TitleRepository titleRepository;
	@Autowired AuthorshipRepository authorshipRepository;
	
	@Setter @NonNull private Author author;

	
	public String getName() {
		return author.getName() + " " + author.getSurname();
	}
	
	public String getFirtstName() {
		return author.getName();
	}
	
	public String getSurname() {
		return author.getSurname();
	}

	public String getBirthDate() {
		return (author.getBirthDate() != null) ? author.getBirthDate().toString() : "-";
	}

	public String getDeathDate() {
		return (author.getDeathDate() != null) ? author.getDeathDate().toString() : "-";
	}
	
	public boolean isAlive() {
		return (author.getDeathDate() != null);
	}
	
//	TODO public Nationality getNationality() {}
	
	public String getDecsription() {
		return author.getDescription();
	}
	
	public MyList<String> getPenNames() {
		//TODO getPenNames
		return new MyList<>(new ArrayList<String>());
	}
	
	public String getPenNamesText() {
		return getPenNames().serialize_coma();
	}
	
	
	public MyList<String> getTitlesNames() {
		List<String> names = getTitles().stream()
			.map(t -> t.getTitle())
			.collect(Collectors.toUnmodifiableList());
		
		return new MyList<>(names);
	}
	
	public String getTitlesText() {
		return getTitlesNames().serialize_newLine();
	}

	public List<Title> getTitles() {
		List<Title> list = titleRepository.findByAuthorAndPenNames(author.getId());
		
		return Collections.unmodifiableList(list);
	}
	
	public List<Title> getTitlesAsHimself() {
		List<Title> list = titleRepository.findByAuthorId(author.getId());
		
		return Collections.unmodifiableList(list);
		
	};
//	TODO public List<Title> getTitlesAsPenNames() {
//		List<Title> list = titleRepository.findByAuthorAsPenNames(author);
//		
//		return Collections.unmodifiableList(list);
//	};
//	TODO public List<Title> getTitlesAs(penName) {
//		List<Title> list = titleRepository.findByAuthorAsPenName(penName);
//		
//		return Collections.unmodifiableList(list);
//	};
	
	public StringNumber<Integer> getTitlesCount() {
		int count = 0;
		
		count += authorshipRepository.countTitlesByAuthorId(author.getId());
//TODO	count += penNameAuthorshipRepository.countTitlesByAuthor(author);
		
		return new StringNumber<Integer>(count);
	}

	public String getNationality() {//TODO nationality
		return "";
	}
	
//	TODO public static List<Title> sortByYear(List<Title> list){}
	
	@Override
	public String getLabel() {		
		return author.toString();
	}

	@Override
	public boolean setEnt(Ent entity) {
		if (!(entity instanceof Author)) return false;
		
		setAuthor((Author) entity);
		return true;
	}

	public String getLiveDates() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
		StringBuilder sb = new StringBuilder();
		
		if (author.getBirthDate() != null || author.getDeathDate() != null) {
			sb.append("(");
			if (getBirthDate() != null)
				sb.append(formatter.format(author.getBirthDate()));
			if (author.getDeathDate() != null) {
				sb.append(" - ");
				sb.append(formatter.format(author.getDeathDate()));
			}
			sb.append(")");
		}
		
		return sb.toString().trim();
	}
}
