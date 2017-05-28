package FYPpackage.LeadersCommunityTimeline;

import java.util.ArrayList;
import java.util.HashMap;

public class Leader_Community {
   public HashMap<String,ArrayList<Integer>> leaders_Attribution; 
   
   public Leader_Community(){
       leaders_Attribution = new HashMap();
   } 
   
   public void attributeLC(HashMap<String,ArrayList<Integer>> communities,ArrayList<Double> rank){
    for(String key : communities.keySet()){
        leaders_Attribution.put(key, new ArrayList<>());
        for(int i=0;i<communities.get(key).size();++i){
            int add_Index=0;
            for(int j=0;j<leaders_Attribution.get(key).size();++j){
                if(rank.get(communities.get(key).get(i))<rank.get(leaders_Attribution.get(key).get(j))){
                    add_Index++;
                }
            }
            leaders_Attribution.get(key).add(add_Index, communities.get(key).get(i));           
        }
    }
   } 
}
