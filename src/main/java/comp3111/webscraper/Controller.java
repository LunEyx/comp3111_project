/**
 * 
 */
package comp3111.webscraper;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
    
    private WebScraper scraper;
    
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
    	
    	
    }
    
    /**
     * Called when the new button is pressed. Very dummy action - print something in the command prompt.
     */
    @FXML
    private void actionNew() {
    	System.out.println("actionNew");
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
}

