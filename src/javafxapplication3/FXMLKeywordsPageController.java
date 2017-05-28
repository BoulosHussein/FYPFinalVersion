/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication3;

import FYPpackage.CommunityDetection.BigClam;
import FYPpackage.DataExtraction.Pair;
import FYPpackage.DataExtraction.TwitterExtraction;
import FYPpackage.LeadersCommunityTimeline.Leader_Community;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author generals
 */
public class FXMLKeywordsPageController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button runId;
    @FXML
    private ScrollPane scrollId;
    @FXML
    private FlowPane flowId;
    @FXML
    private Button addKeywordId;
    ArrayList<TextField> keywordsTextField = new ArrayList<>();
    
        //=>a chaque reseau social, son depth et sa liste de keywords;
    private HashMap<String,Pair<Integer,ArrayList<String>>> tasksExtraction = new HashMap<>();
    BigClam bigClam;
    TwitterExtraction extractAtt;
    ArrayList<Double> rank;
    HashMap<Integer,Long> invers;
    ArrayList<String> keywords;
    ArrayList<String> keywordsOrigin = new ArrayList<>();
    Leader_Community lc;
    HashMap<String,String> communityNameAttribution;
    HashMap<Integer,ArrayList<Integer>> map = new HashMap<>();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void setMap(HashMap<Integer,ArrayList<Integer>> map)
    {
        this.map = map;
    }
    public void addKeyword()
    {
        double space = this.flowId.getVgap();
        TextField text = new TextField();
        text.setPromptText("keyword");
        int numberOfChild = this.flowId.getChildren().size();
        if(numberOfChild != 0)
        {
           double sizeOfFlow = this.flowId.getHeight();
           double textFieldHeight = ((TextField)(this.flowId.getChildren().get(numberOfChild-1))).getHeight();
           double sizeOfTextFields = numberOfChild*textFieldHeight + (numberOfChild+1)*space;
           
           if(sizeOfTextFields+textFieldHeight>sizeOfFlow)
           {
                double currentLength = this.flowId.getHeight();
                this.flowId.setPrefHeight(currentLength+textFieldHeight+space);
           }
        }
        this.flowId.getChildren().add(text);
        keywordsTextField.add(text);
        System.out.println("textField:"+text.getText());
    }
    
    public void run() throws IOException
    {
        if(this.keywordsTextField.isEmpty())
        {    
            Alert alert = new Alert(Alert.AlertType.WARNING,
                        "Won't you like to add Dictionary?",
            ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = alert.showAndWait();
        }
        else
        {            
                //FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLRunPage.fxml"));
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLNewRunPage.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                //FXMLRunPageController controller = (FXMLRunPageController) fxmlLoader.getController();
                FXMLNewRunPageController controller = (FXMLNewRunPageController) fxmlLoader.getController();
                controller.setBigClam(this.bigClam);
                controller.setTaskExtraction(tasksExtraction);
                controller.setTwitterExtraction(extractAtt);
                setKeywordsOrigin(); //HON L MESHKLE
                controller.setKeywordsOriginal(keywordsOrigin);
                controller.setLeaderCommunity(lc);
                controller.setCommunityNameAttribution(communityNameAttribution);
                controller.setKeywords(keywords);
                controller.setInverse(this.invers);
                controller.setRank(this.rank);
                controller.setMap(this.map);
                
                
                Stage stage = new Stage();
                stage.setTitle("Run Page");
                stage.setScene(new Scene(root1));  
                stage.addEventHandler(WindowEvent.WINDOW_SHOWN, new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent window) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {                     
                                controller.run();
//                                controller.createStatistics();
                                //controller.setTaskExtraction(tasksExtraction);
                                //controller.run();
                            }
                        });
                    }
                });          
                stage.show();        
        }
    }

    public void setKeywordsOrigin()
    {
        for(int i=0;i<this.keywordsTextField.size();++i)
        {
            if(!keywordsTextField.get(i).getText().equals(""))
                this.keywordsOrigin.add(keywordsTextField.get(i).getText());
        }
    }
    public void setRank(ArrayList<Double> rank)
    {
        this.rank = rank;
    }
    public void setInvers(HashMap<Integer,Long> invers)
    {
        this.invers = invers;
    }
    public void setKeywords(ArrayList<String> k )//correspond aux accounts
    {   
        this.keywords = k;
    }
    public void setBigClam(BigClam b)
    {
        this.bigClam = b;
    }
    public void setTaskExtraction(HashMap<String,Pair<Integer,ArrayList<String>>> tasksExtraction )
    {
        this.tasksExtraction = tasksExtraction;
    }
    public void setTwitterExtraction(TwitterExtraction extract)
    {
        this.extractAtt = extract;
    }
    public void setLeaderCommunity(Leader_Community lc)
    {
        this.lc = lc;
    }
    public void setCommunityNameAttribution(HashMap<String,String> communityNameAttribution)
    {
        this.communityNameAttribution = communityNameAttribution;
    }
    
}
