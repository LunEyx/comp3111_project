package comp3111.webscraper;


import org.junit.Test;

import static org.junit.Assert.*;
import java.util.Date;
import java.text.ParseException;

public class ItemTest {

	@Test
	public void testSetTitle() {
		Item i = new Item();
		String tempTitle = "ABCDE";
		i.setTitle(tempTitle);
		assertEquals(i.getTitle(), tempTitle);
	}
	
	@Test
	public void testSetPrice() {
		Item i = new Item();
		Double tempPrice = 37.462d;
		i.setPrice(tempPrice);
		assertEquals(i.getPrice(), tempPrice, 0.001);
	}
	
	@Test
	public void testSetURL() {
		Item i = new Item();
		String tempUrl = "https://www.youtube.com/?gl=HK";
		i.setUrl(tempUrl);
		assertEquals(i.getUrl(), tempUrl);
	}
	
	@Test
	public void testSetPostedDate1() {
		Item i = new Item();
		String tempDate = "1996 - 12 - 13";
		try {
			i.setPostedDate(tempDate);
		}catch(ParseException e){
			fail();
		}
		
		assertEquals(i.getPostedDate(), tempDate);
	}
	
	@Test
	public void testSetPostedDate2() {
		Item i = new Item();
		Date tempDate = new Date(1996, 12, 13);
		i.setPostedDate(tempDate);
		assertTrue(i.getDate().equals(tempDate));
	}
	
	@Test
	public void testNextPageItem() {
		Item i = new Item();
		String tempUrl = "https://www.youtube.com/?gl=HK";
		i.nextPageItem(tempUrl);
		
		assertEquals(i.getTitle(), "Next Page");
		assertEquals(i.getPrice(), 0.0, 0.001);
		assertEquals(i.getUrl(), tempUrl);
		assertEquals(i.getPostedDate(), "1990 - 01 - 01");
		assertNull(i.getHyperlink());
	}
	
	@Test
	public void testTestNextPage1() {
		Item i = new Item();
		try {
			i.setTitle("Next Page");
			i.setPrice(0.0);
			i.setPostedDate("1990 - 01 - 01");
			assertTrue(i.testNextPage());
		}catch (ParseException e){
			//this exception is not related to this test case, actually can do nothing
			//but here would still indicate fail otherwise
			fail();
		}
	}
	
	@Test
	public void testTestNextPage2() {
		Item i = new Item();
		try {
			i.setTitle("Fail Case Tester");
			i.setPrice(0.0);
			i.setPostedDate("1990 - 01 - 01");
			assertFalse(i.testNextPage());
		}catch (ParseException e){
			//same as testTestNextPage1()
			fail();
		}
	}
	
	@Test
	public void testTestNextPage3() {
		Item i = new Item();
		try {
			i.setTitle("Next Page");
			i.setPrice(1.0);
			i.setPostedDate("1990 - 01 - 01");
			assertFalse(i.testNextPage());
		}catch (ParseException e){
			//same as testTestNextPage1()
			fail();
		}
	}
	
	@Test
	public void testTestNextPage4() {
		Item i = new Item();
		try {
			i.setTitle("Next Page");
			i.setPrice(0.0);
			i.setPostedDate("2018 - 01 - 01");
			assertFalse(i.testNextPage());
		}catch (ParseException e){
			//same as testTestNextPage1()
			fail();
		}
	}
}
