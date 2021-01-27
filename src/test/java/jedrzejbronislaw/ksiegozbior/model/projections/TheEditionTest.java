package jedrzejbronislaw.ksiegozbior.model.projections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.BeforeClass;
import org.junit.Test;

import jedrzejbronislaw.ksiegozbior.model.entities.Edition;
import jedrzejbronislaw.ksiegozbior.model.entities.Edition_Title;
import jedrzejbronislaw.ksiegozbior.model.entities.Title;

public class TheEditionTest {

	private static Title title1 = new Title();
	private static Title title2 = new Title();
	private static Title title3 = new Title();
	private static Title title4 = new Title();
		
	private static Edition edition1 = new Edition();
	private static Edition edition2 = new Edition();
	private static Edition edition3 = new Edition();
	private static Edition edition4 = new Edition();
	private static Edition edition5 = new Edition();
	private static Edition edition6 = new Edition();
	private static Edition edition7 = new Edition();
	private static Edition edition8 = new Edition();
	private static Edition edition9 = new Edition();
	private static Edition edition10 = new Edition();
	private static Edition edition11 = new Edition();
	
	
	private static Edition_Title prepareTitleForEdition(Edition edition, Title title, String titleInEdition, String subtitleInEdition) {
		Edition_Title et = new Edition_Title(edition, title, titleInEdition, subtitleInEdition);
		et.setEdition(edition);
		et.setTitleObj(title);
		
		return et;
	}	
	
	private static Edition_Title prepareTitleForEdition(Edition edition, Title title) {
		Edition_Title et = new Edition_Title(edition, title);
		et.setEdition(edition);
		et.setTitleObj(title);
		
		return et;
	}
	
	private static Edition addTitlesToEdition(Edition edition, Edition_Title... ets) {
		edition.setTitles(Stream.of(ets).collect(Collectors.toSet()));
		
		return edition;
	}
	
	@BeforeClass
	public static void prepare() throws Exception {

		title1.setTitle(   "Original title 1");
		title1.setSubtitle("Original subtitle 1");

		title2.setTitle(   "Original title 2");
		title2.setSubtitle("Original subtitle 2");

		title3.setTitle(   "Original title 3");
		title3.setSubtitle("Original subtitle 3");
		

		addTitlesToEdition(edition1,
			prepareTitleForEdition(edition1, title1, "Title 1 PL", "Subtitle 1 PL"),
			prepareTitleForEdition(edition1, title2, "Title 2 PL", "Subtitle 2 PL"));

		addTitlesToEdition(edition2,
			prepareTitleForEdition(edition2, title1, "Title 1 PL", "Subtitle 1 PL"),
			prepareTitleForEdition(edition2, title2));
		
		addTitlesToEdition(edition3,
			prepareTitleForEdition(edition3, title1, "", ""),
			prepareTitleForEdition(edition3, title2, "Title 2 PL", "Subtitle 2 PL"));

		addTitlesToEdition(edition4,
			prepareTitleForEdition(edition4, title1),
			prepareTitleForEdition(edition4, title2));

		addTitlesToEdition(edition5,
				prepareTitleForEdition(edition5, title1, "ET Title 1", "ET Subtitle 1"));
		
		addTitlesToEdition(edition6,
				prepareTitleForEdition(edition6, title1));
		
		addTitlesToEdition(edition7,
				prepareTitleForEdition(edition7, title1, "ET Title 7", "ET Subtitle 7"));

		addTitlesToEdition(edition8,
				prepareTitleForEdition(edition8, title4));

		addTitlesToEdition(edition9,
				prepareTitleForEdition(edition9, title2));
		
		addTitlesToEdition(edition10,
				prepareTitleForEdition(edition10, title1, "ET Title 10", "ET Subtitle 10"));
		
		addTitlesToEdition(edition11,
				prepareTitleForEdition(edition11, title1));
		
		
		edition1.setTitle(      "Title Edition 1");
		edition1.setSubtitle("Subtitle Edition 1");
		
		edition2.setTitle(      "Title Edition 2");

		edition5.setTitle(      "Title Edition 5");
		edition5.setSubtitle("Subtitle Edition 5");
		
		edition6.setTitle(      "Title Edition 6");
		edition6.setSubtitle("Subtitle Edition 6");
		
		edition10.setTitle(     "Title Edition 10");
		
		edition11.setTitle(     "Title Edition 11");
	}


	private static void assertEqualsAlt(Object actual, Object...expectedAlternatives) {
		boolean match = Stream.of(expectedAlternatives).anyMatch(actual::equals);
		
		assertTrue("Fail. Actual value:" + actual.toString(), match);
	}

	private static void assertNotEqualsAlt(Object actual, Object...expectedAlternatives) {
		boolean match = Stream.of(expectedAlternatives).anyMatch(actual::equals);
		
		assertFalse("Fail. Actual value:" + actual.toString(), match);
	}


	@Test
	public void testGetTitlesText1() {
		assertEqualsAlt(
				new TheEdition(edition1).getTitlesText(),
				"Title 1 PL, Title 2 PL",
				"Title 2 PL, Title 1 PL");
	}
	
	@Test
	public void testGetTitlesText1_negative() {
		assertNotEqualsAlt(
				new TheEdition(edition1).getTitlesText(),
				"Title 1 PL, Title 2 PL+",
				"Title 2 PL, Title 1 PL+");
	}

	@Test
	public void testGetTitlesText2() {
		assertEqualsAlt(
				new TheEdition(edition2).getTitlesText(),
				"Title 1 PL, Original title 2",
				"Original title 2, Title 1 PL"
				);
	}

	@Test
	public void testGetTitlesText3() {
		assertEqualsAlt(
				new TheEdition(edition3).getTitlesText(),
				"Original title 1, Title 2 PL",
				"Title 2 PL, Original title 1");
	}

	@Test
	public void testGetTitlesText4() {
		assertEqualsAlt(
				new TheEdition(edition4).getTitlesText(),
				"Original title 1, Original title 2",
				"Original title 2, Original title 1");
	}
	
	@Test
	public void testGetTitlesText5() {
		assertEqualsAlt(
				new TheEdition(edition5).getTitlesText(),
				"Title Edition 5");
	}
	
	@Test
	public void testGetTitlesText6() {
		assertEqualsAlt(
				new TheEdition(edition6).getTitlesText(),
				"Title Edition 6");
	}
	
	@Test
	public void testGetTitlesText7() {
		assertEqualsAlt(
				new TheEdition(edition7).getTitlesText(),
				"ET Title 7");
	}
	
	@Test
	public void testGetTitlesText8() {
		assertEqualsAlt(
				new TheEdition(edition8).getTitlesText(),
				TheEdition.NO_TITLE_NAME);
	}
	
	@Test
	public void testGetTitlesText9() {
		assertEqualsAlt(
				new TheEdition(edition9).getTitlesText(),
				"Original title 2");
	}

	
	//test -> getTitle
	

	@Test
	public void testGetTitle1() {
		TheEdition theEdition = new TheEdition(edition1);
		
		assertEquals("Title Edition 1", theEdition.getTitle());
	}
	
	@Test
	public void testGetTitle2() {
		TheEdition theEdition = new TheEdition(edition2);
		
		assertEquals("Title Edition 2", theEdition.getTitle());
	}
	
	@Test
	public void testGetTitle3() {
		TheEdition theEdition = new TheEdition(edition3);

		assertEquals("", theEdition.getTitle());
	}
	
	@Test
	public void testGetTitle4() {
		TheEdition theEdition = new TheEdition(edition4);
		
		assertEquals("", theEdition.getTitle());
	}
	
	@Test
	public void testGetTitle5() {
		TheEdition theEdition = new TheEdition(edition5);
		
		assertEquals("Title Edition 5", theEdition.getTitle());
	}
	
	@Test
	public void testGetTitle6() {
		TheEdition theEdition = new TheEdition(edition6);
		
		assertEquals("Title Edition 6", theEdition.getTitle());
	}
	
	@Test
	public void testGetTitle7() {
		TheEdition theEdition = new TheEdition(edition7);
		
		assertEquals("ET Title 7", theEdition.getTitle());
	}
	
	@Test
	public void testGetTitle8() {
		TheEdition theEdition = new TheEdition(edition8);
		
		assertEquals(TheEdition.NO_TITLE_NAME, theEdition.getTitle());
	}
	
	@Test
	public void testGetTitle9() {
		TheEdition theEdition = new TheEdition(edition9);
		
		assertEquals("Original title 2", theEdition.getTitle());
	}
	
	
	//test -> getTitles
	
	
	@Test
	public void testGetTitles1() {
		assertEquals(
				Set.of(title1, title2),
				new TheEdition(edition1).getTitles());
	}
	
	@Test
	public void testGetTitles1_negative() {
		assertNotEquals(
				Set.of(title1),
				new TheEdition(edition1).getTitles());
	}
	
	@Test
	public void testGetTitles2() {
		assertEquals(
				Set.of(title1, title2),
				new TheEdition(edition2).getTitles());
	}
	
	@Test
	public void testGetTitles3() {
		assertEquals(
				Set.of(title1, title2),
				new TheEdition(edition3).getTitles());
	}
	
	@Test
	public void testGetTitles4() {
		assertEquals(
				Set.of(title1, title2),
				new TheEdition(edition4).getTitles());
	}
	
	@Test
	public void testGetTitles5() {
		assertEquals(
				Set.of(title1),
				new TheEdition(edition5).getTitles());
	}
	
	@Test
	public void testGetTitles6() {
		assertEquals(
				Set.of(title1),
				new TheEdition(edition6).getTitles());
	}
	
	@Test
	public void testGetTitles7() {
		assertEquals(
				Set.of(title1),
				new TheEdition(edition7).getTitles());
	}
	
	@Test
	public void testGetTitles8() {
		assertEquals(
				Set.of(title4),
				new TheEdition(edition8).getTitles());
	}
	
	@Test
	public void testGetTitles9() {
		assertEquals(
				Set.of(title2),
				new TheEdition(edition9).getTitles());
	}

	
	//test -> getsubitle
	

	@Test
	public void testGetSubtitle01() {
		TheEdition theEdition = new TheEdition(edition1);
		
		assertEquals("Subtitle Edition 1", theEdition.getSubtitle());
	}
	
	@Test
	public void testGetSubtitle02() {
		TheEdition theEdition = new TheEdition(edition2);
		
		assertEquals("", theEdition.getSubtitle());
	}
	
	@Test
	public void testGetSubtitle03() {
		TheEdition theEdition = new TheEdition(edition3);
		
		assertEquals("", theEdition.getSubtitle());
	}
	
	@Test
	public void testGetSubtitle04() {
		TheEdition theEdition = new TheEdition(edition4);
		
		assertEquals("", theEdition.getSubtitle());
	}
	
	@Test
	public void testGetSubtitle05() {
		TheEdition theEdition = new TheEdition(edition5);
		
		assertEquals( "Subtitle Edition 5", theEdition.getSubtitle());
	}
	
	@Test
	public void testGetSubtitle06() {
		TheEdition theEdition = new TheEdition(edition6);
		
		assertEquals("Subtitle Edition 6", theEdition.getSubtitle());
	}
	
	@Test
	public void testGetSubtitle07() {
		TheEdition theEdition = new TheEdition(edition7);
		
		assertEquals( "ET Subtitle 7", theEdition.getSubtitle());
	}
	
	@Test
	public void testGetSubtitle08() {
		TheEdition theEdition = new TheEdition(edition8);
		
		assertEquals("", theEdition.getSubtitle());
	}
	
	@Test
	public void testGetSubtitle09() {
		TheEdition theEdition = new TheEdition(edition9);
		
		assertEquals("Original subtitle 2", theEdition.getSubtitle());
	}
	
	@Test
	public void testGetSubtitle10() {
		TheEdition theEdition = new TheEdition(edition10);
		
		assertEquals("", theEdition.getSubtitle());
	}
	
	@Test
	public void testGetSubtitle11() {
		TheEdition theEdition = new TheEdition(edition11);
		
		assertEquals("", theEdition.getSubtitle());
	}
}
