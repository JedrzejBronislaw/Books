package jedrzejbronislaw.ksiegozbior;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jedrzejbronislaw.ksiegozbior.model.entities.Author;
import jedrzejbronislaw.ksiegozbior.model.entities.Authorship;
import jedrzejbronislaw.ksiegozbior.model.entities.Book;
import jedrzejbronislaw.ksiegozbior.model.entities.BookComment;
import jedrzejbronislaw.ksiegozbior.model.entities.Edition;
import jedrzejbronislaw.ksiegozbior.model.entities.Edition_Title;
import jedrzejbronislaw.ksiegozbior.model.entities.Language;
import jedrzejbronislaw.ksiegozbior.model.entities.Library;
import jedrzejbronislaw.ksiegozbior.model.entities.Location;
import jedrzejbronislaw.ksiegozbior.model.entities.PublishingHouse;
import jedrzejbronislaw.ksiegozbior.model.entities.Title;
import jedrzejbronislaw.ksiegozbior.model.entities.User;
import jedrzejbronislaw.ksiegozbior.model.entities.User.Mode;
import jedrzejbronislaw.ksiegozbior.model.entities.User.Role;
import jedrzejbronislaw.ksiegozbior.model.entities.Visibility;
import jedrzejbronislaw.ksiegozbior.model.entities.collections.BookCollection;
import jedrzejbronislaw.ksiegozbior.model.entities.collections.BookCollectionLink;
import jedrzejbronislaw.ksiegozbior.model.entities.collections.EditionCollection;
import jedrzejbronislaw.ksiegozbior.model.entities.collections.EditionCollectionLink;
import jedrzejbronislaw.ksiegozbior.model.entities.collections.TitleCollection;
import jedrzejbronislaw.ksiegozbior.model.entities.collections.TitleCollectionLink;
import jedrzejbronislaw.ksiegozbior.model.repositories.AuthorRepository;
import jedrzejbronislaw.ksiegozbior.model.repositories.AuthorshipRepository;
import jedrzejbronislaw.ksiegozbior.model.repositories.BookCollectionLinkRepository;
import jedrzejbronislaw.ksiegozbior.model.repositories.BookCollectionRepository;
import jedrzejbronislaw.ksiegozbior.model.repositories.BookCommentRepository;
import jedrzejbronislaw.ksiegozbior.model.repositories.BookRepository;
import jedrzejbronislaw.ksiegozbior.model.repositories.EditionCollectionLinkRepository;
import jedrzejbronislaw.ksiegozbior.model.repositories.EditionCollectionRepository;
import jedrzejbronislaw.ksiegozbior.model.repositories.EditionRepository;
import jedrzejbronislaw.ksiegozbior.model.repositories.Edition_TitleRepository;
import jedrzejbronislaw.ksiegozbior.model.repositories.LanguageRepository;
import jedrzejbronislaw.ksiegozbior.model.repositories.LibraryRepository;
import jedrzejbronislaw.ksiegozbior.model.repositories.LocationRepository;
import jedrzejbronislaw.ksiegozbior.model.repositories.PublishingHouseRepository;
import jedrzejbronislaw.ksiegozbior.model.repositories.TitleCollectionLinkRepository;
import jedrzejbronislaw.ksiegozbior.model.repositories.TitleCollectionRepository;
import jedrzejbronislaw.ksiegozbior.model.repositories.TitleRepository;
import jedrzejbronislaw.ksiegozbior.model.repositories.UserRepository;

@Component("testData")
public class TestData {

	@Autowired private UserRepository userRepositiry;
	@Autowired private LibraryRepository libraryRepository;
	
	@Autowired private LanguageRepository languageRepository;
	@Autowired private LocationRepository locationRepository;
	@Autowired private PublishingHouseRepository publishingHouseRepository;
	@Autowired private AuthorRepository authorRepository;
	@Autowired private AuthorshipRepository authorshipRepository;
	@Autowired private TitleRepository titleRepository;
	@Autowired private EditionRepository editionRepository;
	@Autowired private Edition_TitleRepository edition_TitleRepository;
	@Autowired private BookRepository bookRepository;
	@Autowired private BookCommentRepository bookCommentRepository;
	@Autowired private TitleCollectionRepository titleCollectionRepository;
	@Autowired private TitleCollectionLinkRepository titleCollectionLinkRepository;
	@Autowired private EditionCollectionRepository editionCollectionRepository;
	@Autowired private EditionCollectionLinkRepository editionCollectionLinkRepository;
	@Autowired private BookCollectionRepository bookCollectionRepository;
	@Autowired private BookCollectionLinkRepository bookCollectionLinkRepository;
	
	
	public void save() {
		saveTestUsersAndLibraries();
		
		saveTestLanguages();
		saveTestLocations();
		saveTestPublishingHouses();
		
		saveTestAuthors();
		saveTestTitles();
		saveTestEdition();
		saveTestBooks();

		saveTestComments();
		
		saveTestTitleCollections();
		saveTestEditionCollections();
		saveTestBookCollections();
	}

	private void saveTestUsersAndLibraries() {
		User admin = new User();
		User user = new User();
		
		admin.setLogin("admin");
		admin.setFirstName("admin");
		admin.setLastName("admin");
		admin.setEmail("admin@admin.com");
		admin.setRegistrationTime(Timestamp.valueOf(LocalDateTime.now()));
		admin.setMode(Mode.Ok);
		admin.setRole(Role.Admin);
		
		user.setLogin("kowal");
		user.setFirstName("Adam");
		user.setLastName("Kowalski");
		user.setEmail("adam@kowalski.com");
		user.setRegistrationTime(Timestamp.valueOf(LocalDateTime.now()));
		
		userRepositiry.save(admin);
		userRepositiry.save(user);
		
		
		
		Library adminLibrary = new Library();
		Library userLibrary = new Library();
		Library commonLibrary = new Library();
		
		adminLibrary.setName("Admin's Library");
		userLibrary.setName("Adam's Library");
		commonLibrary.setName("Admin's and Adam's Library");
		
		List<Library> adminsLibs1 = new ArrayList<Library>();
		List<Library> usersLibs2 = new ArrayList<Library>();
		
		adminsLibs1.add(adminLibrary);
		adminsLibs1.add(commonLibrary);
		usersLibs2.add(userLibrary);
		usersLibs2.add(commonLibrary);
		
		libraryRepository.save(adminLibrary);
		libraryRepository.save(userLibrary);
		libraryRepository.save(commonLibrary);
		
		
		
		admin.setLibraries(adminsLibs1);
		user.setLibraries(usersLibs2);

		userRepositiry.save(admin);
		userRepositiry.save(user);
	}

	private void saveTestLanguages() {
		Language lang;
		
		lang = new Language();
		lang.setName("Polski");
		lang.setAbbr("PL");
		languageRepository.save(lang);

		lang = new Language();
		lang.setName("Angielski");
		lang.setAbbr("EN");
		languageRepository.save(lang);

		lang = new Language();
		lang.setName("Niemiecki");
		lang.setAbbr("DE");
		languageRepository.save(lang);
		
	}

	private void saveTestLocations() {
		Location location, locationDom, locationPokoj;
		
		locationDom = new Location();
		locationDom.setName("Dom");
		locationDom.setDescription("Dom w Poznaniu");
		locationRepository.save(locationDom);
		
		location = new Location();
		location.setName("Działka");
		location.setDescription("Działka w Kórniku");
		locationRepository.save(location);

		
		location = new Location();
		location.setName("Salon");
		location.setSuperLocation(locationDom);
		locationRepository.save(location);

		locationPokoj = new Location();
		locationPokoj.setName("Pokój");
		locationPokoj.setSuperLocation(locationDom);
		locationPokoj.setDescription("Pokój gościnny");
		locationRepository.save(locationPokoj);

		location = new Location();
		location.setName("Korytarz");
		location.setSuperLocation(locationDom);
		locationRepository.save(location);

		location = new Location();
		location.setName("Półka przy oknie");
		location.setSuperLocation(locationPokoj);
		locationRepository.save(location);

		location = new Location();
		location.setName("Półka przy drzwiach");
		location.setSuperLocation(locationPokoj);
		locationRepository.save(location);
	}

	private void saveTestPublishingHouses() {
		PublishingHouse publisher;
		
		publisher = new PublishingHouse();
		publisher.setName("Polskie Wydawnictwo Naukowe");
		publisher.setCity("Warszawa");
		publisher.setAbbr("PWN");
		publishingHouseRepository.save(publisher);
		
		publisher = new PublishingHouse();
		publisher.setName("Zyski i S-ka");
		publisher.setCity("Poznań");
		publisher.setAbbr("Zysk");
		publishingHouseRepository.save(publisher);
		
		publisher = new PublishingHouse();
		publisher.setName("Amber");
		publisher.setCity("Warszawa");
		publisher.setAbbr("Amber");
		publishingHouseRepository.save(publisher);
	}

	private void saveTestAuthors() {
		Author author;
		
		author = new Author();
		author.setName("Adam");
		author.setSurname("Mickiewicz");
		author.setBirthDate(Date.valueOf("1789-12-24"));
		author.setDeathDate(Date.valueOf("1855-11-26"));
		author.setDescription("A. Mickiewicz - opis");
		authorRepository.save(author);
		
		author = new Author();
		author.setName("Henryk");
		author.setSurname("Sienkiewicz");
		author.setBirthDate(Date.valueOf("1846-5-5"));
		author.setDeathDate(Date.valueOf("1916-11-15"));
		author.setDescription("H. Sienkiewicz - opis");
		authorRepository.save(author);
		
		author = new Author();
		author.setName("John R. R.");
		author.setSurname("Tolkien");
		author.setBirthDate(Date.valueOf("1892-1-3"));
		author.setDeathDate(Date.valueOf("1973-9-2"));
		author.setDescription("Brytyjski pisarz oraz profesor filologii klasycznej i literatury staroangielskiej na University of Oxford. Jako autor powieści Władca Pierścieni, której akcja rozgrywa się w mitycznym świecie Śródziemia, spopularyzował literaturę fantasy. ");
		authorRepository.save(author);
		
		author = new Author();
		author.setName("Christopher");
		author.setSurname("Tolkien");
		author.setBirthDate(Date.valueOf("1924-11-21"));
		author.setDescription("Syn J. R. R. Tolkiena");
		authorRepository.save(author);
		
		author = new Author();
		author.setName("Elżbieta");
		author.setSurname("Cherezińska");
		authorRepository.save(author);
	}

	private void saveTestTitles() {
		Title title;
		
		Language langPL = languageRepository.findByAbbr("PL")[0];
		Language langEN = languageRepository.findByAbbr("EN")[0];
		Author authorMickiewicz = authorRepository.findByName("Adam","Mickiewicz").get(0);
		Author authorSienkiewicz = authorRepository.findByName("Henryk","Sienkiewicz").get(0);
		Author authorJRRTolkien = authorRepository.findByName("John R. R.","Tolkien").get(0);
		Author authorChTolkien = authorRepository.findByName("Christopher","Tolkien").get(0);
		Author authorCherezinska = authorRepository.findBySurname("Cherezińska").get(0);
		
		
		title = new Title();
		title.setTitle("Pan Tadeusz, czyli Ostatni zajazd na Litwie");
		title.setSubtitle("historia szlachecka z roku 1811 i 1812 we dwunastu księgach wierszem");
		title.setLanguage(langPL);
		title.setDescription("Epopeja narodowa z elementami gawędy szlacheckiej powstała w latach 1832–1834 w Paryżu. Składa się z dwunastu ksiąg pisanych wierszem, trzynastozgłoskowym aleksandrynem polskim. Czas akcji: pięć dni z roku 1811 i dwa dni z roku 1812.");
		title.setYear((short) 1834);
		titleRepository.save(title);
		authorshipRepository.save(new Authorship(authorMickiewicz,title));

		title = new Title();
		title.setTitle("Quo vadis");
		title.setSubtitle("Powieść z czasów Nerona");
		title.setLanguage(langPL);
		title.setDescription("Publikowana najpierw w odcinkach w warszawskiej „Gazecie Polskiej” (lata 1895–1896) i – z minimalnym opóźnieniem w stosunku do „Gazety” – także w krakowskim dzienniku „Czas” i „Dzienniku Poznańskim”. Wkrótce powieść została wydana w formie druku zwartego, jej premiera odbyła się w Krakowie, w 1896 roku. Powieść odniosła światowy sukces i została przetłumaczona na ponad pięćdziesiąt języków. Pełny tekst powieści Quo vadis znajduje się w rękopisie Biblioteki Narodowej, a we wrocławskim Ossolineum znajduje się 39 luźnych kart pierwszego szkicu powieści. Rękopis BN został zakupiony od spadkobierców Leopolda Kronenberga. Karty znajdujące się w zbiorach Ossolineum pochodzą ze zbiorów rodziny pisarza z Oblęgorka.");
		title.setYear((short) 1896);
		titleRepository.save(title);
		authorshipRepository.save(new Authorship(authorSienkiewicz,title));
			

		title = new Title();
		title.setTitle("Ogniem i mieczem");
		title.setSubtitle("Powieść z lat dawnych");
		title.setLanguage(langPL);
		title.setDescription("pierwsza z trzech powieści historycznych będących częścią Trylogii, pisanej dla pokrzepienia serc przez Henryka Sienkiewicza w latach 1884–1888.");
		title.setYear((short) 1884);
		titleRepository.save(title);
		authorshipRepository.save(new Authorship(authorSienkiewicz,title));
			
		title = new Title();
		title.setTitle("Potop");
		title.setLanguage(langPL);
		title.setYear((short) 1886);
		titleRepository.save(title);
		authorshipRepository.save(new Authorship(authorSienkiewicz,title));			

		title = new Title();
		title.setTitle("Pan Wołodyjowski ");
		title.setLanguage(langPL);
		title.setYear((short) 1888);
		titleRepository.save(title);
		authorshipRepository.save(new Authorship(authorSienkiewicz,title));
		

		title = new Title();
		title.setTitle("The Silmarillion");
		title.setLanguage(langEN);
		title.setYear((short) 1977);
		titleRepository.save(title);
		authorshipRepository.save(new Authorship(authorJRRTolkien,title));
		authorshipRepository.save(new Authorship(authorChTolkien,title));

		title = new Title();
		title.setTitle("The Hobbit");
		title.setSubtitle("or There and Back Again");
		title.setLanguage(langEN);
		title.setYear((short) 1937);
		titleRepository.save(title);
		authorshipRepository.save(new Authorship(authorJRRTolkien,title));

		title = new Title();
		title.setTitle("The Fellowship of the Ring");
		title.setLanguage(langEN);
		title.setYear((short) 1954);
		titleRepository.save(title);
		authorshipRepository.save(new Authorship(authorJRRTolkien,title));

		title = new Title();
		title.setTitle("The Two Towers");
		title.setLanguage(langEN);
		title.setYear((short) 1954);
		titleRepository.save(title);
		authorshipRepository.save(new Authorship(authorJRRTolkien,title));

		title = new Title();
		title.setTitle("The Return of the King");
		title.setLanguage(langEN);
		title.setYear((short) 1955);
		titleRepository.save(title);
		authorshipRepository.save(new Authorship(authorJRRTolkien,title));
			
		title = new Title();
		title.setTitle("Harda");
		title.setLanguage(langPL);
		title.setYear((short) 2016);
		titleRepository.save(title);
		authorshipRepository.save(new Authorship(authorCherezinska,title));
	}

	private void saveTestEdition() {
		Edition edition;

		Language langPL = languageRepository.findByAbbr("PL")[0];
		
		PublishingHouse publisherZysk = publishingHouseRepository.findByName("Zysk").get(0);
		PublishingHouse publisherAmber = publishingHouseRepository.findByName("Amber").get(0);

		edition = new Edition();
		edition.setTitle(null);
		edition.setSubtitle(null);
		edition.setPublishingHouse(publisherZysk);
		edition.setNumber((short) 1);
		edition.setYear((short) 2016);
		edition.setHardCover(false);
		edition.setISBN(9788377859599L);
		edition.setLanguage(langPL);
		edition.setNumOfPages((short) 573);
//		edition.setDescription(description);
		editionRepository.save(edition);
		edition_TitleRepository.save(new Edition_Title(edition, titleRepository.findByName("Harda").get(0)));

		edition = new Edition();
		edition.setTitle("Silmarillion");
		edition.setSubtitle(null);
		edition.setPublishingHouse(publisherAmber);
		edition.setNumber((short) 20);
		edition.setYear((short) 2013);
		edition.setHardCover(false);
		edition.setISBN(9788324148615L);
		edition.setLanguage(langPL);
		edition.setNumOfPages((short) 447);
		edition.setDescription("wydanie kieszonkowe, przekład Maria Skibniewska, ilustracje Teda Nasmitha");
		editionRepository.save(edition);
		edition_TitleRepository.save(new Edition_Title(edition, titleRepository.findByName("The Silmarillion").get(0)));

		edition = new Edition();
		edition.setTitle("Hobbit");
		edition.setSubtitle(null);
		edition.setPublishingHouse(publisherAmber);
		edition.setNumber((short) 23);
		edition.setYear((short) 2014);
		edition.setHardCover(false);
		edition.setISBN(9788324150069L);
		edition.setLanguage(langPL);
		edition.setNumOfPages((short) 349);
		edition.setDescription("wydanie kieszonkowe, przekład Paulina Braiter, ilustracje Alan Lee");
		editionRepository.save(edition);
		edition_TitleRepository.save(new Edition_Title(edition, titleRepository.findByName("The Hobbit").get(0)));

		edition = new Edition();
		edition.setTitle("Dzieła Tolkiena");
		edition.setSubtitle(null);
//		edition.setPublishingHouse(publisherAmber);
//		edition.setNumber((short) 23);
//		edition.setYear((short) 2014);
		edition.setHardCover(false);
//		edition.setISBN(9788324150069L);
		edition.setLanguage(langPL);
//		edition.setNumOfPages((short) 349);
//		edition.setDescription("wydanie kieszonkowe, przekład Paulina Braiter, ilustracje Alan Lee");
		editionRepository.save(edition);
		edition_TitleRepository.save(new Edition_Title(edition, titleRepository.findByName("The Hobbit").get(0), "O Hobbicie", ""));
		edition_TitleRepository.save(new Edition_Title(edition, titleRepository.findByName("The Silmarillion").get(0)));//, "O Silmarillionie", ""));
		
	}

	private void saveTestBooks() {
		Book book;
		
		Location locationDzialka = locationRepository.findByName("Działka").get(0);
		Location locationPolkaOkno = locationRepository.findByName("Półka przy oknie").get(0);
		Location locationPolkaDrzwi = locationRepository.findByName("Półka przy drzwiach").get(0);
		
		Edition editionHarda = editionRepository.findByTitle("Harda").get(0);
		Edition editionSilmallirion = editionRepository.findByTitle("Silmarillion").get(0);
		Edition editionHobbit = editionRepository.findByTitle("The Hobbit").get(0);
		Edition editionDzielaTolkiena = editionRepository.findByTitle("Dzieła Tolkiena").get(0);
		
		Library library1 = libraryRepository.findById(1L).get();
		Library library2 = libraryRepository.findById(2L).get();
		Library library3 = libraryRepository.findById(3L).get();
		
		book = new Book();
		book.setEdition(editionHarda);
		book.setLocation(locationDzialka);
		book.setPurchaseDate(Date.valueOf("2010-1-5"));
		book.setVisibility(Visibility.OwnerOnly);
		book.setLibrary(library1);
		bookRepository.save(book);
		
		book = new Book();
		book.setEdition(editionSilmallirion);
		book.setLocation(locationPolkaDrzwi);
		book.setPurchaseDate(Date.valueOf("2012-4-15"));
		book.setVisibility(Visibility.All);
		book.setLibrary(library2);
		bookRepository.save(book);
		
		book = new Book();
		book.setEdition(editionHobbit);
		book.setLocation(locationPolkaOkno);
		book.setPurchaseDate(Date.valueOf("2019-2-13"));
		book.setVisibility(Visibility.Friends);
		book.setLibrary(library3);
		bookRepository.save(book);
		
		book = new Book();
		book.setEdition(editionDzielaTolkiena);
		book.setLocation(locationPolkaOkno);
		book.setPurchaseDate(Date.valueOf("2019-2-13"));
		book.setVisibility(Visibility.Default);
		book.setLibrary(library3);
		bookRepository.save(book);
	}

	private void saveTestComments() {
		BookComment comment;
		
		Book bookHarda        = bookRepository.findByTitle("Harda").get(0);
		Book bookSilmallirion = bookRepository.findByTitle("The Silmarillion").get(0);
		Book bookHobbit       = bookRepository.findByTitle("Hobbit").get(0);
		
		comment = new BookComment("missing pages 105-106");
		bookHarda.getComments().add(comment);
		bookCommentRepository.save(comment);

		comment = new BookComment("I got this book from Adam");
		bookSilmallirion.getComments().add(comment);
		bookHarda.getComments().add(comment);
		bookCommentRepository.save(comment);
		
		comment = new BookComment("flooded");
		bookHobbit.getComments().add(comment);
		bookCommentRepository.save(comment);

		bookRepository.save(bookHarda);
		bookRepository.save(bookSilmallirion);
		bookRepository.save(bookHobbit);
	}

	private void saveTestTitleCollections() {
		TitleCollection collTolkien, collLOTR, collTrylogia;
		
		Title titleSilmarillion = titleRepository.findByName("The Silmarillion").get(0);
		Title titleHobbit = titleRepository.findByName("The Hobbit").get(0);
		Title titleLOTR1 = titleRepository.findByName("The Fellowship of the Ring").get(0);
		Title titleLOTR2 = titleRepository.findByName("The Two Towers").get(0);
		Title titleLOTR3 = titleRepository.findByName("The Return of the King").get(0);
		Title titleTrylogia1 = titleRepository.findByName("Ogniem i mieczem").get(0);
		Title titleTrylogia2 = titleRepository.findByName("Potop").get(0);
		Title titleTrylogia3 = titleRepository.findByName("Pan Wołodyjowski ").get(0);

		collTolkien = new TitleCollection("Legendarium Tolkiena");
		titleCollectionRepository.save(collTolkien);
		titleCollectionLinkRepository.save(new TitleCollectionLink(collTolkien, titleSilmarillion, (short)1));
		titleCollectionLinkRepository.save(new TitleCollectionLink(collTolkien, titleHobbit, (short)2));

		collLOTR = new TitleCollection("Władca Pierścieni");
		collLOTR.setSuperCollection(collTolkien);
		titleCollectionRepository.save(collLOTR);
		titleCollectionLinkRepository.save(new TitleCollectionLink(collLOTR, titleLOTR1, (short)1));
		titleCollectionLinkRepository.save(new TitleCollectionLink(collLOTR, titleLOTR2, (short)2));
		titleCollectionLinkRepository.save(new TitleCollectionLink(collLOTR, titleLOTR3, (short)3));

		collTrylogia = new TitleCollection("Trylogia Sienkiewicza");
		titleCollectionRepository.save(collTrylogia);
		titleCollectionLinkRepository.save(new TitleCollectionLink(collTrylogia, titleTrylogia1, (short)1));
		titleCollectionLinkRepository.save(new TitleCollectionLink(collTrylogia, titleTrylogia2, (short)2));
		titleCollectionLinkRepository.save(new TitleCollectionLink(collTrylogia, titleTrylogia3, (short)3));

	}

	private void saveTestEditionCollections() {
		EditionCollection collection;
		
		Edition editionSilmarillion = editionRepository.findByTitle("Silmarillion").get(0);
		Edition editionHobbit = editionRepository.findByTitle("Hobbit").get(0);
		
		collection = new EditionCollection("Bestsellery do kieszeni");
		editionCollectionRepository.save(collection);
		editionCollectionLinkRepository.save(new EditionCollectionLink(collection, editionSilmarillion, (short)0));
		editionCollectionLinkRepository.save(new EditionCollectionLink(collection, editionHobbit, (short)0));
	}

	private void saveTestBookCollections() {
		BookCollection collection;
		
		Book bookSilmalition = bookRepository.findByTitle("Silmarillion").get(0);
		Book bookHobbit = bookRepository.findByTitle("Hobbit").get(0);
		Book bookHarda = bookRepository.findByTitle("Harda").get(0);
		
		collection = new BookCollection("Ulubione");
		bookCollectionRepository.save(collection);
		bookCollectionLinkRepository.save(new BookCollectionLink(collection, bookSilmalition, (short)0));
		bookCollectionLinkRepository.save(new BookCollectionLink(collection, bookHobbit, (short)0));
		bookCollectionLinkRepository.save(new BookCollectionLink(collection, bookHarda, (short)0));
	}
}
