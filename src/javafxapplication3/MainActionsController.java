package javafxapplication3;

import FYPpackage.Caracteristics.CaracteristiqueGenerator;
import FYPpackage.DataExtraction.TwitterExtraction;
import FYPpackage.LeadersCommunityTimeline.Community;
import FYPpackage.LeadersCommunityTimeline.LeadersTimeline;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.DirectoryChooser;
import javafx.stage.WindowEvent;


public class MainActionsController implements Initializable{
   
    @FXML
    private Button stats ;
    @FXML 
    private Button mail;
    @FXML 
    private Button contact;
    @FXML 
    private Button campaign;
    
    public ArrayList<Community> communities = new ArrayList<>();
    private TwitterExtraction extract;
    private LeadersTimeline opinionDetection;
    private HashMap<String,String> communityNameAttribution = new HashMap<>();
    public void setCommunityNameAttribution(HashMap<String,String> communityNameAttribution)
    {
        this.communityNameAttribution = communityNameAttribution;
    }
    public void setTwitterExtraction(TwitterExtraction extract)
    {
        this.extract = extract;
    }
    public void setLeadersTimeline(LeadersTimeline opinionDetection)
    {
        this.opinionDetection = opinionDetection;
       
    }
    @FXML
    private void handleButtonAction() throws IOException {   
        
                ArrayList<String> criteres = new ArrayList<>();
                criteres.add("Location");
                criteres.add("Lang");
                
                CaracteristiqueGenerator caracteristics = new CaracteristiqueGenerator(extract.list_app,criteres,opinionDetection.communities,extract.list_followers,extract.integrationGraphInvers);
                caracteristics.getCaracteristique();
                for(Community com : opinionDetection.communities)
                {
                    com.showCaracteristiques();
                }
        
        
                System.out.println("\n debut du stat handler");

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLStatistics.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                FXMLStatisticsController controller = (FXMLStatisticsController) fxmlLoader.getController();
                controller.setCommunities(communities);
                controller.setCommunityNameAttribution(this.communityNameAttribution);
                controller.chartsConstruction();
                
                
                Stage stage = new Stage();
                stage.setTitle("Statistics");
                stage.setScene(new Scene(root1)); 
                stage.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void setCommunities(ArrayList<Community> communities)
    {
        this.communities = communities;
    }
    
}
