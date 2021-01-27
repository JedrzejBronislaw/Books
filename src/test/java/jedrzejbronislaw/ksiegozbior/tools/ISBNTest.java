package jedrzejbronislaw.ksiegozbior.tools;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ISBNTest {

	@Test
	public void testGetLong_constructorLong() {
		
		assertEquals(new ISBN(9788377859599L).getLong(), 9788377859599L);
		assertEquals(new ISBN(9788324148615L).getLong(), 9788324148615L);
		assertEquals(new ISBN(9788324150069L).getLong(), 9788324150069L);
	}
	
	@Test
	public void testGetLong_constructorString() {
		assertEquals(new ISBN("9788377859599").getLong(), 9788377859599L);
		assertEquals(new ISBN("9788324148615").getLong(), 9788324148615L);
		assertEquals(new ISBN("9788324150069").getLong(), 9788324150069L);
	}
	
	@Test
	public void testGetLong_constructorDirtyString() {
		assertEquals(new ISBN("978-83-7785-959-9")     .getLong(), 9788377859599L);
		assertEquals(new ISBN("   97  88 324148 615  ").getLong(), 9788324148615L);
		assertEquals(new ISBN("9 -788 32--415 0-0 69") .getLong(), 9788324150069L);
	}
	
	@Test
	public void testGetLong_constructorSmallISBN() {
		assertEquals(new ISBN("8377859599").getLong(), 8377859599L);
		assertEquals(new ISBN("0024148615").getLong(), 24148615L);
		assertEquals(new ISBN(69L)         .getLong(), 69L);
	}
	
	@Test
	public void testToStr_constructorLong() {
		assertEquals(new ISBN(9788377859599L).toStr(), "9788377859599");
		assertEquals(new ISBN(8324148615L)   .toStr(), "8324148615");
		assertEquals(new ISBN(24150069L)     .toStr(), "0024150069");
	}
}
