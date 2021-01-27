package jedrzejbronislaw.ksiegozbior.tools;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RomanNumberTest {

	@Test
	public void testToRoman_1() {
		assertEquals(RomanNumber.toRoman(1), "I");
	}
	
	@Test
	public void testToRoman_2() {
		assertEquals(RomanNumber.toRoman(2), "II");
	}
	
	@Test
	public void testToRoman_3() {
		assertEquals(RomanNumber.toRoman(3), "III");
	}
	
	@Test
	public void testToRoman_4() {
		assertEquals(RomanNumber.toRoman(4), "IV");
	}
	
	@Test
	public void testToRoman_5() {
		assertEquals(RomanNumber.toRoman(5), "V");
	}
	
	@Test
	public void testToRoman_6() {
		assertEquals(RomanNumber.toRoman(6), "VI");
	}
	
	@Test
	public void testToRoman_7() {
		assertEquals(RomanNumber.toRoman(7), "VII");
	}
	
	@Test
	public void testToRoman_8() {
		assertEquals(RomanNumber.toRoman(8), "VIII");
	}
	
	@Test
	public void testToRoman_9() {
		assertEquals(RomanNumber.toRoman(9), "IX");
	}
	
	@Test
	public void testToRoman_10() {
		assertEquals(RomanNumber.toRoman(10), "X");
	}
	
	@Test
	public void testToRoman_11() {
		assertEquals(RomanNumber.toRoman(11), "XI");
	}
	
	@Test
	public void testToRoman_12() {
		assertEquals(RomanNumber.toRoman(12), "XII");
	}
	
	@Test
	public void testToRoman_13() {
		assertEquals(RomanNumber.toRoman(13), "XIII");
	}
	
	@Test
	public void testToRoman_14() {
		assertEquals(RomanNumber.toRoman(14), "XIV");
	}
	
	@Test
	public void testToRoman_15() {
		assertEquals(RomanNumber.toRoman(15), "XV");
	}
	
	@Test
	public void testToRoman_16() {
		assertEquals(RomanNumber.toRoman(16), "XVI");
	}
	
	@Test
	public void testToRoman_17() {
		assertEquals(RomanNumber.toRoman(17), "XVII");
	}
	
	@Test
	public void testToRoman_18() {
		assertEquals(RomanNumber.toRoman(18), "XVIII");
	}
	
	@Test
	public void testToRoman_19() {
		assertEquals(RomanNumber.toRoman(19), "XIX");
	}
	
	@Test
	public void testToRoman_20() {
		assertEquals(RomanNumber.toRoman(20), "XX");
	}
	
	@Test
	public void testToRoman_21() {
		assertEquals(RomanNumber.toRoman(21), "XXI");
	}
	

	
	@Test
	public void testToRoman_56() {
		assertEquals(RomanNumber.toRoman(56), "LVI");
	}
	
	@Test
	public void testToRoman_77() {
		assertEquals(RomanNumber.toRoman(77), "LXXVII");
	}
	
	@Test
	public void testToRoman_139() {
		assertEquals(RomanNumber.toRoman(139), "CXXXIX");
	}
	
	@Test
	public void testToRoman_2019() {
		assertEquals(RomanNumber.toRoman(2019), "MMXIX");
	}
	
	@Test
	public void testToRoman_33() {
		assertEquals(RomanNumber.toRoman(33), "XXXIII");
	}
	
	@Test
	public void testToRoman_444() {
		assertEquals(RomanNumber.toRoman(444), "CDXLIV");
	}
	
	@Test
	public void testToRoman_987() {
		assertEquals(RomanNumber.toRoman(987), "CMLXXXVII");
	}
	
	@Test
	public void testToRoman_1001() {
		assertEquals(RomanNumber.toRoman(1001), "MI");
	}
	
	@Test
	public void testToRoman_999() {
		assertEquals(RomanNumber.toRoman(999), "CMXCIX");
	}
	
	@Test
	public void testToRoman_937() {
		assertEquals(RomanNumber.toRoman(937), "CMXXXVII");
	}
}
