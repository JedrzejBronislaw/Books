package jedrzejbronislaw.ksiegozbior.model.projections;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jedrzejbronislaw.ksiegozbior.model.entities.Author;
import jedrzejbronislaw.ksiegozbior.model.entities.Title;
import jedrzejbronislaw.ksiegozbior.model.repositories.Repositories;
import jedrzejbronislaw.ksiegozbior.tools.MyList;
import jedrzejbronislaw.ksiegozbior.tools.StringNumber;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TheAuthor implements TheEnt{

	@NonNull
	private Author author;

	
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
//		String separator = ", ";
//		StringBuilder sb = new StringBuilder();
//		List<String> penNames = getPenNames();
//		
//		for(int i=0; i<penNames.size()-1; i++) {
//			sb.append(penNames.get(i));
//			sb.append(separator);
//		}
//		if(penNames.size() > 0)
//			sb.append(penNames.get(penNames.size()-1));
//		
//		return sb.toString();
		
		return getPenNames().serialize_coma();
	}
	
	
	public MyList<String> getTitlesNames() {
		List<String> names = new ArrayList<String>();
		List<Title> titles = getTitles();
		
		titles.forEach(t -> names.add(t.getTitle()));
		
		return new MyList<>(names);
	}
	
	public String getTitlesText() {
//		final String separator = System.lineSeparator();//", ";
//		List<Title> titles = getTitles();
//		StringBuilder sb = new StringBuilder();
//		
//		for(int i=0; i<titles.size()-1; i++) {
//			sb.append(titles.get(i).getTitle());
//			sb.append(separator);
//		}
//		
//		if(titles.size() > 0)
//			sb.append(titles.get(titles.size()-1).getTitle());
//		
//		return sb.toString();
		
		return getTitlesNames().serialize_newLine();
	};

	public List<Title> getTitles() {
		Set<Title> set = new HashSet<Title>();
		set.addAll(Repositories.getTitleRepository().findByAuthorAndPenNames(author.getId()));
		
		List<Title> list = List.copyOf(set);
		
		return Collections.unmodifiableList(list);
	};
	public List<Title> getTitlesAsHimself() {
		Set<Title> set = new HashSet<Title>();
		set.addAll(Repositories.getTitleRepository().findByAuthorId(author.getId()));
//		set.addAll(titleRepository.findByAuthorId(author.getId()));
		
		List<Title> list = List.copyOf(set);
		
		return Collections.unmodifiableList(list);
		
	};
//	TODO public List<Title> getTitlesAsPenNames() {
//		Set<Title> set = new HashSet<Title>();
//		set.addAll(titleRepository.findByAuthorAsPenNames(author));
//		
//		List<Title> list = List.copyOf(set);
//		
//		return Collections.unmodifiableList(list);
//	};
//	TODO public List<Title> getTitlesAs(penName) {
//		Set<Title> set = new HashSet<Title>();
//		set.addAll(titleRepository.findByAuthorAsPenName(penName));
//		
//		List<Title> list = List.copyOf(set);
//		
//		return Collections.unmodifiableList(list);
//	};
	
	public StringNumber<Integer> getTitlesCount() {
		int count = 0;
		
		count += Repositories.getAuthorshipRepository().countTitlesByAuthorId(author.getId());
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
