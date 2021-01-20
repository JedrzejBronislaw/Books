package jedrzejbronislaw.ksiegozbior.tools;

public class ISBN {
	
	enum ISBNType{ISBN10, ISBN13, Error};
	
	
	private long isbn;
	private ISBNType type;

	public ISBN(String isbn) {
		isbn = clean(isbn);
		this.isbn = Long.valueOf(isbn);
		type = matchType();		
	}
	
	public ISBN(long isbn) {
		this.isbn = isbn;
		type = matchType();
	}
	
	private static String clean(String isbn) {
		isbn = isbn.replace("-", "");
		isbn = isbn.replace(" ", "");
		return isbn;
	}
	
	private ISBNType matchType() {
		int length = Long.toString(isbn).length();
		
		if (length == 13)
			return ISBNType.ISBN13;
		if (length <= 10)
			return ISBNType.ISBN10;

		return ISBNType.Error;
	}

	//TODO public static boolean chceck(long isbn) {
	//		return false;
	//	}
	//TODO public static boolean chceck(String isbn) {
	//		isbn = clean(isbn);
	//		return false;
	//	}
	//	
	//TODO	public static boolean checkCheckDigit() {
	//		return false;
	//	}
	
	public long getLong() {
		return isbn;
	}
	
	public String toStr() {
		String s =  Long.toString(isbn);
		
		if (type == ISBNType.ISBN10 && s.length() < 10) {
			StringBuffer sb = new StringBuffer();
			int zeros = 10-s.length();
			
			for(int i=0; i<zeros ;i++)
				sb.append("0");
			
			sb.append(s);
			s = sb.toString();
		}
		
		return s;
	}
	
	public String getFormattedString() {
		//TODO check country prefix length
		//TODO check publisher prefix length
		String s =  toStr();
		
		
		if (type == ISBNType.ISBN13) {
			s = 
				s.substring(0, 3) +
				"-" +
				s.substring(3, 5) +
				"-" +
				s.substring(5, 9) +
				"-" +
				s.substring(9, 12) +
				"-" +
				s.substring(12, 13);
		} else if (type == ISBNType.ISBN10) {
			s = 
//				s.substring(0, 3) +
//				"-" +
				s.substring(0, 2) +
				"-" +
				s.substring(2, 6) +
				"-" +
				s.substring(6, 9) +
				"-" +
				s.substring(9, 10);
		} 
		
		return s;	
	}
}
