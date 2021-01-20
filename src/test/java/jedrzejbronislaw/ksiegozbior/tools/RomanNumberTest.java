package jedrzejbronislaw.ksiegozbior.tools;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

public class RomanNumberTest {

	@Test
	public void testToRoman() {
		String[] actuals = {
				RomanNumber.toRoman(1),
				RomanNumber.toRoman(2),
				RomanNumber.toRoman(3), 
				RomanNumber.toRoman(4), 
				RomanNumber.toRoman(5), 
				RomanNumber.toRoman(6), 
				RomanNumber.toRoman(7), 
				RomanNumber.toRoman(8), 
				RomanNumber.toRoman(9), 
				RomanNumber.toRoman(10), 
				RomanNumber.toRoman(11), 
				RomanNumber.toRoman(12), 
				RomanNumber.toRoman(13), 
				RomanNumber.toRoman(14), 
				RomanNumber.toRoman(15), 
				RomanNumber.toRoman(16), 
				RomanNumber.toRoman(17), 
				RomanNumber.toRoman(18), 
				RomanNumber.toRoman(19), 
				RomanNumber.toRoman(20), 
				RomanNumber.toRoman(21), 
				
				RomanNumber.toRoman(56), 
				RomanNumber.toRoman(77), 
				RomanNumber.toRoman(139), 
				RomanNumber.toRoman(2019), 
				RomanNumber.toRoman(33), 
				RomanNumber.toRoman(444), 
				RomanNumber.toRoman(987), 
				RomanNumber.toRoman(1001), 
				RomanNumber.toRoman(999), 
				RomanNumber.toRoman(937)
				};
		String[] expecteds = {
				"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X",
				"XI", "XII", "XIII", "XIV", "XV", "XVI", "XVII", "XVIII", "XIX", "XX",
				"XXI",
				
				"LVI", "LXXVII", "CXXXIX", "MMXIX", "XXXIII", "CDXLIV", "CMLXXXVII", "MI", "CMXCIX", "CMXXXVII"};
		
		assertArrayEquals(expecteds, actuals);
	}

}
