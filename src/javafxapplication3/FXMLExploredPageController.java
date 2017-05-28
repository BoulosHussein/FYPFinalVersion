/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication3;

import FYPpackage.Caracteristics.SondageGenerator;
import FYPpackage.CommunityDetection.BigClam;
import FYPpackage.DataExtraction.Pair;
import FYPpackage.DataExtraction.TwitterExtraction;
import FYPpackage.LeadersCommunityTimeline.Community;
import FYPpackage.LeadersCommunityTimeline.Leader_Community;
import FYPpackage.LeadersCommunityTimeline.LeadersTimeline;
import FYPpackage.leaders_detection.PageRank;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author generals
 */
public class FXMLExploredPageController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    VBox vboxId;
    @FXML
    ScrollPane scrollId;
    @FXML
    ProgressBar progressId;
    @FXML
    Label progressLabelId;
    @FXML
    Button advancedButtonId;


    //=>a chaque reseau social, son depth et sa liste de keywords;
    private HashMap<String,Pair<Integer,ArrayList<String>>> tasksExtraction = new HashMap<>();
    private ArrayList<Community> communities;
    private HashMap<Integer,ArrayList<Integer>> map = new HashMap<>();
    private HashMap<String,String> communityNameAttribution = new HashMap<>();
    private HashMap<String,ArrayList<Integer>> communityAttribution ;
    private TwitterExtraction extractAtt;
    private LeadersTimeline opinionDetectionAtt;
    //ADDED for export excel
    private ArrayList<Double> rank;
    private HashMap<Integer,Long> inverse;
    ArrayList<String> keywords;
    Leader_Community leaderC;
    BigClam bigClam;
    HashMap<Integer,Double> rankedLeaders;
    
    public void setTasksExtraction(HashMap<String,Pair<Integer,ArrayList<String>>> tasksExtraction )
    {
        this.tasksExtraction = tasksExtraction;
    }
    public void run()
    {
            for(String reseauSocial: tasksExtraction.keySet()){
                Task <Void> task = new Task<Void>() {
                @Override public Void call() throws InterruptedException {

                    TwitterExtraction extract = null;                     
                    double c = 1;
                    double size =4;
                    if(reseauSocial.equals("twitter"))
                    {
                        updateMessage("Data extraction");
                        extract = new TwitterExtraction (tasksExtraction.get(reseauSocial).getR(),""+tasksExtraction.get(reseauSocial).getL());                
                        updateProgress(c,size);
                        ++c;
                    }
                    else
                    {
                        try {
                            throw new Exception("Other social media are not supported yet!");
                        } catch (Exception ex) {
                            
                        }
                    }
                    extractAtt = extract;
                    inverse = extract.integrationGraphInvers;
                    keywords = extract.keywords_list;
                    
                    updateMessage("Leader's detection");
                    PageRank twitter_rank = new PageRank(extract.integrationGraph.size());
                    twitter_rank.fillIn(extract.list_followers, extract.integrationGraph);
                    updateProgress(c,size);
                    ++c;
                    
                    map = twitter_rank.map; //needed to show Graph when show graph is pressed;
                    rank = twitter_rank.rankVector;
                    twitter_rank.rankedLeaders();
                    rankedLeaders = twitter_rank.rankedLeaders; 
                    
                    updateMessage("Community Detection");
                    BigClam com_algorithm = new BigClam(extract.runnableThread,twitter_rank.map,extract.runnableThread.size(),extract.integrationGraph,extract.integrationGraphInvers,extract.list_app);
                    System.out.println("showing initial Matrix of appartenance");
                    com_algorithm.showAppartenance1();
                    com_algorithm.findCommunities(0.01,0.00001);                
                    com_algorithm.communityAttribution(0.1);
                    System.out.println("showing Matrix of appartenance after community detection");
                    com_algorithm.showAppartenance1();//WE NEED THE FIRST SHOW APPARTENANCE, BEFORE THE SCND, BCZ IN THE 1RST WE ARE GETTING THE NAME OF EACH ACCOUNT
                    com_algorithm.showAppartenance2();
                    System.out.println("Here is the attribution of communities index ");
                    for(Integer key:com_algorithm.keywordIndexName.keySet())
                    {
                        System.out.print(""+key+": "+com_algorithm.keywordIndexName.get(key)+"\n");
                    }
                    updateProgress(c,size);
                    ++c;
                    communityAttribution = com_algorithm.attribution_of_communities;
                    communityNameAttribution = com_algorithm.attribution_communities_names; //needed to show in the pieChart he name of community not their equivalence;
                    bigClam = com_algorithm;
                    
                    updateMessage("Attribution of leaders to communities");
                    Leader_Community lc = new Leader_Community();
                    lc.attributeLC(com_algorithm.attribution_of_communities,twitter_rank.rankVector);
                    updateProgress(c,size);
                    ++c;
                    leaderC = lc;
                    return null;
                }
            };
        progressLabelId.textProperty().bind(task.messageProperty());
        progressId.progressProperty().bind(task.progressProperty());
        
        task.setOnSucceeded(e -> {
                this.scrollId.setDisable(false);
                this.advancedButtonId.setDisable(false);
                
                progressLabelId.textProperty().unbind();
                // this message will be seen.
                progressLabelId.setText("operation completed successfully");
                //System.out.println("data size:"+data.size());
                //drawPieChart(data);
                createStatistics();
        });
        
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
        //this.drawPieChart(data);
        //System.out.println("PieChart data:"+this.data.size());
        //updatePieChart(data);
       }      
    }

    public void createStatistics()
    {
         //
        
        TableView<ObservableList<VBox>>  tableView = new TableView();        
        tableView.setColumnResizePolicy(new Callback<TableView.ResizeFeatures,Boolean>(){

            @Override
            public Boolean call(TableView.ResizeFeatures param) {
                return true;
            }
        });
        int height = communityAttribution.size()*38;
        tableView.setMaxSize(650,height);
        tableView.setPrefSize(650, height);
        tableView.setMinSize(650, height);
        tableView.getStylesheets().add(getClass().getResource("chartPie.css").toExternalForm());
        
        int size = 10;
        if(rank.size()<10)
           size = rank.size();

        ArrayList<ArrayList<VBox>> data = new ArrayList<>();
        ArrayList<VBox> headers = new ArrayList<>();
        VBox v = new VBox();
        v.getChildren().add(new Label("Communities"));
        headers.add(v);
        for(int i=1;i<size+1;++i){
            VBox vb = new VBox();
            vb.getChildren().add(new Label("Leader"+i));
            headers.add(vb);
        }
        data.add(data.size(),headers);
        HashMap<Integer,Double> top10 = new HashMap<>();
        int count = 1;
        
        for(Integer key: this.rankedLeaders.keySet())
        {
            if(count>10)
                break;
            top10.put(key, this.rankedLeaders.get(key));
            ++count;
        }
        
        for(String comm : communityAttribution.keySet())
        {        
            ArrayList<Integer> leaders = leaderC.leaders_Attribution.get(comm);
            ArrayList<VBox> marks = new ArrayList<>();
            VBox vbx = new VBox();
            vbx.getChildren().add(new Label(communityNameAttribution.get(comm)));
            marks.add(vbx);
            for(int i=1;i<size+1;++i)
            {
                if(commContainsTop10(top10,i,leaders))
                {
                    VBox vbox = new VBox();
                    Image image = new Image(getClass().getResource("checkSmallBlack.png").toExternalForm());
                    ImageView iView = new ImageView(image);
                    iView.setFitHeight(15);
                    iView.setFitWidth(15);
                    vbox.getChildren().add(iView);
                    marks.add(vbox);
                }
                else
                {
                    //marks.add("-");
                    VBox vbox = new VBox();
                    vbox.getChildren().add(new Label("-"));
                    marks.add(vbox);
                }
            }
            data.add(data.size(),marks);
        }

    //  ObservableList<ObservableList<String>> dataObservable = FXCollections.observableArrayList();
        ObservableList<ObservableList<VBox>> dataObservable = FXCollections.observableArrayList();
        for(int i=0;i<data.size();++i)
        {
            dataObservable.add(FXCollections.observableArrayList(data.get(i)));
        }
        tableView.setItems(dataObservable);
        
        for(int i=0;i<data.get(0).size();++i)
        {
            final int curCol = i;
//            final TableColumn<ObservableList<String>,String> column = new TableColumn<>(
            final TableColumn<ObservableList<VBox>,VBox> column = new TableColumn<>(
                    "Col " + (curCol+1)
            );
            column.setCellValueFactory(
                    param -> new ReadOnlyObjectWrapper<>(param.getValue().get(curCol))
            );
            tableView.getColumns().add(column);
        }
      
        Label tableTitle = new Label("                                      Representing membership of top"+size+" leaders"); //pour centrer le label
        tableTitle.setLayoutX(384);
        //tableTitle.setStyle("-fx-font-weight: bold");
        tableTitle.setFont(new Font("Arial",17));
        tableTitle.setTextAlignment(TextAlignment.CENTER);
        this.vboxId.getChildren().add(tableTitle);

        this.vboxId.getChildren().add(tableView);        

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
       
        final BarChart<String,Number> bc = new BarChart<String,Number>(xAxis,yAxis);
        
        bc.setLegendVisible(true);
        
        bc.setMaxSize(650, 400);
        bc.setPrefSize(650, 400);
        bc.setMinSize(650, 400);

        bc.setTitle("Size of the different Communities");
        xAxis.setLabel("Communities");       
        yAxis.setLabel("size");

        for(String com: communityAttribution.keySet())
        {
           
            ArrayList<Integer> members = communityAttribution.get(com);
            
            XYChart.Series seriesCom = new XYChart.Series();
            seriesCom.setName(this.communityNameAttribution.get(com));
            seriesCom.getData().add(new XYChart.Data(communityNameAttribution.get(com), communityAttribution.get(com).size()));      
            bc.getData().addAll(seriesCom);    
        }

        this.vboxId.getChildren().add(bc);
        
       
        
    }
    private boolean commContainsTop10(HashMap<Integer,Double> top10,int i,ArrayList<Integer> leadersOf)
    {
        int count =1;
        int keyTarget=0;
        for(Integer key:    top10.keySet())
        {
            if(count ==i){                
                keyTarget = key;
                break;
            }
            ++count;
        }
        if(leadersOf.contains(keyTarget))
            return true;
        else
            return false;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    public void initialiseStatistics()
    {
        
    }

    public void advancedButtonHandler() throws IOException
    {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLKeywordsPage.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                FXMLKeywordsPageController controller = (FXMLKeywordsPageController) fxmlLoader.getController();
                controller.setBigClam(this.bigClam);
                controller.setTaskExtraction(tasksExtraction);
                controller.setTwitterExtraction(extractAtt);
                controller.setInvers(this.inverse);
                controller.setKeywords(this.keywords);
                controller.setRank(this.rank);
                controller.setLeaderCommunity(leaderC);
                controller.setCommunityNameAttribution(communityNameAttribution);
                controller.setMap(this.map);
                //controller.runFirstStep();
                
                Stage stage = new Stage();
                stage.setTitle("Run Page");
                stage.setScene(new Scene(root1));  
                stage.show();
                System.out.println("Run page closed");

        
    }
}
