package jedrzejbronislaw.ksiegozbior.tools;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class ISBNTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}
//	9788377859599
//	9788324148615
//	9788324150069
	@Test
	public void testGetLong1() {
		Long[] actuals = {
				new ISBN(9788377859599L).getLong(),
				new ISBN(9788324148615L).getLong(),
				new ISBN(9788324150069L).getLong()};
		Long[] expecteds = {9788377859599L, 9788324148615L, 9788324150069L};
		
		assertArrayEquals(expecteds, actuals);
	}
	@Test
	public void testGetLong2() {
		Long[] actuals = {
				new ISBN("9788377859599").getLong(),
				new ISBN("9788324148615").getLong(),
				new ISBN("9788324150069").getLong()};
		Long[] expecteds = {9788377859599L, 9788324148615L, 9788324150069L};
		
		assertArrayEquals(expecteds, actuals);
	}
	@Test
	public void testGetLong3() {
		Long[] actuals = {
				new ISBN("978-83-7785-959-9").getLong(),
				new ISBN("   97  88 324148 615  ").getLong(),
				new ISBN("9 -788 32--415 0-0 69").getLong()};
		Long[] expecteds = {9788377859599L, 9788324148615L, 9788324150069L};
		
		assertArrayEquals(expecteds, actuals);
	}
	@Test
	public void testGetLong4() {
		Long[] actuals = {
				new ISBN("8377859599").getLong(),
				new ISBN("0024148615").getLong(),
				new ISBN(69L).getLong()};
		Long[] expecteds = {8377859599L, 24148615L, 69L};
		
		assertArrayEquals(expecteds, actuals);
	}

	
	
	@Test
	public void testToStr() {
		String[] actuals = {
				new ISBN(9788377859599L).toStr(),
				new ISBN(8324148615L).toStr(),
				new ISBN(24150069L).toStr()};
		String[] expecteds = {"9788377859599", "8324148615", "0024150069"};
		
		assertArrayEquals(expecteds, actuals);
	}

}
