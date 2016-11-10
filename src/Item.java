public class Item {
	private int number;
	private String word;
	private String symbol;
	private String explain;
	private int mistake; 

	public Item(){
		setNumber(0);
		setWord(null);
		setSymbol(null);
		setExplain(null);
		setMistake(0);
	}
	
	public Object clone() throws CloneNotSupportedException{
		Object O=super.clone();
		return O;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	public int getMistake() {
		return mistake;
	}

	public void setMistake(int mistake) {
		this.mistake = mistake;
	}
}//µ•¥ ¿‡
