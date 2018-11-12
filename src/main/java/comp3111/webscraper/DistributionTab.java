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
	
	public DistributionTab(BarChart<String, Number> barChartHistogram, TextArea textAreaConsole) {
		this.barChartHistogram = barChartHistogram;
		this.textAreaConsole = textAreaConsole;
		defaultHistogram();
	}
	
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
	
	private void defaultHistogram() {
		this.barChartHistogram.setTitle("No Result");
		this.barChartHistogram.getXAxis().setLabel("Dollars ($)");
		this.barChartHistogram.getYAxis().setLabel("Products");
		this.barChartHistogram.getData().clear();
		this.barChartHistogram.layout();
	}
	
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
