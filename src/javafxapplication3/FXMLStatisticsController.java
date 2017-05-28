/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication3;

import FYPpackage.LeadersCommunityTimeline.Community;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author generals
 */
public class FXMLStatisticsController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML 
    private ScrollPane scrollId;
    @FXML
    private VBox vboxId;
    public ArrayList<Community> communities = new ArrayList<>();
    private HashMap<String,String> communityNameAttribution = new HashMap<>();
    public void setCommunityNameAttribution(HashMap<String,String> communityNameAttribution)
    {
        this.communityNameAttribution = communityNameAttribution;
    }
    public void setCommunities(ArrayList<Community> communities)
    {
        this.communities = communities;
    }
    public void chartsConstruction() throws MalformedURLException
    {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
       // String styleSheetURL = "javafxapplication3.chartPie.css";

        final BarChart<String,Number> bc = new BarChart<String,Number>(xAxis,yAxis);
     //   bc.getStylesheets().addAll(getClass().getResource("chartPie.css").toExternalForm());
        
 //       bc.getStylesheets().add((new File(styleSheetURL)).toURI().toURL().toExternalForm());            
        
        bc.setLegendVisible(true);
        
        bc.setMaxSize(700, 400);
        bc.setPrefSize(700, 400);
        bc.setMinSize(700, 400);

        bc.setTitle("Location Summary");
        xAxis.setLabel("Country");       
        yAxis.setLabel("number of users");

        for(Community com: communities)
        {
            HashMap<String,HashMap<String,Double>> car = com.caracteristiques;
            HashMap<String,Double> location = car.get("Location");//se rassurer de Location;

            XYChart.Series seriesCom = new XYChart.Series();
            seriesCom.setName(communityNameAttribution.get(com.name));
            System.out.println("\n affichage des locations de:"+com.name+ "\n");
            System.out.println("\n affichage des locations de:"+communityNameAttribution.get(com.name)+ "\n");
            for(String val: location.keySet())
            {
                System.out.print(val + " :"+location.get(val)+"  , ");                
                seriesCom.getData().add(new XYChart.Data(val, location.get(val)));            
            }

            bc.getData().addAll(seriesCom);    
        }
        //bc.setStyle(styleSheetURL);
        //bc.applyCss();
        this.vboxId.getChildren().add(bc);
        
        /////////////////////////Le deuxieme graph
        
        final CategoryAxis xAxisLine = new CategoryAxis();
        final NumberAxis yAxisLine = new NumberAxis();
        xAxisLine.setLabel("Languages");       
        yAxisLine.setLabel("number of users");
        final LineChart<String,Number> lineChart = new LineChart<String,Number>(xAxisLine,yAxisLine);
       // lineChart.getStylesheets().addAll(getClass().getResource("chartPie.css").toExternalForm());
        lineChart.setLegendVisible(true);

        lineChart.setMaxSize(700, 400);
        lineChart.setPrefSize(700, 400);
        lineChart.setMinSize(700, 400);

        lineChart.setTitle("Language summary");
        for(Community com :communities)
        {
            HashMap<String,HashMap<String,Double>> carLang = com.caracteristiques;
            HashMap<String,Double> lang = carLang.get("Lang");
            XYChart.Series series = new XYChart.Series();
            series.setName(communityNameAttribution.get(com.name));
            System.out.println("\n affichage des languages de:"+com.name+ "\n");
            for(String val:lang.keySet())
            {
               System.out.print(val + " :"+lang.get(val)+"  , ");                
               series.getData().add(new XYChart.Data(val, lang.get(val)));
            }
            lineChart.getData().add(series);        
        }
        this.vboxId.getChildren().add(lineChart);

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {


    }    
    
}
