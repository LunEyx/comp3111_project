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

/**
 * TableTab class for manipulating the Table Tab
 *
 */
public class TableTab {
	private ObservableList<Item> result;
	private TableView<Item> tableMain;
	private TableColumn<Item, String> tableColTitle;
	private TableColumn<Item, Double> tableColPrice;
	private TableColumn<Item, Hyperlink> tableColUrl;
	private TableColumn<Item, Date> tableColPostedDate;
	
	/**
	 * Constructor for TableTab.
	 * 
	 * @param inTableMain reference variable to FXML TableView (for Item objects) in TableTab which is the whole table base itself.
	 * @param inTableColTitle reference variable to FXML TableColumn (for String) inside the TableView responsible for showing title of items.
	 * @param inTableColPrice reference variable to FXML TableColumn (for Double) inside the TableView responsible for showing price of items.
	 * @param inTableColUrl  reference variable to FXML TableColumn (for Hyperlink) inside the TableView responsible for showing URL of items.
	 * @param inTableColPostedDate  reference variable to FXML TableColumn (for String) inside the TableView responsible for showing posted date of items.
	 */
	public TableTab(TableView<Item> inTableMain, TableColumn<Item, String> inTableColTitle, TableColumn<Item, Double> inTableColPrice, TableColumn<Item, Hyperlink> inTableColUrl, TableColumn<Item, Date> inTableColPostedDate){
		result = FXCollections.observableArrayList(Collections.<Item>emptyList());
		this.tableMain = inTableMain;
		this.tableColTitle = inTableColTitle;
		this.tableColPrice = inTableColPrice;
		this.tableColUrl = inTableColUrl;
		this.tableColPostedDate = inTableColPostedDate;
		
		this.tableColTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
		this.tableColPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
		this.tableColUrl.setCellValueFactory(new PropertyValueFactory<>("hyperlink"));
		this.tableColPostedDate.setCellValueFactory(new PropertyValueFactory<>("postedDate"));
		
		this.tableColTitle.setSortable(true);
		this.tableColPrice.setSortable(true);
		this.tableColUrl.setSortable(true);
		this.tableColPostedDate.setSortable(true);
		
		refreshTable();
	}
	
	/**
	 * Clear all the data of the table.
	 */
	public void reset() {
		this.tableMain.getItems().clear();
	}
	
	/**
	 * Refresh the result data member in TableTab with results to be shown, then refresh the table.
	 * 
	 * @param inResult List of Item objects to be shown
	 */
	public void refreshResult(List<Item> inResult) {
		this.result = FXCollections.observableArrayList(inResult);
		refreshTable();
		return;
	}
	
	/**
	 * Refresh the table with stored list of items in result data member
	 */
	private void refreshTable() {
		this.tableMain.setItems(result);
		return;
	}
}
