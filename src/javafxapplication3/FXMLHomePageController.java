/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication3;

import FYPpackage.DataExtraction.Pair;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author generals
 */
public class FXMLHomePageController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private MenuBar menuBarId ;
    @FXML
    private ScrollPane keyWordscrollId;
    @FXML
    private FlowPane keyWordFlowId; 
    @FXML
    private Button keyWordButtonId;
    @FXML
    private Pane socialGraphPaneId;
    @FXML
    private Pane twitterPaneId;
    @FXML
    private Pane twitterSubPaneId;
    @FXML
    private RadioButton twitterRadioId;
    @FXML
    private ImageView twitterImageId;
    @FXML
    private Slider twitterSliderId;
    @FXML
    private Label twitterLabelDepthId;
    @FXML
    private TextField twitterTextFieldDepthId;
    @FXML
    private Pane facebookPaneId;
    @FXML
    private Pane facebookSubPaneId;
    @FXML
    private RadioButton facebookRadioId;
    @FXML
    private ImageView facebookImageId;
    @FXML
    private Slider facebookSliderId;
    @FXML
    private Label facebookLabelDepthId;
    @FXML
    private TextField facebookTextFieldDepthId;
    @FXML
    private Pane linkedInPaneId;
    @FXML
    private Pane linkedInSubPaneId;
    @FXML
    private RadioButton linkedInRadioId;
    @FXML
    private ImageView linkedInImageId;
    @FXML
    private Slider linkedInSliderId;
    @FXML
    private Label linkedInLabelDepthId;
    @FXML
    private TextField linkedInTextFieldDepthId;
    
    @FXML
    private Button runId;
    
    private ArrayList<TextField> keywords = new ArrayList<>();  
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //setting the listerner on twitterSlider
        this.twitterSliderId.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                    int new_val_i = (int)new_val.doubleValue();
                    //twitterTextFieldDepthId.setText(String.format("%.2f", new_val));
                    twitterTextFieldDepthId.setText(""+new_val_i);
            }
        });
        //setting the listerner on facebookSlider
        this.facebookSliderId.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                    int new_val_i = (int)new_val.doubleValue();
                    //facebookTextFieldDepthId.setText(String.format("%.2f", new_val));
                    facebookTextFieldDepthId.setText(""+new_val_i);
            }
        });
        //setting the listerner on linkedInSlider
        this.linkedInSliderId.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                    int new_val_i = (int)new_val.doubleValue();
                    //linkedInTextFieldDepthId.setText(String.format("%.2f", new_val));
                    linkedInTextFieldDepthId.setText(""+new_val_i);
            }
        });
    }    
    private static boolean isNumeric(String str)
    {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
            return true;
    }
    public void addTextField()
    {
        double space = this.keyWordFlowId.getVgap();
        TextField text = new TextField();
        text.setPromptText("account");
        int numberOfChild = this.keyWordFlowId.getChildren().size();
        if(numberOfChild != 0)
        {
           double sizeOfFlow = this.keyWordFlowId.getHeight();
           double textFieldHeight = ((TextField)(this.keyWordFlowId.getChildren().get(numberOfChild-1))).getHeight();
           double sizeOfTextFields = numberOfChild*textFieldHeight + (numberOfChild+1)*space;
           
           if(sizeOfTextFields+textFieldHeight>sizeOfFlow)
           {
                double currentLength = this.keyWordFlowId.getHeight();
                this.keyWordFlowId.setPrefHeight(currentLength+textFieldHeight+space);
           }
        }
        this.keyWordFlowId.getChildren().add(text);
        this.keywords.add(text);
        System.out.println("textField:"+text.getText());
    }
    @FXML
    private void handleRunAction(ActionEvent event) throws IOException
    {
       if(twitterRadioId.isSelected() || facebookRadioId.isSelected() || linkedInRadioId.isSelected())
       {
           //we get the list of keywords; otherwise, popup to inform the user that he should specify a social graph;
            ArrayList<String> keywords = new ArrayList<>();
            int depth = 0;
           
            for(TextField text : this.keywords)
            {
                 if(!text.getText().equals("")) //working only on the textFields that aren't empty
                     keywords.add(text.getText());
            }
            if(keywords.size()!=0)
            {
                HashMap<String,Pair<Integer,ArrayList<String>>> tasksExtraction = new HashMap<>();
                
                if(twitterRadioId.isSelected())
                {
                    //we run it on the twitter social graph, with a thread; //to dont copy the same code multiple times,
                    //une classe factory qui prend en parametre le sociial media, et qui extrait les donnees, puis applique les memes algorithmes sur chacun;
                   depth = Integer.parseInt(this.twitterTextFieldDepthId.getText());
                   System.out.println("depth of twitter:"+depth);
                   Pair<Integer,ArrayList<String>> pair = new Pair<>(depth,keywords);
                   tasksExtraction.put("twitter", pair);
                                    
                }
                if(facebookRadioId.isSelected())
                {
                    //we run it on the facebook social graph, with another thread;
                    depth = Integer.parseInt(this.facebookTextFieldDepthId.getText());
                    System.out.println("depth of facebook:"+depth);
                    Pair<Integer,ArrayList<String>> pair = new Pair<>(depth,keywords);
                    tasksExtraction.put("facebook", pair);
                }
                if(linkedInRadioId.isSelected())
                {
                   //we run it on the linkedIn social graph, with another thread;
                    depth = Integer.parseInt(this.linkedInTextFieldDepthId.getText());
                    System.out.println("depth of linkedIn:"+depth);
                    Pair<Integer,ArrayList<String>> pair = new Pair<>(depth,keywords);
                    tasksExtraction.put("linkedIn", pair);
                }

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLExploredPage.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                FXMLExploredPageController controller = (FXMLExploredPageController) fxmlLoader.getController();
                Stage stage = new Stage();
                stage.setTitle("Explored Page");
                stage.setScene(new Scene(root1));  
                stage.addEventHandler(WindowEvent.WINDOW_SHOWN, new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent window) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                controller.setTasksExtraction(tasksExtraction);
                                controller.run();
//                                controller.createStatistics();
                                //controller.setTaskExtraction(tasksExtraction);
                                //controller.run();
                            }
                        });
                    }
                });
                stage.show();
                System.out.println("Run page closed");
//                controller.setTaskExtraction(tasksExtraction);

                
/*                
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLRunPage.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                FXMLRunPageController controller = (FXMLRunPageController) fxmlLoader.getController();
                Stage stage = new Stage();
                stage.setTitle("RUN PAGE");
                stage.setScene(new Scene(root1));  
                stage.addEventHandler(WindowEvent.WINDOW_SHOWN, new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent window) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                controller.setTaskExtraction(tasksExtraction);
                                controller.run();
                            }
                        });
                    }
                });
                stage.show();
                System.out.println("Run page closed");
//                controller.setTaskExtraction(tasksExtraction);
                */
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.WARNING, 
                "Please specify one keyword at least.", 
                ButtonType.OK);
                alert.show();
            }
       }
       else
       {
           //System.out.println("no social media");
            Alert alert = new Alert(Alert.AlertType.WARNING, 
            "Please specify one social media at least.", 
            ButtonType.OK);
            alert.show();
       }
    }
    @FXML
    public void twitterRadioHandler()
    {
       if(this.twitterSubPaneId.isDisable())
           this.twitterSubPaneId.setDisable(false);
       else
           this.twitterSubPaneId.setDisable(true);
    }
    @FXML
    public void facebookRadioHandler()
    {
       if(this.facebookSubPaneId.isDisable())
           this.facebookSubPaneId.setDisable(false);
       else
           this.facebookSubPaneId.setDisable(true);
        
    }
    @FXML
    public void linkedInRadioHandler()
    {
       if(this.linkedInSubPaneId.isDisable())
           this.linkedInSubPaneId.setDisable(false);
       else
           this.linkedInSubPaneId.setDisable(true);
        
    }
    
}
