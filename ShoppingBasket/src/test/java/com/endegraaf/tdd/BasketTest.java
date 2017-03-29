package com.endegraaf.tdd;

import org.junit.Test;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

public class BasketTest {

	private Basket basket;
	
	@Test
	public void itemCanBeAddedToTheBasket() throws Exception{
	    
	    basket.add(new Product(10.00)); 
	    basket.add(new Product(10.00)); 
	    Assert.assertEquals(2, basket.size());
	}
	@Test
	public void basketCanCalculateTheTotalCostOfBasket() throws Exception {
		
		basket.add(new Product(19.45));
		basket.add(new Product(15.45));
		Assert.assertEquals(34.90, basket.total(), 0);
	}
	@Test(expected = IllegalArgumentException.class)
	public void basketDoesNotAcceptProductsWithANegativePrice(){
	    
	    basket.add(new Product(-10.00));
	    Assert.assertEquals(34.90, basket.total(), 0);
	}	
	@Test
	public void basketDoesAcceptProductWithZero(){
	    
	    basket.add(product(0.00));
	    Assert.assertEquals(0.00, basket.total(), 0);
	}
	
	@Test
	public void basketAddSalesTaxToTotal() throws Exception {
		basket.add(product(20.00));
		basket.add(product(20.00));
		Assert.assertEquals(48.00, basket.totalWithSalesTax(), 0);
	}
	
	@Test
	public void differentTypesOfProductCanBeAddedToTheBasket() {
		basket.add(new Book(20.00));
		basket.add(new Video(20.00));
		Assert.assertEquals(40.00, basket.total(), 0);
	}
	
	@Test
	public void whenAPromotionIsAddedToTheBasketItCanAffectThePrice() {
		basket.add(new Book(20.00));
		basket.add(new Video(20.00));
		
		basket.setPromotion(new Promotion(){

			public double applyTo(Product product) {
				if (product instanceof Video){
					return 19.00;
				}
				return product.getPrice();
			}
			
		});
		Assert.assertEquals(39.00, basket.total(), 0);
	}
	
	
	
	private Product product(double d) {
		return new Product(d);
	}
	
	@Before
	public void setUp() {
		basket = new Basket();
	}
	@After
	public void tearDown() {
		basket=null;
	}
}

