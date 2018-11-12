/**
 * 
 */
package comp3111.webscraper;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Hyperlink;
import java.util.List;


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
    
    @FXML
    private MenuItem lastSearchMenuItem;
    
    private WebScraper scraper;
    private List<Item> items;
    
    private ConsoleTab consoleTab;
    private SummaryTab summaryTab;
    private TableTab tableTab;
    private DistributionTab distributionTab;
    private TrendTab trendTab;
    
    private String currentSearchKeyword;
    private List<Item> currentSearchResult;
    private String lastSearchKeyword;
    private List<Item> lastSearchResult;
    
    /**
     * Default controller
     */
    public Controller() {
    	scraper = new WebScraper();
    	currentSearchResult = null;
    }

    /**
     * Default initializer. It is empty.
     */
    @FXML
    private void initialize() {
    	consoleTab = new ConsoleTab();
    	summaryTab = new SummaryTab();
    	tableTab = new TableTab();
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
    	
    	// Below codes are related to basic 6
    	if (currentSearchResult != null) {
        	lastSearchKeyword = currentSearchKeyword;
    		lastSearchResult = currentSearchResult;
    	} else {
    		lastSearchKeyword = textFieldKeyword.getText();
    		lastSearchResult = result;
    	}
    	currentSearchKeyword = textFieldKeyword.getText();
    	currentSearchResult = result;
    	lastSearchMenuItem.setDisable(false);
    }
    
    /**
     * Called when the new button is pressed. Very dummy action - print something in the command prompt.
     */
    @FXML
    private void actionNew() {
    	lastSearchMenuItem.setDisable(true);
    	String output = "";
    	for (Item item : lastSearchResult) {
    		output += item.getTitle() + "\t" + item.getPrice() + "\t" + item.getUrl() + "\n";
    	}
    	textAreaConsole.setText(output);
    	
    	textFieldKeyword.setText(lastSearchKeyword);
    	
    	distributionTab.refresh(lastSearchKeyword, lastSearchResult);
    }
    
    /**
     * Called when the close button is pressed. Initialize all the tabs.
     */
    @FXML
    private void actionClose() {
    	textAreaConsole.setText("");
    	textFieldKeyword.setText("");
    	distributionTab.initialize();
    }
    
    /**
     * Called when the 'About Your Team' menu item is pressed. Pop a dialog to show team information.
     */
    @FXML
    private void showTeamInfo() {
    	String[] name = {"Lam Ping Yeung", "Fung King Fai", "Chang Chu Ling"};
    	String[] itsc = {"pylamag", "kffungaa", "clchang"};
    	String[] github = {"LunEyx", "ArekaFung", "Patsixd"};
    	String content = "";
    	for (int i = 0; i < 3; i++) {
    		content += "Member " + (i+1) + ":\n";
    		content += "\tName: " + name[i] + "\n";
    		content += "\tITSC: " + itsc[i] + "\n";
    		content += "\tGithub: " + github[i] + "\n\n";
    	}
    	final Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("About Your Team");
    	alert.setHeaderText("");
    	alert.setContentText(content);
    	alert.showAndWait();
    }
    
    /**
     * Called when the 'Quit' menu item is pressed. Quit the application and close all connection.
     */
    @FXML
    private void actionQuit() {
    	Platform.exit();
    }
}

