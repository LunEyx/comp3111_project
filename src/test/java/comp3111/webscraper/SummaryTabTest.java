package comp3111.webscraper;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import javafx.application.Application;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;

public class SummaryTabTest {
	@BeforeClass
	public static void setUpClass() throws InterruptedException {
	    // Initialize Java FX

	    System.out.printf("About to launch FX App\n");
	    Thread t = new Thread("JavaFX Init Thread") {
	        public void run() {
	            Application.launch(WebScraperApplication.class, new String[0]);
	        }
	    };
	    t.setDaemon(true);
	    t.start();
	    System.out.printf("FX App thread started\n");
	    Thread.sleep(500);
	}

	@Test
	public void testReset() throws Exception {
		Label labelCount = new Label();
		Label labelPrice = new Label();
		Hyperlink labelMin = new Hyperlink();
		Hyperlink labelLatest = new Hyperlink();

		SummaryTab st = new SummaryTab(labelCount, labelPrice, labelMin, labelLatest);
		st.reset();

		Field field1 = st.getClass().getDeclaredField("labelCount");
	    field1.setAccessible(true);
	    Label lc = (Label) field1.get(st);
	    assertEquals("0", lc.getText());

	    Field field2 = st.getClass().getDeclaredField("labelPrice");
	    field2.setAccessible(true);
	    Label lp = (Label) field2.get(st);
	    assertEquals("-", lp.getText());

	    Field field3 = st.getClass().getDeclaredField("labelMin");
	    field3.setAccessible(true);
	    Hyperlink lm = (Hyperlink) field3.get(st);
	    assertEquals("-", lm.getText());

	    Field field4 = st.getClass().getDeclaredField("labelLatest");
	    field4.setAccessible(true);
	    Hyperlink ll = (Hyperlink) field4.get(st);
	    assertEquals("-", ll.getText());
	}

	@Test
	public void testRefreshNoItem() throws Exception {
		Label labelCount = new Label();
		Label labelPrice = new Label();
		Hyperlink labelMin = new Hyperlink();
		Hyperlink labelLatest = new Hyperlink();

		SummaryTab st = new SummaryTab(labelCount, labelPrice, labelMin, labelLatest);
		List<Item> items = new ArrayList<>();
		st.refresh(items);

		Field field1 = st.getClass().getDeclaredField("labelCount");
	    field1.setAccessible(true);
	    Label lc = (Label) field1.get(st);
	    assertEquals("0", lc.getText());

	    Field field2 = st.getClass().getDeclaredField("labelPrice");
	    field2.setAccessible(true);
	    Label lp = (Label) field2.get(st);
	    assertEquals("-", lp.getText());

	    Field field3 = st.getClass().getDeclaredField("labelMin");
	    field3.setAccessible(true);
	    Hyperlink lm = (Hyperlink) field3.get(st);
	    assertEquals("-", lm.getText());

	    Field field4 = st.getClass().getDeclaredField("labelLatest");
	    field4.setAccessible(true);
	    Hyperlink ll = (Hyperlink) field4.get(st);
	    assertEquals("-", ll.getText());
	}

	@Test
	public void testRefreshSomeItem1() throws Exception {
		Label labelCount = new Label();
		Label labelPrice = new Label();
		Hyperlink labelMin = new Hyperlink();
		Hyperlink labelLatest = new Hyperlink();

		SummaryTab st = new SummaryTab(labelCount, labelPrice, labelMin, labelLatest);
		List<Item> items = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			Item item = new Item();
			item.setPrice(63500);
			item.setPostedDate("2018 - 01 - 01");
			item.setHyperlink("http://www.google.com");
			items.add(item);
		}
		st.refresh(items);

		Field field1 = st.getClass().getDeclaredField("labelCount");
	    field1.setAccessible(true);
	    Label lc = (Label) field1.get(st);
	    assertEquals("100", lc.getText());

	    Field field2 = st.getClass().getDeclaredField("labelPrice");
	    field2.setAccessible(true);
	    Label lp = (Label) field2.get(st);
	    assertEquals("63500.0", lp.getText());
	}

	@Test
	public void testRefreshSomeItem2() throws Exception {
		Label labelCount = new Label();
		Label labelPrice = new Label();
		Hyperlink labelMin = new Hyperlink();
		Hyperlink labelLatest = new Hyperlink();

		SummaryTab st = new SummaryTab(labelCount, labelPrice, labelMin, labelLatest);
		List<Item> items = new ArrayList<>();

		Item item;
		item = new Item();
		item.setPrice(100);
		item.setPostedDate("2018 - 01 - 01");
		item.setHyperlink("http://www.google.com");
		items.add(item);

		item = new Item();
		item.setPrice(50);
		item.setPostedDate("2018 - 01 - 02");
		item.setHyperlink("http://www.google.com");
		items.add(item);

		item = new Item();
		item.setPrice(0);
		item.setPostedDate("2018 - 01 - 02");
		item.setHyperlink("http://www.google.com");
		items.add(item);

		st.refresh(items);

		Field field1 = st.getClass().getDeclaredField("labelCount");
	    field1.setAccessible(true);
	    Label lc = (Label) field1.get(st);
	    assertEquals("2", lc.getText());
	}
}
