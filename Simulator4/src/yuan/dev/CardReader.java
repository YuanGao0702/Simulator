package yuan.dev;

public class CardReader {
	public static int CardReaderId;
	public String value;
	
	public static int getCardReaderId() {
		return CardReaderId;
	}
	public static void setCardReaderId(int cardReaderId) {
		CardReaderId = cardReaderId;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

}
