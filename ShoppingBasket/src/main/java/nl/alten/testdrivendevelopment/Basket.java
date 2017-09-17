package nl.alten.testdrivendevelopment;

import java.util.ArrayList;
import java.util.List;

public class Basket {

	private static final double DEFAULT_SALES_TAX = 1.2;
	private List<Product> items = new ArrayList<Product>();
	private Promotion promotion;


	public void add(Product p) {
		if (p.getPrice() < 0.0) {
			throw new IllegalArgumentException();
		} else {
			items.add(p);
		}
	}

	public int size() {
		return items.size();
	}

	public double total() {
		double runningTotal=0.0;
		for (Product product : items) {
			if (promotion != null) {
				runningTotal += promotion.applyTo(product);
			} else {
				runningTotal += product.getPrice();
			}
		}
		return runningTotal;
	}

	public double totalWithSalesTax() {
		return total() * DEFAULT_SALES_TAX;
	}

	public double totalWithSalesTax(double salesTax) {
		return total() * salesTax;
	}

	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
	}

	public class Double {
		Double add(Promotion promo){
			return null;	
		}
	}
}
