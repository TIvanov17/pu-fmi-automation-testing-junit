package currency;

public class Currency {

	private String code;
	private String symbol;

	public Currency(String code, String symbol) {
		this.code = code;
		this.symbol = symbol;
	}
	
	public Currency() {
		
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
}
