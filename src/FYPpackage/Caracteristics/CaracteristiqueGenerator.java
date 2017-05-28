/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FYPpackage.Caracteristics;


import FYPpackage.DataExtraction.TwitterApplicationCredentials;
import FYPpackage.LeadersCommunityTimeline.Community;
import FYPpackage.Testing_package.DummyEntity;
import FYPpackage.Testing_package.DummyUser;
import FYPpackage.Testing_package.TwitterDataExtraction;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.RateLimitStatus;
import twitter4j.ResponseList;
import twitter4j.TwitterException;
import twitter4j.User;

/**
 *
 * @author generals
 */
public class CaracteristiqueGenerator {
    ArrayList<Community> communities;
    ArrayList<String> criteres;
    HashMap<String,HashMap<String,Double>> result = new HashMap<>();
    HashMap<Long,User> users = new HashMap<>();
    HashMap<Integer,Long> integrationGraphInvers;
    ArrayList<TwitterApplicationCredentials> list_app = new ArrayList<>();
    
    public CaracteristiqueGenerator(ArrayList<TwitterApplicationCredentials> list_app,ArrayList<String> criteres,ArrayList<Community> communities,
            HashMap<Long,ArrayList<Long>> new_followers,
            HashMap<Integer,Long> integrationGraphInvers
    )
    {

        this.criteres = criteres;
        this.communities = communities;
        //this.users = users;
        this.list_app = list_app;
        this.integrationGraphInvers = integrationGraphInvers;
        //applicationInitialisation();//for testing;
        retrieveUsers(new_followers);
    }
    
    private User retrieveUser(Long aTraiter,TwitterDataExtraction extract, RateLimitStatus status,int k)
    {
            for(int i=0;i>=0;i=k%this.list_app.size())
            {
                System.out.println("\n using  "+i+" em application to getFollowing");
                //twitter4j.Twitter twitter = list_app.get(i).getTwitterConf();
                //extract.setTwitterAccount(twitter);


                // status = twitter.getRateLimitStatus("followers").get("/followers/ids");
                try{
                    //status = twitter.getRateLimitStatus("users").get("/users/lookup");
                    status = extract.getRateLimitStatus("users").get("/users/lookup");
                    //twitter.getUserTimeline(aTraiter).get(0).getUser().getFriendsCount();
                    //twitter.lookup(aTraiter).get(0).getUser().getFriendsCount();
                }
                catch(TwitterException ex2){
                    try {
                        // thread to sleep for 1000 milliseconds
                        System.out.println("thread of getting users for generation of caracteristics will sleep");                            
                        Thread.sleep(900000);
                        System.out.println("thread of  woke up");
                        // store.close();
                        // store = new PostgresStore("twitter");
                        continue;
                    }
                    catch(Exception e){
                        System.out.println("thread couldn't sleep while getting following of account");
                    }   
                }

                if(status.getRemaining()>0)
                {
                    try{
                        //ResponseList<User> response = twitter.lookupUsers(aTraiter);
                        ResponseList<User> response = extract.lookupUsers(aTraiter);                           

                        if(response.size()!=0)
                        {                //added
                            User user = response.get(0);
                            if(!users.containsKey(user.getId()))
                                users.put(user.getId(), user);
                            return user;

                            //following = response.get(0).getFriendsCount();
                        }
                        //if we didn't have following so the account is private
                        break;
                    }
                    catch (TwitterException ex1)
                    { //its a private account so we couldn't retrieve its following;
                        System.out.println("cannot retrieve following of that private account "+aTraiter);
                        // Logger.getLogger(Extraction_thread.class.getName()).log(Level.SEVERE, null, ex1);
                        break;
                    }        
                }
                else
                {
                    k++;
                }
            }   
            return null;
    }
    private void retrieveUsers (HashMap<Long,ArrayList<Long>> new_followers)
    {
       //int following=-1;
       //User user  ;
       int k =0;
       RateLimitStatus status = null;
       TwitterDataExtraction extract;
        //extract = new RealEntity(twitter);
        
       //extract = new RealEntity();
        extract = new DummyEntity();
        for(Long aTraiter:new_followers.keySet())
        {
            User user = retrieveUser(aTraiter,extract,status,k);
            
            for(Long follower: new_followers.get(aTraiter))
                retrieveUser(follower,extract,status,k);
        }
        
    }
    
    
    
    public void getCaracteristique()//on va generer les caracteristiques de chacune des communautees et l'initier dans l'attribut communities;
    {
        for(Community comm :communities)
        {
            HashMap<String,HashMap<String,Double>> caracteristiques = comm.getCaracteristiques();
            ArrayList<Integer> members = comm.members;
            
            for(String c: criteres)
            {
                HashMap<String,Double> values = new HashMap<>();
                caracteristiques.put(c, values);
            }
            
            for(int i =0;i<members.size();++i)
            {
                User user = users.get(integrationGraphInvers.get(members.get(i)));
                
                String userPath = "twitter4j.User";
                
                
                for(String critere :criteres)
                {                   
                    String valueCriteria = "";
                    try {
                        //invoking dynamically the getter of that criteria on the user;
                        
                        String methodName = "get"+critere;
                        Class[] noparams = {};
                        Class c;       
                        c = Class.forName(userPath);
                        Method method = c.getDeclaredMethod(methodName,noparams);
                        System.out.println(user.getLocation());
                        valueCriteria = (String) method.invoke(user,null);
                       System.out.println("user :"+members.get(i)+" ,critere: "+critere+" ,val:"+valueCriteria);
                    } catch (ClassNotFoundException ex1) {
                        Logger.getLogger(CaracteristiqueGenerator.class.getName()).log(Level.SEVERE, null, ex1);
                    } catch (NoSuchMethodException ex1) {
                        Logger.getLogger(CaracteristiqueGenerator.class.getName()).log(Level.SEVERE, null, ex1);
                    } catch (SecurityException ex1) {
                        Logger.getLogger(CaracteristiqueGenerator.class.getName()).log(Level.SEVERE, null, ex1);
                    } catch (IllegalArgumentException ex1) {
                        Logger.getLogger(CaracteristiqueGenerator.class.getName()).log(Level.SEVERE, null, ex1);
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(CaracteristiqueGenerator.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InvocationTargetException ex) {
                        Logger.getLogger(CaracteristiqueGenerator.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    if(caracteristiques.get(critere).containsKey(valueCriteria))
                    {
                        double val = caracteristiques.get(critere).get(valueCriteria);
                        val = val + 1;
                        caracteristiques.get(critere).replace(valueCriteria, val);
                    }
                    else
                    {
                          caracteristiques.get(critere).put(valueCriteria, 1.0);
                    }
                }
            }
        }
    }
    
}
