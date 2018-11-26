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
import java.text.ParseException;

import javafx.stage.FileChooser;

import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 * Controller class that manage GUI interaction. Please see document about JavaFX for details.
 * @author kevin
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
    
    //Console Tab FXML start
    @FXML
    private TextField textFieldKeyword;
    
    @FXML
    private TextArea textAreaConsole;
  //Console Tab FXML end
    
    //DistributionTab FXML start
    @FXML
	private BarChart<String, Number> barChartHistogram;
    //DistributionTab FXML end
    
    //Menu Bar FXML
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
    
    //Data members: Tabs and WebScrapper
    private WebScraper scraper;
    private SummaryTab summaryTab;
    private TableTab tableTab;
    private DistributionTab distributionTab;
    
    //Data members: 
    private String currentSearchKeyword;
    private List<Item> currentSearchResult;
    private String lastSearchKeyword;
    private List<Item> lastSearchResult;
    
    
    /**
     * Default controller constructor
     */
    public Controller() {
    	scraper = new WebScraper();
    	currentSearchResult = null;
    }

    /**
     * Default initializer. Initialize all member variables of tabs.
     */
    @FXML
    private void initialize() {
    	summaryTab = new SummaryTab(labelCount, labelPrice, labelMin, labelLatest);
    	tableTab = new TableTab(tableMain, tableColTitle, tableColPrice, tableColUrl, tableColPostedDate);
    	distributionTab = new DistributionTab(barChartHistogram, textAreaConsole);
    }
    
    /**
     * Show results found by WebScrapper. Trigger Event: "Search" button clicked. 
     */
    @FXML
    private void actionSearch() {
    	System.out.println("actionSearch: " + textFieldKeyword.getText());
    	List<Item> result = scraper.scrape(textFieldKeyword.getText(), false);
    	
		if(result == null) { //s.t. there is error from the scrapper
    		String tempKeyword = textFieldKeyword.getText();
    		this.actionClose(); //clear all tabs
    		textFieldKeyword.setText(tempKeyword);
    		textAreaConsole.setText("Error from WebScrapper, empty results");
    	}else{
    		while(result.get(result.size()-1).testNextPage()){ //this is for basic feature 3, testing if more pages exist for show
    			System.out.println("next page");
        		String nextPageUrl;
        		nextPageUrl = result.get(result.size()-1).getUrl();
        		result.remove(result.size()-1);
        		
        		//because cannot achieve synchronized update, so comment out the refresh line
        		//refreshAllTabs(textFieldKeyword.getText(), result);
        		
        		result.addAll(scraper.scrape(nextPageUrl, true));
        	}
    		
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
    		//end of basic 6
        	
        	refreshAllTabs(textFieldKeyword.getText(), result);
    	}
    }
    
    /**
     * Let all tab display last search result. Trigger Event: "Last Search" button clicked. 
     */
    @FXML
    private void actionNew() {
    	lastSearchMenuItem.setDisable(true);
    	refreshAllTabs(lastSearchKeyword, lastSearchResult);
    }
    
    
    /**
     * Pop a dialog to show team information. Trigger Event: "About Your Team" menu item clicked. 
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
     * Quit the application and close all connection. Trigger Event: "Quit" menu item clicked. 
     */
    @FXML
    private void actionQuit() {
    	Platform.exit();
    }
    
    /**
     * Save the shown result to an output file. Trigger Event: "Save" button clicked. Opens a file chooser.
     */
    @FXML
    private void saveToFile() {
    	System.out.println("Save to File");
    	
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
    		    
    		    //output every item in the result list, a spcing line between each item
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
    
    /**
     * Read from an existing file and show the results stored in it. Trigger Event: "Load" button clicked. Opens a file chooser.  
     */
    @FXML
    private void loadFromFile() {
    	System.out.println("Load from File");
    	
    	FileChooser fc = new FileChooser();
    	File inputFile;
    	
    	fc.setTitle("Load a search record");
    	fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files (.txt)", "*.txt"));
    	inputFile = fc.showOpenDialog(null);
    	
    	if(inputFile != null) {
    		String tempKeyword = null;
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
    		    
    		    tempKeyword = reader.readLine(); //get the keyword in first line
    		    reader.readLine(); //read the empty spacing line
    		    
    		    while((tempTitle = reader.readLine()) != null) { //read all the attributes
    		    	tempPrice = reader.readLine();
    		    	tempUrl = reader.readLine();
    		    	tempPostedDate = reader.readLine();
    		    	reader.readLine(); //read the empty spacing line
    		    	
    		    	if(tempPrice == null || tempUrl == null || tempPostedDate == null) //to know that readLine() got eof and this indicates invalid file format
    		    		readableFile = false;
    		    	else {
    		    		try {
    		    			tempItem = new Item();
        		    		tempItem.setTitle(tempTitle);
        		    		tempItem.setPrice(Double.parseDouble(tempPrice));
        		    		tempItem.setUrl(tempUrl);
        		    		tempItem.setHyperlink(tempUrl);
        		    		tempItem.setPostedDate(tempPostedDate);
        		    		tempList.add(tempItem);
    		    		}catch(ParseException e) {
    		    			readableFile = false;
    		    		}
    		    		
    		    	}
    		    }
    		    reader.close();
    		}catch (IOException ex) {
    			readableFile = false;
    		}catch (NumberFormatException e) {
    			readableFile = false;
    		}
    		
    		String output; //because have to show extra information, the text of console have to be set explicitly
			if(readableFile) {
				currentSearchResult = tempList;
				refreshAllTabs(tempKeyword, currentSearchResult);
				
				output  = "--Data Loading from " + inputFile + "--\n";
				for (Item item : tempList) {
		    		output += item.getTitle() + "\t" + item.getPrice() + "\t" + item.getUrl() + "\n";
		    	}
			}else {
				output = "--Data File" + inputFile + " is an Invalid File--";
				this.resetAllTabs(); //reset all tabs data to empty
			}
				
			textAreaConsole.setText(output);	
    	}
    }
    
    /**
     * Initialize all the tabs. Trigger Event: "Close" button clicked. 
     */
    @FXML
    private void actionClose() {
    	resetAllTabs();
    }
    
    /**
     * Clear all the data of all the tabs and show default output.
     */
    private void resetAllTabs() {
    	this.resetConsole();
    	summaryTab.reset();
    	distributionTab.reset();
    	tableTab.reset();
    }
    
    /**
     * Clear all text areas in console tab
     */
    private void resetConsole() {
    	textAreaConsole.setText("");
    	textFieldKeyword.setText("");
    }
    
    /**
     * Refresh all the tabs by the search result.
     * 
     * @param keyword searched keyword
     * @param items results to be shown
     */
    private void refreshAllTabs(String keyword, List<Item> items) {
    	this.refreshConsole(keyword, items);
    	summaryTab.refresh(items);
    	distributionTab.refresh(keyword, items);
    	tableTab.refreshResult(items);
    }
    
    /**
     * Refresh console and keyword text area
     * 
     * @param keyword searched keyword
     * @param items results to be shown
     */
    private void refreshConsole(String keyword, List<Item> items){
    	String output = "";
    	for (Item item : items) {
    		output += item.getTitle() + "\t" + item.getPrice() + "\t" + item.getUrl() + "\n";
    	}
    	textAreaConsole.setText(output);
    	textFieldKeyword.setText(keyword);
    }
}