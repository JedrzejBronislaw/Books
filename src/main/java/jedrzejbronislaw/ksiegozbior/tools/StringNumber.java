package jedrzejbronislaw.ksiegozbior.tools;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StringNumber<E extends Number> {

	@NonNull private E number;
	
	private String zeroStringValue = null;
	private String negativeStringValue = null;
	private String positiveStringValue = null;
	
	public String str() {
		String outcome;
		
		if(number instanceof Integer) outcome = Integer.toString((int)  number); else
		if(number instanceof Long)    outcome = Long   .toString((long) number); else
		if(number instanceof Short)   outcome = Short  .toString((short)number); else
		if(number instanceof Float)   outcome = Float  .toString((float)number); else
									  outcome ="error";
		
		if(    zeroStringValue != null && outcome.equals("0"))      outcome =     zeroStringValue; else
		if(negativeStringValue != null && outcome.charAt(0) == '-') outcome = negativeStringValue; else
		if(positiveStringValue != null)                             outcome = positiveStringValue;
		
		return outcome;
	}
	
	public E num() {
		return number;
	}
	

	public StringNumber<E> setZero(String zeroValue) {
		zeroStringValue = zeroValue;
		return this;
	}
	public StringNumber<E> setNegative(String negativeValue) {
		negativeStringValue = negativeValue;
		return this;
	}
	public StringNumber<E> setPositive(String positiveValue) {
		positiveStringValue = positiveValue;
		return this;
	}
	
	@Override
	public String toString() {
		return str();
	}
}
