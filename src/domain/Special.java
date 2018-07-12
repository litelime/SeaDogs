package domain;

public class Special {
	String item_ID;
	int discountPercentage;
	
	public Special(String item_ID, int discoutPercentage) {
		super();
		this.item_ID = item_ID;
		this.discountPercentage = discoutPercentage;
	}
	
	public String getItem_ID() {
		return item_ID;
	}
	public void setItem_ID(String item_ID) {
		this.item_ID = item_ID;
	}
	public int getDiscountPercentage() {
		return discountPercentage;
	}
	public void setDiscountPercentage(int discountPercentage) {
		this.discountPercentage = discountPercentage;
	}
	
	
}
