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
}
