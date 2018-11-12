package comp3111.webscraper;

import java.util.Date;
import java.util.List;

import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;

public class SummaryTab {
	private Label labelCount;
	private Label labelPrice;
	private Hyperlink labelMin;
	private Hyperlink labelLatest;
	
	public SummaryTab(Label labelCount, Label labelPrice, Hyperlink labelMin, Hyperlink labelLatest) {
		this.labelCount = labelCount;
		this.labelPrice = labelPrice;
		this.labelMin = labelMin;
		this.labelLatest = labelLatest;
	}
	
	public void refresh(List<Item> items) {
		int itemsCount = 0;
		double itemsPrice = 0;
		String itemsMin = "";
		String itemsMinUrl = "";
		double itemsMinValue = 0;
		String itemsLatest = "";
		String itemsLatestUrl = "";
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
			labelLatest.setText(itemsLatest);
		}
	}
}
