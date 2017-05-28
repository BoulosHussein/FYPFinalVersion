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
import graphdrawtest.DrawGraph;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * FXML Controller class
 *
 * @author generals
 */
public class FXMLNewRunPageController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ProgressBar progressId;
    @FXML
    private Label progressLabelId;
    @FXML
    //private TabPane tabPaneId;
    private BorderPane borderpaneId;
    @FXML
    private Pane containerId;
    
    @FXML
    private Button exportId;
    @FXML
    private Button printId;
    @FXML
    private Button actionId;
    @FXML
    private Button showGraphId;

    
    
    private ArrayList<String> tasks = new ArrayList<>();
    //=>a chaque reseau social, son depth et sa liste de keywords;
    private HashMap<String,Pair<Integer,ArrayList<String>>> tasksExtraction = new HashMap<>();
    ArrayList<PieChart.Data> data;
    private ArrayList<Community> communities;
    private HashMap<Integer,ArrayList<Integer>> map = new HashMap<>();
    private HashMap<String,String> communityNameAttribution = new HashMap<>();
    private HashMap<String,ArrayList<Integer>> communityAttribution ;
    private TwitterExtraction extractAtt;
    private LeadersTimeline opinionDetectionAtt;
    //ADDED for export excel
    private ArrayList<Double> rank;
    private HashMap<Integer,Long> inverse;
    ArrayList<String> keywords; //accounts
    Leader_Community leaderC;
    BigClam com_algorithm;
    TwitterExtraction extract;
    ArrayList<String> keywordsOriginal ; //keywords
    
//    public FXMLRunPageController(HashMap<String,Pair<Integer,ArrayList<String>>> tasksExtraction)
//    {
//        this.tasksExtraction = tasksExtraction;
//    }
//    
    public void setMap(HashMap<Integer,ArrayList<Integer>> map)
    {
        this.map = map;
    }
    public void setKeywordsOriginal(ArrayList<String> keywordsOriginal)
    {
            this.keywordsOriginal = keywordsOriginal;
            
    }
    public void setRank(ArrayList<Double> rank)
    {
        this.rank = rank;
    }
    public void setInverse(HashMap<Integer,Long> invers)
    {
        this.inverse = invers;
    }
    public void setKeywords(ArrayList<String> keywords)
    {
        this.keywords = keywords;
    }
    public void setCommunities(ArrayList<Community> communities)
    {
        this.communities = communities;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //this.pieChartId.setTitle("Sondage");
    }    
    public void setTaskExtraction(HashMap<String,Pair<Integer,ArrayList<String>>> tasksExtraction)
    {
        this.tasksExtraction = tasksExtraction;
    }
    public void run()
    {
            for(String reseauSocial: tasksExtraction.keySet()){
//                ExecutionHandler  hndler = new ExecutionHandler(reseauSocial,tasksExtraction.get(reseauSocial).getL(),tasksExtraction.get(reseauSocial).getR(),null,progressBarId);              
                Task <Void> task = new Task<Void>() {
                @Override public Void call() throws InterruptedException {

        /*            TwitterExtraction extract = null;                     
                    double c = 1;
                    double size =5;
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
                            Logger.getLogger(ExecutionHandler.class.getName()).log(Level.SEVERE, null, ex);
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
                    
                    updateMessage("Attribution of leaders to communities");
                    Leader_Community lc = new Leader_Community();
                    lc.attributeLC(com_algorithm.attribution_of_communities,twitter_rank.rankVector);
                    updateProgress(c,size);
                    ++c;
                    leaderC = lc;

                    updateMessage("Detection of communitie's Opinion");
        */
                    //////starting from here the run 
                    updateMessage("Collecting and analysing sentiment of users");
                    int c =1;
                    int size = 2;
                    Stage s = null;
                    LeadersTimeline opinionDetection = new LeadersTimeline(keywordsOriginal,s,leaderC.leaders_Attribution,tasksExtraction.get(reseauSocial).getR(),com_algorithm.keywordIndexName,extract.integrationGraphInvers,extract.list_app,true);
                    opinionDetection.getOpinions(2);
                    updateProgress(c,size);
                    opinionDetectionAtt = opinionDetection;
                                        
                    System.out.println("c="+c);
                    for(String key:opinionDetection.allOpinions.keySet())
                    {
                        System.out.print("Community "+key+": ("+opinionDetection.allOpinions.get(key).get(0)+","+opinionDetection.allOpinions.get(key).get(1)+") | community Size: "+opinionDetection.communityAttribution.get(key).size()+" \n");
                    }
                    System.out.println();
                    for(int k=0;k<opinionDetection.communities.size();++k)
                        opinionDetection.communities.get(k).show();    

                    //getting the reference to ArrayList<Communities>;
                    communities = opinionDetection.communities;
                    ++c;
                    //statistique sur la taille 
    ///                System.out.println();
                    updateMessage("Generating the results");
                    SondageGenerator sgenerator = new SondageGenerator(opinionDetection.communities,opinionDetection.attributionInvers,opinionDetection.keywordIndexName);
                    sgenerator.draw();
                    updateProgress(c,size);
                    //ArrayList<PieChart.Data> data = sgenerator.fxDrawChartData();
                    //data = sgenerator.fxDrawChartData();
//                    //drawPieChart(data);
                    ///pieChartId.getData().addAll(data);
                    return null;
                }
            };
        this.progressLabelId.textProperty().bind(task.messageProperty());
        this.progressId.progressProperty().bind(task.progressProperty());
        
        task.setOnSucceeded(e -> {
                this.containerId.setDisable(false);
                this.progressLabelId.textProperty().unbind();
                // this message will be seen.
                this.progressLabelId.setText("operation completed successfully");
              //  System.out.println("data size:"+data.size());
                //drawPieChart(data);
                drawTabPane();
        });
        
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
        //this.drawPieChart(data);
        //System.out.println("PieChart data:"+this.data.size());
        //updatePieChart(data);
       }      
    }
    public void drawTabPane()
    {
        //i will create tabs as much as we have keywordsOrigin;
        //in each tab i will create a BarChart to show the opinion of each comm;
        HashMap<Integer,String> keywordIndex = opinionDetectionAtt.keywordIndexName;
        ArrayList<String> keywords = opinionDetectionAtt.keywords;
        //pour chaque keyword on cree un tab
//            Tab tab = new Tab(""+keywordIndex.get(index));
            //create datas for our barChart;
            
            ///////table
            
        TableView<ObservableList<String>>  tableView = new TableView();
        tableView.setColumnResizePolicy(new Callback<TableView.ResizeFeatures,Boolean>(){

            @Override
            public Boolean call(TableView.ResizeFeatures param) {
                return true;
            }
        });
//        tableView.setMaxSize(600,400);
//        tableView.setPrefSize(600, 400);
//        tableView.setMinSize(600, 400);
        tableView.getStylesheets().addAll(getClass().getResource("opinionTableCss.css").toExternalForm());
        
//        int size = 10;
        int size = 1+keywordIndex.size(); // 1+ nmbrdeKeyword colonnes l'une vide , l'autre pour contenir le keyword 
//        if(rank.size()<10)
//           size = rank.size();

        ArrayList<ArrayList<String>> data = new ArrayList<>();
        
        ArrayList<String> headers = new ArrayList<>();
        headers.add("Communities");
        for(Integer index: keywordIndex.keySet())
        {
            headers.add("Keyword "+keywordIndex.get(index));
        }
        

//        for(int i=1;i<size+1;++i)
//            headers.add("Leader"+i);
        data.add(data.size(),headers);
        
        for(Community com: communities)
        {
            ArrayList<String> opinionList = com.opinions;
            ArrayList<String> marks = new ArrayList<>();
            marks.add(this.communityNameAttribution.get(com.name));
            for(Integer index: keywordIndex.keySet())
            {
                String opinion = opinionList.get(index);
            
                marks.add(opinion);

            }
                data.add(data.size(),marks);            
        }

        ObservableList<ObservableList<String>> dataObservable = FXCollections.observableArrayList();
        for(int i=0;i<data.size();++i)
        {
            dataObservable.add(FXCollections.observableArrayList(data.get(i)));
        }
        tableView.setItems(dataObservable);
        
        for(int i=0;i<data.get(0).size();++i)
        {
            final int curCol = i;
            final TableColumn<ObservableList<String>,String> column = new TableColumn<>(
                    "Col " + (curCol+1)
            );
            column.setCellValueFactory(
                    param -> new ReadOnlyObjectWrapper<>(param.getValue().get(curCol))
            );
            tableView.getColumns().add(column);
        }
      
//        Label tableTitle = new Label("TABLE TITLE");
//        tableTitle.setLayoutX(384);
  //      this.vboxId.getChildren().add(tableTitle);

//        this.vboxId.getChildren().add(tableView);        
         //   tab.setContent(tableView);
            
            //this.tabPaneId.getTabs().add(tab);
          //  this.paneId.getTabs().add(tab);
            this.borderpaneId.setCenter(tableView);
            
    }
    @FXML
    public void exportHandle() throws IOException, BiffException, WriteException
    {
        Stage stage = new Stage();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(stage);
        
        String path = selectedDirectory.getAbsolutePath();
        //create the destination new document
        String name="";
       
        
        for(int i = 0;i<keywords.size();++i){
            name=name+"_"+keywords.get(i);
        }
        
        path=path+"/"+"Analytics"+name+".xls";
        
        WritableWorkbook newDoc = Workbook.createWorkbook(new File(path));
        //Create new excel sheet
        WritableSheet writablesheet = newDoc.createSheet("sheet" , 0);
        File excel = new File(path);
        WritableCellFormat cellFormat = new WritableCellFormat(new WritableFont(WritableFont.ARIAL,8));
        
        jxl.write.Label g = new jxl.write.Label(0,0,"Graph Size : ",cellFormat);
        jxl.write.Label gSize = new jxl.write.Label(1,0,Integer.toString(inverse.size()),cellFormat);
        try{
            writablesheet.addCell(g);
            writablesheet.addCell(gSize);
        }catch(WriteException e){}
        int rowPos=2;
        for(String key: this.leaderC.leaders_Attribution.keySet()){
            jxl.write.Label comm = new jxl.write.Label(0,rowPos,key,cellFormat);
            try{
                writablesheet.addCell(comm);
            }
            catch(WriteException e)
            {
            
            }
            for(int i=0;i<this.leaderC.leaders_Attribution.get(key).size();++i){
                long a = inverse.get(this.leaderC.leaders_Attribution.get(key).get(i));
                jxl.write.Label l = new jxl.write.Label(0,rowPos+i+1,Long.toString(a),cellFormat);
//                System.out.println("rank size:"+rank.size());
//                System.out.println("rank.get(i): "+ rank.get(6));
//                System.out.println("\n \ntesting leaderC.leaders_Attribution.get(key): "+leaderC.leaders_Attribution.get(key).get(i));
                jxl.write.Label l1 = new jxl.write.Label(1,rowPos+i+1,Double.toString(rank.get(this.leaderC.leaders_Attribution.get(key).get(i))),cellFormat);
                try{
                    writablesheet.addCell(l);
                    writablesheet.addCell(l1);
                }catch(WriteException e){}
            }
            rowPos = rowPos+1;
        }
        newDoc.write();
        newDoc.close();
    }
    @FXML
    public void printHandle() throws IOException
    {
     /*   FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLExploredPage.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        
        FXMLExploredPageController controller = (FXMLExploredPageController) fxmlLoader.getController();
      
        System.out.println("\n size of communities"+communities.size());
        Stage stage = new Stage();
        stage.setTitle("Actions");
        stage.setScene(new Scene(root1));  
        stage.show();
        System.out.println("Actions closed");
    */
    
    }
    
    @FXML
    public void actionHandle() throws IOException
    {            
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainActions.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        
        MainActionsController controller = (MainActionsController) fxmlLoader.getController();
        controller.setCommunities(communities);
        controller.setLeadersTimeline(opinionDetectionAtt);
        controller.setTwitterExtraction(extract);
        controller.setCommunityNameAttribution(this.communityNameAttribution);
        System.out.println("\n size of communities"+communities.size());
        Stage stage = new Stage();
        stage.setTitle("Actions");
        stage.setScene(new Scene(root1));  
        stage.show();
        System.out.println("Actions closed");
    }
    @FXML
    public void showGraphHandle()
    {
        //DrawGraph graphDrawer = new DrawGraph(this.map,this.communities);
        DrawGraph graphDrawer = new DrawGraph(this.map,this.com_algorithm.attribution_of_communites_showGraph);
    }
    public void drawPieChart(ArrayList<PieChart.Data> data)
    {
    //    runButtonId.setVisible(false);
    //    this.keywordID.setDisable(true);

//        String red = "red";
//        String blue = "blue";
//        String orange = "orange";
//        String green = "green";
//        ArrayList<String> colors = new ArrayList<>();
//        colors.add(red);
//        colors.add(blue);
//        colors.add(orange);
//        colors.add(green);
//        
//        for(int i=0;i<data.size();++i)
//        {
//            //System.out.println("data:"+data.get(0).getNode().getStyle());
//            data.get(i).getNode().setStyle("-fx-pie-color: "+colors.get(i)+";");
//            data.get(i).getNode().setStyle(blue);
//        }
        
 /*       this.pieChartId.getData().addAll(data);
        data.forEach(slice ->
                slice.nameProperty().bind(
                        Bindings.concat(" ",
                                communityNameAttribution.get(slice.getName())," ", " : ", slice.pieValueProperty(),"%"
                        )
                )
            );
   */     for(int i=0;i<data.size();++i)
        {
            System.out.println("data "+i+" :"+data.get(i).getNode().getStyle());
        }
        
        for(int i=0;i<communities.size();++i)
        {
            System.out.println("data "+i+" :"+communities.get(i).name);
            for(int j=0;j<communities.get(i).members.size();++j)
                System.out.print(" : "+communities.get(i).members.get(j));
        }        
    }

    public void setBigClam(BigClam b)
    {
        this.com_algorithm = b;
    }
    public void setTwitterExtraction(TwitterExtraction t)
    {
        this.extract = t;
    }
    public void setLeaderCommunity(Leader_Community leaderC)
    {
        this.leaderC = leaderC;
    }
    public void setCommunityNameAttribution(HashMap<String,String> communityNameAttribution)
    {
        this.communityNameAttribution = communityNameAttribution;
    }    
}
/*

            
            
            final NumberAxis xAxis = new NumberAxis();
            final CategoryAxis yAxis = new CategoryAxis();
            // String styleSheetURL = "javafxapplication3.chartPie.css";

            final BarChart<Number,String> bc = new BarChart<Number,String>(xAxis,yAxis);

         //       bc.getStylesheets().add((new File(styleSheetURL)).toURI().toURL().toExternalForm());            

            bc.setLegendVisible(true);

            bc.setMaxSize(600, 400);
            bc.setPrefSize(600, 400);
            bc.setMinSize(600, 400);

            bc.setTitle("Opinions of Communities");
            xAxis.setLabel("Community");       
            yAxis.setLabel("Opinion");

            for(Community com: communities)
            {
                //HashMap<String,HashMap<String,Double>> car = com.caracteristiques;
                //HashMap<String,Double> location = car.get("Location");//se rassurer de Location;
                ArrayList<String> opinionList = com.opinions;
                String opinion = opinionList.get(index);
                
                XYChart.Series seriesCom = new XYChart.Series();
                seriesCom.setName(com.name);
                System.out.println("\n affichage des opinions de:"+com.name+ "\n");
//                for(String val: location.keySet())
//                {
//                    System.out.print(val + " :"+location.get(val)+"  , ");                
//                    seriesCom.getData().add(new XYChart.Data(val, location.get(val)));            
//                }
                int nmbr = 0;
                if(opinion.equals("positive"))
                    nmbr =1;
                if(opinion.equals("negative"))
                    nmbr = -1;
                if(opinion.equals("neutral"))
                    nmbr = 0;
                if(opinion.equals(""))
                    nmbr = -10;
                seriesCom.getData().add(new XYChart.Data(0,opinion));
                bc.getData().addAll(seriesCom);    
            }
            tab.setContent(bc);
            this.tabPaneId.getTabs().add(tab);
        }   
*/

/*

   public void drawTabPane()
    {
        //i will create tabs as much as we have keywordsOrigin;
        //in each tab i will create a BarChart to show the opinion of each comm;
        HashMap<Integer,String> keywordIndex = opinionDetectionAtt.keywordIndexName;
        ArrayList<String> keywords = opinionDetectionAtt.keywords;
        //pour chaque keyword on cree un tab
        for(Integer index: keywordIndex.keySet())
        {
            Tab tab = new Tab(""+keywordIndex.get(index));
            //create datas for our barChart;
            
            ///////table
            
        TableView<ObservableList<String>>  tableView = new TableView();
        tableView.setColumnResizePolicy(new Callback<TableView.ResizeFeatures,Boolean>(){

            @Override
            public Boolean call(TableView.ResizeFeatures param) {
                return true;
            }
        });
//        tableView.setMaxSize(600,400);
//        tableView.setPrefSize(600, 400);
//        tableView.setMinSize(600, 400);
        tableView.getStylesheets().addAll(getClass().getResource("chartPie.css").toExternalForm());
        
//        int size = 10;
        int size = 2; // 2 colonnes l'une vide , l'autre pour contenir le keyword 
//        if(rank.size()<10)
//           size = rank.size();

        ArrayList<ArrayList<String>> data = new ArrayList<>();
        
        ArrayList<String> headers = new ArrayList<>();
        headers.add("");
        headers.add("Keyword "+keywordIndex.get(index));
//        for(int i=1;i<size+1;++i)
//            headers.add("Leader"+i);
        data.add(data.size(),headers);
        
        for(Community com: communities)
        {
            ArrayList<String> opinionList = com.opinions;
            String opinion = opinionList.get(index);
            ArrayList<String> marks = new ArrayList<>();
            marks.add("community: "+this.communityNameAttribution.get(com.name));
            marks.add(opinion);
            data.add(data.size(),marks);
        }

        ObservableList<ObservableList<String>> dataObservable = FXCollections.observableArrayList();
        for(int i=0;i<data.size();++i)
        {
            dataObservable.add(FXCollections.observableArrayList(data.get(i)));
        }
        tableView.setItems(dataObservable);
        
        for(int i=0;i<data.get(0).size();++i)
        {
            final int curCol = i;
            final TableColumn<ObservableList<String>,String> column = new TableColumn<>(
                    "Col " + (curCol+1)
            );
            column.setCellValueFactory(
                    param -> new ReadOnlyObjectWrapper<>(param.getValue().get(curCol))
            );
            tableView.getColumns().add(column);
        }
      
//        Label tableTitle = new Label("TABLE TITLE");
//        tableTitle.setLayoutX(384);
  //      this.vboxId.getChildren().add(tableTitle);

//        this.vboxId.getChildren().add(tableView);        
            tab.setContent(tableView);
            
            //this.tabPaneId.getTabs().add(tab);
            this.paneId.getTabs().add(tab);
        }
    }

*/