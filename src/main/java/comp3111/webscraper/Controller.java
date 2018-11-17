/**
 * 
 */
package comp3111.webscraper;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Hyperlink;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import javafx.stage.FileChooser;

import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 * 
 * @author kevinw
 *
 *
 * Controller class that manage GUI interaction. Please see document about JavaFX for details.
 * 
 */
public class Controller {

	//SummaryTab FXML start
    @FXML 
    private Label labelCount; 
    @FXML 
    private Label labelPrice; 
    @FXML 
    private Hyperlink labelMin; 
    @FXML 
    private Hyperlink labelLatest; 
    //SummaryTab FXML end
    
    @FXML
    private TextField textFieldKeyword;
    
    @FXML
    private TextArea textAreaConsole;
    
    @FXML
	private BarChart<String, Number> barChartHistogram;
    
    @FXML
    private MenuItem lastSearchMenuItem;
    
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
    
    private SummaryTab summaryTab;
    private TableTab tableTab;
    private DistributionTab distributionTab;
    
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
    	summaryTab = new SummaryTab(labelCount, labelPrice, labelMin, labelLatest);
    	tableTab = new TableTab(tableMain, tableColTitle, tableColPrice, tableColUrl, tableColPostedDate);
    	distributionTab = new DistributionTab(barChartHistogram, textAreaConsole);
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
    	
    	// This line is for basic 1
    	summaryTab.refresh(result);
    	
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

    	//This line is for basic 4
    	tableTab.refreshResult(result);
    }
    
    /**
     * Called when the 'Last Search' button is pressed. Let all tab display last search result.
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
    	summaryTab.refresh(lastSearchResult);
    	distributionTab.refresh(lastSearchKeyword, lastSearchResult);
    	tableTab.refreshResult(lastSearchResult);
    }
    
    /**
     * Called when the close button is pressed. Initialize all the tabs.
     */
    @FXML
    private void actionClose() {
    	textAreaConsole.setText("");
    	textFieldKeyword.setText("");
    	summaryTab.initialize();
    	distributionTab.initialize();
    	tableTab.initialize();
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
    
    @FXML
    private void saveToFile() {
    	System.out.println("Save");
    	FileChooser fc = new FileChooser();
    	File outputFile;
    	fc.setTitle("Save current search record");
    	fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files (.txt)", "*.txt"));
    	outputFile = fc.showSaveDialog(null);
    	if(outputFile != null) {
    		try {
    		    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
    		          new FileOutputStream(outputFile)));
    		          
    		    //output the keyword searched first
    		    writer.write(textFieldKeyword.getText());
    		    writer.newLine();
    		    writer.newLine();
    		    
    		    //output every item in the result list
    		    for(Item temp : currentSearchResult) {
    		    	writer.write(temp.getTitle());
    		    	writer.newLine();
    		    	writer.write(Double.toString(temp.getPrice()));
    		    	writer.newLine();
    		    	writer.write(temp.getUrl());
    		    	writer.newLine();
    		    	writer.write(temp.getPostedDate());
    		    	writer.newLine();
    		    	writer.newLine();
    		    }
    		    
    		    writer.close();
    		} catch (IOException ex) {}
    	}
    }
    
    @FXML
    private void loadFromFile() {
    	System.out.println("Load");
    	FileChooser fc = new FileChooser();
    	File inputFile;
    	fc.setTitle("Load a search record");
    	fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files (.txt)", "*.txt"));
    	inputFile = fc.showOpenDialog(null);
    	
    	if(inputFile != null) {
    		String tempKeyWord = null;
    		String tempTitle = null;
		    String tempPrice = null;
		    String tempUrl = null;
		    String tempPostedDate = null;
		    Boolean readableFile = true;
		    Item tempItem;
		    Vector<Item> tempList = new Vector<Item>();

		    try {
    		    BufferedReader reader = new BufferedReader(new InputStreamReader(
    		          new FileInputStream(inputFile)));
    		    
    		    tempKeyWord = reader.readLine(); //get the keyword in first line
    		    reader.readLine(); //read the empty spacing line
    		    
    		    while((tempTitle = reader.readLine()) != null) { //read all the attributes
    		    	tempPrice = reader.readLine();
    		    	tempUrl = reader.readLine();
    		    	tempPostedDate = reader.readLine();
    		    	reader.readLine(); //read the empty spacing line
    		    	
    		    	if(tempPrice == null || tempUrl == null || tempPostedDate == null) //to know that readLine() got eof and this indicates invalid file format
    		    		readableFile = false;
    		    	else {
    		    		tempItem = new Item();
    		    		tempItem.setTitle(tempTitle);
    		    		tempItem.setPrice(Double.parseDouble(tempPrice));
    		    		tempItem.setUrl(tempUrl);
    		    		tempItem.setPostedDate(tempPostedDate);
    		    		tempList.add(tempItem);
    		    	}
    		    }
    		    reader.close();
    		}catch (IOException ex) {
    			readableFile = false;
    		}catch (NumberFormatException e) {
    			readableFile = false;
    		}
    		
    		String output = "--Data Loading from " + inputFile + "--\n";
			if(readableFile) {
				for (Item item : tempList) {
		    		output += item.getTitle() + "\t" + item.getPrice() + "\t" + item.getUrl() + "\n";
		    	}
				textFieldKeyword.setText(tempKeyWord);
				currentSearchResult = tempList;
				summaryTab.refresh(currentSearchResult);
				tableTab.refreshResult(currentSearchResult);
				distributionTab.refresh(textFieldKeyword.getText(), currentSearchResult);
			}else {
				output = "Data File" + inputFile + " is an Invalid File";
				this.actionClose();
			}
				
			textAreaConsole.setText(output);	
    	}
    }
}
