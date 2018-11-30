package comp3111.webscraper;


import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

import java.io.File;
import java.lang.reflect.*;
import java.util.Date;
import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Label;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.util.ArrayList;
import java.text.ParseException;
import javafx.scene.control.MenuItem;

public class ControllerTest {
	@BeforeClass
	public static void setUpClass() throws InterruptedException {
	    // Initialise Java FX
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
	public void testActionClose() {
		Controller con = new Controller();
		Class conClass = con.getClass();
		TextArea ta = new TextArea();
		TextField tf = new TextField();
		
		try {
			Field fe = conClass.getDeclaredField("textAreaConsole");
			fe.setAccessible(true);
			fe.set(con, new TextArea());
			ta = (TextArea)fe.get(con);
					
			fe = conClass.getDeclaredField("textFieldKeyword");
			fe.setAccessible(true);
			fe.set(con,new TextField ());
			tf = (TextField)fe.get(con);
			
			fe = conClass.getDeclaredField("summaryTab");
			fe.setAccessible(true);
			fe.set(con,new SummaryTab(new Label(), new Label(), new Hyperlink(), new Hyperlink()));
			
			fe = conClass.getDeclaredField("tableTab");
			fe.setAccessible(true);
			fe.set(con,new TableTab(new TableView<Item>(), new TableColumn<Item, String>(), new TableColumn<Item, Double>(), new TableColumn<Item, Hyperlink>(), new TableColumn<Item, Date>()));
			
			fe = conClass.getDeclaredField("distributionTab");
			fe.setAccessible(true);
			fe.set(con,new DistributionTab(new BarChart<String, Number>(new CategoryAxis(), new NumberAxis()), new TextArea()));
			
			Method method = conClass.getDeclaredMethod("actionClose");
			method.setAccessible(true);
			method.invoke(con);
			
			assertEquals(ta.getText().length(), 0);
			assertEquals(tf.getText().length(), 0);
		}catch(NoSuchMethodException e) {
	    }catch(IllegalAccessException ee) {
	    }catch(InvocationTargetException ee2) {
	    }catch(NoSuchFieldException ee3) {
	    }
	}
	
	@Test
	public void testRefreshAllTabs() {
		Controller con = new Controller();
		Class conClass = con.getClass();
		TextArea ta = new TextArea();
		TextField tf = new TextField();
		
		try {
			Item item = new Item();
			item.setPrice(50);
			item.setPostedDate("2018 - 01 - 02");
			item.setHyperlink("http://www.google.com");
			List<Item> items = new ArrayList<>();
			items.add(item);
			
			Field fe = conClass.getDeclaredField("textAreaConsole");
			fe.setAccessible(true);
			fe.set(con, new TextArea());
			ta = (TextArea)fe.get(con);
			
			fe = conClass.getDeclaredField("textFieldKeyword");
			fe.setAccessible(true);
			fe.set(con,new TextField ());
			tf = (TextField)fe.get(con);
			
			fe = conClass.getDeclaredField("summaryTab");
			fe.setAccessible(true);
			fe.set(con,new SummaryTab(new Label(), new Label(), new Hyperlink(), new Hyperlink()));
			
			fe = conClass.getDeclaredField("tableTab");
			fe.setAccessible(true);
			fe.set(con,new TableTab(new TableView<Item>(), new TableColumn<Item, String>(), new TableColumn<Item, Double>(), new TableColumn<Item, Hyperlink>(), new TableColumn<Item, Date>()));
			
			fe = conClass.getDeclaredField("distributionTab");
			fe.setAccessible(true);
			fe.set(con,new DistributionTab(new BarChart<String, Number>(new CategoryAxis(), new NumberAxis()), new TextArea()));
			
			Method method = conClass.getDeclaredMethod("refreshAllTabs", String.class, List.class);
			method.setAccessible(true);
			method.invoke(con, "Tester Keyword", items);
			
			assertEquals(tf.getText(), "Tester Keyword");
			assertEquals(ta.getText(), item.getTitle() + "\t" + item.getPrice() + "\t" + item.getUrl() + "\n");
		}catch(NoSuchMethodException e) {
	    }catch(IllegalAccessException ee) {
	    }catch(InvocationTargetException ee2) {
	    }catch(NoSuchFieldException ee3) {
	    }catch(ParseException ee4) {
	    }
	}

	@Test
	public void testActionNew() {
		Controller con = new Controller();
		Class conClass = con.getClass();
		TextArea ta = new TextArea();
		TextField tf = new TextField();
		
		try {
			Field fe = conClass.getDeclaredField("lastSearchMenuItem");
			fe.setAccessible(true);
			fe.set(con, new MenuItem());
			
			fe = conClass.getDeclaredField("lastSearchKeyword");
			fe.setAccessible(true);
			fe.set(con,"Tester Keyword");
			
			fe = conClass.getDeclaredField("lastSearchResult");
			fe.setAccessible(true);
			fe.set(con, new ArrayList<>());
			
			fe = conClass.getDeclaredField("textAreaConsole");
			fe.setAccessible(true);
			fe.set(con, new TextArea());
			ta = (TextArea)fe.get(con);
			
			fe = conClass.getDeclaredField("textFieldKeyword");
			fe.setAccessible(true);
			fe.set(con,new TextField ());
			tf = (TextField)fe.get(con);
			
			fe = conClass.getDeclaredField("summaryTab");
			fe.setAccessible(true);
			fe.set(con,new SummaryTab(new Label(), new Label(), new Hyperlink(), new Hyperlink()));
			
			fe = conClass.getDeclaredField("tableTab");
			fe.setAccessible(true);
			fe.set(con,new TableTab(new TableView<Item>(), new TableColumn<Item, String>(), new TableColumn<Item, Double>(), new TableColumn<Item, Hyperlink>(), new TableColumn<Item, Date>()));
			
			fe = conClass.getDeclaredField("distributionTab");
			fe.setAccessible(true);
			fe.set(con,new DistributionTab(new BarChart<String, Number>(new CategoryAxis(), new NumberAxis()), new TextArea()));
			
			Method method = conClass.getDeclaredMethod("actionNew");
			method.setAccessible(true);
			method.invoke(con);
			
			assertEquals(tf.getText(), "Tester Keyword");
			assertEquals(ta.getText().length(), 0);
		}catch(NoSuchMethodException e) {
	    }catch(IllegalAccessException ee) {
	    }catch(InvocationTargetException ee2) {
	    }catch(NoSuchFieldException ee3) {
	    }
	}
	
	@Test
	public void testActionSearchWithNoResult() {
		Controller con = new Controller();
		Class conClass = con.getClass();
		TextField tf = new TextField();
		TableView tv = new TableView<Item>();
		
		try {
			Field fe;
			
			fe = conClass.getDeclaredField("textFieldKeyword");
			fe.setAccessible(true);
			fe.set(con,new TextField ());
			tf = (TextField)fe.get(con);
			tf.setText("Tester Keyword");
			
			fe = conClass.getDeclaredField("textAreaConsole");
			fe.setAccessible(true);
			fe.set(con, new TextArea());
			
			fe = conClass.getDeclaredField("lastSearchMenuItem");
			fe.setAccessible(true);
			fe.set(con, new MenuItem());
			
			fe = conClass.getDeclaredField("lastSearchKeyword");
			fe.setAccessible(true);
			fe.set(con,"Tester Keyword");
			
			fe = conClass.getDeclaredField("lastSearchResult");
			fe.setAccessible(true);
			fe.set(con, new ArrayList<>());
			
			fe = conClass.getDeclaredField("currentSearchKeyword");
			fe.setAccessible(true);
			fe.set(con,"Tester Keyword2");
			
			fe = conClass.getDeclaredField("currentSearchResult");
			fe.setAccessible(true);
			fe.set(con, null);
			
			fe = conClass.getDeclaredField("summaryTab");
			fe.setAccessible(true);
			fe.set(con,new SummaryTab(new Label(), new Label(), new Hyperlink(), new Hyperlink()));
			
			fe = conClass.getDeclaredField("tableTab");
			fe.setAccessible(true);
			fe.set(con, new TableTab(tv, new TableColumn<Item, String>(), new TableColumn<Item, Double>(), new TableColumn<Item, Hyperlink>(), new TableColumn<Item, Date>()));
			
			fe = conClass.getDeclaredField("distributionTab");
			fe.setAccessible(true);
			fe.set(con,new DistributionTab(new BarChart<String, Number>(new CategoryAxis(), new NumberAxis()), new TextArea()));
			
			Method method = conClass.getDeclaredMethod("actionSearch");
			method.setAccessible(true);
			method.invoke(con);
			
			String temp = (String)conClass.getDeclaredField("currentSearchKeyword").get(con);
			
			assertEquals(tf.getText(), "Tester Keyword");
			assertEquals(temp, "Tester Keyword");
			assertEquals(tv.getItems().size(), 0);
		}catch(NoSuchMethodException e) {
	    }catch(IllegalAccessException ee) {
	    }catch(InvocationTargetException ee2) {
	    }catch(NoSuchFieldException ee3) {
	    }
	}
	
	@Test
	public void testActionSearchResultMoreThanOnePage() {
		Controller con = new Controller();
		Class conClass = con.getClass();
		TextField tf = new TextField();
		TableView tv = new TableView<Item>();
		
		try {
			Field fe;
			
			fe = conClass.getDeclaredField("textFieldKeyword");
			fe.setAccessible(true);
			fe.set(con,new TextField ());
			tf = (TextField)fe.get(con);
			tf.setText("negative");
			
			fe = conClass.getDeclaredField("textAreaConsole");
			fe.setAccessible(true);
			fe.set(con, new TextArea());
			
			fe = conClass.getDeclaredField("lastSearchMenuItem");
			fe.setAccessible(true);
			fe.set(con, new MenuItem());
			
			fe = conClass.getDeclaredField("lastSearchKeyword");
			fe.setAccessible(true);
			fe.set(con,"Tester Keyword");
			
			fe = conClass.getDeclaredField("lastSearchResult");
			fe.setAccessible(true);
			fe.set(con, new ArrayList<>());
			
			fe = conClass.getDeclaredField("currentSearchKeyword");
			fe.setAccessible(true);
			fe.set(con,"Tester Keyword2");
			
			fe = conClass.getDeclaredField("currentSearchResult");
			fe.setAccessible(true);
			fe.set(con, new ArrayList<Item>());
			
			fe = conClass.getDeclaredField("summaryTab");
			fe.setAccessible(true);
			fe.set(con,new SummaryTab(new Label(), new Label(), new Hyperlink(), new Hyperlink()));
			
			fe = conClass.getDeclaredField("tableTab");
			fe.setAccessible(true);
			fe.set(con, new TableTab(tv, new TableColumn<Item, String>(), new TableColumn<Item, Double>(), new TableColumn<Item, Hyperlink>(), new TableColumn<Item, Date>()));

			
			fe = conClass.getDeclaredField("distributionTab");
			fe.setAccessible(true);
			fe.set(con,new DistributionTab(new BarChart<String, Number>(new CategoryAxis(), new NumberAxis()), new TextArea()));
			
			Method method = conClass.getDeclaredMethod("actionSearch");
			method.setAccessible(true);
			method.invoke(con);
			
			String temp = (String)conClass.getDeclaredField("currentSearchKeyword").get(con);
			
			assertEquals(tf.getText(), "negative");
			assertEquals(temp, "negative");
			assertTrue(tv.getItems().size() >= 120);
		}catch(NoSuchMethodException e) {
	    }catch(IllegalAccessException ee) {
	    }catch(InvocationTargetException ee2) {
	    }catch(NoSuchFieldException ee3) {
	    }
	}
	
	@Test
	public void testActionSearchResultWithinOnePage() {
		Controller con = new Controller();
		Class conClass = con.getClass();
		TextField tf = new TextField();
		TableView tv = new TableView<Item>();
		
		try {
			Field fe;
			
			fe = conClass.getDeclaredField("textFieldKeyword");
			fe.setAccessible(true);
			fe.set(con,new TextField ());
			tf = (TextField)fe.get(con);
			tf.setText("GTX 1080");
			
			fe = conClass.getDeclaredField("textAreaConsole");
			fe.setAccessible(true);
			fe.set(con, new TextArea());
			
			fe = conClass.getDeclaredField("lastSearchMenuItem");
			fe.setAccessible(true);
			fe.set(con, new MenuItem());
			
			fe = conClass.getDeclaredField("lastSearchKeyword");
			fe.setAccessible(true);
			fe.set(con,"Tester Keyword");
			
			fe = conClass.getDeclaredField("lastSearchResult");
			fe.setAccessible(true);
			fe.set(con, new ArrayList<>());
			
			fe = conClass.getDeclaredField("currentSearchKeyword");
			fe.setAccessible(true);
			fe.set(con, null);
			
			fe = conClass.getDeclaredField("currentSearchResult");
			fe.setAccessible(true);
			fe.set(con, null);
			
			fe = conClass.getDeclaredField("summaryTab");
			fe.setAccessible(true);
			fe.set(con,new SummaryTab(new Label(), new Label(), new Hyperlink(), new Hyperlink()));
			
			fe = conClass.getDeclaredField("tableTab");
			fe.setAccessible(true);
			fe.set(con,new TableTab(tv, new TableColumn<Item, String>(), new TableColumn<Item, Double>(), new TableColumn<Item, Hyperlink>(), new TableColumn<Item, Date>()));
			
			fe = conClass.getDeclaredField("distributionTab");
			fe.setAccessible(true);
			fe.set(con,new DistributionTab(new BarChart<String, Number>(new CategoryAxis(), new NumberAxis()), new TextArea()));
			
			Method method = conClass.getDeclaredMethod("actionSearch");
			method.setAccessible(true);
			method.invoke(con);
			
			String temp = (String)conClass.getDeclaredField("currentSearchKeyword").get(con);
			
			assertEquals(tf.getText(), "GTX 1080");
			assertEquals(temp, "GTX 1080");
			assertTrue(tv.getItems().size() > 0 && tv.getItems().size() < 120);
		}catch(NoSuchMethodException e) {
	    }catch(IllegalAccessException ee) {
	    }catch(InvocationTargetException ee2) {
	    }catch(NoSuchFieldException ee3) {
	    }
	}
	
	@Test
	public void testActionQuit() throws Exception {
		Controller con = new Controller();
		Method method = con.getClass().getDeclaredMethod("actionQuit");
		method.setAccessible(true);
		method.invoke(con);
		
		assertTrue(Platform.isImplicitExit());
	}
}