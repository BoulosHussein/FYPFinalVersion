package graphdrawtest;

import FYPpackage.LeadersCommunityTimeline.Community;
import java.awt.*;
import javax.swing.*;
import org.jgrapht.*;
import org.jgrapht.ext.*;
import org.jgrapht.graph.*;
import com.mxgraph.layout.*;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxICell;
import com.mxgraph.swing.*;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource.mxIEventListener;
import com.mxgraph.view.mxGraphSelectionModel;
import com.mxgraph.view.mxStylesheet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Random;


public class DrawGraph extends JApplet {
    
    private static final long serialVersionUID = 2202072534703043194L;
    static Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
    private static final Dimension DEFAULT_SIZE = new Dimension((int)screensize.getWidth(), (int)screensize.getHeight());
    private JGraphXAdapter<String, DefaultEdge> jgxAdapter;
    HashMap<Integer,ArrayList<Integer>> graph = new HashMap<>();
    HashMap<String,ArrayList<Integer>> communities = new HashMap<>();
    
    public DrawGraph(HashMap<Integer,ArrayList<Integer>> g,HashMap<String,ArrayList<Integer>> comm){
        graph.putAll(g);
        communities.putAll(comm);
        init();
        JFrame frame = new JFrame();
        frame.getContentPane().add(this);
        frame.setTitle("JGraphT Adapter to JGraph Demo");
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(screensize);
        frame.setVisible(true);
    }
    
    @Override
    public void init(){
        
        ListenableGraph<String, DefaultEdge> g = new ListenableDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
        
        jgxAdapter = new JGraphXAdapter<>(g);
        
        HashMap<String, Object> style = new HashMap<String, Object>();
        style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
        
        HashMap<String, Object> styleEdge = new HashMap<String, Object>();
        styleEdge.put(mxConstants.STYLE_NOLABEL,true);
        
        
        jgxAdapter.getStylesheet().getDefaultVertexStyle().putAll(style);
        jgxAdapter.getStylesheet().getDefaultEdgeStyle().putAll(styleEdge);
       
       
        
        jgxAdapter.getSelectionModel().addListener(mxEvent.CHANGE, new mxIEventListener() {
            @Override
            public void invoke(Object sender, mxEventObject evt) {
                mxGraphSelectionModel sm = (mxGraphSelectionModel) sender;
                mxCell cell = (mxCell) sm.getCell();
                if (cell != null && cell.isVertex()) {
                    JOptionPane.showMessageDialog(null, "Hello");
                }
            }
        });
        

        getContentPane().add(new mxGraphComponent(jgxAdapter));
        resize(DEFAULT_SIZE);
        
        HashMap<Integer,String> nodeVertex = new HashMap<>();
        
//        int k = 0 ;
//        String s ="s";
//        for(Integer key : graph.keySet()){
//            if(!nodeVertex.containsKey(key)){
//                nodeVertex.put(key, s+k);
//                k++;
//                s="s";
//            }
//            for(int j=0;j<graph.get(key).size();++j){
//                if(!nodeVertex.containsKey(graph.get(key).get(j))){
//                    nodeVertex.put(graph.get(key).get(j), s+k);
//                    k++;
//                    s="s";
//                }
//            }
//        }
        String s ="s";
        for(Integer key:graph.keySet())
        {
            if(!nodeVertex.containsKey(key)){
                nodeVertex.put(key, s+key);
            }
            for(int j=0;j<graph.get(key).size();++j)
            {
                if(!nodeVertex.containsKey(graph.get(key).get(j)))
                {
                    nodeVertex.put(graph.get(key).get(j), s+graph.get(key).get(j));
                }
            }
        }
        
        for(int i=0;i<nodeVertex.size();++i){
            g.addVertex(nodeVertex.get(i));
            mxICell c = new mxCell(nodeVertex.get(i));
            jgxAdapter.addCell(c);
        }
        
        HashMap<String , ArrayList<String>> vertexVertex = new HashMap<>();
        for(Integer key : graph.keySet()){
            ArrayList<Integer> temp = new ArrayList<>();
            temp = graph.get(key);
            ArrayList<String> followers = new ArrayList<>();
            for(int l=0;l<graph.get(key).size();++l){
                //int a = temp.get(l);
                followers.add(nodeVertex.get(graph.get(key).get(l)));
            }
            vertexVertex.put(nodeVertex.get(key), followers);
        }
        
        for(String key : vertexVertex.keySet()){
            for(int i=0;i<vertexVertex.get(key).size();++i){
                g.addEdge(vertexVertex.get(key).get(i), key);
            }
        }
        
        HashMap<String,ArrayList<String>> commVertex = new HashMap<>();
        for(String key: communities.keySet()){
            ArrayList<Integer> temp = new ArrayList<>();
            temp = communities.get(key);
            ArrayList<String> members = new ArrayList<>();
            for(int l=0;l<communities.get(key).size();++l){
                members.add(nodeVertex.get(communities.get(key).get(l)));
            }
            commVertex.put(key, members);
        }
        
        HashMap<String,mxICell> vertexToCell = jgxAdapter.getVertexToCellMap(); 
        
        mxStylesheet stylesheet = jgxAdapter.getStylesheet();
        
        Hashtable<String, Object> style1 = new Hashtable<String, Object>();
        style1.put(mxConstants.STYLE_FILLCOLOR,"red");
        
        Hashtable<String, Object> style2 = new Hashtable<String, Object>();
        style2.put(mxConstants.STYLE_FILLCOLOR,"orange");
        
        Hashtable<String, Object> style3 = new Hashtable<String, Object>();
        style3.put(mxConstants.STYLE_FILLCOLOR,"yellow");
        
        Hashtable<String, Object> style4 = new Hashtable<String, Object>();
        style4.put(mxConstants.STYLE_FILLCOLOR,"black");
        
        Hashtable<String, Object> style5 = new Hashtable<String, Object>();
        style5.put(mxConstants.STYLE_FILLCOLOR,"blue");
        
        Hashtable<String, Object> style6 = new Hashtable<String, Object>();
        style6.put(mxConstants.STYLE_FILLCOLOR,"cyan");
        
        Hashtable<String, Object> style7 = new Hashtable<String, Object>();
        style7.put(mxConstants.STYLE_FILLCOLOR,"darkGray");
        
        Hashtable<String, Object> style8 = new Hashtable<String, Object>();
        style8.put(mxConstants.STYLE_FILLCOLOR,"gray");
        
        Hashtable<String, Object> style9 = new Hashtable<String, Object>();
        style9.put(mxConstants.STYLE_FILLCOLOR,"green");
        
        Hashtable<String, Object> style10 = new Hashtable<String, Object>();
        style10.put(mxConstants.STYLE_FILLCOLOR,"magenta");
        
        Hashtable<String, Object> style11 = new Hashtable<String, Object>();
        style11.put(mxConstants.STYLE_FILLCOLOR,"pink");
       
        
        stylesheet.putCellStyle("red", style1);
        stylesheet.putCellStyle("orange", style2);
        stylesheet.putCellStyle("yellow", style3);
        stylesheet.putCellStyle("black", style4);
        stylesheet.putCellStyle("blue", style5);
        stylesheet.putCellStyle("cyan", style6);
        stylesheet.putCellStyle("darkGray", style7);
        stylesheet.putCellStyle("gray", style8);
        stylesheet.putCellStyle("green", style9);
        stylesheet.putCellStyle("magenta", style10);
        stylesheet.putCellStyle("pink", style11);
        
        ArrayList<String> j = new ArrayList<>();
        j.add("green");
        j.add("red");
        j.add("orange");
        j.add("yellow");
        j.add("black");
        j.add("bleu");
        j.add("cyan");
        j.add("darkGray");
        j.add("gray");
        j.add("magenta");
        j.add("pink");
        
        /*String styleCell = "Style";
        Random rand = new Random(0);
        
        for(int i=0;i<commVertex.size();++i){
            styleCell=styleCell+i;
            
            int r = rand.nextInt(255);
            int f = rand.nextInt(255);
            int b = rand.nextInt(255);
            Color c = new Color(r,f,b);
            
            Hashtable<String, Object> style3 = new Hashtable<String, Object>();
            style3.put(mxConstants.STYLE_FILLCOLOR,c);
            stylesheet.putCellStyle(styleCell, style3);
            styleCell="Style";
        }*/
        
        int f=0; 
        ArrayList<Object> cellsGroup = new ArrayList<>();
        
        for(String key : commVertex.keySet()){
            for(int l=0;l<commVertex.get(key).size();++l){
                cellsGroup.add(vertexToCell.get(commVertex.get(key).get(l)));
            }
            jgxAdapter.setCellStyle(j.get(f),cellsGroup.toArray());
            cellsGroup.clear();
            f++;
        }
        //mxHierarchicalLayout layout = new mxHierarchicalLayout(jgxAdapter);
        mxFastOrganicLayout layout = new mxFastOrganicLayout(jgxAdapter);
        layout.execute(jgxAdapter.getDefaultParent());
    }
}
