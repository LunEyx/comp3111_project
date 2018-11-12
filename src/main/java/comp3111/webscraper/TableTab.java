package comp3111.webscraper;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Hyperlink;

public class TableTab {
	private ObservableList<Item> result;
	private TableView<Item> table_main;
	private TableColumn<Item, String> table_col_title;
	private TableColumn<Item, Double> table_col_price;
	private TableColumn<Item, Hyperlink> table_col_url;
	private TableColumn<Item, String> table_col_posted_date;
	
	public TableTab(TableView<Item> in_table_main, TableColumn in_table_col_title, TableColumn in_table_col_price, TableColumn in_table_col_url, TableColumn in_table_col_posted_date){
		result = FXCollections.observableArrayList(Collections.<Item>emptyList());
		this.table_main = in_table_main;
		this.table_col_title = in_table_col_title;
		this.table_col_price = in_table_col_price;
		this.table_col_url = in_table_col_url;
		this.table_col_posted_date = in_table_col_posted_date;
		
		this.table_col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
		this.table_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
		this.table_col_url.setCellValueFactory(new PropertyValueFactory<>("hyperlink"));
		this.table_col_posted_date.setCellValueFactory(new PropertyValueFactory<>("posted_date"));
		
		this.table_col_title.setSortable(true);
		this.table_col_price.setSortable(true);
		this.table_col_url.setSortable(true);
		this.table_col_posted_date.setSortable(true);
		
		refresh_table();
	}
	
	public void refresh_result(List<Item> in_result) {
		this.result = FXCollections.observableArrayList(in_result);
		refresh_table();
		return;
	}
	
	private void refresh_table() {
		this.table_main.setItems(result);
		return;
	}
}
