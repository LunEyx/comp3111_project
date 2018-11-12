/**
 * 
 */
package comp3111.webscraper;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Hyperlink;

import java.util.Date;
import java.util.List;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

import javafx.stage.FileChooser;


/**
 * 
 * @author kevinw
 *
 *
 * Controller class that manage GUI interaction. Please see document about JavaFX for details.
 * 
 */
public class Controller {

    @FXML 
    private Label labelCount; 

    @FXML 
    private Label labelPrice; 

    @FXML 
    private Hyperlink labelMin; 

    @FXML 
    private Hyperlink labelLatest; 

    @FXML
    private TextField textFieldKeyword;
    
    @FXML
    private TextArea textAreaConsole;
    
    @FXML
	private BarChart<String, Number> barChartHistogram;
    
    //TableTab FXML start
    @FXML
    private TableView<Item> tableMain;
    @FXML
    private TableColumn<Item, String> tableColTitle;
    @FXML
    private TableColumn<Item, Double> tableColPrice;
    @FXML
    private TableColumn<Item, Hyperlink> tableColUrl;
    @FXML
    private TableColumn<Item, Date> tableColPostedDate;
    //TableTab FXML end
    
    private WebScraper scraper;
    private List<Item> items;
    
    private ConsoleTab consoleTab;
    private SummaryTab summaryTab;
    private TableTab tableTab;
    private DistributionTab distributionTab;
    private TrendTab trendTab;
    
    /**
     * Default controller
     */
    public Controller() {
    	scraper = new WebScraper();
    }

    /**
     * Default initializer. It is empty.
     */
    @FXML
    private void initialize() {
    	consoleTab = new ConsoleTab();
    	summaryTab = new SummaryTab();
    	tableTab = new TableTab(tableMain, tableColTitle, tableColPrice, tableColUrl, tableColPostedDate);
    	distributionTab = new DistributionTab(barChartHistogram, textAreaConsole);
    	trendTab = new TrendTab();
    }
    
    /**
     * Called when the search button is pressed.
     */
    @FXML
    private void actionSearch() {
    	System.out.println("actionSearch: " + textFieldKeyword.getText());
    	List<Item> result = scraper.scrape(textFieldKeyword.getText());
    	String output = "";
    	for (Item item : result) {
    		output += item.getTitle() + "\t" + item.getPrice() + "\t" + item.getUrl() + "\n";
    	}
    	textAreaConsole.setText(output);
    	
    	// This line is for advance 1
    	distributionTab.refresh(textFieldKeyword.getText(), result);
    	
    	//This line is for basic 4
    	tableTab.refreshResult(result);
    }
    
    /**
     * Called when the new button is pressed. Very dummy action - print something in the command prompt.
     */
    @FXML
    private void actionNew() {
    	System.out.println("actionNew");
    }
    
    @FXML
    private void saveToFile() {
    	System.out.println("Save");
    	FileChooser fc = new FileChooser();
    	fc.setTitle("Save search record");
    	fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files (.txt)", "*.txt"));
    	fc.showSaveDialog(null);
    }
    @FXML
    private void loadFromFile() {
    	System.out.println("Load");
    	FileChooser fc = new FileChooser();
    	fc.setTitle("Load a search record");
    	fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files (.txt)", "*.txt"));
    	fc.showOpenDialog(null);
    }
}

