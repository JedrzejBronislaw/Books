package jedrzejbronislaw.ksiegozbior.tools;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StringNumber<E extends Number> {

	private final E number;
	
	private String zeroStringValue = null;
	private String negativeStringValue = null;
	private String positiveStringValue = null;
	
	public String str() {
		if (number == null) return "";
		
		String outcome;
		
		if(number instanceof Integer) outcome = Integer.toString((int)  number); else
		if(number instanceof Long)    outcome = Long   .toString((long) number); else
		if(number instanceof Short)   outcome = Short  .toString((short)number); else
		if(number instanceof Float)   outcome = Float  .toString((float)number); else
									  outcome ="error";
		
		if (isZero(outcome)) {
			if (zeroStringValue     != null) outcome =     zeroStringValue;
		} else
		if (isNegative(outcome)) {
			if (negativeStringValue != null) outcome = negativeStringValue;
		} else
		if (isPositive(outcome)) {
			if (positiveStringValue != null) outcome = positiveStringValue;
		}
		
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

	private boolean isZero(String outcome) {
		return outcome.equals("0");
	}

	private boolean isNegative(String outcome) {
		return outcome.charAt(0) == '-';
	}

	private boolean isPositive(String outcome) {
		return outcome.charAt(0) != '-';
	}
	
	@Override
	public String toString() {
		return str();
	}
}
