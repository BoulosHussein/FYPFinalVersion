/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FYPpackage.Caracteristics;

import FYPpackage.LeadersCommunityTimeline.Community;

import static java.awt.Component.TOP_ALIGNMENT;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import static javafx.scene.input.DataFormat.URL;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.util.Rotation;


/**
 *
 * @author generals
 */
public class SondageGenerator {

    ArrayList<Community> communities = new ArrayList<>();
    HashMap<Integer,Long> attributionInvers = new HashMap<>();
    HashMap<Integer,String>keywordIndexName = new HashMap<>();
    //save the user infos when im doing lookup;
    HashMap<String,Sondage> communitySondage = new HashMap<>();
   
    
    public SondageGenerator(ArrayList<Community> communities,HashMap<Integer,Long> attributionInvers,HashMap<Integer,String> keywordIndexName)
    {
        this.communities = communities;
        this.attributionInvers = attributionInvers;
        this.keywordIndexName = keywordIndexName;
        getSizeOfCommunity();
    }
    public SondageGenerator()
    {
    }
    
    private void getSizeOfCommunity()
    {
        double value;
        double pourcent;
        for(int i=0; i<communities.size();++i)
        {
            Community c = communities.get(i);
            value = (double)c.members.size();
            pourcent = (value/attributionInvers.size())*100;
            Sondage sdg = new Sondage(pourcent,value);
            this.communitySondage.put(c.name, sdg);
        }
    }
    
    public void draw()
    {
      for(String comm : communitySondage.keySet())
      {
          System.out.println("community:"+comm+" | size:"+communitySondage.get(comm).nombre+" | pourcentage:"+communitySondage.get(comm).pourcent+"%");
      }
    }
    
    public ArrayList<PieChart.Data> fxDrawChartData()
    {

        ArrayList<PieChart.Data> data = new ArrayList<>();
        for(String comm:communitySondage.keySet())
        {
            PieChart.Data slice = new PieChart.Data(comm, communitySondage.get(comm).pourcent);
            data.add(slice);
        }
        return data;
        //PieChart.Data slice1 = new PieChart.Data("Desktop", 213);
        //PieChart.Data slice2 = new PieChart.Data("Phone", 67);
        //PieChart.Data slice3 = new PieChart.Data("Tablet", 36);
        //this.controller.drawPieChart(data);
//        this.pieChartId.getData().addAll(data);
        //PieChart  p = this.controller.getPieChart();
        //this.controller.getPieChart().getData().addAll(data);
        
        //this.pieChartId.getData().addAll(slice1,slice2,slice3);
    }
    public void chartPieDraw()
    {
        DefaultPieDataset pieDataset = new DefaultPieDataset();
//        pieDataset.setValue("One", new Integer(10));
//        pieDataset.setValue("Two", new Integer(20));
//        pieDataset.setValue("Three", new Integer(30));
//        pieDataset.setValue("Four",new Integer(40));
        this.communities.add(new Community("A",new ArrayList<>(),new ArrayList<>()));
        this.communities.add(new Community("B",new ArrayList<>(),new ArrayList<>()));
        this.communities.add(new Community("C",new ArrayList<>(),new ArrayList<>()));
        this.communities.add(new Community("D",new ArrayList<>(),new ArrayList<>()));
        communitySondage.put("A", new Sondage(10,10));
        communitySondage.put("B", new Sondage(30,30));
        communitySondage.put("C", new Sondage(40,40));
        communitySondage.put("D", new Sondage(20,20));
        for(int i=0;i<communities.size();++i)
        {
            pieDataset.setValue(communities.get(i).name, communitySondage.get(communities.get(i).name).pourcent);
        }
        JFreeChart chart = ChartFactory.createPieChart("Pile Chart", pieDataset, true, true, true);
        PiePlot p = (PiePlot) chart.getPlot();
        //p.setForegroundAlpha(TOP_ALIGNMENT);
        ChartFrame frame = new ChartFrame("Pile Chart",chart);
        frame.setVisible(true);
        frame.setSize(450,500);
    }
}
