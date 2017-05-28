/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FYPpackage.CommunityDetection;



import FYPpackage.DataExtraction.Extraction_thread;
import FYPpackage.DataExtraction.TwitterApplicationCredentials;
import FYPpackage.Testing_package.DummyEntity;
import FYPpackage.Testing_package.TwitterDataExtraction;
import FYPpackage.Utils.CollectionsModified;
import FYPpackage.Utils.CombinationGenerator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Collections;
import java.util.stream.Collectors;
import twitter4j.RateLimitStatus;
import twitter4j.TwitterException;
import twitter4j.User;

/**
 *
 * @author generals
 */
public class BigClam {
    
    ArrayList<ArrayList<Double>> appartenance = new ArrayList<>();
    HashMap<Integer,ArrayList<Integer>> neighbors = new HashMap<>();
    ArrayList<Double> sigmaFv = new ArrayList<>();
    int number_of_community ;
    HashMap<Long,Integer> integrationGraph = new HashMap<>();
    HashMap<Integer,Long> integrationGraphInvers = new HashMap<>();
    //hashMap to attribute each keyword to one community r index r column in appartenance;
    HashMap<Long,Integer> keywordId = new HashMap<>();

    public HashMap<Integer,String>keywordIndexName = new HashMap<>();
    //remove l int a after testing
    //added to visualise the username of each twitter id that we had retrieved
    HashMap<Long,String> id_username = new HashMap<>();
    //used to retrieve username of ids --- 
    ArrayList<TwitterApplicationCredentials> list_app = new ArrayList<>();
    //HashMap in which we will store all the types of communities;=>String: name of community; Pair=>Integer: account index, ArrayList=>appartenance of account
    public HashMap<String,ArrayList<Integer>> attribution_of_communities = new HashMap<>();
    public HashMap<String,String> attribution_communities_names = new HashMap<>();
    public HashMap<String,ArrayList<Integer>> attribution_of_communites_showGraph = new HashMap<>();

    public BigClam (ArrayList<Extraction_thread> runnableThread, HashMap<Integer,ArrayList<Integer>> n,int number_of_community,HashMap<Long,Integer> integration,HashMap<Integer,Long> integrationInvers,ArrayList<TwitterApplicationCredentials> list_app)
    {
        this.neighbors=n;
        this.integrationGraph=integration;
        this.integrationGraphInvers=integrationInvers;
        this.number_of_community=number_of_community;
        this.list_app = list_app;
       //keyword ATTRIBUTION
        for(int i=0;i<runnableThread.size();++i)
        {
            keywordId.put(runnableThread.get(i).keyword_id, i);
            keywordIndexName.put(i, runnableThread.get(i).keyword);
        }
        
        try {
            initialise(runnableThread);
        } catch (Exception ex) {
            Logger.getLogger(BigClam.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void communityAttribution(double seuil)
    {
        //creating all the combinations of possible communities; to initiate attribution_of_communities;
        String communities = "";
        String communities_name = "";
        
        for(int i=0;i<number_of_community;++i){         
            communities+=i;
            communities_name+=keywordIndexName.get(i);
        }
        
        CombinationGenerator generator = new CombinationGenerator(communities);
        ArrayList<String> combinations = generator.result;
        combinations.add(combinations.size(),"");
        
        CombinationGenerator generatorName = new CombinationGenerator(communities_name);
        ArrayList<String> combinations_name = generatorName.result;
        combinations_name.add(combinations_name.size(),"Neutre");
        
        System.out.println("\n Attribution Of Communities");
        for(int i=0;i<combinations.size();++i)
        {   
            ArrayList<Integer> keyvalue = new ArrayList<>();
            attribution_of_communities.put(combinations.get(i),keyvalue);
            attribution_communities_names.put(combinations.get(i), combinations_name.get(i));
            ArrayList<Integer> keyValueShowGraph = new ArrayList<>();
            this.attribution_of_communites_showGraph.put(combinations.get(i), keyValueShowGraph);
            System.out.println(""+combinations.get(i)+"  -> "+combinations_name.get(i));
        }
        
        
        //will iterate on all accounts in appartenance, to add each to the suitable community initialized before;
        boolean isHeadAccount = false;
        for(int i=0;i<appartenance.size();++i)
        {
            ArrayList<Double> appartenanceTemp = appartenance.get(i);
            String community = "";
            for(int j=0;j<appartenanceTemp.size();++j)
            {
                isHeadAccount = false;
                if(j == i)
                {
                    attribution_of_communities.get(""+j).add(i);
                    this.attribution_of_communites_showGraph.get(""+j).add(i);
                    isHeadAccount = true;
                    break;
                }
                else
                {
                    if(appartenanceTemp.get(j)>=seuil)
                    {
                        community+=j;
                        if(community.length()>1)
                        {
                            attribution_of_communities.get(""+j).add(i);
                        }
                        attribution_of_communities.get(community).add(i); //added
                    }
                }
            }
            if(!isHeadAccount)
            {
                attribution_of_communites_showGraph.get(community).add(i); 
            }
            
        }

        for(String commKey: this.attribution_of_communites_showGraph.keySet())
        {
            System.out.print("\n "+commKey+"  | size: "+this.attribution_of_communites_showGraph.get(commKey).size()+" | \n");
            for(int i=0;i<this.attribution_of_communites_showGraph.get(commKey).size();++i)
            {
                System.out.print(" "+this.attribution_of_communites_showGraph.get(commKey).get(i));
            }
            System.out.print("\n");
        }
    }
   
    //needed to create the matrix of powers and initialise the sigmaFv by 0s as much as we have communities;  
    public void initialise(ArrayList<Extraction_thread> threads) throws Exception{
        //create the appartenance matrix
       
        for(int i=0;i<this.integrationGraph.size();++i)
        {
            ArrayList<Double> l = new ArrayList<>();
            
            for(int j=0;j<number_of_community;++j)
                l.add(new Double(0.0));
            //KEN FI 0.1
            appartenance.add(l);
        } 
       //
        for(int i=0;i<threads.size();++i)
        {
            //we have all the accounts that are at a distance of key from the keyword i represented by the community i ;
            double dist = 1;
            for(Integer key : threads.get(i).distAttribution.keySet())
            {
                ArrayList<Long> accounts = threads.get(i).distAttribution.get(key);
                if(key == 0)
                    throw new Exception("distance between keyword and account couldn't be zero");
                
                dist= 1.0/key;
                
                for(int j=0;j<accounts.size();++j)
                {//WE ARE WORKING WITH DISTATTRIBUTION SO WE MAY HAVE IDS OF PRIVATE ACCOUNTS;
                    if(integrationGraph.containsKey(accounts.get(j)))
                    {
                        int index = integrationGraph.get(accounts.get(j));
                        appartenance.get(index).set(i, dist);          
                    }            
                }
            }
        }
       
       //
   
        //initialise sigmaFv at 0;
        for(int i=0;i<number_of_community;++i)
        {
            sigmaFv.add(i,(double)0.0);
        }
    }
    
    //To find if we had reached the maximum;
    public boolean isStable(ArrayList<Double> gradient,double epsinum,int iteration, int index)
    {
        double result =0;
        
        for(int i=0;i<gradient.size();++i)
        {
            result = result + gradient.get(i)*gradient.get(i);
        }
        System.out.println("result of isStable for the "+iteration+" rst iteration at index:"+index+" is: "+ result +" \n");
        
        if(Math.sqrt(result)<epsinum)
            return true;
        else
            return false;
//        for(int i=0;i<gradient.size();++i){
//            if(Math.abs(gradient.get(i))<epsinum)
//                result ++;
//        }
//        if(result==gradient.size())
//            return true;
//        else
//            return false;
    }
    public boolean isStable(ArrayList<Double> gradient,double epsinum,int iteration,int index,double oldLogFu,double newLogFu)
    {
        double result =0;
        
//        for(int i=0;i<gradient.size();++i)
//        {
//            result = result + gradient.get(i)*gradient.get(i);
//        }
        
      // System.out.println("result of isStable for the "+iteration+" rst iteration at index:"+index+" \n");
        
        //if((Math.abs(oldLogFu-newLogFu)/oldLogFu)<epsinum)
        
        if(Math.abs(oldLogFu-newLogFu)<epsinum)
            return true;
        
        else
            return false;
    }
    //while iterating over the rows, we need to update sigmaFv that we had cashed;
    public void updateSigmaFv(ArrayList<Double> lapl,double beta)
    {
        for(int i=0;i<sigmaFv.size();++i)
            sigmaFv.set(i,sigmaFv.get(i)+beta*lapl.get(i));
    }
    
    //needed to add two vectors together;
    public void addVector(ArrayList<Double> v1,ArrayList<Double>v2)
    {
        for(int i=0;i<v1.size();++i)
        {
            v1.set(i, v1.get(i)+v2.get(i));
        }
    }
    public void addVectorParticular(ArrayList<Double> v1, ArrayList<Double>v2)
    {
        for(int i=0;i<v1.size();++i)
        {
            if(v1.get(i)+v2.get(i)<0)
                v1.set(i,(double) 0.0);//KEN FI 0.1
            else
                v1.set(i, v1.get(i)+v2.get(i));
        }
    }
    
    //needed in the beginng to calculate sigmaFv once, and cash it as optimization
    public void initialiseSigmaFv()
    {
        for(int i=0;i<appartenance.size();++i)
        {
            addVector(sigmaFv,appartenance.get(i));
        }
    }
    //user for the showApppartenance to retrieve the screen name of the id-
    private Long getKeyFromValue(int value) {
        for (Long o : integrationGraph.keySet()) {
          if (integrationGraph.get(o).equals(value)) {
            return o;
          }
        }
    return new Long("-1");
  }
    
    //show  members for each community 
    public void showAppartenance2()
    {
        for(String key:attribution_of_communities.keySet())
        {
            System.out.println("members of community "+key+":");
            for(int i=0;i<attribution_of_communities.get(key).size();++i)
            {
                Long id = integrationGraphInvers.get(attribution_of_communities.get(key).get(i));
                String name= ""+id;
                if(id_username.containsKey(id))
                {
                    name = id_username.get(id);
                }
                else
                {
                   System.out.print("\n in showAppartenance2, the name of "+id +" wasn't found");
                }
                System.out.print(name+" , ");
            }
            System.out.print("\n");
        }
    }
    //show appartenance of each account;
    public void showAppartenance1()
    {
        for(int i=0;i<appartenance.size();++i)
        {
            //System.out.print(" user: "+getUsername(getKeyFromValue(i)));
            System.out.print(" user: "+getUsername(integrationGraphInvers.get(i)));
            for(int j=0;j<appartenance.get(i).size();++j)
            {
                System.out.print(" "+appartenance.get(i).get(j));
            }
            System.out.print("\n");
        }
    }
    //retrieve the username of that id -- used in showAppartenance
    private String getUsername(Long id)
    {
        if(this.id_username.containsKey(id))
            return id_username.get(id);
        else
        {
            String name = getUsernameApi(id);
            id_username.put(id, name);
            return name;
        }
    }
    
    private String getUsernameApi(Long id)
    {
        int k=0;
        RateLimitStatus status = null;
        User user = null;
        
        //
            TwitterDataExtraction extract;
            //extract = new RealEntity();
            extract = new DummyEntity(); 
        //
        for(int i=0;i>=0;i=0+k%list_app.size())
        {
            //twitter4j.Twitter twitter = list_app.get(i).getTwitterConf();
            //extract.setTwitterAccount(twitter);
            
            try {
                    //status = twitter.getRateLimitStatus("users").get("/users/lookup");
                    status = extract.getRateLimitStatus("users").get("/users/lookup");
                    //user = twitter.showUser(id);
                    if(status.getRemaining()<1)
                    {
                        ++k;
                        continue;
                    }
                    user = extract.showUser(id);
                    return user.getScreenName();               
            } 
            catch (TwitterException ex){
                try
                {
                            // thread to sleep for 1000 milliseconds
                            System.out.println("thread of community detection will sleep for 15 minutes while retrieving the screename of "+id);                            
                            Thread.sleep(900000);
                            continue;
                }
                catch(Exception e)
                {
                        System.out.println("thread couldn't sleep while retrieving the screename of account");
                }
            }   
            
        }
        return "Not found id of:"+id;
    }
    
    //take index of the row that we need to calculate its laplacien; we need to update the sigmaFv in it;
    ArrayList<Double> getGradient(int u)
    {
        
        ArrayList<Double> secondField =  getSecondField(u);
        
        ArrayList<Double> first_field = getFirstField(u);
        //updateSigmaFv()
        addVector(first_field,oppose(secondField));
        
        return first_field;
    }
    
    public double logFu2nd(int u)
    {//sigma(Fu,transpose(Fv)); => produit(Fu, sigma(Fv));
        ArrayList<Double> temp = new ArrayList<>();
        
        temp = getSecondField(u);
        double product = product(appartenance.get(u),temp);
        
        return product;
    }
    
    public double logFu2nd()
    {//sigma(Fu,transpose(Fv)); => produit(Fu, sigma(Fv));
            //ArrayList<Double> temp = new ArrayList<>();
            double temp = 0;
            temp = getSecondField();
            //double product = product(appartenance.get(u),temp);

            return temp;
    }
    
    double getSecondField()
    {
                double resultFinal = 0;
                for(Integer u:neighbors.keySet())
                {
                    ArrayList<Double> result = new ArrayList<>();

                    for(int i=0;i<number_of_community;++i)
                        result.add((double)0.0);

                    ArrayList<Double> sigma_neighbors = sigma_neighbors(u);

                    for(int i=0;i<result.size();++i)
                    {
                        double val1 = (double) (sigmaFv.get(i)-appartenance.get(u).get(i)-sigma_neighbors.get(i));
                        result.set(i, val1);
                    }

                    double product = product(appartenance.get(u),result);
                    resultFinal += product;
                }                        
            return resultFinal;
    }

    public double logFu1rst()
    {
        double resultFinal = 0;
//        for(int u=0;u<appartenance.size();++u)
        for(Integer u : neighbors.keySet())
        { 
            double result = 0;
            ArrayList<Integer> n = this.neighbors.get(u);        
            for(int i=0;i<n.size();++i)
            {
                double product = product(appartenance.get(u),appartenance.get(n.get(i)));
                if(product != 0)
                {
                    double temp_res = Math.log(1-Math.exp(-product));
                    result = result + temp_res;
                }
            }
            resultFinal+=result;
        }
        return resultFinal;
    }    
    
    public double logFu1rst(int u)
    {
        double result = 0;
               
        ArrayList<Integer> n = this.neighbors.get(u);        
        for(int i=0;i<n.size();++i)
        {
            double product = product(appartenance.get(u),appartenance.get(n.get(i)));
            if(product != 0)
            {
                double temp_res = Math.log(1-Math.exp(-product));
                result = result + temp_res;
            }
        }
        
        return result;
    }
    
    public double logFu()
    {
        double result =0;
        result = logFu1rst()  - logFu2nd();
        return result;        
    }
    
    public double logFu(int u)
    {
        double result =0;
        result = logFu1rst(u)  - logFu2nd(u);
        return result;
    }
    
    public ArrayList<ArrayList<Double>> matrixDif(ArrayList<ArrayList<Double>> appartenanceBefore,ArrayList<ArrayList<Double>> appartenanceAfter)
    {
        ArrayList<ArrayList<Double>> result = new ArrayList<>();
        for(int i=0;i<appartenanceBefore.size();++i)
        {
            ArrayList<Double> row = new ArrayList<>();
            for(int j=0;j<appartenanceBefore.get(i).size();++j)
            {
                row.add(j,Math.abs(appartenanceBefore.get(i).get(j)-appartenanceAfter.get(i).get(j)));
            }
            result.add(i,row);
        }
        
        return result;
    }
    
    public double normOne(ArrayList<ArrayList<Double>> appartenanceDiff)
    {//ca correspond a la somme de la colonne qui a comme somme maximal;
        double result = 0;
        for(int j=0;j<appartenanceDiff.get(0).size();++j)
        {
            double temp = 0;
            for(int i=0;i<appartenanceDiff.size();++i)
            {
                temp+=appartenanceDiff.get(i).get(j);
            }
            if(temp>result)
                result = temp;
        }
        return result;
    }
    
    public void findCommunities1(double beta,double eps)
    {
        initialiseSigmaFv();
        int iteration = 1;
        int k=0;
        int nombre =0;
        double ecart = 1000;
        double oldLogFu = 0;
        double newLogFu = 0;
        while(ecart>eps)
        {
            
            //ArrayList<ArrayList<Double>> old = (ArrayList<ArrayList<Double>>) this.appartenance.clone();
            //ArrayList<ArrayList<Double>> old = new ArrayList<>(this.appartenance);
            ArrayList<ArrayList<Double>> old = new ArrayList<>();
            //Collections.copy(old, appartenance);
            if(!CollectionsModified.copy(old, appartenance))
                System.out.println("couldn't copy the appartenance");
                    //.stream().collect(Collectors.toList());
            oldLogFu = logFu();
            for(int i=0;i<appartenance.size();++i)
            {
  //                  double oldLogFu = logFu(i);
  //                  System.out.print(" \n oldLog(F"+i+"):"+oldLogFu+"   ");

                    ArrayList<Double> lap = getGradient(i);
                    addVectorParticular(appartenance.get(i),product_Factor(lap,beta));
                    updateSigmaFv(lap,beta);
  //                  double newLogFu = logFu(i);
  //                 // System.out.print(" newLog(F"+i+"):"+newLogFu+"\n");

  //                  if(!isStable(lap,eps,iteration,i,oldLogFu,newLogFu))
  //                  {
  //                      System.out.println("\n not stable");
  //                  }
  //                  else
  //                  {
  //                          System.out.println("after "+iteration+" rst iteration at index:"+i+" we found convergence \n");

  //                          System.out.println("");
  //                          break;

   //                 }

 //                   if(i==appartenance.size()-1)
 //                      ++iteration;
            }
            newLogFu = logFu();
            //ecart = normOne(matrixDif(old,this.appartenance))/normOne(old);
            ecart = Math.abs(newLogFu-oldLogFu)/Math.abs(oldLogFu);
            System.out.println("ecart:"+ecart);
            
        }
          
        System.out.println("community founded with, ecart:"+ecart);
    }
    
    public static void main(String []argv)
    {
        ArrayList<Double> l = new ArrayList<>();
        //ArrayList<Double> lc = (ArrayList<Double>)l.clone();
        ArrayList<Double> lc = new ArrayList<>(l);
        l.add((double)1);
        System.out.println("size of l:"+l.size()+" and size of lc:"+lc.size());
    }
    
    public void findCommunities(double beta,double eps)
    {
        initialiseSigmaFv();
        int iteration = 1;
        int k=0;
        int nombre =0;
            //for(int i=0;i<appartenance.size();++i)
            for(int i=0;i>=0;i=0+k%appartenance.size())
            {
                double oldLogFu = logFu(i);
                System.out.print(" \n oldLog(F"+i+"):"+oldLogFu+"   ");
                
                ArrayList<Double> lap = getGradient(i);
                /*if(!isStable(lap,eps,iteration,i))
                {
                  //  System.out.print("\n row "+i+" modified");
                   addVectorParticular(appartenance.get(i),product_Factor(lap,beta));
                }
                else
                {
                 //   System.out.print("\n at row "+i+" algorithm was stopped");
                    break;
                }
                */
                addVectorParticular(appartenance.get(i),product_Factor(lap,beta));
                updateSigmaFv(lap,beta);
                
                double newLogFu = logFu(i);
                //System.out.print(" newLog(F"+i+"):"+newLogFu+"\n");
                System.out.println("\n ecart:"+Math.abs(newLogFu-oldLogFu));
                if(!isStable(lap,eps,iteration,i,oldLogFu,newLogFu))
                {
                    System.out.println("\n not stable");
                }
                else
                {
                   // if(nombre >= appartenance.size())
                   // {
                        System.out.println("after "+iteration+" rst iteration at index:"+i+" we found convergence \n");
        
                        System.out.println("");
                        break;
                    
                    //}
                   // else
                     //   ++nombre;
                }

                if(i==appartenance.size()-1)
                  ++iteration;
                
                ++k;
               
            }
            
        System.out.println("founded with "+iteration+" iteration");
    }
    
    ArrayList<Double> getSecondField(int u)
    {
        ArrayList<Double> result = new ArrayList<>();
        
        for(int i=0;i<number_of_community;++i)
            result.add((double)0.0);
        
        ArrayList<Double> sigma_neighbors = sigma_neighbors(u);
        
        for(int i=0;i<result.size();++i)
        {
            double val1 = (double) (sigmaFv.get(i)-appartenance.get(u).get(i)-sigma_neighbors.get(i));
            result.set(i, val1);
        }
        
        return result;
    }
    
    ArrayList<Double> sigma_neighbors(int u)
    {
        ArrayList<Double> neighbors = new ArrayList<>();
        for(int i=0;i<number_of_community;++i)
            neighbors.add((double)0.0);
        
        if(this.neighbors.containsKey(u))
        {
            ArrayList<Integer> n = this.neighbors.get(u);

            for(int i=0;i<n.size();++i)
            {
                int neighbor = n.get(i);
                addVector(neighbors,appartenance.get(neighbor));
            }
        }
        
        return neighbors;
    }

    ArrayList<Double> oppose(ArrayList<Double> l)
    {
        for(int i=0;i<l.size();++i)
            l.set(i, -1*l.get(i));
        
        return l;
    }
    
    ArrayList<Double> getFirstField(int u)
    {
        ArrayList<Double> result = new ArrayList<>();
        for(int i=0;i<number_of_community;++i)
            result.add((double)0.0);
        
       if(this.neighbors.containsKey(u))
       {
            ArrayList<Integer> neighbors = this.neighbors.get(u);

            ArrayList<Double> temp = new ArrayList<>();

            for(int i=0;i<neighbors.size();++i)
            {
                double product_fu_fv = product(appartenance.get(u),appartenance.get(neighbors.get(i)));
                temp = new ArrayList<>();
                temp = (ArrayList<Double>) appartenance.get(neighbors.get(i)).clone();
                
                if(product_fu_fv != 0)
                {
                    double factor = (double) (Math.exp(-product_fu_fv)/(1-Math.exp(-product_fu_fv)));
                    product_Factor(temp,factor);
                    addVector(result,temp);                             
                }
            }       
       }
       return result;
    }
    
    double product(ArrayList<Double> f_u,ArrayList<Double> f_v)
    {
        double result = 0;
        
        for(int i=0;i<f_u.size();++i)
        {
            result = result + f_u.get(i)*f_v.get(i);
        }
        
        return result;
    }
    
    ArrayList<Double> product_Factor(ArrayList<Double> l,double factor)
    {
        for(int i=0;i<l.size();++i)
        {
            l.set(i, l.get(i)*factor);
        }
        return l;
    }
    
}

/*
        for(String key : communities.keySet()){
          //for(int i=0;i<this.communities.size();++i){  
//            if(!nodeVertex.containsKey(key)){
//                nodeVertex.put(key, s+k);
//                k++;
//                s="s";
//            }
            //ArrayList<Integer> members = this.communities.get(i).members;
            ArrayList<Integer> members = this.communities.get(key);
            //for(int j=0;j<graph.get(key).size();++j){
            for(int j=0;j<members.size();++j){  
                if(!nodeVertex.containsKey(members.get(j))){
                    //nodeVertex.put(graph.get(key).get(j), s+k);
                    nodeVertex.put(members.get(j), s+members.get(j));
                    k++;
                    s="s";
                }
            }
        }
*/