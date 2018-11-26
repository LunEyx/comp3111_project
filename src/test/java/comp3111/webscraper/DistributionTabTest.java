package comp3111.webscraper;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import javafx.application.Application;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.TextArea;

public class DistributionTabTest {
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
	    TextArea textAreaConsole = new TextArea();
	    final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
	    BarChart<String, Number> barChartHistogram = new BarChart<>(xAxis, yAxis);
	    
	    DistributionTab dt = new DistributionTab(barChartHistogram, textAreaConsole);
	    dt.reset();
	    
	    Field field = dt.getClass().getDeclaredField("barChartHistogram");
	    field.setAccessible(true);
	    BarChart<String, Number> bc = (BarChart<String, Number>) field.get(dt);
	    assertEquals("No Result", bc.getTitle());
	}

	@Test
	public void testRefreshWithItemEmpty() throws Exception {
		TextArea textAreaConsole = new TextArea();
	    final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
	    BarChart<String, Number> barChartHistogram = new BarChart<>(xAxis, yAxis);
	    
	    DistributionTab dt = new DistributionTab(barChartHistogram, textAreaConsole);
	    List<Item> items = new ArrayList<>();
	    
	    dt.refresh("Hello", items);
	    
	    Field field = dt.getClass().getDeclaredField("barChartHistogram");
	    field.setAccessible(true);
	    BarChart<String, Number> bc = (BarChart<String, Number>) field.get(dt);
	    assertEquals("No Result", bc.getTitle());
	}

	@Test
	public void testRefreshWithFiveItems() throws Exception {
		TextArea textAreaConsole = new TextArea();
	    final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
	    BarChart<String, Number> barChartHistogram = new BarChart<>(xAxis, yAxis);
	    
	    DistributionTab dt = new DistributionTab(barChartHistogram, textAreaConsole);
	    List<Item> items = new ArrayList<>();
	    for (int i = 0; i < 5; i++) {
	    	Item item = new Item();
	    	item.setPrice(500.00);
	    	items.add(item);
	    }
	    
	    dt.refresh("Hello", items);
	    
	    Field field = dt.getClass().getDeclaredField("barChartHistogram");
	    field.setAccessible(true);
	    BarChart<String, Number> bc = (BarChart<String, Number>) field.get(dt);
	    assertEquals("The selling price of Hello", bc.getTitle());
	}
	
	@Test
	public void testRefreshWithManyItems() throws Exception {
		TextArea textAreaConsole = new TextArea();
	    final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
	    BarChart<String, Number> barChartHistogram = new BarChart<>(xAxis, yAxis);
	    
	    DistributionTab dt = new DistributionTab(barChartHistogram, textAreaConsole);
	    List<Item> items = new ArrayList<>();
	    for (int i = 0; i < 20; i++) {
	    	Item item = new Item();
	    	item.setPrice(500.00 * i);
	    	items.add(item);
	    }
	    
	    dt.refresh("Hello", items);
	    
	    Field field = dt.getClass().getDeclaredField("barChartHistogram");
	    field.setAccessible(true);
	    BarChart<String, Number> bc = (BarChart<String, Number>) field.get(dt);
	    assertEquals("The selling price of Hello", bc.getTitle());
	}
}
