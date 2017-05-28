package FYPpackage.leaders_detection;

import static java.lang.Math.*;
import java.util.ArrayList; 
import java.util.HashMap;

public class PageRank {
   
    Matrix matrixGraph;
    public ArrayList<Double> rankVector;
    public HashMap<Integer , ArrayList<Integer>> map;
    int size;
    public HashMap<Integer,Double> rankedLeaders = new HashMap<>();
    
 public void rankedLeaders(){
        ArrayList<Double> leaders = new ArrayList<>();
        for(int i=0;i<rankVector.size();++i){
            leaders.add(rankVector.get(i));
        }
        ArrayList<Integer> indexes = new ArrayList<>();
        for(int j=0;j<rankVector.size();++j){
            double max =0;
            for(int i=0;i<rankVector.size();++i){
                if(leaders.get(i)>max) max = leaders.get(i);
            }
            for(int i=0;i<rankVector.size();++i){
                if(rankVector.get(i)==max) indexes.add(i);
            }
            for(int i=0;i<indexes.size();++i){
                rankedLeaders.put(indexes.get(i), max);
            }
            indexes.clear();
            for(int i=0;i<indexes.size();++i){
                leaders.remove(indexes.get(i));
            }
        }
    }
    
    public PageRank(int size){ 
        
        this.size=size;
        
        matrixGraph = new Matrix(size);
        
        rankVector = new ArrayList<>();
        
        for(int i=0;i<matrixGraph.getSize();++i){
            rankVector.add(i,1.0/size);
        }
        map = new HashMap<>();
    }
  
   public void fillIn(HashMap<Long,ArrayList<Long>> extracted, HashMap<Long,Integer> attributed){
        for(Long key : extracted.keySet()){
            ArrayList<Long> temp = new ArrayList<>();
            temp = extracted.get(key);
            ArrayList<Integer> followers = new ArrayList<>();
            for(int l=0;l<extracted.get(key).size();++l){
                Long a = temp.get(l);
                if(attributed.containsKey(extracted.get(key).get(l))){
                    followers.add(attributed.get(extracted.get(key).get(l)));
                }
                else{
                    extracted.get(key).remove(l);
                    l--;
                }
            }
            map.put(attributed.get(key), followers);
        }
        
        ArrayList<Integer> degree = new ArrayList<>();
        for(int i=0;i<size;++i){
            int compteur = 0;
            for(int j=0;j<map.size();++j){
                if(map.get(j).contains(i)){
                    compteur++;
                }
            }
            degree.add(compteur);
        }
        
        for(Integer key : map.keySet()){
            for(int i=0;i<map.get(key).size();++i){
                matrixGraph.connect(map.get(key).get(i), key,1.0/degree.get(map.get(key).get(i)));
            }
        }
    }
   
    public double diffModule(ArrayList<Double> v1,ArrayList<Double> v2) throws Exception{
        if(v1.size()==v2.size()){
            double diff=0.0;
            for(int i=0;i<v1.size();++i){
                diff+=(v2.get(i)-v1.get(i))*(v2.get(i)-v1.get(i));
               
            }
            return diff;
        }
        else{
            throw new Exception("Vector Size");
        }
    }
    
    public void index() throws Exception{
            ArrayList<Double> rankIterated = new ArrayList<>();

            boolean iteration = true;
            
            do{
                rankIterated = matrixGraph.rankProduct(rankVector);
                
                if(diffModule(rankIterated, rankVector)<1e-7){
                    iteration = false;
                }
                for(int i =0;i<matrixGraph.getSize();++i){
                    rankVector.set(i, rankIterated.get(i));
                }
                        
                rankIterated.clear();
            }while(iteration);
    }
    
    public int displayRank(){
        System.out.println("Rank :");
        double s=0;
        double max =-1;
        int indice =0;
        for(int i=0;i<rankVector.size();++i){
            if(max<rankVector.get(i)){
                indice = i;
                max = rankVector.get(i);
            }
            s+=rankVector.get(i);
            System.out.print(rankVector.get(i)+" ");
        }
        System.out.println();
        System.out.println("Rank Vector sum :");
        System.out.println(s);
        
        
        //printing max
        System.out.println("Our leaders is "+max);
        return indice;
    }
}

        /*public void fillIn(HashMap<Long,Pair<ArrayList<Long>,Integer>> extracted, HashMap<Long,Integer> attributed, HashMap<Integer,Long> attribution_inv){
        for(Long key : extracted.keySet()){
            ArrayList<Long> temp = new ArrayList<>();
            temp = extracted.get(key).getL();
            ArrayList<Integer> followers = new ArrayList<>();
            for(int l=0;l<extracted.get(key).getL().size();++l){
                Long a = temp.get(l);
                if(attributed.containsKey(extracted.get(key).getL().get(l))){
                    followers.add(attributed.get(extracted.get(key).getL().get(l)));
                }
                else{
                    extracted.get(key).getL().remove(l);
                    l--;
                }
            }
            map.put(attributed.get(key), followers);
        }
        
        ArrayList<Integer> degree = new ArrayList<>();
        for(int i=0;i<size;++i){
            int compteur = 0;
            for(int j=0;j<map.size();++j){
                if(map.get(j).contains(i)){
                    compteur++;
                }
            }
            degree.add(compteur);
        }
        
        for(Integer key : map.keySet()){
            for(int i=0;i<map.get(key).size();++i){
                //long node = attribution_inv.get(map.get(key).get(i));
                //int degree = extracted.get(node).getR();
                //int degree = 0;
                matrixGraph.connect(map.get(key).get(i), key,1.0/degree.get(i));
                //this.connect(map.get(key).get(i),key);
            }
        }
    }*/