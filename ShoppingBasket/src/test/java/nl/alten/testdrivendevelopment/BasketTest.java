package nl.alten.testdrivendevelopment;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import java.math.BigDecimal;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.isA;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BasketTest {

	Basket basket;

	@Before
	public void SetUp() {
		basket = new Basket();
	}

	@Test
	public void itemCanBeAddedToTheBasket() {

		basket.add(new Product(10.00));
		basket.add(new Product(11.00));
		Assert.assertEquals(2, basket.size());
	}

	@Test
	public void basketCanCalculateTheTotalCostOfBasket() throws Exception {

		basket.add(new Product(19.45));
		basket.add(new Product(15.45));
		Assert.assertEquals(34.90, basket.total(), 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void basketDoesNotAcceptProductsWithANegativePrice() {

		basket.add(new Product(-10.00));
		Assert.assertEquals(34.90, basket.total(), 0);
	}

	@Test
	public void basketDoesAcceptProductWithZero() {

		basket.add(new Product(0.00));
		Assert.assertEquals(0.00, basket.total(), 0);
		Assert.assertEquals(1, basket.size());
	}

	@Test
	public void basketAddSalesTaxToTotal() throws Exception {
		basket.add(new Product(20.00));
		basket.add(new Product(20.00));
		Assert.assertEquals(48.00, basket.totalWithSalesTax(), 0);
	}

	@Test
	public void basketCanUseAnArbitrarySalesTaxToCalculateValue() {
		basket.add(product(20.00));
		basket.add(product(20.00));
		compare(44.00, basket.totalWithSalesTax(1.1)); // Exercise 5
	}

	@Test
	public void differentTypesOfProductCanBeAddedToTheBasket() {
		basket.add(new Book(20.00));
		basket.add(new Video(20.00));
		Assert.assertEquals(40.00, basket.total(), 0);
	}


	@Test
	public void whenAPromotionIsAddedToTheBasketThenItCanAffectThePrice() {
		basket.add(new Book(20.00));
		basket.add(new Video(20.00));
	
		basket.setPromotion(new Promotion() {
			public double applyTo(Product product) {
				if (product instanceof Video) {
					return 19.00;
				}
				return product.getPrice();
			}
		});
		compare(39.00, basket.total());
	}

	
	@Test
	public void whenAPromotionIsAddedToTheBasketThenItCanAffectThePriceWithAMock() {
		basket.add(new Book(20.00));
		basket.add(new Video(20.00));
		Promotion mockPromotion = mock(Promotion.class);
		when(mockPromotion.applyTo(isA(Video.class))).thenReturn(19.00);
		when(mockPromotion.applyTo(isA(Book.class))).thenReturn(20.00);
		basket.setPromotion(mockPromotion);
//		basket.setPromotion(new Promotion() {
//			public double applyTo(Product product) {
//				if (product instanceof Video) {
//					return 19.00;
//				}
//				return product.getPrice();
//			}
//		});
		compare(39.00, basket.total());
	}

	@After
	public void tearDown() {
		basket = null;
	}

	private Product product(double d) {
		return new Product(d);
	}

	public void compare(double expected, double actual) {
		Assert.assertEquals(expected, actual, 0);
	}

}
