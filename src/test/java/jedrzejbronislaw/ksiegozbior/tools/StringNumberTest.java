package jedrzejbronislaw.ksiegozbior.tools;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class StringNumberTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	//positive
	
	@Test
	public void positiveInt() {
		assertEquals("5", new StringNumber<Integer>(5).str());
	}

	@Test
	public void positiveLong() {
		assertEquals("5", new StringNumber<Long>(5L).str());
	}

	@Test
	public void positiveShort() {
		assertEquals("5", new StringNumber<Short>((short)5).str());
	}

	@Test
	public void positiveFloat() {
		assertEquals("5.0", new StringNumber<Float>(5f).str());
	}

	//negative
	
	@Test
	public void negativeInt() {
		assertEquals("-5", new StringNumber<Integer>(-5).str());
	}

	@Test
	public void negativeLong() {
		assertEquals("-5", new StringNumber<Long>(-5L).str());
	}

	@Test
	public void negativeShort() {
		assertEquals("-5", new StringNumber<Short>((short)-5).str());
	}

	@Test
	public void negativeFloat() {
		assertEquals("-5.0", new StringNumber<Float>(-5f).str());
	}

	//zero
	
	@Test
	public void zeroInt() {
		assertEquals("0", new StringNumber<Integer>(0).str());
	}

	@Test
	public void zeroLong() {
		assertEquals("0", new StringNumber<Long>(0L).str());
	}

	@Test
	public void zeroShort() {
		assertEquals("0", new StringNumber<Short>((short)0).str());
	}

	@Test
	public void zeroFloat() {
		assertEquals("0.0", new StringNumber<Float>(0f).str());
	}
	

	//filters
	//single filter

	@Test
	public void int_negativeFilter() {
		assertEquals(":(", new StringNumber<Integer>(-5)  .setNegative(":(").str());
		assertEquals( "0", new StringNumber<Integer>( 0)  .setNegative(":(").str());
		assertEquals( "5", new StringNumber<Integer>( 5)  .setNegative(":(").str());
	}
	
	@Test
	public void int_positiveFilter() {
		assertEquals("-5", new StringNumber<Integer>(-5)  .setPositive(":)").str());
		assertEquals( "0", new StringNumber<Integer>( 0)  .setPositive(":)").str());
		assertEquals(":)", new StringNumber<Integer>( 5)  .setPositive(":)").str());
	}
	
	@Test
	public void int_zeroFilter() {
		assertEquals("-5", new StringNumber<Integer>(-5)  .setZero(":|").str());
		assertEquals(":|", new StringNumber<Integer>( 0)  .setZero(":|").str());
		assertEquals( "5", new StringNumber<Integer>( 5)  .setZero(":|").str());
	}
	
	//two filters

	@Test
	public void int_negativeAndPositiveFilters() {
		assertEquals(":(", new StringNumber<Integer>(-5)  .setNegative(":(").setPositive(":)").str());
		assertEquals( "0", new StringNumber<Integer>( 0)  .setNegative(":(").setPositive(":)").str());
		assertEquals(":)", new StringNumber<Integer>( 5)  .setNegative(":(").setPositive(":)").str());
	}
	
	@Test
	public void int_positiveAndZeroFilters() {
		assertEquals("-5", new StringNumber<Integer>(-5)  .setPositive(":)").setZero(":|").str());
		assertEquals(":|", new StringNumber<Integer>( 0)  .setPositive(":)").setZero(":|").str());
		assertEquals(":)", new StringNumber<Integer>( 5)  .setPositive(":)").setZero(":|").str());
	}
	
	@Test
	public void int_zeroAndNegativeFilters() {
		assertEquals(":(", new StringNumber<Integer>(-5)  .setZero(":|").setNegative(":(").str());
		assertEquals(":|", new StringNumber<Integer>( 0)  .setZero(":|").setNegative(":(").str());
		assertEquals( "5", new StringNumber<Integer>( 5)  .setZero(":|").setNegative(":(").str());
	}
	
	//three filters

	@Test
	public void int_negativePositiveZeroFilters() {
		assertEquals(":(", new StringNumber<Integer>(-5)  .setNegative(":(").setPositive(":)").setZero(":|").str());
		assertEquals(":|", new StringNumber<Integer>( 0)  .setNegative(":(").setPositive(":)").setZero(":|").str());
		assertEquals(":)", new StringNumber<Integer>( 5)  .setNegative(":(").setPositive(":)").setZero(":|").str());
	}
}
