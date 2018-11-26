package comp3111.webscraper;


import org.junit.Test;
import javafx.application.Application;
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.text.ParseException;
import org.junit.BeforeClass;

public class SaveLoadTest {
	
	@Test
	public void testSave() {
		Controller con = new Controller();
		List<Item> testerList = new ArrayList<Item>();
		
		Item testerItem;
		
		try {
			testerItem = new Item();
			testerItem.setTitle("Test Item 1");
			testerItem.setPrice(100.0);
			testerItem.setUrl("http://testurl.1.com");
			testerItem.setPostedDate("1996 - 12 - 13");
			testerList.add(testerItem);
			
			testerItem = new Item();
			testerItem.setTitle("Test Item 2");
			testerItem.setPrice(200.0);
			testerItem.setUrl("http://testurl.2.com");
			testerItem.setPostedDate("2018 - 12 - 13");
			testerList.add(testerItem);
			
			File tempFile = File.createTempFile("temporalSave", ".txt");
			Class cc = con.getClass();
			Method method = cc.getDeclaredMethod("fileWritter", File.class, String.class, List.class);
			method.setAccessible(true);
			method.invoke(con, tempFile, "Test Case", testerList);
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(
			          new FileInputStream(tempFile)));
			assertEquals(reader.readLine(), "Test Case");
			reader.readLine();
			assertEquals(reader.readLine(), "Test Item 1");
			assertEquals(reader.readLine(), "100.0");
			assertEquals(reader.readLine(), "http://testurl.1.com");
			assertEquals(reader.readLine(), "1996 - 12 - 13");
			reader.readLine();
			assertEquals(reader.readLine(), "Test Item 2");
			assertEquals(reader.readLine(), "200.0");
			assertEquals(reader.readLine(), "http://testurl.2.com");
			assertEquals(reader.readLine(), "2018 - 12 - 13");
			reader.close();
		}catch(IOException e1) {
		}catch(NoSuchMethodException e2) {
		}catch(IllegalAccessException e3) {
		}catch(InvocationTargetException e4) {
	    }catch(ParseException e5) {
	    }
	}
	
	@Test
	public void testLoad1() {
		Controller con = new Controller();
		
		try {
			Class cc = con.getClass();
			Method method = cc.getDeclaredMethod("fileReader", File.class);
			method.setAccessible(true);
			
			File tempFile = File.createTempFile("temporalLoad", ".txt");
		    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
		          new FileOutputStream(tempFile)));
		    
		    writer.write("Test Case");
		    writer.newLine();
		    writer.newLine();
		    
		    Item testerItem1, testerItem2;
		    
		    testerItem1 = new Item();
			testerItem1.setTitle("Test Item 1");
			testerItem1.setPrice(100.0);
			testerItem1.setUrl("http://testurl.1.com");
			testerItem1.setPostedDate("1996 - 12 - 13");
			
			writer.write(testerItem1.getTitle());
		    writer.newLine();
		    writer.write(Double.toString(testerItem1.getPrice()));
		    writer.newLine();
	    	writer.write(testerItem1.getUrl());
	    	writer.newLine();
	    	writer.write(testerItem1.getPostedDate());
	    	writer.newLine();
	    	writer.newLine();
	    	
			testerItem2 = new Item();
			testerItem2.setTitle("Test Item 2");
			testerItem2.setPrice(200.0);
			testerItem2.setUrl("http://testurl.2.com");
			testerItem2.setPostedDate("2018 - 12 - 13");
			
			writer.write(testerItem2.getTitle());
		    writer.newLine();
		    writer.write(Double.toString(testerItem2.getPrice()));
		    writer.newLine();
	    	writer.write(testerItem2.getUrl());
	    	writer.newLine();
	    	writer.write(testerItem2.getPostedDate());
	    	writer.newLine();
	    	writer.newLine();
		    
		    writer.close();
		    
		    Object o = method.invoke(con, tempFile);
		    List<Item> tempList = (List<Item>) o;
		    assertEquals(tempList.get(0).getTitle(), "Test Case");
		    assertEquals(tempList.get(1).getTitle(), testerItem1.getTitle());
		    assertEquals(tempList.get(1).getPrice(), testerItem1.getPrice(), 0.001);
		    assertEquals(tempList.get(1).getUrl(), testerItem1.getUrl());
		    assertEquals(tempList.get(1).getPostedDate(), testerItem1.getPostedDate());
		    assertEquals(tempList.get(2).getTitle(), testerItem2.getTitle());
		    assertEquals(tempList.get(2).getPrice(), testerItem2.getPrice(), 0.001);
		    assertEquals(tempList.get(2).getUrl(), testerItem2.getUrl());
		    assertEquals(tempList.get(2).getPostedDate(), testerItem2.getPostedDate());
		}catch(IOException e1) {
		}catch(NoSuchMethodException e2) {
		}catch(IllegalAccessException e3) {
		}catch(InvocationTargetException e4) {
	    }catch(ParseException e5) {
	    }
	}
	
	@Test
	public void testLoad2() {
		Controller con = new Controller();
		
		try {
			Class cc = con.getClass();
			Method method = cc.getDeclaredMethod("fileReader", File.class);
			method.setAccessible(true);
			
			File tempFile = File.createTempFile("temporalLoad", ".txt");
		    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
		          new FileOutputStream(tempFile)));
		    
		    writer.write("Test Case");
		    writer.newLine();
		    writer.newLine();
		    
		    Item testerItem1, testerItem2;
		    
		    testerItem1 = new Item();
			testerItem1.setTitle("Test Item 1");
			testerItem1.setPrice(100.0);
			testerItem1.setUrl("http://testurl.1.com");
			testerItem1.setPostedDate("1996 - 12 - 13");
			
			writer.write(testerItem1.getTitle());
		    writer.newLine();
		    writer.write(Double.toString(testerItem1.getPrice()));
		    writer.newLine();
	    	writer.write(testerItem1.getUrl());
	    	writer.newLine();
	    	writer.write(testerItem1.getPostedDate());
	    	writer.newLine();
	    	writer.newLine();
	    	
			testerItem2 = new Item();
			testerItem2.setTitle("Test Item 2");
			testerItem2.setPrice(200.0);
			testerItem2.setUrl("http://testurl.2.com");
			
			writer.write(testerItem2.getTitle());
		    writer.newLine();
		    writer.write(Double.toString(testerItem2.getPrice()));
		    writer.newLine();
	    	writer.write(testerItem2.getUrl());
		    
		    writer.close();
		    
		    Object o = method.invoke(con, tempFile);
		    List<Item> tempList = (List<Item>) o;
		    assertEquals(tempList.size(), 0);
		}catch(IOException e1) {
		}catch(NoSuchMethodException e2) {
		}catch(IllegalAccessException e3) {
		}catch(InvocationTargetException e4) {
	    }catch(ParseException e5) {
	    }
	}
	
	@Test
	public void testLoad3() {
		Controller con = new Controller();
		
		try {
			Class cc = con.getClass();
			Method method = cc.getDeclaredMethod("fileReader", File.class);
			method.setAccessible(true);
			
			File tempFile = File.createTempFile("temporalLoad", ".txt");
		    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
		          new FileOutputStream(tempFile)));
		    
		    writer.write("Test Case");
		    writer.newLine();
		    writer.newLine();
		    
		    Item testerItem1, testerItem2;
		    
		    testerItem1 = new Item();
			testerItem1.setTitle("Test Item 1");
			testerItem1.setPrice(100.0);
			testerItem1.setUrl("http://testurl.1.com");
			testerItem1.setPostedDate("1996 - 12 - 13");
			
			writer.write(testerItem1.getTitle());
		    writer.newLine();
		    writer.write(Double.toString(testerItem1.getPrice()));
		    writer.newLine();
	    	writer.write(testerItem1.getUrl());
	    	writer.newLine();
	    	writer.write(testerItem1.getPostedDate());
	    	writer.newLine();
	    	writer.newLine();
	    	
			testerItem2 = new Item();
			testerItem2.setTitle("Test Item 2");
			testerItem2.setPrice(200.0);
			
			writer.write(testerItem2.getTitle());
		    writer.newLine();
		    writer.write(Double.toString(testerItem2.getPrice()));
		    
		    writer.close();
		    
		    Object o = method.invoke(con, tempFile);
		    List<Item> tempList = (List<Item>) o;
		    assertEquals(tempList.size(), 0);
		}catch(IOException e1) {
		}catch(NoSuchMethodException e2) {
		}catch(IllegalAccessException e3) {
		}catch(InvocationTargetException e4) {
	    }catch(ParseException e5) {
	    }
	}
}