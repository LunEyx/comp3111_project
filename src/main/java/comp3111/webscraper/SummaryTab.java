package comp3111.webscraper;

import java.awt.Desktop;
import java.net.URL;
import java.util.Date;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;

public class SummaryTab {
	private Label labelCount;
	private Label labelPrice;
	private Hyperlink labelMin;
	private Hyperlink labelLatest;
	private String itemsMinUrl;
	private String itemsLatestUrl;
	
	
	/**
     * Constructor for SummaryTab.
     * 
     * @param labelCount - reference variable to FXML label in Summary Tab that shows amount of searched items.
     * @param labelPrice - reference variable to FXML label in Summary Tab that shows average selling price.
     * @param labelMin - reference variable to FXML Hyperlink in Summary Tab that shows Hyperlink to lowest price item.
     * @param labelLatest - reference variable to Hyperlink label in Summary Tab that shows Hyperlink to latest posted item.
     */
	public SummaryTab(Label labelCount, Label labelPrice, Hyperlink labelMin, Hyperlink labelLatest) {
		this.labelCount = labelCount;
		this.labelPrice = labelPrice;
		this.labelMin = labelMin;
		this.labelLatest = labelLatest;
	}
	
	/**
	 * Clear the output data and set labels to invalid values.
	 */
	public void reset() {
		labelCount.setText("0");
    	labelPrice.setText("-");
    	labelMin.setText("-");
    	labelLatest.setText("-");
	}
	
	/**
	 * Refresh all data of SummaryTab with the results to be shown.
	 * 
	 * @param items
	 */
	public void refresh(List<Item> items) {
		int itemsCount = 0;
		double itemsPrice = 0;
		String itemsMin = "";
		double itemsMinValue = 0;
		String itemsLatest = "";
		Date itemsLatestValue = new Date();
		
		for (Item item : items) {
			if (item.getPrice() > 0) {
				itemsCount += 1;
				itemsPrice += item.getPrice();
				if (item.getPrice() < itemsMinValue || itemsMin == "") {
					itemsMinValue = item.getPrice();
					itemsMin = item.getTitle();
					itemsMinUrl = item.getUrl();
				}
				if (item.getDate().after(itemsLatestValue) || itemsLatest == "") {
					itemsLatest = item.getTitle();
					itemsLatestUrl = item.getUrl();
					itemsLatestValue = item.getDate();
				}
			}
    	}
		
		if (itemsCount == 0) {
			labelCount.setText("0");
			labelPrice.setText("-");
			labelMin.setText("-");
			labelLatest.setText("-");
		}
		else {
			labelCount.setText(String.valueOf(itemsCount));
			labelPrice.setText(String.valueOf(itemsPrice/itemsCount));
			labelMin.setText(itemsMin);
			labelMin.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					try {
						Desktop.getDesktop().browse(new URL(itemsMinUrl).toURI());
					} catch (Exception be) {}
				}
			});
			labelLatest.setText(itemsLatest);
			labelLatest.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					try {
						Desktop.getDesktop().browse(new URL(itemsLatestUrl).toURI());
					} catch (Exception be) {}
				}
			});
		}
	}
}
