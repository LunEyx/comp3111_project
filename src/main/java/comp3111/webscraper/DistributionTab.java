package comp3111.webscraper;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

public class DistributionTab {
	private BarChart<String, Number> barChartHistogram;
	private TextArea textAreaConsole;
	
	private List<List<Item>> data;
	
	/**
	 * Constructor for DistributionTab
	 * 
	 * @param barChartHistogram - reference variable to FXML BarChart that shows data in a bar chart
	 * @param textAreaConsole - - reference variable to FXML TextArea which is inside ConsoleTabe
	 */
	public DistributionTab(BarChart<String, Number> barChartHistogram, TextArea textAreaConsole) {
		this.barChartHistogram = barChartHistogram;
		this.textAreaConsole = textAreaConsole;
		defaultHistogram();
	}
	
	/**
	 * Call defaultHistogram() to clear all the data of the bar chart and title.
	 */
	public void reset() {
		defaultHistogram();
	}
	
	/**
	 * Refresh the bar chart with results to be shown.
	 * 
	 * @param keyword - The searched keyword
	 * @param items - List of Item objects to be shown
	 */
	public void refresh(String keyword, List<Item> items) {
		if (items.isEmpty()) {
			defaultHistogram();
			return;
		}

		this.barChartHistogram.setTitle("The selling price of " + keyword);
		items = new ArrayList<>(items);
		List<List<Item>> priceData = groupByPrice(sortItems(items));
		if (priceData.get(0).get(0).getPrice() == 0) {
			priceData.remove(0);
		}
		
		while (!drawChart(priceData)) {
			priceData.get(priceData.size() - 2).addAll(priceData.get(priceData.size() - 1));
			priceData.remove(priceData.size() - 1);
		}
	}
	
	/**
	 * Sort all Item objects to be shown according to price.
	 * 
	 * @param items - List of currently showing Item objects
	 * @return List<Item> Sorted list of the input Item objects
	 */
	private List<Item> sortItems(List<Item> items) {
		items.sort((item1, item2) -> {
			if (item1.getPrice() < item2.getPrice())
				return -1;
			else if (item1.getPrice() > item2.getPrice())
				return 1;
			else
				return 0;
		});
		
		return items;
	}
	
	/**
	 * Group Item objects to be shown according to their price and return in form of Lists
	 * 
	 * @param sortedItems - Sorted list of all Item objects to be shown according to price.
	 * @return List<List<Item>> Groupings of List<Item> according to price of Item objects
	 */
	private List<List<Item>> groupByPrice(List<Item> sortedItems) {
		List<List<Item>> data = new ArrayList<>();
		for (Item item : sortedItems) {
			if (data.isEmpty() || data.get(data.size() - 1).get(0).getPrice() != item.getPrice()) {
				data.add(new ArrayList<>());
			}
			data.get(data.size() - 1).add(item);
		}
		
		return data;
	}
	
	/**
	 * Clear all the data of the bar chart and title.
	 */
	private void defaultHistogram() {
		this.barChartHistogram.setTitle("No Result");
		this.barChartHistogram.getXAxis().setLabel("Dollars ($)");
		this.barChartHistogram.getYAxis().setLabel("Products");
		this.barChartHistogram.getData().clear();
		this.barChartHistogram.layout();
	}
	
	/**
	 * Refresh the bar chart with data to be shown.
	 * 
	 * @param priceData - List of List<Item>, which are Item objects grouped accordingly to price.
	 * @return boolean Indicator of huge gap exist between groups of price.
	 */
	private boolean drawChart(List<List<Item>> priceData) {
		XYChart.Series<String, Number> series = new XYChart.Series<>();
		
		if (priceData.size() <= 10) {
			for (int i = 0; i < priceData.size(); i++) {
				series.getData().add(new XYChart.Data<String, Number>("" + priceData.get(i).get(0).getPrice(), priceData.get(i).size()));
				this.data = priceData;
			}
		} else {
			double min = priceData.get(0).get(0).getPrice();
			double max = priceData.get(priceData.size() - 1).get(0).getPrice();
			double range = (max - min) / 10;
			List<List<Item>> data = new ArrayList<>(10);
			for (int i = 0; i < 10; i++) {
				data.add(new ArrayList<Item>());
			}
			int p = 0;
			for (List<Item> list : priceData) {
				int oldP = p;
				while (list.get(0).getPrice() > min + (p+1) * range) {
					p++;
				}
				if (p - oldP > 4) {
					return false;
				}
				data.get(p).addAll(list);
			}
			for (int i = 0; i < 10; i++) {
				XYChart.Data<String, Number> chartData = new XYChart.Data<String, Number>(String.format("%.2f%s", (min + (i + 0.5) * range), (i == 9 ? "+" : "")), data.get(i).size());
				series.getData().add(chartData);
			}
			this.data = data;
		}
		
		this.barChartHistogram.getData().clear();
		this.barChartHistogram.getData().add(series);

        for (int i = 0; i < series.getData().size(); i++){
        	final int fi = i;
        	series.getData().get(i).getNode().setOnMouseClicked((MouseEvent event) -> {
        		if (event.getClickCount() == 2) {
	            	String output = "";
	            	List<Item> list = data.get(fi);
	            	for (Item item : list) {
	            		output += item.getTitle() + "\t" + item.getPrice() + "\t" + item.getUrl() + "\n";
	            	}
	            	this.textAreaConsole.setText(output);

            		for (int column = 0; column < series.getData().size(); column++) {
            			series.getData().get(column).getNode().setStyle("");
            		}
            		series.getData().get(fi).getNode().setStyle("-fx-bar-fill: orange");
            	}
            });
        }
		
		return true;
	}
}
