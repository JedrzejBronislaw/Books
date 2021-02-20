package jedrzejbronislaw.ksiegozbior.tools;

public class ISBN {
	
	enum ISBNType{ISBN10, ISBN13, ERROR};
	
	private long isbn;
	private ISBNType type;
	

	public ISBN(String isbn) {
		isbn = clean(isbn);
		this.isbn = Long.valueOf(isbn);
		type = matchType();
	}
	
	public ISBN(Long isbn) {
		if (isbn == null || isbn == 0)
			throw new IllegalArgumentException();
		
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
		
		if (length == 13) return ISBNType.ISBN13;
		if (length <= 10) return ISBNType.ISBN10;

		return ISBNType.ERROR;
	}
	
	public long getLong() {
		return isbn;
	}
	
	public String toStr() {
		String strIsbn =  Long.toString(isbn);
		
		if (type == ISBNType.ISBN10 && strIsbn.length() < 10)
			strIsbn = "0".repeat(10-strIsbn.length()).concat(strIsbn);
		
		return strIsbn;
	}
	
	public String getFormattedString() {
		//TODO check country prefix length
		//TODO check publisher prefix length
		String s = toStr();
		
		if (type == ISBNType.ISBN13) {
			s = s.substring(0, 3)  + "-" +
				s.substring(3, 5)  + "-" +
				s.substring(5, 9)  + "-" +
				s.substring(9, 12) + "-" +
				s.substring(12, 13);
		} else if (type == ISBNType.ISBN10) {
			s = s.substring(0, 2) + "-" +
				s.substring(2, 6) + "-" +
				s.substring(6, 9) + "-" +
				s.substring(9, 10);
		} 
		
		return s;	
	}
}
