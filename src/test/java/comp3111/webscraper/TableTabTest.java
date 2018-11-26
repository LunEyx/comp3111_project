package comp3111.webscraper;


import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import java.lang.reflect.*;
import java.util.Date;

import javafx.application.Application;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TableTabTest {
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
	public void testConstructor() {
		int tempSize = -1;
		
		TableView<Item> tv = new TableView<Item>();
		TableColumn<Item, String> tc1 = new TableColumn<Item, String>();
		TableColumn<Item, Double> tc2 = new TableColumn<Item, Double>();
		TableColumn<Item, Hyperlink> tc3 = new TableColumn<Item, Hyperlink>();
		TableColumn<Item, Date> tc4 = new TableColumn<Item, Date>();
		
		TableTab tt = new TableTab(tv, tc1, tc2, tc3, tc4);
		Class te = tt.getClass();
		TableView checkTv = null;
		TableColumn checkTc1 = null;
		TableColumn checkTc2 = null;
		TableColumn checkTc3 = null;
		TableColumn checkTc4 = null;
		
		try {
			Method method = te.getDeclaredMethod("refreshTable", (Class[])null);
			method.setAccessible(true);
			method.invoke(tt, (Object[])null);
			
			Field fe = te.getDeclaredField("tableMain");
			fe.setAccessible(true);
			checkTv = (TableView)fe.get(tt);
			
			fe = te.getDeclaredField("tableColTitle");
			fe.setAccessible(true);
			checkTc1 = (TableColumn)fe.get(tt);
			
			fe = te.getDeclaredField("tableColPrice");
			fe.setAccessible(true);
			checkTc2 = (TableColumn)fe.get(tt);
			
			fe = te.getDeclaredField("tableColUrl");
			fe.setAccessible(true);
			checkTc3 = (TableColumn)fe.get(tt);
			
			fe = te.getDeclaredField("tableColPostedDate");
			fe.setAccessible(true);
			checkTc4 = (TableColumn)fe.get(tt);
			
		}catch(NoSuchMethodException e) {
	    }catch(IllegalAccessException ee) {
	    }catch(InvocationTargetException ee2) {
	    }catch(NoSuchFieldException ee3) {
	    }
		
		assertEquals(checkTv, tv);
		assertEquals(checkTc1, tc1);
		assertEquals(checkTc2, tc2);
		assertEquals(checkTc3, tc3);
		assertEquals(checkTc4, tc4);
	}
	
	@Test
	public void testReset() {
		int tempSize = -1;
		TableTab tt = new TableTab(new TableView<Item>(), new TableColumn<Item, String>(), new TableColumn<Item, Double>(), new TableColumn<Item, Hyperlink>(), new TableColumn<Item, Date>());
		Class te = tt.getClass();
		try {
			Field fe = te.getDeclaredField("tableMain");
			fe.setAccessible(true);
			TableView tc = (TableView)fe.get(tt);
			tempSize = tc.getItems().size();
		}catch(NoSuchFieldException e) {
	    }catch(IllegalAccessException ee) {
	    }
		
		tt.reset();
		assertEquals(tempSize, 0);
	}
}
