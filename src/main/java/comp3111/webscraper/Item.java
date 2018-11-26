package comp3111.webscraper;

import java.awt.Desktop;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Hyperlink;

/**
 * Item class for creating and regulating objects for storing searched data.
 *
 */
public class Item {
	private String title; 
	private double price;
	private String url;
	private Date postedDate;
	private Hyperlink hyperlink;
	
	public void nextPageItem(String url) {
		this.setTitle("Next Page");
		this.setPrice(0.0);
		this.setUrl(url);
		this.setPostedDate("Jan 01, 1990");
		this.setHyperlink(url);
	}
	public Boolean testNextPage() {
		if(this.getTitle().equals("Next Page") && 
				this.getPrice() == 0.0 &&
				this.getPostedDate().equals("Jan 01, 1990"))
			return true;
		else
			return false;
	}
	/**
     * Getter: return the title of Item object.
     * 
     * @return String - Title of Item object
     */
	public String getTitle() {
		return title;
	}
	
	/**
     * Setter: set the title data member of Item object.
     * 
     * @param title The title of Item object searched
     */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
     * Getter: return the price of Item object.
     * 
     * @return Double - Price of Item object
     */
	public double getPrice() {
		return price;
	}
	
	/**
     * Setter: set the price data member of Item object.
     * 
     * @param price The price of Item object searched
     */
	public void setPrice(double price) {
		this.price = price;
	}
	
	/**
     * Getter: return the URL of Item object.
     * 
     * @return String - URL of Item object
     */
	public String getUrl() {
		return url;
	}
	
	/**
     * Setter: set the URL data member of Item object.
     * 
     * @param url The URL of Item object searched
     */
	public void setUrl(String url) {
		this.url = url;
	}
	
	/**
     * Getter: return the URL in Hyperlink form of Item object.
     * 
     * @return Hyperlink - URL of Item object
     */
	public Hyperlink getHyperlink() {
		return this.hyperlink;
	}
	
	/**
     * Setter: set the Hyperlink data member of Item object.
     * 
     * @param url The URL of Item object searched
     */
	public void setHyperlink(String url) {
		this.hyperlink = new Hyperlink();
		this.hyperlink.setText(url);
		this.hyperlink.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
				    Desktop.getDesktop().browse(new URL(url).toURI());
				} catch (Exception be) {}
			}
		});
	}
	
	/**
     * Getter: return the posted date of Item object. Mainly for internal comparison use.
     * 
     * @return Date - Posted date of Item object
     */
	public Date getDate() { //this is for getting the posted date in Date type (for Summary Tab, comparing dates of products)
		return this.postedDate;
	}
	
	/**
     * Getter: return the posted date of Item object. Mainly for GUI output.
     * 
     * @return String - Posted date of Item object
     */
	public String getPostedDate() {
		String temp;
		temp = this.postedDate.toString().substring(4, 7); //get the month
		temp += ' ';
		temp += this.postedDate.toString().substring(8, 10); //get the date
		temp += ", ";
		temp += this.postedDate.toString().substring(24, 28); //get the year		
		return temp;
	}
	
	/**
     * Setter: set the posted date data member of Item object. Used with results scrapped online.
     * 
     * @param inDate The posted date (of Date type variable) of Item object searched
     */
	public void setPostedDate(Date inDate) { //this is called by WebScrapper
		this.postedDate = inDate;
	}

	/**
     * Setter: set the posted date data member of Item object. Used with results loaded locally.
     * 
     * @param inDate The posted date (of String type variable) of Item object
     */
	public void setPostedDate(String inDate) {//this is for after loading a search record
		SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
		try{
			this.setPostedDate(formatter.parse(inDate));
		}catch (Exception e) {}
	}
}