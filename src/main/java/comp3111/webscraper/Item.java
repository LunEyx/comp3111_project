package comp3111.webscraper;

import java.awt.Desktop;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Hyperlink;


public class Item {
	private String title ; 
	private double price ;
	private String url ;
	private Date postedDate;
	private Hyperlink hyperlink;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url, String url2) {
		this.url = url;
		this.hyperlink = new Hyperlink();
		this.hyperlink.setText(url2);
		this.hyperlink.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
				    Desktop.getDesktop().browse(new URL(url2).toURI());
				} catch (Exception be) {}
			}
		});
	}
	public Hyperlink getHyperlink() {
		return this.hyperlink;
	}
	public void setPostedDate(Date inDate) {
		this.postedDate = inDate;
	}
	public String getPostedDate() {
		String temp;
		temp = this.postedDate.toString().substring(4, 7); //get the month
		temp += ' ';
		temp += this.postedDate.toString().substring(8, 10); //get the date
		temp += ", ";
		temp += this.postedDate.toString().substring(24, 28); //get the year		
		return temp;
	}
	
	public void setUrl(String url) {//this is for after loading a search record
		System.out.println(url);
		for(int i=0; i<url.length()-8; i++) {
			if (url.substring(i, i+8).equals("https://")) this.setUrl(url.substring(0, i), url.substring(i, url.length()));
		}
	}
	public void setPostedDate(String inDate) {//this is for after loading a search record
		SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
		try{
			this.setPostedDate(formatter.parse(inDate));
		}catch (Exception e) {}
	}
}
