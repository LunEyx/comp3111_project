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
	private TableView<Item> tableMain;
	private TableColumn<Item, String> tableColTitle;
	private TableColumn<Item, Double> tableColPrice;
	private TableColumn<Item, Hyperlink> tableColUrl;
	private TableColumn<Item, String> tableColPostedDate;
	
	public TableTab(TableView<Item> inTableMain, TableColumn inTableColTitle, TableColumn inTableColPrice, TableColumn inTableColUrl, TableColumn inTableColPostedDate){
		result = FXCollections.observableArrayList(Collections.<Item>emptyList());
		this.tableMain = inTableMain;
		this.tableColTitle = inTableColTitle;
		this.tableColPrice = inTableColPrice;
		this.tableColUrl = inTableColUrl;
		this.tableColPostedDate = inTableColPostedDate;
		
		this.tableColTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
		this.tableColPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
		this.tableColUrl.setCellValueFactory(new PropertyValueFactory<>("hyperlink"));
		this.tableColPostedDate.setCellValueFactory(new PropertyValueFactory<>("posted_date"));
		
		this.tableColTitle.setSortable(true);
		this.tableColPrice.setSortable(true);
		this.tableColUrl.setSortable(true);
		this.tableColPostedDate.setSortable(true);
		
		refreshTable();
	}
	
	public void initialize() {
		this.tableMain.getItems().clear();
	}

	public void refreshResult(List<Item> inResult) {
		this.result = FXCollections.observableArrayList(inResult);
		refreshTable();
		return;
	}
	
	private void refreshTable() {
		this.tableMain.setItems(result);
		return;
	}
}
